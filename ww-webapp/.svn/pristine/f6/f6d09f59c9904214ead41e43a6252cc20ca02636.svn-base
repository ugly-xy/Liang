package com.zb.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.service.ToolRedPacketService;
import com.zb.service.ZbToolsService;

@Controller
@RequestMapping("/tool")
public class ToolsRedPacketCtl extends BaseCtl {

	@Autowired
	ZbToolsService zbToolsService;

	@Autowired
	ToolRedPacketService toolRedPacketService;

	/**
	 * 发出去红包
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/redPack/get")
	public ReMsg getPack(Double amount, Integer num, Integer year) {
		return new ReMsg(ReCode.OK, toolRedPacketService.getWxPack(amount, num,
				year));
	}

	/**
	 * 收到红包
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/redPack/put")
	public ReMsg putPack(Double amount, Integer num, Integer year) {
		return new ReMsg(ReCode.OK, toolRedPacketService.putWxPack(amount, num,
				year));
	}

	/**
	 * 收到红包
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/redPack/getAli")
	public ReMsg getAliPack(Double amount, Integer num, Integer year) {
		return new ReMsg(ReCode.OK, toolRedPacketService.getAliPack(amount,
				num, year));
	}

	/**
	 * 发出去红包
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/redPack/putAli")
	public ReMsg putAliPack(Double amount, Integer num, Integer year) {
		return new ReMsg(ReCode.OK, toolRedPacketService.putAliPack(amount,
				num, year));
	}

	/**
	 * 收到红包qq
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/redPack/getQq")
	public ReMsg getQqPack(Double amount, Integer num, Integer year) {
		return new ReMsg(ReCode.OK, toolRedPacketService.getQqPack(amount, num,
				year));
	}

	/**
	 * 发出去红包qq
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/redPack/putQq")
	public ReMsg putQqPack(Double amount, Integer num, Integer year) {
		return new ReMsg(ReCode.OK, toolRedPacketService.putQqPack(amount, num,
				year));
	}
}
