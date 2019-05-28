package com.zb.service.jobs;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Const;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.DateUtil;
import com.zb.models.DocName;
import com.zb.models.GameCPScore;
import com.zb.models.TitleModel;
import com.zb.models.UserTitle;
import com.zb.models.finance.CoinLog;
import com.zb.models.goods.BaseGoods;
import com.zb.service.AppCoverService;
import com.zb.service.BaseService;
import com.zb.service.EnvService;
import com.zb.service.GameCPScoreService;
import com.zb.service.GiftRecvTopService;
import com.zb.service.MessageService;
import com.zb.service.RankListService;
import com.zb.service.UserService;
import com.zb.service.WinCoinRankService;
import com.zb.service.pay.CoinService;
import com.zb.service.room.SakuranService;

public class RankListJob extends BaseService {
	static final Logger log = LoggerFactory.getLogger(RankListJob.class);

	@Autowired
	RankListService rankListService;

	@Autowired
	CoinService coinService;

	@Autowired
	EnvService envService;

	@Autowired
	MessageService messageService;

	@Autowired
	AppCoverService appCoverService;

	@Autowired
	GiftRecvTopService giftRecvTopService;

	@Autowired
	UserService userService;

	@Autowired
	SakuranService sakuranService;

	@Autowired
	GameCPScoreService gameCPScoreService;

	@Autowired
	WinCoinRankService winCoinRankService;

	public static final int SIZE = 10;
	public static final int SIZE_30 = 30;
	public static final int TYPE = 131;

	public static final int SAKURAN_TITLE_SIZE = 3;// 花魁大乱斗称号

	// 圣诞活动奖励名次
	public static final int ARTICLE_FLOWER = 3;

	// 情人节送花活动
	public static final int VALENTINE_DAY_START = 20180210;
	public static final int VALENTINE_DAY_END = 20180214;

	// 圈子活动
	public static final int GROUP_START = 20180313;
	public static final int GROUP_END = 20180318;

	// 花魁榜特殊称号
	public static final int DAY_0214 = 20180214;

	public static final int COIN_START = 20180313;
	public static final int COIN_END = 20180318;

	public void execute() throws ParseException {
		if (rankListService.lock(RK.rankListLock(), 60L)) {
			try {
				int day = RankListService.getDay(RankListService.YESTERDAY);
				log.error("发放魅力榜富豪榜奖励");
				senRewardCharmToday(day);
				log.error("发放你画我猜风云榜奖励");
				sendDrawSth();
				log.error("发放游戏赢金榜奖励");
				sendGameWinCoinRankTitle();

				String monthFirstDay = DateUtil.dateFormatyyyyMMdd(DateUtil.getMonthFirstDay());
				String today = DateUtil.dateFormatyyyyMMdd(new Date());
				if (monthFirstDay.equals(today)) {
					log.error("发放月花魁榜称号奖励");
					monthTitle();
				}

				Calendar c = Calendar.getInstance();
				if (2 == c.get(Calendar.DAY_OF_WEEK)) {
					log.error("发放小游戏周榜奖励");
					littleGameCoinReward();
				}

				// if (day >= GROUP_START && day <= GROUP_END) {
				// log.error("发放圈子称号奖励");
				// sendArticleFlowerTitle();
				// }
				//
				// if (day >= VALENTINE_DAY_START && day <= VALENTINE_DAY_END) {
				// log.error("发放情人节称号奖励");
				// sendValentineFlowerTitle(day);
				// }
			} finally {
				rankListService.unlock(RK.rankListLock());
			}
		}

	}

	static final int[] littleGame_coins = new int[] { 0, 30000, 20000, 10000 };

