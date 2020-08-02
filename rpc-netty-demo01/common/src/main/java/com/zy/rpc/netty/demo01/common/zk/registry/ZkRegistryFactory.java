package com.zy.rpc.netty.demo01.common.zk.registry;

import com.zy.rpc.netty.demo01.common.demo.Constants;
import com.zy.rpc.netty.demo01.common.zk.CuratorZkClientFactory;
import com.zy.rpc.netty.demo01.common.zk.ZkUrl;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ZkRegistryFactory {
    private static final Map<String, ZkRegistry> REGISTRY_MAP = new ConcurrentHashMap<>();

    public static ZkRegistry getZkRegistry(ZkUrl url) {
        String key = Arrays.stream(url.getConnectionStrings()).sorted().collect(Collectors.joining(Constants.SEPARATOR_04));
        ZkRegistry registry = REGISTRY_MAP.get(key);
        if (registry == null) {
            registry = new ZkRegistry(CuratorZkClientFactory.getInstance(), url);
            REGISTRY_MAP.put(key, registry);
        }
        return registry;
    }

}
