package com.zy.rpc.netty.demo01.producer.netty;

import com.zy.rpc.netty.demo01.common.demo.Constants;
import com.zy.rpc.netty.demo01.common.exception.RpcException;
import com.zy.rpc.netty.demo01.common.core.RpcImplement;
import com.zy.rpc.netty.demo01.common.core.RpcServiceKey;
import com.zy.rpc.netty.demo01.common.utils.IPTools;
import com.zy.rpc.netty.demo01.common.zk.ZkUrl;
import com.zy.rpc.netty.demo01.common.zk.registry.ZkRegistry;
import com.zy.rpc.netty.demo01.common.zk.registry.ZkRegistryFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NettySpringBeanFactory implements ApplicationContextAware, ApplicationListener<ApplicationContextEvent>, DisposableBean {

    private static final Map<RpcServiceKey, Object> NETTY_SPRING_BEAN_MAP = new ConcurrentHashMap<>();
    private ApplicationContext applicationContext;
    private NettyServer server;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        if (event.getApplicationContext() != this.applicationContext) {
            // skip, 这里可以打印日志记录信息
        } else {
            String[] beanDefinitionNames = this.applicationContext.getBeanDefinitionNames();
            if (beanDefinitionNames.length <= 0) {
                throw new RpcException("maybe there is an error in NettySpringBeanFactory");
            }
            for (String beanDefinitionName : beanDefinitionNames) {
                Object bean = this.applicationContext.getBean(beanDefinitionName);
                RpcImplement rpcImplement = bean.getClass().getAnnotation(RpcImplement.class);
                if (Objects.isNull(rpcImplement)) {
                    continue;
                }
                RpcServiceKey rpcServiceKey = RpcServiceKey.getInstance(rpcImplement.interfaceType(), rpcImplement.implCode());
                if (NETTY_SPRING_BEAN_MAP.containsKey(rpcServiceKey)) {
                    throw new RpcException("interfaceType and implCode are both equals." + rpcServiceKey);
                }
                NETTY_SPRING_BEAN_MAP.put(rpcServiceKey, bean);
            }
            // TODO 可以在这里启动 NettyServer
            if (!NETTY_SPRING_BEAN_MAP.isEmpty()) {
                String localIP = IPTools.getLocalIP();
                Integer port = Integer.getInteger("rpc.server.port", Constants.PORT);
                // 启动 server
                this.server = NettyServerFactory.getServer(localIP, port);

                // 连接 zk, 并将 provider 注册到 zk 中
                ZkUrl zkUrl = ZkUrl.builder().connectionStrings(Constants.CONNECTION_STRINGS).build();
                ZkRegistry registry = ZkRegistryFactory.getZkRegistry(zkUrl);
                String address = localIP + Constants.SEPARATOR_03 + port;
                NETTY_SPRING_BEAN_MAP.forEach((k,v) -> registry.register(k, address));
            }
        }
    }

    public static Object getBean(Class<?> interfaceType, String implCode) {
        RpcServiceKey rpcServiceKey = RpcServiceKey.getInstance(interfaceType, implCode);
        return NETTY_SPRING_BEAN_MAP.get(rpcServiceKey);
    }

    @Override
    public void destroy() throws Exception {
        // TODO 可以在这里销毁 NettyServer, 断开各种连接
        if (Objects.nonNull(server)) {
            server.close();
        }
        NETTY_SPRING_BEAN_MAP.clear();
    }
}
