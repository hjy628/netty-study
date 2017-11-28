package chap10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by hjy on 17-11-28.
 */
public class ShortToByteEncoder extends MessageToByteEncoder<Short>{    //扩展了MessageToByteEncoder
    protected void encode(ChannelHandlerContext ctx, Short msg, ByteBuf out) throws Exception {
        out.writeShort(msg);    //将Short写入ByteBuf中
    }
}
