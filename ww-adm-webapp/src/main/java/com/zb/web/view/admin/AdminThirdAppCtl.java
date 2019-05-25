package com.zb.web.view.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.utils.RandomUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.third.OpenOrderService;
import com.zb.service.third.ThirdService;

@Controller
@RequestMapping("/admin")
public class AdminThirdAppCtl {

	static final Logger log = LoggerFactory.getLogger(AdminThirdAppCtl.class);

	@Autowired
	ThirdService thirdService;

	@Autowired
	OpenOrderService openOrderService;

	@RequestMapping("/openOrders")
	public ModelAndView openOrders(String appId, String openId, Long uid,
			Long provider, Integer status, String outOrderNo,
			Integer callStatus, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = openOrderService.query(appId, openId, uid,
				provider, status, outOrderNo, callStatus, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("appId", appId);
		model.put("provider", provider);
		model.put("openId", openId);
		model.put("uid", uid);
		model.put("outOrderNo", outOrderNo);
		model.put("callStatus", callStatus);
		model.put("providers",
				thirdService.findMerchant(Const.STATUS_UP, 0, 100));
		return new ModelAndView("admin/third/openOrders", model);
	}

	@RequestMapping("/thirdApps")
	public ModelAndView games(Integer type, Integer status, Long provider,
			Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = thirdService.queryApp(type, provider, status,
				page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("type", type);
		model.put("provider", provider);
		model.put("providers",
				thirdService.findMerchant(Const.STATUS_UP, 0, 100));
		return new ModelAndView("admin/third/apps", model);
	}

	@RequestMapping("/thirdApp")
	public ModelAndView game(String id) {
		ModelAndView model = new ModelAndView("admin/third/app");
		model.addObject("providers",
				thirdService.findMerchant(Const.STATUS_UP, 0, 100));
		if (id == null) {
			return model;
		}
		DBObject dbo = thirdService.findAppById(id);
		model.addObject("obj", dbo);

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/thirdApp", method = RequestMethod.POST)
	public ReMsg saveGame(String id, Integer type, Long provider,
			Integer status, String title, Integer sort, Integer ch,
			Integer childCh, String url, String pic, String description,
			Integer count, String callback, String blowfishKey,
			String blowfishVecter, String showUrl, Integer urlStyle) {
		return thirdService.upsert(id, type, provider, status, title, sort, ch,
				childCh, url, pic, description, count, callback, blowfishKey,
				blowfishVecter, showUrl, urlStyle);
	}

	@RequestMapping("/merchants")
	public ModelAndView gameCPs(Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = thirdService.queryMerchant(status, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		return new ModelAndView("admin/third/merchants", model);
	}

	@RequestMapping("/merchant")
	public ModelAndView gameCP(Long id) {
		ModelAndView model = new ModelAndView("admin/third/merchant");
		if (id == null) {
			return model;
		}
		DBObject dbo = thirdService.findMerchantById(id);
		model.addObject("obj", dbo);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/merchant", method = RequestMethod.POST)
	public ReMsg saveGameCP(Long id, Integer status, Integer sort, String url,
			String pic, String description, String name, String signKey) {
		if (StringUtils.isBlank(signKey)) {
			signKey = RandomUtil.randomNoCase(12);
		}
		return thirdService.upsertMerchant(id, status, sort, url, pic,
				description, name, signKey);
	}

}
