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
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.utils.DateUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.DiscountService;

@Controller
@RequestMapping("/admin")
public class AdminDiscountCtl {

	static final Logger log = LoggerFactory.getLogger(AdminDiscountCtl.class);

	@Autowired
	DiscountService discountService;

	@RequestMapping("/discountConfigs")
	public ModelAndView discountConfigs(Integer type, Integer status,
			Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = discountService.queryConfig(type, status,
				page, size, null);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("type", type);
		return new ModelAndView("admin/goods/discountConfigs", model);
	}

	@RequestMapping("/discountConfig")
	public ModelAndView discountConfig(Long id) {
		DBObject dbo = discountService.findConfigById(id);
		return new ModelAndView("admin/goods/discountConfig", "obj", dbo);
	}

	@ResponseBody
	@RequestMapping(value = "/discountConfig", method = RequestMethod.POST)
	public ReMsg update(Long id, String name, Integer type, Integer amount,
			Integer limitAmount, String startTime, String endTime,
			Integer status, Integer total) {

		if (amount == null) {
			new ReMsg(ReCode.PARAMETER_ERR);
		} else {
			amount = amount * 100;
		}

		if (limitAmount == null) {
			new ReMsg(ReCode.PARAMETER_ERR);
		} else {
			limitAmount = limitAmount * 100;
		}

		Long sT;
		Long eT;
		if (StringUtils.isBlank(startTime)) {
			sT = System.currentTimeMillis();
		} else {
			try {
				sT = DateUtil.convertDate(startTime, "yyyy-MM-dd").getTime();
			} catch (ParseException e) {
				sT = System.currentTimeMillis();
			}
		}

		if (StringUtils.isBlank(endTime)) {
			eT = System.currentTimeMillis() + Const.YEAR * 99;
		} else {
			try {
				eT = DateUtil.convertDate(endTime, "yyyy-MM-dd").getTime();
			} catch (ParseException e) {
				eT = System.currentTimeMillis() + Const.YEAR * 99;
			}
		}
		if (id == null || id == 0) {
			return discountService.saveConfig(type, name, amount, limitAmount,
					sT, eT, status, total);
		} else {
			return discountService.updateConfig(id, name, amount, limitAmount,
					sT, eT, status, total, type);
		}
	}

}
