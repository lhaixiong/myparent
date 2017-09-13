package com.lhx.lilinfeng.fake;

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
 * 伪异步bio 时间服务器
 */
public class TimeServerFake {
    public static void main(String[] args) throws Exception{
        int port=8888;
        ServerSocket ss=new ServerSocket(port);
        System.out.println("服务器启动,端口："+port);
        TimeHanderExecutorPool pool=null;
        try {
            //与同步阻碍bio相比，这里限制了服务器的线程数（socket数）,不会导致服务器宕机，但底层仍然是同步阻碍io。
            pool=new TimeHanderExecutorPool(50,5000);
            while (true){
                Socket s = ss.accept();
                pool.execute(new TimeHandler(s));
            }
        }finally {
            if (ss != null) {
                ss.close();
                System.out.println("服务器关闭...");
            }
            if (pool != null) {
                pool.shutDown();
            }
        }
    }
    private static class TimeHanderExecutorPool{
        private ExecutorService executor;
        public TimeHanderExecutorPool(int maxPoolSize,int queueSize){
            this.executor=new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),maxPoolSize,
                    120L,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(queueSize));
        }
        public void execute(Runnable task){
            executor.submit(task);
        }
        public void shutDown(){
            System.out.println("线程池关闭...");
            this.executor.shutdown();
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
