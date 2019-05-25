package com.zb.service.room.robit;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.activity.Cow;
import com.zb.models.room.activity.CowActor;
import com.zb.service.room.CowService;

@Service
public class CowRobitService extends ActivityRobitService {
	static final Logger log = LoggerFactory.getLogger(CowRobitService.class);

	private static final int ROBIT_TIME = 2200;
	@Autowired
	CowService cowService;

	@Override
	public ReMsg handle(Room r, long uid) {
		Cow uc = super.getActivity(r.get_id(), Cow.class);
		if (Cow.STATUS_READY == uc.getStatus()) {// 准备阶段
			CowActor dsa = super.getRoomActor(r.get_id(), uid, CowActor.class);
			int rcnt = 0;// 机器人数量
			Set<CowActor> dsas = super.getRoomActors(r.get_id(), CowActor.class);
			for (CowActor cdsa : dsas) {
				if (cdsa.isRobit()) {
					rcnt++;
				}
			}
			if (rcnt == dsas.size()) {// 房间里全是机器人
				this.closeRoom(r.get_id());
				return null;
			}
			int cowAccessCoin = cowService.getCowAccessCoin(uc.getType());
			int roomCnt = super.getActivityRoomCount(r.getType(), Room.ACTIVITY);// 当前游戏的房间数
			int cr = RandomUtil.nextInt(100);
			if (dsa == null) {// 还没进入房间
				if (RandomUtil.nextInt(5) > 2) {
					if (DboUtil.tryGetInt(userService.findById(uid), "coin") < cowAccessCoin) {
						if (cowService.findCowLog(uid) == null) {// 从没玩过牛牛
							coinService.addCoin(uid, CoinLog.IN_ROBIT_FLOWER, 0,
									cowAccessCoin * (RandomUtil.nextInt(10) + 2), super.getUid(), "牛牛机器人加币");
						}
					}
				}
				if (dsas.size() < 5) {// 房间总人数小于5个
					if (rcnt < 3) {
						if (rcnt < 1) {// 没有机器人
							if (cr < 100 - roomCnt / 2) {// 第一个机器人进入房间的概率
								cowService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						} else if (rcnt < 2) {// 1个机器人
							if (cr < 50 - roomCnt * 5) {// 第二个机器人进入房间的概率
								cowService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						} else {// 2个机器人
							if (cr < 10 - roomCnt * 4) {// 第三个机器人进入房间的概率
								cowService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						}
					}
				}
			} else {
				long[] table = uc.getSeatUserTable();
				if (dsa.getSeat() == -1) {// 不在座位上的
					if (DboUtil.tryGetInt(userService.findById(uid), "coin") < cowAccessCoin) {// 没钱被迫在树上
						if (RandomUtil.nextInt(5) > 3) {// 退出房间概率
							cowService.outRoom(uid, r);
							return null;
						}
					}
					if (rcnt < 3) {// 1个机器人
						int rc = 100 - roomCnt * 5;
						if (rc < 0) {
							rc = 0;
						}
						if (cr < rc) {// 准备概率
							ReMsg rm = null;
							for (int i = 0; i < 6; i++) {
								if (table[i] == 0) {
									rm = cowService.getDownTheTree(uid, r.get_id(), i);
									break;
								}
							}
							if (rm.getCode() == ReCode.OK.reCode()) {
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							} else {
								cowService.outRoom(uid, r);
							}
						} else {
							if (rc < rc + roomCnt * 4 / 3 + 2) {// 退出概率
								cowService.outRoom(uid, r);
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
							ReMsg rm = null;
							for (int i = 0; i < 6; i++) {
								if (table[i] == 0) {
									rm = cowService.getDownTheTree(uid, r.get_id(), i);
									break;
								}
							}
							if (rm.getCode() == ReCode.OK.reCode()) {
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							} else {
								cowService.outRoom(uid, r);
							}
						} else {
							if (cr < rc + roomCnt / 2 + 8) {// 退出房间概率
								cowService.outRoom(uid, r);
							} else {// 停留概率
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						}
					}
				} else {// 已准备机器人
					int playCnt = dsa.getPlayCnt();
					if (cr < playCnt * 9 - 10) {// 根据场次判断退出房间
						cowService.outRoom(uid, r);
					} else if (rcnt < 2) {// 1个机器人
						if (cr < 3 + roomCnt * 3 / 2) {// 多个机器人退出房间概率
							cowService.outRoom(uid, r);
						} else {
							userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
						}
					} else {// 多个机器人
						if (cr < 15 + roomCnt * 3 / 2) {// 一个机器人退出房间概率
							cowService.outRoom(uid, r);
						} else {
							userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
						}
					}
				}
			}
		} else if (Cow.STATUS_ROBBANKER == uc.getStatus()) {
			// 抢庄阶段
			if (uc.getActors().contains(uid)) {
				List<Long> table = uc.getSpeakerTable();
				if (table.size() > 0 && table.get(0) == uid) {// 该我抢庄
					long dealer = uc.getDealer();
					if (dealer != 0) {
						int fold = uc.getUserFold(dealer);
						int nextFold = RandomUtil.nextInt(4);
						if (fold == 1) {
							if (nextFold == 3) {
								cowService.hoging(uid, r.get_id(), 3);
							} else if (nextFold > 0) {
								cowService.hoging(uid, r.get_id(), 2);
							} else {
								cowService.hoging(uid, r.get_id(), 0);
							}
						} else if (fold == 2) {
							if (nextFold == 3) {
								cowService.hoging(uid, r.get_id(), 3);
							} else {
								cowService.hoging(uid, r.get_id(), 0);
							}
						} else {
							cowService.hoging(uid, r.get_id(), 0);
						}
					} else {// 还没人喊庄
						cowService.hoging(uid, r.get_id(), RandomUtil.nextInt(2) + 1);
					}
					userRobitService.addJob(r.get_id(), uid, robitTime());
				} else {
					userRobitService.addJob(r.get_id(), uid, robitTime());
				}
			}
		} else if (Cow.STATUS_FILLER == uc.getStatus()) {
			// 加注阶段
			if (uc.getActors().contains(uid)) {
				List<Long> table = uc.getSpeakerTable();
				if (table.contains(uid)) {// 该我加注 随机加注
					int fold = RandomUtil.nextInt(10);
					if (fold > 7) {// 2/10
						fold = 5;
					} else if (fold > 4) {// 3/10
						fold = 3;
					} else {// 5/10
						fold = 1;
					}
					cowService.filling(uid, r.get_id(), fold);
					userRobitService.addJob(r.get_id(), uid, robitTime());
				} else {
					userRobitService.addJob(r.get_id(), uid, robitTime());
				}
			}
		} else {
			if (uc.getActors().contains(uid)) {
				userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	public int robitTime() {
		return RandomUtil.nextInt(6000) + 2000;
	}

	public void closeRoom(final long roomId) {
		Set<Actor> actors = super.getRoomActors(roomId);
		for (Actor actor : actors) {
			if (actor.isRobit()) {// 删除机器人job
				super.delRoomUserRobitJob(roomId, actor.getUid());
			}
		}
		super.closeRoom(roomId);
	}
}
