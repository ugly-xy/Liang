package com.we.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.Constant.Const;
import com.we.common.Constant.ReCode;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.models.ApplyForTask;
import com.we.models.DocName;

@Service
public class ApplyForTaskService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(ApplyForTaskService.class);

	/** 提交任务申请信息 */
	public ReMsg saveApplyFortask(long uid, String name, String phone, String weChat, String email, String taskDetail,
			long taskBudget, String type) {
		ApplyForTask applyForTask = new ApplyForTask(uid, phone, name, weChat, email, taskDetail, taskBudget,
				Const.STATUS_DEF, type);
		long time = System.currentTimeMillis();
		applyForTask.setCreateTime(time);
		applyForTask.setUpdateTime(time);
		applyForTask.set_id(super.getNextId(DocName.APPLY_FOR_TASK));
		super.getMongoTemplate().save(applyForTask);
		return new ReMsg(ReCode.OK);
	}

	public DBObject findById(Long _id) {
		return getC(DocName.APPLY_FOR_TASK).findOne(new BasicDBObject("_id", _id));
	}

	public Page<DBObject> query(Integer status, Long uid, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(status, uid, page, size);
		int count = count(status, uid);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int count(Integer status, Long uid) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}
		return getC(DocName.APPLY_FOR_TASK).find(q).count();
	}

	public List<DBObject> find(Integer status, Long uid, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}
		return getC(DocName.APPLY_FOR_TASK).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("createTime", -1)).toArray();
	}

	/** 后台审核 改状态 */
	public ReMsg status(final Long _id, final Integer status, long adminId) {
		super.getC(DocName.APPLY_FOR_TASK).update(new BasicDBObject("_id", _id),
				new BasicDBObject("$set", new BasicDBObject("status", status).append("adminId", adminId)
						.append("updateTime", System.currentTimeMillis())));
		return new ReMsg(ReCode.OK);
	}

}
