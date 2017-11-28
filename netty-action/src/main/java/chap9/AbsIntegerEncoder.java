package chap9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by hjy on 17-11-28.
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf>{    //将MessageToMessageEncoder以将一个消息编码为另一种格式
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
                while (msg.readableBytes()>=4){ //检查是否有足够的字节用来编码
                    int value = Math.abs(msg.readInt());    //从输入的ByteBuf中读取下一个整数，并且计算其绝对值
                    out.add(value); //将该整数写入到编码消息的List中
                }
    }
}
