package com.zb.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.zb.common.mongo.DboUtil;
import com.zb.core.Page;
import com.zb.models.DocName;
import com.zb.models.Env;

@Service
public class EnvService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(EnvService.class);

	public static final String TOOL_USED_COUNT = "tool.used.total";
	
	public static final String APP_SHARE_LINK_NAME = "app.share.link";
	
	public String getShareLink(){
		return this.getString(APP_SHARE_LINK_NAME);
	}

	public DBObject findById(String id) {
		return getC(DocName.ENV).findOne(new BasicDBObject("_id", id));
	}

	public Page<DBObject> query(String key, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(key, page, size);
		Integer count = count(key);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> find(String key, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(key)) {
			q.put("_id", key);
		}
		return getC(DocName.ENV).find(q).skip(getStart(page, size)).limit(getSize(size)).toArray();
	}

	public Integer count(String key) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(key)) {
			q.put("_id", key);
		}
		return getC(DocName.ENV).find(q).count();
	}

	public Integer getInt(String id) {
		return DboUtil.getInt(findById(id), "value");
	}

	public String getString(String id) {
		return DboUtil.getString(findById(id), "value");
	}

	public Long getLong(String id) {
		return DboUtil.getLong(findById(id), "value");
	}

	public Double getDouble(String id) {
		return DboUtil.getDouble(findById(id), "value");
	}

	public Float getFloat(String id) {
		return DboUtil.getFloat(findById(id), "value");
	}

	public Boolean getBool(String id) {
		return DboUtil.getBool(findById(id), "value");
	}

	public <T> Object get(String id, Class<T> c) {
		return DboUtil.get(findById(id), "value", c);
	}

	public void set(String id, Object obj) {
		save(id, obj.toString());
	}

	public void save(String id, String value) {
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(value)) {
			Env g = new Env(id, value);
			getMongoTemplate().save(g);
		}
	}

	public void del(String id) {
		getC(DocName.ENV).remove(new BasicDBObject("_id", id), WriteConcern.ACKNOWLEDGED);
	}

}
