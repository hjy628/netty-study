package chap4;

/**
 * Created by hjy on 17-11-24.
 */
public class Test {

    public static void main(String[] args) throws Exception{
        NettyNioServer server = new NettyNioServer();
        NettyOioServer oserver = new NettyOioServer();
        server.server(8888);
    }

}
