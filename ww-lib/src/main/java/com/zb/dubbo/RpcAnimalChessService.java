package com.zb.dubbo;

import com.zb.core.web.ReMsg;

public interface RpcAnimalChessService {

	// 点击棋子
	public ReMsg clickNode(long uid, long roomId, int[] XY);

	// 移动棋子
	public ReMsg moveNode(long uid, long roomId, int[] oldXY, int[] newXY);

	// 认输
	public ReMsg giveupGame(long uid, long roomId);

}
