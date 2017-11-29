package chap122;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by hjy on 17-11-29.
 */
//扩展SimpleChannelInboundHandler以处理FullHttpRequest消息
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest>{

    private final String wsUri;
    private static final File INDEX;

    static {
        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            String path = location.toURI()+"index.html";
            path = !path.contains("file:")?path:path.substring(5);
            INDEX = new File(path);
        }catch (URISyntaxException e){
            throw new IllegalStateException("Unable to locate index.html",e);
        }
    }

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        if (wsUri.equalsIgnoreCase(fullHttpRequest.getUri())){
            //如果请求了WebSocket协议升级，则增加引用计数(调用retain()方法),并将它传递给下一个ChannelInboundHandler
            channelHandlerContext.fireChannelRead(fullHttpRequest.retain());
        }else {
            if (HttpHeaders.is100ContinueExpected(fullHttpRequest)){
                //处理100 Continue 请求以符合Http 1.1规范
                send100Continue(channelHandlerContext);
            }
            RandomAccessFile file = new RandomAccessFile(INDEX,"r");    //读取index.html
            HttpResponse response = new DefaultHttpResponse(fullHttpRequest.getProtocolVersion(),HttpResponseStatus.OK);
            response.headers().set(
                    HttpHeaders.Names.CONTENT_TYPE,"text/plain;charset=UTF-8");
            boolean keepAlive = HttpHeaders.isKeepAlive(fullHttpRequest);
            if (keepAlive){ //如果请求了keep-alive,则添加所需要的HTTP头信息
                response.headers().set(
                        HttpHeaders.Names.CONTENT_LANGUAGE,file.length());
                response.headers().set(
                        HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
            }
            channelHandlerContext.write(response);  //将HttpResponse写到客户端
            if (channelHandlerContext.pipeline().get(SslHandler.class)==null){  //将index.html写到客户端
                channelHandlerContext.write(new DefaultFileRegion(file.getChannel(),0,file.length()));
            }else {
                channelHandlerContext.write(new ChunkedNioFile(file.getChannel()));
            }

            ChannelFuture future = channelHandlerContext.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT); //写LastHttpContent并冲刷至客户端
            if (!keepAlive){
                //如果没有请求keep-alive，则在写操作完成之后关闭Channel
                future.addListener(ChannelFutureListener.CLOSE);
            }


        }
    }



    private static void send100Continue(ChannelHandlerContext ctx){
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
