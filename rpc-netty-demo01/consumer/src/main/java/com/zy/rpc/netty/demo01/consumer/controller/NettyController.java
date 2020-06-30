package com.zy.rpc.netty.demo01.consumer.controller;

import com.zy.rpc.netty.demo01.common.IGoodsService;
import com.zy.rpc.netty.demo01.common.netty.Request;
import com.zy.rpc.netty.demo01.common.netty.Response;
import com.zy.rpc.netty.demo01.consumer.netty.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NettyController {

    @Autowired
    private NettyClient nettyClient;

    @RequestMapping("getGoodsName")
    public Response getGoodsName(Integer id) {
        Request request = new Request();
        request.setClassType(IGoodsService.class);
        request.setMethodName("getGoodsName");
        request.setArgs(new Object[]{id});
        request.setArgsType(new Class<?>[]{Integer.class});
        return nettyClient.send(request);
    }
}
