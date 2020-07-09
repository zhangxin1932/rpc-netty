package com.zy.rpc.netty.demo01.common.serialize.hessian;

import com.zy.rpc.netty.demo01.common.serialize.ObjectInput;
import com.zy.rpc.netty.demo01.common.serialize.ObjectOutput;
import com.zy.rpc.netty.demo01.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Hessian2Serialization implements Serialization {

    @Override
    public ObjectOutput serialize(OutputStream out) throws IOException {
        return new Hessian2ObjectOutput(out);
    }

    @Override
    public ObjectInput deserialize(InputStream is) throws IOException {
        return new Hessian2ObjectInput(is);
    }

}
