package com.zb.service.room;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.room.activity.Punish;
import com.zb.service.BaseService;

@Service
public class PunishService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(PunishService.class);

	public DBObject findById(Long id) {
		return getC(DocName.PUNISH).findOne(new BasicDBObject("_id", id));
	}

	public Page<DBObject> query(Integer status, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(status, page, size);
		int count = count(status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int count(Integer status) {
		DBObject q = new BasicDBObject();
		if (status != null) {
			q.put("status", status);
		}
		return getC(DocName.PUNISH).find(q).count();
	}

	public List<DBObject> find(Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (status != null) {
			q.put("status", status);
		}
		return getC(DocName.PUNISH).find(q).skip(super.getStart(page, size))
				.limit(getSize(size)).sort(new BasicDBObject("_id", -1))
				.toArray();
	}

	public ReMsg add(String words) {
		String[] cs = words.split(";;;");
		for (String txt : cs) {
			DBObject q = new BasicDBObject();
			q.put("txt", txt);
			int c = super.getC(DocName.PUNISH).find(q).count();
			if (c < 1) {
				upsert(null, txt, Const.STATUS_OK);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg upsert(Long id, String txt, Integer status) {
		if (id == null || id < 1L) {
			id = super.getNextId(DocName.PUNISH);
			Punish banner = new Punish(id, txt, status);
			getMongoTemplate().save(banner);
			return new ReMsg(ReCode.OK);
		} else {
			DBObject dbo = new BasicDBObject("updateTime",
					System.currentTimeMillis());
			dbo.put("txt", txt);
			if (status != null && status != 0) {
				dbo.put("status", status);
			}
			super.getC(DocName.PUNISH).update(new BasicDBObject("_id", id),
					new BasicDBObject("$set", dbo));
			return new ReMsg(ReCode.OK);
		}
	}

	public Set<String> getPunish(int n) {
		if (n < 0) {
			return null;
		}
		int c = this.count(Const.STATUS_OK);
		Set<String> sets = new HashSet<String>();
		int t = 0;
		while (sets.size() < n) {
			t++;
			int r = RandomUtil.nextInt(c);
			List<DBObject> dbos = this.find(Const.STATUS_OK, r, 2);
			if (dbos.size() > 0) {
				sets.add(DboUtil.getString(dbos.get(0), "txt"));
			}
			if (t > n * 5) {
				break;
			}
		}
		return sets;
	}

}
