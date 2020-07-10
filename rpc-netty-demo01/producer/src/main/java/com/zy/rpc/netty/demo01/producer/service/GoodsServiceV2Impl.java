package com.zy.rpc.netty.demo01.producer.service;

import com.zy.rpc.netty.demo01.common.IGoodsService;
import com.zy.rpc.netty.demo01.common.core.RpcImplement;
import org.springframework.stereotype.Service;

@Service
@RpcImplement(interfaceType = IGoodsService.class, implCode = "goodsServiceV2Impl")
public class GoodsServiceV2Impl implements IGoodsService {
    @Override
    public String getGoodsName(Integer id) {
        return String.format("goodsServiceV2Impl: goodsName: %s", id);
    }
}
