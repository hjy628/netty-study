package chap5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * Created by hjy on 17-11-24.
 * 直接缓冲区模式
 *
 */
public class Pattern2 {

    public void test(){
        ByteBuf directBuf = Unpooled.copiedBuffer("堆缓冲区", CharsetUtil.UTF_8);
        if (!directBuf.hasArray()){    //检查ByteBuf是否有一个支撑数组.如果不是，则这是一个直接缓冲区
            int length = directBuf.readableBytes();   //获得可读字节数
            byte[] array = new byte[length];    //分配一个新的数组来保存具有该长度的字节数据
            directBuf.getBytes(directBuf.readerIndex(),array);  //将字节复制到该数组
            handleArray(array,0,length);   //使用数组，偏移量和长度做为参数调用你的方法

        }
    }


    public void handleArray(byte[] bytes,int offset,int length){
        // TODO: 17-11-24 处理数据
    }

}
