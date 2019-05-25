package com.zb.service.third;

import java.io.*;
import java.net.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.common.Constant.ReCode;
import com.zb.common.crypto.BlowFishUtil;
import com.zb.common.crypto.DES3Util;
import com.zb.common.crypto.MDUtil;
import com.zb.common.http.HttpsClientUtil;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.core.web.ReMsg;
import com.zb.service.BaseService;
import com.zb.service.EnvService;

@Service
public class PingAnGameService extends BaseService {

	public PingAnGameService() {
	}

	private final static String URL_TEST = "http://164test9-wap.stg2.24cp.com/?act=landing&st=goto_page&goUrl=";
	private final static String REAL_URL = "http://gameapp.wanlitong.com/?act=landing&st=goto_page&goUrl=";
	private final static String TRACK_U = "&track_u=bjzbzdb";

	private final static String PASSPORT_TEST = "https://passport-stg2.wanlitong.com/pass-info/oauth2/3rdPartAuth2.do?";
	private final static String PASSPORT_REAL = "https://passport.wanlitong.com/pass-info/oauth2/3rdPartAuth2.do?";

	private final static String USER_CENTER_TEST = "http://164test9-wap.stg2.24cp.com/?act=newucenter&st=main&track_u=bjzbzdb";
	private final static String USER_CENTER = "http://gameapp.wanlitong.com/?act=newucenter&st=main&track_u=bjzbzdb";

	private final static String ID_TEST = "EX_000064";
	private final static String SECRET_TEST = "E5DA638F913943FDA5E3C902C6943BA6";
	private final static String SIGN_KEY = "d1bd3f7d099b49e7a77ea8e32a69c2d5";
	private final static String DES_KEY = "F6011F0398F3461BA53D3A9B";
	private final static String MEDIA_SOURCE = "pawifi";

	private String client_id = "EX_000074";
	private String client_secret = "35EC4197D2DC4E8681DAB49444F47E60";
	private String signKey = "fc65a7c4523e402d9d89d6479a9c69d2";
	private String desKey = "5FF37BA2B9C34B5E8CDAF46D";
	private String media_source = "bjzbzdb";

	Boolean isDebug = true;
	boolean init = false;

	private void init() {
		if (!init) {
			init = true;
			isDebug = envService.getBool(PINGAN_GAME_STATUS);
			if (!isDebug) {
				client_id = ID_TEST;
				client_secret = SECRET_TEST;
				signKey = SIGN_KEY;
				desKey = DES_KEY;
				media_source = MEDIA_SOURCE;
			}
		}
	}

	@Autowired
	EnvService envService;
	private static final String PINGAN_GAME_STATUS = "pingan.game.status";

	public String getUserCenter(HttpServletRequest req)
			throws UnsupportedEncodingException, Exception {

		String cururl = null;
		if (isDebug) {
			cururl = USER_CENTER;
		} else {
			cururl = USER_CENTER_TEST;
		}
		return getUrl(req, cururl);
	}

	public String getUrl(HttpServletRequest req, String gameUrl)
			throws UnsupportedEncodingException, Exception {
		init();
		System.out.println("isDebug ================ >" + isDebug);
		String cururl = null;
		if (isDebug) {
			cururl = REAL_URL + URLEncoder.encode(gameUrl, "utf8") + "&sid="
					+ sendUserInfo(req) + TRACK_U;
		} else {
			cururl = URL_TEST + URLEncoder.encode(gameUrl, "utf8") + "&sid="
					+ sendUserInfo(req) + TRACK_U;
		}
		return cururl;
	}

	public String sendUserInfo(HttpServletRequest req) throws Exception {
		String json = singleSend(req);
		Map<String, Object> m = JSONUtil.jsonToMap(json);
		if ("0000".equals(m.get("errorCode"))) {
			return (String) m.get("session_id");
		}
		return null;
	}

	/* 单发接口 */
	public String singleSend(HttpServletRequest req) throws Exception {
		StringBuilder sUrl = null;
		if (isDebug) {
			sUrl = new StringBuilder(PASSPORT_REAL);
		} else {
			sUrl = new StringBuilder(PASSPORT_TEST);
		}
		sUrl.append("client_id=").append(client_id).append("&client_secret=")
				.append(client_secret).append("&media_source=")
				.append(media_source).append("&response_type=code");
		String openId = "" + super.getUid();

		String phone = "";
		if (super.getUser("phone") != null) {
			phone = (String) super.getUser("phone");
		}
		String username = (String) super.getUser("nickname");
		String sex = "M";
		if (super.getUser("sex") != null) {
			sex = (int) super.getUser("sex") == 2 ? "F" : "M";
		}

		String timestamp = DateUtil.dateFormatLong(new Date());
		// String algorithm = "";
		StringBuilder userInfo = new StringBuilder();
		userInfo.append(phone).append(";").append(username).append(";")
				.append(sex).append(";;;");

		System.out.println("userinfo:" + userInfo.toString());

		String ui3des = DES3Util.Encrypt3DES(userInfo.toString(), desKey);
		System.out.println("3des:" + ui3des);
		String sign = MDUtil.SHA.digest2HEX(client_id + openId + timestamp
				+ ui3des + signKey);
		sUrl.append("&sign=").append(sign).append("&openId=").append(openId)
				.append("&timestamp=")
				.append(URLEncoder.encode(timestamp, "utf8"))
				.append("&userInfo=").append(URLEncoder.encode(ui3des, "utf8"));
		System.out.println(sUrl);
		return HttpsClientUtil.get(sUrl.toString(), null);
	}

//	public static void main(String[] args) throws Exception {
//		String str; // 存放调用返回的结果,判断成功与否应该分析这个字符串
//		PingAnGameService sms = new PingAnGameService(); // 这里修改成你自己的用户名和密码
//		// str = sms.getUrl(null,
//		// "http://gameapp.wanlitong.com/?act=game_poker&track_u=bjzbzdb");
//
//		str = sms
//				.getUrl(null,
//						"http://164test9-wap.stg2.24cp.com/?act=game_xiyou&track_u=bjzbzdb");
//		System.out.println(str);
//	}
	
	public static void main(String [] args){
		//https://192.168.1.155/api/dynamicKey?data=df89589ca74da5ae54d49d8525618f8f&reqKey=18937176182&timestamp=1492397591018&via=2
//		BaseService bs =new BaseService();
//		ReMsg rs = bs.getKey("18937176182", 2.60, 2, "25b38d4843f343560b46c3cb40ece8a1", 1492405469405L);
//		if(rs.getCode()==ReCode.OK.reCode()){
//			System.out.println(rs.getData().toString());
//		}else{
//			System.out.println(rs.getCode());
//		}
		try {
			String decode = BlowFishUtil.decryptHex("c1s112312az01gc", "ihl9rNLeMGh+PYY0/317OaItIYGAPTeRvxbzvYczYrU=", "a1da9lcz");
			System.out.println(decode);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
