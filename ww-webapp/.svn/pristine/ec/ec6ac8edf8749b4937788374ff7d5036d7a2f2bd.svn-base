package com.zb.web.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.zb.core.web.ReMsg;
import com.zb.service.PushService;

@Controller
@RequestMapping("/api")
public class PushCtl extends BaseCtl {
	static final Logger log = LoggerFactory.getLogger(PushCtl.class);

	@Autowired
	PushService pushService;

	@ResponseBody
	@RequestMapping("/pushBind")
	public ReMsg pushBind(String channelId, Integer channelType,  String appCode,
			HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException {
		return pushService.savePushChannel(channelId, channelType, appCode, req);
	}

	// @ResponseBody
	// @RequestMapping("/push/addTag")
	// public ReMsg addTag(String tag, Integer devType,
	// HttpServletRequest req)
	// throws JsonParseException, JsonMappingException, IOException,
	// PushClientException, PushServerException {
	// return pushService.savePushTag(tag, DevType.of(devType));
	// }

}
