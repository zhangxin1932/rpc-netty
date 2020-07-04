package com.zy.rpc.netty.demo01.common.netty;

public interface ProxyFactory<T> {
    T getProxy() throws Exception;

    enum Type {
        JDK,
        CGLIB,
        JAVASSIST,
        ;
    }
}
