package com.we.models.sign;

import com.we.models.AbstractDocument;

public class Sign extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5096494720335652833L;

	private Integer cycle = 1;// 周期签到当前第几天
	private Integer lastSign = 1;// 最后连续签到天数
	private Integer maxSign = 1;// 最长连续签到天数
	private Integer total = 1;// 总签到次数

	public Sign(Long uid) {
		this._id = uid;
		this.updateTime = System.currentTimeMillis();
	}

	public Sign() {

	}

	public Sign continued() {
		cycle = cycle % 7 + 1;
		lastSign++;
		if (lastSign > maxSign)
			maxSign = lastSign;
		total++;
		this.updateTime = System.currentTimeMillis();
		return this;
	}

	public Sign discontinuous() {
		cycle = 1;
		lastSign = 1;
		if (lastSign > maxSign)
			maxSign = lastSign;
		total++;
		this.updateTime = System.currentTimeMillis();
		return this;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public Integer getLastSign() {
		return lastSign;
	}

	public void setLastSign(Integer lastSign) {
		this.lastSign = lastSign;
	}

	public Integer getMaxSign() {
		return maxSign;
	}

	public void setMaxSign(Integer maxSign) {
		this.maxSign = maxSign;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
