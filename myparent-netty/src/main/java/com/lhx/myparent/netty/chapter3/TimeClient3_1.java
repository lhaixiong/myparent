package com.lhx.myparent.netty.chapter3;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.logging.Logger;

public class TimeClient3_1 {
    public static void main(String[] args) throws Exception {
        int port=8080;
        if (args!=null&&args.length>0) {
            try {
                port=Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        System.out.println("客户端启动...");
        new TimeClient3_1().connect("127.0.0.1",port);
    }

    public void connect(String host, int port) throws Exception {
        //配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            //发起异步连接操作
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            //等待客户端链路关闭
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }
    }
    static class TimeClientHandler extends ChannelHandlerAdapter{
        private static final Logger LOGGER=Logger.getLogger(TimeClientHandler.class.getName());
        private final ByteBuf firstMessage;
        public TimeClientHandler(){
            byte[] req="query time order".getBytes();
            firstMessage= Unpooled.buffer(req.length);
            firstMessage.writeBytes(req);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(firstMessage);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
           ByteBuf buf= (ByteBuf) msg;
           byte[] req=new byte[buf.readableBytes()];
           buf.readBytes(req);
            System.out.println("客户端收到消息>>>"+new String(req,"utf-8"));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            //释放资源
            LOGGER.warning("Unexpected Exception from downstream :"+cause.getMessage());
            ctx.close();
        }
    }
}
