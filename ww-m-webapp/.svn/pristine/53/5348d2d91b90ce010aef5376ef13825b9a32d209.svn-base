package com.zb.web.view.m;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.DateUtil;
import com.zb.core.Page;
import com.zb.models.article.Article;
import com.zb.service.UserService;
import com.zb.service.article.ArticleCommentService;
import com.zb.service.article.ArticleService;

@Controller
public class ArticleCtl {

	@Autowired
	ArticleService articleService;

	@Autowired
	ArticleCommentService articleCommentService;

	//
	// @Autowired
	// BannerService bannerService;
	//
	// @Autowired
	// ResService resService;

	private static String LINKEDKEY = "5436806990c729f8b520eb2817694215";
	private static String TYPE = "live";
	
	@RequestMapping("/article/{id}")
	public ModelAndView article(@PathVariable Long id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("key", LINKEDKEY);
		rs.put("type", TYPE);
		// banners
		DBObject obj = articleService.readArticle(id);
		if (obj == null || DboUtil.getInt(obj, "status") < Article.PERSONAL) {
			return new ModelAndView("redirect:/404.html");
		}

		rs.put("article", compLv(obj));
		List<DBObject> tops = articleCommentService.getTopComments(id, 3);
		if (tops.size() > 0) {
			tops = compLvs(tops);
			rs.put("tops", tops);
		}
		Page<DBObject> news = articleCommentService.query(0L, id,
				Const.STATUS_OK, 0, 5, null);
		List<DBObject> curList = compLvs(news.getList());
		news.setList(curList);
		rs.put("news", news);
		rs.put("date", DateUtil.dateFormatyyyyMMdd(new Date()));
		return new ModelAndView("html/article/detail", rs);
	}

	private List<DBObject> compLvs(List<DBObject> dbos) {
		List<DBObject> res = new ArrayList<DBObject>();
		for (DBObject dbo : dbos) {
			res.add(compLv(dbo));
		}
		return res;
	}

	private DBObject compLv(DBObject dbo) {
		if (dbo != null) {
			dbo.put("lvPic", "/images/"+UserService.getLevel(DboUtil.getInt(dbo, "point"))+"@2x.png");
			return dbo;
		}
		return null;
	}

}
