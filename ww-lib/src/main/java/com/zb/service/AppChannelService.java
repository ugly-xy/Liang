package com.zb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.res.ZbTool;
import com.zb.models.res.ZbToolCate;
import com.zb.service.article.ArticleService;
import com.zb.service.room.UserActivityService;
import com.zb.view.ChannelView;

@Service
public class AppChannelService extends BaseService {

	@Autowired
	ZbToolsService zbToolsService;

	@Autowired
	BannerService bannerService;

	@Autowired
	ResService resService;

	@Autowired
	EnvService envService;

	@Autowired
	ArticleService articleService;

	@Autowired
	UserActivityService userActivityService;

	public ReMsg getNewIndex(Integer via, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		boolean isCheck = false;
		if (via != null && via == 2) {
			Double curVer = envService.getDouble("ios.check.ver");
			if (curVer == null) {
				curVer = 1.0d;
			}
			Double ver = super.getVer(req);
			if (ver == null || ver >= curVer) {
				isCheck = true;
			}
		}
		rs.put("check", isCheck);

		List<DBObject> banners = bannerService.findBanner(BannerService.TYPES[2], Const.STATUS_UP, via, 0, 5);
		rs.put("banners", banners);

		rs.put("games", userActivityService.findOnlineActivity(req));

		return new ReMsg(ReCode.OK, rs);
	}

	public ReMsg getIndex(Integer via, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		List<ChannelView> cvs = null;
		boolean isCheck = false;
		if (via != null && via == 2) {
			Double curVer = envService.getDouble("artifact.ios.check.ver");
			if (curVer == null) {
				curVer = 1.0d;
			}
			Double ver = super.getVer(req);
			if (ver == null || ver >= curVer) {
				isCheck = true;
			}
		} else {
			Double curVer = envService.getDouble("ios.check.ver");
			if (curVer == null) {
				curVer = 1.0d;
			}
			Double ver = super.getVer(req);
			if (ver == null || ver >= curVer) {
				isCheck = true;
			}
		}
		rs.put("check", isCheck);

		if (isCheck) {// 审核版本
			rs.put("title", "神器");
			rs.put("channelName", "神器");
			List<DBObject> banners = new ArrayList<DBObject>();
			// banners.add(bannerService.findBannerById(11l));
			banners.add(bannerService.findBannerById(envService.getLong("ios.check.banner.index")));
			rs.put("banners", banners);

			List<DBObject> tools = zbToolsService.findZbTool(ZbTool.DEF_TAG_APPLE, Const.STATUS_UP, 0L, 0, 200, null,
					0);
			int end = tools.size() > 4 ? 4 : tools.size();
			List<DBObject> newTools = tools.subList(0, end);
			rs.put("newTools", newTools);

			List<DBObject> rscates = zbToolsService.findZbToolCate(ZbToolCate.TOOL_V2, 0L, ZbToolCate.HAS_SUB,
					Const.STATUS_UP, 0, 3, null);
			rs.put("natives", rscates);

			cvs = new ArrayList<ChannelView>();
			Map<Long, String> cateMap = new HashMap<Long, String>();
			// cateMap.put(13L, "土豪任性");
			cateMap.put(14L, "爱情表白");
			cateMap.put(15L, "恶搞逗趣");
			// cateMap.put(16L, "游戏达人");
			cateMap.put(17L, "逆天证件");
			cateMap.put(18L, "天才少年");

			Map<Long, List<ZbTool>> map = new HashMap<Long, List<ZbTool>>();

			for (DBObject dbo : tools) {
				ZbTool tool = null;
				tool = DboUtil.toBean(dbo, ZbTool.class);
				if (tool.getCates() != null && tool.getCates().length > 0) {
					List<ZbTool> cts = map.get(tool.getCates()[0]);
					if (cts == null) {
						cts = new ArrayList<ZbTool>();
					}
					if (cts.size() < 4) {
						cts.add(tool);
					}
					map.put(tool.getCates()[0], cts);
				}
			}
			for (Long cateId : cateMap.keySet()) {
				List<ZbTool> curTools = map.get(cateId);
				if (curTools != null && curTools.size() > 0) {
					ChannelView cv = new ChannelView(curTools, cateMap.get(cateId), cateId);
					cvs.add(cv);
				}
			}
			rs.put("cates", cvs);
		} else {
			rs.put("title", "装逼");
			rs.put("channelName", "装逼");
			List<DBObject> banners = bannerService.findBanner(BannerService.TYPES[0], Const.STATUS_UP, via, 0, 5);
			rs.put("banners", banners);

			List<DBObject> newTools = zbToolsService.findZbTool(ZbTool.DEF_TAG_NEW, Const.STATUS_UP, 0L, 0, 4, null, 0);
			rs.put("newTools", newTools);

			List<DBObject> topTools = zbToolsService.findUseTopZbTool(null, Const.STATUS_UP, 0L, 0, 4, null,
					ZbTool.SERVER_TRUE);
			rs.put("topTools", topTools);

			List<DBObject> rscates = zbToolsService.findZbToolCate(ZbToolCate.TOOL_V2, 0L, ZbToolCate.HAS_SUB,
					Const.STATUS_UP, 0, 3, null);
			rs.put("natives", rscates);

			cvs = new ArrayList<ChannelView>();
			List<DBObject> cates = zbToolsService.findZbToolCate(ZbToolCate.TOOL_V2, 0L, ZbToolCate.HAS_NOT_SUB,
					Const.STATUS_UP, 0, 10, null);
			// System.out.println(cates.size());
			Long[] cateIds = new Long[cates.size()];
			for (int i = 0; i < cates.size(); i++) {
				cateIds[i] = DboUtil.getLong(cates.get(i), "_id");
			}

			Map<Long, List<ZbTool>> map = new HashMap<Long, List<ZbTool>>();

			List<DBObject> tools = zbToolsService.findZbTool(Const.STATUS_UP, cateIds, 0, 200);
			for (DBObject dbo : tools) {
				ZbTool tool = null;
				tool = DboUtil.toBean(dbo, ZbTool.class);
				if (tool.getCates() != null && tool.getCates().length > 0) {
					List<ZbTool> cts = map.get(tool.getCates()[0]);
					if (cts == null) {
						cts = new ArrayList<ZbTool>();
					}
					if (cts.size() < 4) {
						cts.add(tool);
					}
					map.put(tool.getCates()[0], cts);
				}
			}
			for (DBObject cate : cates) {
				List<ZbTool> curTools = map.get(DboUtil.getLong(cate, "_id"));
				if (curTools != null && curTools.size() > 0) {
					ChannelView cv = new ChannelView(curTools, DboUtil.getString(cate, "name"),
							DboUtil.getLong(cate, "_id"));
					cvs.add(cv);
				}
			}
			rs.put("cates", cvs);
		}
		return new ReMsg(ReCode.OK, rs);
	}

