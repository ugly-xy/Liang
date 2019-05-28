package com.zb.service.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hankcs.hanlp.HanLP;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.Activity;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.activity.DrawGuessLog;
import com.zb.models.room.activity.DrawLog;
import com.zb.models.room.activity.DrawLogData;
import com.zb.models.room.activity.DrawSomething;
import com.zb.models.room.activity.DrawSomethingActor;
import com.zb.models.room.activity.GuessWord;
import com.zb.models.usertask.Task;
import com.zb.service.room.vo.RoomInfo;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class DrawSomethingService extends RoomService {
	static final Logger log = LoggerFactory.getLogger(DrawSomethingService.class);

	private static final int START_MIN_COUNT = 4;
	private static final int START_MAX_COUNT = 6;
	private static final int TIME_START = 10;// 10;
	private static final int TIME_SELECT_WORD = 25;
	private static final int TIME_RESELECT_WORD = 10;
	private static final int TIME_END = 4;// 5;
	private static final int TIME_PUNISHED = 1;// 40;
	private static final int TIME_DRAWING = 70;// 30;
	private static final int TIME_TEMPLATE = 3;
	// private static final int TIME_NOW_START = 1;

	// private static final int COIN_WIN = 50;
	// private static final int COIN_DEF = 10;
	private static final int COIN_LOST = 50;
	// private static final int OUT_LINE_COIN = 50;
	private static final int OUT_COIN = 50;
	private static final int COIN_REWORD = 0;
	private static final int COIN_REWORD_INC = 2;

	private static final int POINT_WIN = 100;
	// private static final int POINT_END = 20;
	private static final int POINT_LOST = 10;
	// 加0为了适应sort sort=1 取 FOUR_ROOM[1]
	private static final int[] FOUR_ROOM = new int[] { 0, 50, 20, -20, -50 };
	private static final int[] FIVE_ROOM = new int[] { 0, 60, 30, 10, -30, -50 };
	private static final int[] SIX_ROOM = new int[] { 0, 80, 30, 10, -30, -40, -50 };

	@Override
	public ReMsg handle(Room room, long fr) {
		if (room.getType() != ActivityType.DRAW_SOMETHING.getType()) {
			log.error(room.get_id() + " " + room.getType());
			return new ReMsg(ReCode.FAIL);
		}
		try {
			DrawSomething uc = super.getActivity(room.get_id(), DrawSomething.class);
			if (DrawSomething.STATUS_READY == uc.getStatus()) {
				start(room, uc);
			} else if (DrawSomething.STATUS_SELECT_WORD == uc.getStatus()) {
				// 用户没有选择的情况下直接进入下一局
				nextInnting(room, uc);
			} else if (DrawSomething.STATUS_DRAWING == uc.getStatus()) {
				inningEnd(room, uc);
			} else if (DrawSomething.STATUS_SHOW == uc.getStatus()) {
				nextInnting(room, uc);
			} else if (DrawSomething.STATUS_END == uc.getStatus()) {
				// restart(room, fr);
				return punished(room, uc);
			} else if (DrawSomething.STATUS_PUNISH == uc.getStatus()) {
				restart(room, fr, uc);
			}
		} catch (Exception e) {
			log.error("调用任务roomId=" + room.get_id() + "\n", e);
			this.addJob(room.get_id(), TIME_START);
		}
		return new ReMsg(ReCode.FAIL);
	}

	// public ReMsg inRoom(final long uid, Room r) {
	// return inRoom(uid, r, false);
	// }

	public ReMsg inRoom(final long uid, Room r, int model, boolean robit) {
		RoomInfo ri = new RoomInfo(ActivityType.DRAW_SOMETHING.getType());
		if (r == null || r.getType() != ActivityType.DRAW_SOMETHING.getType()) {
			log.error(r.get_id() + " " + r.getType());
			ri.setCode(1);
			return new ReMsg(ReCode.FAIL, ri);
		}
		DrawSomethingActor ua = super.getRoomActor(r.get_id(), uid, DrawSomethingActor.class);
		if (ua != null) {// 恢复重连
			ua.setStatus(Actor.STATUS_ONLINE);
		} else {// 进入房间
			DBObject user = userService.findById(uid);
			if (user == null) {
				ri.setCode(1);
				return new ReMsg(ReCode.FAIL, ri);
			}
			ua = new DrawSomethingActor();
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
			if (Room.ACTIVITY == r.getStatus()) {
				ua.setRole(Actor.ROLE_VIEWER);
			}
			if (null != r.getBuyUid() && uid == r.getBuyUid()) {// 房间购买人设置最高权限
				ua.setBuyUser(true);
			}
			ua.setGame("你画我猜");
		}
		DrawSomething uc = super.getActivity(r.get_id(), DrawSomething.class);
		if (ua.isBuyUser() && (null == uc.getOwner() || uc.getOwner() != ua.getUid())) {// 房主不是房管
			if (null != uc.getOwner()) {// 原房管不为空
				DrawSomethingActor actor = super.getRoomActor(r.get_id(), uc.getOwner(), DrawSomethingActor.class);
				if (null != actor) {
					actor.setOwner(false);
					super.saveRoomActor(r.get_id(), actor);
				}
			}
			ua.setOwner(true);
			uc.setOwner(uid);
			saveActivity(r.get_id(), uc);
		}
		super.saveRoomActor(r.get_id(), ua);

		Set<DrawSomethingActor> as = super.getRoomActors(r.get_id(), DrawSomethingActor.class);
		super.changeRoomCount(r, as, T_INROOM);
		
		if (r.getStatus() == Room.OPEN) {
			ri.setStatus(Room.OPEN);
		} else {
			// DrawSomething uc = super.getActivity(r.get_id(),
			// DrawSomething.class);
			ri.setWord(uc.getTip());
			if (uc.getStatus() == DrawSomething.STATUS_PUNISH) {
				ri.setStatus(3);
			} else {
				ri.setStatus(Room.ACTIVITY);
			}
		}

		if (r.getUid() != 0 && (null == uc.getOwner() || uc.getOwner() == 0)) {
			for (DrawSomethingActor a : as) {
				uc.setOwner(a.getUid());
				a.setOwner(true);
				super.saveRoomActor(r.get_id(), a);
				super.saveActivity(r.get_id(), uc);
				break;
			}
		}
		ri.setPub(r.isPub());
		ri.setHasOwner(r.getUid() == 0 ? false : true);
		ri.setActors(as);
		ri.setSys(r.isSys());// 系统房间或包间
		MapBody mb = new MapBody(SocketHandler.IN_ROOM, SocketHandler.FN_USER_ID, ua.getUid())
				.append("nickname", ua.getNickname()).append("sex", ua.getSex()).append("avatar", ua.getAvatar())
				.append("actors", as).append("province", ua.getProvince()).append("city", ua.getCity())
				.append("role", ua.getRole());
		long dt = System.currentTimeMillis();
		for (Actor a : as) {
			if (uid != a.getUid()) {
				super.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.ROOM, mb, dt);
			}
			// else{
			// log.error("UAS---ROOMID:"+r.get_id()+" uid:"+uid+"
			// vip:"+a.getVip());
			// }
		}
		return new ReMsg(ReCode.OK, ri);
	}

	public synchronized ReMsg ready(final long uid, final long roomId) {
		DrawSomething uc = getActivity(roomId, DrawSomething.class);
		if (DrawSomething.STATUS_READY == uc.getStatus()) {// 房间在准备状态下，才能准备
			DrawSomethingActor a = super.getRoomActor(roomId, uid, DrawSomethingActor.class);
			if (a != null && Actor.A_STATUS_IN_ROOM == a.getUcStatus()) {
				if (!a.isRobit()) {
					DBObject user = super.userService.findById(uid);
					if (user == null || DboUtil.tryGetInt(user, "coin") < COIN_LOST) {
						return new ReMsg(ReCode.COIN_BALANCE_LOW);
					}
				}

				a.setUcStatus(Actor.A_STATUS_READY);
				super.saveRoomActor(roomId, a);
				uc.putActor(uid, DrawSomething.DRAW_INIT);
				super.saveActivity(roomId, uc);

				Set<DrawSomethingActor> actors = getRoomActors(roomId, DrawSomethingActor.class);
				MapBody mb = new MapBody(SocketHandler.ACTIVITY_READY, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", uc.get_id());
				boolean start = false;

				int readyCount = uc.getActors().size();
				long total = actors.size();

				if (readyCount >= START_MAX_COUNT) {// 准备人数超过最大游戏数，立即开始
					start = true;
				} else if (readyCount >= START_MIN_COUNT) {// 准备人数超过最小人数
					if (readyCount == total) {// 全体准备
						start = true;
					} else {// 还有未准备用户
						addJob(roomId, TIME_START);// 增加系统调用倒计时
						mb.append(SocketHandler.FN_LIMIT, TIME_START);
					}
				}
				super.pubRoomMsg(roomId, MsgType.DRAWSOMETHING, mb);// 发送用户准备通知
				if (start) {
					this.addJob(roomId, TIME_TEMPLATE);
				}
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	private boolean start(Room r, DrawSomething uc) {
		Set<DrawSomethingActor> actors = super.getRoomActors(r.get_id(), DrawSomethingActor.class);
		int readyCount = uc.getActors().size();
		// log.error(r.get_id() + "=========readyCount:" + readyCount);
		boolean start = false;
		if (readyCount >= START_MIN_COUNT) {// 准备人数超过最小人数
			start = true;
		} else {
			checkReadyRoom(r, readyCount, TIME_START);
		}

		if (start) {
			// long activityNo = super.incrId(RK.activityId());
			addJob(r.get_id(), TIME_SELECT_WORD);// 开始
			r.setStatus(Room.ACTIVITY);
			r.setActivityDate(System.currentTimeMillis());
			// this.updateRoom(r);
			// uc.set_id(activityNo);
			Set<Long> kickIds = new TreeSet<Long>();
			uc.clearActors();
			int kickCount = readyCount - START_MAX_COUNT;
			// log.error(r.get_id() + "====1=====kickCount:" + kickCount);
			List<Long> readyActors = null;
			if (kickCount > 0) {
				readyActors = new ArrayList<>();
			}
			for (DrawSomethingActor actor : actors) {
				if (Actor.A_STATUS_READY == actor.getUcStatus()) {
					actor.setUcStatus(DrawSomethingActor.A_STATUS_START);
					if (kickCount > 0) {
						if (actor.isRobit()) {
							kickIds.add(actor.getUid());
							kickCount--;
						}
					}
					if (kickCount > 0 && !actor.isRobit()) {
						readyActors.add(actor.getUid());
					}
				} else {
					kickIds.add(actor.getUid());
				}
			}
			int i = 0;
			while (kickCount > 0) {
				i++;
				// log.error(r.get_id() + "=====4====kickCount:" +
				// (readyActors.size() - i));
				kickIds.add(readyActors.get(readyActors.size() - i));
				kickCount--;
			}
			for (DrawSomethingActor actor : actors) {
				if (kickIds.contains(actor.getUid())) {
					super.delRoomActor(r.get_id(), actor.getUid());
					uc.removeActor(actor.getUid());
				} else {
					actor.setUcStatus(DrawSomethingActor.A_STATUS_START);
					saveRoomActor(r.get_id(), actor);
					uc.putActor(actor.getUid(), DrawSomething.DRAW_INIT);
				}
			}
			pubWord(r, uc, actors, kickIds);
			changeRoomCount(r, super.getRoomActors(r.get_id(), DrawSomethingActor.class), T_START);
		}
		return start;
	}

	private void pubWord(Room r, DrawSomething uc, Set<DrawSomethingActor> actors, Set<Long> kickIds) {
		if (actors == null) {
			actors = super.getRoomActors(r.get_id(), DrawSomethingActor.class);
		}
		Map<Long, Integer> as = uc.getActors();
		DrawSomethingActor actor = null;
		for (long cuid : as.keySet()) {
			if (DrawSomething.DRAW_INIT == as.get(cuid)) {
				actor = super.getRoomActor(r.get_id(), cuid, DrawSomethingActor.class);
				if (actor != null && actor.getStatus() == DrawSomethingActor.STATUS_ONLINE) {
					break;
				} else {
					uc.putActor(cuid, DrawSomething.NOT_DRAW);
					actor = null;
				}
			} else if (DrawSomething.DRAWING == as.get(cuid)) {
				uc.putActor(cuid, DrawSomething.DRAWED);
			}
		}
		if (actor != null) {
			uc.setStatus(DrawSomething.STATUS_SELECT_WORD);
			// uc.setInning(uc.getInning() + 1);
			Map<String, String> gwords = getWords(uc);
			uc.putActor(actor.getUid(), DrawSomething.DRAWING);
			uc.setGws(gwords);
			Set<DrawSomethingActor> curActors = new TreeSet<DrawSomethingActor>();
			for (DrawSomethingActor a : actors) {// 发送房间内
				if (uc.getActors().containsKey(a.getUid())) {
					if (a.isDraw()) {
						a.setDraw(false);
					} else if (a.getUid() == actor.getUid()) {
						a.setDraw(true);
					}
					a.setUcStatus(DrawSomethingActor.A_STATUS_SELECT_WORD);
					super.saveRoomActor(r.get_id(), a);
					curActors.add(a);
				} else {
					curActors.add(a);
				}
			}

			long curTime = System.currentTimeMillis();
			MapBody mb2 = new MapBody(SocketHandler.DRAWSOMETHING_WORD_PUB, SocketHandler.FN_ROOM_ID, r.get_id())
					.append(SocketHandler.FN_LIMIT, TIME_SELECT_WORD).append("inning", uc.getInning())
					.append("actors", curActors).append("kicks", kickIds)
					.append(SocketHandler.FN_USER_ID, actor.getUid());
			for (DrawSomethingActor a : actors) {// 发送房间内
				if (a.getStatus() == DrawSomethingActor.STATUS_ONLINE) {
					if (a.getUid() == actor.getUid()) {
						mb2.append("words", gwords.keySet()).append("rePubCoin",
								(COIN_REWORD_INC * uc.getReWordCnt()) + COIN_REWORD);
					}
					this.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.DRAWSOMETHING, mb2, curTime);
				}
			}
		} else {
			end(r.get_id(), uc);
		}
		super.saveActivity(r.get_id(), uc);
	}

	public void rePubWord(final long roomId, final long uid) {
		DrawSomething uc = super.getActivity(roomId, DrawSomething.class);
		if (uc != null) {
			if (uc.getActors().get(uid) == DrawSomething.DRAWING) {
				ReCode r = coinService.reduce(uid, CoinLog.DRAW_SOMETHING, uc.get_id(),
						(COIN_REWORD_INC * uc.getReWordCnt()) + COIN_REWORD, 0L, "重新发布词");
				if (ReCode.OK.reCode() == r.reCode()) {
					Map<String, String> gwords = getWords(uc);
					uc.setGws(gwords);
					uc.setReWordCnt(uc.getReWordCnt() + 1);
					super.saveActivity(roomId, uc);
					MapBody mb = new MapBody(SocketHandler.DRAWSOMETHING_WORD_REPUB, SocketHandler.FN_ROOM_ID, roomId);
					Double et = super.getJobEndTime(roomId);
					if (et != null) {
						long t = Math.round(et) - System.currentTimeMillis();
						if (t < TIME_RESELECT_WORD * 1000) {
							mb.append(SocketHandler.FN_LIMIT, TIME_RESELECT_WORD);
							super.addJob(roomId, TIME_RESELECT_WORD);
						}
					}

					Set<DrawSomethingActor> actors = super.getRoomActors(roomId, DrawSomethingActor.class);
					for (DrawSomethingActor actor : actors) {
						if (uid == actor.getUid()) {
							mb.append("words", gwords.keySet()).append("rePubCoin",
									(COIN_REWORD_INC * uc.getReWordCnt()) + COIN_REWORD);
						} else {
							if (mb.contains("words")) {
								mb.remove("words").remove("rePubCoin");//
							}
						}
						this.pubRoomUserMsg(roomId, actor.getUid(), MsgType.DRAWSOMETHING, mb,
								System.currentTimeMillis());
					}
				} else {
					MapBody mb2 = new MapBody("code", 1).append("title", "金币不足").append("body", "金币余额不足，无法换词！")
							.append("prompt", SocketHandler.PROMPT_LOW_COIN);
					;
					this.pubRoomUserMsg(roomId, uid, MsgType.PROMPT, mb2, System.currentTimeMillis());
				}
			}
		}
	}

	public ReMsg selectWord(long uid, final long roomId, String word) {
		DrawSomething uc = super.getActivity(roomId, DrawSomething.class);
		if (DrawSomething.STATUS_SELECT_WORD == uc.getStatus()) {
			DrawSomethingActor actor = super.getRoomActor(roomId, uid, DrawSomethingActor.class);
			if (Actor.ROLE_EXECUTOR == actor.getRole() && actor.isDraw()) {// 非死亡的参与者才可以投票
				if (uc.getGws().containsKey(word)) {
					super.addJob(roomId, TIME_DRAWING);
					uc.setWord(word);
					uc.setTip(uc.getGws().get(word));
					uc.setStatus(DrawSomething.STATUS_DRAWING);
					uc.setDlCount(this.countDrawLogs(word, null));
					super.saveActivity(roomId, uc);
					actor.setWord(word);
					actor.setUcStatus(DrawSomethingActor.A_STATUS_DRAWING);
					super.saveRoomActor(roomId, actor);
					MapBody mb = new MapBody(SocketHandler.DRAWSOMETHING_DRAW_START, SocketHandler.FN_USER_ID, uid)
							.append("tip", uc.getGws().get(word)).append("count", word.length())
							.append(SocketHandler.FN_ROOM_ID, roomId).append(SocketHandler.FN_LIMIT, TIME_DRAWING);
					super.pubRoomMsg(roomId, MsgType.DRAWSOMETHING, mb);
					return new ReMsg(ReCode.OK);
				}
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg clear(final long uid, final long roomId) {
		DrawSomething uc = super.getActivity(roomId, DrawSomething.class);
		if (DrawSomething.STATUS_DRAWING == uc.getStatus()) {
			DrawSomethingActor actor = super.getRoomActor(roomId, uid, DrawSomethingActor.class);
			if (Actor.ROLE_EXECUTOR == actor.getRole() && actor.isDraw()) {// 非死亡的参与者才可以投票
				MapBody mb = new MapBody(SocketHandler.DRAWSOMETHING_CLEAR, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId);
				super.pubRoomMsg(roomId, MsgType.DRAWSOMETHING, mb);
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawing(final long uid, final long roomId, @SuppressWarnings("rawtypes") Map json) {
		DrawSomething uc = super.getActivity(roomId, DrawSomething.class);
		if (DrawSomething.STATUS_DRAWING == uc.getStatus()) {
			DrawSomethingActor actor = super.getRoomActor(roomId, uid, DrawSomethingActor.class);
			if (null == actor) {
				return new ReMsg(ReCode.ACTOR_ERROR);
			}
			if (Actor.ROLE_EXECUTOR == actor.getRole() && actor.isDraw()) {// 非死亡的参与者才可以绘画
				uc.setDrawCnt(uc.getDrawCnt() + 1);
				super.saveActivity(roomId, uc);
				if (!actor.isRobit() && uc.getDlCount() < 20) {// 非机器人且日志数少于20
																// 添加绘画记录
					saveDrawLog(json, uc, uid);
				}
				MapBody mb = new MapBody(SocketHandler.DRAWSOMETHING_DRAWING, SocketHandler.FN_USER_ID, uid)
						.append("paint", json).append(SocketHandler.FN_ROOM_ID, roomId);
				super.pubRoomMsg(roomId, MsgType.DRAWSOMETHING, mb);
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	private void saveDrawLog(@SuppressWarnings("rawtypes") Map json, DrawSomething uc, long uid) {
		String id = uc.get_id() + "-" + uc.getInning();
		DBObject dbo = findDrawLogById(id);
		DrawLog dl = null;
		if (dbo == null) {
			@SuppressWarnings("rawtypes")
			List<Map> draws = new ArrayList<Map>();
			draws.add(json);
			dl = new DrawLog(id, uc.getWord(), uid);
			DrawLogData dld = new DrawLogData(id, draws);
			super.getMongoTemplate().save(dl);
			super.getMongoTemplate().save(dld);
		} else {
			super.getC(DocName.DRAW_LOG_DATA).update(new BasicDBObject("_id", id),
					new BasicDBObject("$push", new BasicDBObject("draws", json)));
		}

	}

	public DBObject findDrawLogById(String id) {
		return super.getC(DocName.DRAW_LOG).findOne(new BasicDBObject("_id", id));
	}

	public DBObject findDrawDataById(String id) {
		return super.getC(DocName.DRAW_LOG_DATA).findOne(new BasicDBObject("_id", id));
	}

	// 删除绘画
	public void delDrawLogById(String id) {
		super.getC(DocName.DRAW_LOG).remove(new BasicDBObject("_id", id));
		super.getC(DocName.DRAW_LOG_DATA).remove(new BasicDBObject("_id", id));
	}

	public Page<DBObject> queryDrawLogs(Long uid, String word, Integer status, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);

		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(word)) {
			q.put("word", word);
		}
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}
		DBCursor dbc = getC(DocName.DRAW_LOG).find(q);
		int count = dbc.count();
		List<DBObject> dbos = dbc.skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("updateTime", -1)).toArray();
		return new Page<DBObject>(count, size, page, dbos);
	}

	// 获取绘画数据
	@SuppressWarnings("rawtypes")
	public List drawData(String w) {
		DBCursor dbc = super.getC(DocName.DRAW_LOG)
				.find(new BasicDBObject("word", w).append("status", Const.STATUS_OK));
		int count = dbc.count();
		if (count > 0) {
			int i = RandomUtil.nextInt(count);
			DBObject dbo = dbc.skip(i).limit(1).toArray().get(0);
			DBObject drawData = this.findDrawDataById(DboUtil.getString(dbo, "_id"));
			return (List) drawData.get("draws");
		}
		return null;
	}

	public int countDrawLogs(String word, Integer status) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(word)) {
			q.put("word", word);
		}

		if (status != null && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.DRAW_LOG).find(q).count();
	}

	public List<DBObject> findDrawLogs(String word, Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(word)) {
			q.put("word", word);
		}

		if (status != null && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.DRAW_LOG).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("updateTime", -1)).toArray();
	}

	/**
	 * 用户猜词
	 * 
	 * @param uid
	 * @param roomId
	 * @param txt
	 * @return
	 */
	public ReMsg guess(long uid, final long roomId, String txt) {
		DrawSomething uc = getActivity(roomId, DrawSomething.class);
		if (DrawSomething.STATUS_DRAWING == uc.getStatus()) {
			DrawSomethingActor actor = super.getRoomActor(roomId, uid, DrawSomethingActor.class);
			if (actor != null) {
				if (Actor.ROLE_EXECUTOR == actor.getRole() && !actor.isDraw()) {// 非死亡的参与者才可以投票
					if (!uc.getRights().contains(uid)) {
						txt = txt.trim();
						MapBody mb = new MapBody(SocketHandler.DRAWSOMETHING_GUESS_END, SocketHandler.FN_USER_ID, uid)
								.append(SocketHandler.FN_ROOM_ID, roomId);
						if (uc.getWord().equals(txt) || uc.getWord().equals(HanLP.convertToSimplifiedChinese(txt))) {
							uc.addRight(uid);
							super.saveActivity(roomId, uc);
							int right = uc.getRights().size();
							int count = uc.getActors().size();
							int point = count + 1;
							if (right == 1) {
								mb.append("first", true);
							} else {
								mb.append("first", false);
								point = count + 1 - right;
							}
							if (right == count - 1) {
								super.addJob(roomId, TIME_TEMPLATE);
							}
							mb.append("word", "").append("point", point);
							int p = actor.addPoint(point);
							mb.append("totalPoint", p);
							if (count - right == 2) {
								Double et = getJobEndTime(roomId);
								if (et != null) {
									int temp = (int) (et - System.currentTimeMillis());
									if (temp >= TIME_START * 1000) {
										super.addJob(roomId, TIME_START);
										mb.append(SocketHandler.FN_LIMIT, TIME_START);
									}
								}
							}
						} else {
							mb.append("word", txt).append("point", 0).append("totalPoint", actor.getCurPoint());
							actor.setTalk(txt);
							if (!actor.isRobit() && txt.length() == uc.getWord().length()) {// 添加绘画记录
								saveDrawGuess(txt, uc);
							}
						}
						super.saveRoomActor(roomId, actor);
						super.pubRoomMsg(roomId, MsgType.DRAWSOMETHING, mb);
						return new ReMsg(ReCode.OK);
					}
				}
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	String[] blacklist = { "逼", "傻", "sb", "你妈", "尼玛", "fuck", "日你", "二货", "cao", "艹", "死", "滾" };

	public void saveDrawGuess(String txt, DrawSomething uc) {
		for (String bw : blacklist) {
			if (txt.toLowerCase().contains(bw)) {
				return;
			}
		}

		DBObject dbo = super.getC(DocName.DRAW_GUESS_LOG)
				.findOne(new BasicDBObject("word", uc.getWord()).append("guess", txt));
		if (dbo == null) {
			DrawGuessLog dl = new DrawGuessLog(super.getNextId(DocName.DRAW_GUESS_LOG), uc.getWord(), txt);
			super.getMongoTemplate().save(dl);
		}
	}

	public DBObject findDrawGuessById(Long id) {
		return super.getC(DocName.DRAW_GUESS_LOG).findOne(new BasicDBObject("_id", id));
	}

	public Page<DBObject> queryDrawGuessLogs(String word, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findGuessLogs(word, page, size);
		int count = countGuessLogs(word);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countGuessLogs(String word) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(word)) {
			q.put("word", word);
		}
		return getC(DocName.DRAW_GUESS_LOG).find(q).count();
	}

	public void delDrawGuessLogById(long id) {
		super.getC(DocName.DRAW_GUESS_LOG).remove(new BasicDBObject("_id", id));
	}

	public List<DBObject> findGuessLogs(String word, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(word)) {
			q.put("word", word);
		}
		return getC(DocName.DRAW_GUESS_LOG).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	private void inningEnd(Room r, DrawSomething uc) {
		if (DrawSomething.STATUS_DRAWING == uc.getStatus()) {
			int point = 0;
			if (uc.getRights().size() < uc.getActors().size() - 1) {
				point = uc.getRights().size() * 2;
			}
			long duid = 0;
			for (Long uid : uc.getActors().keySet()) {
				if (uc.getActors().get(uid) == DrawSomething.DRAWING) {
					duid = uid;
					break;
				}
			}
			if (duid > 0) {
				DrawSomethingActor a = super.getRoomActor(r.get_id(), duid, DrawSomethingActor.class);
				if (a != null) {
					int p = a.addPoint(point);
					super.saveRoomActor(r.get_id(), a);
					super.addJob(r.get_id(), TIME_TEMPLATE);
					uc.setStatus(DrawSomething.STATUS_SHOW);
					super.saveActivity(r.get_id(), uc);
					MapBody mb = new MapBody(SocketHandler.DRAWSOMETHING_INNING_END, SocketHandler.FN_ROOM_ID,
							r.get_id()).append("word", uc.getWord()).append("uid", duid).append("point", point)
									.append("totalPoint", p).append("right", uc.getRights().size());
					super.pubRoomMsg(r.get_id(), MsgType.DRAWSOMETHING, mb);
				}
			}
		}
	}

	private void nextInnting(Room room, DrawSomething uc) {
		super.addJob(room.get_id(), TIME_SELECT_WORD);
		uc.setInning(uc.getInning() + 1);
		uc.setStatus(DrawSomething.STATUS_SELECT_WORD);
		uc.clearRights();
		uc.setReWordCnt(1);
		uc.setDrawCnt(0);
		super.saveActivity(room.get_id(), uc);
		Set<DrawSomethingActor> actors = super.getRoomActors(room.get_id(), DrawSomethingActor.class);
		pubWord(room, uc, actors, null);
	}

	private void end(final long roomId, DrawSomething uc) {
		if (uc == null)
			uc = super.getActivity(roomId, DrawSomething.class);
		uc.setStatus(DrawSomething.STATUS_END);

		Set<DrawSomethingActor> uas = super.getRoomActors(roomId, DrawSomethingActor.class);
		int min = 0;
		int max = 0;
		for (DrawSomethingActor dsa : uas) {
			if (dsa.getRole() == DrawSomethingActor.ROLE_EXECUTOR) {
				if (uc.getWinner().size() == 0) {
					uc.addWinner(dsa.getUid());
					uc.addLoser(dsa.getUid());
					min = dsa.getCurPoint();
					max = dsa.getCurPoint();
				} else {
					if (min == dsa.getCurPoint()) {
						uc.addLoser(dsa.getUid());
					} else if (min > dsa.getCurPoint()) {
						min = dsa.getCurPoint();
						uc.clearLoser();
						uc.addLoser(dsa.getUid());
					}
					if (max == dsa.getCurPoint()) {
						uc.addWinner(dsa.getUid());
					} else if (max < dsa.getCurPoint()) {
						max = dsa.getCurPoint();
						uc.clearWinner();
						uc.addWinner(dsa.getUid());
					}
				}
			}
		}
		if (uc.getWinner().size() == uc.getActors().size()) {
			if (StringUtils.isBlank(uc.getWord())) {
				super.closeRoom(roomId);
				return;
			} else {
				uc.clearWinner();
				uc.clearLoser();
			}
		}
		super.saveActivity(roomId, uc);
		super.addJob(roomId, TIME_END);// 5秒倒计时，5秒后进入进入奖惩阶段

		MapBody mb = new MapBody(SocketHandler.DRAWSOMETHING_END, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_END);
		long dt = System.currentTimeMillis();

		List<DrawSomethingActor> list = new ArrayList<>();
		Map<Long, Integer> sortMap = new HashMap<>();
		ArrayList<String[]> reduceList = new ArrayList<String[]>();
		for (DrawSomethingActor dsa : uas) {
			if (dsa.getRole() == DrawSomethingActor.ROLE_EXECUTOR) {
				list.add(dsa);
			}
		}
		int[] reduceCoin = null;// 金币数组
		if (!list.isEmpty()) {
			if (list.size() == 4) {
				reduceCoin = FOUR_ROOM;
			} else if (list.size() == 5) {
				reduceCoin = FIVE_ROOM;
			} else if (list.size() == 6) {
				reduceCoin = SIX_ROOM;
			} else {
				super.closeRoom(roomId);
				return;
			}
			list = list.stream().sorted((e1, e2) -> {
				return e2.getCurPoint() - e1.getCurPoint();
			}).collect(Collectors.toList());

			for (int i = 1; i <= list.size(); i++) {
				sortMap.put(list.get(i - 1).getUid(), i);
				reduceList.add(new String[] { i + "", list.get(i - 1).getUid() + "", reduceCoin[i] + "" });
			}
		}
		for (DrawSomethingActor dsa : uas) {
			if (dsa.getRole() == DrawSomethingActor.ROLE_EXECUTOR) {
				int sort = sortMap.get(dsa.getUid());
				mb.append(SocketHandler.FN_USER_ID, dsa.getUid()).append("sort", sort).append("list", reduceList);
				if (reduceCoin[sort] > 0) {// 金币结果>0 算 赢币
					mb.append("status", 1).append("coin", reduceCoin[sort]);
					coinService.addCoin(dsa.getUid(), CoinLog.DRAW_SOMETHING, uc.get_id(), reduceCoin[sort], 0,
							"游戏胜利加币");
					userService.changePoint(dsa.getUid(), Point.DRAW_SOMETHING.getType(), POINT_WIN, 0);
				} else if (reduceCoin[sort] < 0) {// 金币结果<0 算输币
					mb.append("status", 3).append("coin", Math.abs(reduceCoin[sort]));
					coinService.forceReduce(dsa.getUid(), CoinLog.DRAW_SOMETHING, uc.get_id(),
							Math.abs(reduceCoin[sort]), 0, "游戏失败扣币");
					userService.changePoint(dsa.getUid(), Point.DRAW_SOMETHING.getType(), POINT_LOST, 0);
				}
				super.pubRoomUserMsg(roomId, dsa.getUid(), MsgType.DRAWSOMETHING, mb, dt);
				// 如果不在线
				if (dsa.getStatus() != DrawSomethingActor.STATUS_ONLINE) {
					// if (dsa.getStatus() == DrawSomethingActor.STATUS_OFFLINE)
					// {// 掉线扣50
					// if (!dsa.isReduceCoin()) {
					// coinService.forceReduce(dsa.getUid(),
					// CoinLog.DRAW_SOMETHING, uc.get_id(), OUT_LINE_COIN, 0,
					// "未完成游戏掉线惩罚扣币");
					// dsa.setReduceCoin(true);
					// }
					// super.saveRoomActor(roomId, dsa);
					// } else
					if (dsa.getStatus() == DrawSomethingActor.STATUS_OUT_ROOM) {// 主动退出房间扣币
						if (!dsa.isReduceCoin()) {
							coinService.forceReduce(dsa.getUid(), CoinLog.DRAW_SOMETHING, uc.get_id(), OUT_COIN, 0,
									"未完成游戏退出房间惩罚扣币");
							dsa.setReduceCoin(true);
						}
						super.saveRoomActor(roomId, dsa);
					}
				}
			}
		}
		// for (DrawSomethingActor dsa : uas) {
		// if (dsa.getRole() == DrawSomethingActor.ROLE_EXECUTOR) {
		// mb.append(SocketHandler.FN_USER_ID, dsa.getUid()).append("sort",
		// sortMap.get(dsa.getUid()));
		// if (dsa.getStatus() == DrawSomethingActor.STATUS_ONLINE) {
		// if (uc.getWinner().contains(dsa.getUid())) {// 胜利
		// mb.append("status", 1).append("coin", COIN_WIN);
		// coinService.addCoin(dsa.getUid(), CoinLog.DRAW_SOMETHING,
		// uc.get_id(), COIN_WIN, 0, "游戏胜利加币");
		// userService.changePoint(dsa.getUid(), Point.DRAW_SOMETHING.getType(),
		// POINT_WIN, 0);
		// } else if (uc.getLoser().contains(dsa.getUid())) {// 失败
		// mb.append("status", 3).append("coin", COIN_LOST);
		// coinService.forceReduce(dsa.getUid(), CoinLog.DRAW_SOMETHING,
		// uc.get_id(), COIN_LOST, 0,
		// "游戏失败扣币");
		// userService.changePoint(dsa.getUid(), Point.DRAW_SOMETHING.getType(),
		// POINT_LOST, 0);
		// } else {
		// mb.append("status", 2).append("coin", COIN_DEF);
		// coinService.addCoin(dsa.getUid(), CoinLog.DRAW_SOMETHING,
		// uc.get_id(), COIN_DEF, 0, "完成游戏加币");
		// userService.changePoint(dsa.getUid(), Point.DRAW_SOMETHING.getType(),
		// POINT_END, 0);
		// }
		// super.pubRoomUserMsg(roomId, dsa.getUid(), MsgType.DRAWSOMETHING, mb,
		// dt);
		// } else {
		// if (!dsa.isReduceCoin()) {
		// coinService.forceReduce(dsa.getUid(), CoinLog.DRAW_SOMETHING,
		// uc.get_id(), OUT_ROOM_COIN, 0,
		// "未完成游戏惩罚扣币");
		// dsa.setReduceCoin(true);
		// }
		// super.saveRoomActor(roomId, dsa);
		// }
		// }
		// }
		// 触发任务
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			for (Actor actor : uas) {
				if (!actor.isRobit()) {
					jobsDone(actor.getUid(), Task.DROWSTH, 1);
				}
			}
			return null;
		});
		try {
			future.get();
		} catch (Exception e) {
			log.error("调用任务draw", e);
		}
		// uc.setUpdateTime(System.currentTimeMillis());
		// super.getMongoTemplate().save(uc);
	}

	private ReMsg punished(Room r, DrawSomething uc) {
		uc.setStatus(DrawSomething.STATUS_PUNISH);
		super.saveActivity(r.get_id(), uc);
		uc.setUpdateTime(System.currentTimeMillis());
		super.getMongoTemplate().save(uc);
		super.addJob(r.get_id(), TIME_PUNISHED);
		Set<String> punish = punishService.getPunish(6);

		MapBody mb = new MapBody(SocketHandler.UNDERCOVER_REWARD, SocketHandler.FN_ROOM_ID, r.get_id())
				.append("uids", uc.getLoser()).append("punish", punish).append(SocketHandler.FN_LIMIT, TIME_PUNISHED);
		super.pubRoomMsg(r.get_id(), MsgType.DRAWSOMETHING, mb);
		return new ReMsg(ReCode.OK);
	}

	public boolean restart(Room r, long fr, DrawSomething uc) {
		Set<DrawSomethingActor> actors = getRoomActors(r.get_id(), DrawSomethingActor.class);
		long owner = 0L;
		if (r.getUid() != 0) {
			if (uc == null) {
				uc = super.getActivity(r.get_id(), DrawSomething.class);
			}
			owner = uc.getOwner();
		}
		super.delActivity(r.get_id());// 删除uc
		Set<DrawSomethingActor> initActors = new TreeSet<DrawSomethingActor>();
		int robitCount = 0;
		for (DrawSomethingActor actor : actors) {
			if (Actor.STATUS_ONLINE != actor.getStatus()) {
				super.delRoomActor(r.get_id(), actor.getUid());
				if (r.getUid() != 0 && actor.getUid() == owner) {
					DrawSomethingActor newOua = getNewOwner(actors);
					if (newOua != null) {
						owner = newOua.getUid();
					}
					// pubOwnerChange(owner, r, false, uc);
				}
			} else {
				actor.setRole(DrawSomethingActor.ROLE_EXECUTOR);
				actor.setStatus(DrawSomethingActor.STATUS_ONLINE);
				actor.setTalk("");
				actor.setUcStatus(DrawSomethingActor.A_STATUS_IN_ROOM);
				actor.setChangeCoin(0);
				actor.setDraw(false);
				actor.clearPoints();
				actor.setWord("");
				initActors.add(actor);
				super.saveRoomActor(r.get_id(), actor);
				if (actor.isRobit()) {
					robitCount++;
				}
			}
		}
		r.setStatus(Room.OPEN);
		r.setCount(initActors.size());
		r.setRobitCount(robitCount);
		super.updateRoom(r, T_RESTART);

		if (r.getUid() != 0) {
			uc = super.getActivity(r.get_id(), DrawSomething.class);// 重新新建一个UC,保存owner
			uc.setOwner(owner);
			super.saveActivity(r.get_id(), uc);
		}
		MapBody mb = new MapBody(SocketHandler.ROOM_RESTART, SocketHandler.FN_ROOM_ID, r.get_id()).append("actors",
				initActors);
		long dt = System.currentTimeMillis();
		for (Actor actor : initActors) {
			super.pubRoomUserMsg(r.get_id(), actor.getUid(), MsgType.ROOM, mb, dt);
		}
		return true;
	}

	public DBObject findWordById(String id) {
		return getC(DocName.GUESS_WORD).findOne(new BasicDBObject("_id", id));
	}

	// 你画我猜猜词
	public Page<DBObject> query(Integer status, String id, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(status, id, page, size);
		int count = count(status, id);
		return new Page<DBObject>(count, size, page, dbos);
	}

	// 你画我猜猜词
	public int count(Integer status, String id) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (StringUtils.isNotBlank(id)) {
			q.put("_id", id);
		}
		return getC(DocName.GUESS_WORD).find(q).count();
	}

	// 你画我猜猜词
	public List<DBObject> find(Integer status, String id, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (StringUtils.isNotBlank(id)) {
			q.put("_id", id);
		}
		return getC(DocName.GUESS_WORD).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public ReMsg adds(String words, String tip, String provider, Long uid) {
		if (null == uid) {
			uid = 0l;
		}
		String[] ww = words.split(";");
		for (String w : ww) {
			upsert(w, tip, Const.STATUS_OK, provider, uid);
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg userAddWord(String word, final String tip) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject dbo = findWordById(word);
		if (dbo == null) {
			return upsert(word, tip, Const.STATUS_DEF, super.getUser("nickname").toString(), uid);
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	public ReMsg upsert(String word, String tip, Integer status, String provider, long uid) {
		word = word.trim();
		word = word.trim();
		GuessWord banner = new GuessWord(word, status, tip, provider, uid);
		getMongoTemplate().save(banner);
		if (Const.STATUS_OK == status && uid > 0) {
			coinService.addCoin(uid, CoinLog.VALIDWORD_OK, CoinLog.DRAW_SOMETHING, 100, 0, "你画我猜<" + word + ">审核通过加币");
			messageService.imMsgHandler(Const.SYSTEM_ID, uid, "您为你画我猜贡献的题目\"" + word + "\"已通过审核,奖励您100金币,感谢您的支持!", null,
					null);
			// messageService.easeMsgHandler(Const.SYSTEM_ID, uid,
			// "您为你画我猜贡献的题目\"" + word + "\"已通过审核,奖励您100金币,感谢您的支持!", null, null);
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg delGressword(String word) {
		super.getC(DocName.GUESS_WORD).remove(new BasicDBObject("_id", word));
		return new ReMsg(ReCode.OK);
	}

	private Map<String, String> getWords(DrawSomething ds) {
		int c = this.count(Const.STATUS_OK, null);
		Map<String, String> words = new HashMap<String, String>();
		if (c > 4) {
			while (words.size() < 4) {
				int t = RandomUtil.nextInt(c);
				List<DBObject> dbos = this.find(Const.STATUS_OK, null, t, 1);
				if (dbos.size() > 0) {
					String word = DboUtil.getString(dbos.get(0), "_id");
					if (!words.containsKey(word) && !ds.getWords().contains(word)) {
						words.put(word, DboUtil.getString(dbos.get(0), "tip"));
					}
				}
			}
		}
		if (words.size() != 4) {
			words.clear();
			words.put("美女", "人");
			words.put("女神", "好看");
			words.put("垃圾", "生活");
			words.put("粑粑", "恶心");
		}
		return words;
	}

	// private Set<DrawSomethingView> getUavs(Set<DrawSomethingActor> uas) {
	// Set<DrawSomethingView> uavs = new TreeSet<DrawSomethingView>();
	// if (uas != null) {
	// for (DrawSomethingActor ua : uas) {
	// if (DrawSomethingActor.ROLE_EXECUTOR == ua.getRole()) {
	// uavs.add(new DrawSomethingView(ua));
	// }
	// }
	// }
	// return uavs;
	// }

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
		if (r.getType() != ActivityType.DRAW_SOMETHING.getType()) {
			log.error(r.get_id() + " " + r.getType());
			return new ReMsg(ReCode.FAIL);
		}
		if (Room.ACTIVITY == r.getStatus() && SocketHandler.KICK == type) {
			return new ReMsg(ReCode.KICK_ERROR);
		}
		DrawSomethingActor actor = super.getRoomActor(r.get_id(), uid, DrawSomethingActor.class);
		DrawSomething uc = super.getActivity(r.get_id(), DrawSomething.class);
		boolean ucChange = false;
		if (null == actor) {
			return new ReMsg(ReCode.ACTOR_ERROR);
		} else {
			if (Room.OPEN == r.getStatus()) {
				if (DrawSomethingActor.A_STATUS_READY == actor.getUcStatus()
						|| DrawSomethingActor.A_STATUS_IN_ROOM == actor.getUcStatus()) {
					if (SocketHandler.KICK == type) {
						if (actor.isBuyUser()) {// 房主不能被踢
							return new ReMsg(ReCode.FAIL);
						}
						try {
							MapBody mb = new MapBody(type).append("nickname",
									super.getRoomActor(r.get_id(), uc.getOwner()).getNickname());
							super.pubUserMsg(uid, MsgType.PROMPT, mb);
						} catch (Exception e) {
							log.error("发送踢人信息", e);
						}
					}
				}
				if (DrawSomethingActor.A_STATUS_READY == actor.getUcStatus()) {
					// DrawSomething uc = super.getActivity(r.get_id(),
					// DrawSomething.class);
					uc.removeActor(uid);
					ucChange = true;
					// super.saveActivity(r.get_id(), uc);
				}
				this.delRoomActor(r.get_id(), uid);
				Set<DrawSomethingActor> as = super.getRoomActors(r.get_id(), DrawSomethingActor.class);
				super.changeRoomCount(r, as, T_OUTROOM);
			} else if (Actor.ROLE_VIEWER == actor.getRole()) {
				this.delRoomActor(r.get_id(), actor.getUid());
				Set<DrawSomethingActor> as = super.getRoomActors(r.get_id(), DrawSomethingActor.class);
				super.changeRoomCount(r, as, T_OUTROOM);
			} else {
				// DrawSomething uc = super.getActivity(r.get_id(),
				// DrawSomething.class);
				if (SocketHandler.DISCONNECT != type) {
					if (DrawSomething.STATUS_PUNISH == uc.getStatus()) {
						if (!actor.isReduceCoin()) {
							coinService.forceReduce(uid, CoinLog.DRAW_SOMETHING, uc.get_id(), OUT_COIN, 0,
									"未完成游戏退出房间惩罚扣币");
							actor.setReduceCoin(true);
						}
						super.delRoomActor(r.get_id(), uid);
						uc.removeActor(uid);
						ucChange = true;
						// super.saveActivity(r.get_id(), uc);
					} else {
						actor.setStatus(Actor.STATUS_OUT_ROOM);
					}
				} else {
					// if (DrawSomething.STATUS_PUNISH == uc.getStatus())
					// {//惩奖阶段掉线
					// if (!actor.isReduceCoin()) {
					// coinService.forceReduce(uid, CoinLog.DRAW_SOMETHING,
					// uc.get_id(), OUT_LINE_COIN, 0,
					// "未完成游戏掉线惩罚扣币");
					// actor.setReduceCoin(true);
					// }
					// }
					actor.setStatus(Actor.STATUS_OFFLINE);
				}
				this.saveRoomActor(r.get_id(), actor);
			}
			MapBody mb = new MapBody(type, SocketHandler.FN_USER_ID, actor.getUid())
					.append("nickname", actor.getNickname()).append(SocketHandler.FN_ROOM_ID, r.get_id());
			this.pubRoomMsg(r.get_id(), MsgType.ROOM, mb);
			if (r.getUid() != 0 && SocketHandler.DISCONNECT != type) {
				Long owner = uc.getOwner();
				if (uid == owner) {
					Set<DrawSomethingActor> as = super.getRoomActors(r.get_id(), DrawSomethingActor.class);
					DrawSomethingActor newOwn = this.getNewOwner(as);
					if (newOwn != null) {
						pubOwnerChange(uid, newOwn, r, uc);
						ucChange = false;
					}
				}
			}
		}
		if (ucChange) {
			super.saveActivity(r.get_id(), uc);
		}
		int outCount = 0;
		Set<DrawSomethingActor> actors = super.getRoomActors(r.get_id(), DrawSomethingActor.class);
		for (DrawSomethingActor ua : actors) {
			if (ua.getStatus() != DrawSomethingActor.STATUS_ONLINE) {
				outCount++;
			}
		}
		if (outCount == actors.size()) {
			super.closeRoom(r.get_id());
		}
		return new ReMsg(ReCode.OK);
	}

	// @Override
	// public ReMsg userFastCard(long uid, Room r) {
	// // DrawSomething uc = super.getActivity(r.get_id(),
	// // DrawSomething.class);
	// // if (uc.getStatus() == DrawSomething.STATUS_PUNISH) {
	// // ReCode rc = fastCard(uid, uc.get_id());
	// // if (rc.reCode() == ReCode.OK.reCode()) {
	// // this.restart(r, uid,uc);
	// // }
	// // return new ReMsg(rc);
	// // }
	// return new ReMsg(ReCode.CAN_NOT_USE_FAST_CARD);
	// }

	@Override
	public ReMsg getActorStatus(long uid, Room r) {
		DrawSomethingActor uca = super.getRoomActor(r.get_id(), uid, DrawSomethingActor.class);
		if (uca == null) {
			return new ReMsg(ReCode.OK, 1);
		} else {
			DrawSomething uc = super.getActivity(r.get_id(), DrawSomething.class);
			if (uc.getStatus() == DrawSomething.STATUS_PUNISH) {
				return new ReMsg(ReCode.OK, 1);
			} else {
				return new ReMsg(ReCode.OK, 2);
			}
		}
	}

	@Override
	public ReMsg canInRoom(final int type, final long actId, final long roomId) {
		DrawSomething uc = super.getActivity(roomId, DrawSomething.class);
		if (actId == uc.get_id()) {
			if (uc.getStatus() > Activity.STATUS_READY && uc.getStatus() < DrawSomething.STATUS_PUNISH)
				return new ReMsg(ReCode.OK, 1);
		}
		return new ReMsg(ReCode.OK, 2);
	}

	public void drawLogValidOK(String id) {
		super.getC(DocName.DRAW_LOG).update(new BasicDBObject("_id", id),
				new BasicDBObject("$set", new BasicDBObject("status", Const.STATUS_OK)));
	}

	public void drawGuessLogValid(Long id, int status) {
		super.getC(DocName.DRAW_GUESS_LOG).update(new BasicDBObject("_id", id),
				new BasicDBObject("$set", new BasicDBObject("status", status)));
	}

	public Page<DBObject> queryDrawGuessLogs(String word, Integer status, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findGuessLogs(word, status, page, size);
		int count = countGuessLogs(word, status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countGuessLogs(String word, Integer status) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(word)) {
			q.put("word", word);
		}
		if (status != null && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.DRAW_GUESS_LOG).find(q).count();
	}

	public List<DBObject> findGuessLogs(String word, Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(word)) {
			q.put("word", word);
		}
		if (status != null && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.DRAW_GUESS_LOG).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public ReMsg validGressword(final String word, final Integer status) {
		super.getC(DocName.GUESS_WORD).update(new BasicDBObject("_id", word),
				new BasicDBObject("$set", new BasicDBObject("status", status)));
		DBObject object = super.getC(DocName.GUESS_WORD).findOne(new BasicDBObject("_id", word));
		// 增加100金币
		GuessWord words = DboUtil.toBean(object, GuessWord.class);
		if (words.getUid() > 0) {
			if (status == 3) {// 通过
				coinService.addCoin(words.getUid(), CoinLog.VALIDWORD_OK, CoinLog.DRAW_SOMETHING, 100, 0,
						"你画我猜<" + words.get_id() + ">审核通过加币");
				messageService.imMsgHandler(Const.SYSTEM_ID, words.getUid(),
						"您为你画我猜贡献的题目\"" + words.get_id() + "\"已通过审核,奖励您100金币,感谢您的支持!", null, null);
				// messageService.easeMsgHandler(Const.SYSTEM_ID,
				// words.getUid(), "您为你画我猜贡献的题目\"" + words.get_id() +
				// "\"已通过审核,奖励您100金币,感谢您的支持!", null, null);
			} else if (status < 0) {// 不通过
				messageService.imMsgHandler(Const.SYSTEM_ID, words.getUid(),
						"您为你画我猜贡献的题目\"" + words.get_id() + "\"未通过审核,感谢您的支持!", null, null);
				// messageService.easeMsgHandler(Const.SYSTEM_ID,
				// words.getUid(), "您为你画我猜贡献的题目\"" + words.get_id() +
				// "\"未通过审核,感谢您的支持!", null, null);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	private DrawSomethingActor getNewOwner(Set<DrawSomethingActor> as) {
		for (DrawSomethingActor a : as) {
			if (Actor.STATUS_ONLINE == a.getStatus()) {
				return a;
			}
		}
		return null;
	}

	private void pubOwnerChange(long uid, DrawSomethingActor a, Room r, DrawSomething uc) {
		uc.setOwner(a.getUid());
		a.setOwner(true);
		super.saveRoomActor(r.get_id(), a);
		super.saveActivity(r.get_id(), uc);
		MapBody mb = new MapBody(SocketHandler.CHANGE_OWNER).append("lastOwner", uid).append("curOwner", uc.getOwner());
		super.pubRoomMsg(r.get_id(), MsgType.ROOM, mb);
	}

	public ReMsg guessLogSift(String ids, int status) {
		if (StringUtils.isNotBlank(ids)) {
			String[] rids = ids.split(",");
			List<Long> idl = new ArrayList<>();
			for (String e : rids) {
				idl.add(Long.parseLong(e));
			}
			BasicDBObject q = new BasicDBObject();
			q.put("_id", new BasicDBObject("$in", idl));
			if (status == 0) {
				super.getC(DocName.DRAW_GUESS_LOG).remove(q);
				return new ReMsg(ReCode.OK);
			}
			BasicDBObject u = new BasicDBObject();
			u.put("$set", new BasicDBObject("status", status));
			super.getC(DocName.DRAW_GUESS_LOG).update(q, u, false, true);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg bitchValidOK(String ids) throws Exception {
		if (StringUtils.isNotBlank(ids)) {
			String[] rids = ids.split(",");
			BasicDBObject q = new BasicDBObject();
			q.put("_id", new BasicDBObject("$in", rids));
			super.getC(DocName.DRAW_LOG).update(q,
					new BasicDBObject("$set", new BasicDBObject("status", Const.STATUS_OK)), false, true);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	// 批量删除
	public ReMsg bitchRemove(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] rids = ids.split(",");
			BasicDBObject q = new BasicDBObject();
			q.put("_id", new BasicDBObject("$in", rids));
			super.getC(DocName.DRAW_LOG).remove(q);
			super.getC(DocName.DRAW_LOG_DATA).remove(q);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public static void main(String[] args) {
		for (int a = 0; a < 1000; a++) {
			Set<Long> kickIds = new TreeSet<Long>();
			int roomCnt = RandomUtil.nextInt(5) + 6;

			List<Long> readyActors = null;

			int rec = roomCnt - (int) (Math.random() * 3);
			int rob = (int) (Math.random() * roomCnt);
			System.out.println("房间人数:" + roomCnt + ",准备的人数=" + rec);
			int kickCount = rec - START_MAX_COUNT;
			if (kickCount > 0) {
				readyActors = new ArrayList<>();
			}

			Set<DrawSomethingActor> actors = new HashSet<>();
			for (int i = 0; i < roomCnt; i++) {
				DrawSomethingActor temp = new DrawSomethingActor();
				temp.setInTime((long) (Math.random() * 1000));
				temp.setUid((long) (Math.random() * 1000));
				if (i == rob) {
					temp.setRobit(true);
				}
				if (i < rec)
					temp.setUcStatus(2);
				else
					temp.setUcStatus(1);
				// System.out.println(temp.getUcStatus());
				// System.out.println(temp.isRobit());
				actors.add(temp);
			}

			for (DrawSomethingActor actor : actors) {
				if (Actor.A_STATUS_READY == actor.getUcStatus()) {
					actor.setUcStatus(DrawSomethingActor.A_STATUS_START);
					if (kickCount > 0) {
						if (actor.isRobit()) {
							kickIds.add(actor.getUid());
							kickCount--;
						}
					}
					if (kickCount > 0 && !actor.isRobit()) {
						readyActors.add(actor.getUid());
					}
				} else {
					kickIds.add(actor.getUid());
				}
			}
			int i = 0;
			while (kickCount > 0) {
				kickIds.add(readyActors.get(readyActors.size() - ++i));
				kickCount--;
			}
			System.out.println("kickIds.size()=" + kickIds.size() + "房间人数： " + (roomCnt - kickIds.size()));
		}

	}
}
