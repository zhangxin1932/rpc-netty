package com.zy.rpc.netty.demo01.common.codec;

import com.zy.rpc.netty.demo01.common.exception.RpcException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.Objects;

public class NettyEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        Class<?> clazz = msg.getClass();
        Codec codec = CodecFactory.getCodec(clazz);
        if (Objects.isNull(codec)) {
            throw new RpcException("codec cannot be null.");
        } else {
            out.writeInt(0);
            out.writeByte(codec.getCode());
            codec.encode(new ByteBufOutputStream(out), msg);
            out.setIndex(0, out.writerIndex() - 4);
        }
    }
}
