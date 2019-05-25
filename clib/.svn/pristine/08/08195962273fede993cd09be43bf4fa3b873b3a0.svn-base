//package com.we.service.article;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import org.aspectj.asm.internal.Relationship;
//import org.springframework.stereotype.Service;
//
//import com.mongodb.BasicDBObject;
//import com.mongodb.DBObject;
//import com.we.common.Constant.ReCode;
//import com.we.core.web.ReMsg;
//import com.we.models.DocName;
//import com.we.service.BaseService;
//
//@Service
//public class ArticleAtUsersService extends BaseService {
//
//	public ReMsg queryAtUsers(long uid) {
//		BasicDBObject match = new BasicDBObject("$match", new BasicDBObject("primUid", uid));
//		BasicDBObject group = new BasicDBObject("$group", new BasicDBObject("_id", "atUid"));
//		BasicDBObject limit = new BasicDBObject("$limit", 5);
//		BasicDBObject sort = new BasicDBObject("updateTime", -1);
//		List<DBObject> list = new ArrayList<>();
//		list.add(match);
//		list.add(group);
//		list.add(limit);
//		list.add(sort);
//		Iterable<DBObject> itb = super.getC(DocName.ARTICLE).aggregate(list).results();
//		list.clear();
//		Iterator<DBObject> it = itb.iterator();
//		while (it.hasNext()) {
//			DBObject e = it.next();
//			list.add(e);
//		}
//		if (list.size() < 5) {
//			List<DBObject> friends = super.getC(DocName.RELATIONSHIP).find(new BasicDBObject("myId", uid).append("r", Relationship.FRIENDS))
//					.sort(new BasicDBObject("updateTime", -1)).toArray();
//			list.addAll(friends);
//		}
//		return new ReMsg(ReCode.OK, list);
//	}
//	
//	//public  ReMsg queryAtUsers(long uid) {
//
//}
