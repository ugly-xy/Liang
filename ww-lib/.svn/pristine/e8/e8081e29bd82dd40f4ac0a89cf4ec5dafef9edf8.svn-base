package com.zb.models.usertask;

import java.util.ArrayList;
import java.util.List;

import com.zb.models.AbstractDocument;

/*
 * 任务对象模型
 */
public class TaskModel extends AbstractDocument {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4752407286432731940L;
	private String tmPic; // 任务图片
	private String tmTitle; // 任务标题 免费赚金币 免费赚经验

	private String tmName; // 任务名称
	private String tmReward; // 任务奖励
	private int tmType; // 任务类型 标志位 1代表每日 2代表新手任务 3VIP
	private int tmPlan;// 任务当前进度
	private int tmEndCondition;// 任务结束条件 当进度不小于结束条件则结束
	private int tmStatus; // 任务是否已经结束 标志位 1代表未完成 2代表已完成待领取 3代表已领取
	private String op; // 跳转范围 沿用OperationType
	private int opid; // 范围下的id 无则置为0
    private int sort;//排序
	private int status;// 任务模型 状态 上下线删除
	private String rewards;//用于奖励发放

	public TaskModel(long tmId, String tmName, String tmReward, int tmType, int tmPlan, int tmEndCondition,
			int tmStatus, int status,String rewards, String op, int opid,int sort) {
		super();
		this._id = tmId;
		this.tmName = tmName;
		this.tmReward = tmReward;
		this.tmType = tmType;
		this.tmPlan = tmPlan;
		this.tmEndCondition = tmEndCondition;
		this.tmStatus = tmStatus;
		this.rewards = rewards;
		this.updateTime = System.currentTimeMillis();
		this.status = status;
		this.op = op;
		this.opid = opid;
		this.sort=sort;
	}

	public TaskModel(long tmId, String tmPic, String tmTitle, String tmName, String tmReward, int tmType, int tmPlan,
			int tmEndCondition, int tmStatus, int status, String rewards, String op, int opid,int sort) {
		super();
		this.tmPic = tmPic;
		this.tmTitle = tmTitle;
		this._id = tmId;
		this.tmName = tmName;
		this.tmReward = tmReward;
		this.tmType = tmType;
		this.tmPlan = tmPlan;
		this.tmEndCondition = tmEndCondition;
		this.tmStatus = tmStatus;
		this.rewards = rewards;
		this.status = status;
		this.updateTime = System.currentTimeMillis();
		this.op = op;
		this.opid = opid;
		this.sort=sort;
	}

	public TaskModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "TaskModel [tmPic=" + tmPic + ", tmTitle=" + tmTitle + ", tmName=" + tmName + ", tmReward=" + tmReward
				+ ", tmType=" + tmType + ", tmPlan=" + tmPlan + ", tmEndCondition=" + tmEndCondition + ", tmStatus="
				+ tmStatus + ", op=" + op + ", opid=" + opid + ", sort=" + sort + ", status=" + status + ", rewards="
				+ rewards + ", _id=" + _id + ", updateTime=" + updateTime + "]";
	}

	public String getTmName() {
		return tmName;
	}

	public void setTmName(String tmName) {
		this.tmName = tmName;
	}

	public String getTmReward() {
		return tmReward;
	}

	public void setTmReward(String tmReward) {
		this.tmReward = tmReward;
	}

	public int getTmType() {
		return tmType;
	}

	public void setTmType(int tmType) {
		this.tmType = tmType;
	}

	public int getTmPlan() {
		return tmPlan;
	}

	public void setTmPlan(int tmPlan) {
		this.tmPlan = tmPlan;
	}

	public int getTmEndCondition() {
		return tmEndCondition;
	}

	public void setTmEndCondition(int tmEndCondition) {
		this.tmEndCondition = tmEndCondition;
	}

	public int getTmStatus() {
		return tmStatus;
	}

	public void setTmStatus(int tmStatus) {
		this.tmStatus = tmStatus;
	}

	public String getRewards() {
		return rewards;
	}

	public void setRewards(String rewards) {
		this.rewards = rewards;
	}

	public String getTmPic() {
		return tmPic;
	}

	public void setTmPic(String tmPic) {
		this.tmPic = tmPic;
	}

	public String getTmTitle() {
		return tmTitle;
	}

	public void setTmTitle(String tmTitle) {
		this.tmTitle = tmTitle;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public int getOpid() {
		return opid;
	}

	public void setOpid(int opid) {
		this.opid = opid;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
