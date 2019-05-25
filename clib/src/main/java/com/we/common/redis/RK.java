package com.we.common.redis;

public class RK {

	/**
	 * im 消息自增长Id
	 * 
	 * @return
	 */
	public static String imMsgId() {
		return "im:msg:id";
	}

	/**
	 * 用户
	 * 
	 * @param id
	 * @return
	 */
	public static String keyUser(Long id) {
		return "user:" + id;
	}

	/**
	 * 用户登录失败次数
	 * 
	 * @param username
	 * @return
	 */
	public static String loginFailedCount(String username) {
		return "login.fail.cnt:" + username;
	}

	/**
	 * 每客户端注册数
	 * 
	 * @param client
	 * @return
	 */
	public static String regClientCount(String client) {
		return "reg.client.cnt:" + client;
	}

	/**
	 * 验证码
	 * 
	 * @param key
	 * @return
	 */
	public static String authCode(String key) {
		return "auth.code:" + key;
	}

	/**
	 * 访问token
	 * 
	 * @param acctoken
	 * @return
	 */
	public static String accessToken(String acctoken) {
		return "acctoken:" + acctoken;
	}

	/**
	 * 黑名单用户
	 * 
	 * @param devId
	 * @return
	 */
	public static String blackDev(String devId) {
		return "black.dev:" + devId;
	}

	/**
	 * 发送短信IP
	 * 
	 * @param ip
	 * @return
	 */
	public static String smsIp(String ip) {
		return "sms.ip:" + ip;
	}

	/**
	 * 发送短信手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static String smsPhone(String phone) {
		return "sms.phone:" + phone;
	}

	/**
	 * 
	 * @param 段位列表
	 * @return
	 */
	public static String divisionList(Integer status) {
		return "divisionlist:" + status;
	}

	/**
	 * 
	 * @param task列表
	 * @return
	 */
	public static String taskList(Integer status) {
		return "taskList:" + status;
	}

	/**
	 * 
	 * @param telegramKey
	 * @return
	 */
	public static String telegramKey(String key) {
		return "telegramKey:" + key;
	}

	/**
	 * 展示的dataView
	 * 
	 * @param id
	 * @return
	 */
	public static String keyDataView() {
		return "dataView";
	}
	
	/**
	 * 用户今日收入
	 * 
	 * @param id
	 * @return
	 */
	public static String userToday(Long uid) {
		return "user:today:"+uid;
	}
	
	/**
	 * 发送邮箱认证
	 * @param mailNo
	 * @return
	 */
	public static String toMailNo(String mailNo) {
		return "toMailNo:" + mailNo;
	}
	public static String receiveMailCode(String code) {
		return "receiveCode:" + code;
	}
	/**
	 * 
	 */
	public static String collectActicleMark() {
		return "collectActicleMark";
	}

}
