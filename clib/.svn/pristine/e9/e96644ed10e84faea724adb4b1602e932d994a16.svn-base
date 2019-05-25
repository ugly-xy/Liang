package com.we.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.Constant.Const;
import com.we.common.Constant.ReCode;
import com.we.common.crypto.MDUtil;
import com.we.common.mongo.DboUtil;
import com.we.common.redis.RK;
import com.we.common.utils.RegexUtil;
import com.we.core.web.ReMsg;
import com.we.models.DocName;
import com.we.models.SmsLog;

import net.sf.json.JSONObject;

@Service
public class SmsService extends BaseService {

	private String comName; // 企业用户登陆名
	private String comPwd; // 企业用户登陆密码

	// 默认国内短信平台
	public SmsService() {
		this("l7964f", "4n93Xry0");
	}

	// 美联软通短信平台
	private static final int SMS_TYPE_MLRT = 1;
	private static final String MLRT_APIKEY = "c1e4573edf83c623c15720e986d43428";
	private static final String MLRT_USERNAME = "13366666474";
	private static final String MLRT_PWD = "sz1992229";

	// 天一鸿国际短信平台
	private static final int SMS_TYPE_TYHGJ = 2;
	private static final String TYHGJ_USERNAME = "weshow";
	private static final String TYHGJ_PWD = "sz1992229";
//	private static final String TYHGJ_PWD = "LINGpaikeji2018";

	private static int SMS_TYPE = SMS_TYPE_MLRT;// 默认采用美联软通

	public SmsService(String name, String pwd) {
		this.comName = name;
		this.comPwd = pwd;
	}

	/** 发送验证码 */
	public ReMsg sendAuthCode(HttpServletRequest req, String encode, String phone, long timestamp, String tokenCode)
			throws UnsupportedEncodingException {
		if (phone != null) {
			String ip = super.getIp(req);
			ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
//			if (!RegexUtil.isPhone(encode, phone)) {
//				return new ReMsg(ReCode.PHONE_NUMBER_FORMAT_ERROR);
//			}

			if (System.currentTimeMillis() > timestamp) {
				return new ReMsg(ReCode.SMS_SEND_OUTTIME_ERR);
			}
			String key = super.getKeyByCache(phone);
			if (!MDUtil.MD5.digest2HEX(key + timestamp + phone).equals(tokenCode)) {
				return new ReMsg(ReCode.SMS_DRAW_ERR);
			}
			// System.out.println(getRedisTemplate().getExpire(RK.smsPhone(phone),
			// TimeUnit.SECONDS));
			if (24 * 3600 - getRedisTemplate().getExpire(RK.smsPhone(phone), TimeUnit.SECONDS) < 60) {
				return new ReMsg(ReCode.SMS_PHONE_TOO_OFTEN_60);
			}
			Long count = opsv.increment(RK.smsPhone(phone), 1);

			if (count > 5) {
				return new ReMsg(ReCode.SMS_PHONE_TOO_OFTEN);
			}
			// count = opsv.increment(RK.smsIp(ip), 1);
			// if (count > 15) {// ip 次数限制
			// return new ReMsg(ReCode.SMS_IP_TOO_OFTEN);
			// }
			String authCode = random(4);
			long id = super.getNextId(DocName.SMS_LOG);
			SmsLog sl = new SmsLog(id, phone, authCode, ip);
			super.getMongoTemplate().save(sl);
			Boolean success = null;
			String res = null;
			if (RegexUtil.isPhoneChina(phone)) {
				// 电话号码为中国
				res = this.singleSend(phone, "您的验证码为：" + authCode + ",如非本人操作，请忽略！");
				getRedisTemplate().expire(RK.smsPhone(phone), 1, TimeUnit.DAYS);
				if (StringUtils.isNotBlank(res) && res.contains(",")) {
					String[] strs = res.split(",");
					if ("0".equals(strs[1])) {
						success = true;
					}
				}
			} else if (RegexUtil.isPhoneKorea(phone)) {
				// 韩国号码
				// 美联软通
				if (SMS_TYPE == SMS_TYPE_MLRT) {
					res = this.sendSmsKoreaMLRT(phone, "[WE SHOW] 고객님의 인증번호는 [" + authCode + "] 입니다 ");
					getRedisTemplate().expire(RK.smsPhone(phone), 1, TimeUnit.DAYS);
					if (StringUtils.isNotBlank(res)) {
						if (res.contains("success")) {
							// 成功返回示例: success:64668429905641
							success = true;
						} else if (res.contains("insufficient") || res.contains("blocked") || res.contains("address")) {
							// error:Account balance is insufficient 余额不足
							// error:Account is blocked 账户被禁用
							// error:Unauthorized IP address 未被授权ip
							// 出现以上情况 转用天一鸿国际短信 并且设置变量
							SMS_TYPE = SMS_TYPE_TYHGJ;
							res = this.sendSmsKoreaTYHGJ(phone, "[WE SHOW] 고객님의 인증번호는 [" + authCode + "] 입니다 ");
							getRedisTemplate().expire(RK.smsPhone(phone), 1, TimeUnit.DAYS);
							// {"status":0, "array":[[17600107108,91540]],
							// "success":1,
							// "fail":0}
							if (StringUtils.isNotBlank(res) && res.contains(",")) {
								String[] strs = res.split(",");
								if ("0".equals(strs[0])) {
									success = true;
								}
							}
						}
					}
				} else {// 使用天一鸿国际短信
					res = this.sendSmsKoreaTYHGJ(phone, "[WE SHOW] 고객님의 인증번호는 [" + authCode + "] 입니다 ");
					getRedisTemplate().expire(RK.smsPhone(phone), 1, TimeUnit.DAYS);
					// {"status":0, "array":[[17600107108,91540]],
					// "success":1,
					// "fail":0}
					if (StringUtils.isNotBlank(res) && res.contains(",")) {
						String[] strs = res.split(",");
						if ("0".equals(strs[0])) {
							success = true;
						}
					}
				}
			}
			if (success != null && success) {
				getRedisTemplate().expire(RK.smsIp(ip), 1, TimeUnit.DAYS);
				super.getC(DocName.SMS_LOG).update(new BasicDBObject("_id", id),
						new BasicDBObject("$set", new BasicDBObject("sendStatus", Const.STATUS_OK)));
				return new ReMsg(ReCode.OK);
			}
			opsv.increment(RK.smsIp(ip), -1);
			opsv.increment(RK.smsPhone(phone), -1);
		}
		return new ReMsg(ReCode.SMS_SEND_ERR);

	}

