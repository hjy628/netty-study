package chap11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by hjy on 17-11-29.
 */
public class LengthBasedInitializer extends ChannelInitializer<Channel>{
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(
                new LengthFieldBasedFrameDecoder(64*1024,0,8)); //使用LengthFieldBasedFrameDecoder解码将帧长度编码到帧起始的前８个字节中的消息
        pipeline.addLast(new FrameHandler());   //添加FrameHandler以处理每个帧

    }


    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf>{

        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
            //do something with the frame   处理帧的数据
        }
    }
}
