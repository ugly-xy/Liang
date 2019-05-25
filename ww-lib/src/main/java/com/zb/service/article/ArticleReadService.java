package com.zb.service.article;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.models.DocName;
import com.zb.models.article.ArticleReadLog;
import com.zb.service.BaseService;

@Service
public class ArticleReadService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(ArticleReadService.class);

	public List<DBObject> find(long aid, long uid, int page, int size) {
		DBObject q = new BasicDBObject();
		if (aid != 0) {
			q.put("aid", aid);
		}
		if (uid != 0) {
			q.put("userId", uid);
		}

		return getC(DocName.ARTICLE_READ_LOG).find(q)
				.sort(new BasicDBObject("_id", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
	}

	public ReCode saveReadLog(long aid) {
		long uid = super.getUid();
		if (uid < 1)
			return ReCode.FAIL;

		List<DBObject> rls = find(aid, uid, 0, 1);
		boolean noRead = true;
		if (rls.size() != 0) {
			DBObject dbo = rls.get(0);
			if (System.currentTimeMillis() - DboUtil.getLong(dbo, "updateTime") < 3600 * 1000 * 24) {
				noRead = false;
			}
		}
		if (noRead) {
			long id = super.getNextId(DocName.ARTICLE_READ_LOG);
			Object nicknameO = super.getUser("nickname");
			String nickname = "";
			if (nicknameO != null) {
				nickname = nicknameO.toString();
			}
			ArticleReadLog arl = new ArticleReadLog(id, aid, uid, nickname);
			getMongoTemplate().save(arl);
			return ReCode.OK;
		}
		return ReCode.FAIL;

	}

}
