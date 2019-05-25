package com.we.web.view.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.DBObject;
import com.we.common.Constant.ReCode;
import com.we.common.Constant.Role;
import com.we.common.mongo.DboUtil;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.models.permission.Menu;
import com.we.service.PermissionService;
import com.we.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminPermCtl {

	static final Logger log = LoggerFactory.getLogger(AdminPermCtl.class);

	@Autowired
	PermissionService permissionService;

	@Autowired
	UserService userService;

	@RequestMapping("/perms")
	public ModelAndView perms(Integer menuId, String uri, String name, Integer role, Integer page, Integer size,
			HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menus", Menu.values());
		model.put("roles", Role.values());
		Page<DBObject> curPage = permissionService.queryPermission(menuId, uri, name, role, page, size);
		curPage.setUrl(request.getRequestURI());
		model.put("page", curPage);
		model.put("menuId", menuId);
		model.put("uri", uri);
		model.put("name", name);
		model.put("role", role);
		return new ModelAndView("admin/perm/perms", model);
	}

	@ResponseBody
	@RequestMapping(value = "/perms", method = RequestMethod.POST)
	public ReMsg perms(Integer role, HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {
		List<DBObject> pms = permissionService.findPermission(0, null, null, role, 0, 500);
		return new ReMsg(ReCode.OK, pms);
	}

	@RequestMapping("/perm")
	public ModelAndView perm(Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menus", Menu.values());
		model.put("roles", Role.values());
		if (id != null && id > 0) {
			model.put("obj", permissionService.findPermById(id));
		}
		return new ModelAndView("admin/perm/perm", model);
	}

	@ResponseBody
	@RequestMapping(value = "/perm", method = RequestMethod.POST)
	public ReMsg perm(Long id, String name, String uri, int menuId, String roles, HttpServletRequest request)
			throws IOException {
		return permissionService.upsertPermission(id, name, uri, menuId, roles);
	}

	@RequestMapping("/userPerm")
	public ModelAndView userPerm(Long uid) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roles", Role.values());
		if (uid != null && uid > 0) {
			DBObject user = userService.findById(uid);
			if (user != null) {
				model.put("nickname", DboUtil.getString(user, "nickname"));
				model.put("uid", uid);
				model.put("obj", permissionService.getPermissions(uid));
			}
		}
		return new ModelAndView("admin/perm/userPerm", model);
	}

	@ResponseBody
	@RequestMapping(value = "/userPerm", method = RequestMethod.POST)
	public ReMsg userPerm(Long uid, String ids, HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {
		permissionService.upsertUserPerms(uid, ids);
		return new ReMsg(ReCode.OK);
	}
}
