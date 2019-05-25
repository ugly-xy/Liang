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
import com.zb.models.DocName;
import com.zb.models.res.Airport;

@Service
public class AirPortService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(AirPortService.class);

	public DBObject findAirPortById(Long id) {
		return getC(DocName.AIR_PORT).findOne(new BasicDBObject("_id", id));
	}

	public Page<DBObject> queryAirPort(String city, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findAirPort(city, page, size);
		int count = countAirPort(city);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countAirPort(String city) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(city)) {
			q.put("city", city);
		}
		return getC(DocName.AIR_PORT).find(q).count();
	}

	public List<DBObject> findAirPort(String city, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(city)) {
			q.put("city", city);
		}
		return getC(DocName.AIR_PORT).find(q).skip(super.getStart(page, size))
				.limit(getSize(size)).sort(new BasicDBObject("sort", -1))
				.toArray();
	}

	public ReMsg saveAirPort(Long id, String name, String city, String enCity,
			String code3, String code4) {
		if (null == id) {
			id = super.getNextId(DocName.AIR_PORT);
		}
		Airport zbtc = new Airport(id, name, city, enCity, code3, code4);
		getMongoTemplate().save(zbtc);
		return new ReMsg(ReCode.OK, zbtc);
	}

}
