package com.zb.service.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.common.redis.RK;
import com.zb.service.RecommendService;

public class RecommendJob {
	static final Logger LOGGER = LoggerFactory.getLogger(RecommendJob.class);

	@Autowired
	RecommendService recommendService;

	public void execute() {
		if (recommendService.lock(RK.recommendLock(), 30L)) {
			String flag = recommendService.getRedisTemplate().opsForValue().get(RK.recommendFlag());
			flag = null == flag ? "1" : flag;
			if ("1".equals(flag)) {
				// 1缓存使用中 ,刷新数据到2缓存
				recommendService.pushDate2Cache(2);
				// 切换2缓存为可用
				recommendService.getRedisTemplate().opsForValue().set(RK.recommendFlag(), "2");
			} else if ("2".equals(flag)) {
				recommendService.pushDate2Cache(1);
				recommendService.getRedisTemplate().opsForValue().set(RK.recommendFlag(), "1");
			}
			// 好友推荐
			recommendService.statisticsGameLog();
//			// 对战记录
//			recommendService.gameCPFightLog();
		}
	}

}
