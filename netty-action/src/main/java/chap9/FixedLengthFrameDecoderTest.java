package chap9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by hjy on 17-11-27.
 * testFramesDecoded()方法验证了：一个包含了9个可读字节ByteBuf被解码为3个ByteBuf,每个都包含了３个字节，
 * 需要注意的是，仅通过一次对writeInbound()方法的调用，ByteBuf是如何被填充了9个可读字节的，在此之后，通过执行finish()方法
 * ,将EmbadedChannel标记为了已完成状态.最后，通过调用readInbound()方法，从EmbadedChannel中正好读取了３个帧和一个null
 *
 *
 * testFramesDecoded2()方法也是类似的，只有一处不同：入站ByteBuf是通过两个步骤写入的，当writeInbound(input.readBytes(2))被调用时，
 * 返回了false.因为如果readInbound()的后续调用将会返回数据，那么writeInbound()方法将返回true,但是只有当有3个或者更多的字节可供读取时，
 * FixedLengthFrameDecoder才会产生输出,该测试剩下的部分和testFramesDecoded()是相同的
 */
public class FixedLengthFrameDecoderTest {


    @Test   //使用了注解 @Test标注，因此JUnit将会执行该方法
    public void testFramesDecoded(){    //第一个测试方法testFramesDecoded()
        ByteBuf buf = Unpooled.buffer();    //创建一个ByteBuf,并存储9字节
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(
                new FixedLengthFrameDecoder(3));  //创建一个EmbeddedChannel,并添加一个FixedLengthFrameDecoder,将其以3字节的帧长度被测试
        assertTrue(channel.writeInbound(input.retain()));   //将数据写入EmbeddedChannel
        assertTrue(channel.finish());   //标记Channel为已完成状态


        //read messages
        //读取所生成的消息，并且验证是否有3帧（切片）,其中每帧(切片)都为3字节
        ByteBuf read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3),read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3),read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3),read);
        read.release();

        assertNull(channel.readInbound());

        buf.release();
    }


    @Test
    public void testFramesDecoded2(){ //第二个测试方法testFramesDecoded2()
        ByteBuf buf = Unpooled.buffer();    //创建一个ByteBuf,并存储9字节
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(
                new FixedLengthFrameDecoder(3));  //创建一个EmbeddedChannel,并添加一个FixedLengthFrameDecoder,将其以3字节的帧长度被测试

        assertFalse(channel.writeInbound(input.readBytes(2)));  //返回false,因为没有一个完整的可供读取的帧
        assertTrue(channel.writeInbound(input.readBytes(7)));

        assertTrue(channel.finish());

        ByteBuf read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3),read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3),read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3),read);
        read.release();

        assertNull(channel.readInbound());

        buf.release();

    }


}
