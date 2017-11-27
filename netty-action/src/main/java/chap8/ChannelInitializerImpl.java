package chap8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * Created by hjy on 17-11-27.
 */
public final class ChannelInitializerImpl extends ChannelInitializer<Channel> {    //用已设置ChannelPipeline的自定义ChannelInitializerImpl
    @Override
    protected void initChannel(Channel ch) throws Exception {   //将所需的ChannelHandler添加到ChannelPipeline
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpClientCodec());
        pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
    }
}
