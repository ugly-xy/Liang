package com.zb.core.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class PageDirective implements TemplateDirectiveModel {

	static final Logger log = LoggerFactory.getLogger(PageDirective.class);

	public static final String IMGPATH = "/upload";

	@SuppressWarnings("deprecation")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] arg2,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		HttpServletRequest request = null;
		try {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes();
			if (attr != null) {
				request = attr.getRequest();
			}
		} catch (Exception e) {

		}
		int cur = T2TUtil.obj2Int(params.get("cur"));
		int total = T2TUtil.obj2Int(params.get("total"));

		String url = null;
		if (request != null) {
			url = request.getRequestURI();
			if (request.getQueryString() != null) {
				url = url + "?" + request.getQueryString();
			}
		} else {
			url = params.get("url").toString();
		}

		StringBuilder sb = new StringBuilder(
				"<div class=\"dataTables_paginate paging_simple_numbers\" >");
		sb.append("<ul class=\"pagination\">");

		if (StringUtils.isBlank(url)) {
			url = "/?page=";
		} else {
			if (url.contains("page=")) {
				url = url.substring(0, url.indexOf("page=") + 5);
			} else {
				if (url.contains("?")) {
					url = url + "&page=";
				} else {
					url = url + "?page=";
				}
			}
		}
		if (cur < 2) {
			sb.append("<li class=\"paginate_button previous disabled\" ><a href=\"#\">&lt;&lt;</a></li>");
			if (cur == 1 && total > 0) {
				sb.append("<li class=\"paginate_button \" ><a href=\"#\">1</a> </li>");
			}
		} else {
			sb.append("<li class=\"paginate_button previous\" ><a href=\"")
					.append(url + (cur - 1)).append("\">&lt;&lt;</a></li>");
			sb.append("<li class=\"paginate_button \" ><a href=\"")
					.append(url + 1).append("\">1</a></li>");
		}
		int start = 2;
		int end = total;
		boolean endPoint = false;
		if (end - cur > 5) {
			end = cur + 5;
			endPoint = true;
		}
		if (cur - 2 > 3) {
			start = cur - 3;
			sb.append("<li class=\"paginate_button previous disabled\" ><a href=\"#\">..</a></li>");
		}
		for (int i = start; i < end; i++) {
			sb.append("<li class=\"paginate_button");
			if (i == cur) {
				sb.append("active");
			}
			sb.append("\" ><a href=\"").append(url + i).append("\">").append(i)
					.append("</a></li>");
		}
		if (endPoint) {
			sb.append("<li class=\"paginate_button previous disabled\" ><a href=\"#\">..</a></li>");
		}
		if (cur >= total) {
			if (cur > 1) {
				sb.append("<li class=\"paginate_button active \" ><a href=\"")
						.append(url + total).append("\">").append(total)
						.append("</a> </li>");
			}
			sb.append("<li class=\"paginate_button next disabled")
					.append("\" ><a href=\"").append(url + (cur + 1))
					.append("\">&gt;&gt;</a></li>");
		} else {
			sb.append(
					"<li class=\"paginate_button \" aria-controls=\"dataTables-example\" tabindex=\"0\"><a href=\"")
					.append(url + total).append("\">").append(total)
					.append("</a> </li>");

			sb.append("<li class=\"paginate_button next")
					.append("\" ><a href=\"").append(url + (cur + 1))
					.append("\">&gt;&gt;</a></li>");
		}
		sb.append("</ul> </div>");

		env.setVariable("vpage", ObjectWrapper.DEFAULT_WRAPPER.wrap(sb));
		body.render(env.getOut());
	}
}
