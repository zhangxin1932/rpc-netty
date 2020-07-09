package com.zy.rpc.netty.demo01.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.Objects;

public class NettyDecoder extends LengthFieldBasedFrameDecoder {
    public NettyDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf byteBuf = (ByteBuf) super.decode(ctx, in);
        if (Objects.isNull(byteBuf)) {
            return null;
        }
        try {
            byte code = byteBuf.readByte();
            Codec codec = CodecFactory.getCodec(code);
            return codec.decode(new ByteBufInputStream(byteBuf));
        } finally {
            // FIXME 这里应该 release 么? 后续有其他继承了 SimpleChannelInboundHandler 的呢?
            byteBuf.release();
        }
    }
}
