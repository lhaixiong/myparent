package com.lhx.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedMap;

/**
 * 一、缓冲区（Buffer）：在 Java NIO 中负责数据的存取。缓冲区就是数组。用于存储不同数据类型的数据
 *
 * 根据数据类型不同（boolean 除外），提供了相应类型的缓冲区：
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 *
 * 上述缓冲区的管理方式几乎一致，通过 allocate() 获取缓冲区
 *
 * 二、缓冲区存取数据的两个核心方法：
 * put() : 存入数据到缓冲区中
 * get() : 获取缓冲区中的数据
 *
 * 三、缓冲区中的四个核心属性：
 * capacity : 容量，表示缓冲区中最大存储数据的容量。一旦声明不能改变。
 * limit : 界限，表示缓冲区中可以操作数据的大小。（limit 后数据不能进行读写）
 * position : 位置，表示缓冲区中正在操作数据的位置。
 *
 * mark : 标记，表示记录当前 position 的位置。可以通过 reset() 恢复到 mark 的位置
 *
 * 0 <= mark <= position <= limit <= capacity
 *
 * 四、直接缓冲区与非直接缓冲区：
 * 非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
 * 直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
 */

/**
 * 一、通道（Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 *
 * 二、通道的主要实现类
 * 	java.nio.channels.Channel 接口：
 * 		|--FileChannel
 * 		|--SocketChannel
 * 		|--ServerSocketChannel
 * 		|--DatagramChannel
 *
 * 三、获取通道
 * 1. Java 针对支持通道的类提供了 getChannel() 方法
 * 		本地 IO：
 * 		FileInputStream/FileOutputStream
 * 		RandomAccessFile
 *
 * 		网络IO：
 * 		Socket
 * 		ServerSocket
 * 		DatagramSocket
 *
 * 2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * 3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 *
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 *
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 *
 * 六、字符集：Charset
 * 编码：字符串 -> 字节数组
 * 解码：字节数组  -> 字符串
 *
 */

/**
 * 一、使用 NIO 完成网络通信的三个核心：
 *
 * 1. 通道（Channel）：负责连接
 *
 * 	   java.nio.channels.Channel 接口：
 * 			|--SelectableChannel
 * 				|--SocketChannel
 * 				|--ServerSocketChannel
 * 				|--DatagramChannel(收发udp协议包)
 *
 * 				|--Pipe.SinkChannel
 * 				|--Pipe.SourceChannel
 *
 * 2. 缓冲区（Buffer）：负责数据的存取
 *
 * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
 *
 */

/**
 * 管道(Pipe)
 * Java NIO 管道是2个线程之间的单向数据连接。Pipe有一个source通道和一个sink通道。
 * 数据会被写到sink通道，从source通道读取。
 */
public class NIOTest {
    @Test
    public void tesp(){
        String ss="aA汉子";
        for (byte b : ss.getBytes()) {
            System.out.println(b+":"+ss.getBytes().length);
        }
    }
    @Test
    public void testPipe() throws IOException{
        //1. 获取管道
        Pipe pipe = Pipe.open();

        //2. 将缓冲区中的数据写入管道
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //这里可以放入写数据线程代码中
        Pipe.SinkChannel sinkChannel = pipe.sink();
        buf.put("通过单向管道发送数据".getBytes());
        buf.flip();
        sinkChannel.write(buf);

        //这里可以放入读数据线程代码中
        //3. 读取缓冲区中的数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        buf.flip();
        int len = sourceChannel.read(buf);
        System.out.println(new String(buf.array(), 0, len));

        sourceChannel.close();
        sinkChannel.close();
    }


