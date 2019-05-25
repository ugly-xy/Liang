package com.zb.service;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.Notification;

@Service
public class NotificationService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(NotificationService.class);

	public DBObject findById(long uid) {
		DBObject dbo = super.findById(DocName.NOTIFYCATION, uid);
		if (dbo == null) {
			Notification notification = new Notification();
			notification.set_id(uid);
			notification.setUpdateTime(System.currentTimeMillis());
			super.getMongoTemplate().save(notification);
			dbo = DboUtil.beanToDBO(notification);
		}
		return dbo;
	}

	public ReMsg update(Boolean disturb, Boolean message, Integer st, Integer et) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		if (disturb != null) {
			u.put("disturb", disturb);
		}
		if (message != null) {
			u.put("message", message);
		}

		if (st != null && et != null) {
			u.put("st", st);
			u.put("et", et);
		}
		return update(uid, u);
	}

	public ReMsg update(Long uid, DBObject u) {
		getC(DocName.NOTIFYCATION).update(new BasicDBObject("_id", uid), new BasicDBObject("$set", u));
		return new ReMsg(ReCode.OK);
	}

	public ReMsg queryNotification() {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return new ReMsg(ReCode.OK, findById(uid));
	}

	// FIXME 检测是否能够通知
	public boolean canPush(long uid) {
		DBObject dbo = findById(uid);
		if (!DboUtil.getBool(dbo, "message")) {// 不允许通知
			return false;
		}
		if (!DboUtil.getBool(dbo, "disturb")) {// 未开启免打扰
			return true;
		}
		// 免打扰开启 判断能否推送
		int st = DboUtil.getInt(dbo, "st"); // 22
		int et = DboUtil.getInt(dbo, "et"); // 10
		int i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);// 当前小时 16
		if (st > et) { // 22 10  夜间睡觉
			if (et <= i && i < st) { // 10-22可推
				return true;
			}
		}
		if (st < et) { // 10 22  白天睡觉 
			if (et <= i || i < st) { // 22点到十点
				return true;
			}
		}
		return false;
	}

	// public static void main(String[] args) {
	// int i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	// System.out.println(i);
	// }
}
