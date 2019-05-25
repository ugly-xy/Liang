package com.zb.service.room.robit;

import java.util.Map;
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
import com.zb.models.room.activity.Texas;
import com.zb.models.room.activity.TexasActor;
import com.zb.service.room.TexasService;

@Service
public class TexasRobitService extends ActivityRobitService {
	static final Logger log = LoggerFactory.getLogger(TexasRobitService.class);

	private static final int ROBIT_TIME = 2200;
	@Autowired
	TexasService texasService;

	static final int FILL_TYPE_ADDFILL = TexasService.FILL_TYPE_ADDFILL;// 加注
	static final int FILL_TYPE_FILL = TexasService.FILL_TYPE_FILL;// 跟注
	static final int FILL_TYPE_GIVEUP = TexasService.FILL_TYPE_GIVEUP;// 弃牌
	static final int FILL_TYPE_CHECK = TexasService.FILL_TYPE_CHECK;// 让牌
	static final int FILL_TYPE_PUTALL = TexasService.FILL_TYPE_PUTALL;// 梭哈

	@Override
	public ReMsg handle(Room r, long uid) {
		Texas uc = super.getActivity(r.get_id(), Texas.class);
		if (Texas.STATUS_READY == uc.getStatus()) {// 准备阶段
			TexasActor dsa = super.getRoomActor(r.get_id(), uid, TexasActor.class);
			int rcnt = 0;// 机器人数量
			Set<TexasActor> dsas = super.getRoomActors(r.get_id(), TexasActor.class);
			for (TexasActor cdsa : dsas) {
				if (cdsa.isRobit()) {
					rcnt++;
				}
			}
			if (rcnt == dsas.size()) {// 房间里全是机器人
				this.closeRoom(r.get_id());
				return null;
			}
			int texasAccessCoin = texasService.getTexasAccessCoin(uc.getType());
			int roomCnt = super.getActivityRoomCount(r.getType(), Room.ACTIVITY);// 当前游戏的房间数
			int cr = RandomUtil.nextInt(100);
			if (dsa == null) {// 还没进入房间
				if (RandomUtil.nextInt(5) > 2) {
					if (DboUtil.tryGetInt(userService.findById(uid), "coin") < texasAccessCoin) {
						if (texasService.findTexasLog(uid) == null) {// 从没玩过德州
							coinService.addCoin(uid, CoinLog.IN_ROBIT_FLOWER, 0,
									texasAccessCoin * (RandomUtil.nextInt(10) + 2), super.getUid(), "德州机器人加币");
						}
					}
				}
				if (dsas.size() < 5) {// 房间总人数小于5个
					if (rcnt < 3) {
						if (rcnt < 1) {// 没有机器人
							if (cr < 100 - roomCnt / 2) {// 第一个机器人进入房间的概率
								texasService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						} else if (rcnt < 2) {// 1个机器人
							if (cr < 50 - roomCnt * 5) {// 第二个机器人进入房间的概率
								texasService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						} else {// 2个机器人
							if (cr < 10 - roomCnt * 4) {// 第三个机器人进入房间的概率
								texasService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						}
					}
				}
			} else {
				long[] table = uc.getSeatUserTable();
				if (dsa.getSeat() == -1) {// 不在座位上的
					if (DboUtil.tryGetInt(userService.findById(uid), "coin") < texasAccessCoin) {// 没钱被迫在树上
						if (RandomUtil.nextInt(5) > 3) {// 退出房间概率
							texasService.outRoom(uid, r);
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
									rm = texasService.getDownTheTree(uid, r.get_id(), i);
									break;
								}
							}
							if (rm.getCode() == ReCode.OK.reCode()) {
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							} else {
								texasService.outRoom(uid, r);
							}
						} else {
							if (rc < rc + roomCnt * 4 / 3 + 2) {// 退出概率
								texasService.outRoom(uid, r);
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
									rm = texasService.getDownTheTree(uid, r.get_id(), i);
									break;
								}
							}
							if (rm.getCode() == ReCode.OK.reCode()) {
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							} else {
								texasService.outRoom(uid, r);
							}
						} else {
							if (cr < rc + roomCnt / 2 + 8) {// 退出房间概率
								texasService.outRoom(uid, r);
							} else {// 停留概率
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						}
					}
				} else {// 已准备机器人
					int playCnt = dsa.getPlayCnt();
					if (cr < playCnt * 9 - 10) {// 根据场次判断退出房间
						texasService.outRoom(uid, r);
					} else if (rcnt < 2) {// 1个机器人
						if (cr < 3 + roomCnt * 3 / 2) {// 多个机器人退出房间概率
							texasService.outRoom(uid, r);
						} else {
							userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
						}
					} else {// 多个机器人
						if (cr < 15 + roomCnt * 3 / 2) {// 一个机器人退出房间概率
							texasService.outRoom(uid, r);
						} else {
							userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
						}
					}
				}
			}
		} else if (Texas.STATUS_BET == uc.getStatus()) {
			if (uc.getSpeakerTable().get(0) == uid) {// 下注阶段 该我下注
				int boutCoin = uc.getBoutCoin();// 当前回合最大金钱数
				Map<Long, Integer> userBetCoins = uc.getUserBetCoins();
				Map<Long, Integer> userAllCoins = uc.getUserAllCoins();
				int coin = userAllCoins.get(uid);
				int baseCoin = uc.getBaseCoin();// 基础金币
				// TODO 未来可能会加入根据底池金币的机器人判断
				int coinPool = uc.getCoinPool();// 底池金币
				int needCoin = 0;
				if (userBetCoins.size() != 0) {// 跟上本回合的加注金币
					needCoin = userBetCoins.get(uid) == null ? boutCoin : boutCoin - userBetCoins.get(uid);
				}
				boolean isPlay = false;
				int nextInt = RandomUtil.nextInt(10);
				if (nextInt > 7 && !isPlay) {// 加注
					if (coin - needCoin - baseCoin >= 0) {
						texasService.filling(uid, r.get_id(), FILL_TYPE_ADDFILL, baseCoin);
						isPlay = true;
					}
				}
				if (nextInt > 4 && !isPlay) {// 跟注
					if (coin - needCoin >= 0) {
						texasService.filling(uid, r.get_id(), FILL_TYPE_FILL, baseCoin);
						isPlay = true;
					}
				}
				if (!isPlay) {// 弃牌
					texasService.filling(uid, r.get_id(), FILL_TYPE_GIVEUP, baseCoin);
				}
			}
			userRobitService.addJob(r.get_id(), uid, robitTime());
		} else {
			if (uc.getActors().keySet().contains(uid)) {
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
