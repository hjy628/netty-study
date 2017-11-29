package chap11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * Created by hjy on 17-11-28.
 * 使用Https
 */
public class HttpCodecInitializer extends ChannelInitializer<Channel>{

    private final SslContext context;
    private final boolean isClient;

    public HttpCodecInitializer(SslContext context, boolean isClient) {
        this.context = context;
        this.isClient = isClient;
    }

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        SSLEngine engine = context.newEngine(channel.alloc());
        pipeline.addFirst("ssl",new SslHandler(engine));    //将SslHandler添加到ChannelPipeline中以使用HTTPS


        if (isClient){
            pipeline.addLast("codec",new HttpClientCodec());    //如果是客户端，则添加HttpClientCodec
        }else {
            pipeline.addLast("codec",new HttpServerCodec());   //如果是服务器，则添加HttpServerCodec
        }

    }
}
