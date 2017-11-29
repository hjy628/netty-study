package chap13;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by hjy on 17-11-29.
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
        ByteBuf data = datagramPacket.content();    //获取对DatagramPacket中的数据(ByteBuf)的引用
        int idx = data.indexOf(0,data.readableBytes(),LogEvent.SEPARATOR);  //获取该SEPARATOR的索引
        String filename = data.slice(0,idx).toString(CharsetUtil.UTF_8);    //提取文件名
        String logMsg = data.slice(idx+1,data.readableBytes()).toString(CharsetUtil.UTF_8); //提取日志消息

        //构建一个新的LogEvent对象，并且将它添加到(已经解码的消息的)列表中
        LogEvent event = new LogEvent(datagramPacket.sender(),filename,logMsg,System.currentTimeMillis());
        list.add(event);
    }
}
