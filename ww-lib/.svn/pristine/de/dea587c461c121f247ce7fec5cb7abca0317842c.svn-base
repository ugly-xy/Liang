package com.zb.models.room;

import java.io.Serializable;

public class Actor implements Serializable, Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 923485932744604178L;

	// public static final int A_STATUS_START = 3;// 游戏开始
	public static final int A_STATUS_READY = 2;// 准备开始
	public static final int A_STATUS_IN_ROOM = 1;// 进入房间
	// public static final int A_STATUS_DIE = -1;// 死亡

	public static final int STATUS_ONLINE = 3;// 在线
	public static final int STATUS_OFFLINE = 2;// 离线
	public static final int STATUS_OUT_ROOM = 1;// 退出房间

	public static final int ROLE_VIEWER = 2;// 观众
	public static final int ROLE_EXECUTOR = 1;// 执行人

	private long uid;
	private int status = STATUS_ONLINE;// 用户状态
	private int ucStatus = A_STATUS_IN_ROOM;// 活动状态
	private String nickname;
	private String avatar;
	private int vip;
	private int sex;
	private int point;
	private int role = ROLE_EXECUTOR;// 房间内角色 是否观众
	private boolean reduceCoin = false;
	private long inTime = System.currentTimeMillis();
	private boolean robit = false;
	private boolean owner = false; // 房主
	private boolean buyUser = false;// 房间购买人 最高权限

	private String city;
	private String province;

	private String game; // 正在玩的游戏

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	// private long no;
	private boolean dead = false;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	// public long getRoomId() {
	// return roomId;
	// }
	//
	// public void setRoomId(long roomId) {
	// this.roomId = roomId;
	// }

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getUcStatus() {
		return ucStatus;
	}

	public void setUcStatus(int activityStatus) {
		this.ucStatus = activityStatus;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public boolean isReduceCoin() {
		return reduceCoin;
	}

	public void setReduceCoin(boolean reduceCoin) {
		this.reduceCoin = reduceCoin;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public long getInTime() {
		return inTime;
	}

	public void setInTime(long inTime) {
		this.inTime = inTime;
	}

	@Override
	public int compareTo(Object o) {
		Actor a = (Actor) o;
		long c = this.inTime - a.inTime;
		if (c != 0) {
			return (int) c;
		}
		return (int) (this.uid - a.uid);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Actor) {
			if (this.uid == ((Actor) o).uid)
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return ((Long) this.uid).hashCode();
	}

	public boolean isRobit() {
		return robit;
	}

	public void setRobit(boolean robit) {
		this.robit = robit;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

	public boolean isBuyUser() {
		return buyUser;
	}

	public void setBuyUser(boolean buyUser) {
		this.buyUser = buyUser;
	}
}
