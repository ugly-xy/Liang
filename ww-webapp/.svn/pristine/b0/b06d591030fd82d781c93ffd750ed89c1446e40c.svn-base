package com.zb.web.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.DBObject;
import com.zb.common.Constant.OperationType;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.Op;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.Message;
import com.zb.service.MessageService;

@Controller
@RequestMapping("/message")
public class MessageCtl extends BaseCtl {

	@Autowired
	MessageService messageService;

	/**
	 * 发送消息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/send")
	public ReMsg send(String content, Long recvUid, Long replyId,
			HttpServletRequest request) {
		return messageService.save(Message.TYPE_USER_MESSAGE, content, recvUid,
				replyId, OperationType.CHAT, null, request);
	}

	@ResponseBody
	@RequestMapping("/read")
	public ReMsg read(Long id) {
		return messageService.readMessage(id);
	}

	@ResponseBody
	@RequestMapping("/allRead")
	public ReMsg read() {
		return messageService.readAllMessage();
	}

	@ResponseBody
	@RequestMapping("/unread")
	public ReMsg myMessageCount() {
		return messageService.getUnreadMessage();
	}

	@ResponseBody
	@RequestMapping("/my")
	public ReMsg getMyMessage(Integer status, Integer page, Integer size) {
		Page<DBObject> p = null;
		Long uid = super.getUid();
		if (status == null || status == 0) {
			p = messageService.query(null, null, uid, Message.STATUS_DEL,
					Op.GT, page, size);
		} else {
			p = messageService.query(null, null, uid, status, null, page, size);
		}

		return new ReMsg(ReCode.OK, p);
	}

	@ResponseBody
	@RequestMapping("/del")
	public ReMsg cate(Long id) {
		return messageService.del(id);
	}

	@ResponseBody
	@RequestMapping("/dels")
	public ReMsg cate(String ids) {
		return messageService.del(ids);
	}

}
