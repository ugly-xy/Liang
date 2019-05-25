package com.liang.web;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class DateDirective implements TemplateDirectiveModel {

	static final Logger log = LoggerFactory.getLogger(DateDirective.class);

	@SuppressWarnings("deprecation")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] arg2,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Object obj = params.get("fmt");
		obj="hehe";
		env.setVariable("date", ObjectWrapper.DEFAULT_WRAPPER.wrap(obj));
		body.render(env.getOut());
	}

}
