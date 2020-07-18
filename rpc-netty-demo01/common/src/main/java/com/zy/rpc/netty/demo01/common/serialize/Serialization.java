package com.zy.rpc.netty.demo01.common.serialize;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serialization {

    ObjectOutput serialize(OutputStream output) throws IOException;

    ObjectInput deserialize(InputStream input) throws IOException;

    @AllArgsConstructor
    @Getter
    enum Type {
        /**
         * Hessian2 序列化 & 反序列化
         */
        HESSIAN2((byte) 2, (byte) 3),
        /**
         * gson 序列化 & 反序列化
         */
        GSON((byte) 4, (byte) 5),
        /**
         * gson 序列化 & 反序列化
         */
        AVRO((byte) 6, (byte) 7),

        ;
        private final byte requestCode;
        private final byte responseCode;
    }
}
