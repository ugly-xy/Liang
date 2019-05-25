package com.liang.web.interceptor;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class OAuth2SimpleInterceptor extends HandlerInterceptorAdapter {

	static final Logger log = LoggerFactory.getLogger(OAuth2SimpleInterceptor.class);


	private static final ThreadLocal<Map<String, Object>> sessionHolder = new ThreadLocal<Map<String, Object>>();


	static String TOKEN = "token";

	protected static ThreadLocal<Map<String, Object>> getSessionholder() {
		return sessionHolder;
	}

	public static void setSession(Map<String, Object> session) {
		sessionHolder.set(session);
	}

	public static Map<String, Object> getSession() {
		Map<String, Object> map = sessionHolder.get();
		return map;
	}
	
	public Object getSessionData(String key) {
		return sessionHolder.get().get(key);
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		sessionHolder.remove();
	}

	private List<String> verifiedUris;

	public void setVerifiedUris(List<String> verifiedUris) {
		this.verifiedUris = verifiedUris;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException, IOException {
		
		String cururi = request.getRequestURI();
		boolean valid = false;
		if (verifiedUris != null) {// 过滤需要验证列表
			for (String uri : verifiedUris) {
				if (cururi.startsWith(uri)) {// 当前uri在验证列表内
					valid = true;
					break;
				}
			}
		}
		if(valid) {
			response.sendRedirect("/");
			return false;
		}
		
		return true;
	}


}