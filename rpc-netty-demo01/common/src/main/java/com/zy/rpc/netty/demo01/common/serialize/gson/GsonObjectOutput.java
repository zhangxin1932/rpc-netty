package com.zy.rpc.netty.demo01.common.serialize.gson;

import com.google.gson.Gson;
import com.zy.rpc.netty.demo01.common.serialize.ObjectOutput;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GsonObjectOutput implements ObjectOutput {

    private final Gson gson;
    private final PrintWriter writer;

    public GsonObjectOutput(OutputStream output) {
        this.gson = new Gson();
        this.writer = new PrintWriter(new OutputStreamWriter(output));
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        char[] json = gson.toJson(obj).toCharArray();
        writer.write(json, 0, json.length);
        writer.println();
        writer.flush();
        json = null;
    }

    @Override
    public void writeObject(Object obj, Class<?> clazz) throws IOException {
        char[] json = gson.toJson(obj, clazz).toCharArray();
        writer.write(json, 0, json.length);
        writer.println();
        writer.flush();
        json = null;
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeShort(short v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeBytes(byte[] v) throws IOException {
        writer.println(new String(v));
    }

    @Override
    public void writeBytes(byte[] v, int off, int len) throws IOException {
        writer.println(new String(v, off, len));
    }

    @Override
    public void flushBuffer() throws IOException {
        writer.flush();
    }
}
