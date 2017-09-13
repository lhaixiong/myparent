package com.lhx.lilinfeng.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 传统bio（同步阻碍io）客户端
 */
public class TimeClientBIO {
    public static void main(String[] args) throws Exception{
        System.out.println("客户端启动...");
        String host="127.0.0.1";
        int port=8888;
        Socket s=null;
        BufferedReader in=null;
        PrintWriter out=null;
        try {
            s=new Socket(host,port);
            out=new PrintWriter(s.getOutputStream(),true);
            out.println("currentTime");
            in=new BufferedReader(new InputStreamReader(s.getInputStream()));
            String currentTime = in.readLine();
            System.out.println("客户端接收到服务器传过来的时间:"+currentTime);
        }finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (s != null) {
                s.close();
                System.out.println("客户端关闭socket:"+s);
            }
            s=null;
        }
    }
}
