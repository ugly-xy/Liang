package com.we.socket.store;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import com.we.common.utils.JSONUtil;
import com.we.socket.model.JsonMsg;
import com.we.socket.server.websocket.WebSocketChannelMap;

//import com.weibo.core.logback.LogbackUtils;
//import com.weibo.socket.ChatMsgHandler;

public class RedisRecvListener implements MessageListener {

	public static final Logger log = LoggerFactory.getLogger(RedisRecvListener.class);

	public static final String ALL = "c:a";
	public static final String ROOM = "c:r:*";
	public static final String USER = "c:u:*";
	public static final String ROOM_USER = "c:r:u:*";
	public static final String SESSION = "c:s";

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String json = new String(message.getBody(), Charset.forName("utf-8"));
		log.error(json);
		TextWebSocketFrame twsf = new TextWebSocketFrame(json);
		String strp = new String(pattern);
		JsonMsg m = JSONUtil.jsonToBean(json, JsonMsg.class);
		if (USER.equals(strp)) {
			HashSet<Channel> cs = WebSocketChannelMap.getChannel(m.getDataVal("toid"));
			for(Channel c :cs) {
				c.writeAndFlush(m.toTWSFrame());
			}

		} else if (ALL.equals(strp)) {
			Iterator<Channel> chs = WebSocketChannelMap.getAllChannel().iterator();
			while (chs.hasNext()) {
				Channel c = chs.next();
				c.writeAndFlush(m.toTWSFrame());
			}
		}
	}
}
