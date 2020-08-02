package com.zy.rpc.netty.demo01.consumer.netty.v2;

import com.zy.rpc.netty.demo01.common.demo.Constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettyClientFactory {
    private static final Map<String, NettyClientV2> MAP = new ConcurrentHashMap<>();

    public static NettyClientV2 getClient(String address) {
        NettyClientV2 client = MAP.get(address);
        if (client == null){
            String[] split = address.split(Constants.SEPARATOR_03);
            client = new NettyClientV2(split[0], Integer.parseInt(split[1]));
            MAP.putIfAbsent(address, client);
        }
        return client;
    }
}
