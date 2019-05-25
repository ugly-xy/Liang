package com.zb.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.common.mongo.DboUtil;
import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcUserService;
import com.zb.models.User;
import com.zb.models.server.ServerInfo.ServerType;
import com.zb.service.MessageService;
import com.zb.service.UserService;
import com.zb.service.server.ServerMngService;
import com.zb.socket.model.Msg;

public class RpcUserServiceImpl implements RpcUserService {
	static final Logger log = LoggerFactory.getLogger(RpcUserServiceImpl.class);

	@Autowired
	UserService userSerice;

	@Autowired
	MessageService messageService;
	
	@Autowired
	ServerMngService serverMngService;

	@Override
	public void sendMsg(Msg msg) {
		messageService.msgHandler(msg);
		// if(MsgType.CHAT.getT() == msg.getT()){
		// Map<String, Object> m = (Map<String, Object>) msg.getO();
		// messageService.socketMsgSave(Message.TYPE_USER_MESSAGE,
		// MapUtil.getStr(m, "msg"), msg.getTo(), (long) msg.getFr(),
		// OperationType.MESSAGE, null);
		// }

	}

	@Override
	public User getUser(long id) {
		return DboUtil.toBean(userSerice.findById(id), User.class);
	}

}
