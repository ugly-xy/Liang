package com.zb.common.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapUtil {

	static final Logger log = LoggerFactory.getLogger(MapUtil.class);

	public static Long getLong(Map map, String key) {
		return getLong(map, key, null);
	}

	public static Long getLong(Map map, String key, Long def) {
		if (map != null && map.containsKey(key)) {
			return T2TUtil.obj2Long(map.get(key), def);
		}
		return def;
	}

	public static Integer getInt(Map map, String key) {
		return getInt(map, key, null);
	}

	public static Integer getInt(Map map, String key, Integer def) {
		if (map != null && map.containsKey(key)) {
			return T2TUtil.obj2Int(map.get(key), def);
		}
		return def;
	}

	public static Short getShort(Map map, String key) {
		return getShort(map, key, null);
	}

	public static Short getShort(Map map, String key, Short def) {
		if (map != null && map.containsKey(key)) {
			return T2TUtil.obj2Short(map.get(key), def);
		}
		return def;
	}

	public static String getStr(Map map, String key) {
		return getStr(map, key, null);
	}

	public static String getStr(Map map, String key, String def) {
		if (map != null && map.containsKey(key)) {
			return map.get(key) == null ? def : map.get(key).toString();
		}
		return def;
	}

	public static Float getFloat(Map map, String key) {
		return getFloat(map, key, null);
	}

	public static Float getFloat(Map map, String key, Float def) {
		if (map != null && map.containsKey(key)) {
			return T2TUtil.obj2Float(map.get(key), def);
		}
		return def;
	}

	public static Double getDouble(Map map, String key) {
		return getDouble(map, key, null);
	}

	public static Double getDouble(Map map, String key, Double def) {
		if (map != null && map.containsKey(key)) {
			return T2TUtil.obj2Double(map.get(key), def);
		}
		return def;
	}

	public static void main(String[] args) {

	}
}
