package com.zy.rpc.netty.demo01.common.core;

public interface ProxyFactory<T> {
    T getProxy() throws Exception;

    enum Type {
        JDK,
        CGLIB,
        JAVASSIST,
        ;
    }
}