    @Test
    public void testDataGramClient() throws IOException{
        DatagramChannel dc = DatagramChannel.open();
        dc.configureBlocking(false);
        ByteBuffer buf = ByteBuffer.allocate(1024);
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()){
            String str = scan.next();
            buf.put((new Date().toString() + ":\n" + str).getBytes());
            buf.flip();
            dc.send(buf, new InetSocketAddress("127.0.0.1", 9898));
            buf.clear();
        }
        dc.close();
    }

    @Test
    public void testDataGramServer() throws IOException{
        DatagramChannel dc = DatagramChannel.open();
        dc.configureBlocking(false);
        dc.bind(new InetSocketAddress(9898));
        Selector selector = Selector.open();
        dc.register(selector, SelectionKey.OP_READ);
        while(selector.select() > 0){
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey sk = it.next();
                if(sk.isReadable()){
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    dc.receive(buf);
                    buf.flip();
                    System.out.println(new String(buf.array(), 0, buf.limit()));
                    buf.clear();
                }
            }
            it.remove();
        }
    }

    /*******非阻塞IO开始,重要重要！！！！！！！！**********/
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            System.out.println(scanner.nextLine());
        }
    }
    //为毛在junit Test方法中System.in不生效在main方法中则可以？在stackoverflow上找不了解答[手动捂脸]
    @Test
    public void testScanner(){
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            System.out.println(scanner.nextLine());
        }
    }
    //客户端
    @Test
    public void testNonBlockingClient() throws Exception {
        //1获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        //2设置非阻塞
        sChannel.configureBlocking(false);
        //3发送数据
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(LocalDateTime.now().toString().getBytes());
        buf.flip();
        sChannel.write(buf);
        buf.clear();
        System.out.println("客户端发送数据完毕");

//        Scanner scan = new Scanner(System.in);
//
//        while(scan.hasNext()){
//            String str = scan.next();
//            buf.put((LocalDateTime.now().toString() + "\n" + str).getBytes());
//            buf.flip();
//            sChannel.write(buf);
//            buf.clear();
//        }
        //5关闭通道
        sChannel.close();
    }
    //服务端
    @Test
    public void testNoneBlockingServer() throws Exception {
        //1. 获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        //2. 切换非阻塞模式
        ssChannel.configureBlocking(false);
        //3. 绑定服务端口
        ssChannel.bind(new InetSocketAddress(9898));
        //4. 获取选择器
        Selector selector = Selector.open();
        //5. 将通道注册到选择器上, 并且指定“监听接收事件”
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        //6. 轮询式的获取选择器上已经“准备就绪”的事件
        while (selector.select()>0){
            //7. 获取当前选择器中所有注册的“选择键(已就绪的监听事件)”
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();//这里注意selectedKeys
            while (it.hasNext()){
                SelectionKey sk = it.next();
                //对各个事件的处理
                if(sk.isAcceptable()){//接收就绪
                    System.out.println("sk.isAcceptable()");
                    SocketChannel sChannel= ssChannel.accept();
                    //切换非阻塞模式,将该通道注册到选择器上
                    sChannel.configureBlocking(false);
                    sChannel.register(selector,SelectionKey.OP_READ);
                }else if(sk.isReadable()){//读就绪
//                    System.out.println("sk.isReadable()");
                    SocketChannel sChannel= (SocketChannel) sk.channel();
                    //读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int len=0;
                    while ((len=sChannel.read(buf))!=-1){
                        buf.flip();
                        System.out.println(new String(buf.array(),0,len));
                        buf.clear();
                    }
                }else if(sk.isConnectable()){
                    System.out.println("sk.isConnectable()");
                }else if(sk.isWritable()){
                    System.out.println("sk.isWritable()");
                }else if(sk.isValid()){
                    System.out.println("sk.isValid()");
                }
                // 取消选择键 SelectionKey
                it.remove();
            }

        }

    }
    /*******非阻塞IO结束**********/

    /*******阻塞IO开始**********/
    //客户端  阻塞IO
    @Test
    public void testBlockingClient() throws Exception {
        //获取通道、装载数据、传输数据
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        ByteBuffer buf = ByteBuffer.allocate(1024);
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        while(inChannel.read(buf)!=-1) {
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }
        System.out.println("客户端发送数据完毕,等等服务端返回数据");
        //告诉服务端发送数据完毕了(可以通过shutdownOutput、close、和切换成非阻塞模式，
        // 不然服务端一直等待客户端发送数据过去)
        sChannel.shutdownOutput();
        int len=0;
        //接收服务端的返回消息
        while ((len=sChannel.read(buf))!=-1){
            buf.flip();
            System.out.println(new String(buf.array(),0,len));
            buf.clear();
        }
        inChannel.close();
        sChannel.close();
    }
    //服务端  阻塞IO
    @Test
    public void testBlockingSever() throws Exception {
        //获取通道、接收数据、处理数据
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.bind(new InetSocketAddress(9898));
        SocketChannel sChannel = ssChannel.accept();
        FileChannel outChannel = FileChannel.open(Paths.get("server.jpg"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        ByteBuffer buf = ByteBuffer.allocate(1024);
        while (sChannel.read(buf)!=-1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }
        System.out.println("服务端处理数据完毕");
        //返回消息
        buf.put("服务端处理数据完毕aaaaa".getBytes());
        buf.flip();
        sChannel.write(buf);
        outChannel.close();
        sChannel.close();
        ssChannel.close();
    }
    /*******阻塞IO结束**********/
    //字符集
    @Test
    public void testCharSet() throws Exception{
        Charset cs1 = Charset.forName("GBK");

        //获取编码器
        CharsetEncoder ce = cs1.newEncoder();

        //获取解码器
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("梁冬妙大美女！");
        cBuf.flip();

        //编码
        ByteBuffer bBuf = ce.encode(cBuf);

        for (int i = 0; i < 12; i++) {
            System.out.println(bBuf.get());
        }

        //解码
        bBuf.flip();
        CharBuffer cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());

        System.out.println("------------------------------------------------------");

        Charset cs2 = Charset.forName("GBK");
        bBuf.flip();
        CharBuffer cBuf3 = cs2.decode(bBuf);
        System.out.println(cBuf3.toString());

        SortedMap<String, Charset> map = Charset.availableCharsets();
        System.out.println(map.size());
        for (String key : map.keySet()) {
            System.out.println(key+":"+map.get(key));
        }
    }
    //分散和聚集
    @Test
    public void testScatterAndGather() throws Exception{
        FileChannel inChannel = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("2.txt"), StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        ByteBuffer buf1 = ByteBuffer.allocate(150);
        ByteBuffer buf2 = ByteBuffer.allocate(400);
        ByteBuffer[] bufs={buf1,buf2};
        //3. 分散读取
        inChannel.read(bufs);

        //切换模式
        for (ByteBuffer buf : bufs) {
            buf.flip();
        }
        //4. 聚集写入
        outChannel.write(bufs);
        outChannel.close();
        inChannel.close();
    }
    //通道之间的数据传输(直接缓冲区) 推荐
    @Test
    public void testChannel3() throws IOException{
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("testChannel3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

//		inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }
    //使用直接缓冲区完成文件的复制(内存映射文件) 麻烦，不推荐
    @Test
    public void testChannel2() throws Exception{
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("testChannel2.jpg"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        //内存映射文件
        MappedByteBuffer inMapBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMapBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
        //直接对缓冲区进行数据的读写操作
        byte[] bf=new byte[inMapBuf.limit()];
        inMapBuf.get(bf);
        outMapBuf.put(bf);
        outChannel.close();
        inChannel.close();
        System.out.println("testChannel2 复制文件完毕");
    }
    //利用通道完成文件的复制（非直接缓冲区）
    @Test
    public void testChannel(){
        FileInputStream fis=null;
        FileOutputStream fos=null;
        //①获取通道
        FileChannel inChannel=null;
        FileChannel outChannel=null;
        try {

            fis=new FileInputStream("1.jpg");
            fos=new FileOutputStream("testChannel.jpg");
            inChannel=fis.getChannel();
            outChannel=fos.getChannel();
            //②分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);
            //③将通道中的数据存入缓冲区中
            while (inChannel.read(buf)!=-1){
                buf.flip();//切换读取数据的模式
                //④将缓冲区中的数据写入通道中
                outChannel.write(buf);
                buf.clear();//清空缓冲区
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("复制图片1.jpg完毕");
    }
    @Test
    public void testDirectoryBuffer(){
        ByteBuffer bf = ByteBuffer.allocateDirect(1024);
        System.out.println(bf.isDirect());
    }
    @Test
    public void testMarkAndReset(){
        String str="abcde";
        ByteBuffer bf = ByteBuffer.allocate(256);
        bf.put(str.getBytes());
        bf.flip();
        byte[] dst=new byte[100];
        bf.get(dst,0,2);
        System.out.println(new String(dst));
        bf.mark();
        bf.get(dst,2,2);
        System.out.println(new String(dst));
        bf.reset();
        System.out.println("reset 后position--"+bf.position()+"   limit---"+bf.limit());
        System.out.println(new String(dst));

        if (bf.hasRemaining()) {
            System.out.println("hasRemaining "+bf.remaining());
        }

//        dst=new byte[100];//重新分配dst后，输出cde
        System.out.println("bf.limit()-bf.position()="+(bf.limit()-bf.position()));
        bf.get(dst,4,bf.limit()-bf.position());
        System.out.println(new String(dst));//abcdcde
    }
    @Test
    public void testBuffer(){
        String str="abcde";
        //1. 分配一个指定大小的缓冲区
        ByteBuffer bb = ByteBuffer.allocate(1024);
        System.out.println("-------allocate后---------");
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());

        //2. 利用 put() 存入数据到缓冲区中
        bb.put(str.getBytes());
        System.out.println("-------put后---------");
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());

        //3. 切换读取数据模式
        bb.flip();
        System.out.println("-------flip后---------");
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());

        //4. 利用 get() 读取缓冲区中的数据
        byte[] bytes=new byte[bb.limit()-2];
        System.out.println("-------get后---------");
        bb.get(bytes);
        System.out.println(new String(bytes));
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());

        //5. rewind() : 可重复读
        bb.rewind();
        System.out.println("-------rewind后---------");
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());

        //6. clear() : 清空缓冲区. 但是缓冲区中的数据依然存在，但是处于“被遗忘”状态
        bb.clear();
        System.out.println("-------clear后---------");
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println("clear后各个指针恢复初始状态，但是数据依然存在，处于“被遗忘”状态");
        System.out.println((char)bb.get());
    }
}
