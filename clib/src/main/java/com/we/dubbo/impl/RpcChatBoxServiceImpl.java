package com.we.dubbo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DBObject;
import com.we.common.Constant.ReCode;
import com.we.common.mongo.DboUtil;
import com.we.core.web.ReMsg;
import com.we.dubbo.RpcChatBoxService;
import com.we.service.MessageService;
import com.we.service.UserService;
import com.we.socket.model.JsonMsg;

public class RpcChatBoxServiceImpl implements RpcChatBoxService {

	List<JsonMsg> msgs = new ArrayList<JsonMsg>();

	@Autowired
	UserService userService;

	@Autowired
	MessageService messageService;

	@Override
	public ReMsg chat(JsonMsg msg) {
		Long frid = msg.getDataVal("frid", Long.class);
		if (JsonMsg.ALL.equals(msg.getMethod())) {
			DBObject frUser = userService.findById(frid);
			if (frUser != null) {
				// TODO valid sendTime
				msg.append("nickname", DboUtil.getString(frUser, "username"));
				msg.append("level", DboUtil.getString(frUser, "divisionId"));
				msg.append("avatar", DboUtil.getString(frUser, "photo"));
				Long toid = msg.getDataVal("toid", Long.class);
				if (toid != null) {
					DBObject toUser = userService.findById(toid);
					msg.append("toNickname", DboUtil.getString(toUser, "username"));
				}
				messageService.sendAll(msg);
				addMsg(msg);
			}
		} else if(JsonMsg.TIP.equals(msg.getMethod())){
			Long curUid=frid;
			for(JsonMsg m : msgs) {
				m.append("toid", curUid);
				messageService.sendP2P(m);
			}
		}else {
			DBObject frUser = userService.findById(frid);
			if (frUser != null) {
				msg.append("nickname", DboUtil.getString(frUser, "username"));
				msg.append("level", DboUtil.getString(frUser, "divisionId"));
				msg.append("avatar", DboUtil.getString(frUser, "photo"));
				messageService.sendP2P(msg);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	@Override
	public ReMsg Login(String token) {
		long curUid = userService.getUid(token);
		if (curUid == 0 || curUid<1) {
			return new ReMsg(ReCode.FAIL);
		}
		
		return new ReMsg(ReCode.OK, curUid);
	}
	
	private void addMsg(JsonMsg msg) {
		msgs.add(msg);
		if(msgs.size()>10) {
			msgs.remove(0);
		}
	}

}
