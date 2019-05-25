package com.zb.web.view.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.service.server.BakService;

@Controller
@RequestMapping("/admin")
public class AdminBakCtl {

	static final Logger log = LoggerFactory.getLogger(AdminBakCtl.class);


	@Autowired
	BakService bakService;

	
	@RequestMapping("/sys/bak")
	public ModelAndView bak(HttpServletRequest request) {
		return new ModelAndView("admin/sys/bak");
	}
	
	@ResponseBody
	@RequestMapping(value = "/sys/bak", method = RequestMethod.POST)
	public ReMsg bakCoin(String type,HttpServletRequest request) {
		String time = null;
		if("coin".equals(type)){
			time = bakService.bak(DocName.COIN_LOG, 30);
		}else if("point".equals(type)){
			time = bakService.bak(DocName.POINT_LOG, 30);
		}else if("login".equals(type)){
			time = bakService.bak(DocName.LOGIN_LOG, 90);
		}else if("task".equals(type)){
			time = bakService.bakTask();
		}else if("st".equals(type)){
			time = bakService.bakSt();
//		}else if("rst".equals(type)){
//			time = bakService.bakStReSet();
		}
		return new ReMsg (ReCode.OK, time);
	}

}
