package com.zb.web.view.admin;

import java.text.ParseException;
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
import com.zb.common.Constant.OperationType;
import com.zb.common.utils.DateUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.BannerService;

@Controller
@RequestMapping("/admin")
public class AdminBannerCtl {

	static final Logger log = LoggerFactory.getLogger(AdminBannerCtl.class);

	@Autowired
	BannerService bannerService;

	@RequestMapping("/banners")
	public ModelAndView banner(String type, Integer status, Integer via,
			Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = bannerService.queryBanner(status, type, via,
				page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("type", type);
		model.put("via", via);
		model.put("types", BannerService.TYPES);
		model.put("ops", OperationType.getOps());
		return new ModelAndView("admin/banner/banners", model);
	}

	// static int[] vias = { 1, 2, 5 };
	@RequestMapping("/banner")
	public ModelAndView pack(Long id) {
		ModelAndView model = new ModelAndView("admin/banner/banner");
		model.addObject("types", BannerService.TYPES);
		model.addObject("ops", OperationType.getOps());

		// model.addObject("vias", vias);
		if (id == null) {
			return model;
		}
		DBObject dbo = bannerService.findBannerById(id);
		model.addObject("obj", dbo);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/banner", method = RequestMethod.POST)
	public ReMsg saveResPack(Long id, String type, String title, String pic,
			String op, String opId, Integer status, Integer[] vias, Integer sort,String st,String et )throws ParseException  {
		long startTime = 0;
		long endTime = 0;
		if (StringUtils.isNotBlank(st)) {
			startTime = DateUtil.getZeroTime(DateUtil.convertDate(st, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(et)) {
			endTime = DateUtil.getZeroTime(DateUtil.convertDate(et, "yyyy-MM-dd")) + DateUtil.DAY;
		}
		return bannerService.upsert(id, type, title, pic, op, opId, status,
				vias, sort,startTime,endTime);
	}
}
