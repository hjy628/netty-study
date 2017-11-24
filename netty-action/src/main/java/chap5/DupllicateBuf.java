package chap5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Created by hjy on 17-11-24.
 */
public class DupllicateBuf {

    public static void main(String[] args) {
        DupllicateBuf dupllicateBuf = new DupllicateBuf();
       // dupllicateBuf.sliceTest();
        dupllicateBuf.copyTest();
    }



    /**
     *   @Author
     *   @Description   对ByteBuf进行切片
     *   @Date: 下午3:11 17-11-24
     */
    public void sliceTest(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);   //创建一个用于保存给定字符串的字节的ByteBuf
        ByteBuf sliced = buf.slice(0,15);   //创建该ByteBuf从索引0开始到索引15结束的一个新切片
        System.out.println(sliced.toString(utf8));  //将打印"Netty in Action"
        buf.setByte(0,(byte)'J');   //更新索引0处的字节
        assert buf.getByte(0) == sliced.getByte(0);     //将会成功，因为数据是共享的,对其中一个所做的更改对另外一个也是可见的
    }

    /**
     *   @Author
     *   @Description   复制一个ByteBuf
     *   @Date: 下午3:11 17-11-24
     */
    public void copyTest(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);   //创建一个用于保存给定字符串的字节的ByteBuf
        ByteBuf copy = buf.copy(0,15);   //创建该ByteBuf从索引0开始到索引15结束的分段的副本
        System.out.println(copy.toString(utf8));  //将打印"Netty in Action"
        buf.setByte(0,(byte)'J');   //更新索引0处的字节
        assert buf.getByte(0) != copy.getByte(0);     //将会成功，因为数据不是共享的
    }


}
