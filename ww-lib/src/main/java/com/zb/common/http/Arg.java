package com.zb.common.http;

import java.util.HashMap;
import java.util.Map;

import com.zb.common.utils.JSONUtil;

public class Arg {

	Map<String, String> params = new HashMap<String, String>();

	public Arg() {

	}

	public Arg(String key, String value) {
		params.put(key, value);
	}

	public Arg append(String key, String value) {
		params.put(key, value);
		return this;
	}

	public Map<String, String> getArgs() {
		return params;
	}

	public String getJson() {
		return JSONUtil.beanToJson(params);
	}

}
