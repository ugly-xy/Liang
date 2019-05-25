package com.zb.dubbo;

import java.util.List;

import com.mongodb.DBObject;
import com.zb.core.web.ReMsg;

public interface RpcSysService {

	public ReMsg serverHeartbeat(String host, int port, String type, int connectCnt,int maxCnt, int status);
	
	public List<DBObject> unreadMsgsByFr(Long uid, Long fr, int size);
	
	public void msgReaded(Long id);

}
