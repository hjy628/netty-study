package chap13;

import java.net.InetSocketAddress;

/**
 * Created by hjy on 17-11-29.
 * 消息组件POJO
 */
public class LogEvent {

    public static final byte SEPARATOR = (byte)':';
    private final InetSocketAddress source;
    private final String logfile;
    private final String msg;
    private final long received;

    public LogEvent(String logfile, String msg) {   //用于传出消息的构造函数
        this(null,logfile,msg,-1);
    }

    public LogEvent(InetSocketAddress source, String logfile, String msg, long received) {  //用于传入消息的构造函数
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }

    public InetSocketAddress getSource() {  //返回发送LogEvent的源的InetSocketAddress
        return source;
    }

    public String getLogfile() {    //返回所发送的LogEvent的日志文件的名称
        return logfile;
    }

    public String getMsg() {    //返回消息内容
        return msg;
    }

    public long getReceived() {     //返回接受LogEvent的时间
        return received;
    }
}
