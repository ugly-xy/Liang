package com.zb.dubbo;

import com.zb.core.web.ReMsg;

public interface RpcWinmineService {

	// 点击节点
	public ReMsg clickNode(long uid, long roomId, int[] XY, int type);

}
