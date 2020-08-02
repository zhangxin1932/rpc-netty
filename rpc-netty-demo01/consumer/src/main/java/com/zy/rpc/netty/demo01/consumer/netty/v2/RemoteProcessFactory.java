package com.zy.rpc.netty.demo01.consumer.netty.v2;

import com.zy.rpc.netty.demo01.common.core.RpcServiceKey;
import com.zy.rpc.netty.demo01.common.demo.Constants;
import com.zy.rpc.netty.demo01.common.exception.RpcException;
import com.zy.rpc.netty.demo01.common.zk.ZkUrl;
import com.zy.rpc.netty.demo01.common.zk.registry.ZkRegistry;
import com.zy.rpc.netty.demo01.common.zk.registry.ZkRegistryFactory;
import com.zy.rpc.netty.demo01.consumer.netty.v2.proxy.JDKProxyFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RemoteProcessFactory {
    private static final ZkRegistry REGISTRY;

    static {
        ZkUrl url = ZkUrl.builder().connectionStrings(Constants.CONNECTION_STRINGS).build();
        REGISTRY = ZkRegistryFactory.getZkRegistry(url);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> interfaceType, String implCode) {
        RpcServiceKey serviceKey = RpcServiceKey.getInstance(interfaceType, implCode);
        REGISTRY.subscribe(serviceKey);
        List<String> servers = REGISTRY.getServers(serviceKey);
        if (servers == null || servers.size() == 0) {
            return null;
        }
        List<Object> list = new ArrayList<>(servers.size());
        for (String server : servers) {
            NettyClientV2 client = NettyClientFactory.getClient(server);
            JDKProxyFactory<T> proxy = new JDKProxyFactory<>(interfaceType, client, implCode);
            T t;
            try {
                t = proxy.getProxy();
            } catch (Exception e) {
                throw new RpcException("failed to create client proxy for rpc services.");
            }
            list.add(t);
        }
        return (T) list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

}
