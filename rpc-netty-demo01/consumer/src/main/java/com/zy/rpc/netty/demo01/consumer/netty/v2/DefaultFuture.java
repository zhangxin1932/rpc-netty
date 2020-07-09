package com.zy.rpc.netty.demo01.consumer.netty.v2;

import com.zy.rpc.netty.demo01.common.model.Request;
import com.zy.rpc.netty.demo01.common.model.Response;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultFuture extends CompletableFuture<Object> {
    private static final Map<Long, DefaultFuture> FUTURE_MAP = new ConcurrentHashMap<>();
    private static final Map<Long, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();

    private final Long requestId;
    private final Channel channel;
    private final Request request;

    public DefaultFuture(Channel channel, Request request) {
        this.channel = channel;
        this.request = request;
        this.requestId = request.getRequestId();

        FUTURE_MAP.putIfAbsent(requestId, this);
        CHANNEL_MAP.putIfAbsent(requestId, channel);
    }

    public static void received(Channel channel, Response response) {
        try {
            DefaultFuture future = FUTURE_MAP.remove(response.getRequestId());
            if (Objects.nonNull(future)) {
                if (Objects.isNull(response.getE())) {
                    future.complete(response.getResult());
                } else {
                    future.completeExceptionally(response.getE());
                }
            } else {
                throw new RuntimeException("no future in Future_Map");
            }
        } finally {
            CHANNEL_MAP.remove(response.getRequestId());
        }
    }
}
