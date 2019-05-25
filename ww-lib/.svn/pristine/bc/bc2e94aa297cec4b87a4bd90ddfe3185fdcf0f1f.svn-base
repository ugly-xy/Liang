package com.zb.service.jobs;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import com.zb.common.Constant.Const;
import com.zb.common.redis.RK;
import com.zb.service.room.RoomDefaultService;
import com.zb.service.room.RoomHandlerDispatcher;
import com.zb.service.room.RoomService;

public class RoomJob {
	static final Logger log = LoggerFactory.getLogger(RoomJob.class);
	@Autowired
	RoomHandlerDispatcher roomHandlerDispatcher;
	
	
	@Autowired
	RoomDefaultService roomDefaultService;
	
	long lastTime = 0;

	public void execute() {
		long curT = System.currentTimeMillis();
		Set<TypedTuple<String>> curset = null;
		if (roomDefaultService.lock(RK.roomLock(), 2L)) {
			curset = roomDefaultService.getRedisTemplate().opsForZSet()
					.rangeByScoreWithScores(RK.roomJob(), 0, curT);
			roomDefaultService.getRedisTemplate().opsForZSet().removeRangeByScore(RK.roomJob(), 0,
					curT);
			roomDefaultService.unlock(RK.roomLock());
		}
		if (curset != null) {
			for (TypedTuple<String> t : curset) {
				try {
					String roomId = t.getValue();
					roomHandlerDispatcher.handle(Long.parseLong(roomId), RoomService.FR_SYS);
				} catch (Exception e) {
					log.error("RoomJob:" , e);
				}
			}
		}
		//关闭僵尸房间
		if(System.currentTimeMillis()-lastTime>Const.MINUTE*20){
			lastTime = System.currentTimeMillis();
			roomDefaultService.findZombiesRoom();
		}
	}

}
