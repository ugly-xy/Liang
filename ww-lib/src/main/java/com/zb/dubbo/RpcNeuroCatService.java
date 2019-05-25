package com.zb.dubbo;

import com.zb.core.web.ReMsg;

public interface RpcNeuroCatService {
	// 放置杠杆
	public ReMsg putLever(long uid, long roomId, int[] XY, int leverType);

	// 移动棋子
	public ReMsg moveCat(long uid, long roomId, int[] oldXY, int[] XY);

	// 认输
	public ReMsg giveupGame(long uid, long roomId);

}
