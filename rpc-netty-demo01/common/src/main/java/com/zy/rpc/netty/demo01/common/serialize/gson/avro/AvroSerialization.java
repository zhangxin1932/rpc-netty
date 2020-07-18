package com.zy.rpc.netty.demo01.common.serialize.gson.avro;

import com.zy.rpc.netty.demo01.common.serialize.ObjectInput;
import com.zy.rpc.netty.demo01.common.serialize.ObjectOutput;
import com.zy.rpc.netty.demo01.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AvroSerialization implements Serialization {
    @Override
    public ObjectOutput serialize(OutputStream output) throws IOException {
        return new AvroObjectOutput(output);
    }

    @Override
    public ObjectInput deserialize(InputStream input) throws IOException {
        return new AvroObjectInput(input);
    }
}
