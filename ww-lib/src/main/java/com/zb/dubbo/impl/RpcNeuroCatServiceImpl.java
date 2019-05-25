package com.zb.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcNeuroCatService;
import com.zb.service.room.cp.NeuroCatService;

public class RpcNeuroCatServiceImpl implements RpcNeuroCatService {
	static final Logger log = LoggerFactory.getLogger(RpcNeuroCatServiceImpl.class);

	@Autowired
	NeuroCatService neuroCatService;

	@Override
	public ReMsg putLever(long uid, long roomId, int[] XY,  int leverType) {
		return neuroCatService.putLever(uid, roomId, XY, leverType);
	}

	@Override
	public ReMsg moveCat(long uid, long roomId, int[] oldXY, int[] XY) {
		return neuroCatService.moveCat(uid, roomId, oldXY, XY);
	}

	@Override
	public ReMsg giveupGame(long uid, long roomId) {
		return neuroCatService.giveupGame(uid, roomId);
	}

}
