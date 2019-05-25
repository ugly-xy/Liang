package com.zb.service.room.robit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.Room;
import com.zb.models.room.machines.SlotMachines;
import com.zb.models.room.machines.SlotMachinesActor;
import com.zb.service.pay.CoinService;
import com.zb.service.room.SlotMachinesService;
import com.zb.socket.model.RoomId4Type;

@Service
public class SlotMachinesRobitService extends ActivityRobitService {
	static final Logger log = LoggerFactory.getLogger(SlotMachinesRobitService.class);

	@Autowired
	SlotMachinesService slotMachinesService;

	@Autowired
	CoinService coinService;

	int[] coins = { 10, 100 };

	@Override
	public ReMsg handle(Room r, long uid) {
		SlotMachines uc = super.getActivity(r.get_id(), SlotMachines.class);
		SlotMachinesActor dsa = super.getRoomActor(r.get_id(), uid, SlotMachinesActor.class);
		Set<SlotMachinesActor> dsas = super.getRoomActors(r.get_id(), SlotMachinesActor.class);
		int totalCnt = dsas.size();
		int rcnt = 0;
		for (SlotMachinesActor cdsa : dsas) {
			if (cdsa.isRobit()) {
				rcnt++;
			}
		}
		if (dsa == null) {
			if (rcnt < 5 && totalCnt < 20) {
				slotMachinesService.inRoom(uid, r, 0, true);
			} else {
				return null;
			}
		}
		if (SlotMachines.STATUS_BET == uc.getStatus()) {
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			int rn = RandomUtil.nextInt(8) + 1;
			int total = 0;
			for (int i = 0; i < rn; i++) {
				int t = coins[RandomUtil.nextInt(coins.length)];
				total = total + t;
				if (total < 300) {
					map.put(RandomUtil.nextInt(8) + 1, t);
				} else {
					break;
				}
			}
			// System.out.println("betting:" + uid);
			if (slotMachinesService.betting(RoomId4Type.SLOTMACHINES.getId(), uid, map) == ReCode.COIN_BALANCE_LOW
					.reCode()) {
				coinService.addCoin(uid, CoinLog.SLOT_MACHINES, 0, 5000, 10000, "机器人加币");
			}
			userRobitService.addJob(r.get_id(), uid, 30000);
		} else {
			if (RandomUtil.nextInt(10) < 3) {
				slotMachinesService.outRoom(uid, r);
				// System.out.println("outRoom:" + uid);
			} else {
				userRobitService.addJob(r.get_id(), uid, 10000);
			}
		}

		return new ReMsg(ReCode.FAIL);
	}

	public String getSpeak(String word) {
		DBCursor dbc = super.getC(DocName.UNDERCOVER_SPEAK_LOG).find(new BasicDBObject("word", word));
		int c = dbc.count();
		if (c > 0) {
			int s = RandomUtil.nextInt(c);
			DBObject dbo = dbc.skip(s).limit(1).toArray().get(0);
			return DboUtil.getString(dbo, "speak");
		}
		return null;
	}

}