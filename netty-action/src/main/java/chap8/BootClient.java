package chap8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by hjy on 17-11-27.
 * 引导一个使用NIO TCP传输的客户端
 */
public class BootClient {

    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();  //创建一个Bootstrap类的实例以创建和连接新的客户端channel
        bootstrap.group(group)  //设置EventLoopGroup,提供用于处理Channel事件的EventLoop
        .channel(NioSocketChannel.class)    //指定要使用的Channel实现
        .handler(new SimpleChannelInboundHandler<ByteBuf>() {       //设置用于Channel事件和数据的ChannelInBoundHandler
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                System.out.println("Receive data");
                System.out.println((char)msg.readByte());
            }
        });

        ChannelFuture future = bootstrap.connect(new InetSocketAddress("uu.ttsales.cn",80));    //连接到远程主机
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("Connection established");
                    }else {
                        System.err.println("Connection attempt failed");
                        future.cause().printStackTrace();
                    }
            }
        });
    }

}
