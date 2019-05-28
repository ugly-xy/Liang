package com.zb.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.service.othergames.StarTrekService;

@Controller
@RequestMapping("/starTrek")
public class StarTrekCtl extends BaseCtl {

	@Autowired
	StarTrekService starTrekService;

	/** 查询好友列表 */
	@ResponseBody
	@RequestMapping("/queryFriends")
	public ReMsg queryFriends(Integer page) {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return starTrekService.queryFriends(id, null == page ? 1 : page);
	}

	/** 查询是否有待收金币 */
	@ResponseBody
	@RequestMapping("/checkGain")
	public ReMsg checkGain() {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return starTrekService.checkGain(id);
	}

	/** 查询个人信息 */
	@ResponseBody
	@RequestMapping("/userCenter")
	public ReMsg userCenter(Long uid) {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return starTrekService.userCenter(id, uid);
	};

	/** 查询用户的奴隶数量 */
	@ResponseBody
	@RequestMapping("/querySlaveCount")
	public ReMsg querySlaveCount(Long uid) {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (uid == null || uid < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return new ReMsg(ReCode.OK, starTrekService.querySlaveCount(uid));
	};

	/** 查询用户的所有奴隶 */
	@ResponseBody
	@RequestMapping("/queryAllSlaveByMasterId")
	public ReMsg queryAllSlaveByMasterId(Long uid) {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (uid == null || uid < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return new ReMsg(ReCode.OK, starTrekService.queryAllSlaveByMasterId(uid));
	}

	/** 查询奴隶详情 */
	@ResponseBody
	@RequestMapping("/queryBySlaveId")
	public ReMsg queryBySlaveId(Long sid) {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (sid == null || sid < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return new ReMsg(ReCode.OK, starTrekService.queryBySlaveId(sid));
	}

	/** 查询我的奴隶 */
	@ResponseBody
	@RequestMapping("/mySlaves")
	public ReMsg mySlaves() {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return new ReMsg(ReCode.OK, starTrekService.mySlaves(id));
	}

	/** 获取工作内容 */
	@ResponseBody
	@RequestMapping("/getWork")
	public ReMsg getWork() {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return starTrekService.getWork();
	}

	/** 赐名 */
	@ResponseBody
	@RequestMapping("/giveNickname")
	public ReMsg giveNickname(Long sid, String name) {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (sid == null || sid < 1 || null == name || "".equals(name)) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return starTrekService.giveNickname(id, sid, name);
	}

	/** 派遣奴隶工作 */
	@ResponseBody
	@RequestMapping("/sendSlave")
	public ReMsg sendSlave(Long sid, String work) {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (sid == null || sid < 1 || null == work || "".equals(work)) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return starTrekService.sendSlave(id, sid, work);
	}

	/** 收取收益 */
	@ResponseBody
	@RequestMapping("/gain")
	public ReMsg gain(Long sid) {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (sid == null || sid < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return starTrekService.gain(id, sid);
	}

	/** 升级星球 */
	@ResponseBody
	@RequestMapping("/upGradeStar")
	public ReMsg upGradeStar() {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return starTrekService.upGradeStar(id);
	}

	/** 买奴隶 */
	@ResponseBody
	@RequestMapping("/buySlave")
	public ReMsg buySlave(Long sid) {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (sid == null || sid < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return starTrekService.buySlave(id, sid);
	}

	/** 释放奴隶 */
	@ResponseBody
	@RequestMapping("/releaseSlave")
	public ReMsg releaseSlave(Long sid) {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (sid == null || sid < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return starTrekService.releaseSlave(id, sid);
	}

	/** 召回奴隶 */
	@ResponseBody
	@RequestMapping("/callBack")
	public ReMsg callBack(Long sid) {
		long id = super.getUid();
		if (id < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (sid == null || sid < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return starTrekService.callBack(sid);
	}

}
