package com.zb.service;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.zb.common.AuthCode;
import com.zb.common.Constant.Param;
import com.zb.common.Constant.ReCode;
import com.zb.common.crypto.BlowFishUtil;
import com.zb.common.crypto.MDUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.IpUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.redis.RedisUtil;
import com.zb.core.web.ReMsg;
import com.zb.core.web.interceptor.OAuth2SimpleInterceptor;

public class BaseService {
	static final Logger log = LoggerFactory.getLogger(BaseService.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private RedisTemplate<String, String> chatRedisTemplate;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	// @Autowired
	// @Qualifier("mongoTemplate2")
	// private MongoTemplate mongoTemplate2;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	
	public DBCollection getC(String name) {
		return getC(mongoTemplate, name);
	}

	public DBObject findById(String docName, Long id) {
		return getC(docName).findOne(new BasicDBObject("_id", id));
	}

	public DBCollection getC(MongoTemplate tmpl, String name) {
		return tmpl.getCollection(name);
	}

	private static final String SEQ = "seq";
	private static final DBObject seqField = new BasicDBObject(SEQ, Integer.valueOf(1));
	private static final DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject(SEQ, Long.valueOf(1)));

	public long incrMsgId() {
		return incrId(chatRedisTemplate, RK.imMsgId());
	}

	public long incrId(String key) {
		return incrId(chatRedisTemplate, key);
	}

	public long incrId(RedisTemplate<String, String> tmp, String key) {
		return RedisUtil.incrId(tmp, key);
	}

	public boolean lock(String key, long time) {
		return lock(redisTemplate, key, time);
	}

	public boolean lock(RedisTemplate<String, String> tmp, String key, long time) {
		return RedisUtil.lock(tmp, key, time);
	}

	public void unlock(String key) {
		unlock(redisTemplate, key);
	}

	public void unlock(RedisTemplate<String, String> tmp, String key) {
		RedisUtil.unlock(tmp, key);
	}

	// public void pubMessageIm(String channel, String json) {
	// chatRedisTemplate.convertAndSend(channel, json);
	// }

	public Long getCurrentId(String name) {
		return DboUtil.getLong(mongoTemplate.getCollection("counters").findOne(new BasicDBObject("_id", name)), "seq");
	}

	public Long getNextId(MongoTemplate tmpl, String name) {
		return (Long) tmpl.getCollection("counters")
				.findAndModify(new BasicDBObject("_id", name), seqField, null, false, incSeq, true, true).get(SEQ);
	}

	public Long getNextId(String name) {
		return (Long) mongoTemplate.getCollection("counters")
				.findAndModify(new BasicDBObject("_id", name), seqField, null, false, incSeq, true, true).get(SEQ);
	}

	public RedisTemplate<String, String> getRedisTemplate() {
		return redisTemplate;
	}

	public RedisTemplate<String, String> getChatRedisTemplate() {
		return chatRedisTemplate;
	}

	public static final String CODE_URL = "/api/authcode";

	Map<String, String> authcodeImage(HttpServletRequest req) {
		String key = RandomUtil.random(8 + ((int) System.currentTimeMillis() & 1)).toLowerCase();
		String url = CODE_URL + "?authKey=" + key;
		Map<String, String> rs = new HashMap<String, String>();
		rs.put("authKey", key);
		rs.put("authUrl", url);
		return rs;
	}

	public void authcode(HttpServletRequest req, HttpServletResponse response) throws IOException {
		String code = RandomUtil.random(4 + ((int) System.currentTimeMillis() & 1));
		String request_key = req.getParameter("authKey");
		String key = RK.authCode(request_key);
		getRedisTemplate().opsForValue().set(key, code, 60L, TimeUnit.SECONDS);

		response.addHeader("Content-Type", "image/png");
		AuthCode.draw(code, 160, 48, response.getOutputStream());

	}

	boolean codeVeri(HttpServletRequest req) {
		String auth_code = req.getParameter("authCode");
		String auth_key = req.getParameter("authKey");
		String key = RK.authCode(auth_key);
		Object red_code = getRedisTemplate().opsForValue().get(key);
		if (null == auth_code || null == red_code || !auth_code.equalsIgnoreCase(red_code.toString())) {
			getRedisTemplate().delete(key);
			return true;
		}
		return false;
	}

	private static final String KEY = "c1s112312az01gc";
	private static final String VECTOR = "a1da9lcz";

	private static final String VECTOR_DECODE = "a11mZlcz";

	private static final String MD5_KEY = "dfdsafas";

	public ReMsg getKey(String reqKey, double ver, int via, String data, long timestarp) {
		// System.out.println(MDUtil.MD5.digest2HEX(reqKey + via + MD5_KEY +
		// timestarp));
		if (!MDUtil.MD5.digest2HEX(reqKey + via + MD5_KEY + timestarp).equals(data)) {
			return new ReMsg(ReCode.SECURITY_SIGN_ERR);
		}
		String key = randomKey(15);
		long date = System.currentTimeMillis() + 1000 * 60 * 10;
		redisTemplate.opsForValue().set(reqKey, key, 10, TimeUnit.MINUTES);
		String strEn;
		try {
			strEn = BlowFishUtil.encryptHex(KEY, key + "," + date, VECTOR);
			return new ReMsg(ReCode.OK, strEn);
		} catch (Exception e) {
			log.error("BlowFish err:", e);
		}
		return new ReMsg(ReCode.SYS_ERR);
	}

