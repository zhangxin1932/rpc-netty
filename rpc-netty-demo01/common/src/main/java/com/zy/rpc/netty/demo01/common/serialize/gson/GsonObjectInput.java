package com.zy.rpc.netty.demo01.common.serialize.gson;

import com.google.gson.Gson;
import com.zy.rpc.netty.demo01.common.serialize.ObjectInput;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class GsonObjectInput implements ObjectInput {
    private final Gson gson;
    private final BufferedReader reader;

    public GsonObjectInput(InputStream input) {
        this.gson = new Gson();
        this.reader = new BufferedReader(new InputStreamReader(input));
    }

    @Override
    public Object readObject() throws IOException, ClassNotFoundException {
        String line = readLine();
        return gson.fromJson(line, String.class);
    }

    @Override
    public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
        return read(cls);
    }

    @Override
    public <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException {
        // FIXME skip now
        return null;
    }

    @Override
    public boolean readBool() throws IOException {
        return read(boolean.class);
    }

    @Override
    public byte readByte() throws IOException {
        return read(byte.class);
    }

    @Override
    public short readShort() throws IOException {
        return read(short.class);
    }

    @Override
    public int readInt() throws IOException {
        return read(int.class);
    }

    @Override
    public long readLong() throws IOException {
        return read(long.class);
    }

    @Override
    public float readFloat() throws IOException {
        return read(float.class);
    }

    @Override
    public double readDouble() throws IOException {
        return read(double.class);
    }

    @Override
    public String readUTF() throws IOException {
        return read(String.class);
    }

    @Override
    public byte[] readBytes() throws IOException {
        return readLine().getBytes(StandardCharsets.UTF_8);
    }

    private String readLine() throws IOException {
        String line = reader.readLine();
        if (line == null || line.trim().length() == 0) {
            throw new EOFException("error to readLine in gson");
        }
        return line;
    }

    private <T> T read(Class<T> clazz) throws IOException {
        String line = readLine();
        return gson.fromJson(line, clazz);
    }
}
