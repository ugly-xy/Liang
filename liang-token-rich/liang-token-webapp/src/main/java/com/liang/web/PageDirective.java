package com.liang.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class PageDirective implements TemplateDirectiveModel {

	static final Logger log = LoggerFactory.getLogger(PageDirective.class);

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] arg2,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		@SuppressWarnings("unused")
		HttpServletRequest request = null;
		try {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes();
			if (attr != null) {
				request = attr.getRequest();
			}
		} catch (Exception e) {

		}


		StringBuilder sbu = new StringBuilder();
			
		sbu.append("HAHA");

		env.setVariable("vpage", ObjectWrapper.DEFAULT_WRAPPER.wrap(sbu));
		body.render(env.getOut());
	}
}