	public void littleGameCoinReward() {
		long time = gameCPScoreService.getTime(GameCPScore.TIMETYPE_LASTWEEK);
		Long uid;
		// 斗兽棋
		int i = 0;
		List<DBObject> list = gameCPScoreService.find(ActivityType.ANIMAL_CHESS.getType(), 7, time, 1, 3);
		for (DBObject dbo : list) {
			i++;
			uid = DboUtil.getLong(dbo, "uid");
			coinService.addCoin(uid, CoinLog.RANKLIST, getIntDay(), littleGame_coins[i], 0,
					getIntDay() + "斗兽棋周榜第" + i + "名" + littleGame_coins[i] + "金币");
			messageService.imMsgHandler(Const.SYSTEM_ID, uid, "恭喜玩家获得斗兽棋周榜第" + i + "名，奖励您" + littleGame_coins[i] + "金币",
					null, null);
		}
		// 教室大作战
		i = 0;
		list = gameCPScoreService.find(ActivityType.SCHOOL_WAR.getType(), 7, time, 1, 3);
		for (DBObject dbo : list) {
			i++;
			uid = DboUtil.getLong(dbo, "uid");
			coinService.addCoin(uid, CoinLog.RANKLIST, getIntDay(), littleGame_coins[i], 0,
					getIntDay() + "教室大作战周榜第" + i + "名" + littleGame_coins[i] + "金币");
			messageService.imMsgHandler(Const.SYSTEM_ID, uid,
					"恭喜玩家获得教室大作战周榜第" + i + "名，奖励您" + littleGame_coins[i] + "金币", null, null);
		}
		// 五子棋
		i = 0;
		list = gameCPScoreService.find(ActivityType.GOMOKU.getType(), 7, time, 1, 3);
		for (DBObject dbo : list) {
			i++;
			uid = DboUtil.getLong(dbo, "uid");
			coinService.addCoin(uid, CoinLog.RANKLIST, getIntDay(), littleGame_coins[i], 0,
					getIntDay() + "五子棋周榜第" + i + "名" + littleGame_coins[i] + "金币");
			messageService.imMsgHandler(Const.SYSTEM_ID, uid, "恭喜玩家获得五子棋周榜第" + i + "名，奖励您" + littleGame_coins[i] + "金币",
					null, null);
		}
		// 扫雷
		i = 0;
		list = gameCPScoreService.find(ActivityType.WINMINE.getType(), 7, time, 1, 3);
		for (DBObject dbo : list) {
			i++;
			uid = DboUtil.getLong(dbo, "uid");
			coinService.addCoin(uid, CoinLog.RANKLIST, getIntDay(), littleGame_coins[i], 0,
					getIntDay() + "扫雷周榜第" + i + "名" + littleGame_coins[i] + "金币");
			messageService.imMsgHandler(Const.SYSTEM_ID, uid, "恭喜玩家获得扫雷周榜第" + i + "名，奖励您" + littleGame_coins[i] + "金币",
					null, null);
		}
		// 神经猫
		i = 0;
		list = gameCPScoreService.find(ActivityType.NEURO_CAT.getType(), 7, time, 1, 3);
		for (DBObject dbo : list) {
			i++;
			uid = DboUtil.getLong(dbo, "uid");
			coinService.addCoin(uid, CoinLog.RANKLIST, getIntDay(), littleGame_coins[i], 0,
					getIntDay() + "哈皮兔周榜第" + i + "名" + littleGame_coins[i] + "金币");
			messageService.imMsgHandler(Const.SYSTEM_ID, uid, "恭喜玩家获得哈皮兔周榜第" + i + "名，奖励您" + littleGame_coins[i] + "金币",
					null, null);
		}
	}

	static final int[] coins = new int[] { 0, 50000, 20000, 10000 };
	static final int[] coins_double = new int[] { 0, 100000, 40000, 20000 };

