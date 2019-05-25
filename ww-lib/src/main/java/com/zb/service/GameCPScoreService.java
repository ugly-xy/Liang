package com.zb.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.DateUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.GameCPScore;

@Service
public class GameCPScoreService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(GameCPScoreService.class);

	@Autowired
	UserService userService;

	@Autowired
	RelationshipService relationshipService;

	// 最低上榜分数
	public static int SCORE_MIN = 0;

	public DBObject getUserScore(long uid, int gameType, int timeType, long time) {
		BasicDBObject q = new BasicDBObject("_id", GameCPScore.get_Id(uid, gameType, timeType, time));
		DBObject dbo = super.getC(DocName.GAMECPSCORE).findOne(q);
		if (dbo == null) {
			GameCPScore gameCPScore = new GameCPScore(uid, gameType, timeType, time, GameCPScore.DEFAULT_SCORE,
					System.currentTimeMillis());
			super.getMongoTemplate().save(gameCPScore);
			return DboUtil.beanToDBO(gameCPScore);
		}
		return dbo;
	}

	public Page<DBObject> query(int gameType, Integer timeType, Long time, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		List<DBObject> list = this.find(gameType, timeType, time, page, size);
		int count = count(gameType, timeType, time);
		return new Page<>(count, size, page, list);
	}

	public List<DBObject> find(int gameType, Integer timeType, Long time, Integer page, Integer size) {
		BasicDBObject q = new BasicDBObject("gameType", gameType).append("score", new BasicDBObject("$gt", SCORE_MIN));
		if (null != timeType) {
			q.append("timeType", timeType);
		}
		if (null != time) {
			q.append("time", time);
		}
		return super.getC(DocName.GAMECPSCORE).find(q).skip(getStart(page, size)).limit(size)
				.sort(new BasicDBObject("score", -1)).toArray();
	}

	public List<DBObject> find(int gameType, Integer timeType, Integer page, Integer size) {
		List<BasicDBObject> pipeline = new ArrayList<BasicDBObject>();
		BasicDBObject q = new BasicDBObject("gameType", gameType).append("timeType", timeType).append("score",
				new BasicDBObject("$gt", SCORE_MIN));
		pipeline.add(new BasicDBObject("$match", q));
		pipeline.add(new BasicDBObject("$group",
				new BasicDBObject("_id", "$uid").append("time", new BasicDBObject("$first", "$time"))
						.append("gameType", new BasicDBObject("$first", "$gameType"))
						.append("timeType", new BasicDBObject("$first", "$timeType"))
						.append("uid", new BasicDBObject("$first", "$uid"))
						.append("score", new BasicDBObject("$max", "$score"))));
		pipeline.add(new BasicDBObject("$sort", new BasicDBObject("score", -1).append("uid", 1)));
		pipeline.add(new BasicDBObject("$limit", size));
		Iterable<DBObject> dbos = getC(DocName.GAMECPSCORE).aggregate(pipeline).results();
		return IteratorUtils.toList(dbos.iterator());
	}

	public int count(int gameType, Integer timeType, Long time) {
		BasicDBObject q = new BasicDBObject("gameType", gameType);
		if (null != timeType) {
			q.append("timeType", timeType);
		}
		if (null != time) {
			q.append("time", time);
		}
		return super.getC(DocName.GAMECPSCORE).find(q).count();
	}

	/** 查询自己的排名 */
	public int findUserRankByScore(int gameType, int timeType, long time, int score) {
		BasicDBObject q = new BasicDBObject("score", new BasicDBObject("$gt", score)).append("gameType", gameType)
				.append("timeType", timeType).append("time", time);
		return super.getC(DocName.GAMECPSCORE).find(q).count() + 1;
	}

	/** 查看自己的排名和积分 */
	public ReMsg queryUserRank(long uid, int gameType, int timeType) {
		int userScore = getUserScore(uid, gameType, timeType);
		int rank = findUserRankByScore(gameType, getTimeType(timeType), getTime(timeType), userScore);
		Map<String, Integer> res = new HashMap<String, Integer>();
		res.put("score", userScore);
		res.put("rank", rank);
		return new ReMsg(ReCode.OK, res);
	}

	/** 查询玩家当前得分 游戏类型 日期类型 日期 */
	public int getUserScore(long uid, int gameType, int timeType) {
		DBObject dbo = null;
		if (timeType == GameCPScore.TIMETYPE_WEEK) {// 本周
			dbo = getUserScore(uid, gameType, getTimeType(timeType), getTime(timeType));
		} else if (timeType == GameCPScore.TIMETYPE_LASTWEEK) {// 上周
			dbo = getUserScore(uid, gameType, getTimeType(timeType), getTime(timeType));
		} else if (timeType == GameCPScore.TIMETYPE_MONTH) {// 本月
			dbo = getUserScore(uid, gameType, getTimeType(timeType), getTime(timeType));
		} else {
			dbo = getUserScore(uid, gameType, GameCPScore.TIMETYPE_ALL, getTime(GameCPScore.TIMETYPE_ALL));// 总
		}
		return DboUtil.getInt(dbo, "score");

	}

	/** 查询指定游戏积分榜单 */
	public ReMsg queryRankByGameType(int gameType, Integer timeType, Integer page, Integer size) {
		Map<String, Object> res = new HashMap<String, Object>();
		long time = getTime(timeType);
		res.put("time", time);
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String key = gameType + "-" + timeType;
		String objects = opsv.get(RK.keyRankListGameCP(key));
		if (objects != null) {
			res.put("data", (List<DBObject>) JSON.parse(objects));
		} else {
			List<DBObject> dbos = null;
			if (null != timeType && timeType != 0) {
				dbos = find(gameType, getTimeType(timeType), time, 1, 100);
			} else {// 名人堂
				dbos = find(gameType, GameCPScore.TIMETYPE_WEEK, 1, 30);
			}
			for (DBObject dbo : dbos) {
				DBObject user = userService.findById(DboUtil.getLong(dbo, "uid"));
				dbo.put("nickname", DboUtil.getString(user, "nickname"));
				dbo.put("avatar", userService.toMaxAvatar(user));
				dbo.put("point", DboUtil.getInt(user, "point"));
				dbo.put("vip", DboUtil.getInt(user, "vip"));
				dbo.put("sex", DboUtil.getInt(user, "sex"));
			}
			opsv.set(RK.keyRankListGameCP(key), JSON.serialize(dbos), 10, TimeUnit.MINUTES);
			res.put("data", dbos);
		}
		return new ReMsg(ReCode.OK, res);
	}

	/** 改变玩家的分值 周榜 月榜 总榜 */
	public void changeScore(long uid, int gameType, int change) {
		if (uid < 1) {
			return;
		}
		// 周
		DBObject scoreWeek = getUserScore(uid, gameType, GameCPScore.TIMETYPE_WEEK, getTime(GameCPScore.TIMETYPE_WEEK));
		scoreWeek.put("score",
				DboUtil.getInt(scoreWeek, "score") + change > 0 ? DboUtil.getInt(scoreWeek, "score") + change : 0);
		super.getC(DocName.GAMECPSCORE).save(scoreWeek);
		// // 月
		// DBObject scoreMonth = getUserScore(uid, gameType,
		// GameCPScore.TIMETYPE_MONTH,
		// getTime(GameCPScore.TIMETYPE_MONTH));
		// scoreMonth.put("score",
		// DboUtil.getInt(scoreMonth, "score") + change > 0 ?
		// DboUtil.getInt(scoreMonth, "score") + change : 0);
		// super.getC(DocName.GAMECPSCORE).save(scoreMonth);
		// // 总
		// DBObject scoreAll = getUserScore(uid, gameType,
		// GameCPScore.TIMETYPE_ALL, getTime(GameCPScore.TIMETYPE_ALL));
		// scoreAll.put("score",
		// DboUtil.getInt(scoreAll, "score") + change > 0 ?
		// DboUtil.getInt(scoreAll, "score") + change : 0);
		// super.getC(DocName.GAMECPSCORE).save(scoreAll);
	}

	/** 根据时间类型获取时间 */
	public long getTime(Integer timeType) {
		if (null != timeType) {
			if (timeType == GameCPScore.TIMETYPE_LASTWEEK) {// 上周
				return DateUtil.getWeekZeroTime() - Const.WEEK;
			} else if (timeType == GameCPScore.TIMETYPE_WEEK) {// 本周
				return DateUtil.getWeekZeroTime();
			} else if (timeType == GameCPScore.TIMETYPE_MONTH) {// 本月
				return DateUtil.getZeroTime(DateUtil.getMonthFirstDay());
			}
		}
		return GameCPScore.TIMETYPE_ALL;
	}

	public int getTimeType(int timeType) {
		if (timeType == GameCPScore.TIMETYPE_LASTWEEK) {
			return GameCPScore.TIMETYPE_WEEK;
		}
		return timeType;
	}

	/** CP游戏 save */
	public void saveGameCPHistory(DBObject uc, int gameType) {
		String[] arr = null;
		if (gameType == ActivityType.SCHOOL_WAR.getType()) {
			arr = (String[]) DboUtil.get(uc, "actors", Map.class).keySet().toArray(new String[] {});
		} else {
			List list = DboUtil.get(uc, "actors", ArrayList.class);
			if (null == list || list.size() != 2) {
				return;
			}
			arr = new String[] { list.get(0) + "", list.get(1) + "" };
		}
		Long st = DboUtil.getLong(uc, "st");
		Long winner = DboUtil.getLong(uc, "winner");
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (arr[i] != arr[j]) {
					try {
						saveCP(Long.parseLong(arr[i]), Long.parseLong(arr[j]), winner, st, gameType);
					} catch (Exception e) {
						log.error("保存失败", e);
					}
				}
			}
		}
	}

	/** CP游戏 save */
	private void saveCP(long myid, long uid, Long win, long updateTime, int gameType) {
		String fields = "";
		if (null == win || win == 0) {
			fields = "peace";
		} else if (myid == win) {
			fields = "win";
		} else {
			fields = "lost";
		}
		BasicDBObject inc = new BasicDBObject("$inc", new BasicDBObject(fields, Integer.valueOf(1))).append("$set",
				new BasicDBObject("updateTime", updateTime).append("gameType", gameType).append("sex",
						DboUtil.getInt(userService.findById(uid), "sex")));
		super.getMongoTemplate().getCollection(DocName.GAME_CP_HISTORY).findAndModify(
				new BasicDBObject("_id", myid + "-" + uid).append("myid", myid).append("uid", uid), null, null, false,
				inc, true, true);
	}

	/** CP游戏 find */
	public List<DBObject> findGameCPUser(long myid, Integer gameType, Integer sex, Integer page, Integer size) {
		BasicDBObject q = new BasicDBObject();
		if (null != sex) {
			q.append("sex", sex);
		}
		if (null != gameType) {
			q.append("gameType", gameType);
		}
		q.append("myid", myid);
		return getC(DocName.GAME_CP_HISTORY).find(q).sort(new BasicDBObject("updateTime", -1))
				.skip(getStart(page, size)).limit(super.getSize(size)).toArray();
	}

	/** 双人游戏对战记录 */
	public ReMsg gameCpUsersHistory(long myid, Integer gameType, Integer sex, Integer page, Integer size) {
		List<DBObject> cpUser = this.findGameCPUser(myid, gameType, sex, page, size);
		if (!cpUser.isEmpty()) {
			for (DBObject dbo : cpUser) {
				try {
					Long uid = DboUtil.getLong(dbo, "uid");
					DBObject rdbo = userService.findById(uid);
					if (rdbo != null) {
						dbo.put("nickname", DboUtil.getString(rdbo, "nickname"));
						dbo.put("sex", DboUtil.getInt(rdbo, "sex"));
						dbo.put("avatar", DboUtil.getString(rdbo, "avatar"));
						dbo.put("vip", null == DboUtil.getInt(rdbo, "vip") ? 0 : DboUtil.getInt(rdbo, "vip"));
						dbo.put("point", DboUtil.getInt(rdbo, "point"));
						dbo.put("friendWorth",
								null == DboUtil.getInt(rdbo, "friendWorth") ? 1 : DboUtil.getInt(rdbo, "friendWorth"));
						dbo.put("relationShip", isFriend(myid, uid));
					}
				} catch (Exception e) {
					log.error("查询游戏好友信息", e);
				}
			}
		}
		return new ReMsg(ReCode.OK, cpUser);
	}

	/** 校验是否是好友 */
	public boolean isFriend(long uid, long rid) {
		// 用户关系 0 陌生人 1 请求加好友 2 被请求加好友 3黑名单 4好友
		int r = (int) relationshipService.getRelation(uid, rid);
		return r == 4;
	}

	/** 查询和某个人的对战胜负 */
	public ReMsg gameCpOneHistory(long myId, long rid) {
		return new ReMsg(ReCode.OK,
				super.getC(DocName.GAME_CP_HISTORY).findOne(new BasicDBObject("_id", myId + "-" + rid)));
	}
}
