package com.zy.rpc.netty.demo01.consumer.netty.v2.proxy;

import com.zy.rpc.netty.demo01.common.model.Request;
import com.zy.rpc.netty.demo01.common.utils.ReflectUtils;
import com.zy.rpc.netty.demo01.consumer.netty.v2.NettyClientV2;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;

public class JavassistProxyFactory<T> extends AbstractProxyFactory<T> implements MethodHandler {
    public JavassistProxyFactory(Class<T> interfaceType, NettyClientV2 nettyClientV2, String implCode) {
        super(interfaceType, nettyClientV2, implCode);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getProxy() throws Exception {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(new Class<?>[] {interfaceType});
        Proxy instance = (Proxy) proxyFactory.createClass().newInstance();
        instance.setHandler(this);
        return (T) instance;
    }

    @Override
    public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (DEFAULT_METHODS.contains(methodName)) {
            return methodName;
        }

        Request request = new Request();
        request.setInterfaceName(ReflectUtils.getDesc(interfaceType));
        request.setMethodName(methodName);
        request.setArgs(args);
        request.setArgsTypes(ReflectUtils.getDesc(method.getParameterTypes()));
        request.setReturnType(ReflectUtils.getDesc(method.getReturnType()));
        request.setImplCode(implCode);

        // FIXME 这里是异步转同步的 方式
        return nettyClientV2.send(request).get();
    }
}
