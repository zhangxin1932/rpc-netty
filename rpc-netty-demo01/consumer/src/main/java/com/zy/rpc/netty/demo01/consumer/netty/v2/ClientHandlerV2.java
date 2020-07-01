package com.zy.rpc.netty.demo01.consumer.netty.v2;

import com.alibaba.fastjson.JSON;
import com.zy.rpc.netty.demo01.common.netty.Response;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
@ChannelHandler.Sharable
public class ClientHandlerV2 extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        DefaultFuture.received(channelHandlerContext.channel(), JSON.parseObject(msg, Response.class));
    }
}
