package com.lhx.myparent.netty.chapter2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * AIO
 */
public class TimeServer2_2 {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new Thread(new AsyncTimeServerHandler(port)).start();
    }
    static class AsyncTimeServerHandler implements Runnable{
        private int port;
        private AsynchronousServerSocketChannel asynchronousServerSocketChannel;
        private CountDownLatch countDownLatch;

        public AsyncTimeServerHandler(int port) {
            this.port = port;
           try {
               this.asynchronousServerSocketChannel =AsynchronousServerSocketChannel.open();
               this.asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
               System.out.println("服务器启动完毕...");
           }catch (Exception e){
               e.printStackTrace();
           }
        }

        @Override
        public void run() {
            countDownLatch=new CountDownLatch(1);
            doAccept();
            try {
                countDownLatch.await();
            }catch (Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }

        private void doAccept() {
            this.asynchronousServerSocketChannel.accept(this,new AcceptCompleteHandler());
        }

    }

    static class AcceptCompleteHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncTimeServerHandler>{

        @Override
        public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
            attachment.asynchronousServerSocketChannel.accept(attachment,this);

            ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
            result.read(byteBuffer,byteBuffer,new ReadCompletionHandler(result));
        }

        @Override
        public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
            exc.printStackTrace();
            attachment.countDownLatch.countDown();
        }
    }

    static class ReadCompletionHandler implements CompletionHandler<Integer,ByteBuffer>{
        private AsynchronousSocketChannel asynchronousSocketChannel;

        public ReadCompletionHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
            if (this.asynchronousSocketChannel==null) {
                this.asynchronousSocketChannel = asynchronousSocketChannel;
            }
        }

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            attachment.flip();
            byte[] req=new byte[attachment.remaining()];
            attachment.get(req);
            try {
                String body=new String(req,"utf-8");
                System.out.println("服务端接收到消息....."+body);
                String currentTime=null;
                if (body.equalsIgnoreCase("query time order")) {
                    currentTime=new Date().toString();
                }else {
                    currentTime="Bad order";
                }
                doWrite(currentTime);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        private void doWrite(String currentTime) {
            if (currentTime != null && currentTime.length()>0) {
                ByteBuffer writeBuffer=ByteBuffer.allocate(1024);
                writeBuffer.put(currentTime.getBytes());
                writeBuffer.flip();
                this.asynchronousSocketChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        //如果没有发送完成，继续发送
                        if (attachment.hasRemaining()) {
                            asynchronousSocketChannel.write(writeBuffer,writeBuffer,this);
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        try {
                            asynchronousSocketChannel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try {
                asynchronousSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
