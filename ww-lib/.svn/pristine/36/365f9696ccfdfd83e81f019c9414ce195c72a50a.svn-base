package com.zb.core.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.APNTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.zb.common.utils.JSONUtil;

public class GeTuiPush {

	static final Logger log = LoggerFactory.getLogger(GeTuiPush.class);
	private static String appId = "E8eYlzYMnj7mu2MFSvH9o";
	private static String appKey = "RscBpSOBtU5b2CVRVA34DA";
	private static String masterSecret = "uM5DTdNgQm5bLyFyIEWxh7";

	static String host = "http://sdk.open.api.igexin.com/apiex.htm";

	public static String pushAndroidSingle(String cliendId, int network,
			int time, String title, String text, String json) {
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		NotificationTemplate template = notificationTemplate(title, text, json);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		if (time > 0) {
			message.setOfflineExpireTime(time * 1000);
		}
		message.setData(template);
		if (network == 1) {
			message.setPushNetWorkType(network);
		}
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(cliendId);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		if (ret != null) {
			System.out.println(ret.getResponse().toString());
		} else {
			System.out.println("服务器响应异常");
		}
		return ret.getResponse().toString();

	}

	public static String pushAllToAndroid(String title, String body, String json) {
		return pushAllToAndroid(title, body, json, 100, 0, 0, null, null);
	}

	public static String pushAllToAndroid(String title, String body,
			String json, int speed, int network, int time,
			List<String> provinceList, List<String> tagList) {
		IGtPush push = new IGtPush(host, appKey, masterSecret);

		NotificationTemplate template = notificationTemplate(title, body, json);
		AppMessage message = new AppMessage();
		if (speed > 0) {
			message.setSpeed(speed);
		}
		message.setData(template);
		message.setOffline(true);
		if (time > 0) {
			message.setOfflineExpireTime(time * 1000);
		}
		if (network == 1) {
			message.setPushNetWorkType(network);
		}
		// 推送给App的目标用户需要满足的条件
		AppConditions cdt = new AppConditions();
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(appId);
		message.setAppIdList(appIdList);
		// 手机类型
		List<String> phoneTypeList = new ArrayList<String>();
		phoneTypeList.add("ANDROID");// IOS
		cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
		if (provinceList != null && provinceList.size() > 0) {
			cdt.addCondition(AppConditions.REGION, provinceList);
		}
		if (tagList != null && tagList.size() > 0) {
			cdt.addCondition(AppConditions.TAG, tagList);
		}
		message.setConditions(cdt);

		IPushResult ret = push.pushMessageToApp(message, "任务别名_"+title);
		System.out.println(ret.getResponse().toString());
		return ret.getResponse().toString();
	}

	// public static LinkTemplate linkTemplate(String title, String text) {
	// LinkTemplate template = new LinkTemplate();
	// // 设置APPID与APPKEY
	// template.setAppId(appId);
	// template.setAppkey(appKey);
	// // 设置通知栏标题与内容
	// template.setTitle(title);
	// template.setText(text);
	// // 配置通知栏图标
	// template.setLogo("icon.png");
	// // 配置通知栏网络图标，填写图标URL地址
	// template.setLogoUrl("");
	// // 设置通知是否响铃，震动，或者可清除
	// template.setIsRing(true);
	// template.setIsVibrate(true);
	// template.setIsClearable(true);
	// // 设置打开的网址地址
	// template.setUrl("http://www.baidu.com");
	// return template;
	// }

