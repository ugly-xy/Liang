package com.zb.models.finance;

import com.zb.models.AbstractDocument;

public class ShoutComment extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016361050004072890L;

	private long sid;
	private String content;
	private long userId;
	private long adminId;// 最后操作管理员
	private long createTime;
	private Long baseId;// 有回复显示
	private Long cid;// 被评论的ID
	private String nickname;
	private Long reUid;
	private String reNickname;

	public ShoutComment() {

	}

	public ShoutComment(long id, long sid, String content, long userId, String nickname) {
		this._id = id;
		this.sid = sid;
		this.content = content;
		this.userId = userId;
		this.nickname = nickname;
	}

	public ShoutComment(long id, long sid, String content, long userId, String nickname, long baseId,Long reUid,String  reNickname) {
		this._id = id;
		this.sid = sid;
		this.content = content;
		this.userId = userId;
		this.nickname = nickname;
		this.baseId = baseId;
		this.reNickname = reNickname;
		this.reUid = reUid;
	}

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public Long getBaseId() {
		return baseId;
	}

	public void setBaseId(Long baseId) {
		this.baseId = baseId;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Long getReUid() {
		return reUid;
	}

	public void setReUid(Long reUid) {
		this.reUid = reUid;
	}

	public String getReNickname() {
		return reNickname;
	}

	public void setReNickname(String reNickname) {
		this.reNickname = reNickname;
	}
	
}