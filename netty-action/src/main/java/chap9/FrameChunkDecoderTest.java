package chap9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hjy on 17-11-28.
 */
public class FrameChunkDecoderTest {


    @Test
    public void testFramesDecoded(){
        ByteBuf buf = Unpooled.buffer();    //创建一个ByteBuf,并向它写入9字节
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(
                new FrameChunkDecoder(3));  //创建一个EmbeddedChannel，并向其安装一个帧大小为3字节的FixedLengthFrameDecoder
        assertTrue(channel.writeInbound(input.readBytes(2)));   //向它写入2字节，并断言它们将会产生一个新帧
        try {
            channel.writeInbound(input.readBytes(4));   //写入一个4字节大小的帧，并捕获预期的TooLongFrameException
            Assert.fail();; //如果上面没有抛出异常，那么就会到达这个断言并且测试失败
        }catch (TooLongFrameException e){
           // e.printStackTrace();
        }

        assertTrue(channel.writeInbound(input.readBytes(3)));   //写入剩余的２字节，并断言将会产生一个有效帧
        assertTrue(channel.finish());   //将该Channel标记为已完成状态


        ByteBuf read = (ByteBuf) channel.readInbound(); //读取产生的消息并验证值
        assertEquals(buf.readSlice(2),read);
        read.release();

        read = (ByteBuf)channel.readInbound();
        assertEquals(buf.skipBytes(4).readSlice(3),read);
        read.release();
        buf.release();

    }


}
