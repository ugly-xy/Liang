package com.zb.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.OperationType;
import com.zb.common.Constant.PushConst;
import com.zb.common.Constant.ReCode;
import com.zb.common.Constant.Via;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.core.Page;
import com.zb.core.push.GeTuiPush;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.PushLimit;
import com.zb.models.push.PushChannel;
import com.zb.models.push.PushTag;

@Service
public class PushService extends BaseService {
	@Autowired
	UserService userService;
	@Autowired
	NotificationService notificationService;
	static final Logger log = LoggerFactory.getLogger(PushService.class);

	public DBObject findPushTagById(String name) {
		return getC(DocName.PUSH_TAG).findOne(new BasicDBObject("_id", name));
	}

	// public DBObject findPushChannelById(int type, String channelId) {
	// return getC(DocName.PUSH_CHANNEL).findOne(
	// new BasicDBObject("_id", type + "_" + channelId));
	// }

	public DBObject findPushChannelById(Long id) {
		return getC(DocName.PUSH_CHANNEL).findOne(new BasicDBObject("_id", id));
	}

	public Page<DBObject> queryPushTags(Integer status, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findPushTag(status, page, size);
		int count = countPushTag(status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countPushTag(Integer status) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.PUSH_TAG).find(q).count();
	}

	public List<DBObject> findPushTag(Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.PUSH_TAG).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("createTime", -1)).toArray();
	}

	public Page<DBObject> queryPushChannel(Long userId, Integer status, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findPushChannel(userId, status, page, size);
		int count = countPushChannel(userId, status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countPushChannel(Long userId, Integer status) {
		DBObject q = new BasicDBObject();
		if (null != userId && userId != 0) {
			q.put("userId", userId);
		}
		if (null != status && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.PUSH_CHANNEL).find(q).count();
	}

	public List<DBObject> findPushChannel(Long userId, Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != userId && userId != 0) {
			q.put("userId", userId);
		}
		if (null != status && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.PUSH_CHANNEL).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("updateTime", -1)).toArray();
	}

	public ReMsg updatePushTag(PushTag zbTool) {
		getMongoTemplate().save(zbTool);
		return new ReMsg(ReCode.OK);
	}

	public ReMsg updatePushChannel(PushChannel zbToolCate) {
		getMongoTemplate().save(zbToolCate);
		return new ReMsg(ReCode.OK);
	}

	// public ReMsg savePushTag(String name, DevType devType)
	// throws PushClientException, PushServerException {
	// if (BdPush.createTag(name, devType)) {
	// PushTag zbt = new PushTag(name, devType.type());
	// getMongoTemplate().save(zbt);
	// return new ReMsg(ReCode.OK, zbt);
	// }
	// return new ReMsg(ReCode.FAIL);
	// }

	public ReMsg savePushChannel(String channelId, Integer channelType, String appCode, HttpServletRequest req)
			throws JsonParseException, JsonMappingException, IOException {
		Long userId = super.getUid();
		if (null == appCode) {
			appCode = Const.APP_CODE_BIBI;
		}
		if (userId > 0) {
			int devType = super.getVia(req);
			DBObject dbo = this.findPushChannelById(userId);
			if (dbo != null) {
				if (userId > 0) {
					PushChannel pc = DboUtil.toBean(dbo, PushChannel.class);
					pc.setDevType(devType);
					pc.setUpdateTime(System.currentTimeMillis());
					pc.setChannelId(channelId);
					pc.setAppCode(appCode);
					getMongoTemplate().save(pc);
				}
				return new ReMsg(ReCode.OK);
			} else {
				PushChannel zbtc = new PushChannel(userId, devType, channelId, channelType, appCode);
				getMongoTemplate().save(zbtc);

			}
		}
		return new ReMsg(ReCode.OK);
	}

	public PushChannel getPushChannel(Long uid) {
		List<DBObject> dbos = this.findPushChannel(uid, Const.STATUS_OK, 1, 1);
		if (dbos.size() > 0) {
			return DboUtil.toBean(dbos.get(0), PushChannel.class);
		}
		return null;
	}

	public ReMsg push(Long toUid, String title, String body, Map<String, String> json) {
//		if (!notificationService.canPush(toUid)) {
//			return new ReMsg(ReCode.OK);
//		}
		DBObject pc = this.findPushChannelById(toUid);
		String result = null;
		if (pc != null && DboUtil.getInt(pc, "channelType") == 10) {
			int devType = DboUtil.getInt(pc, "devType");
			String cid = DboUtil.getString(pc, "channelId");
			if (Via.IPhone.getVia() == devType || Via.IPad.getVia() == devType) {
				result = GeTuiPush.pushIosSingle(cid, title, body, json);
			} else {
				result = GeTuiPush.pushAndroidSingle(cid, 0, 3600 * 2, title, body, JSONUtil.beanToJson(json));
			}
		}
		return new ReMsg(ReCode.OK, result);
	}

	public ReMsg pushAll(int via, String title, String body, Map<String, String> json) {
		String result = null;
		if (via == Via.Android.getVia() || via == Via.AndPad.getVia()) {
			result = GeTuiPush.pushAllToAndroid(title, body, JSONUtil.beanToJson(json));
		} else if (via == Via.IPhone.getVia() || via == Via.IPad.getVia()) {
			result = GeTuiPush.pushAllToIos(title, body, JSONUtil.beanToJson(json));
		} else {
			result = GeTuiPush.pushAll(title, body, JSONUtil.beanToJson(json));
		}
		return new ReMsg(ReCode.OK, result);
	}

	// pushLimit
	public Integer push(long toUid, String title, String body, Map<String, String> opMap, PushConst pushConst,
			Integer limitType) {
		if (null == pushConst && null == limitType) {
			push(toUid, title, body, opMap);
			return PushConst.OK;
		}
		int eday = super.getC(DocName.PUSHLIMIT).find(new BasicDBObject().append("to", toUid).append("t", limitType)
				.append("dt", new BasicDBObject("$gt", System.currentTimeMillis() - Const.DAY))).count();
		if (eday < pushConst.getEday()) {
			int ehour = super.getC(DocName.PUSHLIMIT)
					.find(new BasicDBObject().append("to", toUid).append("t", limitType).append("dt",
							new BasicDBObject("$gt", System.currentTimeMillis() - Const.HOUR)))
					.count();
			if (ehour < pushConst.getEhour()) {
				PushLimit limit = new PushLimit(limitType, toUid, System.currentTimeMillis(), (int) super.getUid());
				limit.set_id(super.getNextId(DocName.PUSHLIMIT));
				getMongoTemplate().save(limit);
				push(toUid, title, body, opMap);
			} else {
				return PushConst.FAIL_HOUR;
			}
		} else {
			return PushConst.FAIL_DAY;
		}
		return PushConst.OK;
	}
}
