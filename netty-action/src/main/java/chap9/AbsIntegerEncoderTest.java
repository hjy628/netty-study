package chap9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hjy on 17-11-28.
 * 代码执行步骤：
 *  １.将4字节的负整数写入到一个新的ByteBuf中
 *  2.创建一个EmbeddedChannel,并为它分配一个AbsIntegerEncoder
 *  3.调用EmbeddedChannel上的writeOutbound()方法来写入该ByteBuf
 *  ４.标记为Channel为已完成状态
 *  5.从EmbeddedChannel的出站端读取所有的整数，并验证是否只产生了绝对值
 */
public class AbsIntegerEncoderTest {

    @Test
    public void testEncoded(){
        ByteBuf buf = Unpooled.buffer();    //创建一个ByteBuf,并写入9个负整数
        for (int i = 0; i < 10; i++) {
            buf.writeInt(i*-1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(
                new AbsIntegerEncoder());   //创建一个EmbeddedChannel,并安装一个要测试的AbsIntegerEncoder
        assertTrue(channel.writeOutbound(buf)); //写入ByteBuf,并断言调用readOutbound()方法将会产生数据
        assertTrue(channel.finish());   //将该Channel标记为已完成状态

        // read bytes
        for (int i = 0; i < 10; i++) {  //读取所产生的消息，并断言它们包含了对应的绝对值
            assertEquals(i,channel.readOutbound());
        }

        assertNull(channel.readOutbound());


    }



}
