package com.zy.rpc.netty.demo01.common.codec;

import java.io.InputStream;
import java.io.OutputStream;

public interface Codec {
    byte getCode();

    Class<?> getRpcMsgClass();

    void encode(OutputStream outputStream, Object msg);

    Object decode(InputStream inputStream);
}
