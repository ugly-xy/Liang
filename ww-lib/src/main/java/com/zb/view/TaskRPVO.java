package com.zb.view;

import java.io.Serializable;

public class TaskRPVO  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4551322480572226731L;
	private int skip;//是否直接跳转每日
    private int newRP;//新手任务小红点
    private int dailyRP;//每日任务小红点
    private int vipRP; //vip任务小红点
	public int getSkip() {
		return skip;
	}
	public void setSkip(int skip) {
		this.skip = skip;
	}
	public int getNewRP() {
		return newRP;
	}
	
	public void setNewRP(int newRP) {
		this.newRP = newRP;
	}
	public int getDailyRP() {
		
		return dailyRP;
	}
	public void setDailyRP(int dailyRP) {
		this.dailyRP = dailyRP;
	}
	public int getVipRP() {
		return vipRP;
	}
	public void setVipRP(int vipRP) {
		this.vipRP = vipRP;
	}
	public TaskRPVO(int skip, int newRP, int dailyRP, int vipRP) {
		super();
		this.skip = skip;
		this.newRP = newRP;
		this.dailyRP = dailyRP;
		this.vipRP = vipRP;
	}
	public TaskRPVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "TaskRPVO [skip=" + skip + ", newRP=" + newRP + ", dailyRP=" + dailyRP + ", vipRP=" + vipRP + "]";
	}
}

