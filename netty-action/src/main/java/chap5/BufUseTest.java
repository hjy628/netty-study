package chap5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Created by hjy on 17-11-24.
 */
public class BufUseTest {

    public static void main(String[] args) {
        BufUseTest.readOrWriteTest();
    }

    public static void getOrSetTest(){

        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);   //创建一个用于保存给定字符串的字节的ByteBuf
        System.out.println((char)buf.getByte(0));   //打印第一个字符N
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();        //存储当前的readerIndex和writerIndex
        buf.setByte(0,(byte)'B');   //将索引0处的字节更新为字符'B'
        System.out.println((char)buf.getByte(0));
        assert  readerIndex ==buf.readerIndex();
        assert  writerIndex == buf.writerIndex();   //将会成功，因为这些操作并不会修改相应的索引

    }

    public static void readOrWriteTest(){

        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);   //创建一个用于保存给定字符串的字节的ByteBuf
        System.out.println((char)buf.readByte());   //打印第一个字符N
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();        //存储当前的readerIndex和writerIndex
        buf.writeByte((byte)'?');   //将字符'B'追加到缓冲区
        System.out.println((char)buf.getByte(0));
        assert  readerIndex ==buf.readerIndex();
        assert  writerIndex != buf.writerIndex();   //将会成功，因为writeByte()方法移动了writerIndex

    }


}
