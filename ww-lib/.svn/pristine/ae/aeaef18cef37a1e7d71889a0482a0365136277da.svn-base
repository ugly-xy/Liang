package com.zb.service.usertask;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.finance.CoinLog;
import com.zb.models.usertask.OneTask;
import com.zb.models.usertask.OneTaskLog;
import com.zb.service.BaseService;
import com.zb.service.LoginLogService;
import com.zb.service.MessageService;
import com.zb.service.UserService;
import com.zb.service.pay.CoinService;

@Service
public class OneTaskService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(OneTaskService.class);
	@Autowired
	CoinService coinService;
	@Autowired
	UserService userService;
	@Autowired
	MessageService messageService;
	@Autowired
	LoginLogService loginLogService;

	public DBObject findById(Long id) {
		return super.findById(DocName.ONE_TASK, id);
	}

	public ReMsg upsert(Long id, int type, String name, long st, long et, double version, int coin, int point,
			String prop, int status) {
		OneTask coinGiven = new OneTask(name, type, status, st, et, version, coin, point, prop);
		if (null == id) {
			coinGiven.set_id(super.getNextId(DocName.ONE_TASK));
		} else {
			coinGiven.set_id(id);
		}
		coinGiven.setUpdateTime(System.currentTimeMillis());
		super.getMongoTemplate().save(coinGiven);
		return new ReMsg(ReCode.OK);
	}

	public Page<DBObject> findCoinGiven(Integer status, Double version, Integer type, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		List<DBObject> dbos = this.query(status, version, type, page, size);
		int count = this.count(status, version, type);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> query(Integer status, Double version, Integer type, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (null != version) {
			q.put("version", version);
		}
		if (null != type) {
			q.put("type", type);
		}
		return getC(DocName.ONE_TASK).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public int count(Integer status, Double version, Integer type) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (null != version) {
			q.put("version", version);
		}
		if (null != type) {
			q.put("type", type);
		}
		return getC(DocName.ONE_TASK).find(q).count();
	}

	private List<DBObject> findCoinActive(Double version) {
		DBObject q = new BasicDBObject();
		q.put("st", new BasicDBObject("$lte", System.currentTimeMillis()));
		q.put("et", new BasicDBObject("$gte", System.currentTimeMillis()));
		q.put("status", Const.STATUS_OK);
		q.put("version", new BasicDBObject("$lte", version));
		return getC(DocName.ONE_TASK).find(q).sort(new BasicDBObject("_id", -1)).toArray();
	}

	public void checkCoinGiven(long uid, Double version) {
		if (null == version) {
			return;
		}
		List<DBObject> active = findCoinActive(version);
		if (null == active || active.size() == 0) {
			return;
		}
		Long coinGivenId = null;
		int coin = 0;
		int point = 0;
		String prop = "";
		String body = "";
		String name = "";
		Boolean up = null;// 是否是升级用户
		for (DBObject dbo : active) {
			Double taskVersion = DboUtil.getDouble(dbo, "version");
			// if (version < taskVersion) {// 版本小于条件版本 略过
			// continue;
			// }
			coinGivenId = DboUtil.getLong(dbo, "_id");
			if (isRecvCoin(coinGivenId, uid)) {// 未发送过奖励
				if (null != DboUtil.getInt(dbo, "type") && DboUtil.getInt(dbo, "type") == OneTask.UP) {// 如果是升级活动
					if (null == up) {// 查询isLogin
						up = checkLoginLog(taskVersion, uid);
					}
					if (!up) {// 如果不是升级上来的用户
						continue;
					}
				}
				saveCoinGivenLog(coinGivenId, uid);// 存储log
				coin = DboUtil.getInt(dbo, "coin");
				point = DboUtil.getInt(dbo, "point");
				prop = DboUtil.getString(dbo, "prop");
				name = DboUtil.getString(dbo, "name");
				// body = "恭喜完成" + name + "活动,系统赠送 ";
				if (coin > 0) {
					coinService.addCoin(uid, CoinLog.ONETASK, coinGivenId, coin, 0, "活动奖励");
					// body = body + coin + "金币";
				}
				if (point > 0) {
					userService.changePoint(uid, Point.COINGIVEN.getType(), point, 0L);
					// body = body + point + "经验";
				}
				// if (StringUtils.isNotBlank(prop)) {
				//
				// }

				messageService.imMsgHandler(Const.SYSTEM_ID, uid, name, null, null);
				// messageService.easeMsgHandler(Const.SYSTEM_ID, uid, body,
				// null, null);

			}

		}
	}

	private boolean isRecvCoin(long coinGivenId, long uid) {
		if (getC(DocName.ONE_TASK_LOG).find(new BasicDBObject("_id", coinGivenId + "-" + uid)).count() > 0) {
			return false;
		}
		return true;
	}

	private void saveCoinGivenLog(long coinGivenId, long uid) {
		OneTaskLog log = new OneTaskLog(coinGivenId, uid);
		super.getMongoTemplate().save(log);
	}

	/** 是否是升级版本 */
	private boolean checkLoginLog(double taskVersion, long uid) {
		List<DBObject> loginLog = loginLogService.findByLoginLogId(null, null, null, uid, 1, 10);
		Double ver = null;
		for (DBObject log : loginLog) {
			ver = DboUtil.getDouble(log, "version");
			if (null != ver && ver < taskVersion) {// 有历史版本小于活动版本 是升级的
				return true;
			}
		}
		return false;
	}

}
