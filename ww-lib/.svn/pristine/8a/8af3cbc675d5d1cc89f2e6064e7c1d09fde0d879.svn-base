package com.zb.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.http.HttpClientUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.common.mongo.Op;
import com.zb.common.redis.RK;
import com.zb.common.utils.BlackWordUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.PinYinUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.res.ZbTool;
import com.zb.models.res.ZbToolCate;
import com.zb.models.res.ZbToolExt;
import com.zb.models.res.ZbToolExtItem;
import com.zb.models.res.ZbToolKey;
import com.zb.models.res.ZbToolUseLog;

@Service
public class ZbToolsService extends BaseService {

	@Autowired
	EnvService envService;

	@Autowired
	ZbToolUseService zbToolUseService;

	@Autowired
	AirPortService airPortService;

	static final Logger log = LoggerFactory.getLogger(ZbToolsService.class);

	public DBObject findZbToolById(Long id) {
		return getC(DocName.TOOL).findOne(new BasicDBObject("_id", id));
	}

	public DBObject findZbToolCateById(Long id) {
		return getC(DocName.TOOL_CATE).findOne(new BasicDBObject("_id", id));
	}

	DBObject resKeys = new BasicDBObject("_id", 1).append("name", 1).append("logo", 1).append("status", 1)
			.append("hidden", 1).append("sort", 1).append("cates", 1).append("mark", 1).append("point", 1)
			.append("tags", 1).append("server", 1).append("description", 1).append("tmpBackPic", -1)
			.append("tmpBackPicIos", -1).append("count", 1).append("type", 1);// 刚更新
																				// 需要再进行观察

	public Page<DBObject> queryZbTools(String tag, Integer status, Integer hidden, Long cate, Integer page,
			Integer size, Op op, Integer server) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findZbTool(tag, status, cate, page, size, op, server);
		int count = countZbTool(tag, status, cate, op, server);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countZbTool(String tag, Integer status, Long cate, Op op, Integer server) {
		DBObject q = getQuery(tag, status, cate, op, server);

		return getC(DocName.TOOL).find(q).count();
	}

	public List<DBObject> findZbTool(String tag, Integer status, Long cate, Integer page, Integer size, Op op,
			Integer server) {
		DBObject q = getQuery(tag, status, cate, op, server);

		return getC(DocName.TOOL).find(q, resKeys).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", -1)).toArray();
	}

