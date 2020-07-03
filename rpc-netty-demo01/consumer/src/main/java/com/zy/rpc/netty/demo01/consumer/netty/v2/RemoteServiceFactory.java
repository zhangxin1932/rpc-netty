package com.zy.rpc.netty.demo01.consumer.netty.v2;

import com.zy.rpc.netty.demo01.common.netty.RpcServiceKey;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RemoteServiceFactory {
    private final Map<RpcServiceKey, Object> map = new ConcurrentHashMap<>();
    private final NettyClientV2 nettyClientV2;

    public RemoteServiceFactory(NettyClientV2 nettyClientV2) {
        this.nettyClientV2 = nettyClientV2;
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> interfaceType, String implCode) {
        RpcServiceKey rpcServiceKey = RpcServiceKey.getInstance(interfaceType, implCode);
        Object obj = map.get(rpcServiceKey);
        if (Objects.nonNull(obj)) {
            return (T) obj;
        }
        JDKProxy<T> jdkProxy = new JDKProxy<>(interfaceType, nettyClientV2, implCode);
        T t = jdkProxy.getProxy();
        map.putIfAbsent(rpcServiceKey, t);
        return t;
    }
}
