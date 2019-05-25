package com.we.socket.server.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashSet;

import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.we.common.redis.RK;
import com.we.socket.model.JsonMsg;
import com.we.socket.store.DBInit;
import com.we.socket.store.RedisInit;

public abstract class WebSocketCommandHandler  {

	public HashSet<Channel> getChannel(final long uid) {
		if (uid > 0) {
			return WebSocketChannelMap.getChannel(uid);
		}
		return null;
	}

	public void regUser(final Channel c,final Long uid) {
		if (uid > 0) {
			WebSocketChannelMap.addUserInfo(uid, c);
		}
	}


	public long getUid(final Channel c) {
		return WebSocketChannelMap.getUid(c);
	}

	public abstract void handler(ChannelHandlerContext ctx, JsonMsg msg);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public long incrId(String key) {
		return (long) getChatRedis().execute(new RedisCallback() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.incr(key.getBytes());
			}
		});
	}

	public long incrMsgId() {
		return incrId(RK.imMsgId());
	}
	
	public MongoTemplate getChatDb() {
		return DBInit.getMainDb();
	}

	public StringRedisTemplate getMainRedis() {
		return RedisInit.getMainRedis();
	}

	public StringRedisTemplate getChatRedis() {
		return RedisInit.getMainRedis();
	}

	public Long getUid(String token) {
		Long uid = 0L;
		if (token != null) {
			String strUid = getMainRedis().opsForValue().get(
					RK.accessToken(token));
			if (strUid != null) {
				try {
					uid = Long.parseLong(strUid);
				} catch (Exception e) {
				}
			}
		}
		return uid;
	}
	
//	public User getUser(Long uid) {
//		
//		return uid;
//	}
}