	private DBObject getQuery(String tag, Integer status, Long cate, Op op, Integer server) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(tag)) {
			q.put("tags", tag);
		}
		if (op != null) {
			op.getOp(q, "status", status);
		} else if (null != status && status != 0) {
			q.put("status", status);
		}
		if (cate != null && cate != 0) {
			q.put("cates", cate);
		}

		if (server != null && server != 0) {
			q.put("server", server);
		}
		return q;
	}

	public Page<DBObject> queryZbToolsSortByCount(String tag, Integer status, Integer hidden, Long cate, Integer page,
			Integer size, Op op, Integer server) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findUseTopZbTool(tag, status, cate, page, size, op, server);
		int count = countZbTool(tag, status, cate, op, server);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> findUseTopZbTool(String tag, Integer status, Long cate, Integer page, Integer size, Op op,
			Integer server) {
		DBObject q = getQuery(tag, status, cate, op, server);
		return getC(DocName.TOOL).find(q, resKeys).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("count", -1)).toArray();
	}

	public List<DBObject> findZbTool(Integer status, Long[] cate, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (cate != null && cate.length > 0) {
			q.put("cates", new BasicDBObject("$in", cate));
		}

		return getC(DocName.TOOL).find(q, resKeys).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", -1)).toArray();
	}

	public Page<DBObject> queryZbToolCate(Integer type, Long pid, Integer hasSub, Integer status, Integer page,
			Integer size, Op op) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findZbToolCate(type, pid, hasSub, status, page, size, op);
		int count = countZbToolCate(type, pid, hasSub, status, op);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countZbToolCate(Integer type, Long pid, Integer hasSub, Integer status, Op op) {
		DBObject q = new BasicDBObject();
		if (null != type && type != 0) {
			q.put("type", type);
		}

		if (null != pid) {
			q.put("parentId", pid);
		}

		if (op != null) {
			op.getOp(q, "status", status);
		} else if (null != status && status != 0) {
			q.put("status", status);
		}
		if (hasSub != null && hasSub > 0) {
			q.put("hasSub", hasSub);
		}
		return getC(DocName.TOOL_CATE).find(q).count();
	}

	// public static final Long CATE_DEF = 0L;

	// public static final Long CATE_NATIVE = 10000L;

	public List<DBObject> findZbToolCate(Long[] ids, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		q.put("_id", new BasicDBObject("$in", ids));
		return getC(DocName.TOOL_CATE).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", -1)).toArray();
	}

	public List<DBObject> findZbToolCate(Integer type, Long pid, Integer hasSub, Integer status, Integer page,
			Integer size, Op op) {
		DBObject q = new BasicDBObject();
		if (null != type && type != 0) {
			q.put("type", type);
		}
		if (op != null) {
			op.getOp(q, "status", status);
		} else if (null != status && status != 0) {
			q.put("status", status);
		}

		if (null != pid) {
			q.put("parentId", pid);
		}
		if (hasSub != null && hasSub != 0) {
			q.put("hasSub", hasSub);
		}

		return getC(DocName.TOOL_CATE).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("sort", -1)).toArray();
	}

	public ReMsg updateZbTool(Long id, String name, String logo, Integer status, Integer hidden, Integer sort,
			Long[] cate, String tmpBackPic, String mark, String tmpBackPicIos, int point, String[] tag, Integer server,
			String description, String[] otherPics, String style, String draw, String clazz, Integer type) {
		if (server == null || server < 1 || server > 2) {
			server = ZbTool.SERVER_FALSE;
		}

		DBObject up = new BasicDBObject("server", server).append("updateTime", System.currentTimeMillis());
		if (StringUtils.isNotBlank(name)) {
			up.put("name", name);
		}
		if (StringUtils.isNotBlank(logo)) {
			up.put("logo", logo);
		}
		if (StringUtils.isNotBlank(tmpBackPic)) {
			up.put("tmpBackPic", tmpBackPic);
		}
		if (StringUtils.isNotBlank(tmpBackPicIos)) {
			up.put("tmpBackPicIos", tmpBackPicIos);
		}
		if (StringUtils.isNotBlank(mark)) {
			up.put("mark", mark);
		}
		if (StringUtils.isNotBlank(description)) {
			up.put("description", description);
		}

		if (status != null && status != 0) {
			up.put("status", status);
		}
		if (hidden != null && hidden != 0) {
			up.put("hidden", hidden);
		}
		if (sort != null && sort != 0) {
			up.put("sort", sort);
		}
		if (point != 0) {
			up.put("point", point);
		}
		if (cate != null) {
			up.put("cates", cate);
		}
		if (otherPics != null) {
			up.put("otherPics", otherPics);
		}
		if (tag != null) {
			up.put("tags", tag);
		}
		if (StringUtils.isNotBlank(style)) {
			up.put("style", style);
		}
		if (StringUtils.isNotBlank(draw)) {
			up.put("draw", draw);
		}
		if (StringUtils.isNotBlank(clazz)) {
			up.put("clazz", clazz);
		}
		if (type != null && type != 0) {
			up.put("type", type);
		}
		getC(DocName.TOOL).update(new BasicDBObject("_id", id), new BasicDBObject("$set", up));
		return new ReMsg(ReCode.OK);
		// return new ReMsg(ReCode.FAIL);
	}

	public ReMsg updateZbToolCate(ZbToolCate zbToolCate) {
		getMongoTemplate().save(zbToolCate);
		return new ReMsg(ReCode.OK);
	}

	public ReMsg saveZbTool(String name, String logo, Integer status, Integer hidden, Integer sort, Long[] cate,
			String tmpBackPic, String mark, String tmpBackPicIos, int point, String[] tag, Integer server,
			String description, String[] otherPics, String style, String draw, String clazz, Integer type) {
		if (server == null || server < 1 || server > 2) {
			server = ZbTool.SERVER_FALSE;
		}
		ZbTool zbt = new ZbTool(super.getNextId(DocName.TOOL), name, logo, status, hidden, sort, cate, tmpBackPic, mark,
				tmpBackPicIos, point, tag, server, description, otherPics, style, draw, clazz, type);
		getMongoTemplate().save(zbt);
		return new ReMsg(ReCode.OK, zbt);
	}

	public ReMsg saveZbToolCate(String name, String cover, int status, Integer sort, Integer type, Long parentId,
			Integer hasSub, String namePic, String mark) {
		ZbToolCate zbtc = new ZbToolCate(super.getNextId(DocName.TOOL_CATE), name, cover, status, sort, type, parentId,
				hasSub, namePic, mark);
		getMongoTemplate().save(zbtc);
		return new ReMsg(ReCode.OK, zbtc);
	}

	public DBObject findToolKeyByUid(Long uid) {
		return getC(DocName.TOOL_KEY).findOne(new BasicDBObject("_id", uid));
	}

	/**
	 * 给用户添加key
	 * 
	 * @param uid
	 * @param toolId
	 * @param times
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public ReMsg addZbToolKey(Long uid, Long toolId, Long times)
			throws JsonParseException, JsonMappingException, IOException {
		ZbToolKey tk = DboUtil.toBean(findToolKeyByUid(uid), ZbToolKey.class);
		if (tk == null) {
			tk = new ZbToolKey(uid);
		}
		ZbTool t = DboUtil.toBean(findZbToolById(toolId), ZbTool.class);
		if (t == null) {
			return new ReMsg(ReCode.FAIL);
		}
		tk.addKey(t.getMark(), times);
		getMongoTemplate().save(tk);
		return new ReMsg(ReCode.OK, tk);
	}

	public ReMsg useZbTool(Long toolId, String mark, Integer model, HttpServletRequest req) {
		Long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		ZbToolUseLog tk = new ZbToolUseLog(super.getNextId(DocName.ZB_TOOL_USE_LOG), mark, toolId, uid, model);
		getMongoTemplate().save(tk);
		return zbToolUseService.addUse(toolId);
	}

	public ReMsg zhengjian(Long toolId, String json, HttpServletRequest req) {
//		Long uid = super.getUid();
//		if (uid < 1) {
//			return new ReMsg(ReCode.NOT_AUTHORIZED);
//		}
//		if (userToolUseLimit(uid)) {
//			return new ReMsg(ReCode.TOOL_USE_MAX_ERR);
//		}
		return toolServerCreater(toolId, json, 1, req);
	}

	private boolean userToolUseLimit(Long uid) {
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String sc = opsv.get(RK.toolUserUse(uid));
		boolean isLimit = false;
		if (StringUtils.isNotBlank(sc)) {
			isLimit = true;
		}
		opsv.set(RK.toolUserUse(uid), "1", TOOL_USER_USE_MIN_TIME, TimeUnit.SECONDS);
		return isLimit;
	}

	public static final int TOOL_USER_USE_MIN_TIME = 2; // 每用户最短调用间隔
	public static final int TOOL_IP_USE_MAX = 500; // 每IP最短调用间隔
	public static final int TOOL_IP_USE_MAX_MINOTH = 10; // 每IP最短调用间隔

	public ReMsg toolServerCreate(Long toolId, String json, HttpServletRequest req) {
//		Long uid = super.getUid();
//		if (uid < 1) {
//			return new ReMsg(ReCode.NOT_AUTHORIZED);
//		}
//		if (userToolUseLimit(uid)) {
//			return new ReMsg(ReCode.TOOL_USE_MAX_ERR);
//		}
		return toolServerCreater(toolId, json, 2, req);
	}

	public ReMsg toolServerCreate(HttpServletRequest req) {
		return toolServerCreater(null, null, 2, req);
	}

	private ReMsg toolServerCreater(Long toolId, String json, int version, HttpServletRequest req) {
		String ip = super.getIp(req);
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String sc = opsv.get(RK.toolIpUse(ip));
		if (StringUtils.isNotBlank(sc)) {
			int c = Integer.valueOf(sc);
			if (c > TOOL_IP_USE_MAX) {
				return new ReMsg(ReCode.TOOL_USE_IP_MAX_ERR);
			}
			opsv.set(RK.toolIpUse(ip), "" + (c + 1), TOOL_IP_USE_MAX_MINOTH, TimeUnit.MINUTES);
		} else {
			opsv.set(RK.toolIpUse(ip), "1", TOOL_IP_USE_MAX_MINOTH, TimeUnit.MINUTES);
		}

		if (toolId == null) {
			toolId = T2TUtil.str2Long(req.getParameter("id"), 0L);
		}
		DBObject dbo = this.findZbToolById(toolId);
		if (dbo == null) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		// System.out.println("1");
		Map params = null;
		String mark = DboUtil.getString(dbo, "mark");
		String draw = DboUtil.getString(dbo, "draw");
		String clazz = DboUtil.getString(dbo, "clazz");
		List<ZbToolExtItem> items = (List<ZbToolExtItem>) this.getToolExt(mark).getData();
		String tmpBackPic = null;
		if (json != null) {
			ReMsg rs = BlackWordUtil.blackWordStatus(json);
			if (ReCode.FAIL.reCode() == rs.getCode()) {
				return new ReMsg(ReCode.BLACK_WORD_ERR);
			}
			params = JSONUtil.jsonToMap(json);
			for (ZbToolExtItem item : items) {
				if (item.isHasExt()) {
					String key = params.get(item.get_id()).toString();
					for (int i = 0; i < item.getSelects().size(); i++) {
						if (key.equals(item.getSelects().get(i))) {
							tmpBackPic = item.getSelectsExt().get(i);
							break;
						}
					}
				}
				if (tmpBackPic != null) {
					break;
				}
			}
		} else {
			params = new HashMap();
			for (ZbToolExtItem item : items) {
				String value = req.getParameter(item.get_id());
				// System.out.println(json);
				ReMsg rs = BlackWordUtil.blackWordStatus(value);
				if (ReCode.FAIL.reCode() == rs.getCode()) {
					return new ReMsg(ReCode.BLACK_WORD_ERR);
				}
				params.put(item.get_id(), value);
				if (item.isHasExt()) {
					for (int i = 0; i < item.getSelects().size(); i++) {
						if (value.equals(item.getSelects().get(i))) {
							tmpBackPic = item.getSelectsExt().get(i);
						}
					}
				}
			}
		}
		if (tmpBackPic == null) {
			tmpBackPic = DboUtil.getString(dbo, "tmpBackPic");
		}
		params.put("tmpBackPic", tmpBackPic);// 设置模版
		if (!params.containsKey("style")) {
			String style = DboUtil.getString(dbo, "style");
			if (StringUtils.isNotBlank(style))
				params.put("style", style);
		}

		Map<String, Long> map = (Map<String, Long>) zbToolUseService.addUse(toolId).getData();
		if (!params.containsKey("useCount")) {
			String c = map.get("count").toString();
			if (StringUtils.isNotBlank(c))
				params.put("useCount", c);
		}

		String jsonR = getJsonString(mark, params, map, draw, clazz);
		if (null != jsonR) {
			if (version == 1)
				return JSONUtil.jsonToBean(jsonR, ReMsg.class);
			else {
				ReMsg rs = JSONUtil.jsonToBean(jsonR, ReMsg.class);
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("url", rs.getData());
				m.put("otherPics", dbo.get("otherPics"));
				m.put("name", DboUtil.getString(dbo, "name"));
				m.put("type", dbo.get("type"));
				rs.setData(m);
				return rs;
			}
		} else {
			log.error("Err id:" + toolId + ",mark:" + mark);
		}
		return new ReMsg(ReCode.FAIL);
	}

	private static final String IMG_API = "http://img.zhuangdianbi.com/sys/";

	private String getUrl(String mothed, Map params) throws UnsupportedEncodingException {
		String url = IMG_API + mothed;
		for (Object key : params.keySet()) {
			url += key.toString() + "=" + URLEncoder.encode(params.get(key).toString(), "utf8") + "&";
		}
		if (url.endsWith("&")) {
			url = url.substring(0, url.length() - 1);
		}
		return url;
	}

	String[] names = { "港龙航空", "中国国际航空", "阿联酋航空", "伊朗航空", "越南航空", "日本航空" };

	private String getJsonString(String mark, Map params, Map<String, Long> map, String draw, String clazz) {
		String jsonR = null;
		try {
			String count = "";
			if ("JiangZhuangNew".equals(mark)) {
				jsonR = HttpClientUtil.get(getUrl("zjJiangZhuang?", params), null);
			} else if ("TongYongZhengJian".equals(mark) || "TongYongZhengJianNew".equals(mark)
					|| "TongYongZhengJianGirl".equals(mark) || "TongYongZhengJianLove".equals(mark)) {
				if (map != null && map.containsKey("count")) {
					String c = map.get("count").toString();
					int length = 6 - c.length();
					for (int i = 0; i < length; i++) {
						c = "0" + c;
					}
					count = "ZB" + c;
					params.put("serialNumber", count);
				}
				String picPath = params.get("pic").toString();
				if (picPath.startsWith("http") && !picPath.startsWith("http://imgt.")) {
					picPath = picPath.substring(picPath.indexOf(".com") + 5);
					params.put("pic", picPath);
				}
				jsonR = HttpClientUtil.get(getUrl("zjTongYong?", params), null);
			} else if ("YanZhiCeShi".equals(mark)) {
				if (map != null && map.containsKey("count")) {
					String c = map.get("count").toString();
					int length = 7 - c.length();
					for (int i = 0; i < length; i++) {
						c = "0" + c;
					}
					params.put("use", c);
				}
				jsonR = HttpClientUtil.get(getUrl("zjYanZhiCeShi?", params), null);
			} else if ("TaiQuanDao".equals(mark)) {
				if (map != null && map.get("count") != null) {
					String c = map.get("count").toString();
					int length = 6 - c.length();
					for (int i = 0; i < length; i++) {
						c = "0" + c;
					}
					count = "CN" + c;
					params.put("serialNumber", count);
				}
				jsonR = HttpClientUtil.get(getUrl("zjTaiQuanDao?", params), null);
			} else if ("WenShen".equals(mark)) {
				String name = params.get("name").toString();
				name = PinYinUtil.toPinyinAllFirstUp(name);
				params.put("name", name);
				jsonR = HttpClientUtil.get(getUrl("zjWenShen?", params), null);
			} else if ("MTShirt".equals(mark) || "WTShirt".equals(mark)) {
				jsonR = HttpClientUtil.get(getUrl("zjTShirt?", params), null);
			} else if ("JiPiaoGuoNei".equals(mark) || "JiPiaoGuoJi".equals(mark)) {
				String name, enName, enFCity, fc3, enTCity, tc3;
				String style = params.get("style").toString();
				String fCity = params.get("fCity").toString();
				String tCity = params.get("tCity").toString();
				boolean flag = false;
				for (String s : names) {
					if (s.equals(style)) {
						flag = true;
						break;
					}
				}
				name = params.get("name").toString();
				if (flag) {
					if (StringUtils.isNotBlank(name)) {
						if (name.length() > 1) {
							enName = PinYinUtil.toPinyinUp(name.substring(0, 1)) + "/"
									+ PinYinUtil.toPinyinUp(name.substring(1, name.length()));
						} else {
							enName = PinYinUtil.toPinyinUp(name);
						}
					} else {
						enName = "ZDB";
					}
				} else {
					if (StringUtils.isNotBlank(name)) {
						enName = PinYinUtil.toPinyinUp(name);
					} else {
						enName = "ZDB";
					}
				}
				params.put("enName", enName);

				List<DBObject> faps = airPortService.findAirPort(fCity, 0, 1);
				if (faps.size() > 0) {
					DBObject fc = faps.get(0);
					enFCity = DboUtil.getString(fc, "enCity");
					fc3 = DboUtil.getString(fc, "code3");
				} else {
					enFCity = PinYinUtil.toPinyinUp(fCity);
					fc3 = "";
				}
				params.put("enFCity", enFCity);
				params.put("fc3", fc3);

				List<DBObject> taps = airPortService.findAirPort(tCity, 0, 1);
				if (taps.size() > 0) {
					DBObject fc = taps.get(0);
					enTCity = DboUtil.getString(fc, "enCity");
					tc3 = DboUtil.getString(fc, "code3");
				} else {
					enTCity = PinYinUtil.toPinyinUp(tCity);
					tc3 = "";
				}

				params.put("enTCity", enTCity);
				params.put("tc3", tc3);

				jsonR = HttpClientUtil.get(getUrl("zjJiPiaoNew?", params), null);
			} else {
				if (StringUtils.isNotBlank(draw) && StringUtils.isNotBlank(clazz)) {
					params.put("tmpl", clazz);
					jsonR = HttpClientUtil.get(getUrl(draw + "?", params), null);
				} else {
					jsonR = HttpClientUtil.get(getUrl("zj" + mark + "?", params), null);
				}
			}

		} catch (UnsupportedEncodingException e) {
			log.error("Draw Img Err:", e, mark);
		} catch (IOException e) {
			log.error("Draw Img Err:", e, mark);
		}
		return jsonR;
	}

	public ReMsg saveToolExt(String id, String json) {
		List<ZbToolExtItem> items = JSONUtil.jsonToArray(json, ZbToolExtItem.class);
		ZbToolExt zte = new ZbToolExt(id, items);
		getMongoTemplate().save(zte);
		return new ReMsg(ReCode.OK);
	}

	public ReMsg getToolExt(String id) {
		ZbToolExt zbt = super.getMongoTemplate().findById(id, ZbToolExt.class);
		if (zbt != null)
			return new ReMsg(ReCode.OK, zbt.getProps());
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg delToolExt(String id) {
		int i = super.getC(DocName.TOOL_EXT).remove(new BasicDBObject("_id", id), WriteConcern.ACKNOWLEDGED).getN();
		if (i > 0)
			return new ReMsg(ReCode.OK);
		return new ReMsg(ReCode.FAIL);
	}

	public Page<DBObject> queryToolExt(String key, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findToolExt(key, page, size);
		Integer count = countToolExt(key);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> findToolExt(String key, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(key)) {
			q.put("_id", key);
		}
		List<DBObject> list = getC(DocName.TOOL_EXT).find(q).skip(getStart(page, size)).limit(getSize(size)).toArray();
		List<DBObject> rlist = new ArrayList<DBObject>();
		for (DBObject item : list) {
			item.put("porps", JSONUtil.beanToJson(item.get("props")));
			rlist.add(item);
		}
		return rlist;

	}

	public Integer countToolExt(String key) {
		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(key)) {
			q.put("_id", key);
		}
		return getC(DocName.TOOL_EXT).find(q).count();
	}

	public Page<DBObject> searchZbTools(String qtxt, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		Pattern pattern = Pattern.compile("^.*" + qtxt + ".*$", Pattern.CASE_INSENSITIVE);
		DBObject q = new BasicDBObject("status", Const.STATUS_UP).append("name", pattern);

		DBCursor dbc = getC(DocName.TOOL).find(q, resKeys);
		int count = dbc.count();
		List<DBObject> dbos = dbc.skip(getStart(page, size)).limit(getSize(size)).sort(new BasicDBObject("sort", -1))
				.toArray();
		return new Page<DBObject>(count, size, page, dbos);
	}

	private static final String TOOL_SEARCH_RECOMMEND = "tool.search.recommend";

	public String[] searchRecommend() {
		String txt = envService.getString(TOOL_SEARCH_RECOMMEND);
		if (StringUtils.isNotBlank(txt)) {
			return txt.split(",");
		}
		return new String[0];
	}

	DBObject nameKeys = new BasicDBObject("_id", 1).append("name", 1).append("status", 1);

	public Map<Long, DBObject> findZbToolNameByIds(Long[] ids) {
		DBObject q = new BasicDBObject();
		q.put("_id", new BasicDBObject("$in", ids));
		List<DBObject> dbos = getC(DocName.TOOL).find(q, nameKeys).sort(new BasicDBObject("sort", -1)).toArray();
		Map<Long, DBObject> res = new HashMap<Long, DBObject>();
		for (DBObject dbo : dbos) {
			res.put(DboUtil.getLong(dbo, "_id"), dbo);
		}
		return res;
	}

	// private static final String SEQ = "count";
	// private static final DBObject seqField = new BasicDBObject(SEQ,
	// Integer.valueOf(1));
	// private static final DBObject incSeq = new BasicDBObject("$inc",
	// new BasicDBObject(SEQ, Long.valueOf(1)));
	//
	// public ReMsg addUse(Long toolId) {
	// Long count = (Long) super
	// .getC(DocName.TOOL)
	// .findAndModify(new BasicDBObject("_id", toolId), seqField,
	// null, false, incSeq, true, true).get(SEQ);
	// Long total = (Long) super
	// .getC(DocName.ZB_TOOL_USE_ST)
	// .findAndModify(new BasicDBObject("_id", 0), seqField, null,
	// false, incSeq, true, true).get(SEQ);
	// Map<String, Long> useC = new HashMap<String, Long>();
	// useC.put("count", count);
	// useC.put("total", total);
	// return new ReMsg(ReCode.OK, useC);
	// }

}
