package com.zy.rpc.netty.demo01.producer.config;

import com.zy.rpc.netty.demo01.common.netty.RpcException;
import com.zy.rpc.netty.demo01.common.netty.RpcImplement;
import com.zy.rpc.netty.demo01.common.netty.RpcServiceKey;
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
                    throw new RpcException("interfaceType and implCode are both equals.");
                }
                NETTY_SPRING_BEAN_MAP.put(rpcServiceKey, bean);
            }
            // TODO 可以在这里启动 NettyServer
        }
    }

    public static Object getBean(Class<?> interfaceType, String implCode) {
        RpcServiceKey rpcServiceKey = RpcServiceKey.getInstance(interfaceType, implCode);
        return NETTY_SPRING_BEAN_MAP.get(rpcServiceKey);
    }

    @Override
    public void destroy() throws Exception {
        // TODO 可以在这里销毁 NettyServer, 断开各种连接
    }
}