	public static NotificationTemplate notificationTemplate(String title,
			String text, String json) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 设置通知栏标题与内容
		template.setTitle(title);
		template.setText(text);
		// 配置通知栏图标
		template.setLogo("icon.png");
		// 配置通知栏网络图标
		template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		if (StringUtils.isNotBlank(json)) {
			// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
			template.setTransmissionType(2);
			template.setTransmissionContent(json);
		}
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		return template;
	}

	public static final int NET_ALL = 0;
	public static final int NET_WIFI = 0;

	public static String pushIosSingle(String cid, String title, String body,
			Map<String, String> cdata) {
		if (cid != null) {
			IGtPush push = new IGtPush(host, appKey, masterSecret);
			APNTemplate t = new APNTemplate();
			APNPayload apnpayload = new APNPayload();
			if (cdata != null && cdata.size() > 0) {
				for (String key : cdata.keySet()) {
					apnpayload.addCustomMsg(key, cdata.get(key));
				}
			}
			apnpayload.setSound("");
			// apn高级推送
			APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
			// //通知文本消息标题
			alertMsg.setTitle(title);
			// 通知文本消息字符串
			alertMsg.setBody(body);
			// 对于标题指定执行按钮所使用的Localizable.strings,仅支持IOS8.2以上版本
			// alertMsg.setTitleLocKey("ccccc");
			// 指定执行按钮所使用的Localizable.strings
			// alertMsg.setActionLocKey("ddddd");
			apnpayload.setAlertMsg(alertMsg);
			t.setAPNInfo(apnpayload);
			SingleMessage sm = new SingleMessage();
			sm.setData(t);
			IPushResult ret = push.pushAPNMessageToSingle(appId, cid, sm);
			System.out.println(ret.getResponse());
			return ret.getResponse().toString();
		}
		return null;
	}

	public static String pushAllToIos(String title, String body, String json,
			int speed, int network, int time, List<String> provinceList,
			List<String> tagList) {
		IGtPush push = new IGtPush(host, appKey, masterSecret);

		TransmissionTemplate template = getTemplate(title, body, json);
		AppMessage message = new AppMessage();
		if (speed > 0) {
			message.setSpeed(speed);
		}
		message.setData(template);
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		if (time > 0) {
			message.setOfflineExpireTime(time * 1000);
		}
		if (network == 1) {
			message.setPushNetWorkType(network);
		}
		// 推送给App的目标用户需要满足的条件
		AppConditions cdt = new AppConditions();
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(appId);
		message.setAppIdList(appIdList);
		List<String> phoneTypeList = new ArrayList<String>();
		cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
		if (provinceList != null && provinceList.size() > 0) {
			cdt.addCondition(AppConditions.REGION, provinceList);
		}
		if (tagList != null && tagList.size() > 0) {
			cdt.addCondition(AppConditions.TAG, tagList);
		}
		message.setConditions(cdt);

		IPushResult ret = push.pushMessageToApp(message, "任务别名_"+title);
		System.out.println(ret.getResponse().toString());
		return ret.getResponse().toString();
	}

	public static String pushAllToIos(String title, String body, String json) {
		return pushAllToIos(title, body, json, 100, 0, 0, null, null);
	}

	public static TransmissionTemplate getTemplate(String title, String body,
			String json) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		if (StringUtils.isNotBlank(json)) {
			template.setTransmissionContent(json);
			template.setTransmissionType(2);
		}
		APNPayload payload = new APNPayload();

		// payload.setBadge(1);
		payload.setContentAvailable(1);
		// payload.setSound("default");
		// payload.setCategory("$由客户端定义");

		// 简单模式APNPayload.SimpleMsg
		if (StringUtils.isNotBlank(body)) {
			payload.setAlertMsg(getDictionaryAlertMsg(title, body));
		} else {
			payload.setAlertMsg(new APNPayload.SimpleAlertMsg(title));
		}
		// 字典模式使用下者
		//
		template.setAPNInfo(payload);
		return template;
	}

	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(
			String title, String content) {
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		alertMsg.setBody("body");
		alertMsg.setActionLocKey("ActionLockey");
		alertMsg.setLocKey(content);
		alertMsg.addLocArg("loc-args");
		alertMsg.setLaunchImage("launch-image");
		// IOS8.2以上版本支持
		// alertMsg.setTitle("Title");
		alertMsg.setTitle(title);
		alertMsg.setTitleLocKey(title);
		alertMsg.addTitleLocArg("TitleLocArg");
		return alertMsg;
	}

	public static void setTag(List<String> tagList, String cid) {
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		IQueryResult ret = push.setClientTag(appId, cid, tagList);
		// System.out.println(ret.getResponse().toString());
	}

	public static void setBadgeForCid(String cids, String badge) {
		List<String> cidList = new ArrayList<String>();
		// 用户应用icon上显示的数字
		if (StringUtils.isNotBlank(cids)) {
			String cidarr[] = cids.split(",");
			for (String cid : cidarr) {
				cidList.add(cid);
			}
			IGtPush push = new IGtPush(appKey, masterSecret);
			// "+1"即在原有badge上加1；具体详情使用请参考该接口描述
			badge = "+1";
			// "-1"即在原有badge上减1；具体详情使用请参考该接口描述
			// badge = "-1";
			// 直接设置badge数值，会覆盖原有数值；具体详情使用请参考该接口描述
			// badge = "1";
			IQueryResult res = push.setBadgeForCID(badge, appId, cidList);
			System.out.println(res.getResponse());

		}
	}

	public static String pushAll(String title, String body, String json) {
		String s = "ios:" + pushAllToIos(title, body, json);
		s = s + ",android:" + pushAllToAndroid(title, body, json);
		return s;
	}

	public static void main(String[] args) {
		String json = "{\"op\":\"url\",\"opId\":\"http://www.zhuangdianbi.com\"}";
		//
		// String andCid = "a7a2b70b9c82413e938f001ace869489";
		// GeTuiPush.pushAndroidSingle(andCid, 0, 3600, "test", "testcontent",
		// null);
		//
		// String iosCid = "27fe82d5b60afd399dc9ace670c5f07f";
		//
		// Map<String, String> cdata = new HashMap<String, String>();
		// cdata.put("op", "op");
		// cdata.put("opId", "opId");
		Map<String, String> cdata = new HashMap<String, String>();
		// // url
		// cdata.put("op", "url");
		// cdata.put("opId", "http://www.zhuangdianbi.com");

		// // Tool
		// cdata.put("op", "tool");
		// cdata.put("opId", "358");

		// // eif
		// cdata.put("op", "eif");
		// cdata.put("opId", "45");

		// // ToolTag
		// cdata.put("op", "toolTag");
		// cdata.put("opId", "new");

		// // topic
		// cdata.put("op", "topic");
		// cdata.put("opId", "6");

		// // article
		// cdata.put("op", "article");
		// cdata.put("opId", "9890");

		// // userSpace
		// cdata.put("op", "userSpace");
		// cdata.put("opId", "100005042");

		// // chat
		// cdata.put("op", "chat");
		// cdata.put("opId", "100005042");

		// // add_friend
		// cdata.put("op", "add_friend");
		// cdata.put("opId", "100005042");

		// third_login
		cdata.put("op", "third_login");
		cdata.put("opId", "3c1645607ab8d98c1288aac9d3aa4227");

		String devToken = "4c26ed481115534c9011d8e5c2c909d0240a8a50ae5290902372f1e88d985108";
		String cliendId = "b0eea769fdea7766c8394530be3e5ded";
//		GeTuiPush.pushAllToAndroid("test", "testcontent",
//				JSONUtil.beanToJson(cdata));
		GeTuiPush.pushIosSingle(devToken, "test", "testContent", cdata);

		// String title = "all";
		// String body = "all1";
		// GeTuiPush.pushAll(title, body, json);

		//
		// GeTuiPush.pushAllToAndroid(title, body, json);
		// GeTuiPush.pushAllToIos(title, body, json);
		// GeTuiPush.pushMessageToApp();
		// String json = "{\"op\":\"testop\",\"opId\":\"1\"}";
		// GeTuiPush.pushMessageToApp(NET_ALL, 3600, "title", "text", null, 100,
		// json);
	}
}
