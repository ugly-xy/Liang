package com.zb.service.usertask;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.usertask.TaskModel;
import com.zb.service.BaseService;

@Service
public class TaskModelService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(TaskModelService.class);

	public DBObject findById(Long id) {
		return super.findById(DocName.TASK_MODEL, id);
	}

	public Page<DBObject> query(Integer tmType, Integer status, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(tmType, status, page, size);
		Integer count = count(tmType, status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> find(Integer tmType, Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != tmType && tmType != 0) {
			q.put("tmType", tmType);
		}
		if (null != status && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.TASK_MODEL).find(q).sort(new BasicDBObject("sort", 1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
	}

	public List<DBObject> find(Integer tmType,Integer end, Integer status) {
		DBObject q = new BasicDBObject();
		if (null != tmType && tmType != 0) {
			q.put("tmType", tmType);
		}
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (null != end ) {
			q.put("tmEndCondition", new BasicDBObject("$lte", end));
		}
		return getC(DocName.TASK_MODEL).find(q).sort(new BasicDBObject("sort", 1)).toArray();
	}

	public Integer count(Integer tmType, Integer status) {
		DBObject q = new BasicDBObject();
		if (null != tmType && tmType != 0) {
			q.put("tmType", tmType);
		}
		if (null != status && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.TASK_MODEL).find(q).count();
	}

	public ReMsg upSet(Long tmId, String tmPic, String tmTitle, String tmName, String tmReward, int tmType, int tmPlan,
			int tmEndCondition, int tmStatus, int status, String rewards, String op, Integer opid, Integer sort) {
		if (null == tmId || tmId < 1) {
			if (null == opid) {
				opid = 0;
			}
			tmId = super.getNextId(DocName.TASK_MODEL);
		}

		TaskModel model = new TaskModel(tmId, tmPic, tmTitle, tmName, tmReward, tmType, tmPlan, tmEndCondition,
				tmStatus, status, rewards, op, opid, sort);
		getMongoTemplate().save(model);
		return new ReMsg(ReCode.OK, model);

	}

	public ReMsg del(long tmId) {
		DBObject a = findById(tmId);
		if (null == a) {
			return new ReMsg(ReCode.FAIL);
		}

		DBObject uo = new BasicDBObject("$set", new BasicDBObject("status", -1));
		getC(DocName.TASK_MODEL).update(new BasicDBObject("_id", tmId), uo);
		return new ReMsg(ReCode.OK);

	}

}
