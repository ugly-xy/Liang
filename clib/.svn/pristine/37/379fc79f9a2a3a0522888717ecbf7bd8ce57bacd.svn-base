package com.we.core.myTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.we.service.article.CollectActicleService;

@Component("collectWebHtmlArticle")
public class CollectWebHtmlArticle {
	static final Logger log=LoggerFactory.getLogger(CollectWebHtmlArticle.class);
	
	@Autowired
	CollectActicleService collectActicleService;
	
	public void doTask() {
		log.warn("------collectWebHtmlArticle:---");
		collectActicleService.getRequireWebHtmlData();
		
	}
	
	

}
