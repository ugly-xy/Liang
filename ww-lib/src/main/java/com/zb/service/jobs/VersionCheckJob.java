package com.zb.service.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.service.VersionUpdateService;

//@Service
public class VersionCheckJob {

	static final Logger log = LoggerFactory.getLogger(VersionCheckJob.class);

	@Autowired
	VersionUpdateService versionUpdateService;

	public void execute() {
		versionUpdateService.checkIosVersion();
	}

}
