package com.ttstore.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 
 * @ClassName:  RedisService   
 * @Description:RedisService 封装层
 * @author: carlos-y
 * @date:   2016年12月23日 下午9:44:07   
 * @Copyright: 2016 carlos-y. All rights reserved.
 */
@Service
public class RedisService {
	
	@Autowired
	private ShardedJedisPool shardedJedisPool;
	

	private <T> T execute(Function<T,ShardedJedis> fun){

        ShardedJedis shardedJedis = null;
        try {
            // 从连接池中获取到jedis分片对象
            shardedJedis = shardedJedisPool.getResource();
            return fun.callBack(shardedJedis);
         } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != shardedJedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
            return null;

        }
		
	}
	
	/**
	 * 
	 * @Title: set   
	 * @Description: 存值 并设置生存时间（单位：秒）
	 * @param: @param key
	 * @param: @param value
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String set(final String key,final String value,final Integer seconds){
 
            return execute(new Function<String, ShardedJedis>() {

				@Override
				public String callBack(ShardedJedis e) {
					String str =  e.set(key, value);
					e.expire(key, seconds);
					return str;
					
					  
				}
            	
			});

	}
	
	/**
	 * 
	 * @Title: get   
	 * @Description: 取值
	 * @param: @param key
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String get(final String key){
		 
		 return execute(new Function<String, ShardedJedis>() {

				@Override
				public String callBack(ShardedJedis e) {
					
					return e.get(key);
					
					  
				}
         	
			});
	}
	
	/**
	 * 
	 * @Title: del   
	 * @Description: 删除数据
	 * @param: @param key
	 * @param: @return      
	 * @return: Long      
	 * @throws
	 */
	public Long del(final String key){
		 
		 return execute(new Function<Long, ShardedJedis>() {

				@Override
				public Long callBack(ShardedJedis e) {
					
					return e.del(key);
					
					  
				}
        	
			});
	}
	
	/**
	 * 
	 * @Title: exprie   
	 * @Description: 设置生存时间
	 * @param: @param key
	 * @param: @param seconds
	 * @param: @return      
	 * @return: Long      
	 * @throws
	 */
	public Long exprie(final String key, final Integer seconds){
		 
		 return execute(new Function<Long, ShardedJedis>() {

				@Override
				public Long callBack(ShardedJedis e) {
					
					return e.expire(key, seconds);
					
					  
				}
       	
			});
	}
	
}
