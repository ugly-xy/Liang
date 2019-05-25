package com.we.socket.model;

import java.util.HashMap;

/**
 * 说明：消息对象
 *
 * @author walker.bao
 * @param <T>
 */
public class SysMsgUtil {

	public static synchronized Msg toSysMsg(Msg msg) {
		return new Msg(0L, MsgType.DEFAULT, msg);
	}

	public static synchronized Msg toMsg(Msg msg) {
		return (Msg) msg.getO();
	}

	public static void main(String[] args) {
	}
}
