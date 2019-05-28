package com.zb.dubbo.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcChatBoxService;
import com.zb.service.room.ChatBoxService;

public class RpcChatBoxServiceImpl implements RpcChatBoxService {

	@Autowired
	ChatBoxService chatBoxService;

	@Override
	public ReMsg getDownChat(Long operatorUid, long uid, long roomId) {
		return chatBoxService.getDownChat(operatorUid, uid, roomId);
	}

	@Override
	public ReMsg climbChat(Long operatorUid, long uid, long roomId, int seatNo) {
		return chatBoxService.climbChat(operatorUid, uid, roomId, seatNo);
	}

	@Override
	public ReMsg lockSeat(long operatorUid, long roomId, int seatNo) {
		return chatBoxService.lockSeat(operatorUid, roomId, seatNo);
	}

	@Override
	public ReMsg unlockSeat(long operatorUid, long roomId, int seatNo) {
		return chatBoxService.unlockSeat(operatorUid, roomId, seatNo);
	}

	@Override
	public ReMsg lockUser(long operatorUid, long roomId, long uid) {
		return chatBoxService.lockUser(operatorUid, roomId, uid);
	}

	@Override
	public ReMsg unlockUser(long operatorUid, long roomId, long uid) {
		return chatBoxService.unlockUser(operatorUid, roomId, uid);
	}

	@Override
	public ReMsg shutupChat(long operatorUid, long roomId, Boolean voice, Boolean wordHome) {
		return chatBoxService.shutupChat(operatorUid, roomId, voice, wordHome);
	}
}
