package com.ccq.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisUtil {
    /**
     * 日志记录
     */
    private static final Logger LOG = LoggerFactory.getLogger(RedisUtil.class);
    /**
     * redis 连接池，这里jedisPool我们再之前spring配置中配置好了，交给spring管理，这里可以自动注入
     */
    @Autowired
    private JedisPool jedisPool;
 
    public void setPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
    /**
     * 获取jedis
     * @return
     */
    public Jedis getResource(){
        Jedis jedis =null;
        try {
            jedis =jedisPool.getResource();
        } catch (Exception e) {
            LOG.info("can't  get  the redis resource");
        }
        return jedis;
    }
    /**
     * 关闭连接
     * @param jedis
     */
    public void disconnect(Jedis jedis){
        jedis.disconnect();
    }
    /**
     * 将jedis 返还连接池
     * @param jedis
     */
    public void returnResource(Jedis jedis){
        if(null != jedis){
            try {
                jedisPool.returnResource(jedis);
            } catch (Exception e) {
                LOG.info("can't return jedis to jedisPool");
            }
        }
    }
    /**
     * 无法返还jedispool，释放jedis客户端对象，
     * @param jedis
     */
    public void brokenResource(Jedis jedis){
        if (jedis!=null) {
            try {
                jedisPool.returnBrokenResource(jedis);
            } catch (Exception e) {
                LOG.info("can't release jedis Object");
            }
        }
    }
}