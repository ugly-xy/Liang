package com.zb.web.view.m;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.mongo.DboUtil;
import com.zb.service.EnvService;
import com.zb.service.VersionUpdateService;

@Controller
@RequestMapping("/downloads")
public class DownloadCtl {

	@Autowired
	EnvService envService;

	@Autowired
	VersionUpdateService versionUpdateService;

	@RequestMapping("/app")
	public ModelAndView index(HttpServletRequest req) {
		Map<String, String> rs = new HashMap<String, String>();
		DBObject dbo = versionUpdateService.getListVersion(req);
		if (dbo != null) {
			rs.put("android", DboUtil.getString(dbo, "url"));
		}
		return new ModelAndView("html/downloads/app", rs);
	}

}
