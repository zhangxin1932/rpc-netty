package com.zy.rpc.netty.demo01.consumer.netty.v2;

import com.alibaba.fastjson.JSON;
import com.zy.rpc.netty.demo01.common.model.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultThreadFactory;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.LongAdder;

public class NettyClientV2 {

    private final String host;
    private final int port;
    private final Bootstrap client;
    private final NioEventLoopGroup nioEventLoopGroup;

    private volatile Channel channel;
    private static final LongAdder REQUEST_ID = new LongAdder();

    private final RemoteServiceFactory remoteServiceFactory;

    public RemoteServiceFactory getRemoteServiceFactory() {
        return remoteServiceFactory;
    }

    public NettyClientV2(String host, int port) {
        this.host = host;
        this.port = port;
        this.client = new Bootstrap();
        this.nioEventLoopGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(), new DefaultThreadFactory("clientV2-worker", true));

        this.init();

        this.remoteServiceFactory = new RemoteServiceFactory(this);
    }

    private void init() {
        this.client.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(409600, 0, 4, 0, 4))
                                .addLast(new StringDecoder())
                                .addLast(new LengthFieldPrepender(4))
                                .addLast(new StringEncoder())
                                .addLast(new ClientHandlerV2());
                    }
                });

        this.connect();
    }

    private void connect() {
        synchronized (client) {
            try {
                ChannelFuture future = this.client.connect(host, port).sync();
                future.addListener((ChannelFutureListener) channelFuture -> {
                    if (!channelFuture.isSuccess()) {
                        channelFuture.channel().pipeline().fireChannelInactive();
                    } else {
                        Channel oldChannel = NettyClientV2.this.channel;
                        if (Objects.nonNull(oldChannel)) {
                            oldChannel.close();
                        }
                        NettyClientV2.this.channel = channelFuture.channel();
                    }
                });
            } catch (Throwable e) {
                System.out.println("Netty 客户端连接失败");
                e.printStackTrace();
            }
        }
    }

    public Channel getChannel() {
        Channel channel = NettyClientV2.this.channel;
        if (Objects.isNull(channel) || !channel.isActive()) {
            return null;
        }
        return channel;
    }

    public CompletableFuture<Object> send(Request request) {
        REQUEST_ID.increment();
        long requestId = REQUEST_ID.longValue();
        request.setRequestId(requestId);
        Channel channel = getChannel();
        DefaultFuture future = new DefaultFuture(channel, request);
        channel.writeAndFlush(JSON.toJSONString(request));
        return future;
    }

}
