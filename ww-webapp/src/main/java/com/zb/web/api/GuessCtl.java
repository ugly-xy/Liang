package com.zb.web.api;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.common.Constant.ReCode;
import com.zb.common.utils.JSONUtil;
import com.zb.core.web.ReMsg;
import com.zb.service.othergames.GuessService;

@Controller
@RequestMapping("/guess")
public class GuessCtl extends BaseCtl {
	static final Logger log = LoggerFactory.getLogger(GuessCtl.class);

	@Autowired
	GuessService guessService;

	/** 能否进入猜猜 */
	@ResponseBody
	@RequestMapping("/inGuess")
	public ReMsg inGuess() {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return guessService.canInGuess(uid);
	}

	/** 我发起的猜猜 */
	@ResponseBody
	@RequestMapping("/myGuess")
	public ReMsg myGuess(Integer page, Integer size) {
		return guessService.myGuessList(page, size);
	}

	/** 我参与的猜猜 */
	@ResponseBody
	@RequestMapping("/myGuessLog")
	public ReMsg myGuessLog(Integer page, Integer size) {
		return guessService.getGuessLogList(page, size);
	}

	/** 最新的猜猜列表 */
	@ResponseBody
	@RequestMapping("/guessList")
	public ReMsg guessList(Integer page, Integer size) {
		return guessService.getGuessList(null, page, size);
	}

	/** 猜猜详情 */
	@ResponseBody
	@RequestMapping("/guessDetail")
	public ReMsg guessDetail(long guessId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return guessService.guessDetail(uid, guessId);
	}

	/** 发起猜猜 */
	@ResponseBody
	@RequestMapping("/sponsorGuess")
	public ReMsg sponsorGuess(String endDrawTime, String title, String items) {
		if (StringUtils.isBlank(endDrawTime) || StringUtils.isBlank(title) || StringUtils.isBlank(items)) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		List<String> ps = null;
		if (StringUtils.isNotBlank(items)) {
			ps = JSONUtil.jsonToArray(items, String.class);
		}
		return guessService.sponsorGuess(uid, endDrawTime, title, ps);
	}

	/** 下注 */
	@ResponseBody
	@RequestMapping("/buttonPour")
	public ReMsg buttonPour(long guessId, String guessItemId, int amount) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		if (StringUtils.isBlank(guessItemId)) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return guessService.buttonPour(uid, guessId, guessItemId, amount);
	}

	/** 解散猜猜 */
	@ResponseBody
	@RequestMapping("/dissMissGuess")
	public ReMsg dissMissGuess(long guessId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return guessService.dissMyMissGuess(guessId);
	}

	/** 开奖 */
	@ResponseBody
	@RequestMapping("/drawGuess")
	public ReMsg drawGuess(long guessId, String guessItemId) {
		if (StringUtils.isBlank(guessItemId)) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return guessService.drawGuess(guessId, guessItemId);
	}

	/** 举报 */
	@ResponseBody
	@RequestMapping("/reportGuess")
	public ReMsg reportGuess(long guessId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return guessService.reportGuess(uid, guessId);
	}

	/** 分享猜猜给好友 */
	@ResponseBody
	@RequestMapping("/shareFriend")
	public ReMsg shareFriend(String ids, Long guessId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (StringUtils.isBlank(ids) || null == guessId) {
			return new ReMsg(ReCode.FAIL);
		}
		Long[] users = null;
		List<Long> t = JSONUtil.jsonToArray(ids, Long.class);
		users = t.toArray(new Long[] {});
		return guessService.shareFriends(uid, users, guessId);
	}

}
