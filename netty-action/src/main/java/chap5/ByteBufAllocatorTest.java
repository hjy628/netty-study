package chap5;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by hjy on 17-11-24.
 */
public class ByteBufAllocatorTest {

    public static void test(){
        Channel channel = new SocketChannel() {
            public int compareTo(Channel o) {
                return 0;
            }

            public <T> Attribute<T> attr(AttributeKey<T> key) {
                return null;
            }

            public EventLoop eventLoop() {
                return null;
            }

            public ServerSocketChannel parent() {
                return null;
            }

            public SocketChannelConfig config() {
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

            public InetSocketAddress localAddress() {
                return null;
            }

            public InetSocketAddress remoteAddress() {
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

            public ChannelFuture newFailedFuture(Throwable cause) {
                return null;
            }

            public ChannelPromise voidPromise() {
                return null;
            }

            public ChannelFuture bind(SocketAddress localAddress) {
                return null;
            }

            public ChannelFuture connect(SocketAddress remoteAddress) {
                return null;
            }

            public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
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

            public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
                return null;
            }

            public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
                return null;
            }

            public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
                return null;
            }

            public ChannelFuture disconnect(ChannelPromise promise) {
                return null;
            }

            public ChannelFuture close(ChannelPromise promise) {
                return null;
            }

            public ChannelFuture deregister(ChannelPromise promise) {
                return null;
            }

            public Channel read() {
                return null;
            }

            public ChannelFuture write(Object msg) {
                return null;
            }

            public ChannelFuture write(Object msg, ChannelPromise promise) {
                return null;
            }

            public Channel flush() {
                return null;
            }

            public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
                return null;
            }

            public ChannelFuture writeAndFlush(Object msg) {
                return null;
            }

            public boolean isInputShutdown() {
                return false;
            }

            public boolean isOutputShutdown() {
                return false;
            }

            public ChannelFuture shutdownOutput() {
                return null;
            }

            public ChannelFuture shutdownOutput(ChannelPromise future) {
                return null;
            }
        };


        ByteBufAllocator allocator = channel.alloc();   //从Channel获取一个到ByteBufAllocator的引用


        ChannelHandlerContext ctx = new ChannelHandlerContext() {
            public Channel channel() {
                return null;
            }

            public EventExecutor executor() {
                return null;
            }

            public String name() {
                return null;
            }

            public ChannelHandler handler() {
                return null;
            }

            public boolean isRemoved() {
                return false;
            }

            public ChannelHandlerContext fireChannelRegistered() {
                return null;
            }

            public ChannelHandlerContext fireChannelUnregistered() {
                return null;
            }

            public ChannelHandlerContext fireChannelActive() {
                return null;
            }

            public ChannelHandlerContext fireChannelInactive() {
                return null;
            }

            public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
                return null;
            }

            public ChannelHandlerContext fireUserEventTriggered(Object event) {
                return null;
            }

            public ChannelHandlerContext fireChannelRead(Object msg) {
                return null;
            }

            public ChannelHandlerContext fireChannelReadComplete() {
                return null;
            }

            public ChannelHandlerContext fireChannelWritabilityChanged() {
                return null;
            }

            public ChannelFuture bind(SocketAddress localAddress) {
                return null;
            }

            public ChannelFuture connect(SocketAddress remoteAddress) {
                return null;
            }

            public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
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

            public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
                return null;
            }

            public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
                return null;
            }

            public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
                return null;
            }

            public ChannelFuture disconnect(ChannelPromise promise) {
                return null;
            }

            public ChannelFuture close(ChannelPromise promise) {
                return null;
            }

            public ChannelFuture deregister(ChannelPromise promise) {
                return null;
            }

            public ChannelHandlerContext read() {
                return null;
            }

            public ChannelFuture write(Object msg) {
                return null;
            }

            public ChannelFuture write(Object msg, ChannelPromise promise) {
                return null;
            }

            public ChannelHandlerContext flush() {
                return null;
            }

            public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
                return null;
            }

            public ChannelFuture writeAndFlush(Object msg) {
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

            public ChannelFuture newFailedFuture(Throwable cause) {
                return null;
            }

            public ChannelPromise voidPromise() {
                return null;
            }

            public <T> Attribute<T> attr(AttributeKey<T> key) {
                return null;
            }
        };

        ByteBufAllocator allocator2 = ctx.alloc();      //从ChannelHandlerContext获取一个到ByteBufAllocator的引用
    }

}
