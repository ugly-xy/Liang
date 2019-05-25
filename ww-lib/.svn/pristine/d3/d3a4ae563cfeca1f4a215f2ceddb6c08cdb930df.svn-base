package com.zb.models.room.activity;

import java.io.Serializable;

/** 神经猫坐标中心数据 */
public class NeuroCatBox implements Serializable {

	private static final long serialVersionUID = -655480697446969649L;

	public static final int EXIST_YES = 1;// 存在
	public static final int EXIST_STARTING_LEVER = -1;// 首杆
	public static final int EXIST_NO = 0;// 默认不存在

	private int top = EXIST_NO;
	private int below = EXIST_NO;
	private int left = EXIST_NO;
	private int right = EXIST_NO;
	private int cat = EXIST_NO;

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getBelow() {
		return below;
	}

	public void setBelow(int below) {
		this.below = below;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getCat() {
		return cat;
	}

	public void setCat(int cat) {
		this.cat = cat;
	}

	public NeuroCatBox(int cat) {
		super();
		this.cat = cat;
	}

	public NeuroCatBox() {
		super();
	}

	public int getDataByAround(int around) {
		if (around == 0) {
			return this.cat;
		} else if (around == 1) {
			return this.top;
		} else if (around == 2) {
			return this.below;
		} else if (around == 3) {
			return this.left;
		} else if (around == 4) {
			return this.right;
		}
		return EXIST_NO;
	}

	public void setDataByAround(int around, int data) {
		if (around == 0) {
			this.cat = data;
		} else if (around == 1) {
			this.top = data;
		} else if (around == 2) {
			this.below = data;
		} else if (around == 3) {
			this.left = data;
		} else if (around == 4) {
			this.right = data;
		}
	}
}
