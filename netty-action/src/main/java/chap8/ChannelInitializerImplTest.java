package chap8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;

/**
 * Created by hjy on 17-11-27.
 */
public class ChannelInitializerImplTest {

    public static void main(String[] args) throws Exception{

        ServerBootstrap bootstrap = new ServerBootstrap();  //创建ServerBootstrap以创建和绑定新的Channel
        bootstrap.group(new NioEventLoopGroup(),new NioEventLoopGroup())    //设置EventLoopGroup,将其提供用以处理Channel事件的EventLoop
        .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializerImpl());    //注册一个ChannelInitializerImpl的实例来设置ChannelPipeline

        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
        future.sync();



    }

}
