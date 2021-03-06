package com.we.core.web.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.we.common.ParamKey;
import com.we.common.Constant.ReCode;
import com.we.common.http.CookieUtil;
import com.we.common.http.HttpRequestUtil;
import com.we.common.utils.JSONUtil;
import com.we.core.web.ReMsg;
import com.we.core.web.SimpleJsonView;
import com.we.service.AuthService;

public class OAuth2SimpleInterceptor extends HandlerInterceptorAdapter {

	static final Logger log = LoggerFactory.getLogger(OAuth2SimpleInterceptor.class);

	@Autowired
	private AuthService authService;

	private static final ThreadLocal<Map<String, Object>> sessionHolder = new ThreadLocal<Map<String, Object>>();

	static final Integer ROBOT_MAX = 1023956;

	static String TOKEN = "token";

	protected AuthService getAuthService() {
		return authService;
	}

	protected static ThreadLocal<Map<String, Object>> getSessionholder() {
		return sessionHolder;
	}

	public static void setSession(Map<String, String> session) {
		sessionHolder.set((Map) session);
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

	private List<String> excludedUris;
	private List<String> verifiedUris;

	public void setExcludedUris(List<String> excludedUris) {
		this.excludedUris = excludedUris;
	}

	public void setVerifiedUris(List<String> verifiedUris) {
		this.verifiedUris = verifiedUris;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException, IOException {
		
		String cururi = request.getRequestURI();
		if(cururi!=null&&cururi.startsWith("/app")) {
			return getPreHandleResult(cururi,request,response);
		}
		if (excludedUris != null) {// 先过滤无需验证列表
			for (String uri : excludedUris) {
				if (cururi.startsWith(uri)) {
					// System.out.println("1");
					return true;
				}
			}
		}
		boolean cookie = false;
		String token = request.getParameter(TOKEN);// 获取token信息
		if (token == null) {
			token = parseHeaderToken(request);// 解析头信息
			cookie = true;
		}
		boolean valid = false;
		if (verifiedUris != null) {// 过滤需要验证列表
			for (String uri : verifiedUris) {
				if (cururi.startsWith(uri)) {// 当前uri在验证列表内
					valid = true;
					break;
				}
			}
		}

		if (token != null) {
			ReMsg rm = authService.getAuthUser(request, token);
			if (ReCode.OK.reCode() == rm.getCode()) {
				sessionHolder.set((Map) rm.getData());
				// System.out.println("2");
				return true;
			} else {
				if (valid) {
					return result(request, response, cururi, cookie, rm);
				}
			}
		} else {
			if (valid) {
				return result(request, response, cururi, cookie, new ReMsg(ReCode.NOT_AUTHORIZED));
			}
		}

		// System.out.println("5");
		return true;
	}

	private boolean result(HttpServletRequest request, HttpServletResponse response, String cururi, boolean cookie,
			ReMsg rm) throws ServletException, IOException, UnsupportedEncodingException {
		if (cookie) {
			CookieUtil.removeCookie("accessToken", request, response);
			if (HttpRequestUtil.isAjaxRequest(request)) {
				handleNotAuthorized(request, response, rm);
				return false;
			}
			response.sendRedirect("/login.xhtml?url=" + URLEncoder.encode(cururi, "utf-8"));
			// System.out.println("3");
			return false;
		}
		handleNotAuthorized(request, response, rm);
		// System.out.println("4");
		return false;
	}
	
	/**
	 * 封装状态信息，返回数据
	 * 
	 * @param request
	 * @param response
	 * @param json
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void handleNotAuthorized(HttpServletRequest request, HttpServletResponse response, ReMsg rm)
			throws ServletException, IOException {
		String json = JSONUtil.beanToJson(rm);
		String callback = request.getParameter(ParamKey.In.callback);
		if (StringUtils.isNotBlank(callback)) {
			json = callback + '(' + json + ')';
		}
		SimpleJsonView.rennderJson(json, response);
	}
	
	/**
	 * 
	 * @param cururi
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean getPreHandleResult(String cururi,HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
		boolean cookie = false;
		String token = parseToken(request);// 获取token信息
		boolean valid = false;
		if (verifiedUris != null) {// 过滤需要验证列表
			for (String uri : verifiedUris) {
				if (cururi.startsWith(uri)) {// 当前uri在验证列表内
					valid = true;
					break;
				}
			}
		}		
		if(null!=token) {
			ReMsg rm = getAuthService().getAuthUser(request, token);
			if(ReCode.OK.reCode() == rm.getCode()) {
				setSession((Map<String, String>) rm.getData());
				return true;
			}else {
				if (valid) {
					return jsonResult(request, response, cookie, rm);
				}
			}
		}else {
			if (valid) {
				return jsonResult(request, response, cookie, new ReMsg(ReCode.NOT_AUTHORIZED));
			}
		}
		return true;
		
	}
	
	private boolean jsonResult(HttpServletRequest request, HttpServletResponse response, boolean cookie,
			ReMsg rm) throws ServletException, IOException, UnsupportedEncodingException {
		CookieUtil.removeCookie("accessToken", request, response);
		handleNotAuthorized(request, response, rm);
		return false;
	}

	/**
	 * 解析token，通过url地址获取token
	 * 
	 * @param request
	 * @return
	 */
	public static String parseToken(HttpServletRequest request) {
		String token = request.getParameter(TOKEN);// 获取token信息
		if (token == null) {
			token = parseHeaderToken(request);// 解析头信息
			if(token==null) {
				token= StringUtils.defaultIfEmpty(request.getHeader("accessToken"), null) ;
			}
		}
		return token;
	}

	/**
	 * Parse the OAuth header parameters. The parameters will be oauth-decoded.
	 *
	 * @param request
	 *            The request.
	 * @return The parsed parameters, or null if no OAuth authorization header was
	 *         supplied.
	 */
	static String parseHeaderToken(HttpServletRequest request) {
		return CookieUtil.getCookieValue("accessToken", request);
	}

}