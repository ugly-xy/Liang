package com.zb.service.jobs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.DateUtil;
import com.zb.models.DocName;
import com.zb.models.goods.GiftLog;
import com.zb.service.GoodsService;
import com.zb.service.PropLogService;
import com.zb.service.pay.CoinService;
import com.zb.service.room.RoomHandlerDispatcher;

public class StJob {
	static final Logger log = LoggerFactory.getLogger(StJob.class);
	@Autowired
	RoomHandlerDispatcher roomHandlerDispatcher;

	@Autowired
	GoodsService goodsService;
	@Autowired
	PropLogService propLogService;

	@Autowired
	CoinService coinService;

	public static final int DIVIDE_DAY = 20171113;

	public void execute() {
		if (roomHandlerDispatcher.getDefService().lock(RK.stLock(), 10L)) {
			try {
				Calendar cbC = Calendar.getInstance();
				if (cbC.get(Calendar.HOUR_OF_DAY) == 5 && cbC.get(Calendar.MINUTE) == 1) {
					long now = cbC.getTimeInMillis();
					cbC.set(Calendar.SECOND, 0);
					long s = cbC.getTimeInMillis();
					cbC.set(Calendar.SECOND, 10);
					long e = cbC.getTimeInMillis();
					if (now >= s && now < e) {
						coinBalance();
					}
				}

				String lastId = roomHandlerDispatcher.getDefService().getRedisTemplate().opsForValue()
						.get(RK.stLastId());
				long id = 0;
				if (lastId != null) {
					id = Long.parseLong(lastId);
				}
				// gift
				List<DBObject> dbos = goodsService.getGiftLogs(id, 100);
				for (DBObject dbo : dbos) {
					id = DboUtil.getLong(dbo, "_id");
					long sendUid = DboUtil.getLong(dbo, "sendUid");
					long recvUid = DboUtil.getLong(dbo, "recvUid");
					long local = DboUtil.getInt(dbo, "local");
					long localid = DboUtil.getLong(dbo, "localId");
					int bgId = DboUtil.getInt(dbo, "bgId");
					long count = DboUtil.getLong(dbo, "count");
					long amount = DboUtil.getLong(dbo, "amount");
					long time = DboUtil.getLong(dbo, "updateTime");
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(time);
					int y = c.get(Calendar.YEAR);
					int m = c.get(Calendar.MONTH) + 1;
					int d = c.get(Calendar.DAY_OF_MONTH);
					int w = c.get(Calendar.WEEK_OF_YEAR);
					giftRecv(recvUid, bgId, count, amount, y, m, d, w);
					giftSend(sendUid, bgId, count, amount, y, m, d, w);
					recvTotal(recvUid, count, amount, y, m, d, w);
					sendTotal(sendUid, count, amount, y, m, d, w);
					userGift(recvUid, sendUid, bgId, count, amount, y, m, d, w);
					if (GiftLog.CIRCLE == local) {
						circleGift(localid, sendUid, bgId, count, amount);
					}
				}
				roomHandlerDispatcher.getDefService().getRedisTemplate().opsForValue().set(RK.stLastId(), "" + id);
				// prop
				String propLastId = roomHandlerDispatcher.getDefService().getRedisTemplate().opsForValue()
						.get(RK.stPropLastId());
				long propId = 0;
				if (propLastId != null) {
					propId = Long.parseLong(propLastId);
				}
				dbos = propLogService.getPropLogs(propId, 100);
				for (DBObject dbo : dbos) {
					propId = DboUtil.getLong(dbo, "_id");
					Long toUid = DboUtil.getLong(dbo, "toUid");
					if (null == toUid) {
						continue;
					}
					long uid = DboUtil.getLong(dbo, "uid");
					int bgId = DboUtil.getInt(dbo, "bgId");
					long count = DboUtil.getLong(dbo, "count");
					long amount = DboUtil.getLong(dbo, "amount");
					long time = DboUtil.getLong(dbo, "updateTime");
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(time);
					int y = c.get(Calendar.YEAR);
					int m = c.get(Calendar.MONTH) + 1;
					int d = c.get(Calendar.DAY_OF_MONTH);
					int w = c.get(Calendar.WEEK_OF_YEAR);
					giftRecv(uid, bgId, count, amount, y, m, d, w);
					giftSend(toUid, bgId, count, amount, y, m, d, w);
					recvTotal(uid, count, amount, y, m, d, w);
					sendTotal(toUid, count, amount, y, m, d, w);
					userGift(uid, toUid, bgId, count, amount, y, m, d, w);
				}
				roomHandlerDispatcher.getDefService().getRedisTemplate().opsForValue().set(RK.stPropLastId(),
						"" + propId);

				String strCoinId = roomHandlerDispatcher.getDefService().getRedisTemplate().opsForValue()
						.get(RK.stCoinLastId());
				long coinId = 0;
				if (strCoinId != null) {
					coinId = Long.parseLong(strCoinId);
				}
				// gift
				List<DBObject> coinDbos = coinService.findCoinLog(coinId, 100);
				for (DBObject dbo : coinDbos) {
					coinId = DboUtil.getLong(dbo, "_id");
					int io = DboUtil.getInt(dbo, "io");
					int type = DboUtil.getInt(dbo, "type");
					int amount = DboUtil.getInt(dbo, "amount");
					long time = DboUtil.getLong(dbo, "updateTime");
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(time);
					int y = c.get(Calendar.YEAR);
					int m = c.get(Calendar.MONTH) + 1;
					int d = c.get(Calendar.DAY_OF_MONTH);
					coinIO(type, io, amount, y, m, d);
				}
				roomHandlerDispatcher.getDefService().getRedisTemplate().opsForValue().set(RK.stCoinLastId(),
						"" + coinId);
			} finally {
				roomHandlerDispatcher.getDefService().unlock(RK.stLock());
			}
		}
	}

