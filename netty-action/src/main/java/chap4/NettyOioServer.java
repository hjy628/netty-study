package chap4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.sctp.oio.OioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by hjy on 17-11-23.
 * 使用Netty框架的阻塞版本
 */
public class NettyOioServer {


    public void server(int port)throws Exception{
        final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
        EventLoopGroup group = new OioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();  //创建ServerBootstrap
            b.group(group)
                    .channel(OioSctpServerChannel.class)    //使用OioEventLoopGroup以允许阻塞模式(旧的I/O)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {     //指定ChannelInitializer,对于每个已接受的连接都调用它
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new ChannelInboundHandlerAdapter(){ //添加一个ChannelInboundHandlerAdapter以拦截和处理事件
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            //将消息写到客户端，并添加ChannelFutureListener以便消息一被写完就关闭连接
                                            System.out.println("receive oio");
                                            ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                        }
                                    }
                            );
                        }
                    });
            ChannelFuture f = b.bind().sync();  //绑定服务器以接受连接
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();  //释放所有的资源
        }







    }


}
