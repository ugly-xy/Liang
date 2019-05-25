package com.zb.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.crypto.MDUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.game.Game;
import com.zb.models.game.GameCP;
import com.zb.models.game.GameLog;
import com.zb.models.game.OpenUser;
import com.zb.service.third.PingAnGameService;

@Deprecated
@Service
public class GameService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(GameService.class);

	@Autowired
	PingAnGameService pingAnGameService;

	@Autowired
	UserService userService;

	public DBObject findGameById(Long id) {
		return getC(DocName.GAME).findOne(new BasicDBObject("_id", id));
	}

	public List<DBObject> findGame(Integer type, Long provider, Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (type != null && type != 0) {
			q.put("type", type);
		}
		if (null != provider && provider != 0) {
			q.put("provider", provider);
		}

		return getC(DocName.GAME).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", -1)).toArray();
	}

	public DBObject findGame(String appKey) {
		DBObject q = new BasicDBObject("appKey", appKey);
		List<DBObject> dbos = getC(DocName.GAME).find(q).skip(0).limit(getSize(1)).sort(new BasicDBObject("sort", -1))
				.toArray();
		if (dbos.size() > 0)
			return dbos.get(0);
		return null;
	}

	public int countGame(Integer type, Long provider, Integer status) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (type != null && type != 0) {
			q.put("type", type);
		}
		if (null != provider && provider != 0) {
			q.put("provider", provider);
		}
		return getC(DocName.GAME).find(q).count();
	}

	public Page<DBObject> queryGame(Integer type, Long provider, Integer status, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findGame(type, provider, status, page, size);
		int count = countGame(type, provider, status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public ReMsg upsert(Long id, Integer type, Long provider, Integer status, String title, Integer sort, Integer ch,
			Integer childCh, String url, String pic, String description, Integer count) {
		Long adminId = super.getUid();
		if (id == null || id < 1L) {
			id = super.getNextId(DocName.GAME);
			String appKey = MDUtil.MD5.digest2HEX("open" + id);
			String appSecret = (new ObjectId()).toString();
			Game banner = new Game(id, type, provider, status, title, sort, adminId, ch, childCh, url, pic, description,
					count, appKey, appSecret);
			getMongoTemplate().save(banner);
			return new ReMsg(ReCode.OK);
		} else {
			DBObject dbo = new BasicDBObject();
			if (null != type && type != 0) {
				dbo.put("type", type);
			}
			if (null != provider && provider != 0) {
				dbo.put("provider", provider);
			}
			if (StringUtils.isNotBlank(title)) {
				dbo.put("title", title);
			}
			if (StringUtils.isNotBlank(pic)) {
				dbo.put("pic", pic);
			}
			if (status != null && status != 0) {
				dbo.put("status", status);
			}
			if (sort != null && sort != 0) {
				dbo.put("sort", sort);
			}
			if (ch != null && ch != 0) {
				dbo.put("ch", ch);
			}
			if (childCh != null && childCh != 0) {
				dbo.put("childCh", childCh);
			}
			if (count != null && count != 0) {
				dbo.put("count", count);
			}
			if (StringUtils.isNotBlank(url)) {
				dbo.put("url", url);
			}
			if (StringUtils.isNotBlank(description)) {
				dbo.put("description", description);
			}

			super.getC(DocName.GAME).update(new BasicDBObject("_id", id), new BasicDBObject("$set", dbo));
			return new ReMsg(ReCode.OK);
		}
		// return new ReMsg(ReCode.FAIL);
	}

	public ReMsg getGameUserCenter(HttpServletRequest req) throws UnsupportedEncodingException, Exception {
		String curUrl = pingAnGameService.getUserCenter(req);
		Map<String, String> m = new HashMap<String, String>();
		m.put("title", "游戏用户中心");
		m.put("url", curUrl);
		return new ReMsg(ReCode.OK, m);
	}

	public ReMsg loginGame(Long id, HttpServletRequest req) throws UnsupportedEncodingException, Exception {
		Long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject dbo = findGameById(id);
		Long provider = DboUtil.getLong(dbo, "provider");
		String url = DboUtil.getString(dbo, "url");
		String title = DboUtil.getString(dbo, "title");
		Map<String, String> m = new HashMap<String, String>();
		m.put("title", title);
		if (provider == 1) {
			String curUrl = pingAnGameService.getUrl(req, url);
			m.put("url", curUrl);
		} else {
			String accessToken = MDUtil.SHA.digest2HEX((new ObjectId()).toString() + "GameToken");
			ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
			opsv.set(RK.openToken(accessToken), uid.toString(), 10, TimeUnit.MINUTES);
			String curUrl = null;
			String appKey = DboUtil.getString(dbo, "appKey");
			if (url.contains("&")) {
				curUrl = url + "&";
			} else if (!url.contains("?")) {
				curUrl = url + "?";
			} else {
				curUrl = url;
			}
			long timestamp = System.currentTimeMillis();
			DBObject cpDbo = this.findGameCpById(provider);
			String signKey = DboUtil.getString(cpDbo, "signKey");
			String sign = MDUtil.SHA.digest2HEX(accessToken + appKey + timestamp + signKey);
			curUrl = curUrl + "zdbAToken=" + accessToken + "&zdbAppKey=" + appKey + "&sign=" + sign + "&timestamp="
					+ timestamp;
			m.put("url", curUrl);
		}
		if (m.containsKey("url")) {
			upsertGameLog(uid, id);
		}
		return new ReMsg(ReCode.OK, m);
	}

	public ReMsg getUserInfo(String accessToken, String appKey) {
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String uid = opsv.get(RK.openToken(accessToken));
		getRedisTemplate().delete(RK.openToken(accessToken));
		if (StringUtils.isNotBlank(uid)) {
			Long userId = Long.parseLong(uid);
			DBObject user = userService.findById(userId);
			Map<String, String> m = new HashMap<String, String>();
			DBObject dbo = this.findGame(appKey);
			if (dbo != null) {
				String appSecret = DboUtil.getString(dbo, "appSecret");
				String openId = MDUtil.MD5.digest2HEX(appKey + userId + appSecret);
				DBObject openUser = this.findOpenUserById(openId);
				if (openUser == null) {
					this.saveOpenUser(openId, appKey, userId, DboUtil.getLong(dbo, "_id"),
							DboUtil.getLong(dbo, "provider"));
				}
				m.put("openId", openId);
				m.put("nickname", DboUtil.getString(user, "nickname"));
				m.put("sex", DboUtil.getInt(user, "sex") == 2 ? "女" : "男");
				return new ReMsg(ReCode.OK, m);
			}
		}
		return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
	}

	public DBObject findGameCpById(Long id) {
		return getC(DocName.GAME_CP).findOne(new BasicDBObject("_id", id));
	}

	public List<DBObject> findGameCP(Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.GAME_CP).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", -1)).toArray();
	}

	public int countGameCP(Integer status) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.GAME_CP).find(q).count();
	}

	public Page<DBObject> queryGameCP(Integer status, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findGameCP(status, page, size);
		int count = countGameCP(status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public ReMsg upsertCP(Long id, Integer status, Integer sort, String url, String pic, String description,
			String name, String signKey) {
		Long adminId = super.getUid();
		if (id == null || id < 1L) {
			id = super.getNextId(DocName.GAME_CP);
			GameCP cp = new GameCP(id, status, sort, adminId, url, pic, description, name, signKey);
			getMongoTemplate().save(cp);
			return new ReMsg(ReCode.OK);
		} else {
			DBObject dbo = new BasicDBObject();
			if (StringUtils.isNotBlank(signKey)) {
				dbo.put("signKey", signKey);
			}
			if (StringUtils.isNotBlank(name)) {
				dbo.put("name", name);
			}
			if (StringUtils.isNotBlank(description)) {
				dbo.put("description", description);
			}
			if (StringUtils.isNotBlank(pic)) {
				dbo.put("pic", pic);
			}
			if (status != null && status != 0) {
				dbo.put("status", status);
			}
			if (sort != null && sort != 0) {
				dbo.put("sort", sort);
			}

			if (StringUtils.isNotBlank(url)) {
				dbo.put("url", url);
			}
			if (StringUtils.isNotBlank(description)) {
				dbo.put("description", description);
			}

			super.getC(DocName.GAME_CP).update(new BasicDBObject("_id", id), new BasicDBObject("$set", dbo));
			return new ReMsg(ReCode.OK);
		}
		// return new ReMsg(ReCode.FAIL);
	}

	public ReMsg upsertGameLog(Long userId, Long gameId) {
		Long id = super.getNextId(DocName.GAME_LOG);
		GameLog banner = new GameLog(id, userId, gameId);
		getMongoTemplate().save(banner);
		return new ReMsg(ReCode.OK);
	}

	public DBObject findOpenUserById(String openId) {
		return getC(DocName.OPEN_USER).findOne(new BasicDBObject("_id", openId));
	}

	public ReMsg saveOpenUser(String id, String appKey, Long uid, Long appId, Long provider) {
		OpenUser cp = new OpenUser(id, appKey, uid, appId, provider);
		getMongoTemplate().save(cp);
		return new ReMsg(ReCode.OK);
	}

}
