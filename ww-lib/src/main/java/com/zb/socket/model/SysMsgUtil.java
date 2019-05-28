package com.zb.socket.model;

import java.util.HashMap;

/**
 * 说明：消息对象
 *
 * @author walker.bao
 * @param <T>
 */
public class SysMsgUtil {

	public static synchronized Msg toSysMsg(Msg msg) {
		return new Msg(0L, MsgType.SYS, msg);
	}

	public static synchronized Msg toMsg(Msg msg) {
		return (Msg) msg.getO();
	}

	public static void main(String[] args) {
		Msg m = new Msg(1L, MsgType.BACK, new HashMap());
		Msg m1 = SysMsgUtil.toSysMsg(m);
		Msg m2 = SysMsgUtil.toMsg(m1);
		System.out.println(m2.get_id());
	}
}
