package com.zb.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcRoomService;
import com.zb.service.room.UserActivityService;

public class RpcRoomServiceImpl implements RpcRoomService {
	static final Logger log = LoggerFactory.getLogger(RpcRoomServiceImpl.class);

	@Autowired
	UserActivityService userActivityService;

	@Override
	public ReMsg inRoom(long uid, long roomId, int model) {
		// TODO Auto-generated method stub
		return userActivityService.inRoom(uid, roomId,model);
	}

	@Override
	public ReMsg outRoom(long uid, long roomId) {
		return userActivityService.outRoom(uid, roomId);
	}

	@Override
	public void disconnectRoom(long uid, long roomId) {
		if (uid < 1)
			return;
		userActivityService.disconnectRoom(uid, roomId);
	}

	@Override
	public void chat(long uid, long roomId, String fmt, String txt) {
		userActivityService.chat(uid, roomId, fmt, txt);
	}

	@Override
	public ReMsg roomUserStatus(long uid, long roomId) {
		return userActivityService.inRoom(uid, roomId,0);
	}

	@Override
	public void speaking(long uid, long roomId) {
		userActivityService.speaking(uid, roomId);
	}

	@Override
	public void cancelSpeak(long uid, long roomId) {
		userActivityService.cancelSpeak(uid, roomId);
		
	}

	@Override
	public void pauseSpeaking(long uid, long roomId) {
		userActivityService.pauseSpeaking(uid, roomId);
	}
}
