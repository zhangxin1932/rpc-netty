package com.zy.rpc.netty.demo01.common.codec.hessian2;

import com.zy.rpc.netty.demo01.common.model.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class Hessian2Request implements Serializable {
    private static final long serialVersionUID = 7422528753266842195L;
    private final Request request;
}
