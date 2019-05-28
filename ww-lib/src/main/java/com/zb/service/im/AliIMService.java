package com.zb.service.im;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.mongodb.DBObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimCustmsgPushRequest;
import com.taobao.api.request.OpenimCustmsgPushRequest.CustMsg;
import com.taobao.api.request.OpenimImmsgPushRequest;
import com.taobao.api.request.OpenimImmsgPushRequest.ImMsg;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.request.OpenimUsersDeleteRequest;
import com.taobao.api.request.OpenimUsersGetRequest;
import com.taobao.api.request.OpenimUsersUpdateRequest;
import com.taobao.api.response.OpenimCustmsgPushResponse;
import com.taobao.api.response.OpenimImmsgPushResponse;
import com.taobao.api.response.OpenimUsersAddResponse;
import com.taobao.api.response.OpenimUsersDeleteResponse;
import com.taobao.api.response.OpenimUsersGetResponse;
import com.taobao.api.response.OpenimUsersUpdateResponse;
import com.zb.common.crypto.MDUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.core.conf.Config;
import com.zb.models.User;

@Service
public class AliIMService {
	static final Logger log = LoggerFactory.getLogger(AliIMService.class);
	public static String APPKEY = "24577547";
	public static String SECRET = "72cf4066dbf01a32424cf290b11f8ee0";
	public static final String APPKEY_TEST = "24591632";
	public static final String SECRET_TEST = "b47acff4a223565ff5c2688fea1360f0";
	public static final String URL_HTTP = "http://gw.api.taobao.com/router/rest";
	static {
		if (Config.cur().getInt("istest", 0) != 0) {// 测试
			log.error("-------------- ALI TEST ----------------");
			APPKEY = APPKEY_TEST;
			SECRET = SECRET_TEST;
		}
	}

	// private TaobaoClient getClient() {
	// if (Config.cur().getInt("istest", 0) == 0) {// 正式
	// return new DefaultTaobaoClient(URL_HTTP, APPKEY, SECRET);
	// } else {// 测试
	// return new DefaultTaobaoClient(URL_HTTP, APPKEY_TEST, SECRET_TEST);
	// }
	// }
	//
	// private String getAPPKEY() {
	// if (Config.cur().getInt("istest", 0) == 0) {// 正式
	// return APPKEY;
	// } else {// 测试
	// return APPKEY_TEST;
	// }
	// }

	public String getUsername(long uid) {
		return String.valueOf(uid);
	}

	public String getPwd(String username) {
		return MDUtil.MD5.digest2HEX("BiBi" + username + "bpwd");
	}

	/** 注册用户 */
	public String reg(String username, DBObject dbo) {
		TaobaoClient client = new DefaultTaobaoClient(URL_HTTP, APPKEY, SECRET);
		OpenimUsersAddRequest req = new OpenimUsersAddRequest();
		List<Userinfos> list = new ArrayList<Userinfos>();
		Userinfos user = new Userinfos();
		String pwd = getPwd(username);
		user.setUserid(username);// 账户
		user.setPassword(pwd);// 密码
		String nickname = DboUtil.getString(dbo, "nickname");
		if (StringUtils.isNotBlank(nickname)) {
			if (nickname.length() > 16) {// 阿里昵称限制长度
				nickname = nickname.substring(0, 16);
			}
			user.setName(nickname);
			user.setNick(nickname);// 昵称
		}
		user.setIconUrl(
				DboUtil.getString(dbo, "avatar") == null ? User.DEFAULT_AVATAR : DboUtil.getString(dbo, "avatar"));// 头像
		user.setGender(DboUtil.getInt(dbo, "sex") == null ? "2" : String.valueOf(DboUtil.getInt(dbo, "sex")));// 性别
		user.setVip(DboUtil.getInt(dbo, "vip") == null ? "0" : String.valueOf(DboUtil.getInt(dbo, "vip")));
		// user.setExtra(DboUtil.getInt(dbo, "point") == null ? "0" :
		// String.valueOf(DboUtil.getInt(dbo, "point")));// 扩展point
		// user.setAddress(DboUtil.getString(dbo, "lbs"));
		list.add(user);
		req.setUserinfos(list);
		OpenimUsersAddResponse rsp = null;
		try {
			rsp = client.execute(req);
			Map<String, Object> map = JSONUtil.jsonToMap(rsp.getBody());
			map = (Map) map.get("openim_users_add_response");
			if (null == map) {// 请求失败 参数错误
				return null;
			}
			String failMsg = map.get("fail_msg").toString();
			if ("{}".equals(failMsg) || "{string=[data exist]}".equals(failMsg)) {// 注册成功或已注册
				return pwd;
			}
		} catch (ApiException e) {
			log.error("aliIM addUser err:", e);
		}
		return null;
	}

