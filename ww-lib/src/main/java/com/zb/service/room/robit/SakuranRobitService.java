package com.zb.service.room.robit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.common.Constant.ReCode;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.Room;
import com.zb.models.room.activity.SakuranActor;
import com.zb.models.room.machines.Sakuran;
import com.zb.service.pay.CoinService;
import com.zb.service.room.SakuranService;
import com.zb.socket.model.RoomId4Type;

@Service
public class SakuranRobitService extends ActivityRobitService {
	static final Logger log = LoggerFactory.getLogger(SakuranRobitService.class);

	@Autowired
	SakuranService sakuranService;

	@Autowired
	CoinService coinService;

	int[] coins = { 10, 100 };

	@Override
	public ReMsg handle(Room r, long uid) {
		Sakuran uc = super.getActivity(r.get_id(), Sakuran.class);
		SakuranActor dsa = super.getRoomActor(r.get_id(), uid, SakuranActor.class);
		Set<SakuranActor> dsas = super.getRoomActors(r.get_id(), SakuranActor.class);
		int totalCnt = dsas.size();
		int rcnt = 0;
		for (SakuranActor cdsa : dsas) {
			if (cdsa.isRobit()) {
				rcnt++;
			}
		}
		if (dsa == null) {
			if (rcnt < 5 && totalCnt < 30) {
				sakuranService.inRoom(uid, r, 0, true);
			} else {
				return null;
			}
		}
		if (Sakuran.STATUS_BET == uc.getStatus() || Sakuran.STATUS_SETTLEMENT == uc.getStatus()
				|| Sakuran.STATUS_ROTATE == uc.getStatus()) {
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			int rn = RandomUtil.nextInt(8) + 1;
			int total = 0;
			for (int i = 0; i < rn; i++) {
				int t = coins[RandomUtil.nextInt(coins.length)];
				total = total + t;
				if (total < 10000) {
					map.put(RandomUtil.nextInt(8) + 1, t);
				} else {
					break;
				}
			}
			boolean temp = true;
			while (temp) {
				if (sakuranService.betting(RoomId4Type.SAKURAN.getId(), uid, map) == ReCode.COIN_BALANCE_LOW.reCode()) {
					coinService.addCoin(uid, CoinLog.SAKURAN, 0, 100000, 0, "机器人加币");
				} else {
					temp = false;
				}
			}
			userRobitService.addJob(r.get_id(), uid, 30000);
		} else {
			if (RandomUtil.nextInt(10) < 3) {
				sakuranService.outRoom(uid, r);
				// System.out.println("outRoom:" + uid);
			} else {
				userRobitService.addJob(r.get_id(), uid, 10000);
			}
		}

		return new ReMsg(ReCode.FAIL);
	}

}