package com.zy.rpc.netty.demo01.common.codec.hessian2;

import com.zy.rpc.netty.demo01.common.codec.AbstractResponseCodec;
import com.zy.rpc.netty.demo01.common.model.Response;
import com.zy.rpc.netty.demo01.common.serialize.Serialization;
import com.zy.rpc.netty.demo01.common.serialize.SerializationFactory;

public class Hessian2ResponseCodec extends AbstractResponseCodec {
    @Override
    protected Serialization getSerialization() {
        return SerializationFactory.getSerialization(this.getCode());
    }

    @Override
    protected Response getResponse(Object msg) {
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
}
