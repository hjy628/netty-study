package chap5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

/**
 * Created by hjy on 17-11-24.
 * 复合缓冲区模式
 *
 */
public class Pattern3 {

    public void bytebuffer() {
        ByteBuffer header = ByteBuffer.allocate(256);
        ByteBuffer body = ByteBuffer.allocate(1024);
        ByteBuffer[] message = new ByteBuffer[]{};

        ByteBuffer message2 = ByteBuffer.allocate(header.remaining() + body.remaining());

        message2.put(header);
        message2.put(body);
        message2.flip();
    }


    public void compositebytebuf() {

        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
        ByteBuf headerBuf = Unpooled.buffer();
        ByteBuf bodyBuf = Unpooled.buffer();


        messageBuf.addComponents(headerBuf,bodyBuf);    //将ByteBuf实例追加到CompositeByteBuf

        messageBuf.removeComponent(0);  //remove the header 删除位于索引位置为0(第一个组件)的ByteBuf
       /* for (ByteBuf buf :   messageBuf) {
            System.out.println(buf.toString());
        }*/
        CompositeByteBuf compBuf = Unpooled.compositeBuffer();
        int length = compBuf.readableBytes();   //获得可读字节数
        byte[] array = new byte[length];    //分配一个具有可读字节数长度的新数组
        compBuf.getBytes(compBuf.readerIndex(),array);  //将字节读到该数组中
        handleArray(array,0,length);
    }


    public void handleArray(byte[] bytes,int offset,int length){
        // TODO: 17-11-24 处理数据
    }

}