	/** 游戏称号 */
	public void sendGameWinCoinRankTitle() {
		String time = winCoinRankService.getStrTime(-1);
		// 花魁乱斗称号
		List<DBObject> winLog = winCoinRankService.findWinLog(time, SAKURAN_TITLE_SIZE, ActivityType.SAKURAN.getType());
		String body = null;
		int i = 1;
		int[] rewards = coins;
		Calendar c = Calendar.getInstance();
		if (7 == c.get(Calendar.DAY_OF_WEEK)) {// 今天是周六 发周五的金币奖励 翻倍
			rewards = coins_double;
		}
		for (DBObject dbo : winLog) {
			if (i > SAKURAN_TITLE_SIZE) {
				break;
			}
			body = "恭喜您在花魁大乱斗每日赢金榜中,当日赢金数第" + i + "名";
			Long uid = DboUtil.getLong(dbo, "uid");
			TitleModel tm = TitleModel.getUserTitleModel(UserTitle.SAKURAN_WIN, i);
			if (tm != null) {
				userService.saveTitle(uid, tm);
				coinService.addCoin(uid, CoinLog.RANKLIST, getIntDay(), rewards[i], 0,
						getIntDay() + "花魁乱斗赢金榜第" + i + "名" + rewards[i] + "金币");
				body = body + "，荣获" + tm.getName() + "称号！称号有效期为" + tm.getVal() + "天。获得" + rewards[i]
						+ "金币奖励，系统已经将奖励发放至您的账户当中，请注意查收";
				messageService.imMsgHandler(Const.SYSTEM_ID, uid, body, null, null);
			} else {
				log.error("TitleModel is null");
			}
			i++;
			body = null;
		}
		// 牛牛称号
		winLog = winCoinRankService.findWinLog(time, SAKURAN_TITLE_SIZE, ActivityType.COW_FATHER.getType());
		i = 1;
		for (DBObject dbo : winLog) {
			if (i > SAKURAN_TITLE_SIZE) {
				break;
			}
			body = "恭喜您在斗牛每日赢金榜中,当日赢金数第" + i + "名";
			Long uid = DboUtil.getLong(dbo, "uid");
			TitleModel tm = TitleModel.getUserTitleModel(UserTitle.COW_WIN, i);
			if (tm != null) {
				userService.saveTitle(uid, tm);
				coinService.addCoin(uid, CoinLog.RANKLIST, getIntDay(), rewards[i], 0,
						getIntDay() + "斗牛赢金榜第" + i + "名" + rewards[i] + "金币");
				body = body + "，荣获" + tm.getName() + "称号！称号有效期为" + tm.getVal() + "天。获得" + rewards[i]
						+ "金币奖励，系统已经将奖励发放至您的账户当中，请注意查收";
				messageService.imMsgHandler(Const.SYSTEM_ID, uid, body, null, null);
			} else {
				log.error("TitleModel is null");
			}
			i++;
			body = null;
		}

		// 德州称号
//		winLog = winCoinRankService.findWinLog(time, SAKURAN_TITLE_SIZE, ActivityType.TEXAX_POKER_FATHER.getType());
//		i = 1;
//		for (DBObject dbo : winLog) {
//			if (i > SAKURAN_TITLE_SIZE) {
//				break;
//			}
//			body = "恭喜您在德州扑克每日赢金榜中,当日赢金数第" + i + "名";
//			Long uid = DboUtil.getLong(dbo, "uid");
//			TitleModel tm = TitleModel.getUserTitleModel(UserTitle.TEXAS_WIN, i);
//			if (tm != null) {
//				userService.saveTitle(uid, tm);
//				coinService.addCoin(uid, CoinLog.RANKLIST, getIntDay(), rewards[i], 0,
//						getIntDay() + "德州扑克赢金榜第" + i + "名" + rewards[i] + "金币");
//				body = body + "，荣获" + tm.getName() + "称号！称号有效期为" + tm.getVal() + "天。获得" + rewards[i]
//						+ "金币奖励，系统已经将奖励发放至您的账户当中，请注意查收";
//				messageService.imMsgHandler(Const.SYSTEM_ID, uid, body, null, null);
//			} else {
//				log.error("TitleModel is null");
//			}
//			i++;
//			body = null;
//		}
	}

