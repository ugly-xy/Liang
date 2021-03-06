package com.zb.web.view.admin;

import java.text.ParseException;
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
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.article.Article;
import com.zb.models.article.Group;
import com.zb.models.article.Topic;
import com.zb.service.TipOffService;
import com.zb.service.article.ArticleCommentService;
import com.zb.service.article.ArticleGroupService;
import com.zb.service.article.ArticleService;
import com.zb.service.article.ArticleTopicService;

@Controller
@RequestMapping("/admin")
public class AdminArticleCtl {

	static final Logger log = LoggerFactory.getLogger(AdminArticleCtl.class);

	@Autowired
	ArticleService articleService;

	@Autowired
	ArticleCommentService articleCommentService;

	@Autowired
	ArticleTopicService articleTopicService;

	@Autowired
	ArticleGroupService articleGroupService;

	@Autowired
	TipOffService tipOffService;
	@Autowired
	ArticleGroupService groupService;

	@RequestMapping("/tipOffSt")
	public ModelAndView tipOffSt(Integer type, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = tipOffService.querySt(type, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("type", type);
		return new ModelAndView("admin/article/tipOffSt", model);
	}

	@RequestMapping("/tipOff")
	public ModelAndView tipOff(Long aid, Long uid, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = tipOffService.query(aid, uid, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("aid", aid);
		model.put("uid", uid);
		return new ModelAndView("admin/article/tipOff", model);
	}

	@RequestMapping("/articles")
	public ModelAndView articles(Long groupId, Long topicId, Integer type, Integer status, Long userId, Integer essence,
			Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = articleService.query(groupId, userId, type, status, essence, Article.AUDITED_N, page,
				size, true, null, topicId, null, null, null);
		curPage.setUrl(request.getRequestURI());
		List<DBObject> groups = groupService.findGroups(Group.UP, 1, 10);
		List<DBObject> topics = new ArrayList<DBObject>();
		if (null != groupId && groupId != 0) {
			topics = articleTopicService.queryTopic(groupId, null, null, Topic.TOPIC_UP, null, null, true).getList();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("groups", groups);
		model.put("page", curPage);
		model.put("status", status);
		model.put("type", type);
		model.put("userId", userId);
		model.put("audited", Article.AUDITED_N);
		model.put("essence", essence);
		model.put("groupId", groupId);
		model.put("topicId", topicId);
		model.put("topics", topics);
		model.put("nav_id", "article");
		return new ModelAndView("admin/article/articles", model);
	}

	@RequestMapping("/articles/audited")
	public ModelAndView articlesA(Long groupId, Integer type, Integer status, Long userId, Integer essence,
			Long topicId, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = articleService.query(groupId, userId, type, status, essence, Article.AUDITED_Y, page,
				size, true, null, topicId, null, null, null);
		curPage.setUrl(request.getRequestURI());
		List<DBObject> groups = groupService.findGroups(Group.UP, 1, 10);
		List<DBObject> topics = new ArrayList<DBObject>();
		if (null != groupId && groupId != 0) {
			topics = articleTopicService.queryTopic(groupId, null, null, Topic.TOPIC_UP, null, null, true).getList();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("groups", groups);
		model.put("page", curPage);
		model.put("status", status);
		model.put("type", type);
		model.put("userId", userId);
		model.put("audited", Article.AUDITED_Y);
		model.put("essence", essence);
		model.put("groupId", groupId);
		model.put("topicId", topicId);
		model.put("topics", topics);
		model.put("nav_id", "articled");
		return new ModelAndView("admin/article/articles", model);
	}

	@RequestMapping("/article")
	public ModelAndView article(Long id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		List<DBObject> groups = groupService.findGroups(Group.UP, 1, 10);
		rs.put("groups", groups);
		if (id == null) {
			return new ModelAndView("admin/article/article", rs);
		}
		DBObject dbo = articleService.findById(id);
		rs.put("obj", dbo);
		return new ModelAndView("admin/article/articleEdit", rs);
	}

	@RequestMapping("/articleView")
	public ModelAndView articleView(Long id) {
		if (id == null) {
			return new ModelAndView("admin/article/article");
		}
		DBObject dbo = articleService.findById(id);
		List<DBObject> ts = articleCommentService.getTopComments(id, 3, true);
		List<DBObject> ns = articleCommentService.find(null, id, null, 1, 10, null, true);
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("obj", dbo);
		rs.put("tops", ts);
		rs.put("news", ns);
		return new ModelAndView("admin/article/articleView", rs);
	}

	@RequestMapping("/article/pics")
	public ModelAndView articlePics(Long id) {
		DBObject dbo = articleService.findById(id);
		return new ModelAndView("admin/article/pics", "obj", dbo);
	}

	@ResponseBody
	@RequestMapping(value = "/article", method = RequestMethod.POST)
	public ReMsg saveArticle(Long id, String title, String content, String tags, Integer status, Integer essence,
			Integer audited, Long groudId, String video, String pics, Integer anonymous, String topic, Long topicId,
			String pubTime, Integer userType, Long curUid, String atUsers, HttpServletRequest request,
			ArrayList<Map> draws, String voiceUrl, Integer pri) {
		String[] cPics = null;
		if (StringUtils.isNotBlank(pics)) {
			cPics = pics.split(",");
		}

		String[] users = null;
		if (StringUtils.isNotBlank(atUsers)) {
			users = pics.split(",");
		}

		String[] cTags = null;
		if (StringUtils.isNotBlank(tags)) {
			cTags = tags.split(",");
		}
		Long adminId = 0L;
		Long publishTime = 0L;
		if (pri == null) {
			pri = Article.PRI_ALL;
		}
		if (StringUtils.isBlank(pubTime)) {
			publishTime = System.currentTimeMillis();
		} else {
			try {
				publishTime = DateUtil.convertDate(pubTime, "yyyy-MM-dd HH:mm:ss").getTime();
			} catch (ParseException e) {
				return new ReMsg(ReCode.FAIL, "发布时间有误！");
			}
		}
		if (id != null && id > 0) {
			return articleService.updateArticle(id, title, content, cTags, status, essence, audited, cPics, video,
					publishTime, topic, userType, users, request, draws, voiceUrl, groudId);
		} else {
			return articleService.saveArticle(title, content, cTags, status, essence, audited, adminId, groudId, cPics,
					video, anonymous, topicId, topic, publishTime, userType, curUid, users, request, draws, voiceUrl,
					pri);
		}
	}

	@RequestMapping("/comments")
	public ModelAndView comments(Long userId, Long aid, Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = articleCommentService.query(userId, aid, status, page, size, null, true);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("aid", aid);
		model.put("userId", userId);
		return new ModelAndView("admin/article/comments", model);
	}

	@ResponseBody
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public ReMsg comment(Long id, Integer status, HttpServletRequest request) {
		if (id != null && id > 0) {
			return new ReMsg(articleCommentService.updateComment(id, status));
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/commentArt", method = RequestMethod.POST)
	public ReMsg commentArt(Long id, String content, String pics, Long curUid, Long cid, HttpServletRequest request) {
		if (id != null && id > 0) {
			String[] cPics = null;
			String[] atUsers = null;
			if (StringUtils.isNotBlank(pics)) {
				cPics = pics.split(",");
			}
			if (null != cid && cid > 0) {
				atUsers = new String[] { "" + cid };
			}
			return articleService.commentByRobit(id, content, curUid, cPics, atUsers);
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	/** 机器人送花 */
	@ResponseBody
	@RequestMapping(value = "/robotFlower", method = RequestMethod.POST)
	public ReMsg robotFlower(Long id, Integer count, HttpServletRequest request) {
		if (id != null && id > 0) {
			return articleService.sendFlowerByRobit(count, id);
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/commentReply", method = RequestMethod.POST)
	public ReMsg commentReply(Long aid, Long cid, String content, Long curUid, HttpServletRequest request) {
		if (cid == null) {
			return new ReMsg(ReCode.FAIL);
		}
		return articleService.replyCommentByRobit(aid, cid, curUid, content);
	}

	@ResponseBody
	@RequestMapping(value = "/comment/parise", method = RequestMethod.POST)
	public ReMsg commentParise(Long id, HttpServletRequest request) {
		if (id != null && id > 0) {
			int count = RandomUtil.nextInt(4) + 1;
			return articleCommentService.praiseComment(id, count);
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	@RequestMapping("/topics")
	public ModelAndView topics(Long groupId, Long id, String topic, Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = articleTopicService.queryTopic(groupId, id, topic, status, page, size, true);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		List<DBObject> groups = groupService.findGroups(Group.UP, 1, 10);
		model.put("groups", groups);
		model.put("page", curPage);
		model.put("id", id);
		model.put("topic", topic);
		model.put("groupId", groupId);
		model.put("status", status);
		return new ModelAndView("admin/article/topics", model);
	}

	@RequestMapping(value = "/topic")
	public ModelAndView topic(Long id, HttpServletRequest request) {
		return new ModelAndView("admin/article/topic", "obj", articleTopicService.findId(id));
	}

	@ResponseBody
	@RequestMapping(value = "/topic", method = RequestMethod.POST)
	public ReMsg topic(Long id, String name, Double sort, Integer status, HttpServletRequest request) {
		if (id != null && id > 0) {
			return new ReMsg(articleTopicService.updateTopic(id, name, status, null, sort));
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/article/weight", method = RequestMethod.POST)
	public ReMsg addWeight(Long id, Double sort, HttpServletRequest request) {
		if (id != null && id > 0) {
			return articleService.addWeight(id, sort);
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	@RequestMapping(value = "/addGroup")
	public ModelAndView addGroup(Long id) {
		ModelAndView model = new ModelAndView("admin/article/group");
		model.addObject("types", ArticleService.TYPES.values());
		if (id == null) {
			return model;
		}
		DBObject dbo = articleGroupService.findById(id);
		model.addObject("obj", dbo);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/addGroup", method = RequestMethod.POST)
	public ReMsg addGroup(Long id, String title, String pic, String branchPic, String tagPic, String buttonPic,
			Integer status, Integer sort, Integer type) {
		return articleGroupService.upsert(id, title, pic, status, sort, type, branchPic, tagPic, buttonPic);
	}

	@RequestMapping("/groups")
	public ModelAndView groups(Integer status, Integer page, Integer size, HttpServletRequest request) {
		status = null == status ? 0 : status;
		Page<DBObject> curPage = articleGroupService.queryGroups(status, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		return new ModelAndView("admin/article/groups", model);
	}

	/** 批量移除 */
	@RequestMapping("/bitchRemoveArticle")
	public @ResponseBody ReMsg bitchRemoveArticle(String ids, int status) {
		return articleService.bitchRemoveSift(ids, status);
	}

	@ResponseBody
	@RequestMapping("/getTopics")
	public List<DBObject> querytopicsList(Long groupId, HttpServletRequest request) {
		Page<DBObject> curPage = articleTopicService.queryTopic(groupId, null, null, Topic.TOPIC_UP, null, null, true);
		return curPage.getList();
	}

}
