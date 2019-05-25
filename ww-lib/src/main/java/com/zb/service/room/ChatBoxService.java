package com.zb.service.room;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.core.web.ReMsg;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.activity.ChatBox;
import com.zb.models.room.activity.ChatBoxActor;
import com.zb.service.room.vo.ChatBoxRoomInfo;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class ChatBoxService extends RoomService {

	@Override
	public ReMsg handle(Room room, long fr) {
		return new ReMsg(ReCode.OK);
	}

	/** 语音禁言 和公屏禁言 全体禁言套餐 */
	public ReMsg shutupChat(long operatorUid, long roomId, Boolean voice, Boolean wordHome) {
		ChatBox cb = super.getActivity(roomId, ChatBox.class);
		ChatBoxActor operatorUser = super.getRoomActor(roomId, operatorUid, ChatBoxActor.class);
		if (null == operatorUser) {
			return new ReMsg(ReCode.USER_NOT_EXISTS);
		}
		if (!operatorUser.isBuyUser() && !operatorUser.isOwner()) {// 房主或者房管有权限操作
			return new ReMsg(ReCode.FAIL);
		}
		MapBody mb = new MapBody(SocketHandler.CHATBOX_SHUTUP).append(SocketHandler.FN_ROOM_ID, roomId)
				.append("actId", cb.get_id()).append("operatorUid", operatorUid);
		if (null != voice) {
			long[] seatUserTable = cb.getSeatUserTable();
			if (voice) {// 全部开麦 解锁被锁座位
				for (int i = 1; i < seatUserTable.length; i++) {// 忽略房管位
					if (seatUserTable[i] == -1) {
						seatUserTable[i] = 0;
					}
				}
			} else {// 全部禁言 锁住无人座位
				for (int i = 1; i < seatUserTable.length; i++) {// 忽略房管位
					if (seatUserTable[i] == 0) {
						seatUserTable[i] = -1;
					}
				}
			}
			cb.setSeatUserTable(seatUserTable);
			cb.setVoice(voice);
		}
		if (null != wordHome) {
			cb.setWordHome(wordHome);
		}
		mb.append("seatUserTable", cb.getSeatUserTable());
		mb.append("wordHome", cb.isWordHome());
		mb.append("voice", cb.isVoice());
		super.saveActivity(roomId, cb);
		super.pubRoomMsg(roomId, MsgType.CHATBOX, mb);
		return new ReMsg(ReCode.OK);
	}

	/** 下麦 */
	public ReMsg getDownChat(Long operatorUid, long uid, long roomId) {
		ChatBox cb = super.getActivity(roomId, ChatBox.class);
		ChatBoxActor ca = super.getRoomActor(roomId, uid, ChatBoxActor.class);
		if (null == ca) {
			return new ReMsg(ReCode.USER_NOT_EXISTS);
		} else if (ca.getRole() != Actor.ROLE_EXECUTOR) {
			return new ReMsg(ReCode.ON_TREE_YET);
		}
		if (null != operatorUid && operatorUid > 0) {// 被动操作
			ChatBoxActor operatorUser = super.getRoomActor(roomId, operatorUid, ChatBoxActor.class);
			if (null == operatorUser) {
				return new ReMsg(ReCode.USER_NOT_EXISTS);
			}
			if (operatorUser.isOwner() || operatorUser.isBuyUser()) { // 房主或者房管有权限操作
				if (ca.isBuyUser()) {// 房主不能被操作
					return new ReMsg(ReCode.FAIL);
				}
			} else {// 非房主或房管 没有权限操作
				return new ReMsg(ReCode.FAIL);
			}
		}
		ca.setRole(Actor.ROLE_VIEWER);
		int index = -1;
		ca.setSeat(index);
		long[] seatUserTable = cb.getSeatUserTable();
		for (int i = 0; i < seatUserTable.length; i++) {
			if (seatUserTable[i] == uid) {
				index = i;
				seatUserTable[i] = 0;
				break;
			}
		}
		cb.setSeatUserTable(seatUserTable);
		if (index == 0) {// 从0号位置离开
			cb.setOwner(0L);
			ca.setOwner(false);
		}
		super.saveActivity(roomId, cb);
		super.saveRoomActor(roomId, ca);
		MapBody mb = new MapBody(SocketHandler.CHATBOX_DOWNCHAT, SocketHandler.FN_USER_ID, uid)
				.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", cb.get_id()).append("emptyNo", index)
				.append("operatorUid", operatorUid);
		super.pubRoomMsg(roomId, MsgType.CHATBOX, mb);
		return new ReMsg(ReCode.OK);
	}

	/** 上麦 */
	public ReMsg climbChat(Long operatorUid, long uid, long roomId, int seatNo) {
		if (lock(RK.seatLock(roomId), 1L)) {
			ChatBox cb = super.getActivity(roomId, ChatBox.class);
			ChatBoxActor ca = super.getRoomActor(roomId, uid, ChatBoxActor.class);
			if (null == ca) {
				return new ReMsg(ReCode.USER_NOT_EXISTS);
			}
			if (null != operatorUid && operatorUid > 0) {// 被动操作
				ChatBoxActor operatorUser = super.getRoomActor(roomId, operatorUid, ChatBoxActor.class);
				if (null == operatorUser) {
					return new ReMsg(ReCode.USER_NOT_EXISTS);
				}
				if (operatorUser.isOwner() || operatorUser.isBuyUser()) { // 房主或者房管有权限操作
					if (ca.isBuyUser()) {// 房主不能被操作
						return new ReMsg(ReCode.FAIL);
					}
				} else {// 非房主或房管 没有权限操作
					return new ReMsg(ReCode.FAIL);
				}
			}
			int emptyNo = -1;
			long[] seatTable = cb.getSeatUserTable();
			if (seatTable[seatNo] != 0) {
				unlock(RK.seatLock(roomId));
				return new ReMsg(ReCode.SEAT_BE_USE);
			} else {
				if (ca.getSeat() >= 0) {// 离开原来的位置
					emptyNo = ca.getSeat();
					seatTable[emptyNo] = 0;// 原位置置空
					if (emptyNo == 0) {// 原位置是管理员位置 初始化数据
						ca.setOwner(false);
						cb.setOwner(0L);
					}
				}
				seatTable[seatNo] = uid;
				cb.setSeatUserTable(seatTable);
				ca.setSeat(seatNo);
				ca.setRole(Actor.ROLE_EXECUTOR);
				super.saveRoomActor(roomId, ca);
				if (seatNo == 0) {// 上到0号位置
					cb.setOwner(uid);
					ca.setOwner(true);
				}
				super.saveActivity(roomId, cb);
				unlock(RK.seatLock(roomId));
			}
			ca.setRole(Actor.ROLE_EXECUTOR);
			super.saveRoomActor(roomId, ca);
			MapBody mb = new MapBody(SocketHandler.CHATBOX_CLIMBCHAT, SocketHandler.FN_USER_ID, uid)
					.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", cb.get_id()).append("seatNo", seatNo)
					.append("operatorUid", operatorUid).append("emptyNo", emptyNo);
			super.pubRoomMsg(roomId, MsgType.CHATBOX, mb);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.SEAT_BE_USE);
	}

	/** 锁定位置 */
	public ReMsg lockSeat(long operatorUid, long roomId, int seatNo) {
//		if (seatNo == 0) {// 0号位无法被锁定
//			return new ReMsg(ReCode.FAIL);
//		}
		if (lock(RK.seatLock(roomId), 1L)) {
			ChatBox cb = super.getActivity(roomId, ChatBox.class);
			ChatBoxActor operatorUser = super.getRoomActor(roomId, operatorUid, ChatBoxActor.class);
			if (null == operatorUser) {
				return new ReMsg(ReCode.USER_NOT_EXISTS);
			}
			if (operatorUser.isOwner() || operatorUser.isBuyUser()) { // 房主或者房管有权限操作
				long[] seatTable = cb.getSeatUserTable();
				if (seatTable[seatNo] != ChatBox.STATUS_OK) {// 座位上有人 无法关闭位置
					unlock(RK.seatLock(roomId));
					return new ReMsg(ReCode.SEAT_BE_USE);
				} else {
					seatTable[seatNo] = ChatBox.STATUS_LOCK;
					cb.setSeatUserTable(seatTable);
					super.saveActivity(roomId, cb);
					unlock(RK.seatLock(roomId));
				}
				MapBody mb = new MapBody(SocketHandler.CHATBOX_LOCKSEAT).append(SocketHandler.FN_ROOM_ID, roomId)
						.append("actId", cb.get_id()).append("seatNo", seatNo).append("operatorUid", operatorUid);
				super.pubRoomMsg(roomId, MsgType.CHATBOX, mb);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	/** 解锁位置 */
	public ReMsg unlockSeat(long operatorUid, long roomId, int seatNo) {
		if (lock(RK.seatLock(roomId), 1L)) {
			ChatBox cb = super.getActivity(roomId, ChatBox.class);
			ChatBoxActor operatorUser = super.getRoomActor(roomId, operatorUid, ChatBoxActor.class);
			if (null == operatorUser) {
				return new ReMsg(ReCode.USER_NOT_EXISTS);
			}
			if (operatorUser.isOwner() || operatorUser.isBuyUser()) { // 房主或者房管有权限操作
				long[] seatTable = cb.getSeatUserTable();
				if (seatTable[seatNo] != ChatBox.STATUS_LOCK) {
					unlock(RK.seatLock(roomId));
					return new ReMsg(ReCode.SEAT_BE_USE);
				} else {
					seatTable[seatNo] = ChatBox.STATUS_OK;
					cb.setSeatUserTable(seatTable);
					super.saveActivity(roomId, cb);
					unlock(RK.seatLock(roomId));
				}
				MapBody mb = new MapBody(SocketHandler.CHATBOX_UNLOCKSEAT).append(SocketHandler.FN_ROOM_ID, roomId)
						.append("actId", cb.get_id()).append("emptyNo", seatNo).append("operatorUid", operatorUid);
				super.pubRoomMsg(roomId, MsgType.CHATBOX, mb);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	/** 房主或房管关闭某个人麦 */
	public ReMsg lockUser(long operatorUid, long roomId, long uid) {
		ChatBox cb = super.getActivity(roomId, ChatBox.class);
		ChatBoxActor operatorUser = super.getRoomActor(roomId, operatorUid, ChatBoxActor.class);
		if (null == operatorUser) {
			return new ReMsg(ReCode.USER_NOT_EXISTS);
		}
		if (operatorUser.isOwner() || operatorUser.isBuyUser()) {// 房主或者房管有权限操作
			ChatBoxActor ca = super.getRoomActor(roomId, uid, ChatBoxActor.class);
			if (null == ca) {
				return new ReMsg(ReCode.USER_NOT_EXISTS);
			}
			if (ca.isBuyUser()) {// 房主不能被操作
				return new ReMsg(ReCode.FAIL);
			}
			int seatNo = -1;
			long[] seatUserTable = cb.getSeatUserTable();
			for (int i = 0; i < seatUserTable.length; i++) {
				if (seatUserTable[i] == uid) {
					seatNo = i;
					break;
				}
			}
			if (seatNo >= 0 && ca.getRole() == Actor.ROLE_EXECUTOR) {
				cb.addLockUser(uid, 0L);
				ca.setUserVoice(ChatBoxActor.STATUS_LOCK);
				super.saveActivity(roomId, cb);
				super.saveRoomActor(roomId, ca);
				MapBody mb = new MapBody(SocketHandler.CHATBOX_LOCKUSER, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", cb.get_id()).append("seatNo", seatNo)
						.append("operatorUid", operatorUid).append("lockUsers", cb.getLockUsers())
						.append("userVoice", ca.getUserVoice());
				super.pubRoomMsg(roomId, MsgType.CHATBOX, mb);
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 房主或房管打开某个人麦 */
	public ReMsg unlockUser(long operatorUid, long roomId, long uid) {
		ChatBox cb = super.getActivity(roomId, ChatBox.class);
		ChatBoxActor operatorUser = super.getRoomActor(roomId, operatorUid, ChatBoxActor.class);
		if (null == operatorUser) {
			return new ReMsg(ReCode.USER_NOT_EXISTS);
		}
		if (operatorUser.isOwner() || operatorUser.isBuyUser()) {// 房主或者房管有权限操作
			ChatBoxActor ca = super.getRoomActor(roomId, uid, ChatBoxActor.class);
			if (null == ca) {
				return new ReMsg(ReCode.USER_NOT_EXISTS);
			}
			int seatNo = -1;
			long[] seatUserTable = cb.getSeatUserTable();
			for (int i = 0; i < seatUserTable.length; i++) {
				if (seatUserTable[i] == uid) {
					seatNo = i;
					break;
				}
			}
			if (seatNo >= 0 && ca.getRole() == Actor.ROLE_EXECUTOR) {
				cb.removeLockUser(uid);
				if (cb.isVoice()) {// 全局可说话
					ca.setUserVoice(ChatBoxActor.STATUS_DEFAULT);
				} else {// 全局禁言时开麦
					ca.setUserVoice(ChatBoxActor.STATUS_UP);
				}
				super.saveActivity(roomId, cb);
				super.saveRoomActor(roomId, ca);
				MapBody mb = new MapBody(SocketHandler.CHATBOX_UNLOCKUSER, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", cb.get_id()).append("seatNo", seatNo)
						.append("uid", uid).append("operatorUid", operatorUid).append("lockUsers", cb.getLockUsers())
						.append("userVoice", ca.getUserVoice());
				super.pubRoomMsg(roomId, MsgType.CHATBOX, mb);
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg inRoom(final long uid, Room r, int model, boolean robit) {
		ChatBoxRoomInfo ri = new ChatBoxRoomInfo(ActivityType.CHATBOX.getType());
		if (r == null || r.getType() != ActivityType.CHATBOX.getType()) {
			log.error(r.get_id() + " " + r.getType());
			ri.setCode(1);
			return new ReMsg(ReCode.FAIL, ri);
		}
		ChatBox uc = super.getActivity(r.get_id(), ChatBox.class);
		ChatBoxActor ua = super.getRoomActor(r.get_id(), uid, ChatBoxActor.class);
		if (ua != null) {// 恢复重连
			ua.setStatus(Actor.STATUS_ONLINE);
		} else {// 进入房间
			DBObject user = userService.findByIdSafe(uid);
			if (user == null) {
				ri.setCode(1);
				return new ReMsg(ReCode.FAIL, ri);
			}
			ua = new ChatBoxActor();
			ua.setVip(DboUtil.getInt(user, "vip") == null ? 0 : DboUtil.getInt(user, "vip"));
			ua.setRobit(robit);
			ua.setUid(DboUtil.getLong(user, "_id"));
			ua.setAvatar(DboUtil.getString(user, "avatar"));
			ua.setPoint(DboUtil.getInt(user, "point"));
			ua.setSex(DboUtil.getInt(user, "sex"));
			ua.setNickname(DboUtil.getString(user, "nickname"));
			ua.setProvince(DboUtil.getString(user, "province") == null ? "火星" : DboUtil.getString(user, "province"));
			ua.setPersonLabel(DboUtil.getString(user, "personLabel"));
			if (ua.getProvince().contains("市")) {
				ua.setCity("");
			} else {
				ua.setCity(DboUtil.getString(user, "city") == null ? "" : DboUtil.getString(user, "city"));
			}
			ua.setRole(Actor.ROLE_VIEWER);
			if (null != r.getBuyUid() && uid == r.getBuyUid()) {// 房间购买人设置最高权限
				ua.setBuyUser(true);
			}
			ua.setGame("语音聊天室");
			if (uc.getLockUsers().containsKey(uid)) {// 禁麦
				ua.setUserVoice(ChatBoxActor.STATUS_LOCK);
			} else {
				ua.setUserVoice(ChatBoxActor.STATUS_DEFAULT);
			}
		}
		super.saveRoomActor(r.get_id(), ua);
		Set<ChatBoxActor> as = super.getRoomActors(r.get_id(), ChatBoxActor.class);
		super.changeRoomCount(r, as, T_INROOM);
		if (r.getStatus() == Room.OPEN) {
			ri.setStatus(Room.OPEN);
		} else {
			ri.setStatus(Room.ACTIVITY);
		}
		// if (null != r.getBuyUid() && r.getBuyUid() > 0 && (null ==
		// uc.getBuyUid() || uc.getBuyUid() == 0)) {
		// uc.setBuyUid(r.getBuyUid());
		// super.saveActivity(r.get_id(), uc);
		// }
		ri.setPub(r.isPub());
		ri.setHasOwner((null == uc.getOwner() || uc.getOwner() == 0) ? false : true);
		ri.setActors(as);
		ri.setLockUsers(uc.getLockUsers());// 被禁麦用户
		ri.setVoice(uc.isVoice());
		ri.setWordHome(uc.isWordHome());
		ri.setrName(r.getrName());
		ri.setSeatUserTable(uc.getSeatUserTable());
		ri.setSys(r.isSys());// 系统房间或包间
		MapBody mb = new MapBody(SocketHandler.IN_ROOM, SocketHandler.FN_USER_ID, ua.getUid())
				.append("nickname", ua.getNickname()).append("sex", ua.getSex()).append("avatar", ua.getAvatar())
				.append("actors", as).append("province", ua.getProvince()).append("city", ua.getCity())
				.append("role", ua.getRole()).append("userVoice", ua.getUserVoice())
				.append("lockUsers", uc.getLockUsers()).append("seatUserTable", uc.getSeatUserTable())
				.append("voice", uc.isVoice()).append("wordHome", uc.isWordHome());
		long dt = System.currentTimeMillis();
		for (Actor a : as) {
			if (uid != a.getUid()) {
				super.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.ROOM, mb, dt);
			}
		}
		return new ReMsg(ReCode.OK, ri);
	}

	@Override
	public ReMsg outRoom(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.OUT_ROOM);
	}

	@Override
	public ReMsg disconnectRoom(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.DISCONNECT);
	}

	@Override
	public ReMsg kick(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.KICK);
	}

	public ReMsg outRoom(long uid, Room r, int type) {
		if (r.getType() != ActivityType.CHATBOX.getType()) {
			log.error(r.get_id() + " " + r.getType());
			return new ReMsg(ReCode.FAIL);
		}
		if (Room.ACTIVITY == r.getStatus() && SocketHandler.KICK == type) {
			return new ReMsg(ReCode.KICK_ERROR);
		}
		ChatBoxActor actor = super.getRoomActor(r.get_id(), uid, ChatBoxActor.class);
		ChatBox uc = super.getActivity(r.get_id(), ChatBox.class);
		if (null == actor) {
			return new ReMsg(ReCode.ACTOR_ERROR);
		} else {
			if (SocketHandler.KICK == type) {// 被踢出房间
				if (actor.isBuyUser()) {// 房间购买人不能被踢
					return new ReMsg(ReCode.FAIL);
				} else {
					try {
						MapBody mb = new MapBody(type).append("nickname",
								super.getRoomActor(r.get_id(), uc.getOwner()).getNickname());
						super.pubUserMsg(uid, MsgType.PROMPT, mb);
					} catch (Exception e) {
						log.error("发送踢人信息", e);
					}
				}
			}
		}
		if (actor.getSeat() >= 0) {// 下麦
			getDownChat(null, uid, r.get_id());
		}
		this.delRoomActor(r.get_id(), uid);
		Set<ChatBoxActor> as = super.getRoomActors(r.get_id(), ChatBoxActor.class);
		super.changeRoomCount(r, as, T_OUTROOM);
		MapBody mb = new MapBody(type, SocketHandler.FN_USER_ID, actor.getUid()).append("nickname", actor.getNickname())
				.append(SocketHandler.FN_ROOM_ID, r.get_id());
		this.pubRoomMsg(r.get_id(), MsgType.ROOM, mb);
		Set<ChatBoxActor> actors = super.getRoomActors(r.get_id(), ChatBoxActor.class);
		if (0 == actors.size()) {
			super.closeRoom(r.get_id());
		}
		return new ReMsg(ReCode.OK);
	}

	@Override
	public ReMsg getActorStatus(long uid, Room r) {
		return new ReMsg(ReCode.OK, 2);
	}

	@Override
	public ReMsg canInRoom(final int type, final long actId, final long roomId) {
		return new ReMsg(ReCode.OK, 2);
	}

}
