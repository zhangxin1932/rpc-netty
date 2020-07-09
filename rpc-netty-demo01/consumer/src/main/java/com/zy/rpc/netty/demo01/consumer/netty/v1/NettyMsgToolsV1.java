package com.zy.rpc.netty.demo01.consumer.netty.v1;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zy.rpc.netty.demo01.common.model.Response;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class NettyMsgToolsV1 {
    private static final Cache<Long, BlockingQueue<Response>> RESPONSE_MSG_CACHE =
            CacheBuilder.newBuilder()
            .maximumSize(100000)
            .expireAfterWrite(50, TimeUnit.SECONDS)
            .build();

    public static void initReceiveMsg(Long requestId) {
        RESPONSE_MSG_CACHE.put(requestId, new LinkedBlockingQueue<>(1));
    }

    public static void setResponse(String response) {
        Response result = JSON.parseObject(response, Response.class);
        BlockingQueue<Response> queue = RESPONSE_MSG_CACHE.getIfPresent(result.getRequestId());
        if (Objects.nonNull(queue)) {
            queue.add(result);
        }
    }

    public static Response getResponse(Long requestId) {
        try {
            BlockingQueue<Response> queue = RESPONSE_MSG_CACHE.getIfPresent(requestId);
            Response msg = Objects.requireNonNull(queue).poll(3000, TimeUnit.MILLISECONDS);
            RESPONSE_MSG_CACHE.invalidate(requestId);
            return msg;
        } catch (Throwable e) {
            Response response = new Response();
            response.setRequestId(requestId);
            response.setE(e);
            e.printStackTrace();
            return response;
        }
    }

}
