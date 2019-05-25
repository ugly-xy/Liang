package com.zb.web.view.admin;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zb.common.Constant.OperationType;
import com.zb.core.web.ReMsg;
import com.zb.service.PushService;

@Controller
@RequestMapping("/admin")
public class AdminGeTuiPushCtl {
	static final Logger log = LoggerFactory.getLogger(AdminGeTuiPushCtl.class);

	@Autowired
	PushService pushService;

	@RequestMapping("/push")
	public ModelAndView push() {
		return new ModelAndView("admin/sys/push");
	}

	@RequestMapping(value = "/push", method = RequestMethod.POST)
	public ModelAndView push(String title, String body, String op, String opid,
			Integer platform, Long id) {// id是推送单个用户id
		ReMsg rm = null;
		Map<String, String> cdata = new HashMap<String, String>();
		if (!op.equals(OperationType.NULL)) {
			cdata.put("op", op);
			cdata.put("opId", opid);
		}
		if (null == id || id == 0) {
			rm = pushService.pushAll(platform, title, body, cdata);
		} else {
			rm = pushService.push(id, title, body, cdata);
		}
		return new ModelAndView("admin/sys/push", "msg", rm);
	}
}
