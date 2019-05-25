package com.zb.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.zb.common.Constant.Const;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.DateUtil;
import com.zb.models.DocName;
import com.zb.models.WinCoinRank;

@Service
public class WinCoinRankService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(WinCoinRankService.class);

	@Autowired
	UserService userService;

	private static final int RANK_SIZE = 30;// 赢金榜长度

	/** 查询赢币榜单 */
	public List<DBObject> findWinLog(String time, int size, int activityType) {
		BasicDBObject q = new BasicDBObject("time", time).append("type", activityType);
		return super.getC(DocName.WINCOINRANK).find(q).limit(size).sort(new BasicDBObject("income", -1)).toArray();
	}

	/** 查询个人 赢币 */
	public long findOneWinLog(String time, long uid, int activityType) {
		String _id = uid + "-" + time + "-" + activityType;
		DBObject winCoinLog = super.getC(DocName.WINCOINRANK).findOne(new BasicDBObject("_id", _id));
		return null == winCoinLog ? 0 : DboUtil.getLong(winCoinLog, "income");
	}

	public String getStrTime(int type) {
		Date date = new Date();
		if (type == -1) {// 昨天
			return DateUtil.dateFormatyyyyMMdd(new Date(date.getTime() - 24 * Const.HOUR));
		}
		// 默认返回今天
		return DateUtil.dateFormatyyyyMMdd(date);
	}

	/** 查询赢币 */
	public Map<String, Object> queryWinLog(int type, int activityType) {
		Map<String, Object> map = new HashMap<String, Object>();
		String time = getStrTime(type);
		List<DBObject> list = null;
		String key = time + "-" + type + "-" + activityType;
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String objects = opsv.get(RK.rankListWinCoinLog(key));
		if (objects != null) {
			list = (List<DBObject>) JSON.parse(objects);
		} else {
			list = findWinLog(time, RANK_SIZE, activityType);
			opsv.set(RK.rankListWinCoinLog(key), JSON.serialize(list), 10, TimeUnit.MINUTES);
		}
		long uid = super.getUid();
		long coin = findOneWinLog(time, uid, activityType);
		int rank = 0;
		for (int i = 0; i < list.size(); i++) {
			if (uid == DboUtil.getLong(list.get(i), "uid")) {
				rank = i + 1;
				break;
			}
		}
		map.put("rank", rank);
		map.put("coin", coin);
		map.put("list", list);
		return map;
	}

	/** 保存赢币 */
	public void saveWinLog(long uid, String time, long income, int activityType) {
		String _id = uid + "-" + time + "-" + activityType;
		// 查询条件
		BasicDBObject q = new BasicDBObject("_id", _id);
		if (super.getC(DocName.WINCOINRANK).find(q).count() > 0) {
			super.getC(DocName.WINCOINRANK).update(q, new BasicDBObject("$inc", new BasicDBObject("income", income)));
		} else {
			DBObject user = userService.findById(uid);
			String nickname = DboUtil.getString(user, "nickname");
			String avatar = DboUtil.getString(user, "avatar");
			WinCoinRank wr = new WinCoinRank(_id, uid, income, time, nickname, avatar, activityType);
			super.getMongoTemplate().save(wr);
		}
	}
}
