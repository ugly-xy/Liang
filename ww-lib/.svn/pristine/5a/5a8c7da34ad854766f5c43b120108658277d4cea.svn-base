package com.zb.dubbo;

import java.util.Map;

import com.zb.core.web.ReMsg;

public interface RpcDrawSomethingService {

	public ReMsg selectWord(long uid, long roomId, String word);

	public ReMsg drawing(long uid, long roomId, Map map);

	public ReMsg guess(long uid, long roomId, String txt);

	public void rePubWord(long roomId, long uid);
	
	public ReMsg ready(long uid, long roomId);
	
	public ReMsg clear(long uid, long roomId);

}
