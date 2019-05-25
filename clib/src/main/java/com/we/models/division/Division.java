package com.we.models.division;


public enum Division  {
	//青铜브론즈
	//白银
	//黄金
	//铂金
	//钻石
	BRONZE(1, "브론즈", 0.5,3000,""), SILVER(2, "실버", 0.6,1000,""), GOLD(3, "골드", 0.8,2000,""), PLATINUM(4, "플래티넘", 1,3000,""), DIAMOND(5, "다이아몬드",
			1.05,77777,""), KING(6, "챌린저", 1.1,88888,"");

	private int code;
	private String des;// 条件解释
	private double rate;
	private Integer amount;
	private String pic;
	
	public static Division of(int code) {
		switch(code) {
		case 1 : 
			return BRONZE;
		case 2:
			return SILVER;
		case 3:
			return GOLD;
		case 4:
			return PLATINUM;
		case 5:
			return DIAMOND;
		case 6 :
			return KING;
		default:
			return BRONZE;
		}
	}

	private Division(int code, String des, double rate,Integer amount,String pic) {
		this.code = code;
		this.des = des;
		this.rate = rate;
		this.amount = amount;
		this.pic =pic;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getCode() {
		return code;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
}
//}
//	public enum DivisionType {
//		
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -1156725693107986625L;
//
//	/** 段位条件类型 */
//	public enum ConditiontType {
//		REG(1, "用户完成注册"), EMAIL(2, "用户绑定邮箱"), AUTH(3, "完成身份认证"), SHARE(4, "推广注册新用户"), DIRECTLY_UNDER_TO_GOLD(5,
//				"直属下级达到黄金"), DIRECTLY_UNDER_TO_PLATNUM(6, "直属下级达到铂金"),;
//
//		private int code; // 条件code
//		private String msg;// 条件解释
//
//		private ConditiontType(int code, String msg) {
//			this.code = code;
//			this.msg = msg;
//		}
//
//		public int getCode() {
//			return code;
//		}
//
//		public String getMsg() {
//			return msg;
//		}
//	}
//
//	public class Condition implements Serializable {
//		private static final long serialVersionUID = -3072770743806680823L;
//		private int type;// 条件类型
//		private String detail;// 详细描述
//		private Integer cnt;// 达成数目
//		private Integer reward;// 糖果奖励数目
//
//		public int getType() {
//			return type;
//		}
//
//		public void setType(int type) {
//			this.type = type;
//		}
//
//		public String getDetail() {
//			return detail;
//		}
//
//		public void setDetail(String detail) {
//			this.detail = detail;
//		}
//
//		public Integer getCnt() {
//			return cnt;
//		}
//
//		public void setCnt(Integer cnt) {
//			this.cnt = cnt;
//		}
//
//		public int getReward() {
//			return reward;
//		}
//
//		public void setReward(Integer reward) {
//			this.reward = reward;
//		}
//
//		public Condition() {
//			super();
//		}
//
//		public Condition(int type, String detail, Integer cnt, Integer reward) {
//			super();
//			this.type = type;
//			this.detail = detail;
//			this.cnt = cnt;
//			this.reward = reward;
//		}
//	}
//
//	private ArrayList<Condition> conditions;// 达到该段位的条件
//	private String name;// 名称
//	private int recommendCnt;// 推荐名额
//	private double unit;// 对应提成
//	private int sort;// 排序
//	private int status;// 状态
//	private String prerogative;// 特权
//
//	public ArrayList<Condition> getConditions() {
//		return conditions;
//	}
//
//	public void setConditions(ArrayList<Condition> conditions) {
//		this.conditions = conditions;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public int getRecommendCnt() {
//		return recommendCnt;
//	}
//
//	public void setRecommendCnt(int recommendCnt) {
//		this.recommendCnt = recommendCnt;
//	}
//
//	public double getUnit() {
//		return unit;
//	}
//
//	public void setUnit(double unit) {
//		this.unit = unit;
//	}
//
//	public int getSort() {
//		return sort;
//	}
//
//	public void setSort(int sort) {
//		this.sort = sort;
//	}
//
//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}
//
//	public String getPrerogative() {
//		return prerogative;
//	}
//
//	public void setPrerogative(String prerogative) {
//		this.prerogative = prerogative;
//	}
//
//	public Division(String name, int recommendCnt, double unit, int sort, int status, String prerogative) {
//		super();
//		this.name = name;
//		this.recommendCnt = recommendCnt;
//		this.unit = unit;
//		this.sort = sort;
//		this.status = status;
//		this.prerogative = prerogative;
//	}
//
//	public Division() {
//		super();
//	}
//
//}