	private static final String COUNT = "count";
	private static final String AMOUNT = "amount";

	private void giftRecv(long uid, int bgId, long count, long amount, int y, int m, int d, int w) {
		int type = 0;
		int day = 0;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("giftRecv").findAndModify(
				new BasicDBObject("_id", day + "-" + bgId + "-" + uid).append("uid", uid).append("bgId", bgId)
						.append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);

		type = 1;
		if (y * 10000 + m * 100 + d <= StJob.DIVIDE_DAY) {
			day = y * 1000 + m * 100 + d;
		} else {
			day = y * 10000 + m * 100 + d;
		}
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("giftRecv").findAndModify(
				new BasicDBObject("_id", day + "-" + bgId + "-" + uid).append("uid", uid).append("bgId", bgId)
						.append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);

		type = 7;
		day = y * 100 + w;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("giftRecv").findAndModify(
				new BasicDBObject("_id", day + "-" + type + "-" + bgId + "-" + uid).append("uid", uid)
						.append("bgId", bgId).append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);

		type = 30;
		day = y * 100 + m;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("giftRecv").findAndModify(
				new BasicDBObject("_id", day + "-" + bgId + "-" + uid).append("uid", uid).append("bgId", bgId)
						.append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);
	}

	private void giftSend(long uid, int bgId, long count, long amount, int y, int m, int d, int w) {
		int type = 0;
		int day = 0;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("giftSend").findAndModify(
				new BasicDBObject("_id", day + "-" + bgId + "-" + uid).append("uid", uid).append("bgId", bgId)
						.append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);

		type = 1;
		day = getDay(y, m, d);
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("giftSend").findAndModify(
				new BasicDBObject("_id", day + "-" + bgId + "-" + uid).append("uid", uid).append("bgId", bgId)
						.append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);

		type = 7;
		day = y * 100 + w;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("giftSend").findAndModify(
				new BasicDBObject("_id", day + "-" + type + "-" + bgId + "-" + uid).append("uid", uid)
						.append("bgId", bgId).append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);

		type = 30;
		day = y * 100 + m;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("giftSend").findAndModify(
				new BasicDBObject("_id", day + "-" + bgId + "-" + uid).append("uid", uid).append("bgId", bgId)
						.append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);
	}

	private void recvTotal(long uid, long count, long amount, int y, int m, int d, int w) {
		int type = 0;
		int day = 0;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("recvTotal").findAndModify(
				new BasicDBObject("_id", day + "-" + uid).append("uid", uid).append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);

		type = 1;
		day = getDay(y, m, d);
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("recvTotal").findAndModify(
				new BasicDBObject("_id", day + "-" + uid).append("uid", uid).append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);

		type = 7;
		day = y * 100 + w;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("recvTotal").findAndModify(
				new BasicDBObject("_id", day + "-" + type + "-" + uid).append("uid", uid).append("type", type)
						.append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);

		type = 30;
		day = y * 100 + m;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("recvTotal").findAndModify(
				new BasicDBObject("_id", day + "-" + uid).append("uid", uid).append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);
	}

	private void sendTotal(long uid, long count, long amount, int y, int m, int d, int w) {
		int type = 0;
		int day = 0;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("sendTotal").findAndModify(
				new BasicDBObject("_id", day + "-" + uid).append("uid", uid).append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);

		type = 1;
		day = getDay(y, m, d);
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("sendTotal").findAndModify(
				new BasicDBObject("_id", day + "-" + uid).append("uid", uid).append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);

		type = 7;
		day = y * 100 + w;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("sendTotal").findAndModify(
				new BasicDBObject("_id", day + "-" + type + "-" + uid).append("uid", uid).append("type", type)
						.append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);

		type = 30;
		day = y * 100 + m;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("sendTotal").findAndModify(
				new BasicDBObject("_id", day + "-" + uid).append("uid", uid).append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);
	}

