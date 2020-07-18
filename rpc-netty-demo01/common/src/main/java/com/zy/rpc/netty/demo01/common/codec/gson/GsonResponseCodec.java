package com.zy.rpc.netty.demo01.common.codec.gson;

import com.zy.rpc.netty.demo01.common.codec.Codec;
import com.zy.rpc.netty.demo01.common.model.Response;
import com.zy.rpc.netty.demo01.common.serialize.ObjectInput;
import com.zy.rpc.netty.demo01.common.serialize.ObjectOutput;
import com.zy.rpc.netty.demo01.common.serialize.Serialization;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GsonResponseCodec implements Codec {

    @Override
    public byte getCode() {
        return Serialization.Type.GSON.getResponseCode();
    }

    @Override
    public Class<?> getRpcMsgClass() {
        return GsonResponse.class;
    }

    @Override
    public Response getResponse(Object msg) {
        GsonResponse response = (GsonResponse) msg;
        return response.getResponse();
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
            return deserialize.readObject(Response.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
