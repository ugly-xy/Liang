package com.zb.service;

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
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.res.ContactsHead;

@Service
public class ContactHeadService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(ContactHeadService.class);

	public static final int PRI_PRIVATE = 2;
	public static final int PRI_PUBLIC = 1;
	public static final String[] CATE_NAME = { "男明星", "女明星", "体坛明星", "政要",
			"名企名人", "历史人物", "网红", "美女帅哥" };

	public static final String PRIVATE_LABEL = "我的";

	public DBObject findResById(Long id) {
		return getC(DocName.CONTACT_HEAD).findOne(new BasicDBObject("_id", id));
	}

	public List<DBObject> findContactsHead(Integer status, Integer pri,
			Long uid, String cateName, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}

		if (null != pri && pri != 0) {
			q.put("pri", pri);
		}

		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}

		if (StringUtils.isNotBlank(cateName)) {
			q.put("cateName", cateName);
		}

		return getC(DocName.CONTACT_HEAD).find(q)
				.skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", -1)).toArray();
	}

	public int countContactsHead(Integer status, Integer pri, Long uid,
			String cateName) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}

		if (null != pri && pri != 0) {
			q.put("pri", pri);
		}

		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}

		if (StringUtils.isNotBlank(cateName)) {
			q.put("cateName", cateName);
		}

		return getC(DocName.CONTACT_HEAD).find(q).count();
	}

	Map<String, Map<String, String>> publicConstant = new HashMap<String, Map<String, String>>();

	public ReMsg getContactsHead(String label) {
//		long uid = super.getUid();
//		if (uid < 1) {
//			return new ReMsg(ReCode.NOT_AUTHORIZED);
//		}
		List<DBObject> chs = null;
		if (PRIVATE_LABEL.equals(label)) {
			chs = findContactsHead(Const.STATUS_UP, null, 0l, null, 0, 200);
		} else {
			chs = findContactsHead(Const.STATUS_UP, PRI_PUBLIC, null, label, 0,
					200);
		}
		return new ReMsg(ReCode.OK, chs);
	}

	public Page<DBObject> queryContactHead(Integer status, Long uid,
			Integer pri, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findContactsHead(status, pri, uid, null,
				page, size);
		int count = countContactsHead(status, pri, uid, null);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public ReMsg saveContactHead(String name, String headPic) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return saveContactHead(null, name, Const.STATUS_UP, null, headPic,
				null, uid, null);
	}

	public ReMsg saveContactHead(Long id, String name, Integer status,
			Integer sort, String headPic, Integer pri, Long uid, String cateName) {
		boolean cateNameOk = false;
		if (null != uid && uid > 0) {
			cateName = PRIVATE_LABEL;
			cateNameOk = true;
			pri = PRI_PRIVATE;
		} else {
			pri = PRI_PUBLIC;
			for (String cn : CATE_NAME) {
				if (cn.equals(cateName)) {
					cateNameOk = true;
					break;
				}
			}
		}
		if (!cateNameOk) {
			return new ReMsg(ReCode.CONTACT_HEAD_CATE_NAME_ERR);
		}
		if (id == null || id == 0) {
			id = super.getNextId(DocName.CONTACT_HEAD);
		}
		if (sort == null) {
			sort = id.intValue();
		}
		ContactsHead zbt = new ContactsHead(id, name, status, sort, headPic,
				pri, uid, cateName);
		getMongoTemplate().save(zbt);
		return new ReMsg(ReCode.OK, zbt);
	}

	public ReMsg delContactHead(Long id) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject q = new BasicDBObject("_id", id);
		q.put("uid", uid);
		getC(DocName.CONTACT_HEAD).update(
				q,
				new BasicDBObject("$set", new BasicDBObject("status",
						Const.STATUS_DEF)));
		return new ReMsg(ReCode.OK);
	}

}
