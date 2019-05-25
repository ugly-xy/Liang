package com.zb.service.third;

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
import com.zb.models.third.*;
import com.zb.service.BaseService;
import com.zb.service.UserService;
import com.zb.service.third.PingAnGameService;

@Service
public class ThirdService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(ThirdService.class);

	@Autowired
	PingAnGameService pingAnGameService;

	@Autowired
	UserService userService;

	public DBObject findAppById(String id) {
		return getC(DocName.APP).findOne(new BasicDBObject("_id", id));
	}

	public List<DBObject> findApp(Integer type, Long provider, Integer status,
			Integer page, Integer size) {
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

		return getC(DocName.APP).find(q).skip(super.getStart(page, size))
				.limit(getSize(size)).sort(new BasicDBObject("sort", -1))
				.toArray();
	}

	public int countApp(Integer type, Long provider, Integer status) {
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
		return getC(DocName.APP).find(q).count();
	}

	public Page<DBObject> queryApp(Integer type, Long provider, Integer status,
			Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findApp(type, provider, status, page, size);
		int count = countApp(type, provider, status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public ReMsg upsert(String id, Integer type, Long provider, Integer status,
			String title, Integer sort, Integer ch, Integer childCh,
			String url, String pic, String description, Integer count,
			String callback, String blowfishKey, String blowfishVecter,
			String showUrl, Integer urlStyle) {
		Long adminId = super.getUid();
		if (StringUtils.isBlank(id)) {
			long tid = super.getNextId(DocName.APP);
			String appId = MDUtil.MD5.digest2HEX("open" + tid);
			String appSecret = (new ObjectId()).toString();
			App banner = new App(appId, type, provider, status, title, sort,
					adminId, ch, childCh, url, pic, description, count,
					appSecret, callback, blowfishKey, blowfishVecter, showUrl,
					urlStyle);
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
			if (StringUtils.isNotBlank(callback)) {
				dbo.put("callback", callback);
			}

			if (StringUtils.isNotBlank(blowfishKey)) {
				dbo.put("blowfishKey", blowfishKey);
			}

			if (StringUtils.isNotBlank(blowfishVecter)) {
				dbo.put("blowfishVecter", blowfishVecter);
			}
			if (StringUtils.isNotBlank(showUrl)) {
				dbo.put("showUrl", showUrl);
			}
			if (urlStyle != null && urlStyle != 0) {
				dbo.put("urlStyle", urlStyle);
			}
			super.getC(DocName.APP).update(new BasicDBObject("_id", id),
					new BasicDBObject("$set", dbo));
			return new ReMsg(ReCode.OK);
		}
		// return new ReMsg(ReCode.FAIL);
	}

	public ReMsg getAppUserCenter(HttpServletRequest req)
			throws UnsupportedEncodingException, Exception {
		String curUrl = pingAnGameService.getUserCenter(req);
		Map<String, String> m = new HashMap<String, String>();
		m.put("title", "游戏用户中心");
		m.put("url", curUrl);
		return new ReMsg(ReCode.OK, m);
	}

	public ReMsg loginApp(String id, HttpServletRequest req)
			throws UnsupportedEncodingException, Exception {
		Long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject dbo = findAppById(id);
		Long provider = DboUtil.getLong(dbo, "provider");
		String url = DboUtil.getString(dbo, "url");
		String title = DboUtil.getString(dbo, "title");
		Map<String, String> m = new HashMap<String, String>();
		m.put("title", title);
		if (provider == 1) {
			String curUrl = pingAnGameService.getUrl(req, url);
			m.put("url", curUrl);
		} else {
			String accessToken = MDUtil.SHA.digest2HEX((new ObjectId())
					.toString() + "AppToken");
			ValueOperations<String, String> opsv = getRedisTemplate()
					.opsForValue();
			opsv.set(RK.openToken(accessToken), uid.toString(), 10,
					TimeUnit.MINUTES);
			String curUrl = null;
			String appId = DboUtil.getString(dbo, "_id");

			long timestamp = System.currentTimeMillis();
			// DBObject cpDbo = this.findMerchantById(provider);
			String signKey = DboUtil.getString(dbo, "appSecret");
			String sign = MDUtil.SHA.digest2HEX(accessToken + appId + timestamp
					+ signKey);
			int urlStyle = DboUtil.getInt(dbo, "urlStyle");
			if (urlStyle == 2) {
				if (url.endsWith("/")) {
					curUrl = url;
				} else {
					curUrl = url + "/";
				}
				curUrl = curUrl + appId + "/" + accessToken + "/" + sign + "/"
						+ timestamp;
			} else {
				if (url.contains("&")) {
					curUrl = url + "&";
				} else if (!url.contains("?")) {
					curUrl = url + "?";
				} else {
					curUrl = url;
				}
				curUrl = curUrl + "zdbAToken=" + accessToken + "&zdbAppKey="
						+ appId + "&sign=" + sign + "&timestamp=" + timestamp;
			}
			m.put("url", curUrl);
		}
		if (m.containsKey("url")) {
			upsertAppLog(uid, id);
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
			DBObject dbo = this.findAppById(appKey);
			if (dbo != null) {
				String appSecret = DboUtil.getString(dbo, "appSecret");
				String openId = MDUtil.MD5.digest2HEX(appKey + userId
						+ appSecret);
				DBObject openUser = this.findOpenUserById(openId);
				if (openUser == null) {
					this.saveOpenUser(openId, DboUtil.getString(dbo, "_id"),
							userId, DboUtil.getLong(dbo, "provider"));
				}
				m.put("openId", openId);
				m.put("nickname", DboUtil.getString(user, "nickname"));
				m.put("sex", DboUtil.getInt(user, "sex") == 2 ? "女" : "男");
				m.put("avatar", DboUtil.getString(user, "avatar"));
				return new ReMsg(ReCode.OK, m);
			}
		}
		return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
	}

	public DBObject findMerchantById(Long id) {
		return getC(DocName.MERCHANT).findOne(new BasicDBObject("_id", id));
	}

	public List<DBObject> findMerchant(Integer status, Integer page,
			Integer size) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.MERCHANT).find(q).skip(super.getStart(page, size))
				.limit(getSize(size)).sort(new BasicDBObject("sort", -1))
				.toArray();
	}

	public int countMerchant(Integer status) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.MERCHANT).find(q).count();
	}

	public Page<DBObject> queryMerchant(Integer status, Integer page,
			Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findMerchant(status, page, size);
		int count = countMerchant(status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public ReMsg upsertMerchant(Long id, Integer status, Integer sort,
			String url, String pic, String description, String name,
			String signKey) {
		Long adminId = super.getUid();
		if (id == null || id < 1L) {
			id = super.getNextId(DocName.MERCHANT);
			Merchant cp = new Merchant(id, status, sort, adminId, url, pic,
					description, name, signKey);
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

			super.getC(DocName.MERCHANT).update(new BasicDBObject("_id", id),
					new BasicDBObject("$set", dbo));
			return new ReMsg(ReCode.OK);
		}
		// return new ReMsg(ReCode.FAIL);
	}

	public ReMsg upsertAppLog(Long userId, String AppId) {
		Long id = super.getNextId(DocName.APP_LOG);
		AppLog banner = new AppLog(id, userId, AppId);
		getMongoTemplate().save(banner);
		return new ReMsg(ReCode.OK);
	}

	public DBObject findOpenUserById(String openId) {
		return getC(DocName.OPEN_USER)
				.findOne(new BasicDBObject("_id", openId));
	}

	public ReMsg saveOpenUser(String id, String appId, Long uid, Long provider) {
		OpenUser cp = new OpenUser(id, uid, appId, provider);
		getMongoTemplate().save(cp);
		return new ReMsg(ReCode.OK);
	}

}
