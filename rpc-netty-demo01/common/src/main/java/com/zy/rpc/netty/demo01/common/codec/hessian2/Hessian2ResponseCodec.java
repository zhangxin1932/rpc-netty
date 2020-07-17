package com.zy.rpc.netty.demo01.common.codec.hessian2;

import com.zy.rpc.netty.demo01.common.codec.Codec;
import com.zy.rpc.netty.demo01.common.model.Response;
import com.zy.rpc.netty.demo01.common.serialize.ObjectInput;
import com.zy.rpc.netty.demo01.common.serialize.ObjectOutput;
import com.zy.rpc.netty.demo01.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Hessian2ResponseCodec implements Codec {

    @Override
    public Response getResponse(Object msg) {
        Hessian2Response hessian2Response = (Hessian2Response) msg;
        return hessian2Response.getResponse();
    }

    @Override
    public byte getCode() {
        return Serialization.Type.HESSIAN2.getResponseCode();
    }

    @Override
    public Class<?> getRpcMsgClass() {
        return Hessian2Response.class;
    }

    @Override
    public void encode(OutputStream outputStream, Object msg) {
        try {
            Serialization serialization = this.getSerialization();
            ObjectOutput serialize = serialization.serialize(outputStream);
            serialize.writeObject(this.getResponse(msg));
            serialize.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object decode(InputStream inputStream) {
        try {
            Serialization serialization = this.getSerialization();
            ObjectInput deserialize = serialization.deserialize(inputStream);
            return deserialize.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
