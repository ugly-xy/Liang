package com.we.service.article;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.Constant.ReCode;
import com.we.common.mongo.DboUtil;
import com.we.core.web.ReMsg;
import com.we.models.DocName;
import com.we.models.article.Article;
import com.we.models.article.ArticleSiftRead;
import com.we.service.BaseService;
import com.we.service.UserService;

@Service

public class ArticleSiftReadService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(ArticleSiftReadService.class);
	@Autowired
	ArticleService articleService;
	@Autowired
	UserService userService;
	@Autowired
	ArticleGroupService groupService;

	// 查询精选文章(分页)
	public ReMsg querySiftArticle( Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		DBObject q = articleService.getQuery(null, null, null, Article.OK, 0, 0, System.currentTimeMillis(), null, null,
				null,Article.PRI_ALL);
		page = super.getPage(page);
		size = super.getSize(size);
		long time=System.currentTimeMillis();

		ArticleSiftRead read = querySiftRead(uid);
		List<DBObject> dbos = new ArrayList<DBObject>();
		// 请求的不是第一页 是老数据
		if (page != 1) {
			dbos = getDbosHistory(read, q, page, size);
			return new ReMsg(ReCode.OK, setInfo(dbos));
		}
		dbos = findArticle(q, time, page, size);
		if (read.getRecord().size() == 0) {
			Long[] rs = new Long[2];
			rs[0] = DboUtil.getLong(dbos.get(0), "publishTime");
			rs[1] = DboUtil.getLong(dbos.get(dbos.size() - 1), "publishTime");
			read.getRecord().add(rs);
			save(read);
			return new ReMsg(ReCode.OK, setInfo(dbos));
		}
		// 递归取出记录中的数据
		int i = 0;
		try {
			dbos = getDbos(dbos, read, q, page, size, i);
			return new ReMsg(ReCode.OK, setInfo(dbos));
		} catch (Exception e) {
			log.error("ArticleSiftReadService  --- select error");
			return new ReMsg(ReCode.OK, setInfo(dbos));
		}
	}

	public List<DBObject> getDbosHistory(ArticleSiftRead read, DBObject q, Integer page, Integer size) {
		int start = super.getStart(page, size);
		long count = 0;
		long beginId = 0;
		// 从i开始 推 size
		List<Long[]> list = read.getRecord();
		if (null == list || list.size() == 0) {
			long time=System.currentTimeMillis();
			return findArticle(q, time, page, size);
		}
		Long begin = list.get(0)[0];
		return findArticle(q, begin, page, size);
	}

	// 递归查询 直到dbos填充完毕数据
	public List<DBObject> getDbos(List<DBObject> list, ArticleSiftRead read, DBObject q, Integer page, Integer size,
			Integer i) {
		if (i > 0) {
			i = 1;
		}
		// 新的开始结束
		DBObject new_begin = list.get(0);
		DBObject new_end = list.get(list.size() - 1);
		// 记录 至少size=1
		List<Long[]> record = read.getRecord();
		// 如果新的结束大于size的开始
		if (DboUtil.getLong(new_end, "publishTime") > record.get(i)[0]) {
			Long[] read2 = new Long[2];
			read2[0] = DboUtil.getLong(new_begin, "publishTime");
			read2[1] = DboUtil.getLong(new_end, "publishTime");
			if (i == 0) {
				read.getRecord().add(i, read2);
			}
			if (i > 0) {
				read.getRecord().get(0)[1] = DboUtil.getLong(new_end, "publishTime");
			}
			save(read);
			return list;
		}
		// 如果新的结束小于等于size的开始
		if (DboUtil.getLong(new_end, "publishTime") <= record.get(i)[0]) {
			// 删除重复元素
			List<DBObject> laji = new ArrayList<DBObject>();
			Long begin = DboUtil.getLong(list.get(0), "publishTime");
			Long end = DboUtil.getLong(list.get(list.size() - 1), "publishTime");
			for (DBObject dbo : list) {
				// 如果publishTime在重合区间之内 删除
				if (DboUtil.getLong(dbo, "publishTime") <= record.get(i)[0]
						&& DboUtil.getLong(dbo, "publishTime") >= record.get(i)[1]) {
					laji.add(dbo);
				}
			}
			for (DBObject o : laji) {
				list.remove(o);
			}
			// 合并记录 把新的开始赋值给记录
			if (i == 0) {
				if (begin >= record.get(i)[0]) {
					record.get(i)[0] = begin;
				}
				if (end <= record.get(i)[1]) {
					record.get(i)[1] = end;
				}
			}
			if (i > 0) {
				record.get(i)[0] = record.get(0)[0];
				record.remove(0);
			}
			if (list.size() == size) {
				save(read);
				return list;
			}
			// 往下直接查
			List<DBObject> list2 = findArticle(q, record.get(0)[1] - 1, page, size - list.size());
			list.addAll(list2);
			if (record.size() == 1) {
				if (list.size() != 0) {
					record.get(0)[1] = DboUtil.getLong(list.get(list.size() - 1), "publishTime");
				}
				save(read);
				return list;
			}
			// 如果往下不止一页
			list = getDbos(list, read, q, page, size, i + 1);
		}
		return list;
	}

	// 查询用户的siftReadLog
	private ArticleSiftRead querySiftRead(long uid) {
		DBObject q = new BasicDBObject();
		if (uid != 0) {
			q.put("uid", uid);
		}
		ArticleSiftRead read = new ArticleSiftRead();
		DBObject object = getC(DocName.ARTICLE_SIFT_READ).findOne(q);
		if (null == object) {
			read = new ArticleSiftRead(super.getNextId(DocName.ARTICLE_SIFT_READ), uid);
		} else {
			read = DboUtil.toBean(object, ArticleSiftRead.class);
		}
		return read;
	}

	// save
	private void save(ArticleSiftRead a) {
		if (a.getRecord().size() > 1) {
			if (a.getRecord().get(0)[1] <= a.getRecord().get(1)[0]) {
				a.getRecord().get(1)[0] = a.getRecord().get(0)[0];
				a.getRecord().remove(0);
			}
		}
		a.setUpdateTime(System.currentTimeMillis());
		getMongoTemplate().save(a);
	}

	public List<DBObject> findArticle(DBObject q, long time, Integer page, Integer size) {
		// 取小于当前时间的
		if (time > 0) {
			q.put("publishTime", new BasicDBObject("$lt", time));
		}
		List<DBObject> dbos = getC(DocName.ARTICLE).find(q).sort(new BasicDBObject("publishTime", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
		return dbos;
	}

//	private long findUpId(DBObject q) {
//		List<DBObject> dbos = getC(DocName.ARTICLE).find(q).sort(new BasicDBObject("_id", -1)).skip(getStart(1, 1))
//				.limit(getSize(1)).toArray();
//		return DboUtil.getLong(dbos.get(0), "_id");
//	}

	private List<DBObject> setInfo(List<DBObject> list) {
		if (null == list || list.size() == 0) {
			return list;
		}
		List<DBObject> rs = new ArrayList<DBObject>();
		for (DBObject dbo : list) {
			dbo = articleService.addUserInfo(false, dbo, true);
			// 添加文章标签图
			DBObject group = groupService.findById(DboUtil.getLong(dbo, "groupId"));
			if (null != group) {
				dbo.put("tagPic", DboUtil.getString(group, "tagPic"));
			}
			rs.add(dbo);
		}
		return rs;
	}
}
