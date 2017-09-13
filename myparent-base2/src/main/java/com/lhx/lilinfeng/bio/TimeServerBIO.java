package com.lhx.lilinfeng.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 传统bio（同步阻碍io） 时间服务器
 */
public class TimeServerBIO {
    public static void main(String[] args) throws Exception{
        int port=8888;
        ServerSocket ss=new ServerSocket(port);
        System.out.println("服务器启动,端口："+port);
        try {
            while (true){
                Socket s = ss.accept();
                new Thread(new TimeHandler(s)).start();
            }
        }finally {
            if (ss != null) {
                ss.close();
                System.out.println("服务器关闭...");
            }
        }
    }
    private static class TimeHandler implements Runnable{
        private Socket socket;
        public TimeHandler(Socket socket){
            this.socket=socket;
        }
        @Override
        public void run() {
            BufferedReader in=null;
            PrintWriter out=null;
            try {
                String content = null;
                String currentTime=null;
                in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out=new PrintWriter(socket.getOutputStream(),true);
                while (true){
                    content=in.readLine();
                    if (content==null) {
                        break;
                    }
                    currentTime=content.equalsIgnoreCase("currentTime")?new Date().toString():"bad request";
                    out.println(currentTime);
                    out.println("服务器返回时间给socket:"+socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (socket != null) {
                    try {
                        socket.close();
                        System.out.println("服务端关闭socket:"+socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
