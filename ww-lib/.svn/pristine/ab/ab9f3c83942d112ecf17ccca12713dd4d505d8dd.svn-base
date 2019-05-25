package com.zb.core.push;

//import java.util.Date;
//import java.util.List;

//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.zb.core.conf.Config;

public class BdPush {

//	static final Logger log = LoggerFactory.getLogger(BdPush.class);
//	static String apiKey = Config.cur().get("baidu.push.apiKey");
//	static String secretKey = Config.cur().get("baidu.push.secretKey");
//	static PushKeyPair pair = new PushKeyPair(apiKey, secretKey);
//	static int deployStatus = Config.cur().getInt("baidu.push.iosDepolyStatus",
//			2);
//
//	public static enum MsgType {
//		THROUGH(0), NOTICE(1);
//		int t;
//
//		private MsgType(int t) {
//			this.t = t;
//		}
//
//		public int type() {
//			return t;
//		}
//	}
//
//	public static enum DevType {
//		NULL(-1), ANDROID(3), IOS(4);
//		int t;
//
//		private DevType(int t) {
//			this.t = t;
//		}
//
//		public int type() {
//			return t;
//		}
//
//		public static DevType of(int i) {
//			if (i == 3) {
//				return ANDROID;
//			} else if (i == 4) {
//				return IOS;
//			}
//			return NULL;
//		}
//	}
//
//	public static PushBatchUniMsgResponse pushBatch(List<String> chs,
//			String tagName, PushMsg msg, String topic, int expires)
//			throws PushClientException, PushServerException {
//		BaiduPushClient pushClient = getBPC();
//
//		String[] channelIds = chs.toArray(new String[chs.size()]);
//		PushBatchUniMsgRequest request = new PushBatchUniMsgRequest()
//				.addChannelIds(channelIds).addMessage(msg.toString());
//		if (expires > 0) {
//			request.addMsgExpires(expires);
//		}
//		if (StringUtils.isNotBlank(topic)) {
//			request.addTopicId(topic);// 设置类别主题
//		}
//		if (msg.getDevType() != null) {
//			request.addDeviceType(msg.getDevType().type());
//		}
//		if (msg.getMsgType() != null) {
//			request.addMessageType(msg.getMsgType().type());
//		}
//		return pushClient.pushBatchUniMsg(request);
//	}
//
//	public static PushMsgToAllResponse pushAll(PushMsg msg, int expires,
//			Date pushTime) throws PushClientException, PushServerException {
//		BaiduPushClient pushClient = getBPC();
//		PushMsgToAllRequest request = new PushMsgToAllRequest().addMessage(msg
//				.toString());
//		if (expires > 0) {
//			request.addMsgExpires(expires);
//		}
//		if (pushTime != null) {
//			if (pushTime.getTime() > System.currentTimeMillis() + 120000) {
//				request.addSendTime(pushTime.getTime() / 1000);
//			} else {
//				request.addSendTime(System.currentTimeMillis() / 1000 + 12000);
//			}
//		}
//		if (msg.getDevType() != null) {
//			request.addDeviceType(msg.getDevType().type());
//			if (msg.getDevType().type() == DevType.IOS.type()) {
//				request.addDepolyStatus(deployStatus);
//			}
//		}
//		if (msg.getMsgType() != null) {
//			request.addMessageType(msg.getMsgType().type());
//		}
//
//		return pushClient.pushMsgToAll(request);
//	}
//
//	public static PushMsgToSingleDeviceResponse pushSingle(String chid,
//			PushMsg msg, int expires) throws PushClientException,
//			PushServerException {
//		BaiduPushClient pushClient = getBPC();
//		PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
//				.addChannelId(chid).addMessage(msg.toString());
//		if (expires > 0) {
//			request.addMsgExpires(expires);
//		}
//		if (msg.getDevType() != null) {
//			request.addDeviceType(msg.getDevType().type());
//		}
//		if (msg.getMsgType() != null) {
//			request.addMessageType(msg.getMsgType().type());
//			if (msg.getDevType().type() == DevType.IOS.type()) {
//				request.addDeployStatus(deployStatus);
//			}
//		}
//
//		return pushClient.pushMsgToSingleDevice(request);
//	}
//
//	public static PushMsgToSmartTagResponse pushSmartTag(PushMsg msg,
//			int expires, BdSelector bds, Date pushTime)
//			throws PushClientException, PushServerException {
//		BaiduPushClient pushClient = getBPC();
//
//		PushMsgToSmartTagRequest request = new PushMsgToSmartTagRequest()
//				.addSelector(bds.toString()).addMessage(msg.toString());
//		if (expires > 0) {
//			request.addMsgExpires(expires);
//		}
//
//		if (pushTime != null) {
//			if (pushTime.getTime() > System.currentTimeMillis() + 120000) {
//				request.addSendTime(pushTime.getTime() / 1000);
//			} else {
//				request.addSendTime(System.currentTimeMillis() / 1000 + 12000);
//			}
//		}
//		if (msg.getDevType() != null) {
//			request.addDeviceType(msg.getDevType().type());
//		}
//		if (msg.getMsgType() != null) {
//			request.addMessageType(msg.getMsgType().type());
//			if (msg.getDevType().type() == DevType.IOS.type()) {
//				request.addDeployStatus(deployStatus);
//			}
//		}
//		return pushClient.pushMsgToSmartTag(request);
//
//	}
//
//	public static PushMsgToTagResponse pushTag(String tagName, PushMsg msg,
//			String topic, int expires, Date pushTime)
//			throws PushClientException, PushServerException {
//
//		BaiduPushClient pushClient = getBPC();
//
//		PushMsgToTagRequest request = new PushMsgToTagRequest().addTagName(
//				tagName).addMessage(msg.toString());
//		if (expires > 0) {
//			request.addMsgExpires(expires);
//		}
//		if (pushTime != null) {
//			if (pushTime.getTime() > System.currentTimeMillis() + 120000) {
//				request.addSendTime(pushTime.getTime() / 1000);
//			} else {
//				request.addSendTime(System.currentTimeMillis() / 1000 + 12000);
//			}
//		}
//		if (msg.getDevType() != null) {
//			request.addDeviceType(msg.getDevType().type());
//		}
//		if (msg.getMsgType() != null) {
//			request.addMessageType(msg.getMsgType().type());
//			if (msg.getDevType().type() == DevType.IOS.type()) {
//				request.addDeployStatus(deployStatus);
//			}
//		}
//
//		return pushClient.pushMsgToTag(request);
//	}
//
//	public static boolean createTag(String tag, DevType devType)
//			throws PushClientException, PushServerException {
//		BaiduPushClient pushClient = getBPC();
//
//		CreateTagRequest request = new CreateTagRequest().addTagName(tag);
//		if (devType.type() != DevType.NULL.type()) {
//			request.addDeviceType(devType.type());
//		}
//		CreateTagResponse ctr = pushClient.createTag(request);
//		return ctr.getResult() == 0;
//	}
//
//	public static boolean delTag(String tag, DevType devType)
//			throws PushClientException, PushServerException {
//		BaiduPushClient pushClient = getBPC();
//
//		DeleteTagRequest request = new DeleteTagRequest().addTagName(tag);
//		if (devType.type() != DevType.NULL.type()) {
//			request.addDeviceType(devType.type());
//		}
//		return pushClient.deleteTag(request).getResult() == 0;
//	}
//
//	public static List<DeviceInfo> addDevsToTag(List<String> chs,
//			String tagName, DevType devType) throws PushClientException,
//			PushServerException {
//		BaiduPushClient pushClient = getBPC();
//		AddDevicesToTagRequest request = new AddDevicesToTagRequest()
//				.addTagName(tagName).addChannelIds(
//						chs.toArray(new String[chs.size()]));
//		if (devType.type() != DevType.NULL.type()) {
//			request.addDeviceType(devType.type());
//		}
//		return pushClient.addDevicesToTag(request).getDevicesInfoAfterAdded();
//	}
//
//	public static List<DeviceInfo> delDevsFromTag(List<String> chs,
//			String tagName, DevType devType) throws PushClientException,
//			PushServerException {
//		BaiduPushClient pushClient = getBPC();
//		DeleteDevicesFromTagRequest request = new DeleteDevicesFromTagRequest()
//				.addTagName(tagName).addChannelIds(
//						chs.toArray(new String[chs.size()]));
//		if (devType.type() != DevType.NULL.type()) {
//			request.addDeviceType(devType.type());
//		}
//		return pushClient.deleteDevicesFromTag(request)
//				.getDevicesInfoAfterDel();
//	}
//
//	private static BaiduPushClient getBPC() {
//		BaiduPushClient pushClient = new BaiduPushClient(pair,
//				BaiduPushConstants.CHANNEL_REST_URL);
//		pushClient.setChannelLogHandler(new YunLogHandler() {
//			@Override
//			public void onHandle(YunLogEvent event) {
//				log.info(event.getMessage());
//			}
//		});
//		return pushClient;
//	}

}
