package com.zy.rpc.netty.demo01.consumer.netty.v2.proxy;

import com.zy.rpc.netty.demo01.common.model.Request;
import com.zy.rpc.netty.demo01.common.utils.ReflectUtils;
import com.zy.rpc.netty.demo01.consumer.netty.v2.NettyClientV2;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxyFactory<T> extends AbstractProxyFactory<T> implements InvocationHandler {

    public JDKProxyFactory(Class<T> interfaceType, NettyClientV2 nettyClientV2, String implCode) {
        super(interfaceType, nettyClientV2, implCode);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getProxy() throws Exception {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{interfaceType},
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (DEFAULT_METHODS.contains(methodName)) {
            return methodName;
        }

        Request request = new Request();
        request.setInterfaceName(ReflectUtils.getDesc(interfaceType));
        request.setMethodName(methodName);
        request.setArgs(args);
        request.setArgsTypes(ReflectUtils.getDesc(method.getParameterTypes()));
        request.setImplCode(implCode);

        // FIXME 这里是异步转同步的 方式
        return nettyClientV2.send(request).get();
    }

}
