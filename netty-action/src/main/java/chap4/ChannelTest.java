package chap4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by hjy on 17-11-24.
 */
public class ChannelTest {


    public void  writeAndFlushtest() throws Exception{
        Channel channel = new Channel() {
            public EventLoop eventLoop() {
                return null;
            }

            public Channel parent() {
                return null;
            }

            public ChannelConfig config() {
                return null;
            }

            public boolean isOpen() {
                return false;
            }

            public boolean isRegistered() {
                return false;
            }

            public boolean isActive() {
                return false;
            }

            public ChannelMetadata metadata() {
                return null;
            }

            public SocketAddress localAddress() {
                return null;
            }

            public SocketAddress remoteAddress() {
                return null;
            }

            public ChannelFuture closeFuture() {
                return null;
            }

            public boolean isWritable() {
                return false;
            }

            public Unsafe unsafe() {
                return null;
            }

            public ChannelPipeline pipeline() {
                return null;
            }

            public ByteBufAllocator alloc() {
                return null;
            }

            public ChannelPromise newPromise() {
                return null;
            }

            public ChannelProgressivePromise newProgressivePromise() {
                return null;
            }

            public ChannelFuture newSucceededFuture() {
                return null;
            }

            public ChannelFuture newFailedFuture(Throwable throwable) {
                return null;
            }

            public ChannelPromise voidPromise() {
                return null;
            }

            public ChannelFuture bind(SocketAddress socketAddress) {
                return null;
            }

            public ChannelFuture connect(SocketAddress socketAddress) {
                return null;
            }

            public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress1) {
                return null;
            }

            public ChannelFuture disconnect() {
                return null;
            }

            public ChannelFuture close() {
                return null;
            }

            public ChannelFuture deregister() {
                return null;
            }

            public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
                return null;
            }

            public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
                return null;
            }

            public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress1, ChannelPromise channelPromise) {
                return null;
            }

            public ChannelFuture disconnect(ChannelPromise channelPromise) {
                return null;
            }

            public ChannelFuture close(ChannelPromise channelPromise) {
                return null;
            }

            public ChannelFuture deregister(ChannelPromise channelPromise) {
                return null;
            }

            public Channel read() {
                return null;
            }

            public ChannelFuture write(Object o) {
                return null;
            }

            public ChannelFuture write(Object o, ChannelPromise channelPromise) {
                return null;
            }

            public Channel flush() {
                return null;
            }

            public ChannelFuture writeAndFlush(Object o, ChannelPromise channelPromise) {
                return null;
            }

            public ChannelFuture writeAndFlush(Object o) {
                return null;
            }

            public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
                return null;
            }

            public int compareTo(Channel o) {
                return 0;
            }
        };
        ByteBuf buf = Unpooled.copiedBuffer("data ", Charset.forName("UTF-8")); //创建持有要写数据的ByteBuf
        ChannelFuture cf = channel.writeAndFlush(buf);  //写数据并冲刷它
        cf.addListener(new ChannelFutureListener() {    //添加ChannelFutureListener以便在写操作完成后接收通知
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    System.out.println("Write successful"); //写操作完成，并且没有错误发生
                }else {
                    System.err.println("Write error");
                    channelFuture.cause().printStackTrace();       //记录错误
                }
            }
        });
    }

    public void  writeAndFlushtest1() throws Exception{
        final Channel channel = new Channel() {
            public EventLoop eventLoop() {
                return null;
            }

            public Channel parent() {
                return null;
            }

            public ChannelConfig config() {
                return null;
            }

            public boolean isOpen() {
                return false;
            }

            public boolean isRegistered() {
                return false;
            }

            public boolean isActive() {
                return false;
            }

            public ChannelMetadata metadata() {
                return null;
            }

            public SocketAddress localAddress() {
                return null;
            }

            public SocketAddress remoteAddress() {
                return null;
            }

            public ChannelFuture closeFuture() {
                return null;
            }

            public boolean isWritable() {
                return false;
            }

            public Unsafe unsafe() {
                return null;
            }

            public ChannelPipeline pipeline() {
                return null;
            }

            public ByteBufAllocator alloc() {
                return null;
            }

            public ChannelPromise newPromise() {
                return null;
            }

            public ChannelProgressivePromise newProgressivePromise() {
                return null;
            }

            public ChannelFuture newSucceededFuture() {
                return null;
            }

            public ChannelFuture newFailedFuture(Throwable throwable) {
                return null;
            }

            public ChannelPromise voidPromise() {
                return null;
            }

            public ChannelFuture bind(SocketAddress socketAddress) {
                return null;
            }

            public ChannelFuture connect(SocketAddress socketAddress) {
                return null;
            }

            public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress1) {
                return null;
            }

            public ChannelFuture disconnect() {
                return null;
            }

            public ChannelFuture close() {
                return null;
            }

            public ChannelFuture deregister() {
                return null;
            }

            public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
                return null;
            }

            public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
                return null;
            }

            public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress1, ChannelPromise channelPromise) {
                return null;
            }

            public ChannelFuture disconnect(ChannelPromise channelPromise) {
                return null;
            }

            public ChannelFuture close(ChannelPromise channelPromise) {
                return null;
            }

            public ChannelFuture deregister(ChannelPromise channelPromise) {
                return null;
            }

            public Channel read() {
                return null;
            }

            public ChannelFuture write(Object o) {
                return null;
            }

            public ChannelFuture write(Object o, ChannelPromise channelPromise) {
                return null;
            }

            public Channel flush() {
                return null;
            }

            public ChannelFuture writeAndFlush(Object o, ChannelPromise channelPromise) {
                return null;
            }

            public ChannelFuture writeAndFlush(Object o) {
                return null;
            }

            public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
                return null;
            }

            public int compareTo(Channel o) {
                return 0;
            }
        };
        final ByteBuf buf = Unpooled.copiedBuffer("data ", CharsetUtil.UTF_8).retain(); //创建持有要写数据的ByteBuf

        Runnable writer = new Runnable() {      //创建将数据写到Channel的Runnable
            public void run() {
                channel.writeAndFlush(buf.duplicate());
            }
        };

        Executor executor = Executors.newCachedThreadPool();    //获取到线程池Executor的引用

        executor.execute(writer);       //递交写任务给线程池以便在某个线程中执行

        executor.execute(writer);    //递交另一个写任务给线程池以便在另一个线程中执行


        ChannelFuture cf = channel.writeAndFlush(buf);  //写数据并冲刷它
        cf.addListener(new ChannelFutureListener() {    //添加ChannelFutureListener以便在写操作完成后接收通知
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    System.out.println("Write successful"); //写操作完成，并且没有错误发生
                }else {
                    System.err.println("Write error");
                    channelFuture.cause().printStackTrace();       //记录错误
                }
            }
        });
    }
}
