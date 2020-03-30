package com.ccq.service.impl;


import com.ccq.service.RedisCacheStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Service("redisCacheStorage")
public class RedisCacheStorageImpl implements RedisCacheStorage {

	//日志记录
    private Logger log= LoggerFactory.getLogger(RedisCacheStorageImpl.class);
 
    /**
     * 默认过时时间
     */
    

	@Autowired
	private JedisPool jedisPool;
	
	
	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.set(key, value);
		jedis.close();
		return string;
	}

	@Override
	public String hget(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.hget(hkey, key);
		jedis.close();
		return string;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(hkey, key, value);
		jedis.close();
		return result;
	}

	@Override
	public long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public long expire(String key, int second) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, second);
		jedis.close();
		return result;
	}

	@Override
	public long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}
	
	 @Override
	    public long del(String key) {
	        Jedis jedis = jedisPool.getResource();
	        Long result = jedis.del(key);
	        jedis.close();
	        return result;
	    }
	 
	    @Override
	    public long hdel(String hkey, String key) {
	        Jedis jedis = jedisPool.getResource();
	        Long result = jedis.hdel(hkey, key);
	        jedis.close();
	        return result;
	    }
	
	
 
    


}