	public ReMsg getDouTu(Integer via, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		boolean isCheck = false;
		if (via != null && via == 2) {
			Double curVer = envService.getDouble("ios.check.ver");
			if (curVer == null) {
				curVer = 1.0d;
			}
			Double ver = super.getVer(req);
			if (ver == null || ver >= curVer) {
				isCheck = true;
			}
		}
		rs.put("check", isCheck);
		if (isCheck) {
			List<DBObject> banners = new ArrayList<DBObject>();
			banners.add(bannerService.findBannerById(envService.getLong("ios.check.banner.doutu")));
			rs.put("banners", banners);

			List<DBObject> packs = resService.findResPack("apple", 0L, Const.STATUS_UP, 0, 0, 200);
			int end = packs.size() > 4 ? 4 : packs.size();
			List<DBObject> newPacks = packs.subList(0, end);
			rs.put("newPacks", newPacks);
			Map<Long, String> rmap = new HashMap<Long, String>();
			rmap.put(1L, "金馆长系列");
			rmap.put(3L, "中二萌妹系列");
			rmap.put(4L, "神综艺系列");
			rmap.put(5L, "动漫杂货铺系列");
			rmap.put(2L, "恶搞系列");
			List<ChannelView> cvs = new ArrayList<ChannelView>();
			Map<Long, List<DBObject>> map = new HashMap<Long, List<DBObject>>();
			for (DBObject dbo : packs) {
				Long id = DboUtil.getLong(dbo, "cateId");
				List<DBObject> cps = map.get(id);
				if (cps == null) {
					cps = new ArrayList<DBObject>();
				}
				if (cps.size() < 4) {
					cps.add(dbo);
				}
				map.put(id, cps);
			}
			for (Long key : rmap.keySet()) {
				List<DBObject> list = map.get(key);
				if (list != null && list.size() > 0) {
					ChannelView cv = new ChannelView(list, rmap.get(key), key);
					cvs.add(cv);
				}
			}
			rs.put("cates", cvs);
		} else {
			List<DBObject> banners = bannerService.findBanner(BannerService.TYPES[1], Const.STATUS_UP, via, 0, 5);
			rs.put("banners", banners);

			List<DBObject> newPacks = resService.getNewResPack(null, Const.STATUS_UP, 0, 4);
			rs.put("newPacks", newPacks);

			List<DBObject> rpcs = resService.findResPackCate(Const.STATUS_UP, 0, 10);
			Long[] rpcIds = new Long[rpcs.size()];
			for (int i = 0; i < rpcs.size(); i++) {
				rpcIds[i] = DboUtil.getLong(rpcs.get(i), "_id");
			}
			List<DBObject> packs = resService.findResPack(rpcIds, Const.STATUS_UP, 0, 200);
			List<ChannelView> cvs = new ArrayList<ChannelView>();
			Map<Long, List<DBObject>> map = new HashMap<Long, List<DBObject>>();
			for (DBObject dbo : packs) {
				Long id = DboUtil.getLong(dbo, "cateId");
				List<DBObject> cps = map.get(id);
				if (cps == null) {
					cps = new ArrayList<DBObject>();
				}
				if (cps.size() < 4) {
					cps.add(dbo);
				}
				map.put(id, cps);
			}
			for (DBObject dbo : rpcs) {
				List<DBObject> list = map.get(DboUtil.getLong(dbo, "_id"));
				if (list != null && list.size() > 0) {
					ChannelView cv = new ChannelView(list, DboUtil.getString(dbo, "name"), DboUtil.getLong(dbo, "_id"));
					cvs.add(cv);
				}
			}
			rs.put("cates", cvs);

		}
		return new ReMsg(ReCode.OK, rs);

	}

	public ReMsg getDouTu(Long cid, Integer page, Integer size) {
		Page<DBObject> packs = resService.queryResPack(null, cid, Const.STATUS_UP, null, page, size);
		return new ReMsg(ReCode.OK, packs);
	}

	// public ReMsg getGroup(Integer via) {
	// List<DBObject> arts = articleService.find(0L, 0L, 0, Article.DEF, 0, 0,
	// 0, 0, false, System.currentTimeMillis(), 0L);
	// Map<String, List<?>> rs = new HashMap<String, List<?>>();
	// rs.put("articles", arts);
	// return new ReMsg(ReCode.OK, rs);
	// }

}
