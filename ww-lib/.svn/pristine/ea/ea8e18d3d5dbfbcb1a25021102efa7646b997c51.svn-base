package com.zb.models.room.activity;

import java.util.HashSet;
import java.util.Set;

import com.zb.models.room.ActivityCP;

public class AnimalChess extends ActivityCP {
	private static final long serialVersionUID = -86832852028064510L;

	public static final int STATUS_PLAY = 2;// 开始下棋
	public static final int STATUS_END = 3;// 结束

	private Set<Long> actors = new HashSet<Long>();
	private boolean play = false;// 本局有没有行动 如果是false 即60s未行动 结束游戏
	private long player;// 本局行走者
	private long colorRed;// 红方
	private long colorBlue;// 蓝方
	private int stepCnt = 0;// 行走局数
	private int endCnt = 0;// 可以强制结束次数
	private int[][][] chessboard;// 棋盘节点以及对应棋子状态

	public int[][][] getChessboard() {
		return chessboard;
	}

	public void setChessboard(int[][][] chessboard) {
		this.chessboard = chessboard;
	}

	public boolean isPlay() {
		return play;
	}

	public void setPlay(boolean play) {
		this.play = play;
	}

	public long getPlayer() {
		return player;
	}

	public void setPlayer(long player) {
		this.player = player;
	}

	public long getColorRed() {
		return colorRed;
	}

	public void setColorRed(long colorRed) {
		this.colorRed = colorRed;
	}

	public long getColorBlue() {
		return colorBlue;
	}

	public void setColorBlue(long colorBlue) {
		this.colorBlue = colorBlue;
	}

	public int getEndCnt() {
		return endCnt;
	}

	public void setEndCnt(int endCnt) {
		this.endCnt = endCnt;
	}

	public int getStepCnt() {
		return stepCnt;
	}

	public void setStepCnt(int stepCnt) {
		this.stepCnt = stepCnt;
	}

	public Set<Long> getActors() {
		return actors;
	}

	public void putActors(long uid) {
		this.actors.add(uid);
	}

}
