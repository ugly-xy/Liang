package com.zb.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.goods.BaseGoods;
import com.zb.models.lottery.Lottery;
import com.zb.models.lottery.LotteryLog;
import com.zb.models.lottery.LotteryResult;

@Service
public class LotteryService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(LotteryService.class);
	@Autowired
	UserPackService userPackService;

	public DBObject findById(Long id) {
		return super.findById(DocName.LOTTERY_CONFIG, id);
	}

	public ReMsg draw(Long lotteryId, Long uid) throws JsonParseException, JsonMappingException, IOException {
		Lottery lottery = DboUtil.toBean(findById(lotteryId), Lottery.class);
		if (lottery == null) {
			return new ReMsg(ReCode.FAIL);
		}

		if (lottery.getStatus() == Const.STATUS_OK) {
			long curTime = System.currentTimeMillis();
			if (curTime > lottery.getStartTime() && curTime < lottery.getEndTime()) {
				return new ReMsg(ReCode.OK, draw(lottery, uid));// 抽奖 TODO
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	private static Random r = new Random();
	private static Map<Long, Integer[]> drawMap = new HashMap<Long, Integer[]>();
	private static Map<Long, String[]> drawIdsMap = new HashMap<Long, String[]>();

	private LotteryResult draw(Lottery lottery, long uid) throws JsonParseException, JsonMappingException, IOException {
		Integer[] arr = getDrawArr(lottery);
		int len = arr.length;
		int rnum = r.nextInt(lottery.getDenominator());
		int idx = len;
		for (int i = 0; i < len; i++) {
			if (arr[i] > rnum) {
				idx = i;
				break;
			}
		}

		if (idx == len) {// 没有中奖
			LotteryLog ll = new LotteryLog(super.getNextId(DocName.LOGIN_LOG), lottery.get_id(), uid);
			getMongoTemplate().save(ll);
		} else {
			String id = drawIdsMap.get(lottery.get_id())[idx];
			Lottery.Item it = lottery.getItems().get(id);
			if (it.getLimit() <= 0 || it.getWinners() < it.getLimit()) {// 中奖成功
				LotteryLog ll = new LotteryLog(super.getNextId(DocName.LOGIN_LOG), lottery.get_id(), uid, id,
						it.getType(), it.getLinkId(), it.getAmount());
				getMongoTemplate().save(ll);
				if (BaseGoods.GoodsType.PROP.getT() == it.getType()) {
					//userPackService.addGoods(uid, it.getLinkId(), it.getAmount());
				} else {
					// TODO 增加物品之外的东西
				}

				it.setWinners(it.getWinners() + 1);
				lottery.getItems().put(id, it);
				save(lottery);
				return new LotteryResult(true, id, it.getName(), it.getPic());
			}
		}
		return new LotteryResult();
	}

	private Integer[] getDrawArr(Lottery lottery) {
		if (drawMap.containsKey(lottery.get_id())) {
			return drawMap.get(lottery.get_id());
		} else {
			int c = lottery.getItems().size();
			if (c == 0) {
				return null;
			}
			Integer[] draw = new Integer[c];
			String[] ids = new String[c];
			int i = 0;
			for (String key : lottery.getItems().keySet()) {
				Lottery.Item it = lottery.getItems().get(key);
				ids[i] = key;
				draw[i] = it.getRate();
				i++;
			}
			drawMap.put(lottery.get_id(), draw);
			drawIdsMap.put(lottery.get_id(), ids);
			return draw;
		}
	}

	public ReMsg save(String name, Long startTime, Long endTime, Integer status, Integer denominator) {
		Long id = super.getNextId(DocName.LOTTERY_CONFIG);
		Lottery g = new Lottery(id, name, startTime, endTime, status, denominator);
		return new ReMsg(save(g), g);
	}

	public ReCode save(Lottery lottery) {
		if (lottery != null) {
			getMongoTemplate().save(lottery);
			return ReCode.OK;
		} else {
			return ReCode.FAIL;
		}
	}

}
