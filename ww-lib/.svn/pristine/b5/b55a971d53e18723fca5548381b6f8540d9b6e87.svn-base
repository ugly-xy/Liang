package com.zb.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zb.models.usertask.Task;
import com.zb.models.usertask.TaskModel;

/**
 * @author wangzc
 *
 */
public class TaskVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7499925287567008249L;
	private List<TaskModelVO> tasks;

	public List<TaskModelVO> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskModelVO> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "TaskVO [tasks=" + tasks + "]";
	}

	public TaskVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TaskVO(Task task, int money, boolean showAppstore) {
		this.tasks = new ArrayList<TaskModelVO>();
		List<TaskModel> list = task.getTasks();
		for (TaskModel taskModel : list) {
			if ("appStore".equals(taskModel.getOp())) {
				if (!showAppstore) {
					continue;
				}
			}
			this.tasks.add(new TaskModelVO(taskModel, money));
		}
	}

	public class TaskModelVO implements Serializable {

		private static final long serialVersionUID = -4519211672181138663L;

		private long id; // id
		private String tmPic; // 任务图片
		private String tmTitle; // 任务标题 免费赚金币 免费赚经验
		private int money; // 当前充值钱数
		private String tmName; // 任务名称
		private String tmReward; // 任务奖励
		private int tmType; // 任务类型 标志位 1代表每日 2代表新手任务 3VIP
		private int tmPlan;// 任务当前进度
		private int tmEndCondition;// 任务结束条件 当进度不小于结束条件则结束
		private int tmStatus; // 任务是否已经结束 标志位 1代表未完成 2代表已完成待领取 3代表已领取
		private String op; // 跳转范围 沿用OperationType
		private int opid; // 范围下的id 无则置为0

		public TaskModelVO(TaskModel tm, int money) {
			super();
			this.id = tm.get_id();
			this.tmPic = tm.getTmPic();
			this.tmTitle = tm.getTmTitle();
			this.tmName = tm.getTmName();
			this.tmReward = tm.getTmReward();
			this.tmType = tm.getTmType();
			this.tmPlan = tm.getTmPlan();
			this.tmEndCondition = tm.getTmEndCondition();
			this.tmStatus = tm.getTmStatus();
			this.op = tm.getOp();
			this.opid = tm.getOpid();
			this.money = money;
		}

		public TaskModelVO() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "TaskModelVO [id=" + id + ", tmPic=" + tmPic + ", tmTitle=" + tmTitle + ", tmName=" + tmName
					+ ", tmReward=" + tmReward + ", tmType=" + tmType + ", tmPlan=" + tmPlan + ", tmEndCondition="
					+ tmEndCondition + ", tmStatus=" + tmStatus + ", op=" + op + ", opid=" + opid + "]";
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
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

		public int getMoney() {
			return money;
		}

		public void setMoney(int money) {
			this.money = money;
		}
	}
}
