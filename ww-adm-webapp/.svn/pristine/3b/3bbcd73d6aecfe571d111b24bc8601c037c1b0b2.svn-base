package com.zb.web.interceptor;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.common.Constant.ReCode;
import com.zb.common.http.CookieUtil;
import com.zb.common.utils.MapUtil;
import com.zb.core.web.ReMsg;
import com.zb.core.web.interceptor.OAuth2SimpleInterceptor;
import com.zb.models.permission.UserPerms;
import com.zb.service.PermissionService;

public class AdmOAuth2SimpleInterceptor extends OAuth2SimpleInterceptor {

	static final Logger log = LoggerFactory.getLogger(AdmOAuth2SimpleInterceptor.class);

	@Autowired
	PermissionService permissionService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException, IOException {
		String token = parseToken(request); // 通过url获取地址中token信息
		if (request.getRequestURI().startsWith("/admin/login")) {
			return true;
		}

		if (token != null) {
			ReMsg rm = getAuthService().getAuthUser(request, token);
			if (ReCode.OK.reCode() == rm.getCode()) {
				Map data = (Map) rm.getData();
				if (MapUtil.getInt(data, "role", 1) < 2) {
					response.sendRedirect(
							"/admin/login.xhtml?url=" + URLEncoder.encode(request.getRequestURI(), "utf-8"));
					return false;
				}
				getSessionholder().set(data);
				String uri = request.getRequestURI();
				if (!uri.endsWith(".xhtml")) {
					uri = uri + ".xhtml";
				}
				if (permissionService.countPermission(0, uri, null, 0) > 0) {
					UserPerms up = permissionService.getUserPerms(MapUtil.getLong(data, "_id"));
					if (null == up || !up.contains(uri)) {
						response.sendRedirect(
								"/admin/index.xhtml?msg="+URLEncoder.encode("没有权限访问", "utf-8"));
						return false;
						//log.error("no perm:"+uri);
					}
				}else{
					log.error(uri);
				}
				return true;
			} else {
				CookieUtil.removeCookie("accessToken", request, response);
				response.sendRedirect("/admin/login.xhtml?url=" + URLEncoder.encode(request.getRequestURI(), "utf-8"));
				return false;
			}
		} else {
			response.sendRedirect("/admin/login.xhtml?url=" + URLEncoder.encode(request.getRequestURI(), "utf-8"));
			return false;
		}
	}
}