package com.lhx.lilinfeng.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO 时间服务器
 */
public class TimeServerNIO {
    public static void main(String[] args){
        int port=8888;
        try {
            System.out.println("服务器启动...端口为:"+port);
            new Thread(new MultiPlexerTimeserver(port)).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 多路复用器
     */
    private static class MultiPlexerTimeserver implements Runnable{
        private volatile boolean stop=false;
        private ServerSocketChannel ssc;
        private Selector selector;
        public MultiPlexerTimeserver(int port){
            try {
                //打开ServerSocketChannel
                ssc=ServerSocketChannel.open();
                //设置非阻塞
                ssc.configureBlocking(false);
                //绑定端口
                ssc.bind(new InetSocketAddress(port));
                //注册到selector上
                selector = Selector.open();
                ssc.register(selector, SelectionKey.OP_ACCEPT);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        @Override
        public void run() {
            while (!this.stop){
                try {
                    selector.select(1000);//selector每隔1s唤醒一次
                    Set<SelectionKey> keySet = selector.selectedKeys();
                    Iterator<SelectionKey> it = keySet.iterator();
                    //轮询selector
                    SelectionKey key=null;
                    while (it.hasNext()){
                        key = it.next();
                        it.remove();//这里为何要移除？思考一下
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
                } catch (Exception e) {
                    e.printStackTrace();
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

        public void stop(){
            this.stop=true;
        }

        private void handleInput(SelectionKey key) throws Exception {
            if (key.isValid()) {
                System.out.println(key.channel()+"key.isValid()");
                //根据各种状态处理具体业务
                //channel接入就绪
                if (key.isAcceptable()) {
                   handleAccept(key);
                }
                //channel读就绪
                if (key.isReadable()) {
                    handleRead(key);
                }
                //channel写就绪
                if (key.isWritable()) {
                   handleWrite(key);
                }
                //channel连接就绪
                if (key.isConnectable()) {
                    handleConnect(key);
                }
            }
        }

        private void handleAccept(SelectionKey key) throws Exception{
            System.out.println(key.channel()+"key.isAcceptable()");
            ServerSocketChannel sschannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = sschannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector,SelectionKey.OP_READ);
        }
        private void handleRead(SelectionKey key) throws Exception{
            System.out.println(key.channel()+"key.isReadable()");
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer readBuffer=ByteBuffer.allocate(1024);//这里不确定请求有多少字节，暂时分配1024
            int readBytes = channel.read(readBuffer);//，这里存在“读半包"的问题，要解决
            if (readBytes>0) {
                readBuffer.flip();//这里注意
                byte[] dst=new byte[readBuffer.remaining()];//当前readBuffer中有多少内容
                readBuffer.get(dst);
                //得到请求的数据，这里可进行解码、具体业务操作
                String reqContent=new String(dst,"UTF-8");
                System.out.println(System.currentTimeMillis()+"服务器接收到数据:"+reqContent);
                String rsp=reqContent.equalsIgnoreCase("currentTime")?System.currentTimeMillis()+new Date().toString():"bad order";

                //响应，可抽成方法
                ByteBuffer writeBuffer=ByteBuffer.allocate(rsp.getBytes().length);
                writeBuffer.put(rsp.getBytes());
                writeBuffer.flip();
                channel.write(writeBuffer);//writeBuffer.hasRemaining()这里存在“写半包"的问题，要解决
            }else if(readBytes<0){//说明链路已关闭
                //关闭链路
                key.cancel();
                key.channel().close();
            }else {
                //读入0字节，不处理
            }
        }
        private void handleWrite(SelectionKey key) throws Exception{
            System.out.println(key.channel()+"key.isWritable()");
        }
        private void handleConnect(SelectionKey key) throws Exception{
            System.out.println(key.channel()+"key.isConnectable()");
        }
    }
}
