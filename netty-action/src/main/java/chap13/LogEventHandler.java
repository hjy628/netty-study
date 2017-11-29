package chap13;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by hjy on 17-11-29.
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent>{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogEvent logEvent) throws Exception {
        StringBuilder builder = new StringBuilder(); //创建StringBuilder,并且构建输出的字符串
        builder.append(logEvent.getReceived());
        builder.append(" [");
        builder.append(logEvent.getSource().toString());
        builder.append("] [");
        builder.append(logEvent.getLogfile());
        builder.append("] : ");
        builder.append(logEvent.getMsg());
        System.out.println(builder.toString()); //打印logEvent的数据
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();        //当异常发生时，打印栈跟中信息，并关闭对应的Channel
    }
}
