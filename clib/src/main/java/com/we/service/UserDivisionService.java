package com.we.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.Constant.Const;
import com.we.common.Constant.ReCode;
import com.we.common.mongo.DboUtil;
import com.we.common.utils.JSONUtil;
import com.we.common.utils.T2TUtil;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.models.DocName;
import com.we.models.division.UserDivision;
import com.we.models.division.Division;
import com.we.models.division.DivisionItem;
import com.we.models.division.DivisionTask;
import com.we.models.division.DivisionTask.DivisionTaskType;
import com.we.models.finance.CoinLog;

/** 用户的段位完成度表 */
@Service
public class UserDivisionService extends BaseService {
	@Autowired
	UserService userService;

	@Autowired
	UserWalletService userWalletService;

	static final Logger log = LoggerFactory.getLogger(UserDivisionService.class);

	public Page<DBObject> query(Long _id, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(_id, page, size);
		int count = count(_id);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int count(Long _id) {
		DBObject q = new BasicDBObject();
		if (_id != null && _id > 0) {
			q.put("_id", _id);
		}
		return getC(DocName.USER_DIVISION).find(q).count();
	}

	public List<DBObject> find(Long _id, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (_id != null && _id > 0) {
			q.put("_id", _id);
		}
		return getC(DocName.USER_DIVISION).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("updateTime", -1)).toArray();
	}

	public DBObject findById(long _id) {
		return getC(DocName.USER_DIVISION).findOne(new BasicDBObject("_id", _id));
	}

	public UserDivision getUserDivision(long _id) {
		DBObject dbo = this.findById(_id);
		if (dbo == null) {
			return new UserDivision(_id);
		}
		return DboUtil.toBean(dbo, UserDivision.class);
	}

	public void saveUserDivision(UserDivision userDivision) {
		userDivision.setUpdateTime(System.currentTimeMillis());
		super.getMongoTemplate().save(userDivision);
	}

	public List<DivisionTask> getUserDivisionList(Long uid,int size){
		return getUserDivisionList(uid, size,null);
	}
	/** 获取用户显示的所有任务列表(包含用户进度) */
	public List<DivisionTask> getUserDivisionList(Long uid,int size,Integer version) {
		// 获取所有的段位列表
		DBObject user = userService.findByIdSafe(uid);
		int divisionId = DboUtil.getInt(user, "divisionId");
		UserDivision userDivision = this.getUserDivision(uid);
		List<DivisionTask> dis = new ArrayList<DivisionTask>();
		// 组装段位列表
		for (DivisionTaskType dtt : DivisionTaskType.values()) {//
			DivisionItem di = userDivision.getConditionsVal(dtt.getCode());
			DivisionTask dt  =null;
			if (di != null) {
				if (di.getStatus() != Const.STATUS_OK) {
					dt = new DivisionTask(dtt, di);
					if (dtt.getCnt() <= di.getCnt()) {
						if (dtt.getDivisionCode() - 1 <= divisionId) {
							dt.setStatus(Const.STATUS_PROCESSED);
						}
					}
				}
			}else{
				dt = new DivisionTask(dtt);
			}
			if(dt!=null) {
				if(version!=null&&version==2) {
					dt =DivisionTask.appValues(dt);
				}
				dis.add(dt);
			}
			if(size == dis.size()) {
				break;
			}
		}
		return dis;
	}

	/** 用户做段位任务 */
	public void doDivisionTask(long uid, DivisionTask.DivisionTaskType divisionTask, int cnt) {
		if (cnt == 0) {
			cnt = 1;
		}
		// 用户现在的段位
		UserDivision userDivision = getUserDivision(uid);
		if (userDivision == null) {
			userDivision = new UserDivision(uid);
		}
		DivisionItem di = userDivision.getConditionsVal(divisionTask.getCode());
		if (di == null) {
			di = new DivisionItem();
			di.setCnt(cnt);
		} else if (di.getStatus() == Const.STATUS_OK) {
			return;
		} else {
			di.setCnt(di.getCnt() + 1);
		}
		userDivision.put(divisionTask.getCode(), di);
		super.getMongoTemplate().save(userDivision);
	}

	public ReMsg recvReword(long uid, int divisionTaskId) {
		Integer divId = T2TUtil.obj2Int(super.getUser("divisionId"));
		// 用户现在的段位
		UserDivision userDivision = getUserDivision(uid);
		if (userDivision == null) {
			return new ReMsg(ReCode.FAIL);
		}
		DivisionItem di = userDivision.getConditionsVal(divisionTaskId);
		if (di == null) {
			return new ReMsg(ReCode.FAIL);
		}
		DivisionTaskType dtt = DivisionTaskType.of(divisionTaskId);
		if (dtt.getDivisionCode() - 1 <= divId) {
			di.setStatus(Const.STATUS_OK);
			userDivision.put(divisionTaskId, di);
			super.getMongoTemplate().save(userDivision);
			ReCode r = userWalletService.addCoin(uid, divisionTaskId, divisionTaskId, CoinLog.DEF_COIN_TYPE,
					dtt.getReward(), 0, "임무 완성ID:" + divisionTaskId);//完成任务id：1的任务
			if (r.reCode() != ReCode.OK.reCode()) {
				log.error("ADD COIN ERR:" + uid + " ,DIVISION TASK : " + divisionTaskId);
			}
			Map<String, Object> result = new HashMap<String, Object>();
			if (dtt.getDivisionCode() > 0) {
				boolean isUp = true;
				for (DivisionTaskType cdtt : DivisionTaskType.values()) {
					if (cdtt.getDivisionCode() == dtt.getDivisionCode() && cdtt.getCode() != dtt.getCode()) {
						DivisionItem cdi = userDivision.getConditionsVal(cdtt.getCode());
						if (cdi == null || cdi.getStatus() != Const.STATUS_OK) {
							isUp = false;
							break;
						}
					}
				}
				if (isUp) {
					int newDivId = divId + 1;
					userService.updateUserDivision(uid, newDivId);
					result.put("befDivision", Division.of(divId));
					result.put("curDivision", Division.of(newDivId));
					result.put("befDivId", divId);
					result.put("newDivId", newDivId);

					if (newDivId == Division.PLATINUM.getCode()) {
						Long puid = T2TUtil.obj2Long(super.getUser("shareUid"));
						if (puid != null) {
							int count = 0;
							List<DBObject> dbos = userService.findUserByShareUid(puid);
							for (DBObject dbo : dbos) {
								if (DboUtil.getInt(dbo, "divisionId") == newDivId) {
									count++;
								}
							}
							if (count < 11) {
								this.doDivisionTask(puid, DivisionTaskType.SUB_PLATINUM_10, 1);
							} else if (count < 31) {
								this.doDivisionTask(puid, DivisionTaskType.SUB_PLATINUM_30, 1);
							}
						}
					}
				}
			}
			result.put("coin", dtt.getReward());
			return new ReMsg(ReCode.OK, result);
		} else {
			return new ReMsg(ReCode.FAIL);
		}

	}
	
	public List<Division> getDivisionValues(){
		List<Division> list = new ArrayList<Division>();
		for(int i=0;i<Division.values().length;i++) {
			  list.add(Division.values()[i]);
		}
		return list;
	}
	
	public List<Map<String, Object>> getAppDivisionValues(){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int i=0;i<Division.values().length;i++) {
			 Map<String, Object> map=new HashMap<String, Object>();
			  Division division=Division.values()[i];
			  map.put("code", division.getCode());
			  map.put("des", division.getDes());
			  map.put("rate", division.getRate());
			  map.put("amount", division.getAmount());
			  list.add(map);
		}
		return list;
	}

}
