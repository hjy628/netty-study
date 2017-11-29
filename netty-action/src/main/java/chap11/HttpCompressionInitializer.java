package chap11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.*;

/**
 * Created by hjy on 17-11-28.
 */
public class HttpCompressionInitializer extends ChannelInitializer<Channel>{


    private final boolean isClient;

    public HttpCompressionInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    protected void initChannel(Channel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();

        if (isClient){
            pipeline.addLast("codec",new HttpClientCodec());    //如果是客户端，则添加HttpClientCodec
            pipeline.addLast("decompressor",new HttpContentDecompressor());   //如果是客户端，则添加HttpContentDecompressor以处理来自服务器的压缩内容
        }else {
            pipeline.addLast("codec",new HttpServerCodec());   //如果是服务器，则添加HttpServerCodec
            pipeline.addLast("compressor",new HttpContentCompressor()); //如果是服务器，则添加HttpContentCompressor来压缩数据(如果客户端支持它)
        }


    }
}
