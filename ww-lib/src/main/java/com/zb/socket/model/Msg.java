package com.zb.socket.model;

import java.io.Serializable;

/**
 * 说明：消息对象
 *
 * @author walker.bao
 * @param <T>
 */
public class Msg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 462517658335404851L;

	public Msg() {

	}

	public Msg(Long id, MsgType mt, Object o) {
		this._id = id;
		this.t = mt.getT();
		this.o = o;
	}

	public Msg(Long id, MsgType mt, int fr, Long to, long dt, Object o) {
		this._id = id;
		this.t = mt.getT();
		this.fr = fr;
		this.to = to;
		this.dt = dt;
		this.o = o;
	}

	public Msg(Long id, int mt, int fr, Long to, long dt, Object o) {
		this._id = id;
		this.t = mt;
		this.fr = fr;
		this.to = to;
		this.dt = dt;
		this.o = o;
	}

	private long _id;// 消息id
	private byte via = 0;//
	private int fr = 0;// 消息来源用户
	private Long to;// 消息接受者
	private int t; // 消息类型
	private short v = 1; // 协议版本号
	protected Object o;// 消息内容
	private boolean hasS = false;// 是否需要签名
	private boolean ok = false;// 签名是否OK
	private int c = 0;// 0代表不加密
	private long dt;// 时间戳
	private Integer st;// 状态

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public short getV() {
		return v;
	}

	public void setV(short v) {
		this.v = v;
	}

	public Object getO() {
		return o;
	}

	public void setO(Object o) {
		this.o = o;
	}

	public void mapBody(MapBody mb) {
		this.o = mb.getData();
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public long getDt() {
		return dt;
	}

	public void setDt(long dt) {
		this.dt = dt;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public long get_id() {
		return _id;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public int getFr() {
		return fr;
	}

	public void setFr(int fr) {
		this.fr = fr;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public Integer getSt() {
		return st;
	}

	public void setSt(Integer st) {
		this.st = st;
	}

	public byte getVia() {
		return via;
	}

	public void setVia(byte via) {
		this.via = via;
	}

	public boolean isHasS() {
		return hasS;
	}

	public void setHasS(boolean hasS) {
		this.hasS = hasS;
	}
}
