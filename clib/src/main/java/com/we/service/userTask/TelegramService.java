package com.we.service.userTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.we.common.Constant.Const;
import com.we.common.Constant.ReCode;
import com.we.common.Constant.ThirdIdType;
import com.we.common.crypto.MDUtil;
import com.we.common.http.HttpsClientUtil;
import com.we.common.mongo.DboUtil;
import com.we.common.redis.RK;
import com.we.common.utils.JSONUtil;
import com.we.core.web.ReMsg;
import com.we.models.ThirdUser;
import com.we.models.ThirdUserInfo;
import com.we.models.userTask.Task;
import com.we.service.BaseService;
import com.we.service.UserService;
import com.we.service.ThirdUserService;
import com.we.service.userTask.model.Message;
import com.we.service.userTask.model.MessageUpdate;
import com.we.service.userTask.model.User;

@Service
public class TelegramService extends BaseService {

	@Autowired
	ThirdUserService thirdUserService;

	@Autowired
	TaskService taskService;

	@Autowired
	UserTaskService userTaskService;

	@Autowired
	UserService userService;

	static final Logger log = LoggerFactory.getLogger(TelegramService.class);

	// 机器人token
	public static final String BOT = "bot";
	public static final String BOT_NAME = "Candy_Club_bot";
	public static final String BOT_TOKEN = "695254223:AAEV55Li2xE0NKoo8Pb-Di2LJW1oWVHoCk0";

	// 开始的start指令
	public static final String START = "start";
	public static final String JOINCHAT = "joinchat/";
	public static final String DOMAIN = "https://t.me/";
	public static final String START_URL = DOMAIN + BOT_NAME + "?" + START + "=";

	// 发送消息地址
	public static final String SENDMESSAGE = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
	// 发送消息参数 用户id 文本内容
	public static final String CHAT_ID = "chat_id";
	public static final String TEXT = "text";

	// 网址打开方式
	public static final String URL_SELF = "_self";
	public static final String URL_BLANK = "_blank";

	// candy.club task界面
	public static final String TASK_URL = "https://candy.club/task";

	/** 点击做任务 */
	public ReMsg doTelegramTask(Long taskId, Long uid) {
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String md5Key = getMD5Key(uid, taskId);
		opsv.set(RK.telegramKey(md5Key), uid + "-" + taskId, 1, TimeUnit.DAYS);
		Map<String, Object> res = new HashMap<String, Object>();
		// 类型 telegram
		res.put("type", Task.TastTemplate.JOIN_TELEGRAM.getCode());
		// 要跳转的url
		res.put("url", START_URL + md5Key);
		// 打开方式
		res.put("urlType", URL_BLANK);
		return new ReMsg(ReCode.OK, res);
	}

	/** 根据用户id和任务id生成唯一的串 */
	private String getMD5Key(long uid, Long taskId) {
		return MDUtil.SHA.digest2HEX(uid + "-" + taskId);
	}

	// /** 获取发送地址URL */
	// public String getSendMessageUrl(long chat_id, String text) {
	// return SENDMESSAGE + "?chat_id=" + chat_id + "&text=" + text;
	// }

	/**
	 * 调用telegram的接口发送信息
	 * 
	 * @throws IOException
	 */
	String sendMessage(Integer userTelegarmId, String text) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("chat_id", "" + userTelegarmId);
		params.put("text", text);
		return HttpsClientUtil.post(SENDMESSAGE, params, null);

