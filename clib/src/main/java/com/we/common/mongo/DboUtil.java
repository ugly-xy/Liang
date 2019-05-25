package com.we.common.mongo;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.we.common.utils.JSONUtil;

public class DboUtil {

	public static String getString(DBObject dbo, String field) {
		if (null != dbo) {
			Object obj = dbo.get(field);
			if (null != obj) {
				return obj.toString();
			}
		}
		return null;
	}

	public static <T> T get(DBObject dbo, String field, Class<T> c) {
		if (null != dbo) {
			Object obj = dbo.get(field);
			if (null != obj) {
				return (T) obj;
			}
		}
		return null;
	}

	public static Boolean getBool(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			return Boolean.parseBoolean(s);
		}
		return null;
	}

	public static Integer getInt(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			return Integer.parseInt(s);
		}
		return null;
	}

	public static Integer tryGetInt(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			double temp = Double.valueOf(s);
			return (int) temp;
		}
		return null;
	}

	public static Long getLong(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			return Long.parseLong(s);
		}
		return null;
	}

	public static Byte getByte(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			return Byte.parseByte(s);
		}
		return null;
	}

	public static Short getShort(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			return Short.parseShort(s);
		}
		return null;
	}

	public static Float getFloat(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			return Float.parseFloat(s);
		}
		return null;
	}

	public static Double getDouble(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			return Double.parseDouble(s);
		}
		return null;
	}

	public static <T> T toBean(DBObject dbo, Class<T> c) {
		if (null == dbo) {
			return null;
		}
		return JSONUtil.jsonToBean(dbo.toString(), c);
	}

	public static DBObject beanToDBO(Object o) {
		return (DBObject) JSON.parse(JSONUtil.beanToJson(o));
	}

}
