package com.we.common.Constant;

public enum ReCode {
	OK(0, "OK"),

	FAIL(1, "FAILED"),

	SYS_ERR(2, "시스템 오류"),//ERR,系统错误

	USERNAME_ERR(10001, "아이디가 올바르지 않습니다 영문 소문자 대문자 숫자를 포함 하여 2-16자리수의 아이디를 입력해주세요"),//输入的用户有误，用户名必须为2-16位中文，字母，数字

	PASSWORD_ERR(10002, "비밀번호가 올바르지 않습니다 6-20자리수의 비밀번호를 입력해주세요"),//输入密码有误，密码必须为6-20位

	USERNAME_EXISTS(10003, "사용자 아이디가 존재하지 않습니다"),//用户名已经存在
	
	PHONE_EXISTS(10004, "가입된 번호입니다"),//手机号已经注册

	PHONE_NUMBER_FORMAT_ERROR(50011, "올바른 휴대폰 번호를 입력해주세요"),//手机号格式错误

	USER_NOT_EXISTS(50013, "존재하지 않는  아이디 입니다"),//用户不存在

	LOGIN_FAILED_ERR_USERNAME(10004, "로그인 실패하였습니다 존재하지 않는 아이디 입니다"),//登录失败，用户名不存在

	LOGIN_FAILED_ERR_PASSWORD(10005, "로그인 실패하였습니다 올바른 아이디 혹은 비밀번호를 입력해주세요"),//登录失败，用户名或密码错误

	LOGIN_FAILED_ERR_TOO_OFTEN(10006, "로그인 실패 하였습니다 5분뒤 다시 시도해 주세요"),//多次登录失败，5分钟后再登录

	AUTHCODE_ERR(10007, "인증번호가 일치하지 않습니다"),//验证码错误，重新获取验证码！

	NICKNAME_EXISTS(10008, "사용중인 닉네임 입니다"),//昵称已经存在

	PASSWORD_VER_ERR(10009, "비밀번호 인증 실패 되었습니다"),//密码验证失败

	HAVE_BEEN_SIGNED(10010, "출석 체크 완료 되었습니다"),//已经签到

	THIRD_LOGIN_FAILED_ERR(10011, "第三方登陆失败"),//第三方登陆失败

	THIRD_LOGIN_FAILED_ERR_NO_INFO(10012, "第三方登陆失败，无返回用户信息"),//第三方登陆失败，无返回用户信息

	LOGIN_FAILED_ERR_THIRD(10013, "第三方登录失败"),//第三方登录失败

	COIN_BALANCE_LOW(10014, "잔액이 부족합니다"),//余额不足！

	NO_RESIGN(10018, "无需补签"),//无需补签

	PARAMETER_ERR(10100, "参数错误"),//参数错误

	SIGN_ERR(10101, "签名错误"),//签名错误

	NOT_AUTHORIZED(11000, "로그인 되지 않았거나 로그인 실패 하였습니다 다시 로그인 해주시길 바랍니다"),//没有登录或登录已失效，请重新登录

	LOGIN_NOT_ALLOWED(11002, "사용 금지된 계정입니다 "),//账户已禁用

	REG_TOO_OFTEN(11003, "로그인 접속자가 많아 잠시 후 시도해 주시길 바랍니다"),//注册太频繁|以前注释

	BANNED(11004, "악성 방문 IP 금지"),//恶意访问，ip，设备被禁

	USER_ID_ERR(11005, "사용자 아이디 오류"),//用户Id错误

	USER_LOCKED(11006, "계정이 사용 금지 되었습니다"),//账户已禁用|以前注释

	SMS_IP_TOO_OFTEN(11101, "해당IP 메시지 인증 회수가 초과 되었습니다"),//当前IP超过短信验证码此数

	SMS_PHONE_TOO_OFTEN(11102, "메시지 인증 회수 초과하였습니다"),//当前手机号超过短信验证码次数

	SMS_PHONE_TOO_OFTEN_60(11103, "시스템 오류가 발생하였습니다 1분뒤 다시 시도해 주세요"),//获取验证码太频繁，请1分钟后重试

