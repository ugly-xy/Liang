package com.zb.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.AddressComponent;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.LbsUtil;
import com.zb.common.utils.MapUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.User;
import com.zb.models.room.Actor;
import com.zb.models.room.GameUserHistory;
import com.zb.service.room.CowService;
import com.zb.service.room.RoomDefaultService;
import com.zb.service.room.TexasService;
import com.zb.service.room.WerewolfKillService;
import com.zb.service.room.vo.GameFriendsList;
import com.zb.service.room.vo.GameUserList;
import com.zb.service.room.vo.RecommendUserInfoVO;

@Service
public class RecommendService extends BaseService {

	static final Long DISTANCE = 100 * 1000L;
	static final Long FAR_DISTANCE = 1000 * 1000L;
	static final Long POSSIBLEY_ONLINE_USER_TIME = 2 * 60 * 60 * 1000L;
	static final Long MAYBE_ONLINE_USER_TIME = 6 * 60 * 60 * 1000L;
	static final Long OFFLINE_USER_TIME = 7 * 24 * 60 * 60 * 1000L;
	static final int SIZE = 20;

	static final int HOME_PAGE_RECOMMEND_SIZE = 4;
	static final int ADD_FRIENDS_SIZE = 6;
	static final int SAME_SEX_SIZE = 2;
	static final int OTHER_SEX_SIZE = 4;
	static final int IN_GAME = 1;
	static final int IS_GREEN = 2;
	static final int IS_DRIDER = 3;
	static final int PLAYED = 4;
	static final int MALE = 1;
	static final int FEMALE = 2;
	static final long GAME_DATE = 3600 * 1000L;

	static final long GAMING_SCORE = 9999L; // 游戏中
	static final long ONLINE3_SCORE = 8888L;// 在线新用户
	static final long ONLINE7_SCORE = 5555L;// 在线老用户
	static final long OFFLINE3_SCORE = 2222L;// 非在线新用户
	static final long OFFLINE7_SCORE = 555L;// 非在线老用户

	static final String DEFAULT_AVATA = "http://zim.zhuangdianbi.com/default_avata.png";

	@Autowired
	UserService userService;

	@Autowired
	RoomDefaultService roomDefaultService;

	@Autowired
	RelationshipService relationshipService;

	@Autowired
	WerewolfKillService werewolfKillService;

	@Autowired
	CowService cowService;

	@Autowired
	TexasService texasService;

	/** 省市简单过滤 */
	public static String filterProCiName(String input) {
		return input.replace("自治区", "").replace("省", "").replace("市", "").replace("特别行政区", "").replace("壮族", "")
				.replace("维吾尔", "").replace("藏族", "").replace("回族", "").replace("地区", "").replace("州", "")
				.replace("县", "").replace("区", "").replace("蒙古族", "");
	}

	@SuppressWarnings("all")
	public ReMsg findNearByBiBi(long uid, Double latitude, Double longitude, Integer pageIndex, Integer size,
			Integer bookIndex, Integer searchSex, String ip) {
		AddressComponent ac = null;
		if (latitude == null || longitude == null) {
			if (ip == null) {
				return new ReMsg(ReCode.PARAMETER_ERR);
			} else {
				try {
					ac = LbsUtil.getIpLbs(ip);
					if (ac != null) {
						longitude = ac.getX();
						latitude = ac.getY();
					}
				} catch (Exception e) {
					log.error("通过ip获取经纬度失败 uid=" + uid, e);
				}
			}
		}
		if (pageIndex == null || pageIndex < 0) {
			pageIndex = 0;
		}
		if (bookIndex == null || bookIndex < 0) {
			bookIndex = 0;
		}
		size = SIZE;
		int baiduSize = 50;
		// GPS加密
		Map bdLoc = LbsUtil.geoconv(longitude, latitude);
		if (null == bdLoc) {
			return new ReMsg(ReCode.GPS_POSITION_ERROR);
		}
		Long sex = 1L;
		try {
			User user = DboUtil.toBean(userService.findById(uid), User.class);
			sex = (long) user.getSex();
			LbsUtil.updateLocation(longitude, latitude, uid, System.currentTimeMillis(), sex);
		} catch (Exception e) {
			log.error("更新位置失败 uid=" + uid, e);
			return new ReMsg(ReCode.FAIL);
		}
		try {
			Long now = System.currentTimeMillis();
			Long pos = now - POSSIBLEY_ONLINE_USER_TIME;
			Long may = now - MAYBE_ONLINE_USER_TIME;
			Long off = now - OFFLINE_USER_TIME;
			Double x = MapUtil.getDouble(bdLoc, "x");
			Double y = MapUtil.getDouble(bdLoc, "y");
			long searchSexl = 0l;
			if (null == searchSex) {
				searchSexl = 1 == sex ? 2L : 1L;
			} else {
				searchSexl = searchSex + 0l;
			}
			List<Long> fri = queryFriends(uid);
			fri.add(uid);
			fri.add(Const.SYSTEM_ID);
			fri.add(Const.SYSTEM_ID2);
			// 查询附近的人
			List<Map> allList = new ArrayList<>();
			if (bookIndex == 0) {
				List<Map> list = LbsUtil.findNearBy(x, y, DISTANCE, searchSexl, pos + 1, now, pageIndex, baiduSize);
				list = list.stream().filter(e -> !fri.contains((long) e.get("uid"))).collect(Collectors.toList());
				allList.addAll(list);
				if (allList.size() < SIZE) {
					bookIndex = 1;
					pageIndex = 0;
				}
			}
			if (bookIndex == 1) {
				List<Map> list2 = LbsUtil.findNearBy(x, y, DISTANCE, searchSexl, may + 1, pos, pageIndex, baiduSize);
				list2 = list2.stream().filter(e -> !fri.contains((long) e.get("uid"))).collect(Collectors.toList());
				allList.addAll(list2);
				if (allList.size() < SIZE) {
					bookIndex = 2;
					pageIndex = 0;
				}
			}
			if (bookIndex == 2) {
				List<Map> list3 = LbsUtil.findNearBy(x, y, DISTANCE, searchSexl, off + 1, may, pageIndex, baiduSize);
				list3 = list3.stream().filter(e -> !fri.contains((long) e.get("uid"))).collect(Collectors.toList());
				allList.addAll(list3);
				if (allList.size() < SIZE) {
					bookIndex = 3;
					pageIndex = 0;
				}
			}
			if (bookIndex >= 3) {
				List<Map> list4 = LbsUtil.findNearBy(x, y, FAR_DISTANCE, searchSexl, off + 1, may, pageIndex,
						baiduSize);
				List<Map> list3 = LbsUtil.findNearBy(x, y, DISTANCE, searchSexl, off + 1, may, pageIndex, baiduSize);
				list4.removeAll(list3);
				list4 = list4.stream().filter(e -> !fri.contains((long) e.get("uid"))).collect(Collectors.toList());
				allList.addAll(list4);
			}
			final long myUid = uid;
			List<User> users = allList.stream().map(e -> {
				User user = null;
				Long id = MapUtil.getLong(e, "uid");
				DBObject rm = userService.findById(id);
				if (rm != null) {
					user = DboUtil.toBean(rm, User.class);
					if (null != user && 0 == user.getFriendWorth()) {
						user.setFriendWorth(1);
					}
				}
				return user;
			}).filter(e -> e != null).distinct().collect(Collectors.toCollection(ArrayList::new));
			Map<String, Object> ret = new HashMap<>();
			int fsize = users.size();
			if (fsize > 1 && fsize % 2 == 1) {
				users.remove(fsize - 1);
			}
			ret.put("sursor2", bookIndex);
			ret.put("sursor1", ++pageIndex);
			ret.put("users", users);
			return new ReMsg(ReCode.OK, ret);
		} catch (Exception e) {
			log.error("寻找附近的人失败 uid=" + uid, e);
			return new ReMsg(ReCode.FAIL);
		}

	}

