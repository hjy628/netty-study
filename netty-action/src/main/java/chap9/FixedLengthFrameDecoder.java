package chap9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * Created by hjy on 17-11-27.
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder{
    //扩展ByteToMessageDecoder以处理入站字节，并将它们解码为消息

    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength) {   //指定要生成的帧的长度
        if (frameLength<=0){
            throw new IllegalArgumentException("frameLength must be a positive integer : "+frameLength);
        }
        this.frameLength = frameLength;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            while (in.readableBytes()>=frameLength){    //检查是否有足够的字节可以被读取，以生成下一个帧
                ByteBuf buf = in.readBytes(frameLength);    //从ByteBuf 中读取一个新帧
                out.add(buf);   //将该帧添加到已被解码的消息列表中
            }
    }
}