		// System.setProperty("sun.net.client.defaultConnectTimeout", "20000");
		// // 连接超时：20秒
		// System.setProperty("sun.net.client.defaultReadTimeout", "20000"); //
		// 读取超时：20秒
		// StringBuffer buffer = new
		// StringBuffer(getSendMessageUrl(userTelegarmId, text));
		// try {
		// URL url = new URL(buffer.toString());
		// HttpURLConnection connection = (HttpURLConnection)
		// url.openConnection();
		// connection.setRequestMethod("POST");
		// connection.setRequestProperty("Connection", "Keep-Alive");
		// BufferedReader reader = new BufferedReader(new
		// InputStreamReader(url.openStream()));
		// reader.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public static void main(String[] args) {
		TelegramService ts = new TelegramService();
		try {
			ts.sendMessage(575009688, "Join Telegram Group:  https://t.me/joinchat/IkXzmA-hummZpGuHB1lM5A");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * webhook回调
	 * 
	 * @throws IOException
	 */
	public boolean callBack(String json) {
		log.error("telegram webhook json : " + json);
		MessageUpdate mu = JSONUtil.jsonToBean(json, MessageUpdate.class);
		mu.getUpdate_id();
		Message msg = mu.getMessage();

		String text = msg.getText();
		if (StringUtils.isNotBlank(text) && text.startsWith("/" + START)) {
			try {
				String key = text.substring(7);
				ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
				String object = opsv.get(RK.telegramKey(key));
				if (StringUtils.isNotBlank(object)) {
					super.getRedisTemplate().delete(RK.telegramKey(key));

					String[] split = object.split("-");// userId,taskId
					User from = msg.getFrom();
					if (from != null) {
						Long uid = Long.valueOf(split[0]);
						DBObject user = userService.findById(uid);
						if (user != null) {
							DBObject dbotu = thirdUserService.findById(uid);
							ThirdUserInfo tui = new ThirdUserInfo(from);
							ThirdUser tu = null;
							if (dbotu != null) {
								tu = DboUtil.toBean(dbotu, ThirdUser.class);
							} else {
								tu = new ThirdUser(uid);
							}
							tu.put(ThirdIdType.TELEGRAM.getType(), tui);
							super.getMongoTemplate().save(tu);
							DBObject task = taskService.findById(Long.parseLong(split[1]));
							// 任务不为空
							if (task != null) {
								String data = DboUtil.getString(task, "data");
								String msgText = "Join Telegram Group:  " + data;
								// 发送加入群组消息
								try {
									sendMessage(from.getId(), msgText);
								} catch (IOException e) {
									log.error("SendMsg Err:", e);
									return false;
								}
								return true;
							}
						}
					}

				}
			} catch (Exception e) {
				log.error("start err:", e);
			}
		} else if (msg.getNew_chat_members() != null && !msg.getNew_chat_members().isEmpty()) {// 是加入群组消息
			try {
				String group = msg.getChat().getTitle();
				if (group != null) {
					Task t = taskService.getTaskByUniqueId(Task.TastTemplate.JOIN_TELEGRAM.getCode(), group);
					if (t != null && t.getStatus() == Const.STATUS_OK) {
						for (User u : msg.getNew_chat_members()) {
							Integer tuid = u.getId();
							DBObject dbo = thirdUserService.findByThirdId(ThirdIdType.TELEGRAM.getType(),
									tuid.toString());
							if (dbo != null) {
								Long uid = DboUtil.getLong(dbo, "_id");
								DBObject userTask = userTaskService.findByUidAndtaskId(uid, t.get_id());
								if (userTask == null) {
									ReCode rc = userTaskService.doJoinTelegarm(t, uid);
									if (ReCode.OK.reCode() == rc.reCode()) {
										try {
											sendMessage(tuid, "미션 완료하였습니다! 캔디클럽 미션 페이지로 이동하여 리워드를 수령하시기 바랍니다!" + TASK_URL);// 任务做完啦～～～～快回去领奖励吧
											return true;
										} catch (IOException e) {
											log.error("SendMsg Err:", e);
											return false;
										}
									}
								}
							}
						}
					}
				}
				// 拿到群聊新用户的信息
			} catch (Exception e) {
				log.error("new chat member err:", e);
			}

		} else {
			log.error(json);
			return true;
		}
		return false;

	}

}