	private void userGift(long uid, long sendUid, int bgId, long count, long amount, int y, int m, int d, int w) {
		int type = 0;
		int day = 0;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("userGift")
				.findAndModify(new BasicDBObject("_id", day + "-" + bgId + "-" + uid + "-" + sendUid).append("uid", uid)
						.append("sendUid", sendUid).append("bgId", bgId).append("type", type).append("day", day), null,
						null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
						true, true);

		type = 1;
		day = getDay(y, m, d);
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("userGift")
				.findAndModify(new BasicDBObject("_id", day + "-" + bgId + "-" + uid + "-" + sendUid).append("uid", uid)
						.append("sendUid", sendUid).append("bgId", bgId).append("type", type).append("day", day), null,
						null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
						true, true);

		type = 7;
		day = y * 100 + w;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("userGift").findAndModify(
				new BasicDBObject("_id", day + "-" + type + "-" + bgId + "-" + uid + "-" + sendUid).append("uid", uid)
						.append("sendUid", sendUid).append("bgId", bgId).append("type", type).append("day", day),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);

		type = 30;
		day = y * 100 + m;
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("userGift")
				.findAndModify(new BasicDBObject("_id", day + "-" + bgId + "-" + uid + "-" + sendUid).append("uid", uid)
						.append("sendUid", sendUid).append("bgId", bgId).append("type", type).append("day", day), null,
						null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
						true, true);
	}

	private void circleGift(long localId, long sendUid, int bgId, long count, long amount) {
		roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection("articleGift").findAndModify(
				new BasicDBObject("_id", localId + "_" + sendUid + "-" + bgId).append("localId", localId)
						.append("sendUid", sendUid).append("bgId", bgId),
				null, null, false, new BasicDBObject("$inc", new BasicDBObject(COUNT, count).append(AMOUNT, amount)),
				true, true);

	}

	public static int getDay(int y, int m, int d) {
		if (y * 10000 + m * 100 + d <= DIVIDE_DAY) {
			return y * 1000 + m * 100 + d;
		}
		return y * 10000 + m * 100 + d;
	}

	private void coinIO(int type, int io, long amount, int y, int m, int d) {
		int day = y * 10000 + m * 100 + d;
		if (io == 1) {
			roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection(DocName.ST_COIN_IO).findAndModify(
					new BasicDBObject("_id", day + "-" + 0).append("type", 0).append("day", day), null, null, false,
					new BasicDBObject("$inc", new BasicDBObject("inAmount", amount)), true, true);
			roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection(DocName.ST_COIN_IO).findAndModify(
					new BasicDBObject("_id", day + "-" + type).append("type", type).append("day", day), null, null,
					false, new BasicDBObject("$inc", new BasicDBObject("inAmount", amount)), true, true);
		} else {
			roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection(DocName.ST_COIN_IO).findAndModify(
					new BasicDBObject("_id", day + "-" + 0).append("type", 0).append("day", day), null, null, false,
					new BasicDBObject("$inc", new BasicDBObject("outAmount", amount)), true, true);
			roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection(DocName.ST_COIN_IO).findAndModify(
					new BasicDBObject("_id", day + "-" + type).append("type", type).append("day", day), null, null,
					false, new BasicDBObject("$inc", new BasicDBObject("outAmount", amount)), true, true);
		}
	}

	private void coinBalance() {
		System.out.println(" coinBalance " );
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		int day = DateUtil.toDayyyyMMdd(c.getTime());
		DBObject dbo = roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection(DocName.COIN_BALANCE)
				.findOne(new BasicDBObject("_id", day));
		if (dbo == null) {
			List<BasicDBObject> pipeline = new ArrayList<BasicDBObject>();
			BasicDBObject ag = new BasicDBObject("$group",
					new BasicDBObject("_id", null).append("coin", new BasicDBObject("$sum", "$coin")));
			pipeline.add(ag);
			AggregationOutput aggrResult = roomHandlerDispatcher.getDefService().getMongoTemplate()
					.getCollection(DocName.USER).aggregate(pipeline);
			Iterator<DBObject> iter = aggrResult.results().iterator();
			while (iter.hasNext()) {
				DBObject obj = (DBObject) iter.next();
				Object tmp = obj.get("coin");
				System.out.println(tmp + " " + tmp.toString());
				Object curCoin = obj.get("coin");
				if (curCoin instanceof Long) {
					Long amount = (Long) curCoin;
					roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection(DocName.COIN_BALANCE)
							.findAndModify(new BasicDBObject("_id", day), null, null, false,
									new BasicDBObject("balance", amount), true, true);
				} else if (curCoin instanceof Double) {
					Double amount = (Double) curCoin;
					roomHandlerDispatcher.getDefService().getMongoTemplate().getCollection(DocName.COIN_BALANCE)
							.findAndModify(new BasicDBObject("_id", day), null, null, false,
									new BasicDBObject("balance", amount), true, true);
				}

			}
		}
	}

	public static void main(String[] args) {
		StJob st = new StJob();
		st.coinIO(1, 1, 100, 2017, 11, 20);
		st.coinIO(1, 2, 100, 2017, 11, 20);

		st.coinIO(2, 1, 100, 2017, 11, 20);
		st.coinIO(2, 2, 100, 2017, 11, 20);
	}

}
