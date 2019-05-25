package com.we.common.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohu.blackword.JianceManager;

public class PinYinUtil {

	static final Logger log = LoggerFactory.getLogger(PinYinUtil.class);

	public static String toPinyin(String str) {
		return JianceManager.getInstance().getPinyinmanager()
				.getPinYinbString(str);
	}

	public static String toPinyinUp(String str) {
		return JianceManager.getInstance().getPinyinmanager()
				.getPinYinbString(str).toUpperCase();
	}

	public static String toPinyinFirstUp(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		String s = JianceManager.getInstance().getPinyinmanager()
				.getPinYinbString(str);
		return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0)))
				.append(s.substring(1)).toString();
	}

	public static String toPinyinAllFirstUp(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		char[] ca = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		boolean isChinese = true;
		for (char ch : ca) {
			if (JianceManager.getInstance().getPinyinmanager().isChinese(ch)) {
				String s = JianceManager.getInstance().getPinyinmanager()
						.getPinYinbyChar(ch);
				sb.append(Character.toUpperCase(s.charAt(0))).append(
						s.substring(1));
				isChinese = true;
			} else {
				if (isChinese) {
					if (Character.isLowerCase(ch)) {
						sb.append(Character.toUpperCase(ch));
					} else {
						sb.append(ch);
					}
				} else {
					if (Character.isUpperCase(ch)) {
						sb.append(Character.toLowerCase(ch));
					} else {
						sb.append(ch);
					}
				}
				isChinese = false;
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(PinYinUtil.toPinyin("sha逼投资"));
		System.out.println(PinYinUtil.toPinyinUp("sha逼投资"));
		System.out.println(PinYinUtil.toPinyinFirstUp("sha逼投资"));
		System.out.println(PinYinUtil.toPinyinAllFirstUp("sha逼Sss投dDD资DDD"));
	}
}
