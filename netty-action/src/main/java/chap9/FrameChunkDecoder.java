package chap9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Created by hjy on 17-11-28.
 */
public class FrameChunkDecoder extends ByteToMessageDecoder{    //扩展ByteToMessageDecoder以将入站字节编码为消息
    private final int maxFrameSize;

    public FrameChunkDecoder(int maxFrameSize) {   //指定将要产生的帧的最大允许大小
        this.maxFrameSize = maxFrameSize;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readableBytes = in.readableBytes();
        if (readableBytes > maxFrameSize ){
            in.clear(); //如果该帧太大，则丢弃它并抛出一个TooLongFrameException
            throw new TooLongFrameException();
        }

        ByteBuf buf = in.readBytes(readableBytes);  //否则，从ByteBuf中读取一个新的帧
        out.add(buf);   //将该帧添加到解码消息的List中

    }
}
