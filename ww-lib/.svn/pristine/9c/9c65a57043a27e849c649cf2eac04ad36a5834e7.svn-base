package com.zb.dubbo.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcDrawSomethingService;
import com.zb.service.room.DrawSomethingService;
import com.zb.service.room.UserActivityService;

public class RpcDrawSomethingServiceImpl implements RpcDrawSomethingService {
	static final Logger log = LoggerFactory
			.getLogger(RpcDrawSomethingServiceImpl.class);

	@Autowired
	UserActivityService userActivityService;

	@Autowired
	DrawSomethingService drawSomethingService;

	@Override
	public ReMsg selectWord(long uid, long roomId, String word) {
		return drawSomethingService.selectWord(uid, roomId, word);
	}

	@Override
	public ReMsg drawing(long uid, long roomId, Map json) {
		return drawSomethingService.drawing(uid, roomId, json);
	}

	@Override
	public ReMsg guess(long uid, long roomId, String txt) {
		return drawSomethingService.guess(uid, roomId, txt);
	}

	@Override
	public void rePubWord(long roomId, long uid) {
		drawSomethingService.rePubWord(roomId, uid);
	}

	@Override
	public ReMsg ready(long uid, long roomId) {
		return drawSomethingService.ready(uid, roomId);
	}

	@Override
	public ReMsg clear(long uid, long roomId) {
		return drawSomethingService.clear(uid, roomId);
	}
}
