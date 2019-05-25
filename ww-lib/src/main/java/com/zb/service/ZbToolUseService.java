package com.zb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;

@Service
public class ZbToolUseService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(ZbToolUseService.class);

	private static final String SEQ = "count";
	private static final DBObject seqField = new BasicDBObject(SEQ,
			Integer.valueOf(1));
	private static final DBObject incSeq = new BasicDBObject("$inc",
			new BasicDBObject(SEQ, Long.valueOf(1)));

	public ReMsg addUse(Long toolId) {
		Long count = (Long) super
				.getC(DocName.TOOL)
				// .getC(DocName.ZB_TOOL_USE_ST)
				.findAndModify(new BasicDBObject("_id", toolId), seqField,
						null, false, incSeq, true, true).get(SEQ);
		Long total = (Long) super
				.getC(DocName.ZB_TOOL_USE_ST)
				.findAndModify(new BasicDBObject("_id", 0), seqField, null,
						false, incSeq, true, true).get(SEQ);
		Map<String, Long> useC = new HashMap<String, Long>();
		useC.put("count", count);
		useC.put("total", total);
		return new ReMsg(ReCode.OK, useC);
	}

	public Long toolUseCount(Long toolId) {

		return DboUtil.getLong(super.findById(DocName.ZB_TOOL_USE_ST, toolId),
				SEQ);

	}

	public Page<DBObject> queryZbTools(Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findZbTool(page, size);
		int count = countZbTool();
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countZbTool() {
		return getC(DocName.ZB_TOOL_USE_ST).find().count();
	}

	public List<DBObject> findZbTool(Integer page, Integer size) {
		return getC(DocName.ZB_TOOL_USE_ST).find()
				.skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("count", -1)).toArray();
	}

}
