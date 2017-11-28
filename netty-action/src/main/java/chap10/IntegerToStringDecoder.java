package chap10;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by hjy on 17-11-28.
 */
public class IntegerToStringDecoder extends MessageToMessageEncoder<Integer>{   //扩展了MessageToMessageEncoder
    protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        out.add(String.valueOf(msg));   //将Integer消息转换为它的String表示，并将其添加到输出的List中

    }
}
