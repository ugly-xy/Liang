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
import com.zb.models.SlotMachinesModelPool;
import com.zb.service.room.SlotMachinesService;

@Controller
@RequestMapping("/admin")
public class AdminSlotMachines {

	static final Logger log = LoggerFactory.getLogger(AdminSlotMachines.class);

	@Autowired
	SlotMachinesService slotMachinesService;

	@RequestMapping("/slotMachines/query")
	public ModelAndView query(Integer state, Integer page, Integer size) {
		Page<DBObject> curPage = slotMachinesService.query(state, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("state", state);
		return new ModelAndView("admin/activitys/slotMachines", model);
	}

	@RequestMapping("/slotMachines/preAdd")
	public ModelAndView preAdd() {
		return new ModelAndView("admin/activitys/slotMachinesAdd");
	}

	@RequestMapping("/slotMachines/add")
	public @ResponseBody ReMsg add(String name, String code) {
		slotMachinesService.save(name, code);
		return new ReMsg(ReCode.OK);
	}

	@RequestMapping("/slotMachines/downOrUp")
	public @ResponseBody ReMsg downOrUp(Long id, Integer available) {
		if (available == 1)
			slotMachinesService.delete(id);
		else
			slotMachinesService.up(id);
		return new ReMsg(ReCode.OK);
	}

	@RequestMapping("/slotMachines/exhibition")
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
		Page<DBObject> curPage = slotMachinesService.getAllHistory(page, size, startDate, endDate);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("st", st);
		model.put("et", et);
		SlotMachinesModelPool pool = slotMachinesService.querySmPool();
		model.put("pool", pool);
		return new ModelAndView("admin/activitys/slotExhibition", model);
	}

	@RequestMapping("/slotMachines/exhibition/{id}")
	public ModelAndView exhibitionDetail(@PathVariable Long id) {
		return new ModelAndView("admin/activitys/slotExhibitionDetail").addObject("dbo",
				slotMachinesService.getOneHistory(id));
	}

	@RequestMapping("/slotMachines/changePool/{type}")
	public @ResponseBody ReMsg changePool(@PathVariable String type, Long change) {
		slotMachinesService.changePool(type, change);
		return new ReMsg(ReCode.OK);
	}

	@RequestMapping("/slotMachines/slotUserSearch")
	public ModelAndView userSearch(Integer page, Integer size, String st, String et, Long uid) throws ParseException {
		Long startTime = 0L;
		Long endTime = 0L;
		if (StringUtils.isNotBlank(st)) {
			startTime = DateUtil.convertDate(st, "yyyy-MM-dd").getTime();
		}
		if (StringUtils.isNotBlank(et)) {
			endTime = DateUtil.convertDate(et, "yyyy-MM-dd").getTime();
		}
		Page<DBObject> curPage = slotMachinesService.slotUserSearch(startTime, endTime, uid, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("st", st);
		model.put("et", et);
		model.put("uid", uid);
		return new ModelAndView("admin/activitys/slotUserSearch", model);
	}

	@RequestMapping("/slotMachines/blackUsers")
	public ModelAndView blackUsers(Integer page, Integer size, Long uid) throws ParseException {
		Page<DBObject> curPage = slotMachinesService.blackUsers(uid, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("uid", uid);
		return new ModelAndView("admin/activitys/blackUsers", model);
	}

	@RequestMapping("/slotMachines/addBlackUsers")
	public @ResponseBody ReMsg addBlackUsers(Long uid) {
		return slotMachinesService.addblackUsers(uid);
	}

	@RequestMapping("/slotMachines/delBlackUsers")
	public @ResponseBody ReMsg delBlackUsers(Long uid) {
		return slotMachinesService.delBlackUsers(uid);
	}

}
