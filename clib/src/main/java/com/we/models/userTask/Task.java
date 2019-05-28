package com.we.models.userTask;

import java.util.HashMap;

import com.we.models.AbstractDocument;

/** 任务 */
public class Task extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6942919374872923128L;
	private String title; // 任务标题
	private String detail;// 描述
	private Integer type;// 类型
	private Integer template;// 任务类型 自有 项目方
	private HashMap<String, Double> rewards;// 任务奖励 货币符号:数量
	private String data;// 任务数据,比如Telegram群链接
	private String uniqueId;//任务标示数据，比如说Telegram群名
	private String qrCode;// 二维码地址
	private Long total;// 活动总共任务数
	private Long finish;// 已完成总量
	private int count;// 活动完成所需执行任务
	private int sort;// 排序
	private Long startTime; // 上线时间
	private Long endTime; // 下线时间
	private String pic;// 货币图标
	private int status = 1;// 状态 1 上线 下线-1

	private Long adminId;
	private Boolean candy;

	private String symbol;// 货币简写
	private String tokenName;// 全称
	private String summary;// 项目方介绍
	private String officialUrl;// 项目方官网
	private String stage;// 融资阶段
	private String country;// 国家
	private Long initialAmount;// 总发行量

	public Task(Long id, String title, String detail, Integer type, Integer template, String data, String qrCode,
			Long total, Integer count, int sort, Long startTime, Long endTime, Long adminId, TokenPrj token,
			HashMap<String, Double> rewards, Integer status,String uniqueId) {
		this._id = id;
		this.title = title;
		this.detail = detail;
		this.type = type;
		this.template = template;
		this.data = data;
		this.qrCode = qrCode;
		this.total = total;
		this.count = count;
		this.sort = sort;
		this.startTime = startTime;
		this.endTime = endTime;
		this.adminId = adminId;
		this.candy = token.getCandy();
		this.symbol = token.get_id();
		this.tokenName = token.getTokenName();
		this.summary = token.getSummary();
		this.officialUrl = token.getUrl();
		this.stage = token.getStage();
		this.country = token.getCountry();
		this.initialAmount = token.getInitialAmount();
		this.rewards = rewards;
		this.pic = token.getPic();
		this.status = status;
		this.uniqueId = uniqueId;

	}

	public Task(Long id, String title, String detail, Integer type, Integer template, String data, String qrCode,
			Long total, Integer count, int sort, Long startTime, Long endTime, String pic, Long adminId, Boolean candy,
			String symbol, String tokenName, String summary, String officialUrl, String stage, String country,
			Long initialAmount, HashMap<String, Double> rewards,String uniqueId) {
		this._id = id;
		this.title = title;
		this.detail = detail;
		this.type = type;
		this.template = template;
		this.data = data;
		this.qrCode = qrCode;
		this.total = total;
		this.count = count;
		this.sort = sort;
		this.startTime = startTime;
		this.endTime = endTime;
		this.pic = pic;
		this.adminId = adminId;
		this.candy = candy;
		this.symbol = symbol;
		this.tokenName = tokenName;
		this.summary = summary;
		this.officialUrl = officialUrl;
		this.stage = stage;
		this.country = country;
		this.initialAmount = initialAmount;
		this.rewards = rewards;
		this.uniqueId = uniqueId;

	}

	public Task() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTemplate() {
		return template;
	}

	public void setTemplate(Integer template) {
		this.template = template;
	}

	public HashMap<String, Double> getRewards() {
		return rewards;
	}

	public void setRewards(HashMap<String, Double> rewards) {
		this.rewards = rewards;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getOfficialUrl() {
		return officialUrl;
	}

	public void setOfficialUrl(String officialUrl) {
		this.officialUrl = officialUrl;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getCandy() {
		return candy;
	}

	public void setCandy(Boolean candy) {
		this.candy = candy;
	}

	public Long getInitialAmount() {
		return initialAmount;
	}

	public void setInitialAmount(Long initialAmount) {
		this.initialAmount = initialAmount;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getFinish() {
		return finish;
	}

	public void setFinish(Long finish) {
		this.finish = finish;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}



	public static final int TYPE_WE = 1;// 自有任务
	public static final int TYPE_PROJECT = 2;// 项目方任务

	/** 模版类型 */
	public enum TastTemplate {
		JOIN_TELEGRAM(1, "加入Telegram电报群", 1), SHARE_UPLOAD(2, "分享上传", 2), FOllOW_TWITTER(3, "关注Twitter",
				1), JOIN_KAKAO(4, "Kakao", 1),JOIN_LINK(5, "加入跳转链接", 1);

		private int code; // code
		private String title;// 解释
		private int op;// 1自动 2上传图片

		private TastTemplate(int code, String title, int op) {
			this.code = code;
			this.title = title;
			this.op = op;
		}

		public int getCode() {
			return code;
		}


		public String getTitle() {
			return title;
		}

		public int getOp() {
			return op;
		}

	}

	/** 模版类型 */
	public enum TastType {
		TELEGRAM(1, "Telegram"), TWITTER(2, "Twitter"), KAKAO(3, "Kakao"), OTHERS(1000, "Others");

		private int code; // code
		private String title;// 解释

		private TastType(int code, String title) {
			this.code = code;
			this.title = title;
		}

		public int getCode() {
			return code;
		}

		public String getTitle() {
			return title;
		}

	}

}
