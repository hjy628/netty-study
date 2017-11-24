package chap5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufProcessor;
import io.netty.buffer.Unpooled;

/**
 * Created by hjy on 17-11-24.
 * ByteBuf 查找操作
 */
public class FindTest {

    public void find(){
        ByteBuf buf = Unpooled.buffer();

        int index = buf.forEachByte(ByteBufProcessor.FIND_CR); //查找回车符(\r)
    }

}