	SMS_SEND_ERR(11104, "인증번호 발송 실패하였습니다 잠시후 다시 시도해주세요"),//验证码发送失败，稍后重试

	SMS_SEND_OUTTIME_ERR(11105, "발송 시간이 초과 되었습니다"),//发送超时！

	SMS_DRAW_ERR(11106, "시스템 오류가 발생하였습니다 1분뒤 다시 시도해 주세요"),//获取验证码太频繁，请1分钟后重试

	ROOL_NO(12000, "권한이 없습니다"),//没有权限|暂时没有到

	TOOL_USE_IP_MAX_ERR(14003, "IP접속자가 많아 잠시후 시도해주시길 바랍니다"),//该IP使用太频繁|暂时没有到

	SECURITY_SIGN_ERR(20001, "출석 오류"),//签名错误！|暂时没有到

	FEEDBACK_ERR(22001, "좋은 의견 감사드립니다"),//您的反馈我们已经收到，休息一会儿吧！

	ACCESS_TOKEN_ERR(30000, "방문 실패 AccessToken무효"),//访问失败,AccessToken无效
	
	EMPTY_TITLE(31000, "请输入标题"),
	
	//
	NICKNAME_EMP_ERR(40001,"닉네임을 입력해주세요"),//昵称不能为空!
	NICKNAME_PATTAN_ERR(40002,"닉네임은 최대 20자리 까지 입력할 수 있습니다"),//昵称最多可输入20个字符
	EMAIL_PATTERN_ERR(40003, "올바른 이메일을 입력해주세요"),//您邮箱格式不正确！
	KYC_UPLOAD_ERR(40004, "이미지를 다시 선택해주세요"),//请上传图片！
	PASSWORD_LENGTH_ERR(40005, "비밀번호 자리수가 부족합니다"),//您密码长度不够！
	PASSWORD_LOWERCASE_ERR(40006, "비밀번호는 영문 대문자 소문자 수자를 포함하여 입력하여야 합니다"),//您密码不能全为数字！
	
	Mail_CLICK_OVERTIME(40007, "이메일 인증 다시 진행해 주세요"),//链接已失效，请重新去邮箱认证
	Mail_REGED_ERR(40008, "이미 가입된 메일주소 입니다. 다른 메일 주소를 입력해 주시길 바랍니다"),//邮箱已被注册，请选其它邮箱！
	SHARE_CODE_SENDING(40009, "지정된 이메일로 발송중 입니다"),//正在发送..|邮箱
	SHARE_CODE_FAIL(40010, "이메일 인증번호 발송 실패 하였습니다 잠시후 다시 시도해 주시길 바랍니다"),//邮箱发送失败！请稍后重试..
	REGISTED_PHONE_ERR(40011, "가입된 번호입니다"),//该手机已被注册！！
	EMPTY_PHONE_ERR(40012, "가입 되지 않은 번호입니다"),//该手机号没被注册！！
	BUSINESS_NAME_EMPTY(40013, "프로젝트명을 입력해주세요"),//项目名称不能为空
	BUSINESS_CONTACT_EMPTY(40014, "연락 성함을 입력해주세요"),//联系人不能为空
	BUSINESS_CONTACTINFO_EMPTY(40015, "연락주소를 입력해주세요"),//联系方式不能为空
	BUSINESS_COOPERATION_EMPTY(40016, "문의 내용서을 입력해주세요"),//Cooperation intention不能为空
	BUSINESS_PHONE_EMPTY(40017, "휴대폰 번호를 입력해주세요"),//手机号不能为空
	OLDPWD_WRITE_ERR(40018,"정확한 기존 비밀번호를 입력해주세요"),//请填写正确的原始密码
	NOT_SELECTION_AGREEMENT(400020,"이용약관을 확인하고 동의해주세요");//注册协议没勾选
	
	;

	private int recode = 0;
	private String msg;

	private ReCode(int recode) {
		this.recode = recode;
	}

	private ReCode(int recode, String msg) {
		this.recode = recode;
		this.msg = msg;
	}

	public int reCode() {
		return recode;
	}

	public String getMsg() {
		return msg;
	}
}
