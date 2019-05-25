package com.we.service.userTask;

import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.we.models.userTask.TokenPrj;
import com.we.service.BaseService;


@Service
public class TokenPrjService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(TokenPrjService.class);

	public Page<DBObject> query(Integer status, String _id, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(_id, status, page, size);
		int count = count(_id, status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int count(String _id, Integer status) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(_id)) {
			q.put("_id", new BasicDBObject("$regex", _id));
		}
		if (status != null && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.TOKEN_PRJ).find(q).count();
	}

	public List<DBObject> find(String _id, Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(_id)) {
			q.put("_id", new BasicDBObject("$regex", _id));
		}
		if (status != null && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.TOKEN_PRJ).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", -1).append("updateTime", -1)).toArray();
	}

	public DBObject findById(String _id) {
		return getC(DocName.TOKEN_PRJ).findOne(new BasicDBObject("_id", _id));
	}
	
	public TokenPrj getTokenPrj(String symbol) {
		return DboUtil.toBean(findById(symbol), TokenPrj.class);
	}

	/** 根据状态(上下线)查询所有的货币 */
	public List<DBObject> findAllCoinType(Integer status) {
		DBObject q = new BasicDBObject();
		if (status != null && status > 0) {
			q.put("status", status);
		}
		return getC(DocName.TOKEN_PRJ).find(q).toArray();
	}

	/** 更改货币状态 */
	public ReMsg status(final String _id, final Integer status, long adminId) {
		super.getC(DocName.TOKEN_PRJ).update(new BasicDBObject("_id", _id),
				new BasicDBObject("$set", new BasicDBObject("status", status).append("adminId", adminId)
						.append("updateTime", System.currentTimeMillis())));
		return new ReMsg(ReCode.OK);
	}

	/** 增加或修改货币 */
	public ReCode upset(String symbol, String tokenName, String pic, int status,  int sort, String summary,
			String url, Double price, String stage, String platform, String country, Boolean candy, String coinUnit,
			Long initialAmount) {
		long adminId = super.getUid();
		if(adminId<1) {
			return ReCode.NOT_AUTHORIZED;
		}
		TokenPrj coinType = new TokenPrj( symbol,  tokenName,  pic,  status,  adminId,  sort,  summary,
				 url,  price,  stage,  platform,  country,  candy,  coinUnit,
				 initialAmount);
		coinType.setAdminId(adminId);
		coinType.setUpdateTime(System.currentTimeMillis());
		super.getMongoTemplate().save(coinType);
		return ReCode.OK;
	}

}
