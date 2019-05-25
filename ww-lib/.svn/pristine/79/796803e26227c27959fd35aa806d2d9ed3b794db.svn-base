package com.zb.service.room;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.JSONUtil;
import com.zb.core.redis.RedisChannel;
import com.zb.core.web.ReMsg;
import com.zb.service.BaseService;
import com.zb.service.GoodsService;
import com.zb.service.UserService;
import com.zb.service.dirtywords.DirtyWords;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.Msg;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;
import com.zb.socket.model.SocketUserInfo;

@Service
public class WorldChatService extends BaseService {

	static final Logger LOGGER = LoggerFactory.getLogger(WorldChatService.class);

	private static final int CHAT_LIMIT = 20;
	private static final long WORLD_ADMIN_TYPE = 1;

	@Autowired
	GoodsService goodsService;

	@Autowired
	UserService userService;

	@Autowired
	RoomDefaultService roomService;

	/** 发布世界聊天 */
	public ReMsg worldChat(Long uid, Long atId, Long typeId, String fmt, String txt, boolean adm) {
		return worldChat(uid, atId, typeId, fmt, txt, 0L, 0, adm);
	}

	/** 发布世界聊天 */
	public ReMsg worldChat(Long uid, Long atId, Long typeId, String fmt, String txt, Long roomId, int type,
			boolean adm) {
		if (adm) {
			MapBody m = new MapBody(SocketHandler.HANDLER_NAME, SocketHandler.WORLD_CHAT)
					.append(SocketHandler.FN_USER_ID, uid).append("fmt", fmt).append("msg", txt)
					.append("hornType", typeId).append("roomId", roomId).append("type", type)
					.append("chatTime", System.currentTimeMillis());
			pubWorldMsg(MsgType.WORLDCHAT, m);
		} else {
			if (Arrays.binarySearch(DirtyWords.DIRTY, txt) > 0) {
				return new ReMsg(ReCode.DIRTY_WORDS);
			}
			if (getChatRedisTemplate().opsForValue().get(RK.worldChatUser(uid)) != null) {
				return new ReMsg(ReCode.TOO_FREQUENTLY);
			}
			ReMsg rm = goodsService.useProp(uid, typeId, 1, roomId, 0L);
			if (rm.getCode() == ReCode.OK.reCode()) {
				MapBody m = new MapBody(SocketHandler.HANDLER_NAME, SocketHandler.WORLD_CHAT)
						.append(SocketHandler.FN_USER_ID, uid).append("fmt", fmt).append("msg", txt)
						.append("hornType", typeId).append("roomId", roomId).append("type", type)
						.append("chatTime", System.currentTimeMillis()).append("user", userService.findById(uid));
				pubWorldMsg(MsgType.WORLDCHAT, m);
				getChatRedisTemplate().opsForValue().set(RK.worldChatUser(uid), "", CHAT_LIMIT, TimeUnit.SECONDS);
			} else {
				return rm;
			}
		}
		return new ReMsg(ReCode.OK);
	}

	public void pubWorldMsg(MsgType mt, MapBody mb) {
		Msg msg = new Msg(super.incrMsgId(), mt, 0, 0L, System.currentTimeMillis(), mb.getData());
		super.getChatRedisTemplate().convertAndSend(RedisChannel.ALL.get(), JSONUtil.beanToJson(msg));
	}

	// /** 查询 @记录 */
	// @SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	// public List<Map> queryAtHistory(Long uid) {
	// List<Map> list = new ArrayList<>();
	// List<DBObject> dbos = getC(DocName.WORLD_CHAT_LOG).find(new
	// BasicDBObject("uid", uid))
	// .sort(new BasicDBObject("updateTime", -1)).toArray();
	// if (dbos != null && !dbos.isEmpty()) {
	// list = dbos.stream().filter(e -> DboUtil.getLong(e, "atId") !=
	// null).map(e -> DboUtil.getLong(e, "atId"))
	// .distinct().map(e -> {
	// return new HashMap() {
	// {
	// put("id", e);
	// put("name", DboUtil.getString(userService.findById(e), "nickname"));
	// }
	// };
	// }).collect(Collectors.toList());
	// }
	// return list;
	// }

	/** APP前后台切换 */
	public ReMsg sleepOrWake(long uid, Integer type) {
		String json = super.getChatRedisTemplate().opsForValue().get(RK.imOnlineUser(uid));
		if (StringUtils.isNotBlank(json)) {
			SocketUserInfo ui = JSONUtil.jsonToBean(json, SocketUserInfo.class);
			ui.setSleep(type == 1 ? true : false);
			super.getChatRedisTemplate().opsForValue().set(RK.imOnlineUser(uid), JSONUtil.beanToJson(ui));
			return new ReMsg(ReCode.OK);
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	/** 游戏世界邀请 */
	public ReMsg inviteWorldChat(long uid, long roomId) {
		DBObject dbo = roomService.findById(roomId);
		if (dbo != null) {
			String game = "一局游戏";
			int type = DboUtil.getInt(dbo, "type");
			game = ActivityType.getActivityName(type);
			DBObject user = userService.findById(uid);
			StringBuilder sb = new StringBuilder();
			sb.append(Const.USERNAME_PREFIX + DboUtil.getString(user, "nickname") + Const.USERNAME_SUFFIX)
					.append("正准备玩").append(Const.GAME_PREFIX + game + Const.GAME_SUFFIX).append("，就差你了，快来和TA一起玩耍吧！");
			return worldChat(0L, 0L, WORLD_ADMIN_TYPE, "txt", sb.toString(), roomId, type, false);
		}
		return new ReMsg(ReCode.FAIL);
	}

}
