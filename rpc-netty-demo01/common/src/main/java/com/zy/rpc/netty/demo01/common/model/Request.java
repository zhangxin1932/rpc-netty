package com.zy.rpc.netty.demo01.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Request implements Serializable {
    private Long requestId;
    private Class<?> interfaceType;
    private String methodName;
    private Object[] args;
    private Class<?>[] argsType;
    private Class<?> returnType;
    private String implCode;
}
