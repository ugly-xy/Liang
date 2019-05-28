package com.zb.dubbo.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcCowService;
import com.zb.service.room.CowService;

public class RpcCowServiceImpl implements RpcCowService {

	@Autowired
	CowService cowService;

	@Override
	public ReMsg hoging(long uid, long roomId, int fold) {
		return cowService.hoging(uid, roomId, fold);
	}

	@Override
	public ReMsg filling(long uid, long roomId, int fold) {
		return cowService.filling(uid, roomId, fold);
	}

	@Override
	public ReMsg climbTheTree(long uid, long roomId) {
		return cowService.climbTheTree(uid, roomId);
	}

	@Override
	public ReMsg getDownTheTree(long uid, long roomId, int seatNo) {
		return cowService.getDownTheTree(uid, roomId, seatNo);
	}

}
