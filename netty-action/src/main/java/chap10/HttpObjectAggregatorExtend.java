package chap10;

import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * Created by hjy on 17-11-28.
 */
public class HttpObjectAggregatorExtend extends HttpObjectAggregator{
    /**
     * Creates a new instance.
     *
     * @param maxContentLength the maximum length of the aggregated content.
     *                         If the length of the aggregated content exceeds this value,
     *                         a {@link TooLongFrameException} will be raised.
     */
    public HttpObjectAggregatorExtend(int maxContentLength) {
        super(maxContentLength);
    }
}
