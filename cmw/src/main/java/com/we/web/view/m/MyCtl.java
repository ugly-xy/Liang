package com.we.web.view.m;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.we.common.Constant.ReCode;
import com.we.common.mongo.DboUtil;
import com.we.common.utils.MapUtil;
import com.we.common.utils.RegexUtil;
import com.we.core.web.ReMsg;
import com.we.models.division.Division;
import com.we.models.finance.CoinLog;
import com.we.service.ApplyForTaskService;
import com.we.service.AuthService;
import com.we.service.BusinessCooperationService;
import com.we.service.LoginLogService;
import com.we.service.MailLogService;
import com.we.service.SignService;
import com.we.service.SmsService;
import com.we.service.UserDivisionService;
import com.we.service.UserIdentityService;
import com.we.service.UserService;
import com.we.service.UserShareService;
import com.we.service.UserWalletService;
import com.we.service.WithdrawLogService;
import com.we.service.cloud.StorageService;
import com.we.service.image.PosterService;
import com.we.service.userTask.UserTaskService;

@Controller
@RequestMapping("/my")
public class MyCtl extends BaseCtl {

	@Autowired
	AuthService authService;

	@Autowired
	UserService userService;

	@Autowired
	SmsService smsService;

	@Autowired
	UserWalletService userWalletService;

	@Autowired
	WithdrawLogService withdrawLogService;

	@Autowired
	UserIdentityService userIdentityService;

	@Autowired
	UserShareService userShareService;

	@Autowired
	UserTaskService userTaskService;

	@Autowired
	ApplyForTaskService applyForTaskService;

	@Autowired
	UserDivisionService userDivisionService;

	@Autowired
	BusinessCooperationService businessCooperationService;

	@Autowired
	LoginLogService loginLogService;

	@Autowired
	StorageService storageService;

	@Autowired
	MailLogService mailLogService;

	@Autowired
	PosterService posterService;

