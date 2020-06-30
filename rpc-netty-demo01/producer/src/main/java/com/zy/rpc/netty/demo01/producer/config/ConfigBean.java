package com.zy.rpc.netty.demo01.producer.config;

import com.zy.rpc.netty.demo01.common.Constants;
import com.zy.rpc.netty.demo01.producer.netty.NettyServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBean {
    @Bean
    public NettyServer nettyServer() {
        return new NettyServer(Constants.HOST, Constants.PORT);
    }
}
