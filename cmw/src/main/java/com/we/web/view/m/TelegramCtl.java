package com.we.web.view.m;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.we.common.http.HttpRequestUtil;
import com.we.service.userTask.TelegramService;

@Controller
@RequestMapping(value = "/telegram")
public class TelegramCtl extends BaseCtl {

	@Autowired
	TelegramService telegramService;

//	@ResponseBody
//	@RequestMapping("/webhook/{json}")
//	public ReMsg webhook(@PathVariable String json) {
//		return new ReMsg(telegramService.callBack(json));
//	}

	@ResponseBody
	@RequestMapping("/webhook/dafie2nkdafdscadsfjads")
	public boolean callback(String json,HttpServletRequest req) {
		if(StringUtils.isBlank(json)) {
			try {
				json = HttpRequestUtil.getRequestJsonString(req);
			} catch (IOException e) {
				return false ;
			}
		}
		return telegramService.callBack(json);
	}

}
