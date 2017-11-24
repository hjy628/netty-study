package chap5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * Created by hjy on 17-11-24.
 * .堆缓冲区模式
 * 当hasArray()方法返回false时，尝试访问支撑数组将触发一个UnsupportedOperationException.这个模式类似于JDK的ByteBuffer的用法
 */
public class Pattern1 {

    public void test(){
        ByteBuf heapBuf = Unpooled.copiedBuffer("堆缓冲区", CharsetUtil.UTF_8);
        if (heapBuf.hasArray()){    //检查ByteBuf是否有一个支撑数组
            byte[] array = heapBuf.array();     //如果有，则获取对该数组的引用
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex(); //计算第一个字节的偏移量
            int length = heapBuf.readableBytes();   //获得可读字节数
            handleArray(array,offset,length);   //使用数组，偏移量和长度做为参数调用你的方法

        }
    }


    public void handleArray(byte[] bytes,int offset,int length){
        // TODO: 17-11-24 处理数据
    }

}
