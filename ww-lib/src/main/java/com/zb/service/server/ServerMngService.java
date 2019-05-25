package com.zb.service.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.IpUtil;
import com.zb.common.utils.RegexUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.res.Airport;
import com.zb.models.server.ServerInfo;
import com.zb.models.server.ServerInfo.ServerType;
import com.zb.models.server.ServerInfoLog;
import com.zb.service.BaseService;

@Service
public class ServerMngService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(ServerMngService.class);

	public DBObject findById(Long id) {
		return getC(DocName.SERVER_INFO).findOne(new BasicDBObject("_id", id));
	}

	public Page<DBObject> query(String type, Long updateTime, Integer status, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(type, updateTime, status, page, size);
		int count = count(type, updateTime, status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int count(String type, Long updateTime, Integer status) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(type)) {
			q.put("type", type);
		}
		if (updateTime != null && updateTime > 0) {
			q.put("updateTime", new BasicDBObject("$gt", updateTime));
		}
		if (status != null && status > 0) {
			q.put("status", status);
		}
		return getC(DocName.SERVER_INFO).find(q).count();
	}

	public List<DBObject> find(String type, Long updateTime, Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(type)) {
			q.put("type", type);
		}
		if (updateTime != null && updateTime > 0) {
			q.put("updateTime", new BasicDBObject("$gt", updateTime));
		}
		if (status != null && status > 0) {
			q.put("status", status);
		}
		return getC(DocName.SERVER_INFO).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("availableCnt", -1)).toArray();
	}

	Map<String, Long> times = new HashMap<String, Long>();

	public ReMsg save(String host, int port, String type, int connectCnt, int maxCnt, int status) {
		host = host.trim();
		if (RegexUtil.isIp(host)) {
			ServerInfo si = new ServerInfo(host.trim(), port, type, connectCnt, maxCnt, status);
			getMongoTemplate().save(si);
			long curTime = System.currentTimeMillis();
			if (times.get(si.get_id()) == null || curTime - times.get(si.get_id()) > Const.MINUTE * 5) {
				times.put(si.get_id(), curTime);
				getMongoTemplate().save(new ServerInfoLog(super.getNextId(DocName.SERVER_INFO_LOG), si));
			}
			return new ReMsg(ReCode.OK, si);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public List<String> getOkServer(ServerType st, int size) {
		long updateTime = System.currentTimeMillis() - 30 * Const.SECOND;
		List<DBObject> dbos = this.find(st.name(), updateTime, Const.STATUS_OK, 1, size);
		List<String> ss = new ArrayList<String>();
		for (DBObject dbo : dbos) {
			ss.add(DboUtil.getString(dbo, "_id"));
		}
		return ss;
	}

}
