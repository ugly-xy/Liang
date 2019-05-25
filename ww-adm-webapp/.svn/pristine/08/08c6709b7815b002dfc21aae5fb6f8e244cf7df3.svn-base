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
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.RechargeShoutService;
import com.zb.service.UserService;
import com.zb.service.article.ArticleService;

@Controller
@RequestMapping("/admin")
public class AdminShoutCtl {

	static final Logger log = LoggerFactory.getLogger(AdminShoutCtl.class);

	@Autowired
	ArticleService articleService;

	@Autowired
	UserService userService;

	@Autowired
	RechargeShoutService reChargeShoutService;

	@RequestMapping("/shouts")
	public ModelAndView shouts(Long uid, Integer all, Integer reply, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = reChargeShoutService.adminQueryShout(uid, page, size, all, reply);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("uid", uid);
		model.put("all", all);
		model.put("reply", reply);
		model.put("size", size);
		return new ModelAndView("admin/shout/shouts", model);
	}

	@RequestMapping("/shout")
	public ModelAndView shout(Long id) {
		if(id!=null){
			DBObject dbo  = reChargeShoutService.findById(id);
			return new ModelAndView("admin/shout/shout","obj",dbo);
		}
		return new ModelAndView("admin/shout/shout");
	}

	@RequestMapping(value = "/shout", method = RequestMethod.POST)
	public @ResponseBody ReMsg shout(Long id,Long uid, Integer all, Integer reply, String content, Integer vip, Integer combo) {
		if(id==null){
			combo = null == combo ? 1 : combo;
			return reChargeShoutService.adminShout(uid, content, all, reply, combo, vip);
		}else{
			return reChargeShoutService.update(id, content, all);
		}
	}

}