	public String getKeyByCache(String reqKey) {
		return redisTemplate.opsForValue().get(reqKey);
	}

	String decoder(String reqKey, String ver, String via, String data)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		String key = redisTemplate.opsForValue().get(reqKey);
		if (null != key && StringUtils.isNotBlank(data)) {
			return BlowFishUtil.decryptHex(key, data, VECTOR_DECODE);
		}

		return null;
	}

	public String encoder(String reqKey, String ver, String via, String data)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		String key = redisTemplate.opsForValue().get(reqKey);
		String strEn = BlowFishUtil.encryptHex(key, data, VECTOR_DECODE);
		return strEn;
	}

	static final char[] SEEDS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnPpQqRrSsTtUuVvWwXxYyZz1234567890".toCharArray();

	String randomKey(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("size must > 0");
		}
		char[] result = new char[size];
		Random random = ThreadLocalRandom.current();
		int len = SEEDS.length;
		for (int i = 0; i < size; i++) {
			result[i] = SEEDS[random.nextInt(len)];
		}
		return new String(result);
	}

	public int getChannel(HttpServletRequest req) {
		String cchan = req.getParameter(Param.CHILD_CHANNEL);
		if (StringUtils.isNotBlank(cchan)) {
			int cch = Integer.parseInt(cchan);
			if (cch > 10000) {
				return cch;
			} else {
				String chan = req.getParameter(Param.CHANNEL);
				if (StringUtils.isNotBlank(chan)) {
					int ch = Integer.parseInt(chan);
					return ch * 10000 + cch;
				}
			}
		}
		return 0;
	}

	public int getCh(HttpServletRequest req) {
		int c = getChannel(req);
		if (c > 0) {
			return c / 10000;
		}
		return 0;
	}

	public int getChildCh(HttpServletRequest req) {
		int c = getChannel(req);
		if (c > 0) {
			return c % 10000;
		}
		return 0;
	}

	public int getVia(HttpServletRequest req) {
		String svia = req.getParameter(Param.VIA);
		if (StringUtils.isNotBlank(svia))
			return Integer.parseInt(svia);
		return 0;
	}

	public String getDevId(HttpServletRequest req) {
		return req.getParameter(Param.DEV_ID);
	}

	public String getToken(HttpServletRequest req) {
		return req.getParameter(Param.TOKEN);
	}

	public String getClientId(HttpServletRequest req) {
		String client_id = getDevId(req);
		if (StringUtils.isNotBlank(client_id)) {
			return client_id;
		}
		return getIp(req);
	}

	public Double getVer(HttpServletRequest req) {
		String ver = req.getParameter(Param.VERSION);
		if (StringUtils.isNotBlank(ver)) {
			return Double.parseDouble(ver);
		}
		return null;
	}

	public Long getUid(String acctoken) {
		Object obj = getRedisTemplate().opsForValue().get(RK.accessToken(acctoken));
		if (null != obj)
			return Long.valueOf(obj.toString());
		return 0L;
	}

	String getIp(HttpServletRequest req) {
		return IpUtil.getIpAddr(req);
	}

	String getData(HttpServletRequest req) {
		return req.getParameter(Param.DATA);// 数据
	}

	String getKey(HttpServletRequest req) {
		return req.getParameter(Param.REQ_KEY);// 客户端
	}

	public long getUid() {
		Object obj = null;
		if (OAuth2SimpleInterceptor.getSession() != null) {
			obj = OAuth2SimpleInterceptor.getSession().get("_id");
		}
		if (null != obj)
			return Long.parseLong(obj.toString());
		return 0l;
	}

	public Object getUser(String key) {
		if (OAuth2SimpleInterceptor.getSession() != null) {
			return OAuth2SimpleInterceptor.getSession().get(key);
		}
		return null;
	}

	protected int getSize(HttpServletRequest req) {
		return getSize(T2TUtil.str2Int(req.getParameter(Param.SIZE), 20));
	}

	protected int getPage(HttpServletRequest req) {
		return T2TUtil.str2Int(req.getParameter(Param.PAGE), 1);
	}

	protected int getPage(Integer page) {
		if (page == null || page < 1) {
			page = 1;
		}
		return page;
	}

	protected int getSize(Integer size) {
		if (size == null || size < 1 || size > 1000) {
			size = 20;
		}
		return size;
	}

	protected int getStart(Integer page, Integer size) {
		if (null == page || page < 1) {
			page = 1;
		}
		size = getSize(size);
		return (page - 1) * size;
	}

	protected DBObject removeFile(DBObject dbo, String[] fields) {
		for (String field : fields) {
			dbo.removeField(field);
		}
		return dbo;
	}

}
