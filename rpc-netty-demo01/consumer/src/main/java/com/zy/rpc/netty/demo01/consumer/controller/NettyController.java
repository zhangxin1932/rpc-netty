package com.zy.rpc.netty.demo01.consumer.controller;

import com.zy.rpc.netty.demo01.common.demo.IGoodsService;
import com.zy.rpc.netty.demo01.common.demo.StuDTO;
import com.zy.rpc.netty.demo01.consumer.netty.v2.RemoteProcessFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class NettyController {

    private final IGoodsService remoteGoodsService = RemoteProcessFactory.getService(IGoodsService.class, "goodsServiceV2Impl");

    @RequestMapping("getGoodsNameV7")
    public List<String> getGoodsNameV7(@RequestBody StuDTO stuDTO) {
        stuDTO.getMap().put(99L, LocalDateTime.now());
        return remoteGoodsService.getGoodsName(stuDTO);
    }

/*

    @Autowired
    private NettyClientV2 nettyClientV2;
    @RequestMapping("getGoodsNameV6")
    public List<String> getGoodsNameV6(@RequestBody StuDTO stuDTO) {
        RemoteServiceFactory remoteServiceFactory = nettyClientV2.getRemoteServiceFactory();
        IGoodsService goodsService = remoteServiceFactory.getService(IGoodsService.class, "goodsServiceV2Impl", ProxyFactory.Type.JAVASSIST);
        stuDTO.getMap().put(99L, LocalDateTime.now());
        return goodsService.getGoodsName(stuDTO);
    }
*/

}
