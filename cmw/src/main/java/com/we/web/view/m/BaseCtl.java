package com.we.web.view.m;

import java.util.HashMap;
import java.util.Map;

import com.we.common.utils.MapUtil;
import com.we.core.conf.Config;
import com.we.core.web.interceptor.OAuth2SimpleInterceptor;


public class BaseCtl {

	public long getUid() {
		Object obj = null;
		if (null!=OAuth2SimpleInterceptor.getSession()) {
			obj = OAuth2SimpleInterceptor.getSession().get("_id");
		}
		if (null != obj)
			return Long.parseLong(obj.toString());
		return 0l;
	}
	
	public Map<String, Object> getCommonMap() {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("imgDomain", Config.cur().getImgDomain());
		Map<String,Object> user = OAuth2SimpleInterceptor.getSession();
		if (null!=user) {
			Long uid = MapUtil.getLong(user, "_id");
			String username = MapUtil.getStr(user, "username");
			String phone = MapUtil.getStr(user, "phone");
			if(null !=uid) {
				res.put("uid", uid);
				res.put("username", username);
				res.put("phone", phone);
			}
		}
		return res;
	}
	public Map<String, Object> getTokenUser() {
		return OAuth2SimpleInterceptor.getSession();
	}
}
