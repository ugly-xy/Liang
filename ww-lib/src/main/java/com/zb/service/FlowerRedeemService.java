package com.zb.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.FlowerRedeem;
import com.zb.common.Constant.ReCode;
import com.zb.common.http.CookieUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.finance.CoinLog;
import com.zb.models.goods.BaseGoods;
import com.zb.service.pay.CoinService;

@Service
public class FlowerRedeemService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(FlowerRedeemService.class);

	@Autowired
	GoodsService goodsService;
	@Autowired
	UserService userService;
	@Autowired
	MessageService messageService;
	@Autowired
	CoinService coinService;

	private static int REWARD_YES = 2;
	private static int REWARD_NO = 1;

	private static String TOKEN = "token";

	// 鲜花兑换金币
	public ReMsg redeemCoin(HttpServletRequest req, Integer count) {
		int type = BaseGoods.Gift.FLOWER.getV().getId();
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		if (null == count || count < 100) {
			return new ReMsg(ReCode.FAIL, "兑换花数需要达到100朵才能兑换哦～");
		}
		if (resCount(uid) < count) {
			return new ReMsg(ReCode.FAIL, "您兑换的花数超出您现有的额度啦!");
		}
		DBObject dbo = findById(uid + "-" + type);
		if (null != dbo) {
			// 更新
			dbo.put("count", DboUtil.getLong(dbo, "count") + count);
			dbo.put("updateTime", System.currentTimeMillis());
			super.getMongoTemplate().getCollection(DocName.FLOWER_REDEEM).save(dbo);
		} else {
			// 存储记录
			super.getMongoTemplate().getCollection(DocName.FLOWER_REDEEM)
					.save(new BasicDBObject("_id", uid + "-" + type).append("uid", uid).append("type", type)
							.append("key", type + "").append("status", REWARD_YES).append("count", count)
							.append("createTime", System.currentTimeMillis())
							.append("updateTime", System.currentTimeMillis()));
		}
		// 添加金币
		coinService.addCoin(uid, CoinLog.REDEEM_COIN, type, count / 2, 0, "花费" + count + "鲜花兑换" + (count / 2) + "金币");
		// 发送消息
		messageService.imMsgHandler(Const.SYSTEM_ID, uid, "您成功兑换" + (count / 2) + "金币,本次消耗鲜花额度:" + count + "朵", null,
				null);
		// messageService.easeMsgHandler(Const.SYSTEM_ID,uid , "您成功兑换" + count +
		// "金币,本次消耗鲜花额度:" + count + "朵", null, null);
		return new ReMsg(ReCode.OK);
	}

	// 兑换 兑奖码
	public ReMsg getRedeemKey(Integer type, HttpServletRequest req) {
		String token = CookieUtil.getCookieValue(TOKEN, req);
		if (null == token) {
			return new ReMsg(ReCode.FAIL, "您还没有登录,请先登录");
		}
		// 根据token拿到uid
		long uid = super.getUid(token);
		if (uid < 1) {
			return new ReMsg(ReCode.FAIL, "您还没有登录,请先登录");
		}
		FlowerRedeem redeem = FlowerRedeem.getRedeem(type);
		if (null == redeem) {
			return new ReMsg(ReCode.FAIL, "商品信息错误,兑换失败");
		}
		if (countRedeemLog(uid, type, null) > 0) {
			// 已经兑过这个奖品了
			return new ReMsg(ReCode.FAIL, "您已经兑换过这个奖品了,看看别的吧");
		}
		// 可用额度 如果小于对应需要的花数 返回数量不足
		if (resCount(uid) < redeem.getPrice()) {
			return new ReMsg(ReCode.FAIL, "兑换失败,您的可用鲜花兑换额度不足");
		}
		// 全部验证通过 生成一个随机的兑换码
		String key = getCDKEY();
		// 把兑换存储到数据库 状态设置为未兑奖
		super.getMongoTemplate().getCollection(DocName.FLOWER_REDEEM)
				.save(new BasicDBObject("_id", uid + "-" + type).append("uid", uid).append("type", type)
						.append("key", key).append("status", REWARD_NO).append("count", redeem.getPrice())
						.append("createTime", System.currentTimeMillis()).append("updateTime", 0));
		// 发送消息
		messageService.imMsgHandler(Const.SYSTEM_ID, uid, "恭喜您成功兑换" + redeem.getName() + ",本次消耗鲜花数:"
				+ redeem.getPrice() + ",兑换码:" + key + ",请联系领奖QQ：1260110110或微信公众号：zhuangdianbi", null, null);
//		messageService.easeMsgHandler(Const.SYSTEM_ID, uid, "恭喜您成功兑换" + redeem.getName() + ",本次消耗鲜花数:"
//				+ redeem.getPrice() + ",兑换码:" + key + ",请联系领奖QQ：1260110110或微信公众号：zhuangdianbi", null, null);
		return new ReMsg(ReCode.OK, key);
	}

	// 拿兑换码兑奖
	public ReMsg redeemReward(String key) {
		// 根据兑换码状态 和兑换码进行findandModify操作 并取到返回值
		DBObject u = new BasicDBObject("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()).append("status", REWARD_YES));
		DBObject dbo = super.getMongoTemplate().getCollection(DocName.FLOWER_REDEEM)
				.findAndModify(new BasicDBObject("key", key).append("status", REWARD_NO), u);
		// 如果返回的数据为空 返回 该兑换码无效
		if (null == dbo) {
			return new ReMsg(ReCode.FAIL);
		}
		String goodsName = FlowerRedeem.getRedeem(DboUtil.getInt(dbo, "type")).getName();
		messageService.imMsgHandler(Const.SYSTEM_ID,DboUtil.getLong(dbo, "uid"), "您的奖品" + goodsName + "兑换成功了哦", null,
				null);
//		messageService.easeMsgHandler(Const.SYSTEM_ID,DboUtil.getLong(dbo, "uid"), "您的奖品" + goodsName + "兑换成功了哦", null,
//				null);
		return new ReMsg(ReCode.OK);
	}

	// 查询用户兑奖码记录
	public Page<DBObject> queryRedeemLog(Long uid, Integer type, String key, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findRedeemLog(uid, type, key, page, size);
		int count = this.countRedeemLog(uid, type, key);
		return new Page<DBObject>(count, size, page, dbos);
	}

	// 查询用户额度
	public long queryRedeem(HttpServletRequest req) {
		String token = CookieUtil.getCookieValue(TOKEN, req);
		if (null != token) {
			Long uid = super.getUid(token);
			if (uid > 0) {
				return resCount(uid);
			}
		}
		return 0;
	}

	// 可用额度
	public long resCount(long uid) {
		// 总收花数
		long flower = goodsService.queryRecvGiftCnt(uid, BaseGoods.Gift.FLOWER.getV().getId());
		return flower - findRedeemCount(uid);
	}

	// 可用额度
	public long resCount(long uid, long flower) {
		return flower - findRedeemCount(uid);
	}

	// 查询累计兑换额度
	private int findRedeemCount(long uid) {
		List<BasicDBObject> pipeline = new ArrayList<BasicDBObject>();
		BasicDBObject q = new BasicDBObject("uid", uid);
		pipeline.add(new BasicDBObject("$match", q));
		pipeline.add(new BasicDBObject("$group",
				new BasicDBObject("_id", "$uid").append("sum", new BasicDBObject("$sum", "$count"))));
		Iterable<DBObject> dbos = getC(DocName.FLOWER_REDEEM).aggregate(pipeline).results();
		for (Iterator<DBObject> it = dbos.iterator(); it.hasNext();) {
			DBObject cur = it.next();
			return DboUtil.getInt(cur, "sum");
		}
		return 0;
	}

	private DBObject findById(String id) {
		return super.getC(DocName.FLOWER_REDEEM).findOne(new BasicDBObject("_id", id));
	}

	// 查询用户的活动兑换信息 总额度 可用额度
	public Page<DBObject> queryUserRedeem(Long uid, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		DBObject q = new BasicDBObject("bgId", BaseGoods.Gift.FLOWER.getV().getId()).append("type", 0);
		if (null != uid && uid > 0) {
			q.put("uid", uid);
		}
		List<DBObject> list = super.getMongoTemplate().getCollection(DocName.GIFT_RECV).find(q)
				.skip(super.getStart(page, size)).limit(getSize(size)).sort(new BasicDBObject("count", -1)).toArray();
		int count = getC(DocName.GIFT_RECV).find(q).count();
		list = addUserInfo(list);
		for (DBObject dbo : list) {
			long userId = DboUtil.getLong(dbo, "uid");
			// 查询该用户的总额度
			long flower = goodsService.queryRecvGiftCnt(userId, BaseGoods.Gift.FLOWER.getV().getId());
			// 查询余额
			long resCount = resCount(userId, flower);
			dbo.put("flower", flower);
			dbo.put("resCount", resCount);
			if (flower != resCount) {
				dbo.put("redeem", REWARD_YES);
			} else {
				dbo.put("redeem", REWARD_NO);
			}
		}
		return new Page<DBObject>(count, size, page, list);
	}

	public List<DBObject> findRedeemLog(Long uid, Integer type, String key, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}
		if (null != type && type != 0) {
			q.put("type", type);
		}
		if (StringUtils.isNotBlank(key)) {
			q.put("key", key);
		}
		List<DBObject> list = getC(DocName.FLOWER_REDEEM).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
		return addUserInfo(list);
	}

	// 添加用户信息
	public List<DBObject> addUserInfo(List<DBObject> list) {
		if (null == list || list.size() == 0) {
			return list;
		}
		List<DBObject> rs = new ArrayList<DBObject>();
		for (DBObject dbo : list) {
			DBObject user = userService.findById(DboUtil.getLong(dbo, "uid"));
			dbo.put("nickname", DboUtil.getString(user, "nickname"));
			dbo.put("avatar", DboUtil.getString(user, "avatar"));
			rs.add(dbo);
		}
		return rs;
	}

	public int countRedeemLog(Long uid, Integer type, String key) {
		DBObject q = new BasicDBObject();
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}
		if (null != type && type != 0) {
			q.put("type", type);
		}
		if (StringUtils.isNotBlank(key)) {
			q.put("key", key);
		}
		return getC(DocName.FLOWER_REDEEM).find(q).count();
	}

	public String getCDKEY() {
		String key = getKEY();
		int count = countRedeemLog(null, null, key);
		if (count != 0) {
			getCDKEY();
		}
		return key;
	}

	// 生成兑换码
	private String getKEY() {
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		// str += str.toLowerCase();
		str += "0123456789";
		StringBuffer sb = new StringBuffer(6);
		for (int i = 0; i < 10; i++) {
			char ch2 = str.charAt(new Random().nextInt(str.length()));
			sb.append(ch2);
		}
		return sb.toString();
	}

	// public static void main(String[] args){
	// String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	// str += "0123456789";
	// StringBuffer sb = new StringBuffer(6);
	// for (int i = 0; i < 10; i++) {
	// char ch2 = str.charAt(new Random().nextInt(str.length()));
	// sb.append(ch2);
	// }
	// System.out.println(sb.toString());
	// }
}
