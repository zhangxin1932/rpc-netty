package com.zy.rpc.netty.demo01.consumer.netty.v2;

import com.zy.rpc.netty.demo01.common.netty.ProxyFactory;
import com.zy.rpc.netty.demo01.common.exception.RpcException;
import com.zy.rpc.netty.demo01.common.netty.RpcServiceKey;
import com.zy.rpc.netty.demo01.consumer.netty.v2.proxy.CglibProxyFactory;
import com.zy.rpc.netty.demo01.consumer.netty.v2.proxy.JDKProxyFactory;
import com.zy.rpc.netty.demo01.consumer.netty.v2.proxy.JavassistProxyFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RemoteServiceFactory {
    private static final Map<RpcServiceKey, Object> JDK_PROXY_MAP = new ConcurrentHashMap<>();
    private static final Map<RpcServiceKey, Object> CGLIB_PROXY_MAP = new ConcurrentHashMap<>();
    private static final Map<RpcServiceKey, Object> JAVASSIST_PROXY_MAP = new ConcurrentHashMap<>();
    private final NettyClientV2 nettyClientV2;

    public RemoteServiceFactory(NettyClientV2 nettyClientV2) {
        this.nettyClientV2 = nettyClientV2;
    }

    public <T> T getService(Class<T> interfaceType, String implCode) {
        return getService(interfaceType, implCode, ProxyFactory.Type.JDK);
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> interfaceType, String implCode, ProxyFactory.Type type) {
        if (Objects.isNull(type)) {
            type = ProxyFactory.Type.JDK;
        }

        RpcServiceKey rpcServiceKey = RpcServiceKey.getInstance(interfaceType, implCode);
        ProxyFactory<T> proxyFactory;
        Object obj;

        try {
            switch (type) {
                case JDK:
                    obj = JDK_PROXY_MAP.get(rpcServiceKey);
                    if (Objects.isNull(obj)) {
                        proxyFactory = new JDKProxyFactory<>(interfaceType, nettyClientV2, implCode);
                        obj = proxyFactory.getProxy();
                        JDK_PROXY_MAP.putIfAbsent(rpcServiceKey, obj);
                    }
                    break;
                case CGLIB:
                    obj = CGLIB_PROXY_MAP.get(rpcServiceKey);
                    if (Objects.isNull(obj)) {
                        proxyFactory = new CglibProxyFactory<>(interfaceType, nettyClientV2, implCode);
                        obj = proxyFactory.getProxy();
                        CGLIB_PROXY_MAP.putIfAbsent(rpcServiceKey, obj);
                    }
                    break;
                case JAVASSIST:
                    obj = JAVASSIST_PROXY_MAP.get(rpcServiceKey);
                    if (Objects.isNull(obj)) {
                        proxyFactory = new JavassistProxyFactory<>(interfaceType, nettyClientV2, implCode);
                        obj = proxyFactory.getProxy();
                        JAVASSIST_PROXY_MAP.putIfAbsent(rpcServiceKey, obj);
                    }
                    break;
                default:
                    // the code will never be there.
                    throw new RpcException("cannot find proxy type");
            }
        } catch (Exception e) {
            throw new RpcException(e);
        }
        return (T) obj;
    }

}
