package com.zb.service.jobs;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import com.zb.common.Constant.ActivityType;
import com.zb.common.redis.RK;
import com.zb.models.room.Room;
import com.zb.service.room.RoomDefaultService;
import com.zb.service.room.RoomHandlerDispatcher;
import com.zb.service.room.robit.AnimalChessRobitService;
import com.zb.service.room.robit.CowRobitService;
import com.zb.service.room.robit.DiceRobitService;
import com.zb.service.room.robit.DrawSomethingRobitService;
import com.zb.service.room.robit.GomokuRobitService;
import com.zb.service.room.robit.NeuroCatRobitService;
import com.zb.service.room.robit.SakuranRobitService;
import com.zb.service.room.robit.SchoolWarRobitService;
import com.zb.service.room.robit.SlotMachinesRobitService;
import com.zb.service.room.robit.TexasRobitService;
import com.zb.service.room.robit.UndercoverRobitService;
import com.zb.service.room.robit.UserRobitService;
import com.zb.service.room.robit.WerewolfKillRobitService;
import com.zb.service.room.robit.WinmineRobitService;

public class RoomUserRobitJob {
	static final Logger log = LoggerFactory.getLogger(RoomUserRobitJob.class);
	@Autowired
	RoomHandlerDispatcher roomHandlerDispatcher;

	@Autowired
	RoomDefaultService roomDefaultService;

	@Autowired
	UserRobitService userRobitService;

	@Autowired
	DrawSomethingRobitService drawSomethingRobitService;

	@Autowired
	UndercoverRobitService undercoverRobitService;

	@Autowired
	SlotMachinesRobitService slotMachinesRobitService;

	@Autowired
	SakuranRobitService sakuranRobitService;

	@Autowired
	DiceRobitService diceRobitService;

	@Autowired
	WerewolfKillRobitService werewolfKillRobitService;

	@Autowired
	SchoolWarRobitService schoolWarRobitService;

	@Autowired
	AnimalChessRobitService animalChessRobitService;

	@Autowired
	GomokuRobitService gomokuRobitService;

	@Autowired
	WinmineRobitService winmineRobitService;

	@Autowired
	NeuroCatRobitService neuroCatRobitService;

	@Autowired
	CowRobitService cowRobitService;

	@Autowired
	TexasRobitService texasRobitService;

	public void execute() {
		long curT = System.currentTimeMillis();
		Set<TypedTuple<String>> curset = null;
		if (roomDefaultService.lock(RK.roomUserRobitLock(), 1L)) {
			curset = roomDefaultService.getRedisTemplate().opsForZSet().rangeByScoreWithScores(RK.roomUserRobitJob(), 0,
					curT);
			roomDefaultService.getRedisTemplate().opsForZSet().removeRangeByScore(RK.roomUserRobitJob(), 0, curT);
			roomDefaultService.unlock(RK.roomUserRobitLock());
		}
		if (curset != null) {
			for (TypedTuple<String> t : curset) {
				String ruid = t.getValue();
				String[] arr = ruid.split("-");
				long rid = Long.parseLong(arr[0]);
				long uid = Long.parseLong(arr[1]);
				Room r = roomDefaultService.getRoom(rid);
				if (r != null) {
					try {
						if (r.getType() == ActivityType.DRAW_SOMETHING.getType()) {
							drawSomethingRobitService.handle(r, uid);
						} else if (r.getType() == ActivityType.UNDERCOVER.getType()) {
							undercoverRobitService.handle(r, uid);
						} else if (r.getType() == ActivityType.DICE.getType()) {
							diceRobitService.handle(r, uid);
						} else if (r.getType() == ActivityType.SLOT_MACHINES.getType()) {
							slotMachinesRobitService.handle(r, uid);
						} else if (r.getType() == ActivityType.SAKURAN.getType()) {
							sakuranRobitService.handle(r, uid);
						} else if (r.getType() == ActivityType.SCHOOL_WAR.getType()) {
							schoolWarRobitService.handle(r, uid);
						} else if (r.getType() == ActivityType.ANIMAL_CHESS.getType()) {
							animalChessRobitService.handle(r, uid);
						} else if (r.getType() == ActivityType.WEREWOLF6.getType()
								|| r.getType() == ActivityType.WEREWOLF.getType()
								|| r.getType() == ActivityType.WEREWOLF9.getType()) {
							// werewolfKillRobitService.handle(r, uid);
						} else if (r.getType() == ActivityType.GOMOKU.getType()) {
							gomokuRobitService.handle(r, uid);
						} else if (r.getType() == ActivityType.WINMINE.getType()) {
							winmineRobitService.handle(r, uid);
						} else if (r.getType() == ActivityType.NEURO_CAT.getType()) {
							neuroCatRobitService.handle(r, uid);
						} else if (r.getType() == ActivityType.COW_LOW.getType()
								|| r.getType() == ActivityType.COW_HIGH.getType()) {
							cowRobitService.handle(r, uid);
						} else if (r.getType() == ActivityType.TEXAS_LOW.getType()
								|| r.getType() == ActivityType.TEXAS_MID.getType()
								|| r.getType() == ActivityType.TEXAS_HIGH.getType()) {
							// texasRobitService.handle(r, uid);
						}
					} catch (Exception e) {
						log.error("RoomUserJob：RID:" + rid + " ,uid:" + uid, e);
					}
				}
			}
		}
	}

}
