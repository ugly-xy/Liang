package com.we.models;

/** 任务发布申请 */
public class ApplyForTask extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5358112736371829133L;
	private long uid;
	private String phone;
	private String name;
	private String WeChat;
	private String email;
	private String type;//任务类型
	private String taskDetail;// 任务介绍
	private long taskBudget;// 任务预算
	private long createTime;
	private int status;// 状态 已处理 忽略 待处理
	private Long adminId;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWeChat() {
		return WeChat;
	}

	public void setWeChat(String weChat) {
		WeChat = weChat;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(String taskDetail) {
		this.taskDetail = taskDetail;
	}

	public long getTaskBudget() {
		return taskBudget;
	}

	public void setTaskBudget(long taskBudget) {
		this.taskBudget = taskBudget;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ApplyForTask() {
		super();
	}

	public ApplyForTask(long uid, String phone, String name, String weChat, String email, String taskDetail,
			long taskBudget, int status, String type) {
		super();
		this.uid = uid;
		this.phone = phone;
		this.name = name;
		this.WeChat = weChat;
		this.email = email;
		this.taskDetail = taskDetail;
		this.taskBudget = taskBudget;
		this.status = status;
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((WeChat == null) ? 0 : WeChat.hashCode());
		result = prime * result + ((adminId == null) ? 0 : adminId.hashCode());
		result = prime * result + (int) (createTime ^ (createTime >>> 32));
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + status;
		result = prime * result + (int) (taskBudget ^ (taskBudget >>> 32));
		result = prime * result + ((taskDetail == null) ? 0 : taskDetail.hashCode());
		result = prime * result + (int) (uid ^ (uid >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplyForTask other = (ApplyForTask) obj;
		if (WeChat == null) {
			if (other.WeChat != null)
				return false;
		} else if (!WeChat.equals(other.WeChat))
			return false;
		if (adminId == null) {
			if (other.adminId != null)
				return false;
		} else if (!adminId.equals(other.adminId))
			return false;
		if (createTime != other.createTime)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (status != other.status)
			return false;
		if (taskBudget != other.taskBudget)
			return false;
		if (taskDetail == null) {
			if (other.taskDetail != null)
				return false;
		} else if (!taskDetail.equals(other.taskDetail))
			return false;
		if (uid != other.uid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ApplyForTask [uid=" + uid + ", phone=" + phone + ", name=" + name + ", WeChat=" + WeChat + ", email="
				+ email + ", taskDetail=" + taskDetail + ", taskBudget=" + taskBudget + ", createTime=" + createTime
				+ ", status=" + status + ", adminId=" + adminId + "]";
	}

}
