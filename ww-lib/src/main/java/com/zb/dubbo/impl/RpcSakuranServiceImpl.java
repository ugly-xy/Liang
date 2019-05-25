package com.zb.dubbo.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcSakuranService;
import com.zb.service.room.SakuranService;

public class RpcSakuranServiceImpl implements RpcSakuranService {

	@Autowired
	SakuranService sakuranService;

	@Override
	public ReMsg betting(long roomId, long uid, int choice, int stakeCoin) {
		return sakuranService.betting(roomId, uid, choice, stakeCoin);
	}

}
