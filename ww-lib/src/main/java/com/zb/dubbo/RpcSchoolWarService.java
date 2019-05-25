package com.zb.dubbo;

import com.zb.core.web.ReMsg;

public interface RpcSchoolWarService {

	// 动手打
	public ReMsg fighting(long uid, long roomId, int way);

	// 嘲笑
	public ReMsg laughing(long uid, long roomId);

	// // 认输
	// public ReMsg giveupGame(long uid, long roomId);

}
