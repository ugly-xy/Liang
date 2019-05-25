package com.zb.service.article;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.core.Page;
import com.zb.models.DocName;
import com.zb.models.article.Topic;
import com.zb.service.BaseService;

@Service
public class ArticleTopicService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(ArticleTopicService.class);

	public DBObject findTopicById(Long id) {
		return getC(DocName.TOPIC).findOne(new BasicDBObject("_id", id));
	}

	public Page<DBObject> queryTopic(Long groupId, Long id, String topic, Integer status, Integer page, Integer size) {
		return queryTopic(groupId, id, topic, status, page, size, false); 
	}

	public Page<DBObject> queryTopic(Long groupId, Long id, String topic, Integer status, Integer page, Integer size, boolean adm) {
		size = getOwnSize(size, adm);
		page = getPage(page);
		List<DBObject> dbos = findTopic(groupId, id, topic, status, page, size, adm);
		int count = countTopic(groupId, id, topic, status, adm);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countTopic(Long groupId, Long id, String topic, Integer status, boolean adm) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(topic)) {
			q.put("name", new BasicDBObject("$regex", topic).append("$options", 'i'));
		}
		if (id != null && id > 0) {
			q.put("_id", id);
		}
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (groupId != null && groupId > 0) {
			q.put("groupId", groupId);
		}
//		if (!adm) {
//			q.put("updateTime", new BasicDBObject("$gt", System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000L));
//		}
		return getC(DocName.TOPIC).find(q).count();
	}

	public List<DBObject> findTopic(Long groupId, Long id, String topic, Integer status, Integer page, Integer size, boolean adm) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(topic)) {
			q.put("name", new BasicDBObject("$regex", topic).append("$options", 'i'));
		}
		if (id != null && id > 0) {
			q.put("_id", id);
		}
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (groupId != null && groupId > 0) {
			q.put("groupId", groupId);
		}
//		if (!adm) {
//			q.put("updateTime", new BasicDBObject("$gt", System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000L));
//		}
		return getC(DocName.TOPIC).find(q).skip(getOwnStart(page, size, adm)).limit(getOwnSize(size, adm))
				.sort(new BasicDBObject("sort", -1)).toArray();
	}

	public List<DBObject> findTopic(Long groupId, Long id, String topic, Integer status, Integer page, Integer size) {
		return findTopic(groupId, id, topic, status, page, size, false);
	}

	public Long getTopicId(String name, Long groupId) {
		DBObject q = new BasicDBObject("name", name).append("groupId", groupId);
		DBObject dbo = getC(DocName.TOPIC).findOne(q);
		if (dbo != null) {
			long ct = System.currentTimeMillis();
			Long id = DboUtil.getLong(dbo, "_id");
			int count = DboUtil.getInt(dbo, "count");
			double sort = complateSort(ct, ct, count + 1);
			DBObject uo = new BasicDBObject("$inc", new BasicDBObject("count", Integer.valueOf(1)).append("sort", sort))
					.append("$set", new BasicDBObject("updateTime", ct));
			getC(DocName.TOPIC).update(new BasicDBObject("_id", id), uo);
			return DboUtil.getLong(dbo, "_id");
		} else {
			Long id = super.getNextId(DocName.TOPIC);
			long ct = System.currentTimeMillis();
			double sort = complateSort(ct, ct, 1);
			Topic zbtc = new Topic(id, name, 1, sort, ct, Const.STATUS_OK, groupId);
			getMongoTemplate().save(zbtc);
			return id;
		}
	}

	public DBObject findId(Long id) {
		return super.findById(DocName.TOPIC, id);
	}

	public ReCode updateTopic(Long id, String topic, Integer status, Integer count, Double sort) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		if (status != 0 && status != 0) {
			u.put("status", status);
		}

		if (count != null && count != 0) {
			u.put("count", count);
		}

		if (sort != 0 && sort != 0) {
			u.put("sort", sort);
		}

		if (StringUtils.isNotBlank(topic)) {
			u.put("name", topic);
		}
		getC(DocName.TOPIC).update(new BasicDBObject("_id", id), new BasicDBObject("$set", u));
		return ReCode.OK;

	}

	private double complateSort(long createTime, long updateTime, int count) {
		double ln = 0d;
		if (count <= 1) {
			ln = Math.log(1.5);
		} else {
			ln = Math.log(count) - Math.log(count - 1);
		}
		if (createTime == updateTime) {
			ln = Math.log(updateTime) + ln;
		} else {
			ln = ln + Math.log(updateTime - createTime);
		}
		return ln;
	}

	int getOwnSize(Integer size, boolean adm) {
		if (size == null || size < 1 || size > 500) {
			if (adm) {
				size = 30;
			} else {
				size = 10;
			}
		}
		return size;
	}

	int getOwnStart(Integer page, Integer size, boolean adm) {
		if (null == page || page < 1) {
			page = 1;
		}
		size = getOwnSize(size, adm);
		return (page - 1) * size;
	}

}
