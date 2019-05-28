package com.zb.service.room.robit;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.room.Room;
import com.zb.models.room.activity.WerewolfKill;
import com.zb.models.room.activity.WerewolfKillActor;
import com.zb.service.room.WerewolfKillService;

@Service
public class WerewolfKillRobitService extends ActivityRobitService {
	static final Logger LOGGER = LoggerFactory.getLogger(WerewolfKillRobitService.class);

	@Autowired
	WerewolfKillService werewolfKillService;
	@Autowired
	UserRobitService userRobitService;

	private static final int ROBIT_TIME = 3000;

	@Override
	public ReMsg handle(Room r, long uid) {
		WerewolfKill uc = super.getActivity(r.get_id(), WerewolfKill.class);
		if (WerewolfKill.STATUS_READY == uc.getStatus()) {
			WerewolfKillActor dsa = super.getRoomActor(r.get_id(), uid, WerewolfKillActor.class);
			Set<WerewolfKillActor> dsas = super.getRoomActors(r.get_id(), WerewolfKillActor.class);
			int rcnt = 0;
			for (WerewolfKillActor cdsa : dsas) {
				if (cdsa.isRobit()) {
					rcnt++;
				}
			}
			int cr = RandomUtil.nextInt(100);
			int roomCnt = super.getActivityRoomCount(r.getType(), Room.ACTIVITY);// 当前游戏的房间数
			if (dsa == null) {
				if (dsas.size() < 5) {
					if (rcnt == dsas.size()) {
						werewolfKillService.closeRoom(r.get_id());
					} else if (rcnt < 3) {
						if (rcnt < 1) {
							if (cr < 100 - roomCnt / 2) {
								werewolfKillService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						} else if (rcnt < 2) {
							if (cr < 50 - roomCnt * 5) {
								werewolfKillService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						} else {
							if (cr < 20 - roomCnt * 4) {
								werewolfKillService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						}
					}
				}
			} else {
				if (rcnt < 2) {
					if (cr < 3 + roomCnt * 3 / 2) {// 多个机器人推出房间概率
						werewolfKillService.outRoom(uid, r);
					} else {
						userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
					}
				} else {
					if (cr < 15 + roomCnt * 3 / 2) {// 一个机器人退出房间概率
						werewolfKillService.outRoom(uid, r);
					} else {
						userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
					}
				}
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReMsg outRoom(long uid, Room r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReMsg disconnectRoom(long uid, Room r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReMsg kick(long uid, Room r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReMsg getActorStatus(long uid, Room r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReMsg canInRoom(int type, long actId, long roomId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReMsg inRoom(long uid, Room r, int model, boolean robit) {
		// TODO Auto-generated method stub
		return null;
	}

}
