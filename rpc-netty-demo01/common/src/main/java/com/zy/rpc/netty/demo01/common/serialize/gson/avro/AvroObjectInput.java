package com.zy.rpc.netty.demo01.common.serialize.gson.avro;

import com.zy.rpc.netty.demo01.common.serialize.ObjectInput;
import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.util.Utf8;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AvroObjectInput implements ObjectInput {

    private static final DecoderFactory DECODER_FACTORY = DecoderFactory.get();
    private final BinaryDecoder decoder;

    public AvroObjectInput(InputStream input) {
        this.decoder = DECODER_FACTORY.binaryDecoder(input, null);
    }

    @Override
    public Object readObject() throws IOException, ClassNotFoundException {
        ReflectDatumReader<Object> reader = new ReflectDatumReader<>(Object.class);
        return reader.read(null, decoder);
    }

    @Override
    @SuppressWarnings(value = {"unchecked"})
    public <T> T readObject(Class<T> cls) throws IOException {
        //Map interface class change to HashMap implement
        if (cls == Map.class) {
            cls = (Class<T>) HashMap.class;
        }

        ReflectDatumReader<T> reader = new ReflectDatumReader<>(cls);
        return reader.read(null, decoder);
    }

    @Override
    public boolean readBool() throws IOException {
        return decoder.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        byte[] bytes = new byte[1];
        decoder.readFixed(bytes);
        return bytes[0];
    }

    @Override
    public short readShort() throws IOException {
        return (short) decoder.readInt();
    }

    @Override
    public int readInt() throws IOException {
        return decoder.readInt();
    }

    @Override
    public long readLong() throws IOException {
        return decoder.readLong();
    }

    @Override
    public float readFloat() throws IOException {
        return decoder.readFloat();
    }

    @Override
    public double readDouble() throws IOException {
        return decoder.readDouble();
    }

    @Override
    public String readUTF() throws IOException {
        Utf8 result = new Utf8();
        result = decoder.readString(result);
        return result.toString();
    }

    @Override
    public byte[] readBytes() throws IOException {
        String resultStr = decoder.readString();
        return resultStr.getBytes(StandardCharsets.UTF_8);
    }
}
