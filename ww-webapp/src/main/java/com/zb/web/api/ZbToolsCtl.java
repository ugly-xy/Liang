package com.zb.web.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.mongo.Op;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.ZbToolUseService;
import com.zb.service.ZbToolsService;

@Controller
@RequestMapping("/tool")
public class ZbToolsCtl extends BaseCtl {

	@Autowired
	ZbToolsService zbToolsService;

	@Autowired
	ZbToolUseService zbToolUseService;

	/**
	 * 工具总使用数
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/usedCount")
	public ReMsg usedCount(Long toolId) {
		return new ReMsg(ReCode.OK, zbToolUseService.toolUseCount(toolId));
		// return new ReMsg(ReCode.OK,
		// envService.getInt(EnvService.TOOL_USED_COUNT));
	}

	/**
	 * 工具使用详情
	 * 
	 * @param tid
	 * @param mark
	 * @param model
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/use")
	public ReMsg useTool(Long tid, String mark, Integer model,
			HttpServletRequest req) {
		return zbToolsService.useZbTool(tid, mark, model, req);
	}

	@ResponseBody
	@RequestMapping("/cates/{type}")
	public ReMsg index(@PathVariable Integer type, Long pid, Integer page,
			Integer size) {
		if (null == pid)
			pid = 0L;
		Page<DBObject> p = zbToolsService.queryZbToolCate(type, pid, 0,
				Const.STATUS_HIDDEN, page, size, Op.GTE);
		return new ReMsg(ReCode.OK, p);
	}

	@ResponseBody
	@RequestMapping("/tools")
	public ReMsg cate(Long cid, int page, int size) {
		// size = 100;
		List<DBObject> dbos = zbToolsService.findZbTool(null,
				Const.STATUS_HIDDEN, cid, page, 100, Op.GTE, null);
		List<DBObject> rts = new ArrayList<DBObject>();
		for (DBObject dbo : dbos) {
			Integer hidden = DboUtil.getInt(dbo, "hidden");
			if (null != hidden
					&& 1 == hidden
					&& StringUtils.isNotBlank(DboUtil.getString(dbo,
							"tmpBackPicIos"))) {
				dbo.put("tmpBackPic", DboUtil.getString(dbo, "tmpBackPicIos"));
			}
			rts.add(dbo);
		}
		return new ReMsg(ReCode.OK, rts);
	}

	@ResponseBody
	@RequestMapping("/tool")
	public ReMsg tool(Long id) {
//		if (super.getUid() < 1) {
//			return new ReMsg(ReCode.NOT_AUTHORIZED);
//		}
		DBObject dbo = zbToolsService.findZbToolById(id);
		Integer hidden = DboUtil.getInt(dbo, "hidden");
		if (null != hidden
				&& 1 == hidden
				&& StringUtils.isNotBlank(DboUtil.getString(dbo,
						"tmpBackPicIos"))) {
			dbo.put("tmpBackPic", DboUtil.getString(dbo, "tmpBackPicIos"));
		}
		return new ReMsg(ReCode.OK, dbo);
	}

	@ResponseBody
	@RequestMapping("/zhengjian")
	public ReMsg jiangZhuang(Long id, String json, HttpServletRequest req) {
		return zbToolsService.zhengjian(id, json, req);
	}

	@ResponseBody
	@RequestMapping("/create")
	public ReMsg serverCreate(Long id, String json, HttpServletRequest req) {
		return zbToolsService.toolServerCreate(id, json, req);
	}

	@ResponseBody
	@RequestMapping("/ext")
	public ReMsg toolExt(String id) {
		return zbToolsService.getToolExt(id);
	}

	@ResponseBody
	@RequestMapping("/search")
	public ReMsg toolSearch(String qtxt, Integer page, Integer size) {
		return new ReMsg(ReCode.OK, zbToolsService.searchZbTools(qtxt, page,
				size));
	}

	@ResponseBody
	@RequestMapping("/searchRecommend")
	public ReMsg toolSearch() {
		return new ReMsg(ReCode.OK, zbToolsService.searchRecommend());
	}

	@ResponseBody
	@RequestMapping("/tag")
	public ReMsg toolTag(String name, Integer page, Integer size) {
		return new ReMsg(ReCode.OK, zbToolsService.queryZbTools(name,
				Const.STATUS_UP, 0, 0L, page, size, null, 0));
	}

}