	/** 推荐狼人杀用户 */
	public ReMsg recommendWolfUsers(long uid, Integer index) {
		Long size = getRedisTemplate().opsForList().size(RK.wolfUsers()); // 推荐池人数
		if (size == null || size < 1) {
			return new ReMsg(ReCode.FAIL);
		} else {
			index = null == index ? 0 : index;
			int end = index + HOME_PAGE_RECOMMEND_SIZE;
			List<String> list = getRedisTemplate().opsForList().range(RK.wolfUsers(), index, end - 1);
			List<RecommendUserInfoVO> vol = new ArrayList<>();
			for (String json : list) {
				RecommendUserInfoVO rui = JSONUtil.jsonToBean(json, RecommendUserInfoVO.class);
				vol.add(rui);
			}
			GameUserList ret = new GameUserList();
			ret.setList(vol);
			ret.setIndex(end);
			return new ReMsg(ReCode.OK, ret);
		}
	}

	/** 推荐牛牛用户 */
	public ReMsg recommendCowUsers(long uid, Integer index) {
		Long size = getRedisTemplate().opsForList().size(RK.cowUsers()); // 推荐池人数
		if (size == null || size < 1) {
			return new ReMsg(ReCode.FAIL);
		} else {
			index = null == index ? 0 : index;
			int end = index + HOME_PAGE_RECOMMEND_SIZE;
			List<String> list = getRedisTemplate().opsForList().range(RK.cowUsers(), index, end - 1);
			List<RecommendUserInfoVO> vol = new ArrayList<>();
			for (String json : list) {
				RecommendUserInfoVO rui = JSONUtil.jsonToBean(json, RecommendUserInfoVO.class);
				vol.add(rui);
			}
			GameUserList ret = new GameUserList();
			ret.setList(vol);
			ret.setIndex(end);
			return new ReMsg(ReCode.OK, ret);
		}
	}

	/** 推荐德州用户 */
	public ReMsg recommendTexasUsers(long uid, Integer index) {
		Long size = getRedisTemplate().opsForList().size(RK.texasUsers()); // 推荐池人数
		if (size == null || size < 1) {
			return new ReMsg(ReCode.FAIL);
		} else {
			index = null == index ? 0 : index;
			int end = index + HOME_PAGE_RECOMMEND_SIZE;
			List<String> list = getRedisTemplate().opsForList().range(RK.texasUsers(), index, end - 1);
			List<RecommendUserInfoVO> vol = new ArrayList<>();
			for (String json : list) {
				RecommendUserInfoVO rui = JSONUtil.jsonToBean(json, RecommendUserInfoVO.class);
				vol.add(rui);
			}
			GameUserList ret = new GameUserList();
			ret.setList(vol);
			ret.setIndex(end);
			return new ReMsg(ReCode.OK, ret);
		}
	}

	// /** 双人游戏对战记录 */
	// public ReMsg gameCpUsersHistory(long myid, Integer gameType, Integer sex,
	// Integer page, Integer size) {
	// List<DBObject> cpUser = this.findGameCPUser(myid, gameType, sex, page,
	// size);
	// if (!cpUser.isEmpty()) {
	// for (DBObject dbo : cpUser) {
	// try {
	// Long uid = DboUtil.getLong(dbo, "uid");
	// DBObject rdbo = userService.findById(uid);
	// if (rdbo != null) {
	// dbo.put("nickname", DboUtil.getString(rdbo, "nickname"));
	// dbo.put("sex", DboUtil.getInt(rdbo, "sex"));
	// dbo.put("avatar", DboUtil.getString(rdbo, "avatar"));
	// dbo.put("vip", null == DboUtil.getInt(rdbo, "vip") ? 0 :
	// DboUtil.getInt(rdbo, "vip"));
	// dbo.put("point", DboUtil.getInt(rdbo, "point"));
	// dbo.put("friendWorth",
	// null == DboUtil.getInt(rdbo, "friendWorth") ? 1 : DboUtil.getInt(rdbo,
	// "friendWorth"));
	// dbo.put("ralationShip", isNotFriend(myid, uid));
	// }
	// } catch (Exception e) {
	// log.error("查询游戏好友信息", e);
	// }
	// }
	// }
	// return new ReMsg(ReCode.OK, cpUser);
	// }

