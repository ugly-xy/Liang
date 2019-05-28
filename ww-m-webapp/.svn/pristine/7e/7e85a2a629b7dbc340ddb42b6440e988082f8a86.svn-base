package com.zb.web.view.m;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zb.common.Constant.ReCode;
import com.zb.common.http.CookieUtil;
import com.zb.core.conf.Config;
import com.zb.core.web.ReMsg;
import com.zb.service.AuthService;

@Controller
public class UserCtl {
	
	@Autowired
	AuthService authService;
	
	@RequestMapping("/login")
	public ModelAndView login(String url) {
		return new ModelAndView("users/login", "url", url);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(String username, String password, String url,
			HttpServletRequest req, HttpServletResponse resq) {

		ReMsg rm = authService.login(username, password, req);
		if (rm.getCode() == ReCode.OK.reCode()) {
			// System.out.println("aaaaa:" + rm.getData().toString() + ","
			// + Config.cur().get("domain"));
			CookieUtil.addCookie("accessToken", rm.getData().toString(), 0,
					"/", Config.cur().get("domain"), resq);
			if (StringUtils.isNotBlank(url) && !url.startsWith("/html/login")) {
				return new ModelAndView("redirect:" + url);
			} else {
				return new ModelAndView("redirect:/index.xhtml");
			}
		}
		return new ModelAndView("users/login", "url", url);
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest req, HttpServletResponse resq) {
		CookieUtil.removeCookie("accessToken", req, resq);
		return new ModelAndView("redirect:/login.xhtml");
	}

}
