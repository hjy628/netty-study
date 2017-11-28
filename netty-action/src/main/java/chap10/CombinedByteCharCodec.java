package chap10;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * Created by hjy on 17-11-28.
 */
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder,CharToByteEncoder>{
    public CombinedByteCharCodec() {
        super(new ByteToCharDecoder(),new CharToByteEncoder());
    }
}
