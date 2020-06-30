package com.zy.rpc.netty.demo01.consumer.netty;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zy.rpc.netty.demo01.common.netty.Response;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class NettyMsgTools {
    private static Cache<Long, BlockingQueue<Response>> responseMsgCache =
            CacheBuilder.newBuilder()
            .maximumSize(50000)
            .expireAfterWrite(1000, TimeUnit.SECONDS)
            .build();

    public static void initReceiveMsg(Long requestId) {
        responseMsgCache.put(requestId, new LinkedBlockingQueue<>(1));
    }

    public static void setResponse(String response) {
        Response result = JSON.parseObject(response, Response.class);
        BlockingQueue<Response> queue = responseMsgCache.getIfPresent(result.getRequestId());
        if (Objects.nonNull(queue)) {
            queue.add(result);
        }
    }

    public static Response getResponse(Long requestId) {
        try {
            BlockingQueue<Response> queue = responseMsgCache.getIfPresent(requestId);
            Response msg = Objects.requireNonNull(queue).poll(3000, TimeUnit.MILLISECONDS);
            responseMsgCache.invalidate(requestId);
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
