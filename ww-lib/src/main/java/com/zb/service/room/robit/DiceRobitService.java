package com.zb.service.room.robit;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.common.Constant.ReCode;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.room.Room;
import com.zb.models.room.activity.DiceActor;
import com.zb.models.room.activity.Dice;
import com.zb.service.room.DiceService;

@Service
public class DiceRobitService extends ActivityRobitService {
	static final Logger log = LoggerFactory.getLogger(DiceRobitService.class);

	@Autowired
	DiceService diceService;

	private static final int ROBIT_TIME = 3200;
	private static final int[] coins = { 10, 20, 50, 100 };

	@Override
	public ReMsg handle(Room r, long uid) {
		Dice uc = super.getActivity(r.get_id(), Dice.class);
		if (Dice.STATUS_READY == uc.getStatus()) {
			DiceActor dsa = super.getRoomActor(r.get_id(), uid, DiceActor.class);
			int cr = RandomUtil.nextInt(100);
			Set<DiceActor> dsas = super.getRoomActors(r.get_id(), DiceActor.class);
			int rcnt = 0;
			for (DiceActor cdsa : dsas) {
				if (cdsa.isRobit()) {
					rcnt++;
				}
			}
			int roomCnt = super.getActivityRoomCount(r.getType(), Room.ACTIVITY);// 当前游戏的房间数
			if (dsa == null) {
				if (dsas.size() < 5) {
					if (rcnt == dsas.size()) {
						diceService.closeRoom(r.get_id());
					} else if (rcnt < 3) {
						if (rcnt < 1) {// 没有机器人
							if (cr < 100 - roomCnt / 2) {// 第一个机器人进入房间的概率
								diceService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						} else if (rcnt < 2) {// 1个机器人
							if (cr < 50 - roomCnt * 5) {// 第二个机器人进入房间的概率
								diceService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						} else {// 2个机器人
							if (cr < 10 - roomCnt * 4) {// 第三个机器人进入房间的概率
								diceService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						}
					}
				}
			} else {
				if (dsa.getUcStatus() == DiceActor.A_STATUS_IN_ROOM) {
					if (rcnt < 2) {// 1个机器人
						int rc = 100 - roomCnt * 5;
						if (rc < 0) {
							rc = 0;
						}
						if (cr < rc) {// 准备概率
							ReMsg rm = diceService.ready(uid, r.get_id());
							if (rm.getCode() == ReCode.OK.reCode()) {
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							} else {
								diceService.outRoom(uid, r);
							}
						} else {
							if (rc < rc + roomCnt * 4 / 3 + 2) {// 退出概率
								diceService.outRoom(uid, r);
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
							ReMsg rm = diceService.ready(uid, r.get_id());
							if (rm.getCode() == ReCode.OK.reCode()) {
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							} else {
								diceService.outRoom(uid, r);
							}
						} else {
							if (cr < rc + roomCnt / 2 + 8) {// 退出房间概率
								diceService.outRoom(uid, r);
							} else {// 停留概率
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						}
					}
				} else {// 已准备机器人
					if (rcnt < 2) {// 1个机器人
						if (cr < 3 + roomCnt * 3 / 2) {// 多个机器人推出房间概率
							diceService.outRoom(uid, r);
						} else {
							userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
						}
					} else {// 多个机器人
						if (cr < 15 + roomCnt * 3 / 2) {// 一个机器人退出房间概率
							diceService.outRoom(uid, r);
						} else {
							userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
						}
					}
				}
			}
		} else if (Dice.STATUS_DICING == uc.getStatus()) {// 摇骰子阶段
			if (uc.getActors().containsKey(uid)) {
				Double et = super.getJobEndTime(r.get_id());
				if (et != null && et - System.currentTimeMillis() < 20000) {
					diceService.confirmDice(r.get_id(), uid);
					userRobitService.addJob(r.get_id(), uid, (RandomUtil.nextInt(9) + 4) * 1000);
				} else {
					userRobitService.addJob(r.get_id(), uid, (RandomUtil.nextInt(3) + 3) * 1000);
				}
			}
		} else if (Dice.STATUS_GUESSING == uc.getStatus()) {// 叫阶段
			if (uc.getActors().containsKey(uid)) {
				Double et = super.getJobEndTime(r.get_id());
				if (et != null && et - System.currentTimeMillis() < 22000) {
					long[][] arr = uc.getStateMap();
					for (int i = 0; i < arr[1].length; i++) {
						if (arr[1][i] == DiceActor.A_STATUS_GUESSING) {
							long curUid = 0;
							if (i == arr[1].length - 1) {
								curUid = arr[0][0];
							} else {
								curUid = arr[0][i + 1];
							}
							if (curUid == uid) {
								DiceActor da = super.getRoomActor(r.get_id(), uid, DiceActor.class);
								if (da.isRobit()) {
									int coin = 10;
									int count = uc.getDiceTotalNum();// 数量
									int num = uc.getDiceCount();// 点数
									boolean brag = true;
									if (num == 0 && count == 0) {
										num = 2;
										count = 3;
									} else {
										Map<Long, int[]> dices = uc.getDices();
										int curCount = 0;
										for (int[] dice : dices.values()) {
											for (int curNum : dice) {
												if (curNum == 1 || curNum == num) {
													curCount++;
												}
											}
										}
										int rn = RandomUtil.nextInt(100);
										if (curCount <= count) {
											if (uc.getActors().size() * 3 < count) {
												if (rn < 90) {
													brag = false;
												}
											} else if (uc.getActors().size() * 5 / 2 < count) {
												if (rn < 70) {
													brag = false;
												}
											} else if (uc.getActors().size() * 5 / 6 < count) {
												if (rn < 45) {
													brag = false;
												}
											} else {
												if (rn < 30) {
													brag = false;
												}
											}
										} else {
											if (uc.getActors().size() * 3 < count) {
												if (rn < 50) {
													brag = false;
												}
											} else if (uc.getActors().size() * 5 / 2 < count) {
												if (rn < 30) {
													brag = false;
												}
											} else if (uc.getActors().size() * 5 / 6 < count) {
												if (rn < 15) {
													brag = false;
												}
											}
										}
										if (brag) {
											if (num == 6) {
												num = 2;
												rn = RandomUtil.nextInt(100);
												if (rn > 90) {
													count = count + RandomUtil.nextInt(3);
													coin = coins[RandomUtil.nextInt(2)];
												} else if (rn > 70) {
													count = count + RandomUtil.nextInt(2);
													coin = coins[RandomUtil.nextInt(1)];
												} else {
													count++;
												}
											} else {
												num++;
												rn = RandomUtil.nextInt(100);
												if (rn > 90) {
													count = count + RandomUtil.nextInt(5);
													coin = coins[RandomUtil.nextInt(4)];
												} else if (rn > 70) {
													count = count + RandomUtil.nextInt(3);
													coin = coins[RandomUtil.nextInt(3)];
												}
											}
											diceService.bragDices(r.get_id(), uid, count, num, coin);
										} else {
											diceService.showDice(r.get_id(), uid);
										}
									}
									// if (num == 0 && count == 0) {
									// num = 2;
									// count = 3;
									// } else {
									// if (num == 6) {
									// count++;
									// num = 2;
									// } else {
									// num++;
									// int rn = RandomUtil.nextInt(100);
									// if (rn > 90) {
									// count = count + RandomUtil.nextInt(5);
									// coin = coin * RandomUtil.nextInt(5);
									// } else if (rn > 70) {
									// count = count + RandomUtil.nextInt(3);
									// coin = coin * RandomUtil.nextInt(3);
									// }
									// }
									// }
									// if (uc.getActors().size() * 3 < count) {
									// diceService.showDice(r.get_id(), uid);
									// } else if (uc.getActors().size() * 5 / 2
									// < count) {
									// if (RandomUtil.nextInt(10) > 5) {
									// diceService.showDice(r.get_id(), uid);
									// } else {
									// diceService.bragDices(r.get_id(), uid,
									// count, num, coin);
									// }
									// } else if (uc.getActors().size() * 2 <
									// count) {
									// if (RandomUtil.nextInt(20) > 2) {
									// diceService.bragDices(r.get_id(), uid,
									// count, num, coin);
									// } else {
									// diceService.showDice(r.get_id(), uid);
									// }
									// } else {
									// diceService.bragDices(r.get_id(), uid,
									// count, num, coin);
									// }
								}
								break;
							}
						}
					}
					userRobitService.addJob(r.get_id(), uid, (RandomUtil.nextInt(3) + 3) * 1000);
				} else {
					userRobitService.addJob(r.get_id(), uid, (RandomUtil.nextInt(2) + 2) * 1000);
				}
			}
		} else {
			if (uc.getActors().containsKey(uid)) {
				userRobitService.addJob(r.get_id(), uid, 10000);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

}
