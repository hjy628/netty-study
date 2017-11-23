package chap4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by hjy on 16-8-26.
 */
public class PlainOioServer {

    public void serve(int port) throws Exception {
        final ServerSocket serverSocket = new ServerSocket(port);   //将服务器绑定到指定端口
        try {
            for (;;){
                final Socket clientSocket =serverSocket.accept();   //接受连接
                System.out.println("Accepted	connection	from	"	+	clientSocket);


                new Thread(new Runnable() {         //创建一个新的线程来处理该连接
                    @Override
                    public void run() {
                        OutputStream out;
                        try {
                            out = clientSocket.getOutputStream();
                            out.write("Hi\r\n".getBytes(Charset.forName("UTF-8")));     //将消息写给已连接的客户端
                            out.flush();
                            clientSocket.close();       //关闭连接
                        }catch (IOException e){
                            e.printStackTrace();
                            try {
                                clientSocket.close();    //关闭连接
                            }catch (IOException e1){

                            }
                        }
                    }
                }).start();     //启动线程
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            serverSocket.close();
        }
    }


}
