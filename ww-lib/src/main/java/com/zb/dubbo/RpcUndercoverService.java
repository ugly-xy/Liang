package com.zb.dubbo;

import com.zb.core.web.ReMsg;

public interface RpcUndercoverService {

	public ReMsg speak(Long uid, Long roomId,String txt);

	public ReMsg toVote(Long uid, Long roomId,Long toUid);

	public ReMsg pkSpeak(Long uid, Long roomId,String txt);

	public ReMsg pkVote(Long uid, Long roomId,Long toUid);
	
	public ReMsg dice(long uid, long roomId);
	
	public ReMsg ready(long uid, long roomId);

}
