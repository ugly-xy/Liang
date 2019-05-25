package com.zb.models.paper;

import java.util.ArrayList;
import java.util.List;

import com.zb.models.AbstractDocument;

public class Paper extends AbstractDocument {
	public static final int DEL = -1;//删除
	public static final int DOWN = 1;//下线
	public static final int UP = 2;//上线
	public static final int OK = 3;// 上首页
	
	public static final int ISPAY = 1;
	public static final int NOTPAY = -1 ;
	/**
	 * 
	 */
	private static final long serialVersionUID = 434361807307900409L;
	private int type;// 1 分数型 2跳转型
	private String title;
	
	private String introduce;//介绍
	private String logo;//图标
	private String backPic;//展示图
	private Integer count = 0;//使用次数
	
	private String content;//0 没有content，1图片 ，2文本
	
	private int pay;//1付费 -1免费 
	private double sort=1;//排序
	private int status; // -1 删除 1 下线 2上线 3 上圈子首页
	private Long createTime;//创建时间
	private List<Question> questions = new ArrayList<Question>();

	private List<Result> result = new ArrayList<Result>();

	
	public Paper(){
		
	}
	
	public Paper(Long id,int type,String title,String introduce,String logo,String backPic,String content,int count,int pay,double sort,int status,
			List<Question> questions,List<Result> result){
		this._id = id;
		this.type = type;
		this.title =title;
		this.introduce = introduce;
		this.logo = logo;
		this.backPic = backPic;
		this.count = count;
		this.content =content;
		this.pay = pay;
		this.sort = sort;
		this.status = status;
		this.createTime = System.currentTimeMillis();
		this.updateTime = this.createTime;
		this.questions = questions;
		this.result = result;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getBackPic() {
		return backPic;
	}

	public void setBackPic(String backPic) {
		this.backPic = backPic;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPay() {
		return pay;
	}

	public void setPay(int pay) {
		this.pay = pay;
	}

	public double getSort() {
		return sort;
	}

	public void setSort(double sort) {
		this.sort = sort;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Result> getResult() {
		return result;
	}

	public void setResult(List<Result> result) {
		this.result = result;
	}
	
	
	
}
