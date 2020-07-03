package com.zy.rpc.netty.demo01.producer.service;

import com.zy.rpc.netty.demo01.common.IGoodsService;
import com.zy.rpc.netty.demo01.common.netty.RpcImplement;
import org.springframework.stereotype.Service;

@Service
@RpcImplement(interfaceType = IGoodsService.class, implCode = "goodsServiceV1Impl")
public class GoodsServiceV1Impl implements IGoodsService {
    @Override
    public String getGoodsName(Integer id) {
        return String.format("goodsServiceV1Impl: goodsName: %s", id);
    }
}
