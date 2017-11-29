package chap13;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by hjy on 17-11-29.
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent>{

    private final InetSocketAddress remoteAddress;

    //LogEventEncoder创建了即将被发送到指定的InetSocketAddress的DatagramPacket消息
    public LogEventEncoder(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    protected void encode(ChannelHandlerContext channelHandlerContext, LogEvent logEvent, List<Object> list) throws Exception {
        byte[] file = logEvent.getLogfile().getBytes(CharsetUtil.UTF_8);
        byte[] msg = logEvent.getMsg().getBytes(CharsetUtil.UTF_8);

        ByteBuf buf = channelHandlerContext.alloc()
                .buffer(file.length+msg.length+1);
        buf.writeBytes(file);   //将文件名写入到ByteBuf中
        buf.writeByte(LogEvent.SEPARATOR);  //添加一个SEPARATOR
        buf.writeBytes(msg); //将日志消息写入到ByteBuf中
        list.add(new DatagramPacket(buf,remoteAddress));    //将一个拥有数据和目的地地址的新DatagramPacket添加到出站的消息列表中

    }
}
