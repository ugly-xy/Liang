package com.zb.common.Constant;

public enum PushConst {
	STAR_SLAVE_CALLBACK(0, 1, 1),
	// ADD_FRIENDS(1, 1, 10), INVITEFRIENDS(2, 1, 10), CHAT(3, 1, 10),;
	STAR_SLAVE_BUY(4, 1, 1), ARTICLE_COMMENT(5, 1, 1),; // 推送文章评论
	PushConst(int index, int ehour, int eday) {
		this.index = index;
		this.ehour = ehour;
		this.eday = eday;
	}

	private int index;
	private int ehour;// 每小时限制几条
	private int eday;// 每天限制几条

	public int getIndex() {
		return index;
	}

	public int getEhour() {
		return ehour;
	}

	public int getEday() {
		return eday;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setEhour(int ehour) {
		this.ehour = ehour;
	}

	public void setEday(int eday) {
		this.eday = eday;
	}

	public static int FAIL_HOUR = 1;
	public static int FAIL_DAY = 24;
	public static int OK = 0;
}
