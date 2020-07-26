package com.zy.rpc.netty.demo01.common.exception;

public class ZkException extends RuntimeException {
    private static final long serialVersionUID = 456374284718247184L;

    public ZkException() {
    }

    public ZkException(String message) {
        super(message);
    }

    public ZkException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZkException(Throwable cause) {
        super(cause);
    }

    public ZkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
