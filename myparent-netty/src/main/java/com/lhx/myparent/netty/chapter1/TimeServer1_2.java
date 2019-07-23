package com.lhx.myparent.netty.chapter1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 伪异步I/O,阻塞、同步，只是吞吐量大一点
 */
public class TimeServer1_2 {
    public static void main(String[] args) {
        int port=8080;
        if (args!=null&&args.length>0) {
            try {
                port=Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        ServerSocket ss=null;
        try {
            System.out.println("TimeServer2 服务器启动...");
            ss=new ServerSocket(port);
            TimeServerHandlerExecutePool executePool=new TimeServerHandlerExecutePool(50,500);
            while (true){
                Socket socket = ss.accept();
                executePool.execute(new TimeServerHander(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (ss != null) {
                System.out.println("TimeServer2 服务器关闭...");
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    static class TimeServerHander implements Runnable{
        private Socket socket;
        public TimeServerHander(Socket socket) {
            this.socket=socket;
        }

        @Override
        public void run() {
            BufferedReader in=null;
            PrintWriter out=null;
            try {
                in=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                out=new PrintWriter(this.socket.getOutputStream(),true);
                 String currentTime=null;
                 String body=null;
                 while (true){
                     body=in.readLine();
                     if (body==null) {
                         break;
                     }
                     System.out.println(Thread.currentThread().getName()+"接收到客户端消息>>"+body);
                     if (body.equalsIgnoreCase("query time order")) {
                         currentTime=new Date().toString();
                     }else {
                         currentTime="bad order";
                     }
                     out.println(currentTime);
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
    static class TimeServerHandlerExecutePool{
        private ExecutorService executorService;

        public TimeServerHandlerExecutePool(int maxPoolSize,int queueSize) {
            this.executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                    maxPoolSize,
                    120L,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(queueSize)
                    );
        }
        public void execute(Runnable task){
            executorService.execute(task);
        }
    }
}
