package com.zb.web.view.admin;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.utils.DateUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.SakuranModelPool;
import com.zb.models.SlotMachinesModelPool;
import com.zb.service.room.SakuranService;

@Controller
@RequestMapping("/admin")
public class AdminSakuranCtl {

	static final Logger log = LoggerFactory.getLogger(AdminSakuranCtl.class);

	@Autowired
	SakuranService sakuranService;

	@RequestMapping("/sakuran/exhibition")
	public ModelAndView exhibition(Integer page, Integer size, String st, String et) throws ParseException {
		long startDate = 0;
		long endDate = 0;
		if (StringUtils.isBlank(st)) {
			startDate = DateUtil.getTodayZeroTime();
		} else {
			startDate = DateUtil.getZeroTime(DateUtil.convertDate(st, "yyyy-MM-dd"));
		}
		if (StringUtils.isBlank(et)) {
			endDate = startDate + DateUtil.DAY;
		} else {
			endDate = DateUtil.getZeroTime(DateUtil.convertDate(et, "yyyy-MM-dd")) + DateUtil.DAY;
		}
		Page<DBObject> curPage = sakuranService.getAllHistory(page, size, startDate, endDate);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("st", st);
		model.put("et", et);
		SakuranModelPool pool = sakuranService.querySmPool();
		model.put("pool", pool);
		return new ModelAndView("admin/activitys/sakuranExhibition", model);
	}

	@RequestMapping("/sakuran/exhibition/{id}")
	public ModelAndView exhibitionDetail(@PathVariable Long id) {
		return new ModelAndView("admin/activitys/sakuranExhibitionDetail").addObject("dbo",
				sakuranService.getOneHistory(id));
	}

	@RequestMapping("/sakuran/changePool/{type}")
	public @ResponseBody ReMsg changePool(@PathVariable String type, Long change) {
		sakuranService.changePool(type, change);
		return new ReMsg(ReCode.OK);
	}

	@RequestMapping("/sakuran/sakuranUserSearch")
	public ModelAndView userSearch(Integer page, Integer size, String st, String et, Long uid) throws ParseException {
		Long startTime = 0L;
		Long endTime = 0L;
		if (StringUtils.isNotBlank(st)) {
			startTime = DateUtil.convertDate(st, "yyyy-MM-dd").getTime();
		}
		if (StringUtils.isNotBlank(et)) {
			endTime = DateUtil.convertDate(et, "yyyy-MM-dd").getTime();
		}
		Page<DBObject> curPage = sakuranService.slotUserSearch(startTime, endTime, uid, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("st", st);
		model.put("et", et);
		model.put("uid", uid);
		return new ModelAndView("admin/activitys/sakuranUserSearch", model);
	}

}
