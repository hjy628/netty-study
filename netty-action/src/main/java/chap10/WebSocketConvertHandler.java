package chap10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.*;

import java.util.List;

/**
 * Created by hjy on 17-11-28.
 */
public class WebSocketConvertHandler extends MessageToMessageCodec<WebSocketFrame,WebSocketConvertHandler.MyWebSocketFrame>{


    //将MyWebSocketFrame编码为指定的WebSocketFrame子类型
    protected void encode(ChannelHandlerContext ctx, MyWebSocketFrame msg, List<Object> out) throws Exception {
        ByteBuf payload = msg.getData().duplicate().retain();   //实例化一个指定子类型的WebSocketFrame
        switch (msg.getType()){
            case BINARY:
                out.add(new BinaryWebSocketFrame(payload));
                break;
            case TEXT:
                out.add(new TextWebSocketFrame(payload));
                break;
            case CLOSE:
                out.add(new CloseWebSocketFrame(true,0,payload));
                break;
            case CONTINUATION:
                out.add(new ContinuationWebSocketFrame(payload));
                break;
            case PONG:
                out.add(new PongWebSocketFrame(payload));
                break;
            case PING:
                out.add(new PingWebSocketFrame(payload));
                break;
            default:
                throw new IllegalArgumentException("Unsupported websocket msg"+msg);
        }
    }

    //将WebSocketFrame解码为指定的MyWebSocketFrame,并设置FrameType
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        ByteBuf payload = msg.content().duplicate().retain();
        if (msg instanceof BinaryWebSocketFrame){
            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.BINARY,payload));
        }
        if (msg instanceof CloseWebSocketFrame){
            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.CLOSE,payload));
        }
        if (msg instanceof PingWebSocketFrame){
            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.PING,payload));
        }
        if (msg instanceof TextWebSocketFrame){
            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.TEXT,payload));
        }
        if (msg instanceof ContinuationWebSocketFrame){
            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.CONTINUATION,payload));
        }else {
            throw new IllegalArgumentException("Unsupported websocket msg"+msg);
        }

    }

    public static final class MyWebSocketFrame{ //声明WebSocketConvertHandler所使用的OUTBOUND_IN类型

        public enum FrameType{  //定义拥有被包装的有效负载的WebSocketFrame的类型
            BINARY,
            CLOSE,
            PING,
            PONG,
            TEXT,
            CONTINUATION
        }

        private final FrameType type;
        private final ByteBuf data;


        public MyWebSocketFrame(FrameType type, ByteBuf data) {
            this.type = type;
            this.data = data;
        }

        public FrameType getType() {
            return type;
        }

        public ByteBuf getData() {
            return data;
        }
    }
}
