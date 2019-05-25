package com.zb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.core.Page;
import com.zb.models.DocName;
import com.zb.models.Keyword;

@Service
public class KeywordService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(KeywordService.class);

	public DBObject findById(String id) {
		return getC(DocName.KEYWORD).findOne(new BasicDBObject("_id", id));
	}

	public Page<DBObject> query(Integer type, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(type, page, size);
		Integer count = count(type);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> find(Integer type, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (type != null && type > 0) {
			q.put("type", type);
		}
		return getC(DocName.KEYWORD).find(q).skip(getStart(page, size))
				.limit(getSize(size)).sort(new BasicDBObject("_id", -1))
				.toArray();
	}

	public List<DBObject> getAll(Integer type) {
		DBObject q = new BasicDBObject();
		if (type != null && type > 0) {
			q.put("type", type);
		}
		int count = count(type);
		return getC(DocName.KEYWORD).find(q).skip(0).limit(count)
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public Integer count(Integer type) {
		DBObject q = new BasicDBObject();
		if (type != null && type > 0) {
			q.put("type", type);
		}
		return getC(DocName.KEYWORD).find(q).count();
	}

	public void save(Integer type, String word) {
		DBObject dbo = this.findById(word);

		if (dbo == null) {
			Keyword kw = new Keyword(word, type);
			getMongoTemplate().save(kw);
		}
	}

	public void del(String word) {
		getC(DocName.KEYWORD).remove(new BasicDBObject("_id", word));
	}

}
