package com.zy.rpc.netty.demo01.consumer.controller;

import com.zy.rpc.netty.demo01.common.demo.IGoodsService;
import com.zy.rpc.netty.demo01.common.core.ProxyFactory;
import com.zy.rpc.netty.demo01.common.demo.StuDTO;
import com.zy.rpc.netty.demo01.consumer.netty.v2.NettyClientV2;
import com.zy.rpc.netty.demo01.consumer.netty.v2.RemoteServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class NettyController {

    @Autowired
    private NettyClientV2 nettyClientV2;

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
        stuDTO.getMap().put(99L, LocalDateTime.now());
        return goodsService.getGoodsName(stuDTO);
    }
}
