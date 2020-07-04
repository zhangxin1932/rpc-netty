package com.zy.rpc.netty.demo01.consumer.controller;

import com.zy.rpc.netty.demo01.common.IGoodsService;
import com.zy.rpc.netty.demo01.common.netty.ProxyFactory;
import com.zy.rpc.netty.demo01.common.netty.Request;
import com.zy.rpc.netty.demo01.common.netty.Response;
import com.zy.rpc.netty.demo01.consumer.netty.v1.NettyClientV1;
import com.zy.rpc.netty.demo01.consumer.netty.v2.NettyClientV2;
import com.zy.rpc.netty.demo01.consumer.netty.v2.RemoteServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class NettyController {

    @Autowired
    private NettyClientV1 nettyClientV1;

    @Autowired
    private NettyClientV2 nettyClientV2;

    /**
     * 同步获取结果
     * @param id
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("getGoodsNameV1")
    public Response getGoodsNameV1(Integer id) throws ExecutionException, InterruptedException {
        Request request = new Request();
        request.setInterfaceType(IGoodsService.class);
        request.setMethodName("getGoodsName");
        request.setArgs(new Object[]{id});
        request.setArgsType(new Class<?>[]{Integer.class});
        return nettyClientV1.send(request);
    }

    /**
     * 全链路异步
     * @param id
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("getGoodsNameV2")
    public CompletableFuture<Object> getGoodsNameV2(Integer id) throws ExecutionException, InterruptedException {
        Request request = new Request();
        request.setInterfaceType(IGoodsService.class);
        request.setMethodName("getGoodsName");
        request.setArgs(new Object[]{id});
        request.setArgsType(new Class<?>[]{Integer.class});
        return nettyClientV2.send(request);
    }

    /**
     * 异步转同步
     * @param id
     * @return
     */
    @RequestMapping("getGoodsNameV3")
    public String getGoodsNameV3(Integer id) {
        RemoteServiceFactory remoteServiceFactory = nettyClientV2.getRemoteServiceFactory();
        IGoodsService goodsService = remoteServiceFactory.getService(IGoodsService.class, "goodsServiceV1Impl");
        return goodsService.getGoodsName(id);
    }

    /**
     * 异步转同步
     * @param id
     * @return
     */
    @RequestMapping("getGoodsNameV4")
    public String getGoodsNameV4(Integer id) {
        RemoteServiceFactory remoteServiceFactory = nettyClientV2.getRemoteServiceFactory();
        IGoodsService goodsService = remoteServiceFactory.getService(IGoodsService.class, "goodsServiceV2Impl");
        return goodsService.getGoodsName(id);
    }

    @RequestMapping("getGoodsNameV5")
    public String getGoodsNameV5(Integer id) {
        RemoteServiceFactory remoteServiceFactory = nettyClientV2.getRemoteServiceFactory();
        IGoodsService goodsService = remoteServiceFactory.getService(IGoodsService.class, "goodsServiceV2Impl", ProxyFactory.Type.CGLIB);
        return goodsService.getGoodsName(id);
    }

    @RequestMapping("getGoodsNameV6")
    public String getGoodsNameV6(Integer id) {
        RemoteServiceFactory remoteServiceFactory = nettyClientV2.getRemoteServiceFactory();
        IGoodsService goodsService = remoteServiceFactory.getService(IGoodsService.class, "goodsServiceV2Impl", ProxyFactory.Type.JAVASSIST);
        return goodsService.getGoodsName(id);
    }
}
