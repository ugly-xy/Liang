package com.we.web.view.m;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.we.common.Constant.Const;
import com.we.common.Constant.EncodeCountry;
import com.we.common.Constant.Param;
import com.we.common.Constant.ReCode;
import com.we.common.http.CookieUtil;
import com.we.common.utils.RegexUtil;
import com.we.core.conf.Config;
import com.we.core.web.ReMsg;
import com.we.service.AuthService;
import com.we.service.EnvService;
import com.we.service.SmsService;
import com.we.service.UserService;

@Controller
@RequestMapping(value="/app")
public class UserAppCtl extends BaseCtl {

	@Autowired
	AuthService authService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	EnvService envService;
	
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ReMsg login(String username, String password, HttpServletRequest req,
			HttpServletResponse resq) {
		ReMsg rm = authService.login(username, password, req);
		if (rm.getCode() == ReCode.OK.reCode()) {
			// cookie有效期一个月
			if (rm.getData() instanceof String) {
				authService.logout(req, resq);
				CookieUtil.addCookie("accessToken", (String) rm.getData(), (int) (Const.MONTH / 1000), "/",
						Config.cur().get("domain"), resq);
			} else {
				Map<String, String> res = (Map<String, String>) rm.getData();
				authService.logout(req, resq);
				CookieUtil.addCookie("accessToken", res.get("token"), (int) (Const.MONTH / 1000), "/",
						Config.cur().get("domain"), resq);
			}
		}
		
		return rm;
	}
	
	/** 获取安全码 */
	@ResponseBody
	@RequestMapping(value = "/dynamicKey", method = RequestMethod.GET)
	public ReMsg getDynamicKey(String reqKey,Integer flag) {
		if(Param.REGISTER_FLAG!=flag&&Param.FORGET_FLAG!=flag) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		ReMsg reMsg = userService.queryUserByPhone(reqKey);
		if(Param.REGISTER_FLAG==flag && ReCode.EMPTY_PHONE_ERR.reCode()!=reMsg.getCode()) {
			return reMsg;
		}
		if(Param.FORGET_FLAG==flag && ReCode.REGISTED_PHONE_ERR.reCode()!=reMsg.getCode()) {
			return reMsg;
		}
		return authService.getKey(reqKey);
	}
	
	/** 请求验证码接口 */
	@ResponseBody
	@RequestMapping(value = "/sendSms", method = RequestMethod.POST)
	public ReMsg sendSmsAuthCode(String encode, String phone, long timestamp, String data, HttpServletRequest req)
			throws JsonParseException, JsonMappingException, IOException {
		if (StringUtils.isBlank(encode)) {
			encode = EncodeCountry.SOUTH_KOREA.getEncode();
		}
		return smsService.sendAuthCode(req, encode, phone, timestamp, data);
	}
	
	/** 注册 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ReMsg registerPhone(String url, String encode, String phone, String password, String code,
			String shareCode,boolean checkbox,Integer flag,HttpServletRequest req, HttpServletResponse resq) {
		if(!checkbox) {
			return new ReMsg(ReCode.NOT_SELECTION_AGREEMENT);
		}
		if (StringUtils.isBlank(encode)) {
			encode = EncodeCountry.SOUTH_KOREA.getEncode();
		}
		if (!RegexUtil.isPhoneChina(phone) && !RegexUtil.isPhoneKorea(phone)) {
			return new ReMsg(ReCode.PHONE_NUMBER_FORMAT_ERROR);
		}
		if (!RegexUtil.isPassword(password)) {
			return new ReMsg(ReCode.PASSWORD_ERR);	
		}
		// 注册
		ReMsg rm = userService.reg(encode, phone, password, code, shareCode, req);
		if (rm.getCode() == ReCode.OK.reCode()) {
			return this.login(phone, password, req, resq);
		}
		return rm;
	}
	
	/** 验证码重设密码接口 */
	@ResponseBody
	@RequestMapping(value = "/forget", method = RequestMethod.POST)
	public ReMsg upSetpwd(String encode, String url, String phone, String code, String password,
			HttpServletRequest req,HttpServletResponse resq) throws JsonParseException, JsonMappingException, IOException {
		if (StringUtils.isBlank(encode)) {
			encode = EncodeCountry.SOUTH_KOREA.getEncode();
		}
		if (!RegexUtil.isPhoneChina(phone) && !RegexUtil.isPhoneKorea(phone)) {
			return new ReMsg(ReCode.PHONE_NUMBER_FORMAT_ERROR);
		}
		if (!RegexUtil.isPassword(password)) {
			return new ReMsg(ReCode.PASSWORD_ERR);
		}
		if (StringUtils.isBlank(code)) {
			return new ReMsg(ReCode.AUTHCODE_ERR);
		}
		// 更改密码
		ReMsg rm = userService.upsetPwd(phone, code, password, req);
		if (rm.getCode() == ReCode.OK.reCode()) {
			authService.logout(req, resq);
		}
		return rm;
	}
	
	/** 退出 */
	@RequestMapping(value = "/logout",method=RequestMethod.GET)
	@ResponseBody
	public ReMsg logout(HttpServletRequest req, HttpServletResponse resq) {
		authService.logout(req, resq);
		return new ReMsg(ReCode.OK);
	}
	
	/** 邀请好友界面 分享文字*/
	@RequestMapping(value = "/share/invitingFriends")
	@ResponseBody
	public ReMsg InvitingFriends(HttpServletRequest req,Integer conNum,String conCode) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		Map<String, Object> res = super.getTokenUser();
		String shareCode = "";
		if (res.containsKey("shareCode")) {
			shareCode = res.get("shareCode")==null?"":res.get("shareCode").toString();
		}
		res.put("shareCode", shareCode);
		return new ReMsg(ReCode.OK, res);
	}
	
	@ResponseBody
	@RequestMapping(value="/queryUid",method=RequestMethod.GET)
	public ReMsg queryUid() {
		long uid = super.getUid();
		if(uid<1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("imgDomain", Config.cur().getImgDomain());
		res.put("uid",uid);
		String ws = envService.getString("ws.url");
		if(ws==null) {
			ws = "ws://www.candy.club/websocket";
		}
		res.put("ws", ws);
		return new ReMsg(ReCode.OK,res);
	}
	
}
