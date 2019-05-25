package com.we.service.article;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.Constant.ReCode;
import com.we.common.mongo.DboUtil;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.models.DocName;
import com.we.models.article.TipOff;
import com.we.service.BaseService;

@Service
public class TipOffService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(TipOffService.class);

	public DBObject findById(Long id) {
		return getC(DocName.TIP_OFF).findOne(new BasicDBObject("_id", id));
	}

	public Page<DBObject> query(Long aid, Long uid, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(aid, uid, page, size);
		int count = count(aid, uid);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int count(Long aid, Long uid) {
		DBObject q = new BasicDBObject();
		if (aid != null && aid != 0) {
			q.put("aid", aid);
		}
		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}
		return getC(DocName.TIP_OFF).find(q).count();
	}

	public List<DBObject> find(Long aid, Long uid, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (aid != null && aid != 0) {
			q.put("aid", aid);
		}
		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}
		return getC(DocName.TIP_OFF).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public Page<DBObject> querySt(Integer type, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findSt(type, page, size);
		int count = countSt(type);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countSt(Integer type) {
		DBObject q = new BasicDBObject();
		if (type != null && type != 0) {
			q.put("type", type);
		}
		return getC(DocName.TIP_OFF_ST).find(q).count();
	}

	public List<DBObject> findSt(Integer type, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (type != null && type != 0) {
			q.put("type", type);
		}
		return getC(DocName.TIP_OFF_ST).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("count", -1)).toArray();
	}

	public void updateSt(Long id, Integer status) {
		DBObject u = new BasicDBObject("status", status);
		super.getC(DocName.TIP_OFF_ST).update(new BasicDBObject("_id", id), new BasicDBObject("$set", u));

	}

	private static final String SEQ = "count";
	private static final DBObject seqField = new BasicDBObject(SEQ, Integer.valueOf(1));
	private static final DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject(SEQ, Long.valueOf(1)));

	public ReMsg save(String content, Long aid, Integer type) {
		Long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (null == type || type < 1) {
			type = TipOff.ARTICLE;
		}
		BasicDBObject q = new BasicDBObject();
		q.append("uid", uid);
		q.append("aid", aid);
		if (getC(DocName.TIP_OFF).find(q).toArray().size() > 0) {
			return new ReMsg(ReCode.OK);
		}
		long id = super.getNextId(DocName.TIP_OFF);
		TipOff zbtc = new TipOff(id, content, aid, uid, type);

		getMongoTemplate().save(zbtc);
		DBObject one = getC(DocName.TIP_OFF_ST).findOne(new BasicDBObject("_id", aid));
		if (null == one) {
			getC(DocName.TIP_OFF_ST)
					.save(new BasicDBObject("_id", aid).append("type", type).append("count", Integer.valueOf(1)));
			return new ReMsg(ReCode.OK, zbtc);
		}
		one.put("count", DboUtil.getInt(one, "count") + 1);
		if (null == DboUtil.getInt(one, "type")) {
			one.put("type", type);
		}
		getC(DocName.TIP_OFF_ST).save(one);
		return new ReMsg(ReCode.OK, zbtc);
	}
}
