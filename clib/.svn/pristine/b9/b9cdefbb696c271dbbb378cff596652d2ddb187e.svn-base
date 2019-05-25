package com.we.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.Constant.Const;
import com.we.common.Constant.ReCode;
import com.we.common.utils.RegexUtil;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.models.BusinessCooperation;
import com.we.models.DocName;

@Service
public class BusinessCooperationService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(BusinessCooperationService.class);

	/** 提交商务合作信息 */
	public ReMsg saveBusinessCooperation(long uid, String name, String introduction, String officialAddr, String contact, String phone,
			String contactInfo,String email,String cooperation) {
		if(StringUtils.isBlank(name)) {
			return new ReMsg(ReCode.BUSINESS_NAME_EMPTY);
		}
		if(StringUtils.isBlank(contact)) {
			return new ReMsg(ReCode.BUSINESS_CONTACT_EMPTY);		
		}
		if(StringUtils.isBlank(phone)) {
			return new ReMsg(ReCode.BUSINESS_PHONE_EMPTY);
		}else {
			if(!RegexUtil.isPhoneKorea(phone)&&!RegexUtil.isPhoneChina(phone)) {
				return new ReMsg(ReCode.PHONE_NUMBER_FORMAT_ERROR);
			}
		}
		if(StringUtils.isBlank(contactInfo)) {
			return new ReMsg(ReCode.BUSINESS_CONTACTINFO_EMPTY);
		}
		if(StringUtils.isBlank(cooperation)) {
			return new ReMsg(ReCode.BUSINESS_COOPERATION_EMPTY);
		}
		
		BusinessCooperation businessCooperation = new BusinessCooperation(uid, name, introduction, officialAddr, contact,phone,
				contactInfo, email,cooperation);
		long time = System.currentTimeMillis();
		businessCooperation.setUpdateTime(time);
		businessCooperation.set_id(super.getNextId(DocName.BUSINESS_COOPERATION));
		super.getMongoTemplate().save(businessCooperation);
		return new ReMsg(ReCode.OK);
	}

	public DBObject findById(Long _id) {
		return getC(DocName.BUSINESS_COOPERATION).findOne(new BasicDBObject("_id", _id));
	}

	public Page<DBObject> query(Integer status, Long uid, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(status, uid, page, size);
		int count = count(status, uid);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int count(Integer status, Long uid) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}
		return getC(DocName.BUSINESS_COOPERATION).find(q).count();
	}

	public List<DBObject> find(Integer status, Long uid, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}
		return getC(DocName.BUSINESS_COOPERATION).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("createTime", -1)).toArray();
	}

	/** 后台审核 改状态 */
	public ReMsg status(final Long _id, final Integer status, long adminId) {
		super.getC(DocName.BUSINESS_COOPERATION).update(new BasicDBObject("_id", _id),
				new BasicDBObject("$set", new BasicDBObject("status", status).append("adminId", adminId)
						.append("updateTime", System.currentTimeMillis())));
		return new ReMsg(ReCode.OK);
	}

}
