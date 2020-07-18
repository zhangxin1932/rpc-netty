package com.zy.rpc.netty.demo01.common.codec.avro;

import com.zy.rpc.netty.demo01.common.model.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AvroResponse {
    private final Response response;
}