	/** 情人节期间送花收花称号 */
	private void sendValentineFlowerTitle(int day) {
		long recvUid = 0;
		long sendUid = 0;
		String recvNickname = null;
		String sendNickname = null;
		long count = 0;
		TitleModel sendTitle = TitleModel.getUserTitleModel(UserTitle.FLOWER_SEND, 1);
		TitleModel recvTitle = TitleModel.getUserTitleModel(UserTitle.FLOWER_RECV, 1);
		if (sendTitle != null && recvTitle != null) {
			String sendBody = "，荣获" + sendTitle.getName() + "称号！称号有效期为" + sendTitle.getVal() + "天。";
			String recvBody = "，荣获" + recvTitle.getName() + "称号！称号有效期为" + recvTitle.getVal() + "天。";
			DBObject q = new BasicDBObject("day", day).append("count", new BasicDBObject("$gte", 52014));
			List<DBObject> dbos = getC(DocName.USER_GIFT).find(q).sort(new BasicDBObject("count", -1).append("uid", 1))
					.toArray();
			for (DBObject dbo : dbos) {
				sendUid = DboUtil.getLong(dbo, "sendUid");
				recvUid = DboUtil.getLong(dbo, "uid");
				sendNickname = "" + DboUtil.getString(userService.findById(sendUid), "nickname");
				recvNickname = "" + DboUtil.getString(userService.findById(recvUid), "nickname");
				count = DboUtil.getLong(dbo, "count");
				messageService.imMsgHandler(Const.SYSTEM_ID, sendUid,
						"您昨日赠送 " + recvNickname + " " + count + "朵鲜花" + sendBody, null, null);
				messageService.imMsgHandler(Const.SYSTEM_ID, recvUid,
						"昨日您被 " + sendNickname + " 赠送" + count + "朵鲜花" + recvBody, null, null);
				userService.saveTitle(sendUid, sendTitle);
				userService.saveTitle(recvUid, recvTitle);
			}
		} else {
			log.error("TitleModel is null");
		}
	}

