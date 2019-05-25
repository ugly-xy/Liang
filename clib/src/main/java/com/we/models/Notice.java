package com.we.models;

/** 公告 */
public class Notice extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6260890925457156479L;

	private long createTime;
	private String title;
	private String content;
	private Long adminId;
	private int status;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Notice() {
		super();
	}

	public Notice(long createTime, String title, String content, Long adminId, int status) {
		super();
		this.title = title;
		this.createTime = createTime;
		this.content = content;
		this.adminId = adminId;
		this.status = status;
	}

}
