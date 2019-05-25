package com.zb.web.view.admin;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zb.service.cloud.StorageService;

@Controller
@RequestMapping("/admin")
public class UploadToolCtl {

	static final Logger log = LoggerFactory.getLogger(UploadToolCtl.class);
	@Autowired
	StorageService storageService;

	@RequestMapping("/upload")
	public String index() {
		return "admin/upload";
	}
	
	@RequestMapping("/uploadV")
	public String uploadV() {
		return "admin/uploadV";
	}


}
