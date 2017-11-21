package com.lhx.blog.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    public static void main(String[] args) throws Exception {
        //1.建立ConnectionFactory工厂对象，需要填入用户名、密码以及ActiveMQ地址，默认端口为tcp://localhost:61616
        ConnectionFactory factory=new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER
                ,ActiveMQConnectionFactory.DEFAULT_PASSWORD,"tcp://localhost:61616");
        //2.通过ConnectionFactory工厂对象创建Connection连接，并调用Connection对象的start方法开启连接，默认为关闭状态
        Connection connection = factory.createConnection();
        connection.start();
        //3.通过Connection对象创建Session会话（上下文环境对象），用于接收和发送消息，参数一：是否开启事务，参数二：签收模式，一般设置为自动签收
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        //4.通过Session对象创建Destination对象，用来指定生产消息和消费消息来源的对象，在PTP中，Destination被称为Queue（队列），
        //在Publish/Subscribe模式，Destination被称为Topic（主题）。程序中可以有多个Queue和Topic
        Destination destinationQueque=session.createQueue("myQueueqabc");//这里队列名字必须要和生产者一致
        //5.通过Session对象创建消息的发送和接收对象（生产者和消费者）MessageProducer和MessageConsumer
        MessageConsumer messageConsumer=session.createConsumer(destinationQueque);
        //6.使用MessageProducer的setDeliveryMode方法设置持久化特性或非持久化。

        while (true){//循环的监听，一有消息就处理
            //该方法会阻塞，直到接收到消息，还有receive(long l)阻塞指定的时间，receiveNoWait()不阻塞
            TextMessage msg = (TextMessage) messageConsumer.receive();
            System.out.println("接收到：" + msg.getText());
            if ("4".equals(msg.getText())) {
                break;
            }
        }
        //8.关闭Connection
        if (connection != null) {
            connection.close();
        }
    }
}
