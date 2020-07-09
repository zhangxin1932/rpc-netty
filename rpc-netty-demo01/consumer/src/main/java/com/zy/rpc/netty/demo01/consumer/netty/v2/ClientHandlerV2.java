package com.zy.rpc.netty.demo01.consumer.netty.v2;

import com.alibaba.fastjson.JSON;
import com.zy.rpc.netty.demo01.common.model.Response;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ExecutorService;

@ChannelHandler.Sharable
public class ClientHandlerV2 extends SimpleChannelInboundHandler<String> {

    private static final ExecutorService EXECUTOR_SERVICE = new DefaultEventExecutorGroup(
            Runtime.getRuntime().availableProcessors() * 2,
            new DefaultThreadFactory("NettyClientHandler", true)
    );

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        EXECUTOR_SERVICE.submit(() -> {
            DefaultFuture.received(channelHandlerContext.channel(), JSON.parseObject(msg, Response.class));
        });
    }
}
