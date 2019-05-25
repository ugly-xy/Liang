package com.zb.service.task;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.models.TitleModel;
import com.zb.models.UserTitle;
import com.zb.models.finance.Order;
import com.zb.service.BaseService;
import com.zb.service.MessageService;
import com.zb.service.OrderService;
import com.zb.service.RechargeShoutService;
import com.zb.service.UserPackService;
import com.zb.service.UserService;

@Aspect
@Component
public class OrderInterceptor extends BaseService {

	@Autowired
	UserService userService;
	@Autowired
	RechargeShoutService rechargeShoutService;
	@Autowired
	MessageService messageService;
	@Autowired
	UserPackService userPackService;
	@Autowired
	OrderService orderService;

	public static final int MIN_168 = 168;
	public static final int MIN_388 = 388;

	@AfterReturning(pointcut = "@annotation(oa)", returning = "ret")
	public void rechargeShout(JoinPoint jp, OrderAspect oa, Object ret) throws Throwable {
		ReMsg rm = (ReMsg) ret;
		// rm.getCode() == ReCode.ORDER_OPENED.reCode() ||
		if (rm.getCode() == ReCode.OK.reCode()) {// 成功开通
			Order o = (Order) rm.getData();
			long uid = o.getRecUid();
			int amount = o.getAmount();
			amount = amount > MIN_388 ? amount / 100 : amount;
			vipPay(uid, amount);
			if (amount >= MIN_168) {
				userService.saveTitle(uid, amount, UserTitle.COIN);
				if (amount >= MIN_388) {
					rechargeShoutService.realShout(uid, amount);
				}
			}
		}
	}

	void vipPay(long uid, int money) {
		int flower = 0;
		int point = 0;
		for (Integer e : VIPUp.map.keySet()) {
			if (e == money) {
				flower = VIPUp.map.get(e).getFlower();
				point = VIPUp.map.get(e).getPoint();
				if (flower == 0 && point == 0) {// 低档礼包
					messageService.imMsgHandler(Const.SYSTEM_ID, uid, "恭喜您成功充值" + money + "元游戏礼包，已发送您的至账户，请注意查收！", null,
							null);
				} else {// 168 388
					flower = VIPUp.map.get(e).getFlower();
					point = VIPUp.map.get(e).getPoint();
					TitleModel model = TitleModel.getCoinModel(money);
					if (null != model) {
						messageService.imMsgHandler(Const.SYSTEM_ID,
								uid, "恭喜您成功充值" + money + "元游戏礼包，额外赠送您" + point + "经验，" + flower + "朵鲜花，\""
										+ model.getName() + "\"称号，称号有效期为" + model.getVal() + "天，已发送您的至账户，请注意查收！",
								null, null);
					}
				}
				break;
			}
		}
	}

	public enum VIPUp {
		_6(6, 0, 0), _18(18, 0, 0), _68(68, 0, 0), _168(168, 6666, 888), _388(388, 16666, 1888);
		VIPUp(int money, int point, int flower) {
			this.money = money;
			this.point = point;
			this.flower = flower;
		}

		private int money;
		private int point;
		private int flower;

		public int getMoney() {
			return money;
		}

		public int getPoint() {
			return point;
		}

		public int getFlower() {
			return flower;
		}

		@SuppressWarnings({ "all" })
		public static final Map<Integer, VIPUp> map = new HashMap() {
			{
				for (VIPUp e : EnumSet.allOf(VIPUp.class)) {
					put(e.getMoney(), e);
				}
			}
		};
	}

}
