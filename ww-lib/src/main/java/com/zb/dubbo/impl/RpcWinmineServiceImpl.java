package com.zb.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcWinmineService;
import com.zb.service.room.cp.WinmineService;

public class RpcWinmineServiceImpl implements RpcWinmineService {
	static final Logger log = LoggerFactory.getLogger(RpcWinmineServiceImpl.class);

	@Autowired
	WinmineService winmineService;

	@Override
	public ReMsg clickNode(long uid, long roomId, int[] XY, int type) {
		return winmineService.clickNode(uid, roomId, XY, type);
	}

}
