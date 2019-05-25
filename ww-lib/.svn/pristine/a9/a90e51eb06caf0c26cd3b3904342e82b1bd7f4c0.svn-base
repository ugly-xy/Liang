package com.zb.dubbo.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcWorldChatService;
import com.zb.service.room.WorldChatService;

public class RpcWorldChatServiceImpl implements RpcWorldChatService {

	@Autowired
	WorldChatService worldChatService;

	@Override
	public ReMsg worldChat(Long uid, Long atId, Long typeId, String fmt, String txt) {
		return worldChatService.worldChat(uid, atId, typeId, fmt, txt, false);
	}

}