	/** 首页-推荐用户 */
	public ReMsg recommendUsers(long uid, Integer index, Integer gameIndex) {
		String flag = getRedisTemplate().opsForValue().get(RK.recommendFlag());// 最新数据标识
		int type = flag != null ? Integer.parseInt(flag) : 1;
		int sex = DboUtil.getInt(userService.findById(uid), "sex") - 1 > 0 ? MALE : FEMALE; // 推荐异性
		Long size = getRedisTemplate().opsForZSet().zCard(RK.recommendUsers(type, sex)); // 推荐池人数
		index = null == index || 0 > index ? 0 : index;
		gameIndex = null == gameIndex || 0 > gameIndex ? 0 : gameIndex;
		GameUserList ret = null;
		if (size == null || size < 1) {
			return new ReMsg(ReCode.FAIL);
		} else {
			if (index > size - HOME_PAGE_RECOMMEND_SIZE + 1) { // 尽量保证四条数据
				index = 0;
			}
			List<RecommendUserInfoVO> list = new ArrayList<>();
			index = addRecommendList(type, uid, index, sex, size, list, HOME_PAGE_RECOMMEND_SIZE,
					HOME_PAGE_RECOMMEND_SIZE);
			RecommendUserInfoVO vo = null;
			try {
				DBObject dbo = findLastGameOneUser(uid, gameIndex, sex);
				if (null != dbo) {
					GameUserHistory gh = DboUtil.toBean(dbo, GameUserHistory.class);
					Long rid = gh.getRelatedUid();
					int gameType = gh.getGameType();
					DBObject du = userService.findById(rid);
					if (null != du) {
						User user = DboUtil.toBean(du, User.class);
						if (null == user.getAvatar()) {
							user.setAvatar(DEFAULT_AVATA);
						}
						vo = new RecommendUserInfoVO(rid, ActivityType.getActivityName(gameType), user.getVip(),
								user.getPoint(), user.getAvatar(), user.getPersonLabel(), user.getNickname(), PLAYED,
								user.getSex(), 0 == user.getFriendWorth() ? 1 : user.getFriendWorth());
					}
				}
			} catch (Exception e) {
				log.error("查询玩过好友失败", e);
			}
			if (null != vo && list.size() == HOME_PAGE_RECOMMEND_SIZE && !list.contains(vo)) {
				list.remove(HOME_PAGE_RECOMMEND_SIZE - 1);
				list.add(vo);
				Collections.shuffle(list, new Random());
			}
			ret = new GameUserList();
			ret.setList(list);
			ret.setGameIndex(++gameIndex);
			ret.setIndex(index); // 返回客户端的刷新索引
		}
		return new ReMsg(ReCode.OK, ret);
	}

	/** 获取推荐列表 */
	private int addRecommendList(int type, long uid, int index, int sex, Long size, List<RecommendUserInfoVO> list,
			int count, int totalCount) {
		Double max = 100000000D;
		Set<TypedTuple<String>> set = getRedisTemplate().opsForZSet()
				.reverseRangeByScoreWithScores(RK.recommendUsers(type, sex), 0, max, index, count);
		if (set != null && !set.isEmpty()) {
			for (TypedTuple<String> t : set) {
				String json = t.getValue();
				RecommendUserInfoVO rui = JSONUtil.jsonToBean(json, RecommendUserInfoVO.class);
				if (isNotFriend(uid, rui.getUid()) && uid != rui.getUid()) {
					list.add(rui);
				}
			}
			if (list.size() < totalCount) {
				for (int i = 0; i < 6; i++) {
					int ret = addRecommendList(type, uid, index + count, sex, size, list, totalCount - list.size(),
							totalCount);
					if (list.size() == totalCount || i == 5)
						return ret;
				}
			} else {
				return index + count;
			}
		}
		return index + count;
	}

	/** 校验是否是好友 */
	public boolean isNotFriend(long uid, long rid) {
		// 用户关系 0 陌生人 1 请求加好友 2 被请求加好友 3黑名单 4好友
		int r = (int) relationshipService.getRelation(rid).getData();
		return r != 3 && r != 4 && r != 1;
	}

	/** 查询好友列表 */
	public List<Long> queryFriends(long uid) {
		// 用户关系 0 陌生人 1 请求加好友 2 被请求加好友 3黑名单 4好友
		List<DBObject> list = relationshipService.queryIds(uid);
		List<Long> ids = new ArrayList<>();
		for (DBObject dbo : list) {
			ids.add(DboUtil.getLong(dbo, "rid"));
		}
		return ids;
	}

	/** 计算新老用户 */
	public boolean isGreen(Long creatTime) {
		return System.currentTimeMillis() - creatTime < 3 * 24 * 60 * 60 * 1000;
	}

