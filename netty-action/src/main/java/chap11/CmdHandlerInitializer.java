package chap11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Created by hjy on 17-11-28.
 * 如果正在使用除了行尾符之外的分隔符分割的帧，那么你可以以类似的方式使用DelimiterBasedFrameDecoder,
 * 只需要将特定的分隔符序列指定到其构造函数即可
 *
 * 1.传入数据流是一系列的帧，每个帧都由换行符(\n)分割
 * 2.每个帧都由一系列的元素组成，每个元素都由单个空格字符分割
 * 3.一个帧的内容代表一个命令，定义为一个命令名称后跟着数目可变的参数
 *
 * 根据这个协议的自定义解码器将定义以下类:
 * Cmd--将帧(命令)的内容存储在ByteBuf中，一个ByteBuf用于名称，另一个用于参数
 * CmdDecoder--从被重写了的decode()方法中获取一行字符串，并从它的内容构建一个Cmd的实例
 * CmdHandler--从CmdDecoder获取解码的Cmd对象，并对它进行一些处理
 * CmdHandlerInitializer--为简便起见，我们将会把前面的这些类定义为专门的ChannelInitializer的嵌套类，
 * 其将会把这些ChannelInboundHandler安装到ChannelPipeline中
 *
 *
 *
 */
public class CmdHandlerInitializer extends ChannelInitializer<Channel>{

    static final byte SPACE = (byte)' ';


    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new CmdDecoder(64*1024));  //添加CmdDecoder以提取Cmd对象，并将它转发给下一个ChannelInboundHandler
        pipeline.addLast(new CmdHandler()); //添加CmdHandler以接收和处理Cmd对象
    }







    public static final class Cmd{  //Cmd POJO
        private final ByteBuf name;
        private final ByteBuf args;

        public Cmd(ByteBuf name, ByteBuf args) {
            this.name = name;
            this.args = args;
        }

        public ByteBuf name(){
            return name;
        }

        public ByteBuf args(){
            return args;
        }
    }

    public static final class CmdDecoder extends LineBasedFrameDecoder{

        public CmdDecoder(int maxLength) {
            super(maxLength);
        }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
            ByteBuf frame = (ByteBuf)super.decode(ctx, buffer); //从ByteBuf中提取由行尾符序列分割的帧
            if (frame==null){   //如果输入中没有帧，则返回null
                return null;
            }

            int index = frame.indexOf(frame.readerIndex(),frame.writerIndex(),SPACE);   //查找第一个空格字符的索引，前面是命令名称，接着是参数
            return new Cmd(frame.slice(frame.readerIndex(),index),frame.slice(index+1,frame.writerIndex()));    //使用包含有命令名称和参数的切片创建新的Cmd对象


        }
    }

    public static final class CmdHandler extends SimpleChannelInboundHandler<Cmd>{

        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Cmd cmd) throws Exception {
            //do something with the command 处理传经ChannenPipeline的Cmd对象
        }
    }

}
