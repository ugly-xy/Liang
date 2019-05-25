package com.zb.web.view.admin;

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
import com.zb.common.mongo.Op;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.res.ZbToolCate;
import com.zb.service.ZbToolsService;

@Controller
@RequestMapping("/admin")
public class AdminToolsCtl {

	static final Logger log = LoggerFactory.getLogger(AdminToolsCtl.class);

	@Autowired
	ZbToolsService zbToolsService;

	@RequestMapping("/toolCates")
	public ModelAndView toolCates(Integer type, Long pid, Integer hasSub,
			Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		log.debug(type + "," + status);
		Page<DBObject> curPage = zbToolsService.queryZbToolCate(type, pid,
				hasSub, status, page, size, null);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("type", type);
		model.put("pid", pid);
		return new ModelAndView("admin/tools/cates", model);
	}

	@RequestMapping("/toolCate")
	public ModelAndView cate(Long id) {
		DBObject dbo = zbToolsService.findZbToolCateById(id);
		return new ModelAndView("admin/tools/cate", "cate", dbo);
	}

	// @ResponseBody
	// @RequestMapping("/toolCateUpdate")
	// public ReMsg updateToolCates(Long id, String name, String cover,
	// Integer status, Integer sort, Integer type, Long parentId,
	// Integer hasSub, String namePic, String mark) {
	//
	// }

	// @RequestMapping(value = "/toolCate", method = RequestMethod.POST)
	// public ModelAndView addToolCate() {
	// return new ModelAndView("admin/tools/cate");
	// }

	@ResponseBody
	@RequestMapping(value = "/toolCate", method = RequestMethod.POST)
	public ReMsg doAddToolCate(Long id, String name, String cover,
			Integer status, Integer sort, Integer type, Long parentId,
			Integer hasSub, String namePic, String mark) {
		if (id != null && id > 0) {
			ZbToolCate zbToolCate = new ZbToolCate(id, name, cover, status,
					sort, type, parentId, hasSub, namePic, mark);
			return zbToolsService.updateZbToolCate(zbToolCate);
		}
		return zbToolsService.saveZbToolCate(name, cover, status, sort, type,
				parentId, hasSub, namePic, mark);
	}

	// tools
	@RequestMapping("/tools")
	public ModelAndView tools(Long cate, Integer status, Integer index,
			Integer server, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = zbToolsService.queryZbTools(null, status,
				index, cate, page, size, null, server);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("cate", cate);
		model.put("index", index);
		model.put("cates", zbToolsService.findZbToolCate(ZbToolCate.TOOL_V2,
				0L, 0, Const.STATUS_DEL, 0, 50, Op.GT));
		return new ModelAndView("admin/tools/tools", model);
	}

	@RequestMapping("/tool")
	public ModelAndView tool(Long id) {
		List<DBObject> cs = zbToolsService.findZbToolCate(ZbToolCate.TOOL_V2,
				0L, 0, Const.STATUS_DEL, 0, 50, Op.GT);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("cas", cs);
		if (id == null) {
			return new ModelAndView("admin/tools/tool", m);
		}
		DBObject dbo = zbToolsService.findZbToolById(id);
		m.put("obj", dbo);
		return new ModelAndView("admin/tools/tool", m);
	}

	@ResponseBody
	@RequestMapping(value = "/tool", method = RequestMethod.POST)
	public ReMsg saveTool(Long id, String name, String logo, Integer status,
			Integer hidden, Integer sort, String cate, String tmpBackPic,
			String mark, String tmpBackPicIos, String ext, int point,
			String tag, Integer server, String description, String otherPics,
			String style, String draw, String clazz,Integer type) {
		String tags[] = null;
		if (StringUtils.isNotBlank(tag)) {
			tags = tag.split(",");
		}
		Long[] cateIds = null;
		if (StringUtils.isNotBlank(cate)) {
			String[] cates = cate.split(",");
			cateIds = new Long[cates.length];
			for (int i = 0; i < cates.length; i++) {
				cateIds[i] = Long.valueOf(cates[i]);
			}
		}
		String[] otherPicArr = null;
		if (StringUtils.isNotBlank(otherPics)) {
			otherPicArr = otherPics.split(",");
		}
		if (id != null && id > 0) {
			return zbToolsService.updateZbTool(id, name, logo, status, hidden,
					sort, cateIds, tmpBackPic, mark, tmpBackPicIos, point,
					tags, server, description, otherPicArr, style, draw, clazz,type);
		} else {
			return zbToolsService.saveZbTool(name, logo, status, hidden, sort,
					cateIds, tmpBackPic, mark, tmpBackPicIos, point, tags,
					server, description, otherPicArr, style, draw, clazz,type);
		}
	}

	@RequestMapping("/exts")
	public ModelAndView exts(String key, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = zbToolsService.queryToolExt(key, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("key", key);
		return new ModelAndView("admin/tools/exts", model);
	}

	@RequestMapping("/ext")
	public ModelAndView ext(String id, String json, Integer page, Integer size,
			HttpServletRequest request) {
		zbToolsService.saveToolExt(id, json);
		Page<DBObject> curPage = zbToolsService.queryToolExt(null, page, size);

		curPage.setUrl("/admin/exts");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		return new ModelAndView("admin/tools/exts", model);
	}

	@RequestMapping("/delext")
	public ModelAndView delExt(String id, Integer page, Integer size,
			HttpServletRequest request) {
		zbToolsService.delToolExt(id);
		Page<DBObject> curPage = zbToolsService.queryToolExt(null, page, size);
		curPage.setUrl("/admin/exts");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		return new ModelAndView("admin/tools/exts", model);
	}
}
