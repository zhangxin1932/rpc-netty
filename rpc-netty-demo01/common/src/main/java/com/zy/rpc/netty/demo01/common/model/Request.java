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
    private static final long serialVersionUID = 6375206057883501985L;
    private Long requestId;
    private String implCode;
    private String interfaceName;
    private String methodName;
    private String returnType;
    private String argsTypes;
    private Object[] args;
    // private Class<?>[] argsType;
    // private Class<?> interfaceType;
    // private Class<?> returnType;
}
