package com.zb.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcAnimalChessService;
import com.zb.service.room.cp.AnimalChessService;

public class RpcAnimalChessServiceImpl implements RpcAnimalChessService {
	static final Logger log = LoggerFactory.getLogger(RpcAnimalChessServiceImpl.class);

	@Autowired
	AnimalChessService animalChessService;

	@Override
	public ReMsg clickNode(long uid, long roomId, int[] XY) {
		return animalChessService.clickNode(uid, roomId, XY);
	}

	@Override
	public ReMsg moveNode(long uid, long roomId, int[] oldXY, int[] newXY) {
		return animalChessService.moveNode(uid, roomId, oldXY, newXY);
	}

	@Override
	public ReMsg giveupGame(long uid, long roomId) {
		return animalChessService.giveupGame(uid, roomId);
	}

}
