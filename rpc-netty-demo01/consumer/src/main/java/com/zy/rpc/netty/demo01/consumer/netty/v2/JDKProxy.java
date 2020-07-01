package com.zy.rpc.netty.demo01.consumer.netty.v2;

import com.zy.rpc.netty.demo01.common.netty.Request;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy<T> implements InvocationHandler {

    private final NettyClientV2 nettyClientV2;

    private final Class<T> interfaceType;

    public JDKProxy(Class<T> interfaceType, NettyClientV2 nettyClientV2) {
        this.interfaceType = interfaceType;
        this.nettyClientV2 = nettyClientV2;
    }

    @SuppressWarnings("unchecked")
    public T getProxy() {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{interfaceType},
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setClassType(interfaceType);
        request.setMethodName(method.getName());
        request.setArgs(args);
        request.setArgsType(method.getParameterTypes());

        // FIXME 这里是异步转同步的 方式
        return nettyClientV2.send(request).get();
    }

}
