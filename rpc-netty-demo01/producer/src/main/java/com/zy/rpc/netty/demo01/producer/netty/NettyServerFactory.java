package com.zy.rpc.netty.demo01.producer.netty;

import com.zy.rpc.netty.demo01.common.demo.Constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettyServerFactory {
    private static final Map<String, NettyServer> MAP = new ConcurrentHashMap<>();

    public static NettyServer getServer(String ip, int port) {
        String address = ip + Constants.SEPARATOR_03 + port;
        NettyServer nettyServer = MAP.get(address);
        if (nettyServer == null) {
            nettyServer = new NettyServer(ip, port);
            MAP.putIfAbsent(address, nettyServer);
        }
        return nettyServer;
    }
}
