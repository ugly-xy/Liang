package com.zb.models.room;

import com.zb.models.AbstractDocument;

public class Room extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2384872531597310677L;

	public static final int SELL_STATUS_SHELVES = 1;// 销售状态，不可售
	public static final int SELL_STATUS_SALES = 2;// 上架
	public static final int SELL_STATUS_SOLD = 3;// 已售
	public static final int SELL_STATUS_OBLIGATION = 4;// 已被抽取未付款

	public static final int USE_STATUS_OK = 2;// 可用
	public static final int USE_STATUS_LOCK = 1;// 不可用

	// public static final int CLOSE = 0;//所有人推出房间，房间处于关闭状态，可重启开启
	public static final int OPEN = 1;// 房间内有人，房间处于开启状态,但是房间没有进入某一项活动
	public static final int ACTIVITY = 2;// 房间处于活动状态

	// public static final int INNING_TIED = 1;

	private Long uid;// 房主Id 类似于房管
	private Long buyUid;// 房间购买人
	// private Long roomNo;//房间号
	private int useStatus = 2;// 用户使用状态 1 不可用 2 可用
	private int sellStatus;// 1 不可售 2 可售 3 已售
	private boolean sys;// 是否系统房间
	private Long expiry = 0L;// 有效期
	private Long createDate;// 创建时间
	private int type;// 房间类型 游戏n／直播／语音
	private int status = OPEN;
	private boolean pub = true;// 是否私密 私密状态房主邀请才能请入
	private String pwd;// 房间密码 
	// private int listprice;// 房间定价
	// private int price;// 当前售价
	private int count;// 房间总人数
	private int robitCount;// 房间机器人数
	private long activityDate;
	private String vipRoomType;// 房间号类型 aaa aab abc
	private int numLength;// 房间号长度
	private Long lotteryTime;// 被抽走的时间
	private String rName;// 房间名称
	private Integer attenCnt;// 当前房间关注人数
	// private long no;//第几个活动

	// private RoomExt ext = null;

	public Room(Long id, Long uid, int sellStatus, boolean sys, int type, int status) {
		this._id = id;
		this.uid = uid;
		this.sellStatus = sellStatus;
		this.sys = sys;
		this.createDate = System.currentTimeMillis();
		this.updateTime = createDate;
		this.type = type;
		this.status = status;
		this.numLength = transLength(id);
	}

	public Room(Long id, Long uid, int sellStatus, boolean sys, int type, int status, boolean pub, String vipRoomType) {
		this._id = id;
		this.uid = uid;
		this.sellStatus = sellStatus;
		this.sys = sys;
		this.createDate = System.currentTimeMillis();
		this.updateTime = createDate;
		this.type = type;
		this.status = status;
		this.vipRoomType = vipRoomType;
		this.numLength = transLength(id);
		this.pub = pub;
	}

	public Room() {

	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public int getSellStatus() {
		return sellStatus;
	}

	public void setSellStatus(int sellStatus) {
		this.sellStatus = sellStatus;
	}

	public boolean isSys() {
		return sys;
	}

	public void setSys(boolean sys) {
		this.sys = sys;
	}

	public Long getExpiry() {
		return expiry;
	}

	public void setExpiry(Long expiry) {
		this.expiry = expiry;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isPub() {
		return pub;
	}

	public void setPub(boolean pub) {
		this.pub = pub;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(int useStatus) {
		this.useStatus = useStatus;
	}

	public long getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(long activityDate) {
		this.activityDate = activityDate;
	}

	public int getRobitCount() {
		return robitCount;
	}

	public void setRobitCount(int robitCount) {
		this.robitCount = robitCount;
	}

	public String getVipRoomType() {
		return vipRoomType;
	}

	public void setVipRoomType(String vipRoomType) {
		this.vipRoomType = vipRoomType;
	}

	public int getNumLength() {
		return numLength;
	}

	public void setNumLength(int numLength) {
		this.numLength = numLength;
	}

	public int transLength(long roomId) {
		return String.valueOf(roomId).length();
	}

	public Long getLotteryTime() {
		return lotteryTime;
	}

	public void setLotteryTime(Long lotteryTime) {
		this.lotteryTime = lotteryTime;
	}

	public Long getBuyUid() {
		return buyUid;
	}

	public void setBuyUid(Long buyUid) {
		this.buyUid = buyUid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getrName() {
		return rName;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}

	public Integer getAttenCnt() {
		return attenCnt;
	}

	public void setAttenCnt(Integer attenCnt) {
		this.attenCnt = attenCnt;
	}

}
