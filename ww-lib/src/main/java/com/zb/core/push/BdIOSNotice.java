package com.zb.core.push;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BdIOSNotice extends PushMsg {

//	static final Logger log = LoggerFactory.getLogger(BdIOSNotice.class);
//	Map aps = new HashMap<>();
//
//	public BdIOSNotice() {
//		super.devType = DevType.IOS;
//		super.msgType = MsgType.NOTICE;
//	}
//
//	public BdIOSNotice alert(String alert) {
//		aps.put("alert", alert);
//		return this;
//	}
//
//	/**
//	 * //可选
//	 * @param sound
//	 * @return
//	 */
//	public BdIOSNotice sound(String sound) {
//		aps.put("sound", sound);
//		return this;
//	}
//
//	/**
//	 * //可选
//	 * @param badge
//	 * @return
//	 */
//	public BdIOSNotice badge(int badge) {
//		aps.put("badge", badge);
//		return this;
//	}
//
//	public BdIOSNotice addCustom(String key, String value) {
//		root.put(key, value);
//		return this;
//	}
//
//	public String toString() {
//		ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			root.put("aps", aps);
//			return objectMapper.writeValueAsString(root);
//		} catch (JsonProcessingException e) {
//			log.error("JsonErr:", e);
//		}
//		return null;
//	}
//
//	public static void main(String[] args) {
//
//	}
}
