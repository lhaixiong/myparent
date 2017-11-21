package com.lhx.blog.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer2 {
    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageConsumer messageConsumer;
    private Destination destination;
    private static final String SELECTOR_COLOR="color = '44'";
    private static final String SELECTOR_SAL="sal > 4000";

    public Consumer2() throws JMSException {
        this.factory=new ActiveMQConnectionFactory("tcp://localhost:61616");
        this.connection=factory.createConnection();
        connection.start();
        this.session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        this.destination=session.createQueue("first");
        this.messageConsumer=session.createConsumer(destination, SELECTOR_COLOR);
    }
    public void receive(){
        try {
            this.messageConsumer.setMessageListener(new Listener());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    class Listener implements MessageListener{

        @Override
        public void onMessage(Message message) {
            if(message instanceof TextMessage) {

            }
            if(message instanceof MapMessage) {
                System.out.println(message.toString());
                try {
                    String name=((MapMessage) message).getString("name");
                    int age=((MapMessage) message).getInt("age");
                    System.out.println("name>>>>"+name);
                    System.out.println("age>>>>"+age);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws JMSException {
        Consumer2 consumer2=new Consumer2();
        consumer2.receive();
    }
}
