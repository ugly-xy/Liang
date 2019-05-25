package com.we.service.server;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.mongo.DboUtil;
import com.we.common.utils.DateUtil;
import com.we.models.DocName;
import com.we.service.BaseService;

@Service
public class BakService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(BakService.class);

	@Autowired
	private MongoTemplate bakMongoTemplate;

	public MongoTemplate getBakMongoTemplate() {
		return bakMongoTemplate;
	}

	public String bak(String docName, int days) {
		long st = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, days * -1);
		long lt = c.getTimeInMillis();
		DBObject q = new BasicDBObject("updateTime", new BasicDBObject("$lt", lt));
		int cnt = getC(docName).find(q).count();
		int total = cnt;
		Long id = 0L;
		int i =0;
		while (cnt > 0) {
			i++;
			List<DBObject> dbos = getC(docName).find(q).sort(new BasicDBObject("_id", 1)).limit(1000).toArray();
			if (dbos.size() > 0) {
				id = DboUtil.getLong(dbos.get(dbos.size() - 1), "_id");
				q.put("_id", new BasicDBObject("$gt", id));
				getBakMongoTemplate().getCollection(docName).insert(dbos);
				if (i % 20 == 0) {
					log.error("|||" + id );
					getC(docName).remove(new BasicDBObject("_id", new BasicDBObject("$lte", id)));
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cnt = cnt - dbos.size();
			} else {
				if (id > 0) {
					getC(docName).remove(new BasicDBObject("_id", new BasicDBObject("$lte", id)));
				}
				break;
			}
		}
		return (System.currentTimeMillis() - st) + "毫秒，记录：" + total + "条";
	}

	public String bakTask() {
		long st = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -3);
		int md = DateUtil.toDayyyyMMdd(c.getTime());
		DBObject q = new BasicDBObject("day", new BasicDBObject("$lt", md).append("$gt", 10000000));
		int cnt = getC(DocName.TASK).find(q).count();
		int total = cnt;
		String id = null;
		int i = 0;
		while (cnt > 0) {
			i++;
			List<DBObject> dbos = getC(DocName.TASK).find(q).sort(new BasicDBObject("_id", 1)).limit(1000).toArray();
			if (dbos.size() > 0) {
				id = DboUtil.getString(dbos.get(dbos.size() - 1), "_id");
				q.put("_id", new BasicDBObject("$gt", id));
				getBakMongoTemplate().getCollection(DocName.TASK).insert(dbos);
				if (i % 20 == 0) {
					log.error("|||" + id );
					getC(DocName.TASK).remove(new BasicDBObject("_id", new BasicDBObject("$lte", id)).append("day",
							new BasicDBObject("$lt", md).append("$gt", 10000000)));
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cnt = cnt - dbos.size();
			} else {
				if (id != null) {
					getC(DocName.TASK).remove(new BasicDBObject("_id", new BasicDBObject("$lte", id)).append("day",
							new BasicDBObject("$lt", md).append("$gt", 10000000)));
				}
				break;
			}

		}
		return (System.currentTimeMillis() - st) + "毫秒，记录：" + total + "条";
	}

	public String bakSt() {
		long st = System.currentTimeMillis();

		String[] stBak = { DocName.GIFT_RECV, DocName.GIFT_SEND, DocName.RECV_TOTAL, DocName.SEND_TOTAL,
				DocName.USER_GIFT };
		int[] types = { 1, 7, 30 };
		int total = 0;
		for (String doc : stBak) {
			Calendar c = Calendar.getInstance();
			for (int type : types) {
				int md = 0;
				if (type == 1) {
					c.add(Calendar.DATE, -31);
					md = DateUtil.toDayyyyMMdd(c.getTime());
				} else if (type == 7) {
					c.add(Calendar.DATE, -31);
					md = DateUtil.toYearWeekyyyyWeek(c);
				} else if (type == 30) {
					c.add(Calendar.DATE, -62);
					md = DateUtil.toYearMonthyyyyMM(c);
				}
				BasicDBObject q = new BasicDBObject("day", new BasicDBObject("$lte", md)).append("type", type);
				int cnt = getC(doc).find(q).count();
				total += cnt;
				int i = 0;
				String id = null;
				int curMd = 0;
				while (cnt > 0) {
					i++;
					List<DBObject> dbos = getC(doc).find(q).sort(new BasicDBObject("day", 1).append("_id", 1))
							.limit(1000).toArray();
					if (dbos.size() > 0) {
						id = DboUtil.getString(dbos.get(dbos.size() - 1), "_id");
						curMd = DboUtil.getInt(dbos.get(dbos.size() - 1), "day");

						q.put("_id", new BasicDBObject("$gt", id));

						getBakMongoTemplate().getCollection(doc).insert(dbos);
						if (i % 20 == 0) {
							log.error(doc + "|||" + id + "|||" + type + "|||" + curMd);
							getC(doc).remove(new BasicDBObject("day", new BasicDBObject("$lte", curMd))
									.append("type", type).append("_id", new BasicDBObject("$lte", id)));
						}
						cnt = cnt - dbos.size();
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						if (id != null && curMd > 0) {
							getC(doc).remove(new BasicDBObject("day", new BasicDBObject("$lte", curMd))
									.append("type", type));
						}
						break;
					}
				}
			}
		}
		return (System.currentTimeMillis() - st) + "毫秒，" + total + "条";
	}

	// public String bakStReSet() {
	// long st = System.currentTimeMillis();
	// Calendar c = Calendar.getInstance();
	// c.add(Calendar.DATE, -31);
	// int md = DateUtil.toDayyyyMMdd(c.getTime());
	// String[] stBak = { DocName.GIFT_RECV, DocName.GIFT_SEND,
	// DocName.RECV_TOTAL, DocName.SEND_TOTAL,
	// DocName.USER_GIFT };
	// int total = 0;
	// for (String doc : stBak) {
	// BasicDBObject q = new BasicDBObject("day", new BasicDBObject("$lt",
	// md)).append("type", 1);
	// int cnt = getC(doc).find(q).count();
	// total += cnt;
	// int i = 0;
	// while (cnt > 0) {
	// List<DBObject> dbos = getC(doc).find(q).sort(new BasicDBObject("_id",
	// 1)).limit(500).toArray();
	// String id = null;
	// int curDay = 0;
	// if (dbos.size() > 0) {
	// id = DboUtil.getString(dbos.get(dbos.size() - 1), "_id");
	// curDay = DboUtil.getInt(dbos.get(dbos.size() - 1), "day");
	// q.put("_id", new BasicDBObject("$gt",id));
	// log.error(doc + "|||" + id);
	// getBakMongoTemplate().getCollection(doc).insert(dbos);
	// getC(doc).remove(new BasicDBObject("day", new BasicDBObject("$lte",
	// curDay)).append("type", 1)
	// .append("_id", new BasicDBObject("$lte", id)));
	// cnt = cnt - dbos.size();
	// try {
	// Thread.sleep(50);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// } else {
	// if(id)
	// getC(doc).remove(new BasicDBObject("day", new BasicDBObject("$lte",
	// curDay)).append("type", 1)
	// .append("_id", new BasicDBObject("$lte", id)));
	// break;
	// }
	// }
	// }
	// return (System.currentTimeMillis() - st) + "毫秒，" + total + "条";
	// }
}
