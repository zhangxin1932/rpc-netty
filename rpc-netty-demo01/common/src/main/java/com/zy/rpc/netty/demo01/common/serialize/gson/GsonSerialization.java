package com.zy.rpc.netty.demo01.common.serialize.gson;

import com.zy.rpc.netty.demo01.common.serialize.ObjectInput;
import com.zy.rpc.netty.demo01.common.serialize.ObjectOutput;
import com.zy.rpc.netty.demo01.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GsonSerialization implements Serialization {
    @Override
    public ObjectOutput serialize(OutputStream output) throws IOException {
        return new GsonObjectOutput(output);
    }

    @Override
    public ObjectInput deserialize(InputStream input) throws IOException {
        return new GsonObjectInput(input);
    }
}
