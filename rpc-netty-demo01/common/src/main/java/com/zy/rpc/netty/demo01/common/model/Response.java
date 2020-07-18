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
public class Response implements Serializable {
    private static final long serialVersionUID = -3377934858674584553L;
    private Long requestId;
    private Object result;
    private Throwable e;
}
