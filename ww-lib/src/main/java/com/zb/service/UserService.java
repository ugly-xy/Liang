package com.zb.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gexin.rp.sdk.base.uitls.MD5Util;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.GiftForm;
import com.zb.common.Constant.IMType;
import com.zb.common.Constant.OperationType;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.ReCode;
import com.zb.common.Constant.Role;
import com.zb.common.crypto.MDUtil;
import com.zb.common.http.HttpClientUtil;
import com.zb.common.http.HttpsClientUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.AddressComponent;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.InterestsUtil;
import com.zb.common.utils.LbsUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.PointLog;
import com.zb.models.TitleModel;
import com.zb.models.UpdatePwdLog;
import com.zb.models.User;
import com.zb.models.UserTitle;
import com.zb.models.VipMap;
import com.zb.models.goods.BaseGoods;
import com.zb.models.goods.BaseGoods.Gift;
import com.zb.models.user.Relationship;
import com.zb.models.user.UserTag;
import com.zb.models.usertask.Task;
import com.zb.service.article.ArticleService;
import com.zb.service.cloud.StorageService;
import com.zb.service.im.AliIMService;
import com.zb.service.im.EasemobService;
import com.zb.service.jobs.RankListJob;
import com.zb.service.othergames.StarTrekService;
import com.zb.service.pay.CoinService;
import com.zb.service.usertask.UserTaskService;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.Msg;
import com.zb.socket.model.MsgType;

@Service
public class UserService extends BaseService {
	@Autowired
	RelationshipService relationshipService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	MessageService messageService;
	@Autowired
	UserTaskService userTaskService;
	@Autowired
	UserPackService userPackService;
	@Autowired
	FlowerRedeemService redeemService;
	@Autowired
	StarTrekService starTrekService;
	@Autowired
	EasemobService easemobService;
	@Autowired
	EnvService envService;
	@Autowired
	AliIMService aliIMService;
	@Autowired
	CoinService coinService;
	@Autowired
	StorageService storageService;
	@Autowired
	UserTagService userTagService;

	@Autowired
	ArticleService articleService;

	static final Logger log = LoggerFactory.getLogger(UserService.class);

	public static int[] WORTHS = { 1, 5, 20, 50, 100, 500, 1000 };

	public DBObject findByUsername(String username) {
		return getC(DocName.USER).findOne(new BasicDBObject("username", username));
	}

	public DBObject findByPhone(String phone) {
		return getC(DocName.USER).findOne(new BasicDBObject("phone", phone));

	}

	public ReMsg findMyCoin() {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject user = findById(uid);
		if (null != user) {
			return new ReMsg(ReCode.OK, DboUtil.getInt(user, "coin"));
		} else {
			return new ReMsg(ReCode.USER_ID_ERR);
		}
	}

