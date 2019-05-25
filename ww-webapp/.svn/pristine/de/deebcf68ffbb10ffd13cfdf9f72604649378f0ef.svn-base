package com.zb.web.api;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.models.goods.BaseGoods;
import com.zb.service.AppCoverService;
import com.zb.service.FlowerRedeemService;
import com.zb.service.GiftRecvTopService;
import com.zb.service.GoodsService;
import com.zb.service.RankListService;
import com.zb.service.UserPackService;

@Controller
@RequestMapping("/api")
public class GoodsCtl extends BaseCtl {
	static final Logger log = LoggerFactory.getLogger(GoodsCtl.class);

	@Autowired
	GoodsService goodsService;
	@Autowired
	RankListService rankListService;
	@Autowired
	UserPackService userPackService;
	@Autowired
	FlowerRedeemService redeemService;
	@Autowired
	AppCoverService appCoverService;
	@Autowired
	GiftRecvTopService giftRecvTopService;

	// 使用道具
	@ResponseBody
	@RequestMapping("/prop/use")
	public ReMsg use(Long id, Integer count, Long roomId, Long toUid, HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (null == id) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return goodsService.useProp(uid, id, count, roomId, toUid);
	}

	// 用户背包礼物数
	@ResponseBody
	@RequestMapping("/prop/query")
	public ReMsg queryProp(Integer type, Integer baseId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return new ReMsg(ReCode.OK, userPackService.queryPropCount(type, baseId, uid));
	}

	// 赠送礼物
	@ResponseBody
	@RequestMapping("/gift/send")
	// sengdGift
	public ReMsg sendGift(int id, Integer count, int local, Long localId, Long toUid, HttpServletRequest req) {
		return goodsService.sendGift(id, count, local, localId, toUid);
	}

	// 魅力或富豪榜
	@ResponseBody
	@RequestMapping("/rankList")
	// 第一页 每页30
	public ReMsg rankList(Integer day, Integer bgId, int type, HttpServletRequest req) {
		if (null == bgId) {
			bgId = BaseGoods.Gift.FLOWER.getV().getId();
		}
		return rankListService.queryRankList(day, bgId, type, RankListService.PAGE, RankListService.SIZE);
	}

	// 空间收花榜
	@ResponseBody
	@RequestMapping("/gift/list")
	public ReMsg userSpaceGift(Long uid, Integer day, Integer bgId, Integer page, Integer size) {
		return goodsService.userSpaceGift(uid, day, bgId, page, size);
	}

	// 文章 收花榜
	@ResponseBody
	@RequestMapping("/gift/article")
	public ReMsg articleGift(Long id, Integer page, Integer size) {
		return goodsService.articleGift(id, page, size);
	}

	// app启动封面
	@ResponseBody
	@RequestMapping("/appCover")
	public ReMsg appStartCover(Long day) {
		return new ReMsg(ReCode.OK, appCoverService.queryAppCover(day));
	}

	// 花魁名人堂
	@ResponseBody
	@RequestMapping("/gift/giftRecvTop")
	public ReMsg giftRecvTop(Integer page, Integer size) {
		return new ReMsg(ReCode.OK, giftRecvTopService.queryGiftRecvTop(page, size));
	}

	// 昨日花魁
	@ResponseBody
	@RequestMapping("/gift/recvTop1")
	public ReMsg recvTop1() {
		return new ReMsg(ReCode.OK, giftRecvTopService.recvTop1());
	}

	// 圣诞活动排行榜
//	@ResponseBody
//	@RequestMapping("/gift/dateRange")
//	public ReMsg dateRange(Integer eventType, Integer type, Integer size) {
//		return new ReMsg(ReCode.OK, rankListService.getChristmasFlowerTop(eventType, type, size));
//	}

}
