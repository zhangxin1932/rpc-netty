package com.zy.rpc.netty.demo01.producer.netty;

import com.zy.rpc.netty.demo01.common.codec.NettyDecoder;
import com.zy.rpc.netty.demo01.common.codec.NettyEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.Objects;

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
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new NettyEncoder())
                                    .addLast(new NettyDecoder(409600, 0, 4, 0, 4))
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

    public void close() {
        if (Objects.nonNull(bossGroup)) {
            bossGroup.shutdownGracefully();
        }
        if (Objects.nonNull(workerGroup)) {
            workerGroup.shutdownGracefully();
        }
    }
}
