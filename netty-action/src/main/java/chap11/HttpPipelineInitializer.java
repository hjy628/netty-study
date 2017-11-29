package chap11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Created by hjy on 17-11-28.
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel>{

    private final boolean client;

    public HttpPipelineInitializer(boolean client) {
        this.client = client;
    }

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if (client){
            pipeline.addLast("decoder",new HttpResponseDecoder()); //如果是客户端，则添加HttpResponseDecoder以处理来自服务器的响应
            pipeline.addLast("encoder",new HttpRequestEncoder()); //如果是客户端，则添加HttpRequestEncoder以向服务器发送请求
        }else {
            pipeline.addLast("decoder",new HttpRequestDecoder()); //如果是服务器，则添加HttpRequestDecoder以接收来自客户端的请求
            pipeline.addLast("encoder",new HttpResponseEncoder()); //如果是服务器，则添加HttpResponseEncoder以向客户端发送响应
        }

    }
}
