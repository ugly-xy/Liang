package com.zb.service;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.sys.AppShareLog;
import com.zb.models.usertask.Task;
import com.zb.service.usertask.UserTaskService;

@Service
public class AppShareService extends BaseService {
	@Autowired
	UserTaskService userTaskService;

	static final Logger log = LoggerFactory.getLogger(AppShareService.class);

	public ReMsg getShareCount() {
		Long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		int count = 0;
		DBObject dbo = super.findById(DocName.APP_SHARE, uid);
		if (dbo != null) {
			count = DboUtil.getInt(dbo, "count");
		}
		return new ReMsg(ReCode.OK, count);
	}

	public ReMsg save(Double ver, Integer via, HttpServletRequest request) {
		long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		Long id = super.getNextId(DocName.APP_SHARE_LOG);

		AppShareLog g = new AppShareLog(id, ver, userId, via);
		getMongoTemplate().save(g);
		DBObject uo = new BasicDBObject("$inc", new BasicDBObject("count",
				Integer.valueOf(1)));
		super.getC(DocName.APP_SHARE).update(
				new BasicDBObject("_id", userId), uo, true, false);
		userTaskService.doUserTask(userId, Task.SHARE_QQWECHAT, 1);
		return new ReMsg(ReCode.OK, g);
	}
}
