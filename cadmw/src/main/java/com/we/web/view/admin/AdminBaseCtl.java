package com.we.web.view.admin;

import java.util.HashMap;
import java.util.Map;

import com.we.core.conf.Config;
import com.we.core.web.interceptor.OAuth2SimpleInterceptor;


public class AdminBaseCtl {

	public long getUid() {
		Object obj = null;
		if (null!=OAuth2SimpleInterceptor.getSession()) {
			obj = OAuth2SimpleInterceptor.getSession().get("_id");
		}
		if (null != obj)
			return Long.parseLong(obj.toString());
		return 0l;
	}
	
	public Map<String, Object> getImgMap() {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("imgDomain", Config.cur().getImgDomain());
		return res;
	}

}
