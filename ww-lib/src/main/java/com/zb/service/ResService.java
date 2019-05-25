package com.zb.service;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.res.Res;
import com.zb.models.res.ResPack;
import com.zb.models.res.ResPackCate;

@Service
public class ResService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(ResService.class);

	@Autowired
	EnvService envService;

	public DBObject findResPackById(Long id) {
		return getC(DocName.RES_PACK).findOne(new BasicDBObject("_id", id));
	}

	public DBObject findResPackCateById(Long id) {
		return getC(DocName.RES_PACK_CATE)
				.findOne(new BasicDBObject("_id", id));
	}

	public Page<DBObject> queryResPack(String tag, Long catId, Integer status,
			Integer type, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findResPack(tag, catId, status, type, page, size);
		int count = countResPack(tag, catId, status, type);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countResPack(String tag, Long cateId, Integer status,
			Integer type) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(tag)) {
			q.put("tags", tag);
		}
		if (null != cateId && cateId != 0) {
			q.put("cateId", cateId);
		}
		if (null != type && type != 0) {
			q.put("type", type);
		}
		if (null != status && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.RES_PACK).find(q).count();
	}

	DBObject resPackKeys = new BasicDBObject("_id", 1).append("name", 1)
			.append("cover", 1).append("cateId", 1).append("type", 1)
			.append("status", 1);

	public List<DBObject> findResPack(String tag, Long cateId, Integer status,
			Integer type, Integer page, Integer size) {

		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(tag)) {
			q.put("tags", tag);
		}
		if (null != cateId && cateId != 0) {
			q.put("cateId", cateId);
		}
		if (null != type && type != 0) {
			q.put("type", type);
		}
		if (null != status && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.RES_PACK).find(q, resPackKeys)
				.skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", -1)).toArray();
	}

	public List<DBObject> findResPack(Long[] cateIds, Integer status,
			Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != cateIds && cateIds.length > 0) {
			q.put("cateId", new BasicDBObject("$in", cateIds));
		}

		if (null != status && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.RES_PACK).find(q, resPackKeys)
				.skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", -1)).toArray();
	}

	// public List<DBObject> findResPackByTag(String tag, Integer status,
	// Integer page, Integer size) {
	// DBObject q = new BasicDBObject();
	// if (StringUtils.isNotBlank(tag)) {
	// q.put("tags", tag);
	// }
	//
	// if (null != status && status != 0) {
	// q.put("status", status);
	// }
	//
	// return getC(DocName.RES_PACK).find(q, resPackKeys)
	// .skip(super.getStart(page, size)).limit(getSize(size))
	// .sort(new BasicDBObject("sort", -1)).toArray();
	// }

	public List<DBObject> getNewResPack(Integer type, Integer status,
			Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != type && type != 0) {
			q.put("type", type);
		}
		if (null != status && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.RES_PACK).find(q, resPackKeys)
				.skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public ReMsg saveResPack(Long id, Integer type, String name, String cover,
			Integer status, Integer sort, Long cateId, List<Res> reses,
			String[] tags) {
		if (null == id) {
			id = super.getNextId(DocName.RES_PACK);
		}
		ResPack zbtc = new ResPack(id, type, name, cover, status, sort, cateId,
				reses, super.getUid(), tags);
		getMongoTemplate().save(zbtc);
		return new ReMsg(ReCode.OK, zbtc);
	}

	public ReMsg saveResPackCate(Long id, String name, Integer status,
			Integer sort) {
		if (null == id) {
			id = super.getNextId(DocName.RES_PACK_CATE);
		}
		ResPackCate zbtc = new ResPackCate(id, name, status, sort);
		getMongoTemplate().save(zbtc);
		return new ReMsg(ReCode.OK, zbtc);
	}

	public Page<DBObject> queryResPackCate(Integer status, Integer page,
			Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findResPackCate(status, page, size);
		int count = countResPackCate(status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countResPackCate(Integer status) {
		DBObject q = new BasicDBObject();

		if (null != status && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.RES_PACK_CATE).find(q).count();
	}

	public List<DBObject> findResPackCate(Integer status, Integer page,
			Integer size) {
		DBObject q = new BasicDBObject();

		if (null != status && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.RES_PACK_CATE).find(q)
				.skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", -1)).toArray();
	}

	public List<DBObject> findResPackCate(long[] ids, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		q.put("_id", new BasicDBObject("$in", ids));
		return getC(DocName.RES_PACK_CATE).find(q)
				.skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", -1)).toArray();
	}

	public Page<DBObject> searchReses(String qtxt, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		Pattern pattern = Pattern.compile("^.*" + qtxt + ".*$",
				Pattern.CASE_INSENSITIVE);
		DBObject q = new BasicDBObject("status", Const.STATUS_UP).append(
				"reses.name", pattern);

		DBCursor dbc = getC(DocName.RES_PACK).find(q);
		int count = dbc.count();
		List<DBObject> dbos = dbc.skip(getStart(page, size))
				.limit(getSize(size)).sort(new BasicDBObject("sort", -1))
				.toArray();
		return new Page<DBObject>(count, size, page, dbos);
	}

	private static final String RES_SEARCH_RECOMMEND = "res.search.recommend";

	public String[] searchRecommend() {
		String txt = envService.getString(RES_SEARCH_RECOMMEND);
		if (StringUtils.isNotBlank(txt)) {
			return txt.split(",");
		}
		return new String[0];
	}

}
