package com.zb.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.ReNameLog;
import com.zb.models.finance.CoinLog;
import com.zb.service.pay.CoinService;

import freemarker.template.utility.StringUtil;

@Service
public class ReNameLogService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(ReNameLogService.class);
	@Autowired
	CoinService coinServie;
	@Autowired
	UserService userService;

	/** 查询修改记录 */
	private DBObject findById(long uid) {
		return getC(DocName.RENAMELOG).findOne(new BasicDBObject("_id", uid));
	}

	/** 更新用户信息并存储更新nickname日志 */
	private void updateUserAndsaveLog(long uid, String nickname) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis()).append("nickname", nickname);
		userService.update(uid, u);

		DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject("count", Integer.valueOf(1))).append("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()));
		getMongoTemplate().getCollection(DocName.RENAMELOG).findAndModify(new BasicDBObject("_id", uid),
				new BasicDBObject("count", Integer.valueOf(1)), null, false, incSeq, true, true);
	}

	/** 查询所需金币 */
	public int queryNeedCoin(long uid) {
		DBObject dbo = findById(uid);
		return dbo == null ? 0 : DboUtil.getInt(dbo, "count") * ReNameLog.DEFAULT_MONEY;
	}

	/** 查询所需金币 */
	public ReMsg queryNeedCoin() {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return new ReMsg(ReCode.OK, queryNeedCoin(uid));
	}

	/** 改名 */
	public ReMsg changeNickname(String nickname) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		ReMsg rm = userService.checkNickname(nickname, uid);
		if (ReCode.OK.reCode() != rm.getCode()) {
			return rm;
		}
		ReCode rc = null;
		int coin = queryNeedCoin(uid);
		if (coin > 0) {// 扣币
			rc = coinServie.reduce(uid, CoinLog.OUT_RENAME, uid, coin, 0, "改名花费金币-" + coin);
		}
		if (rc==null || rc.reCode()==ReCode.OK.reCode()) {// 扣币成功
			this.updateUserAndsaveLog(uid, nickname.trim());
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(rc);
	}
}
