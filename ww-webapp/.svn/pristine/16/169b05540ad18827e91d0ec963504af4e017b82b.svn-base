package com.zb.web.api;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.Constant.Role;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.article.ArticleCommentService;
import com.zb.service.article.ArticleService;
import com.zb.service.article.ArticleTopicService;

@Controller
@RequestMapping(value = "/admin/sys")
public class ArticleAdmCtl extends BaseCtl {
	static final Logger log = LoggerFactory.getLogger(ArticleAdmCtl.class);

	@Autowired
	ArticleService articleService;

	@Autowired
	ArticleCommentService articleCommentService;

	@Autowired
	ArticleTopicService articleTopicService;

	@ResponseBody
	@RequestMapping("/articles")
	public ReMsg articles(int audited, Long groupId, Integer type, Integer status, Long userId, Integer essence,
			Integer pri, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = articleService.query(groupId, userId, type, status, essence, audited, page, size, true,
				null, null, null, null, pri);
		return new ReMsg(ReCode.OK, curPage);
	}

	@ResponseBody
	@RequestMapping(value = "/article", method = RequestMethod.POST) // FIXME
																		// POST
	public ReMsg saveArticle(Long id, String title, String content, String tags, Integer status, Integer essence,
			Integer audited, Long groudId, String video, String pics, Integer anonymous, String topic, Long topicId,
			String pubTime, Integer userType, String atUsers, ArrayList<Map> draws, String voiceUrl, Integer pri,
			HttpServletRequest request) {

		String[] cPics = null;
		if (StringUtils.isNotBlank(pics)) {
			cPics = pics.split(",");
		}

		String[] users = null;
		if (StringUtils.isNotBlank(atUsers)) {
			List<String> j = JSONUtil.jsonToArray(atUsers, String.class);
			users = j.toArray(new String[] {});
		}

		String[] cTags = null;
		if (StringUtils.isNotBlank(tags)) {
			cTags = tags.split(",");
		}
		Long adminId = super.getUid();
		int r = T2TUtil.obj2Int(articleService.getUser("role"), 0);
		if (Role.ADMIN.getRole() == r || r >= Role.EDITOR.getRole()) {
			Long publishTime = 0L;
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
				return articleService.saveArticle(title, content, cTags, status, essence, audited, adminId, groudId,
						cPics, video, anonymous, topicId, topic, publishTime, userType, 0L, users, request, draws,
						voiceUrl, pri);
			}
		} else {
			return new ReMsg(ReCode.ROLE_NOT_OP);
		}

	}

}
