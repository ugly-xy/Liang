package com.zb.service.im;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zb.common.crypto.MDUtil;
import com.zb.common.http.Arg;
import com.zb.common.http.HttpsClientUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.MapUtil;
import com.zb.core.conf.Config;
import com.zb.models.easemob.EmCmdMsg;
import com.zb.models.easemob.EmMsg.TargetType;
import com.zb.service.BaseService;
import com.zb.models.easemob.EmTxtMsg;

@Service
public class EasemobService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(EasemobService.class);

	public static final String ORG_NAME = "1127161214115670";
	public static final String APP_NAME = "bibi";
	public static final String CLIENT_ID = "YXA6MgvUYMG7Eeajlj_IMd-2dA";
	public static final String CLIENT_SECRET = "YXA61lOGTDlKGmHfSuKik1Xi9FBY-x4";

	public static final String APP_NAME_TEST = "bibitest";
	public static final String CLIENT_ID_TEST = "YXA671y9wMWvEeaBOMU4ZdFjOw";
	public static final String CLIENT_SECRET_TEST = "YXA6w8Ffki20LCC45YOPHzlQWdsgyxo";

	public static final String DOMAIN = "https://a1.easemob.com/";
	String token = null;
	long expires_in = 0;

	private String getUrl(String path) {
		if (Config.cur().getInt("istest", 0) == 0) {
			return DOMAIN + ORG_NAME + "/" + APP_NAME + "/" + path;
		} else {
			return DOMAIN + ORG_NAME + "/" + APP_NAME_TEST + "/" + path;
		}
	}

	public String getToken() {
		long t = System.currentTimeMillis();
		if (t > expires_in || token == null) {
			String url = getUrl("token");
			Arg arg = new Arg("grant_type", "client_credentials");

			if (Config.cur().getInt("istest", 0) == 0) {
				arg.append("client_id", CLIENT_ID).append("client_secret", CLIENT_SECRET);
			} else {
				arg.append("client_id", CLIENT_ID_TEST).append("client_secret", CLIENT_SECRET_TEST);
			}
			String params = arg.getJson();
			Map<String, String> headers = (new Arg("Content-Type", "application/json")).getArgs();
			try {
				String json = HttpsClientUtil.postData(url, params, headers);
				Map<String, Object> respM = JSONUtil.jsonToMap(json);
				if (respM.containsKey("access_token")) {
					token = respM.get("access_token").toString();
					expires_in = MapUtil.getLong(respM, "expires_in") * 1000 + t;
				}
			} catch (IOException e) {
				log.error("GET token error:", e);
			}
		}
		return token;
	}

	public String getUsername(long uid) {
		return String.valueOf(uid);
	}

	public String getPwd(String username) {
		return MDUtil.MD5.digest2HEX("BiBi" + username + "bpwd");
	}

	public String reg(String username, String nickname) {
		String url = getUrl("users");
		String token = getToken();
		String pwd = getPwd(username);
		Arg arg = new Arg("username", username).append("password", pwd);
		if (StringUtils.isNotBlank(nickname)) {
			arg.append("nickname", nickname);
		}
		String params = arg.getJson();
		Map<String, String> headers = (new Arg("Content-Type", "application/json").append("Authorization",
				"Bearer " + token)).getArgs();
		try {
			String json = HttpsClientUtil.postData(url, params, headers);
			// System.out.println(json);
			Map<String, Object> respM = JSONUtil.jsonToMap(json);
			if (!respM.containsKey("error")) {
				return pwd;
			} else if ("duplicate_unique_property_exists".equals(respM.get("error").toString())) {
				return pwd;
			}
		} catch (IOException e) {
			log.error("GET token error:", e);
		}
		return null;
	}

	public boolean addFriend(String owner, String friend) {
		String url = getUrl("users/" + owner + "/contacts/users/" + friend);
		String token = getToken();
		Map<String, String> headers = (new Arg("Authorization", "Bearer " + token)).getArgs();
		try {
			String json = HttpsClientUtil.postData(url, null, headers);
			Map<String, Object> respM = JSONUtil.jsonToMap(json);
			if (!respM.containsKey("error")) {
				return true;
			}
		} catch (IOException e) {
			log.error("GET token error:", e);
		}
		return false;
	}

	public boolean delFriend(String owner, String friend) {
		String url = getUrl("users/" + owner + "/contacts/users/" + friend);
		String token = getToken();
		Map<String, String> headers = (new Arg("Authorization", "Bearer " + token)).getArgs();
		try {
			String json = HttpsClientUtil.delData(url, null, headers);
			Map<String, Object> respM = JSONUtil.jsonToMap(json);
			if (!respM.containsKey("error")) {
				return true;
			}
		} catch (IOException e) {
			log.error("GET token error:", e);
		}
		return false;
	}

	public boolean addBlocks(String owner, String friend) {
		String url = getUrl("users/" + owner + "/blocks/users");
		String token = getToken();
		Map<String, String> headers = (new Arg("Authorization", "Bearer " + token)).getArgs();
		String[] bs = { friend };
		Map<String, String[]> arg = new HashMap<String, String[]>();
		arg.put("usernames", bs);
		try {
			String json = HttpsClientUtil.postData(url, JSONUtil.beanToJson(arg), headers);
			Map<String, Object> respM = JSONUtil.jsonToMap(json);
			if (!respM.containsKey("error")) {
				return true;
			}
		} catch (IOException e) {
			log.error("GET token error:", e);
		}
		return false;
	}

	public boolean delBlocks(String owner, String friend) {
		String url = getUrl("users/" + owner + "/blocks/users/" + friend);
		String token = getToken();
		Map<String, String> headers = (new Arg("Authorization", "Bearer " + token)).getArgs();
		try {
			String json = HttpsClientUtil.delData(url, null, headers);
			Map<String, Object> respM = JSONUtil.jsonToMap(json);
			if (!respM.containsKey("error")) {
				return true;
			}
		} catch (IOException e) {
			log.error("GET token error:", e);
		}
		return false;
	}

	public boolean deactivate(String username) {
		String url = getUrl("users/" + username + "/deactivate");
		String token = getToken();

		String params = null;
		Map<String, String> headers = (new Arg("Content-Type", "application/json").append("Authorization",
				"Bearer " + token)).getArgs();
		try {
			String json = HttpsClientUtil.postData(url, params, headers);
			Map<String, Object> respM = JSONUtil.jsonToMap(json);
			if (!respM.containsKey("error")) {
				return true;
			}
		} catch (IOException e) {
			log.error("GET token error:", e);
		}
		return false;
	}

	public boolean activate(String username) {
		String url = getUrl("users/" + username + "/activate");
		String token = getToken();

		String params = null;
		Map<String, String> headers = (new Arg("Content-Type", "application/json").append("Authorization",
				"Bearer " + token)).getArgs();
		try {
			String json = HttpsClientUtil.postData(url, params, headers);
			Map<String, Object> respM = JSONUtil.jsonToMap(json);
			if (!respM.containsKey("error")) {
				return true;
			}
		} catch (IOException e) {
			log.error("GET token error:", e);
		}
		return false;
	}

	public boolean disconnect(String username) {
		String url = getUrl("users/" + username + "/disconnect");
		String token = getToken();

		String params = null;
		Map<String, String> headers = (new Arg("Content-Type", "application/json").append("Authorization",
				"Bearer " + token)).getArgs();
		try {
			String json = HttpsClientUtil.postData(url, params, headers);
			Map<String, Object> respM = JSONUtil.jsonToMap(json);
			if (!respM.containsKey("error")) {
				return true;
			}
		} catch (IOException e) {
			log.error("GET token error:", e);
		}
		return false;
	}

	public boolean updateNickname(String username, String nickname) {
		String url = getUrl("users/" + username);
		String token = getToken();
		Arg arg = new Arg("nickname", nickname);
		String params = arg.getJson();
		Map<String, String> headers = (new Arg("Authorization", "Bearer " + token)).getArgs();
		try {
			String json = HttpsClientUtil.putData(url, params, headers);
			Map<String, Object> respM = JSONUtil.jsonToMap(json);
			if (!respM.containsKey("error")) {
				return true;
			}
		} catch (IOException e) {
			log.error("GET token error:", e);
		}
		return false;
	}
	
	public boolean sendMsgTxt(TargetType mt, Long toUid, String msg, long fuid,Map<String,Object> ext) {
		List<Long> toUids = new ArrayList<Long>();
		toUids.add(toUid);
		return this.sendMsgTxt(mt, toUids, msg, fuid,ext);
	}

	// 通过服务器发送文本消息
	public boolean sendMsgTxt(TargetType mt, List<Long> toUids, String msg, long fuid,Map<String,Object> ext) {
		String url = getUrl("messages");
		String token = getToken();
		List<String> toUs = new ArrayList<String>();
		for (Long uid : toUids) {
			toUs.add(this.getUsername(uid));
		}
		EmTxtMsg etMsg = new EmTxtMsg(mt, toUs, getUsername(fuid),ext);
		etMsg.msg(msg);
		String params = etMsg.json();
		Map<String, String> headers = (new Arg("Authorization", "Bearer " + token)).getArgs();
		try {
			String json = HttpsClientUtil.putData(url, params, headers);
			Map<String, Object> respM = JSONUtil.jsonToMap(json);
			if (!respM.containsKey("error")) {
				return true;
			}
		} catch (IOException e) {
			log.error("GET token error:", e);
		}
		return false;
	}
	
	// 通过服务器发送透传消息
		public boolean sendMsgCmd(TargetType mt, Long toUid, String action, long fuid, Map<String, Object> ext) {
			List<Long> toUids = new ArrayList<Long>();
			toUids.add(toUid);
			return this.sendMsgCmd(mt, toUids, action, fuid, ext);
		}

	// 通过服务器发送透传消息
	public boolean sendMsgCmd(TargetType mt, List<Long> toUids, String action, long fuid, Map<String, Object> ext) {
		String url = getUrl("messages");
		String token = getToken();
		List<String> toUs = new ArrayList<String>();
		for (Long uid : toUids) {
			toUs.add(this.getUsername(uid));
		}
		EmCmdMsg etMsg = new EmCmdMsg(mt, toUs, getUsername(fuid),ext);
		etMsg.action(action);
		String params = etMsg.json();
		Map<String, String> headers = (new Arg("Authorization", "Bearer " + token)).getArgs();
		try {
			String json = HttpsClientUtil.putData(url, params, headers);
			Map<String, Object> respM = JSONUtil.jsonToMap(json);
			if (!respM.containsKey("error")) {
				return true;
			}
		} catch (IOException e) {
			log.error("GET token error:", e);
		}
		return false;
	}

	public static void main(String[] args) {
		EasemobService es = new EasemobService();
		List<Long> uids = new ArrayList<Long>();
		uids.add(100145403L);
		es.sendMsgTxt(TargetType.users, uids, "测试服务器消息", 100184853,null);
		// System.out.println(es.reg("test1", "test"));
	}

}
