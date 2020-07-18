package com.zy.rpc.netty.demo01.common.codec;

import com.zy.rpc.netty.demo01.common.codec.gson.GsonRequestCodec;
import com.zy.rpc.netty.demo01.common.codec.gson.GsonResponseCodec;
import com.zy.rpc.netty.demo01.common.codec.hessian2.Hessian2RequestCodec;
import com.zy.rpc.netty.demo01.common.codec.hessian2.Hessian2ResponseCodec;

import java.util.HashMap;
import java.util.Map;

public class CodecFactory {
    private static final Map<Byte, Codec> BYTE_SERIALIZATION_MAP = new HashMap<>();
    private static final Map<Class<?>, Codec> CLASS_SERIALIZATION_MAP = new HashMap<>();

    static {
        Hessian2RequestCodec hessian2RequestCodec = new Hessian2RequestCodec();
        BYTE_SERIALIZATION_MAP.put(hessian2RequestCodec.getCode(), hessian2RequestCodec);
        CLASS_SERIALIZATION_MAP.put(hessian2RequestCodec.getRpcMsgClass(), hessian2RequestCodec);

        Hessian2ResponseCodec hessian2ResponseCodec = new Hessian2ResponseCodec();
        BYTE_SERIALIZATION_MAP.put(hessian2ResponseCodec.getCode(), hessian2ResponseCodec);
        CLASS_SERIALIZATION_MAP.put(hessian2ResponseCodec.getRpcMsgClass(), hessian2ResponseCodec);


        GsonRequestCodec gsonRequestCodec = new GsonRequestCodec();
        BYTE_SERIALIZATION_MAP.put(gsonRequestCodec.getCode(), gsonRequestCodec);
        CLASS_SERIALIZATION_MAP.put(gsonRequestCodec.getRpcMsgClass(), gsonRequestCodec);

        GsonResponseCodec gsonResponseCodec = new GsonResponseCodec();
        BYTE_SERIALIZATION_MAP.put(gsonResponseCodec.getCode(), gsonResponseCodec);
        CLASS_SERIALIZATION_MAP.put(gsonResponseCodec.getRpcMsgClass(), gsonResponseCodec);
    }

    public static Codec getCodec(byte code) {
        return BYTE_SERIALIZATION_MAP.get(code);
    }

    public static Codec getCodec(Class<?> type) {
        return CLASS_SERIALIZATION_MAP.get(type);
    }
}
