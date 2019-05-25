package com.zb.dubbo.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcGomokuService;
import com.zb.service.room.cp.GomokuService;

public class RpcGomokuServiceImpl implements RpcGomokuService {

	@Autowired
	GomokuService gomokuService;

	public ReMsg putDown(long roomId, long uid, int x, int y) {
		return gomokuService.putDown(roomId, uid, x, y);
	}

	public void drawApply(long roomId, long uid) {
		gomokuService.drawApply(roomId, uid);
	}

	public void drawAgree(long roomId, long uid) {
		gomokuService.drawAgree(roomId, uid);
	}

	public void drawRefuse(long roomId, long uid) {
		gomokuService.drawRefuse(roomId, uid);
	}

	public void regretApply(long roomId, long uid) {
		gomokuService.regretApply(roomId, uid);
	}

	public void regretAgree(long roomId, long uid) {
		gomokuService.regretAgree(roomId, uid);
	}

	public void regretRefuse(long roomId, long uid) {
		gomokuService.regretRefuse(roomId, uid);
	}

	public void sayUncle(long roomId, long uid) {
		gomokuService.sayUncle(roomId, uid);
	}

}
