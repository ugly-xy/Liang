package com.we.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.Constant.ReCode;
import com.we.common.mongo.DboUtil;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.models.DocName;
import com.we.models.UserShare;

@Service
public class UserShareService extends BaseService {
	
	@Autowired
	UserService userService;

	static final Logger log = LoggerFactory.getLogger(UserShareService.class);

	public Page<DBObject> query(Long _id, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(_id, page, size);
		int count = count(_id);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int count(Long _id) {
		DBObject q = new BasicDBObject();
		if (_id != null && _id > 0) {
			q.put("_id", _id);
		}
		return getC(DocName.USER_SHARE).find(q).count();
	}

	public List<DBObject> find(Long _id, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (_id != null && _id > 0) {
			q.put("_id", _id);
		}
		return getC(DocName.USER_SHARE).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("updateTime", -1)).toArray();
	}

	public DBObject findById(long _id) {
		return getC(DocName.USER_SHARE).findOne(new BasicDBObject("_id", _id));
	}

	/** 增加一个通过分享注册的m数目 */
	public void addUserShareCnt(long uid, String field) {
		DBObject incSeq = new BasicDBObject("$inc",
				new BasicDBObject(field, Integer.valueOf(1)).append(UserShare.TOTAL_CNT, Integer.valueOf(1)))
						.append("$set", new BasicDBObject("updateTime", System.currentTimeMillis()));
		getMongoTemplate().getCollection(DocName.USER_SHARE).findAndModify(new BasicDBObject("_id", uid), null, null,
				false, incSeq, false, true);
	}

	/** 增加通过邀请网络获取的货币数 */
	public void addUserShareCoin(long uid, long amount) {
		DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject("amount", amount)).append("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()));
		getMongoTemplate().getCollection(DocName.USER_SHARE).findAndModify(new BasicDBObject("_id", uid), null, null,
				false, incSeq, false, true);
	}
	//查m1,m2,m3
	public Map<String, Object> findShareIncludeUser(Long uid) {
		Map<String, Object> res=new HashMap<>();
		DBObject userShare = this.findById(uid);
		res.put("userShare",userShare);
		if (userShare != null) {
			List<Long> list = new ArrayList<>();
			list.add(uid);
			List<DBObject> m1 = userService.findUser(list) ;
			for(DBObject dbo:m1) {
				String phone = DboUtil.getString(dbo, "phone");
				if(phone!=null&&phone.length()>8) {
					dbo.put("phone", phone.replace(phone.substring(2, 8), "******"));
				}
			}
			res.put("userM1", m1);
			//m2
//			List<DBObject> m2 = getUserNextLevel(m1) ;
//			res.put("userM2", m2);
//			//m3
//			List<DBObject> m3 = getUserNextLevel(m2) ;
//			res.put("userM3", m3);
		}
		
		return res;
	}
	
	public List<DBObject> getUserNextLevel(List<DBObject> m){
		List<Long> list = new ArrayList<>();
		if(m!=null) {
			for(DBObject db:m) {
				if(db!=null) {
					list.add(DboUtil.getLong(db, "_id"));
				}
			}
		}
		return userService.findUser(list);
	}

}
