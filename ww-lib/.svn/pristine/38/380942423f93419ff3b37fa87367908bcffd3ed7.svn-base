package com.zb.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcDiceService;
import com.zb.service.room.DiceService;

public class RpcDiceServiceImpl implements RpcDiceService {
	private static final Logger LOG = LoggerFactory.getLogger(RpcDiceServiceImpl.class);
	@Autowired
	DiceService diceService;

	@Override
	public ReMsg ready(long uid, long roomId) {
		return diceService.ready(uid, roomId);
	}

	@Override
	public void reDicing(long roomId, long uid) {
		diceService.reDicing(roomId, uid);
	}

	@Override
	public void confirmDice(long roomId, long uid) {
		diceService.confirmDice(roomId, uid);

	}

	@Override
	public ReMsg bragDices(long roomId, long uid, int num, int count, int addCoin) {
		return diceService.bragDices(roomId, uid, num, count, addCoin);
	}

	@Override
	public ReMsg showDice(long roomId, long uid) {
		return diceService.showDice(roomId, uid);
	}

	@Override
	public void getDices(long roomId, long uid) {
		diceService.getDices(roomId, uid);
	}
}
