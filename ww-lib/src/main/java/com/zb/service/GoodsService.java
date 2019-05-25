package com.zb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.GiftForm;
import com.zb.common.Constant.IMType;
import com.zb.common.Constant.OperationType;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.mongo.Op;
import com.zb.common.redis.RK;
import com.zb.common.utils.RandomUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.finance.CoinLog;
import com.zb.models.goods.BaseGoods;
import com.zb.models.goods.BaseGoods.G;
import com.zb.models.goods.BaseGoods.Gift;
import com.zb.models.goods.BaseGoods.GoodsType;
import com.zb.models.goods.GiftLog;
import com.zb.models.goods.Goods;
import com.zb.models.goods.GoodsItem;
import com.zb.models.goods.PropLog;
import com.zb.models.room.Activity;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.activity.WerewolfKillActor;
import com.zb.models.room.activity.WerewolfKillActorState;
import com.zb.models.usertask.Task;
import com.zb.service.article.ArticleService;
import com.zb.service.pay.CoinService;
import com.zb.service.room.RoomDefaultService;
import com.zb.service.room.RoomHandlerDispatcher;
import com.zb.service.usertask.UserTaskService;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.Msg;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class GoodsService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(GoodsService.class);

	@Autowired
	RoomDefaultService roomDefaultService;

	@Autowired
	RoomHandlerDispatcher roomHandlerDispatcher;

	@Autowired
	CoinService coinService;

	@Autowired
	UserPackService userPackService;

	@Autowired
	MessageService messageService;

	@Autowired
	UserTaskService userTaskService;
	@Autowired
	UserService userService;
	@Autowired
	ArticleService articleService;

	private static int ALL = 0;

	public DBObject findById(Long id) {
		return super.findById(DocName.GOODS, id);
	}

	public Page<DBObject> query(Integer baseId, Integer status, Integer page, Integer size, Op op) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(baseId, status, page, size, op);
		Integer count = count(baseId, status, op);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> find(Integer baseId, Integer status, Integer page, Integer size, Op op) {
		DBObject q = new BasicDBObject();
		if (baseId != null && baseId != 0) {
			q.put("baseId", baseId);
		}
		if (op != null) {
			op.getOp(q, "status", status);
		} else if (null != status && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.GOODS).find(q).sort(new BasicDBObject("sort", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
	}

	public Integer count(Integer baseId, Integer status, Op op) {
		DBObject q = new BasicDBObject();
		if (baseId != null && baseId != 0) {
			q.put("baseId", baseId);
		}
		if (op != null) {
			op.getOp(q, "status", status);
		} else if (null != status && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.GOODS).find(q).count();
	}

	public ReMsg update(Long id, String name, String img, Integer listPrice, Integer price, Long amount, Long startTime,
			Long endTime, Integer status, Integer sort) {

		DBObject a = findById(id);
		if (null == a) {
			return new ReMsg(ReCode.FAIL);
		}

		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		if (StringUtils.isNotBlank(name)) {
			u.put("name", name);
		}
		if (StringUtils.isNotBlank(img)) {
			u.put("img", img);
		}

		if (listPrice != 0) {
			u.put("listPrice", listPrice);
		}
		if (status != 0) {
			u.put("status", status);
		}
		if (price != 0) {
			u.put("price", price);
		}
		if (amount != 0) {
			u.put("amount", amount);
		}
		if (sort != 0) {
			u.put("sort", sort);
		}
		if (startTime != 0) {
			u.put("startTime", startTime);
		}
		if (endTime != 0) {
			u.put("endTime", endTime);
		}

		if (getC(DocName.GOODS).update(new BasicDBObject("_id", id), new BasicDBObject("$set", u), false, false,
				WriteConcern.ACKNOWLEDGED).getN() > 0) {
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);

	}

	/** 查询用户某天送花总数 */
	public DBObject findSendTotal(long uid, int day) {
		return getC(DocName.SEND_TOTAL).findOne(new BasicDBObject("_id", day + "-" + uid));
	}

	public ReMsg save(String name, String img, Integer listPrice, Integer price, Long startTime, Long endTime,
			Integer status, Integer sort, int cat, int limit, List<GoodsItem> items) {
		Long id = super.getNextId(DocName.GOODS);
		Goods g = new Goods(id, name, img, listPrice, price, startTime, endTime, status, sort, cat, items);
		getMongoTemplate().save(g);
		return new ReMsg(ReCode.OK, g);
	}

	public BaseGoods getBaseGoodsById(String goodsType, int id) {
		G[] gs = getGs(goodsType);
		if (gs != null) {
			for (G g : gs) {
				if (g.getV().getId() == id) {
					return g.getV();
				}
			}
		}
		return null;
	}

	public BaseGoods getBaseGoodsById(GoodsType gt, int id) {
		G[] gs = getGs(gt);
		if (gs != null) {
			for (G g : gs) {
				if (g.getV().getId() == id) {
					return g.getV();
				}
			}
		}
		return null;
	}

	// public Page<DBObject> queryBaseGoods(String goodsType, Integer page,
	// Integer size) {
	// size = getSize(size);
	// page = getPage(page);
	// List<DBObject> dbos = findBaseGoods(goodsType, page, size);
	// Integer count = countBaseGoods(goodsType);
	// return new Page<DBObject>(count, size, page, dbos);
	// }

	public List<BaseGoods> getBaseGoods(String goodsType) {
		G[] gs = getGs(goodsType);
		if (gs != null) {
			List<BaseGoods> bs = new ArrayList<BaseGoods>();
			for (G g : gs) {
				bs.add(g.getV());
			}
			return bs;
		}
		return null;
	}

	public G[] getGs(String goodsType) {
		GoodsType gt = GoodsType.valueOf(goodsType);
		G[] gs = null;
		if (gt.getT() == BaseGoods.GoodsType.PROP.getT()) {
			gs = BaseGoods.Prop.values();
		} else if (gt.getT() == BaseGoods.GoodsType.MONEY.getT()) {
			gs = BaseGoods.Money.values();
		} else if (gt.getT() == BaseGoods.GoodsType.GIFT.getT()) {
			gs = BaseGoods.Gift.values();
		} else if (gt.getT() == BaseGoods.GoodsType.EXP.getT()) {
			gs = BaseGoods.Exp.values();
		} else if (gt.getT() == BaseGoods.GoodsType.CAR.getT()) {
			gs = BaseGoods.Car.values();
		}
		return gs;
	}

	public G[] getGs(GoodsType gt) {
		G[] gs = null;
		if (gt.getT() == BaseGoods.GoodsType.PROP.getT()) {
			gs = BaseGoods.Prop.values();
		} else if (gt.getT() == BaseGoods.GoodsType.MONEY.getT()) {
			gs = BaseGoods.Money.values();
		} else if (gt.getT() == BaseGoods.GoodsType.GIFT.getT()) {
			gs = BaseGoods.Gift.values();
		} else if (gt.getT() == BaseGoods.GoodsType.EXP.getT()) {
			gs = BaseGoods.Exp.values();
		} else if (gt.getT() == BaseGoods.GoodsType.CAR.getT()) {
			gs = BaseGoods.Car.values();
		}
		return gs;
	}

	public ReMsg sendGift(int id, Integer count, Integer local, Long localId, Long toUid) {
		return sendGift(id, count, local, localId, toUid, 0L);
	}

	public ReMsg sendGift(int id, Integer count, Integer local, Long localId, Long toUid, long uid) {
		if (1 > uid) {
			uid = super.getUid();
		}
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (count < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		BaseGoods bg = getBaseGoodsById(BaseGoods.GoodsType.GIFT, id);
		if (bg != null) {
			ReMsg rs = userPackService.useGoods(uid, bg, count);
			int used = 0;
			if (rs.getCode() == ReCode.OK.reCode()) {
				used = Integer.parseInt(rs.getData().toString());
			}
			ReCode rc = ReCode.OK;
			if (used < count) {
				rc = coinService.reduce(uid, CoinLog.OUT_GIFT, id, bg.getPrice() * (count - used), 0, "赠送礼物");
			}
			if (rc.reCode() == ReCode.OK.reCode()) {
				boolean myself = uid == toUid ? true : false;
				// 送花成功 增加经验
				userService.changePoint(uid, Point.SENDGIFT.getType(), bg.getPrice() * count / 5, 0);
				// 爆花
				long now = System.currentTimeMillis();
				// 12.23 0:00 至 1.4 0:00
				if (now > 1513958400000L && now < 1514995200000L) {
					flowersCrit(uid, count);
				}
				if (local == null) {
					local = 0;
				}
				if (localId == null) {
					localId = 0L;
				}
				GiftLog gl = new GiftLog(super.getNextId(DocName.GIFT_LOG), uid, toUid, local, localId, bg.getId(),
						count, bg.getPrice() * count, used);
				super.getMongoTemplate().save(gl);
				if (!myself && count > 519) {
					userService.changeGuard(toUid, uid, count);
				}
				if (local == GiftLog.ROOM) {
					MapBody m = new MapBody(SocketHandler.ROOM_GIFT, SocketHandler.FN_ROOM_ID, localId)
							.append(SocketHandler.FN_USER_ID, uid).append("toUid", toUid).append("bgId", bg.getId())
							.append("count", count);
					roomDefaultService.pubRoomMsg(localId, MsgType.ROOM, m);
				} else if (local == GiftLog.CIRCLE) {
					DBObject article = articleService.findById(localId);
					if (id == Gift.FLOWER.getV().getId()) {
						articleService.upHot(article, count);
					}
					if (!myself) {// 不是给自己帖子送花 才发消息
						String content = DboUtil.getString(article, "content");
						if (StringUtils.isNotBlank(content)) {
							if (content.length() > 10) {
								content = content.substring(0, 10) + "...";
							}
						}
						MapBody mb = new MapBody("op", OperationType.ARTICLE.getOp()).append("opId", localId)
								.append("content", DboUtil.getString(userService.findById(uid), "nickname") + "送给你的帖子"
										+ count + bg.getUnit() + bg.getName() + ":[" + content + "]");
						Msg msg = new Msg(super.incrMsgId(), MsgType.NOTICE, (int) uid, toUid,
								System.currentTimeMillis(), mb.getData());
						messageService.msgHandler(msg);
					}
				} else if (local == GiftLog.USER_ZONE) {
					if (!myself) {
						Map<String, Object> ext = new HashMap<String, Object>();
						ext.put("type", IMType.FLOWER.getType());
						ext.put("op", OperationType.USERSPACE.getOp());
						ext.put("opId", uid);
						messageService.imMsgHandler(uid, toUid,
								"[" + uid + "] 送给 [" + toUid + "] " + count + bg.getUnit() + bg.getName(), ext,
								IMType.FLOWER.getType(), DboUtil.getString(userService.findById(uid), "nickname")
										+ " 送给你 " + count + bg.getUnit() + bg.getName());
					}
				}
				// 进行赠送玫瑰的新手任务
				if (id == BaseGoods.Gift.FLOWER.getV().getId()) {
					userTaskService.doUserTask(uid, Task.FLOWER, count);
				}
				// 删除用户收花缓存
				String key = toUid + "-" + ALL + "-" + id;
				super.getRedisTemplate().delete(RK.keyGiftLog(key));
				return new ReMsg(ReCode.OK);
			} else {
				return new ReMsg(rc);
			}
		}
		return new ReMsg(ReCode.PARAMETER_ERR);
	}

	public List<DBObject> getGiftLogs(long id, int size) {
		return super.getC(DocName.GIFT_LOG).find(new BasicDBObject("_id", new BasicDBObject("$gt", id))).limit(size)
				.toArray();
	}

	public ReMsg useProp(Long uid, Long id, Integer count, Long roomId, Long toUid) {
		if (count < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		if (id == BaseGoods.Prop.DICE.getV().getId()) {
			data.put("dice", RandomUtil.nextInt(6) + 1);
			MapBody m = new MapBody(SocketHandler.ROOM_PROP, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_USER_ID, uid).append("prop", id).append("count", count)
					.append("data", data);
			if (toUid != null) {
				m.append("toUid", toUid);
			}
			roomDefaultService.pubRoomMsg(roomId, MsgType.ROOM, m);
		} else if (id == BaseGoods.Prop.FAST_CARD.getV().getId()) {// 取消快进卡
			// Room r = roomDefaultService.getRoom(roomId);
			// return roomHandlerDispatcher.getService(r).userFastCard(uid, r);
		} else if (id == BaseGoods.Prop.WOLF.getV().getId() || id == BaseGoods.Prop.KISS.getV().getId()
				|| id == BaseGoods.Prop.GOODMAN.getV().getId() || id == BaseGoods.Prop.SLIPPER.getV().getId()) {
			BaseGoods bg = getBaseGoodsById(BaseGoods.GoodsType.PROP, id.intValue());
			ReMsg rs = userPackService.useGoods(uid, bg, count);
			int used = 0;
			if (rs.getCode() == ReCode.OK.reCode()) {
				used = Integer.parseInt(rs.getData().toString());
			}
			ReCode rc = ReCode.OK;
			String detail = "未知";
			if (id == BaseGoods.Prop.WOLF.getV().getId()) {
				detail = "使用狼飞镖";
			} else if (id == BaseGoods.Prop.KISS.getV().getId()) {
				detail = "使用么么哒";
			} else if (id == BaseGoods.Prop.GOODMAN.getV().getId()) {
				detail = "使用好人飞镖";
			} else if (id == BaseGoods.Prop.SLIPPER.getV().getId()) {
				detail = "使用拖鞋";
			}
			if (used < count) {
				rc = coinService.reduce(uid, CoinLog.OUT_PROP, id, bg.getPrice() * (count - used), 0, detail);
			}
			if (rc.reCode() == ReCode.OK.reCode()) {
				userService.changePoint(uid, Point.SENDGIFT.getType(), bg.getPrice() * count / 5, 0);
				PropLog pl = new PropLog(super.getNextId(DocName.PROP_LOG), uid, toUid, PropLog.ROOM, roomId,
						bg.getId(), count, bg.getPrice() * count, used);
				super.getMongoTemplate().save(pl);
				MapBody m = new MapBody(SocketHandler.ROOM_PROP, SocketHandler.FN_ROOM_ID, roomId)
						.append(SocketHandler.FN_USER_ID, uid).append("prop", id).append("count", count);
				if (toUid != null) {
					m.append("toUid", toUid);
				}
				Activity ac = roomDefaultService.getActivity(roomId, Activity.class);
				if (null == ac) {
					return new ReMsg(ReCode.FAIL);
				} else if (ac.getStatus() != Activity.STATUS_READY) {
					Room room = roomDefaultService.getRoom(roomId);
					if (null != room && (room.getType() == ActivityType.WEREWOLF.getType()
							|| room.getType() == ActivityType.WEREWOLF6.getType()
							|| room.getType() == ActivityType.WEREWOLF9.getType())) {
						Set<WerewolfKillActorState> set = roomDefaultService.getRoomActorsState(roomId,
								WerewolfKillActorState.class);
						Set<Long> servivals = set.stream().filter(e -> !e.getDeath() || e.getDeath() && e.isDeadLater())
								.map(e -> e.getUid()).collect(Collectors.toSet());
						if (!servivals.contains(uid) || roomDefaultService
								.getRoomActor(roomId, uid, WerewolfKillActor.class).getRole() == Actor.ROLE_VIEWER) {
							long now = System.currentTimeMillis();
							Set<WerewolfKillActor> ws = roomDefaultService.getRoomActors(roomId,
									WerewolfKillActor.class);
							for (int i = 0; i < count; i++) {
								for (WerewolfKillActor wa : ws) {
									if (!servivals.contains(wa.getUid()) || roomDefaultService
											.getRoomActor(roomId, wa.getUid(), WerewolfKillActor.class)
											.getRole() == Actor.ROLE_VIEWER) {
										roomDefaultService.pubRoomUserMsg(roomId, wa.getUid(), MsgType.ROOM, m, now);
									}
								}
							}
						} else {
							if (ac.getStatus() == WerewolfKillActor.STATUS_KILL_CHECK
									|| ac.getStatus() == WerewolfKillActor.STATUS_SAVE_POISON
									|| ac.getStatus() == WerewolfKillActor.STATUS_DEFEND) {
								roomDefaultService.pubRoomUserMsg(roomId, toUid, MsgType.ROOM, m,
										System.currentTimeMillis());
							} else {
								for (int i = 0; i < count; i++) {
									roomDefaultService.pubRoomMsg(roomId, MsgType.ROOM, m);
								}
							}
						}
					} else {
						for (int i = 0; i < count; i++) {
							roomDefaultService.pubRoomMsg(roomId, MsgType.ROOM, m);
						}
					}
				} else {
					for (int i = 0; i < count; i++) {
						roomDefaultService.pubRoomMsg(roomId, MsgType.ROOM, m);
					}
				}
				return new ReMsg(ReCode.OK);
			} else {
				return new ReMsg(rc);
			}
		} else if (id == BaseGoods.Prop.HORN.getV().getId()) {
			PropLog pl = new PropLog(super.getNextId(DocName.PROP_LOG), uid, null, PropLog.ROOM, roomId, id.intValue(),
					count, 0, -1);
			super.getMongoTemplate().save(pl);
			return new ReMsg(ReCode.OK);
		} else if (id == BaseGoods.Prop.GOLDEN_HORN.getV().getId()) {
			BaseGoods bg = getBaseGoodsById(BaseGoods.GoodsType.PROP, id.intValue());
			ReMsg rs = userPackService.useGoods(uid, bg, count);
			int used = 0;
			if (rs.getCode() == ReCode.OK.reCode()) {
				used = Integer.parseInt(rs.getData().toString());
			}
			ReCode rc = ReCode.OK;
			if (used < count) {
				rc = coinService.reduce(uid, CoinLog.OUT_PROP, id, bg.getPrice() * (count - used), 0, "使用金喇叭");
			}
			if (rc.reCode() == ReCode.OK.reCode()) {
				userService.changePoint(uid, Point.SENDGIFT.getType(), bg.getPrice() * count / 5, 0);
				PropLog pl = new PropLog(super.getNextId(DocName.PROP_LOG), uid, null, PropLog.ROOM, roomId, bg.getId(),
						count, bg.getPrice() * count, used);
				super.getMongoTemplate().save(pl);
				return new ReMsg(ReCode.OK);
			} else {
				return new ReMsg(rc);
			}
		} else {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return new ReMsg(ReCode.OK);
	}

	// 查询该用户一共收了多少礼物
	public Long queryRecvGiftCnt(Long uid, Integer bgId) {
		DBObject q = new BasicDBObject();
		if (uid != null) {
			q.put("uid", uid);
		}
		if (bgId != null && bgId != 0) {
			q.put("bgId", bgId);
		}
		// 查总榜
		q.put("type", 0);
		DBObject object = getC(DocName.GIFT_RECV).findOne(q);
		Long count = DboUtil.getLong(object, "count");
		if (null == count) {
			count = 0L;
		}
		return count;
	}

	// 查询该用户一共赠送多少礼物
	public long querySendGiftCnt(Long uid, Integer bgId) {
		DBObject q = new BasicDBObject();
		if (uid != null) {
			q.put("uid", uid);
		}
		if (bgId != null && bgId != 0) {
			q.put("bgId", bgId);
		}
		// 查总榜
		q.put("type", 0);
		DBObject object = getC(DocName.GIFT_SEND).findOne(q);
		Long count = DboUtil.getLong(object, "count");
		if (null == count) {
			count = 0L;
		}
		return count;
	}

	// 空间送花榜单
	public ReMsg userSpaceGift(Long uid, Integer day, Integer bgId, Integer page, Integer size) {
		if (null == page || page < 1) {
			page = 1;
		}
		if (null == size || size > 10) {
			size = 10;
		}
		if (null == uid || uid < 1) {
			return new ReMsg(ReCode.FAIL);
		}
		if (null == day) {
			day = 0;
		}
		if (null == bgId) {
			bgId = BaseGoods.Gift.FLOWER.getV().getId();
		}
		return new ReMsg(ReCode.OK, queryUserGift(uid, day, bgId, page, size));
	}

	// 圈子送花榜单
	public ReMsg articleGift(Long articleId, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		List<DBObject> list = queryArticleGift(articleId, BaseGoods.Gift.FLOWER.getV().getId(), page, size);
		return new ReMsg(ReCode.OK, size, page, list);
	}

	// 查询userGift
	public List<DBObject> queryUserGift(Long recvUid, Integer day, Integer bgId, int page, int size) {
		String key = recvUid + "-" + day + "-" + bgId;
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String objects = opsv.get(RK.keyGiftLog(key));
		if (objects != null) {
			return (List<DBObject>) JSON.parse(objects);
		}
		DBObject q = new BasicDBObject();

		if (null != recvUid && recvUid > 0) {
			q.put("uid", recvUid);
		}
		// 类型
		if (null != day) {
			q.put("day", day);
		}
		// 礼物类型
		if (null != bgId && bgId > 0) {
			q.put("bgId", bgId);
		}
		List<DBObject> dbos = getC(DocName.USER_GIFT).find(q).sort(new BasicDBObject("count", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
		;
		if (null == dbos || dbos.size() == 0) {
			return dbos;
		}
		for (DBObject dbo : dbos) {
			Long sendUid = DboUtil.getLong(dbo, "sendUid");
			DBObject user = userService.findById(sendUid);
			dbo.put("uid", sendUid);
			dbo.put("local", ALL);
			dbo.put("localId", ALL);
			dbo.put("bgId", bgId);
			dbo.put("nickname", DboUtil.getString(user, "nickname"));
			dbo.put("avatar", DboUtil.getString(user, "avatar"));
			dbo.put("point", DboUtil.getInt(user, "point"));
			dbo.put("vip", DboUtil.getInt(user, "vip"));
			dbo.put("sex", DboUtil.getInt(user, "sex"));
			dbo.put("cover", DboUtil.getString(user, "cover"));
		}
		// 存储redis缓存
		opsv.set(RK.keyGiftLog(key), JSON.serialize(dbos), 1, TimeUnit.HOURS);
		return dbos;
	}

	// 查询articleGift
	public List<DBObject> queryArticleGift(long localId, int bgId, int page, int size) {
		DBObject q = new BasicDBObject();
		// 文章id
		if (localId > 0) {
			q.put("localId", localId);
		}
		// 礼物类型
		if (bgId > 0) {
			q.put("bgId", bgId);
		}
		List<DBObject> dbos = getC(DocName.ARTICLE_GIFT).find(q).sort(new BasicDBObject("count", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
		if (null == dbos || dbos.size() == 0) {
			return dbos;
		}
		for (DBObject dbo : dbos) {
			Long sendUid = DboUtil.getLong(dbo, "sendUid");
			// 添加user信息
			DBObject user = userService.findById(sendUid);
			dbo.put("uid", sendUid);
			dbo.put("local", GiftLog.CIRCLE);
			dbo.put("localId", DboUtil.getLong(dbo, "localId"));
			dbo.put("bgId", bgId);
			dbo.put("nickname", DboUtil.getString(user, "nickname"));
			dbo.put("avatar", DboUtil.getString(user, "avatar"));
			dbo.put("point", DboUtil.getInt(user, "point"));
			dbo.put("vip", DboUtil.getInt(user, "vip"));
			dbo.put("sex", DboUtil.getInt(user, "sex"));
			dbo.put("cover", DboUtil.getString(user, "cover"));
		}
		return dbos;
	}

	// 后台查询userGift 不存储缓存
	public Page<DBObject> adminUserGift(Long recvUid, Integer day, Integer bgId, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		if (null == bgId) {
			bgId = BaseGoods.Gift.FLOWER.getV().getId();
		}

		DBObject q = new BasicDBObject();
		if (null == recvUid || recvUid < 1) {
			return new Page<>(0, 1, 1, new ArrayList<>());
		}
		// 接受人id
		q.put("uid", recvUid);
		// 礼物类型
		q.put("bgId", bgId);
		// 日期类型
		if (null != day) {
			q.put("day", day);
		}
		List<DBObject> dbos = getC(DocName.USER_GIFT).find(q).sort(new BasicDBObject("count", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
		;
		if (null == dbos || dbos.size() == 0) {
			return new Page<>(0, 1, 1, new ArrayList<>());
		}
		for (DBObject dbo : dbos) {
			Long sendUid = DboUtil.getLong(dbo, "sendUid");
			DBObject user = userService.findById(sendUid);
			dbo.put("uid", sendUid);
			dbo.put("local", ALL);
			dbo.put("localId", ALL);
			dbo.put("bgId", bgId);
			dbo.put("nickname", DboUtil.getString(user, "nickname"));
			dbo.put("avatar", DboUtil.getString(user, "avatar"));
			dbo.put("point", DboUtil.getInt(user, "point"));
			dbo.put("vip", DboUtil.getInt(user, "vip"));
			dbo.put("sex", DboUtil.getInt(user, "sex"));
			dbo.put("cover", DboUtil.getString(user, "cover"));
		}
		int count = getC(DocName.USER_GIFT).find(q).count();
		return new Page<DBObject>(count, size, page, dbos);
	}

	public Page<DBObject> adminArticleGift(Long localId, Integer bgId, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		if (null == bgId) {
			bgId = BaseGoods.Gift.FLOWER.getV().getId();
		}

		DBObject q = new BasicDBObject();
		// 文章id
		if (null == localId || localId < 1) {
			return new Page<>(0, 1, 1, new ArrayList<>());

		}
		q.put("localId", localId);
		// 礼物类型
		if (null != bgId && bgId > 0) {
			q.put("bgId", bgId);
		}
		List<DBObject> dbos = getC(DocName.ARTICLE_GIFT).find(q).sort(new BasicDBObject("count", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
		;
		if (null == dbos || dbos.size() == 0) {
			return new Page<>(0, 1, 1, new ArrayList<>());
		}
		for (DBObject dbo : dbos) {
			Long sendUid = DboUtil.getLong(dbo, "sendUid");
			// 添加user信息
			DBObject user = userService.findById(sendUid);
			dbo.put("uid", sendUid);
			dbo.put("local", GiftLog.CIRCLE);
			dbo.put("localId", DboUtil.getLong(dbo, "localId"));
			dbo.put("bgId", bgId);
			dbo.put("nickname", DboUtil.getString(user, "nickname"));
			dbo.put("avatar", DboUtil.getString(user, "avatar"));
			dbo.put("point", DboUtil.getInt(user, "point"));
			dbo.put("vip", DboUtil.getInt(user, "vip"));
			dbo.put("sex", DboUtil.getInt(user, "sex"));
			dbo.put("cover", DboUtil.getString(user, "cover"));
		}
		int count = getC(DocName.ARTICLE_GIFT).find(q).count();
		return new Page<DBObject>(count, size, page, dbos);
	}

	public static final int[] OPTIONS = new int[] { 6, 33, 88, 188, 520, 666, 888, 1314, 3344, 6666, 9999 };
	public static final int BASE = 10000;

	/** 圣诞活动期间爆花 */
	public void flowersCrit(long uid, int cnt) {
		// int day = RankListService.getDay(RankListService.DAY);
		int num = RandomUtil.nextInt(200 * BASE);
		int sum = 0;
		for (int i = OPTIONS.length - 1; i >= 0; i--) {
			int t = (int) (BASE * cnt / OPTIONS[i]);
			sum += t;
			// System.out.print(" t:"+ t+" s:"+sum+" ");
			if (num < sum) {
				messageService.imMsgHandler(Const.SYSTEM_ID, uid,
						"恭喜您成功送出鲜花" + cnt + "朵,幸运的获得了圣诞老公公赠送的鲜花大礼包（鲜花*" + OPTIONS[i] + "），已放入您的背包中，祝您圣诞快乐！", null,
						IMType.FLOWER.getType(), null);
				userPackService.addGoods(uid, BaseGoods.Gift.FLOWER.getV(), OPTIONS[i], GiftForm.EVENT, cnt);
				// System.out.print(" Z:"+OPTIONS[i]);
				break;
			}
		}
		// System.out.println();
	}

	public static void main(String[] args) {
		GoodsService gs = new GoodsService();
		int[] s = { 1, 3, 6, 66, 88, 520, 1314, 3344, 10000, 50000, 100000, 200000, 500000 };
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < s.length; j++) {
				System.out.print(s[j] + " ");
				gs.flowersCrit(123, s[j]);
			}
		}
	}

	// public static int test(int cnt) {
	// int[] probability = new int[OPTIONS.length];
	// for (int i = OPTIONS.length-1; i >= 0; i--) {
	// probability[i] = (int) ((BASE * 0.01 * cnt) / OPTIONS[i]);
	// if(i<OPTIONS.length-1){
	// probability[i]=probability[i]+probability[i+1];
	// }
	// }
	// for (int i = probability.length - 1; i >= 0; i--) {
	// System.out.print(OPTIONS[i]+":"+probability[i]+" ");
	// }
	// int num = RandomUtil.nextInt(BASE);
	// for (int i = probability.length - 1; i >= 0; i--) {
	// if (num <= probability[i]) {
	// return OPTIONS[i];
	// }
	// }
	// return 0;
	// }

	// public static void main(String[] args) {
	// Map<String, Integer> map = new HashMap<String, Integer>();
	// test(80);
	// for (int i = 0; i < 10000000; i++) {
	// String test = "" + test(1);
	// map.put(test, null == map.get(test) ? 1 : map.get(test) + 1);
	// }
	// for (Entry<String, Integer> string : map.entrySet()) {
	// System.out.println(string.getKey()+" : "+string.getValue());
	// }
	// }
}