	/** 好友推荐 */
	public ReMsg recommendFriends(long uid, Integer maleIndex, Integer femaleIndex, Integer maleBook,
			Integer femaleBook) {
		int maleCount = 0;
		int femaleCount = 0;
		int needMaleCount = 0;
		int needFemaleCount = 0;
		List<DBObject> maleUsers = new ArrayList<>();
		List<DBObject> femaleUsers = new ArrayList<>();
		maleIndex = null == maleIndex ? 0 : maleIndex;
		femaleIndex = null == femaleIndex ? 0 : femaleIndex;
		maleBook = null == maleBook ? 0 : maleBook;
		femaleBook = null == femaleBook ? 0 : femaleBook;
		int sex = DboUtil.getInt(userService.findById(uid), "sex");
		if (sex == MALE) {
			maleCount = SAME_SEX_SIZE;
			femaleCount = OTHER_SEX_SIZE;
		} else if (sex == FEMALE) {
			femaleCount = SAME_SEX_SIZE;
			maleCount = OTHER_SEX_SIZE;
		}
		if (maleBook == 0) {
			maleUsers = findLastGameUser(uid, maleIndex, MALE, maleCount);
			maleUsers = null == maleUsers ? new ArrayList<DBObject>() : maleUsers;
			if (maleUsers.size() < maleCount) {
				// needMaleCount = maleCount - maleUsers.size();
				maleBook = 1;
				maleIndex = 0;
			} else {
				maleIndex = maleIndex + maleUsers.size();
			}
		} // else {
		needMaleCount = maleCount;
		// }
		if (femaleBook == 0) {
			femaleUsers = findLastGameUser(uid, femaleIndex, FEMALE, femaleCount);
			femaleUsers = null == femaleUsers ? new ArrayList<DBObject>() : femaleUsers;
			if (femaleUsers.size() < femaleCount) {
				// needFemaleCount = femaleCount - femaleUsers.size();
				femaleBook = 1;
				femaleIndex = 0;
			} else {
				femaleIndex = femaleIndex + femaleUsers.size();
			}
		} // else {
		needFemaleCount = femaleCount;
		// }
		maleUsers.addAll(femaleUsers);
		String flag = getRedisTemplate().opsForValue().get(RK.recommendFlag());// 最新数据标识
		int type = flag != null ? Integer.parseInt(flag) : 1;
		Long size = getRedisTemplate().opsForZSet().zCard(RK.recommendUsers(type, sex)); // 推荐池人数
		List<RecommendUserInfoVO> maleList = new ArrayList<>();
		List<RecommendUserInfoVO> femaleList = new ArrayList<>();
		if (maleBook == 1) {
			maleIndex = addRecommendList(type, uid, maleIndex, MALE, size, maleList, needMaleCount, needMaleCount);
		}
		if (femaleBook == 1) {
			femaleIndex = addRecommendList(type, uid, femaleIndex, FEMALE, size, femaleList, needFemaleCount,
					needFemaleCount);
		}
		maleList.addAll(femaleList);
		GameFriendsList ret = new GameFriendsList();
		List<RecommendUserInfoVO> temp = new ArrayList<>();
		List<RecommendUserInfoVO> list = new ArrayList<>();
		if (!maleUsers.isEmpty()) {
			for (DBObject dbo : maleUsers) {
				try {
					Long relatedUid = DboUtil.getLong(dbo, "relatedUid");
					DBObject rdbo = userService.findById(relatedUid);
					if (rdbo != null) {
						RecommendUserInfoVO vo = new RecommendUserInfoVO();
						vo.setUid(DboUtil.getLong(rdbo, "_id"));
						vo.setGameType(ActivityType.getActivityName(DboUtil.getInt(dbo, "gameType")));
						vo.setCover(DboUtil.getString(rdbo, "cover"));
						vo.setNickname(DboUtil.getString(rdbo, "nickname"));
						vo.setType(PLAYED);
						vo.setSex(DboUtil.getInt(rdbo, "sex"));
						vo.setAvatar(DboUtil.getString(rdbo, "avatar"));
						vo.setVip(null == DboUtil.getInt(rdbo, "vip") ? 0 : DboUtil.getInt(rdbo, "vip"));
						vo.setPoint(DboUtil.getInt(rdbo, "point"));
						vo.setPersonLabel(DboUtil.getString(rdbo, "personLabel"));
						vo.setFriendWorth(
								null == DboUtil.getInt(rdbo, "friendWorth") ? 1 : DboUtil.getInt(rdbo, "friendWorth"));
						temp.add(vo);
					}
				} catch (Exception e) {
					log.error("查询游戏好友信息", e);
				}
			}
		}
		for (int i = 0; i < maleList.size(); i++) {
			RecommendUserInfoVO vo = maleList.get(i);
			if (!temp.contains(vo)) {
				temp.add(vo);
			}
		}
		if (temp.size() > ADD_FRIENDS_SIZE) {
			list = temp.subList(0, ADD_FRIENDS_SIZE);
		} else {
			list = temp;
		}
		ret.setList(list.size() == ADD_FRIENDS_SIZE ? list : null);
		ret.setMaleIndex(maleIndex);
		ret.setFemaleIndex(femaleIndex);
		ret.setMaleBook(maleBook);
		ret.setFemaleBook(femaleBook);
		return new ReMsg(ReCode.OK, ret);
	}

