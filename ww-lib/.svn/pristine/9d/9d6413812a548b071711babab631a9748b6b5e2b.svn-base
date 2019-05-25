package com.zb.service.othergames;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.IMType;
import com.zb.common.Constant.OperationType;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.VipMap;
import com.zb.models.finance.CoinLog;
import com.zb.models.lottery.WelfareLottery;
import com.zb.models.othergames.Guess;
import com.zb.models.othergames.GuessItem;
import com.zb.models.othergames.GuessLog;
import com.zb.service.BaseService;
import com.zb.service.MessageService;
import com.zb.service.UserService;
import com.zb.service.pay.CoinService;

@Service
public class GuessService extends BaseService {
	static final Logger logger = LoggerFactory.getLogger(GuessService.class);

	@Autowired
	CoinService coinService;

	@Autowired
	UserService userService;
	@Autowired
	MessageService messageService;

	public static final int INGUESS_VIP = 0;// 准入VIP等级
	public static final int SPONSORGUESS_VIP = 0;// 发起猜猜VIP等级
	public static final int SPONSORGUESS_POINT = 15;// 发起猜猜经验等级
	public static final int SPONSORGUESS_COUNT = 30;// 每日发起上限
	public static final int GUESS_MIN_COIN = 1000;// 单注最小金币
	public static final int GUESS_MAX_COIN = 20000;// 单项最多下注金币
	public static final int GUESS_DEPOSIT = 50000;// 保证金
	public static final int GUESS_ITEM_MINCOUNT = 2;// 最低选项数
	public static final int GUESS_ITEM_MAXCOUNT = 20;// 最多选项数
	public static final String LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final double USER_FEE = 0.04;
	private static final double SYSTEM_FEE = 0.04;
	public static final long TIME_LAG = Const.MINUTE * 10;// 快三开奖时间差
	// 上海快三第0期开奖 08:48 第一期08:58
	public static final long SHKS_STARTTIME = Const.HOUR * 8 + Const.MINUTE * 48;
	public static final String[] SHKS_ITEMS = new String[] { "3.4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
			"14", "15", "16", "17.18" };

	/** 根据guessId猜猜 */
	public DBObject findgGuessById(Long guessId) {
		return getC(DocName.GUESS).findOne(new BasicDBObject("_id", guessId));
	}

	public Guess getGuess(Long guessId) {
		return DboUtil.toBean(findgGuessById(guessId), Guess.class);
	}

