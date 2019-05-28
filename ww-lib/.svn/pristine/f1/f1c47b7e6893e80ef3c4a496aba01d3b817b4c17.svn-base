package com.zb.core.redis;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import com.zb.common.utils.JSONUtil;


public class RedisRecvListener implements MessageListener {

	public static final Logger log = LoggerFactory
			.getLogger(RedisRecvListener.class);

	@Override
	public void onMessage(Message message, byte[] pattern) {
		// FIXME socket 接受 redis消息
		String channel = new String(message.getChannel());
		String msgStr = message.toString();
		Map<String, Object> m = (Map<String, Object>) JSONUtil
				.jsonToMap(msgStr);
		System.out.println("channal:" + channel + ",msgType:"
				+ m.get("msgType"));

		// HandlerFactory.handler(null, null, m);
		System.out.println("channal:" + channel + ",msg:" + msgStr);
	}

}
