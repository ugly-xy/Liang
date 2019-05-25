package com.zb.models.room.activity;

import java.util.HashMap;
import java.util.Map;

import com.zb.models.room.Activity;

public class ChatBox extends Activity {

	private static final long serialVersionUID = -2849472570646215073L;

	public static final int STATUS_OK = 0;// 可用
	public static final int STATUS_LOCK = -1;// 不可用

	private long[] seatUserTable = new long[13]; // 座次顺序

	private Map<Long, Long> lockUsers = new HashMap<Long, Long>();// 被禁麦的用户

	private boolean wordHome = true; // 开启文字聊天
	private boolean voice = true;// 开启语音聊天

	public long[] getSeatUserTable() {
		return seatUserTable;
	}

	public void setSeatUserTable(long[] seatUserTable) {
		this.seatUserTable = seatUserTable;
	}

	public boolean isVoice() {
		return voice;
	}

	public void setVoice(boolean voice) {
		this.voice = voice;
	}

	public void addLockUser(long uid, Long time) {
		this.lockUsers.put(uid, time);
	}

	public void removeLockUser(long uid) {
		this.lockUsers.remove(uid);
	}

	public Map<Long, Long> getLockUsers() {
		return lockUsers;
	}

	public boolean isWordHome() {
		return wordHome;
	}

	public void setWordHome(boolean wordHome) {
		this.wordHome = wordHome;
	}

}