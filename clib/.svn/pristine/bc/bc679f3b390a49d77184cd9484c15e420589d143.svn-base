package com.we.models.article;

import java.util.List;

import com.we.models.AbstractDocument;

public class Group extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016361050004072890L;

	public static final int DEL = -1;
	public static final int DOWN = 1;
	public static final int DRAFT = 2;
	public static final int UP = 3;

	public static final Long SYSTEM_GROUP = 1001L;

	private String name;
	private String discription;
	private String cover;  //封面
	private String branchPic;  //圈子banner
	private String tagPic;  //标签banner
	private String buttonPic; //发帖按钮图
	private Long createUid;
	private String[] tags;
	private int status;
	private Long adminId;// 最后操作管理员
	private int sort;
	private int memberCnt;// 读数
	private int articleCnt; // 赞数
	private int commentCnt; // 评论数
	private Long createTime;
	private Long master;
	private List<Long> leads = null;
	private Integer type; // 圈子类型

	public Group(long id, String name, String discription, String cover, long createUid, String[] tags, int status,
			int sort, long adminId, int memberCnt, int articleCnt, int commentCnt, long master, List<Long> leads,
			Integer type,String branchPic,String tagPic,String buttonPic) {
		this._id = id;
		this.name = name;
		this.discription = discription;
		this.cover = cover;
		this.createUid = createUid;
		this.tags = tags;
		this.sort = sort;
		this.status = status;
		this.adminId = adminId;
		this.memberCnt = memberCnt;
		this.articleCnt = articleCnt;
		this.commentCnt = commentCnt;
		this.master = master;
		this.leads = leads;
		this.updateTime = System.currentTimeMillis();
		this.createTime = this.updateTime;
		this.type = type;
		this.branchPic=branchPic;
		this.tagPic=tagPic;
		this.buttonPic=buttonPic;
	}

	public Group() {

	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Long getCreateUid() {
		return createUid;
	}

	public void setCreateUid(Long createUid) {
		this.createUid = createUid;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getMemberCnt() {
		return memberCnt;
	}

	public void setMemberCnt(int memberCnt) {
		this.memberCnt = memberCnt;
	}

	public int getArticleCnt() {
		return articleCnt;
	}

	public void setArticleCnt(int articleCnt) {
		this.articleCnt = articleCnt;
	}

	public int getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getMaster() {
		return master;
	}

	public void setMaster(Long master) {
		this.master = master;
	}

	public List<Long> getLeads() {
		return leads;
	}

	public void setLeads(List<Long> leads) {
		this.leads = leads;
	}

	public String getBranchPic() {
		return branchPic;
	}

	public void setBranchPic(String branchPic) {
		this.branchPic = branchPic;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTagPic() {
		return tagPic;
	}

	public void setTagPic(String tagPic) {
		this.tagPic = tagPic;
	}

	public String getButtonPic() {
		return buttonPic;
	}

	public void setButtonPic(String buttonPic) {
		this.buttonPic = buttonPic;
	}
}
