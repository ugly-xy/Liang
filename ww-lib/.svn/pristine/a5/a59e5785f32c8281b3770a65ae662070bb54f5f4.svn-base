package com.zb.service.usertask;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.GiftForm;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.ReCode;
import com.zb.common.Constant.Via;
import com.zb.common.mongo.DboUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.VipMap;
import com.zb.models.finance.CoinLog;
import com.zb.models.goods.BaseGoods;
import com.zb.models.usertask.Login7Day;
import com.zb.models.usertask.Task;
import com.zb.models.usertask.TaskModel;
import com.zb.service.BaseService;
import com.zb.service.EnvService;
import com.zb.service.MessageService;
import com.zb.service.UserPackService;
import com.zb.service.UserService;
import com.zb.service.pay.CoinService;
import com.zb.view.TaskRPVO;
import com.zb.view.TaskVO;

@Service
public class UserTaskService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(UserTaskService.class);
	// private static final int[][] login7day = { { 1000, 100 }, { 200, 200 }, {
	// 300, 300 }, { 400, 400 }, { 500, 500 },
	// { 600, 600 }, { 700, 700 } };
	private static final int[][] login3day = { { 800, 100 }, { 500, 0 }, { 200, 0 } };

	@Autowired
	CoinService coinService;
	@Autowired
	UserService userService;
	@Autowired
	UserPackService userPackService;
	@Autowired
	TaskModelService taskModelService;
	@Autowired
	MessageService messageService;
	@Autowired
	OneTaskService oneTaskService;
	@Autowired
	EnvService envService;

	public ReMsg doLogin7Day(HttpServletRequest req) {
		long uid = super.getUid();
		// log.error("xxxxxx:" + uid);
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return doLogin7Day(uid, req);
	}

	public ReMsg doLogin7Day(long uid, HttpServletRequest req) {
		Double version = getVer(req);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				// 执行每日登录任务
				doUserTask(uid, Task.LOGIN, 1);
				// 绑定手机任务
				DBObject user = userService.findById(uid);
				String phone = DboUtil.getString(user, "phone");
				if (StringUtils.isNotBlank(phone)) {
					doUserTask(uid, Task.BINDING_PHONE, 1);
				}
				DBObject dbo = getC(DocName.LOGIN_7DAY).findOne(new BasicDBObject("_id", uid));
				if (dbo == null) {
					Login7Day l7d = new Login7Day();
					l7d.set_id(uid);
					l7d.addFinishedTime(System.currentTimeMillis());
					getMongoTemplate().save(l7d);
					coinService.addCoin(uid, CoinLog.IN_LOGIN3DAY, 0, login3day[0][0], 0l, "首次登录用户赠送金币经验");
					userService.changePoint(uid, Point.TASK_LOGIN3DAY.getType(), login3day[0][1], 0L);
					String body = "欢迎尊敬的主人来到BiBi娱乐社区，我是您的小助理BiBi君，有什么问题欢迎随时向我反馈哟。/:086/:086/:086\n完成首页福利的任务可领取大量金币和经验，一般人我不告诉他，嘻嘻！/:079/:079/:079";
					messageService.imMsgHandler(Const.SYSTEM_ID, uid, body, null, null);
					body = "今天是您的第1天登陆,系统赠送您" + login3day[0][0] + "金币和" + login3day[0][1]
							+ "点经验作为见面礼哟！/:805/:805/:805\n第二天登录还会有丰厚的金币奖励，每日签到也会领取宝箱奖励，快来邀请小伙伴一起玩耍吧！/:072/:072/:072";
					// 今天是您的第1天登录，系统赠送您800金币和100点经验作为见面礼哟！/:805
					// 第二天登录还会有丰厚的金币奖励，每日签到也会领取宝箱奖励，快来邀请小伙伴一起玩耍吧！/:072
					messageService.imMsgHandler(Const.SYSTEM_ID, uid, body, null, null);
					// messageService.easeMsgHandler(Const.SYSTEM_ID, uid, body,
					// null, null);
				} else {
					Login7Day l7d = DboUtil.toBean(dbo, Login7Day.class);
					if (l7d.getFinishedTime().size() < 3) {// 未完成
						int no = l7d.getFinishedTime().size();
						long last = l7d.getFinishedTime().get(no - 1);
						long cur = System.currentTimeMillis();
						if (cur - last > 3600 * 1000 * 24) {
							l7d.addFinishedTime(cur);
							getMongoTemplate().save(l7d);
							coinService.addCoin(uid, CoinLog.IN_LOGIN3DAY, 0, login3day[no][0], 0l,
									"用户连续登录奖励金币--" + (no + 1));
							String body = "今天是你第" + (no + 1) + "天登陆,赠送你" + login3day[no][0] + "金币";
							if (no + 1 == 2) {
								body = "今天是您的第" + (no + 1) + "天登录，系统赠送您" + login3day[no][0]
										+ "金币的奖励！/:'\"\"/:'\"\"/:'\"\"\n您也可以通过星球奴隶来抓好友或基友/CP为您打工赚钱，记得邀请好友加入最高领取8888金币奖励哟！/:085/:085/:085";
								// 今天是您的第2天登录，系统赠送您500金币的奖励！/:'""
								// 您也可以通过星球奴隶来抓好友或基友/CP为您打工赚钱，记得邀请好友加入最高领取8888金币奖励哟！/:085
							} else if (no + 1 == 3) {
								body = "今天是您的第" + (no + 1) + "天登录，系统赠送您" + login3day[no][0]
										+ "金币的奖励！/:(Zz...)/:(Zz...)/:(Zz...)\n完成6元充值可以获取永久VIP会员，新游戏特权，红名特权，升级加速，每日VIP礼包等28项特权功能！/:807/:807/:807";
								// 今天是您的第3天登录，系统赠送您200金币的奖励！/:(Zz...)
								// 完成6元充值可以获取永久VIP会员，新游戏特权，红名特权，升级加速，每日VIP礼包等28项特权功能！/:807
							}
							messageService.imMsgHandler(Const.SYSTEM_ID, uid, body, null, null);
							// messageService.easeMsgHandler(Const.SYSTEM_ID,
							// uid, body, null, null);
						}
					}
				}
				oneTaskService.checkCoinGiven(uid, version);
			}
		});
		t.start();
		return new ReMsg(ReCode.OK);
	}

	// 获取用户任务
	public ReMsg getUserTask(Integer type, Integer via, HttpServletRequest req) {
		if (null == type) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		if (null == via || via == 0) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		if (Task.TYPE_DAILY != type && Task.TYPE_NEW != type && Task.TYPE_VIP != type) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		Task task = getTask(type, uid);
		boolean showAppstore = false;
		if (Via.IPhone.getVia() == via) {// ios
			Double ver = super.getVer(req);
			Double checkVer = Double.valueOf(envService.getString("ios.check.ver"));
			if (null == ver || null == checkVer || ver < checkVer) {
				showAppstore = true;
			}
		}
		if (type == Task.TYPE_VIP) {
			DBObject user = userService.findById(uid);
			int vip = DboUtil.getInt(user, "vip") == null ? 0 : DboUtil.getInt(user, "vip");
			List<TaskModel> list = task.getVips();
			if (null == list) {
				task.setVips(getList(Task.TYPE_VIP));
			} else {
				task.getVips().addAll(getList(Task.TYPE_VIP).subList(task.getVips().size(), 10));
			}
			task.setTasks(task.getVips());
			return new ReMsg(ReCode.OK, new TaskVO(task, vip, showAppstore).getTasks());
		}
		return new ReMsg(ReCode.OK, new TaskVO(task, 0, showAppstore).getTasks());
	}

	// 查询用户未领取任务数 (任务小红点)
	public ReMsg getRedPoint(Integer via) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		if (null == via || via == 0) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return new ReMsg(ReCode.OK, queryRedPointCount(uid, via));
	}

	// 查询用户未完成新手任务数(决定是否直接跳转每日任务) 以及分栏目小红点
	public ReMsg getSkip(Integer via) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		if (null == via || via == 0) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return new ReMsg(ReCode.OK, querySkip(uid, via));
	}

	// 用户进行了某一项任务
	public ReMsg doUserTask(long uid, int[][] task, int count) {
		for (int[] a : task) {
			if (a[0] == Task.TYPE_NEW) {
				doDailyNewTask(uid, a[0], a[1], count);
				continue;
			}
			if (a[0] == Task.TYPE_DAILY) {
				doDailyNewTask(uid, a[0], a[1], count);
				continue;
			}
			// if (a[0] == Task.TYPE_CIRCLE) {
			// try {
			// doCircleTask(uid, a[0], a[1], count);
			// } catch (Exception e) {
			// log.error("doCircleTask--error");
			// }
			// continue;
			// }
			return new ReMsg(ReCode.FAIL);
		}
		return new ReMsg(ReCode.OK);
	}

	// // 进行圈子任务
	// public ReMsg doCircleTask(long uid, int taskType, long tmId, int count) {
	// int doTask = 0;
	// Task task = getTask(taskType, uid);
	// List<TaskModel> list = task.getCircles();
	// for (TaskModel c : list) {
	// if (c.get_id() == tmId) {
	// if (c.getTmStatus() == Task.STATUS_DOING) {
	// c.setTmPlan(c.getTmPlan() + count);
	// sendReward(uid, c);
	// // 达到结束条件
	// if (c.getTmPlan() >= c.getTmEndCondition()) {
	// c.setTmPlan(c.getTmEndCondition());
	// c.setTmStatus(Task.STATUS_RECEIVED);
	// }
	// doTask = 1;
	// break;
	// }
	// }
	// }
	// if (doTask == 1) {
	// save(task);
	// }
	// return new ReMsg(ReCode.OK);
	// }

	// 进行每日或者新手任务
	public ReMsg doDailyNewTask(long uid, int taskType, long tmId, int count) {
		int doTask = 0;
		Task task = getTask(taskType, uid);
		List<TaskModel> list = task.getTasks();
		for (TaskModel c : list) {
			if (c.get_id() == tmId) {
				if (c.getTmStatus() == Task.STATUS_DOING) {
					c.setTmPlan(c.getTmPlan() + count);
					// 达到结束条件
					if (c.getTmPlan() >= c.getTmEndCondition()) {
						c.setTmPlan(c.getTmEndCondition());
						c.setTmStatus(Task.STATUS_UNRECEIVE);
						TaskModel a = c;
						list.remove(c);
						list.add(0, a);
					}
					doTask = 1;
					break;
				}
			}
		}
		if (doTask == 1) {
			save(task);
		}
		return new ReMsg(ReCode.OK);
	}

	// 用户完成某一项任务 领奖励
	public ReMsg receiveReward(Integer taskType, Long tmId) {
		long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		if (null == tmId || tmId < 1 || null == taskType) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		int day = Task.getDailyDay();
		if (taskType == Task.TYPE_NEW) {
			day = Task.getNewDay();
		}
		DBObject dbObject = findById(userId + "-" + day);
		if (null == dbObject) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		Task task = getTask(taskType, userId);
		List<TaskModel> list = task.getTasks();
		if (taskType == Task.TYPE_VIP) {
			list = task.getVips();
		}
		for (int i = 0; i < list.size(); i++) {
			TaskModel c = list.get(i);
			if (c.get_id() == tmId) {
				if (c.getTmStatus() != Task.STATUS_UNRECEIVE) {
					return new ReMsg(ReCode.FAIL);
				}
				sendReward(userId, c);
				c.setTmStatus(Task.STATUS_RECEIVED);
				if (taskType == Task.TYPE_DAILY || taskType == Task.TYPE_NEW) {
					list.remove(i);
					list.add(c);
				}
				save(task);
				return new ReMsg(ReCode.OK, c.getTmReward());
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	// 获取用户任务列表
	private Task getTask(int type, long uid) {
		Task task = null;
		DBObject db = null;
		Integer day = null;
		int vip = 0;
		if (type == Task.TYPE_NEW) {
			day = Task.getNewDay();
			db = findById(uid + "-" + day);
			if (null == db) {
				task = new Task(uid, day, getList(Task.TYPE_NEW));
				save(task);
				return task;
			}
		} else {
			day = Task.getDailyDay();
			db = findById(uid + "-" + day);
			DBObject user = userService.findById(uid);
			vip = VipMap.getLevel(DboUtil.getInt(user, "vip"));
			if (null == db) {
				task = new Task(uid, day, getList(Task.TYPE_DAILY), null, getList(vip, Task.TYPE_VIP));
				save(task);
				return task;
			}
		}
		return checkVIP(vip, uid, getTask(db));
	}

	private Task checkVIP(int vip, long uid, Task task) {
		if (vip < 1) {
			return task;
		} else {
			List<TaskModel> vips = task.getVips();
			if (null == vips || vips.size() == 0) {// 原本是空
				task.setVips(getList(vip, Task.TYPE_VIP));
				save(task);
				return task;
			} else if (vips.size() < vip) { // vip等级有提升
				List<TaskModel> list = getList(vip, Task.TYPE_VIP);
				task.getVips().addAll(list.subList(task.getVips().size(), vip));
				save(task);
				return task;
			}
		}
		return task;
	}

	private DBObject findById(String taskId) {
		DBObject task = getC(DocName.TASK).findOne(new BasicDBObject("_id", taskId));
		return task;

	}

	private void save(Task task) {
		task.setUpdateTime(System.currentTimeMillis());
		getMongoTemplate().save(task);
	}

	private Task getTask(DBObject dbo) {
		List<DBObject> vips = DboUtil.get(dbo, "vips", ArrayList.class);
		List<DBObject> tasks = DboUtil.get(dbo, "tasks", ArrayList.class);
		Task task = DboUtil.toBean(dbo, Task.class);
		task.setTasks(getList(tasks));
		task.setVips(getList(vips));
		return task;
	}

	private void sendReward(Long uid, TaskModel model) {
		// if (model.get_id() == Task.CRICLE_PUBLISH[0][1]) {// 发表文章
		// // messageService.aliMsgHandler(Const.SYSTEM_ID, uid, "发表文章成功,系统赠送您"
		// // + model.getTmReward(), null, null);
		// MapBody mb = new MapBody("op",
		// OperationType.NULL.getOp()).append("opId", 0).append("content",
		// "发表文章成功,系统赠送您" + model.getTmReward());
		// Msg msg = new Msg(super.incrMsgId(), MsgType.NOTICE, 0, uid,
		// System.currentTimeMillis(), mb.getData());
		// messageService.msgHandler(msg);
		// }
		String rewards = model.getRewards();
		String[] split = rewards.split(";");
		for (String string : split) {
			String[] reward = string.split("=");
			if ("鲜花".equals(reward[0])) {
				userPackService.addGoods(uid, BaseGoods.Gift.FLOWER.getV(), Integer.parseInt(reward[1]), GiftForm.TASK,
						model.get_id());
				continue;
			}
			if ("经验".equals(reward[0])) {
				userService.changePoint(uid, Point.TASK.getType(), Integer.parseInt(reward[1]), 0);
				continue;
			}
			if ("金币".equals(reward[0])) {
				coinService.addCoin(uid, CoinLog.TASK_DONE, model.get_id(), Integer.parseInt(reward[1]), 0,
						model.getTmName());
				continue;
			}
			if ("黄金喇叭".equals(reward[0])) {
				userPackService.addGoods(uid, BaseGoods.Prop.GOLDEN_HORN.getV(), Integer.parseInt(reward[1]),
						GiftForm.TASK, model.get_id());
				continue;
			}
			if ("快进卡".equals(reward[0])) {
				userPackService.addGoods(uid, BaseGoods.Prop.FAST_CARD.getV(), Integer.parseInt(reward[1]),
						GiftForm.TASK, model.get_id());
				continue;
			}
		}
	}

	private List<TaskModel> getList(Integer tmType) {
		List<DBObject> list = taskModelService.find(tmType, null, Const.STATUS_OK);
		if (null == list || list.size() == 0) {
			return null;
		}
		List<TaskModel> taskModels = new ArrayList<TaskModel>();
		for (int i = 0; i < list.size(); i++) {
			TaskModel model = DboUtil.toBean(list.get(i), TaskModel.class);
			taskModels.add(model);
		}
		return taskModels;
	}

	private List<TaskModel> getList(Integer vip, Integer tmType) {
		List<DBObject> list = taskModelService.find(tmType, vip, Const.STATUS_OK);
		if (null == list || list.size() == 0) {
			return null;
		}
		List<TaskModel> taskModels = new ArrayList<TaskModel>();
		for (int i = 0; i < list.size(); i++) {
			TaskModel model = DboUtil.toBean(list.get(i), TaskModel.class);
			model.setTmStatus(Task.STATUS_UNRECEIVE);
			taskModels.add(model);
		}
		return taskModels;
	}

	private List<TaskModel> getList(List<DBObject> dbos) {
		if (dbos == null || dbos.size() == 0) {
			return new ArrayList<TaskModel>();
		}
		List<TaskModel> models = new ArrayList<TaskModel>();
		for (int i = 0; i < dbos.size(); i++) {
			TaskModel model = DboUtil.toBean(dbos.get(i), TaskModel.class);
			models.add(model);
		}
		return models;
	}

	// 跳转 以及redPoint
	private TaskRPVO querySkip(Long uid, Integer via) {
		TaskRPVO vo = new TaskRPVO();
		int count = 0;
		int num = 0;
		Task task = getTask(Task.TYPE_NEW, uid);
		// 跳转
		for (TaskModel tm : task.getTasks()) {
			if (tm.getTmStatus() != Task.STATUS_RECEIVED) {
				count++;
				if ("appStore".equals(tm.getOp())) {
					num++;
				}
			}
			continue;
		}
		if (via == Via.Android.getVia()) {
			vo.setSkip(count - num);
		} else {
			vo.setSkip(count);
		}
		count = 0;
		// 是否有未领取的新手任务
		for (TaskModel tm : task.getTasks()) {
			if (tm.getTmStatus() == Task.STATUS_UNRECEIVE) {
				count++;
			}
		}
		vo.setNewRP(count);
		count = 0;
		task = getTask(Task.TYPE_DAILY, uid);
		if (null != task.getVips() && task.getVips().size() != 0) {
			// 是否有未领取的vip任务
			for (TaskModel tm : task.getVips()) {
				if (tm.getTmStatus() == Task.STATUS_UNRECEIVE) {
					count++;
				}
			}
		}

		vo.setVipRP(count);
		// 是否有未领取的每日任务
		count = 0;
		for (TaskModel tm : task.getTasks()) {
			if (tm.getTmStatus() == Task.STATUS_UNRECEIVE) {
				count++;
			}
		}
		vo.setDailyRP(vo.getVipRP() + count);

		return vo;
	}

	private int queryRedPointCount(Long uid, Integer via) {
		int count = 0;
		int num = 0;
		Task task = getTask(Task.TYPE_NEW, uid);
		for (TaskModel tm : task.getTasks()) {
			if (tm.getTmStatus() == Task.STATUS_UNRECEIVE) {
				count++;
				if ("appStore".equals(tm.getOp())) {
					num++;
				}
			}
			continue;
		}
		task = getTask(Task.TYPE_DAILY, uid);
		for (TaskModel tm : task.getTasks()) {
			if (tm.getTmStatus() == Task.STATUS_UNRECEIVE) {
				count++;

			}
			continue;
		}
		if (null != task.getVips() && task.getVips().size() != 0) {
			for (TaskModel tm : task.getVips()) {
				if (tm.getTmStatus() == Task.STATUS_UNRECEIVE) {
					count++;
				}
				continue;
			}
		}

		if (via == Via.Android.getVia()) {
			return count - num;
		}
		return count;
	}

}