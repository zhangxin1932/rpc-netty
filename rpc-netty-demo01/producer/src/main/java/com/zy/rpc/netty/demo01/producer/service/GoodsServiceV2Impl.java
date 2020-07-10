package com.zy.rpc.netty.demo01.producer.service;

import com.zy.rpc.netty.demo01.common.demo.IGoodsService;
import com.zy.rpc.netty.demo01.common.core.RpcImplement;
import com.zy.rpc.netty.demo01.common.demo.StuDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RpcImplement(interfaceType = IGoodsService.class, implCode = "goodsServiceV2Impl")
public class GoodsServiceV2Impl implements IGoodsService {
    @Override
    public List<String> getGoodsName(StuDTO stuDTO) {
        List<String> list = new ArrayList<>();
        list.add("v2");
        if (Objects.nonNull(stuDTO)) {
            list.add(String.valueOf(stuDTO.getId()));
            stuDTO.getMap().forEach((k,v) -> list.add(k + " ==> " + v));
        }
        return list;
    }
}