	/** 缓存db数据到cache */
	public void pushDate2Cache(int type) {
		Map<Long, RecommendUserInfoVO> maleMap = new HashMap<>();
		Map<Long, RecommendUserInfoVO> femaleMap = new HashMap<>();
		List<Actor> gameActors = new ArrayList<>();
		List<DBObject> maleUserDBOs = new ArrayList<>();
		List<DBObject> femaleUserDOBs = new ArrayList<>();
		boolean maleContinue = true;
		boolean femaleContinue = true;
		int mgcount = 0;
		int fgcount = 0;
		List<Long> keys = roomDefaultService.getRoomIds();
		Collections.shuffle(keys, new Random());
		k: for (Long key : keys) {
			try {
				Map<Object, Object> map = super.getRedisTemplate().opsForHash().entries(RK.roomUser(key));
				for (Object e : map.values()) {
					try {
						Actor actor = JSONUtil.jsonToBean((String) e, Actor.class);
						if (actor != null && !actor.isRobit() && StringUtils.isNotEmpty(actor.getAvatar())) {
							if (actor.getSex() == MALE && maleContinue) {
								gameActors.add(actor);
								mgcount++;
								if (mgcount >= 200) {
									maleContinue = false;
								}
							} else if (actor.getSex() == FEMALE && femaleContinue) {
								gameActors.add(actor);
								fgcount++;
								if (fgcount >= 200) {
									femaleContinue = false;
								}
							}
						}
						if (!maleContinue && !femaleContinue)
							break k;
					} catch (Exception exc) {
						log.error("json trans error", exc);
					}
				}
			} catch (Exception exc) {
				log.error("json trans error", exc);
			}
		}
		int maleGameCount = 1;
		int femaleGameCount = 1;
		super.getRedisTemplate().delete(RK.wolfUsers());
		super.getRedisTemplate().delete(RK.cowUsers());
		super.getRedisTemplate().delete(RK.texasUsers());
		for (Actor ac : gameActors) {
			try {
				User user = DboUtil.toBean(userService.findById(ac.getUid()), User.class);
				if (null != user) {
					if (null == user.getAvatar()) {
						user.setAvatar(DEFAULT_AVATA);
					}
					RecommendUserInfoVO vo = new RecommendUserInfoVO(user.get_id(), ac.getGame(), user.getVip(),
							user.getPoint(), user.getAvatar(), user.getPersonLabel(), user.getNickname(), IN_GAME,
							user.getSex(), 0 == user.getFriendWorth() ? 1 : user.getFriendWorth());
					if (user.getSex() == MALE) {
						vo.setScore(GAMING_SCORE - maleGameCount);
						maleMap.put(user.get_id(), vo);
						maleGameCount++;
					} else if (user.getSex() == FEMALE) {
						vo.setScore(GAMING_SCORE - femaleGameCount);
						femaleMap.put(user.get_id(), vo);
						femaleGameCount++;
					}
					// if (ac.getGame().indexOf("狼") > -1) {
					// super.getRedisTemplate().opsForList().leftPush(RK.wolfUsers(),
					// JSONUtil.beanToJson(vo));
					// }
				}
			} catch (Exception e) {
				log.error("dbo trans error", e);
			}
		}
		if (maleContinue || femaleContinue) {
			int maleOnlineCount = 1;
			int femaleOnlineCount = 1;
			o: for (int i = 0; i < 10; i++) {
				try {
					List<DBObject> onlineList = userService.findOnlineUser(i + 1, 100);
					if (onlineList != null && !onlineList.isEmpty()) {
						for (DBObject dbo : onlineList) {
							if (dbo != null) {
								try {
									User user = DboUtil.toBean(dbo, User.class);
									if (user != null && user.getSex() == MALE
											&& StringUtils.isNotEmpty(user.getAvatar()) && maleContinue) {
										RecommendUserInfoVO vo = new RecommendUserInfoVO();
										vo.setUid(user.get_id());
										if (!maleMap.keySet().contains(vo.getUid())) {
											vo.setGameType(null);
											vo.setCover(user.getCover());
											vo.setNickname(user.getNickname());
											vo.setType(isGreen(user.getCreateTime()) ? IS_GREEN : IS_DRIDER);
											vo.setSex(user.getSex());
											vo.setVip(user.getVip());
											vo.setPoint(user.getPoint());
											vo.setPersonLabel(user.getPersonLabel());
											if (null == user.getAvatar()) {
												user.setAvatar(DEFAULT_AVATA);
											}
											vo.setFriendWorth(0 == user.getFriendWorth() ? 1 : user.getFriendWorth());
											vo.setAvatar(user.getAvatar());
											vo.setScore((isGreen(user.getCreateTime()) ? ONLINE3_SCORE : ONLINE7_SCORE)
													- maleOnlineCount);
											maleMap.put(user.get_id(), vo);
											maleOnlineCount++;
										} else {
											// RecommendUserInfoVO temp =
											// maleMap.get(user.get_id());
											// temp.setScore(temp.getScore()
											// + (isGreen(user.getCreateTime())
											// ? ONLINE3_SCORE : ONLINE7_SCORE)
											// - maleOnlineCount);
											// maleMap.put(user.get_id(), temp);
										}
										if (maleMap.keySet().size() >= 200) {
											maleContinue = false;
										}
									} else if (user != null && user.getSex() == FEMALE
											&& StringUtils.isNotEmpty(user.getAvatar()) && femaleContinue) {
										RecommendUserInfoVO vo = new RecommendUserInfoVO();
										vo.setUid(user.get_id());
										if (!femaleMap.keySet().contains(vo.getUid())) {
											vo.setGameType(null);
											vo.setSex(user.getSex());
											vo.setCover(user.getCover());
											vo.setNickname(user.getNickname());
											vo.setVip(user.getVip());
											vo.setPoint(user.getPoint());
											vo.setPersonLabel(user.getPersonLabel());
											if (null == user.getAvatar()) {
												user.setAvatar(DEFAULT_AVATA);
											}
											vo.setFriendWorth(0 == user.getFriendWorth() ? 1 : user.getFriendWorth());
											vo.setAvatar(user.getAvatar());
											vo.setType(isGreen(user.getCreateTime()) ? IS_GREEN : IS_DRIDER);
											vo.setScore((isGreen(user.getCreateTime()) ? ONLINE3_SCORE : ONLINE7_SCORE)
													- femaleOnlineCount);
											femaleMap.put(user.get_id(), vo);
											femaleOnlineCount++;
										} else {
											// RecommendUserInfoVO temp =
											// femaleMap.get(user.get_id());
											// temp.setScore(temp.getScore()
											// + (isGreen(user.getCreateTime())
											// ? ONLINE3_SCORE : ONLINE7_SCORE)
											// - femaleOnlineCount);
											// femaleMap.put(user.get_id(),
											// temp);
										}
										if (femaleMap.keySet().size() >= 200) {
											femaleContinue = false;
										}
									}
									if (!maleContinue && !femaleContinue)
										break o;
								} catch (Exception exc) {
									log.error("onlineList error", exc);
								}
							}
						}
					}
				} catch (Exception e) {
					log.error("onlineList error", e);
				}
			}
		}
		if (maleContinue || femaleContinue) {
			int maleOfflineSize = 200;
			int maleOfflineCount = 1;
			maleUserDBOs = userService.searchLastRegUsers(maleOfflineSize, MALE);
			Collections.shuffle(maleUserDBOs, new Random());
			for (DBObject dbo : maleUserDBOs) {
				try {
					User user = DboUtil.toBean(dbo, User.class);
					if (null != user && StringUtils.isNotEmpty(user.getAvatar())) {
						RecommendUserInfoVO vo = new RecommendUserInfoVO();
						vo.setUid(user.get_id());
						if (!maleMap.keySet().contains(vo.getUid())) {
							vo.setGameType(null);
							vo.setSex(user.getSex());
							vo.setCover(user.getCover());
							vo.setVip(user.getVip());
							vo.setPoint(user.getPoint());
							vo.setPersonLabel(user.getPersonLabel());
							if (null == user.getAvatar()) {
								user.setAvatar(DEFAULT_AVATA);
							}
							vo.setFriendWorth(0 == user.getFriendWorth() ? 1 : user.getFriendWorth());
							vo.setAvatar(user.getAvatar());
							vo.setNickname(user.getNickname());
							vo.setType(isGreen(user.getCreateTime()) ? IS_GREEN : IS_DRIDER);
							vo.setScore((isGreen(user.getCreateTime()) ? OFFLINE3_SCORE : OFFLINE7_SCORE)
									- maleOfflineCount);
							maleMap.put(user.get_id(), vo);
							maleOfflineCount++;
						} else {
							// RecommendUserInfoVO temp =
							// maleMap.get(user.get_id());
							// temp.setScore(
							// temp.getScore() + (isGreen(user.getCreateTime())
							// ? OFFLINE3_SCORE : OFFLINE7_SCORE)
							// - maleOfflineCount);
							// maleMap.put(user.get_id(), temp);
						}
						if (maleMap.size() >= 200)
							break;
					}
				} catch (Exception e) {
					log.error("dbo trans error", e);
				}
			}
			int femaleOfflineSize = 200;
			int femaleOfflineCount = 1;
			femaleUserDOBs = userService.searchLastRegUsers(femaleOfflineSize, FEMALE);
			Collections.shuffle(femaleUserDOBs, new Random());
			for (DBObject dbo : femaleUserDOBs) {
				try {
					User user = DboUtil.toBean(dbo, User.class);
					if (null != user && StringUtils.isNotEmpty(user.getAvatar())) {
						RecommendUserInfoVO vo = new RecommendUserInfoVO();
						vo.setUid(user.get_id());
						if (!femaleMap.keySet().contains(vo.getUid())) {
							vo.setGameType(null);
							vo.setSex(user.getSex());
							vo.setCover(user.getCover());
							vo.setVip(user.getVip());
							vo.setPoint(user.getPoint());
							vo.setPersonLabel(user.getPersonLabel());
							if (null == user.getAvatar()) {
								user.setAvatar(DEFAULT_AVATA);
							}
							vo.setFriendWorth(0 == user.getFriendWorth() ? 1 : user.getFriendWorth());
							vo.setAvatar(user.getAvatar());
							vo.setNickname(user.getNickname());
							vo.setType(isGreen(user.getCreateTime()) ? IS_GREEN : IS_DRIDER);
							vo.setScore((isGreen(user.getCreateTime()) ? OFFLINE3_SCORE : OFFLINE7_SCORE)
									- femaleOfflineCount);
							femaleMap.put(user.get_id(), vo);
							femaleOfflineCount++;

						} else {
							// RecommendUserInfoVO temp =
							// femaleMap.get(user.get_id());
							// temp.setScore(
							// temp.getScore() + (isGreen(user.getCreateTime())
							// ? OFFLINE3_SCORE : OFFLINE7_SCORE)
							// - femaleOfflineCount);
							// femaleMap.put(user.get_id(), temp);
						}
						if (femaleMap.size() >= 200)
							break;
					}
				} catch (Exception e) {
					log.error("dbo trans error", e);
				}
			}
		}
		super.getRedisTemplate().opsForZSet().removeRange(RK.recommendUsers(type, MALE), 0, 1000);
		super.getRedisTemplate().opsForZSet().removeRange(RK.recommendUsers(type, FEMALE), 0, 1000);
		for (RecommendUserInfoVO vo : maleMap.values()) {
			try {
				super.getRedisTemplate().opsForZSet().add(RK.recommendUsers(type, MALE), JSONUtil.beanToJson(vo),
						vo.getScore());
			} catch (Exception e) {
				log.error("json2b trans error", e);
			}
		}
		for (RecommendUserInfoVO vo : femaleMap.values()) {
			try {
				super.getRedisTemplate().opsForZSet().add(RK.recommendUsers(type, FEMALE), JSONUtil.beanToJson(vo),
						vo.getScore());
			} catch (Exception e) {
				log.error("json2b trans error", e);
			}
		}
		// 缓存狼人杀推荐池子数据
		List<RecommendUserInfoVO> wolfList = werewolfKillService.find24hWolfPlayer().stream().map(e -> {
			RecommendUserInfoVO vo = null;
			if (null != e) {
				DBObject dbo = userService.findById(e);
				if (null != dbo) {
					User user = DboUtil.toBean(dbo, User.class);
					vo = new RecommendUserInfoVO(user.get_id(), "狼人杀", user.getVip(), user.getPoint(), user.getAvatar(),
							user.getPersonLabel(), user.getNickname(), IN_GAME, user.getSex(),
							0 == user.getFriendWorth() ? 1 : user.getFriendWorth());
				}
			}
			return vo;
		}).filter(e -> null != e).collect(Collectors.toList());
		if (!wolfList.isEmpty()) {
			List<String> temp = new ArrayList<>();
			for (RecommendUserInfoVO vo : wolfList) {
				String json = JSONUtil.beanToJson(vo);
				temp.add(json);
			}
			super.getRedisTemplate().opsForList().leftPushAll(RK.wolfUsers(), temp.toArray(new String[] {}));
		}

		// 缓存牛牛推荐池子数据
		List<RecommendUserInfoVO> cowList = cowService.find24hCowPlayer().stream().map(e -> {
			RecommendUserInfoVO vo = null;
			if (null != e) {
				DBObject dbo = userService.findById(e);
				if (null != dbo) {
					User user = DboUtil.toBean(dbo, User.class);
					vo = new RecommendUserInfoVO(user.get_id(), "牛牛", user.getVip(), user.getPoint(), user.getAvatar(),
							user.getPersonLabel(), user.getNickname(), IN_GAME, user.getSex(),
							0 == user.getFriendWorth() ? 1 : user.getFriendWorth());
				}
			}
			return vo;
		}).filter(e -> null != e).collect(Collectors.toList());
		if (!cowList.isEmpty()) {
			List<String> temp = new ArrayList<>();
			for (RecommendUserInfoVO vo : cowList) {
				String json = JSONUtil.beanToJson(vo);
				temp.add(json);
			}
			super.getRedisTemplate().opsForList().leftPushAll(RK.cowUsers(), temp.toArray(new String[] {}));
		}

		// 缓存德州推荐池子数据
		List<RecommendUserInfoVO> texasList = texasService.find24hTexasPlayer().stream().map(e -> {
			RecommendUserInfoVO vo = null;
			if (null != e) {
				DBObject dbo = userService.findById(e);
				if (null != dbo) {
					User user = DboUtil.toBean(dbo, User.class);
					vo = new RecommendUserInfoVO(user.get_id(), "德州扑克", user.getVip(), user.getPoint(),
							user.getAvatar(), user.getPersonLabel(), user.getNickname(), IN_GAME, user.getSex(),
							0 == user.getFriendWorth() ? 1 : user.getFriendWorth());
				}
			}
			return vo;
		}).filter(e -> null != e).collect(Collectors.toList());
		if (!texasList.isEmpty()) {
			List<String> temp = new ArrayList<>();
			for (RecommendUserInfoVO vo : texasList) {
				String json = JSONUtil.beanToJson(vo);
				temp.add(json);
			}
			super.getRedisTemplate().opsForList().leftPushAll(RK.texasUsers(), temp.toArray(new String[] {}));
		}
	}

