package com.lhx.myparent.netty.chapter4;

import io.netty.bootstrap.ServerBootstrap;
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
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Date;

/**
 * 演示tcp粘包问题
 */
public class TimeServer4_1 {
    public static void main(String[] args) throws Exception {
        int port=8080;
        if (args!=null&&args.length>0) {
            try {
                port=Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        System.out.println("服务端启动...");
        new TimeServer4_1().bind(port);
    }


    public void bind(int port) throws Exception{
        //配置服务端的NIO线程组
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChildChannelHandler());

            //绑定端口，同步等待成功
            ChannelFuture channelFuture = bootstrap.bind(port).sync();

            //等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    static class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }
    static class  TimeServerHandler extends ChannelHandlerAdapter{
        private int counter;//修改点
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf= (ByteBuf) msg;
            byte[] req=new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body=new String(req,"utf-8").substring(0,req.length-System.getProperty("line.separator").length());//修改点
            System.out.println("服务端接收到消息》》"+body+",the counter is "+(++counter));//修改点
            String currentTime=null;
            if (body.equalsIgnoreCase("query time order")) {
                currentTime=new Date().toString();
            }else {
                currentTime="Bad order ";
            }
            currentTime+=System.getProperty("line.separator");//修改点
            ByteBuf resp= Unpooled.copiedBuffer(currentTime.getBytes());
            ctx.writeAndFlush(resp);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
