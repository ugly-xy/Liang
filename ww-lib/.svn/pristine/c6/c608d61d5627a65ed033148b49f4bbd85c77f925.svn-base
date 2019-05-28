package com.zb.dubbo.impl;


import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcSlotMachinesService;
import com.zb.service.room.SlotMachinesService;

public class RpcSlotMachinesServiceImpl implements RpcSlotMachinesService {

	@Autowired
	SlotMachinesService slotMachinesService;

	@Override
	public ReMsg betting(long roomId, long uid, int choice, int stakeCoin) {
		return slotMachinesService.betting(roomId, uid, choice, stakeCoin);
	}

}