	/** 用户修改个人信息 */
	@ResponseBody
	@RequestMapping(value = "/upsetUserInfo", method = RequestMethod.POST)
	public ReMsg upsetUserInfo(String firstname, String lastname, String walletAddress, String email,
			HttpServletRequest req, HttpServletResponse resq) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return userService.upsetUserInfo(uid, firstname, lastname, walletAddress, email, req);
	}

	/** 设置身份信息 */
	@ResponseBody
	@RequestMapping(value = "/setUserIdentity", method = RequestMethod.POST)
	public ReMsg setUserIdentity(String frontPic, String backPic, String inHandPic, String realname, String number) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return userIdentityService.saveUserIdentity(uid, frontPic, backPic, inHandPic, realname, number);
	}

	/** 申请提现 */
	@ResponseBody
	@RequestMapping(value = "/withDraw", method = RequestMethod.POST)
	public ReMsg withDrawLog(double amount, String coinType, String walletAddress) {
		if (amount < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return withdrawLogService.saveWithdrawLog(uid, amount, coinType, walletAddress);
	}

	/** 查询提现记录 */
	@ResponseBody
	@RequestMapping(value = "/withDrawLogs", method = RequestMethod.POST)
	public ReMsg withDrawLogs(Integer status, String coinType, Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return new ReMsg(ReCode.OK, withdrawLogService.find(status, uid, null, coinType, null, page, size));
	}

	/** 查看自己的邀请网络信息(推荐人数 获取货币数目) */
	@ResponseBody
	@RequestMapping(value = "/userShare", method = RequestMethod.POST)
	public ReMsg userShare() {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return new ReMsg(ReCode.OK, userShareService.findById(uid));
	}

	/** 查看自己的推荐码 */
	@ResponseBody
	@RequestMapping(value = "/shareCode", method = RequestMethod.POST)
	public ReMsg shareCode() {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		@SuppressWarnings("deprecation")
		DBObject user = userService.findById(uid);
		if (user != null) {
			return new ReMsg(ReCode.OK, DboUtil.getString(user, "shareCode"));
		}
		return new ReMsg(ReCode.FAIL);
	}

	@Autowired
	SignService signService;

	@ResponseBody
	@RequestMapping("/sign")
	public ReMsg sign(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.AUTHCODE_ERR);
		}
		ReMsg reMsg = signService.sign(uid);
		if (0 == reMsg.getCode()) {
			reMsg.setData(signService.isSign(uid));
		}
		return reMsg;
	}

	// /** 根据任务列表以及用户任务进度组装用户任务列表 */
	// @ResponseBody
	// @RequestMapping(value = "/userTaskList", method = RequestMethod.POST)
	// public ReMsg userTaskList() {
	// long uid = super.getUid();
	// // if (uid < 1) {
	// // return new ReMsg(ReCode.NOT_AUTHORIZED);
	// // }
	// return new ReMsg(ReCode.OK, userTaskService.getUserTaskList(uid));
	// }

	/** 用户点击做任务 决定要下一步往哪儿走 */
	@ResponseBody
	@RequestMapping(value = "/doTask", method = RequestMethod.POST)
	public ReMsg doTask(long taskId) {
		return userTaskService.doTask(taskId);
	}

	/** 用户领取奖励 */
	@ResponseBody
	@RequestMapping(value = "/taskRewards", method = RequestMethod.POST)
	public ReMsg taskRewards(Long utid) {
		long uid = super.getUid();
		return userTaskService.recvReward(uid, utid);
	}

	/** 提交一条任务申请 */
	@ResponseBody
	@RequestMapping(value = "/applyForTask")
	public ReMsg applyForTask(String name, String weChat, String phone, String email, String taskDetail,
			long taskBudget, String type) {
		long uid = super.getUid();
		// if (uid < 1) {
		// return new ReMsg(ReCode.NOT_AUTHORIZED);
		// }
		return applyForTaskService.saveApplyFortask(uid, name, phone, weChat, email, taskDetail, taskBudget, type);
	}

	/** 获取自己钱包货币列表 */
	@ResponseBody
	@RequestMapping(value = "/userWallet", method = RequestMethod.POST)
	public ReMsg userWallet() {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return new ReMsg(ReCode.OK, userWalletService.findById(uid));
	}

	/** 查询收支记录 */
	@ResponseBody
	@RequestMapping(value = "/coinLogs", method = RequestMethod.POST)
	public ReMsg coinLogs(Integer io, Integer type, String coinType, Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return new ReMsg(ReCode.OK,
				userWalletService.findCoinLog(null, null, CoinLog.IN, type, uid, coinType, page, size));
	}

	// /** 获取段位列表 */
	// @ResponseBody
	// @RequestMapping(value = "/userDivision", method = RequestMethod.POST)
	// public ReMsg userDivisionList() {
	// long uid = super.getUid();
	// // if (uid < 1) {
	// // return new ReMsg(ReCode.NOT_AUTHORIZED);
	// // }
	// return new ReMsg(ReCode.OK, userDivisionService.getUserDivisionList(uid));
	// }

	/** 提交一条商务合作 可以未登录 */
	// second之前属性String name, String weChat, String phone, String email, String
	// selfIntroduction,String cooperation
	@ResponseBody
	@RequestMapping(value = "/applyForBusiness")
	public ReMsg applyForBusiness(String name, String introduction, String officialAddr, String contact, String phone,
			String contactInfo, String email, String cooperation) {
		long uid = super.getUid();
		// if (uid < 1) {
		// return new ReMsg(ReCode.NOT_AUTHORIZED);
		// }
		return businessCooperationService.saveBusinessCooperation(uid, name, introduction, officialAddr, contact, phone,
				contactInfo, email, cooperation);
	}

	/** 上传 */
	@RequestMapping(value = "/upload")
	public ModelAndView uploadView(Long id, HttpServletRequest req) {
		DBObject dbo = userTaskService.findById(id);
		Map<String, Object> res = super.getCommonMap();
		res.put("obj", dbo);
		return new ModelAndView("task/upload", res);
	}

	/** 提交一条商务合作 可以未登录 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ReMsg upload(String photos, Long id) {
		String[] pics = null;
		if (StringUtils.isNotBlank(photos)) {
			pics = photos.split(",");
		} else {
			// 没有图片
			return new ReMsg(ReCode.FAIL);
		}
		return userTaskService.upload(id, pics);
	}

	/** 个人中心主界面 */
	@RequestMapping(value = "/personalMine")
	public ModelAndView personalMine(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> res = super.getCommonMap();
		// 用户信息
		DBObject dbo = userService.findById(uid);
		res.put("user", dbo);

		// 段位信息
		res.put("userDivision", Division.of(DboUtil.getInt(dbo, "divisionId")));

		res.put("myDivisonTask", userDivisionService.getUserDivisionList(uid, 3));

		//
		res.put("walletsAmount", userWalletService.getMyUserCoinsAmount(uid));

		// 用户邀请网络
		res.put("userShare", userShareService.findById(uid));
		// 签到情况
		res.put("sign", signService.isSign(uid));
		res.put("todayAmount", userWalletService.getTodayCoinIn(uid));
		res.put("signReward", signService.reward);
		return new ModelAndView("personal/personal-mine", res);
	}

	@ResponseBody
	@RequestMapping(value = "/takeReward", method = RequestMethod.POST)
	public ReMsg updateTakeReward(@RequestParam(value = "taskCode", required = true) Integer taskCode) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.FAIL);
		}
		ReMsg reMsg= userDivisionService.recvReword(uid, taskCode);
		@SuppressWarnings("unchecked")
		Map<String, Object> result =(Map<String, Object>)reMsg.getData();
		if(result.containsKey("newDivId")&&result.containsKey("befDivId")) {
			Integer divId =(Integer)result.get("newDivId");
			result.put("befRate", Division.of((Integer)(result.get("befDivId"))).getRate()*100);
			result.put("curRate", Division.of(divId).getRate()*100);
			result.put("amount", Division.of(divId).getAmount());
			result.put("name", Division.of(divId).getDes());
		}
		return new ReMsg(ReCode.OK, result);
	}

	/** 个人中心钱包界面 */
	@RequestMapping(value = "/personalWallet")
	public ModelAndView personalWallet(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> res = super.getCommonMap();
		res.put("obj", userWalletService.queryUserCoin(uid));
		return new ModelAndView("personal/personal-wallet", res);
	}

	/** 我的钱包界面 */
	@RequestMapping(value = "/wallet")
	public ModelAndView wallet(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> res = super.getCommonMap();

		res.put("objs", userWalletService.queryUserAllCoins(uid));

		return new ModelAndView("personal/wallet", res);
	}

	/** 我的钱包体现申请 */
	@ResponseBody
	@RequestMapping(value = "/updateCashApplication")
	public ReMsg updateCashApplication(HttpServletRequest req,
			@RequestParam(value = "coinType", required = true) String coinType) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.FAIL);
		}
		ReMsg msg = userWalletService.queryUserContainsCoins(uid, coinType);
		if (0 != msg.getCode()) {
			return msg;
		}
		return withdrawLogService.updateWithdrawApplication(uid, 1d, coinType);
	}

	/** 个人中心段位界面 */
	@RequestMapping(value = "/personalLevel")
	public ModelAndView personalLevel(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> res = super.getCommonMap();
		res.put("divisions", Division.values());
		res.put("userDivision", DboUtil.getLong(userService.findById(uid), "divisionId"));
		return new ModelAndView("personal/personal-level", res);
	}

	/** 个人中心设置界面 */
	@RequestMapping(value = "/personalSetting")
	public ModelAndView personalSetting(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> res = super.getCommonMap();
		// 用户信息
		res.put("user", userService.findById(uid));
		res.put("userIdentity", userIdentityService.findById(uid));
		return new ModelAndView("personal/personal-setting", res);
	}

	/** 我的下线 界面 */
	@RequestMapping(value = "/invitingFriendDownline")
	public ModelAndView invitingFriendDownline(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> res = super.getCommonMap();
		//
		res.putAll(userShareService.findShareIncludeUser(uid)); 
		res.put("divisonInfos", userDivisionService.getDivisionValues());
		return new ModelAndView("personal/inviting-friends-downline", res);
	}

	/** 我的等级 界面 */
	@RequestMapping(value = "/invitingLevel")
	public ModelAndView invitingLevel(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> res = super.getCommonMap();

		res.put("divisonInfos", userDivisionService.getDivisionValues());

		return new ModelAndView("personal/inviting-level", res);
	}

	/** 下载海报. */
	@RequestMapping(value = "/downloadPoster")
	public ReMsg downloadPoster(HttpServletRequest req, HttpServletResponse res, String picAddr) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.FAIL);
		}
		String fileName = "sharePoster.jpg";
		storageService.downloadPoster(fileName,picAddr,req, res);
		return null;
	}

	/** 账号安全界面 */
	@RequestMapping(value = "/settingAccount")
	public ModelAndView settingAccount(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> res = super.getCommonMap();
		// 用户信息
		DBObject userDbo = userService.findById(uid);
		res.put("user", userDbo);

		res.put("userIdentity", userIdentityService.findById(uid));

		res.putAll(mailLogService.queryUserMailLogStatus(uid, userDbo));

		res.put("hisrecords", loginLogService.queryLastLogByUser(uid, 10));

		return new ModelAndView("personal/setting-account", res);
	}

	/** 绑定邮箱界面 */
	@RequestMapping(value = "/settingEmail")
	public ModelAndView settingEmail(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> res = super.getCommonMap();
		// 用户信息
		DBObject userDbo = userService.findById(uid);
		res.put("user", userDbo);
		res.putAll(mailLogService.queryUserMailLogStatus(uid, userDbo));
		return new ModelAndView("personal/setting-email", res);
	}

	/** 修改密码界面 */
	@RequestMapping(value = "/settingPassword")
	public ModelAndView settingPassword(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> res = super.getCommonMap();

		return new ModelAndView("personal/setting-password", res);
	}

	// 更改密码
	@ResponseBody
	@RequestMapping(value = "/updateOriginalPwd", method = RequestMethod.POST)
	public ReMsg updateOriginalPwd(HttpServletRequest req, HttpServletResponse resq,
			@RequestParam(value = "oldPwd", required = true) String oldPwd,
			@RequestParam(value = "newPwd", required = true) String newPwd) {
		if (newPwd != null && newPwd.length() < 6) {
			return new ReMsg(ReCode.PASSWORD_LENGTH_ERR);
		}
		if (RegexUtil.isNumber(newPwd)) {
			return new ReMsg(ReCode.PASSWORD_LOWERCASE_ERR);
		}
		ReMsg reMsg = userService.upsetPwd(oldPwd, newPwd, req);
		if (0 == reMsg.getCode()) {
			authService.logout(req, resq);
		}
		return reMsg;
	}

	// 异步检验原密码
	@ResponseBody
	@RequestMapping(value = "/getOriginalPwd", method = RequestMethod.GET)
	public ReMsg getOriginalPwd(HttpServletRequest req, HttpServletResponse resq,
			@RequestParam(value = "oldPwd", required = true) String oldPwd) {
		if (oldPwd != null && oldPwd.length() < 6) {
			return new ReMsg(ReCode.OLDPWD_WRITE_ERR);
		}
		return userService.getOriginalPwd(oldPwd);
	}

	// 发送邮箱
	@ResponseBody
	@RequestMapping(value = "/updateOriginalEmail", method = RequestMethod.POST)
	public ReMsg updateOriginalEmail(HttpServletRequest req,
			@RequestParam(value = "email", required = true) String email) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}

		return mailLogService.sendMailToUser(req, email, uid);
	}

	/** redirect correctPage */
	@RequestMapping(value = "/settingEmailSended")
	public ModelAndView correctPage(HttpServletRequest req) {
		Map<String, Object> res = super.getCommonMap();
		return new ModelAndView("personal/setting-email-sended", res);
	}

	/** 个人设置界面 */
	@RequestMapping(value = "/settingPersonal")
	public ModelAndView settingPersonal(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> res = super.getCommonMap();
		// 用户信息
		res.put("user", userService.findByIdSafe(uid));
		return new ModelAndView("personal/setting-personal", res);
	}

	/**
	 * 更改个人设置
	 */
	@ResponseBody
	@RequestMapping(value = "/updateSettingPersonal", method = RequestMethod.POST)
	public ReMsg updateSettingPersonal(HttpServletRequest req, String username, Integer sex, String photo) {
		if (StringUtils.isEmpty(username)) {
			return new ReMsg(ReCode.NICKNAME_EMP_ERR);
		}
		if (StringUtils.isNotEmpty(username) && username.length() > 20) {
			return new ReMsg(ReCode.NICKNAME_PATTAN_ERR);
		}
		return userService.upsetPersonInfo(username, sex, photo);

	}

	/** 上传个人头像 */
	@ResponseBody
	@RequestMapping(value = "/updatePersonalImg")
	public ReMsg updatePersonalImg(@RequestParam MultipartFile file, HttpServletRequest req) {

		return storageService.uploadMyImg(file, null, null);
	}

	/** 账户详细界面 */
	@RequestMapping(value = "/accountDetails")
	public ModelAndView accountDetails(HttpServletRequest req, Integer page, String coinType) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> res = super.getCommonMap();
		// 用户信息
		res.put("curPage", userWalletService.queryCoinLogByUid(uid, page, coinType,null));
		res.put("coinType", coinType);
		return new ModelAndView("personal/account-details", res);
	}

	/** KYC认证界面 */
	@RequestMapping(value = "/settingkyc")
	public ModelAndView settingKYC(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> res = super.getCommonMap();
		// 用户信息
		res.put("userIdentity", userIdentityService.findById(uid));

		return new ModelAndView("personal/setting-kyc", res);
	}

	/** 更改KYC认证 */
	@ResponseBody
	@RequestMapping(value = "/updateSettingkyc")
	public ReMsg updateSettingkyc(HttpServletRequest req, @RequestParam(value = "frontPic") String frontPic,
			@RequestParam(value = "backPic", required = false) String backPic,
			@RequestParam(value = "inHandPic") String inHandPic) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		Map<String, Object> res = super.getCommonMap();

		return userIdentityService.updateKYC(uid, frontPic, backPic, inHandPic, String.valueOf(res.get("username")));
	}

	/** 获取分享图片 */
	@ResponseBody
	@RequestMapping(value = "/sharePic", method = RequestMethod.POST)
	public ReMsg myShare(HttpServletRequest req) {
		Map<String, Object> user = super.getTokenUser();
		String code = MapUtil.getStr(user, "shareCode");
		Map<String, Object> res = super.getCommonMap();
		try {
			List<String> pics = posterService.createPoster(code);
			for (int i = 0; i < pics.size(); i++) {
				res.put("poster" + i, pics.get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ReMsg(ReCode.OK, res);
	}
}
