package com.zb.service;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.GiftForm;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.Sign;
import com.zb.models.SignLog;
import com.zb.models.SignMonth;
import com.zb.models.SignWeek;
import com.zb.models.finance.CoinLog;
import com.zb.models.goods.BaseGoods;
import com.zb.service.pay.CoinService;
import com.zb.service.usertask.UserTaskService;
import com.zb.view.SignMonthVO;
import com.zb.view.SignWeekVO;

@Service
public class SignService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(SignService.class);

	@Autowired
	UserService userService;
	@Autowired
	CoinService coinService;
	@Autowired
	UserPackService userPackService;
	@Autowired
	UserTaskService userTaskService;

	public List<DBObject> findLogByUid(Long userId, int page, int size) {
		return getC(DocName.SIGN_LOG).find(new BasicDBObject("userId", userId)).sort(new BasicDBObject("day", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
	}

	public DBObject findLogByUid(Long userId) {
		String id = DateUtil.curDay() + "_" + userId;
		return getC(DocName.SIGN_LOG).findOne(new BasicDBObject("_id", id));
	}

	public DBObject findById(Long userId) {
		return getC(DocName.SIGN).findOne(new BasicDBObject("_id", userId));
	}

	public Sign getSign(Long userId) {
		return DboUtil.toBean(findById(userId), Sign.class);
	}

	public SignMonth getSignMonth(Long uid, int month) {
		String id = month + "-" + uid;
		DBObject dbo = getC(DocName.SIGN_MONTH).findOne(new BasicDBObject("_id", id));
		if (dbo == null) {
			return new SignMonth(uid, month);
		}
		return DboUtil.toBean(dbo, SignMonth.class);
	}

	public ReMsg isSign(HttpServletRequest req) {
		long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		int month = getCurMonth();
		SignMonth sm = getSignMonth(userId, month);
		int day = getToday();
		int cnt = 3;
		if (T2TUtil.obj2Int(super.getUser("vip"), 0) > 99) {
			cnt = 6;
		}
		for (Integer cur : sm.getDays()) {
			if (day == cur) {
				return new ReMsg(ReCode.HAVE_BEEN_SIGNED, new SignMonthVO(sm, 0, cnt));
			}
		}
		return new ReMsg(ReCode.OK, new SignMonthVO(sm, 0, cnt));
	}

	private int getCurMonth() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1;
	}

	private int getToday() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_MONTH);
	}

	int[] draws = { 3, 7, 14, 28 };

	public ReMsg sign(HttpServletRequest req) {
		long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		int month = getCurMonth();
		SignMonth sm = getSignMonth(userId, month);
		int day = getToday();
		int cnt = 3;
		if (T2TUtil.obj2Int(super.getUser("vip"), 0) > 99) {
			cnt = 6;
		}

		for (Integer cur : sm.getDays()) {
			if (day == cur) {
				return new ReMsg(ReCode.HAVE_BEEN_SIGNED, new SignMonthVO(sm, 0, cnt));
			}
		}
		sm.addDay(day);

		int max = sm.getDays().size();
		if (max < day) {
			max = 1;
			for (int i = sm.getDays().size() - 1; i > 0; i--) {
				if (sm.getDays().get(i) - sm.getDays().get(i - 1) == 1) {
					max++;
				} else {
					break;
				}
			}
		}
		for (int i = 0; i < draws.length; i++) {
			if (draws[i] <= max) {
				if (!sm.hasDraw(draws[i])) {
					sm.putDraw(draws[i], false);
				}
			} else {
				break;
			}
		}

		super.getMongoTemplate().save(sm);
		save(userId, DateUtil.curDay(), getIp(req), getDevId(req), getVia(req));

		int coin = 100 + 10 * sm.getDays().size();
		coinService.addCoin(userId, CoinLog.IN_SIGN, day, coin, 0L, "Sign");

		return new ReMsg(ReCode.OK, new SignMonthVO(sm, coin, cnt));
	}

	public ReMsg reSign(HttpServletRequest req) {
		long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		int month = getCurMonth();
		SignMonth sm = getSignMonth(userId, month);
		int day = getToday();

		int rDay = 0;

		int size = sm.getDays().size();

		int cnt = 3;
		if (T2TUtil.obj2Int(super.getUser("vip"), 0) > 99) {
			cnt = 6;
		}
		if (sm.getRetroactive().size() >= cnt) {
			return new ReMsg(ReCode.NO_SIGN_CARD);
		}
		if (day == 1) {
			return new ReMsg(ReCode.NO_RESIGN);
		}
		if (size == day) {
			return new ReMsg(ReCode.NO_RESIGN);
		}

		int lastDay = 0;
		if (size > 0) {
			lastDay = sm.getDays().get(size - 1);
		}
		if (day - lastDay > 1) {// 今日未签到，昨日也没签到，补签昨日
			rDay = day - 1;
		}
		if (rDay == 0 && size > 0) {// 昨日已经签到
			if (day - lastDay < 2 && day != 1) {
				for (int i = size - 1; i > 0; i--) {
					if (sm.getDays().get(i) - sm.getDays().get(i - 1) != 1) {
						rDay = sm.getDays().get(i) - 1;
						break;
					}
				}
				if (rDay == 0 && sm.getDays().get(0) != 1) {
					rDay = sm.getDays().get(0) - 1;
				}
			}
		}

		if (rDay == 0) {
			return new ReMsg(ReCode.NO_RESIGN);
		}
		sm.addRetroactive(rDay);

		int amount = sm.getRetroactive().size() * 100;

		ReCode r = coinService.reduce(userId, CoinLog.OUT_RESIGN, rDay, amount, 0L, "补签抠金币" + amount);
		if (r.reCode() != ReCode.OK.reCode()) {
			return new ReMsg(r);
		}

		int max = 1;
		for (int i = sm.getDays().size() - 1; i > 0; i--) {
			if (sm.getDays().get(i) - sm.getDays().get(i - 1) == 1) {
				max++;
			} else {
				break;
			}
		}
		for (int i = 0; i < draws.length; i++) {
			if (draws[i] <= max) {
				if (!sm.hasDraw(draws[i])) {
					sm.putDraw(draws[i], false);
				}
			} else {
				break;
			}
		}

		super.getMongoTemplate().save(sm);

		// int coin = 100 + 10 * lastDay;
		// coinService.addCoin(userId, CoinLog.IN_SIGN, lastDay, coin, 0L,
		// "reSign");
		//
		// userPackService.addGoodsByBaseId(userId, 1, draw);
		return new ReMsg(ReCode.OK, new SignMonthVO(sm, 0, cnt));
	}

	private static int[] prize = { 100, 200, 300, 400, 600, 800, 1000 };
	private static int[][] rates = { { 550, 750, 850, 910, 950, 980, 1001 }, { 200, 350, 600, 800, 890, 960, 1001 },
			{ 0, 0, 0, 300, 500, 800, 1001 }, { 0, 0, 0, 0, 400, 700, 1001 } };

	public ReMsg draw(int month, int cycle) {
		long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}

		int draw = -1;
		for (int i = 0; i < draws.length; i++) {
			if (cycle == draws[i]) {
				draw = i;
				break;
			}
		}
		if (draw > -1) {
			SignMonth sm = getSignMonth(userId, month);
			if (sm.hasDraw(cycle) && !sm.getDraws().get(cycle)) {
				int r = RandomUtil.nextInt(1000) + 1;
				int idx = 0;
				for (int i = 0; i < rates[draw].length; i++) {
					if (r < rates[draw][i]) {
						idx = i;
						break;
					}
				}
				int coin = prize[idx];
				sm.putDraw(cycle, true);
				super.getMongoTemplate().save(sm);
				coinService.addCoin(userId, CoinLog.IN_SIGN, cycle, coin, 0L, "连续签到抽奖");
				return new ReMsg(ReCode.OK, coin);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	private void save(Long userId, Integer day, String ip, String devId, int via) {
		SignLog sl = new SignLog(userId, day, ip, devId, via);
		super.getMongoTemplate().save(sl);
	}

	/*
	 * 以下为按周签到代码
	 */

	// 每周第三天 第五天 第七天签到 可获得宝箱奖励
	int[] drawsWeek = { 3, 5, 7 };

	// 有没有可以签到或者补签 小红点
	public ReMsg getSignRedPoint(HttpServletRequest req) {
		long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		ReMsg msg = isSignWeek(userId);
		SignWeekVO sw = (SignWeekVO) msg.getData();
		if (sw.getTodaySign() == 1) {
			return new ReMsg(ReCode.OK, 1);
		}
		return new ReMsg(ReCode.OK, 0);
	}

	public ReMsg isSignWeek(HttpServletRequest req) {
		long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return isSignWeek(userId);
	}

	// 查询本周 签到情况的方法
	private ReMsg isSignWeek(long userId) {
		int week = getCurWeek();
		SignWeek sw = getSignWeek(userId, week);
		int day = getTodayOfWeek();
		int cnt = 1;
		if (T2TUtil.obj2Int(super.getUser("vip"), 0) > 599) {
			cnt = 2;
		}
		// 如果第day天已经签到
		if (checkSign(sw, day)) {
			return new ReMsg(ReCode.HAVE_BEEN_SIGNED, new SignWeekVO(day, sw, getRewardWeek(0), cnt));
		}
		return new ReMsg(ReCode.OK, new SignWeekVO(day, sw, getRewardWeek(sw.getDays().size() + 1), cnt));
	}

	// 签到的方法 给奖励
	public ReMsg signWeek(HttpServletRequest req) {
		long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		int week = getCurWeek();
		SignWeek sw = getSignWeek(userId, week);

		int day = getTodayOfWeek();
		int cnt = 1;
		if (T2TUtil.obj2Int(super.getUser("vip"), 0) > 599) {
			cnt = 2;
		}
		// 如果第day天已经签到
		if (checkSign(sw, day)) {
			return new ReMsg(ReCode.HAVE_BEEN_SIGNED, new SignWeekVO(day, sw, getRewardWeek(0), cnt));
		}
		sw.addDay(day);
		super.getMongoTemplate().save(sw);
		save(userId, DateUtil.curDay(), getIp(req), getDevId(req), getVia(req));
		return signDrawWeek(userId, sw.getDays().size(),sw);
	}

	// 补签的方法 先判断补签次数
	public ReMsg reSignWeek(HttpServletRequest req) {
		long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		int week = getCurWeek();
		SignWeek sw = getSignWeek(userId, week);

		int day = getTodayOfWeek();
		int cnt = 1;
		if (T2TUtil.obj2Int(super.getUser("vip"), 0) > 599) {
			cnt = 2;
		}
		// 如果该天还没有签到
		if (!checkSign(sw, day)) {
			return new ReMsg(ReCode.FAIL, new SignWeekVO(day, sw, getRewardWeek(sw.getDays().size() + 1), cnt));
		}

		// 如果该天已经签到
		if (checkSign(sw, day)) {
			// 如果 未签的天数 小于0
			if (day - sw.getDays().size() <= 0) {
				return new ReMsg(ReCode.NO_RESIGN, new SignWeekVO(day, sw, getRewardWeek(0), cnt));
			}
		}

		if (sw.getRetroactive().size() >= cnt) {
			// 如果已补签次数不小于补签卡个数 返回补签卡不足
			return new ReMsg(ReCode.NO_SIGN_CARD);
		}

		int amount = getNeedMoney(sw.getRetroactive().size());
		// 补签扣除金币
		ReCode r = coinService.reduce(userId, CoinLog.OUT_RESIGN, sw.getDays().size() + 1, amount, 0L,
				"补签扣金币" + amount);
		if (r.reCode() != ReCode.OK.reCode()) {
			return new ReMsg(r);
		}

		// 签到记录 今天是第几天 签到的 补签记录同时会进入正常签到记录
		sw.addRetroactive(day);
		super.getMongoTemplate().save(sw);
		save(userId, DateUtil.curDay(), getIp(req), getDevId(req), getVia(req));
		return signDrawWeek(userId, sw.getDays().size(),sw);
	}

	// 抽取签到奖励的方法
	public ReMsg signDrawWeek(long userId, int day,SignWeek sw) {
		int cnt = 1;
		// 判断VIP等级
		if (T2TUtil.obj2Int(super.getUser("vip"), 0) > 599) {
			// 设置为两次
			cnt = 2;
		}
		if (!check(drawsWeek, day)) {
			sendRewardWeek(userId, day);
			return new ReMsg(ReCode.OK, new SignWeekVO(getTodayOfWeek(), sw, getRewardWeek(day), cnt));
		}
		int coin = getDrawCoin(day);
		int point = getDrawPoint(day);
		String prop = getDrawProp(day);
		String reward = "金币×" + coin + "、经验× " + point + "、" + prop;

		userService.changePoint(userId, Point.SIGN.getType(), point, 0);
		coinService.addCoin(userId, CoinLog.IN_SIGN, DateUtil.curDay(), coin, 0, getRewardWeek(day));
		String[] ps = prop.split("×");
		if (ps[0].equals("鲜花")) {
			userPackService.addGoods(userId, BaseGoods.Gift.FLOWER.getV(), Integer.parseInt(ps[1]),GiftForm.SIGN,day);
		}
		if (ps[0].equals("黄金喇叭")) {
			userPackService.addGoods(userId, BaseGoods.Prop.GOLDEN_HORN.getV(), Integer.parseInt(ps[1]),GiftForm.SIGN,day);
		}
		return new ReMsg(ReCode.OK, new SignWeekVO(getTodayOfWeek(), sw, reward, cnt));
	}

	// 获取本周签到对象
	public SignWeek getSignWeek(Long uid, int week) {
		String id = week + "-" + uid;
		DBObject dbo = getC(DocName.SIGN_WEEK).findOne(new BasicDBObject("_id", id));
		if (dbo == null) {
			return new SignWeek(uid, week);
		}
		return DboUtil.toBean(dbo, SignWeek.class);
	}

	private int getTodayOfWeek() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_WEEK);
		if (day == 1) {
			return 7;
		} else {
			return day - 1;
		}
	}

	private int getCurWeek() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(DateUtil.getWeekZeroTime());
		int week = c.get(Calendar.YEAR) * 100 + c.get(Calendar.WEEK_OF_YEAR);
		if (week > 201827) {
			return week;
		}
		return week % 100;
	}

	// 获取奖励
	private String getRewardWeek(int day) {
		if (day == 1) {
			return "鲜花" + "×" + "5";
		}
		if (day == 2) {
			return "金币" + "×" + "200";
		}
		if (day == 3) {
			return "铜宝箱" + "×" + "1";
		}
		if (day == 4) {
			return "经验" + "×" + "1000";
		}
		if (day == 5) {
			return "银宝箱" + "×" + "1";
		}
		if (day == 6) {
			return "鲜花" + "×" + "10";
		}
		if (day == 7) {
			return "金宝箱" + "×" + "1";
		}
		return "无可领取";
	}

	// 获取补签所需金币
	private int getNeedMoney(int num) {
		if (num == 0) {
			return 100;
		}
		if (num == 1) {
			return 300;
		}
		return 0;
	}

	private Boolean checkSign(SignWeek sw, int day) {
		if (sw.getDays().contains(day))
			return true;
		return false;
	}

	// 发送签到奖励
	private void sendRewardWeek(long uid, int day) {
		// 第一天签到 五朵红玫瑰
		if (day == 1) {
			userPackService.addGoods(uid, BaseGoods.Gift.FLOWER.getV(), 5,GiftForm.SIGN,day);
		}
		if (day == 2) {
			coinService.addCoin(uid, CoinLog.IN_SIGN,  DateUtil.curDay(), 200, 0, "本周第二次签到奖励200金币");
		}
		if (day == 4) {
			userService.changePoint(uid, Point.TASK.getType(), 1000, 0);
		}
		if (day == 6) {
			userPackService.addGoods(uid, BaseGoods.Gift.FLOWER.getV(), 10,GiftForm.SIGN,day);
		
		}
	}

	// 金币箱子
	private static int[] coin_prize = { 1, 2, 3 };
	private static int[] coin_rates = { 500, 850, 1000 };

	// 经验箱子
	private static int[] point_prize = { 1, 2, 3 };
	private static int[] point_rates = { 500, 850, 1000 };

	// 道具箱子
	private static int[] prop_prize = { 1, 2, 3, 4 };
	private static int[] prop_rates = { 400, 650, 850, 1000 };

	private int getDrawCoin(int day) {
		int r = RandomUtil.nextInt(1000) + 1;
		int idx = 0;
		for (int i = 0; i < coin_rates.length; i++) {
			if (r < coin_rates[i]) {
				idx = i;
				break;
			}
		}
		int i = coin_prize[idx];
		if (day == 3) {
			if (i == 1) {
				return 200;
			}
			if (i == 2) {
				return 300;
			}
			if (i == 3) {
				return 500;
			}
		}
		if (day == 5) {
			if (i == 1) {
				return 300;
			}
			if (i == 2) {
				return 500;
			}
			if (i == 3) {
				return 800;
			}
		}
		if (day == 7) {
			if (i == 1) {
				return 500;
			}
			if (i == 2) {
				return 800;
			}
			if (i == 3) {
				return 1000;
			}
		}
		return 0;
	}

	private int getDrawPoint(int day) {
		int r = RandomUtil.nextInt(1000) + 1;
		int idx = 0;
		for (int i = 0; i < point_rates.length; i++) {
			if (r < point_rates[i]) {
				idx = i;
				break;
			}
		}
		int i = point_prize[idx];
		if (day == 3) {
			if (i == 1) {
				return 200;
			}
			if (i == 2) {
				return 500;
			}
			if (i == 3) {
				return 800;
			}
		}
		if (day == 5) {
			if (i == 1) {
				return 500;
			}
			if (i == 2) {
				return 1000;
			}
			if (i == 3) {
				return 1500;
			}
		}
		if (day == 7) {
			if (i == 1) {
				return 1000;
			}
			if (i == 2) {
				return 2000;
			}
			if (i == 3) {
				return 3000;
			}
		}
		return 0;
	}

	private String getDrawProp(int day) {
		int r = RandomUtil.nextInt(1000) + 1;
		int idx = 0;
		for (int i = 0; i < prop_rates.length; i++) {
			if (r < prop_rates[i]) {
				idx = i;
				break;
			}
		}
		int i = prop_prize[idx];
		if (day == 3) {
			if (i == 1) {
				return "鲜花×" + 1;
			}
			if (i == 2) {
				return "鲜花×" + 3;
			}
			if (i == 3) {
				return "鲜花×" + 5;
			}
			if (i == 4) {
				return "鲜花×" + 10;
			}
		}
		if (day == 5) {
			if (i == 1) {
				return "鲜花×" + 5;
			}
			if (i == 2) {
				return "鲜花×" + 10;
			}
			if (i == 3) {
				return "鲜花×" + 15;
			}
			if (i == 4) {
				return "鲜花×" + 20;
			}
		}
		if (day == 7) {
			if (i == 1) {
				return "鲜花×" + 10;
			}
			if (i == 2) {
				return "鲜花×" + 15;
			}
			if (i == 3) {
				return "鲜花×" + 20;
			}
			if (i == 4) {
				return "鲜花×" + 30;
			}
		}
		return "";
	}

	private Boolean check(int[] a, int b) {
		for (int i : a) {
			if (i == b)
				return true;
		}
		return false;
	}
}
