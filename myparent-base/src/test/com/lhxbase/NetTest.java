package lhxbase;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class NetTest {
    @Test
    public void testAA(){
        for (BlogOpt blogOpt : BlogOpt.values()) {
            System.out.println(blogOpt);
        }
    }
    @Test
    public void testoo(){
        Date old=new Date();
        Date now=DateUtil.addDay(old,2);
        System.out.println(DateUtil.diffDays(old, now));
        System.out.println(DateUtil.diffDays(new Date(2017,3,29,23,00,00), new Date(2017,3,30,00,00,00)));
    }
    @Test
    public void testSocket() throws IOException {
        System.out.println("客户端启动");
        InetAddress address=InetAddress.getLocalHost();
        Socket socket=new Socket(address,8989);
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        System.out.println("服务器发来的数据》》》"+reader.readLine());
        reader.close();
        inputStream.close();
        socket.close();
    }
    @Test
    public void testServerSocket() throws IOException {
        System.out.println("服务器启动");
        ServerSocket serverSocket=new ServerSocket(8989);
        Socket accept = serverSocket.accept();

        OutputStream outputStream = accept.getOutputStream();
        PrintWriter writer=new PrintWriter(outputStream);
        writer.print("来自服务端的问候，送羊入虎口");
        writer.close();
        outputStream.close();

        accept.close();
        serverSocket.close();
    }
    @Test
    public void testInetAddress() throws UnknownHostException {
        InetAddress address=InetAddress.getByName("www.baidu.com");
        System.out.println("111111111>>>>>"+address);
        address=InetAddress.getLocalHost();
        System.out.println("22222>>>>>"+address);
    }
}
