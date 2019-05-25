package com.we.socket.store;

import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author cyan 系统
 * */
public class DBInit {

	private static MongoTemplate mainDb;
	//private static MongoTemplate chatDb;

	 public static MongoTemplate getMainDb() {
	 return mainDb;
	 }
	
	 public static void setMainDb(MongoTemplate mainDb) {
	 DBInit.mainDb = mainDb;
	 }

//	public static MongoTemplate getChatDb() {
//		return chatDb;
//	}
//
//	public static void setChatDb(MongoTemplate chatDb) {
//		DBInit.chatDb = chatDb;
//	}

	public static void init(ApplicationContext ctx) {
		 DBInit.mainDb = (MongoTemplate) ctx.getBean("mongoTemplate");
		//DBInit.chatDb = (MongoTemplate) ctx.getBean("chatMongoTemplate");
	}

	private static final String SEQ = "seq";
	private static final DBObject seqField = new BasicDBObject(SEQ,
			Integer.valueOf(1));
	private static final DBObject incSeq = new BasicDBObject("$inc",
			new BasicDBObject(SEQ, Long.valueOf(1)));

	public static Long getNextId(MongoTemplate mt, String name) {
		return (Long) mt
				.getCollection("counters")
				.findAndModify(new BasicDBObject("_id", name), seqField, null,
						false, incSeq, true, true).get(SEQ);
	}
}
