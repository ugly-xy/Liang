package com.zb.web.view.admin;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.zb.common.utils.DateUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.article.Group;
import com.zb.models.article.Topic;
import com.zb.models.goods.BaseGoods;
import com.zb.models.goods.BaseGoods.AmountType;
import com.zb.models.goods.BaseGoods.GoodsType;
import com.zb.models.goods.BaseGoods.Unit;
import com.zb.service.GiftLogService;
import com.zb.service.GoodsService;
import com.zb.service.PropLogService;
import com.zb.service.RankListService;
import com.zb.service.UserPackService;
import com.zb.service.article.ArticleGroupService;
import com.zb.service.article.ArticleService;
import com.zb.service.article.ArticleTopicService;
import com.zb.service.jobs.StJob;

@Controller
@RequestMapping("/admin")
public class AdminGoodsCtl {

	static final Logger log = LoggerFactory.getLogger(AdminGoodsCtl.class);

	@Autowired
	GoodsService goodsService;

	@Autowired
	UserPackService userPackService;
	@Autowired
	RankListService rankListService;
	@Autowired
	PropLogService propLogService;
	@Autowired
	GiftLogService giftLogService;
	@Autowired
	ArticleService articleService;
	@Autowired
	ArticleGroupService groupService;
	@Autowired
	ArticleTopicService articleTopicService;

	// 所有
	@RequestMapping("/baseGoodses")
	public ModelAndView goodses(String goodsType, Integer page, Integer size, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("goodsType", goodsType);
		model.put("gts", GoodsType.values());
		model.put("ggts", AmountType.values());
		model.put("units", Unit.values());
		if (StringUtils.isNotBlank(goodsType)) {
			List<BaseGoods> bl = goodsService.getBaseGoods(goodsType);
			model.put("list", bl);
		}
		return new ModelAndView("admin/goods/baseGoodses", model);
	}

	@RequestMapping("/goodses")
	public ModelAndView goodses(Integer baseId, Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = goodsService.query(baseId, status, page, size, null);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("baseId", baseId);
		return new ModelAndView("admin/goods/goodses", model);
	}

