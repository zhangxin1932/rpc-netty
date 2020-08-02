package com.zy.rpc.netty.demo01.common.zk.registry;

import com.zy.rpc.netty.demo01.common.core.RpcServiceKey;
import com.zy.rpc.netty.demo01.common.demo.Constants;
import com.zy.rpc.netty.demo01.common.zk.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ZkRegistry {
    private final IZkClient client;
    private final Map<String, List<String>> childPaths = new ConcurrentHashMap<>();

    public ZkRegistry(CuratorZkClientFactory factory, ZkUrl url) {
        this.client = factory.create(url);
        this.client.addStateListener(new StateListener() {
            @Override
            public void stateChanged(int connected) {
                System.out.println("----------------------");
                System.out.println(connected);
                System.out.println("----------------------");
            }
        });
        this.init();
    }

    private void init() {
        if (!this.client.checkExists(Constants.SEPARATOR_02 + Constants.ROOT_PATH)) {
            this.client.create(Constants.SEPARATOR_02 + Constants.ROOT_PATH, false, false);
        }
        String servicesPath = String.format("/%s/%s", Constants.ROOT_PATH, Constants.SERVICES_PATH);
        if (!this.client.checkExists(servicesPath)) {
            this.client.create(servicesPath, false, false);
        }
    }

    public void register(RpcServiceKey serviceKey, String address) {
        String path1 = String.format("/%s/%s/%s", Constants.ROOT_PATH, Constants.SERVICES_PATH, serviceKey.getType().getName());
        if (!this.client.checkExists(path1)) {
            this.client.create(path1, false, false);
        }

        String path2 = String.format("%s/%s", path1, Constants.IMPLS);
        if (!this.client.checkExists(path2)) {
            this.client.create(path2, false, false);
        }

        String path3 = String.format("%s/%s", path2, serviceKey.getImplCode());
        if (!this.client.checkExists(path3)) {
            this.client.create(path3, false, false);
        }

        String path4 = String.format("%s/%s", path3, address);
        if (!this.client.checkExists(path4)) {
            this.client.create(path4, true, false);
        }
    }

    public void unregister() {

    }

    public void subscribe(RpcServiceKey serviceKey) {
        String path = String.format("/%s/%s/%s/%s/%s", Constants.ROOT_PATH, Constants.SERVICES_PATH, serviceKey.getType().getName(), Constants.IMPLS, serviceKey.getImplCode());
        this.client.addChildListener(path, new ChildListener() {
            @Override
            public void childChanged(String path, List<String> children) {
                childPaths.put(path, children);
            }
        });
        childPaths.put(path, this.client.getChildren(path));
    }

    public List<String> getServers(RpcServiceKey serviceKey) {
        String path = String.format("/%s/%s/%s/%s/%s", Constants.ROOT_PATH, Constants.SERVICES_PATH, serviceKey.getType().getName(), Constants.IMPLS, serviceKey.getImplCode());
        return childPaths.get(path);
    }

    public void close() {
        this.client.close();
    }

    public boolean isConnected() {
        return this.client.isConnected();
    }
}
