package chap8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * Created by hjy on 17-11-27.
 */
public class ChannelOptionTest {

    public static void main(String[] args) {
        final AttributeKey<Integer> id = new AttributeKey<Integer>("ID");   //创建一个AttributeKey以标识该属性
        Bootstrap bootstrap = new Bootstrap();  //创建一个Bootstrap的实例以创建客户端Channel并连接他们
        bootstrap.group(new NioEventLoopGroup())    //设置EventLoopGroup,其提供了用以处理Channel事件的EventLoop
                .channel(NioSocketChannel.class)    //指定Channel的实现
                .handler(
                        new SimpleChannelInboundHandler<ByteBuf>() {    //设置用以处理Channel的I/O以及数据的ChannelInboundHandler


                            @Override
                            public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                Integer idValue = ctx.channel().attr(id).get(); //使用AttributeKey检索属性以及它的值
                                //do something with the idValue
                            }

                            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                System.out.println("Received data");
                            }
                        }
                );

        bootstrap.option(ChannelOption.SO_KEEPALIVE,true)   //设置ChannelOption,其将在connect()或者bind()方法被调用时被设置到已经创建的Channel上
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000);
        bootstrap.attr(id,123456);  //存储该id属性

        ChannelFuture future = bootstrap.connect(new InetSocketAddress("uu.ttsales.cn",80));    //使用配置好的Bootstrap实例连接到远程主机
        future.syncUninterruptibly();

    }

}
