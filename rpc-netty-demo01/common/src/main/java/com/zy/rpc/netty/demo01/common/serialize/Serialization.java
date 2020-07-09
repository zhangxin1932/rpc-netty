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
        HESSIAN2((byte)2, (byte) 3),

        ;
        private byte requestCode;
        private byte responseCode;

    }
}
