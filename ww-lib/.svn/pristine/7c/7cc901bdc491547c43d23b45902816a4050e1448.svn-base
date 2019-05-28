package com.zb.models.usertask;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Task implements Serializable {
	private static final long serialVersionUID = 4781371151711012512L;
	
	private String _id; // uid+"-"+day
	private long uid;
	private int day;// yyyyMMdd
	private long updateTime;
	private List<TaskModel> tasks;// 每日或者新手
	private List<TaskModel> circles;// 每日的圈子
	private List<TaskModel> vips;// 每日的vip

	public Task(long uid, int day, List<TaskModel> tasks) {// 新手
		super();
		this.uid = uid;
		this.day = day;
		this.tasks = tasks;
		this._id = uid + "-" + day;
	}

	public Task(long uid, int day, List<TaskModel> tasks, List<TaskModel> cirlces, List<TaskModel> vips) {// 每日
		super();
		this.uid = uid;
		this.day = day;
		this.tasks = tasks;
		this.circles = cirlces;
		this.vips = vips;
		this._id = uid + "-" + day;
	}

	public Task(long uid, int day) {
		super();
		this.uid = uid;
		this.day = day;
		this._id = uid + "-" + day;
	}

	public Task() {
		super();
	}

	@Override
	public String toString() {
		return "Task [uid=" + uid + ", day=" + day + ", tasks=" + tasks + "]";
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public List<TaskModel> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskModel> tasks) {
		this.tasks = tasks;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public List<TaskModel> getCircles() {
		return circles;
	}

	public void setCircles(List<TaskModel> circles) {
		this.circles = circles;
	}

	public List<TaskModel> getVips() {
		return vips;
	}

	public void setVips(List<TaskModel> vips) {
		this.vips = vips;
	}

	public static int getNewDay() {
		return 10000000;
	}

	public static int getDailyDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return Integer.parseInt(sdf.format(new Date()));
	}

	// 任务状态
	public static final int STATUS_DOING = 2; // 未完成
	public static final int STATUS_UNRECEIVE = 3; // 未领取
	public static final int STATUS_RECEIVED = 4; // 已领取

	// 任务类型
	public static final int TYPE_NEW = 1; // 新手任务
	public static final int TYPE_DAILY = 2; // 每日任务
	public static final int TYPE_VIP = 3; // VIP礼包
	public static final int TYPE_CIRCLE = 4; // 圈子任务
	// 每日登陆任务
	public static final int[][] LOGIN = new int[][] { { 2, 61 }};
	// 送玫瑰任务
	public static final int[][] FLOWER = new int[][] { { 1, 3 }, { 2, 29 } };
	// 你画我猜
	public static final int[][] DROWSTH = new int[][] { { 1, 1 }, { 2, 25 } };
	// 谁是卧底
	public static final int[][] UNDERCOVER_ISUNDER = new int[][] { { 1, 1 }, { 2, 26 } }; // 是卧底
	public static final int[][] UNDERCOVER_NOTUNDER = new int[][] { { 1, 1 } }; // 不是卧底
	// 吹牛
	public static final int[][] DICE = new int[][] { { 1, 1 }, { 2, 27 } };
	// 王者大乱斗
	public static final int[][] SLOT_MACHINESE = new int[][] { { 1, 1 }, { 2, 28 } };
	// 添加好友
	public static final int[][] ADD_FRIEND = new int[][] { { 1, 2 } };
	// 上传封面
	public static final int[][] UPLOADING_COVER = new int[][] { { 1, 4 } };
	// 绑定手机号
	public static final int[][] BINDING_PHONE = new int[][] { { 1, 5 } };
	// 分享至社交平台
	public static final int[][] SHARE_QQWECHAT = new int[][] { { 1, 6 }, { 2, 30 } };
	// 苹果五星好评
	public static final int[][] ITUNES_5_STAR = new int[][] { { 1, 7 } };
	// 充值
	public static final int[][] PAY_MONEY = new int[][] { { 2, 31 } };

	// 发表文章
	public static final int[][] CRICLE_PUBLISH = new int[][] { { 4, 48 } };
	// 回复文章
	public static final int[][] CRICLE_COMMENT = new int[][] { { 4, 49 } };
	// 被回复
	public static final int[][] CRICLE_BE_COMMENT = new int[][] { { 4, 51 } };
	// 分享文章
	public static final int[][] CRICLE_SHARE = new int[][] { { 4, 50 } };
	// 精选优质文章
	public static final int[][] CRICLE_GOOD = new int[][] { { 4, 52 } };

	// 发表文章
	public static final int CC_PUBLISH = 48;
	// 回复文章
	public static final int CC_COMMENT = 49;
	// 被回复
	public static final int CC_BE_COMMENT = 51;
	// 分享文章
	public static final int CC_SHARE = 50;
	// 精选优质文章
	public static final int CC_GOOD = 52;

	public enum TaskType {

	}
}
