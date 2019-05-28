package com.zb.models;

public class PushLimit extends AbstractDocument {
    public static final int SLAVE_CALLBACK=135;  //召回
    public static final int SLAVE_BUY=2;  //被买
    public static final int ARTICLE_COMMENT=3;  //文章评论
	private static final long serialVersionUID = 1288525684946478443L;
	private int t;
	private Long to;
	private Long dt;
	private int fr;

	public int getFr() {
		return fr;
	}

	public void setFr(int fr) {
		this.fr = fr;
	}

	public int getT() {
		return t;
	}

	public Long getTo() {
		return to;
	}

	public Long getDt() {
		return dt;
	}

	public void setT(int t) {
		this.t = t;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public void setDt(Long dt) {
		this.dt = dt;
	}

	public PushLimit(int t, Long to, Long dt, int fr) {
		super();
		this.t = t;
		this.to = to;
		this.dt = dt;
		this.fr = fr;
	}

	public PushLimit() {
		super();
		// TODO Auto-generated constructor stub
	}
}
