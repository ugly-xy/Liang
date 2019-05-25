package com.zb.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcUndercoverService;
import com.zb.service.room.UndercoverService;
//import com.zb.service.room.UndercoverService;
import com.zb.service.room.UserActivityService;

public class RpcUndercoverServiceImpl implements RpcUndercoverService {
	static final Logger log = LoggerFactory
			.getLogger(RpcUndercoverServiceImpl.class);

	@Autowired
	UserActivityService userActivityService;

	@Autowired
	UndercoverService undercoverService;

	@Override
	public ReMsg speak(Long uid, Long roomId, String txt) {
		return undercoverService.speak(uid, roomId, txt);
	}

	@Override
	public ReMsg toVote(Long uid, Long roomId, Long toUid) {
		return undercoverService.vote(roomId, uid, toUid);
	}

	@Override
	public ReMsg pkSpeak(Long uid, Long roomId, String txt) {
		return undercoverService.pkSpeak(roomId, uid, txt);
	}

	@Override
	public ReMsg pkVote(Long uid, Long roomId, Long toUid) {
		return undercoverService.pkVote(roomId, uid, toUid);
	}

	@Override
	public ReMsg dice(long uid, long roomId) {
		return undercoverService.dice(roomId, uid);
	}

	@Override
	public ReMsg ready(long uid, long roomId) {
		return undercoverService.ready(uid, roomId);
	}

}
