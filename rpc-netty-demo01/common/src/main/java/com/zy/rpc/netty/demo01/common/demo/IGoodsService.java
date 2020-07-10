package com.zy.rpc.netty.demo01.common.demo;

import java.util.List;

public interface IGoodsService {
    List<String> getGoodsName(StuDTO stuDTO);
}
