package chap10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by hjy on 17-11-28.
 */
public class ToIntegerDecoder2 extends ReplayingDecoder<Void>{  //扩展ReplayingDecoder<Void>以将字节解码为消息
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //  传入的ByteBuf是ReplayingDecoderByteBuf
        out.add(in.readInt());  //从入站ByteBuf中读取一个int,并将其添加到解码消息的List中
    }
}
