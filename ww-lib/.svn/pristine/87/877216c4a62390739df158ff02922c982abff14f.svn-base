package com.zb.dubbo.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcTexasService;
import com.zb.service.room.TexasService;

public class RpcTexasServiceImpl implements RpcTexasService {

	@Autowired
	TexasService texasService;

	@Override
	public ReMsg filling(long uid, long roomId, int type,Integer coin) {
		return texasService.filling(uid, roomId, type, coin);
	}

	@Override
	public ReMsg climbTheTree(long uid, long roomId) {
		return texasService.climbTheTree(uid, roomId);
	}

	@Override
	public ReMsg getDownTheTree(long uid, long roomId, int seatNo) {
		return texasService.getDownTheTree(uid, roomId, seatNo);
	}

}
