package com.zb.dubbo.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcSouthPenguinService;
import com.zb.service.room.SouthPenguinService;

public class RpcSouthPenguinServiceImpl implements RpcSouthPenguinService {
	private static final Logger log = LoggerFactory.getLogger(RpcSouthPenguinServiceImpl.class);
	@Autowired
	SouthPenguinService southPenguinService;

	@Override
	//用户准备
	public ReMsg ready(long uid, long roomId) {
		return southPenguinService.ready(uid, roomId);
	}

	@Override
	//玩家发射企鹅
	public ReMsg launch(long uid, long roomId, List penguins) {
		return southPenguinService.launch(uid, roomId,penguins);
	}

	@Override
	//app上传计算结果
	public ReMsg calculatePalce(long uid,long roomId, List penguins) {
		return southPenguinService.calculatePalce(uid,roomId,penguins);
	}
}
