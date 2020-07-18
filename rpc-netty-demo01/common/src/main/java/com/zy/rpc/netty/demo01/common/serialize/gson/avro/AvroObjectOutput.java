package com.zy.rpc.netty.demo01.common.serialize.gson.avro;

import com.zy.rpc.netty.demo01.common.serialize.ObjectOutput;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.avro.util.Utf8;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class AvroObjectOutput implements ObjectOutput {

    private static final EncoderFactory ENCODER_FACTORY = EncoderFactory.get();
    private final BinaryEncoder encoder;

    public AvroObjectOutput(OutputStream output) {
        this.encoder = ENCODER_FACTORY.binaryEncoder(output, null);
    }

    @Override
    @SuppressWarnings(value = {"rawtypes", "unchecked"})
    public void writeObject(Object obj) throws IOException {
        if (obj == null) {
            encoder.writeNull();
            return;
        }
        ReflectDatumWriter writer = new ReflectDatumWriter<>(obj.getClass());
        writer.write(obj, encoder);
    }

    @Override
    @SuppressWarnings(value = {"rawtypes", "unchecked"})
    public void writeObject(Object obj, Class<?> clazz) throws IOException {
        if (obj == null) {
            encoder.writeNull();
            return;
        }
        ReflectDatumWriter writer = new ReflectDatumWriter<>(clazz);
        writer.write(obj, encoder);
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        encoder.writeBoolean(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        encoder.writeFixed(new byte[]{v});
    }

    @Override
    public void writeShort(short v) throws IOException {
        encoder.writeInt(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        encoder.writeInt(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        encoder.writeLong(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        encoder.writeFloat(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        encoder.writeDouble(v);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        encoder.writeString(new Utf8(v));
    }

    @Override
    public void writeBytes(byte[] v) throws IOException {
        encoder.writeString(new String(v, StandardCharsets.UTF_8));
    }

    @Override
    public void writeBytes(byte[] v, int off, int len) throws IOException {
        byte[] v2 = Arrays.copyOfRange(v, off, off + len);
        encoder.writeString(new String(v2, StandardCharsets.UTF_8));
    }

    @Override
    public void flushBuffer() throws IOException {
        encoder.flush();
    }
}