	/** 注册用户 */
	public Map<String, Object> adminReg(List<DBObject> dbos) {
		TaobaoClient client = new DefaultTaobaoClient(URL_HTTP, APPKEY, SECRET);
		OpenimUsersAddRequest req = new OpenimUsersAddRequest();
		List<Userinfos> list = new ArrayList<Userinfos>();
		for (DBObject dbo : dbos) {
			Long id = DboUtil.getLong(dbo, "_id");
			String username = getUsername(id);
			Userinfos user = new Userinfos();
			String pwd = getPwd(username);
			user.setUserid(username);// 账户
			user.setPassword(pwd);// 密码
			String nickname = DboUtil.getString(dbo, "nickname");
			if (StringUtils.isNotBlank(nickname)) {
				if (nickname.length() > 16) {// 阿里昵称限制长度
					nickname = nickname.substring(0, 16);
				}
				user.setName(nickname);
				user.setNick(nickname);// 昵称
			}
			user.setIconUrl(
					DboUtil.getString(dbo, "avatar") == null ? User.DEFAULT_AVATAR : DboUtil.getString(dbo, "avatar"));// 头像
			user.setGender(DboUtil.getInt(dbo, "sex") == null ? "2" : String.valueOf(DboUtil.getInt(dbo, "sex")));// 性别
			user.setVip(DboUtil.getInt(dbo, "vip") == null ? "0" : String.valueOf(DboUtil.getInt(dbo, "vip")));
			list.add(user);
		}
		if (list.size() > 0) {
			req.setUserinfos(list);
			OpenimUsersAddResponse rsp = null;
			try {
				rsp = client.execute(req);
				Map<String, Object> map = JSONUtil.jsonToMap(rsp.getBody());
				return (Map) map.get("openim_users_add_response");
			} catch (ApiException e) {
				log.error("aliIM addUser err:", e);
			}
		}
		return null;
	}

	/** 删除 */
	public boolean del(String username) {// 多个用户,分隔
		TaobaoClient client = new DefaultTaobaoClient(URL_HTTP, APPKEY, SECRET);
		OpenimUsersDeleteRequest req = new OpenimUsersDeleteRequest();
		req.setUserids(username);
		OpenimUsersDeleteResponse rsp = null;
		try {
			rsp = client.execute(req);
			Map<String, Object> map = JSONUtil.jsonToMap(rsp.getBody());
			map = (Map) map.get("openim_users_delete_response");
			if (map == null) {// 请求失败
				return false;
			}
			return true;
		} catch (ApiException e) {
			log.error("aliIM delUser err:", e);
		}
		return false;
	}

	/** 更新userinfo */
	public boolean update(long uid, DBObject u) {
		boolean update = false;
		Userinfos userinfos = new Userinfos();
		if (null != DboUtil.getString(u, "avatar")) {// 头像
			userinfos.setIconUrl(DboUtil.getString(u, "avatar"));
			update = true;
		}
		if (null != DboUtil.getString(u, "nickname")) {// 昵称
			userinfos.setNick(DboUtil.getString(u, "nickname"));
			userinfos.setName(DboUtil.getString(u, "nickname"));
			update = true;
		}
		if (null != DboUtil.getInt(u, "sex")) {// 性别
			userinfos.setGender(String.valueOf(DboUtil.getInt(u, "sex")));
			update = true;
		}
		if (update) {
			return update(getUsername(uid), userinfos);
		}
		return false;
	}

	/** 更新vip */
	public boolean updateVIP(long uid, Integer vip) {
		Userinfos userinfos = new Userinfos();
		userinfos.setVip(String.valueOf(vip));
		return update(getUsername(uid), userinfos);
	}

	/** 更新point */
	// public boolean updatePoint(long uid, Integer point) {
	// Userinfos userinfos = new Userinfos();
	// userinfos.setExtra(String.valueOf(point));
	// return update(getUsername(uid), userinfos);
	// }

	/** 更新 */
	private boolean update(String username, Userinfos Userinfos) {
		TaobaoClient client = new DefaultTaobaoClient(URL_HTTP, APPKEY, SECRET);
		OpenimUsersUpdateRequest req = new OpenimUsersUpdateRequest();
		List<Userinfos> list = new ArrayList<Userinfos>();
		Userinfos.setUserid(username);
		list.add(Userinfos);
		req.setUserinfos(list);
		OpenimUsersUpdateResponse rsp = null;
		try {
			rsp = client.execute(req);
			Map<String, Object> map = JSONUtil.jsonToMap(rsp.getBody());
			map = (Map) map.get("openim_users_update_response");
			if (map == null) {// 请求失败
				return false;
			}
			String failMsg = map.get("fail_msg").toString();
			if ("{}".equals(failMsg)) {// 更新成功
				return true;
			}
		} catch (ApiException e) {
			log.error("aliIM updateUser err:", e);
		}
		return false;
	}

