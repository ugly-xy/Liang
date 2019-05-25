package com.zb.web.view.m;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zb.service.pay.CoinService;

@Controller
public class GameCtl {

	@Autowired
	CoinService coinService;

	@RequestMapping("/game/index")
	public ModelAndView games(HttpServletRequest req) {
		return new ModelAndView("games/index");
	}

	@RequestMapping("/game/drawSomething")
	public ModelAndView drawSomething(HttpServletRequest req) {
		return new ModelAndView("games/drawSomething");
	}
	
	@RequestMapping("/game/undercover")
	public ModelAndView undercover(HttpServletRequest req) {
		return new ModelAndView("games/undercover");
	}
	
	@RequestMapping("/game/dice")
	public ModelAndView dice(HttpServletRequest req) {
		return new ModelAndView("games/dice");
	}
	
}
