package com.zb.web.view.admin;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.zb.common.Constant.ReCode;
import com.zb.common.utils.DateUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.room.Actor;
import com.zb.service.BannerService;
import com.zb.service.pay.CoinService;
import com.zb.service.room.DrawSomethingService;
import com.zb.service.room.PunishService;
import com.zb.service.room.RoomDefaultService;
import com.zb.service.room.RoomHandlerDispatcher;
import com.zb.service.room.UndercoverService;
import com.zb.service.room.UserActivityService;
import com.zb.service.room.WerewolfKillService;

@Controller
@RequestMapping("/admin")
public class AdminActivityCtl {

	static final Logger log = LoggerFactory.getLogger(AdminActivityCtl.class);

	@Autowired
	UndercoverService undercoverService;

	@Autowired
	PunishService punishService;

	@Autowired
	DrawSomethingService drawSomethingService;

	@Autowired
	UserActivityService userActivityService;

	@Autowired
	CoinService coinService;

	@Autowired
	RoomHandlerDispatcher roomHandlerDispatcher;

	@Autowired
	WerewolfKillService werewolfKillService;

	@Autowired
	RoomDefaultService roomDefaultService;

	@RequestMapping("/punishs")
	public ModelAndView punishs(Integer status, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = punishService.query(status, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		return new ModelAndView("admin/activitys/punishs", model);
	}

	@RequestMapping("/punish")
	public ModelAndView punish(Long id) {
		ModelAndView model = new ModelAndView("admin/activitys/punish");
		if (id == null) {
			return model;
		}
		DBObject dbo = punishService.findById(id);
		model.addObject("obj", dbo);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/punish", method = RequestMethod.POST)
	public ReMsg punish(String words) {
		return punishService.add(words);
	}

	@RequestMapping("/active/sellRooms")
	public ModelAndView sellRooms(Long roomId,Long buyUid, Integer type, Integer sellStatus,Integer status, Boolean sys, String vipRoomType,
			Integer numLength, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = drawSomethingService.query(roomId,status,buyUid, type, sellStatus, sys, vipRoomType, numLength, page,
				size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("type", type);
		model.put("buyUid", buyUid);
		model.put("sellStatus", sellStatus);
		model.put("sys", sys);
		model.put("vipRoomType", vipRoomType);
		model.put("numLength", numLength);
		model.put("roomId", roomId);
		model.put("status", status);
		return new ModelAndView("admin/activitys/sellRooms", model);
	}

	@RequestMapping("/active/sellRoom")
	public ModelAndView sellRoom(Long roomId) {
		DBObject dbo = drawSomethingService.findById(roomId);
		return new ModelAndView("admin/activitys/sellRoom", "obj", dbo);
	}

	/** 更改售卖的房间信息 */
	@ResponseBody
	@RequestMapping(value = "/active/sellRoom", method = RequestMethod.POST)
	public ReMsg sellRoom(Long roomId, Long buyUid, Integer useStatus, Integer sellStatus, Boolean sys, String expiry) {
		Long time = 0L;
		if (StringUtils.isNotBlank(expiry)) {
			try {
				time = DateUtil.convertDate(expiry, "yyyy-MM-dd HH:mm:ss").getTime();
			} catch (ParseException e) {
				return new ReMsg(ReCode.FAIL, "时间有误！");
			}
		}
		return roomDefaultService.updateRoom(roomId, buyUid, useStatus, sellStatus, sys, time);
	}

	@RequestMapping("/active/createRoom")
	public ModelAndView createRoom() {
		return new ModelAndView("admin/activitys/createRoom");
	}

	/** 创建售卖房间 */
	@ResponseBody
	@RequestMapping(value = "/active/createRoom", method = RequestMethod.POST)
	public ReMsg createRoom(String roomIds, int sellStatus, HttpServletRequest request) {
		long adminId = coinService.getUid();
		if (adminId < 1 || StringUtils.isBlank(roomIds)) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return roomDefaultService.adminCreateRoom(roomIds, sellStatus);
	}

	@RequestMapping("/active/rooms")
	public ModelAndView activeRooms(Integer type, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = undercoverService.getActiveRooms(type, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("type", type);
		return new ModelAndView("admin/activitys/activeRooms", model);
	}

	@RequestMapping("/active/roomActors")
	public ModelAndView roomPlayer(long roomId, int type, HttpServletRequest request) {
		Set<Actor> actors = undercoverService.getRoomActors(roomId);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("actors", actors);
		model.put("roomId", roomId);
		model.put("type", type);
		return new ModelAndView("admin/activitys/roomActors", model);
	}

	@ResponseBody
	@RequestMapping("/active/room/close")
	public ReMsg closeRoom(Long id, HttpServletRequest request) {
		roomHandlerDispatcher.getService(id).closeRoom(id);
		return new ReMsg(ReCode.OK);
	}

	@RequestMapping("/activities")
	public ModelAndView activies(Integer status, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = userActivityService.queryActivity(status, page, size, request);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		return new ModelAndView("admin/activitys/activities", model);
	}

	// static int[] vias = { 1, 2, 5 };
	@RequestMapping("/activity")
	public ModelAndView pack(Long id) {
		ModelAndView model = new ModelAndView("admin/activitys/activity");
		model.addObject("types", BannerService.TYPES);
		model.addObject("ops", OperationType.getOps());

		if (id == null) {
			return model;
		}
		DBObject dbo = userActivityService.getOnlineActivity(id);
		model.addObject("obj", dbo);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/activity", method = RequestMethod.POST)
	public ReMsg saveResPack(Long id, String name, String pic, Integer status, Integer sort, Integer point, Integer vip,
			double ver, Integer type, Integer handleType, Integer parentId, String url, String lockPic) {
		return userActivityService.save(id, name, pic, status, sort, point, vip, ver, type, handleType, parentId, url,
				lockPic);
	}

	@RequestMapping(value = "/activity/st")
	public ModelAndView article(Integer io, Integer type, String st, String et) throws ParseException {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("type", type);
		rs.put("st", st);
		rs.put("et", et);
		rs.put("io", io);
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		if (type != null && type > 129) {
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
			list = coinService.stCoinLog(io, type, startDate, endDate, 20);
		}
		rs.put("list", list);
		return new ModelAndView("admin/activitys/st", rs);
	}

	@RequestMapping(value = "/werewolfKillSt")
	public ModelAndView werewolfKillSt(Long uid, Integer actorCount, Integer rateType, Integer sort, Integer page,
			Integer size) {
		Page<DBObject> curPage = werewolfKillService.adminSt(uid, actorCount, rateType, sort, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("uid", uid);
		model.put("actorCount", actorCount);
		model.put("rateType", rateType);
		model.put("sort", sort);
		return new ModelAndView("admin/activitys/werewolfKillSt", model);
	}

	@RequestMapping(value = "/werewolfKillSt2")
	public ModelAndView werewolfKillSt2(Long uid, Integer actorCount, Integer rateType, Integer page, Integer size,
			String st, String et) throws ParseException {
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
		Page<DBObject> curPage = werewolfKillService.adminDetailSt(uid, actorCount, page, size, startDate, endDate);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("uid", uid);
		model.put("actorCount", actorCount);
		model.put("rateType", rateType);
		model.put("st", st);
		model.put("et", et);
		return new ModelAndView("admin/activitys/werewolfKillSt2", model);
	}

	@RequestMapping(value = "/addGameZip")
	public ModelAndView addGameZip(Long id) {
		DBObject dbo = userActivityService.queryGameZipById(id);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("model", dbo);
		return new ModelAndView("admin/activitys/addGameZip", model);
	}

	@RequestMapping(value = "/saveGameZip")
	public @ResponseBody ReMsg saveGameZip(Long id, int gameType, int via, double ver, String url, String appCode) {
		userActivityService.saveGameZip(id, gameType, via, ver, url, appCode);
		return new ReMsg(ReCode.OK);
	}

	@RequestMapping(value = "/queryGameZip")
	public ModelAndView queryGameZip(Integer gameType, Integer page, Integer size) {
		Page<DBObject> curPage = userActivityService.queryGameZip(gameType, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("gameType", gameType);
		return new ModelAndView("admin/activitys/gameZip", model);
	}
}
