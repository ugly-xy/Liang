package com.we.service.article;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.Constant.ReCode;
import com.we.models.DocName;
import com.we.models.article.ArticlePraiseLog;
import com.we.service.BaseService;

@Service
public class ArticlePraiseService extends BaseService {

	static final Logger log = LoggerFactory
			.getLogger(ArticlePraiseService.class);

	public List<DBObject> find(long aid, long uid, int page, int size) {
		DBObject q = new BasicDBObject();
		if (aid != 0) {
			q.put("aid", aid);
		}
		if (uid != 0) {
			q.put("userId", uid);
		}

		return getC(DocName.ARTICLE_PRAISE_LOG).find(q)
				.sort(new BasicDBObject("_id", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
	}

	public boolean isPraise(long aid, long uid) {
		DBObject q = new BasicDBObject("aid", aid).append("userId", uid);
		return getC(DocName.ARTICLE_PRAISE_LOG).count(q) > 0 ? true : false;
	}

	public ReCode saveLog(long aid, int type) {
		long uid = super.getUid();
		if (uid < 1)
			return ReCode.FAIL;

		if (!isPraise(aid, uid)) {
			long id = super.getNextId(DocName.ARTICLE_PRAISE_LOG);
			String nickname = super.getUser("nickname") != null ? super
					.getUser("nickname").toString() : "";
			ArticlePraiseLog arl = new ArticlePraiseLog(id, aid, uid, nickname,
					type);
			getMongoTemplate().save(arl);
			return ReCode.OK;
		}
		return ReCode.FAIL;
	}
}
