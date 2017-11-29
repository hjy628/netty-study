package chap11;

import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by hjy on 17-11-29.
 */
public class FileRegionTest1 {

    public static void main(String[] args) throws Exception{

        File file = new File("");
        FileInputStream in = new FileInputStream(file); //创建一个FileInputStream
        FileRegion region = new DefaultFileRegion(
                in.getChannel(),0,file.length());   //以该文件的完整长度创建一个新的DefaultFileRegion
        Channel channel = new NioSocketChannel();
        channel.writeAndFlush(region).addListener(  //发送该DefaultFileRegion,并注册一个ChannelFutureListener
                new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if(!channelFuture.isSuccess()){
                            Throwable cause = channelFuture.cause();    //处理失败
                        }
                    }
                }
        );
    }


}
