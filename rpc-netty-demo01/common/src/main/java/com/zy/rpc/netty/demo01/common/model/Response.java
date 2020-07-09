package com.zy.rpc.netty.demo01.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {
    private Long requestId;
    private Object result;
    private Throwable e;
}
