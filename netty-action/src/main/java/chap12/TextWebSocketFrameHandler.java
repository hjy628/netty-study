package chap12;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * Created by hjy on 17-11-29.
 * 处理文本帧
 */
//扩展SimpleChannelInboundHandler,并处理TextWebSocketFrame消息
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{


    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //重写userEventTriggered()方法以处理自定义事件
        if (evt== WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE){
            //如果该事件表示握手成功，则从该ChannelPipeline中移除HttpRequestHandler,因为将不会接收到任何HTTP消息了
            ctx.pipeline().remove(HttpRequestHandler.class);
            //通知所有已经连接的WEbSocket客户端新的客户端已经连接上了  1
            group.writeAndFlush(new TextWebSocketFrame(
                    "Client "+ctx.channel()+" joined"));
            group.add(ctx.channel());   //将新的WebSocket Channel添加到ChannelGroup中，以便它可以接收到所有的消息    2
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //增加消息的引用计数，并将它写到ChannelGroup中所有已经连接的客户端    3
        group.writeAndFlush(textWebSocketFrame.retain());
    }
}
