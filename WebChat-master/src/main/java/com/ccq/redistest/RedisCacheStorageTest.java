package com.ccq.redistest;

import com.ccq.service.RedisCache;
import com.ccq.service.RedisCacheStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring*.xml"})
public class RedisCacheStorageTest {
 
    @Autowired
    private RedisCacheStorage redisCacheStorage;
    @Autowired
    private RedisCache<String, User> redisCache;
 
    
    public void testSetGet() throws Exception {
       System.out.print("开始执行测试");
       redisCacheStorage.set("Access", "Hellow");
       String aa=redisCacheStorage.get("Access");
       System.err.println("666"+aa);   
    }
    
    @Test
    public void testOne(){
    	System.err.println("start...........");
    	User user=new User("sysadm","Vsec1234");
    	redisCache.set("User", user);
    	User user2=redisCache.get("User", new User());
    	System.err.println("username"+"--->"+user2.getUsername());
    	System.err.println("password"+"--->"+user2.getPassword());
    	
    }
}