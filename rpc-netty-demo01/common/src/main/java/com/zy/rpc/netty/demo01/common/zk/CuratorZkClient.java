package com.zy.rpc.netty.demo01.common.zk;

import com.zy.rpc.netty.demo01.common.exception.ZkException;
import lombok.Getter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CuratorZkClient {
    private final CuratorFramework client;
    private final Map<String, TreeCache> treeCacheMap = new ConcurrentHashMap<>();
    @Getter
    private final Set<StateListener> stateListeners = new CopyOnWriteArraySet<>();

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

    public void stateChanged(int state) {
        for (StateListener stateListener : getStateListeners()) {
            stateListener.stateChanged(state);
        }
    }

}
