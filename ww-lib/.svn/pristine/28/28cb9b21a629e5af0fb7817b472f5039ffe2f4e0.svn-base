package com.zb.models.usertask;

import com.zb.models.AbstractDocument;

public class TaskLog extends AbstractDocument {
	private static final long serialVersionUID = -386661081275141106L;
    
	private long userId; //用户id
	private String taskId;//所属的任务id
	private int taskType;//任务类型
	private long taskModelId; //任务模型的id
	private String taskName;//任务描述
	private String taskRewards; //任务奖励描述
	private int plan;//当前进度
	private int end;//结束条件
	private int status; //任务状态
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public long getTaskModelId() {
		return taskModelId;
	}
	public void setTaskModelId(long taskModelId) {
		this.taskModelId = taskModelId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskRewards() {
		return taskRewards;
	}
	public void setTaskRewards(String taskRewards) {
		this.taskRewards = taskRewards;
	}
	public int getPlan() {
		return plan;
	}
	public void setPlan(int plan) {
		this.plan = plan;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public TaskLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TaskLog(long userId, String taskId, int taskType, long taskModelId, String taskName, String taskRewards,
			int plan, int end, int status) {
		super();
		this.userId = userId;
		this.taskId = taskId;
		this.taskType = taskType;
		this.taskModelId = taskModelId;
		this.taskName = taskName;
		this.taskRewards = taskRewards;
		this.plan = plan;
		this.end = end;
		this.status = status;
	}
	@Override
	public String toString() {
		return "TaskLog [userId=" + userId + ", taskType=" + taskType + ", taskModelId=" + taskModelId + ", taskName="
				+ taskName + ", taskRewards=" + taskRewards + ", plan=" + plan + ", end=" + end + ", status=" + status
				+ "]";
	}
	
}
