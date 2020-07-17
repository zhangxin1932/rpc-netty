package com.zy.rpc.netty.demo01.consumer.netty.v2.proxy;

import com.zy.rpc.netty.demo01.common.core.ProxyFactory;
import com.zy.rpc.netty.demo01.consumer.netty.v2.NettyClientV2;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProxyFactory<T> implements ProxyFactory<T> {
    protected static List<String> DEFAULT_METHODS;
    static {
        DEFAULT_METHODS = new ArrayList<>(3);
        DEFAULT_METHODS.add("toString");
        DEFAULT_METHODS.add("hashCode");
        DEFAULT_METHODS.add("equals");
    }
    protected final NettyClientV2 nettyClientV2;
    protected final Class<T> interfaceType;
    protected final String implCode;

    protected AbstractProxyFactory(Class<T> interfaceType, NettyClientV2 nettyClientV2, String implCode) {
        this.interfaceType = interfaceType;
        this.nettyClientV2 = nettyClientV2;
        this.implCode = implCode;
    }

}
