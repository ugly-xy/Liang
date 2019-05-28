package com.zb.web.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.models.usertask.Task;
import com.zb.service.usertask.UserTaskService;

@Controller
@RequestMapping("/task")
public class UserTaskCtl extends BaseCtl {
	@Autowired
	UserTaskService userTaskService;

	// 查询有没有未领取的任务(包括VIP)
	@ResponseBody
	@RequestMapping("/getRedPoint")
	public ReMsg queryDoCount(Integer via, HttpServletRequest req) {
		return userTaskService.getRedPoint(via);
	}

	// 查询有没有不是Received的新手任务
	@ResponseBody
	@RequestMapping("/getSkip")
	public ReMsg queryRecNew(Integer via, HttpServletRequest req) {
		return userTaskService.getSkip(via);
	}

	// 获取任务列表 参数 任务类型 1新手任务 2每日任务 (vip属于每日任务)
	@ResponseBody
	@RequestMapping("/getTask")
	public ReMsg queryTask(Integer type, Integer via,Double ver, HttpServletRequest req) {
		return userTaskService.getUserTask(type, via,req);
	}

	// 领取任务奖励 任务id 具体的任务模型id
	@ResponseBody
	@RequestMapping("/receiveReward")
	public ReMsg receiveReward(Integer taskType, Long tmId, HttpServletRequest req) {
		return userTaskService.receiveReward(taskType, tmId);
	}

//	// 分享任务
//	@ResponseBody
//	@RequestMapping("/share")
//	public ReMsg shareTask(HttpServletRequest req) {
//		long uid = super.getUid();
//		if (uid < 1) {
//			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
//		}
//		return userTaskService.doUserTask(uid, Task.SHARE_QQWECHAT, 1);
//	}

	// 五星好评
	@ResponseBody
	@RequestMapping("/fiveStar")
	public ReMsg fiveTask(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return userTaskService.doUserTask(uid, Task.ITUNES_5_STAR, 1);
	}

	// //测试做任务
	// @ResponseBody
	// @RequestMapping("/doTask")
	// public ReMsg doTask(String taskId, Integer taskType, Long tmId,
	// HttpServletRequest req) {
	// return userTaskService.doUserTask(100108664, Task.DICE, 100);
	// }
}
