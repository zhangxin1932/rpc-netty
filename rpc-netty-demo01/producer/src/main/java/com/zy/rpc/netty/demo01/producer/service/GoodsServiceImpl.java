package com.zy.rpc.netty.demo01.producer.service;

import com.zy.rpc.netty.demo01.common.IGoodsService;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl implements IGoodsService {
    @Override
    public String getGoodsName(Integer id) {
        return String.format("goodsName: %s", id);
    }
}
