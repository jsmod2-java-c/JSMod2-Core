package net.noyark.scpslserver.jsmod2;

import net.noyark.scpslserver.jsmod2.inferf.log.ILogger;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static net.noyark.scpslserver.jsmod2.FileSystem.getFileSystem;

/**
 * jsmod2 server class
 *
 * @author magiclu550 #(code)jsmod2
 *
 */

public class Server {


    static {
        commandInfo = new HashMap<>();
        registerCommand();
    }
    // 开辟线程
    // 监听线程 和 一个输入线程
    // 输入线程负责输入命令等

    public static final String PROP = "prop:";

    private static Scanner scanner = new Scanner(System.in);

    private ILogger log;

    private Properties lang;

    private ExecutorService pool = Executors.newFixedThreadPool(5);
    /**用于将服务器对象传递给插件对象*/
    private Server server;

    private static CommandConsoleSender sender;

    private static Map<String,String> commandInfo;

    public static final int MAX_LENGTH = 65535;

    public final File serverfolder;


    public Properties serverProp;

    public File pluginDir;


    Server(ILogger log, Properties lang) {

        this.log = log;

        this.lang = lang;

        this.server = this;

        this.serverfolder = new File(System.getProperty("user.dir")).getParentFile();

        //创建plugin文件夹
        this.pluginDir = getFileSystem().pluginDir(server);

        this.serverProp = getFileSystem().serverProperties(server);

        sender = new CommandConsoleSender(server);

        start();
    }

    public void start(){
        this.pool.execute(new ListenerThread());
    }


    public void close(){
        closeStream();
        log.info(lang.getProperty("end.finish"));
        System.exit(0);
    }

    public static Scanner getScanner(){
        return scanner;
    }

    private void closeStream(){
        try{
            List<InputStream> oStreams = FileSystem.getFileSystem().getInputStreams();
            for(InputStream stream : oStreams){
                stream.close();
            }
            List<OutputStream> iStreams = FileSystem.getFileSystem().getOutputStreams();
            for(OutputStream stream : iStreams){
                stream.close();
            }
            List<BufferedReader> readers = FileSystem.getFileSystem().getReaders();
            for(BufferedReader reader:readers){
                reader.close();
            }
            List<PrintWriter> writers = FileSystem.getFileSystem().getWriters();
            for(PrintWriter writer:writers){
                writer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 服务器监听线程启动
     */
    private class ListenerThread implements Runnable{
        @Override
        public void run() {
            try{
                DatagramSocket socket = getSocket(Integer.parseInt(serverProp.getProperty("port")));

                while (true) {

                    //接收数据包

                    DatagramPacket request = new DatagramPacket(new byte[MAX_LENGTH], MAX_LENGTH);

                    socket.receive(request);

                    String message = new String(request.getData(), 0 , request.getLength());


                    if(message.startsWith("[2]")){
                        //停止服务端
                        socket.close();
                        close();
                    }
                    //发出数据包部分由C#插件决定，C#插件的Server中央处理java发出的请求
                    //专门设立发包的api
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public ILogger getLogger() {
        return log;
    }


    public Properties getLang() {
        return lang;
    }


    public Server getServer() {
        return server;
    }

    public File getServerFolder(){
        return serverfolder;
    }
    public static CommandConsoleSender getSender(){
        return sender;
    }

    public void help(){
        System.out.println("++++++++++++HELP++++++++++");
        Set<Map.Entry<String,String>> cmdSet = commandInfo.entrySet();
        for(Map.Entry<String,String> entry:cmdSet){
            String key = entry.getKey();
            String value = entry.getValue();
            if(value.startsWith(PROP)){
                StringBuilder builder = new StringBuilder(value);
                value = builder.substring(PROP.length());
                value = lang.getProperty(value);
            }
            System.out.println(key+": "+value);
        }
    }

    public static void registerCommand(){
        /**
         * prop:指向当前的lang文件
         */
        commandInfo.put("help","prop:cmd.help");
        commandInfo.put("stop","prop:cmd.stop");
    }

    //监听Smod2转发端接口
    public DatagramSocket getSocket(int port) throws SocketException {

        DatagramSocket socket = new DatagramSocket(port);

        return socket;
    }
}
