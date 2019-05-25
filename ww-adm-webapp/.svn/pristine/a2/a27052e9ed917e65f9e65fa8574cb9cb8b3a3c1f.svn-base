package com.zb.web.view.admin;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.Constant.Role;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.TitleModel;
import com.zb.models.UserTitle;
import com.zb.models.user.UserTag;
import com.zb.models.user.UserTagItem;
import com.zb.service.MobileContactsService;
import com.zb.service.RelationshipService;
import com.zb.service.UserService;
import com.zb.service.UserTagService;
import com.zb.service.usertask.UserMonitorService;

@Controller
@RequestMapping("/admin")
public class AdminUserCtl extends AdminBaseCtl {

	static final Logger log = LoggerFactory.getLogger(AdminUserCtl.class);

	@Autowired
	UserService userService;
	@Autowired
	MobileContactsService contactsService;
	@Autowired
	RelationshipService relationshipService;
	@Autowired
	UserTagService userTagService;

	@Autowired
	UserMonitorService userMonitorService;

	@RequestMapping("/relationship")
	public ModelAndView relations(Long userId, HttpServletRequest request) {
		List<DBObject> rs = relationshipService.findAllAdmin(userId, null);
		for (DBObject dbo : rs) {
			Long rid = DboUtil.getLong(dbo, "rid");
			DBObject user = userService.findById(rid);
			dbo.put("avatar", DboUtil.getString(user, "avatar"));
			dbo.put("nickname", DboUtil.getString(user, "nickname"));
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId", userId);
		model.put("rs", rs);
		return new ModelAndView("admin/users/relationship", model);
	}

	@RequestMapping("/users")
	public ModelAndView resPacks(Long userId, Integer role, Integer status, Integer page, Integer size, String phone,
			String nickname, HttpServletRequest request) {
		Page<DBObject> curPage = userService.queryUser(userId, status, role, phone, nickname, page, size);
		for (DBObject dbo : curPage.getList()) {
			dbo.put("lv", UserService.getLevel(DboUtil.getInt(dbo, "point")));
			dbo.put("avatar", userService.toMinAvatar(dbo));
		}
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("role", role);
		model.put("userId", userId);
		model.put("phone", phone);
		model.put("nickname", nickname);
		return new ModelAndView("admin/users/users", model);
	}

	@RequestMapping("/coinUsers")
	public ModelAndView coinUsers(Long min, Long max, Integer page, Integer size, HttpServletRequest request) {
		if (min == null) {
			min = 5000L;
		}
		Page<DBObject> curPage = userService.queryCoinUser(min, max, page, size);
		for (DBObject dbo : curPage.getList()) {
			dbo.put("avatar", userService.toMinAvatar(dbo));
		}
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("min", min);
		model.put("max", max);
		return new ModelAndView("admin/users/coinUsers", model);
	}

	@RequestMapping("/user")
	public ModelAndView pack(Long id) {
		DBObject dbo = userService.findById(id);
		UserTag ut = userTagService.getUserTag(id);
		if (ut != null) {
			String tags = "";
			for (UserTagItem item : ut.getTags()) {
				tags += item.getKey() + "=" + item.getValue() + ";";
			}
			dbo.put("tags", tags);
		}
		return new ModelAndView("admin/users/user", "obj", dbo);
	}

	@ResponseBody
	@RequestMapping(value = "/user/setTags", method = RequestMethod.POST)
	public ReMsg setRole(Long uid, String tags) {
		return userTagService.adminSetTags(uid, tags);
	}

	@ResponseBody
	@RequestMapping(value = "/user/goodNo", method = RequestMethod.POST)
	public ReMsg createGoodNo(String uids, String pwd) {
		return new ReMsg(userService.createGoodNo(uids, pwd, null));
	}

	@ResponseBody
	@RequestMapping("/createRobit")
	public ReMsg createRobit(Integer count, HttpServletRequest req) {
		return new ReMsg(userService.createRobit(count, req, Role.ROBOT));
	}

	@ResponseBody
	@RequestMapping(value = "/user/setRole", method = RequestMethod.POST)
	public ReMsg setRole(Long uid, Integer role) {
		return userService.setRole(uid, role);
	}

	@ResponseBody
	@RequestMapping(value = "/user/setStatus", method = RequestMethod.POST)
	public ReMsg setStatus(Long uid, Integer status) {
		return userService.setStatus(uid, status);
	}

	@ResponseBody
	@RequestMapping(value = "/user/nickname", method = RequestMethod.POST)
	public ReMsg setStatus(Long uid, String nickname) {
		return userService.updateUser(uid, 0, nickname, null);
	}

	@ResponseBody
	@RequestMapping(value = "/user/vip", method = RequestMethod.POST)
	public ReMsg setVip(Long uid, int vipVal) {
		userService.changeVipAdm(uid, vipVal);
		return new ReMsg(ReCode.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/user/pwd", method = RequestMethod.POST)
	public ReMsg setPwd(Long uid, String newPwd) {
		return userService.updatePwd(uid, newPwd);
	}

	@ResponseBody
	@RequestMapping(value = "/user/setAddress", method = RequestMethod.POST)
	public ReMsg setAddress(Long uid, String province, String city) {
		return userService.updateAddress(uid, province, city);
	}

	// 签名
	@ResponseBody
	@RequestMapping(value = "/user/personLabel", method = RequestMethod.POST)
	public ReMsg updateLabel(Long uid, String personLabel) {
		return userService.updateLabel(uid, personLabel);
	}

	// 修改签名的权限
	@ResponseBody
	@RequestMapping(value = "/user/setModifyLabel", method = RequestMethod.POST)
	public ReMsg setModifyLabel(long uid, Boolean modifyLabel) {
		long adminId = userService.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return userService.updateModifyLabel(uid, modifyLabel);
	}

	// 封面
	@ResponseBody
	@RequestMapping(value = "/user/cover", method = RequestMethod.POST)
	public ReMsg setCover(Long uid, String cover) {
		return userService.changeCover(uid, cover);
	}

	@ResponseBody
	@RequestMapping(value = "/user/avatar", method = RequestMethod.POST)
	public ReMsg setAvatar(Long uid) {
		String avatar = null;
		for (;;) {
			int n = RandomUtil.nextInt(150000);
			DBObject dbo = userService.findById(100000000L + n);
			if (dbo != null) {
				avatar = DboUtil.getString(dbo, "avatar");
				if (StringUtils.isNotBlank(avatar)) {
					break;
				}
			}
		}
		return userService.updateUser(uid, 0, null, avatar);
	}

	@ResponseBody
	@RequestMapping(value = "/user/getUsers", method = RequestMethod.POST)
	public ReMsg getUsers(String ids) {
		Long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (StringUtils.isBlank(ids)) {
			return new ReMsg(ReCode.FAIL);
		}
		Long[] users = null;
		List<Long> t = JSONUtil.jsonToArray(ids, Long.class);
		users = t.toArray(new Long[] {});
		return new ReMsg(ReCode.OK, userService.findByids(users));
	}

	@ResponseBody
	@RequestMapping(value = "/user/info", method = RequestMethod.POST)
	public ReMsg setUser(Long uid, String phone, Integer role, Integer status, Integer sex, String photos,
			String personLabel) {
		String[] pics = null;
		if (StringUtils.isNotBlank(photos)) {
			pics = photos.split(",");
		}
		return userService.setInfo(uid, role, status, phone, sex, pics, personLabel);
	}

	@ResponseBody
	@RequestMapping(value = "/user/info")
	public ReMsg getUser(Long uid) {
		return new ReMsg(ReCode.OK, userService.findByIdSafe(uid));
	}

	@RequestMapping("/userContacts")
	public ModelAndView mobileContacts(Long uid, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = contactsService.adminContacts(uid, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("uid", uid);
		model.put("page", curPage);
		return new ModelAndView("admin/users/userContacts", model);
	}

	@RequestMapping("/userContacts/detail")
	public ModelAndView contactsDetail(Long uid, Integer page, Integer size) {
		Page<DBObject> curPage = contactsService.adminContactsDetail(uid, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("uid", uid);
		model.put("page", curPage);
		return new ModelAndView("admin/users/userContactsDetail", model);
	}

	@ResponseBody
	@RequestMapping(value = "/user/lbs")
	public ReMsg setLbs() {
		// String avatar = null;
		// for (;;) {
		// int n = RandomUtil.nextInt(150000);
		// DBObject dbo = userService.findById(100000000L + n);
		// if (dbo != null) {
		// avatar = DboUtil.getString(dbo, "avatar");
		// if (StringUtils.isNotBlank(avatar)) {
		// break;
		// }
		// }
		// }
		userService.updateUserLbs();
		return new ReMsg(ReCode.OK);
	}

	// 用户称号
	@RequestMapping("/userTitles")
	public ModelAndView userTitles(Long userId, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = userService.queryUserTitle(userId, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("userId", userId);
		return new ModelAndView("admin/users/userTitles", model);
	}

	// 添加称号
	@RequestMapping("/changeUserTitle")
	public ModelAndView changeUserTitle() {
		return new ModelAndView("admin/users/changeUserTitle", "list", TitleModel.getAllTitleModel(null, true));
	}

	// 修改称号
	@ResponseBody
	@RequestMapping(value = "/changeUserTitle", method = RequestMethod.POST)
	public ReMsg changeUserTitle(long uid, String title, Integer day, HttpServletRequest request) {
		long adminId = userService.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		String[] split = title.split(":");
		return userService.upsetUserTitle(uid, Integer.valueOf(split[0]), split[1], day);
	}

	@RequestMapping("/monitors")
	public ModelAndView monitors(Long uid, Integer weight, Integer status, Integer page, Integer size, String date,
			HttpServletRequest request) {
		Date curDate = null;
		if (StringUtils.isNotBlank(date)) {
			try {
				curDate = DateUtil.convertDate(date, "yyyy-MM-dd");
			} catch (ParseException e) {
			}
		}
		Page<DBObject> curPage = userMonitorService.query(uid, weight, status, curDate, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("weight", weight);
		model.put("status", status);
		model.put("uid", uid);
		model.put("curDate", date);
		return new ModelAndView("admin/users/monitors", model);
	}
}
