package com.zb.common.http;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static Cookie getCookie(String cookieName,
			final HttpServletRequest request) throws IllegalArgumentException {
		if (null == request)
			throw new IllegalArgumentException("request is null");
		Cookie[] cookies = request.getCookies();
		if (null != cookies)
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					return cookie;
				}
			}
		return null;
	}

	public static String getCookieValue(String cookieName,
			final HttpServletRequest httpRequest) {
		Cookie cookie = getCookie(cookieName, httpRequest);
		if (null != cookie) {
			return cookie.getValue();
		}
		return null;
	}

	public static void addCookie(Cookie cookie, HttpServletResponse response)
			throws IllegalArgumentException {
		if (response == null)
			throw new IllegalArgumentException("Response is null");
		response.addCookie(cookie);
	}

	public static void addCookie(String cookieName, String cookieValue,
			int expiry, String path, HttpServletResponse httpResponse) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		if (expiry > 0) {
			cookie.setMaxAge(expiry);
		}
		if (path != null) {
			cookie.setPath(path);
		}
		addCookie(cookie, httpResponse);
	}

	public static void addCookie(String cookieName, String cookieValue,
			int expiry, String path, String domain,
			HttpServletResponse httpResponse) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		if (expiry > 0) {
			cookie.setMaxAge(expiry);
		}
		if (path != null) {
			cookie.setPath(path);
		}
		if (domain != null) {
			cookie.setDomain(domain);
		}
		addCookie(cookie, httpResponse);
	}

	public static void addCookie(String cookieName, String cookieValue,
			HttpServletResponse httpResponse) {
		addCookie(cookieName, cookieValue, 0, null, httpResponse);
	}

	public static void addCookie(String cookieName, String cookieValue,
			int expiry, HttpServletResponse httpResponse) {
		addCookie(cookieName, cookieValue, expiry, null, httpResponse);
	}

	public static void removeCookie(String cookieName,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		Cookie cookie = getCookie(cookieName, httpRequest);
		if (cookie != null) {
			cookie.setValue(null);
			cookie.setMaxAge(0);
			addCookie(cookie, httpResponse);
		}
	}
	
	public static void removeCookie(String cookieName,String path,String domain,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		Cookie cookie = getCookie(cookieName, httpRequest);
		if (cookie != null) {
			cookie.setValue(null);
			cookie.setPath(path);
			cookie.setDomain(domain);
			cookie.setMaxAge(0);
			addCookie(cookie, httpResponse);
		}
	}
}
