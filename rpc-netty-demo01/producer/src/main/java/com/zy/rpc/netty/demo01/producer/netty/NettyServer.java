package com.zy.rpc.netty.demo01.producer.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultThreadFactory;

public class NettyServer {

    private final String host;
    private final int port;
    private final ServerBootstrap server;
    private final NioEventLoopGroup bossGroup;
    private final NioEventLoopGroup workerGroup;

    public NettyServer(String host, int port) {
        this.host = host;
        this.port = port;
        this.server = new ServerBootstrap();
        this.bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("server-boss", true));
        this.workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(), new DefaultThreadFactory("server-worker", true));
        this.init();
    }

    private void init() {
        try {
            this.server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(409600, 0, 4, 0, 4))
                                    .addLast(new StringDecoder())
                                    .addLast(new LengthFieldPrepender(4))
                                    .addLast(new StringEncoder())
                                    .addLast(new ServerHandler());
                        }
                    });

            this.server.bind(host, port).sync();
            System.out.println("Netty 服务端启动成功 ------------");
        } catch (InterruptedException e) {
            System.out.println("Netty 服务端启动失败 ------------");
            e.printStackTrace();
        }
    }
}
