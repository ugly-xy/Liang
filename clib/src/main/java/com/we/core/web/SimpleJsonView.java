package com.we.core.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.we.common.ParamKey;
import com.we.common.utils.JSONUtil;

public class SimpleJsonView implements View {
	private static final SimpleJsonView instance = new SimpleJsonView();
	static final String jsonType = "text/plain;charset=utf-8";

	public String getContentType() {
		return "text/plain;charset=utf-8";
	}

	public void render(Map<String, ?> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String json = toJson(model);
		String callback = request.getParameter(ParamKey.In.callback);
		if ((null != callback) && (callback.length() > 0)) {
			rennderJson(callback + '(' + json + ')',
					"application/x-javascript;charset=utf-8", response);
			return;
		}
		rennderJson(json, response);
	}

	protected String toJson(Map<String, ?> model) {
		return JSONUtil.beanToJson(model);
	}

	public static void rennderJson(String json, HttpServletResponse response)
			throws IOException {
		rennderJson(json, "application/json;charset=UTF-8", response);
	}

	public static void rennderJson(String json, String contentType,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType(contentType);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	public static ModelAndView asJson(Map o) {
		return new ModelAndView(instance, o);
	}
}
