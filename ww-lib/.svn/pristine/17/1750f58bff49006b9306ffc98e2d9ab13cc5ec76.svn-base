package com.zb.core.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtil {

	public static final Logger log = LoggerFactory.getLogger(RedisUtil.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static long incrId(RedisTemplate<String, String> tmp, final String key) {
		return (long) tmp.execute(new RedisCallback() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				Long inc = connection.incr(key.getBytes());
				// log.info("结果 : " + inc);
				return inc;
			}
		});
	}

	private static final byte[] LOCK_VALUE = "l".getBytes();

	/**
	 * redis 实现分布式锁
	 * 
	 * @param tmp
	 *            redisTemplate
	 * @param key
	 *            锁key
	 * @param time
	 *            自动释放时间秒
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Boolean lock(RedisTemplate<String, String> tmp, final String key, final long time) {
		return (boolean) tmp.execute(new RedisCallback() {
			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] bk = key.getBytes();
				Boolean b = false;
				synchronized (RedisUtil.class) {
					b = connection.setNX(bk, LOCK_VALUE);
				}
				connection.expire(bk, time);
				return b;
			}
		});
	}

	public static void unlock(RedisTemplate<String, String> tmp, String key) {
		tmp.delete(key);
	}

}
