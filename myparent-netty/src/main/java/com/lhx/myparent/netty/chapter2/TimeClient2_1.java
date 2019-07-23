package com.lhx.myparent.netty.chapter2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClient2_1 {
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

            new Thread(new TimeClientHandle("127.0.0.1",port)).start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    static class TimeClientHandle implements Runnable {
        private String host;
        private int port;
        private Selector selector;
        private SocketChannel socketChannel;
        private volatile boolean stop;

        public TimeClientHandle(String host, int port) {
            this.host = host;
            this.port = port;
            try {
                selector = Selector.open();
                socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(false);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        @Override
        public void run() {
            try {
                doConnect();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
            try {
                while (!stop) {
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
                }
            } catch (Exception e) {
                e.printStackTrace();
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

        private void handle(SelectionKey key) {
            try {
                if (key.isValid()) {
                    if (key.isConnectable()) {
                        System.out.println("key.isConnectable()...");
                        if (socketChannel.finishConnect()) {
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            doWrite();
                        } else {
                            //连接失败，退出
                            System.exit(1);
                        }
                    }

                    if (key.isReadable()) {
                        System.out.println("key.isReadable()...");
                        doRead(key);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void doRead(SelectionKey key) throws Exception {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer resp = ByteBuffer.allocate(1024);
            int readNum = socketChannel.read(resp);
            if (readNum > 0) {
                resp.flip();
                byte[] content = new byte[resp.remaining()];
                resp.get(content);
                System.out.println("客户端接收到消息>>>" + new String(content, "utf-8"));
                stop();
            } else if (readNum < 0) {
                //对链路关闭
                key.cancel();
                socketChannel.close();
            } else {
                //读取到0字节，不处理
            }
        }

        private void doWrite() throws Exception {
            ByteBuffer req = ByteBuffer.allocate(1024);
            req.put("query time order".getBytes());
            req.flip();
            socketChannel.write(req);
            if (!req.hasRemaining()) {
                System.out.println("客户端发送消息完毕...");
            }
        }

        private void doConnect()  throws Exception{
            boolean connect = socketChannel.connect(new InetSocketAddress(host, port));
            if (connect) {
                socketChannel.register(selector,SelectionKey.OP_READ);
                doWrite();
            }else {
                socketChannel.register(selector,SelectionKey.OP_CONNECT);
            }
        }

        public void stop() {
            this.stop = true;
        }
    }
}