	public ReMsg sendAuthCode(HttpServletRequest req, String phone) throws UnsupportedEncodingException {
		if (null == phone) {
			return new ReMsg(ReCode.SMS_DRAW_ERR);
		}
		// if (System.currentTimeMillis() > timestamp) {
		// return new ReMsg(ReCode.SMS_SEND_OUTTIME_ERR);
		// }

		if (24 * 3600 - getRedisTemplate().getExpire(RK.smsPhone(phone), TimeUnit.SECONDS) < 60) {
			return new ReMsg(ReCode.SMS_PHONE_TOO_OFTEN_60);
		}

		String key = super.getKeyByCache("" + phone);
		// if (!MDUtil.MD5.digest2HEX(key + timestamp +
		// phone).equals(tokenCode)) {
		// return new ReMsg(ReCode.SMS_DRAW_ERR);
		// }

		String ip = super.getIp(req);
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();

		Long count = opsv.increment(RK.smsPhone(phone), 1);

		if (count > 5) {
			return new ReMsg(ReCode.SMS_PHONE_TOO_OFTEN);
		}

		count = opsv.increment(RK.smsIp(ip), 1);
		if (count > 15) {// ip 次数限制
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

	public boolean validCode(String phone, String code) {
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
		/*
		 * sUrl = "http://www.139000.com/send/gsend.asp?name=" + comName + "&pwd=" +
		 * comPwd + "&dst=" + dst + "&msg=" + msg;
		 */
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

	/** 美联软通短信平台 */
	public String sendSmsKoreaMLRT(String phone, String content) {
		String temp = "82" + phone;
		// 连接超时及读取超时设置
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); // 连接超时：30秒
		System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时：30秒
		StringBuffer buffer = new StringBuffer();
		String encode = "UTF-8";
		try {
			String contentUrlEncode = URLEncoder.encode(content, encode); // 对短信内容做Urlencode编码操作。注意：如
			// 把发送链接存入buffer中，如连接超时，可能是您服务器不支持域名解析，请将下面连接中的：【m.5c.com.cn】修改为IP：【115.28.23.78】
			buffer.append("http://115.28.23.78/api/send/index.php?username=" + MLRT_USERNAME + "&password_md5="
					+ MDUtil.getMD5(MLRT_PWD) + "&mobile=" + temp + "&apikey=" + MLRT_APIKEY + "&content="
					+ contentUrlEncode + "&encode=" + encode);
			// System.out.println(buffer); // 调试功能，输入完整的请求URL地址
			// 把buffer链接存入新建的URL中
			URL url = new URL(buffer.toString());
			// 打开URL链接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 使用POST方式发送
			connection.setRequestMethod("POST");
			// 使用长链接方式
			connection.setRequestProperty("Connection", "Keep-Alive");
			// 发送短信内容
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			// 获取返回值
			temp = reader.readLine();
			// 输出result内容，查看返回值，成功为success，错误为error，详见该文档起始注释
			System.out.println(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	/** 天一鸿国际短信平台V0V0 */
	public String sendSmsKoreaTYHGJV0(String phone, String content) {
		String temp;
		// 短信加国码
		if (RegexUtil.isPhoneChina(phone)) {// 中国
			temp = "86" + phone;
		} else if (RegexUtil.isPhoneKorea(phone)) {// 韩国 去除首位的0
			temp = "82" + phone.substring(1, phone.length());
		} else {
			temp = phone;
		}
		// 连接超时及读取超时设置
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); // 连接超时：30秒
		System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时：30秒

		// 新建一个StringBuffer链接
		StringBuffer buffer = new StringBuffer();
		String encode = "UTF-8";
		try {

			String contentUrlEncode = URLEncoder.encode(content, encode); // 对短信内容做Urlencode编码操作。注意：如

			// 把发送链接存入buffer中，如连接超时，可能是您服务器不支持域名解析，请将下面连接中的：【m.5c.com.cn】修改为IP：【115.28.23.78】
			buffer.append("http://sms.skylinelabs.cc:20003/sendsms?account=" + TYHGJ_USERNAME + "&password=" + TYHGJ_PWD
					+ "&numbers=" + temp + "&content=" + contentUrlEncode);

			URL url = new URL(buffer.toString());

			// 打开URL链接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// 使用POST方式发送
			connection.setRequestMethod("POST");

			// 使用长链接方式
			connection.setRequestProperty("Connection", "Keep-Alive");

			// 发送短信内容
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

			// 获取返回值
			temp = reader.readLine();

			// 输出result内容，查看返回值，成功为success，错误为error，详见该文档起始注释
			System.out.println(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	/** 天一鸿国际短信平台V2V2 */
	public String sendSmsKoreaTYHGJ(String phone, String content) {
		String temp;
		// 短信加国码
		if (RegexUtil.isPhoneChina(phone)) {// 中国
			temp = "86" + phone;
		} else if (RegexUtil.isPhoneKorea(phone)) {// 韩国 去除首位的0
			temp = "82" + phone.substring(1, phone.length());
		} else {
			temp = phone;
		}
		// 连接超时及读取超时设置
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); // 连接超时：30秒
		System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时：30秒

		// 新建一个StringBuffer链接Ø
		StringBuffer buffer = new StringBuffer();
		String encode = "UTF-8";
		try {

			String contentUrlEncode = URLEncoder.encode(content, encode); // 对短信内容做Urlencode编码操作。注意：如
			String TIMESTAMPMODE =new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String sign =MDUtil.getMD5(TYHGJ_USERNAME+TYHGJ_PWD+TIMESTAMPMODE);

			// 把发送链接存入buffer中，如连接超时，可能是您服务器不支持域名解析，请将下面连接中的：【m.5c.com.cn】修改为IP：【115.28.23.78】
			buffer.append("http://sms.skylinelabs.cc:20003/sendsmsV2?account=" + TYHGJ_USERNAME + "&sign=" + sign+"&timestamp="+TIMESTAMPMODE
					+ "&numbers=" + temp + "&content=" + contentUrlEncode);

			URL url	 = new URL(buffer.toString());

			// 打开URL链接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// 使用POST方式发送
			connection.setRequestMethod("POST");

			// 使用长链接方式
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

			// 发送短信内容
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = null;
			buffer.setLength(0);
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if(buffer!=null&&buffer.length()>0) {
				JSONObject json = JSONObject.fromObject(buffer.toString());
				temp=String.valueOf(json.get("status"))+",";
			}
			System.out.println("---callbackmsg---"+buffer.toString());
			// 输出result内容，查看返回值，成功为success，错误为error，详见该文档起始注释
			System.out.println(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
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

	public static void main(String[] args) {

		// 连接超时及读取超时设置
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); // 连接超时：30秒
		System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时：30秒

		// 新建一个StringBuffer链接
		StringBuffer buffer = new StringBuffer();

		String encode = "UTF-8";

		String username = "weshow"; // 用户名

		String password = "sz1992229"; // 密码

		String mobile = "821022210321"; // 手机号,只发一个号码：13800000001。发多个号码：13800000001,13800000002,...N
										// 。使用半角逗号分隔。
		String content = "验证码集成测试"; // 要发送的短信内容，特别注意：签名必须设置，网页验证码应用需要加添加【图形识别码】。

		try {

			String contentUrlEncode = URLEncoder.encode(content, encode); // 对短信内容做Urlencode编码操作。注意：如

			// 把发送链接存入buffer中，如连接超时，可能是您服务器不支持域名解析，请将下面连接中的：【m.5c.com.cn】修改为IP：【115.28.23.78】
			buffer.append("http://sms.skylinelabs.cc:20003/sendsms?account=" + username + "&password=" + password
					+ "&numbers=" + mobile + "&content=" + contentUrlEncode);

			URL url = new URL(buffer.toString());

			// 打开URL链接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// 使用POST方式发送
			connection.setRequestMethod("POST");

			// 使用长链接方式
			connection.setRequestProperty("Connection", "Keep-Alive");

			// 发送短信内容
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

			// 获取返回值
			String result = reader.readLine();

			// 输出result内容，查看返回值，成功为success，错误为error，详见该文档起始注释
			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
