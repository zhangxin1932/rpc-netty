package com.zy.rpc.netty.demo01.producer.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> classType) {
        return applicationContext.getBean(classType);
    }

    public static <T> T getBean(String beanName, Class<T> classType) {
        return applicationContext.getBean(beanName, classType);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }
}
