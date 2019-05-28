package com.zb.service.jobs;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.common.redis.RK;
import com.zb.service.UserService;

public class CleanTitleJob {
	static final Logger LOGGER = LoggerFactory.getLogger(CleanTitleJob.class);

	@Autowired
	UserService userService;

	public void execute() throws IOException {
		if (userService.lock(RK.cleanTitleLock(), 100L)) {
			userService.cleanTitle();
		}
	}
}
