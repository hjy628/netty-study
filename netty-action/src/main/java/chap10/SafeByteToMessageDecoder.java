package chap10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Created by hjy on 17-11-28.
 */
public class SafeByteToMessageDecoder extends ByteToMessageDecoder{ //扩展ByteToMessageDecoder以将字节解码为消息
    private static final int MAX_FRAME_SIZE = 1024;

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readable = in.readableBytes();
        if (readable>MAX_FRAME_SIZE){   //检查缓冲区中是否有超过MAX_FRAME_SIZE个字节
            in.skipBytes(readable); //跳过所有的可读字节，抛出TooLongFrameException并通知ChannelHandler
            throw new TooLongFrameException("Frame too big!");
        }
    }
}