	public ReMsg getMyInfo(HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject user = findById(uid);
		if (null != user) {
			super.getRedisTemplate().expire(RK.accessToken(super.getToken(req)), 10, TimeUnit.DAYS);
			// 设置封面
			if (StringUtils.isBlank(DboUtil.getString(user, "cover"))) {
				user.put("cover", DboUtil.getString(user, "avatar"));
			}

			setPhotos(user);

			// if (DboUtil.getString(user, "interests") == null) {
			user.put("interests", new String[0]);
			// }
			if (DboUtil.getString(user, "personLabel") == null) {
				user.put("personLabel", "");
			}
			// 好友数量
			user.put("friends", relationshipService.count(uid, null, Relationship.FRIENDS, null));
			// 自己拥有的鲜花数
			user.put("myFlower",
					userPackService.queryPropCount(Gift.FLOWER.getV().getType(), Gift.FLOWER.getV().getId(), uid));
			// 收花数量
			long flower = goodsService.queryRecvGiftCnt(uid, BaseGoods.Gift.FLOWER.getV().getId());
			user.put("flower", flower);
			// 可用兑换额度
			user.put("resCount", redeemService.resCount(uid, flower));
			// 称号
			user.put("title", queryTitle(uid));
			UserTag ut = userTagService.getUserTag(uid);
			if (ut != null) {
				user.put("tags", ut.getTags());
			}

			return new ReMsg(ReCode.OK, user);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg getUserInfo(Long uid) {
		DBObject user = findById(uid);
		if (null != user) {
			user = removeUserInfo(user);
			// 设置封面
			if (StringUtils.isBlank(DboUtil.getString(user, "cover"))) {
				user.put("cover", DboUtil.getString(user, "avatar"));
			}
			setPhotos(user);
			// 设置兴趣标签
			if (DboUtil.getString(user, "interests") == null) {
				user.put("interests", new String[0]);
			}
			// 设置签名
			if (DboUtil.getString(user, "personLabel") == null) {
				user.put("personLabel", "");
			}

			// 用户好友添加身价
			if (DboUtil.getInt(user, "friendWorth") == null) {
				user.put("friendWorth", WORTHS[0]);
			}
			// 收花数量
			user.put("flower", goodsService.queryRecvGiftCnt(uid, BaseGoods.Gift.FLOWER.getV().getId()));

			UserTag ut = userTagService.getUserTag(uid);
			if (ut != null) {
				user.put("tags", ut.getTags());
			}

			// 称号
			user.put("title", queryTitle(uid));

			return new ReMsg(ReCode.OK, user);
		}
		return new ReMsg(ReCode.FAIL);
	}

	private void setPhotos(DBObject user) {
		Object pObj = user.get("photos");
		if (pObj == null) {
			user.put("photos", new String[] { toMaxAvatar(user) });
		} else {
			// 判断相册第一张是否是头像
			String avatar = this.toMaxAvatar(user);
			@SuppressWarnings("unchecked")
			List<String> photos = (List<String>) pObj;
			boolean insert = true;
			for (String ph : photos) {
				if (avatar.contains(ph)) {
					insert = false;
					break;
				}
			}
			if (insert) {
				photos.add(0, avatar);
				user.put("photos", photos);
			}
		}
	}

	private DBObject removeUserInfo(DBObject user) {
		user = removeFile(user, new String[] { "pwd", "ch", "childCh", "regIp", "openId", "phone", "email", "unionId",
				"emailVeri", "phoneVeri", "lbs", "_class" });
		return user;
	}

	public DBObject findUserByOpenId(Integer type, String openId) {
		DBObject user = getC(DocName.USER).findOne(new BasicDBObject("thirdType", type).append("openId", openId));
		if (null != user) {
			return removeUserInfo(user);
		}

		return null;
	}

	public DBObject findUserByUnionId(Integer type, String unionId) {
		DBObject user = getC(DocName.USER).findOne(new BasicDBObject("thirdType", type).append("unionId", unionId));
		if (null != user) {
			return removeUserInfo(user);
		}

		return null;
	}

	/**
	 * 获取用户全部信息，带有密码之类敏感信息，最好使用findByIdSafe接口
	 * 
	 * @param id
	 * @return
	 */
	@Deprecated
	public DBObject findById(Long id) {
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String s = opsv.get(RK.keyUser(id));
		if (null == s) {
			DBObject user = getC(DocName.USER).findOne(new BasicDBObject("_id", id));
			if (null != user) {
				user.put("avatar", toMinAvatar(user));
				opsv.set(RK.keyUser(id), JSON.serialize(user), 1, TimeUnit.HOURS);
			}
			return user;
		} else {
			return (DBObject) JSON.parse(s);
		}
	}

	/**
	 * 根据id获取用户信息，无密码之类敏感信息
	 * 
	 * @param id
	 * @return
	 */
	public DBObject findByIdSafe(Long id) {
		DBObject dbo = findById(id);
		return removeUserInfo(dbo);
	}

	public String toMinAvatar(DBObject user) {
		String avatar = DboUtil.getString(user, "avatar");
		if (avatar == null) {
			avatar = User.DEFAULT_AVATAR;
		} else if (avatar.startsWith("http://zim.zhuangdianbi.com")) {
			if (avatar.contains("@")) {
				avatar = avatar.substring(0, avatar.indexOf("@")) + "@!bi1";
			} else {
				avatar = avatar + "@!bi1";
			}
		} else if (avatar.startsWith("http://wx.qlogo.cn/mmopen/")) {
			avatar = replaceSuffix(avatar, "/0", "/132");
		} else if (avatar.startsWith("http://q.qlogo.cn/qqapp/")) {
			avatar = replaceSuffix(avatar, "/640", "/100");
		}
		return avatar;
	}

	public void downloadAvatar(Long uid, String avatar) {
		new Thread(new Runnable() {
			String avataar = avatar;

			@Override
			public void run() {
				avataar = replaceSuffix(avataar, "/100", "/640");
				HttpEntity he;
				try {
					he = HttpsClientUtil.downloadEntity(avataar, null);
					ReMsg r = storageService.uploadZim(he);
					if (r.getCode() == ReCode.OK.reCode()) {
						String url = r.getData().toString();
						update(uid, new BasicDBObject("avatar", url));
					}
				} catch (IOException | URISyntaxException e) {
					log.error("getPicError", e);
				}
			}
		}) {
		}.start();

	}

	public String toMaxAvatar(DBObject user) {
		String avatar = DboUtil.getString(user, "avatar");
		if (avatar == null) {
			avatar = User.DEFAULT_AVATAR;
		} else if (avatar.startsWith("http://zim.zhuangdianbi.com")) {
			if (avatar.contains("@")) {
				avatar = avatar.substring(0, avatar.indexOf("@")) + "@!bi7";
			} else {
				avatar = avatar + "@!bi7";
			}
		} else if (avatar.startsWith("http://wx.qlogo.cn/mmopen/")) {
			avatar = replaceSuffix(avatar, "/132", "/0");
		} else if (avatar.startsWith("http://q.qlogo.cn/qqapp/")) {
			avatar = replaceSuffix(avatar, "/100", "/640");
		}
		return avatar;
	}

	private String replaceSuffix(String avatar, String suffix, String replace) {
		if (avatar.endsWith(suffix)) {
			return avatar.substring(0, avatar.indexOf(suffix)) + replace;
		}
		return avatar;
	}

	public Map<Long, DBObject> findByIds(Long[] ids) {
		Map<Long, DBObject> map = new HashMap<Long, DBObject>();
		for (Long id : ids) {
			map.put(id, this.findById(id));
		}
		return map;
	}

	// 最近联系人消息扩充
	public List<DBObject> findByids(Long[] ids) {
		List<DBObject> dbos = new ArrayList<DBObject>();
		for (Long id : ids) {
			if (Const.SYSTEM_ID - id == 0) {
				continue;
			}
			DBObject dbo = this.findById(id);
			if (null == dbo) {
				continue;
			}
			if (null == DboUtil.getString(dbo, "nickname")) {
				dbo.put("nickname", id);
			}
			if (null == DboUtil.getString(dbo, "personLabel")) {
				dbo.put("personLabel", "");
			}
			if (null == DboUtil.getInt(dbo, "vip")) {
				dbo.put("vip", 0);
			}
			dbos.add(removeUserInfo(dbo));
		}
		return dbos;
	}

	public ReMsg checkNickname(String nickname, long uid) {
		DBObject dbo = getC(DocName.USER).findOne(new BasicDBObject("nickname", nickname));
		if (dbo != null) {
			if (uid > 0 && uid == DboUtil.getLong(dbo, "_id")) {
				return new ReMsg(ReCode.OK);
			}
			return new ReMsg(ReCode.NICKNAME_EXISTS);
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg updateUser(Integer sex, String nickname, String avatar) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}

		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		if (StringUtils.isNotBlank(nickname)) {
			ReMsg rm = checkNickname(nickname, uid);
			if (ReCode.OK.reCode() != rm.getCode()) {
				return rm;
			}
			u.put("nickname", nickname);
		}
		if (sex != null && sex != 0) {
			u.put("sex", sex == 1 ? 1 : 2);
		}
		if (StringUtils.isNotBlank(avatar)) {
			u.put("avatar", avatar);
		}
		return update(uid, u);
	}

	public ReMsg updateUser(Long uid, String openId, String unionId, Integer thirdType) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		if (StringUtils.isNotBlank(openId)) {
			u.put("openId", openId);
		}
		if (StringUtils.isNotBlank(unionId)) {
			u.put("unionId", unionId);
		}
		if (null != thirdType) {
			u.put("thirdType", thirdType);
		}
		return update(uid, u);
	}

	public ReMsg updateUserLbs(String lbs, HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		BasicDBObject u = new BasicDBObject();
		AddressComponent ac = null;
		try {
			if (StringUtils.isNotBlank(lbs)) {
				u.put("lbs", lbs);
				ac = LbsUtil.getLocationLbs(lbs);
			} else {
				ac = LbsUtil.getIpLbs(req);
			}
			if (ac != null) {
				u.append("province", LbsUtil.getFmtProvince(ac));
				u.append("city", LbsUtil.getFmtCity(ac));
				update(uid, u);
			}
		} catch (Exception e) {
			log.error("用户更新城市失败");
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg updateUser(String nickname, Integer sex, String interests, String personLabel, String avatar,
			String cover, String photos, String phone) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		if (StringUtils.isNotBlank(nickname)) {
			ReMsg rm = checkNickname(nickname, uid);
			if (ReCode.OK.reCode() != rm.getCode()) {
				return rm;
			}
			u.put("nickname", nickname);
		}
		if (sex != null && sex != 0) {
			u.put("sex", sex == 1 ? 1 : 2);
		}
		if (null != interests) {
			if (!interests.contains("=")) {
				u.put("interests", new String[0]);
			} else {
				String[] split = interests.split(",");
				u.put("interests", split);
			}

		}
		if (null != personLabel) {
			DBObject user = findById(uid);
			if (null == user || null == DboUtil.getBool(user, "modifyLabel") || !DboUtil.getBool(user, "modifyLabel")) {
				return new ReMsg(ReCode.MODIFY_LABEL_ERR);
			}
			if (personLabel.length() > 100) {
				personLabel = personLabel.substring(0, 100);
			}
			u.put("personLabel", personLabel);
		}

		if (StringUtils.isNotBlank(avatar)) {
			u.put("avatar", avatar);
		}
		if (StringUtils.isNotBlank(cover)) {
			u.put("cover", cover);
		}
		if (StringUtils.isNotBlank(photos)) {
			String[] split = photos.split(",");
			Set<String> ps = new HashSet<String>();
			List<String> arrList = new ArrayList<String>();
			for (String p : split) {
				if (StringUtils.isNotBlank(p)) {
					p = p.toLowerCase();
					if (p.startsWith("http")) {
						if (!ps.contains(p)) {
							arrList.add(p);
							ps.add(p);
						}
					}
				}
			}
			u.put("photos", arrList);
			userTaskService.doUserTask(uid, Task.UPLOADING_COVER, 1);
		}
		if (StringUtils.isNotBlank(phone)) {
			u.put("phone", phone);
		}
		return update(uid, u);
	}

	public ReMsg updateUser(Long uid, Integer sex, String nickname, String avatar) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		if (StringUtils.isNotBlank(nickname)) {
			ReMsg rm = checkNickname(nickname, uid);
			if (ReCode.OK.reCode() != rm.getCode()) {
				return rm;
			}
			u.put("nickname", nickname);
		}
		if (sex != null && sex != 0) {
			u.put("sex", sex == 1 ? 1 : 2);
		}
		if (StringUtils.isNotBlank(avatar)) {
			u.put("avatar", avatar);
		}
		return update(uid, u);
	}

	public ReMsg changeFriendWorth(Integer worth) {
		Long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return changeFriendWorth(worth, uid);
	}

	public ReMsg changeFriendWorth(Integer worth, Long uid) {
		boolean isOk = false;
		for (int i : WORTHS) {
			if (i == worth) {
				isOk = true;
				break;
			}
		}
		if (isOk) {
			DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
			u.put("friendWorth", worth);
			return update(uid, u);
		} else {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
	}

	public ReMsg setStatus(Long uid, Integer status) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		u.put("status", status);
		return update(uid, u);
	}

	public ReMsg setRole(Long uid, Integer role) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		u.put("role", role);
		return update(uid, u);
	}

