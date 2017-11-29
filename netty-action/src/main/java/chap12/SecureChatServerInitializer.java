package chap12;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import javax.net.ssl.SSLEngine;

/**
 * Created by hjy on 17-11-29.
 */
public class SecureChatServerInitializer extends ChatServerInitializer{
    //扩展了ChatServerInitializer

    private final SslContext context;

    public SecureChatServerInitializer(ChannelGroup group,SslContext context) {
        super(group);
        this.context = context;
    }

    protected void initChannel(Channel channel) throws Exception {
        super.initChannel(channel); //调用父类的initChannel()方法
        //将所有需要的ChannelHandler添加到ChannelPipeline中
        SSLEngine engine = context.newEngine(channel.alloc());
        engine.setUseClientMode(false);
        channel.pipeline().addFirst(new SslHandler(engine));    //将SslHandler添加到ChannelPipeline


    }
}
