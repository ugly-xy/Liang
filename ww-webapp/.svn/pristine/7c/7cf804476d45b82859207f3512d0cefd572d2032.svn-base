package com.zb.web.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.core.web.ReMsg;
import com.zb.service.AppChannelService;

@Controller
@RequestMapping("/channel")
public class AppChannelCtl extends BaseCtl {

	@Autowired
	AppChannelService appChannelService;

	@ResponseBody
	@RequestMapping("/newIndex")
	public ReMsg newIndex(Integer via, HttpServletRequest req) {
		return appChannelService.getNewIndex(via, req);
	}

	@ResponseBody
	@RequestMapping("/index")
	public ReMsg index(Integer via, HttpServletRequest req) {
		return appChannelService.getIndex(via, req);
	}

	@ResponseBody
	@RequestMapping("/doutu")
	public ReMsg doutu(Integer via, HttpServletRequest req) {
		return appChannelService.getDouTu(via, req);
	}

	@ResponseBody
	@RequestMapping("/doutu/packs")
	public ReMsg doutuCate(Long cid, Integer page, Integer size) {
		return appChannelService.getDouTu(cid, page, size);
	}

}
