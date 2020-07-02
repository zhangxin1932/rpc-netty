package com.zy.rpc.netty.demo01.consumer.netty.v2;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RemoteServiceFactory {
    private final Map<Class<?>, Object> map = new ConcurrentHashMap<>();
    private final NettyClientV2 nettyClientV2;

    public RemoteServiceFactory(NettyClientV2 nettyClientV2) {
        this.nettyClientV2 = nettyClientV2;
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> interfaceType) {
        Object obj = map.get(interfaceType);
        if (Objects.nonNull(obj)) {
            return (T) obj;
        }
        JDKProxy<T> jdkProxy = new JDKProxy<>(interfaceType, nettyClientV2);
        T t = jdkProxy.getProxy();
        map.putIfAbsent(interfaceType, t);
        return t;
    }
}