	public ReMsg setInfo(Long uid, Integer role, Integer status, String phone, Integer sex, String[] photos,
			String personLabel) {
		DBObject dbo = findByPhone(phone);
		String curPhone = DboUtil.getString(dbo, "phone");
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		u.put("photos", photos);
		if (null != photos) {
			u.put("avatar", photos[0]);
		}
		u.put("personLabel", personLabel);
		if (role != null) {
			u.put("role", role);
		}
		if (status != null) {
			u.put("status", status);
		}
		if (curPhone == null && phone != null) {
			u.put("phone", phone);
			u.put("phoneVeri", true);
		} else if (curPhone != null && !curPhone.equals(phone)) {
			u.put("phone", phone);
			u.put("phoneVeri", true);
		}
		if (sex != null) {
			u.put("sex", sex);
		}
		return update(uid, u);
	}

	public ReMsg update(Long uid, DBObject u) {
		getC(DocName.USER).update(new BasicDBObject("_id", uid), new BasicDBObject("$set", u));
		super.getRedisTemplate().delete(RK.keyUser(uid));
		// FIXME
		aliIMService.update(uid, u);
		return new ReMsg(ReCode.OK);
	}

	public void updateAli(Long uid, DBObject u) {
		getC(DocName.USER).update(new BasicDBObject("_id", uid), new BasicDBObject("$set", u));
	}

	public ReMsg update(DBObject q, DBObject u) {
		getC(DocName.USER).update(q, new BasicDBObject("$set", u), false, true);
		return new ReMsg(ReCode.OK);
	}

	public ReMsg updateModifyLabel(long uid, Boolean modifyLabel) {
		return update(uid, new BasicDBObject("modifyLabel", modifyLabel));
	}

	public ReMsg updatePwd(String oldPwd, String newPwd, HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject user = this.findById(uid);
		if (null != user) {
			if (MDUtil.SHA.digest2HEX(oldPwd).equals(user.get("pwd").toString())) {
				DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
				u.put("pwd", MDUtil.SHA.digest2HEX(newPwd));
				if (getC(DocName.USER).update(new BasicDBObject("_id", uid), new BasicDBObject("$set", u), false, false,
						WriteConcern.ACKNOWLEDGED).getN() > 0) {
					saveChangePwdLog(req, uid, Const.STATUS_OK, 1, 0l);
					super.getRedisTemplate().delete(RK.keyUser(uid));
					return new ReMsg(ReCode.OK);
				}
			} else {
				saveChangePwdLog(req, uid, Const.STATUS_FAILED, 1, 0l);
				return new ReMsg(ReCode.PASSWORD_VER_ERR);
			}
		}
		saveChangePwdLog(req, uid, Const.STATUS_FAILED, 1, 0l);
		return new ReMsg(ReCode.FAIL);
	}

	private void saveChangePwdLog(HttpServletRequest req, long uid, int status, int type, long adminId) {
		UpdatePwdLog upl = new UpdatePwdLog(uid, super.getIp(req), status, type, adminId);
		upl.set_id(super.getNextId(DocName.UPDATE_PWD_LOG));
		super.getMongoTemplate().save(upl);
	}

