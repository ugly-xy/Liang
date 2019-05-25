package com.zb.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.Param;
import com.zb.common.Constant.ReCode;
import com.zb.common.http.HttpClientUtil;
import com.zb.common.http.HttpsClientUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.VersionUpdate;

@Service
public class VersionUpdateService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(VersionUpdateService.class);

	@Autowired
	EnvService envService;

	public DBObject findById(Long id) {
		return super.findById(DocName.VERSION_UPDATE, id);
	}

	public DBObject getListVersion(HttpServletRequest req) {
		DBObject q = new BasicDBObject("status", Const.STATUS_UP);
		int ch = super.getCh(req);
		int cch = super.getChildCh(req);
		if (ch < 1) {
			ch = 1;
		}
		if (cch < 1) {
			cch = 1;
		}
		q.put("ch", ch);
		q.put("childCh", cch);
		List<DBObject> ls = getC(DocName.VERSION_UPDATE).find(q).sort(new BasicDBObject("_id", -1)).limit(1).toArray();
		if (!ls.isEmpty()) {
			DBObject dbo = ls.get(0);
			return dbo;
		}
		return null;
	}

	public ReMsg check(HttpServletRequest req) {
		String appCode = req.getParameter(Param.APP_CODE);
		Map<String, String> data = new HashMap<String, String>();
		data.put("status", "1");
		DBObject q = new BasicDBObject("status", Const.STATUS_UP);
		if (appCode == null) {
			appCode = Const.APP_CODE_BIBI;
		}
		q.put("appName", appCode);
		List<DBObject> ls = getC(DocName.VERSION_UPDATE).find(q).sort(new BasicDBObject("_id", -1)).limit(1).toArray();
		if (!ls.isEmpty()) {
			DBObject dbo = ls.get(0);
			double reqv = super.getVer(req);
			if (reqv <= DboUtil.getDouble(dbo, "mustVersion")) {// 强制更新
				data.put("status", "3");
				data.put("msg", "强制更新");
				data.put("discription", DboUtil.getString(dbo, "discription"));
				data.put("title", DboUtil.getString(dbo, "title"));
				data.put("url", DboUtil.getString(dbo, "url"));
			} else if (reqv < DboUtil.getDouble(dbo, "curVersion")) {// 选择更新
				data.put("status", "2");
				data.put("msg", "选择更新");
				data.put("discription", DboUtil.getString(dbo, "discription"));
				data.put("title", DboUtil.getString(dbo, "title"));
				data.put("url", DboUtil.getString(dbo, "url"));
			}
		}
		return new ReMsg(ReCode.OK, data);
	}

	public Page<DBObject> query(Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(page, size);
		Integer count = count();
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> find(Integer page, Integer size) {
		return getC(DocName.VERSION_UPDATE).find().sort(new BasicDBObject("_id", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
	}

	public Integer count() {
		return getC(DocName.VERSION_UPDATE).find().count();
	}

	public ReMsg update(Long id, Integer status, Integer ch, Integer childCh, Double curVersion, Double mustVersion,
			String title, String discription, String url, Integer via, String appName) {
		DBObject a = findById(id);
		if (null == a) {
			return new ReMsg(ReCode.FAIL);
		}
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		u.put("adminId", super.getUid());
		if (status != null && status != 0) {
			u.put("status", status);
		}
		if (ch != null && ch > 0) {
			u.put("ch", ch);
		}
		if (childCh != null && childCh > 0) {
			u.put("childCh", childCh);
		}
		if (curVersion != null && curVersion > 0.0) {
			u.put("curVersion", curVersion);
		}
		if (mustVersion != null && mustVersion > 0.0) {
			u.put("mustVersion", mustVersion);
		}

		if (StringUtils.isNotBlank(title)) {
			u.put("title", title);
		}
		if (StringUtils.isNotBlank(discription)) {
			u.put("discription", discription);
		}
		if (StringUtils.isNotBlank(url)) {
			u.put("url", url);
		}
		if (via != null && via > 0) {
			u.put("via", via);
		}
		if (StringUtils.isNotBlank(appName)) {
			u.put("appName", appName);
		}
		if (getC(DocName.VERSION_UPDATE).update(new BasicDBObject("_id", id), new BasicDBObject("$set", u), false,
				false, WriteConcern.ACKNOWLEDGED).getN() > 0) {
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);

	}

	public ReMsg save(Integer status, int ch, int childCh, double curVersion, double mustVersion, String title,
			String discription, String url, int via, String appName) {
		Long id = super.getNextId(DocName.VERSION_UPDATE);
		Long adminId = super.getUid();
		VersionUpdate g = new VersionUpdate(id, curVersion, mustVersion, status, title, discription, adminId, ch,
				childCh, url, via, appName);
		getMongoTemplate().save(g);
		return new ReMsg(ReCode.OK, g);
	}

	private static final String ITUNES_BIBI = "https://itunes.apple.com/cn/app/qq/id1100160696";

	public void checkIosVersion() {
		try {
			String s = HttpsClientUtil.get(ITUNES_BIBI, null);
			if (s != null) {
				int sind = s.indexOf("softwareVersion");
				if (sind > 0) {
					String subStr = s.substring(sind + 17, sind + 30);
					String vStr = subStr.substring(0, subStr.indexOf('<'));
					Double v = T2TUtil.str2Double(vStr, 0d);
					// System.out.println(v);
					Double curVersion = envService.getDouble("ios.check.ver");
					if (curVersion == null || curVersion <= v) {
						v = v + 0.01d;
						DecimalFormat df = new DecimalFormat("#.00");
						envService.save("ios.check.ver", df.format(v));
					}
				}
			}
		} catch (IOException e) {
			log.error("", e);
		} catch (URISyntaxException e) {
			log.error("", e);
		}
	}

	private static final String MYAPP_BIBI = "http://android.myapp.com/myapp/detail.htm?apkName=";
	private static final String PACKAGE_NAME = "com.zhuangbi";
	private static final String NIHUAWOCAI = "com.bibinihuawocai";
	private static final String SHUISHIWODI = "com.bibishuishiwodi";
	private static final String APK_DOWN = "android.down.url";

	public String getAndroidLastVersion(String appCode) {
		try {
			String packname = null;
			if (Const.APP_CODE_DRAWSOMETHING.equals(appCode)) {
				packname = NIHUAWOCAI;
			} else if (Const.APP_CODE_UNDERCOVER.equals(appCode)) {
				packname = SHUISHIWODI;
			} else {
				packname = PACKAGE_NAME;
			}
			String url = MYAPP_BIBI + packname;
			System.out.println(url);
			String s = HttpClientUtil.get(url, null);
			if (s != null) {
				int sind = s.indexOf("data-apkUrl");
				if (sind > 0) {
					String subStr = s.substring(sind + 13);
					String vStr = subStr.substring(0, subStr.indexOf('"'));
					if (vStr != null && vStr.contains(".apk")) {
						return vStr;
					}
				}
			}
		} catch (IOException e) {
			log.error("", e);
		}
		return null;
	}

	public static void main(String[] args) {
		// VersionUpdateService.checkAndroidVersion();
	}

}
