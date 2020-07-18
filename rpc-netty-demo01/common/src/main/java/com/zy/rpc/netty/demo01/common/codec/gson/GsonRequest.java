package com.zy.rpc.netty.demo01.common.codec.gson;

import com.zy.rpc.netty.demo01.common.model.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class GsonRequest implements Serializable {
    private static final long serialVersionUID = -4207894715421276179L;
    private final Request request;
}
