package com.we.common.http;

import java.io.Serializable;

public class UserAgent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4107445016536748555L;
	private String os;// android,ios,mac,window
	private String osVer;
	private String browser;// 浏览器类型
	private String browserVer;// 浏览器版本
	private String ua;
	private String deviceType;// 设备类型 phone,pad,pc
	private String netType;

	public UserAgent() {
	}

	public UserAgent(String browserType, String browserVersion, String os,
			String osVer, String ua, String deviceType, String netType) {
		this.os = os;// window,android,mac,ios,WINDOWS PHONE,BLACKBERRY,Linux
		this.osVer = osVer;
		this.browser = browserType;
		this.browserVer = browserVersion;
		this.ua = ua;
		this.deviceType = deviceType;
		this.netType = netType;
	}

	public String getBrowserType() {
		return browser;
	}

	public void setBrowserType(String browserType) {
		this.browser = browserType;
	}

	public String getOs() {
		return os;
	}

	public String getOsVer() {
		return osVer;
	}

	public String getBrowser() {
		return browser;
	}

	public String getBrowserVer() {
		return browserVer;
	}

	public String getUa() {
		return ua;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public String toString() {
		return "os:" + os + ";osVer:" + osVer + ";browser:" + browser
				+ ";browserVer:" + browserVer + ";deviceType:" + deviceType
				+ ";netType:" + netType;
	}

	public String getNetType() {
		return netType;
	}
}
