//package com.zb.view;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import com.mongodb.DBObject;
//import com.zb.common.mongo.DboUtil;
//import com.zb.models.User;
//
//public class RankListVO implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -678533435769085073L;
//	private long uid; // 接受人id
//	private int local = 0; // 接受地点
//	private long localId = 0; // 地点id
//	private int bgId = 0; // 礼物id
//	private long count = 0; // 礼物数量
//	private long amount = 0; // 礼物总价值
//	// 个人信息
//	private String nickname = "";
//	private String avatar = "";
//	private String cover = "";
//	private int sex = 2;
//	private int point = 0;
//	private int vip = 0;
//
//	public RankListVO(long uid, int local, long localId, int bgId, long count, long amount) {
//		super();
//		this.uid = uid;
//		this.local = local;
//		this.localId = localId;
//		this.bgId = bgId;
//		this.count = count;
//		this.amount = amount;
//	}
//
//	public RankListVO(long uid, long count, long amount) {
//		super();
//		this.uid = uid;
//		this.count = count;
//		this.amount = amount;
//	}
//
//	public RankListVO() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public String toString() {
//		return "RankListVO [uid=" + uid + ", local=" + local + ", localId=" + localId + ", bgId=" + bgId + ", count="
//				+ count + ", amount=" + amount + ", nickname=" + nickname + ", avatar=" + avatar + ", cover=" + cover
//				+ ", sex=" + sex + ", point=" + point + ", vip=" + vip + "]";
//	}
//
//	public long getUid() {
//		return uid;
//	}
//
//	public void setUid(long uid) {
//		this.uid = uid;
//	}
//
//	public int getLocal() {
//		return local;
//	}
//
//	public void setLocal(int local) {
//		this.local = local;
//	}
//
//	public long getLocalId() {
//		return localId;
//	}
//
//	public void setLocalId(long localId) {
//		this.localId = localId;
//	}
//
//	public int getBgId() {
//		return bgId;
//	}
//
//	public void setBgId(int bgId) {
//		this.bgId = bgId;
//	}
//
//	public long getCount() {
//		return count;
//	}
//
//	public void setCount(long count) {
//		this.count = count;
//	}
//
//	public long getAmount() {
//		return amount;
//	}
//
//	public void setAmount(long amount) {
//		this.amount = amount;
//	}
//
//	public int getSex() {
//		return sex;
//	}
//
//	public void setSex(Integer sex) {
//		if (null != sex) {
//			this.sex = sex;
//		}
//	}
//
//	public int getPoint() {
//		return point;
//	}
//
//	public void setPoint(Integer point) {
//		if (null != point) {
//			this.point = point;
//		}
//	}
//
//	public int getVip() {
//		return vip;
//	}
//
//	public void setVip(Integer vip) {
//		if (null != vip) {
//			this.vip = vip;
//		}
//	}
//
//	public String getAvatar() {
//		return avatar;
//	}
//
//	public void setAvatar(String avatar) {
//		if (null != avatar) {
//			this.avatar = avatar;
//		}
//	}
//
//	public String getCover() {
//		return cover;
//	}
//
//	public void setCover(String cover) {
//		if (null != cover) {
//			this.cover = cover;
//		} else {
//			this.cover = User.DEFAULT_COVER;
//		}
//	}
//
//	public String getNickname() {
//		return nickname;
//	}
//
//	public void setNickname(String nickname) {
//		if (null != nickname) {
//			this.nickname = nickname;
//		}
//	}
//
//	public static List<DBObject> comparator(Map<Long, RankListVO> map) {
//		List<DBObject> vos = new ArrayList<DBObject>();
//		List<Map.Entry<Long, RankListVO>> list = new ArrayList<Map.Entry<Long, RankListVO>>(map.entrySet());
//		// 通过比较器来实现排序
//		Collections.sort(list, new Comparator<Map.Entry<Long, RankListVO>>() {
//			// 升序排序
//			@Override
//			public int compare(Entry<Long, RankListVO> o1, Entry<Long, RankListVO> o2) {
//				// TODO Auto-generated method stub
//				return (int) (o2.getValue().getCount() - o1.getValue().getCount());
//			}
//		});
//		for (Map.Entry<Long, RankListVO> mapping : list) {
//			vos.add(DboUtil.beanToDBO(mapping.getValue()));
//		}
//		return vos;
//	}
//}
