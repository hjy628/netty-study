package chap11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by hjy on 17-11-28.
 * 自动聚合Http的消息片段
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel>{

    private final boolean isClient;

    public HttpAggregatorInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    protected void initChannel(Channel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();

        if (isClient){
            pipeline.addLast("codec",new HttpClientCodec());    //如果是客户端，则添加HttpClientCodec
        }else {
            pipeline.addLast("codec",new HttpServerCodec());   //如果是服务器，则添加HttpServerCodec
        }

        pipeline.addLast("aggregator",new HttpObjectAggregator(512*1024));  //将最大的消息大小为512KB的HttpObjectAggregator添加到ChannelPipeline

    }
}
