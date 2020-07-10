package com.zy.rpc.netty.demo01.common.codec.hessian2;

import com.zy.rpc.netty.demo01.common.codec.AbstractRequestCodec;
import com.zy.rpc.netty.demo01.common.model.Request;
import com.zy.rpc.netty.demo01.common.serialize.Serialization;
import com.zy.rpc.netty.demo01.common.serialize.SerializationFactory;

public class Hessian2RequestCodec extends AbstractRequestCodec {
    @Override
    protected Serialization getSerialization() {
        return SerializationFactory.getSerialization(this.getCode());
    }

    @Override
    protected Request getRequest(Object msg) {
        Hessian2Request hessian2Request = (Hessian2Request) msg;
        return hessian2Request.getRequest();
    }

    @Override
    public byte getCode() {
        return Serialization.Type.HESSIAN2.getRequestCode();
    }

    @Override
    public Class<?> getRpcMsgClass() {
        return Hessian2Request.class;
    }
}
