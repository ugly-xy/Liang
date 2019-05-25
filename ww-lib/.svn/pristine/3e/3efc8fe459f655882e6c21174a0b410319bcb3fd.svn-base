package com.zb.core.web;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.common.utils.DateUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class DateDirective implements TemplateDirectiveModel {

	static final Logger log = LoggerFactory.getLogger(DateDirective.class);
	static final String FMT = "MM-dd HH:mm";

	@SuppressWarnings("deprecation")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] arg2,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Long time = T2TUtil.obj2Long(params.get("time"),0L);
		Object obj = params.get("fmt");
		String fmt = null;
		if(obj!=null){
			fmt = obj.toString();
		}else{
			fmt = FMT;
		}
		String d = DateUtil.dateFormat(new Date(time),fmt);
		env.setVariable("date", ObjectWrapper.DEFAULT_WRAPPER.wrap(d));
		body.render(env.getOut());
	}

}
