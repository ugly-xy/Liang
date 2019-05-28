package com.zb.service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.GiftForm;
import com.zb.common.Constant.OperationType;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.MapUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.UserTitle;
import com.zb.models.finance.CoinLog;
import com.zb.models.finance.Shout;
import com.zb.models.finance.ShoutComment;
import com.zb.models.goods.BaseGoods;
import com.zb.models.user.Relationship;
import com.zb.service.pay.CoinService;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.Msg;
import com.zb.socket.model.MsgType;

@Service
public class RechargeShoutService extends BaseService {
	static final Logger LOG = LoggerFactory.getLogger(RechargeShoutService.class);

	@Autowired
	RelationshipService relationshipService;

	@Autowired
	UserService userService;

	@Autowired
	UserPackService userPackService;

	@Autowired
	CoinService coinService;

	@Autowired
	MessageService messageService;

	public ReMsg adminShout(long uid, String content, Integer all, Integer reply, Integer combo, Integer vip) {
		DBObject user = userService.findById(uid);
		int hitCount = 0;
		for (int i = 0; i < combo; i++) {
			long now = System.currentTimeMillis();
			Shout st = new Shout();
			st.set_id(super.getNextId(DocName.SHOUT));
			st.setUid(uid);
			st.setContent(content == null ? "" : content);
			st.setAll(all == 1 ? true : false);
			st.setReply(reply);
			st.setNickname(DboUtil.getString(user, "nickname"));
			st.setSex(DboUtil.getInt(user, "sex"));
			st.setAvatar(userService.toMaxAvatar(user));
			if (i == 0) {
				BasicDBObject q = new BasicDBObject();
				q.append("uid", uid).append("createTime", new BasicDBObject("$gte", now - Const.HOUR));
				List<DBObject> list = super.getC(DocName.SHOUT).find(q).sort(new BasicDBObject("hitCount", -1))
						.toArray();
				hitCount = !list.isEmpty() ? DboUtil.getInt(list.get(0), "hitCount") : 0;
			}
			st.setHitCount(hitCount + 1 + i);
			st.setPic(null);
			st.setCreateTime(now);
			super.getMongoTemplate().save(st);
			if (null != vip && vip == 1) {
				userService.changeVip(uid, 38800, super.getUid());
			}
			userService.saveTitle(uid, 388, UserTitle.COIN);
			// 喊话附加奖励
			userService.changePoint(uid, Point.VIP_PAY.getType(), 16666, 0L);
			userPackService.addGoods(uid, BaseGoods.Gift.FLOWER.getV(), 1888, GiftForm.ORDER, uid);
			coinService.addCoin(uid, CoinLog.IN, uid, 194000, 0, "喊话加金币");
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg update(Long id, String content, Integer all) {
		DBObject u = new BasicDBObject("$set",
				new BasicDBObject("all", all == 1 ? true : false).append("content", content));
		getC(DocName.SHOUT).update(new BasicDBObject("_id", id), u);
		return new ReMsg(ReCode.OK);
	}

	public ReMsg shout(long uid, String content, int all, int reply) {
		DBObject user = userService.findById(uid);
		Map<String, Object> map = new HashMap<>();
		map.put("uid", uid);
		map.put("content", content == null ? "" : content);
		map.put("all", all);
		map.put("reply", reply);
		map.put("nickname", DboUtil.getString(user, "nickname"));
		map.put("sex", DboUtil.getString(user, "sex"));
		map.put("avatar", userService.toMaxAvatar(user));
		String json = JSONUtil.beanToJson(map);
		getRedisTemplate().opsForValue().set(RK.worldShout(uid), json, 30, TimeUnit.MINUTES);
		return new ReMsg(ReCode.OK);
	}

	public void realShout(long uid, int count) {
		realShout(uid, count, 1);
	}

	public void realShout(long uid, int count, int combo) {
		int hitCount = 0;
		for (int i = 0; i < combo; i++) {
			Shout st = new Shout();
			st.set_id(super.getNextId(DocName.SHOUT));
			st.setUid(uid);
			String json = getRedisTemplate().opsForValue().get(RK.worldShout(uid));
			if (json != null) {
				Map<String, Object> map = JSONUtil.jsonToMap(json);
				st.setContent((String) (map.get("content")));
				st.setAll((int) (map.get("all")) == 1 ? true : false);
				st.setReply((int) (map.get("reply")));
				st.setNickname((String) (map.get("nickname")));
				st.setSex(MapUtil.getInt(map, "sex"));
				st.setAvatar(MapUtil.getStr(map, "avatar"));
			}
			long now = System.currentTimeMillis();
			if (i == 0) {
				BasicDBObject q = new BasicDBObject();
				q.append("uid", uid).append("createTime", new BasicDBObject("$gte", now - Const.HOUR));
				List<DBObject> list = super.getC(DocName.SHOUT).find(q).sort(new BasicDBObject("hitCount", -1))
						.toArray();
				hitCount = !list.isEmpty() ? DboUtil.getInt(list.get(0), "hitCount") : 0;
			}
			st.setHitCount(hitCount + 1 + i);
			st.setPic(null);
			st.setCreateTime(now);
			st.setLevel(count);
			super.getMongoTemplate().save(st);
		}
		getRedisTemplate().delete(RK.worldShout(uid));
	}

	public Page<DBObject> queryShout(Long uid, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		BasicDBObject q = new BasicDBObject();
		q.append("all", true);
		List<DBObject> dbos = super.getC(DocName.SHOUT).find(q).sort(new BasicDBObject("_id", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
		dbos.stream().forEach(e -> {
			long sid = DboUtil.getLong(e, "_id");
			List<DBObject> cdbos = findCommentShout(sid, 6, 1, false);
			e.put("comments", cdbos);
			e.put("commentCnt", findCommentCount(sid));
		});
		int count = super.getC(DocName.SHOUT).find(q).count();
		return new Page<DBObject>(count, size, page, dbos);
	}

	public Page<DBObject> adminQueryShout(Long uid, Integer page, Integer size, Integer all, Integer reply) {
		size = getSize(size);
		page = getPage(page);
		BasicDBObject q = new BasicDBObject();
		if (null != uid) {
			q.append("uid", uid);
		}
		if (null != all) {
			q.append("all", all == 1 ? true : false);
		}
		if (null != reply) {
			q.append("reply", reply);
		}
		List<DBObject> dbos = super.getC(DocName.SHOUT).find(q).sort(new BasicDBObject("_id", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
		int count = super.getC(DocName.SHOUT).find(q).count();
		return new Page<DBObject>(count, size, page, dbos);
	}

	private int findCommentCount(long sid) {
		return getC(DocName.SHOUTCOMMENT).find(new BasicDBObject("sid", sid)).count();
	}

	public DBObject findById(long sid) {
		return super.getC(DocName.SHOUT).findOne(new BasicDBObject("_id", sid));
	}

	public ReMsg commentShout(long sid, long uid, String content, Long baseId) {
		DBObject sdbo = this.findById(sid);
		Long suid = null;
		if (null == sdbo) {
			return new ReMsg(ReCode.FAIL);
		} else {
			suid = DboUtil.getLong(sdbo, "uid");
			Integer reply = DboUtil.getInt(sdbo, "reply");
			if (Shout.CANNOTCOM == reply) {
				return new ReMsg(ReCode.CANNOT_COMMENT);
			} else if (Shout.FRICOM == reply) {
				int rel = relationshipService.getRelation(uid, suid);
				if (rel != Relationship.FRIENDS) {
					return new ReMsg(ReCode.FAIL);
				}
			}
		}
		ShoutComment sc = null;
		// 喊话内容
		String shotContent = DboUtil.getString(sdbo, "content");
		if (StringUtils.isNotBlank(shotContent)) {
			if (shotContent.length() > 10) {
				shotContent = shotContent.substring(0, 10) + "...";
			}
		}
		if (null == baseId) {
			sc = new ShoutComment(super.getNextId(DocName.SHOUTCOMMENT), sid, content, uid,
					(String) super.getUser("nickname"));
		} else {
			DBObject dbo = getCommentShout(baseId);
			sc = new ShoutComment(super.getNextId(DocName.SHOUTCOMMENT), sid, content, uid,
					(String) super.getUser("nickname"), baseId, DboUtil.getLong(dbo, "userId"),
					DboUtil.getString(dbo, "nickname"));
			// 推送给被喊话主人回复评论的人
			MapBody mb = new MapBody("op", OperationType.NULL.getOp()).append("opId", 0).append("content",
					DboUtil.getString(userService.findById(uid), "nickname") + "回复了你在喊话[" + shotContent
							+ "]中的评论,快去看看吧~");
			Msg msg = new Msg(super.incrMsgId(), MsgType.NOTICE, 0, DboUtil.getLong(dbo, "userId"),
					System.currentTimeMillis(), mb.getData());
			messageService.msgHandler(msg);
		}
		if (uid != suid) {// 不是话主自己的评论 推给话主 喊话被评论
			MapBody mb = new MapBody("op", OperationType.NULL.getOp()).append("opId", 0).append("content",
					DboUtil.getString(userService.findById(uid), "nickname") + "评论了你的喊话:[" + shotContent + "],快去看看吧~");
			Msg msg = new Msg(super.incrMsgId(), MsgType.NOTICE, 0, suid, System.currentTimeMillis(), mb.getData());
			messageService.msgHandler(msg);
		}

		super.getMongoTemplate().save(sc);
		return new ReMsg(ReCode.OK);
	}

	public ReMsg queryCommentShout(long sid, Integer size, Integer page, boolean isAdmin) {
		int count = countCommentShout(sid);
		List<DBObject> list = findCommentShout(sid, size, page, isAdmin);
		return new ReMsg(ReCode.OK, size, count, list);
	}

	public List<DBObject> findCommentShout(long sid, Integer size, Integer page, boolean isAdmin) {
		BasicDBObject q = new BasicDBObject("sid", sid);
		List<DBObject> coms = super.getC(DocName.SHOUTCOMMENT).find(q).limit(super.getSize(size))
				.skip(getStart(page, size)).sort(new BasicDBObject("_id", 1)).toArray();
		if (isAdmin) {
			return coms;
		} else {
			List<DBObject> res = new ArrayList<DBObject>();
			for (DBObject dbo : coms) {
				DBObject user = userService.findById(DboUtil.getLong(dbo, "userId"));
				if (user != null && DboUtil.getInt(user, "status") == 1) {
					res.add(dbo);
				}
			}
			return res;
		}
	}

	public int countCommentShout(long sid) {
		BasicDBObject q = new BasicDBObject("sid", sid);
		return super.getC(DocName.SHOUTCOMMENT).find(q).count();
	}

	public DBObject getCommentShout(long id) {
		BasicDBObject q = new BasicDBObject("_id", id);
		return super.getC(DocName.SHOUTCOMMENT).findOne(q);
	}

	enum ShoutPic {
		// _128(128, "不屈白银框.jpg"), _298(298, "至尊大金框.jpg"),
		_388(388, "炫酷钻石框.jpg"),;
		ShoutPic(int count, String pic) {
			this.count = count;
			this.pic = pic;
		}

		private int count;
		private String pic;

		public int getCount() {
			return count;
		}

		public String getPic() {
			return pic;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public void setPic(String pic) {
			this.pic = pic;
		}

		public static String searchPic(int count) {
			for (ShoutPic e : EnumSet.allOf(ShoutPic.class)) {
				if (e.getCount() == count) {
					return e.getPic();
				}
			}
			return null;
		}

	}

}
