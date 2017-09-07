package com.lhx.mina;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MyClient {
    private static final Logger log= LoggerFactory.getLogger(MyClient.class);

    public static void main(String[] args) {
        log.info("客户端启动...");
        IoConnector connector=new NioSocketConnector();

        DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();
        filterChain.addLast("logger", new LoggingFilter());
        filterChain.addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

        connector.setHandler(new IoHandler() {
            @Override
            public void sessionCreated(IoSession session) throws Exception {
                log.info("client sessionCreated..."+session.getId());
            }

            @Override
            public void sessionOpened(IoSession session) throws Exception {
                log.info("client sessionOpened...");
                for (int i = 0; i < 10; i++) {
                    session.write("Hello user_" + i);
                }
                session.write("Bye");
            }

            @Override
            public void sessionClosed(IoSession session) throws Exception {
                log.info("client sessionOpened...");
            }

            @Override
            public void sessionIdle(IoSession session, IdleStatus idleStatus) throws Exception {
                log.info("client sessionIdle...");
            }

            @Override
            public void exceptionCaught(IoSession session, Throwable th) throws Exception {
                log.info("client exceptionCaught...");
                log.error(th.getMessage(), th);
                session.close(true);
            }

            @Override
            public void messageReceived(IoSession session, Object msg) throws Exception {
                log.info("client messageReceived...");
                if (msg.toString().equalsIgnoreCase("Bye")) {
                    session.close(true);
                }
            }

            @Override
            public void messageSent(IoSession session, Object msg) throws Exception {
                log.info("client messageSent...");
            }

            @Override
            public void inputClosed(IoSession session) throws Exception {
                log.info("client inputClosed...");
            }
        });

        IoSession session=null;
        try {
            ConnectFuture future = connector.connect(new InetSocketAddress("localhost", 10000));
            future.awaitUninterruptibly();
            session=future.getSession();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose();
        log.info("客户端关闭....");
    }
}
