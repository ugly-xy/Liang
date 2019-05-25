package com.zb.socket.model;

import java.util.HashMap;
import java.util.Map;

import com.zb.common.utils.MapUtil;

public class MapBody {

	Map<String, Object> data = new HashMap<String, Object>();

	public MapBody() {

	}
	
	public boolean contains(String key) {
		return this.data.containsKey(key);
	}

	public MapBody(final String key, final Object value) {
		data.put(key, value);
	}

	public MapBody(final int shid, final String key, final Object value) {
		data.put(SocketHandler.HANDLER_NAME, shid);
		data.put(key, value);
	}

	public MapBody(final int shid) {
		data.put(SocketHandler.HANDLER_NAME, shid);
	}

	public MapBody(Map<String, Object> map) {
		data = map;
	}

	public MapBody append(String key, Object value) {
		data.put(key, value);
		return this;
	}
	
	public MapBody remove(String key) {
		data.remove(key);
		return this;
	}

	public String stringValue(String key) {
		return MapUtil.getStr(data, key);
	}

	public Long longValue(String key) {
		return MapUtil.getLong(data, key);
	}

	public Integer intValue(String key) {
		return MapUtil.getInt(data, key);
	}

	public Short shortValue(String key) {
		return MapUtil.getShort(data, key);
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
