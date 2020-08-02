package com.zy.rpc.netty.demo01.common.zk;

import com.zy.rpc.netty.demo01.common.demo.Constants;
import com.zy.rpc.netty.demo01.common.exception.ZkException;
import lombok.Getter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;

public class CuratorZkClient implements IZkClient {
    private final CuratorFramework client;
    private final Map<String, TreeCache> treeCacheMap = new ConcurrentHashMap<>();
    @Getter
    private final Set<StateListener> stateListeners = new CopyOnWriteArraySet<>();
    private final Map<String, ConcurrentMap<ChildListener, CuratorWatcherImpl>> childListeners = new ConcurrentHashMap<>();
    private final Map<String, ConcurrentMap<DataListener, CuratorWatcherImpl>> dataListeners = new ConcurrentHashMap<>();

    public CuratorZkClient(ZkUrl zkUrl) {
        try {
            CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                    .connectString(zkUrl.getConnection2String())
                    .connectionTimeoutMs(zkUrl.getConnectionTimeouts())
                    .retryPolicy(new RetryNTimes(1, 1000));
            String authority = zkUrl.getAuthority();
            if (authority != null && authority.length() > 0) {
                builder = builder.authorization("digest", authority.getBytes(StandardCharsets.UTF_8));
            }
            this.client = builder.build();
            this.client.getConnectionStateListenable().addListener((curatorFramework, connectionState) -> {
                if (connectionState == ConnectionState.LOST) {
                    CuratorZkClient.this.stateChanged(StateListener.DISCONNECTED);
                } else if (connectionState == ConnectionState.CONNECTED) {
                    CuratorZkClient.this.stateChanged(StateListener.CONNECTED);
                } else if (connectionState == ConnectionState.RECONNECTED) {
                    CuratorZkClient.this.stateChanged(StateListener.RECONNECTED);
                }
            });
            this.client.start();
        } catch (Exception e) {
            throw new ZkException("cannot create CuratorZkClient.", e);
        }
    }

    @Override
    public void stateChanged(int state) {
        for (StateListener stateListener : getStateListeners()) {
            stateListener.stateChanged(state);
        }
    }

    @Override
    public void addStateListener(StateListener stateListener) {
        this.stateListeners.add(stateListener);
    }

