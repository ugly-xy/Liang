package com.we.web.view.admin;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.we.common.Constant.OperationType;
import com.we.common.Constant.ReCode;
import com.we.common.utils.DateUtil;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.service.BannerService;
import com.we.service.cloud.StorageService;

@Controller
@RequestMapping("/admin")
public class AdminBannerCtl extends AdminBaseCtl {

	static final Logger log = LoggerFactory.getLogger(AdminBannerCtl.class);

	@Autowired
	BannerService bannerService;
	
	@RequestMapping("/banners")
	public ModelAndView banner(String type, Integer status, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = bannerService.queryBanner(status, type, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = super.getImgMap();
		model.put("page", curPage);
		model.put("status", status);
		model.put("type", type);
		model.put("types", BannerService.TYPES);
		
		return new ModelAndView("admin/banner/banners", model);
	}

	@RequestMapping("/banner")
	public ModelAndView pack(Long id) {
		Map<String, Object> model = super.getImgMap();
		model.put("types", BannerService.TYPES);
		model.put("ops", OperationType.getOps());
		if (id != null) {
			DBObject dbo = bannerService.findBannerById(id);
			model.put("obj", dbo);
		}
		return  new ModelAndView("admin/banner/banner",model);
	}

	@ResponseBody
	@RequestMapping(value = "/banner", method = RequestMethod.POST)
	public ReMsg saveResPack(Long id, String type, String title, String pic, String op, String opId, Integer status,
			Integer sort, String st, String et, String prePic) throws ParseException {
		long startTime = 0;
		long endTime = 0;
		if (StringUtils.isNotBlank(st)) {
			startTime = DateUtil.getZeroTime(DateUtil.convertDate(st, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(et)) {
			endTime = DateUtil.getZeroTime(DateUtil.convertDate(et, "yyyy-MM-dd")) + DateUtil.DAY;
		}
		return bannerService.upsert(id, type, title, pic, op, opId, status, sort, startTime, endTime, prePic);
	}
	
	@ResponseBody
	@RequestMapping("/bannerPic")
	public ReMsg uploadPic(@RequestParam MultipartFile file, String ptoken, String timestamp,
			HttpServletRequest request, HttpServletResponse response) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return bannerService.uploadPic(file, ptoken, timestamp);
	}
}
