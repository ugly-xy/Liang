package com.we.service.article;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.Constant.ReCode;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.models.DocName;
import com.we.models.article.Group;
import com.we.service.BaseService;

@Service
public class ArticleGroupService extends BaseService {

	static final Logger LOGGER = LoggerFactory.getLogger(ArticleGroupService.class);

	public DBObject findById(Long id) {
		return getC(DocName.GROUP).findOne(new BasicDBObject("_id", id));
	}

	public List<DBObject> findGroups(Integer status, Integer page, Integer size) {
		DBObject dbo = new BasicDBObject();
		if (status != null && status != 0) {
			dbo.put("status", status);
		}
		return getC(DocName.GROUP).find(dbo).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", 1)).toArray();
	}

	/** 查询可用圈子 升序排列 */
	public Page<DBObject> queryGroups(Integer status, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findGroups(status, page, size);
		int count = countGroups(status);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countGroups(Integer status) {
		DBObject dbo = new BasicDBObject();
		if (status != null && status != 0) {
			dbo.put("status", status);
		}
		return getC(DocName.GROUP).find(dbo).count();
	}

	public ReMsg upsert(Long id, String name, String pic, Integer status, Integer sort, Integer type,String branchPic,String tagPic,String buttonPic) {
		Long uid = super.getUid();
		if (id == null || id < Group.SYSTEM_GROUP) {
			return new ReMsg(ReCode.PARAMETER_ERR);
//			Group group = new Group(id, name, null, pic, uid, null, status, sort, uid, 0, 0, 0, 0L, null, type,branchPic,tagPic);
//			getMongoTemplate().save(group);
//			return new ReMsg(ReCode.OK);
//		} else {
//			DBObject dbo = new BasicDBObject("adminId", uid).append("updateTime", System.currentTimeMillis());
//			if (StringUtils.isNotBlank(name)) {
//				dbo.put("name", name);
//			}
//			if (StringUtils.isNotBlank(pic)) {
//				dbo.put("cover", pic);
//			}
//			if (0 != uid) {
//				dbo.put("createUid", uid);
//			}
//			if (status != null && status != 0) {
//				dbo.put("status", status);
//			}
//			if (sort != null && sort != 0) {
//				dbo.put("sort", sort);
//			}
//			if (type != null && type != 0) {
//				dbo.put("type", type);
//			}
//			if (StringUtils.isNotBlank(branchPic)) {
//				dbo.put("branchPic", branchPic);
//			}
//			if (StringUtils.isNotBlank(tagPic)) {
//				dbo.put("tagPic", tagPic);
//			}
//			super.getC(DocName.GROUP).update(new BasicDBObject("_id", id), new BasicDBObject("$set", dbo));
//			return new ReMsg(ReCode.OK);
		}
		Group group = new Group(id, name, null, pic, uid, null, status, sort, uid, 0, 0, 0, 0L, null, type,branchPic,tagPic,buttonPic);
		getMongoTemplate().save(group);
		return new ReMsg(ReCode.OK);
	};

}
