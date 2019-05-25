package com.zb.models.room.activity;

import com.zb.models.AbstractDocument;

public class OnlineActivity extends AbstractDocument {

	private static final long serialVersionUID = -7445925617492951079L;

	public OnlineActivity() {

	}

	public OnlineActivity(long id, int count, String name, String pic, int status, int sort, double ver) {
		this._id = id;
		this.count = count;
		this.name = name;
		this.pic = pic;
		this.status = status;
		this.sort = sort;
		this.ver = ver;
	}

	public OnlineActivity(long id, int count, String name, String pic, int status, int sort, int point, int vip,
			double ver, Integer type, Integer handleType, Integer parentId, String url, String lockPic) {
		this._id = id;
		this.count = count;
		this.name = name;
		this.pic = pic;
		this.status = status;
		this.sort = sort;
		this.point = point;
		this.vip = vip;
		this.ver = ver;
		this.type = type;
		this.handleType = handleType;
		this.parentId = parentId;
		this.url = url;
		this.lockPic = lockPic;
	}

	private String name;
	private int count;
	private String pic;
	private int status;
	private int sort;
	private int point;
	private int vip;
	private double ver;
	private int handleType; // 1 跳转子页面 2h5 3 直接进入
	private int type = 1;// 游戏类型，默认1 多人进房间准备游戏，2 双人匹配游戏
	private int parentId; // 父id
	private String url; // h5地址
	private String lockPic;

	public String getLockPic() {
		return lockPic;
	}

	public void setLockPic(String lockPic) {
		this.lockPic = lockPic;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getHandleType() {
		return handleType;
	}

	public void setHandleType(int handleType) {
		this.handleType = handleType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public double getVer() {
		return ver;
	}

	public void setVer(double ver) {
		this.ver = ver;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
