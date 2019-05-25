package com.liang.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserCtl extends BaseCtl{
	
	@RequestMapping(value="/testUser")
	public ModelAndView getUserToken() {
		Map<String,Object> map =getCurrMap();
		
		return new ModelAndView("login",map);
	}
	

}
