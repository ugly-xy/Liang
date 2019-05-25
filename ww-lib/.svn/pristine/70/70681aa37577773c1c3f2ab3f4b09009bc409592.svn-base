package com.zb.models.res;

import com.zb.models.AbstractDocument;

public class ZbToolComment extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3703398879028922117L;

	public ZbToolComment() {

	}

	public ZbToolComment(Long id, String content, Integer star, Integer status,
			String nickname, Long userId) {
		this._id = id;
		this.updateTime = System.currentTimeMillis();
		this.content = content;
		this.nickname = nickname;
		this.userId = userId;
		this.star = star;
		this.status = status;
	}

	private String content;
	private Integer star;
	private Integer status;
	private Long userId;
	private String nickname;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
