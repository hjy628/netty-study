package chap10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by hjy on 17-11-28.
 */
public class ToIntegerDecoder extends ByteToMessageDecoder{ //扩展ByteToMessageDecoder类，以将字节解码为特定的格式
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes()>=4){ //检查是否至少有4字节可读（一个int的字节长度）
            out.add(in.readInt());  //从入站ByteBuf中读取一个int,并将其添加到解码消息的List中
        }

    }
}
