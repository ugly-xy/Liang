package com.zb.models;

import java.util.List;

public class UserTitle extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2514471068911636318L;
	public static final int FLOWER = 1;// 花魁榜
	public static final int COIN = 2;// 充值金币
	public static final int FLOWER_SEND = 3;// 送花
	public static final int FLOWER_RECV = 4;// 收花
	public static final int FLOWER_GROUP = 5;// 圈子
	public static final int NEWYEAR = 6;// 新年活动
	public static final int SAKURAN_WIN = 7;// 花魁乱斗赢金
	public static final int COW_WIN = 8;// 牛牛赢金
	public static final int TEXAS_WIN = 9;// 德州赢金

	private List<TitleModel> list;

	public List<TitleModel> getList() {
		return list;
	}

	public void setList(List<TitleModel> list) {
		this.list = list;
	}

	public UserTitle(long id, List<TitleModel> list) {
		this._id = id;
		this.list = list;
		this.updateTime = System.currentTimeMillis();
	}

	public UserTitle() {
	}

}
