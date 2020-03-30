package com.ccq.activemq.producter;

import com.ccq.activemq.servce.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
@Service
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    JmsTemplate jmsTemplate;

    //@Resource(name = "queueDestination")   //一对一   即队列模式
    @Resource(name = "topicDestination")     //发布订阅  即主题模式
    private Destination queueDestination;

    public void sendMessage(final String message) {
        jmsTemplate.send(queueDestination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(message);
                System.out.println("发送消息 = [" + textMessage.getText() + "]");
                return textMessage;
            }
        });

    }
}
