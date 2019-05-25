package com.zb.service.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.common.Constant.Role;
import com.zb.service.EnvService;
import com.zb.service.UserService;
import com.zb.service.article.ArticleService;

//@Service
public class ArticleRobitJob {

	static final Logger log = LoggerFactory.getLogger(ArticleRobitJob.class);
	@Autowired
	ArticleService articleService;

	@Autowired
	UserService userService;

	@Autowired
	EnvService envService;

	public void execute() {
		try {
			articleService.robitArticle();
			if (envService.getBool("comment.user.robit.create")) {
				userService.createRobit(1, null, Role.ROBOT_C);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
