package com.we.web.view.m;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.we.common.Constant.Const;
import com.we.common.Constant.ReCode;
import com.we.core.web.ReMsg;
import com.we.models.userTask.Task;
import com.we.service.BannerService;
import com.we.service.EnvService;
import com.we.service.NoticeService;
import com.we.service.UserWalletService;
import com.we.service.article.ArticleService;
import com.we.service.userTask.UserTaskService;

@Controller
public class ChannelCtl extends BaseCtl {

//	@Autowired
//	UserService userService;

//	@Autowired
//	AuthService authService;

	@Autowired
	BannerService bannerService;

	@Autowired
	NoticeService noticeService;

	@Autowired
	ArticleService articleService;

//	@Autowired
//	DataViewService dataViewService;

	@Autowired
	UserWalletService userWalletService;


//	@Autowired
//	UserShareService userShareService;

//	@Autowired
//	UserDivisionService userDivisionService;

//	@Autowired
//	UserIdentityService userIdentityService;


	@Autowired
	UserTaskService userTaskService;
	
	@Autowired
	EnvService envService;

//	@Autowired
//	TokenPrjService coinTypeService;

	/** 活动主界面 */
	@RequestMapping("/")
	public ModelAndView mainView(HttpServletRequest req) {
		Map<String, Object> res = super.getCommonMap();
		// 两个公告
		res.put("notices", noticeService.find(Const.STATUS_OK, 1, 2));
		// 三个新闻
		res.put("news", articleService.queryArticles(null, 1, 3, null, null).getList());
		// banner index
		res.put("banners", bannerService.findBanner(BannerService.TYPES[0], Const.STATUS_OK, 1, 10));
		// dataView
//		res.put("dataView", dataViewService.findOne());
		return new ModelAndView("index", res);
	}

	

	/** 新闻页面 */
	@RequestMapping(value = "/news")
	public ModelAndView news(Integer page, Integer size, HttpServletRequest req) {
		if (size == null || size == 0) {
			size = 10;
		}
		Map<String, Object> res = super.getCommonMap();
		// 新闻
		res.put("news", articleService.queryArticles(null, page, size, null, null));
		// 公告
		res.put("notices", noticeService.find(Const.STATUS_OK, 1, 10));
		// banner
		res.put("banners", bannerService.findBanner(BannerService.TYPES[2], Const.STATUS_OK, 1, 1));
		return new ModelAndView("news/news", res);
	}
	
	/** 获取一条新闻的详细信息 */
	@RequestMapping("/article")
	public ModelAndView news(Long id) {
		Map<String, Object> res = super.getCommonMap();
		res.put("new", articleService.readArticle(id));
		return new ModelAndView("news/newsInfo", res);
	}

	/** 任务页面 */
	@RequestMapping(value = "/task")
	public ModelAndView task(Integer type,Long taskId,HttpServletRequest req) {
		Map<String, Object> res = super.getCommonMap();
		//long uid = super.getUid();
		res.put("tasks", userTaskService.getUserTaskList(type));
		res.put("type", type);
		res.put("types", Task.TastType.values());
		res.put("coinPics", userWalletService.queryCoinPics());
		if(taskId==null) {
			taskId = 0L;
		}
		res.put("taskId", taskId);
		return new ModelAndView("task/task", res);
	}

	

	/** 商务合作界面 */
	@RequestMapping(value = "/business")
	public ModelAndView business(HttpServletRequest req) {
		Map<String, Object> res = super.getCommonMap();
		return new ModelAndView("business/business",res);
	}
	
	/** tips */
	@RequestMapping(value = "/tips")
	public ModelAndView tips(HttpServletRequest req) {
		return new ModelAndView("tips/tips");
	}

	/** 获取banner */
	@ResponseBody
	@RequestMapping("/banners")
	public ReMsg banner(Integer via, Integer type) {
		List<DBObject> p = bannerService.findBanner(BannerService.TYPES[type], Const.STATUS_OK, 0, 10);
		return new ReMsg(ReCode.OK, p);
	}
	
	/** 获取公告 */
	@ResponseBody
	@RequestMapping("/notices")
	public ReMsg notices(Integer page, Integer size) {
		return new ReMsg(ReCode.OK, noticeService.find(Const.STATUS_OK, page, size));
	}

	/** 获取一条公告的详细信息 */
	@RequestMapping("/notice")
	public ModelAndView notice(Long id) {
		Map<String, Object> res = super.getCommonMap();
		res.put("notice", noticeService.findById(id));
		return new ModelAndView("news/newsInfo", res);
	}
	
	/** 获取一条公告的详细信息 */
	@RequestMapping("/reCAPTCHA")
	public ModelAndView reCAPTCHA() {
		Map<String, Object> res = super.getCommonMap();
		return new ModelAndView("reCAPTCHA", res);
	}
	
	/** 获取一条公告的详细信息 */
	@RequestMapping("/chat")
	public ModelAndView socketTest() {
		Map<String, Object> res = super.getCommonMap();
		String ws = envService.getString("ws.url");
		if(ws==null) {
			ws = "wss://ws.candy.club/websocket";
		}
		res.put("ws", ws);
		return new ModelAndView("socket/chat", res);
	}

}
