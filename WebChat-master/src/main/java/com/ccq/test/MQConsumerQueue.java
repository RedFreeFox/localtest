package com.ccq.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MQConsumerQueue {

    @Test
    public void TestMQConsumerQueue() throws Exception{
        //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://47.107.80.121:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Queue queue = session.createQueue("test-queue");
        //6、使用会话对象创建生产者对象
        MessageConsumer consumer = session.createConsumer(queue);
        //7、向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                // TODO Auto-generated method stub
                if(message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //8、程序等待接收用户消息
        System.in.read();
        //9、关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

    /*public void test2(){
        ConnectionFactory connectionFactory;
        Connection connection;
        Session session;
        Destination destination;
        MessageProducer producer;
        MessageConsumer consumer;
        Message message;
        boolean useTransaction = false;
        try {
            Context ctx = new InitialContext();
            connectionFactory = (ConnectionFactory) ctx.lookup("ConnectionFactoryName");
            //使用ActiveMQ时：connectionFactory = new ActiveMQConnectionFactory(user, password, getOptimizeBrokerUrl(broker));
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("TEST.QUEUE");
            //生产者发送消息
            producer = session.createProducer(destination);
            message = session.createTextMessage("this is a test");

            //消费者同步接收
            consumer = session.createConsumer(destination);
            message = (TextMessage) consumer.receive(1000);
            System.out.println("Received message: " + message);
            //消费者异步接收
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message != null) {
                        doMessageEvent(message);
                    }
                }
            });
        } catch (JMSException e) {
               e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            producer.close();
            session.close();
            connection.close();
        }
    }*/

}
