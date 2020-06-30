package com.zy.rpc.netty.demo01.common.netty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Request {
    private Long requestId;
    private Class<?> classType;
    private String methodName;
    private Object[] args;
    private Class<?>[] argsType;
    private Class<?> returnType;
}
