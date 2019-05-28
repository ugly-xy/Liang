package com.zb.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.finance.CoinLog;
import com.zb.models.user.UserTag;
import com.zb.models.user.UserTagItem;
import com.zb.service.pay.CoinService;

@Service
public class UserTagService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(UserTagService.class);
	@Autowired
	CoinService coinService;

	@Autowired
	EnvService envService;

	public static final int MAX_COUNT = 8;

	// 系统默认
	public String[] getTagsTemplate() {
		String t = envService.getString("user.tag.template");
		if (t != null) {
			return t.split(",");
		}
		return null;
	}

	public ReMsg createTag(String key, String value) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		UserTag u = getUserTag(uid);
		if (u == null) {
			UserTag tag = new UserTag(key, value);
			tag.set_id(uid);
			tag.setUpdateTime(System.currentTimeMillis());
			getMongoTemplate().save(tag);
			return new ReMsg(ReCode.OK);
		} else {
			int len = u.getTags().size();
			if (len >= MAX_COUNT) {
				return new ReMsg(ReCode.TAG_COUNT_MAX);
			} else if (len >= 3) {
				ReCode r = coinService.reduce(uid, CoinLog.OUT_ADD_TAG, len + 1, (len - 2) * 10000, 0,
						"第" + (len + 1) + "条签名");
				if (r.reCode() == ReCode.OK.reCode()) {
					u.add(key, value);
					getMongoTemplate().save(u);
					return new ReMsg(ReCode.OK);
				}
				return new ReMsg(r);
			} else {
				u.add(key, value);
				getMongoTemplate().save(u);
				return new ReMsg(ReCode.OK);
			}
		}
	}

	public ReMsg delTag(int idx) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		UserTag u = getUserTag(uid);
		if (u == null) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		} else {
			if (idx >= u.getTags().size()) {
				return new ReMsg(ReCode.PARAMETER_ERR);
			}
			u.getTags().remove(idx);
			getMongoTemplate().save(u);
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg updateTag(int idx, String key, String value) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		UserTag u = getUserTag(uid);
		if (u == null) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		} else {
			if (idx >= u.getTags().size()) {
				return new ReMsg(ReCode.PARAMETER_ERR);
			}
			u.replace(idx, key, value);
			getMongoTemplate().save(u);
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg changeIdx(int srcIdx, int descIdx) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		UserTag u = getUserTag(uid);
		if (u == null) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		} else {
			if (srcIdx >= u.getTags().size()) {
				return new ReMsg(ReCode.PARAMETER_ERR);
			}
			UserTagItem ut = u.getTags().get(srcIdx);
			u.del(srcIdx);
			u.insert(descIdx, ut);
			getMongoTemplate().save(u);
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg adminSetTags(Long uid, String tag) {
		UserTag u = getUserTag(uid);
		String[] tags = tag.split(";");
		ArrayList<UserTagItem> userTags = new ArrayList<UserTagItem>();
		int length = tags.length <= 8 ? tags.length : 8;
		for (int i = 0; i < length; i++) {
			String[] split = tags[i].split("=");
			if (split.length == 2) {
				userTags.add(new UserTagItem(split[0], split[1]));
			}
		}
		if (null == u) {
			u = new UserTag();
			u.set_id(uid);
		}
		u.setUpdateTime(System.currentTimeMillis());
		u.setTags(userTags);
		getMongoTemplate().save(u);
		return new ReMsg(ReCode.OK);
	}

	public UserTag getUserTag(Long uid) {
		return DboUtil.toBean(findById(uid), UserTag.class);
	}

	public DBObject findById(Long id) {
		return getC(DocName.USER_TAG).findOne(new BasicDBObject("_id", id));
	}

	public List<DBObject> findUserTag(Long uid, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}

		return getC(DocName.USER_TAG).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public int countUserTag(Long uid) {
		DBObject q = new BasicDBObject();
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}

		return getC(DocName.USER_TAG).find(q).count();
	}

	public Page<DBObject> queryUserTag(Long userId, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findUserTag(userId, page, size);
		int count = this.countUserTag(userId);
		return new Page<DBObject>(count, size, page, dbos);
	}
}