	public ReMsg updatePwd(long uid, String newPwd) {
		DBObject user = this.findById(uid);
		if (null != user) {
			DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
			u.put("pwd", MDUtil.SHA.digest2HEX(newPwd));
			return update(uid, u);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg updateAddress(Long uid, String province, String city) {
		DBObject user = this.findById(uid);
		if (null != user) {
			DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
			u.put("province", province);
			u.put("city", city);
			return update(uid, u);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg updateLabel(long uid, String personLabel) {
		DBObject user = this.findById(uid);
		if (null != user) {
			DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
			u.put("personLabel", personLabel);
			return update(uid, u);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg changeCover(long uid, String cover) {
		DBObject user = this.findById(uid);
		if (null != user) {
			DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
			u.put("cover", cover);
			return update(uid, u);
		}
		return new ReMsg(ReCode.FAIL);
	}

	private static final String POINT = "point";
	private static final DBObject seqField = new BasicDBObject(POINT, Integer.valueOf(1));

	public void changePoint(long uid, Point point, long adminId) {
		this.changePoint(uid, point.getType(), point.getPoint(), adminId);
	}

	public void changePoint(long uid, int type, int point, long adminId) {
		if (point < 1) {
			return;
		}
		// 检测是否有等级提升 0代表没有 否则返回当前等级
		int oldPoint = DboUtil.getInt(findById(uid), "point");
		int oldLevel = getLevel(oldPoint);
		int level = getLevel(oldPoint + point);
		if (level - oldLevel > 0) {
			if (level > 1) {
				// 发送socknet 传递当前等级
				MapBody mb = new MapBody("level", level);
				Msg msg = new Msg(super.incrMsgId(), MsgType.UP_LEVEL, 0, uid, System.currentTimeMillis(),
						mb.getData());
				messageService.msgHandler(msg);
			}
			if (level / 10 > oldLevel / 10) {
				int flower = 0;
				for (Integer e : LevelUp.map.keySet()) {
					if (level / 10 - e / 10 == 0) {
						flower = LevelUp.map.get(e).getFlower();
					}
				}
				userPackService.addGoods(uid, BaseGoods.Gift.FLOWER.getV(), flower, GiftForm.LEVELUP, level);
				messageService.imMsgHandler(Const.SYSTEM_ID, uid, "恭喜您的游戏等级升至" + level + "级!获得" + flower + "朵鲜花的大礼包!",
						null, null);
			}
		}
		DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject(POINT, point)).append("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()));
		Object obj = getMongoTemplate().getCollection(DocName.USER)
				.findAndModify(new BasicDBObject("_id", uid), seqField, null, false, incSeq, true, true).get(POINT);
		int changedPoint = 0;
		if (obj instanceof Integer) {
			changedPoint = (int) obj;

		} else {
			changedPoint = ((Long) obj).intValue();
		}
		PointLog pl = new PointLog(super.getNextId(DocName.POINT_LOG), uid, type, point, changedPoint, adminId);
		super.getMongoTemplate().save(pl);
		super.getRedisTemplate().delete(RK.keyUser(uid));
	}

	// FIXME 后台增减经验
	public ReMsg adminChangePoint(int io, long uid, int type, int point, long adminId, String detail, boolean push) {
		String addOrRed = "赠送";
		if (io == 1) {
			point = Math.abs(point);
		} else {
			addOrRed = "扣除";
			point = 0 - Math.abs(point);
		}
		DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject(POINT, point)).append("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()));
		Object obj = getMongoTemplate().getCollection(DocName.USER)
				.findAndModify(new BasicDBObject("_id", uid), seqField, null, false, incSeq, true, true).get(POINT);
		int changedPoint = 0;
		if (obj instanceof Integer) {
			changedPoint = (int) obj;

		} else {
			changedPoint = ((Long) obj).intValue();
		}
		PointLog pl = new PointLog(super.getNextId(DocName.POINT_LOG), uid, type, point, changedPoint, adminId);
		super.getMongoTemplate().save(pl);
		super.getRedisTemplate().delete(RK.keyUser(uid));
		if (push) {
			String body = "系统" + addOrRed + "您" + Math.abs(point) + "经验";
			if (StringUtils.isNotBlank(detail)) {
				body = body + "," + addOrRed + "原因:" + detail + "。";
			}
			messageService.imMsgHandler(Const.SYSTEM_ID, uid, body, null, null);
			// messageService.easeMsgHandler(Const.SYSTEM_ID, uid, body, null,
			// null);
		}
		return new ReMsg(ReCode.OK);
	}

	private static final String VIP = "vip";
	private static final DBObject vipSeqField = new BasicDBObject(VIP, Integer.valueOf(1));

	public void changeVipAdm(long uid, int amount) {
		changeVip(uid, amount, super.getUid());
	}

	public void changeVip(long uid, int amount, long adminId) {
		if (amount < 1 && adminId < 1) {
			return;
		}
		// 检测vip值的变化 看等级是否变化
		int vip = VipMap.countVip(DboUtil.getInt(findById(uid), "vip"), amount);
		if (vip > 0) {
			// 发送socket 传递当前Vip等级
			MapBody mb = new MapBody("vip", vip);
			Msg msg = new Msg(super.incrMsgId(), MsgType.UP_LEVEL, 0, uid, System.currentTimeMillis(), mb.getData());
			messageService.msgHandler(msg);
		}
		DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject(VIP, amount)).append("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()));
		getMongoTemplate().getCollection(DocName.USER).findAndModify(new BasicDBObject("_id", uid), vipSeqField, null,
				false, incSeq, true, true);
		super.getRedisTemplate().delete(RK.keyUser(uid));
		// FIXME
		aliIMService.updateVIP(uid, amount);
	}

	private static final String UN = "rbt";
	private static final String PWD = "rbt456PWD123";
	static String[] END = { "\"", "❤", "'", " ", "♠", "♥", "❥", "ღ", "✬", "❊", "✪", "✡", "❂", "☼", "☪", "⦸", "☻", "🍀",
			"💕", "✨", "👑", "💫", "🌹", "🏀", "🌻", "👠", "☀", "🍼", "⚡", "🐾", "💘", "∝", "∮", "卍", "卐", "つ", "°",
			"づ", "╭╯", "s·", "ゝ", "＊", "ヤ", "╰_╯", "≈", "灬", "♔", "♕", "♖", "♚", "♛", "❀", "❄", "ㄨ", "✔", "❅", "❆", "★",
			"♂", "♀", "☸", "♆", "☸", "♈", "♉", "♊", "♋", "♌", "♍", "♎", "♏", "♐", "♑", "♒", "♓", "└(^o^)┘", "\\^o^/",
			"Y(^_^)Y", "🎸", "🎼", "🔞", "🐬", "🐏", "🐫", "🐗", "™", "←", "ⓝⓑ", "ξ", "Ψ", "Ω", "ω", "㊤", "₪", "☏", "囍",
			"サ", "※", "ん", "〾", "⿻", "㊣", "〄", "　ౄ　ౄ　ౄഇ", "♬", "♪", "♩", "♭", "∮", "￠", "⅓", "⅔", "⅛", "⅜", "⅝", "⅞",
			"√", "㏒", "㏑", "≠", "☹", "♘", "☛", "☚", "웃", "☠", "☝", "☟", "♯", "☬", "☯", "㎡", "៛", "﷼", "㊰", "㊙", "㊞",
			"㊝", "☃", "♨", "✈", "ᴴᴰ", "∛", "∜", "⇜", "⇝", "⇞", "⇟", "↺", "↶", "↷", "❋", "↻", "☊", "☋", "↩", "↪", "↫",
			"↬", "➶", "ₒ", "ₔ", "ₐ", "¹", "²", "³", "⁰", "ⁱ", "⁴", "⁵", "⁶", "⁷", "⁸", "⁹", "⁺", "⁻", "⁼", "ⁿ", "ₑ",
			"ₓ", "㊋", "㊌", "㊍", "㊏", "㊎", "㊐", "㊖", "㊫", "৳" };

	public ReCode createRobit(int size, HttpServletRequest req, Role role) {
		String pwd = MDUtil.SHA.digest2HEX(PWD);
		String ip = null;
		Random random = ThreadLocalRandom.current();
		if (req != null) {
			ip = super.getClientId(req);
		} else {
			ip = "";
			for (int i = 0; i < 4; i++) {
				ip = ip + (random.nextInt(100) + 100) + ".";
			}
			ip = ip.substring(0, ip.length() - 1);
		}

		for (int i = 0; i < size; i++) {
			Long id = super.getNextId(DocName.USER);
			User user = new User(UN + id, pwd, ip);
			user.set_id(id);
			user.setRole(role.getRole());
			user.setCoin(3000);// 金币3000
			user.setPoint(random.nextInt(1000) + 100);// 经验100-1100 1-3级
			DBObject dbo = getCopyUser();
			if (dbo != null) {
				user.setSex(DboUtil.getInt(dbo, "sex"));
				user.setAvatar(DboUtil.getString(dbo, "avatar"));
				user.setNickname(DboUtil.getString(dbo, "nickname"));
				if (StringUtils.isBlank(DboUtil.getString(dbo, "city"))) {
					int p = RandomUtil.nextInt(loc.length);
					int n = RandomUtil.nextInt(loc[p].length - 1) + 1;
					user.setProvince(loc[p][0]);
					user.setCity(loc[p][n]);
				} else {
					user.setProvince(DboUtil.getString(dbo, "province"));
					user.setCity(DboUtil.getString(dbo, "city"));
				}
				getMongoTemplate().save(user);
				// 机器人注册阿里
				aliIMService.reg(aliIMService.getUsername(id), DboUtil.beanToDBO(user));
			}

		}
		return ReCode.OK;
	}

	public DBObject getCopyUser() {
		DBObject dbo = null;
		for (;;) {
			int n = RandomUtil.nextInt(500000);
			dbo = this.findById(100000000L + n);
			if (dbo != null) {
				Integer sex = DboUtil.getInt(dbo, "sex");
				String avatar = DboUtil.getString(dbo, "avatar");
				String nickname = DboUtil.getString(dbo, "nickname");
				if (sex != null && StringUtils.isNotBlank(avatar) && StringUtils.isNotBlank(nickname)) {
					dbo.put("nickname", nickname + END[RandomUtil.nextInt(END.length)]);
					break;
				}
			}
		}
		return dbo;
	}

	/**
	 * 换机器人头像
	 * 
	 * @throws IOException
	 */
	public ReCode changeRobitAvatar(Long id) throws IOException {
		User user = DboUtil.toBean(this.findById(id), User.class);
		DBObject dbo = null;
		Random random = ThreadLocalRandom.current();
		for (;;) {
			int n = random.nextInt(150000);
			dbo = this.findById(100000000L + n);
			if (dbo != null) {
				String avatar = DboUtil.getString(dbo, "avatar");
				if (!checkEmpty(avatar)) {
					user.setAvatar(avatar);
					// log.error(":::::::::搞了一个——>" + id);
					break;
				}
			}
		}
		getMongoTemplate().save(user);
		return ReCode.OK;
	}

	/** 判断机器人头像是否为空 */
	public boolean checkEmpty(String url) throws IOException {
		boolean isEmpty = false;
		String ret = HttpClientUtil.get(url, null, HttpClientUtil.UTF8);
		if (FEATURE_LIBRARY.contains(MD5Util.getMD5Format(ret))) {
			isEmpty = true;
		}
		return isEmpty;
	}

	public ReCode createGoodNo(String uids, String PWD, HttpServletRequest req) {
		String pwd = MDUtil.SHA.digest2HEX(PWD);
		String ip = "";
		if (req != null) {
			ip = super.getClientId(req);
		}
		String[] arruids = null;
		if (uids.contains(",")) {
			arruids = uids.split(",");
			for (String suid : arruids) {
				Long uid = T2TUtil.str2Long(suid, 0L);
				saveGoodNoUser(pwd, ip, uid);
			}
		} else if (uids.contains("-")) {
			arruids = uids.split("-");
			Long sId = T2TUtil.str2Long(arruids[0], 0L);
			if (sId > 9999) {
				Long eId = T2TUtil.str2Long(arruids[1], 0L);
				if (eId > sId) {
					for (Long i = sId; i <= eId; i++) {
						saveGoodNoUser(pwd, ip, i);
					}
				}
			}
		} else {
			Long uid = T2TUtil.str2Long(uids, 0L);
			saveGoodNoUser(pwd, ip, uid);
		}
		return ReCode.OK;
	}

	private void saveGoodNoUser(String pwd, String ip, Long uid) {
		if (uid > 0) {
			if (this.findById(uid) == null) {
				User user = new User("V" + uid, pwd, ip);
				user.set_id(uid);
				user.setNickname("BiBi" + uid);
				getMongoTemplate().save(user);
				// 靓号注册阿里
				aliIMService.reg(aliIMService.getUsername(uid), DboUtil.beanToDBO(user));
			}
		}
	}

	public ReCode save(User user) {
		user.set_id(super.getNextId(DocName.USER));
		if (StringUtils.isNotBlank(user.getPwd())) {
			user.setPwd(MDUtil.SHA.digest2HEX(user.getPwd()));
		}
		user.setCreateTime(System.currentTimeMillis());
		user.setUpdateTime(user.getCreateTime());
		getMongoTemplate().save(user);
		return ReCode.OK;
	}

	public ReCode update(User user) {
		if (StringUtils.isNotBlank(user.getPwd())) {
			user.setPwd(MDUtil.SHA.digest2HEX(user.getPwd()));
		}
		user.setUpdateTime(user.getCreateTime());
		getMongoTemplate().save(user);
		return ReCode.OK;
	}

	public List<DBObject> findTopUser(Integer size) {
		if (size < 1 || size > 100) {
			size = 20;
		}
		List<DBObject> dbos = getC(DocName.USER).find().limit(getSize(size)).sort(new BasicDBObject("point", -1))
				.toArray();
		List<DBObject> results = new ArrayList<DBObject>();
		for (DBObject dbo : dbos) {
			results.add(removeUserInfo(dbo));
		}
		return results;
	}

	public Page<DBObject> queryUser(Long uid, Integer status, Integer role, String phone, String nickname, Integer page,
			Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findUser(uid, status, role, phone, nickname, page, size);
		int count = countUser(uid, status, role, phone, nickname);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> findUser(Long uid, Integer status, Integer role, String phone, Integer page, Integer size) {
		return findUser(uid, status, role, phone, null, page, size);
	}

	public List<DBObject> findUser(Long uid, Integer status, Integer role, String phone, String nickname, Integer page,
			Integer size) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (null != role && role != 0) {
			q.put("role", role);
		}
		if (null != uid && uid > 0) {
			q.put("_id", uid);
		}
		if (StringUtils.isNotBlank(phone)) {
			q.put("phone", phone);
		}
		if (StringUtils.isNotBlank(nickname)) {
			q.put("nickname", new BasicDBObject("$regex", nickname));
		}
		return getC(DocName.USER).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("createTime", -1)).toArray();
	}

	public List<DBObject> findRobit(Integer page, Integer size) {
		DBObject q = new BasicDBObject("role", Role.ROBOT_C.getRole());
		return getC(DocName.USER).find(q, new BasicDBObject("_id", 1)).skip(super.getStart(page, size))
				.limit(getSize(size)).sort(new BasicDBObject("_id", -1)).toArray();
	}

	public int countUser(Long uid, Integer status, Integer role, String phone) {
		return countUser(uid, status, role, phone, null);
	}

	public int countUser(Long uid, Integer status, Integer role, String phone, String nickname) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (null != role && role != 0) {
			q.put("role", role);
		}
		if (null != uid && uid > 0) {
			q.put("_id", uid);
		}
		if (StringUtils.isNotBlank(phone)) {
			q.put("phone", phone);
		}
		if (StringUtils.isNotBlank(nickname)) {
			q.put("nickname", new BasicDBObject("$regex", nickname));
		}
		return getC(DocName.USER).find(q).count();
	}

	public void stUserAddEveryDay() {
		Long end = DateUtil.getTodayZeroTime();
		Long start = end - 1000 * 3600 * 24;
		List<BasicDBObject> pipeline = new ArrayList<BasicDBObject>();
		pipeline.add(new BasicDBObject("$match",
				new BasicDBObject("createTime", new BasicDBObject("$gte", start).append("$lt", end))));
		// pipeline.add(new BasicDBObject("$group",))
		super.getC(DocName.USER).aggregate(pipeline);
	}

	private static final int MAX_LEVEL = 100;
	private static List<Long> levels = new ArrayList<Long>(MAX_LEVEL);

	static {
		double n1 = 0;
		double n2 = 1;
		double t = 0;
		for (int i = 1; i <= MAX_LEVEL; i++) {
			double p = (double) i / 1.717;
			t = t + p;
			n2 = n1 + t;
			if (i > 49) {
				levels.add(Math.round(n2) * 100 * ((i / 10 - 4) * 2));
			} else {
				levels.add((Math.round(n2) * 100));
			}
			n1 = n2;
		}
	}

	public static int getLevel(Integer point) {
		if (point != null) {
			for (int i = 0; i < levels.size(); i++) {
				if (levels.get(i) > point) {
					return i;
				}
			}
			return MAX_LEVEL;
		} else {
			return 0;
		}
	}

	public static List<Long> getLevels() {
		return levels;
	}

	public List<DBObject> query4downloadRobit(long index, int type) {
		if (type == Role.ROBOT_C.getRole() || type == Role.ROBOT.getRole()) {
			BasicDBObject q = new BasicDBObject("role", type);
			q.put("_id", new BasicDBObject("$gte", index));
			return getC(DocName.USER).find(q).sort(new BasicDBObject("_id", -1)).limit(100).toArray();
		}
		return new ArrayList<DBObject>();
	}

	public List<DBObject> queryNotRegAli(long id, Boolean type) {
		DBObject q = new BasicDBObject("_id", new BasicDBObject("$gt", id));
		if (type != null) {
			q.put("chatReg", type);
		}
		return getC(DocName.USER).find(q).sort(new BasicDBObject("_id", 1)).limit(100).toArray();
	}

	public DBObject queryFirstRobot(int type) {
		return getC(DocName.USER).find(new BasicDBObject("role", type)).sort(new BasicDBObject("_id", 1)).limit(1)
				.toArray().get(0);
	}

	// 无效头像特征库
	@SuppressWarnings("all")
	public static final Set<String> FEATURE_LIBRARY = new HashSet() {
		{
			add("d41d8cd98f00b204e9800998ecf8427e");
			add("5b7f5f81049ae612462fd65b229d572d");
			add("21777db24581f7382c51fe8a64d1b8ce");
		}
	};

	// 查询最近注册用户
	public List<DBObject> searchLastRegUsers(int size, int sex) {
		BasicDBObject q = new BasicDBObject("role", new BasicDBObject("$lt", 3)).append("sex", sex);
		return getC(DocName.USER).find(q).sort(new BasicDBObject("createTime", -1)).limit(size).toArray();
	}

	// 根据id或者nickName查询user
	public ReMsg searchUser(String data) {
		DBObject object = null;
		try {
			object = findById(Long.valueOf(data));
			if (null != object)
				return new ReMsg(ReCode.OK, object);
		} catch (Exception e) {
			object = getC(DocName.USER).findOne(new BasicDBObject("nickname", data));
			if (null != object) {
				return new ReMsg(ReCode.OK, object);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	// 获取标签
	public ReMsg getInterestsList() {
		return new ReMsg(ReCode.OK, InterestsUtil.getInterests());
	}

	// 检测用户等级是否有提升
	public static int countLevel(Integer point, int count) {
		if (null == point) {
			return getLevel(count);
		}
		int l1 = getLevel(point);
		int l2 = getLevel(point + count);
		if (l2 > l1) {
			return l2;
		}
		return 0;
	}

	/** 查询1小时内登录过非机器人用户 */
	public List<DBObject> findOnlineUser(Integer page, Integer size) {
		DBObject q = new BasicDBObject("role", new BasicDBObject("$lt", 3)).append("updateTime",
				new BasicDBObject("$gt", System.currentTimeMillis() - 60 * 60 * 1000L));
		return getC(DocName.USER).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("updateTime", -1)).toArray();
	}

	// 转换user
	public List<String[]> getAtUsers(List<String> atUsers) {
		if (null == atUsers || atUsers.size() == 0) {
			return null;
		}
		List<String[]> list = new ArrayList<String[]>();
		for (String string : atUsers) {
			DBObject user = findById(Long.parseLong(string));
			if (null == user) {
				continue;
			}
			list.add(new String[] { DboUtil.getString(user, "_id"), DboUtil.getString(user, "nickname") });
		}
		return list;
	}

	/** 用户是否在线 */
	public Boolean isOnline(long uid) {
		String json = super.getChatRedisTemplate().opsForValue().get(RK.imOnlineUser(uid));
		if (StringUtils.isNotBlank(json)) {
			return true;
		}
		return false;
	}

	private void initRobitC() {
		envService.set("user.robit.init", true);
		super.getRedisTemplate().delete(RK.userRobitList());
		int page = 1;
		boolean next = true;
		int size = 500;
		while (next) {
			List<DBObject> ls = findRobit(page, size);
			// log.error("size:"+ls.size());
			page++;
			if (ls.size() < size) {
				next = false;
			}
			List<String> uids = new ArrayList<String>();
			for (DBObject u : ls) {
				// log.error("uid:"+DboUtil.getString(u, "_id"));
				uids.add(DboUtil.getString(u, "_id"));
			}
			super.getRedisTemplate().opsForList().rightPushAll(RK.userRobitList(), uids);
		}
	}

	long initTime = 0;

	public Long getRobitCId() {
		long curTime = System.currentTimeMillis();
		if (curTime - initTime > Const.MINUTE * 10) {
			initTime = curTime;
			Boolean init = envService.getBool("user.robit.init");
			if (init == null || !init) {
				initRobitC();
			}
		}
		String suid = super.getRedisTemplate().opsForList().leftPop(RK.userRobitList());
		if (suid == null) {
			initRobitC();
			suid = super.getRedisTemplate().opsForList().leftPop(RK.userRobitList());
		}
		if (suid != null) {
			super.getRedisTemplate().opsForList().rightPush(RK.userRobitList(), suid);
			return Long.parseLong(suid);
		}
		return 0L;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> getRobitCUid() {
		long cuid = getRobitCId();
		if (cuid > 0) {
			DBObject dbo = this.findById(cuid);
			if (dbo != null) {
				Map res = new HashMap();
				res.put("uid", DboUtil.getLong(dbo, "_id"));
				res.put("nickname", DboUtil.getString(dbo, "nickname"));
				return res;
			}
		}
		return null;
	}

	/** 根据金币范围查询用户 */
	public Page<DBObject> queryCoinUser(Long min, Long max, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findCoinUser(min, max, page, size);
		int count = countCoinUser(min, max);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> findCoinUser(Long min, Long max, Integer page, Integer size) {
		DBObject q = new BasicDBObject("role", 1);
		if (min != null && min != 0 && max != null && max != 0) {
			q.put("coin", new BasicDBObject("$gte", min).append("$lte", max));
		} else if (min != null && min != 0) {
			q.put("coin", new BasicDBObject("$gte", min));
		} else if (max != null && max != 0) {
			q.put("coin", new BasicDBObject("$lte", max));
		}
		DBObject get = new BasicDBObject("nickname", true).append("coin", true).append("status", true)
				.append("avatar", true).append("createTime", true).append("updateTime", true);
		return getC(DocName.USER).find(q, get).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("coin", -1)).toArray();
	}

	public int countCoinUser(Long min, Long max) {
		DBObject q = new BasicDBObject("role", 1);
		if (min != null && min != 0 && max != null && max != 0) {
			q.put("coin", new BasicDBObject("$gte", min).append("$lte", max));
		} else if (min != null && min != 0) {
			q.put("coin", new BasicDBObject("$gte", min));
		} else if (max != null && max != 0) {
			q.put("coin", new BasicDBObject("$lte", max));
		}
		DBObject get = new BasicDBObject("nickname", true).append("coin", true).append("status", true)
				.append("avatar", true).append("createTime", true).append("updateTime", true);
		return getC(DocName.USER).find(q, get).count();
	}

	String[][] loc = { { "北京", "北京" },
			{ "山东", "德州", "潍坊", "临沂", "烟台", "菏泽", "青岛", "济宁", "聊城", "滨州", "济南", "东营", "泰安", "淄博", "威海", "日照", "枣庄",
					"莱芜" },
			{ "江苏", "泰州", "南京", "徐州", "连云港", "淮安", "盐城", "南通", "扬州", "宿迁", "镇江", "无锡", "常州", "苏州" }, { "宁夏", "银川" },
			{ "浙江", "杭州", "嘉兴", "湖州", "绍兴", "宁波", "金华", "丽水", "台州", "温州", "衢州" }, { "吉林", "长春" }, { "湖北", "黄冈" },
			{ "辽宁", "辽阳" }, { "湖北", "武汉" }, { "天津", "天津" }, { "四川", "成都" }, { "福建", "福州", "厦门" },
			{ "江西", "抚州", "赣州", "吉安", "景德镇", " 九江", "南昌", "萍乡", "上饶", "新余", "宜春", "鹰潭" },
			{ "安徽", "合肥", "芜湖", "蚌埠", "马鞍山", "安庆", "淮南", "铜陵", "黄山", "宣城", "池州", "滁州" }, { "广东", "汕头", "潮州", "梅州", "揭阳",
					"汕尾", "韶关", "清远", "惠州", "东莞", "江门", "中山", "佛山", "珠海", "阳江", "肇庆", "云浮", "茂名", "湛江" },
			{ "贵州", "贵阳", "毕节" }, { "云南", "昆明", "曲靖", "玉溪", "保山", "昭通", "丽江", "思茅", "临沧" } };

	public void updateUserLbs() {
		boolean next = true;
		int size = 500;
		long maxId = 0;
		while (next) {
			List<DBObject> dbos = super.getC(DocName.USER)
					.find(new BasicDBObject("city", new BasicDBObject("$exists", false)).append("_id",
							new BasicDBObject("$gt", maxId)))
					.sort(new BasicDBObject("_id", 1)).limit(size).toArray();
			if (dbos.size() < size) {
				next = false;
			}
			for (DBObject dbo : dbos) {
				maxId = DboUtil.getLong(dbo, "_id");
				int p = RandomUtil.nextInt(loc.length);
				int n = RandomUtil.nextInt(loc[p].length - 1) + 1;
				System.out.println(loc[p][0] + " " + loc[p][n]);
				DBObject u = new BasicDBObject("province", loc[p][0]).append("city", loc[p][n]);
				update(maxId, u);
				// String ip = DboUtil.getString(dbo, "regIp");
				// AddressComponent ac = LbsUtil.getIpLbs(ip);
				// if (ac != null) {
				//
				// // try {
				// // Thread.sleep(100);
				// // } catch (InterruptedException e) {
				// // e.printStackTrace();
				// // }
				// }
			}
		}
	}

	public enum LevelUp {
		_10(10, 88), _20(20, 388), _30(30, 1314), _40(40, 3344), _50(50, 6688), _60(60, 9999), _70(70, 39999), _80(80,
				69999), _90(90, 999999), _100(100, 666666),;
		LevelUp(int level, int flower) {
			this.level = level;
			this.flower = flower;
		}

		private int level;
		private int flower;

		public int getLevel() {
			return level;
		}

		public int getFlower() {
			return flower;
		}

		@SuppressWarnings({ "all" })
		public static final Map<Integer, LevelUp> map = new HashMap() {
			{
				for (LevelUp e : EnumSet.allOf(LevelUp.class)) {
					put(e.getLevel(), e);
				}
			}
		};
	}

	public List<TitleModel> queryTitle(long uid) {
		DBObject dbo = findUserTitleByUid(uid);
		List<TitleModel> newtl = null;
		if (null != dbo) {
			UserTitle ut = DboUtil.toBean(dbo, UserTitle.class);
			long now = System.currentTimeMillis();
			newtl = new ArrayList<TitleModel>();
			for (TitleModel tm : ut.getList()) {
				if (tm.getValidity() > now) {
					newtl.add(tm);
				}
			}
		}
		if (null != newtl && !newtl.isEmpty()) {
			newtl = newtl.stream().sorted((e1, e2) -> {
				return e2.getType() - e1.getType();
			}).collect(Collectors.toList());
		}
		return newtl;
	}

	public void saveTitle(long uid, TitleModel newTm) {
		if (null == newTm) {
			log.error("saveTitle fail  title=null:" + uid);
			return;
		}
		long now = DateUtil.getTodayZeroTime();
		String lockKey = "saveUserTitle" + uid;

		log.error("saveTitle add tilte:" + uid + " " + newTm.getName() + " " + newTm.getValidity());

		try {
			super.lock(lockKey, 2);
			DBObject dbo = findUserTitleByUid(uid);
			List<TitleModel> newtl = new ArrayList<>();
			UserTitle ut = null;
			if (null != dbo) {
				ut = DboUtil.toBean(dbo, UserTitle.class);
				List<TitleModel> tl = ut.getList();
				boolean has = false;
				for (TitleModel tm : tl) {
					long validity = tm.getValidity();
					if (tm.getName().equals(newTm.getName())) {
						if (validity > now) {
							validity = validity + tm.getVal() * Const.DAY;
						} else {
							validity = now + tm.getVal() * Const.DAY;
						}
						log.error("saveTitle validity:" + validity);
						tm.setValidity(validity);
						tm.setUrl(newTm.getUrl());
						tm.setName(newTm.getName());
						tm.setSex(newTm.getSex());
						newtl.add(tm);
						has = true;
					} else {
						if (validity > now + 10 * Const.MINUTE) {
							newtl.add(tm);
						} else {
							log.error("saveTitle remove tilte:" + ut.get_id() + " " + tm.getName() + " "
									+ tm.getValidity());
						}
					}
				}
				if (!has) {
					newtl.add(newTm);
				}
				ut.setList(newtl);
				ut.setUpdateTime(System.currentTimeMillis());
			} else {
				if (null != newTm) {
					newtl.add(newTm);
				}
				ut = new UserTitle(uid, newtl);
			}
			super.getMongoTemplate().save(ut);
		} finally {
			super.unlock(lockKey);
		}
	}

	public void checkTitle(UserTitle ut) {
		MongoTemplate mongoTemplate = super.getMongoTemplate();
		UserService us = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				String lockKey = "saveUserTitle" + ut.get_id();
				try {
					if (us.lock(lockKey, 2)) {
						boolean change = false;
						long now = DateUtil.getTodayZeroTime();
						List<TitleModel> newtl = new ArrayList<>();
						List<TitleModel> tl = ut.getList();
						for (TitleModel tm : tl) {
							long validity = tm.getValidity();
							if (validity > now) {
								if (StringUtils.isBlank(tm.getUrl())) {
									TitleModel cur = TitleModel.getModel(tm.getTtype(), tm.getName());
									tm.setUrl(cur.getUrl());
									change = true;
								}
								newtl.add(tm);
							} else {
								log.error("remove tilte:" + ut.get_id() + " " + tm.getName() + " " + tm.getValidity());
								change = true;
							}
						}
						if (change) {
							if (newtl.size() == 0) {
								mongoTemplate.getCollection(DocName.USERTITLE)
										.remove(new BasicDBObject("_id", ut.get_id()));
							} else {
								ut.setList(newtl);
								ut.setUpdateTime(System.currentTimeMillis());
								mongoTemplate.save(ut);
							}
						}
					}
				} finally {
					us.unlock(lockKey);
				}
			}
		}) {
		}.start();

	}

	public void saveTitle(long uid, int count, int tType) {
		saveTitle(uid, count, tType, 0);
	}

	public void saveTitle(long uid, int count, int tType, int sex) {
		TitleModel cur = TitleModel.getModel(tType, count, sex);
		saveTitle(uid, cur);
	}

	public ReCode changeGuard(long uid, long guid, int count) {
		DBObject user = this.findById(uid);
		if (user != null) {
			Long curGuid = DboUtil.getLong(user, "guard");
			boolean uflag = true;
			if (curGuid != null && curGuid != 0) {
				Long curGCnt = DboUtil.getLong(user, "guardCnt");
				if (count <= curGCnt) {
					uflag = false;
				}
			}
			if (uflag) {
				curGuid = guid;// 最新守护id
				DBObject u = new BasicDBObject("guard", guid).append("guardCnt", count).append("guardExpiryTime", null);
				this.update(uid, u);

				Map<String, Object> ext = new HashMap<String, Object>();
				ext.put("type", IMType.GUARD.getType());
				ext.put("op", OperationType.USERSPACE.getOp());
				ext.put("opId", uid);
				messageService.imMsgHandler(guid, uid,
						"[" + guid + "] 送给 [" + uid + "] " + count + "朵花成为 [" + uid + "] 的守护，陪伴是最长情的告白。", ext,
						IMType.GUARD.getType());
			}
			Long guardExpiryTime = DboUtil.getLong(user, "guardExpiryTime");
			// 守护改变或原本无特效
			if (uflag || null == guardExpiryTime || guardExpiryTime < 1) {
				int day = RankListService.getDay(1);
				// 今天在活动有效期内
				if (day >= RankListJob.VALENTINE_DAY_START && day <= RankListJob.VALENTINE_DAY_END) {
					long sendUid = curGuid;// 现在的守护uid
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							long cnt = count;
							BasicDBObject q = new BasicDBObject("bgId", BaseGoods.Gift.FLOWER.getV().getId())
									.append("day",
											new BasicDBObject("$gte", RankListJob.VALENTINE_DAY_START).append("$lte",
													RankListJob.VALENTINE_DAY_END))
									.append("sendUid", sendUid).append("uid", uid);
							List<DBObject> dbos = getC(DocName.USER_GIFT).find(q).toArray();
							for (DBObject dbo : dbos) {
								cnt = cnt + DboUtil.getLong(dbo, "count");
							}
							if (cnt >= 99999) {
								log.error("增加守护特效:  uid:" + uid);
								DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis())
										.append("guardExpiryTime", System.currentTimeMillis() + Const.DAY * 28);
								update(uid, u);
							}
						}

					});
					t.start();
				}
			}
		}
		return ReCode.OK;
	}

