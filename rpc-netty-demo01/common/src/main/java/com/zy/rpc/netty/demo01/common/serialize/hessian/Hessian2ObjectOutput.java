package com.zy.rpc.netty.demo01.common.serialize.hessian;

import com.caucho.hessian.io.Hessian2Output;
import com.zy.rpc.netty.demo01.common.serialize.ObjectOutput;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Hessian2 Object output.
 */
public class Hessian2ObjectOutput implements ObjectOutput {
    private final Hessian2Output output;

    public Hessian2ObjectOutput(OutputStream os) {
        output = new Hessian2Output(os);
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        output.writeBoolean(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        output.writeInt(v);
    }

    @Override
    public void writeShort(short v) throws IOException {
        output.writeInt(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        output.writeInt(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        output.writeLong(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        output.writeDouble(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        output.writeDouble(v);
    }

    @Override
    public void writeBytes(byte[] b) throws IOException {
        output.writeBytes(b);
    }

    @Override
    public void writeBytes(byte[] b, int off, int len) throws IOException {
        output.writeBytes(b, off, len);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        output.writeString(v);
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        output.writeObject(obj);
    }

    @Override
    public void flushBuffer() throws IOException {
        output.flushBuffer();
    }
}
