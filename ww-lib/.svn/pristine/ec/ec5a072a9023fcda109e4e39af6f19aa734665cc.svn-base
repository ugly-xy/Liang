package com.zb.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SlotMachinesInfo extends AbstractDocument {

	private static final long serialVersionUID = 473505023088292121L;
	private static final String PREFIX = "http://imgzb.zhuangdianbi.com/wzry/";
	private static final String PICSUFFIX = ".png";
	private static final String VOICESUFFIX = ".mp3";

	private String name;
	private String picUrl;
	private String voiceUrl;
	private int available;

	public SlotMachinesInfo(Long id, String name) {
		this._id = id;
		this.name = name;
		this.picUrl = PREFIX.concat(Long.toString(id)).concat(PICSUFFIX);
		this.voiceUrl = PREFIX.concat(Long.toString(id)).concat(VOICESUFFIX);
		this.available = 1;
	}

	public String getVoiceUrl() {
		return voiceUrl;
	}

	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

}
