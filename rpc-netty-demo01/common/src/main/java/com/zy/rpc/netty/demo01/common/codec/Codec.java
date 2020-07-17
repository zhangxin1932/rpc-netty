package com.zy.rpc.netty.demo01.common.codec;

import com.zy.rpc.netty.demo01.common.model.Request;
import com.zy.rpc.netty.demo01.common.model.Response;
import com.zy.rpc.netty.demo01.common.serialize.Serialization;
import com.zy.rpc.netty.demo01.common.serialize.SerializationFactory;

import java.io.InputStream;
import java.io.OutputStream;

public interface Codec {
    byte getCode();

    Class<?> getRpcMsgClass();

    default Serialization getSerialization() {
        return SerializationFactory.getSerialization(this.getCode());
    }

    default Request getRequest(Object msg) {
        return null;
    }

    default Response getResponse(Object msg) {
        return null;
    }

    void encode(OutputStream outputStream, Object msg);

    Object decode(InputStream inputStream);
}
