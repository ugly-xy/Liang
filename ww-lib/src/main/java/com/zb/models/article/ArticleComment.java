package com.zb.models.article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zb.common.Constant.Const;
import com.zb.models.AbstractDocument;

public class ArticleComment extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016361050004072890L;

	private long aid;
	private String content;
	private long userId;
	private int status = Const.STATUS_OK;
	private long adminId;// 最后操作管理员
	private int praiseCnt; // 赞数
	private long createTime;
	private Long baseId;// 有回复显示
	private Long cid;// 被评论的ID
	private String nickname;
	private String[] atUsers;
	private String[] pics;
	private List<Map> draws = new ArrayList<Map>(); // 画板集
	private String voiceUrl; // 语音

	public ArticleComment() {

	}

	public ArticleComment(long id, long aid, String content, long uid, Long baseId, String nickname, Long cid,
			String[] atUsers, String[] pics, List<Map> draws, String voiceUrl) {
		this._id = id;
		this.aid = aid;
		this.content = content;
		this.userId = uid;
		this.createTime = System.currentTimeMillis();
		this.updateTime = this.createTime;
		this.baseId = baseId;
		this.nickname = nickname;
		this.cid = cid;
		this.atUsers = atUsers;
		this.pics = pics;
		this.draws = draws;
		this.voiceUrl = voiceUrl;
	}

	public List<Map> getDraws() {
		return draws;
	}

	public void setDraws(List<Map> draws) {
		this.draws = draws;
	}

	public String getVoiceUrl() {
		return voiceUrl;
	}

	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}

	public String[] getAtUsers() {
		return atUsers;
	}

	public void setAtUsers(String[] atUsers) {
		this.atUsers = atUsers;
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

	public int getPraiseCnt() {
		return praiseCnt;
	}

	public void setPraiseCnt(int praiseCnt) {
		this.praiseCnt = praiseCnt;
	}

	public Long getBaseId() {
		return baseId;
	}

	public void setBaseId(Long baseId) {
		this.baseId = baseId;
	}

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

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String[] getPics() {
		return pics;
	}

	public void setPics(String[] pics) {
		this.pics = pics;
	}
}