	/** 猜猜 后台查询使用 */
	public Page<DBObject> queryGuess(Long guessId, Long uid, Integer status, Integer page, Integer size) {
		BasicDBObject q = new BasicDBObject();
		if (guessId != null) {
			q.put("_id", guessId);
		}
		if (uid != null) {
			q.put("uid", uid);
		}
		if (status != null && status != 0) {
			q.put("status", status);
		}
		size = getSize(size);
		page = getPage(page);
		DBCursor dbc = getC(DocName.GUESS).find(q);
		int count = dbc.count();
		List<DBObject> dbos = dbc.sort(new BasicDBObject("status", 1).append("_id", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
		return new Page<DBObject>(count, size, page, dbos);
	}

	/** 猜猜日志 后台查询使用 */
	public Page<DBObject> queryGuessLog(Long guessId, Long uid, Integer status, Integer page, Integer size) {
		BasicDBObject q = new BasicDBObject();
		if (guessId != null) {
			q.put("guessId", guessId);
		}
		if (uid != null) {
			q.put("uid", uid);
		}
		if (status != null && status != 0) {
			q.put("status", status);
		}
		size = getSize(size);
		page = getPage(page);
		DBCursor dbc = getC(DocName.GUESSLOG).find(q);
		int count = dbc.count();
		List<DBObject> dbos = dbc.sort(new BasicDBObject("status", 1).append("guessId", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> findGuessLog(Long guessId) {
		BasicDBObject q = new BasicDBObject("guessId", guessId);
		return getC(DocName.GUESSLOG).find(q).sort(new BasicDBObject("_id", -1)).toArray();
	}

	public GuessLog getGuessLogByid(Long guessId, Long uid) {
		return DboUtil.toBean(findGuessLogByid(guessId, uid), GuessLog.class);
	}

	public DBObject findGuessLogByid(Long guessId, Long uid) {
		BasicDBObject q = new BasicDBObject("_id", guessId + "-" + uid);
		return getC(DocName.GUESSLOG).findOne(q);
	}

	/** 我参与的猜猜 前台使用 */
	public ReMsg getGuessLogList(Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		page = super.getPage(page);
		size = super.getSize(size);
		List<DBObject> list = new ArrayList<DBObject>();
		BasicDBObject q = new BasicDBObject("uid", uid).append("status",
				new BasicDBObject("$lt", Guess.STATUS_DISMISS));
		DBCursor dbc = getC(DocName.GUESSLOG).find(q);
		int count = dbc.count();
		list = dbc.sort(new BasicDBObject("status", 1).append("guessId", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
		Long ids[] = new Long[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ids[i] = DboUtil.getLong(list.get(i), "guessId");
		}
		BasicDBObject qg = new BasicDBObject("_id", new BasicDBObject("$in", ids)).append("status",
				new BasicDBObject("$lt", Guess.STATUS_DISMISS));
		List<DBObject> result = getC(DocName.GUESS).find(qg)
				.sort(new BasicDBObject("status", 1).append("endDrawTime", 1)).toArray();

		for (DBObject dbo : result) {
			Long guessUid = DboUtil.getLong(dbo, "uid");
			DBObject user = userService.findById(guessUid);
			dbo.put("guessNickname", DboUtil.getString(user, "nickname"));
			dbo.put("guessAvatar", userService.toMaxAvatar(user));
		}
		return new ReMsg(ReCode.OK, size, count, result);
	}

	/** 我发起的猜猜 */
	public ReMsg myGuessList(Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return getGuessList(uid, page, size);
	}

	/** 猜猜列表 */
	public ReMsg getGuessList(Long uid, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		BasicDBObject q = new BasicDBObject("status", Guess.STATUS_GUESSING);// 进行中的
		if (uid != null && uid > 0) {
			q.put("uid", uid);
		}
		int start = getStart(page, size);

		// 查询未开奖的
		DBCursor dbcing = getC(DocName.GUESS).find(q);
		int cntIng = dbcing.count();

		BasicDBObject endq = new BasicDBObject("status", Guess.STATUS_END);// 结束的
		if (uid != null && uid > 0) {
			endq.put("uid", uid);
		}

		DBCursor dbcEd = getC(DocName.GUESS).find(endq);
		int cntEd = dbcing.count();

		List<DBObject> list = null;
		int curSize = getSize(size);
		if (cntIng == 0) {
			list = dbcEd.sort(new BasicDBObject("endDrawTime", -1)).skip(start - cntIng).limit(curSize).toArray();
		} else {
			int b = 0;
			if (cntIng > start) {
				list = dbcing.sort(new BasicDBObject("endDrawTime", 1)).skip(start).limit(curSize).toArray();
				if (list.size() < size) {
					b = size - list.size();
					List<DBObject> lists = dbcEd.sort(new BasicDBObject("endDrawTime", -1)).skip(0).limit(b).toArray();
					for (DBObject dbo : lists) {
						list.add(dbo);
					}
				}
			} else {
				list = dbcEd.sort(new BasicDBObject("endDrawTime", -1)).skip(start - cntIng - b).limit(curSize)
						.toArray();
			}
		}

		if (null == uid) {// 查询的不是自己的猜猜
			for (DBObject dbo : list) {
				Long guessUid = DboUtil.getLong(dbo, "uid");
				DBObject user = userService.findById(guessUid);
				dbo.put("guessNickname", DboUtil.getString(user, "nickname"));
				dbo.put("guessAvatar", userService.toMaxAvatar(user));
			}
		}
		return new ReMsg(ReCode.OK, size, cntIng + cntEd, list);
	}

	/** 用户本天发起的猜猜数量 */
	public int todaySponsorGuessCnt(long uid) {
		long time = DateUtil.getTodayZeroTime();
		// 发起时间大于今天零点时间
		BasicDBObject q = new BasicDBObject("uid", uid).append("createTime", new BasicDBObject("$gte", time));
		return getC(DocName.GUESS).find(q).count();
	}

	/** 后台发起猜猜 */
	public ReMsg adminSponsorGuess(Integer type, String title, Integer issue, String endTime, Long uid, String items,
			long adminId) {
		long endDrawTime = 0;
		Integer drawType = null;
		List<String> list = null;
		if (type == 2) {// 上海快三
			endDrawTime = DateUtil.getTodayZeroTime() + issue * TIME_LAG + SHKS_STARTTIME;
			list = Arrays.asList(SHKS_ITEMS);
			drawType = Guess.DRAWTYPE_AUTO;
			if (issue < 1 || issue > 82) {// 上海快三限制
				return new ReMsg(ReCode.PARAMETER_ERR);
			}
		} else if (type == 1) {// 北京快三
			// TODO
			drawType = Guess.DRAWTYPE_AUTO;
			return new ReMsg(11111, "北京快三暂未开放");
		} else {// 普通猜猜
			String[] split = endTime.split("-");
			endDrawTime = DateUtil.convertDate(Integer.valueOf(split[0]), Integer.valueOf(split[1]) - 1,
					Integer.valueOf(split[2]), Integer.valueOf(split[3]), Integer.valueOf(split[4]), 0);
			String[] data = items.split(";");
			list = Arrays.asList(data);
		}
		return sponsorGuess(uid, endDrawTime, title, list, type, drawType, String.valueOf(issue));
	}

	/** 用户发起猜猜 */
	public ReMsg sponsorGuess(long uid, String endTime, String title, List<String> data) {
		if (todaySponsorGuessCnt(uid) >= SPONSORGUESS_COUNT) {// 发起猜猜数量上限
			return new ReMsg(ReCode.GUESS_MAX3);
		}
		// 2017-11-15-17-09- 年-月-日-时-分
		String[] split = endTime.split("-");
		long endDrawTime = DateUtil.convertDate(Integer.valueOf(split[0]), Integer.valueOf(split[1]) - 1,
				Integer.valueOf(split[2]), Integer.valueOf(split[3]), Integer.valueOf(split[4]), 0);
		return sponsorGuess(uid, endDrawTime, title, data, null, null, null);
	}

	/** 发起猜猜 */
	private ReMsg sponsorGuess(long uid, long endDrawTime, String title, List<String> data, Integer type,
			Integer drawType, String typeLink) {
		long time = endDrawTime - System.currentTimeMillis();
		if (time < Const.MINUTE * 10 || time > Const.HOUR * 48) {// 下注时间不符合标准
			return new ReMsg(ReCode.GUESS_GUESSTIME_ERR);
		}
		if (null == data || data.size() < GUESS_ITEM_MINCOUNT || data.size() > GUESS_ITEM_MAXCOUNT) {// 猜猜选项有误
			return new ReMsg(ReCode.GUESS_ITEMS_ERR);
		}
		DBObject user = userService.findById(uid);
		if (user != null) {
			if (VipMap.getLevel(DboUtil.getInt(user, "vip")) < SPONSORGUESS_VIP) {// vip等级不足
				return new ReMsg(ReCode.GUESS_VIP3_ERR);
			}
			if (userService.getLevel(DboUtil.getInt(user, "point")) < SPONSORGUESS_POINT) {
				return new ReMsg(ReCode.GUESS_POINT30_ERR);
			}
		}
		// 扣除押金
		ReCode rc = coinService.reduce(uid, CoinLog.GUESS, 0, GUESS_DEPOSIT, 0, "发起猜猜押金");
		if (rc.reCode() != ReCode.OK.reCode()) {
			return new ReMsg(rc);
		}
		if (StringUtils.isNotBlank(title)) {
			// 获取items
			List<GuessItem> items = new ArrayList<GuessItem>();
			for (int i = 0; i < data.size(); i++) {
				if (StringUtils.isNotBlank(data.get(i))) {
					items.add(new GuessItem(String.valueOf(LETTER.charAt(i)), data.get(i)));
				}
			}
			Guess guess = new Guess(uid, endDrawTime, title, items);
			if (null != type) {
				guess.setType(type);
			}
			if (null != drawType) {
				guess.setDrawType(drawType);
			}
			if (StringUtils.isNotBlank(typeLink)) {
				guess.setTypeLink(typeLink);
			}
			guess.set_id(super.getNextId(DocName.GUESS));
			super.getMongoTemplate().save(guess);
			String body = "您已成功发起猜猜【" + guess.getTitle() + "】，系统收取您" + GUESS_DEPOSIT + "金币作为保证金，游戏正常开奖后保证金将如数退还   ";
			messageService.imMsgHandler(Const.SYSTEM_ID, uid, body, getMsgExt(guess.get_id()), null);
			return new ReMsg(ReCode.OK, guess);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 下注 猜猜id 猜猜项id 下注金额 */
	public ReMsg buttonPour(long uid, long guessId, String guessItemId, int count) {
		if (count > GUESS_MAX_COIN) {
			return new ReMsg(ReCode.GUESS_ITEM_MAX_ERR);
		}
		if (count < GUESS_MIN_COIN) {
			return new ReMsg(ReCode.GUESS_ITEM_MIN_ERR);
		}
		DBObject guess = findgGuessById(guessId);
		if (null == guess) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		// 大于押注结束时间 或者不是下注中 返回下注失败
		if (System.currentTimeMillis() > DboUtil.getLong(guess, "endDrawTime")
				|| Guess.STATUS_GUESSING != DboUtil.getInt(guess, "status")) {
			return new ReMsg(ReCode.FAIL);
		}
		List<DBObject> items = DboUtil.get(guess, "items", ArrayList.class);
		DBObject gi = null;
		for (DBObject obj : items) {
			if (DboUtil.getString(obj, "_id").equals(guessItemId)) {
				gi = obj;
				break;
			}
		}
		if (gi == null) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		Boolean first = true;
		GuessLog guessLog = getGuessLogByid(guessId, uid);
		if (guessLog == null) {
			guessLog = new GuessLog(guessId, uid, guessItemId, count);
		} else {
			Integer ia = guessLog.getItems().get(guessItemId);
			if (ia != null) {
				if (ia + count > GUESS_MAX_COIN) {
					return new ReMsg(ReCode.GUESS_ITEM_MAX_ERR);
				}
				first = false;
				guessLog.getItems().put(guessItemId, ia + count);
			} else {
				guessLog.getItems().put(guessItemId, count);
			}
		}
		guessLog.setUpdateTime(System.currentTimeMillis());
		// 扣币
		ReCode rc = coinService.reduce(uid, CoinLog.GUESS, guessId, count, 0, "猜猜下注:" + guessItemId);
		if (rc.reCode() == ReCode.OK.reCode()) {
			super.getMongoTemplate().save(guessLog);
			int allAmount = DboUtil.getInt(guess, "amount") + count;// 最新总下注金额
			guess.put("amount", allAmount);

			for (DBObject item : items) {
				if (DboUtil.getString(item, "_id").equals(guessItemId)) {// 当前下注选项
					int amount = DboUtil.getInt(item, "amount") + count;
					double rate = allAmount * (1 - USER_FEE - SYSTEM_FEE) / amount;
					item.put("rate", formatRate(rate));
					item.put("amount", amount);
					if (first) {// 第一次下注该选项
						item.put("count", DboUtil.getInt(item, "count") + 1);
					}
				} else {
					if (0 != DboUtil.getInt(item, "amount")) {// 未下注
						double rate = allAmount * (1 - USER_FEE - SYSTEM_FEE) / DboUtil.getInt(item, "amount");
						item.put("rate", formatRate(rate));
					}
				}
			}

			// 存储guess
			guess.put("items", items);
			guess.put("updateTime", System.currentTimeMillis());
			super.getC(DocName.GUESS).save(guess);
			// 存储guessLog
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(rc);
	}

	/** 用户主动开奖 */
	public ReMsg drawGuess(long guessId, String guessItemId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject guess = findgGuessById(guessId);
		// 我发起的下注中的猜猜 下注时间已经过了
		return drawGuess(guessItemId, uid, guess);
	}

	/** 猜猜开奖 */
	private ReMsg drawGuess(String guessItemId, long uid, DBObject guess) {
		if (null != guess && DboUtil.getLong(guess, "uid") == uid
				&& System.currentTimeMillis() > DboUtil.getLong(guess, "endDrawTime")) {
			if (DboUtil.getInt(guess, "status") == Guess.STATUS_GUESSING) {
				if (super.lock("dissguess:" + DboUtil.getLong(guess, "_id"), 3)) {
					List<DBObject> items = DboUtil.get(guess, "items", ArrayList.class);
					for (DBObject item : items) {
						if (DboUtil.getString(item, "_id").equals(guessItemId)) {
							guess.put("drawId", guessItemId);
							guess.put("status", Guess.STATUS_DRAWING);
							guess.put("drawTime", System.currentTimeMillis());
							super.getC(DocName.GUESS).save(guess);
						}
					}
					settlement(guess);
					return new ReMsg(ReCode.OK);
				}
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 后台不退保证金 */
	public ReMsg adminBackBondGuess(long guessId) {
		int role = T2TUtil.obj2Int(super.getUser("role"), 1);
		if (role != 2) {
			return new ReMsg(ReCode.ROLE_NOT_OP);
		}
		DBObject guess = findgGuessById(guessId);
		if (null == guess) {
			return new ReMsg(ReCode.FAIL);
		}
		guess.put("drawStatus", 2);
		guess.put("adminId", super.getUid());
		return new ReMsg(backBond(guess));
	}

	/** 后台介入开奖 */
	public ReMsg adminDrawGuess(long guessId, String guessItemId) {
		int role = T2TUtil.obj2Int(super.getUser("role"), 1);
		if (role != 2) {
			return new ReMsg(ReCode.ROLE_NOT_OP);
		}
		DBObject guess = findgGuessById(guessId);
		if (null == guess) {
			return new ReMsg(ReCode.FAIL);
		}
		guess.put("adminId", super.getUid());
		// 后台开奖和用户开奖不一致 证明举报有效
		if (StringUtils.isBlank(DboUtil.getString(guess, "drawId"))) {
			guess.put("drawTime", System.currentTimeMillis());
			guess.put("drawId", guessItemId);
			ReMsg r = drawGuess(guessItemId, DboUtil.getLong(guess, "uid"), guess);
			if (r.getCode() != ReCode.OK.reCode()) {
				return r;
			}
		} else if (!DboUtil.getString(guess, "drawId").equals(guessItemId)) {
			guess.put("drawId", guessItemId);
			guess.put("drawStatus", 2);
		}
		// 退回保证金
		if (DboUtil.getInt(guess, "status") == Guess.STATUS_END
				&& DboUtil.getInt(guess, "bondStatus") == Const.STATUS_DEF) {
			return new ReMsg(backBond(guess));
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** job 自动退回保证金 */
	public void aotuSettlement() {
		if (System.currentTimeMillis() < DateUtil.getTodayZeroTime() + Const.HOUR * 9) {// 九点之前不发奖
			return;
		}
		DBObject q = new BasicDBObject("status", Guess.STATUS_END).append("bondStatus", Const.STATUS_DEF)
				.append("reportCnt", 0)
				.append("drawTime", new BasicDBObject("$lt", System.currentTimeMillis() - 10 * Const.MINUTE));
		List<DBObject> dbos = super.getC(DocName.GUESS).find(q).toArray();
		for (DBObject dbo : dbos) {
			backBond(dbo);
		}
	}

	/** 快三类 猜猜开奖 */
	public void aotuDrawGuess(WelfareLottery wfl) {
		DBObject q = new BasicDBObject("status", Guess.STATUS_GUESSING).append("drawType", Guess.DRAWTYPE_AUTO)
				.append("type", wfl.getCity()).append("typeLink", String.valueOf(wfl.getIssue()))
				.append("endDrawTime", new BasicDBObject("$gt", DateUtil.getTodayZeroTime()));
		DBObject guess = super.getC(DocName.GUESS).findOne(q);
		if (guess != null) {
			logger.error("猜猜自动开奖-" + "city:" + wfl.getCity() + "-Issue:" + wfl.getIssue() + "-sum:" + wfl.getSum());
			List<DBObject> items = DboUtil.get(guess, "items", ArrayList.class);
			String answer = String.valueOf(wfl.getSum());
			for (DBObject item : items) {
				if (DboUtil.getString(item, "name").contains(answer)) {// 中奖项
					guess.put("drawId", DboUtil.getString(item, "_id"));
					guess.put("drawTime", System.currentTimeMillis());
					super.getC(DocName.GUESS).save(guess);
					// 开奖
					settlement(guess);
					break;
				}
			}
		}
	}

	/** 猜猜退回押金 */
	private ReCode backBond(DBObject guess) {
		Long guessId = DboUtil.getLong(guess, "_id");
		Long guessUid = DboUtil.getLong(guess, "uid");
		String guessItemId = DboUtil.getString(guess, "drawId");
		String body = null;
		if (DboUtil.getInt(guess, "drawStatus") == 2) {
			body = "您发起的猜猜【" + DboUtil.getString(guess, "title") + "】最终开奖【" + DboUtil.getString(guess, "drawId")
					+ "】，用户举报并被系统判断举报生效，此次猜猜您将不会获得押金返还";
		} else if (DboUtil.getInt(guess, "drawStatus") == 1) {
			int allAmount = DboUtil.getInt(guess, "amount");
			List<DBObject> items = DboUtil.get(guess, "items", ArrayList.class);
			double rate = 0;
			for (DBObject item : items) {
				if (DboUtil.getString(item, "_id").equals(guessItemId)) {// 中奖项
					rate = DboUtil.getDouble(item, "rate");
					break;
				}
			}
			if (rate != 0) {// 表示有人中奖
				int userFee = (int) (allAmount * USER_FEE);
				coinService.addCoin(guessUid, CoinLog.GUESS, guessId, GUESS_DEPOSIT + userFee, 0, "发起者保证金加抽成0.04");
				body = "您发起的猜猜【" + DboUtil.getString(guess, "title") + "】已成功开奖【" + guessItemId + "】，您获得了" + userFee
						+ "金币的抽成，并与保证金一同发放至账户，请注意查收。   ";
			} else {// 无人中奖
				coinService.addCoin(guessUid, CoinLog.GUESS, guessId, GUESS_DEPOSIT, 0, "无人中奖退回保证金");
				body = "您发起的猜猜【" + DboUtil.getString(guess, "title") + "】已成功开奖【" + guessItemId + "】，但无人押中，您的"
						+ GUESS_DEPOSIT + "保证金稍后将返还至账户，请注意查收。   ";
			}
		}
		if (null != body) {
			messageService.imMsgHandler(Const.SYSTEM_ID, guessUid, body, getMsgExt(guessId), null);
		}
		// 存储猜猜
		guess.put("bondStatus", Const.STATUS_OK);
		guess.put("updateTime", System.currentTimeMillis());
		super.getC(DocName.GUESS).save(guess);
		return ReCode.OK;
	}

	/** 猜猜开奖 结算 */
	private ReCode settlement(DBObject guess) {
		Long guessId = DboUtil.getLong(guess, "_id");
		// Long guessUid = DboUtil.getLong(guess, "uid");
		String guessItemId = DboUtil.getString(guess, "drawId");
		// int allAmount = DboUtil.getInt(guess, "amount");
		List<DBObject> items = DboUtil.get(guess, "items", ArrayList.class);
		double rate = 0;
		for (DBObject item : items) {
			if (DboUtil.getString(item, "_id").equals(guessItemId)) {// 中奖项
				rate = DboUtil.getDouble(item, "rate");
				break;
			}
		}
		List<DBObject> guessLogs = findGuessLog(guessId);
		if (rate != 0) {// 表示有人中奖
			for (DBObject dbo : guessLogs) {
				GuessLog guessLog = DboUtil.toBean(dbo, GuessLog.class);
				if (guessLog.getItems().containsKey(guessItemId) && null == guessLog.getDrawAmount()) {
					int coin = (int) (guessLog.getItems().get(guessItemId) * rate);
					guessLog.setDrawAmount(coin);
					guessLog.setUpdateTime(System.currentTimeMillis());
					guessLog.setStatus(Guess.STATUS_END);
					super.getMongoTemplate().save(guessLog);
					coinService.addCoin(guessLog.getUid(), CoinLog.GUESS, guessId, coin, 0, "猜猜中奖");
					String body = "恭喜您在猜猜【" + DboUtil.getString(guess, "title") + "】中投注【" + guessItemId + "】"
							+ guessLog.getItems().get(guessItemId) + "金币，成功获得" + coin + "金币，已发放至您的账户，请注意查收。   ";
					messageService.imMsgHandler(Const.SYSTEM_ID, guessLog.getUid(), body, getMsgExt(guessId), null);
				} else {
					String body = "sorry！您在猜猜【" + DboUtil.getString(guess, "title") + "】中未投注【" + guessItemId
							+ "】，不要气馁，期待您下一次的好运！  ";
					messageService.imMsgHandler(Const.SYSTEM_ID, guessLog.getUid(), body, getMsgExt(guessId), null);
					guessLog.setDrawAmount(0);
					guessLog.setStatus(Guess.STATUS_END);
					super.getMongoTemplate().save(guessLog);
				}
			}
		} else {// 无人中奖
			for (DBObject dbo : guessLogs) {
				GuessLog guessLog = DboUtil.toBean(dbo, GuessLog.class);
				int amount = 0;
				for (Integer itm : guessLog.getItems().values()) {
					amount += itm;
				}
				if (amount > 0) {
					guessLog.setDrawAmount(amount);
					guessLog.setUpdateTime(System.currentTimeMillis());
					coinService.addCoin(guessLog.getUid(), CoinLog.GUESS, guessId, amount, 0, "无人中奖退款");
					String body = "您参与的猜猜【" + DboUtil.getString(guess, "title") + "】已开奖【" + guessItemId + "】，但无人押中，您投注的"
							+ amount + "金币已返还至账户，请注意查收。   ";
					messageService.imMsgHandler(Const.SYSTEM_ID, guessLog.getUid(), body, getMsgExt(guessId), null);
				}
				guessLog.setStatus(Guess.STATUS_END);
				super.getMongoTemplate().save(guessLog);
			}
		}
		// 存储猜猜
		guess.put("status", Guess.STATUS_END);
		guess.put("updateTime", System.currentTimeMillis());
		super.getC(DocName.GUESS).save(guess);
		return ReCode.OK;
	}

	/** 用户解散猜猜 */
	public ReMsg dissMyMissGuess(long guessId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject guess = findgGuessById(guessId);
		if (guess == null) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		if (uid != DboUtil.getLong(guess, "uid")) {
			return new ReMsg(ReCode.GUESS_DISMISS_ERR);
		}
		return dissMissGuess(guess, false);
	}

	/** 后台解散猜猜 */
	public ReMsg adminDissMissGuess(long adminId, long guessId) {
		DBObject guess = findgGuessById(guessId);
		if (guess == null) {
			return new ReMsg(ReCode.FAIL);
		}
		guess.put("adminId", adminId);
		return dissMissGuess(guess, true);
	}

	/** 解散猜猜 金币全部返还 扣除发起者一半押金 */
	public ReMsg dissMissGuess(DBObject guess, boolean admin) {
		Long guessId = DboUtil.getLong(guess, "_id");
		if (super.lock("dissguess:" + guessId, 3)) {
			try {
				Long guessUid = DboUtil.getLong(guess, "uid");
				// 我发起的猜猜 而且未到押注结束时间
				if (null != guess) {
					if ((admin || System.currentTimeMillis() <= DboUtil.getLong(guess, "endDrawTime"))
							&& DboUtil.getInt(guess, "status") == Guess.STATUS_GUESSING) {
						// 退回一半押金
						coinService.addCoin(guessUid, CoinLog.GUESS, DboUtil.getLong(guess, "_id"), GUESS_DEPOSIT / 2,
								0, "猜猜解散返还一半押金");
						String body = "您已解散猜猜【" + DboUtil.getString(guess, "title") + "】，系统扣除您" + GUESS_DEPOSIT / 2
								+ "保证金，剩余保证金将如数退还。   ";
						messageService.imMsgHandler(Const.SYSTEM_ID, guessUid, body, null, null);
						List<DBObject> guessLogs = findGuessLog(DboUtil.getLong(guess, "_id"));
						for (DBObject dbo : guessLogs) {
							GuessLog guessLog = DboUtil.toBean(dbo, GuessLog.class);
							Collection<Integer> values = guessLog.getItems().values();
							int coin = 0;
							for (Integer count : values) {
								coin += count;
							}
							// 退回押注金币
							coinService.addCoin(guessLog.getUid(), CoinLog.GUESS, guessLog.getGuessId(), coin, 0,
									"猜猜解散押注用户退钱");
							body = "您参与的猜猜【" + DboUtil.getString(guess, "title") + "】已被发起人解散，您投注的" + coin
									+ " 金币已返还至账户，请注意查收。  ";
							messageService.imMsgHandler(Const.SYSTEM_ID, guessLog.getUid(), body, null, null);
							// 更新竞猜日志
							guessLog.setDrawAmount(coin);
							guessLog.setUpdateTime(System.currentTimeMillis());
							guessLog.setStatus(Guess.STATUS_DISMISS);
							super.getMongoTemplate().save(guessLog);
						}
						guess.put("status", Guess.STATUS_DISMISS);
						guess.put("updateTime", System.currentTimeMillis());
						super.getC(DocName.GUESS).save(guess);
					}
				}
			} finally {
				super.unlock("dissguess:" + guessId);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	/** 能否进入猜猜 */
	public ReMsg canInGuess(long uid) {
		DBObject user = userService.findById(uid);
		if (user != null) {
			if (VipMap.getLevel(DboUtil.getInt(user, "vip")) >= INGUESS_VIP) {
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.GUESS_VIP1_ERR);
	}

	/** 猜猜详情 */
	public ReMsg guessDetail(long uid, long guessId) {
		DBObject guess = this.findgGuessById(guessId);
		DBObject guessLog = this.findGuessLogByid(guessId, uid);
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("guess", guess);
		res.put("sysTime", System.currentTimeMillis());
		if (null != guessLog) {
			res.put("guessLog", guessLog);
		}
		DBObject user = userService.findById(DboUtil.getLong(guess, "uid"));
		res.put("nickname", DboUtil.getString(user, "nickname"));
		res.put("avatar", userService.toMaxAvatar(user));
		res.put("sex", DboUtil.getInt(user, "sex"));
		res.put("point", DboUtil.getInt(user, "point"));
		res.put("vip", DboUtil.getInt(user, "vip"));
		return new ReMsg(ReCode.OK, res);
	}

	/** 举报猜猜 */
	public ReMsg reportGuess(long uid, long guessId) {
		DBObject guess = this.findgGuessById(guessId);
		if (null != guess) {
			// 任何时候都可以举报
			BasicDBList report = (BasicDBList) guess.get("report");
			if (!report.contains(uid)) {
				report.add(uid);
				guess.put("report", report);
				guess.put("reportCnt", DboUtil.getInt(guess, "reportCnt") + 1);
				guess.put("needAdmin", true);
				getC(DocName.GUESS).save(guess);
				return new ReMsg(ReCode.OK);
			} else {
				return new ReMsg(ReCode.FEEDBACK_ERR);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	// public String drawTimeStr() {
	// // 十分钟后开奖
	// long time = System.currentTimeMillis() + Const.MINUTE * 10;
	// if (time < DateUtil.getTodayZeroTime() + Const.HOUR * 9) {
	// // 十分钟后不到九点 设置为九点开奖
	// time = DateUtil.getTodayZeroTime() + Const.HOUR * 9;
	// }
	// Calendar c = Calendar.getInstance();
	// c.setTimeInMillis(time);
	// return new SimpleDateFormat("HH:mm").format(c.getTime());
	// }

	public double formatRate(double rate) {
		return new BigDecimal(rate).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public ReMsg shareFriends(long uid, Long[] ids, long guessId) {
		DBObject guess = findgGuessById(guessId);
		if (null == guess) {
			return new ReMsg(ReCode.FAIL);
		}
		String body = null;
		for (Long toUid : ids) {
			DBObject dbo = userService.findById(toUid);
			if (null == dbo) {
				continue;
			}
			body = "[" + uid + "] 邀请 [" + toUid + "] 参与猜猜【" + DboUtil.getString(guess, "title") + "】   ";
			messageService.imMsgHandler(uid, toUid, body, getMsgExt(guessId), IMType.GUESS.getType());
		}
		return new ReMsg(ReCode.OK);
	}

	public Map<String, Object> getMsgExt(long guessId) {
		Map<String, Object> ext = new HashMap<String, Object>();
		ext.put("type", IMType.GUESS.getType());
		ext.put("op", OperationType.GUESS.getOp());
		ext.put("opId", guessId);
		return ext;
	}

	public static void main(String[] args) {
		System.out.println(LETTER.charAt(3));
	}
}
