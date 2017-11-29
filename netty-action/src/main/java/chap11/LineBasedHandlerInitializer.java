package chap11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Created by hjy on 17-11-28.
 */
public class LineBasedHandlerInitializer extends ChannelInitializer<Channel>{
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new LineBasedFrameDecoder(64*1024));   //该LineBasedFrameDecoder将提取的帧转发给下一个ChannelInboundHandler
        pipeline.addLast(new FrameHandler());  //添加FrameHandler以接收帧
    }



    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf>{

        //传入了单个帧的内容
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
            //Do something with the data extraced from the frame
        }
    }
}
