package com.zb.models.res;

import com.zb.models.AbstractDocument;

public class ZbTool extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3703398879028922117L;

	public static final int SERVER_FALSE = 1;
	public static final int SERVER_TRUE = 2;

	public static final int TYPE_PIC = 0;
	public static final int TYPE_VIDEO = 1;
	public static final int TYPE_H5 = 2;
	
	public static final String DEF_TAG_NEW = "new";
	public static final String DEF_TAG_APPLE = "apple";

	public ZbTool() {

	}

	public ZbTool(Long id, String name, String logo, Integer status,
			Integer hidden, Integer sort, Long[] cates, String tmpBackPic,
			String mark, String tmpBackPicIos, int point, String[] tag,
			int server, String description, String[] otherPics, String style,
			String draw, String clazz,Integer type) {
		this._id = id;
		this.name = name;
		this.logo = logo;
		this.updateTime = System.currentTimeMillis();
		this.sort = sort;
		this.status = status;
		this.hidden = hidden;
		this.cates = cates;
		this.tmpBackPic = tmpBackPic;
		this.mark = mark;
		this.tmpBackPicIos = tmpBackPicIos;
		this.point = point;
		this.tags = tag;
		this.server = server;
		this.description = description;
		this.otherPics = otherPics;
		this.style = style;
		this.draw = draw;
		this.clazz = clazz;
		this.type = type;
	}

	private String name;
	private String logo;
	private Integer status;
	private Integer hidden; // 1 index
	private Integer sort;
	private String style;
	private Long[] cates;
	private String tmpBackPic;
	private String tmpBackPicIos;
	private String[] otherPics;
	private String mark;
	private int point;
	private String[] tags;
	private int server;
	private String description;
	private int count;
	private double avgStar;
	private int commentCnt;
	private double totalStar;
	private String draw;
	private String clazz;
	private int type; 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getHidden() {
		return hidden;
	}

	public void setHidden(Integer hidden) {
		this.hidden = hidden;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getTmpBackPic() {
		return tmpBackPic;
	}

	public void setTmpBackPic(String tmpBackPic) {
		this.tmpBackPic = tmpBackPic;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getTmpBackPicIos() {
		return tmpBackPicIos;
	}

	public void setTmpBackPicIos(String tmpBackPicIos) {
		this.tmpBackPicIos = tmpBackPicIos;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getServer() {
		return server;
	}

	public void setServer(int server) {
		this.server = server;
	}

	public Long[] getCates() {
		return cates;
	}

	public void setCates(Long[] cates) {
		this.cates = cates;
	}

	public String[] getOtherPics() {
		return otherPics;
	}

	public void setOtherPics(String[] otherPics) {
		this.otherPics = otherPics;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getAvgStar() {
		return avgStar;
	}

	public void setAvgStar(double avgStar) {
		this.avgStar = avgStar;
	}

	public int getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}

	public double getTotalStar() {
		return totalStar;
	}

	public void setTotalStar(double totalStar) {
		this.totalStar = totalStar;
	}

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	

	
}
