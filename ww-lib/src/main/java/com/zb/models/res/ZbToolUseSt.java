package com.zb.models.res;


import com.zb.models.AbstractDocument;

public class ZbToolUseSt extends AbstractDocument  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3703398879028922117L;

	public ZbToolUseSt() {

	}

	public ZbToolUseSt(Long id,  int count) {
		this._id = id;
		this.count = count;
		this.updateTime = System.currentTimeMillis();
	}

	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}
