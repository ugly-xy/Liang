package com.we.service;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.we.common.Constant.ReCode;
import com.we.common.redis.RK;
import com.we.core.web.ReMsg;
import com.we.models.DataView;
import com.we.models.DocName;

@Service
public class DataViewService extends BaseService {

//	static final Logger log = LoggerFactory.getLogger(DataViewService.class);
//
//	public static final DBObject q = new BasicDBObject("_id", new BasicDBObject("$gt", 0));
//
//	public DBObject findOne() {
//		return getC(DocName.DATA_VIEW).findOne(q);
//	}
//
//	public DBObject findByIdCache(Long id) {
//		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
//		String s = opsv.get(RK.keyDataView());
//		if (null == s) {
//			DBObject dbo = findOne();
//			if (null != dbo) {
//				opsv.set(RK.keyDataView(), JSON.serialize(dbo), 1, TimeUnit.HOURS);
//			}
//			return dbo;
//		} else {
//			return (DBObject) JSON.parse(s);
//		}
//	}
//
//	public ReMsg upSetDataView(Long id, Integer projectCnt, Long regCnt, Long sendCandyCnt, Long candyCnt) {
//		if (id == null || id < 1) {
//			id = super.getNextId(DocName.DATA_VIEW);
//		}
//		DataView view = new DataView(projectCnt, regCnt, sendCandyCnt, candyCnt);
//		view.set_id(id);
//		view.setUpdateTime(System.currentTimeMillis());
//		getMongoTemplate().save(view);
//		return new ReMsg(ReCode.OK);
//	}
//
//	/** 增加项目数 */
//	public void incProjectCnt(Integer cnt) {
//		inc("projectCnt", cnt);
//	}
//
//	/** 增加注册人数 */
//	public void incRegCnt(Integer cnt) {
//		inc("regCnt", cnt);
//	}
//
//	/** 增加送出的candy数量 */
//	public void incSendCandyCnt(Integer cnt) {
//		inc("sendCandyCnt", cnt);
//	}
//
//	/** 增加 */
//	private void inc(String fields, Integer cnt) {
//		DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject(fields, Integer.valueOf(cnt))).append("$set",
//				new BasicDBObject("updateTime", System.currentTimeMillis()));
//		getMongoTemplate().getCollection(DocName.DATA_VIEW).findAndModify(q, null, null, false, incSeq, false, true);
//	}
}
