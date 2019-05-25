package com.zb.service.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.common.redis.RK;
import com.zb.service.othergames.GuessService;

public class GuessDrawJob {
	static final Logger LOGGER = LoggerFactory.getLogger(GuessDrawJob.class);

	@Autowired
	GuessService guessService;

	public void execute() {
		if (guessService.lock(RK.guessDrawLock(), 30L)) {
			// 自动退保证金
			guessService.aotuSettlement();
		}
	}

}
