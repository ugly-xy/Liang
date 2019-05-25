package com.zb.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.mongo.Op;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.finance.Discount;
import com.zb.models.finance.DiscountConfig;

@Service
public class DiscountService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(DiscountService.class);

	public DBObject findById(String id) {
		return super.getC(DocName.DISCOUNT).findOne(new BasicDBObject("_id", id));
	}

	public DBObject findConfigById(Long id) {
		return super.findById(DocName.DISCOUNT_CONFIG, id);
	}

	public Page<DBObject> queryConfig(Integer type, Integer status, Integer page, Integer size, Op op) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findConfig(type, status, page, size, op);
		Integer count = countConfig(type, status, op);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> findConfig(Integer type, Integer status, Integer page, Integer size, Op op) {
		DBObject q = new BasicDBObject();
		if (type != null && type != 0) {
			q.put("type", type);
		}
		if (op != null) {
			op.getOp(q, "status", status);
		} else if (null != status && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.DISCOUNT_CONFIG).find(q).sort(new BasicDBObject("updateTime", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
	}

	public Integer countConfig(Integer type, Integer status, Op op) {
		DBObject q = new BasicDBObject();
		if (type != null && type != 0) {
			q.put("type", type);
		}
		if (op != null) {
			op.getOp(q, "status", status);
		} else if (null != status && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.DISCOUNT_CONFIG).find(q).count();
	}

	public List<DBObject> findUserDiscount(Integer status, Integer page, Integer size) {
		return findDiscount(super.getUid(), status, page, size);
	}

	public List<DBObject> findDiscount(Long uid, Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (uid != 0) {
			q.put("userId", uid);
		}
		if (status != 0) {
			q.put("status", status);
		}
		return getC(DocName.DISCOUNT).find(q).sort(new BasicDBObject("_id", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
	}

	public ReCode userDiscount(String id) {
		return updateDiscount(id, Discount.USED);
	}

	public ReCode updateDiscount(String id, Integer status) {
		DBObject a = findById(id);
		if (null == a) {
			return ReCode.FAIL;
		}
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		if (status != 0) {
			u.put("status", status);
		}
		if (getC(DocName.DISCOUNT).update(new BasicDBObject("_id", id), new BasicDBObject("$set", u), false, false,
				WriteConcern.ACKNOWLEDGED).getN() > 0) {
			return ReCode.OK;
		}
		return ReCode.FAIL;
	}

	public ReCode drawConfig(Long id) {
		DBObject a = findConfigById(id);
		if (null == a) {
			return ReCode.FAIL;
		}
		DBObject uo = new BasicDBObject("$inc", new BasicDBObject("drawCount", Integer.valueOf(1))).append("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()));
		if (getC(DocName.DISCOUNT_CONFIG)
				.update(new BasicDBObject("_id", id), uo, false, false, WriteConcern.ACKNOWLEDGED).getN() > 0) {
			return ReCode.OK;
		}
		return ReCode.FAIL;

	}

	public ReMsg updateConfig(Long id, String name, Integer amount, Integer limitAmount, Long startTime, Long endTime,
			Integer status, Integer total, Integer type) {

		DBObject a = findConfigById(id);
		if (null == a) {
			return new ReMsg(ReCode.FAIL);
		}

		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());

		if (status != 0) {
			u.put("status", status);
		}
		if (amount != 0) {
			u.put("amount", amount);
		}
		if (limitAmount != 0) {
			u.put("limitAmount", limitAmount);
		}
		if (total != 0) {
			u.put("sort", total);
		}
		if (startTime != 0) {
			u.put("startTime", startTime);
		}
		if (endTime != 0) {
			u.put("endTime", endTime);
		}
		if (StringUtils.isNotBlank(name)) {
			u.put("name", name);
		}
		if (type != 0) {
			u.put("type", type);
		}

		if (getC(DocName.DISCOUNT_CONFIG).update(new BasicDBObject("_id", id), new BasicDBObject("$set", u), false,
				false, WriteConcern.ACKNOWLEDGED).getN() > 0) {
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);

	}

	public ReCode createDiscount(Long uid, Long configId) throws JsonParseException, JsonMappingException, IOException {
		DBObject dbds = findConfigById(configId);
		if (dbds != null) {
			DiscountConfig ds = DboUtil.toBean(dbds, DiscountConfig.class);
			Discount d = new Discount(uid, ds);
			getMongoTemplate().save(d);
			drawConfig(configId);
			return ReCode.OK;
		}
		return ReCode.FAIL;
	}

	public ReMsg saveConfig(Integer type, String name, Integer amount, Integer limitAmount, Long startTime,
			Long endTime, Integer status, Integer total) {
		Long id = super.getNextId(DocName.DISCOUNT_CONFIG);
		DiscountConfig dc = new DiscountConfig(id, name, type, amount, limitAmount, startTime, endTime, status, total);
		getMongoTemplate().save(dc);
		return new ReMsg(ReCode.OK, dc);
	}

}
