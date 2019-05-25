package com.zb.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.LoginType;
import com.zb.common.Constant.ReCode;
import com.zb.common.crypto.MDUtil;
import com.zb.common.http.HttpClientUtil;
import com.zb.common.http.UserAgent;
import com.zb.common.http.UserAgentUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.AddressComponent;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.LbsUtil;
import com.zb.common.utils.MapUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.common.utils.RegexUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.LoginLog;
import com.zb.models.User;
import com.zb.models.res.ZbToolKey;
import com.zb.service.im.AliIMService;
import com.zb.service.im.EasemobService;
import com.zb.service.task.TaskAspect;
import com.zb.service.task.TaskAspect.TaskType;
import com.zb.service.usertask.UserMonitorService;
import com.zb.service.usertask.UserTaskService;
import com.zb.view.CheckBind;

@Service
public class AuthService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(AuthService.class);

	private final static String QQ_APP_ID = "1105127046";
	private final static String QQ_APP_ID_UNDERCOVER = "1106143335";
	private final static String QQ_APP_ID_DRAWSOMETHING = "1106093885";

	private final static String QQ_APP_ID_ONW = "1106156847";

	// private final static String QQ_APP_KEY = "5wz6tEmnPlImPXHR";
	//
	// private final static String QQ_URL = "https://graph.qq.com/oauth2.0/";
	// private final static String QQ_AUTHCODE_URL =
	// "https://graph.qq.com/oauth2.0/authorize";
	// private final static String QQ_AUTHCODE_REDIRECT_URL =
	// "http://www.wecasting.com/index.html";
	// private final static String QQ_OPENID_URL =
	// "https://graph.qq.com/oauth2.0/me";
	private final static String QQ_USERINFO_URL = "https://graph.qq.com/user/get_user_info";
	private final static String QQ_UNION_ID = "https://graph.qq.com/oauth2.0/me?unionid=1&access_token=";
	// private final static String WEIBO_ACCESS_TOKEN =
	// "https://api.weibo.com/oauth2/access_token";
	private final static String WEIBO_USERINFO_URL = "https://api.weibo.com/oauth2/get_token_info";
	private final static String WEIBO_USERSHOW_URL = "https://api.weibo.com/2/users/show.json";

	// 微信
	private final static String WEIXIN_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";
	// private final static String WEIXIN_URL =
	// "https://api.weixin.qq.com/sns/oauth2/";

	@Autowired
	UserService userService;

	@Autowired
	SmsService smsService;

	@Autowired
	UserPackService userPackService;

	@Autowired
	ZbToolsService zbToolsService;

	@Autowired
	EnvService envService;

	@Autowired
	UserTaskService userTaskService;

	@Autowired
	EasemobService easemobService;
	@Autowired
	AliIMService aliIMService;

	@Autowired
	UserMonitorService userMonitorService;

	Random r = new Random();

	public ReMsg isLogin(String token, String lbs, HttpServletRequest req) {
		if (isBanned(req)) {// 判断有效设备
			return new ReMsg(ReCode.BANNED);
		}
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String s = opsv.get(RK.accessToken(token));
		if (StringUtils.isNotBlank(s)) {
			Long uid = T2TUtil.str2Long(s);
			if (uid > 0) {
				opsv.set(RK.accessToken(token), s, 10, TimeUnit.DAYS);
				loginLog(uid, true, req, true, 0, lbs);
				BasicDBObject u = new BasicDBObject();
				try {
					AddressComponent ac = null;
					if (super.getUser("province") == null) {
						if (StringUtils.isNotBlank(lbs)) {
							u.put("lbs", lbs);
							ac = LbsUtil.getLocationLbs(lbs);
						} else {
							ac = LbsUtil.getIpLbs(req);
						}
						if (ac != null) {
							u.append("province", LbsUtil.getFmtProvince(ac)).append("city", LbsUtil.getFmtCity(ac));
							userService.update(uid, u);
						}
					}
					if (null != ac) {
						final AddressComponent nac = ac;
						CompletableFuture.runAsync(() -> {
							LbsUtil.updateLocation(nac.getX(), nac.getY(), uid, System.currentTimeMillis(),
									(long) super.getUser("sex"));
						});
					}
				} catch (Exception e) {
					log.error("更新lbs失败");
				}
				// log.error("xxxxxx:" + uid + " isLogin");
				// 旧版本七日登录豪礼
				return userTaskService.doLogin7Day(req);
				// return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
	}

	public ReMsg login(String username, String pwd, HttpServletRequest req) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(pwd)) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		if (isBanned(req)) {// 判断有效设备
			return new ReMsg(ReCode.BANNED);
		}
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String sc = opsv.get(RK.loginFailedCount(username));
		if (StringUtils.isNotBlank(sc)) {
			int c = Integer.valueOf(sc);
			if (c > Const.LOGIN_ERR_MAX) {// 大于最大登录次数
				return new ReMsg(ReCode.LOGIN_FAILED_ERR_TOO_OFTEN);
			} else if (c > Const.LOGIN_ERR_AUTHCODE) {// 验证码验证
				if (this.codeVeri(req)) {
					return new ReMsg(ReCode.AUTHCODE_ERR, super.authcodeImage(req));
				}
			}
		}
		DBObject dbo = null;
		if (RegexUtil.isPhone(username)) {
			dbo = userService.findByPhone(username);
		} else {
			dbo = userService.findByUsername(username);
		}
		if (dbo == null) {
			return new ReMsg(ReCode.LOGIN_FAILED_ERR_USERNAME);
		} else {
			if (MDUtil.SHA.digest2HEX(pwd).equals(DboUtil.getString(dbo, "pwd"))) {
				return loginOk(LoginType.DEF.getLoginType(), req, dbo);
			} else {
				Long c = opsv.increment(RK.loginFailedCount(username), 1);
				if (c > Const.LOGIN_ERR_AUTHCODE) {// 登录3次失败，添加验证码逻辑
					return new ReMsg(ReCode.LOGIN_FAILED_ERR_PASSWORD, super.authcodeImage(req));
				}
				getRedisTemplate().expire(RK.loginFailedCount(username), 5, TimeUnit.MINUTES);

				loginLog(Long.parseLong(dbo.get("_id").toString()), false, req, false, LoginType.DEF.getLoginType(),
						null);

				return new ReMsg(ReCode.LOGIN_FAILED_ERR_PASSWORD);
			}

		}
	}

	public ReMsg login(int loginType, String accessToken, String openId, String appCode, HttpServletRequest req)
			throws IOException {
		if (isBanned(req)) {// 判断有效设备
			return new ReMsg(ReCode.BANNED);
		}
		if (appCode == null) {
			appCode = Const.APP_CODE_BIBI;
		}
		int ch = super.getCh(req);
		int cch = super.getChildCh(req);
		String ip = super.getIp(req);

		User user = null;
		DBObject dbo = userService.findUserByOpenId(loginType, openId);// 通过OpenId查找用户

		if (dbo == null) {
			if (loginType == LoginType.WeiXin.getLoginType()) {
				user = getWeiXinUserInfo(loginType, accessToken, openId, ch, cch, ip, appCode);// 调用微信接口获取微信用户信息
				if (user != null) {// 微信信息获取成功
					dbo = userService.findUserByUnionId(loginType, user.getUnionId());
					if (dbo != null) {// 通过unionId 登录成功
						return loginOk(loginType, req, dbo);
					}
				} else {
					return new ReMsg(ReCode.THIRD_LOGIN_FAILED_ERR);
				}
			} else if (loginType == LoginType.QQ.getLoginType()) {
				String appId = QQ_APP_ID;
				if (Const.APP_CODE_UNDERCOVER.equals(appCode)) {
					appId = QQ_APP_ID_UNDERCOVER;
				} else if (Const.APP_CODE_DRAWSOMETHING.equals(appCode)) {
					appId = QQ_APP_ID_DRAWSOMETHING;
				} else if (Const.APP_CODE_ONW.equals(appCode)) {
					appId = QQ_APP_ID_ONW;
				} else if (Const.APP_CODE_ARTIFACT.equals(appCode)) {
					// TODO
				}
				user = getQqInfo(loginType, accessToken, openId, appId, ch, cch, ip, appCode);
				if (user != null) {// qq获取信息成功
					dbo = userService.findUserByUnionId(loginType, user.getUnionId());
					if (dbo != null) {// 通过unionId 登录成功
						return loginOk(loginType, req, dbo);
					}
				} else {
					return new ReMsg(ReCode.THIRD_LOGIN_FAILED_ERR);
				}
			} else if (loginType == LoginType.Weibo.getLoginType()) {
				user = getWeiBoUserInfo(loginType, accessToken, openId, ch, cch, ip, appCode);
			}
			if (user != null) {
				String name = user.getNickname();
				if (name != null) {
					DBObject obj = userService.findByUsername(name);
					if (obj != null) {
						String currentId = (1000 + RandomUtil.nextInt(9000)) + "";
						user.setNickname(name.concat(currentId));
					}
				}
				userService.save(user);// 保存用户信息
				dbo = DboUtil.beanToDBO(user);
				userService.downloadAvatar(user.get_id(), user.getAvatar());
			} else {
				return new ReMsg(ReCode.LOGIN_FAILED_ERR_THIRD);
			}
		}
		if (!dbo.containsField("unionId")) {
			String unionId = null;
			if (loginType == LoginType.WeiXin.getLoginType()) {
				user = getWeiXinUserInfo(loginType, accessToken, openId, ch, cch, ip, appCode);// 调用微信接口获取微信用户信息
				if (user != null) {
					unionId = user.getUnionId();
				} else {
					return new ReMsg(ReCode.LOGIN_FAILED_ERR_THIRD);
				}
			} else if (loginType == LoginType.QQ.getLoginType()) {
				unionId = getQqUnionId(accessToken);
			}
			if (unionId != null) {
				BasicDBObject u = new BasicDBObject("unionId", unionId);
				userService.update(DboUtil.getLong(dbo, "_id"), u);
			}
		}
		return loginOk(loginType, req, dbo);
	}

	private ReMsg loginOk(int loginType, HttpServletRequest req, DBObject dbo) {
		if (!validUser(dbo)) {// 判断账户是否被禁用
			return new ReMsg(ReCode.LOGIN_NOT_ALLOWED);
		}
		String acccode = MDUtil.SHA.digest2HEX((new ObjectId()).toString() + "BibiZdbNb");
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		opsv.set(RK.accessToken(acccode), dbo.get("_id").toString(), 10, TimeUnit.DAYS);

		long uid = DboUtil.getLong(dbo, "_id");
		String username = aliIMService.getUsername(uid);
		String nickname = DboUtil.getString(dbo, "nickname");
		loginLog(DboUtil.getLong(dbo, "_id"), true, req, false, loginType, null);

		Double d = super.getVer(req);
		String pwd = null;
		// FIXME 版本3.11
		if (d != null && d > 3.11) {// ali
			Boolean b = DboUtil.getBool(dbo, "chatReg");
			if (b != null && b) {
				pwd = aliIMService.getPwd(username);
			} else {
				pwd = aliIMService.reg(username, dbo);
				if (pwd != null) {
					DBObject u = new BasicDBObject().append("chatReg", true);
					userService.update(uid, u);
				}
			}
		} else {// 环信
			Boolean b = DboUtil.getBool(dbo, "chatReg");
			if (b != null && b) {
				pwd = aliIMService.getPwd(username);
			} else {
				pwd = easemobService.reg(username, nickname);// 环信注册不更新
				// if (pwd != null) {
				// DBObject u = new BasicDBObject().append("chatReg", 2);
				// userService.update(uid, u);
				// }
			}
		}
		// 旧版本七天登陆送豪礼代码
		userTaskService.doLogin7Day(uid, req);
		if (d != null && d > 2.4) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("token", acccode);
			m.put("chatToken", pwd);
			m.put("chatId", username);
			return new ReMsg(ReCode.OK, m);
		} else {
			return new ReMsg(ReCode.OK, acccode);
		}
	}

	public User getQqInfo(int loginType, String accessToken, String openId, String appId, int ch, int childCh,
			String ip, String appCode) throws IOException {
		if (appId == null || "".equals(appId)) {
			return null;
		}
		if (accessToken == null || "".equals(accessToken)) {
			return null;
		}
		String get_userinfo_url = QQ_USERINFO_URL + "?access_token=" + accessToken + "&oauth_consumer_key=" + appId
				+ "&openid=" + openId + "&format=json";
		return get3UserInfo(loginType, get_userinfo_url, ch, childCh, openId, ip, accessToken, appCode);
	}

	private User getWeiXinUserInfo(int loginType, String accessToken, String openid, int ch, int childCh, String ip,
			String appCode) throws IOException {
		String get_userinfo_url = WEIXIN_USERINFO_URL + "?access_token=" + accessToken + "&openid=" + openid;
		// log.debug("weixin login get_userinfo_url: {}", get_userinfo_url);
		return get3UserInfo(loginType, get_userinfo_url, ch, childCh, openid, ip, accessToken, appCode);
	}

	private User getWeiBoUserInfo(int loginType, String accessToken, String openid, int ch, int childCh, String ip,
			String appCode) throws IOException {
		String get_userinfo_url = WEIBO_USERINFO_URL + "?access_token=" + accessToken;
		// log.debug("weibo login get_userinfo_url: {}", get_userinfo_url);
		return get3UserInfo(loginType, get_userinfo_url, ch, childCh, openid, ip, accessToken, appCode);
	}

	private String getQqUnionId(String accessToken) throws IOException {
		String json = HttpClientUtil.get(QQ_UNION_ID + accessToken, null, HttpClientUtil.UTF8);
		json = json.substring(json.indexOf("(") + 1, json.length() - 2);
		Map<String, Object> unionM = JSONUtil.jsonToMap(json);
		if (unionM.containsKey("unionid")) {
			return MapUtil.getStr(unionM, "unionid");
		}
		return null;
	}

	private User get3UserInfo(int loginType, String get_userinfo_url, int ch, int childCh, String openId, String ip,
			String accessToken, String appCode) throws IOException {
		String respForUserInfo = null;
		if (loginType == LoginType.Weibo.getLoginType()) {
			respForUserInfo = HttpClientUtil.post(get_userinfo_url, null, null);
		} else {
			respForUserInfo = HttpClientUtil.get(get_userinfo_url, null, HttpClientUtil.UTF8);
		}
		if (respForUserInfo == null || respForUserInfo.trim().length() == 0) {
			return null;
		} else {
			Map<String, Object> m = JSONUtil.jsonToMap(respForUserInfo);
			if (loginType == LoginType.WeiXin.getLoginType()) {
				if (m.containsKey("errcode")) {
					return null;
				}
				User user = new User(loginType, openId, ip, ch, childCh, appCode);
				user.setUnionId(MapUtil.getStr(m, "unionid"));
				String avatar = MapUtil.getStr(m, "headimgurl");
				if (avatar != null && !"/0".equals(avatar)) {
					user.setAvatar(avatar);
				}
				user.setNickname(MapUtil.getStr(m, "nickname"));
				user.setSex(MapUtil.getInt(m, "sex"));
				return user;
			} else if (loginType == LoginType.QQ.getLoginType()) {
				if (m.containsKey("ret")) {
					if (MapUtil.getInt(m, "ret") == 0) {
						User user = new User(loginType, openId, ip, ch, childCh, appCode);
						String unionId = getQqUnionId(accessToken);
						user.setUnionId(unionId);
						String nickname = MapUtil.getStr(m, "nickname");
						if ("qzuser".equals(nickname)) {
							// log.error("qzuser:::" + get_userinfo_url);
							// user.setAvatar(User.DEFAULT_AVATAR);
							user.setSex(2);
							DBObject dbo = userService.getCopyUser();
							if (dbo != null) {
								nickname = DboUtil.getString(dbo, "nickname");
								user.setAvatar(DboUtil.getString(dbo, "avatar"));
							}
						} else {
							// log.error("ok:::" + get_userinfo_url);
							String head = MapUtil.getStr(m, "figureurl_qq_2");
							if (head == null) {
								head = MapUtil.getStr(m, "figureurl_qq_1");
								// head = head.substring(0, head.length() - 2) +
								// "640";
							}
							// else {
							// head = head.substring(0, head.length() - 3) +
							// "640";
							// }
							user.setAvatar(head);
							user.setSex("男".equals(MapUtil.getStr(m, "gender")) ? 1 : 2);
							Integer year = MapUtil.getInt(m, "year");
							if (year != null && year > 0) {
								Calendar c = Calendar.getInstance();
								c.set(Calendar.YEAR, year);
								c.set(Calendar.MONTH, 0);
								c.set(Calendar.DATE, 1);
								user.setBirthday(c.getTimeInMillis());
							}
						}
						user.setNickname(nickname);
						return user;
					}
				}
				return null;
			} else if (loginType == LoginType.Weibo.getLoginType()) {
				if (!m.containsKey("uid")) {
					// log.error("Weibo Login err:");
					return null;
				}
				String uid = MapUtil.getLong(m, "uid").toString();
				User user = new User(loginType, MapUtil.getLong(m, "uid").toString(), ip, ch, childCh, appCode);
				try {
					String userUrl = WEIBO_USERSHOW_URL + "?access_token=" + accessToken + "&uid=" + uid;
					// log.error("WeiboShow:" + userUrl);
					respForUserInfo = HttpClientUtil.get(userUrl, null, HttpClientUtil.UTF8);
					if (respForUserInfo != null) {
						Map<String, Object> m2 = JSONUtil.jsonToMap(respForUserInfo);

						String avatar = MapUtil.getStr(m2, "avatar_hd");
						if (avatar == null) {
							avatar = MapUtil.getStr(m2, "avatar_large");
						}
						user.setAvatar(avatar);
						user.setNickname(MapUtil.getStr(m2, "screen_name"));
						user.setSex("m".equals(MapUtil.getStr(m2, "gender")) ? 1 : 2);
					}
				} catch (Exception ex) {
					user.setNickname("无名小辈" + r.nextInt(9999) + 1);
				}

				return user;
			}
		}
		return null;
	}

	public ReMsg checkUsername(String username, HttpServletRequest req) {
		String clientId = super.getClientId(req);
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String ccnt = opsv.get(RK.regClientCount(clientId));
		int cnt = 0;
		if (StringUtils.isNotBlank(ccnt)) {
			cnt = Integer.parseInt(ccnt);
		}
		DBObject dbo = userService.findByUsername(username);
		if (cnt > Const.REG_MAX_AUTHCODE) {
			if (null != dbo) {
				return new ReMsg(ReCode.USERNAME_EXISTS, super.authcodeImage(req));
			}
			return new ReMsg(ReCode.OK, super.authcodeImage(req));
		} else {
			if (null != dbo) {
				return new ReMsg(ReCode.USERNAME_EXISTS);
			}
			return new ReMsg(ReCode.OK);
		}
	}

	public ReMsg regGuest(HttpServletRequest req)
			throws JsonParseException, JsonMappingException, NumberFormatException, IOException {
		String username = super.getDevId(req);

		DBObject dbo = userService.findByUsername(username);
		if (dbo == null) {
			String nickname = "游客" + super.getNextId("guess");
			User user = new User(username, "zb123456", nickname, getIp(req));
			int fch = getChannel(req);
			if (fch > 0) {
				user.setCh(fch / 10000);
				user.setChildCh(fch % 10000);
			}
			user.setThirdType(LoginType.GUEST.getLoginType());
			userService.save(user);
			dbo = userService.findByUsername(username);
		}
		return loginOk(LoginType.GUEST.getLoginType(), req, dbo);
	}

	public ReMsg forgot(String phone, String pwd, String code, HttpServletRequest req)
			throws JsonParseException, JsonMappingException, NumberFormatException, IOException {
		if (!smsService.validCode(Long.parseLong(phone), code)) {
			return new ReMsg(ReCode.AUTHCODE_ERR);
		}
		DBObject dbo = userService.findByPhone(phone);
		if (null == dbo) {
			return new ReMsg(ReCode.USER_NOT_EXISTS);
		}
		User user = DboUtil.toBean(dbo, User.class);
		ReMsg rc = userService.updatePwd(user.get_id(), pwd);
		if (ReCode.OK.reCode() == rc.getCode()) {
			return loginOk(LoginType.DEF.getLoginType(), req, DboUtil.beanToDBO(user));
		}
		return rc;
	}

	public ReMsg reg(String phone, String pwd, String code, String appCode, HttpServletRequest req)
			throws JsonParseException, JsonMappingException, NumberFormatException, IOException {

		if (!smsService.validCode(Long.parseLong(phone), code)) {
			return new ReMsg(ReCode.AUTHCODE_ERR);
		}

		DBObject dbo = userService.findByPhone(phone);
		if (null != dbo) {
			return new ReMsg(ReCode.USERNAME_EXISTS);
		}

		User user = new User(phone, pwd, getIp(req), true);
		// DBObject cdbo = userService.getCopyUser();
		// if (cdbo != null) {
		// user.setNickname(DboUtil.getString(cdbo, "nickname"));
		// user.setAvatar(DboUtil.getString(cdbo, "avatar"));
		// }
		int fch = getChannel(req);
		if (fch > 0) {
			user.setCh(fch / 10000);
			user.setChildCh(fch % 10000);
		}
		if (StringUtils.isBlank(appCode)) {
			appCode = Const.APP_CODE_BIBI;
		}
		user.setAppCode(appCode);

		ReCode rc = userService.save(user);
		if (ReCode.OK.reCode() == rc.reCode()) {
			return loginOk(LoginType.DEF.getLoginType(), req, DboUtil.beanToDBO(user));
		}
		return new ReMsg(rc);
	}

	@Deprecated
	public ReMsg reg(String username, String pwd, HttpServletRequest req) {
		String clientId = super.getClientId(req);
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String ccnt = opsv.get(RK.regClientCount(clientId));
		int cnt = 0;
		if (StringUtils.isNotBlank(ccnt)) {
			cnt = Integer.parseInt(ccnt);
			if (cnt > Const.REG_MAX_AUTHCODE) {
				if (this.codeVeri(req)) {
					return new ReMsg(ReCode.AUTHCODE_ERR, super.authcodeImage(req));
				}
			}
		}

		DBObject dbo = userService.findByUsername(username);
		if (null != dbo) {
			return new ReMsg(ReCode.USERNAME_EXISTS);
		}
		User user = new User(username, pwd, getIp(req));
		int fch = getChannel(req);
		if (fch > 0) {
			user.setCh(fch / 10000);
			user.setChildCh(fch % 10000);
		}

		ReCode rc = userService.save(user);
		if (ReCode.OK.reCode() == rc.reCode()) {
			opsv.increment(RK.regClientCount(clientId), 1);
			getRedisTemplate().expire(RK.regClientCount(username), 5, TimeUnit.MINUTES);
			return loginOk(LoginType.DEF.getLoginType(), req, DboUtil.beanToDBO(user));
		}
		return new ReMsg(rc);
	}

	public ReMsg canUseTool(HttpServletRequest req, String mark)
			throws JsonParseException, JsonMappingException, IOException {
		long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		// List<DBObject> ups = userPackService.find(
		// BaseGoods.GoodsType.VIP.getT(), userId, Const.STATUS_OK, 0, 1,
		// null);
		// if (ups.size() > 0) {
		// UserPack up = DboUtil.toBean(ups.get(0), UserPack.class);
		// if (up.getExpires() > System.currentTimeMillis()) {
		// return new ReMsg(ReCode.OK);
		// }
		// }

		ZbToolKey tk = DboUtil.toBean(zbToolsService.findToolKeyByUid(userId), ZbToolKey.class);
		if (tk != null && tk.hasKey(mark)) {
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public boolean isBanned(HttpServletRequest req) {
		return isBanned(getDevId(req));
	}

	public boolean isBanned(String devId) {
		return getRedisTemplate().hasKey(RK.blackDev(devId));
	}

	public void removeToken(String acctoken) {
		getRedisTemplate().delete(RK.accessToken(acctoken));
	}

	public ReMsg getAuthUser(HttpServletRequest request, String token) {
		String devId = this.getDevId(request);
		if (this.isBanned(devId)) { // 封掉客户端 uid
			this.removeToken(token);// 删除用户key
			return new ReMsg(ReCode.BANNED);
		}
		Long uid = getUid(token);
		if (uid == 0) {// 用户登录失效
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		} else {
			DBObject user = userService.findById(uid);
			if (!validUser(user)) {
				this.removeToken(token);// 删除用户key
				return new ReMsg(ReCode.LOGIN_NOT_ALLOWED);
			}
			return new ReMsg(ReCode.OK, user.toMap());
		}
	}

	// public ReMsg getEasemobUser(String token) {
	// long uid = super.getUid();
	// if (uid < 1) {
	// return new ReMsg(ReCode.NOT_AUTHORIZED);
	// }
	// String username = easemobService.getUsername(uid);
	// String pwd = easemobService.getPwd(username);
	// Map<String, String> m = new HashMap<String, String>();
	// m.put("chatToken", pwd);
	// m.put("chatId", username);
	// return new ReMsg(ReCode.OK, m);
	// }

	public ReMsg getAliUser(String token) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		String username = aliIMService.getUsername(uid);
		String pwd = aliIMService.getPwd(username);
		Map<String, String> m = new HashMap<String, String>();
		m.put("chatToken", pwd);
		m.put("chatId", username);
		return new ReMsg(ReCode.OK, m);
	}

	// 记录登录日志
	public boolean loginLog(long userId, boolean status, HttpServletRequest req, boolean isAuto, int loginType,
			String lbs) {
		int day = DateUtil.curDay();
		if (status && isAuto) {
			long count = super.getC(DocName.LOGIN_LOG)
					.count((new BasicDBObject("userId", userId)).append("status", status).append("day", day));
			if (count > 0) {
				return true;
			}
		}

		String ip = getIp(req);
		UserAgent ua = UserAgentUtil.getUserAgent(req);
		String devId = this.getDevId(req);
		Double version = this.getVer(req);
		int via = getVia(req);
		long now = System.currentTimeMillis();
		LoginLog ll = new LoginLog(super.getNextId(DocName.LOGIN_LOG), userId, day, ip, now, status, ua, devId, version,
				via, loginType, lbs);
		super.getMongoTemplate().save(ll);
		if (via == 2) {
			userMonitorService.upSet(userId, devId, now, ip, lbs, loginType);
		}
		return true;
	}

	private boolean validUser(DBObject user) {
		if (null == user) {
			return false;
		}
		if (null != user.get("status") && "1".equals(user.get("status").toString())) {
			return true;
		}
		return false;
	}

	// 绑定第三方
	public ReCode bindThird(int loginType, String accessToken, String openId, String appCode, HttpServletRequest req)
			throws IOException {
		Long uid = super.getUid();
		if (null == uid || uid < 1) {
			return ReCode.ACCESS_TOKEN_ERR;
		}
		User my = DboUtil.toBean(userService.findById(uid), User.class);
		int ch = super.getCh(req);
		int cch = super.getChildCh(req);
		String ip = super.getIp(req);

		User user = null;
		DBObject dbo = userService.findUserByOpenId(loginType, openId);// 通过OpenId查找用户
		if (dbo != null) {
			return ReCode.THIRD_HAVEBIND;
		}
		if (dbo == null) {
			if (loginType == LoginType.WeiXin.getLoginType()) {
				user = getWeiXinUserInfo(loginType, accessToken, openId, ch, cch, ip, appCode);// 调用微信接口获取微信用户信息
				if (user != null) {// 微信信息获取成功
					dbo = userService.findUserByUnionId(loginType, user.getUnionId());
					if (dbo != null) {
						return ReCode.THIRD_HAVEBIND;
					}
				} else {
					return ReCode.THIRD_LOGIN_FAILED_ERR;
				}
			} else if (loginType == LoginType.QQ.getLoginType()) {
				String appId = QQ_APP_ID;
				if (Const.APP_CODE_UNDERCOVER.equals(appCode)) {
					appId = QQ_APP_ID_UNDERCOVER;
				} else if (Const.APP_CODE_DRAWSOMETHING.equals(appCode)) {
					appId = QQ_APP_ID_DRAWSOMETHING;
				} else if (Const.APP_CODE_ONW.equals(appCode)) {
					appId = QQ_APP_ID_ONW;
				} else if (Const.APP_CODE_ARTIFACT.equals(appCode)) {
					// TODO
				}
				user = getQqInfo(loginType, accessToken, openId, appId, ch, cch, ip, appCode);
			} else if (loginType == LoginType.Weibo.getLoginType()) {
				user = getWeiBoUserInfo(loginType, accessToken, openId, ch, cch, ip, appCode);
			}
			if (user != null) {
				userService.updateUser(uid, user.getOpenId(), user.getUnionId(), user.getThirdType());
			} else {
				return ReCode.THIRD_LOGIN_FAILED_ERR;
			}
		}
		return ReCode.OK;
	}

	// 绑定手机号
	@TaskAspect(TaskType.BINDPHONE)
	public ReMsg bindPhone(HttpServletRequest req, String phone, String code)
			throws JsonParseException, JsonMappingException, NumberFormatException, IOException {
		Long uid = super.getUid();
		if (!smsService.validCode(Long.parseLong(phone), code)) {
			return new ReMsg(ReCode.AUTHCODE_ERR);
		}
		if (null == uid || uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		if (!StringUtils.isNotBlank(phone)) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		DBObject object = userService.findByPhone(phone);
		if (null != object) {
			return new ReMsg(ReCode.PHONE_HAVEBIND);
		}
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		if (StringUtils.isNotBlank(phone)) {
			u.put("phone", phone);
		}
		return userService.update(uid, u);
	}

	// 检查绑定手机号和第三方
	public ReMsg checkBind(HttpServletRequest req) {
		CheckBind obj = new CheckBind();
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		DBObject user = userService.findById(uid);
		String phone = DboUtil.getString(user, "phone");
		Integer thirdType = DboUtil.getInt(user, "thirdType");
		if (StringUtils.isNotBlank(phone)) {
			obj.setPhone(CheckBind.YES);
		}
		if (null != thirdType && thirdType > 1) {
			obj.setThird(thirdType);
		}
		return new ReMsg(ReCode.OK, obj);
	}

	public static void main(String[] args) {
		// String json = "callback(
		// {\"client_id\":\"1105127046\",\"openid\":\"10D482219C1806AEA7587E29E8C0DC88\",\"unionid\":\"UID_4273FEFDD65DD3D66E059D99EB5E677B\"}
		// );";
		// json = json.substring(json.indexOf("(") + 1, json.length() - 2);
		// System.out.println(json);
		// Map<String, Object> unionM = JSONUtil.jsonToMap(json);
		String p = "广西壮族自治区";
		System.out.println(p.replaceAll("[省|市|回族自治区|壮族自治区|维吾尔自治区|自治区]", ""));
	}
}
