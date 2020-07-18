package com.zy.rpc.netty.demo01.common.codec.avro;

import com.zy.rpc.netty.demo01.common.codec.Codec;
import com.zy.rpc.netty.demo01.common.model.Request;
import com.zy.rpc.netty.demo01.common.serialize.ObjectInput;
import com.zy.rpc.netty.demo01.common.serialize.ObjectOutput;
import com.zy.rpc.netty.demo01.common.serialize.Serialization;
import com.zy.rpc.netty.demo01.common.utils.ArrayUtils;
import com.zy.rpc.netty.demo01.common.utils.ReflectUtils;

import java.io.InputStream;
import java.io.OutputStream;

public class AvroRequestCodec implements Codec {
    @Override
    public byte getCode() {
        return Serialization.Type.AVRO.getRequestCode();
    }

    @Override
    public Class<?> getRpcMsgClass() {
        return AvroRequest.class;
    }

    @Override
    public Request getRequest(Object msg) {
        AvroRequest request = (AvroRequest) msg;
        return request.getRequest();
    }

    @Override
    public void encode(OutputStream outputStream, Object msg) {
        try {
            Serialization serialization = this.getSerialization();
            ObjectOutput serialize = serialization.serialize(outputStream);

            Request request = this.getRequest(msg);
            serialize.writeLong(request.getRequestId());
            serialize.writeUTF(request.getImplCode());
            serialize.writeUTF(request.getInterfaceName());
            serialize.writeUTF(request.getMethodName());
            serialize.writeUTF(request.getReturnType());
            serialize.writeUTF(request.getArgsTypes());
            Object[] args = request.getArgs();
            Class<?>[] classes = ReflectUtils.desc2classArray(request.getArgsTypes());
            if (ArrayUtils.isNotEmpty(classes)) {
                for (int i = 0; i < classes.length; i++) {
                    serialize.writeObject(args[i], classes[i]);
                }
            }

            serialize.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object decode(InputStream inputStream) {
        try {
            Serialization serialization = this.getSerialization();
            ObjectInput deserialize = serialization.deserialize(inputStream);

            long requestId = deserialize.readLong();
            String implCode = deserialize.readUTF();
            String interfaceName = deserialize.readUTF();
            String methodName = deserialize.readUTF();
            String returnType = deserialize.readUTF();
            String argsTypes = deserialize.readUTF();
            Request request = new Request();
            request.setRequestId(requestId);
            request.setImplCode(implCode);
            request.setInterfaceName(interfaceName);
            request.setMethodName(methodName);
            request.setReturnType(returnType);
            request.setArgsTypes(argsTypes);

            Class<?>[] classes = ReflectUtils.desc2classArray(argsTypes);
            if (ArrayUtils.isNotEmpty(classes)) {
                Object[] args = new Object[classes.length];
                for (int i = 0; i < classes.length; i++) {
                    args[i] = deserialize.readObject(classes[i]);
                }
                request.setArgs(args);
            }

            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
