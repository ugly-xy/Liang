package com.zb.models.room.activity;

import com.zb.models.goods.GoodsItem;
import com.zb.models.room.Actor;

public class WerewolfKillActor extends Actor {

	private static final long serialVersionUID = 7403281649240157685L;
	public static final int A_STATUS_START = 0;
	public static final int A_STATUS_SPEAK = 1;

	public static final int ALIVE = 0;
	public static final int WOLF_KILL = 1;
	public static final int WITCH_KILL = 2;
	public static final int HUNTER_KILL = 3;
	public static final int VOTE_KILL = 4;
	public static final int BOOM = 5;
	public static final int DEFENDANDSAVE = 6;

	public static final int VIEWER = 0;
	public static final int WOLF = 1;
	public static final int VILLAGE = 2;
	public static final int PROPHET = 3;
	public static final int WITCH = 4;
	public static final int HUNTER = 5;
	public static final int SB = 6;
	public static final int DEFENDER = 7;

	public static final int WOLFWIN = 1;
	public static final int GOODWIN = 2;

	public static final int STATUS_SEND_ROLE = 2; // 发身份
	// public static final int NIGHT = 3; // 夜晚
	public static final int STATUS_KILL_CHECK = 3; // 杀人&验人
	public static final int STATUS_SAVE_POISON = 4; // 救人&毒人
	public static final int STATUS_SPEAK = 5; // 发言
	public static final int STATUS_VOTE = 6; // 投票
	public static final int STATUS_SHOW_VOTE = 7; // 展示投票宣布死亡
	public static final int STATUS_REVENGE = 8;// 猎人开枪
	// public static final int STATUS_EVADE = 9; // 白痴翻牌
	public static final int STATUS_LAST_WORDS = 9; // 发表遗言
	public static final int STATUS_END = 10; // 游戏结束
	public static final int STATUS_SHOW_END = 11; // 展示结果
	public static final int STATUS_DEFEND = 12; // 守卫阶段
	public static final int STATUS_CALCULATION = 13; // 计算死人阶段
	public static final int STATUS_BUY = 14; // 买身份
	public static final int STATUS_DAY = 15; // 天亮了

	public static final int START_TIME = 1;
	public static final int BUY_TIME = 5;
	public static final int SEND_ROLE_TIME = 3;
	public static final int KILL_CHECK_TIME = 25;
	public static final int SAVE_POISON_TIME = 15;
	public static final int SPEAK_TIME = 15;
	public static final int REAL_SPEAK_TIME = 60;
	public static final int VOTE_TIME = 15;
	public static final int SHOW_VOTE_TIME = 3;
	public static final int REVENGE_TIME = 60;
	public static final int REVENGED_TIME = 1;
	public static final int LAST_WORDS_TIME = 15;
	public static final int END_TIME = 2;
	public static final int BOOM_TIME = 3;
	public static final int SHOW_END_TIME = 3;
	public static final int DEFEND_TIME = 15;
	public static final int CALCULATION_TIME = 1;
	public static final int DAY_TIME = 5;

	public static final int FIRST_DAY = 1;
	public static final int WIN_COIN = 50;
	public static final int WIN_POINT = 200;
	public static final int LOSE_POINT = 100;

	public static final int BUYTYPE = GoodsItem.Money.COIN.getV().getId();

	private int seat = -1;

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	@Override
	public int compareTo(Object o) {
		// System.out.println(o + "c");
		WerewolfKillActor a = (WerewolfKillActor) o;
		return (int) (this.getInTime() - a.getInTime());
	}

	// @Override
	// public int hashCode() {
	// System.out.println(this.hashCode());
	// return this.hashCode();
	// }

	@Override
	public boolean equals(Object o) {
		// System.out.println(o + "e");
		if (o instanceof Actor) {
			if (this.getUid() == ((WerewolfKillActor) o).getUid())
				return true;
		}
		return false;
	}

	// @Override
	// public String toString() {
	// return this.getUid() + "";
	// }

}
