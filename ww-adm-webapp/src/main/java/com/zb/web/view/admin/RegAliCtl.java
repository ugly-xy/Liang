package com.zb.web.view.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.easemob.EmMsg.TargetType;
import com.zb.service.UserService;
import com.zb.service.im.AliIMService;
import com.zb.service.im.EasemobService;

@Controller
@RequestMapping("/admin")
public class RegAliCtl {

	static final Logger LOG = LoggerFactory.getLogger(RegAliCtl.class);

	@Autowired
	UserService userService;
	@Autowired
	AliIMService aliIMService;
	@Autowired
	EasemobService easemobService;

	@ResponseBody
	@RequestMapping(value = "/regAli", method = RequestMethod.POST)
	public ReMsg regAli(Boolean type, Long id) {
		if (id == null) {
			id = 0L;
		}
		for (;;) {
			List<DBObject> list = userService.queryNotRegAli(id, type);
			if (null == list || list.size() == 0) {
				break;
			}
			id = DboUtil.getLong(list.get(list.size() - 1), "_id");
			LOG.error("LASTID ---------->:" + id);
			if (null != type && !type) {
				for (DBObject dbo : list) {
					id = DboUtil.getLong(dbo, "_id");
					String pwd = aliIMService.reg(aliIMService.getUsername(id), dbo);
					if (null == pwd) {
						DBObject u = new BasicDBObject("chatReg", false);
						userService.updateAli(id, u);
					} else {
						DBObject u = new BasicDBObject("chatReg", true);
						userService.updateAli(id, u);
					}
				}
			} else {
				Map<String, Object> reg = aliIMService.adminReg(list);
				updateFailUser(reg);
				updateSuccessUser(reg);
			}
		}

		return new ReMsg(ReCode.OK);
	}

	@RequestMapping("/regAli")
	public ModelAndView regAli() {
		return new ModelAndView("/admin/users/regAli");
	}
	
	@RequestMapping("/aliIMHandler")
	public ModelAndView aliIMHandler() {
		return new ModelAndView("/admin/users/aliIMHandler");
	}

	/** 加载user会话列表 */
	@RequestMapping("/aliIM")
	public ModelAndView loginAli(long uid) {
		DBObject dbo = userService.findById(uid);
		String username = aliIMService.getUsername(uid);
		String pwd = aliIMService.getPwd(username);
		dbo.put("uid", username);
		dbo.put("pwd", pwd);
		dbo.put("appkey", aliIMService.APPKEY);
		return new ModelAndView("/admin/users/aliIM", "obj", dbo);
	}

	/** 加载单聊对象信息 */
	@RequestMapping("/chatOne")
	public ModelAndView chatOne(Long myId, Long rid) {
		DBObject my = userService.findById(myId);
		DBObject r = userService.findById(rid);
		String username = aliIMService.getUsername(myId);
		String pwd = aliIMService.getPwd(username);
		DBObject dbo = new BasicDBObject();
		dbo.put("appkey", aliIMService.APPKEY);
		dbo.put("myId", username);
		dbo.put("myNickname", DboUtil.getString(my, "nickname"));
		dbo.put("myAvatar", DboUtil.getString(my, "avatar"));
		dbo.put("myPwd", pwd);
		dbo.put("rId", rid);
		dbo.put("rNickname", DboUtil.getString(r, "nickname"));
		dbo.put("rAvatar", DboUtil.getString(r, "avatar"));
		return new ModelAndView("/admin/users/chatOne", "obj", dbo);
	}

	@ResponseBody
	@RequestMapping(value = "/pushHXMsg", method = RequestMethod.POST)
	public ReMsg pushHXMsg(Long id) {
		final long end = id;
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				long id = 0;
				for (;;) {
					List<DBObject> list = userService.queryNotRegAli(id, null);
					if (null == list || list.size() == 0) {
						break;
					}
					List<Long> toUids = new ArrayList<Long>();
					for (int i = 0; i < list.size(); i++) {
						id = DboUtil.getLong(list.get(i), "_id");
						if (id > end) {
							break;
						}
						toUids.add(id);
					}
					easemobService.sendMsgTxt(TargetType.users, toUids, "【福利来了】点击APP更新最新版，即可领取888金币丰厚奖励！千万不要错过！",
							Const.SYSTEM_ID, null);
					if (id > end) {
						System.out.println("---结束了:" + id);
						break;
					}
					try {
						System.out.println("累了 让我休息会儿--id:" + id);
						Thread.sleep(500);// 休息会儿
					} catch (InterruptedException e) {
					}
				}
			}
		});
		t.start();
		return new ReMsg(ReCode.OK);
	}

	void updateFailUser(Map<String, Object> reg) {
		if (reg != null) {
			reg = (Map) reg.get("uid_fail");
			List<String> users = (List<String>) reg.get("string");
			if (null != users && users.size() > 0) {
				List<Long> uids = new ArrayList<>();
				for (String _id : users) {
					uids.add(Long.parseLong(_id));
				}
				DBObject q = new BasicDBObject("_id", new BasicDBObject("$in", uids));
				DBObject u = new BasicDBObject("chatReg", false);
				userService.update(q, u);
			}
		}
	}

	void updateSuccessUser(Map<String, Object> reg) {
		if (reg != null) {
			reg = (Map) reg.get("uid_succ");
			List<String> users = (List<String>) reg.get("string");
			if (null != users && users.size() > 0) {
				List<Long> uids = new ArrayList<>();
				for (String _id : users) {
					uids.add(Long.parseLong(_id));
				}
				DBObject q = new BasicDBObject("_id", new BasicDBObject("$in", uids));
				DBObject u = new BasicDBObject("chatReg", true);
				userService.update(q, u);
			}
		}
	}
}
