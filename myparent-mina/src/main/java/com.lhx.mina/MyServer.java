package com.lhx.mina;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 学习
 * http://blog.csdn.net/w13770269691/article/details/8614584
 */
public class MyServer {
    private static final Logger log= LoggerFactory.getLogger(MyServer.class);
    public static void main(String[] args) {
        IoAcceptor acceptor=new NioSocketAcceptor();

        DefaultIoFilterChainBuilder filterChain = acceptor.getFilterChain();
        LoggingFilter lf = new LoggingFilter();
        lf.setExceptionCaughtLogLevel(LogLevel.NONE);
        filterChain.addLast("logger", lf);
        filterChain.addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

        acceptor.setHandler(new IoHandler() {
            @Override
            public void sessionCreated(IoSession session) throws Exception {
                log.info("sessionCreated..",session.getId());
            }

            @Override
            public void sessionOpened(IoSession session) throws Exception {
                log.info("sessionOpened..");
            }

            @Override
            public void sessionClosed(IoSession session) throws Exception {
                log.info("sessionClosed..");
            }

            @Override
            public void sessionIdle(IoSession session, IdleStatus idleStatus) throws Exception {
                log.info("sessionIdle..");
            }

            @Override
            public void exceptionCaught(IoSession session, Throwable th) throws Exception {
                log.info("exceptionCaught..");
                log.error(th.getMessage(),th);
                session.closeNow();
            }

            @Override
            public void messageReceived(IoSession session, Object msg) throws Exception {
                log.info("messageReceived..");
                log.info(msg.toString());
                session.write(msg);
            }

            @Override
            public void messageSent(IoSession session, Object msg) throws Exception {
                log.info("messageSent.."+msg);
            }

            @Override
            public void inputClosed(IoSession session) throws Exception {
                //log.info("inputClosed..");
            }
        });

        try {
            acceptor.bind(new InetSocketAddress(10000));
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("服务器成功启动，等待连接...");
    }
}
