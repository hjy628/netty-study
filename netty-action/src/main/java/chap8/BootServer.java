package chap8;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

/**
 * Created by hjy on 17-11-27.
 * 引导一个服务器
 */
public class BootServer {

    public static void main(String[] args) {

        NioEventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();  //创建一个ServerBootstrap类的实例
        bootstrap.group(group)  //设置EventLoopGroup,提供用于处理Channel事件的EventLoop
        .channel(NioServerSocketChannel.class)    //指定要使用的Channel实现
        .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {       //设置用于处理已被接受的子Channel的I/O及数据的ChannelInBoundHandler
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                System.out.println("Receive data");
                System.out.println((char)msg.readByte());
                msg.writeByte((byte)'w');
            }
        });

        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));    //通过配置好的ServerBootstrap的实例绑定该Channel
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("Server bound");
                    }else {
                        System.err.println("Bound attempt failed");
                        future.cause().printStackTrace();
                    }
            }
        });

        Future<?> future1 = group.shutdownGracefully(); //shutdownGracefully()方法将释放所有的资源，并且关闭所有的当前正在使用中的Channel
    }

}
