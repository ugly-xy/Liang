package com.zb.models.othergames;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zb.common.Constant.Const;
import com.zb.models.AbstractDocument;

public class Guess extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6270351820298057009L;

	public static final int STATUS_GUESSING = 1;// 进行中
	public static final int STATUS_DRAWING = 2;// 开奖中
	public static final int STATUS_DISMISS = 6;// 解散
	public static final int STATUS_END = 5;// 结束

	public static final int MARGIN_PAY = 1;// 押金已交
	public static final int MARGIN_BACK = 2;// 押金已退回

	public static final int DRAWTYPE_AUTO = 1;// 自动发奖
	public static final int DRAWTYPE_MAN = 2;// 手动发奖
	
	public static final int TYPE_BJKS=1;//北京快三
	public static final int TYPE_SHKS=2;//上海快三

	private long uid;// 发起者id
	private long endDrawTime;// 押注结束时间
	private String title;// 竞猜标题
	private List<GuessItem> items;// 选项
	private long createTime;// 发起时间
	private long drawTime = 0;// 开奖时间
	private String drawId;// 开奖号码
	private int amount = 0;// 奖池总金额
	private int status = STATUS_GUESSING;// 解散
	private Set<Long> report = new HashSet<Long>();
	private int reportCnt = 0;// 举报人数
	private boolean needAdmin = false;// 需要管理员支持
	private int bondStatus = Const.STATUS_DEF;

	private int drawStatus = 1;// 默认正常开奖，如果用户开奖结果与现实不符合，为2
	private Long adminId;

	private Integer type;// 竞猜类型，比如快3
	private String typeLink;// 竞猜类型对应期号
	private Integer drawType;// 开奖类型，自动|手动

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getEndDrawTime() {
		return endDrawTime;
	}

	public void setEndDrawTime(long endDrawTime) {
		this.endDrawTime = endDrawTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<GuessItem> getItems() {
		return items;
	}

	public void setItems(List<GuessItem> items) {
		this.items = items;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(long drawTime) {
		this.drawTime = drawTime;
	}

	public String getDrawId() {
		return drawId;
	}

	public void setDrawId(String drawId) {
		this.drawId = drawId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<Long> getReport() {
		return report;
	}

	public void setReport(long uid) {
		report.add(uid);
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Guess() {
		super();
	}

	public void setReport(Set<Long> report) {
		this.report = report;
	}

	public int getDrawStatus() {
		return drawStatus;
	}

	public void setDrawStatus(int drawStatus) {
		this.drawStatus = drawStatus;
	}

	public int getReportCnt() {
		return reportCnt;
	}

	public void setReportCnt(int reportCnt) {
		this.reportCnt = reportCnt;
	}

	public boolean isNeedAdmin() {
		return needAdmin;
	}

	public void setNeedAdmin(boolean needAdmin) {
		this.needAdmin = needAdmin;
	}

	public int getBondStatus() {
		return bondStatus;
	}

	public void setBondStatus(int bondStatus) {
		this.bondStatus = bondStatus;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeLink() {
		return typeLink;
	}

	public void setTypeLink(String typeLink) {
		this.typeLink = typeLink;
	}

	public Integer getDrawType() {
		return drawType;
	}

	public void setDrawType(Integer drawType) {
		this.drawType = drawType;
	}

	public Guess(long uid, long endDrawTime, String title, List<GuessItem> items) {
		super();
		this.uid = uid;
		this.endDrawTime = endDrawTime;
		this.title = title;
		this.items = items;
		this.createTime = System.currentTimeMillis();
		this.updateTime = createTime;
	}

}