	public ReMsg getFriendsDynamic(Integer size, Integer page) {
		long uid = super.getUid();
		List<Long> dbos = relationshipService.getFrinds(uid);
		int len = dbos.size();
//		if (len != 0) {
			dbos.add(uid);
			dbos.add(100555555L);
			Long[] uids = new Long[dbos.size()];
			dbos.toArray(uids);
			return articleService.getUsersArticles(uids, size, page);
//		} else {
//			return articleService.getUserArticles(uid, size, page);
//		}
	}

	public DBObject findUserTitleByUid(Long uid) {
		return getC(DocName.USERTITLE).findOne(new BasicDBObject("_id", uid));
	}

	public Page<DBObject> queryUserTitle(Long userId, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findUserTitle(userId, page, size);
		int count = this.countUserTitle(userId);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> findUserTitle(Long uid, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != uid && uid != 0) {
			q.put("_id", uid);
		}
		return getC(DocName.USERTITLE).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("updateTime", -1)).toArray();
	}

	public int countUserTitle(Long uid) {
		DBObject q = new BasicDBObject();
		if (null != uid && uid != 0) {
			q.put("_id", uid);
		}
		return getC(DocName.USERTITLE).find(q).count();
	}

	public ReMsg upsetUserTitle(long uid, Integer titleType, String titleName, Integer day) {
		day = null == day || day < 1 ? 1 : day;
		TitleModel model = TitleModel.getModel(titleType, titleName);
		this.saveTitle(uid, model);
		return new ReMsg(ReCode.OK);
	}

	/** 根据类型获取称号 */
	public ReMsg getAllTitleModels(Integer ttype) {
		return new ReMsg(ReCode.OK, TitleModel.getAllTitleModel(ttype, false));
	}

	public void cleanTitle() {
		int start = 0;
		int size = 100;
		int count = 100;
		while (count > 0) {
			List<DBObject> list = getC(DocName.USERTITLE).find(new BasicDBObject()).skip(start).limit(size).toArray();
			count = list.size();
			start += 100;
			log.error("count" + count + "start" + start);
			for (DBObject dbo : list) {
				checkTitle(DboUtil.toBean(dbo, UserTitle.class));
			}
		}
	}

	/** 移除我对某个人的守护 */
	public ReMsg removeGuard(long myId, long uid) {
		DBObject user = findById(uid);
		Long guard = DboUtil.getLong(user, "guard");
		if (null != guard && guard == myId) {// 取消我对他的守护
			removeGuard(uid);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public void removeGuard(long uid) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis()).append("guard", null)
				.append("guardCnt", null).append("guardExpiryTime", null);
		this.update(uid, u);
	}
}