	/** 统计游戏好友推荐数据 */
	@SuppressWarnings("unchecked")
	public void statisticsGameLog() {
		long now = System.currentTimeMillis();
		long hDate = now - GAME_DATE;
		BasicDBObject q = new BasicDBObject();
		q.append("updateTime", new BasicDBObject("$gt", hDate));
		List<DBObject> drawSomethings = super.getC(DocName.DRAWSOMETHING).find(q).toArray();
		List<DBObject> dices = super.getC(DocName.DICE).find(q).toArray();
		List<DBObject> unders = super.getC(DocName.UNDERCOVER).find(q).toArray();
		List<DBObject> werewolves = super.getC(DocName.WEREWOLF_KILL).find(q).toArray();
		List<DBObject> wars = super.getC(DocName.SCHOOL_WAR).find(q).toArray();
		List<DBObject> chesss = super.getC(DocName.ANIMAL_CHESS).find(q).toArray();
		for (DBObject drawSomething : drawSomethings) {
			Map<String, Integer> actors = DboUtil.get(drawSomething, "actors", Map.class);
			Long updateTime = DboUtil.getLong(drawSomething, "updateTime");
			Iterator<String> it = actors.keySet().iterator();
			while (it.hasNext()) {
				String id = null;
				try {
					id = it.next();
					DBObject dbo = userService.findById(Long.parseLong(id));
					if (null == dbo || DboUtil.getInt(userService.findById(Long.parseLong(id)), "role") > 2) {
						it.remove();
					}
				} catch (Exception e) {
					log.error("游戏过玩家校验角色失败" + id, e);
				}
			}
			String[] arr = actors.keySet().toArray(new String[] {});
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr.length; j++) {
					if (arr[i] != arr[j]) {
						try {
							saveAs22(arr[i], arr[j], updateTime, ActivityType.DRAW_SOMETHING.getType());
						} catch (Exception e) {
							log.error("保存失败", e);
						}
					}
				}
			}
		}

		for (DBObject under : unders) {
			List<Long> actors = DboUtil.get(under, "actors", List.class);
			Long updateTime = DboUtil.getLong(under, "updateTime");
			Iterator<Long> it = actors.iterator();
			while (it.hasNext()) {
				Long id = 0l;
				try {
					id = it.next();
					DBObject dbo = userService.findById(id);
					if (null == dbo || DboUtil.getInt(dbo, "role") > 2) {
						it.remove();
					}
				} catch (Exception e) {
					log.error("游戏过玩家校验角色失败" + id, e);
				}
			}
			Long[] arr = actors.toArray(new Long[] {});
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr.length; j++) {
					if (arr[i] != arr[j]) {
						try {
							saveAs22(arr[i].toString(), arr[j].toString(), updateTime,
									ActivityType.UNDERCOVER.getType());
						} catch (Exception e) {
							log.error("保存失败", e);
						}
					}
				}
			}
		}

		for (DBObject dice : dices) {
			Map<String, Integer> actors = DboUtil.get(dice, "actors", Map.class);
			Long updateTime = DboUtil.getLong(dice, "updateTime");
			Iterator<String> it = actors.keySet().iterator();
			while (it.hasNext()) {
				String id = null;
				try {
					id = it.next();
					DBObject dbo = userService.findById(Long.parseLong(id));
					if (null == dbo || DboUtil.getInt(userService.findById(Long.parseLong(id)), "role") > 2) {
						it.remove();
					}
				} catch (Exception e) {
					log.error("游戏过玩家校验角色失败" + id, e);
				}
			}
			String[] arr = actors.keySet().toArray(new String[] {});
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr.length; j++) {
					if (arr[i] != arr[j]) {
						try {
							saveAs22(arr[i], arr[j], updateTime, ActivityType.DICE.getType());
						} catch (Exception e) {
							log.error("保存失败", e);
						}
					}
				}
			}
		}

		for (DBObject werewolf : werewolves) {
			Map<String, Integer> actors = DboUtil.get(werewolf, "actors", Map.class);
			Long updateTime = DboUtil.getLong(werewolf, "updateTime");
			Iterator<String> it = actors.keySet().iterator();
			while (it.hasNext()) {
				String id = null;
				try {
					id = it.next();
					DBObject dbo = userService.findById(Long.parseLong(id));
					if (null == dbo || DboUtil.getInt(userService.findById(Long.parseLong(id)), "role") > 2) {
						it.remove();
					}
				} catch (Exception e) {
					log.error("游戏过玩家校验角色失败" + id, e);
				}
			}
			String[] arr = actors.keySet().toArray(new String[] {});
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr.length; j++) {
					if (arr[i] != arr[j]) {
						try {
							saveAs22(arr[i], arr[j], updateTime, ActivityType.WEREWOLF.getType());
						} catch (Exception e) {
							log.error("保存失败", e);
						}
					}
				}
			}
		}
	}

	// /** 统计CP游戏对战记录 */
	// @SuppressWarnings("unchecked")
	// public void gameCPFightLog() {
	// long time = System.currentTimeMillis() - 3 * Const.MINUTE;
	// BasicDBObject q = new BasicDBObject();
	// q.append("updateTime", new BasicDBObject("$gt", time));
	// List<DBObject> wars = super.getC(DocName.SCHOOL_WAR).find(q).toArray();
	// List<DBObject> chesss =
	// super.getC(DocName.ANIMAL_CHESS).find(q).toArray();
	//
	// for (DBObject war : wars) {
	// Map<String, Integer> actors = DboUtil.get(war, "actors", Map.class);
	// Long updateTime = DboUtil.getLong(war, "updateTime");
	// Long winner = DboUtil.getLong(war, "winner");
	// Iterator<String> it = actors.keySet().iterator();
	// while (it.hasNext()) {
	// String id = null;
	// try {
	// id = it.next();
	// DBObject dbo = userService.findById(Long.parseLong(id));
	// if (null == dbo ||
	// DboUtil.getInt(userService.findById(Long.parseLong(id)), "role") > 2) {
	// it.remove();
	// }
	// } catch (Exception e) {
	// log.error("游戏过玩家校验角色失败" + id, e);
	// }
	// }
	// String[] arr = actors.keySet().toArray(new String[] {});
	// for (int i = 0; i < arr.length; i++) {
	// for (int j = 0; j < arr.length; j++) {
	// if (arr[i] != arr[j]) {
	// try {
	// saveCP(Long.parseLong(arr[i]), Long.parseLong(arr[j]), winner,
	// updateTime,
	// ActivityType.SCHOOL_WAR.getType());
	// } catch (Exception e) {
	// log.error("保存失败", e);
	// }
	// }
	// }
	// }
	// }
	//
	// for (DBObject chess : chesss) {
	// Map<String, Integer> actors = DboUtil.get(chess, "actors", Map.class);
	// Long updateTime = DboUtil.getLong(chess, "updateTime");
	// Long winner = DboUtil.getLong(chess, "winner");
	// Iterator<String> it = actors.keySet().iterator();
	// while (it.hasNext()) {
	// String id = null;
	// try {
	// id = it.next();
	// DBObject dbo = userService.findById(Long.parseLong(id));
	// if (null == dbo ||
	// DboUtil.getInt(userService.findById(Long.parseLong(id)), "role") > 2) {
	// it.remove();
	// }
	// } catch (Exception e) {
	// log.error("游戏过玩家校验角色失败" + id, e);
	// }
	// }
	// String[] arr = actors.keySet().toArray(new String[] {});
	// for (int i = 0; i < arr.length; i++) {
	// for (int j = 0; j < arr.length; j++) {
	// if (arr[i] != arr[j]) {
	// try {
	// saveCP(Long.parseLong(arr[i]), Long.parseLong(arr[j]), winner,
	// updateTime,
	// ActivityType.ANIMAL_CHESS.getType());
	// } catch (Exception e) {
	// log.error("保存失败", e);
	// }
	// }
	// }
	// }
	// }
	// }

	/** 两两组合保存 */
	public void saveAs22(String primUserId, String relatedUid, Long updateTime, int gameType) {
		BasicDBObject q = new BasicDBObject();
		q.append("primUserId", Long.parseLong(primUserId));
		q.append("relatedUid", Long.parseLong(relatedUid));
		if (super.getC(DocName.GAME_USER_HISTORY).find(q).toArray().size() > 0) {
			BasicDBObject u = new BasicDBObject("updateTime", updateTime).append("gameType", gameType);
			super.getC(DocName.GAME_USER_HISTORY).update(q, new BasicDBObject("$set", u), true, false);
		} else {
			int relatedSex = DboUtil.getInt(userService.findById(Long.parseLong(relatedUid)), "sex");
			GameUserHistory g = new GameUserHistory(super.getNextId(DocName.GAME_USER_HISTORY),
					Long.parseLong(primUserId), Long.parseLong(relatedUid), updateTime, gameType, relatedSex);
			super.getMongoTemplate().save(g);
		}
	}

	// /** CP游戏 save */
	// public void saveCP(long myid, long uid, Long win, long updateTime, int
	// gameType) {
	// String fields = "";
	// if (null == win || win == 0) {
	// fields = "peace";
	// } else if (myid == win) {
	// fields = "win";
	// } else {
	// fields = "lost";
	// }
	// log.error(">>>>>>>>>cpGame save>>>>>" + fields);
	// BasicDBObject inc = new BasicDBObject("$inc", new BasicDBObject(fields,
	// Integer.valueOf(1))).append("$set",
	// new BasicDBObject("updateTime", updateTime).append("sex",
	// DboUtil.getInt(userService.findById(uid), "sex")));
	// super.getMongoTemplate().getCollection(DocName.GAME_CP_HISTORY)
	// .findAndModify(new BasicDBObject("_id", myid + "-" + uid).append("myid",
	// myid).append("uid", uid)
	// .append("gameType", gameType), null, null, false, inc, true, true);
	// }
	//
	// /** CP游戏 find */
	// public List<DBObject> findGameCPUser(long myid, Integer gameType, Integer
	// sex, Integer page, Integer size) {
	// BasicDBObject q = new BasicDBObject();
	// if (null != sex) {
	// q.append("sex", sex);
	// }
	// if (null != gameType) {
	// q.append("gameType", gameType);
	// }
	// q.append("myid", myid);
	// return getC(DocName.GAME_CP_HISTORY).find(q).sort(new
	// BasicDBObject("updateTime", -1))
	// .skip(getStart(page, size)).limit(super.getSize(size)).toArray();
	// }

	public List<DBObject> findLastGameUser(long uid, int gameIndex, int sex, int limit) {
		BasicDBObject q = new BasicDBObject();
		q.append("primUserId", uid);
		q.append("relatedSex", sex);
		q.append("updateTime", new BasicDBObject("$gt", System.currentTimeMillis() - OFFLINE_USER_TIME));
		return getC(DocName.GAME_USER_HISTORY).find(q).sort(new BasicDBObject("updateTime", -1)).skip(gameIndex)
				.limit(limit).toArray().stream()
				.filter(e -> e != null && isNotFriend(uid, DboUtil.getLong(e, "relatedUid")))
				.collect(Collectors.toList());
	}

	public DBObject findLastGameOneUser(long uid, int gameIndex, int sex) {
		DBObject dbo = null;
		BasicDBObject q = new BasicDBObject();
		q.append("primUserId", uid);
		q.append("relatedSex", sex);
		q.append("updateTime", new BasicDBObject("$gt", System.currentTimeMillis() - OFFLINE_USER_TIME));
		List<DBObject> dbos = super.getC(DocName.GAME_USER_HISTORY).find(q).sort(new BasicDBObject("updateTime", -1))
				.limit(1).skip(gameIndex).toArray();
		if (null != dbos && !dbos.isEmpty()) {
			dbo = dbos.get(0);
			return isNotFriend(uid, DboUtil.getLong(dbo, "relatedUid")) ? dbo : null;
		}
		return null;
	}

}
