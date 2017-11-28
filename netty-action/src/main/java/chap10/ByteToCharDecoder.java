package chap10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by hjy on 17-11-28.
 */
public class ByteToCharDecoder extends ByteToMessageDecoder{
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes()>=2){
            out.add(in.readChar()); //将一个或者多个Character对象添加到传出的List中
        }
    }
}
