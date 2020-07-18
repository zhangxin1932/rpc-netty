package com.zy.rpc.netty.demo01.common.codec.avro;

import com.zy.rpc.netty.demo01.common.model.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AvroRequest {
    private final Request request;
}
