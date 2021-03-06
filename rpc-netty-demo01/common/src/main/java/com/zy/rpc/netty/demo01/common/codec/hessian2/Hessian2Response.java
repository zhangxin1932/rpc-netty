package com.zy.rpc.netty.demo01.common.codec.hessian2;

import com.zy.rpc.netty.demo01.common.model.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class Hessian2Response implements Serializable {
    private static final long serialVersionUID = 6684053568730684457L;
    private final Response response;
}
