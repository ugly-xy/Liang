package com.zb.web.view.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.core.Page;
import com.zb.service.ZbToolUseService;
import com.zb.service.ZbToolsService;

@Controller
@RequestMapping("/admin/st")
public class StCtl {

	static final Logger log = LoggerFactory.getLogger(StCtl.class);

	@Autowired
	ZbToolUseService zbToolUseService;

	@Autowired
	ZbToolsService zbToolsService;

	// tools
	@RequestMapping("/toolUsed")
	public ModelAndView feedbacks(Integer page, Integer size,
			HttpServletRequest request) {
		Long total = zbToolUseService.toolUseCount(0L);
		
		Page<DBObject> curPage = zbToolsService.queryZbToolsSortByCount(null, null, null, null, page, size, null, null);
		
//		Page<DBObject> curPage = zbToolUseService.queryZbTools(page, size);
//		Long[] ids = new Long[curPage.getList().size()];
//		for (int i = 0; i < curPage.getList().size(); i++) {
//			ids[i] = DboUtil.getLong(curPage.getList().get(i), "_id");
//		}
//		Map<Long, DBObject> map = zbToolsService.findZbToolNameByIds(ids);
//		List<DBObject> res = new ArrayList<DBObject>();
//		for (DBObject dbo : curPage.getList()) {
//			Long _id = DboUtil.getLong(dbo, "_id");
//			String name = "总装逼次数";
//			String status = "上线";
//			if (_id != 0) {
//				DBObject tool = map.get(_id);
//				if (tool != null) {
//					name = DboUtil.getString(tool, "name");
//					int tstatus = DboUtil.getInt(tool, "status");
//					if (tstatus == Const.STATUS_HIDDEN) {
//						status = "隐藏";
//					} else if (tstatus == Const.STATUS_DOWN) {
//						status = "下线";
//					} else if (tstatus == Const.STATUS_DEL) {
//						status = "删除";
//					}
//				}
//			}
//			dbo.put("name", name);
//			dbo.put("status", status);
//			res.add(dbo);
//		}
//		curPage.setList(res);
//		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("total", total);
		return new ModelAndView("admin/st/toolUsed", model);
	}

}
