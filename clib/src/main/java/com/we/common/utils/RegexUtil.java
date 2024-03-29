package com.we.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.we.common.Constant.EncodeCountry;

public class RegexUtil {

	/** 验证是否是手机号 */
	public static boolean isPhone(String encode, String phone) {
		if (encode.equals(EncodeCountry.CHINA.getEncode())) {
			return isPhoneChina(phone);
		} else if (encode.equals(EncodeCountry.SOUTH_KOREA.getEncode())) {
			return isPhoneKorea(phone);
		}
		return false;
	}

	public static boolean isPhoneChina(String phone) {
		return isMatches("^1[3|4|5|7|8]\\d{9}$", phone);
	}

	/** 韩国手机号 010 011 017 019开头 总计十一位 老号码十位 */
	public static boolean isPhoneKorea(String phone) {
		return isMatches("^01\\d{9}$", phone) || isMatches("^10\\d{8}$", phone)||isMatches("^\\d{8}$", phone);
	}

	public static boolean isEmail(String email) {
		return isMatches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*", email);
	}

	public static boolean isUrl(String url) {
		return isMatches("[a-zA-z]+://[^\\s]*", url);
	}

	public static boolean isZipCode(String zipCode) {
		return isMatches("[1-9]\\d{5}(?!\\d)", zipCode);
	}

	public static boolean isTel(String tel) {
		return isMatches("\\d{3}-\\d{8}|\\d{4}-\\d{7}", tel);
	}

	public static boolean isIp(String ip) {
		return isMatches("^([1-9]|[1-9]\\d|1\\d{2}|2[0-1]\\d|22[0-3])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}$",
				ip);
	}

	// 中文，数字，字母，2位以上
	public static boolean isUsername(String username) {
		// return isMatches("^\\w\\w{5,20}", username);
		return isMatches("^[a-zA-Z0-9\u4e00-\u9fa5]{2,16}$", username);
	}

	//
	public static boolean isNickname(String nickname) {
		if (nickname.length() > 0 && nickname.length() < 9)
			return true;
		return false;
	}

	// 中文，数字，字母，2位以上
	public static boolean isPassword(String password) {
		return isMatches("\\S{6,20}", password);
	}

	// 以字母开头，长度在start~end之间，只能包含字符、数字和下划线。
	public static boolean limitStr(String str, int start, int end) {
		return isMatches("^[a-zA-Z]\\w{" + (start - 1) + "," + (end - 1) + "}$", str);
	}

	// 验证中文
	public static boolean validCn(String str) {
		return isMatches("^[\u4e00-\u9fa5]{0,}$", str);
	}

	public static boolean isMatches(String regex, String input) {
		if (StringUtils.isBlank(input)) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

	public static String getDomain(String url) {
		Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		matcher.find();
		System.out.println(matcher.group());
		return matcher.group();
	}

	/**
	 * 去掉所有html元素
	 * 
	 * @param input
	 * @param length
	 * @return
	 */
	public static String splitAndFilterString(String input) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		String str = input.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", " ").replaceAll("</[a-zA-Z]+[1-9]?>", "");
		return str;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
		return pattern.matcher(str).matches();
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	public static boolean isNumber(String str) {
		return isMatches("^[0-9]+$",str);
	}

	public static void main(String[] args) {
		// String input =
		// "<a href=\"http://my.iteye.com\"
		// title=\"这个很好\">不是吗<span>似的啊</span></a> <div class=\"quick_menu\"
		// style=\"display:none;\"> <input type='btn' name='az' />";
		// System.out.println(splitAndFilterString(input));
		System.out.println(RegexUtil.isIp("1.1.1.1"));
		System.out.println(RegexUtil.isIp("1.12.1.251"));
		System.out.println(RegexUtil.isIp("1.0.1.1"));
		System.out.println(RegexUtil.isIp("127.1.1.1"));
		System.out.println(RegexUtil.isIp("192.1.1.1"));
		System.out.println(RegexUtil.isIp("91.1.1.261"));
		// String url = "http://www.linuxidc.com/entry/4545/0/";
		// RegexUtil.getDomain(url);
		// url = "http://aaa.bbb.ddd.dd.linuxidc.com/entry/4545/0/";
		// RegexUtil.getDomain(url);
		// url = "http://localhost/entry/4545/0/";
		// RegexUtil.getDomain(url);
	}

}
