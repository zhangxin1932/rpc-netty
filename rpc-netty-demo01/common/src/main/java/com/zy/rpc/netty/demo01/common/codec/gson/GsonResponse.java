package com.zy.rpc.netty.demo01.common.codec.gson;

import com.zy.rpc.netty.demo01.common.model.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class GsonResponse implements Serializable {
    private static final long serialVersionUID = -6639004294105822489L;
    private final Response response;
}
