package com.zb.service.room.vo;

import java.io.Serializable;

public class RecommendUserInfoVO implements Serializable {

	private static final long serialVersionUID = -8653642240886314897L;
	private long uid;
	private String gameType;
	private String cover;
	private String nickname;
	private int type; // 1 游戏中 2 新人求带 3 老司机 4 玩过游戏
	private int sex; // 1nan2nv
	private Long score; // 权重
	private String avatar; // 头像
	private int vip;
	private int point;
	private String personLabel; // 个性签名
	private int friendWorth;

	public RecommendUserInfoVO() {
	}

	public RecommendUserInfoVO(long uid, String gameType, int vip, int point, String avatar, String personLabel,
			String nickname, int type, int sex, int friendWorth) {
		this.uid = uid;
		this.gameType = gameType;
		this.vip = vip;
		this.point = point;
		this.nickname = nickname;
		this.type = type;
		this.sex = sex;
		this.avatar = avatar;
		this.personLabel = personLabel;
		this.friendWorth = friendWorth;

	}

	public int getFriendWorth() {
		return friendWorth;
	}

	public void setFriendWorth(int friendWorth) {
		this.friendWorth = friendWorth;
	}

	public String getPersonLabel() {
		return personLabel;
	}

	public void setPersonLabel(String personLabel) {
		this.personLabel = personLabel;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof RecommendUserInfoVO) {
			if (this.getUid() == ((RecommendUserInfoVO) o).getUid())
				return true;
		}
		return false;
	}

}
