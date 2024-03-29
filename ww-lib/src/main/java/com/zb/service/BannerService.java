package com.zb.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.Banner;
import com.zb.models.DocName;

@Service
public class BannerService extends BaseService {

	public static final String[] TYPES = { "index", "doutu", "game", "circle", "vip" };

	// public static final String[] OPS = { "url", "tool", "eif", "game",
	// "toolTag" };

	static final Logger log = LoggerFactory.getLogger(BannerService.class);

	public DBObject findBannerById(Long id) {
		return getC(DocName.BANNER).findOne(new BasicDBObject("_id", id));
	}

	public List<DBObject> findBanner(String type, Integer status, Integer via, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (StringUtils.isNotBlank(type)) {
			q.put("type", type);
		}
		if (null != via && via != 0) {
			q.put("vias", via);
		}
		long time = System.currentTimeMillis(); // 1500
		q.put("startTime", new BasicDBObject("$lte", time));// 开始时间小于当前时间  // 1000
		q.put("endTime", new BasicDBObject("$gte", time));// 结束时间大于当前时间  // 2000

		List<DBObject> dbos = getC(DocName.BANNER).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", -1)).toArray();
		return dbos;
	}

	public List<DBObject> findBannerAdmin(String type, Integer status, Integer via, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (StringUtils.isNotBlank(type)) {
			q.put("type", type);
		}
		if (null != via && via != 0) {
			q.put("vias", via);
		}
		return getC(DocName.BANNER).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", -1)).toArray();

	}

	public int countBanner(String type, Integer status, Integer via) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (StringUtils.isNotBlank(type)) {
			q.put("type", type);
		}
		if (null != via && via != 0) {
			q.put("vias", via);
		}
		return getC(DocName.BANNER).find(q).count();
	}

	public Page<DBObject> queryBanner(Integer status, String type, Integer via, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findBannerAdmin(type, status, via, page, size);
		int count = countBanner(type, status, via);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public ReMsg upsert(Long id, String type, String title, String pic, String op, String opId, Integer status,
			Integer[] vias, Integer sort, Long startTime, Long endTime) {
		Long adminId = super.getUid();// 获取操作用户 权限验证未做 TODO
		if (id == null || id < 1L) {
			id = super.getNextId(DocName.BANNER);
			Banner banner = new Banner(id, type, title, pic, op, opId, status, vias, adminId, sort, startTime, endTime);
			getMongoTemplate().save(banner);
			return new ReMsg(ReCode.OK);
		} else {
			DBObject dbo = new BasicDBObject("adminId", adminId).append("updateTime", System.currentTimeMillis());
			if (StringUtils.isNotBlank(type)) {
				dbo.put("type", type);
			}
			if (StringUtils.isNotBlank(title)) {
				dbo.put("title", title);
			}
			if (StringUtils.isNotBlank(pic)) {
				dbo.put("pic", pic);
			}
			if (StringUtils.isNotBlank(op)) {
				dbo.put("op", op);
			}
			if (StringUtils.isNotBlank(opId)) {
				dbo.put("opId", opId);
			}
			if (status != null && status != 0) {
				dbo.put("status", status);
			}
			if (vias != null) {
				dbo.put("vias", vias);
			}
			if (sort != null && sort != 0) {
				dbo.put("sort", sort);
			}
			if (startTime != null && startTime != 0) {
				dbo.put("startTime", startTime);
			}
			if (endTime != null && endTime != 0) {
				dbo.put("endTime", endTime);
			}
			super.getC(DocName.BANNER).update(new BasicDBObject("_id", id), new BasicDBObject("$set", dbo));
			return new ReMsg(ReCode.OK);
		}
		// return new ReMsg(ReCode.FAIL);
	}

}
