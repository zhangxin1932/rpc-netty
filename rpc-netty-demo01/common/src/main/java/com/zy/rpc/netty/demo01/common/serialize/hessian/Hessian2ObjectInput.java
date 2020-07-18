package com.zy.rpc.netty.demo01.common.serialize.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.zy.rpc.netty.demo01.common.serialize.ObjectInput;
import com.zy.rpc.netty.demo01.common.serialize.hessian.java8.Hessian2SerializerFactory;
import java.io.IOException;
import java.io.InputStream;

public class Hessian2ObjectInput implements ObjectInput {
    private final Hessian2Input input;

    public Hessian2ObjectInput(InputStream is) {
        input = new Hessian2Input(is);
        input.setSerializerFactory(Hessian2SerializerFactory.INSTANCE);
    }

    @Override
    public boolean readBool() throws IOException {
        return input.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return (byte) input.readInt();
    }

    @Override
    public short readShort() throws IOException {
        return (short) input.readInt();
    }

    @Override
    public int readInt() throws IOException {
        return input.readInt();
    }

    @Override
    public long readLong() throws IOException {
        return input.readLong();
    }

    @Override
    public float readFloat() throws IOException {
        return (float) input.readDouble();
    }

    @Override
    public double readDouble() throws IOException {
        return input.readDouble();
    }

    @Override
    public byte[] readBytes() throws IOException {
        return input.readBytes();
    }

    @Override
    public String readUTF() throws IOException {
        return input.readString();
    }

    @Override
    public Object readObject() throws IOException {
        return input.readObject();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readObject(Class<T> cls) throws IOException {
        return (T) input.readObject(cls);
    }

}