	/** 获取用户信息 */
	public Map getUser(String username) {
		TaobaoClient client = new DefaultTaobaoClient(URL_HTTP, APPKEY, SECRET);
		OpenimUsersGetRequest req = new OpenimUsersGetRequest();
		req.setUserids(username);
		OpenimUsersGetResponse rsp = null;
		try {
			rsp = client.execute(req);
			Map<String, Object> map = JSONUtil.jsonToMap(rsp.getBody());
			map = (Map) map.get("openim_users_get_response");
			if (map == null) {// 请求失败
				return null;
			}
			map = (Map) map.get("userinfos");
			List list = (List) map.get("userinfos");
			if (list == null || list.size() == 0) {
				return null;
			}
			return (Map) list.get(0);
		} catch (ApiException e) {
			log.error("aliIM getUser err:", e);
		}
		return null;
	}

	/** 推送普通消息 */
	public boolean sendMsgTxt(long toUid, String content, long fuid) {
		List<Long> toUids = new ArrayList<Long>();
		toUids.add(toUid);
		return this.sendMsgTxt(toUids, content, fuid, APPKEY);
	}

	/** 推送普通消息 */
	public boolean sendMsgTxt(List<Long> toUsers, String content, long fuid, String appkey) {
		TaobaoClient client = new DefaultTaobaoClient(URL_HTTP, APPKEY, SECRET);
		OpenimImmsgPushRequest req = new OpenimImmsgPushRequest();
		ImMsg obj1 = new ImMsg();
		obj1.setFromUser(this.getUsername(fuid));
		List<String> toUs = new ArrayList<String>();
		for (Long uid : toUsers) {
			toUs.add(this.getUsername(uid));
		}
		obj1.setToUsers(toUs);
		obj1.setContext(content);
		obj1.setToAppkey(appkey);
		req.setImmsg(obj1);
		OpenimImmsgPushResponse rsp = null;
		try {
			rsp = client.execute(req);
			return true;
		} catch (ApiException e) {
			log.error("sendIMMsg  err", e);
		}
		return false;
	}

	/** 推送自定义消息 */
	public boolean sendMsgCmd(long toUid, String content, long fuid, Map<String, Object> ext) {
		List<Long> toUids = new ArrayList<Long>();
		toUids.add(toUid);
		return this.sendMsgCmd(toUids, content, fuid, ext, APPKEY);
	}

	/** 推送自定义消息 */
	public boolean sendMsgCmd(List<Long> toUsers, String content, long fuid, Map<String, Object> ext, String appkey) {
		TaobaoClient client = new DefaultTaobaoClient(URL_HTTP, APPKEY, SECRET);
		OpenimCustmsgPushRequest req = new OpenimCustmsgPushRequest();
		List<String> toUs = new ArrayList<String>();
		for (Long uid : toUsers) {
			toUs.add(this.getUsername(uid));
		}
		CustMsg obj1 = new CustMsg();
		obj1.setFromUser(getUsername(fuid));
		obj1.setToAppkey(appkey);
		obj1.setToUsers(toUs);
		// obj1.setInvisible(1L); 是否是透明消息 无效
		obj1.setSummary(content);// 消息
		obj1.setData(JSONUtil.beanToJson(ext));// 扩展消息map
		obj1.setInvisible(0L);
		obj1.setFromNick(null);// 发送人昵称 默认id
		req.setCustmsg(obj1);
		OpenimCustmsgPushResponse rsp = null;
		try {
			rsp = client.execute(req);
			return true;
		} catch (ApiException e) {
			log.error("sendIMMsg  err", e);
		}
		return false;
	}

	public static void main(String[] args) {
		TaobaoClient client = new DefaultTaobaoClient(URL_HTTP, APPKEY_TEST, SECRET_TEST);
		OpenimUsersAddRequest req = new OpenimUsersAddRequest();
		List<Userinfos> list = new ArrayList<Userinfos>();
		Userinfos user = new Userinfos();
		user.setUserid("111");// 账户
		user.setPassword("111");// 密码
		list.add(user);
		user = new Userinfos();
		user.setUserid("222");// 账户
		user.setPassword("222");// 密码
		list.add(user);
		user = new Userinfos();
		user.setUserid("333");// 账户
		user.setPassword("333");// 密码
		list.add(user);
		req.setUserinfos(list);
		OpenimUsersAddResponse rsp = null;
		try {
			rsp = client.execute(req);
			Map<String, Object> map = JSONUtil.jsonToMap(rsp.getBody());
			map = (Map) map.get("openim_users_add_response");
			map = (Map) map.get("uid_fail");
			List object = (List) map.get("string");
			System.out.println(object.size());
			System.out.println(rsp.getBody());
		} catch (ApiException e) {
			log.error("aliIM addUser err:", e);
		}
	}

}
