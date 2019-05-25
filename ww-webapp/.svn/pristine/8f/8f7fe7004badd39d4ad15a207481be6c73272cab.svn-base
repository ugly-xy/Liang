package com.zb.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.service.RechargeShoutService;

@Controller
@RequestMapping("/shout")
public class RechargeShoutCtl extends BaseCtl {

	@Autowired
	RechargeShoutService rechargeShoutService;

	@RequestMapping("/shout")
	public @ResponseBody ReMsg shout(String content, int all, int reply) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.USER_ID_ERR);
		}
		return rechargeShoutService.shout(uid, content, all, reply);
	}

	@RequestMapping("/queryShout")
	public @ResponseBody ReMsg queryShout(Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.USER_ID_ERR);
		}
		if (null != page && page > 5) {
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.OK, rechargeShoutService.queryShout(uid, page, size));
	}

	@RequestMapping("/commentShout")
	public @ResponseBody ReMsg commentShout(long sid, String content, Long baseId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.USER_ID_ERR);
		}
		return rechargeShoutService.commentShout(sid, uid, content, baseId);
	}
	
	@RequestMapping("/commentShouts")
	public @ResponseBody ReMsg commentShouts(long sid, Integer size, Integer page) {
		return rechargeShoutService.queryCommentShout(sid, size, page,false);
	}

}
