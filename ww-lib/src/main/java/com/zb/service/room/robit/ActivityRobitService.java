package com.zb.service.room.robit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.core.web.ReMsg;
import com.zb.models.room.Room;
import com.zb.service.room.RoomService;

@Service
public class ActivityRobitService extends RoomService {
	static final Logger log = LoggerFactory.getLogger(ActivityRobitService.class);


	@Autowired
	UserRobitService userRobitService;

	@Override
	public ReMsg handle(Room r, long uid) {
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
	public ReMsg kick(long uid, Room r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReMsg inRoom(long uid, Room r, int model, boolean robit) {
		// TODO Auto-generated method stub
		return null;
	}

}
