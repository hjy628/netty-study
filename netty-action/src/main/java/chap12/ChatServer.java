package chap12;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

/**
 * Created by hjy on 17-11-29.
 * 引导服务器
 */
public class ChatServer {
    //创建DefaultChannelGroup，其将保存所有已经连接的WebSocketChannel
    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

    private final EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;


    public ChannelFuture start(InetSocketAddress address){
        //引导服务器
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(createInitializer(channelGroup));
        ChannelFuture future = bootstrap.bind(address);
        future.syncUninterruptibly();
        channel = future.channel();
        return future;
    }


    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group){
        // 创建ChannelInitializer
        return new ChatServerInitializer(group);
    }

    public void destory(){
        //处理服务器关闭，并释放所有的资源
        if (channel!=null){
            channel.close();
        }

        channelGroup.close();
        group.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception{
        if (args.length!=1){
            System.err.println(" Please give port as argument " );
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        final ChatServer endpoint = new ChatServer();
        ChannelFuture future = endpoint.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                endpoint.destory();
            }
        });

        future.channel().closeFuture().syncUninterruptibly();

    }




}
