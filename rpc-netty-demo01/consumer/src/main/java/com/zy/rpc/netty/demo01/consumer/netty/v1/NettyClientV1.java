package com.zy.rpc.netty.demo01.consumer.netty.v1;

import com.alibaba.fastjson.JSON;
import com.zy.rpc.netty.demo01.common.netty.Request;
import com.zy.rpc.netty.demo01.common.netty.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.LongAdder;

public class NettyClientV1 {

    private ChannelPoolMap<InetSocketAddress, SimpleChannelPool> map;
    private static LongAdder REQUEST_ID = new LongAdder();

    private final String host;
    private final int port;
    private final Bootstrap client;
    private final NioEventLoopGroup nioEventLoopGroup;
    private final int maxConnections;

    public NettyClientV1(String host, int port) {
        this(host, port, 1);
    }

    public NettyClientV1(String host, int port, int maxConnections) {
        this.host = host;
        this.port = port;
        this.client = new Bootstrap();
        this.nioEventLoopGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(), new DefaultThreadFactory("client-worker", true));
        this.maxConnections = maxConnections;

        init();
    }

    private void init() {
        this.client.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true);

        this.map = new AbstractChannelPoolMap<InetSocketAddress, SimpleChannelPool>() {
            @Override
            protected SimpleChannelPool newPool(InetSocketAddress inetSocketAddress) {
                return new FixedChannelPool(client.remoteAddress(inetSocketAddress), new AbstractChannelPoolHandler() {
                    @Override
                    public void channelCreated(Channel channel) throws Exception {
                        channel.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(409600, 0, 4, 0, 4))
                                .addLast(new StringDecoder())
                                .addLast(new LengthFieldPrepender(4))
                                .addLast(new StringEncoder())
                                .addLast(new ClientHandlerV1());
                    }
                }, maxConnections);
            }
        };
    }

    public Response send(Request request) {
        REQUEST_ID.increment();
        long requestId = REQUEST_ID.longValue();
        request.setRequestId(requestId);

        SimpleChannelPool simpleChannelPool = map.get(new InetSocketAddress(host, port));
        Future<Channel> future = simpleChannelPool.acquire();
        NettyMsgToolsV1.initReceiveMsg(requestId);
        future.addListener((FutureListener<Channel>) channelFuture -> {
            Channel channel = channelFuture.getNow();
            channel.writeAndFlush(JSON.toJSONString(request));
            simpleChannelPool.release(channel);
        });

        return NettyMsgToolsV1.getResponse(requestId);
    }
}
