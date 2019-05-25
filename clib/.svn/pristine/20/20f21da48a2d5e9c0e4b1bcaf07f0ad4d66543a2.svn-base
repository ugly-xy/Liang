package com.we.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.Constant.ReCode;
import com.we.core.Page;
import com.we.models.DocName;

@Service
public class ThirdUserService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(ThirdUserService.class);

	@Autowired
	UserService userService;

	public DBObject findById(Long id) {
		return super.findById(DocName.THIRD_USER, id);
	}

	public Page<DBObject> queryCoinLog(Long uid, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findUserThirdId(uid, page, size);
		int count = countUserThirdId(uid);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countUserThirdId(Long uid) {
		DBObject q = new BasicDBObject();
		if (uid != null && uid != 0) {
			q.put("_id", uid);
		}

		return getC(DocName.THIRD_USER).find(q).count();
	}

	public List<DBObject> findUserThirdId(Long uid, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (uid != null && uid != 0) {
			q.put("_id", uid);
		}
		return getC(DocName.THIRD_USER).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}


	/** 根据第三方id找到用户 */
	public DBObject findByThirdId(Integer thirdIdType, String thirdId) {
		return super.getC(DocName.THIRD_USER).findOne(new BasicDBObject("ids." + thirdIdType+".openId", thirdId));
	}

//	/** 增加第三方id */
//	public ReCode putThirdId(long uid, Integer idType, String thirdId) {
//		String idKey = IDS + "." + idType;
//		DBObject incSeq = new BasicDBObject("$set",
//				new BasicDBObject("updateTime", System.currentTimeMillis()).append(idKey, thirdId));
//		getMongoTemplate().getCollection(DocName.THIRD_USER).findAndModify(new BasicDBObject("_id", uid),
//				new BasicDBObject(IDS, Integer.valueOf(1)), null, false, incSeq, false, true);
//
//		return ReCode.OK;
//	}

}
