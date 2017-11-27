package chap8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.oio.OioDatagramChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

/**
 * Created by hjy on 17-11-27.
 */
public class DatagramChannelTest {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();  //创建一个Bootstrap的实例以创建客户端Channel并连接他们
        bootstrap.group(new OioEventLoopGroup())    //设置EventLoopGroup,其提供了用以处理Channel事件的EventLoop
                .channel(OioDatagramChannel.class)  //指定Channel的实现
                .handler(new SimpleChannelInboundHandler<DatagramPacket>() {   //设置用以处理Channel的I/O以及数据的ChannelInboundHandler
                    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
                        //do something
                    }
                });


        ChannelFuture future = bootstrap.bind(new InetSocketAddress(0));    //调用bind()方法，因为该协议是无连接的
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()){
                    System.out.println("Channel bound");
                }else {
                    System.err.println("Bind attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });


    }


}
