package com.zb.service.pay;

import java.util.ArrayList;
import java.util.List;

import com.zb.models.finance.Order;
import com.zb.models.finance.PayProduct;
import com.zb.service.BaseService;

public class PayProductService extends BaseService {

	private static List<PayProduct> apples = new ArrayList<PayProduct>();

	private static List<PayProduct> others = new ArrayList<PayProduct>();

	// private static List<PayProduct> oldApples = new ArrayList<PayProduct>();
	//
	// private static List<PayProduct> oldOthers = new ArrayList<PayProduct>();

	public static PayProduct getPayProduct(String payType, String id) {
		if (Order.THIRD_APPLE_PAY_APP.equals(payType)) {
			for (PayProduct pp : apples) {
				if (pp.getId().equals(id)) {
					return pp;
				}
			}
		} else {
			for (PayProduct pp : others) {
				if (pp.getId().equals(id)) {
					return pp;
				}
			}
		}
		return null;
	}

	// public static PayProduct getOldPayProduct(String payType, String id) {
	// if (Order.THIRD_APPLE_PAY_APP.equals(payType)) {
	// for (PayProduct pp : oldApples) {
	// if (pp.getId().equals(id)) {
	// return pp;
	// }
	// }
	// } else {
	// for (PayProduct pp : oldOthers) {
	// if (pp.getId().equals(id)) {
	// return pp;
	// }
	// }
	// }
	// return null;
	// }
	//
	public static List<PayProduct> getApples() {
		return apples;
	}

	public static List<PayProduct> getOthers() {
		return others;
	}

	// oldApples.add(new PayProduct("com.zhuangbi.ZBD_6", "", 600, 6000,
	// PayProduct.LIMIT_0, PayProduct.SEND_TYPE_NOTHING, 0));
	// oldApples.add(new PayProduct("com.zhuangbi.ZBD_18", "加赠600", 1800, 18000,
	// PayProduct.LIMIT_0, PayProduct.SEND_TYPE_AMOUNT, 600));
	// oldApples.add(new PayProduct("com.zhuangbi.ZBD_30", "加赠1500", 3000,
	// 30000,
	// PayProduct.LIMIT_0, PayProduct.SEND_TYPE_PROCENTTAGE, 5));
	// oldApples.add(new PayProduct("com.zhuangbi.ZBD_68", "加赠6800", 6800,
	// 68000,
	// PayProduct.LIMIT_0, PayProduct.SEND_TYPE_PROCENTTAGE, 10));
	// oldApples.add(new PayProduct("com.zhuangbi.ZBD_128", "加赠19200", 12800,
	// 128000, PayProduct.LIMIT_0, PayProduct.SEND_TYPE_PROCENTTAGE, 15));
	// oldApples.add(new PayProduct("com.zhuangbi.ZBD_298", "加赠59600", 29800,
	// 298000, PayProduct.LIMIT_0, PayProduct.SEND_TYPE_PROCENTTAGE, 20));
	//
	// oldOthers.add(new PayProduct("6", "", 600, 6000, PayProduct.LIMIT_0,
	// PayProduct.SEND_TYPE_NOTHING, 0));
	// oldOthers.add(new PayProduct("18", "加赠600金币", 1800, 18000,
	// PayProduct.LIMIT_0,
	// PayProduct.SEND_TYPE_AMOUNT, 600));
	// oldOthers.add(new PayProduct("30", "加赠1500金币", 3000, 30000,
	// PayProduct.LIMIT_0, PayProduct.SEND_TYPE_PROCENTTAGE, 5));
	// oldOthers.add(new PayProduct("68", "加赠6800金币", 6800, 68000,
	// PayProduct.LIMIT_0,
	// PayProduct.SEND_TYPE_PROCENTTAGE, 10));
	// oldOthers.add(new PayProduct("128", "加赠19200金币", 12800, 128000,
	// PayProduct.LIMIT_0, PayProduct.SEND_TYPE_PROCENTTAGE, 15));
	// oldOthers.add(new PayProduct("298", "加赠59600金币", 29800, 298000,
	// PayProduct.LIMIT_0, PayProduct.SEND_TYPE_PROCENTTAGE, 20));
	//
	static {
		apples.add(new PayProduct("com.zhuangbi.ZBD_6", "", 600, 3000, PayProduct.LIMIT_0, PayProduct.SEND_TYPE_NOTHING,
				0, 0, 0));
		apples.add(new PayProduct("com.zhuangbi.ZBD_18", "", 1800, 9000, PayProduct.LIMIT_0,
				PayProduct.SEND_TYPE_NOTHING, 0, 0, 0));
		apples.add(new PayProduct("com.zhuangbi.ZBD_68", "", 6800, 34000, PayProduct.LIMIT_0,
				PayProduct.SEND_TYPE_NOTHING, 0, 0, 0));
//		apples.add(new PayProduct("com.zhuangbi.ZBD_128", "", 12800, 64000, PayProduct.LIMIT_0,
//				PayProduct.SEND_TYPE_NOTHING, 0, 6666, 888));
//		apples.add(new PayProduct("com.zhuangbi.ZBD_298", "", 29800, 149000, PayProduct.LIMIT_0,
//				PayProduct.SEND_TYPE_NOTHING, 0, 16666, 1888));

		 apples.add(new PayProduct("com.zhuangbi.ZBD_168", "", 16800, 84000,
		 PayProduct.LIMIT_0,
		 PayProduct.SEND_TYPE_NOTHING, 0, 6666, 888));
		 apples.add(new PayProduct("com.zhuangbi.ZBD_388", "", 38800, 194000,
		 PayProduct.LIMIT_0,
		 PayProduct.SEND_TYPE_NOTHING, 0, 16666, 1888));

		others.add(new PayProduct("6", "", 600, 3000, PayProduct.LIMIT_0, PayProduct.SEND_TYPE_NOTHING, 0, 0, 0));
		others.add(new PayProduct("18", "", 1800, 9000, PayProduct.LIMIT_0, PayProduct.SEND_TYPE_NOTHING, 0, 0, 0));
		others.add(new PayProduct("68", "", 6800, 34000, PayProduct.LIMIT_0, PayProduct.SEND_TYPE_NOTHING, 0, 0, 0));
		others.add(new PayProduct("168", "", 16800, 84000, PayProduct.LIMIT_0, PayProduct.SEND_TYPE_NOTHING, 0, 6666,
				888));
		others.add(new PayProduct("388", "", 38800, 194000, PayProduct.LIMIT_0, PayProduct.SEND_TYPE_NOTHING, 0, 16666,
				1888));
		// others.add(new PayProduct("588", "加赠117600金币", 58800, 588000,
		// PayProduct.LIMIT_0, PayProduct.SEND_TYPE_PROCENTTAGE, 20));
	}
}
