package com.zy.rpc.netty.demo01.consumer.controller;

import com.zy.rpc.netty.demo01.common.demo.IGoodsService;
import com.zy.rpc.netty.demo01.common.core.ProxyFactory;
import com.zy.rpc.netty.demo01.common.demo.StuDTO;
import com.zy.rpc.netty.demo01.common.model.Request;
import com.zy.rpc.netty.demo01.common.model.Response;
import com.zy.rpc.netty.demo01.consumer.netty.v1.NettyClientV1;
import com.zy.rpc.netty.demo01.consumer.netty.v2.NettyClientV2;
import com.zy.rpc.netty.demo01.consumer.netty.v2.RemoteServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
     * @param stuDTO
     * @return
     */
    @RequestMapping("getGoodsNameV3")
    public List<String> getGoodsNameV3(@RequestBody StuDTO stuDTO) {
        RemoteServiceFactory remoteServiceFactory = nettyClientV2.getRemoteServiceFactory();
        IGoodsService goodsService = remoteServiceFactory.getService(IGoodsService.class, "goodsServiceV1Impl");
        return goodsService.getGoodsName(stuDTO);
    }

    /**
     * 异步转同步
     * @param stuDTO
     * @return
     */
    @RequestMapping("getGoodsNameV4")
    public List<String> getGoodsNameV4(@RequestBody StuDTO stuDTO) {
        RemoteServiceFactory remoteServiceFactory = nettyClientV2.getRemoteServiceFactory();
        IGoodsService goodsService = remoteServiceFactory.getService(IGoodsService.class, "goodsServiceV2Impl");
        return goodsService.getGoodsName(stuDTO);
    }

    @RequestMapping("getGoodsNameV5")
    public List<String> getGoodsNameV5(@RequestBody StuDTO stuDTO) {
        RemoteServiceFactory remoteServiceFactory = nettyClientV2.getRemoteServiceFactory();
        IGoodsService goodsService = remoteServiceFactory.getService(IGoodsService.class, "goodsServiceV2Impl", ProxyFactory.Type.CGLIB);
        return goodsService.getGoodsName(stuDTO);
    }

    @RequestMapping("getGoodsNameV6")
    public List<String> getGoodsNameV6(@RequestBody StuDTO stuDTO) {
        RemoteServiceFactory remoteServiceFactory = nettyClientV2.getRemoteServiceFactory();
        IGoodsService goodsService = remoteServiceFactory.getService(IGoodsService.class, "goodsServiceV2Impl", ProxyFactory.Type.JAVASSIST);
        return goodsService.getGoodsName(stuDTO);
    }
}
