package com.zb.service.pay;

import java.util.ArrayList;
import java.util.List;

import com.zb.models.finance.GiftBag;
import com.zb.models.finance.Order;
import com.zb.service.BaseService;

public class GiftBagService extends BaseService {

	private static List<GiftBag> apples = new ArrayList<GiftBag>();

	private static List<GiftBag> others = new ArrayList<GiftBag>();

	private static List<String> noviceIds = new ArrayList<String>();

	public static GiftBag getPayProduct(String payType, String id) {
		if (Order.THIRD_APPLE_PAY_APP.equals(payType)) {
			for (GiftBag pp : apples) {
				if (pp.getId().equals(id)) {
					return pp;
				}
			}
		} else {
			for (GiftBag pp : others) {
				if (pp.getId().equals(id)) {
					return pp;
				}
			}
		}
		return null;
	}

	public static List<GiftBag> getApples() {
		return apples;
	}

	public static List<GiftBag> getOthers() {
		return others;
	}

	public static List<String> getNoviceIds() {
		return noviceIds;
	}

	public static List<GiftBag> getGifgBag(String payType, Integer type) {
		List<GiftBag> list = null;
		if (Order.THIRD_APPLE_PAY_APP.equals(payType)) {
			list = getApples();
		} else {
			list = getOthers();
		}
		// 过滤
		List<GiftBag> res = new ArrayList<GiftBag>();
		if (null == type || type == 0) {// 查询全部礼包
			res = list;
		} else {
			if (type < 0) {// 过滤掉某一部分
				for (GiftBag giftBag : list) {
					if (giftBag.getType() != Math.abs(type)) {
						res.add(giftBag);
					}
				}
			} else if (type > 0) {// 只要某一部分
				for (GiftBag giftBag : list) {
					if (giftBag.getType() == Math.abs(type)) {
						res.add(giftBag);
					}
				}
			}
		}
		return res;
	}

	static {
//		apples.add(new GiftBag("com.zhuangbi.ZBD_18", "新手18元礼包", GiftBag.TYPE_NOVICE, 1800, 20000, 3000, 1000, 10000));
//		apples.add(new GiftBag("com.zhuangbi.ZBD_38", "新手38元礼包", GiftBag.TYPE_NOVICE, 3800, 40000, 10000, 2000, 20000));

		others.add(new GiftBag("10", "新手10元礼包", GiftBag.TYPE_NOVICE, 1000, 10000, 3000, 500, 5000));
		others.add(new GiftBag("20", "新手20元礼包", GiftBag.TYPE_NOVICE, 2000, 20000, 10000, 1000, 10000));

//		noviceIds.add("com.zhuangbi.ZBD_18");
//		noviceIds.add("com.zhuangbi.ZBD_38");
		noviceIds.add("10");
		noviceIds.add("20");

	}
}
