package com.ccq.test;


import com.ccq.activemq.servce.ProducerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= "classpath:spring/spring-producer.xml")
public class MQProducterService {

    @Autowired
    ProducerService producerService;

    @Test
    public void  testMQ(){
        for (int i = 0; i < 5; i++) {
            producerService.sendMessage("testaaa" + i);
        }
    }

}
