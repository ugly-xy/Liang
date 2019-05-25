package com.zb.models.user;

import java.util.HashMap;
import java.util.Map;

import com.zb.common.utils.JSONUtil;
import com.zb.models.AbstractDocument;

public class UserMonitor extends AbstractDocument {

	public static final int W_OK = 1;

	public static final int W_SUSPICION = 3;

	public static final int W_WARN = 6;

	public static final int W_BUG = 10;

	/**
	 * 
	 */
	private static final long serialVersionUID = -9138190418027367116L;
	Map<String, MonitorLog> monitorLogs = new HashMap<String, MonitorLog>();
	int weight = W_OK;
	int status = W_OK;
	Long rechargeTime ; 

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Map<String, MonitorLog> getMonitorLogs() {
		return monitorLogs;
	}

	public void setMonitorLogs(Map<String, MonitorLog> monitorLogs) {
		this.monitorLogs = monitorLogs;
	}
	
	public Long getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Long rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public UserMonitor() {

	}

	public UserMonitor(Long uid, String devId, long lastLoginTime, String ip, String lbs, int loginType) {
		this._id = uid;
		monitorLogs.put(devId, new MonitorLog(lastLoginTime, ip, lbs, loginType));
		this.updateTime = System.currentTimeMillis();
		this.rechargeTime = this.updateTime;
	}

	public UserMonitor(Long uid, String devId, MonitorLog ml) {
		this._id = uid;
		monitorLogs.put(devId, ml);
		this.updateTime = System.currentTimeMillis();
		this.rechargeTime = this.updateTime;
	}

	public MonitorLog monitorLog(String devId) {
		return monitorLogs.get(devId);
	}

	public void putMonitorLog(String devId, MonitorLog ml) {
		monitorLogs.put(devId, ml);
	}

	public void putMonitorLog(String devId, long loginTime, String ip, String lbs, int loginType) {
		MonitorLog ml = monitorLogs.get(devId);
		if (ml == null) {
			monitorLogs.put(devId, new MonitorLog(loginTime, ip, lbs, loginType));
		} else {
			ml.newLogin(loginTime, ip, lbs, loginType);
		}
	}

	public void putMonitorLog(String devId, int amount, long rechangeTime) {
		MonitorLog ml = monitorLogs.get(devId);
		if (ml == null) {
			ml = new MonitorLog(rechangeTime, null, null, 0);
			monitorLogs.put(devId, ml);
		}
		ml.newRechange(rechangeTime, amount);
	}

	public static void main(String[] args) {
		String json = "{ \"_id\" : 100109376 , \"_class\" : \"com.zb.models.user.UserMonitor\" , \"monitorLogs\" : { \"D256883F-F53F-4D29-8478-BC39B3156787\" : { \"loginCnt\" : 1 , \"loginTime\" : 1511926363269 , \"ip\" : \"127.0.0.1\" , \"status\" : 0}} , \"weight\" : 1 , \"status\" : 1 , \"updateTime\" : 1511926363299}";
		JSONUtil.jsonToBean(json, UserMonitor.class);
	}
}
