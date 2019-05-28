package com.zb.service.lottery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.http.HttpClientUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.DateUtil;
import com.zb.models.DocName;
import com.zb.models.lottery.WelfareLottery;
import com.zb.service.BaseService;
import com.zb.service.othergames.GuessService;

@Service
public class SpiderLotteryService extends BaseService {

	@Autowired
	GuessService guessSerive;

	private static final String BJ = "http://www.bwlc.net/bulletin/qck3.html";
	private static final String SH = "http://fucai.eastday.com/LotteryNew/LotteryInfoList.aspx";
	public static final int CITY_BJ = 1;
	public static final int CITY_SH = 2;

	public void spiderLottery(int city) throws IOException {
		long now = System.currentTimeMillis();
		if (city == CITY_BJ) {
			String html = HttpClientUtil.get(BJ, null);
			Document document = Jsoup.parse(html);
			Elements trs = document.getElementById("lottery_tabs").getElementsByTag("tr");
			Iterator<Element> it = trs.iterator();
			int size = trs.size();
			if (size > 1) {
				int count = 1;
				List<WelfareLottery> list = new ArrayList<>();
				while (it.hasNext()) {
					Element e = (Element) it.next();
					if (count > 0) {
						count--;
						continue;
					} else {
						String issue = e.child(0).text();
						String no = e.child(1).text();
						String date = e.child(2).text();
						String[] s = no.split(",");
						int sum = 0;
						for (int i = 0; i < s.length; i++) {
							sum += Integer.parseInt(s[i].trim());
						}
						WelfareLottery bj = new WelfareLottery(Integer.parseInt(issue), no, date, CITY_BJ, sum);
						list.add(bj);
					}
				}
				WelfareLottery bj = list.parallelStream().max((e1, e2) -> e1.getIssue() - e2.getIssue()).get();
				Long no = queryNewestNo(city);
				boolean soon = true;
				if (null == no || no < bj.getIssue()) {
					this.save(bj);
					soon = false;
				}
				long nextTime = 0L;
				if (soon) {
					nextTime = now +Const.MINUTE;
				} else {
					nextTime = now + 10 * Const.MINUTE;
				}
				getRedisTemplate().opsForValue().set(RK.nextQueryLotteryTime(SpiderLotteryService.CITY_BJ),
						Long.toString(nextTime));
			}
		} else if (city == CITY_SH) {
			String html = HttpClientUtil.get(SH, null);
			Document document = Jsoup.parse(html);
			Elements trs = document.getElementById("frm").getElementsByTag("tr");
			int size = trs.size();
			if (size > 2) {
				int count = 2;
				Iterator<Element> it = trs.iterator();
				List<WelfareLottery> list = new ArrayList<>();
				while (it.hasNext()) {
					Element e = (Element) it.next();
					if (count > 0) {
						count--;
						continue;
					} else {
						String issue = e.child(0).text();
						String date = e.child(1).text();
						String no1 = e.child(2).child(0).text();
						String no2 = e.child(3).child(0).text();
						String no3 = e.child(4).child(0).text();
						WelfareLottery sh = new WelfareLottery(Integer.parseInt(issue.substring(9)),
								no1 + "," + no2 + "," + no3, issue.substring(0, 8) + ":" + date, CITY_SH,
								Integer.parseInt(no1) + Integer.parseInt(no2) + Integer.parseInt(no3));
						list.add(sh);
					}
				}
				WelfareLottery sh = list.parallelStream().max((e1, e2) -> e1.getIssue() - e2.getIssue()).get();
				Long no = queryNewestNo(city);
				boolean soon = true;
				if (null == no || no < sh.getIssue()) {
					this.save(sh);
					soon = false;
				}
				long nextTime = 0L;
				if (soon) {
					nextTime = now +Const.MINUTE;
				} else {
					nextTime = now + 10 * Const.MINUTE;
				}
				getRedisTemplate().opsForValue().set(RK.nextQueryLotteryTime(SpiderLotteryService.CITY_SH),
						Long.toString(nextTime));
			}
		}
	}

	private Long queryNewestNo(int city) {
		DBObject q = new BasicDBObject();
		q.put("city", city);
		q.put("updateTime", new BasicDBObject("$gt", DateUtil.getTodayZeroTime()));
		List<DBObject> list = super.getC(DocName.WELFARELOTTERY).find(q).sort(new BasicDBObject("_id", -1)).toArray();
		if (list.isEmpty())
			return null;
		else
			return DboUtil.getLong(list.get(0), "issue");
	}

	public DBObject findByCityIssue(Integer city, Integer issue) {
		DBObject q = new BasicDBObject();
		if (null != city) {
			q.put("city", city);
		}
		if (null != issue) {
			q.put("issue", issue);
		}
		q.put("updateTime", new BasicDBObject("$gt", DateUtil.getTodayZeroTime()));
		return super.getC(DocName.WELFARELOTTERY).findOne(q);
	}

	public void save(WelfareLottery wfl) {
		wfl.set_id(super.getNextId(DocName.WELFARELOTTERY));
		super.getMongoTemplate().save(wfl);
		if (wfl.getCity() == CITY_SH) {// 猜猜
			guessSerive.aotuDrawGuess(wfl);
		}
	}
}