	/** 添加守护特效 */
	public void addGuardEffects(int start, int end) {
		List<BasicDBObject> pipeline = new ArrayList<BasicDBObject>();
		BasicDBObject q = new BasicDBObject("bgId", BaseGoods.Gift.FLOWER.getV().getId()).append("day",
				new BasicDBObject("$gte", start).append("$lte", end));
		pipeline.add(new BasicDBObject("$match", q));
		pipeline.add(new BasicDBObject("$group",
				new BasicDBObject("_id", new BasicDBObject("sendUid", "$sendUid").append("recvUid", "$uid"))
						.append("count", new BasicDBObject("$sum", "$count"))));
		pipeline.add(new BasicDBObject("$sort", new BasicDBObject("count", -1).append("_id", 1)));
		// 送花
		Iterable<DBObject> dbos = getC(DocName.USER_GIFT).aggregate(pipeline).results();
		List<DBObject> list = IteratorUtils.toList(dbos.iterator());
		DBObject user = null;
		long sendUid;
		long recvUid;
		Long curGuid;
		long time = System.currentTimeMillis() + Const.DAY * 28;
		for (DBObject dbo : list) {
			if (DboUtil.getLong(dbo, "count") >= 99999) {
				DBObject _id = DboUtil.get(dbo, "_id", DBObject.class);
				sendUid = DboUtil.getLong(_id, "sendUid");
				recvUid = DboUtil.getLong(_id, "recvUid");
				user = userService.findById(recvUid);
				if (null != user) {
					curGuid = DboUtil.getLong(user, "guard");
					if (curGuid != null && curGuid == sendUid) {// 守护送花累计>99999
						DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis())
								.append("guardExpiryTime", time);
						userService.update(recvUid, u);
					}
				}
				continue;
			}
			break;
		}
	}

	// /** 双旦活动期间 每日送花称号 */
	// private void sendChristmasSendTitle(int day) {
	// List<DBObject> list = rankListService.find(day,
	// BaseGoods.Gift.FLOWER.getV().getId(), 1, SIZE,
	// DocName.GIFT_SEND);
	// int i = 1;
	// String body = null;
	// for (DBObject dbo : list) {
	// if (i > SIZE) {
	// break;
	// }
	// // transDate(start, end);
	// body = "恭喜您在双旦狂欢节活动中,当日送花数量第" + i + "名";
	// long uid = DboUtil.getLong(dbo, "uid");
	// TitleModel tm = TitleModel.getUserTitleModel(UserTitle.FLOWER_SEND, i);
	// if (tm != null) {
	// userService.saveTitle(uid, tm);
	// body = body + "，荣获" + tm.getName() + "称号！称号有效期为" + tm.getVal() + "天。";
	// messageService.imMsgHandler(Const.SYSTEM_ID, uid, body, null, null);
	// } else {
	// log.error("TitleModel is null");
	// }
	// i++;
	// body = null;
	// }
	// }
	//
	// /** 双旦活动期间 每日送花称号 */
	// private void sendChristmasSendTitle(int day) {
	// List<DBObject> list = rankListService.find(day,
	// BaseGoods.Gift.FLOWER.getV().getId(), 1, SIZE,
	// DocName.GIFT_SEND);
	// int i = 1;
	// String body = null;
	// for (DBObject dbo : list) {
	// if (i > SIZE) {
	// break;
	// }
	// // transDate(start, end);
	// body = "恭喜您在双旦狂欢节活动中,当日送花数量第" + i + "名";
	// long uid = DboUtil.getLong(dbo, "uid");
	// TitleModel tm = TitleModel.getUserTitleModel(UserTitle.FLOWER_SEND, i);
	// if (tm != null) {
	// userService.saveTitle(uid, tm);
	// body = body + "，荣获" + tm.getName() + "称号！称号有效期为" + tm.getVal() + "天。";
	// messageService.imMsgHandler(Const.SYSTEM_ID, uid, body, null, null);
	// } else {
	// log.error("TitleModel is null");
	// }
	// i++;
	// body = null;
	// }
	// }
	//
	// /** 双旦称号奖励 累计收花的称号 */
	// private void sendChristmasRecvTitle(int start, int end, int type, String
	// detail) {
	// // 活动累计收花榜
	// List<DBObject> dbos = rankListService.getDateRangeFlowerTop(start, end,
	// RankListService.CHARM, CHRISTMAS_SIZE);
	// int i = 1;
	// String body = null;
	// for (DBObject dbo : dbos) {
	// if (i > CHRISTMAS_SIZE) {
	// break;
	// }
	// body = "恭喜您在" + detail + "活动中,收花数量累计第" + i + "名";
	// long uid = DboUtil.getLong(dbo, "_id");
	// DBObject user = userService.findByIdSafe(uid);
	// int sex = DboUtil.getInt(user, "sex");
	// TitleModel tm = TitleModel.getUserTitleModel(type, i, sex);
	// if (tm != null) {
	// userService.saveTitle(uid, tm);
	// body = body + "，荣获" + tm.getName() + "称号！称号有效期为" + tm.getVal() + "天。";
	// messageService.imMsgHandler(Const.SYSTEM_ID, uid, body, null, null);
	// } else {
	// log.error("TitleModel is null");
	// }
	// i++;
	// body = null;
	// }
	// }

	/** 活动期间圈子帖子收花称号 */
	private void sendArticleFlowerTitle() {
		List<DBObject> dbos = rankListService.getChristmasArticleFlowerTop("白色情人节", 1003, ARTICLE_FLOWER);
		String body = null;
		int i = 1;
		TitleModel tm = TitleModel.getUserTitleModel(UserTitle.FLOWER_GROUP, i);
		for (DBObject dbo : dbos) {
			long uid = DboUtil.getLong(dbo, "_id");
			body = "恭喜您在圈子活动【白色情人节】中,当日发帖收花数量第" + i + "名";
			if (i == 1) {// 第一名签名特权
				userService.updateModifyLabel(uid, true);
				body = body + "，赠送您修改签名的特权";
			} else {// 2 3名3000金币
				coinService.addCoin(uid, CoinLog.RANKLIST, getIntDay(), 3000, 0,
						"圣诞活动" + getStringDay() + " 圈子收花第" + i + "名奖励3000金币");
				body = body + "，赠送您3000金币";
			}
			if (tm != null) {
				userService.saveTitle(uid, tm);
				body = body + "，荣获" + tm.getName() + "称号！称号有效期为" + tm.getVal() + "天。";
				messageService.imMsgHandler(Const.SYSTEM_ID, uid, body, null, null);
			} else {
				log.error("TitleModel is null");
			}
			i++;
			body = null;
		}
	}

	private void monthTitle() {
		List<DBObject> list = giftRecvTopService.findLastMonth();
		final Map<Long, Integer> map = new HashMap<>();
		list.parallelStream().forEach(e -> {
			Long uid = DboUtil.getLong(e, "uid");
			Integer s = map.get(uid);
			if (null != s) {
				map.put(uid, s + 1);
			} else {
				map.put(uid, 1);
			}
		});
		List<Long> users = new ArrayList<>();
		for (Long e : map.keySet()) {
			Integer count = map.get(e);
			if (count >= 3) {
				users.add(e);
			}
		}
		for (Long e : users) {
			int sex = DboUtil.getInt(userService.findByIdSafe(e), "sex");
			TitleModel tm = TitleModel.getModel(UserTitle.FLOWER, 0, sex);
			userService.saveTitle(e, tm);
			String title = tm.getName();
			messageService.imMsgHandler(Const.SYSTEM_ID, e, "恭喜您在一个自然月内累计3次登上花魁榜，荣获" + title + "称号！有效期为一个月。", null,
					null);
		}
	}

	// 发放昨天魅力榜的奖励
	public void senRewardCharmToday(int day) {
		List<DBObject> list = rankListService.find(day, BaseGoods.Gift.FLOWER.getV().getId(), 1, SIZE_30,
				DocName.GIFT_RECV);
		int i = 1;
		for (DBObject dbo : list) {
			long uid = DboUtil.getLong(dbo, "uid");
			DBObject user = userService.findById(uid);
			int sex = DboUtil.getInt(user, "sex");
			TitleModel tm = TitleModel.getFlowerModel(i, sex);
			if (tm != null) {
				userService.saveTitle(uid, tm);
			} else {
				log.error("TitleModel is null");
			}
			String title = "";
			int val = 0;
			title = tm.getName();
			val = tm.getVal();
			String titleMsg = "荣获" + title + "称号！称号有效期为" + val + "天。";
			if (i <= 3) {
				// 拥有修改签名的权限
				userService.updateModifyLabel(uid, true);
				if (i == 1) {// 第一名
					if (day == DAY_0214) {// 特殊时间
						if (null != DboUtil.getLong(user, "guard")) {
							TitleModel guardTm = TitleModel.getFlowerModel(i, sex == 1 ? 2 : 1);
							if (tm != null) {// 给守护送称号
								userService.saveTitle(DboUtil.getLong(user, "guard"), guardTm);
								messageService.imMsgHandler(Const.SYSTEM_ID, DboUtil.getLong(user, "guard"),
										"恭喜您所守护的用户【" + DboUtil.getString(user, "nickname") + "】在 " + getStringDay()
												+ "魅力花魁榜中荣获第" + i + "名,赠送您" + guardTm.getName() + "称号！称号有效期为"
												+ guardTm.getVal() + "天。",
										null, null);
							}
						}

					}
					// 存储花魁到名人堂
					giftRecvTopService.save2GiftRecvTop(dbo, user);
					titleMsg = "荣获" + title + "称号！并被记入花魁名人堂，称号有效期为" + val + "天。";
					// 判断是否需要存储封面
					Calendar c = Calendar.getInstance();
					// c.setFirstDayOfWeek(Calendar.MONDAY);
					if (1 == c.get(Calendar.DAY_OF_WEEK)) {//
						appCoverService.saveAppCover(DboUtil.getLong(dbo, "uid"));
						// titleMsg = "恭喜您成为周花魁，荣获" + title +
						// "称号！并被记入花魁名人堂，称号有效期为"
						// + val
						// + "天。同时获得周花魁封面奖励，系统会将您提交的照片作为游戏启动页，进行为期一周的展示！";
					}
				}
			}
			if (i <= SIZE) {
				coinService.addCoin(DboUtil.getLong(dbo, "uid"), CoinLog.RANKLIST, getIntDay(),
						20000 - ((i - 1) * 2000), 0,
						getIntDay() + " 魅力榜第" + i + "名 " + (20000 - ((i - 1) * 2000)) + "金币");
				messageService.imMsgHandler(
						Const.SYSTEM_ID, DboUtil.getLong(dbo, "uid"), "恭喜您在 " + getStringDay() + "魅力花魁榜中荣获第" + i + "名,"
								+ titleMsg + "获得" + (20000 - ((i - 1) * 2000)) + "金币奖励，系统已将奖励发放至您的账户当中，请注意查收。",
						null, null);
			} else if (i <= SIZE_30) {
				messageService.imMsgHandler(Const.SYSTEM_ID, DboUtil.getLong(dbo, "uid"),
						"恭喜您在 " + getStringDay() + "魅力花魁榜中荣获第" + i + "名," + titleMsg, null, null);
			}
			i++;
		}
	}

	// 发放你画我猜风云榜的奖励
	public void sendDrawSth() {
		// 拿到设置的结束时间
		String string = DboUtil.getString(envService.findById("drawSth"), "value");
		if (StringUtils.isNotBlank(string)) {
			Date date = null;
			try {
				date = DateUtil.convertDate(string, "yyyy-MM-dd");
			} catch (ParseException e) {
				log.error("RankListJob---sendDrawSth  err!!!");
				return;
			}
			// 如果当前系统时间大于结束时间
			if (System.currentTimeMillis() > DateUtil.getZeroTime(date)) {
				return;
			}
		}
		long endDate = DateUtil.getTodayZeroTime();
		long startDate = endDate - DateUtil.DAY;
		List<HashMap<String, Object>> list = coinService.stCoinLog(CoinLog.IN, TYPE, startDate, endDate, SIZE);
		int i = 1;
		for (HashMap<String, Object> m : list) {
			long uid = Long.parseLong((String) m.get("uid"));
			coinService.addCoin(uid, CoinLog.RANKLIST, getIntDay(), 2000 - ((i - 1) * 200), 0,
					getIntDay() + " 画猜榜第" + i + "名 " + (2000 - ((i - 1) * 200)) + "金币");

			messageService.imMsgHandler(Const.SYSTEM_ID, uid, "恭喜您在 " + getStringDay() + "你画我猜风云榜中荣获第" + i + "名,获得"
					+ (2000 - ((i - 1) * 200)) + "金币奖励，系统已将奖励发放至您的账户当中，请注意查收。", null, null);
			i++;
			if (i > SIZE) {
				break;
			}
		}
	}

	// // 发放昨日土豪榜的奖励
	// public void senRewardRichToday(int day) {
	// List<DBObject> list = rankListService.find(day,
	// BaseGoods.Gift.FLOWER.getV().getId(), 1, 5, DocName.GIFT_SEND);
	// int i = 1;
	// for (DBObject dbo : list) {
	// coinService.addCoin(DboUtil.getLong(dbo, "uid"), CoinLog.RANKLIST,
	// getIntDay(), 2000 - ((i - 1) * 200), 0,
	// getIntDay() + "富豪榜第" + i + "名 " + (2000 - ((i - 1) * 200)) + "金币");
	//
	// messageService.imMsgHandler(Const.SYSTEM_ID, DboUtil.getLong(dbo, "uid"),
	// getStringDay() + "富豪榜第" + i + "名奖励" + (2000 - ((i - 1) * 200)) +
	// "金币奖励，系统已将奖励发放至您的账户当中，请注意查收。",
	// null, null);
	// i++;
	// if (i >= SIZE) {
	// break;
	// }
	// }
	// }

	private static String getStringDay() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		return y + "年" + m + "月" + d + "日";
	}

	private static int getIntDay() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		return y * 10000 + m * 100 + d;
	}

	// private static String transDate(int start, int end) {
	// String transDate = null;
	// int year = start / 10000;
	// int month = (start % 10000) / 100;
	// int day = (start % 10000) % 100;
	// transDate = year + "年" + month + "月" + day + "日";
	// year = end / 10000;
	// month = (end % 10000) / 100;
	// day = (end % 10000) % 100;
	// return transDate + "至" + year + "年" + month + "月" + day + "日";
	// }

}
