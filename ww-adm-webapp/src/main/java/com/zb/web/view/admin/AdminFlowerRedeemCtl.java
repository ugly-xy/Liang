package com.zb.web.view.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.FlowerRedeemService;

@Controller
@RequestMapping("/admin")
public class AdminFlowerRedeemCtl {

	static final Logger log = LoggerFactory.getLogger(AdminFlowerRedeemCtl.class);

	@Autowired
	FlowerRedeemService redeemService;

	@RequestMapping("/redeemLogs")
	public ModelAndView redeemLogs(Long uid, Integer type, String key, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = redeemService.queryRedeemLog(uid, type, key, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("uid", uid);
		model.put("type", type);
		model.put("key", key);
		return new ModelAndView("admin/st/redeemLogs", model);
	}

	// 兑奖
	@ResponseBody
	@RequestMapping(value = "/redeemKey", method = RequestMethod.POST)
	public ReMsg redeemKey(String key) {
		return redeemService.redeemReward(key);
	}

	// 查询用户总额度和可用额度
	@RequestMapping("/searchCount")
	public ModelAndView searchCount(Long uid, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = redeemService.queryUserRedeem(uid, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("uid", uid);
		return new ModelAndView("admin/st/userResCount", model);
	}
}
