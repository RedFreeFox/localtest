package com.ccq.test;


import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="classpath:spring/spring-consumer.xml")
public class MQConsumerService {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-consumer.xml");
    }
}
