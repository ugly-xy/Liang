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
import com.zb.service.ContactHeadService;

@Controller
@RequestMapping("/admin")
public class AdminContactsHeadCtl {

	static final Logger log = LoggerFactory
			.getLogger(AdminContactsHeadCtl.class);

	@Autowired
	ContactHeadService contactHeadService;

	// tools
	@RequestMapping("/contactsHeads")
	public ModelAndView reses(Integer status, Long uid, Integer pri,
			Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = contactHeadService.queryContactHead(status,
				uid, pri, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("uid", uid);
		model.put("pri", pri);
		return new ModelAndView("admin/tools/contactsHeads", model);
	}

	@RequestMapping("/contactsHead")
	public ModelAndView res(Long id) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list", ContactHeadService.CATE_NAME);
		if (id == null) {
			return new ModelAndView("admin/tools/contactsHead", m);
		}
		DBObject dbo = contactHeadService.findResById(id);
		m.put("obj", dbo);
		return new ModelAndView("admin/tools/contactsHead", m);
	}

	@ResponseBody
	@RequestMapping(value = "/contactsHead", method = RequestMethod.POST)
	public ReMsg saveRes(Long id, String name, Integer status, Integer sort,
			String headPic, Integer pri, Long uid, String cateName) {
		return contactHeadService.saveContactHead(id, name, status, sort,
				headPic, pri, uid, cateName);
	}

}
