/**
 * 
 */
package com.zb.socket.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 说明：消息类型
 *
 * @author
 */
public enum MsgType {
	DEFAULT(0), // 木有
	AUTH(1), // 权限验证
	SYS(2), // 系统消息
	FORCE_OFF(3), // 用户其他设备登陆强制断连
	BACK(8), // 消息回执
	HEARTBEAT(9, false), // 心跳 不需要签名
	LOGIN(10), // 用户登录
	CHAT(11), // 用户发消息
	SYN(12), // 用户请求同步消息
	PUSH(13), // 用户收到新消息通知
	ADD_FRIENDS(14), // 添加好友
	ADD_FRIENDS_BACK(15), // 添加好友回复
	NOTICE(16), // 系统通知
	CHAT_PROP(17), // 聊天道具，比如骰子，剪刀石头布等
	PROMPT(18), // 消息提示
	INVITEFRIENDS(19), // 邀请好友玩游戏
	AT_USER(20), // @
	GAMECP_INVITE(21), // 双人游戏邀请游戏
	DEL_FRIENDS(22), // 拉黑好友消息
	UP_LEVEL(30), // 提示升级 vip或者level
	// 房间相关命令
	ROOM(100), // 进出房间
	ROOM_INFO(101), // 房间信息

	ACTIVITY_MATCHED(102), // 匹配成功消息

	UNDERCOVER(130), // 谁是卧底
	DRAWSOMETHING(131), // 你画我猜
	DICE(132), // 摇骰子，吹牛
	SLOTMACHINES(133), // 大乱斗
	WORLDCHAT(134), // 用户发世界消息
	STAR_SLAVE(135), // 奴隶
	WEREWOLF(136), // 狼人杀
	WEREWOLF6(137), // 狼人杀
	WEREWOLF9(138), // 狼人杀
	SOUTHPENGUIN(139), // 南极企鹅
	SCHOOL_WAR(140), // 教室大作战
	ANIMAL_CHESS(141), // 斗兽棋争霸
	GOMOKU(142), // 五子棋
	SAKURAN(143), // 花魁乱斗
	CHATBOX(144), // 语音聊天室
	WINMINE(145), // 扫雷
	NEURO_CAT(146), // 神经猫
	COW_FATHER(147), // 牛牛父类
	COW_LOW(148), // 牛牛低倍场
	COW_HIGH(149), // 牛牛高倍场
	WEREWOLF_FATHER(150), // 狼人杀父类
	GAME_CP(151), // 小游戏父类
	TEXAX_FATHER(152), // 德州父类
	TEXAS_LOW(153), // 德州低倍
	TEXAS_MID(154), // 德州中倍
	TEXAS_HIGH(155),// 德州高倍
	;

	private int t;
	private boolean sign = true;

	public int getT() {
		return t;
	}

	public boolean isSign() {
		return sign;
	}

	public static MsgType valueOf(int value) {
		switch (value) {
		case 1:
			return AUTH;
		case 2:
			return SYS;
		case 3:
			return FORCE_OFF;
		case 8:
			return BACK;
		case 9:
			return HEARTBEAT;
		case 10:
			return LOGIN;
		case 11:
			return CHAT;
		case 12:
			return SYN;
		case 13:
			return PUSH;
		case 14:
			return ADD_FRIENDS;
		case 15:
			return ADD_FRIENDS_BACK;
		case 16:
			return NOTICE;
		case 17:
			return CHAT_PROP;
		case 18:
			return PROMPT;
		case 19:
			return INVITEFRIENDS;
		case 100:
			return ROOM;
		case 101:
			return ROOM_INFO;
		case 130:
			return UNDERCOVER;
		case 131:
			return DRAWSOMETHING;
		case 132:
			return DICE;
		case 133:
			return SLOTMACHINES;
		case 134:
			return WORLDCHAT;
		case 136:
			return WEREWOLF;
		case 137:
			return WEREWOLF6;
		case 138:
			return WEREWOLF9;
		case 139:
			return SOUTHPENGUIN;
		case 140:
			return SCHOOL_WAR;
		case 141:
			return ANIMAL_CHESS;
		case 142:
			return GOMOKU;
		case 143:
			return SAKURAN;
		case 144:
			return CHATBOX;
		case 145:
			return WINMINE;
		case 146:
			return NEURO_CAT;
		case 148:
			return COW_LOW;
		case 149:
			return COW_HIGH;
		case 153:
			return TEXAS_LOW;
		case 154:
			return TEXAS_MID;
		case 155:
			return TEXAS_HIGH;
		}
		return DEFAULT;
	}

	@SuppressWarnings("all")
	public static final Set<Integer> specificRoomType = new HashSet() {
		{
			add(WORLDCHAT.getT());
			add(SLOTMACHINES.getT());
			add(SAKURAN.getT());
		}
	};
	@SuppressWarnings("all")
	public static final Set<Integer> canBeCreateRoomType = new HashSet() {
		{
			add(UNDERCOVER.getT());
			add(DRAWSOMETHING.getT());
			add(DICE.getT());
			add(WEREWOLF.getT());
			add(WEREWOLF6.getT());
			add(WEREWOLF9.getT());
			add(SOUTHPENGUIN.getT());
			add(COW_HIGH.getT());
			add(COW_LOW.getT());
			add(TEXAS_LOW.getT());
			add(TEXAS_MID.getT());
			add(TEXAS_HIGH.getT());
		}
	};

	@SuppressWarnings("all")
	public static final Map<Integer, Integer> wolfRoom = new HashMap() {
		{
			put(WEREWOLF.getT(), 12);
			put(WEREWOLF6.getT(), 6);
			put(WEREWOLF9.getT(), 9);
		}
	};

	@SuppressWarnings("all")
	public static final Set<Integer> matchRoomType = new HashSet() {
		{
			add(SCHOOL_WAR.getT());
			add(ANIMAL_CHESS.getT());
			add(WINMINE.getT());
			add(GOMOKU.getT());
			add(NEURO_CAT.getT());
		}
	};

	@SuppressWarnings("all")
	// 忽略房间状态的棋牌游戏
	public static final Set<Integer> ignoreRoomStatusType = new HashSet() {
		{
			add(COW_HIGH.getT());
			add(COW_LOW.getT());
			add(TEXAS_LOW.getT());
			add(TEXAS_MID.getT());
			add(TEXAS_HIGH.getT());
		}
	};

	private MsgType(int value) {
		this.t = value;
	}

	private MsgType(int value, boolean sign) {
		this.t = value;
		this.sign = sign;
	}

}
