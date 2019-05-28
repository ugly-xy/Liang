package com.zb.service.usertask;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.mongo.DboUtil;
import com.zb.core.Page;
import com.zb.models.DocName;
import com.zb.models.usertask.Task;
import com.zb.service.BaseService;

@Service
public class TaskLogService extends BaseService {
	@Autowired
	UserTaskService userTaskService;
	static final Logger log = LoggerFactory.getLogger(TaskLogService.class);

	public Page<DBObject> findTaskLog(Long uid, Integer type, Integer page, Integer size, Integer st, Integer et) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findAll(uid, type, st, et, page, size);
		int count = countAll(uid, type, st, et);
		for (DBObject task : dbos) {
			// 统计普通任务
			List<DBObject> list = (List<DBObject>) task.get("tasks");
			int doing = 0;
			int end = 0;
			int received = 0;
			for (DBObject dbo : list) {
				Integer status = DboUtil.getInt(dbo, "tmStatus");
				if (status == Task.STATUS_DOING) {
					doing++;
				} else if (status == Task.STATUS_UNRECEIVE) {
					end++;
				} else if (status == Task.STATUS_RECEIVED) {
					received++;
				}
			}
			// 统计vip任务
			list = (List<DBObject>) task.get("vips");
			if (null != list && list.size() != 0) {
				for (DBObject dbo : list) {
					Integer status = DboUtil.getInt(dbo, "tmStatus");
					if (status == Task.STATUS_DOING) {
						doing++;
					} else if (status == Task.STATUS_UNRECEIVE) {
						end++;
					} else if (status == Task.STATUS_RECEIVED) {
						received++;
					}
				}
			}
			task.put("doing", doing);
			task.put("end", end);
			task.put("received", received);
		}
		return new Page<DBObject>(count, size, page, dbos);
	}

	public DBObject findTaskLog(String id) {
			return getC(DocName.TASK).findOne(new BasicDBObject("_id", id));
	}

	public List<DBObject> findAll(Long uid, Integer type, Integer st, Integer et, Integer page, Integer size) {
		DBObject q = getQuery(uid, type, st, et);
		return getC(DocName.TASK).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("updateTime", -1)).toArray();
	}

	public int countAll(Long uid, Integer type, Integer st, Integer et) {
		DBObject q = getQuery(uid, type, st, et);
		return getC(DocName.TASK).find(q).count();
	}

	public DBObject getQuery(Long uid, Integer type, Integer st, Integer et) {
		DBObject q = new BasicDBObject();
		if (null != uid && uid > 0) {
			q.put("uid", uid);
		}
		if (null != type) {
			if (type == Task.TYPE_NEW) {
				q.put("day", Task.getNewDay());
			} else if (type == Task.TYPE_DAILY) {
				if (st != null && st != 0 && et != null && et != 0) {
					q.put("day", new BasicDBObject("$gte", st).append("$lte", et).append("$ne", Task.getNewDay()));
				} else if (st != null && st != 0) {
					q.put("day", new BasicDBObject("$gte", st).append("$ne", Task.getNewDay()));
				} else if (et != null && et != 0) {
					q.put("day", new BasicDBObject("$lte", et).append("$ne", Task.getNewDay()));
				} else {
					q.put("day", new BasicDBObject("$ne", Task.getNewDay()));
				}
			} else {
				if (st != null && st != 0 && et != null && et != 0) {
					q.put("day", new BasicDBObject("$gte", st).append("$lte", et));
				} else if (st != null && st != 0) {
					q.put("day", new BasicDBObject("$gte", st));
				} else if (et != null && et != 0) {
					q.put("day", new BasicDBObject("$lte", et));
				}
			}
		}
		return q;
	}
}
