package com.zb.service.jobs;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.common.redis.RK;
import com.zb.service.lottery.SpiderLotteryService;

public class SpiderLotteryJob {
	static final Logger LOGGER = LoggerFactory.getLogger(SpiderLotteryJob.class);

	@Autowired
	SpiderLotteryService spiderLotteryService;

	public void execute() throws IOException {
		if (spiderLotteryService.lock(RK.spiderLotteryLock(), 5L)) {
			try {
				String bj_next = spiderLotteryService.getRedisTemplate().opsForValue()
						.get(RK.nextQueryLotteryTime(SpiderLotteryService.CITY_BJ));
				String sh_next = spiderLotteryService.getRedisTemplate().opsForValue()
						.get(RK.nextQueryLotteryTime(SpiderLotteryService.CITY_SH));
				long now = System.currentTimeMillis();
				long bj = null == bj_next ? now : Long.parseLong(bj_next);
				long sh = null == sh_next ? now : Long.parseLong(sh_next);
				if (bj <= now) {
					spiderLotteryService.spiderLottery(SpiderLotteryService.CITY_BJ);
				}
				if (sh <= now) {
					spiderLotteryService.spiderLottery(SpiderLotteryService.CITY_SH);
				}
			} finally {
				spiderLotteryService.unlock(RK.spiderLotteryLock());
			}
		}
	}

}
