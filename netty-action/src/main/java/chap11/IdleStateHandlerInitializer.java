package chap11;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by hjy on 17-11-28.
 * 演示了如何使用IdleStateHandler来检测远程节点是否仍然活着，并且在它失活时通过关闭连接来释放资源
 * 如果连接超过60秒没有接收或者发送任何的数据，那么IdleStateHandler将会使用一个IdleStateEvent事件来调用fireUserEventTriggered方法
 * HeartbeatHandler实现了userEventTriggered方法，如果这个方法检测到IdleStateEvent事件，它将会发送心跳消息，
 * 并且添加一个将在发送操作失败时关闭该连接的ChannelFutureListener
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel>{
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(
                new IdleStateHandler(0,0,60, TimeUnit.SECONDS));    //IdleStateHandler将在被触发时发送一个IdleStateEvent事件
        pipeline.addLast(new HeartbeatHandler());   //将一个HeartbeatHandler添加到ChannelPipeline中
    }


    public static final class HeartbeatHandler extends ChannelInboundHandlerAdapter{    //实现userEventTriggered方法以发送心跳消息
        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.ISO_8859_1));    //发送到远程节点的心跳消息

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent){ //发送心跳消息，并在发送失败时关闭该连接
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }else { //不是IdleStateEvent事件，所以将它传递给下一个ChannelInboundHandler
                super.userEventTriggered(ctx, evt);
            }
        }
    }



}
