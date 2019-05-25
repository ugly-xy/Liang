package com.zb.dubbo.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DBObject;
import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcSysService;
import com.zb.service.MessageService;
import com.zb.service.server.ServerMngService;

public class RpcSysServiceImpl implements RpcSysService {
	static final Logger log = LoggerFactory.getLogger(RpcSysServiceImpl.class);

	@Autowired
	MessageService messageService;
	
	@Autowired
	ServerMngService serverMngService;


	@Override
	public ReMsg serverHeartbeat(String host, int port, String type, int connectCnt,int maxCnt, int status) {
		return serverMngService.save(host, port, type, connectCnt,maxCnt, status);
	}
	
	public List<DBObject> unreadMsgsByFr(Long uid, Long fr, int size) {
		return messageService.unreadMsgsByFr(uid, fr, size);
	}

	@Override
	public void msgReaded(Long id) {
		messageService.msgReaded(id);
	}

}
