package com.we.socket.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.we.common.utils.JSONUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

public class JsonMsg implements Serializable {
	
	public static final String LOGIN = "login";
	public static final String ALL = "chatAll";
	public static final String P2P = "chatP2P";
	
	public static final String BACK = "back";
	public static final String TIP = "tip";
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 877757175183055061L;
	private long id;
	//private String clazz;
	private String method;
	private Map<String,Object> data;

	public JsonMsg() {
		
	}
	
	public JsonMsg(Long id,String method,Map<String,Object> data) {
		this.id = id;
		this.method = method;
		this.data = data;
	}
	
	
	public void append(String key,Object val) {
		if(this.data==null) {
			this.data = new HashMap<String,Object>();
		}
		this.data.put(key, val);
	}
	
	public <T> T getDataVal(String key,Class<T> clazz) {
		if(this.data==null) {
			return null;
		}
		return (T) this.data.get(key);
	}
	
	public long getDataVal(String key) {
		if(this.data==null) {
			return 0l;
		}
		Long ll=Long.parseLong(String.valueOf(this.data.get(key)));
		if(ll!=null) {
			return ll.longValue();
		}
		return 0l;
	}

	public String toString() {
		return JSONUtil.beanToJson(this);
	}

	public long getId() {
		return id;
	}


	public Object getData() {
		return data;
	}

	public String getMethod() {
		return method;
	}


	public TextWebSocketFrame toTWSFrame() {
		return new TextWebSocketFrame(this.toString());
	}
	

	public BinaryWebSocketFrame toBWSFrame() {
		//System.out.println("ret====="+this.toString());
		ByteBuf buf = Unpooled.copiedBuffer(this.toString(), CharsetUtil.UTF_8);
		BinaryWebSocketFrame bws = new BinaryWebSocketFrame(buf);
		return bws;
	}

}
