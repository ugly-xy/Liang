package com.zb.web.view.admin;

import java.util.ArrayList;
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

import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.res.Res;
import com.zb.service.ResService;

@Controller
@RequestMapping("/admin")
public class AdminResCtl {

	static final Logger log = LoggerFactory.getLogger(AdminResCtl.class);

	@Autowired
	ResService resService;

	@RequestMapping("/resPacks")
	public ModelAndView resPacks(Long cateId, Integer status, Integer type,
			Integer page, Integer size, HttpServletRequest request) {
		List<DBObject> cates = resService.findResPackCate(Const.STATUS_UP, 0,
				100);
		Page<DBObject> curPage = resService.queryResPack(null, cateId, status,
				type, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("cates", cates);
		model.put("page", curPage);
		model.put("status", status);
		model.put("cateId", cateId);
		model.put("type", type);
		return new ModelAndView("admin/tools/resPacks", model);
	}

	@RequestMapping("/resPack")
	public ModelAndView pack(Long id) {
		List<DBObject> cates = resService.findResPackCate(Const.STATUS_UP, 0,
				100);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("cates", cates);
		if (id == null) {
			return new ModelAndView("admin/tools/resPack", m);
		}
		DBObject dbo = resService.findResPackById(id);
		m.put("obj", dbo);
		return new ModelAndView("admin/tools/resPack", m);
	}

	@ResponseBody
	@RequestMapping(value = "/resPack", method = RequestMethod.POST)
	public ReMsg saveResPack(Long id, Integer type, String name, String cover,
			Integer status, Integer sort, Long cate, String pics, String names,
			String tags) {
		List<Res> reses = null;
		if (pics != null && names != null) {
			reses = new ArrayList<Res>();
			String n[] = names.split(",");
			String p[] = pics.split(",");
			if (n.length == p.length) {
				for (int i = 0; i < n.length; i++) {
					if (StringUtils.isNotBlank(n[i]))
						reses.add(new Res(n[i], p[i]));
				}
			} else {
				return new ReMsg(ReCode.FAIL);
			}
		} else {
			return new ReMsg(ReCode.FAIL);
		}
		String[] ts = null;
		if (pics != null && names != null) {
			ts = tags.split(",");
		}
		System.out.println("cover--->" + cover);
		return resService.saveResPack(id, type, name, cover, status, sort,
				cate, reses, ts);
	}

	// tools
	@RequestMapping("/resPackCates")
	public ModelAndView reses(Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = resService
				.queryResPackCate(status, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		return new ModelAndView("admin/tools/reses", model);
	}

	@RequestMapping("/resPackCate")
	public ModelAndView res(Long id) {
		if (id == null) {
			return new ModelAndView("admin/tools/res");
		}
		DBObject dbo = resService.findResPackCateById(id);
		return new ModelAndView("admin/tools/res", "obj", dbo);
	}

	@ResponseBody
	@RequestMapping(value = "/resPackCate", method = RequestMethod.POST)
	public ReMsg saveRes(Long id, String name, Integer status, Integer sort) {
		return resService.saveResPackCate(id, name, status, sort);
	}

}
