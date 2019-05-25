package com.zb.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcSchoolWarService;
import com.zb.service.room.cp.SchoolWarService;

public class RpcSchoolWarServiceImpl implements RpcSchoolWarService {
	static final Logger log = LoggerFactory.getLogger(RpcSchoolWarServiceImpl.class);

	@Autowired
	SchoolWarService schoolWarService;

	@Override
	public ReMsg fighting(long uid, long roomId,int way) {
		return schoolWarService.fighting(uid, roomId,way);
	}

	@Override
	public ReMsg laughing(long uid, long roomId) {
		return schoolWarService.laughing(uid, roomId);
	}

//	@Override
//	public ReMsg giveupGame(long uid, long roomId) {
//		return schoolWarService.giveupGame(uid, roomId);
//	}

}
