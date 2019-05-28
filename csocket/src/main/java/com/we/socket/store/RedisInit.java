package com.we.socket.store;

import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

public class RedisInit {

	private static JdkSerializationRedisSerializer serialization;
	private static RedisMessageListenerContainer redisContainer;
	private static StringRedisTemplate mainRedis = null;
	
	public static JdkSerializationRedisSerializer getSerialization() {
		return serialization;
	}
	public static StringRedisTemplate getMainRedis() {
		return mainRedis;
	}
	
	public static RedisMessageListenerContainer getRedisContainer() {
		return redisContainer;
	}
	
	public static void init(ApplicationContext ctx){
		RedisInit.mainRedis = (StringRedisTemplate) ctx.getBean("mainRedis");
		
		serialization = (JdkSerializationRedisSerializer) ctx.getBean("serialization");
		redisContainer =  (RedisMessageListenerContainer) ctx.getBean("redisContainer");
	}
}
