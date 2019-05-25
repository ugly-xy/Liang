package com.zb.service.room.cp;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.common.Constant.ActivityType;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.service.room.RoomService;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

public abstract class RoomCPService extends RoomService {
	static final Logger log = LoggerFactory.getLogger(RoomCPService.class);

	public void closeRoom(final long roomId) {
		Set<Actor> actors = super.getRoomActors(roomId);
		for (Actor actor : actors) {
			if (actor.isRobit()) {// 删除机器人job
				super.delRoomUserRobitJob(roomId, actor.getUid());
			}
		}
		super.closeRoom(roomId);
	}
	
	void checkRoom(Room r, Set<? extends Actor> actors) {
		MapBody mb = new MapBody(SocketHandler.HANDLER_NAME, SocketHandler.OUT_WAIT);
		for (Actor ga : actors) {
			super.pubUserMsg(ga.getUid(), MsgType.PROMPT, mb);
		}
		this.closeRoom(r.get_id());
	}
}
