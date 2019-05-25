package com.we.models.userTask;


import com.we.models.AbstractDocument;

/** 用户的任务 */
public class UserTask extends AbstractDocument {
	private static final long serialVersionUID = -2901429661726465378L;

	public static final int FAIL = -1;
	public static final int TODO = 1;
	public static final int DOING = 2;
	public static final int AUDIT = 3;
	public static final int FINISH = 4;
	public static final int SUCCESS = 6;

	// 主键自增长
	private long uid;// 用户ID
	private Long taskId;// 项目ID

	private int plan;// 当前进度，完成的工作量
	private int total = 1;
	private int status = 1;// 当前状态 1 可做任务 ，2 进行中 3 待审核 4 已经完成 -1 审核失败 6礼物已领取
	private String[] pics = null;// 需要上传图片任务，图片保存地址

	public UserTask(Long id, Long uid, Long taskId, int plan, int total, int status) {
		this._id = id;
		this.uid = uid;
		this.taskId = taskId;
		this.plan = plan;
		this.total = total;
		this.status = status;
		this.updateTime = System.currentTimeMillis();
	}

	public UserTask() {

	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public int getPlan() {
		return plan;
	}

	public void setPlan(int plan) {
		this.plan = plan;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String[] getPics() {
		return pics;
	}

	public void setPics(String[] pics) {
		this.pics = pics;
	}

}
