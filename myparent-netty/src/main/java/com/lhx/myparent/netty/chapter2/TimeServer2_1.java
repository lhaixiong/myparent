package com.lhx.myparent.netty.chapter2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO
 */
public class TimeServer2_1 {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        MultiplexerTimeSever timeSever = new MultiplexerTimeSever(port);
        new Thread(timeSever).start();
    }

    static class MultiplexerTimeSever implements Runnable {
        private Selector selector;
        private ServerSocketChannel ssc;
        private volatile boolean stop;

        /**
         * 初始化多路复用器，绑定监听端口
         * @param port
         */
        public MultiplexerTimeSever(int port) {
            try {
                this.selector = Selector.open();
                this.ssc = ServerSocketChannel.open();
                this.ssc.configureBlocking(false);
                this.ssc.socket().bind(new InetSocketAddress(port), 1024);
                this.ssc.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("MultiplexerTimeSever 服务器启动。。。");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        @Override
        public void run() {
            while (!stop) {
                try {
                    selector.select(1000);
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    SelectionKey key = null;
                    while (iterator.hasNext()) {
                        key = iterator.next();
                        iterator.remove();
                        try {
                            handle(key);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (key != null) {
                                key.cancel();
                                if (key.channel() != null) {
                                    key.channel().close();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //多路复用器关闭，它上面注册的channel、pipe等资源自动关闭。
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handle(SelectionKey key) throws Exception {
            if (key.isValid()) {
                if (key.isAcceptable()) {
                    System.out.println("key.isAcceptable()....");
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(key.selector(), SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    System.out.println("key.isReadable()....");
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int readBytes = socketChannel.read(byteBuffer);
                    if (readBytes > 0) {
                        byteBuffer.flip();
                        byte[] contentBytes = new byte[byteBuffer.remaining()];
                        byteBuffer.get(contentBytes);
                        String req = new String(contentBytes, "UTF-8");
                        String currentTime = null;
                        if (req.equalsIgnoreCase("query time order")) {
                            currentTime = new Date().toString();
                        } else {
                            currentTime = "bad order";
                        }
                        doWrite(socketChannel, currentTime);
                    } else if (readBytes < 0) {
                        //对链路关闭
                        key.cancel();
                        socketChannel.close();
                    } else {
                        //读取0字节,不做处理
                    }
                } else if (key.isWritable()) {
                    System.out.println("key.isWritable()....");
                } else if (key.isConnectable()) {
                    System.out.println("key.isConnectable()....");
                }
            }
        }

        private void doWrite(SocketChannel socketChannel, String resp) throws Exception {
            if (resp != null && resp.length() > 0) {
                byte[] bytes = resp.getBytes();
                ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
                byteBuffer.put(bytes);
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                if (!byteBuffer.hasRemaining()) {
                    System.out.println("服务端发送消息完毕...");
                }
            }
        }

        public void stop() {
            this.stop = true;
        }
    }
}
