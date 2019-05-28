package com.zb.models.room.activity;

import com.zb.models.room.Actor;

public class ChatBoxActor extends Actor {

	private static final long serialVersionUID = -8771559447212489389L;
	public static final int STATUS_DEFAULT = 0;// 可用
	public static final int STATUS_LOCK = -1;// 不可用
	public static final int STATUS_UP = 1;// 优先发言

	private int seat = -1;// 座位号
	private int userVoice = STATUS_DEFAULT;
	private String personLabel;

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public String getPersonLabel() {
		return personLabel;
	}

	public void setPersonLabel(String personLabel) {
		this.personLabel = personLabel;
	}

	public int getUserVoice() {
		return userVoice;
	}

	public void setUserVoice(int userVoice) {
		this.userVoice = userVoice;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Actor) {
			if (this.getUid() == ((ChatBoxActor) o).getUid())
				return true;
		}
		return false;
	}

	@Override
	public int compareTo(Object o) {
		ChatBoxActor a = (ChatBoxActor) o;
		long c = this.getInTime() - a.getInTime();
		if (c != 0) {
			return (int) c;
		}
		return (int) (this.getInTime() - a.getInTime());
	}

	@Override
	public int hashCode() {
		return ((Long) this.getUid()).hashCode();
	}

}
