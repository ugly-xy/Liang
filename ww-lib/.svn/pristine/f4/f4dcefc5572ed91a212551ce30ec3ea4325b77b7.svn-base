package com.zb.service.jobs;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.common.utils.RandomUtil;
import com.zb.models.room.Room;
import com.zb.service.room.RoomDefaultService;
import com.zb.service.room.robit.UserRobitService;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.RoomId4Type;

public class RoomRobitJob {
	static final Logger log = LoggerFactory.getLogger(RoomRobitJob.class);
	@Autowired
	RoomDefaultService roomDefaultService;

	@Autowired
	UserRobitService userRobitService;

	public void execute() {
		// 老版本，从数据库获取
		List<Room> dbos = roomDefaultService.getActiveRooms(10);
		for (Room r : dbos) {
			long roomId = r.get_id();
			long rbtId = userRobitService.getRobitId();
			if (rbtId > 0) {
				userRobitService.addJob(roomId, rbtId, userRobitService.getTime());
			}
		}

		// //新版本
		// List<Long> dbos = roomDefaultService.getActiveRooms();
		// for (Long roomId : dbos) {
		// long rbtId = userRobitService.getRobitId();
		// if (rbtId > 0) {
		// userRobitService.addJob(roomId, rbtId, userRobitService.getTime());
		// }
		// }
		for (int type : MsgType.matchRoomType) {
			Set<String> uidsStr = roomDefaultService.unmatchUser(type, RandomUtil.nextInt(10) + 20);// 20-30秒没匹配上
			for (String uidStr : uidsStr) {
				long rbtId = userRobitService.getRobitId();
				long rid = roomDefaultService.matchingActivity(rbtId, uidStr, type, 0);
				if (rid > 0) {
					userRobitService.addJob(rid, rbtId, userRobitService.getMatchTime());
				}
			}
		}

		int r = RandomUtil.nextInt(100);
		if (r < 2) {
			long rbtId = userRobitService.getRobitId();
			userRobitService.addJob(RoomId4Type.SLOTMACHINES.getId(), rbtId, userRobitService.getTime());
			// 花魁乱斗机器人
			userRobitService.addJob(RoomId4Type.SAKURAN.getId(), rbtId, userRobitService.getTime());
		}
	}

}
