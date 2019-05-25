package com.zb.models;

public class UserPackLog extends AbstractDocument{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3571127696294595295L;
	private long uid;
	private long bgId;
    private long count; //改变值
    private int op;
    private Long opid;
	public UserPackLog(long _id,long uid, int bgId, long count, int op, long opid) {
		super();
		this._id=_id;
		this.uid = uid;
		this.bgId = bgId;
		this.count = count;
		this.op = op;
		this.opid = opid;
		this.updateTime=System.currentTimeMillis();
	}
	public UserPackLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public long getBgId() {
		return bgId;
	}
	public void setBgId(long bgId) {
		this.bgId = bgId;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public int getOp() {
		return op;
	}
	public void setOp(int op) {
		this.op = op;
	}
	public Long getOpid() {
		return opid;
	}
	public void setOpid(Long opid) {
		this.opid = opid;
	}
	@Override
	public String toString() {
		return "UserPackLog [uid=" + uid + ", bgId=" + bgId + ", count=" + count + ", op=" + op + ", opid=" + opid
				+ "]";
	}
}
