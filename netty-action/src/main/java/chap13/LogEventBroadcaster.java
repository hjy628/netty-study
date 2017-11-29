package chap13;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by hjy on 17-11-29.
 * 监听测试：ncat程序
 *  nc -l -u -p 9999
 */
public class LogEventBroadcaster {

    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;

    public LogEventBroadcaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class)    //引导该NioDatagramChannel(无连接的)
                .option(ChannelOption.SO_BROADCAST,true)    //设置SO_BROADCAST套接字选项
                .handler(new LogEventEncoder(address));
        this.file = file;
    }

    public void run() throws Exception{
        Channel ch = bootstrap.bind(0).sync().channel();    //绑定Channel
        long pointer = 0;
        for (;;){   //启动主处理循环
            long len = file.length();
            if (len<pointer){
                //fie was reset
                pointer = len;
            }else if (len>pointer){ //如果有必要，将文件指针设置到该文件的最后一个字节
                // Content was added
                RandomAccessFile raf = new RandomAccessFile(file,"r");
                raf.seek(pointer);  //设置当前的文件指针，以确保没有任何的旧日志被发送
                String line;
                while ((line=raf.readLine())!=null){
                    ch.writeAndFlush(new LogEvent(null,file.getAbsolutePath(),line,-1));    //对于每个日志条目，写入一个LogEvent到Channel中
                }
                pointer = raf.getFilePointer(); //存储其在文件中的当前位置
                raf.close();
            }
            try {
                TimeUnit.SECONDS.sleep(1);  //休眠一秒，如果被中断，则退出循环，否则重新处理它
            }catch (InterruptedException e){
                Thread.interrupted();
                break;
            }

        }
    }

    public void stop(){
        group.shutdownGracefully();
    }


    public static void main(String[] args) throws Exception{
        if (args.length!=2){
            throw new IllegalArgumentException();
        }

        LogEventBroadcaster broadcaster = new LogEventBroadcaster(  //创建并启动一个新的LogEventBroadcaster实例
                new InetSocketAddress("255.255.255.255",Integer.parseInt(args[0])),new File(args[1]));

        try {
            broadcaster.run();
        }finally {
            broadcaster.stop();
        }

    }

}
