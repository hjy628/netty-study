package chap3;

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
        final ServerSocket serverSocket = new ServerSocket(port);
        try {
            for (;;){
                final Socket clientSocket =serverSocket.accept();
                System.out.println("Accepted	connection	from	"	+	clientSocket);


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream out;
                        try {
                            out = clientSocket.getOutputStream();
                            out.write("Hi\r\n".getBytes(Charset.forName("UTF-8")));
                            out.flush();
                            clientSocket.close();
                        }catch (IOException e){
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            }catch (IOException e1){

                            }
                        }
                    }
                }).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            serverSocket.close();
        }
    }


}
