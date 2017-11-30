package com.lhx.blog.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer2 {
    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;
    private Destination destination;

    public Producer2() throws JMSException{
        this.factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        this.connection = factory.createConnection();
        this.connection.start();
        this.session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        this.messageProducer = session.createProducer(null);
        this.destination = session.createQueue("first");
    }

    public void send() throws JMSException {
        MapMessage mapMsg1 = session.createMapMessage();
        mapMsg1.setString("name","张三");
        mapMsg1.setInt("age",33);
        //只有xxxProperty才对messageSelector起作用
        mapMsg1.setStringProperty("color","33");
        mapMsg1.setIntProperty("sal",3333);

        MapMessage mapMsg2 = session.createMapMessage();
        mapMsg2.setString("name","李四");
        mapMsg2.setInt("age",44);
        //只有xxxProperty才对messageSelector起作用
        mapMsg2.setStringProperty("color","44");
        mapMsg2.setIntProperty("sal",4444);

        MapMessage mapMsg3 = session.createMapMessage();
        mapMsg3.setString("name","王五");
        mapMsg3.setInt("age",55);
        //只有xxxProperty才对messageSelector起作用
        mapMsg3.setStringProperty("color","55");
        mapMsg3.setIntProperty("sal",5555);

        MapMessage mapMsg4 = session.createMapMessage();
        mapMsg4.setString("name","赵六");
        mapMsg4.setInt("age",66);
        //只有xxxProperty才对messageSelector起作用
        mapMsg4.setStringProperty("color","66");
        mapMsg4.setIntProperty("sal",6666);

        //目的地、消息、是否持久化、优先级(0-9)、ttl存活时间
        this.messageProducer.send(destination,mapMsg1,DeliveryMode.NON_PERSISTENT,2,1000*60*10L);
        this.messageProducer.send(destination,mapMsg2,DeliveryMode.NON_PERSISTENT,5,1000*60*10L);
        this.messageProducer.send(destination,mapMsg3,DeliveryMode.NON_PERSISTENT,9,1000*60*10L);
        this.messageProducer.send(destination,mapMsg4,DeliveryMode.NON_PERSISTENT,3,1000*60*10L);

    }

    public void closeConnection() throws JMSException {
        if (this.connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws JMSException{
        Producer2 producer2=new Producer2();
        producer2.send();
        producer2.closeConnection();
    }
}
