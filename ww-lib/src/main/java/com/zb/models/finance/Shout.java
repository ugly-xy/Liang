package com.zb.models.finance;

import com.zb.models.AbstractDocument;

public class Shout extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016361050004072890L;

	private String content = "";// 内容
	private boolean all;// 是否全服喊话
	private int reply;// 1 可以评论 2 不可评论 3好友评论
	private int commentCnt; // 评论数
	private long uid;// 喊话用户
	private int level; // 128,298,588
	private int hitCount = 1;// 连击数
	// private int status = Const.STATUS_DEF;// 喊话 1，创建成功 2
	private String pic;
	private long createTime;
	private String nickname;
	private int sex;
	private String avatar;

	public static final int CANCOM = 1;
	public static final int CANNOTCOM = 2;
	public static final int FRICOM = 3;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	public int getReply() {
		return reply;
	}

	public void setReply(int reply) {
		this.reply = reply;
	}

	public int getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	// public int getStatus() {
	// return status;
	// }
	//
	// public void setStatus(int status) {
	// this.status = status;
	// }

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	

}
