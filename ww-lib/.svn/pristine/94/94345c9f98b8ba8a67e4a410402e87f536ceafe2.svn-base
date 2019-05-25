package com.zb.socket.client.headler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.zb.socket.model.Msg;

public class DefaultClientHandler extends SimpleChannelInboundHandler<Msg> {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultClientHandler.class);

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Msg msg)
			throws Exception {
		Channel c = ctx.channel();
		logger.debug(c.remoteAddress().toString());

	}

}
