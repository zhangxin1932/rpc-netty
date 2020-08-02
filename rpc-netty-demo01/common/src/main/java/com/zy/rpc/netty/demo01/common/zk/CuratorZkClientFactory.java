package com.zy.rpc.netty.demo01.common.zk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CuratorZkClientFactory {

    private final Map<String, IZkClient> zkClientMap = new ConcurrentHashMap<>();
    private static final CuratorZkClientFactory FACTORY = new CuratorZkClientFactory();
    public static CuratorZkClientFactory getInstance() {
        return FACTORY;
    }

    public IZkClient create(ZkUrl url) {
        IZkClient client = null;
        String[] connectionStrings = url.getConnectionStrings();
        client = getZkClientFromCache(connectionStrings);
        if (client != null && client.isConnected()) {
            return client;
        }

        synchronized (zkClientMap) {
            client = getZkClientFromCache(connectionStrings);
            if (client != null && client.isConnected()) {
                return client;
            }
            client = new CuratorZkClient(url);
            for (String connectionString : connectionStrings) {
                zkClientMap.putIfAbsent(connectionString, client);
            }
            return client;
        }
    }

    public IZkClient getZkClientFromCache(String[] connectionStrings) {
        IZkClient client = null;
        for (String connectionString : connectionStrings) {
            client = zkClientMap.get(connectionString);
            if (client != null && client.isConnected()) {
                break;
            }
        }
        if (client != null && client.isConnected()) {
            for (String connectionString : connectionStrings) {
                zkClientMap.putIfAbsent(connectionString, client);
            }
        }
        return client;
    }
}