    @Override
    public void create(String path, boolean ephemeral, boolean sequential) {
        try {
            if (ephemeral) {
                if (sequential) {
                    this.client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path);
                } else {
                    this.client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
                }
            } else {
                if (sequential) {
                    this.client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(path);
                } else {
                    this.client.create().withMode(CreateMode.PERSISTENT).forPath(path);
                }
            }
        } catch (Exception e) {
            throw new ZkException(String.format("cannot create path:{%s}, ephemeral:{%s}, sequential:{%s}", path, ephemeral, sequential));
        }
    }

    @Override
    public void delete(String path) {
        try {
            this.client.delete().forPath(path);
        } catch (Exception e) {
            throw new ZkException(String.format("cannot delete path:{%s}.", path));
        }
    }

    @Override
    public void setData(String path, String data) {
        try {
            this.client.setData().forPath(path, data.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new ZkException(String.format("cannot setData:{%s} for path:{%s}.", data, path));
        }
    }

    @Override
    public String getData(String path) {
        try {
            byte[] bytes = this.client.getData().forPath(path);
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean checkExists(String path) {
        try {
            Stat stat = this.client.checkExists().forPath(path);
            return stat != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> getChildren(String path) {
        try {
            return this.client.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isConnected() {
        return this.client.getZookeeperClient().isConnected();
    }

    @Override
    public void close() {
        if (Objects.nonNull(this.client)) {
            this.client.close();
        }
    }

    /*************************** children listener *************************/
    @Override
    public List<String> addChildListener(String path, ChildListener listener) {
        ConcurrentMap<ChildListener, CuratorWatcherImpl> map = childListeners.get(path);
        if (map == null) {
            childListeners.putIfAbsent(path, new ConcurrentHashMap<>());
            map = childListeners.get(path);
        }
        CuratorWatcherImpl watcher = map.get(listener);
        if (watcher == null) {
            map.putIfAbsent(listener, new CuratorWatcherImpl(this.client, listener));
            watcher = map.get(listener);
        }
        try {
            return this.client.getChildren().usingWatcher(watcher).forPath(path);
        } catch (Exception e) {
            throw new ZkException(String.format("failed to addChildListener for path:{%s}", path));
        }
    }

    @Override
    public void removeChildListener(String path, ChildListener listener) {
        ConcurrentMap<ChildListener, CuratorWatcherImpl> map = childListeners.get(path);
        if (map != null) {
            CuratorWatcherImpl watcher = map.remove(listener);
            if (watcher != null) {
                watcher.unwatch();
            }
        }
    }
    /*************************** data listener *************************/
    @Override
    public void addDataListener(String path, DataListener listener, Executor executor) {
        ConcurrentMap<DataListener, CuratorWatcherImpl> map = dataListeners.get(path);
        if (map == null) {
            dataListeners.putIfAbsent(path, new ConcurrentHashMap<>());
            map = dataListeners.get(path);
        }
        CuratorWatcherImpl watcher = map.get(listener);
        if (watcher == null) {
            map.putIfAbsent(listener, new CuratorWatcherImpl(listener));
            watcher = map.get(listener);
        }

        try {
            TreeCache treeCache = TreeCache.newBuilder(this.client, path).build();
            treeCacheMap.putIfAbsent(path, treeCache);
            treeCache.start();
            if (executor == null){
                treeCache.getListenable().addListener(watcher);
            } else {
                treeCache.getListenable().addListener(watcher, executor);
            }
        } catch (Exception e) {
            throw new ZkException(String.format("failed to add data listener for path:{%s}.", path));
        }
    }

    @Override
    public void removeDataListener(String path, DataListener listener) {
        ConcurrentMap<DataListener, CuratorWatcherImpl> map = dataListeners.get(path);
        if (map != null) {
            CuratorWatcherImpl watcher = map.remove(listener);
            if (watcher != null) {
                TreeCache treeCache = treeCacheMap.get(path);
                if (treeCache != null) {
                    treeCache.getListenable().removeListener(watcher);
                }
                watcher.dataListener = null;
            }
        }
    }

    private static class CuratorWatcherImpl implements CuratorWatcher, TreeCacheListener {

        private CuratorFramework client;
        private volatile ChildListener childListener;
        private volatile DataListener dataListener;

        public CuratorWatcherImpl(CuratorFramework client, ChildListener childListener) {
            this.client = client;
            this.childListener = childListener;
        }

        public CuratorWatcherImpl(DataListener dataListener) {
            this.dataListener = dataListener;
        }

        public void unwatch() {
            this.childListener = null;
        }

        @Override
        public void process(WatchedEvent watchedEvent) throws Exception {
            if (childListener != null) {
                String path = watchedEvent.getPath();
                if (path == null) {
                    path = "";
                }
                List<String> children;
                if (Objects.equals(Constants.EMPTY_STRING, path)) {
                    children = Collections.emptyList();
                } else {
                    children = this.client.getChildren().forPath(path);
                }
                childListener.childChanged(path, children);
            }
        }

        @Override
        public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
            if (dataListener != null) {
                TreeCacheEvent.Type type = event.getType();
                EventType eventType = null;
                String data = null;
                String path = null;
                switch (type) {
                    case NODE_ADDED:
                        eventType = EventType.NodeCreated;
                        path = event.getData().getPath();
                        byte[] bytes = event.getData().getData();
                        data = (bytes == null ? Constants.EMPTY_STRING : new String(bytes, StandardCharsets.UTF_8));
                        break;
                    case NODE_UPDATED:
                        eventType = EventType.NodeDataChanged;
                        path = event.getData().getPath();
                        byte[] byteData = event.getData().getData();
                        data = (byteData == null ? Constants.EMPTY_STRING : new String(byteData, StandardCharsets.UTF_8));
                        break;
                    case NODE_REMOVED:
                        eventType = EventType.NodeDeleted;
                        path = event.getData().getPath();
                        break;
                    case INITIALIZED:
                        eventType = EventType.INITIALIZED;
                        break;
                    case CONNECTION_LOST:
                        eventType = EventType.CONNECTION_LOST;
                        break;
                    case CONNECTION_SUSPENDED:
                        eventType = EventType.CONNECTION_SUSPENDED;
                        break;
                    case CONNECTION_RECONNECTED:
                        eventType = EventType.CONNECTION_RECONNECTED;
                        break;
                }
                dataListener.dataChanged(path, dataListener, eventType);
            }
        }
    }

}
