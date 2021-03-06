package com.we.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csvreader.CsvWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.Constant.Const;
import com.we.common.Constant.ReCode;
import com.we.common.mongo.DboUtil;
import com.we.common.utils.DateUtil;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.models.DocName;
import com.we.models.WithdrawLog;
import com.we.models.finance.CoinLog;

@Service
public class WithdrawLogService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(WithdrawLogService.class);

	@Autowired
	UserService userService;

	@Autowired
	UserWalletService userWalletService;

	/** 后台审核 改状态 */
	public ReMsg validWithdraw(final Long _id, final Integer status, long adminId) {
		super.getC(DocName.WITHDRAW_LOG).update(new BasicDBObject("_id", _id),
				new BasicDBObject("$set", new BasicDBObject("status", status).append("adminId", adminId)
						.append("updateTime", System.currentTimeMillis())));
		return new ReMsg(ReCode.OK);
	}

	/** 后台审核 改状态 */
	public ReMsg validAll(final Integer amount) {
		Long adminId = super.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		DBObject q = new BasicDBObject("status", Const.STATUS_PROCESSED);
		if (amount != null && amount != 0) {
			q.put("amount", amount);
		}
		super.getC(DocName.WITHDRAW_LOG)
				.update(q,
						new BasicDBObject("$set", new BasicDBObject("status", Const.STATUS_OK)
								.append("adminId", adminId).append("updateTime", System.currentTimeMillis())),
						false, true);
		return new ReMsg(ReCode.OK);
	}

	public void export(Integer amount, String date, HttpServletRequest req, HttpServletResponse resp) {
		Date d = null;
		try {
			d = DateUtil.convertDate(date, "yyyy-MM-dd");
		} catch (ParseException e1) {
			log.error("date err:" + date);
			return;
		}
		long sd = DateUtil.getZeroTime(d);
		long ed = sd + 24 * 3600 * 1000;
		long adminId = super.getUid();
		if (adminId < 1) {
			return;
		}

		DBObject q = new BasicDBObject("status", 1).append("createTime",
				new BasicDBObject("$gte", sd).append("$lt", ed));
		if (amount == null) {
			amount = 0;
		}
		if (amount != 0) {
			q.put("amount", amount);
		}

		// int count = getC(DocName.WITHDRAW_LOG).find(q).count();
		List<DBObject> dbos = getC(DocName.WITHDRAW_LOG).find(q).toArray();

		try {
			String filename = amount + "_" + DateUtil.dateFormatMMddHHmmss(new Date());
			File tempFile = File.createTempFile(filename, ".csv");
			CsvWriter csvWriter = new CsvWriter(tempFile.getCanonicalPath(), ',', Charset.forName("UTF-8"));
			for (DBObject dbo : dbos) {
				csvWriter.write(DboUtil.getString(dbo, "walletAddress"));
				csvWriter.write(DboUtil.getString(dbo, "_id"));
				csvWriter.write(DboUtil.getString(dbo, "amount"));
				csvWriter.endRecord();
				validWithdraw(DboUtil.getLong(dbo, "_id"), Const.STATUS_PROCESSED, adminId);
			}

			csvWriter.close();
			java.io.OutputStream out = resp.getOutputStream();
			byte[] b = new byte[10240];
			java.io.File fileLoad = new java.io.File(tempFile.getCanonicalPath());
			System.out.println(tempFile.getCanonicalPath());
			resp.reset();
			resp.setContentType("application/csv");
			resp.setHeader("content-disposition", "attachment; filename=" + filename + ".csv");
			long fileLength = fileLoad.length();
			String length1 = String.valueOf(fileLength);
			resp.setHeader("Content_Length", length1);
			java.io.FileInputStream in = new java.io.FileInputStream(fileLoad);
			int n;
			while ((n = in.read(b)) != -1) {
				out.write(b, 0, n); // 每次写入out1024字节
			}
			in.close();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Page<DBObject> query(Integer status, Long uid, Integer amount, String coinType, String date, Integer page,
			Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(status, uid, amount, coinType, date, page, size);
		// DBObject user;
		// if (uid != null && uid != 0) {
		// user = userService.findById(uid);
		// for (DBObject dbo : dbos) {
		// dbo.put("walletAddress", DboUtil.getString(user, "walletAddress"));
		// dbo.put("firstname", DboUtil.getString(user, "firstname"));
		// dbo.put("lastname", DboUtil.getString(user, "lastname"));
		// dbo.put("phone", DboUtil.getString(user, "phone"));
		// }
		// } else {
		// for (DBObject dbo : dbos) {
		// user = userService.findById(DboUtil.getLong(dbo, "uid"));
		// dbo.put("walletAddress", DboUtil.getString(user, "walletAddress"));
		// dbo.put("firstname", DboUtil.getString(user, "firstname"));
		// dbo.put("lastname", DboUtil.getString(user, "lastname"));
		// dbo.put("phone", DboUtil.getString(user, "phone"));
		// }
		// }
		int count = count(status, uid, amount, coinType, date);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int count(Integer status, Long uid, Integer amount, String coinType, String date) {
		DBObject q = initDate(date);
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}
		if (amount != null && amount != 0) {
			q.put("amount", amount);
		}
		if (StringUtils.isNotBlank(coinType)) {
			q.put("coinType", coinType);
		}
		return getC(DocName.WITHDRAW_LOG).find(q).count();
	}

	public List<DBObject> find(Integer status, Long uid, Integer amount, String coinType, String date, Integer page,
			Integer size) {
		DBObject q = initDate(date);
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}
		if (amount != null && amount != 0) {
			q.put("amount", amount);
		}
		if (StringUtils.isNotBlank(coinType)) {
			q.put("coinType", coinType);
		}
		return getC(DocName.WITHDRAW_LOG).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("createTime", -1)).toArray();
	}

	DBObject initDate(String date) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isBlank(date)) {
			return q;
		}
		Date d = null;
		try {
			d = DateUtil.convertDate(date, "yyyy-MM-dd");
		} catch (ParseException e1) {
			log.error("date err:" + date);
			return q;
		}
		long sd = DateUtil.getZeroTime(d);
		long ed = sd + 24 * 3600 * 1000;
		q.put("createTime", new BasicDBObject("$gte", sd).append("$lt", ed));
		return q;
	}

	/** 提现 *///提币申请|출금 신청
	public ReMsg saveWithdrawLog(long uid, Double amount, String coinType, String walletAddress) {
		ReCode rc = userWalletService.reduce(uid, CoinLog.OUT_WITH_DRAW, 0, coinType, amount, 0,
				"출금 신청: " + coinType + " :" + amount);
		if (rc.reCode() == ReCode.OK.reCode()) {
			// 余额扣除成功
			WithdrawLog withdrawLog = new WithdrawLog(uid, amount, null, Const.STATUS_DEF, coinType, walletAddress);
			withdrawLog.set_id(super.getNextId(DocName.WITHDRAW_LOG));
			super.getMongoTemplate().save(withdrawLog);
		}
		return new ReMsg(rc);
	}
	// 申请提现
	public ReMsg updateWithdrawApplication(long uid, Double amount, String coinType) {
		
		return saveWithdrawLog(uid, amount, coinType, String.valueOf(uid));
	}
}
