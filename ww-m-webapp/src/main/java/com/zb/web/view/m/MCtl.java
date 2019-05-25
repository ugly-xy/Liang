package com.zb.web.view.m;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.Constant.Via;
import com.zb.common.http.CookieUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.core.Page;
import com.zb.core.conf.Config;
import com.zb.core.web.ReMsg;
import com.zb.models.paper.Paper;
import com.zb.models.paper.Result;
import com.zb.models.res.ZbTool;
import com.zb.models.res.ZbToolCate;
import com.zb.service.AuthService;
import com.zb.service.BannerService;
import com.zb.service.PaperService;
import com.zb.service.ResService;
import com.zb.service.ZbToolsService;

@Controller
public class MCtl {

	@Autowired
	AuthService authService;

	@Autowired
	ZbToolsService zbToolsService;

	@Autowired
	BannerService bannerService;

	@Autowired
	ResService resService;
	
	@Autowired
	PaperService paperService;

	
	
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public ModelAndView indexs(Integer page, Integer size,
			HttpServletRequest request) {
		return index(page, size, request);
	}

	@RequestMapping("/index")
	public ModelAndView index(Integer page, Integer size,
			HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		// banners
		List<DBObject> banners = bannerService
				.findBanner(BannerService.TYPES[0], Const.STATUS_UP,
						Via.Web.getVia(), 0, 5);
		rs.put("banners", banners);

		// new Tools
		List<DBObject> newTools = zbToolsService.findZbTool(ZbTool.DEF_TAG_NEW,
				Const.STATUS_UP, 0L, page, size, null, ZbTool.SERVER_TRUE);
		rs.put("newTools", newTools);

		Page<DBObject> curPage = zbToolsService.queryZbTools(null,
				Const.STATUS_UP, 1, 0L, page, size, null, ZbTool.SERVER_TRUE);
		curPage.setUrl(request.getRequestURI());
		rs.put("page", curPage);

		// all Tools
		List<DBObject> tools = zbToolsService.findZbTool(null, Const.STATUS_UP,
				0L, 0, 30, null, ZbTool.SERVER_TRUE);
		rs.put("tools", tools);

		List<DBObject> cates = zbToolsService.findZbToolCate(
				ZbToolCate.TOOL_V2, 0L, ZbToolCate.HAS_NOT_SUB,
				Const.STATUS_UP, 0, 10, null);
		rs.put("cates", cates);
		return new ModelAndView("html/index", rs);
	}

	@RequestMapping("/tool/cate")
	public ModelAndView cate(Long id, Integer page, Integer size,
			HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		// new Tools
		List<DBObject> newTools = zbToolsService.findZbTool(null,
				Const.STATUS_UP, id, page, size, null, ZbTool.SERVER_TRUE);
		rs.put("newTools", newTools);
		
		Page<DBObject> curPage = zbToolsService.queryZbTools(null,
				Const.STATUS_UP, 1, id, page, size, null, ZbTool.SERVER_TRUE);
		curPage.setUrl(request.getRequestURI());
		rs.put("page", curPage);
		
		List<DBObject> cates = zbToolsService.findZbToolCate(
				ZbToolCate.TOOL_V2, 0L, ZbToolCate.HAS_NOT_SUB,
				Const.STATUS_UP, 0, 10, null);
		rs.put("cates", cates);
		String title = "工具";
		for (DBObject dbo : cates) {
			if (id == DboUtil.getLong(dbo, "_id")) {
				title = DboUtil.getString(dbo, "name");
				break;
			}
		}
		rs.put("title", title);
		return new ModelAndView("html/toolCate", rs);
	}

	@RequestMapping("/tool")
	public ModelAndView tool(Long id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		DBObject dbo = zbToolsService.findZbToolById(id);
		rs.put("tool", dbo);
		ReMsg mark = zbToolsService.getToolExt(DboUtil.getString(dbo, "mark"));

		// System.out.println(mark.getData());

		rs.put("mark", mark.getData());
		return new ModelAndView("html/tool", rs);
	}

	@RequestMapping(value = "/tool", method = RequestMethod.POST)
	public ModelAndView toolShow(HttpServletRequest request) {
		ReMsg r = zbToolsService.toolServerCreate(request);
		Map<String, Object> m = null;
		if(r.getCode()==ReCode.OK.reCode()){
			m = (Map<String, Object>) r.getData();
		}else {
			m = new HashMap<String,Object>();
		}
		return new ModelAndView("html/toolShow", m);
	}

	
	@RequestMapping(value = "/papers")
	public ModelAndView papers(Long id,Integer type,String title,String content,Integer pay,
			Integer sort,Integer status, Integer page,
			Integer size, HttpServletRequest request) {
		Page<DBObject> curPage  =paperService.queryPaper(id, type, pay, Paper.UP, page, size);
		
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("type", type);
		model.put("id", id);
		model.put("pay", pay);
		return new ModelAndView("html/papers", model);
	}
	//@ResponseBody
	@RequestMapping(value = "/paper")
	public ModelAndView paper(Long id){
		
		DBObject dbo = paperService.findById(id);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("dbo", dbo);
		model.put("questions", dbo.get("questions"));
		return new ModelAndView("html/paper", model);
	}
	@RequestMapping(value = "/result")
	public ModelAndView result(Long id,String score,Integer type,String title,String content,Integer pay,
			Integer sort,Integer status, Integer page,
			Integer size, HttpServletRequest request){
		
		DBObject dbo = paperService.findById(id);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("dbo", dbo);
		model.put("score", score);
		List<DBObject> list = paperService.find(null, type, pay, Paper.UP, page, size);
		int count = 4;
		int[] a = new int[count];  
	    // 利于此数组产生随机数  
	    int[] b = new int[list.size()];  
	    int size2 = list.size();  
	  
	    // 取样填充至数组a满  
	    for (int i = 0; i < count; i++) {  
	        int num = (int) (Math.random() * size2); // [0,size)  
	        int where = -1;  
	        for (int j = 0; j < b.length; j++) {  
	            if (b[j] != -1) {  
	                where++;  
	                if (where == num) {  
	                    b[j] = -1;  
	                    a[i] = j;  
	                }  
	            }  
	        }  
	        size2 = size2 - 1;  
	    }  
	    // a填满后 将数据加载到rslist  
	    List<Object> rslist = new ArrayList<Object>();  
	    for (int i = 0; i < count; i++) {  
	        Object df = (Object) list.get(a[i]);  
	        rslist.add(df);  
	    } 
		//System.out.println(rslist.size());
		model.put("rslist", rslist);
		return new ModelAndView("html/result", model);
	}
	
}
