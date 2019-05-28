package com.zb.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.paper.Paper;
import com.zb.models.paper.Question;
import com.zb.models.paper.Result;
@Service
public class PaperService extends BaseService{
	
	static final Logger log = LoggerFactory.getLogger(PaperService.class);
	
	public DBObject findById(Long id) {
		
		DBObject dbo = super.findById(DocName.PAPER, id);
		if(dbo!=null){
			DBObject q = new BasicDBObject();
			if(dbo.get("count")!=null){
				q.put("count", Integer.parseInt(String.valueOf(dbo.get("count")))+1);
			}
			if(dbo.get("type")!=null){
				q.put("type", dbo.get("type"));
			}
			if(dbo.get("title")!=null){
				q.put("title", dbo.get("title"));
			}
			if(dbo.get("introduce")!=null){
				q.put("introduce", dbo.get("introduce"));
			}
			if(dbo.get("logo")!=null){
				q.put("logo", dbo.get("logo"));
			}
			if(dbo.get("backPic")!=null){
				q.put("backPic", dbo.get("backPic"));
			}
			if(dbo.get("content")!=null){
				q.put("content", dbo.get("content"));
			}
			if(dbo.get("pay")!=null){
				q.put("pay", dbo.get("pay"));
			}
			if(dbo.get("sort")!=null){
				q.put("sort", dbo.get("sort"));
			}
			if(dbo.get("status")!=null){
				q.put("status", dbo.get("status"));
			}
			if(dbo.get("questions")!=null){
				q.put("questions", dbo.get("questions"));
			}
			if(dbo.get("result")!=null){
				q.put("result", dbo.get("result"));
			}
			
			getC(DocName.PAPER).update(new BasicDBObject("_id", id),
					new BasicDBObject("$set", q));
		}
		
		
		return super.findById(DocName.PAPER, id);
	}
	
	public ReMsg savePaper(Long id,int type,String title,String introduce,String logo,String backPic,String content,int count,int pay,double sort,int status,
			List<Question> questions,List<Result> result){
		if(id ==null){
			id = super.getNextId(DocName.PAPER);
			Paper p = new Paper(id,type,title,introduce,logo,backPic,content,count,pay,sort,status,questions,result);
			getMongoTemplate().save(p);
			return new ReMsg(ReCode.OK, p);
		}
		Paper p = new Paper(id,type,title,introduce,logo,backPic,content,count,pay,sort,status,questions,result);
		getMongoTemplate().save(p);
		return new ReMsg(ReCode.OK, p);
	}
	
	public Page<DBObject> queryPaper(Long id,Integer type,Integer pay,Integer status,
			Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> os = find(id,type,pay,status, page, size);
		int count = count(id,type,pay,status);
		return new Page<DBObject>(count, size, page, os);
	}
	
	//paper
	public List<DBObject> find(Long id,Integer type,  Integer pay,Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		
		if (id != null && id != 0) {
			q.put("_id", id);
		}
		if(type!=null && type!=0){
			q.put("type", type);
		}
		if (pay != null && pay != 0) {
			q.put("pay", pay);
		}
		
		if (status != null && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.PAPER).find(q).sort(new BasicDBObject("sort", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
	}

	private int count(Long id,Integer type, Integer pay, Integer status) {
		DBObject q = new BasicDBObject();
		
		if (id != null && id != 0) {
			q.put("id", id);
		}
		if(type !=null && type !=0){
			q.put("type", type);
		}
		if (pay != null && pay != 0) {
			q.put("pay", pay);
		}
		
		if (status != null && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.PAPER).find(q).count();
	}
	
}
