package com.zb.web.view.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.VersionUpdateService;

@Controller
@RequestMapping("/admin")
public class AdminVersionUpdateCtl {

	static final Logger log = LoggerFactory.getLogger(AdminVersionUpdateCtl.class);

	@Autowired
	VersionUpdateService versionUpdateService;

	// tools
	@RequestMapping("/versions")
	public ModelAndView feedbacks(Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = versionUpdateService.query(page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		return new ModelAndView("admin/sys/versions", model);
	}

	@RequestMapping("/version")
	public ModelAndView feedback(Long id) {
		DBObject dbo = versionUpdateService.findById(id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("obj", dbo);
		return new ModelAndView("admin/sys/version", m);
	}

	@ResponseBody
	@RequestMapping(value = "/version", method = RequestMethod.POST)
	public ReMsg update(Long id, Integer status, Integer ch, Integer childCh, Double curVersion, Double mustVersion,
			String title, String discription, String url, Integer via, String appName) {
		System.out.println(appName);
		if (null != id && id > 0) {
			return versionUpdateService.update(id, status, ch, childCh, curVersion, mustVersion, title, discription,
					url, via,appName);
		} else {
			return versionUpdateService.save(status, ch, childCh, curVersion, mustVersion, title, discription, url,
					via,appName);
		}

	}

	@ResponseBody
	@RequestMapping(value = "/version/andDownUrl")
	public ReMsg getLastVersionDown(String appCode) {
		String url = versionUpdateService.getAndroidLastVersion(appCode);
		if (url != null)
			return new ReMsg(ReCode.OK, url);
		else
			return new ReMsg(ReCode.FAIL);
	}

}
