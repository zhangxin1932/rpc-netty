package com.zy.rpc.netty.demo01.common.codec.hessian2;

import com.zy.rpc.netty.demo01.common.model.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Hessian2Request {
    private final Request request;
}
