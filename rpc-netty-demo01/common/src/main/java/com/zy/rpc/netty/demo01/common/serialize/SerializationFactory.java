package com.zy.rpc.netty.demo01.common.serialize;

import com.zy.rpc.netty.demo01.common.serialize.gson.GsonSerialization;
import com.zy.rpc.netty.demo01.common.serialize.gson.avro.AvroSerialization;
import com.zy.rpc.netty.demo01.common.serialize.hessian.Hessian2Serialization;

import java.util.HashMap;
import java.util.Map;

public class SerializationFactory {
    private static final Map<Byte, Serialization> BYTE_SERIALIZATION_MAP = new HashMap<>();
    static {
        Hessian2Serialization hessian2Serialization = new Hessian2Serialization();
        BYTE_SERIALIZATION_MAP.put(Serialization.Type.HESSIAN2.getRequestCode(), hessian2Serialization);
        BYTE_SERIALIZATION_MAP.put(Serialization.Type.HESSIAN2.getResponseCode(), hessian2Serialization);

        GsonSerialization gsonSerialization = new GsonSerialization();
        BYTE_SERIALIZATION_MAP.put(Serialization.Type.GSON.getRequestCode(), gsonSerialization);
        BYTE_SERIALIZATION_MAP.put(Serialization.Type.GSON.getResponseCode(), gsonSerialization);

        AvroSerialization avroSerialization = new AvroSerialization();
        BYTE_SERIALIZATION_MAP.put(Serialization.Type.AVRO.getRequestCode(), avroSerialization);
        BYTE_SERIALIZATION_MAP.put(Serialization.Type.AVRO.getResponseCode(), avroSerialization);
    }

    public static Serialization getSerialization(byte code) {
        return BYTE_SERIALIZATION_MAP.get(code);
    }
}
