package com.zb.web.view.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.othergames.GuessService;

@Controller
@RequestMapping("/admin")
public class AdminGuessCtl {
	static final Logger log = LoggerFactory.getLogger(AdminGuessCtl.class);

	@Autowired
	GuessService guessService;

	/** 后台猜猜列表 status 下注中 开奖中 已解散 已结束 特殊 开奖中有人举报 属于已冻结 */
	@RequestMapping("/guesses")
	public ModelAndView guesss(Long guessId, Long uid, Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = guessService.queryGuess(guessId, uid, status, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("guessId", guessId);
		model.put("uid", uid);
		model.put("status", status);
		model.put("sysTime", System.currentTimeMillis());
		return new ModelAndView("admin/guess/guesses", model);
	}

	/** 后台猜猜日志列表 */
	@RequestMapping("/guessLogs")
	public ModelAndView guesssLog(Long guessId, Long uid, Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = guessService.queryGuessLog(guessId, uid, status, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("page", curPage);
		model.put("guessId", guessId);
		model.put("uid", uid);
		return new ModelAndView("admin/guess/guessLogs", model);
	}

	/** 某个猜猜详情 */
	@RequestMapping("/guess")
	public ModelAndView guess(Long id) {
		DBObject dbo = guessService.findgGuessById(id);
		if (dbo != null) {
			dbo.put("sysTime", System.currentTimeMillis());
			return new ModelAndView("admin/guess/guess", "obj", dbo);
		}
		return new ModelAndView("admin/guess/publishGuess");
	}

	/** 添加一个猜猜 */
	@ResponseBody
	@RequestMapping(value = "/guess", method = RequestMethod.POST)
	public ReMsg guess(Integer type, String title, Integer issue, String endDrawTime, Long uid, String items) {
		long adminId = guessService.getUid();
		if (adminId < 1 || StringUtils.isBlank(title)) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		if (null == issue && (StringUtils.isBlank(items) || StringUtils.isBlank(endDrawTime))) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return guessService.adminSponsorGuess(type, title, issue, endDrawTime, uid, items, adminId);
	}

	/** 开奖 */
	@ResponseBody
	@RequestMapping("/drawGuess")
	public ReMsg drawGuess(long guessId, String guessItemId) {
		long adminId = guessService.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return guessService.adminDrawGuess(guessId, guessItemId);
	}

	/** 不退押金 */
	@ResponseBody
	@RequestMapping("/backBondGuess")
	public ReMsg backBondGuess(long guessId) {
		long adminId = guessService.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return guessService.adminBackBondGuess(guessId);
	}

	/** 解散 */
	@ResponseBody
	@RequestMapping("/dissMissGuess")
	public ReMsg dissMissGuess(long guessId) {
		long adminId = guessService.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		System.out.println(guessId);
		return guessService.adminDissMissGuess(adminId, guessId);
	}
}
