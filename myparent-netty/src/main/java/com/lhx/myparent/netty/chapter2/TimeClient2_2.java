package com.lhx.myparent.netty.chapter2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class TimeClient2_2 {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.println("客户端启动..");

            new Thread(new TimeClientHandler("127.0.0.1",port)).start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
    static class TimeClientHandler implements Runnable,CompletionHandler<Void,TimeClientHandler>{
        private String host;
        private int port;
        private AsynchronousSocketChannel asynchronousSocketChannel;
        private CountDownLatch countDownLatch;

        public TimeClientHandler(String host, int port) {
            this.host = host;
            this.port = port;
            try {
                asynchronousSocketChannel=AsynchronousSocketChannel.open();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            countDownLatch=new CountDownLatch(1);
            asynchronousSocketChannel.connect(new InetSocketAddress(host,port),this,this);

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                asynchronousSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void completed(Void result, TimeClientHandler attachment) {
            byte[] req="query time order".getBytes();
            ByteBuffer writeBuffer=ByteBuffer.allocate(req.length);
            writeBuffer.put(req);
            writeBuffer.flip();

            asynchronousSocketChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if (attachment.hasRemaining()) {
                        asynchronousSocketChannel.write(attachment,attachment,this);
                    }else {
                        ByteBuffer readBuffer=ByteBuffer.allocate(1024);
                        asynchronousSocketChannel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                            @Override
                            public void completed(Integer result, ByteBuffer attachment) {
                                try {
                                    attachment.flip();
                                    byte[] body=new byte[attachment.remaining()];
                                    attachment.get(body);
                                    String content=new String(body,"utf-8");
                                    System.out.println("客户端收到消息..."+content);
                                    countDownLatch.countDown();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void failed(Throwable exc, ByteBuffer attachment) {
                                try {
                                    asynchronousSocketChannel.close();
                                    countDownLatch.countDown();
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
                        countDownLatch.countDown();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void failed(Throwable exc, TimeClientHandler attachment) {
            try {
                asynchronousSocketChannel.close();
                countDownLatch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
