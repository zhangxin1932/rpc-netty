package com.zy.rpc.netty.demo01.common.exception;

public class RpcException extends RuntimeException {
    public RpcException(String message) {
        super(message);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }
}