	// 背包物品统计
	@RequestMapping("/userPacks")
	public ModelAndView userPackes(Integer type, Long baseId, Integer status, Long userId, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = userPackService.query(type, baseId, userId, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("baseId", baseId);
		model.put("userId", userId);
		model.put("type", type);
		return new ModelAndView("admin/goods/userPacks", model);
	}

	// 背包物品获得纪录
	@RequestMapping("/userPackLogs")
	public ModelAndView banner(Long uid, Integer bgId, Integer op, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = userPackService.queryUserPackLog(uid, bgId, op, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("uid", uid);
		model.put("bgId", bgId);
		model.put("op", op);
		return new ModelAndView("admin/goods/userPackLogs", model);
	}

	// 物品使用明细
	@RequestMapping("/propLogs")
	public ModelAndView banner(Long uid, Integer bgId, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = propLogService.queryPropLog(uid, bgId, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("uid", uid);
		model.put("bgId", bgId);
		return new ModelAndView("admin/goods/propLogs", model);
	}

	// 物品赠送明细
	@RequestMapping("/giftLogs")
	public ModelAndView banner(Long sendUid, Long recvUid, Integer bgId, Integer local, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = giftLogService.queryGiftLog(sendUid, recvUid, bgId, local, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("sendUid", sendUid);
		model.put("recvUid", recvUid);
		model.put("bgId", bgId);
		model.put("local", local);
		return new ModelAndView("admin/goods/giftLogs", model);
	}

	@RequestMapping("/goods")
	public ModelAndView goods(Long id) {
		DBObject dbo = goodsService.findById(id);
		return new ModelAndView("admin/goods/goods", "obj", dbo);
	}

	@ResponseBody
	@RequestMapping(value = "/goods", method = RequestMethod.POST)
	public ReMsg update(Long id, String name, String img, Integer baseId, Integer listPrice, Integer price, Long amount,
			String startTime, String endTime, Integer status, Integer sort, String unit) {
		if (listPrice == null) {
			new ReMsg(ReCode.PARAMETER_ERR);
		} else {
			listPrice = listPrice * 100;
		}

		if (price == null) {
			new ReMsg(ReCode.PARAMETER_ERR);
		} else {
			price = price * 100;
		}

		Long sT;
		Long eT;
		if (StringUtils.isBlank(startTime)) {
			sT = System.currentTimeMillis();
		} else {
			try {
				sT = DateUtil.convertDate(startTime, "yyyy-MM-dd").getTime();
			} catch (ParseException e) {
				sT = System.currentTimeMillis();
			}
		}

		if (StringUtils.isBlank(endTime)) {
			eT = System.currentTimeMillis() + Const.YEAR * 99;
		} else {
			try {
				eT = DateUtil.convertDate(endTime, "yyyy-MM-dd").getTime();
			} catch (ParseException e) {
				eT = System.currentTimeMillis() + Const.YEAR * 99;
			}
		}
		if (id == null || id == 0) {
			return goodsService.save(name, img, listPrice, price, sT, eT, status, sort, 0, 0, null);
		} else {
			return goodsService.update(id, name, img, listPrice, price, amount, sT, eT, status, sort);
		}
	}

	// 魅力花魁榜
	@RequestMapping("/rankList")
	// 时间 日期类型 榜单类型
	public ModelAndView rankList(String st, Integer day, Integer type, Integer bgId, Integer page, Integer size,
			HttpServletRequest request) {
		if (null == page) {
			page = 1;
		}
		;
		if (null == size) {
			size = 30;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("st", st);
		model.put("day", day);
		model.put("type", type);
		model.put("bgId", bgId);

		Calendar c = Calendar.getInstance();
		if (StringUtils.isBlank(st)) {
			c.setTimeInMillis(System.currentTimeMillis());
		} else {
			try {
				c.setTimeInMillis(DateUtil.convertDate(st, "yyyy-MM-dd").getTime());
			} catch (ParseException e) {
				c.setTimeInMillis(System.currentTimeMillis());
				log.error("date--err", e);
			}
		}

		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		int w = c.get(Calendar.WEEK_OF_YEAR);
		if (null == day || 1 == day) {
			day = StJob.getDay(y, m, d);
		} else {
			if (7 == day) {
				day = y * 100 + w;
			} else if (30 == day) {
				day = y * 100 + m;
			} else {
				day = 0;
			}
		}
		Page<DBObject> curPage = rankListService.query(day, bgId, page, size, type, true);
		model.put("page", curPage);
		return new ModelAndView("admin/st/rankList", model);
	}

	@RequestMapping("/rankListArticle")
	public ModelAndView rankListArticle(String st, String et, Long groupId, Long topicId, Long userId, Integer page,
			Integer size, HttpServletRequest request) throws ParseException {
		long startTime = 0;
		long endTime = 0;
		if (StringUtils.isNotBlank(st)) {
			startTime = DateUtil.getZeroTime(DateUtil.convertDate(st, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(et)) {
			endTime = DateUtil.getZeroTime(DateUtil.convertDate(et, "yyyy-MM-dd")) + DateUtil.DAY;
		}

		List<DBObject> groups = groupService.findGroups(Group.UP, 1, 10);
		List<DBObject> topics = new ArrayList<DBObject>();
		if (null != groupId && groupId != 0) {
			topics = articleTopicService.queryTopic(groupId, null, null, Topic.TOPIC_UP, null, null, true).getList();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("groups", groups);
		model.put("groupId", groupId);
		model.put("topicId", topicId);
		model.put("topics", topics);
		model.put("userId", userId);
		model.put("st", st);
		model.put("et", et);
		model.put("page", articleService.articleFlower(startTime, endTime, groupId, topicId, userId, page, size));
		return new ModelAndView("admin/st/rankListArticle", model);
	}

	// 文章收花榜
	@RequestMapping("/articleGift")
	public ModelAndView articleGift(Long aid, Integer bgId, Integer page, Integer size, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("aid", aid);
		model.put("bgId", bgId);
		model.put("page", goodsService.adminArticleGift(aid, bgId, page, size));
		return new ModelAndView("admin/st/articleGift", model);
	}

	// 用户收花榜
	@RequestMapping("/userGift")
	public ModelAndView userGift(String st, Long uid, Integer day, Integer bgId, Integer page, Integer size,
			HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("st", st);
		model.put("day", day);
		model.put("uid", uid);
		model.put("bgId", bgId);
		Calendar c = Calendar.getInstance();
		if (StringUtils.isBlank(st)) {
			c.setTimeInMillis(System.currentTimeMillis());
		} else {
			try {
				c.setTimeInMillis(DateUtil.convertDate(st, "yyyy-MM-dd").getTime());
			} catch (ParseException e) {
				c.setTimeInMillis(System.currentTimeMillis());
				log.error("date--err", e);
			}
		}

		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		int w = c.get(Calendar.WEEK_OF_YEAR);
		if (null == day || 1 == day) {
			day = StJob.getDay(y, m, d);
		} else {
			if (7 == day) {
				day = y * 100 + w;
			} else if (30 == day) {
				day = y * 100 + m;
			} else {
				day = 0;
			}
		}
		model.put("page", goodsService.adminUserGift(uid, day, bgId, page, size));
		return new ModelAndView("admin/st/userGift", model);
	}
}
