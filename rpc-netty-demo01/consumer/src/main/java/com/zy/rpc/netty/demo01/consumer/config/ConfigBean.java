package com.zy.rpc.netty.demo01.consumer.config;

import com.zy.rpc.netty.demo01.common.Constants;
import com.zy.rpc.netty.demo01.consumer.netty.NettyClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBean {
    @Bean
    public NettyClient nettyClient() {
        return new NettyClient(Constants.HOST, Constants.PORT);
    }
}
