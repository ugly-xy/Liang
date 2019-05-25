package com.we.web.view.m;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.we.core.web.ReMsg;
import com.we.service.cloud.StorageService;

@Controller
@RequestMapping("/file")
public class FileUploadCtl {

	@Autowired
	StorageService storageService;

	@ResponseBody
	@RequestMapping("/uPic")
	public ReMsg uploadPic(@RequestParam MultipartFile file, String ptoken, String timestamp,
			HttpServletRequest request, HttpServletResponse response) {
		return storageService.uploadPic(file, ptoken, timestamp);
	}

}
