package com.zb.service.room.robit;

import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.MapUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.room.Room;
import com.zb.models.room.activity.DrawSomethingActor;
import com.zb.models.room.activity.DrawSomething;
import com.zb.service.room.DrawSomethingService;

@Service
public class DrawSomethingRobitService extends ActivityRobitService {
	static final Logger log = LoggerFactory.getLogger(DrawSomethingRobitService.class);

	private static final int ROBIT_TIME = 3200;
	@Autowired
	DrawSomethingService drawSomethingService;


	@Override
	public ReMsg handle(Room r, long uid) {
		DrawSomething uc = super.getActivity(r.get_id(), DrawSomething.class);
		if (DrawSomething.STATUS_READY == uc.getStatus()) {// 装备阶段
			DrawSomethingActor dsa = super.getRoomActor(r.get_id(), uid, DrawSomethingActor.class);
			int rcnt = 0;// 机器人数量
			Set<DrawSomethingActor> dsas = super.getRoomActors(r.get_id(), DrawSomethingActor.class);
			for (DrawSomethingActor cdsa : dsas) {
				if (cdsa.isRobit()) {
					rcnt++;
				}
			}
			int roomCnt = super.getActivityRoomCount(r.getType(), Room.ACTIVITY);// 当前游戏的房间数
			int cr = RandomUtil.nextInt(100);
			if (dsa == null) {// 还没进入房间
				if (dsas.size() < 5) {// 房间总人数小于5个
					if (rcnt == dsas.size()) {
						drawSomethingService.closeRoom(r.get_id());
					} else if (rcnt < 3) {
						if (rcnt < 1) {// 没有机器人
							if (cr < 100 - roomCnt / 2) {// 第一个机器人进入房间的概率
								drawSomethingService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						} else if (rcnt < 2) {// 1个机器人
							if (cr < 50 - roomCnt * 5) {// 第二个机器人进入房间的概率
								drawSomethingService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						} else {// 2个机器人
							if (cr < 10 - roomCnt * 4) {// 第三个机器人进入房间的概率
								drawSomethingService.inRoom(uid, r, 0, true);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						}
					}
				}
			} else {
				if (dsa.getUcStatus() == DrawSomethingActor.A_STATUS_IN_ROOM) {
					if (rcnt < 2) {// 1个机器人
						int rc = 100 - roomCnt * 5;
						if (rc < 0) {
							rc = 0;
						}
						if (cr < rc) {// 准备概率
							ReMsg rm = drawSomethingService.ready(uid, r.get_id());
							if (rm.getCode() == ReCode.OK.reCode()) {
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							} else {
								drawSomethingService.outRoom(uid, r);
							}
						} else {
							if (rc < rc + roomCnt * 4 / 3 + 2) {// 退出概率
								drawSomethingService.outRoom(uid, r);
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
							ReMsg rm = drawSomethingService.ready(uid, r.get_id());
							if (rm.getCode() == ReCode.OK.reCode()) {
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							} else {
								drawSomethingService.outRoom(uid, r);
							}
						} else {
							if (cr < rc + roomCnt / 2 + 8) {// 退出房间概率
								drawSomethingService.outRoom(uid, r);
							} else {// 停留概率
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							}
						}
					}
				} else {// 已准备机器人
					if (rcnt < 2) {// 1个机器人
						if (cr < 3 + roomCnt * 3 / 2) {// 多个机器人推出房间概率
							drawSomethingService.outRoom(uid, r);
						} else {
							userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
						}
					} else {// 多个机器人
						if (cr < 15 + roomCnt * 3 / 2) {// 一个机器人退出房间概率
							drawSomethingService.outRoom(uid, r);
						} else {
							userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
						}
					}
				}
			}
		} else if (DrawSomething.STATUS_SELECT_WORD == uc.getStatus()) {
			if (uc.getActors().containsKey(uid)) {
				if (DrawSomething.DRAWING == uc.getActors().get(uid)) {
					DrawSomethingActor dsa = super.getRoomActor(r.get_id(), uid, DrawSomethingActor.class);
					if (dsa.getUcStatus() == DrawSomethingActor.A_STATUS_SELECT_WORD) {
						boolean ok = false;
						for (String w : uc.getGws().keySet()) {
							int count = drawSomethingService.countDrawLogs(w, Const.STATUS_OK);
							if (count > 0) {
								drawSomethingService.selectWord(uid, r.get_id(), w);
								ok = true;
								break;
							}
						}
						if (!ok) {
							drawSomethingService.rePubWord(r.get_id(), uid);
						}
					}
					userRobitService.addJob(r.get_id(), uid, 2000);
				} else {
					userRobitService.addJob(r.get_id(), uid, 10000);
				}
			}
		} else if (DrawSomething.STATUS_DRAWING == uc.getStatus()) {
			if (uc.getActors().containsKey(uid)) {
				if (DrawSomething.DRAWING == uc.getActors().get(uid)) {// 绘画者
					String draw = super.getRedisTemplate().opsForList()
							.leftPop(RK.userRobitDraws() + r.get_id() + "-" + uid);
					if (draw == null) {
						if (super.getRedisTemplate().opsForValue()
								.get(RK.userRobitDrawFlag() + r.get_id() + "-" + uid) == null) {
							List draws = drawSomethingService.drawData(uc.getWord());
							if (draws != null) {
								int t = 0;
								for (Object obj : draws) {
									if (t == 0) {
										draw = JSONUtil.beanToJson(obj);
									} else {
										super.getRedisTemplate().opsForList().rightPush(
												RK.userRobitDraws() + r.get_id() + "-" + uid, JSONUtil.beanToJson(obj));
									}
									t++;
								}
								super.getRedisTemplate().opsForValue()
										.set(RK.userRobitDrawFlag() + r.get_id() + "-" + uid, "", 70, TimeUnit.SECONDS);
							}
						}
					}
					if (draw != null) {
						Map<String, Object> m = JSONUtil.jsonToMap(draw);
						drawSomethingService.drawing(uid, r.get_id(), m);
						userRobitService.addJob(r.get_id(), uid,
								MapUtil.getLong(m, "timestamp") + RandomUtil.nextInt(150));
					} else {
						userRobitService.addJob(r.get_id(), uid, 10000);
					}
				} else {// 猜词者
					if (uc.getRights().contains(uid)) {
						userRobitService.addJob(r.get_id(), uid, 10000);
					} else {
						Double e = super.getJobEndTime(r.get_id());
						if (e != null) {
							if (e.longValue() - System.currentTimeMillis() > RandomUtil.nextInt(10000) + 45000) {
								userRobitService.addJob(r.get_id(), uid, 10000);
								return null;
							}
						}
						if (uc.getDrawCnt() < 2 + RandomUtil.nextInt(5)) {
							userRobitService.addJob(r.get_id(), uid, 10000);
							return null;
						}
						int rr = RandomUtil.nextInt(100);
						if (rr < 20) {
							drawSomethingService.guess(uid, r.get_id(), uc.getWord());
							userRobitService.addJob(r.get_id(), uid, 10000);
						} else {
							DBCursor dbc = super.getC(DocName.DRAW_GUESS_LOG)
									.find(new BasicDBObject("word", uc.getWord()));
							int count = dbc.count();
							if (count > 0) {
								DBObject dbo = dbc.skip(RandomUtil.nextInt(count)).limit(1).toArray().get(0);
								drawSomethingService.guess(uid, r.get_id(), DboUtil.getString(dbo, "guess"));
							}
							userRobitService.addJob(r.get_id(), uid, 8000 + RandomUtil.nextInt(10000));
						}
					}
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
