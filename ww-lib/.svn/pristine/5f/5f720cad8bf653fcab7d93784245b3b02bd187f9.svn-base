package com.zb.service.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.common.redis.RK;
import com.zb.service.room.WerewolfKillService;

public class WolfRankListJob {
	static final Logger LOGGER = LoggerFactory.getLogger(WolfRankListJob.class);

	@Autowired
	WerewolfKillService werewolfKillService;

	public void execute() {
		if (werewolfKillService.lock(RK.wolfRankListLock(), 30L)) {
			String flag = werewolfKillService.getRedisTemplate().opsForValue().get(RK.wolfRankListFlag());
			flag = null == flag ? "1" : flag;
			if ("1".equals(flag)) {
				// 1缓存使用中 ,刷新数据到2缓存
				werewolfKillService.calRankList(2);
				// 切换2缓存为可用
				werewolfKillService.getRedisTemplate().opsForValue().set(RK.wolfRankListFlag(), "2");
			} else if ("2".equals(flag)) {
				werewolfKillService.calRankList(1);
				werewolfKillService.getRedisTemplate().opsForValue().set(RK.wolfRankListFlag(), "1");
			}
		}
	}

}
