package chap11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * Created by hjy on 17-11-28.
 */
public class SslChannelInitializer extends ChannelInitializer<Channel>{

    private final SslContext context;
    private final boolean startTls;


    public SslChannelInitializer(SslContext context,  //传入要使用的SslContext
                                 boolean startTls) {    //如果设置为true,第一个写入的消息将不会被加密(客户端应该设置为true)
        this.context = context;
        this.startTls = startTls;
    }

    protected void initChannel(Channel ch) throws Exception {
        SSLEngine engine = context.newEngine(ch.alloc());   //对于每个SslHandler实例，都使用Channel的ByteBufAllocator从SslContext获取一个新的SSLEngine
        ch.pipeline().addFirst("ssl",new SslHandler(engine,startTls));//将SsLHandler作为第一个ChannelHandler添加到ChannelPipelinez中

    }
}
