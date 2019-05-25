package com.zb.common.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public enum Op {
	LT("$lt"), GT("$gt"), LTE("$lte"), GTE("$gte"), IN("$in"), EXISTS("$exists");
	private String op;

	private Op(String op) {
		this.op = op;
	}

	// public String getOp() {
	// return op;
	// }

	public DBObject getOp(DBObject dbo, String field, Object value) {
		if (dbo != null && field != null && value != null) {
			dbo.put(field, new BasicDBObject(op, value));
		}
		return dbo;
	}

}
