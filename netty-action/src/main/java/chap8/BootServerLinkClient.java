package chap8;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by hjy on 17-11-27.
 * 从Channel引导客户端
 * 尽可能地重用EventLoop,以减少线程创建所带来的开销
 */
public class BootServerLinkClient {

    public static void main(String[] args) {

        ServerBootstrap bootstrap = new ServerBootstrap();  //创建一个ServerBootstrap类的实例
        bootstrap.group( new NioEventLoopGroup(), new NioEventLoopGroup())  //设置EventLoopGroup,提供用于处理Channel事件的EventLoop
        .channel(NioServerSocketChannel.class)    //指定要使用的Channel实现
        .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {       //设置用于处理已被接受的子Channel的I/O及数据的ChannelInBoundHandler

            ChannelFuture connectFuture;

            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                Bootstrap bootstrap = new Bootstrap();  //创建一个Bootstrap类的实例以连接到远程主机
                bootstrap.channel(NioSocketChannel.class)   //指定Channel的实现
                        .handler(new SimpleChannelInboundHandler<ByteBuf>() {   //为入站I/O设置ChannelInboundHandler
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });

                bootstrap.group(ctx.channel().eventLoop()); //使用与分配给已被接受的子Channel相同的EventLoop
                connectFuture = bootstrap.connect(new InetSocketAddress("uu.ttsales.cn",80));   //连接到远程节点
            }

            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                System.out.println("Receive data");
                if (connectFuture.isDone()){
                    // 当连接完成时，执行一些数据操作(如代理)
                }
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
    }

}
