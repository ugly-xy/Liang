package com.zb.service.room.cp;

import java.util.Map.Entry;
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
import com.zb.models.GameCPScore;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.ActivityCP;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.activity.SchoolWar;
import com.zb.models.room.activity.SchoolWarActor;
import com.zb.service.GameCPScoreService;
import com.zb.service.room.vo.SchoolWarRoomInfo;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class SchoolWarService extends RoomCPService {
	static final Logger log = LoggerFactory.getLogger(SchoolWarService.class);

	@Autowired
	GameCPScoreService gameCPScoreService;
	public static final int TIME_WAIT = 3;
	public static final int TIME_READY = 1;
	public static final int TIME_TOTAL = 60;// 总时长60s
	public static final int TIME_TURN_BACK = 1;// 老师回头时间 1s
	public static final int TIME_STANDUP = 2;// 被抓住罚站时间
	public static final int TIME_START = 5;// 开始
	public static final int TIME_END = 4; // 结束展示

	// 打人或嘲讽
	public static final int SCORE_FIGHT = 1;// 打人 自己+1分
	public static final int SCORE_LAUGH = 2;// 嘲讽 对面-2分

	public static final int START_COUNT = 2;

	public static final int END_NORMAL = 0; // 正常结束
	public static final int END_OUTROOM = 1; // 退出房间结束
	public static final int END_FAIL = 4; // fail结束

	public static final int POINT_WIN = 30;
	public static final int POINT_DEFAULT = 10;
	public static final int COIN_DEFAULT = 10;
	public static final int COIN_GIVEUP = 30;

	public static final int TEACHER_NO = 0; // 没有老师回头
	public static final int TEACHER = 1; // 老师回头
	public static final int HEADER_LEFT_TRUE = 2;// 主任左边真人
	public static final int HEADER_RIGHT_TRUE = 3;// 主任右边真人
	public static final int HEADER_LEFT_LIE = 4;// 主任左边虚影
	public static final int HEADER_RIGHT_LIE = 5;// 主任右边虚影

	public static final String TURNBACK_MAN = "turnBackMan";// 回头人
	public static final String EXTRATIME = "extraTime";// 距离结束剩余时间

	@Override
	public ReMsg handle(Room room, long fr) {
		if (room.getType() != ActivityType.SCHOOL_WAR.getType()) {
			log.error(room.get_id() + " " + room.getType());
			return new ReMsg(ReCode.FAIL);
		}
		try {
			SchoolWar uc = super.getActivity(room.get_id(), SchoolWar.class);
			if (SchoolWar.STATUS_WAIT == uc.getStatus()) {
				startFight(room, uc);
			} else if (SchoolWar.STATUS_END == uc.getStatus()) {
				this.closeRoom(room.get_id(), uc);
			} else if (uc.getTurnBack() == TEACHER_NO) {
				turnBackLie(room.get_id(), uc);
			} else if (uc.getTurnBack() > HEADER_RIGHT_TRUE) {
				turnBack(room.get_id(), uc);
			} else {
				if (uc.getStandup() != 0) {
					turnUp(room.get_id(), uc);
				} else {
					nextFight(room.get_id(), uc, null);
				}
			}
		} catch (Exception e) {
			log.error("调用任务roomId=" + room.get_id() + "\n", e);
			this.addJob(room.get_id(), TIME_START);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 进入房间 */
	public ReMsg inRoom(final long uid, Room r, int model, boolean robit) {
		SchoolWarRoomInfo ri = new SchoolWarRoomInfo(ActivityType.SCHOOL_WAR.getType());
		SchoolWar uc = super.getActivity(r.get_id(), SchoolWar.class);
		int seat = SchoolWarActor.SEAT_LEFT;
		if (uc.getStatus() == SchoolWar.STATUS_READY || uc.getStatus() == SchoolWar.STATUS_WAIT) {// 第一个人进来
			uc.setModel(model == Const.ACTIVITY_INVITE ? model : Const.ACTIVITY_MATCH);
			uc.setStatus(ActivityCP.STATUS_WAIT);

			if (uc.getActors().size() == 0) {
				addJob(r.get_id(), TIME_WAIT);
			} else if (uc.getActors().size() == 1) {
				seat = SchoolWarActor.SEAT_RIGHT;
				addJob(r.get_id(), TIME_READY);
			} else {
				return new ReMsg(ReCode.FAIL, ri);
			}
			uc.putScore(uid, 0);
			uc.putActor(uid, SchoolWarActor.A_STATUS_START);
			super.saveActivity(r.get_id(), uc);
		} else {
			if (!uc.getActors().containsKey(uid)) {// 非进入状态下，除了当前用户，不能进入房间
				return new ReMsg(ReCode.FAIL, ri);
			}
		}

		if (r == null || r.getType() != ActivityType.SCHOOL_WAR.getType()) {
			log.error(r.get_id() + " " + r.getType());
			ri.setCode(1);
			return new ReMsg(ReCode.FAIL, ri);
		}
		SchoolWarActor ua = super.getRoomActor(r.get_id(), uid, SchoolWarActor.class);
		if (ua != null) {
			ua.setStatus(Actor.STATUS_ONLINE);
			super.saveActivity(r.get_id(), uc);
		} else {
			DBObject user = userService.findById(uid);
			if (user == null) {
				ri.setCode(1);
				return new ReMsg(ReCode.FAIL, ri);
			}
			ua = new SchoolWarActor();
			ua.setSeat(1);
			ua.setUcStatus(SchoolWarActor.A_STATUS_START);
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
			ua.setGame("教室大作战");
			ua.setSeat(seat);
			super.saveRoomActor(r.get_id(), ua);
		}
		Set<SchoolWarActor> actors = super.getRoomActors(r.get_id(), SchoolWarActor.class);
		changeRoomCount(r, actors, T_START);
		ri.setActorsStatus(uc.getActors());// 玩家状态
		ri.setActorsScores(uc.getScores());// 玩家积分
		ri.setTime(extraTime(uc.getSt()));// 游戏倒计时
		ri.setStatus(Room.ACTIVITY);
		ri.setPub(r.isPub());
		ri.setHasOwner(r.getUid() == 0 ? false : true);
		ri.setActors(actors);
		// 开始游戏
		if (r.getStatus() == Room.ACTIVITY && uc.getActors().size() == START_COUNT
				&& uc.getStatus() == SchoolWar.STATUS_READY) {
			addJob(r.get_id(), TIME_READY);
		}
		MapBody mb = new MapBody(SocketHandler.IN_ROOM, SocketHandler.FN_USER_ID, ua.getUid())
				.append("nickname", ua.getNickname()).append("sex", ua.getSex()).append("avatar", ua.getAvatar())
				.append("actors", actors).append("province", ua.getProvince()).append("city", ua.getCity())
				.append("role", ua.getRole()).append(SocketHandler.FN_ROOM_ID, r.get_id());
		long dt = System.currentTimeMillis();
		for (SchoolWarActor a : actors) {
			if (uid != a.getUid() && a.getStatus() == Actor.STATUS_ONLINE) {
				super.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.ROOM, mb, dt);
				break;
			}
		}
		return new ReMsg(ReCode.OK, ri);
	}

	/** 开始游戏 */
	private boolean startFight(Room r, SchoolWar uc) {
		Set<SchoolWarActor> actors = super.getRoomActors(r.get_id(), SchoolWarActor.class);
		if (r.getRobitCount() == r.getCount()) {
			super.closeRoom(r.get_id());
		} else if (actors.size() == START_COUNT) {
			uc.setStatus(SchoolWar.STATUS_START);
			uc.setSt(System.currentTimeMillis());
			nextFight(r.get_id(), uc, null);
		} else {
			checkRoom(r, actors);
		}
		return true;
	}

	/** 开始打架 */
	private void nextFight(long roomId, SchoolWar uc, Boolean miniTime) {
		int extraTime = extraTime(uc.getSt());
		if (extraTime <= 1) {
			end(roomId, uc, null, END_NORMAL);
			return;
		}
		int time = 0;
		if (null != miniTime && miniTime) {
			time = fightMiniMinTime();
		} else if (uc.getTurnBack() == HEADER_LEFT_LIE || uc.getTurnBack() == HEADER_RIGHT_LIE) {
			time = fightMinTime();
		} else {
			time = fightMaxTime();
		}
		uc.setTurnBack(0);
		if (extraTime <= time) {
			addJob(roomId, extraTime - 1);
		} else {
			addJob(roomId, time);
		}
		for (Entry<Long, Integer> actor : uc.getActors().entrySet()) {
			uc.getActors().put(actor.getKey(), SchoolWarActor.A_STATUS_FIGHT);
		}
		super.saveActivity(roomId, uc);
		MapBody mb = new MapBody(SocketHandler.SCHOOL_FIGHT, SocketHandler.FN_ROOM_ID, roomId)
				.append(EXTRATIME, extraTime).append("actors", uc.getActors()).append(SocketHandler.FN_LIMIT, time);
		pubRoomMsg(roomId, MsgType.SCHOOL_WAR, mb);
	}

	/** 老师虚影 */
	private void turnBackLie(long roomId, SchoolWar uc) {
		int extraTime = extraTime(uc.getSt());
		if (extraTime <= TIME_TURN_BACK) {
			end(roomId, uc, null, END_NORMAL);
			return;
		}
		int teacher = getTurnBackLie();
		if (teacher > 0) {
			uc.setTurnBack(teacher);
			super.saveActivity(roomId, uc);
			addJob(roomId, TIME_TURN_BACK + RandomUtil.nextInt(3));
			MapBody mb = new MapBody(SocketHandler.SCHOOL_TURNBACK, SocketHandler.FN_ROOM_ID, roomId)
					.append(EXTRATIME, extraTime).append(TURNBACK_MAN, teacher)
					.append(SocketHandler.FN_LIMIT, TIME_TURN_BACK);
			pubRoomMsg(roomId, MsgType.SCHOOL_WAR, mb);
		} else {
			nextFight(roomId, uc, true);
		}
	}

	/** 老师回头 */
	private void turnBack(long roomId, SchoolWar uc) {
		int extraTime = extraTime(uc.getSt());
		if (extraTime <= TIME_TURN_BACK) {
			end(roomId, uc, null, END_NORMAL);
			return;
		}
		int teacher = getTurnBack();

		if (teacher > 0) {
			uc.setTurnBack(teacher);
			super.saveActivity(roomId, uc);
			addJob(roomId, TIME_TURN_BACK);
			MapBody mb = new MapBody(SocketHandler.SCHOOL_TURNBACK, SocketHandler.FN_ROOM_ID, roomId)
					.append(EXTRATIME, extraTime).append(TURNBACK_MAN, teacher)
					.append(SocketHandler.FN_LIMIT, TIME_TURN_BACK);
			pubRoomMsg(roomId, MsgType.SCHOOL_WAR, mb);
		} else {
			nextFight(roomId, uc, true);
		}
	}

	/** 是否需要进入罚站阶段 */
	private void turnUp(long roomId, SchoolWar uc) {
		if (uc.getStandup() == 0) {
			nextFight(roomId, uc, null);
		} else {
			startStandup(roomId, uc);
		}
	}

	/** 开始罚站阶段 */
	private void startStandup(long roomId, SchoolWar uc) {
		uc.setStandup(0);
		super.saveActivity(roomId, uc);
		int extraTime = extraTime(uc.getSt());
		if (extraTime <= 1) {
			end(roomId, uc, null, END_NORMAL);
			return;
		}
		if (extraTime <= TIME_STANDUP) {
			addJob(roomId, extraTime - 1);
		} else {
			addJob(roomId, TIME_STANDUP);
		}
		for (Entry<Long, Integer> actor : uc.getActors().entrySet()) {
			if (SchoolWarActor.A_STATUS_STANDUP != actor.getValue()) {
				uc.getActors().put(actor.getKey(), SchoolWarActor.A_STATUS_LAUGH);
			}
		}
		super.saveActivity(roomId, uc);
		MapBody mb = new MapBody(SocketHandler.SCHOOL_STANDUP, SocketHandler.FN_ROOM_ID, roomId)
				.append(EXTRATIME, extraTime).append("actors", uc.getActors())
				.append(SocketHandler.FN_LIMIT, TIME_STANDUP);
		pubRoomMsg(roomId, MsgType.SCHOOL_WAR, mb);
	}

	/** 打 */
	public ReMsg fighting(long uid, long roomId, int way) {
		SchoolWar uc = super.getActivity(roomId, SchoolWar.class);
		if (uc.getStatus() != SchoolWar.STATUS_START) {
			return new ReMsg(ReCode.FAIL);
		}
		if (null == uc.getActors().get(uid) || uc.getActors().get(uid) != SchoolWarActor.A_STATUS_FIGHT) {
			return new ReMsg(ReCode.FAIL);
		}
		uc.putScore(uid, uc.getScore(uid) + SCORE_FIGHT);
		super.saveActivity(roomId, uc);
		MapBody mb = new MapBody(SocketHandler.SCHOOL_FIGHTING, SocketHandler.FN_ROOM_ID, roomId)
				.append(EXTRATIME, extraTime(uc.getSt())).append("actors", uc.getActors())
				.append(SocketHandler.FN_USER_ID, uid).append("score", uc.getScores()).append("way", way);
		super.pubRoomMsg(roomId, MsgType.SCHOOL_WAR, mb);
		if (uc.getTurnBack() == TEACHER || uc.getTurnBack() == HEADER_LEFT_TRUE
				|| uc.getTurnBack() == HEADER_RIGHT_TRUE) {
			if (uc.getActors().get(uid) != SchoolWarActor.A_STATUS_STANDUP) {
				uc.putActor(uid, SchoolWarActor.A_STATUS_STANDUP);
				uc.setStandup(uc.getStandup() + 1);
				super.saveActivity(roomId, uc);
				mb = new MapBody(SocketHandler.SCHOOL_STANDING, SocketHandler.FN_ROOM_ID, roomId)
						.append(SocketHandler.FN_USER_ID, uid).append(SocketHandler.FN_LIMIT, TIME_TURN_BACK)
						.append(EXTRATIME, extraTime(uc.getSt())).append("actors", uc.getActors())
						.append("teacher", uc.getTurnBack());
				super.pubRoomMsg(roomId, MsgType.SCHOOL_WAR, mb);

			}
		}
		return new ReMsg(ReCode.OK);
	}

	/** 嘲讽 */
	public ReMsg laughing(long uid, long roomId) {
		SchoolWar uc = super.getActivity(roomId, SchoolWar.class);
		Set<SchoolWarActor> uas = super.getRoomActors(roomId, SchoolWarActor.class);
		for (SchoolWarActor actor : uas) {
			if (uid != actor.getUid()) {
				uc.putScore(actor.getUid(),
						uc.getScore(actor.getUid()) - SCORE_LAUGH > 0 ? uc.getScore(actor.getUid()) - SCORE_LAUGH : 0);
				super.saveActivity(roomId, uc);
				MapBody mb = new MapBody(SocketHandler.SCHOOL_LAUGHING, SocketHandler.FN_ROOM_ID, roomId)
						.append(EXTRATIME, extraTime(uc.getSt())).append("actors", uc.getActors())
						.append(SocketHandler.FN_USER_ID, uid).append("score", uc.getScores());
				super.pubRoomMsg(roomId, MsgType.SCHOOL_WAR, mb);
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	/** 结束结算 */
	private void end(final long roomId, SchoolWar uc, Long uid, int endType) {
		if (endType == END_FAIL) {// 开启失败
			MapBody mb = new MapBody(SocketHandler.SCHOOL_END, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_LIMIT, TIME_END).append("endType", endType);
			super.pubRoomMsg(roomId, MsgType.SCHOOL_WAR, mb);
			return;
		}
		if (uc == null)
			uc = super.getActivity(roomId, SchoolWar.class);
		if (uc.getStatus() != SchoolWar.STATUS_START) {
			return;
		}
		uc.setStatus(SchoolWar.STATUS_END);
		super.saveActivity(roomId, uc);
		Set<SchoolWarActor> uas = super.getRoomActors(roomId, SchoolWarActor.class);
		int winReward = COIN_DEFAULT;
		int lostReward = COIN_DEFAULT;
		if (null != uid && uid != 0) {
			for (SchoolWarActor sa : uas) {
				if (sa.getUid() == uid) {
					uc.setLoser(sa.getUid());
				} else {
					uc.setWinner(sa.getUid());
				}
			}
		} else {
			for (SchoolWarActor sa : uas) {
				if (uc.getWinner() == 0) {
					uc.setWinner(sa.getUid());
				} else {
					if (uc.getScore(sa.getUid()) > uc.getScore(uc.getWinner())) {
						uc.setLoser(uc.getWinner());
						uc.setWinner(sa.getUid());
					} else {// 强行胜出
						if (uc.getScore(sa.getUid()) == uc.getScore(uc.getWinner())) {
							uc.putScore(uc.getWinner(), uc.getScore(uc.getWinner()) + 1);
							MapBody mb = new MapBody(SocketHandler.SCHOOL_FIGHTING, SocketHandler.FN_ROOM_ID, roomId)
									.append(EXTRATIME, 0).append("actors", uc.getActors())
									.append(SocketHandler.FN_USER_ID, uc.getWinner()).append("score", uc.getScores())
									.append("way", 1);
							super.pubRoomMsg(roomId, MsgType.SCHOOL_WAR, mb);
						}
						uc.setLoser(sa.getUid());
					}
				}
			}
		}
		if (gameCPScoreService.isFriend(uc.getWinner(), uc.getLoser())) {
			uc.setModel(Const.ACTIVITY_INVITE);
		} else {
			uc.setModel(Const.ACTIVITY_MATCH);
		}
		if (uc.getModel() == Const.ACTIVITY_INVITE) {
			if (null != uid && uid != 0) {
				winReward = 0;
			}
		} else {
			if (null != uid && uid != 0) {
				winReward = 10;
				lostReward = 30;
			} else {
				int score = gameCPScoreService.getUserScore(uc.getWinner(), ActivityType.SCHOOL_WAR.getType(),
						GameCPScore.TIMETYPE_WEEK);
				int[] winRecords = new int[] { uc.getScore(uc.getWinner()), score };
				score = gameCPScoreService.getUserScore(uc.getLoser(), ActivityType.SCHOOL_WAR.getType(),
						GameCPScore.TIMETYPE_WEEK);
				int[] lostRecords = new int[] { uc.getScore(uc.getLoser()), score };
				if (winRecords[0] >= 100 || lostRecords[0] >= 100) {// 有玩家大于100分
					int cnt = (winRecords[0] - lostRecords[0]) / 2;
					if (cnt < 10) {
						winReward = 10;
						lostReward = 10;
					} else if (cnt > 50) {
						winReward = 50;
						lostReward = 50;
					} else {
						winReward = cnt;
						lostReward = cnt;
					}
				} else {
					if (winRecords[1] >= lostRecords[1]) {// 积分排名较高者获胜
						winReward = 10;
						lostReward = 10;
					} else {// 积分排名较低者获胜
						winReward = 20;
						lostReward = 20;
					}
				}
			}
			if (winReward > 0 && uc.getWinner() > 0) {
				gameCPScoreService.changeScore(uc.getWinner(), ActivityType.SCHOOL_WAR.getType(), winReward);
			}
			if (lostReward > 0 && uc.getLoser() > 0) {
				gameCPScoreService.changeScore(uc.getLoser(), ActivityType.SCHOOL_WAR.getType(), 0 - lostReward);
			}
		}
		gameCPScoreService.saveGameCPHistory(DboUtil.beanToDBO(uc), ActivityType.SCHOOL_WAR.getType());
		super.saveActivity(roomId, uc);
		super.addJob(roomId, TIME_END);// 5秒展示 结束

		MapBody mb = new MapBody(SocketHandler.SCHOOL_END, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_END).append("model", uc.getModel())
				.append("winner", uc.getWinner()).append("loser", uc.getLoser()).append("winPoint", POINT_WIN)
				.append("lostPoint", POINT_DEFAULT).append("endType", endType);

		userService.changePoint(uc.getWinner(), Point.SCHOOLWAR.getType(), POINT_WIN, 0);
		userService.changePoint(uc.getLoser(), Point.SCHOOLWAR.getType(), POINT_DEFAULT, 0);

		mb.append("winCoin", winReward).append("lostCoin", lostReward);
		if (winReward > 0) {
			coinService.addCoin(uc.getWinner(), CoinLog.SCHOOLWAR, uc.get_id(), winReward, 0, "游戏胜利加币");
		}
		coinService.forceReduce(uc.getLoser(), CoinLog.SCHOOLWAR, uc.get_id(), lostReward, 0, "游戏失败扣币");
		if (uc.getModel() == Const.ACTIVITY_MATCH) {// 匹配模式
			mb.append("winScore", winReward).append("lostScore", lostReward);
		}
		super.pubRoomMsg(roomId, MsgType.SCHOOL_WAR, mb);
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

	/** 退出房间 有人退出即游戏结束 */
	public ReMsg outRoom(long uid, Room r, int type) {
		if (r.getType() != ActivityType.SCHOOL_WAR.getType()) {
			return new ReMsg(ReCode.FAIL);
		}
		SchoolWarActor actor = super.getRoomActor(r.get_id(), uid, SchoolWarActor.class);
		if (null == actor) {
			return new ReMsg(ReCode.ACTOR_ERROR);
		} else {
			SchoolWar uc = super.getActivity(r.get_id(), SchoolWar.class);
			if (uc.getStatus() == SchoolWar.STATUS_START) {
				end(r.get_id(), uc, uid, END_OUTROOM);
			} else if (uc.getStatus() == ActivityCP.STATUS_READY || uc.getStatus() == ActivityCP.STATUS_WAIT) {
				end(r.get_id(), uc, uid, END_FAIL);
			}
			closeRoom(r.get_id());
		}
		return new ReMsg(ReCode.OK);
	}

	@Override
	public ReMsg getActorStatus(long uid, Room r) {
		return null;
	}

	@Override
	public ReMsg canInRoom(final int type, final long actId, final long roomId) {
		return new ReMsg(ReCode.OK, 2);
	}

	public void closeRoom(final long roomId, SchoolWar uc) {
		if (uc.getStatus() == SchoolWar.STATUS_END && uc.getActors().size() == 2) {
			uc.setUpdateTime(System.currentTimeMillis());
			super.getMongoTemplate().save(uc);
		}
		super.closeRoom(roomId);
	}

	/** 小打架时间 */
	private int fightMinTime() {// 回头频率2-5s
		return RandomUtil.nextInt(4) + 2;
	}

	/** 最小打架时间 */
	private int fightMiniMinTime() {// 回头频率1-2s
		return RandomUtil.nextInt(2) + 1;
	}

	/** 大 打架时间 */
	private int fightMaxTime() {// 回头频率3-6s
		return RandomUtil.nextInt(4) + 3;
	}

	/** 距离结束时间 秒 */
	private int extraTime(long st) {
		long time = System.currentTimeMillis() - st;
		if (time < 1000) {
			return TIME_TOTAL;
		}
		return (int) (TIME_TOTAL - (System.currentTimeMillis() - st) / 1000) - 1;
	}

	/** 老师回头 */
	public int getTurnBack() {
		if (RandomUtil.nextInt(100) < 70) {// 真回头 7/10概率
			return RandomUtil.nextInt(3) + 1; // 真回头
		}
		return 0;
	}

	/** 老师回头 */
	public int getTurnBackLie() {
		if (RandomUtil.nextInt(100) < 70) {// 真回头 7/10概率
			return RandomUtil.nextInt(2) + 4; // 虚影
		}
		return 0;
	}
}
