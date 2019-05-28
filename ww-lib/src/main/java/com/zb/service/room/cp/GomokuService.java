package com.zb.service.room.cp;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.activity.Gomoku;
import com.zb.models.room.activity.GomokuActor;
import com.zb.service.GameCPScoreService;
import com.zb.service.room.vo.RoomInfo;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class GomokuService extends RoomCPService {
	static final Logger LOGGER = LoggerFactory.getLogger(GomokuService.class);
	public static final int TIME_READY = 1;
	public static final int TIME_WAIT = 3;
	public static final int TIME_STEP = 45;
	public static final int TIME_END = 5;
	public static final int GRID = 15;
	public static final int WIN_COUNT = 5;
	public static final int END_NORMAL = 0;
	public static final int END_OUT = 1;
	public static final int END_DRAW = 3;
	public static final int END_SAYUNCLE = 2;
	public static final int END_FAIL = 4;
	public static final int POINT_WIN = 30;
	public static final int POINT_DEFAULT = 10;
	public static final int COIN_DEFAULT = 10;
	public static final int MAX_STEP = 225;

	@Autowired
	GameCPScoreService gameCPScoreService;

	@Override
	public ReMsg handle(Room room, long fr) {
		long roomId = room.get_id();
		Gomoku go = super.getActivity(roomId, Gomoku.class);
		try {
			if (Gomoku.STATUS_WAIT == go.getStatus()) {
				goInit(room, go);
			} else if (Gomoku.STATUS_START == go.getStatus()) {
				end(roomId, go, go.getCurUid(), END_NORMAL);
			} else if (Gomoku.STATUS_SETTLEMENT == go.getStatus()) {
				end(roomId, go, go.getCurUid(), END_NORMAL);
			} else if (Gomoku.STATUS_END == go.getStatus()) {
				endRoom(roomId, go);
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return null;
	}

	private void goInit(Room room, Gomoku go) {
		Set<GomokuActor> set = super.getRoomActors(room.get_id(), GomokuActor.class);
		if (set.size() == 2) {
			MapBody mb = new MapBody(SocketHandler.GOMOKU_INIT).append(SocketHandler.FN_ROOM_ID, room.get_id())
					.append("actId", go.get_id()).append(SocketHandler.FN_LIMIT, TIME_STEP);
			if (room.getRobitCount() == 1) {
				for (GomokuActor ac : set) {
					if (ac.isRobit()) {
						ac.setColour(GomokuActor.WHITE);
						go.setWhiteUser(ac.getUid());
						super.saveRoomActor(room.get_id(), ac);
					} else {
						ac.setColour(GomokuActor.BLACK);
						go.setBlackUser(ac.getUid());
						go.setCurUid(ac.getUid());
						super.saveRoomActor(room.get_id(), ac);
					}
				}
			} else if (room.getRobitCount() == 0) {
				int count = 0;
				int r = RandomUtil.nextInt(2);
				for (GomokuActor ac : set) {
					if (count == r) {
						ac.setColour(GomokuActor.BLACK);
						go.setBlackUser(ac.getUid());
						go.setCurUid(ac.getUid());
						super.saveRoomActor(room.get_id(), ac);
					} else {
						ac.setColour(GomokuActor.WHITE);
						go.setWhiteUser(ac.getUid());
						super.saveRoomActor(room.get_id(), ac);
					}
					count++;
				}
			} else {
				checkRoom(room, set);
				return;
			}
			mb.append("actors", set).append(SocketHandler.FN_USER_ID, go.getCurUid());
			go.setStatus(Gomoku.STATUS_START);
			super.saveActivity(room.get_id(), go);
			super.pubRoomMsg(room.get_id(), MsgType.GOMOKU, mb);
			super.addJob(room.get_id(), TIME_STEP);
		} else {
			MapBody mb = new MapBody(SocketHandler.HANDLER_NAME, SocketHandler.OUT_WAIT);
			for (GomokuActor ga : set) {
				super.pubUserMsg(ga.getUid(), MsgType.PROMPT, mb);
			}
			super.closeRoom(room.get_id());
		}
	}

	public void endRoom(final long roomId, Gomoku go) {
		if (go.getStatus() == Gomoku.STATUS_END) {
			go.setUpdateTime(System.currentTimeMillis());
			go.putActor(go.getBlackUser());
			go.putActor(go.getWhiteUser());
			super.getMongoTemplate().save(go);
		}
		super.closeRoom(roomId);
	}

	public ReMsg putDown(long roomId, long uid, int x, int y) {
		Gomoku go = super.getActivity(roomId, Gomoku.class);
		if (go.getStatus() == Gomoku.STATUS_START && go.getCurUid() == uid) {// 进行中
			int[][] goMap = go.getGoMap();
			if (x >= GRID || x < 0 || y >= GRID || y < 0) {
				return new ReMsg(ReCode.PUTDOWNWRONG);
			} else if (goMap[x][y] != 0) {
				return new ReMsg(ReCode.HASCHESS);
			} else if (go.getCurUid() != 0 && go.getCurUid() != uid) {
				return new ReMsg(ReCode.NOTYOURTURN);
			} else {
				MapBody mb = new MapBody(SocketHandler.GOMOKU_DOWN).append(SocketHandler.FN_ROOM_ID, roomId)
						.append("actId", go.get_id()).append(SocketHandler.FN_USER_ID, uid);
				int step = go.getStep() + 1;
				if (step >= MAX_STEP) {
					return end(roomId, go, go.getCurUid(), END_DRAW);
				}
				go.setStep(step);
				goMap[x][y] = step;
				go.setGoMap(goMap);
				long curUser = go.getCurUid();
				long nextUser = go.nextUser(curUser);
				go.setCurUid(nextUser);
				if (!checkCountSlowly(goMap, x, y)) {
					mb.append(SocketHandler.FN_LIMIT, TIME_STEP).append("nextUser", nextUser).append("x", x).append("y",
							y);
					super.addJob(roomId, TIME_STEP);
				} else {
					mb.append("x", x).append("y", y);
					go.setStatus(Gomoku.STATUS_SETTLEMENT);
					super.addJob(roomId, 1);
				}
				super.saveActivity(roomId, go);
				super.pubRoomMsg(roomId, MsgType.GOMOKU, mb);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	public static boolean checkCountSlowly(int[][] goMap, int x, int y) {
		int count = 0;
		int skewing = 1;
		while (x - skewing >= 0 && goMap[x][y] % 2 == goMap[x - skewing][y] % 2 && goMap[x - skewing][y] != 0) {
			count++;
			skewing++;
		}
		skewing = 1;
		while (x + skewing < GRID && goMap[x][y] % 2 == goMap[x + skewing][y] % 2 && goMap[x + skewing][y] != 0) {
			count++;
			skewing++;
		}
		if (count >= WIN_COUNT - 1) {
			return true;
		} else {
			count = 0;
			skewing = 1;
		}
		while (y - skewing >= 0 && goMap[x][y] % 2 == goMap[x][y - skewing] % 2 && goMap[x][y - skewing] != 0) {
			count++;
			skewing++;
		}
		skewing = 1;
		while (y + skewing < GRID && goMap[x][y] % 2 == goMap[x][y + skewing] % 2 && goMap[x][y + skewing] != 0) {
			count++;
			skewing++;
		}
		if (count >= WIN_COUNT - 1) {
			return true;
		} else {
			count = 0;
			skewing = 1;
		}
		while (x - skewing >= 0 && y + skewing < GRID && goMap[x][y] % 2 == goMap[x - skewing][y + skewing] % 2
				&& goMap[x - skewing][y + skewing] != 0) {
			count++;
			skewing++;
		}
		skewing = 1;
		while (y - skewing >= 0 && x + skewing < GRID && goMap[x][y] % 2 == goMap[x + skewing][y - skewing] % 2
				&& goMap[x + skewing][y - skewing] != 0) {
			count++;
			skewing++;
		}
		if (count >= WIN_COUNT - 1) {
			return true;
		} else {
			count = 0;
			skewing = 1;
		}
		while (x - skewing >= 0 && y - skewing >= 0 && goMap[x][y] % 2 == goMap[x - skewing][y - skewing] % 2
				&& goMap[x - skewing][y - skewing] != 0) {
			count++;
			skewing++;
		}
		skewing = 1;
		while (y + skewing < GRID && x + skewing < GRID && goMap[x][y] % 2 == goMap[x + skewing][y + skewing] % 2
				&& goMap[x + skewing][y + skewing] != 0) {
			count++;
			skewing++;
		}
		if (count >= WIN_COUNT - 1) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean fastCheckCount(int[][] goMap, int x, int y) {
		goMap[x][y] = -1;
		int count = 0;
		int skewing = 1;
		while (x - skewing >= 0 && goMap[x][y] == -1 && -1 == goMap[x - skewing][y]) {
			count++;
			skewing++;
		}
		skewing = 1;
		while (x + skewing < GRID && goMap[x][y] == -1 && -1 == goMap[x + skewing][y]) {
			count++;
			skewing++;
		}
		if (count >= WIN_COUNT - 1) {
			return true;
		} else {
			count = 0;
			skewing = 1;
		}
		while (y - skewing >= 0 && goMap[x][y] == -1 && -1 == goMap[x][y - skewing]) {
			count++;
			skewing++;
		}
		skewing = 1;
		while (y + skewing < GRID && goMap[x][y] == -1 && -1 == goMap[x][y + skewing]) {
			count++;
			skewing++;
		}
		if (count >= WIN_COUNT - 1) {
			return true;
		} else {
			count = 0;
			skewing = 1;
		}
		while (x - skewing >= 0 && y + skewing < GRID && goMap[x][y] == -1 && -1 == goMap[x - skewing][y + skewing]) {
			count++;
			skewing++;
		}
		skewing = 1;
		while (y - skewing >= 0 && x + skewing < GRID && goMap[x][y] == -1 && -1 == goMap[x + skewing][y - skewing]) {
			count++;
			skewing++;
		}
		if (count >= WIN_COUNT - 1) {
			return true;
		} else {
			count = 0;
			skewing = 1;
		}
		while (x - skewing >= 0 && y - skewing >= 0 && goMap[x][y] == -1 && -1 == goMap[x - skewing][y - skewing]) {
			count++;
			skewing++;
		}
		skewing = 1;
		while (y + skewing < GRID && x + skewing < GRID && goMap[x][y] == -1 && -1 == goMap[x + skewing][y + skewing]) {
			count++;
			skewing++;
		}
		goMap[x][y] = 0;
		if (count >= WIN_COUNT - 1) {
			return true;
		} else {
			return false;
		}
	}

	private ReMsg end(long roomId, Gomoku go, long uid, int endType) {
		if (go.getStatus() == Gomoku.STATUS_START || go.getStatus() == Gomoku.STATUS_SETTLEMENT) {
			if (END_DRAW != endType) {
				long nextId = go.nextUser(uid);
				go.setWinner(nextId);
				go.setLoser(uid);
			}
			int step = go.getStep();
			int winReward = 2;
			int lostReward = 2;
			if (step <= 15) {
				winReward = 5;
				lostReward = 5;
			} else if (step <= 30) {
				winReward = 10;
				lostReward = 10;
			} else {
				winReward = 10;
				lostReward = 10;
			}
			if (gameCPScoreService.isFriend(uid, go.nextUser(uid))) {
				go.setModel(Const.ACTIVITY_INVITE);
			} else {
				go.setModel(Const.ACTIVITY_MATCH);
			}
			if (go.getModel() == Const.ACTIVITY_INVITE) {// 好友
				if (endType == END_OUT) {// 退出房间
					winReward = 0;
				} else if (END_DRAW == endType) {// 平局
					winReward = 0;
					lostReward = 0;
				}
			} else {// 匹配
				if (END_DRAW == endType) {
					winReward = 10;
					lostReward = 10;
				}
				if (winReward > 0 && go.getWinner() > 0) {
					gameCPScoreService.changeScore(go.getWinner(), ActivityType.GOMOKU.getType(), winReward);
				}
				if (lostReward > 0 && go.getLoser() > 0) {
					gameCPScoreService.changeScore(go.getLoser(), ActivityType.GOMOKU.getType(), 0 - lostReward);
				}
			}
			gameCPScoreService.saveGameCPHistory(DboUtil.beanToDBO(go), ActivityType.GOMOKU.getType());
			go.setStatus(Gomoku.STATUS_END);
			super.saveActivity(roomId, go);
			super.addJob(roomId, TIME_END);// 5秒展示 结束

			MapBody mb = new MapBody(SocketHandler.GOMOKU_END, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_LIMIT, TIME_END).append("endType", endType).append("model", go.getModel())
					.append("winner", go.getWinner()).append("loser", go.getLoser());
			if (END_DRAW != endType) {
				userService.changePoint(go.getWinner(), Point.GOMOKU.getType(), POINT_WIN, 0);
				userService.changePoint(go.getLoser(), Point.GOMOKU.getType(), POINT_DEFAULT, 0);
				mb.append("winPoint", POINT_WIN).append("lostPoint", POINT_DEFAULT);
			} else {// 平局
				userService.changePoint(go.getBlackUser(), Point.GOMOKU.getType(), POINT_DEFAULT, 0);
				userService.changePoint(go.getWhiteUser(), Point.GOMOKU.getType(), POINT_DEFAULT, 0);

				mb.append("winPoint", POINT_DEFAULT).append("lostPoint", POINT_DEFAULT);
			}
			mb.append("winCoin", winReward).append("lostCoin", lostReward);
			if (winReward > 0) {
				coinService.addCoin(go.getWinner(), CoinLog.GOMOKU, go.get_id(), winReward, 0, "游戏胜利加币");
			}
			if (lostReward > 0) {
				coinService.forceReduce(go.getLoser(), CoinLog.GOMOKU, go.get_id(), lostReward, 0, "游戏失败扣币");
			}
			if (go.getModel() == Const.ACTIVITY_MATCH) {// 匹配
				mb.append("winScore", winReward).append("lostScore", lostReward);
			}
			super.pubRoomMsg(roomId, MsgType.GOMOKU, mb);
		} else {
			MapBody mb = new MapBody(SocketHandler.GOMOKU_END, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_LIMIT, TIME_END).append("endType", endType).append("model", go.getModel());
			super.pubRoomMsg(roomId, MsgType.GOMOKU, mb);
		}
		return null;
	}

	public void drawApply(long roomId, long uid) {
		Gomoku go = super.getActivity(roomId, Gomoku.class);
		MapBody mb = new MapBody(SocketHandler.GOMOKU_DRAW_APPLY).append(SocketHandler.FN_USER_ID, uid);
		long nextUser = go.nextUser(uid);
		super.pubRoomUserMsg(roomId, nextUser, MsgType.GOMOKU, mb, System.currentTimeMillis());
	}

	public void drawAgree(long roomId, long uid) {
		Gomoku go = super.getActivity(roomId, Gomoku.class);
		MapBody mb = new MapBody(SocketHandler.GOMOKU_DRAW_AGREE).append(SocketHandler.FN_USER_ID, uid);
		super.pubRoomMsg(roomId, MsgType.GOMOKU, mb);
		end(roomId, go, uid, END_DRAW);
	}

	public void drawRefuse(long roomId, long uid) {
		MapBody mb = new MapBody(SocketHandler.GOMOKU_DRAW_REFUSE).append(SocketHandler.FN_USER_ID, uid);
		super.pubRoomMsg(roomId, MsgType.GOMOKU, mb);
	}

	public void regretApply(long roomId, long uid) {
		Gomoku go = super.getActivity(roomId, Gomoku.class);
		MapBody mb = new MapBody(SocketHandler.GOMOKU_REGRET_APPLY).append(SocketHandler.FN_USER_ID, uid);
		long nextUser = go.nextUser(uid);
		super.pubRoomUserMsg(roomId, nextUser, MsgType.GOMOKU, mb, System.currentTimeMillis());
	}

	public void regretAgree(long roomId, long uid) {
		Gomoku go = super.getActivity(roomId, Gomoku.class);
		int step = go.getStep();
		int[][] goMap = go.getGoMap();
		long curUid = go.getCurUid();
		int regStep;
		if (uid != curUid && step > 1) {
			regStep = 2;
		} else {
			regStep = 1;
		}
		int x1, y1, x2, y2;
		x1 = y1 = x2 = y2 = 0;
		int count = regStep;
		int index = 1;
		for (int i = 0; i < GRID; i++) {
			for (int j = 0; j < GRID; j++) {
				if (goMap[i][j] == step - index) {
					if (index == 1) {
						x1 = i;
						y1 = j;
						goMap[i][j] = 0;
					} else {
						x2 = i;
						y2 = j;
						goMap[i][j] = 0;
					}
					count--;
					if (count == 0) {
						break;
					} else {
						index++;
						continue;
					}
				}
			}
		}
		go.setStep(step - regStep);
		go.setGoMap(goMap);
		super.saveActivity(roomId, go);
		MapBody mb = new MapBody(SocketHandler.GOMOKU_REGRET_AGREE).append(SocketHandler.FN_USER_ID, uid)
				.append("regStep", regStep).append("x1", x1).append("y1", y1);
		if (regStep != 1) {
			mb.append("x2", x2).append("y2", y2);
		}
		super.pubRoomMsg(roomId, MsgType.GOMOKU, mb);
	}

	public void regretRefuse(long roomId, long uid) {
		MapBody mb = new MapBody(SocketHandler.GOMOKU_REGRET_REFUSE).append(SocketHandler.FN_USER_ID, uid);
		super.pubRoomMsg(roomId, MsgType.GOMOKU, mb);
	}

	public void sayUncle(long roomId, long uid) {
		Gomoku go = super.getActivity(roomId, Gomoku.class);
		end(roomId, go, uid, END_SAYUNCLE);
	}

	@Override
	public ReMsg inRoom(final long uid, Room r, int model, boolean robit) {
		RoomInfo ri = new RoomInfo();
		Gomoku go = super.getActivity(r.get_id(), Gomoku.class);
		if (go.getStatus() == Gomoku.STATUS_READY || go.getStatus() == Gomoku.STATUS_WAIT) {// 第一个人进来
			go.setModel(model == Const.ACTIVITY_INVITE ? model : Const.ACTIVITY_MATCH);
			go.setStatus(Gomoku.STATUS_WAIT);
			if (go.getActors().size() == 0) {
				addJob(r.get_id(), TIME_WAIT);
			} else if (go.getActors().size() == 1) {
				if (robit) {// 房间内已有一个玩家 机器人不能再进入
					return new ReMsg(ReCode.FAIL, ri);
				}
				addJob(r.get_id(), TIME_READY);
			} else {
				return new ReMsg(ReCode.FAIL, ri);
			}
			go.putActor(uid);
			super.saveActivity(r.get_id(), go);
		} else {
			if (uid != go.getBlackUser() && uid != go.getWhiteUser()) {// 非进入状态下，除了当前用户，不能进入房间
				return new ReMsg(ReCode.FAIL, ri);
			}
		}

		if (r == null || r.getType() != ActivityType.GOMOKU.getType()) {
			LOGGER.error(r.get_id() + " " + r.getType());
			ri.setCode(1);
			return new ReMsg(ReCode.FAIL, ri);
		}
		GomokuActor ua = super.getRoomActor(r.get_id(), uid, GomokuActor.class);
		if (ua != null) {
			ua.setStatus(Actor.STATUS_ONLINE);
		} else {
			@SuppressWarnings("deprecation")
			DBObject user = userService.findById(uid);
			if (user == null) {
				ri.setCode(1);
				return new ReMsg(ReCode.FAIL, ri);
			}
			ua = new GomokuActor();
			ua.setUcStatus(GomokuActor.A_STATUS_START);
			ua.setVip(DboUtil.getInt(user, "vip") == null ? 0 : DboUtil.getInt(user, "vip"));
			ua.setRobit(robit);
			ua.setUid(DboUtil.getLong(user, "_id"));
			ua.setAvatar(DboUtil.getString(user, "avatar"));
			ua.setPoint(DboUtil.getInt(user, "point"));
			ua.setSex(DboUtil.getInt(user, "sex"));
			ua.setNickname(DboUtil.getString(user, "nickname"));
			ua.setProvince(DboUtil.getString(user, "province") == null ? "火星" : DboUtil.getString(user, "province"));
			if (ua.getProvince().contains("市")) {
				ua.setCity("");
			} else {
				ua.setCity(DboUtil.getString(user, "city") == null ? "" : DboUtil.getString(user, "city"));
			}
			ua.setGame("五子棋");
			super.saveRoomActor(r.get_id(), ua);
		}
		// super.saveActivity(r.get_id(), go);
		Set<GomokuActor> actors = super.getRoomActors(r.get_id(), GomokuActor.class);
		changeRoomCount(r, actors, T_START);
		ri.setStatus(Room.ACTIVITY);
		ri.setPub(r.isPub());
		ri.setHasOwner(r.getUid() == 0 ? false : true);
		ri.setActors(actors);
		// 开始游戏

		MapBody mb = new MapBody(SocketHandler.IN_ROOM, SocketHandler.FN_USER_ID, ua.getUid())
				.append("nickname", ua.getNickname()).append("sex", ua.getSex()).append("avatar", ua.getAvatar())
				.append("actors", actors).append("province", ua.getProvince()).append("city", ua.getCity())
				.append("role", ua.getRole()).append(SocketHandler.FN_ROOM_ID, r.get_id());
		long dt = System.currentTimeMillis();
		for (GomokuActor a : actors) {
			if (uid != a.getUid() && a.getStatus() == Actor.STATUS_ONLINE) {
				super.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.ROOM, mb, dt);
				break;
			}
		}
		return new ReMsg(ReCode.OK, ri);
	}

	public ReMsg outRoom(long uid, Room r, int type) {
		if (r.getType() != ActivityType.GOMOKU.getType()) {
			LOGGER.error(r.get_id() + " " + r.getType());
			return new ReMsg(ReCode.FAIL);
		}
		GomokuActor actor = super.getRoomActor(r.get_id(), uid, GomokuActor.class);
		if (null == actor) {
			return new ReMsg(ReCode.ACTOR_ERROR);
		} else {
			Gomoku go = super.getActivity(r.get_id(), Gomoku.class);
			if (go.getStatus() == Gomoku.STATUS_START) {
				end(r.get_id(), go, uid, END_OUT);
			} else if (go.getStatus() == Gomoku.STATUS_READY || go.getStatus() == Gomoku.STATUS_WAIT) {
				end(r.get_id(), go, uid, END_FAIL);
			}
			closeRoom(r.get_id());
		}
		return new ReMsg(ReCode.OK);
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
		return null;
	}

	@Override
	public ReMsg getActorStatus(long uid, Room r) {
		return null;
	}

	@Override
	public ReMsg canInRoom(int type, long actId, long roomId) {
		return new ReMsg(ReCode.OK, 2);
	}

}
