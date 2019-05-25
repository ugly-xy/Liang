package com.zb.service.usertask;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.LoginType;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.DateUtil;
import com.zb.core.Page;
import com.zb.models.DocName;
import com.zb.models.user.MonitorLog;
import com.zb.models.user.UserMonitor;
import com.zb.service.BaseService;
import com.zb.service.LoginLogService;

@Service
public class UserMonitorService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(UserMonitorService.class);

	@Autowired
	LoginLogService loginLogService;

	public DBObject findById(Long id) {
		return super.findById(DocName.USER_MONITOR, id);
	}

	public UserMonitor getUserMonitor(Long id) {
		DBObject dbo = findById(id);
		return DboUtil.toBean(dbo, UserMonitor.class);
	}

	public Page<DBObject> query(Long uid, Integer weight, Integer status, Date date, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);

		List<DBObject> dbos = find(uid, weight, status, date, page, size);
		Integer count = count(uid, weight, status, date);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> find(Long uid, Integer weight, Integer status, Date datetime, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != weight && weight != 0) {
			q.put("weight", weight);
		}
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (null != uid && uid != 0) {
			q.put("_id", uid);
		}
		if (datetime != null) {
			q.put("rechargeTime", new BasicDBObject("$gt", datetime.getTime()));
		}
		return getC(DocName.USER_MONITOR).find(q).sort(new BasicDBObject("weight", -1).append("rechargeTime", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
	}

	public Integer count(Long uid, Integer weight, Integer status, Date datetime) {
		DBObject q = new BasicDBObject();
		if (null != weight && weight != 0) {
			q.put("weight", weight);
		}
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (null != uid && uid != 0) {
			q.put("_id", uid);
		}
		if (datetime != null) {
			q.put("rechargeTime", new BasicDBObject("$gt", datetime.getTime()));
		}
		return getC(DocName.USER_MONITOR).find(q).count();
	}

	public UserMonitor upSet(long uid, String devId, long loginTime, String ip, String lbs, int loginType) {
		UserMonitor um = getUserMonitor(uid);
		if (um == null) {
			um = new UserMonitor(uid, devId, loginTime, ip, lbs, loginType);
		} else {
			MonitorLog ml = um.monitorLog(devId);// 获取当前devId是否登录过
			um.putMonitorLog(devId, loginTime, ip, lbs, loginType);
			if (ml == null) {// 没有登录过，新设备登录
				um.setWeight(um.getWeight() + 2);
			} else {
				if (ml.getLoginCnt() > 4 || (ml.getLoginCnt() > 2 && ml.getRechargeCnt() != null)) {
					int w = um.getWeight();
					if (w > UserMonitor.W_OK) {
						um.setWeight(w - 2);
					}
				}
			}
		}
		um.setUpdateTime(System.currentTimeMillis());
		super.getMongoTemplate().save(um);
		return um;
	}

	public UserMonitor upSet(long uid, long orderTime, int amount) {
		List<DBObject> dbos = loginLogService.findByLoginLogId(null, null, null, uid, 0, 1);
		if (dbos != null) {
			DBObject dbo = dbos.get(0);
			String devId = DboUtil.getString(dbo, "devId");
			UserMonitor um = getUserMonitor(uid);
			if (um == null) {
				um = new UserMonitor(uid, devId, DboUtil.getLong(dbo, "updateTime"), DboUtil.getString(dbo, "ip"),
						DboUtil.getString(dbo, "lbs"), DboUtil.getInt(dbo, "loginType"));
			}
			MonitorLog ml = um.monitorLog(devId);
			Long lastTime = ml.getRechargeTime();
			if (lastTime == null) {
				lastTime = 0L;
			}
			um.putMonitorLog(devId, amount, orderTime);
			int devCnt = um.getMonitorLogs().size();
			if (devCnt > 1) {// 多个设备
				MonitorLog ml2 = um.monitorLog(devId);
				if (ml2.getLoginType() == LoginType.DEF.getLoginType()) {// 用手机号登录
					um.setWeight(um.getWeight() + 4 + devCnt * 2);
				} else {
					if (ml2.getLoginCnt() < 3) {// 当前设备登录一次
						if (ml2.getRechargeCnt() == 1) {// 登录一次第一次充值
							if (orderTime - ml2.getLoginCnt() < 10 * Const.MINUTE) {
								um.setWeight(um.getWeight() + 4 + devCnt * 2);
							} else {
								um.setWeight(um.getWeight() + 4);
							}
						} else {// 登录一次多次充值
							if (orderTime - lastTime < 10 * Const.MINUTE) {
								um.setWeight(um.getWeight() + 2);
							}
						}
					} else {// 同一设备2次以上登录
						int w = um.getWeight();
						if (ml2.getRechargeCnt() == 1 && orderTime - ml2.getLoginCnt() < 10 * Const.MINUTE) {// 登录一次第一次充值
							if (w > UserMonitor.W_OK) {
								um.setWeight(um.getWeight() - 2);
							}
						} else {
							if (orderTime - lastTime < 10 * Const.MINUTE) {
								um.setWeight(UserMonitor.W_OK);
							}
						}
					}
				}
			}
			um.setUpdateTime(System.currentTimeMillis());
			um.setRechargeTime(orderTime);
			super.getMongoTemplate().save(um);
			return um;
		}
		return null;
	}

}