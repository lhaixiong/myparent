package com.lhx.lilinfeng.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientNIO {
    public static void main(String[] args) {
        String ip="127.0.0.1";
        int port=8888;
        try {
            System.out.println("客户端启动...");
            new Thread(new TimeClientHandler(ip,port)).start();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    private static class TimeClientHandler implements Runnable{
        private String ip;
        private int port;
        private SocketChannel sc;
        private Selector selector;
        private volatile boolean stop=false;

        public TimeClientHandler(String ip,int port){
            try {
                this.ip=ip==null?"127.0.0.1":ip;
                this.port=port;
                this.sc=SocketChannel.open();
                sc.configureBlocking(false);
                selector=Selector.open();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }


        @Override
        public void run() {
            try {
                tryConnectAndSend();
            }catch (Exception e){
                e.printStackTrace();
                System.exit(1);
            }
            while (!this.stop){
                try {
                    selector.select(1000);
                    Set<SelectionKey> keySet = selector.selectedKeys();
                    Iterator<SelectionKey> it = keySet.iterator();
                    SelectionKey key=null;
                    while (it.hasNext()){
                        key = it.next();
                        it.remove();
                        try {
                            handleInput(key);
                        }catch (Exception e){
                            e.printStackTrace();
                            if (key != null) {
                                key.cancel();
                                if (key.channel() != null) {
                                    key.channel().close();
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            //关闭多路复用器
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //处理响应信息
        private void handleInput(SelectionKey key) throws Exception{
            if (key.isValid()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                //判断是否连接成功
                if (key.isConnectable()) {
                    if (socketChannel.finishConnect()) {
                        socketChannel.register(selector,SelectionKey.OP_READ);
                        System.out.println("客户端handleInput 完成连接");
                        doWrite(socketChannel,"handleInput");
                    }else {
                        //连接失败，进程退出
                        System.exit(1);
                    }
                }

                //读就绪状态
                if (key.isReadable()) {
                    ByteBuffer readBuffer=ByteBuffer.allocate(1024);
                    int readBytes = socketChannel.read(readBuffer);
                    if(readBytes>0){
                        readBuffer.flip();
                        //接收从服务器返回的数据
                        byte[] dst=new byte[readBuffer.remaining()];
                        readBuffer.get(dst);
                        //这里可进行具体的解码、业务工作
                        String rsp=new String(dst,"UTF-8");
                        System.out.println("客户端收到从服务器返回的数据"+rsp);
                        this.stop();
                    }else if(readBytes<0){
                        //链路关闭
                        key.cancel();
                        key.channel().close();
                    }else {
                        //读到0字节
                    }
                }
            }
        }

        //发送数据到服务器
        private void doWrite(SocketChannel socketChannel,String type) throws Exception{
            ByteBuffer writeBuffer=ByteBuffer.allocate(1024);
            writeBuffer.put("currentTime".getBytes());
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
            System.out.println(System.currentTimeMillis()+"客户端发送数据>>calledBy>>>"+type);
        }

        private void tryConnectAndSend() throws Exception{
            boolean connected = sc.connect(new InetSocketAddress(ip, port));
            if(connected){
                System.out.println("tryConnectAndSend 连接完成");//这句话没有输出,因为没连接完成么？
                sc.register(selector,SelectionKey.OP_READ);
                doWrite(sc,"tryConnectAndSend");
            }else {
                sc.register(selector,SelectionKey.OP_CONNECT);
            }
        }

        public void stop(){
            this.stop=true;
        }
    }
}
