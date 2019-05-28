package com.zb.service;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.crypto.MDUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.SmsLog;

@Service
public class SmsService extends BaseService {

	private String comName; // 企业用户登陆名
	private String comPwd; // 企业用户登陆密码

	public SmsService() {
	}

	public SmsService(String name, String pwd) {
		this.comName = name;
		this.comPwd = pwd;
	}

	public ReMsg sendAuthCode(HttpServletRequest req, Long phone) throws UnsupportedEncodingException {
//		if (phone != null) {
//			String ip = super.getIp(req);
//			ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
//
//			if (!isChinaPhoneLegal(phone + "")) {
//				return new ReMsg(ReCode.PHONE_NUMBER_FORMAT_ERROR);
//			}
//
//			//System.out.println(getRedisTemplate().getExpire(RK.smsPhone(phone), TimeUnit.SECONDS));
//			if (24 * 3600 - getRedisTemplate().getExpire(RK.smsPhone(phone), TimeUnit.SECONDS) < 60) {
//				return new ReMsg(ReCode.SMS_PHONE_TOO_OFTEN_60);
//			}
//			Long count = opsv.increment(RK.smsPhone(phone), 1);
//
//			if (count > 3) {
//				return new ReMsg(ReCode.SMS_PHONE_TOO_OFTEN);
//			}
//
//			count = opsv.increment(RK.smsIp(ip), 1);
//			if (count > 3) {// ip 次数限制
//				return new ReMsg(ReCode.SMS_IP_TOO_OFTEN);
//			}
//
//			String authCode = random(4);
//			long id = super.getNextId(DocName.SMS_LOG);
//			SmsLog sl = new SmsLog(id, phone, authCode, ip);
//			super.getMongoTemplate().save(sl);
//			String res = this.singleSend("" + phone, "您的验证码为：" + authCode + ",如非本人操作，请忽略！");
//			getRedisTemplate().expire(RK.smsPhone(phone), 1, TimeUnit.DAYS);
//			if (StringUtils.isNotBlank(res) && res.contains(",")) {
//				String[] strs = res.split(",");
//				if ("0".equals(strs[1])) {
//					getRedisTemplate().expire(RK.smsIp(ip), 1, TimeUnit.DAYS);
//					super.getC(DocName.SMS_LOG).update(new BasicDBObject("_id", id),
//							new BasicDBObject("$set", new BasicDBObject("sendStatus", Const.STATUS_OK)));
//					return new ReMsg(ReCode.OK);
//				}
//			}
//			opsv.increment(RK.smsIp(ip), -1);
//			opsv.increment(RK.smsPhone(phone), -1);
//		}
		return new ReMsg(ReCode.SMS_SEND_ERR);

	}

	public ReMsg sendAuthCode(HttpServletRequest req, Long phone, Long timestamp, String tokenCode)
			throws UnsupportedEncodingException {
		if (null==phone||phone<10000000000L) {
			return new ReMsg(ReCode.SMS_DRAW_ERR);
		}
		if (System.currentTimeMillis() > timestamp) {
			return new ReMsg(ReCode.SMS_SEND_OUTTIME_ERR);
		}
		
		if (24 * 3600 - getRedisTemplate().getExpire(RK.smsPhone(phone), TimeUnit.SECONDS) < 60) {
			return new ReMsg(ReCode.SMS_PHONE_TOO_OFTEN_60);
		}
		
		String key = super.getKeyByCache("" + phone);
		if (!MDUtil.MD5.digest2HEX(key + timestamp + phone).equals(tokenCode)) {
			return new ReMsg(ReCode.SMS_DRAW_ERR);
		}

		String ip = super.getIp(req);
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();

		Long count = opsv.increment(RK.smsPhone(phone), 1);
		
		if (count > 5) {
			return new ReMsg(ReCode.SMS_PHONE_TOO_OFTEN);
		}

		count = opsv.increment(RK.smsIp(ip), 1);
		if (count > 10) {// ip 次数限制
			return new ReMsg(ReCode.SMS_IP_TOO_OFTEN);
		}

		String authCode = random(4);
		long id = super.getNextId(DocName.SMS_LOG);
		SmsLog sl = new SmsLog(id, phone, authCode, ip);
		super.getMongoTemplate().save(sl);
		getRedisTemplate().expire(RK.smsPhone(phone), 1, TimeUnit.DAYS);
		String res = this.singleSend("" + phone, "您的验证码为：" + authCode + ",如非本人操作，请忽略！");
		// System.out.println("res:" + res);
		if (StringUtils.isNotBlank(res) && res.contains(",")) {
			String[] strs = res.split(",");
			if ("0".equals(strs[1])) {
				getRedisTemplate().expire(RK.smsIp(ip), 1, TimeUnit.DAYS);
				super.getC(DocName.SMS_LOG).update(new BasicDBObject("_id", id),
						new BasicDBObject("$set", new BasicDBObject("sendStatus", Const.STATUS_OK)));
				return new ReMsg(ReCode.OK);
			}
		}
		opsv.increment(RK.smsIp(ip), -1);
		opsv.increment(RK.smsPhone(phone), -1);
		return new ReMsg(ReCode.SMS_SEND_ERR);

	}

	public boolean validCode(Long phone, String code) throws JsonParseException, JsonMappingException, IOException {
		List<DBObject> codes = super.getC(DocName.SMS_LOG)
				.find(new BasicDBObject("phoneNo", phone).append("code", code)).sort(new BasicDBObject("_id", -1))
				.limit(1).toArray();
		if (codes.size() == 1) {
			DBObject dbo = codes.get(0);
			Integer status = DboUtil.getInt(dbo, "status");
			Long updateTime = DboUtil.getLong(dbo, "updateTime");
			if (status == Const.STATUS_DEF && System.currentTimeMillis() - updateTime < 300 * 1000) {
				// return super.update(DocName.SMS_LOG,
				// new BasicDBObject("_id", DboUtil.getLong(dbo, "_id")),
				// new BasicDBObject("status", Const.STATUS_OK));
				super.getC(DocName.SMS_LOG).update(new BasicDBObject("_id", DboUtil.getLong(dbo, "_id")),
						new BasicDBObject("$set", new BasicDBObject("status", Const.STATUS_OK).append("updateTime",
								System.currentTimeMillis())));
				return true;
			}
		}
		return false;
	}

	public static String random(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("size must > 0");
		}
		char[] result = new char[size];
		Random random = ThreadLocalRandom.current();
		for (int i = 0; i < size; i++) {
			int t = random.nextInt(10);
			// System.out.println(t);
			result[i] = (char) (t + 48);
		}
		return new String(result);
	}

	/* 单发接口 */
	public String singleSend(String dst, String msg) throws UnsupportedEncodingException {
		String sUrl = null;
		msg = URLEncoder.encode(msg, "UTF-8");
		sUrl = "http://send.18sms.com/msg/HttpBatchSendSM?account=" + comName + "&pswd=" + comPwd + "&mobile=" + dst
				+ "&msg=" + msg;
		return getUrl(sUrl);
	}

	/* 通用调用接口 */
	public String getUrl(String urlString) {
		// System.out.println(random(4));
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + ",");
				// System.out.println(reader.readLine());
			}
			reader.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}
	
	/** 校验手机号格式 */
	public boolean isChinaPhoneLegal(String phone) throws PatternSyntaxException {
		if ("".equals(phone))
			return false;
		String regExp = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(14[5,7]))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(phone);
		return m.matches();
	}
		
}
