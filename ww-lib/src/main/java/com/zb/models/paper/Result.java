package com.zb.models.paper;


public class Result {
	private Long id;
	private Long pId;
	private double score;//分数分值划分区间，如果是结果型，应该相等
	private String title;
	private String content;
	private int contentType;//0 没有content，1图片 ，2文本
	
	public Result(){
		
	}
	
	public Result(Integer contentType,double score,String title,String content){
		this.score = score;
		this.title = title;
		this.content =content;
		this.contentType = contentType;
	}
	
	public Result(Long id,Long pId,double score,String title,String content,int contentType){
		this.id = id;
		this.pId = pId;
		this.score = score;
		this.title = title;
		this.content =content;
		this.contentType = contentType;
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getContentType() {
		return contentType;
	}
	public void setContentType(int contentType) {
		this.contentType = contentType;
	}
	
	
	
}
