package com.zb.service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.crypto.MDUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.Banner;
import com.zb.models.DocName;
import com.zb.models.PointLog;
import com.zb.models.TitleModel;
import com.zb.models.UpdatePwdLog;
import com.zb.models.User;
import com.zb.models.UserPackLog;
import com.zb.models.UserTitle;
import com.zb.models.VipMap;
import com.zb.models.goods.BaseGoods;
import com.zb.models.goods.BaseGoods.Gift;
import com.zb.models.third.Agent;
import com.zb.models.user.Relationship;
import com.zb.models.user.UserTag;
import com.zb.models.usertask.Task;
import com.zb.service.article.ArticleService;
import com.zb.service.cloud.StorageService;
import com.zb.service.im.AliIMService;
import com.zb.service.im.EasemobService;
import com.zb.service.othergames.StarTrekService;
import com.zb.service.pay.CoinService;
import com.zb.service.usertask.UserTaskService;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.Msg;
import com.zb.socket.model.MsgType;

@Service
public class AgentService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(AgentService.class);


	public DBObject findById(Long id) {
		return getC(DocName.AGENT).findOne(new BasicDBObject("_id", id));
	}
	
	public Agent getAgentById(Long id) {
		return DboUtil.toBean(findById(id), Agent.class);
	}

	public List<DBObject> find(Long id,String name, String phone, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != id && id != 0) {
			q.put("_id", id);
		}
		if (StringUtils.isNotBlank(name)) {
			q.put("name", name);
		}
		if (StringUtils.isNotBlank(phone)) {
			q.put("phone", phone);
		}
		List<DBObject> dbos = getC(DocName.AGENT).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
		return dbos;
	}


	public int count(Long id,String name, String phone) {
		DBObject q = new BasicDBObject();
		if (null != id && id != 0) {
			q.put("_id", id);
		}
		if (StringUtils.isNotBlank(name)) {
			q.put("name", name);
		}
		if (StringUtils.isNotBlank(phone)) {
			q.put("phone", phone);
		}
		return getC(DocName.AGENT).find(q).count();
	}

	public Page<DBObject> query(Long id,String name, String phone, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.find(id, name, phone,page, size);
		int count = count(id, name, phone);
		return new Page<DBObject>(count, size, page, dbos);
	}
	

	public ReMsg upsert(Long id, String name, String phone, String pwd, Double rate, Double coinRate, Integer status
			) {
		Long adminId = super.getUid();//
		Agent a = this.getAgentById(id);
		if(pwd!=null){
			pwd = MDUtil.SHA.digest2HEX(pwd);
		}
		if (a == null ) {
			a = new Agent( id , name, phone, pwd, rate, coinRate, adminId);
		} else {
			if(StringUtils.isNotBlank(name)){
				a.setName(name);
			}
			if(StringUtils.isNotBlank(phone)){
				a.setPhone(phone);
			}
			if(StringUtils.isNotBlank(pwd)){
				a.setPwd(pwd);
			}
			if(null!=rate){
				a.setRate(rate);
			}
			if(null!=coinRate){
				a.setCoinRate(coinRate);
			}
			if(status!=null){
				a.setStatus(status);
			}
			a.setAdminId(adminId);
			a.setUpdateTime(System.currentTimeMillis());
		}
		getMongoTemplate().save(a);
		return new ReMsg(ReCode.OK);
	}
	

}
