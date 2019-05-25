package com.zb.service.room.vo;

import java.util.Map;

public class ChatBoxRoomInfo extends RoomInfo {

	private static final long serialVersionUID = 2209498888853314927L;

	public ChatBoxRoomInfo(int type) {
		super(type);
	}

	private Map<Long, Long> lockUsers;
	private long[] seatUserTable = new long[13]; // 座次顺序
	private String rName;

	private boolean voice;
	private boolean wordHome;

	public Map<Long, Long> getLockUsers() {
		return lockUsers;
	}

	public void setLockUsers(Map<Long, Long> lockUsers) {
		this.lockUsers = lockUsers;
	}

	public boolean isVoice() {
		return voice;
	}

	public void setVoice(boolean voice) {
		this.voice = voice;
	}

	public boolean isWordHome() {
		return wordHome;
	}

	public void setWordHome(boolean wordHome) {
		this.wordHome = wordHome;
	}

	public long[] getSeatUserTable() {
		return seatUserTable;
	}

	public void setSeatUserTable(long[] seatUserTable) {
		this.seatUserTable = seatUserTable;
	}

	public String getrName() {
		return rName;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}

}
