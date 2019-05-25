package com.zb.web.api;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.BlackWordUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.core.conf.Config;
import com.zb.core.web.ReMsg;
import com.zb.models.Keyword;
import com.zb.models.server.ServerInfo;
import com.zb.service.AppShareService;
import com.zb.service.BannerService;
import com.zb.service.EnvService;
import com.zb.service.FeedbackService;
import com.zb.service.KeywordService;
import com.zb.service.VersionUpdateService;
import com.zb.service.server.ServerMngService;

@Controller
@RequestMapping(value = "/sys")
public class SysCtl extends BaseCtl {
	static final Logger log = LoggerFactory.getLogger(SysCtl.class);

	@Autowired
	AppShareService appShareService;

	@Autowired
	EnvService envService;

	@Autowired
	FeedbackService feedbackService;

	@Autowired
	VersionUpdateService versionUpdateService;

	@Autowired
	KeywordService keywordService;

	@Autowired
	BannerService bannerService;

	@Autowired
	ServerMngService serverMngService;

	private static String[][] ANDQUNS = { { "533960063", "VOOX67fqtFsegtW0e_AFjpB9kYRar2_D" },
			{ "566613110", "4yo8ZKVUwIazHDW2UhLMipDZ9D2ZKkOR" }, { "491870040", "w6owGo-nmZC0xgQ1fouuwFXZSQaC4-1E" } };

	private static String[][] APPLEQUNS = {
			{ "533960063", "1400a455220d31871b3040173e28e2cd4d4d972c098ea9a8139b910efb68ae84" },
			{ "566613110", "b4538f0d7c03241aca56157f85916c757f2ef595dbcafd474f3f4880b214c7a7" },
			{ "491870040", "974e3c97fe7a542e26061a9cfc632f07fa7ea0aff53171c6f6ea027b449dbe07" } };

	@ResponseBody
	@RequestMapping("/qQun")
	public ReMsg qQun(Integer via, HttpServletRequest request) {
		Integer cur = envService.getInt("qq.qun.cur");
		if (cur == null) {
			cur = 0;
		}
		String[] qun = null;
		if (via == 1) {
			qun = ANDQUNS[cur];
		} else {
			qun = APPLEQUNS[cur];
		}
		return new ReMsg(ReCode.OK, qun);
	}

	@ResponseBody
	@RequestMapping("/img")
	public ReMsg uploadHeadPic(HttpServletRequest request) {
		return new ReMsg(ReCode.OK, Config.cur().get("img.domain", "http://img.zhuangdianbi.com"));
	}

	@ResponseBody
	@RequestMapping("/app/share")
	public ReMsg share(Double ver, Integer via, HttpServletRequest request) {
		return appShareService.save(ver, via, request);
	}

	@ResponseBody
	@RequestMapping("/app/shareCount")
	public ReMsg shareCount(HttpServletRequest request) {
		return appShareService.getShareCount();
	}

	@ResponseBody
	@RequestMapping("/feedback")
	public ReMsg feedback(String content, String contact, String baseEnv, String pics, Integer via, String devInfo,
			HttpServletRequest request) {
		String[] photos = null;
		if (StringUtils.isNotBlank(pics)) {
			List<String> t = JSONUtil.jsonToArray(pics, String.class);
			photos = t.toArray(new String[] {});
		}
		return feedbackService.save(content, contact, baseEnv, photos, via, devInfo, request);
	}

	@ResponseBody
	@RequestMapping("/version")
	public ReMsg version(HttpServletRequest request) {
		return versionUpdateService.check(request);
	}

	@ResponseBody
	@RequestMapping("/useCheck")
	public ReMsg check(Integer via, Double ver, String appCode, HttpServletRequest request) {
		if (via == 2) {
			if (appCode == null || !Const.APP_CODE_ARTIFACT.equals(appCode)) {
				Double curVer = envService.getDouble("ios.check.ver");
				if (curVer == null) {
					curVer = 1.0d;
				}
				if (ver != null && ver < curVer) {
					return new ReMsg(ReCode.OK, false);
				} else {
					return new ReMsg(ReCode.OK, true);
				}
			} else {
				Double curVer = envService.getDouble("artifact.ios.check.ver");
				if (curVer == null) {
					curVer = 1.0d;
				}
				if (ver != null && ver < curVer) {
					return new ReMsg(ReCode.OK, false);
				} else {
					return new ReMsg(ReCode.OK, true);
				}
			}
		}

		return new ReMsg(ReCode.OK, false);
	}

	@ResponseBody
	@RequestMapping("/wordCheck")
	public ReMsg blackCheck(String word, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		return BlackWordUtil.blackWordStatus(word);
	}

	@RequestMapping("/blackWord")
	public void blackWord(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		List<DBObject> dbos = keywordService.getAll(Keyword.BLACK_WORD);
		StringBuilder sb = new StringBuilder();
		for (DBObject dbo : dbos) {
			sb.append(DboUtil.getString(dbo, "_id")).append("\n");
		}
		OutputStream stream = resp.getOutputStream();
		stream.write(sb.toString().getBytes());
		return;
	}

	@RequestMapping("/blackChars")
	public void blackChars(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String bws = "";
		OutputStream stream = resp.getOutputStream();
		stream.write(bws.getBytes());
		return;
	}

	@RequestMapping("/redWord")
	public void redWord(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		List<DBObject> dbos = keywordService.getAll(Keyword.RED_WORD);
		StringBuilder sb = new StringBuilder();
		for (DBObject dbo : dbos) {
			sb.append(DboUtil.getString(dbo, "_id")).append("\n");
		}
		OutputStream stream = resp.getOutputStream();
		stream.write(sb.toString().getBytes());
		return;
	}

	@ResponseBody
	@RequestMapping("/socket/server")
	public ReMsg getSocketServer(HttpServletRequest request) {
		// String ssstr = envService.getString("socket.server.list");
		// String[] ssarr = { "123.56.180.113:9528" };
		// if (StringUtils.isNotBlank(ssstr)) {
		// ssarr = ssstr.split(",");
		// }
		List<String> ssarr = serverMngService.getOkServer(ServerInfo.ServerType.socket, 3);
		if (ssarr.size() > 0) {
			return new ReMsg(ReCode.OK, ssarr);
		}
		return new ReMsg(ReCode.FAIL, ssarr);
	}

	// 获取圈子banner
	@ResponseBody
	@RequestMapping("/banners")
	public ReMsg banner(Integer via, Integer type) {
		List<DBObject> p = bannerService.findBanner(BannerService.TYPES[type], Const.STATUS_UP, via, 0, 5);
		return new ReMsg(ReCode.OK, p);
	}

	// 获取分享地址
	@ResponseBody
	@RequestMapping("/shareLink")
	public ReMsg shareLink(Integer via, Integer type) {
		return new ReMsg(ReCode.OK, envService.getShareLink());
	}

}
