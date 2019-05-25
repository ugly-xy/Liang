package com.zb.service.room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
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
//import com.zb.models.easemob.EmMsg.TargetType;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.Activity;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.activity.UaView;
import com.zb.models.room.activity.Undercover;
import com.zb.models.room.activity.UndercoverActor;
import com.zb.models.room.activity.UndercoverSpeakLog;
import com.zb.models.room.activity.UndercoverWord;
import com.zb.models.usertask.Task;
import com.zb.service.room.vo.RoomInfo;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class UndercoverService extends RoomService {
	static final Logger log = LoggerFactory.getLogger(UndercoverService.class);

	private static final int START_MIN_COUNT = 4;
	private static final int START_MAX_COUNT = 6;
	private static final int TIME_START = 10;// 10;
	private static final int TIME_END = 4;// 5;
	private static final int TIME_PUNISHED = 1;// 40;
	private static final int TIME_VOTE = 24;// 28;
	private static final int TIME_SPACK = 35;// 30;
	private static final int TIME_DICE = 18;// 20;
	private static final int TIME_NEXT = 3;// 3;
	private static final int TIME_TEMPLATE = 3;
	private static final String FN_WORD = "word";

	// private static final int COIN_DEF = 10;
	private static final int COIN_DEF = 20;
	// private static final int COIN_MIN = 20;
	// private static final double COIN_CUT = 0.02;
	private static final int OUT_LINE_COIN = 30;
	private static final int OUT_COIN = 50;

	private static final int POINT_WIN = 100;
	private static final int POINT_LOST = 10;

	@Override
	public ReMsg handle(Room room, long fr) {
		if (room.getType() != ActivityType.UNDERCOVER.getType()) {
			log.error(room.get_id() + " " + room.getType());
			return new ReMsg(ReCode.FAIL);
		}
		try {
			Undercover uc = super.getActivity(room.get_id(), Undercover.class);
			if (Undercover.STATUS_READY == uc.getStatus()) {
				start(room, fr, 0, uc);
			} else if (Undercover.STATUS_SPEAK == uc.getStatus()) {// 说话时间结束，进入投票阶段
				beginVote(room.get_id(), uc, null);// 发投票广播
			} else if (Undercover.STATUS_VOTE == uc.getStatus()) {
				return voted(room.get_id(), uc, null);
			} else if (Undercover.STATUS_TO_NEXT == uc.getStatus()) {
				nextInnting(room, uc);
			} else if (Undercover.STATUS_PK_SPEAK == uc.getStatus()) {// PK说话时间结束，进入PK投票阶段
				beginPkVote(room.get_id(), uc);
			} else if (Undercover.STATUS_PK_VOTE == uc.getStatus()) {
				return pkVoted(room.get_id(), uc, null);
			} else if (Undercover.STATUS_PK_DICE == uc.getStatus()) {
				return diceEnd(room.get_id(), fr, uc);
			} else if (Undercover.STATUS_END == uc.getStatus()) {
				// restart(room, fr);
				return punished(room, uc);
			} else if (Undercover.STATUS_PUNISH == uc.getStatus()) {
				restart(room, fr, uc);
			}
		} catch (Exception e) {
			log.error("调用任务roomId=" + room.get_id() + "\n", e);
			this.addJob(room.get_id(), TIME_START);
		}
		return new ReMsg(ReCode.FAIL);
	}

	// private static final int PUNISH_RATE = 4;
	/**
	 * 用户发言描述
	 * 
	 * @param uid
	 * @param roomId
	 * @param txt
	 * @return
	 */
	public ReMsg speak(long uid, final long roomId, String txt) {
		Undercover uc = super.getActivity(roomId, Undercover.class);
		if (Undercover.STATUS_SPEAK == uc.getStatus()) {
			UndercoverActor actor = super.getRoomActor(roomId, uid, UndercoverActor.class);
			if (actor != null) {
				if (Actor.ROLE_EXECUTOR == actor.getRole() && !actor.isDead()) {// 非死亡的参与者才可以投票
					if (!actor.isRobit()) {
						if (StringUtils.isNotBlank(txt)) {
							if (uid == uc.getUndercover()) {
								saveUndercoverSpeakLog(txt, uc.getUcWord(), Const.STATUS_DEF);
							} else {
								saveUndercoverSpeakLog(txt, uc.getcWord(), Const.STATUS_DEF);
							}
						}
					}
					actor.setUcStatus(UndercoverActor.A_STATUS_SPEAK);
					txt = replace(uc.getcWord() + uc.getUcWord(), txt);
					actor.setTalk(txt);
					super.saveRoomActor(roomId, actor);
					uc.addSpeak(actor.getUid());
					Set<UndercoverActor> actors = super.getRoomActors(roomId, UndercoverActor.class);
					Set<UaView> uavs = this.getUavs(actors);
					MapBody mb = new MapBody(SocketHandler.UNDERCOVER_SPEAK, SocketHandler.FN_USER_ID, uid)
							.append("txt", txt).append(SocketHandler.FN_ROOM_ID, roomId).append("actors", uavs);
					super.pubRoomMsg(roomId, MsgType.UNDERCOVER, mb);
					super.saveActivity(roomId, uc);

					if (uc.getActors().size() - uc.getSpeaks().size() == uc.getDeaths().size()) {// 符合投票条件
						beginVote(roomId, uc, actors);
					}
				}
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	public boolean restart(Room r, long fr, Undercover uc) {
		Set<UndercoverActor> actors = getRoomActors(r.get_id(), UndercoverActor.class);
		long owner = 0L;
		if (r.getUid() != 0) {
			if (uc == null) {
				uc = super.getActivity(r.get_id(), Undercover.class);
			}
			owner = uc.getOwner();
		}
		super.delActivity(r.get_id());
		List<UndercoverActor> initActors = new ArrayList<UndercoverActor>();
		int robitCount = 0;
		for (UndercoverActor actor : actors) {
			if (Actor.STATUS_ONLINE != actor.getStatus()) {
				super.delRoomActor(r.get_id(), actor.getUid());
				if (r.getUid() != 0 && actor.getUid() == owner) {
					UndercoverActor newOua = getNewOwner(actors);
					if (newOua != null) {
						owner = newOua.getUid();
					}
					// pubOwnerChange(owner,newOua, r, false, uc);
				}
			} else {
				// Undercover uc = super.getActivity(r.get_id(),
				// Undercover.class);
				actor.clearVotes();
				actor.setDead(false);
				actor.setRole(UndercoverActor.ROLE_EXECUTOR);
				actor.setDiceNumber(0);
				actor.setStatus(UndercoverActor.STATUS_ONLINE);
				actor.setTalk("");
				actor.setUcStatus(UndercoverActor.A_STATUS_IN_ROOM);
				actor.setChangeCoin(0);
				actor.setChangePoint(0);
				actor.setWinder(false);
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
			uc = super.getActivity(r.get_id(), Undercover.class);
			uc.setOwner(owner);
			super.saveActivity(r.get_id(), uc);
		}

		MapBody mb = new MapBody(SocketHandler.ROOM_RESTART, SocketHandler.FN_ROOM_ID, r.get_id()).append("actors",
				initActors);

		long dt = System.currentTimeMillis();
		for (Actor actor : initActors) {
			if (actor.getStatus() == Actor.STATUS_ONLINE) {
				super.pubRoomUserMsg(r.get_id(), actor.getUid(), MsgType.ROOM, mb, dt);
			}
		}
		return true;
	}

	private boolean start(Room r, long fr, int readyCount, Undercover uc) {
		boolean start = false;
		Set<UndercoverActor> actors = super.getRoomActors(r.get_id(), UndercoverActor.class);
		readyCount = uc.getActors().size();
		if (readyCount >= START_MIN_COUNT) {// 准备人数超过最小人数
			start = true;
		} else {
			checkReadyRoom(r, readyCount, TIME_START);
		}

		if (start) {
			// long activityNo = super.incrId(RK.activityId());
			addJob(r.get_id(), TIME_SPACK);// 开始
			r.setStatus(Room.ACTIVITY);
			r.setActivityDate(System.currentTimeMillis());
			// this.updateRoom(r);
			// uc.set_id(activityNo);

			String[] words = getWords();
			int widx = RandomUtil.nextInt(10) % 2;
			uc.setcWord(words[(widx + 1) % 2]);
			uc.setUcWord(words[widx]);

			Set<Long> kickIds = new TreeSet<Long>();
			Set<UaView> curActors = new TreeSet<UaView>();
			int idx = RandomUtil.nextInt(readyCount);
			int i = 0;
			uc.clearActors();
			int kickCount = readyCount - START_MAX_COUNT;
			List<Long> readyActors = null;
			if (kickCount > 0) {
				readyActors = new ArrayList<>();
			}
			for (UndercoverActor actor : actors) {
				if (Actor.A_STATUS_READY == actor.getUcStatus()) {
					if (i == idx) {
						uc.setUndercover(actor.getUid());
					}
					i++;
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
			int j = 0;
			while (kickCount > 0) {
				kickIds.add(readyActors.get(readyActors.size() - ++j));
				kickCount--;
			}
			for (UndercoverActor actor : actors) {
				if (kickIds.contains(actor.getUid())) {
					super.delRoomActor(r.get_id(), actor.getUid());
					uc.removeActor(actor.getUid());
				} else {
					actor.setUcStatus(UndercoverActor.A_STATUS_SPEAK_BEGIN);
					saveRoomActor(r.get_id(), actor);
					curActors.add(new UaView(actor));
					uc.addActor(actor.getUid());
				}
			}
			uc.setStatus(Undercover.STATUS_SPEAK);
			super.saveActivity(r.get_id(), uc);

			long curTime = System.currentTimeMillis();
			MapBody mb2 = new MapBody(SocketHandler.UNDERCOVER_WORD, SocketHandler.FN_ROOM_ID, r.get_id())
					.append("inning", uc.getInning()).append("actors", curActors).append("kicks", kickIds)
					.append(SocketHandler.FN_LIMIT, TIME_SPACK);
			for (UndercoverActor actor : actors) {// 发送房间内
				if (actor.getStatus() == Actor.STATUS_ONLINE) {
					if (actor.getUid() == uc.getUndercover()) {
						mb2.append(FN_WORD, uc.getUcWord());
					} else if (UndercoverActor.ROLE_EXECUTOR == actor.getRole()) {
						mb2.append(FN_WORD, uc.getcWord());
					} else {
						mb2.append(FN_WORD, "观众");
					}
					this.pubRoomUserMsg(r.get_id(), actor.getUid(), MsgType.UNDERCOVER, mb2, curTime);
				}

			}
			changeRoomCount(r, super.getRoomActors(r.get_id(), UndercoverActor.class), T_START);
		}
		return start;
	}

	/**
	 * 用户发言描述
	 * 
	 * @param uid
	 * @param roomId
	 * @param txt
	 * @return
	 */

	public List<DBObject> findUndercoverSpeakLogs(String word, Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(word)) {
			q.put("word", word);
		}
		if (status != null && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.UNDERCOVER_SPEAK_LOG).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	private ReMsg beginVote(final long roomId, Undercover uc, Set<UndercoverActor> actors) {
		super.addJob(roomId, TIME_VOTE);
		uc.setStatus(Undercover.STATUS_VOTE);
		uc.clearSpeaks();
		super.saveActivity(roomId, uc);
		if (actors == null) {
			actors = super.getRoomActors(roomId, UndercoverActor.class);
		}
		Set<UaView> uavs = new TreeSet<UaView>();
		for (UndercoverActor actor : actors) {
			if (actor.getRole() == UndercoverActor.ROLE_EXECUTOR) {
				actor.setUcStatus(UndercoverActor.A_STATUS_VOTE_BEGIN);
				super.saveRoomActor(roomId, actor);
				uavs.add(new UaView(actor));
			}
		}
		MapBody mb2 = new MapBody(SocketHandler.UNDERCOVER_BEGIN_VOTE, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_VOTE).append("actors", uavs);
		super.pubRoomMsg(roomId, MsgType.UNDERCOVER, mb2);
		return new ReMsg(ReCode.OK);
	}

	/**
	 * 用户投票
	 * 
	 * @param roomId
	 * @param fr
	 *            投票人
	 * @param to
	 *            被投票人
	 * @return
	 */
	public ReMsg vote(final long roomId, long fr, long to) {// 用户投票
		Undercover uc = super.getActivity(roomId, Undercover.class);
		if (Undercover.STATUS_VOTE == uc.getStatus()) {
			UndercoverActor frU = super.getRoomActor(roomId, fr, UndercoverActor.class);
			UndercoverActor toU = super.getRoomActor(roomId, to, UndercoverActor.class);
			if (frU != null && toU != null && UndercoverActor.ROLE_UNDERCOVER == frU.getRole()
					&& UndercoverActor.ROLE_UNDERCOVER == toU.getRole() && !frU.isDead() && !toU.isDead()
					&& frU.getUcStatus() != UndercoverActor.A_STATUS_VOTE && fr != to) {// 投票双方必须是存活的参与者
				frU.setUcStatus(UndercoverActor.A_STATUS_VOTE);
				toU.addVote(fr);
				this.saveRoomActor(roomId, frU);
				this.saveRoomActor(roomId, toU);
				uc.addVote(fr);
				super.saveActivity(roomId, uc);

				Set<UndercoverActor> actors = super.getRoomActors(roomId, UndercoverActor.class);
				Set<UaView> uavs = this.getUavs(actors);

				MapBody mb = new MapBody(SocketHandler.UNDERCOVER_TO_VOTE, SocketHandler.FN_ROOM_ID, roomId)
						.append("frUid", fr).append("toUid", to).append("sys", false).append("actors", uavs);
				super.pubRoomMsg(roomId, MsgType.UNDERCOVER, mb);

				if (uc.getActors().size() - uc.getVotes().size() == uc.getDeaths().size()) {// 符合投票结束条件
					voted(roomId, uc, actors);
				}
			}
		}
		return new ReMsg(ReCode.OK);
	}

	private ReMsg voted(final long roomId, Undercover uc, Set<UndercoverActor> actors) {
		if (actors == null) {// 系统发起的
			actors = super.getRoomActors(roomId, UndercoverActor.class);
			if (uc.getActors().size() - uc.getVotes().size() != uc.getDeaths().size() && !uc.isJob()) {// 需要系统投票
				super.addJob(roomId, TIME_TEMPLATE);
				UndercoverActor va = null;
				List<UndercoverActor> sysVotes = new ArrayList<UndercoverActor>();
				for (UndercoverActor uca : actors) {
					if (uca.getRole() == UndercoverActor.ROLE_EXECUTOR && !uca.isDead()) {// 存活的参与者
						if (va == null) {
							va = uca;
						} else {
							if (va.getVotes().size() < uca.getVotes().size()) {
								va = uca;
							}
						}
						if (uca.getUcStatus() != UndercoverActor.A_STATUS_VOTE) {// 没有投票的人
							sysVotes.add(uca);
						}
					}
				}
				for (UndercoverActor uca : sysVotes) {
					uca.setUcStatus(UndercoverActor.A_STATUS_VOTE);
					va.addVote(uca.getUid());
					super.saveRoomActor(roomId, uca);
					super.saveRoomActor(roomId, va);
					uc.addVote(uca.getUid());
					Set<UaView> uavs = this.getUavs(actors);
					MapBody mb = new MapBody(SocketHandler.UNDERCOVER_TO_VOTE, SocketHandler.FN_ROOM_ID, roomId)
							.append("frUid", uca.getUid()).append("toUid", va.getUid()).append("sys", true)
							.append("actors", uavs);
					super.pubRoomMsg(roomId, MsgType.UNDERCOVER, mb);
				}
				uc.setJob(true);
				super.saveActivity(roomId, uc);
				return new ReMsg(ReCode.OK);
			}
		}
		uc.clearVotes();
		uc.setJob(false);

		List<UndercoverActor> maxs = new ArrayList<UndercoverActor>();// 被投票得票最多的人
		int max = 0;
		for (UndercoverActor actor : actors) {
			if (!actor.isDead() && actor.getRole() == UndercoverActor.ROLE_EXECUTOR) {
				int curVc = actor.getVotes().size();
				if (curVc == max) {
					maxs.add(actor);
				} else if (curVc > max) {
					max = curVc;
					maxs.clear();
					maxs.add(actor);
				}
			}
		}
		if (maxs.size() == 1) {// 出局
			UndercoverActor actor = maxs.get(0);
			if (actor.getUid() == uc.getUndercover()) {// 结束游戏 1 卧底死亡
				return end(roomId, UndercoverActor.ROLE_CIVILIAN, uc);
			} else if (uc.getActors().size() - uc.getDeaths().size() == 3 || uc.getInning() == 2) { // 2
																									// 剩余三人
																									// 或者
																									// 第三局
				return end(roomId, UndercoverActor.ROLE_UNDERCOVER, uc);
			} else {// 用户死亡，进入下一局
				return userDie(roomId, actor, uc);
			}
		} else if (maxs.size() > 1) {// 平局
			toPk(roomId, uc, maxs);
		} else {// 问题局，没人投票或者都离线
			super.closeRoom(roomId);
		}
		return new ReMsg(ReCode.FAIL);
	}

	private void toPk(final long roomId, Undercover uc, List<UndercoverActor> maxs) {
		super.addJob(roomId, TIME_SPACK);
		uc.setStatus(Undercover.STATUS_PK_SPEAK);
		Set<Long> pkIds = new TreeSet<Long>();
		for (UndercoverActor uca : maxs) {
			uca.setUcStatus(UndercoverActor.A_STATUS_SPEAK_BEGIN);
			super.saveRoomActor(roomId, uca);
			pkIds.add(uca.getUid());
		}
		uc.setPks(pkIds);
		super.saveActivity(roomId, uc);
		Set<UndercoverActor> actors = super.getRoomActors(roomId, UndercoverActor.class);
		Set<UaView> uavs = new TreeSet<UaView>();
		for (UndercoverActor actor : actors) {
			actor.clearVotes();
			actor.setTalk("");
			super.saveRoomActor(roomId, actor);
			uavs.add(new UaView(actor));
		}
		MapBody mb = new MapBody(SocketHandler.UNDERCOVER_PK, SocketHandler.FN_ROOM_ID, roomId).append("pks", pkIds)
				.append(SocketHandler.FN_LIMIT, TIME_SPACK).append("actors", uavs);
		super.pubRoomMsg(roomId, MsgType.UNDERCOVER, mb);// 发送pk消息
	}

	public ReMsg pkSpeak(Long roomId, Long fr, String txt) {
		Undercover uc = super.getActivity(roomId, Undercover.class);
		if (Undercover.STATUS_PK_SPEAK == uc.getStatus()) {
			if (uc.getPks().contains(fr)) {
				UndercoverActor actor = super.getRoomActor(roomId, fr, UndercoverActor.class);
				if (!actor.isRobit()) {
					if (StringUtils.isNotBlank(txt)) {
						if (uc.getUndercover() == fr) {
							saveUndercoverSpeakLog(txt, uc.getUcWord(), Const.STATUS_DEF);
						} else {
							saveUndercoverSpeakLog(txt, uc.getcWord(), Const.STATUS_DEF);
						}
					}
				}
				txt = replace(uc.getcWord() + uc.getUcWord(), txt);

				actor.setUcStatus(UndercoverActor.A_STATUS_PK_SPEAK);
				actor.setTalk(txt);
				super.saveRoomActor(roomId, actor);
				uc.addSpeak(actor.getUid());

				Set<UndercoverActor> actors = super.getRoomActors(roomId, UndercoverActor.class);
				Set<UaView> uavs = this.getUavs(actors);

				MapBody mb = new MapBody(SocketHandler.UNDERCOVER_PK_SPEAK, SocketHandler.FN_USER_ID, fr)
						.append("txt", txt).append(SocketHandler.FN_ROOM_ID, roomId).append("actors", uavs);
				super.pubRoomMsg(roomId, MsgType.UNDERCOVER, mb);
				super.saveActivity(roomId, uc);
				if (uc.getPks().size() == uc.getSpeaks().size()) {// 符合投票条件
					beginPkVote(roomId, uc);
				}
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	private String replace(String words, String talk) {
		if (StringUtils.isNotBlank(talk)) {
			char[] ws = words.toCharArray();
			String regex = "";
			for (char w : ws) {
				regex += w + "|";
			}
			if (ws.length > 0) {
				regex = regex.substring(0, regex.length() - 1);
			}
			// System.out.println(regex);
			return talk.replaceAll(regex, "*");
		} else {
			return "";
		}
	}

	public static void main(String[] args) {
		UndercoverService ucs = new UndercoverService();
		System.out.println(ucs.replace("我是好人", "我是我，是好人，好的人会有好报，非常的厉害的，真的，哈哈哈"));
	}

	private void beginPkVote(final long roomId, Undercover uc) {
		super.addJob(roomId, TIME_VOTE);
		uc.setStatus(Undercover.STATUS_PK_VOTE);
		uc.clearSpeaks();
		super.saveActivity(roomId, uc);
		Set<UndercoverActor> uas = super.getRoomActors(roomId, UndercoverActor.class);
		for (UndercoverActor ua : uas) {
			if (ua.getRole() == UndercoverActor.ROLE_EXECUTOR) {
				ua.setUcStatus(UndercoverActor.A_STATUS_PK_VOTE_BEGIN);
				super.saveRoomActor(roomId, ua);
			}
		}
		MapBody mb2 = new MapBody(SocketHandler.UNDERCOVER_PK_BEGIN_VOTE, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_VOTE).append("pks", uc.getPks());
		super.pubRoomMsg(roomId, MsgType.UNDERCOVER, mb2);
	}

	public ReMsg pkVote(final long roomId, long fr, Long to) {
		Undercover uc = super.getActivity(roomId, Undercover.class);
		if (Undercover.STATUS_PK_VOTE == uc.getStatus()) {
			if (uc.getPks().contains(to)) {
				UndercoverActor frU = super.getRoomActor(roomId, fr, UndercoverActor.class);
				if (!frU.isDead() && frU.getRole() == UndercoverActor.ROLE_EXECUTOR
						&& frU.getUcStatus() != UndercoverActor.A_STATUS_PK_VOTE) {
					UndercoverActor toU = super.getRoomActor(roomId, to, UndercoverActor.class);
					frU.setUcStatus(UndercoverActor.A_STATUS_PK_VOTE);
					toU.addVote(frU.getUid());
					this.saveRoomActor(roomId, frU);
					this.saveRoomActor(roomId, toU);
					uc.addVote(fr);
					Set<UndercoverActor> actors = super.getRoomActors(roomId, UndercoverActor.class);
					Set<UaView> uavs = this.getUavs(actors);
					MapBody mb = new MapBody(SocketHandler.UNDERCOVER_PK_VOTE, SocketHandler.FN_ROOM_ID, roomId)
							.append("frUid", fr).append("toUid", to).append("sys", false).append("actors", uavs);
					super.pubRoomMsg(roomId, MsgType.UNDERCOVER, mb);
					super.saveActivity(roomId, uc);
					if (uc.getActors().size() - uc.getVotes().size() == uc.getDeaths().size()) {// 房间参与人数－投票人数＝死亡人数＋1
						this.addJob(roomId, TIME_TEMPLATE);
						// pkVote(roomId, uc, actors);
					}
				}
			}
		}
		return new ReMsg(ReCode.OK);
	}

	private ReMsg pkVoted(final long roomId, Undercover uc, Set<UndercoverActor> actors) {
		if (actors == null) {
			actors = super.getRoomActors(roomId, UndercoverActor.class);
			if (uc.getActors().size() - uc.getVotes().size() != uc.getDeaths().size() && !uc.isJob()) {
				super.addJob(roomId, TIME_TEMPLATE);
				UndercoverActor va = null;
				int maxCount = 0;
				List<UndercoverActor> sysVotes = new ArrayList<UndercoverActor>();

				for (UndercoverActor actor : actors) {
					if (uc.getPks().contains(actor.getUid())) {
						int vc = actor.getVotes().size();
						if (va == null) {
							va = actor;
							maxCount = vc;
						} else {
							if (maxCount < vc) {
								maxCount = vc;
								va = actor;
							}
						}
					}

					if (UndercoverActor.A_STATUS_PK_VOTE != actor.getUcStatus() && !actor.isDead()
							&& actor.getRole() == UndercoverActor.ROLE_EXECUTOR) {// 非死掉且没投票的参与者，需要系统投票
						sysVotes.add(actor);
					}
				}
				for (UndercoverActor actor : sysVotes) {
					va.addVote(actor.getUid());
					super.saveRoomActor(roomId, va);
					actor.setUcStatus(UndercoverActor.A_STATUS_PK_VOTE);
					super.saveRoomActor(roomId, actor);
					uc.addVote(actor.getUid());
					Set<UaView> uavs = this.getUavs(actors);
					MapBody mb = new MapBody(SocketHandler.UNDERCOVER_TO_VOTE, SocketHandler.FN_ROOM_ID, roomId)
							.append("frUid", actor.getUid()).append("toUid", va.getUid()).append("sys", true)
							.append("actors", uavs);
					super.pubRoomMsg(roomId, MsgType.UNDERCOVER, mb);
				}
				uc.setJob(true);
				super.saveActivity(roomId, uc);
				return new ReMsg(ReCode.OK);
			}
		}
		uc.setJob(false);
		uc.clearVotes();
		List<UndercoverActor> maxs = new ArrayList<UndercoverActor>();// 被投票得票最多的人
		int max = 0;
		Set<Long> aids = uc.getPks();
		for (long aid : aids) {
			UndercoverActor actor = super.getRoomActor(roomId, aid, UndercoverActor.class);
			int curVc = actor.getVotes().size();
			if (curVc == max) {
				maxs.add(actor);
			} else if (curVc > max) {
				max = curVc;
				maxs.clear();
				maxs.add(actor);
			}
		}
		if (maxs.size() == 1) {// 出局
			UndercoverActor actor = maxs.get(0);
			if (actor.getUid() == uc.getUndercover()) {// 结束游戏 1 卧底死亡
				return end(roomId, UndercoverActor.ROLE_CIVILIAN, uc);
			} else if (uc.getActors().size() - uc.getDeaths().size() == 3 || uc.getInning() == 2) { // 剩余三人
																									// 或者
																									// 第三局
				return end(roomId, UndercoverActor.ROLE_UNDERCOVER, uc);
			} else {// 用户死亡，进入下一局
				return userDie(roomId, actor, uc);
			}
		} else if (maxs.size() > 1) {// 平局
			toDice(roomId, uc, maxs);
		} else {// 问题局，没人投票或者都离线
			super.closeRoom(roomId);
		}
		return new ReMsg(ReCode.FAIL);
	}

	private void toDice(final long roomId, Undercover uc, List<UndercoverActor> maxs) {
		super.addJob(roomId, TIME_DICE);
		uc.setStatus(Undercover.STATUS_PK_DICE);
		Set<Long> pkIds = new TreeSet<Long>();
		for (UndercoverActor uca : maxs) {
			uca.setUcStatus(UndercoverActor.A_STATUS_PK_DICE_BEGIN);
			super.saveRoomActor(roomId, uca);
			pkIds.add(uca.getUid());
		}
		uc.setPks(pkIds);
		super.saveActivity(roomId, uc);

		Set<UndercoverActor> actors = super.getRoomActors(roomId, UndercoverActor.class);
		Set<UaView> uavs = this.getUavs(actors);

		MapBody mb = new MapBody(SocketHandler.UNDERCOVER_BEGIN_DICE, SocketHandler.FN_ROOM_ID, roomId)
				.append("pks", pkIds).append(SocketHandler.FN_LIMIT, TIME_DICE).append("actors", uavs);
		super.pubRoomMsg(roomId, MsgType.UNDERCOVER, mb);
	}

	public ReMsg dice(final long roomId, long fr) {
		Undercover uc = super.getActivity(roomId, Undercover.class);
		if (uc.getPks().contains(fr)) {
			int d = 0;
			UndercoverActor uca = super.getRoomActor(roomId, fr, UndercoverActor.class);
			if (uca != null) {
				d = RandomUtil.nextInt(6) + 1;
				while (uc.getDices().containsKey(d)) {
					if (d > 3 && uc.getDices().size() > 3) {
						break;
					} else {
						d = RandomUtil.nextInt(6) + 1;
					}
				}
				uca.setUcStatus(UndercoverActor.A_STATUS_PK_DICE);
				uca.setDiceNumber(d);
				super.saveRoomActor(roomId, uca);
				uc.addDice(d, uca.getUid());
				super.saveActivity(roomId, uc);

				Set<UndercoverActor> actors = super.getRoomActors(roomId, UndercoverActor.class);
				Set<UaView> uavs = this.getUavs(actors);

				MapBody mb = new MapBody(SocketHandler.UNDERCOVER_DICE, SocketHandler.FN_ROOM_ID, roomId)
						.append("uid", fr).append("number", d).append("actors", uavs);
				super.pubRoomMsg(roomId, MsgType.UNDERCOVER, mb);// 发送pk消息

				if (uc.getDices().size() == uc.getPks().size()) {
					super.addJob(roomId, TIME_TEMPLATE);
				}
			}
		}

		return new ReMsg(ReCode.OK);
	}

	private ReMsg diceEnd(final long roomId, long fr, Undercover uc) {
		int min = 0;
		UndercoverActor dieActor = null;
		for (long uid : uc.getPks()) {
			UndercoverActor a = super.getRoomActor(roomId, uid, UndercoverActor.class);
			if (a != null && UndercoverActor.A_STATUS_PK_DICE == a.getUcStatus()) {
				if (min == 0) {
					min = a.getDiceNumber();
					dieActor = a;
				} else if (a.getDiceNumber() < min) {
					min = a.getDiceNumber();
					dieActor = a;
				}
			} else {
				dieActor = a;
				break;
			}
		}
		uc.clearDices();

		if (dieActor.getUid() == uc.getUndercover()) {
			return end(roomId, UndercoverActor.ROLE_CIVILIAN, uc);
		} else if (uc.getActors().size() - uc.getDeaths().size() == 3 || uc.getInning() == 2) {
			return end(roomId, UndercoverActor.ROLE_UNDERCOVER, uc);
		} else {
			return userDie(roomId, dieActor, uc);
		}
	}

	private void nextInnting(Room room, Undercover uc) {
		super.addJob(room.get_id(), TIME_SPACK);
		uc.setInning(uc.getInning() + 1);
		uc.setStatus(Undercover.STATUS_SPEAK);
		uc.clearPks();
		uc.clearSpeaks();
		uc.clearVotes();
		super.saveActivity(room.get_id(), uc);

		Set<UndercoverActor> actors = super.getRoomActors(room.get_id(), UndercoverActor.class);
		Set<UaView> uavs = new TreeSet<UaView>();
		for (UndercoverActor ua : actors) {
			ua.setUcStatus(UndercoverActor.A_STATUS_SPEAK_BEGIN);
			ua.setTalk("");
			ua.clearVotes();
			ua.setDiceNumber(0);
			super.saveRoomActor(room.get_id(), ua);
			uavs.add(new UaView(ua));
		}

		MapBody mb = new MapBody(SocketHandler.UNDERCOVER_WORD, SocketHandler.FN_ROOM_ID, room.get_id())
				.append("inning", uc.getInning()).append("actors", uavs).append(SocketHandler.FN_LIMIT, TIME_SPACK)
				.append("kicks", new TreeSet<Long>());
		super.pubRoomMsg(room.get_id(), MsgType.UNDERCOVER, mb);
	}

	private ReMsg end(final long roomId, int winner, Undercover uc) {
		if (uc == null)
			uc = super.getActivity(roomId, Undercover.class);
		uc.setStatus(Undercover.STATUS_END);
		uc.setWinner(winner);
		super.saveActivity(roomId, uc);
		super.addJob(roomId, TIME_END);// 5秒倒计时，5秒后进入进入奖惩阶段
		Set<UndercoverActor> uas = super.getRoomActors(roomId, UndercoverActor.class);

		MapBody mb = new MapBody(SocketHandler.UNDERCOVER_END, SocketHandler.FN_ROOM_ID, roomId)
				.append("winner", winner).append("undercover", uc.getUndercover()).append("ucWord", uc.getUcWord())
				.append("cWord", uc.getcWord()).append(SocketHandler.FN_LIMIT, TIME_END);
		long dt = System.currentTimeMillis();
		int c = uc.getActors().size();

		// int outCount = 0;
		// for (UndercoverActor ua : uas) {
		// if (UndercoverActor.ROLE_EXECUTOR == ua.getRole()) {
		// if (ua.getStatus() != Actor.STATUS_ONLINE) {
		// outCount++;
		// }
		// }
		// }

		// int ucCoin = 0;
		// int ucPoint = 0;
		// int cCoin = 0;
		// int cPoint = 0;
		//
		// if (winner == UndercoverActor.ROLE_UNDERCOVER) {
		// ucCoin = (c - 1) * COIN_DEF;
		// ucPoint = POINT_WIN;
		// cCoin = COIN_DEF;
		// cPoint = POINT_LOST;
		// } else {
		// ucCoin = COIN_DEF;
		// ucPoint = POINT_LOST;
		// cCoin = COIN_DEF;
		// cPoint = POINT_WIN;
		// }

		int ucCoin = (c - 1) * COIN_DEF;
		int ucPoint = 0;
		int cCoin = COIN_DEF;
		int cPoint = 0;

		if (winner == UndercoverActor.ROLE_UNDERCOVER) {
			ucPoint = POINT_WIN;
			cPoint = POINT_LOST;
		} else {
			ucPoint = POINT_LOST;
			cPoint = POINT_WIN;
		}

		for (UndercoverActor ua : uas) {
			if (UndercoverActor.ROLE_EXECUTOR == ua.getRole()) {
				if (ua.getStatus() != Actor.STATUS_ONLINE) {
					if (ua.getStatus() == Actor.STATUS_OFFLINE) {
						ua.setWinder(false);
						ua.setChangeCoin(OUT_LINE_COIN);
						if (!ua.isReduceCoin()) {
							coinService.forceReduce(ua.getUid(), CoinLog.UNDERCOVER, uc.get_id(), OUT_LINE_COIN, 0,
									"未完成游戏掉线惩罚扣币");
							ua.setReduceCoin(true);
						}
					} else if (ua.getStatus() != Actor.STATUS_OUT_ROOM) {
						ua.setWinder(false);
						ua.setChangeCoin(OUT_COIN);
						if (!ua.isReduceCoin()) {
							coinService.forceReduce(ua.getUid(), CoinLog.UNDERCOVER, uc.get_id(), OUT_COIN, 0,
									"未完成游戏退出房间惩罚扣币");
							ua.setReduceCoin(true);
						}
					}
				}
				if (winner == UndercoverActor.ROLE_UNDERCOVER) {
					if (ua.getUid() == uc.getUndercover()) {
						ua.setWinder(true);
						ua.setChangeCoin(ucCoin);
						ua.setChangePoint(ucPoint);
						coinService.addCoin(ua.getUid(), CoinLog.UNDERCOVER, uc.get_id(), ucCoin, 0, "卧底胜利");
						userService.changePoint(ua.getUid(), Point.UNDERCOVER.getType(), ucPoint, 0);
					} else {
						ua.setWinder(false);
						ua.setChangeCoin(cCoin);
						ua.setChangeCoin(cPoint);
						coinService.forceReduce(ua.getUid(), CoinLog.UNDERCOVER, uc.get_id(), COIN_DEF, 0, "平民失败");
						userService.changePoint(ua.getUid(), Point.UNDERCOVER.getType(), cPoint, 0);
					}
				} else {
					if (ua.getUid() == uc.getUndercover()) {
						ua.setWinder(false);
						ua.setChangeCoin(ucCoin);
						ua.setChangePoint(ucPoint);
						coinService.forceReduce(ua.getUid(), CoinLog.UNDERCOVER, uc.get_id(), COIN_DEF, 0, "卧底失败");
						userService.changePoint(ua.getUid(), Point.UNDERCOVER.getType(), ucPoint, 0);
					} else {
						ua.setWinder(true);
						ua.setChangeCoin(cCoin);
						ua.setChangePoint(cPoint);
						coinService.addCoin(ua.getUid(), CoinLog.UNDERCOVER, uc.get_id(), cCoin, 0, "平民胜利");
						userService.changePoint(ua.getUid(), Point.UNDERCOVER.getType(), cPoint, 0);
					}
				}
				super.saveRoomActor(roomId, ua);
			}
			if (ua.getStatus() == Actor.STATUS_ONLINE) {
				mb.append("ucCoin", ucCoin).append("ucPoint", ucPoint).append("cCoin", cCoin).append("cPoint", cPoint);
				super.pubRoomUserMsg(roomId, ua.getUid(), MsgType.UNDERCOVER, mb, dt);
			}
		}
		// 触发任务
		long under = uc.getUndercover();
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			for (UndercoverActor actor : uas) {
				if (!actor.isRobit()) {
					if (under == actor.getUid()) {
						jobsDone(actor.getUid(), Task.UNDERCOVER_ISUNDER, 1);
					} else {
						jobsDone(actor.getUid(), Task.UNDERCOVER_NOTUNDER, 1);
					}
				}
			}
			return null;
		});
		try {
			future.get();
		} catch (Exception e) {
			log.error("调用任务undercover", e);
		}
		// uc.setUpdateTime(System.currentTimeMillis());
		// super.getMongoTemplate().save(uc);
		return new ReMsg(ReCode.OK);
	}

	private ReMsg userDie(final long roomId, Actor dieUser, Undercover uc) {
		uc.setStatus(Undercover.STATUS_TO_NEXT);
		uc.addDie(dieUser.getUid());
		super.saveActivity(roomId, uc);
		super.addJob(roomId, TIME_NEXT);// 倒计时，进入下一个轮
		dieUser.setDead(true);
		this.saveRoomActor(roomId, dieUser);
		Set<UndercoverActor> actors = super.getRoomActors(roomId, UndercoverActor.class);
		Set<UaView> uavs = this.getUavs(actors);

		MapBody mb = new MapBody(SocketHandler.UNDERCOVER_DIE_USER, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_USER_ID, dieUser.getUid()).append("actors", uavs)
				.append("nickname", dieUser.getNickname());
		super.pubRoomMsg(roomId, MsgType.UNDERCOVER, mb);

		return new ReMsg(ReCode.OK);
	}

	private ReMsg punished(Room r, Undercover uc) {
		uc.setStatus(Undercover.STATUS_PUNISH);
		uc.setUpdateTime(System.currentTimeMillis());
		super.getMongoTemplate().save(uc);
		super.saveActivity(r.get_id(), uc);
		super.addJob(r.get_id(), TIME_PUNISHED);
		Set<UndercoverActor> uas = super.getRoomActors(r.get_id(), UndercoverActor.class);
		Set<Long> uids = new TreeSet<Long>();
		Set<String> punish = punishService.getPunish(6);
		for (UndercoverActor ua : uas) {
			if (UndercoverActor.ROLE_EXECUTOR == ua.getRole()) {
				if (uc.getWinner() == UndercoverActor.ROLE_UNDERCOVER) {
					if (ua.getUid() != uc.getUndercover()) {
						uids.add(ua.getUid());
					}
				} else {
					if (ua.getUid() == uc.getUndercover()) {
						uids.add(ua.getUid());
					}
				}
			}
		}
		MapBody mb = new MapBody(SocketHandler.UNDERCOVER_REWARD, SocketHandler.FN_ROOM_ID, r.get_id())
				.append("uids", uids).append("punish", punish).append(SocketHandler.FN_LIMIT, TIME_PUNISHED);
		super.pubRoomMsg(r.get_id(), MsgType.UNDERCOVER, mb);
		return new ReMsg(ReCode.OK);
	}

	public DBObject findWordById(Long id) {
		return getC(DocName.UNDERCOVER_WORD).findOne(new BasicDBObject("_id", id));
	}

	public Page<DBObject> query(String key, Integer status, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(key, status, page, size);
		int count = count(key, status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int count(String key, Integer status) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (StringUtils.isNotBlank(key)) {
			q.put("words", key);
		}
		return getC(DocName.UNDERCOVER_WORD).find(q).count();
	}

	public List<DBObject> find(String key, Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (StringUtils.isNotBlank(key)) {
			q.put("words", key);
		}
		return getC(DocName.UNDERCOVER_WORD).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public ReMsg adds(String words) {
		String[] ww = words.split(";");
		for (String w : ww) {
			add(w);
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg add(String words) {
		String[] c = words.split(",");
		if (c.length == 2) {
			for (int i = 0; i < c.length; i++) {
				c[i] = c[i].trim();
			}
			Arrays.sort(c);
			DBObject q = new BasicDBObject();
			q.put("words", c);
			List<DBObject> dbos = super.getC(DocName.UNDERCOVER_WORD).find(q).toArray();
			Long id = null;
			if (dbos.size() > 0) {
				id = DboUtil.getLong(dbos.get(0), "_id");
			}
			return upsert(id, c, Const.STATUS_OK, 0, null);
		} else {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
	}

	public ReMsg add(String w1, String w2) {
		if (null != w1 && null != w2) {
			long uid = super.getUid();
			if (uid < 1) {
				return new ReMsg(ReCode.NOT_AUTHORIZED);
			}
			String[] c = new String[2];
			c[0] = w1.trim();
			c[1] = w2.trim();
			Arrays.sort(c);
			DBObject q = new BasicDBObject();
			q.put("words", c);
			List<DBObject> dbos = super.getC(DocName.UNDERCOVER_WORD).find(q).toArray();
			if (dbos.size() > 0) {
				return new ReMsg(ReCode.FAIL);
			}
			return upsert(0L, c, Const.STATUS_DEF, uid, super.getUser("nickname").toString());
		}
		return new ReMsg(ReCode.PARAMETER_ERR);
	}

	public ReMsg upsert(Long id, String[] words, Integer status, long uid, String nickname) {
		if (id == null || id < 1L) {
			id = super.getNextId(DocName.UNDERCOVER_WORD);
			UndercoverWord banner = new UndercoverWord(id, words, status, uid, nickname);
			getMongoTemplate().save(banner);
			return new ReMsg(ReCode.OK);
		} else {
			DBObject dbo = new BasicDBObject("updateTime", System.currentTimeMillis());
			dbo.put("words", words);
			if (status != null && status != 0) {
				dbo.put("status", status);
			}
			super.getC(DocName.UNDERCOVER_WORD).update(new BasicDBObject("_id", id), new BasicDBObject("$set", dbo));
			return new ReMsg(ReCode.OK);
		}
		// return new ReMsg(ReCode.FAIL);
	}

	@SuppressWarnings("unchecked")
	private String[] getWords() {
		int c = this.count(null, Const.STATUS_OK);
		List<String> words = null;
		if (c > 0) {
			c = RandomUtil.nextInt(c);
			List<DBObject> dbos = this.find(null, Const.STATUS_OK, c, 1);
			if (dbos.size() > 0) {
				words = (List<String>) dbos.get(0).get("words");
			}
		}
		if (words != null && words.size() == 2) {
			return words.toArray(new String[words.size()]);
		} else {
			return new String[] { "美女", "女神" };
		}
	}

	private Set<UaView> getUavs(Set<UndercoverActor> uas) {
		Set<UaView> uavs = new TreeSet<UaView>();
		if (uas != null) {
			for (UndercoverActor ua : uas) {
				if (UndercoverActor.ROLE_EXECUTOR == ua.getRole()) {
					uavs.add(new UaView(ua));
				}
			}
		}
		return uavs;
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
		if (r.getType() != ActivityType.UNDERCOVER.getType()) {
			log.error(r.get_id() + " " + r.getType());
			return new ReMsg(ReCode.FAIL);
		}
		if (Room.ACTIVITY == r.getStatus() && SocketHandler.KICK == type) {
			return new ReMsg(ReCode.KICK_ERROR);
		}
		UndercoverActor actor = super.getRoomActor(r.get_id(), uid, UndercoverActor.class);
		Undercover uc = super.getActivity(r.get_id(), Undercover.class);
		boolean ucChange = false;
		if (null == actor) {
			return new ReMsg(ReCode.ACTOR_ERROR);
		} else {
			if (Room.OPEN == r.getStatus()) {
				if (UndercoverActor.A_STATUS_READY == actor.getUcStatus()
						|| UndercoverActor.A_STATUS_IN_ROOM == actor.getUcStatus()) {
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
				if (UndercoverActor.A_STATUS_READY == actor.getUcStatus()) {
					uc.removeActor(uid);
					ucChange = true;
				}
				this.delRoomActor(r.get_id(), uid);
				Set<UndercoverActor> as = super.getRoomActors(r.get_id(), UndercoverActor.class);
				super.changeRoomCount(r, as, T_OUTROOM);
			} else if (Actor.ROLE_VIEWER == actor.getRole()) {
				this.delRoomActor(r.get_id(), actor.getUid());
				Set<UndercoverActor> as = super.getRoomActors(r.get_id(), UndercoverActor.class);
				super.changeRoomCount(r, as, T_OUTROOM);
			} else {
				if (SocketHandler.DISCONNECT != type) {
					if (Undercover.STATUS_PUNISH == uc.getStatus()) {
						if (!actor.isReduceCoin()) {
							coinService.forceReduce(uid, CoinLog.UNDERCOVER, uc.get_id(), OUT_COIN, 0, "未完成游戏退出房间惩罚扣币");
							actor.setReduceCoin(true);
						}
						super.delRoomActor(r.get_id(), uid);
						uc.removeActor(uid);
						ucChange = true;
					} else {
						actor.setStatus(Actor.STATUS_OUT_ROOM);
					}
				} else if (SocketHandler.DISCONNECT == type) {
					actor.setStatus(Actor.STATUS_OFFLINE);
				} else if (SocketHandler.KICK == type) {
					super.delRoomActor(r.get_id(), uid);
					uc.removeActor(uid);
				}
				this.saveRoomActor(r.get_id(), actor);
			}
			MapBody mb = new MapBody(type, SocketHandler.FN_USER_ID, actor.getUid())
					.append("nickname", actor.getNickname()).append(SocketHandler.FN_ROOM_ID, r.get_id());
			this.pubRoomMsg(r.get_id(), MsgType.ROOM, mb);
			if (r.getUid() != 0 && SocketHandler.DISCONNECT != type) {
				Long owner = uc.getOwner();
				if (uid == owner) {
					Set<UndercoverActor> as = super.getRoomActors(r.get_id(), UndercoverActor.class);
					UndercoverActor newOwn = this.getNewOwner(as);
					if (newOwn != null) {
						pubOwnerChange(uid, newOwn, r, true, uc);
						ucChange = false;
					}
				}
			}
		}
		if (ucChange) {
			super.saveActivity(r.get_id(), uc);
		}
		int outCount = 0;
		Set<UndercoverActor> actors = super.getRoomActors(r.get_id(), UndercoverActor.class);
		for (UndercoverActor ua : actors) {
			if (ua.getStatus() != UndercoverActor.STATUS_ONLINE) {
				outCount++;
			}
		}
		if (outCount == actors.size()) {
			super.closeRoom(r.get_id());
		}
		// long end = System.currentTimeMillis();
		// log.error("OUT ROOM:" + r.get_id() + ",uid:" + uid + ",end:" + end
		// + ",Diff:" + (end - start));
		return new ReMsg(ReCode.OK);

	}

	public synchronized ReMsg ready(long uid, final long roomId) {
		Undercover uc = super.getActivity(roomId, Undercover.class);
		if (Undercover.STATUS_READY == uc.getStatus()) {// 房间在准备状态下，才能准备
			UndercoverActor a = super.getRoomActor(roomId, uid, UndercoverActor.class);
			if (a != null && Actor.A_STATUS_IN_ROOM == a.getUcStatus()) {
				if (!a.isRobit()) {
					DBObject user = super.userService.findById(uid);
					if (user == null || DboUtil.tryGetInt(user, "coin") < COIN_DEF) {
						return new ReMsg(ReCode.COIN_BALANCE_LOW);
					}
				}

				a.setUcStatus(Actor.A_STATUS_READY);
				super.saveRoomActor(roomId, a);
				uc.addActor(uid);
				super.saveActivity(roomId, uc);

				Set<UndercoverActor> actors = getRoomActors(roomId, UndercoverActor.class);
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
				super.pubRoomMsg(roomId, MsgType.UNDERCOVER, mb);// 发送用户准备通知
				if (start) {
					this.addJob(roomId, TIME_TEMPLATE);
					// start(super.getRoom(roomId), uid, readyCount, actors,
					// uc);
				}
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg inRoom(long uid, Room r, int model, boolean robit) {
		RoomInfo ri = new RoomInfo(ActivityType.UNDERCOVER.getType());
		if (r == null || r.getType() != ActivityType.UNDERCOVER.getType()) {
			log.error(r.get_id() + " " + r.getType());
			ri.setCode(1);
			return new ReMsg(ReCode.FAIL, ri);
		}
		UndercoverActor ua = super.getRoomActor(r.get_id(), uid, UndercoverActor.class);
		if (ua != null) {// 恢复重连
			ua.setStatus(Actor.STATUS_ONLINE);
		} else {// 进入房间
			DBObject user = userService.findById(uid);
			if (user == null) {
				ri.setCode(1);
				return new ReMsg(ReCode.FAIL, ri);
			}
			ua = new UndercoverActor();
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
			ua.setGame("谁是卧底");
		}

		Undercover uc = super.getActivity(r.get_id(), Undercover.class);
		if (ua.isBuyUser() && (null == uc.getOwner() || uc.getOwner() != ua.getUid())) {// 房主不是房管
			if (null != uc.getOwner()) {// 原房管不为空
				UndercoverActor actor = super.getRoomActor(r.get_id(), uc.getOwner(), UndercoverActor.class);
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

		Set<UndercoverActor> as = super.getRoomActors(r.get_id(), UndercoverActor.class);
		// super.kickRobit(as, r);// 踢出机器人
		// as = super.getRoomActors(r.get_id(), UndercoverActor.class);
		super.changeRoomCount(r, as, T_INROOM);
		if (r.getStatus() == Room.OPEN) {
			ri.setStatus(Room.OPEN);
		} else {
			if (ua.getUid() == uc.getUndercover()) {
				ri.setWord(uc.getUcWord());
			} else {
				ri.setWord(uc.getcWord());
			}
			if (uc.getStatus() == Undercover.STATUS_PUNISH) {
				ri.setStatus(3);
			} else {
				ri.setStatus(Room.ACTIVITY);
			}
		}

		if (r.getUid() != 0 && (null == uc.getOwner() || uc.getOwner() == 0)) {
			for (UndercoverActor a : as) {
				uc.setOwner(a.getUid());
				a.setOwner(true);
				super.saveRoomActor(r.get_id(), a);
				super.saveActivity(r.get_id(), uc);
				break;
			}
		}

		ri.setActors(as);
		ri.setPub(r.isPub());
		ri.setHasOwner(r.getUid() == 0 ? false : true);
		ri.setSys(r.isSys());// 系统房间或包间
		MapBody mb = new MapBody(SocketHandler.IN_ROOM, SocketHandler.FN_USER_ID, ua.getUid())
				.append("nickname", ua.getNickname()).append("sex", ua.getSex()).append("avatar", ua.getAvatar())
				.append("actors", as).append("province", ua.getProvince()).append("city", ua.getCity())
				.append("role", ua.getRole()).append(SocketHandler.FN_ROOM_ID, r.get_id());
		long dt = System.currentTimeMillis();
		for (Actor a : as) {
			if (uid != a.getUid() && a.getStatus() == Actor.STATUS_ONLINE) {
				super.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.ROOM, mb, dt);
			}
		}
		// long end = System.currentTimeMillis();
		// log.error("IN ROOM:" + r.get_id() + ",uid:" + uid + ",end:" + end
		// + ",Diff:" + (end - start));
		return new ReMsg(ReCode.OK, ri);
	}

	// @Override
	// public ReMsg userFastCard(long uid, Room r) {
	// // Undercover uc = super.getActivity(r.get_id(), Undercover.class);
	// // if (uc.getStatus() == Undercover.STATUS_PUNISH) {
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
		UndercoverActor uca = super.getRoomActor(r.get_id(), uid, UndercoverActor.class);
		if (uca == null) {
			return new ReMsg(ReCode.OK, 1);
		} else {
			Undercover uc = super.getActivity(r.get_id(), Undercover.class);
			if (uc.getStatus() == Undercover.STATUS_PUNISH) {
				return new ReMsg(ReCode.OK, 1);
			} else {
				return new ReMsg(ReCode.OK, 2);
			}
		}
	}

	@Override
	public ReMsg canInRoom(int type, long actId, final long roomId) {
		Undercover uc = super.getActivity(roomId, Undercover.class);
		if (actId == uc.get_id()) {
			if (uc.getStatus() > Activity.STATUS_READY && uc.getStatus() < Undercover.STATUS_PUNISH)
				return new ReMsg(ReCode.OK, 1);
		}
		return new ReMsg(ReCode.OK, 2);
	}

	public void saveUndercoverSpeakLog(String txt, String word, int stauts) {
		DBObject dbo = super.getC(DocName.UNDERCOVER_SPEAK_LOG)
				.findOne(new BasicDBObject("word", word).append("speak", txt));
		if (dbo == null) {
			UndercoverSpeakLog dl = new UndercoverSpeakLog(super.getNextId(DocName.UNDERCOVER_SPEAK_LOG), word, txt,
					stauts);
			super.getMongoTemplate().save(dl);
		}
	}

	public void saveUndercoverSpeakLogs(String speaks, String word) {
		String[] speakarr = speaks.split(";");
		for (String speak : speakarr) {
			saveUndercoverSpeakLog(speak, word, Const.STATUS_OK);
		}
	}

	public DBObject findUndercoverSpeakById(Long id) {
		return super.getC(DocName.UNDERCOVER_SPEAK_LOG).findOne(new BasicDBObject("_id", id));
	}

	public Page<DBObject> queryUndercoverSpeakLogs(String word, Integer status, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findUndercoverSpeakLogs(word, status, page, size);
		int count = countUndercoverSpeakLogs(word, status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countUndercoverSpeakLogs(String word, Integer status) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(word)) {
			q.put("word", word);
		}
		if (status != null && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.UNDERCOVER_SPEAK_LOG).find(q).count();
	}

	public void delUndercoverSpeakLogById(long id) {
		super.getC(DocName.UNDERCOVER_SPEAK_LOG).remove(new BasicDBObject("_id", id));
	}

	public void validUndercoverSpeakLogById(long id, int status) {
		super.getC(DocName.UNDERCOVER_SPEAK_LOG).update(new BasicDBObject("_id", id),
				new BasicDBObject("$set", new BasicDBObject("status", status)));
	}

	// 审核词语
	public void undercoverValid(Long id, int status) {
		super.getC(DocName.UNDERCOVER_WORD).update(new BasicDBObject("_id", id),
				new BasicDBObject("$set", new BasicDBObject("status", status)));
		DBObject object = super.getC(DocName.UNDERCOVER_WORD).findOne(new BasicDBObject("_id", id));
		UndercoverWord word = DboUtil.toBean(object, UndercoverWord.class);
		if (word.getUid() > 0) {
			if (status == 3) {
				coinService.addCoin(word.getUid(), CoinLog.VALIDWORD_OK, CoinLog.UNDERCOVER, 100, 0,
						"谁是卧底<" + word.getWords()[0] + "," + word.getWords()[1] + ">审核通过加币");
				messageService.imMsgHandler(Const.SYSTEM_ID, word.getUid(),
						"您为谁是卧底贡献的题目\"" + word.getWords()[0] + ";" + word.getWords()[1] + "\"已通过审核，奖励您100金币，感谢您的支持!",
						null, null);
				// messageService.easeMsgHandler(Const.SYSTEM_ID, word.getUid(),
				// "您为谁是卧底贡献的题目\"" + word.getWords()[0] + ";" +
				// word.getWords()[1] + "\"已通过审核，奖励您100金币，感谢您的支持!", null, null);
			} else if (status < 0) {
				messageService.imMsgHandler(Const.SYSTEM_ID, word.getUid(),
						"您为谁是卧底贡献的题目\"" + word.getWords()[0] + ";" + word.getWords()[1] + "\"未通过审核,感谢您的支持!", null,
						null);
				// messageService.easeMsgHandler(Const.SYSTEM_ID, word.getUid(),
				// "您为谁是卧底贡献的题目\"" + word.getWords()[0] + ";" +
				// word.getWords()[1] + "\"未通过审核,感谢您的支持!", null, null);
			}
		}
	}

	// 删除词语
	public void delUndercoverById(Long id) {
		super.getC(DocName.UNDERCOVER_WORD).remove(new BasicDBObject("_id", id));
	}

	private UndercoverActor getNewOwner(Set<UndercoverActor> as) {
		for (UndercoverActor a : as) {
			if (Actor.STATUS_ONLINE == a.getStatus()) {
				return a;
			}
		}
		return null;
	}

	private void pubOwnerChange(long uid, UndercoverActor a, Room r, boolean withMessage, Undercover uc) {
		uc.setOwner(a.getUid());
		a.setOwner(true);
		super.saveRoomActor(r.get_id(), a);
		super.saveActivity(r.get_id(), uc);
		if (withMessage) {
			MapBody mb = new MapBody(SocketHandler.CHANGE_OWNER).append("lastOwner", uid).append("curOwner",
					uc.getOwner());
			super.pubRoomMsg(r.get_id(), MsgType.ROOM, mb);
		}
	}

	public ReMsg checkboxSift(String ids, int status) {
		if (StringUtils.isNotBlank(ids)) {
			String[] rids = ids.split(",");
			List<Long> idl = new ArrayList<>();
			for (String e : rids) {
				idl.add(Long.parseLong(e));
			}
			BasicDBObject q = new BasicDBObject();
			q.put("_id", new BasicDBObject("$in", idl));
			if (status == 0) {
				super.getC(DocName.UNDERCOVER_SPEAK_LOG).remove(q);
				return new ReMsg(ReCode.OK);
			}
			BasicDBObject u = new BasicDBObject();
			u.put("$set", new BasicDBObject("status", status));
			super.getC(DocName.UNDERCOVER_SPEAK_LOG).update(q, u, false, true);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}
}
