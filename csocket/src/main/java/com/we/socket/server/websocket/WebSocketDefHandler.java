package com.we.socket.server.websocket;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;

import com.we.common.Constant.ReCode;
import com.we.common.utils.JSONUtil;
import com.we.socket.model.JsonMsg;
import com.we.core.spring.SpringContextHolder;
import com.we.core.web.ReMsg;
import com.we.dubbo.RpcChatBoxService;

/**
 * 权限验证控制器
 * 
 * @author walker.bao
 *
 */
public class WebSocketDefHandler extends WebSocketCommandHandler {
	static final Logger logger = LoggerFactory.getLogger(WebSocketDefHandler.class);

	RpcChatBoxService rpcChatBoxService = SpringContextHolder.getBean("rpcChatBoxService");
	
	
	public void handler(final ChannelHandlerContext ctx,final JsonMsg jm) {
		RpcChatBoxService rpcChatBoxService = SpringContextHolder.getBean("rpcChatBoxService");
		if (jm.getMethod().equals(JsonMsg.LOGIN)) {
			String token = jm.getDataVal("token", String.class);
			//Long uid = jm.getDataVal("frid", Long.class);

			System.out.println(token);

			ReMsg rm = rpcChatBoxService.Login(token);
			Map<String, Object> reData = new HashMap<String, Object>();
			reData.put("reid", jm.getId());
			reData.put("reMe", jm.getMethod());
			JsonMsg bjm = new JsonMsg(incrMsgId(), JsonMsg.BACK, reData);
			if (rm.getCode() == ReCode.OK.reCode()) {
				bjm.append("status", "ok");
				Long uid = (Long) rm.getData();
				regUser(ctx.channel(), uid);
				JsonMsg js =new JsonMsg(jm.getId(),JsonMsg.TIP,null);
				js.append("frid", uid);
				rpcChatBoxService.chat(js);
			} else {
				bjm.append("status", "fail");
			}
			ctx.writeAndFlush(bjm.toTWSFrame());
		} else {
						
			Long uid = WebSocketChannelMap.getUid(ctx.channel());
			if (uid < 1) {
				Map<String, Object> reData = new HashMap<String, Object>();
				reData.put("reid", jm.getId());
				JsonMsg bjm = new JsonMsg(incrMsgId(), JsonMsg.BACK, reData);
				bjm.append("status", "fail");
				bjm.append("error", "Not yet logged in!");
				ctx.writeAndFlush(bjm.toTWSFrame());
				return;
			}
			jm.append("frid", uid);
			ReMsg rm = rpcChatBoxService.chat(jm);
			if (rm.getCode() != ReCode.OK.reCode()) {
				Map<String, Object> reData = new HashMap<String, Object>();
				reData.put("reid", jm.getId());
				JsonMsg bjm = new JsonMsg(incrMsgId(), JsonMsg.BACK, reData);
				bjm.append("status", "fail");
				bjm.append("error", rm.getMsg());
				ctx.writeAndFlush(bjm.toTWSFrame());
			}
		}
	}
}
