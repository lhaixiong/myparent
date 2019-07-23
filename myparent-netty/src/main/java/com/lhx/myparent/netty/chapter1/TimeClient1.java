package com.lhx.myparent.netty.chapter1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TimeClient1 {
    public static void main(String[] args) {
        int port=8080;
        if (args!=null&&args.length>0) {
            try {
                port=Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        BufferedReader in=null;
        PrintWriter out=null;
        Socket socket=null;
        try {
            System.out.println("客户端启动..");
          socket=new Socket(InetAddress.getLocalHost(),port);
          out=new PrintWriter(socket.getOutputStream(),true);
          out.println("query time order");
          in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
          while (true){
              String resp = in.readLine();
              if (resp == null) {
                  break;
              }
              System.out.println("客户端收到服务器消息>>>"+resp);
          }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (socket != null) {
                    socket.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
