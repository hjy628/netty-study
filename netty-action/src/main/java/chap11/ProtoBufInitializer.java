package chap11;

import com.google.protobuf.MessageLite;
import io.netty.channel.*;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

import java.io.Serializable;

/**
 * Created by hjy on 17-11-29.
 */
public class ProtoBufInitializer extends ChannelInitializer<Channel>{
    private final MessageLite lite;

    public ProtoBufInitializer(MessageLite lite) {
        this.lite = lite;
    }

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());   //添加ProtobufVarint32FrameDecoder以分割帧
        //添加ProtobufEncoder以处理消息的编码
        pipeline.addLast(new ProtobufEncoder());    //还需要在当前的ProtobufEncoder添加一个相应的ProtobufVarint32FrameDecoder以编码进帧长度信息
        pipeline.addLast(new ProtobufDecoder(lite));    //添加ProtobufDecoder以解码消息
        pipeline.addLast(new ObjectHandler());  //添加ObjectHandler以处理解码消息
    }


    public static final class ObjectHandler extends SimpleChannelInboundHandler<Serializable> {

        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Serializable serializable) throws Exception {
            //do something
        }
    }
}
