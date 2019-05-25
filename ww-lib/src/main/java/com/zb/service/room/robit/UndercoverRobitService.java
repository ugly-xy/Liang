package com.zb.service.room.robit;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.room.Room;
import com.zb.models.room.activity.UndercoverActor;
import com.zb.models.room.activity.Undercover;
import com.zb.service.room.UndercoverService;

@Service
public class UndercoverRobitService extends ActivityRobitService {
	static final Logger log = LoggerFactory.getLogger(UndercoverRobitService.class);

	@Autowired
	UndercoverService undercoverService;

	@Autowired
	UserRobitService userRobitService;
	
	private static final int ROBIT_TIME = 3000;

	@Override
	public ReMsg handle(Room r, long uid) {
		Undercover uc = super.getActivity(r.get_id(), Undercover.class);
		if (Undercover.STATUS_READY == uc.getStatus()) {
			UndercoverActor dsa = super.getRoomActor(r.get_id(), uid, UndercoverActor.class);
			Set<UndercoverActor> dsas = super.getRoomActors(r.get_id(), UndercoverActor.class);
			int rcnt = 0;
			for (UndercoverActor cdsa : dsas) {
				if (cdsa.isRobit()) {
					rcnt++;
				}
			}
			int cr = RandomUtil.nextInt(100);
			int roomCnt = super.getActivityRoomCount(r.getType(), Room.ACTIVITY);// 当前游戏的房间数
			if (dsa == null) {
				if (dsas.size() < 5) {
					if (rcnt == dsas.size()) {
						undercoverService.closeRoom(r.get_id());
					} else if (rcnt < 3) {
						if (rcnt < 1) {// 没有机器人
							if (cr < 100 - roomCnt / 2) {// 第一个机器人进入房间的概率
								undercoverService.inRoom(uid, r, 0,true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						} else if (rcnt < 2) {// 1个机器人
							if (cr < 50 - roomCnt * 5) {// 第二个机器人进入房间的概率
								undercoverService.inRoom(uid, r, 0,true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						} else {// 2个机器人
							if (cr < 10 - roomCnt * 4) {// 第三个机器人进入房间的概率
								undercoverService.inRoom(uid, r,0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						}
					}
				}
			} else {
				if (dsa.getUcStatus() == UndercoverActor.A_STATUS_IN_ROOM) {
					if (rcnt < 2) {// 1个机器人
						int rc = 100 - roomCnt * 5;
						if (rc < 0) {
							rc = 0;
						}
						if (cr < rc) {// 准备概率
							ReMsg rm = undercoverService.ready(uid, r.get_id());
							if (rm.getCode() == ReCode.OK.reCode()) {
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							} else {
								undercoverService.outRoom(uid, r);
							}
						} else {
							if (rc < rc + roomCnt * 4 / 3 + 2) {// 退出概率
								undercoverService.outRoom(uid, r);
							} else {// 停留概率
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						}
					} else {// 多个机器人
						int rc = 90 - roomCnt * 9;
						if (rc < 0) {
							rc = 0;
						}
						if (cr < rc) {// 准备概率
							ReMsg rm = undercoverService.ready(uid, r.get_id());
							if (rm.getCode() == ReCode.OK.reCode()) {
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							} else {
								undercoverService.outRoom(uid, r);
							}
						} else {
							if (cr < rc + roomCnt / 2 + 8) {// 退出房间概率
								undercoverService.outRoom(uid, r);
							} else {// 停留概率
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						}
					}
				} else {// 已准备机器人
					if (rcnt <2) {//1个机器人
						if (cr < 3 + roomCnt * 3 / 2) {// 多个机器人推出房间概率
							undercoverService.outRoom(uid, r);
						} else {
							userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
						}
					} else {//多个机器人
						if (cr < 15 + roomCnt * 3 / 2) {// 一个机器人退出房间概率
							undercoverService.outRoom(uid, r);
						} else {
							userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
						}
					}
				}
			}
		} else if (Undercover.STATUS_SPEAK == uc.getStatus()) {// 发言阶段
			if (uc.getActors().contains(uid)) {
				if (!uc.getSpeaks().contains(uid)) {
					UndercoverActor dsa = super.getRoomActor(r.get_id(), uid, UndercoverActor.class);
					if (!dsa.isDead()) {
						String speak = null;
						if (uc.getUndercover() == uid) {
							speak = getSpeak(uc.getUcWord());
						} else {
							speak = getSpeak(uc.getcWord());
						}
						if (speak != null) {
							undercoverService.speak(uid, r.get_id(), speak);
						}
					}
				}
				userRobitService.addJob(r.get_id(), uid, 10000);
			}
		} else if (Undercover.STATUS_VOTE == uc.getStatus()) {// 投票阶段
			if (uc.getActors().contains(uid)) {
				UndercoverActor dsa = super.getRoomActor(r.get_id(), uid, UndercoverActor.class);
				if (!dsa.isDead()) {
					long duid = 0;
					Set<UndercoverActor> uas = super.getRoomActors(r.get_id(), UndercoverActor.class);
					UndercoverActor[] uasa = uas.toArray(new UndercoverActor[uas.size()]);
					for (;;) {
						UndercoverActor ua = uasa[RandomUtil.nextInt(uas.size())];
						if (ua.getRole() == UndercoverActor.ROLE_EXECUTOR && !ua.isDead()) {
							if (duid != uid) {
								duid = ua.getUid();
								break;
							}
						}
					}
					undercoverService.vote(r.get_id(), uid, duid);
				}
				userRobitService.addJob(r.get_id(), uid, 10000);
			}
		} else if (Undercover.STATUS_PK_SPEAK == uc.getStatus()) {// pk发言阶段
			if (!uc.getPks().contains(uid)) {
				// UndercoverActor dsa = super.getRoomActor(r.get_id(), uid,
				// UndercoverActor.class);
				String speak = null;
				if (uc.getUndercover() == uid) {
					speak = getSpeak(uc.getUcWord());
				} else {
					speak = getSpeak(uc.getcWord());
				}
				if (speak != null) {
					undercoverService.pkSpeak(r.get_id(), uid, speak);
				}
			}
			userRobitService.addJob(r.get_id(), uid, 10000);
		} else if (Undercover.STATUS_PK_VOTE == uc.getStatus()) {// pk投票阶段
			if (uc.getActors().contains(uid)) {
				UndercoverActor dsa = super.getRoomActor(r.get_id(), uid, UndercoverActor.class);
				if (!dsa.isDead()) {
					long duid = 0;
					for (Long ua : uc.getPks()) {
						if (ua != uid) {
							duid = ua;
							break;
						}
					}
					undercoverService.pkVote(r.get_id(), uid, duid);
				}
				userRobitService.addJob(r.get_id(), uid, 10000);
			}
		} else if (Undercover.STATUS_PK_DICE == uc.getStatus()) {// 摇骰子
			if (uc.getActors().contains(uid)) {
				if (uc.getPks().contains(uid)) {
					undercoverService.dice(r.get_id(), uid);
				}
				userRobitService.addJob(r.get_id(), uid, 10000);
			}
		} else {
			if (uc.getActors().contains(uid)) {
				userRobitService.addJob(r.get_id(), uid, 10000);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	public String getSpeak(String word) {
		DBCursor dbc = super.getC(DocName.UNDERCOVER_SPEAK_LOG).find(new BasicDBObject("word", word));
		int c = dbc.count();
		if (c > 0) {
			int s = RandomUtil.nextInt(c);
			DBObject dbo = dbc.skip(s).limit(1).toArray().get(0);
			return DboUtil.getString(dbo, "speak");
		}
		return null;
	}
}
