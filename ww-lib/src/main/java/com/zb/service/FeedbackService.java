package com.zb.service;

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
import com.zb.common.Constant.ReCode;
import com.zb.common.http.UserAgent;
import com.zb.common.http.UserAgentUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.Feedback;

@Service
public class FeedbackService extends BaseService {

	@Autowired
	MessageService messageService;
	static final Logger log = LoggerFactory.getLogger(FeedbackService.class);

	public DBObject findById(Long id) {
		return super.findById(DocName.FEEDBACK, id);
	}

	public Page<DBObject> query(Integer status, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(status, page, size);
		Integer count = count(status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> find(Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.FEEDBACK).find(q).sort(new BasicDBObject("_id", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
	}

	public Integer count(Integer status) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}

		return getC(DocName.FEEDBACK).find(q).count();
	}

	public ReMsg update(Long id, String sysReply, Integer status) {
		DBObject a = findById(id);
		if (null == a) {
			return new ReMsg(ReCode.FAIL);
		}
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		if (status != 0) {
			u.put("status", status);
		}
		if (StringUtils.isNotBlank(sysReply)) {
			u.put("sysReply", sysReply);
		}
		getC(DocName.FEEDBACK).update(new BasicDBObject("_id", id), new BasicDBObject("$set", u));
		// 推送系统消息
		if (StringUtils.isNotBlank(sysReply)) {
			messageService.imMsgHandler(Const.SYSTEM_ID, DboUtil.getLong(a, "userId"),
					"您的反馈:\"" + DboUtil.getString(a, "content") + "\"" + "已进行处理,对于此问题客服回复:\"" + sysReply + "\",感谢您的支持!",
					null, null);
//			messageService.easeMsgHandler(Const.SYSTEM_ID, DboUtil.getLong(a, "userId"),
//					"您的反馈:\"" + DboUtil.getString(a, "content") + "\"" + "已进行处理,对于此问题客服回复:\"" + sysReply + "\",感谢您的支持!",
//					null, null);
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg save(String content, String contact, String baseEnv, String[] pics, Integer via, String devInfo,
			HttpServletRequest request) {
		Long userId = super.getUid();
		DBObject q = new BasicDBObject("userId", userId).append("createTime",
				new BasicDBObject("$gt", System.currentTimeMillis() - 300000));
		int c = getC(DocName.FEEDBACK).find(q).count();
		if (c > 0) {
			return new ReMsg(ReCode.FEEDBACK_ERR);
		}
		Long id = super.getNextId(DocName.FEEDBACK);
		Double ver = super.getVer(request);
		UserAgent userAgent = UserAgentUtil.getUserAgent(request);
		Feedback g = new Feedback(id, userId, content, contact, baseEnv, pics, ver, via, devInfo, userAgent);
		getMongoTemplate().save(g);
		return new ReMsg(ReCode.OK, g);
	}

}
