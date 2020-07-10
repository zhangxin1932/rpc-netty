package com.zy.rpc.netty.demo01.common.core;

import lombok.Data;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class RpcServiceKey {
    private final Class<?> type;
    private final String implCode;
    private static final String SEPARATOR = "-";
    private static final Map<String, RpcServiceKey> RPC_SERVICE_KEY_MAP = new ConcurrentHashMap<>();

    private RpcServiceKey(Class<?> type, String implCode) {
        this.type = type;
        this.implCode = implCode;
    }

    public static RpcServiceKey getInstance(final Class<?> type, final String implCode) {
        String key = String.format("%s%s%s", type.getName(), SEPARATOR, implCode);
        RpcServiceKey rpcServiceKey = RPC_SERVICE_KEY_MAP.get(key);
        if (Objects.isNull(rpcServiceKey)) {
            rpcServiceKey = new RpcServiceKey(type, implCode);
            RPC_SERVICE_KEY_MAP.putIfAbsent(key, rpcServiceKey);
        }
        return rpcServiceKey;
    }
}
