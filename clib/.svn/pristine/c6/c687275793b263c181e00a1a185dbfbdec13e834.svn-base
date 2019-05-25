package com.we.common.utils;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T2TUtil {

	static final Logger log = LoggerFactory.getLogger(T2TUtil.class);

	public static Long str2Long(String str) {
		return str2Long(str, null);
	}

	public static Long str2Long(String str, Long def) {
		if (StringUtils.isNotBlank(str)) {
			try {
				return Long.parseLong(str);
			} catch (Exception ex) {
				log.error("Object to Long err:", ex);
			}
		}
		return def;
	}

	public static Long obj2Long(Object obj) {
		return obj2Long(obj, null);
	}

	public static Long obj2Long(Object obj, Long def) {
		if (null != obj) {
			return str2Long(obj.toString(), def);
		}
		return def;
	}

	public static Integer str2Int(String str) {
		return str2Int(str, null);
	}

	public static Integer str2Int(String str, Integer def) {
		if (StringUtils.isNotBlank(str)) {
			try {
				return Integer.parseInt(str);
			} catch (Exception ex) {
				log.error("Object to Long err:", ex);
			}
		}
		return def;
	}

	public static Integer obj2Int(Object obj) {
		return obj2Int(obj, null);
	}

	public static Integer obj2Int(Object obj, Integer def) {
		if (null != obj) {
			return str2Int(obj.toString(), def);
		}
		return def;
	}

	public static Short str2Short(String str) {
		return str2Short(str, null);
	}

	public static Short str2Short(String str, Short def) {
		if (StringUtils.isNotBlank(str)) {
			try {
				return Short.parseShort(str);
			} catch (Exception ex) {
				log.error("Object to Long err:", ex);
			}
		}
		return def;
	}

	public static Short obj2Short(Object obj) {
		return obj2Short(obj, null);
	}

	public static Short obj2Short(Object obj, Short def) {
		if (null != obj) {
			return str2Short(obj.toString(), def);
		}
		return def;
	}

	public static Double str2Double(String str) {
		return str2Double(str, null);
	}

	public static Double str2Double(String str, Double def) {
		if (StringUtils.isNotBlank(str)) {
			try {
				return Double.parseDouble(str);
			} catch (Exception ex) {
				log.error("Object to Long err:", ex);
			}
		}
		return def;
	}

	public static Double obj2Double(Object obj) {
		return obj2Double(obj, null);
	}

	public static Double obj2Double(Object obj, Double def) {
		if (null != obj) {
			return str2Double(obj.toString(), def);
		}
		return def;
	}

	public static Boolean obj2Bool(Object obj) {
		return obj2Bool(obj, null);
	}

	public static Boolean obj2Bool(Object obj, Boolean def) {
		if (null != obj) {
			return str2Bool(obj.toString(), def);
		}
		return def;
	}

	public static Boolean str2Bool(String str) {
		return str2Bool(str, null);
	}

	public static Boolean str2Bool(String str, Boolean def) {
		if (StringUtils.isNotBlank(str)) {
			try {
				return Boolean.parseBoolean(str);
			} catch (Exception ex) {
				log.error("Object to Long err:", ex);
			}
		}
		return def;
	}

	public static Float str2Float(String str) {
		return str2Float(str, null);
	}

	public static Float str2Float(String str, Float def) {
		if (StringUtils.isNotBlank(str)) {
			try {
				return Float.parseFloat(str);
			} catch (Exception ex) {
				log.error("Object to Long err:", ex);
			}
		}
		return def;
	}

	public static Float obj2Float(Object obj) {
		return obj2Float(obj, null);
	}

	public static Float obj2Float(Object obj, Float def) {
		if (null != obj) {
			return str2Float(obj.toString(), def);
		}
		return def;
	}

	/** 四舍五入至整数位 */
	public static Long half_round(Double dob) {
		return Long.parseLong(new BigDecimal(dob).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
	}

	public static void main(String[] args) {

		System.out.println(T2TUtil.obj2Long(111, null));
		System.out.println(T2TUtil.obj2Long(111L, null));
		System.out.println(T2TUtil.obj2Short("1", (short) 1));
		System.out.println(T2TUtil.obj2Long("a"));
		System.out.println(T2TUtil.obj2Long(1.1));

	}
}
