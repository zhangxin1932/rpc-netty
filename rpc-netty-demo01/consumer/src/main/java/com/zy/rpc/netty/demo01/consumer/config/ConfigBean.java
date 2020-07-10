package com.zy.rpc.netty.demo01.consumer.config;

import com.zy.rpc.netty.demo01.common.demo.Constants;
import com.zy.rpc.netty.demo01.consumer.netty.v1.NettyClientV1;
import com.zy.rpc.netty.demo01.consumer.netty.v2.NettyClientV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBean {
    @Bean
    public NettyClientV1 nettyClientV1() {
        return new NettyClientV1(Constants.HOST, Constants.PORT);
    }

    @Bean
    public NettyClientV2 nettyClientV2() {
        return new NettyClientV2(Constants.HOST, Constants.PORT);
    }

}
