package com.we.models.division;

import com.we.common.Constant.Const;

public class DivisionTask {
	public enum DivisionTaskType {
		//REG(1, "完成注册", "", 1, 3000, 1, 1), 
		//2이메일 인증  邮箱认证
		//3个人信息
		//4邀请3名好友
		//累计完成用户前台任务5次
		//邀请5名好友
		//完成7天签到
		//完成KYC认证
		//累计完成用户前台任务10次
		//完成30次签到
		//邀请10名好友
		//指数下线10人达到铂金
		//指数下线30人达到铂金
		
		EMAIL(2, "이메일 인증", "/active/assets/image/dt_email.png", 1, 500, 2, 2,"/my/settingEmail"), USERINFO(3, "개인 정보", "/active/assets/image/dt_person.png", 1, 500, 2,
				3,"/my/settingPersonal"), INVITE_3(4, "친구 3명 초대", "/active/assets/image/dt_3friends.png", 3, 2000, 3, 4,"/share/invitingFriends"),
						FINISH_USER_TASK_5(5, "미션 5회 완성", "/active/assets/image/dt_finishtask5.png", 5, 500, 0,5,"/task"), 
						INVITE_5(6, "친구 5명 초대", "/active/assets/image/dt_5friends.png", 5, 500, 0, 6,"/share/invitingFriends"), 
						SIGN_WEEK(7, "출석 체크7회", "/active/assets/image/dt_weeksign.png", 1, 500, 0, 7,"#sign"), 
						KYC(8,"KYC인증", "/active/assets/image/dt_kyc.png", 1, 3000, 4, 8,"/my/settingkyc"),
						FINISH_USER_TASK_10(9, "미션 10회 완성", "/active/assets/image/dt_finishtask10.png", 10, 1000, 0,9,"/task"),
						SIGN_30(10, "출석 체크30회", "/active/assets/image/dt_30sign.png", 30, 1000, 0, 10,"#sign"), 
						INVITE_10(11, "친구 10명 초대", "/active/assets/image/dt_invite10.png", 10,1000, 0, 11,"/share/invitingFriends"), 
						SUB_PLATINUM_10(12, "실버등급 달성 친구 10명", "/active/assets/image/dt_subplatinum10.png", 10, 77777, 5,11,Const.EMPTYADDR),
						SUB_PLATINUM_30(13, "실버등급 달성 친구 30명", "/active/assets/image/dt_subplatinum30.png", 30, 77777, 6, 11,Const.EMPTYADDR),;
		
		public static DivisionTaskType of(int code) {
			switch (code) {
//			case 1:
//				return DivisionTaskType.REG;
			case 2:
				return DivisionTaskType.EMAIL;
			case 3:
				return DivisionTaskType.USERINFO;
			case 4:
				return DivisionTaskType.INVITE_3;
			case 5:
				return DivisionTaskType.FINISH_USER_TASK_5;
			case 6:
				return DivisionTaskType.INVITE_5;
			case 7:
				return DivisionTaskType.SIGN_WEEK;
			case 8:
				return DivisionTaskType.KYC;
			case 9:
				return DivisionTaskType.FINISH_USER_TASK_10;
			case 10:
				return DivisionTaskType.SIGN_30;
			case 11:
				return DivisionTaskType.INVITE_10;
			case 12:
				return DivisionTaskType.SUB_PLATINUM_10;
			case 13:
				return DivisionTaskType.SUB_PLATINUM_30;
			}
			return null;
		}
		
		private DivisionTaskType(int code, String des, String pic, Integer cnt, Integer reward, int divisionCode,
				int sort,String link) {
			this.code = code;
			this.des = des;
			this.pic = pic;
			this.cnt = cnt;
			this.reward = reward;
			this.divisionCode = divisionCode;
			this.sort = sort;
			this.link = link;
		}

		private int code;
		private String des;// 段位任务名
		private String pic;// 段位任务图片
		private String detail;// 详细描述
		private Integer cnt;// 达成数目
		private Integer reward;// 糖果奖励数目
		private int divisionCode;// 段位，如果为0 代表非主线段位任务
		private int sort = 0;
		private String link; //任务链接

		public String getDes() {
			return des;
		}

		public String getPic() {
			return pic;
		}

		public String getDetail() {
			return detail;
		}

		public Integer getCnt() {
			return cnt;
		}

		public Integer getReward() {
			return reward;
		}

		public int getSort() {
			return sort;
		}

		public int getDivisionCode() {
			return divisionCode;
		}

		public int getCode() {
			return code;
		}
		
		public String getLink() {
			return link;
		}
		
	}

	private int code;
	private String des;// 段位任务名
	private String pic;// 段位任务图片
	private String detail;// 详细描述
	private Integer cnt;// 完成数目
	private Integer needCnt;// 需要数量
	private Integer reward;// 糖果奖励数目
	private int divisionCode;// 段位，如果为0 代表非主线段位任务
	private int status;// 1不可领取，2可领取，3已经完成
	private String link; //任务相应链接

	public DivisionTask(DivisionTaskType dtt, DivisionItem di) {
		this.code = dtt.code;
		this.des = dtt.des;
		this.pic = dtt.pic;
		this.detail = dtt.detail;
		this.cnt = di.getCnt();
		this.needCnt = dtt.cnt;
		this.reward = dtt.reward;
		this.divisionCode = dtt.divisionCode;
		this.status = di.getStatus();
		this.link = dtt.link;
	}
	
	public DivisionTask(DivisionTaskType dtt) {
		this.code = dtt.code;
		this.des = dtt.des;
		this.pic = dtt.pic;
		this.detail = dtt.detail;
		this.cnt = 0;
		this.needCnt = dtt.cnt;
		this.reward = dtt.reward;
		this.divisionCode = dtt.divisionCode;
		this.status = 0;
		this.link =dtt.link;
	}

	public DivisionTask() {

	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public Integer getNeedCnt() {
		return needCnt;
	}

	public void setNeedCnt(Integer needCnt) {
		this.needCnt = needCnt;
	}

	public Integer getReward() {
		return reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
	}

	public int getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(int divisionCode) {
		this.divisionCode = divisionCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public static DivisionTask appValues(DivisionTask that){
		switch (that.getCode()) {
		case 2:
			that.setPic("images/task/dt-email.png");
			that.setLink("information.html");//settingemail.html
			return that;
		case 3:
			that.setPic("images/task/dt-person.png");
			that.setLink("information.html");
			return that;
		case 4:
			that.setPic("images/task/dt_3friends.png");
			that.setLink("#");
			return that;
		case 5:
			that.setPic("images/task/dt_finishtask5.png");
			that.setLink("");
			return that;
		case 6:
			that.setPic("images/task/dt_5friends.png");
			that.setLink("");
			return that;
		case 7:
			that.setPic("images/task/dt_weeksign.png");
			that.setLink("");
			return that;
		case 8:
			that.setPic("images/task/dt-kyc.png");
			that.setLink("information.html");//settingkyc.html
			return that;
		case 9:
			that.setPic("images/task/dt_finishtask10.png");
			that.setLink("");
			return that;
		case 10:
			that.setPic("images/task/dt_30sign.png");
			that.setLink("");
			return that;
		case 11:
			that.setPic("images/task/dt_invite10.png");
			that.setLink("");
			return that;
		case 12:
			that.setPic("images/task/dt_subplatinum10.png");
			that.setLink("");
			return that;
		case 13:
			that.setPic("images/task/dt_subplatinum30.png");
			that.setLink("");
			return that;
		}
		return that;
	}
	
}