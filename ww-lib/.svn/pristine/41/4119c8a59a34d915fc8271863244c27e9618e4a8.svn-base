package com.zb.service.jobs;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.common.redis.RK;
import com.zb.service.room.RoomDefaultService;

public class CleanExpireRoomJob {
	static final Logger LOGGER = LoggerFactory.getLogger(CleanExpireRoomJob.class);

	@Autowired
	RoomDefaultService roomDefaultService;

	public void execute() throws IOException {
		if (roomDefaultService.lock(RK.cleanExpireRoomLock(), 5L)) {
			roomDefaultService.resetBuyRoomJob();
			roomDefaultService.resetExpiryRoomJob();
		}
	}
}
