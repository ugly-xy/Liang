package com.we.service.article;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.Constant.ReCode;
import com.we.models.DocName;
import com.we.models.article.ArticleShareLog;
import com.we.service.BaseService;

@Service
public class ArticleShareService extends BaseService {

	static final Logger log = LoggerFactory
			.getLogger(ArticleShareService.class);

	public List<DBObject> find(long aid, long uid, int page, int size) {
		DBObject q = new BasicDBObject();
		if (aid != 0) {
			q.put("aid", aid);
		}
		if (uid != 0) {
			q.put("userId", uid);
		}

		return getC(DocName.ARTICLE_SHARE_LOG).find(q)
				.sort(new BasicDBObject("_id", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
	}

	public boolean isShare(long aid, long uid, String platform) {
		DBObject q = new BasicDBObject("aid", aid).append("userId", uid)
				.append("platform", platform);
		return getC(DocName.ARTICLE_SHARE_LOG).count(q) > 0 ? true : false;
	}

	public ReCode saveLog(long aid, String platform) {
		long uid = super.getUid();
		if (uid < 1)
			return ReCode.FAIL;

		if (!isShare(aid, uid, platform)) {
			long id = super.getNextId(DocName.ARTICLE_SHARE_LOG);
			ArticleShareLog arl = new ArticleShareLog(id, aid, uid, platform);
			getMongoTemplate().save(arl);
			return ReCode.OK;
		}
		return ReCode.FAIL;

	}

}
