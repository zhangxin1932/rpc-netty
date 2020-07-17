package com.zy.rpc.netty.demo01.consumer.netty.v2.proxy;

import com.zy.rpc.netty.demo01.common.model.Request;
import com.zy.rpc.netty.demo01.common.utils.ReflectUtils;
import com.zy.rpc.netty.demo01.consumer.netty.v2.NettyClientV2;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyFactory<T> extends AbstractProxyFactory<T> implements MethodInterceptor {
    public CglibProxyFactory(Class<T> interfaceType, NettyClientV2 nettyClientV2, String implCode) {
        super(interfaceType, nettyClientV2, implCode);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getProxy() throws Exception {
        return (T) Enhancer.create(interfaceType, this);
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
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
