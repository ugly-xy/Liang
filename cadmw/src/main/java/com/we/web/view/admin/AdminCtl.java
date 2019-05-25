package com.we.web.view.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.we.common.Constant.ReCode;
import com.we.common.http.CookieUtil;
import com.we.core.conf.Config;
import com.we.core.web.ReMsg;
import com.we.service.AuthService;

@Controller
@RequestMapping("/admin")
public class AdminCtl {

	@Autowired
	AuthService authService;

	@RequestMapping("/index")
	public ModelAndView index(String msg) {
		return new ModelAndView("admin/index","msg",msg);
	}

	@RequestMapping("/login")
	public ModelAndView login(String url,String msg) {
		Map<String,String> ret = new HashMap<String,String>();
		ret.put("url", url);
		ret.put("msg", msg);
		return new ModelAndView("admin/login", ret);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(String username, String password, String url,
			HttpServletRequest req, HttpServletResponse resq) {
		ReMsg rm = authService.login(username, password, req);
		if (rm.getCode() == ReCode.OK.reCode()) {
			if (rm.getData() instanceof String) {
				CookieUtil.addCookie("accessToken", (String) rm.getData(), 0,
						"/", Config.cur().get("domain"), resq);
			} else {
				Map<String, String> res = (Map<String, String>) rm.getData();
				CookieUtil.addCookie("accessToken", res.get("token"), 0, "/",
						Config.cur().get("domain"), resq);
			}
			if (StringUtils.isNotBlank(url) && !url.startsWith("/admin/login")) {
				return new ModelAndView("redirect:" + url);
			} else {
				return new ModelAndView("redirect:/admin/index.xhtml");
			}
		}
		Map<String,String> ret = new HashMap<String,String>();
		ret.put("url", url);
		ret.put("msg", rm.getMsg());
		return new ModelAndView("admin/login", ret);
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest req, HttpServletResponse resq) {
		CookieUtil.removeCookie("accessToken", req, resq);
		return new ModelAndView("redirect:/admin/login.xhtml");
	}

}
