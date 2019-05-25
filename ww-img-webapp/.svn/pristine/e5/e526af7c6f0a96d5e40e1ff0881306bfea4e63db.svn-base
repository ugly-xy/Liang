package com.zb.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.zb.common.Constant.ReCode;
import com.zb.common.crypto.MDUtil;
import com.zb.core.web.ReMsg;
import com.zb.service.cloud.StorageService;

@Controller
@RequestMapping(value = "/sys")
public class UploadCtl extends BaseCtl {
	static final Logger log = LoggerFactory.getLogger(UploadCtl.class);

	@Autowired
	StorageService storageService;

	// @RequestMapping("/index")
	// public ModelAndView test() {
	// return new ModelAndView("test/upload");
	// }

	private static final String ADM_TOKEN = "Zdb123Abc456";
	private static final String MD5_KEY = "zbda12ddcc";

	@ResponseBody
	@RequestMapping("/uPic")
	public ReMsg uploadPic(@RequestParam MultipartFile file, String ptoken, String timestamp,
			HttpServletRequest request, HttpServletResponse response) {
		String sign = MDUtil.MD5.digest2HEX(MD5_KEY + timestamp);
		if (!sign.equals(ptoken)) {
			return new ReMsg(ReCode.FAIL);
		}
		return storageService.uploadZim(file, request, response);
	}

	@ResponseBody
	@RequestMapping("/uVideo")
	public ReMsg uploadVideo(@RequestParam MultipartFile file, String ptoken, String timestamp,
			HttpServletRequest request, HttpServletResponse response) {
		return storageService.uploadVideo(file, request, response);
	}

	@ResponseBody
	@RequestMapping("/uAudio")
	public ReMsg uploadAudio(@RequestParam MultipartFile file, String ptoken, String timestamp,
			HttpServletRequest request, HttpServletResponse response) {
		return storageService.uploadAudio(file, request, response);
	}

	@ResponseBody
	@RequestMapping("/admPic")
	public ReMsg uploadAdmPic(@RequestParam MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		// String token =request.getParameter("token");
		return storageService.upload(file, false, request, response);
	}

	@ResponseBody
	@RequestMapping({ "/uBinary" })
	public ReMsg uploadBinaryPic(HttpServletRequest request, HttpServletResponse response) {
		return this.storageService.upload(false, request, response);
	}

	@ResponseBody
	@RequestMapping({ "/admPicZim" })
	public ReMsg uploadAdmPicZim(@RequestParam MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		return this.storageService.uploadZim(file, request, response);
	}

	@ResponseBody
	@RequestMapping("/uTmpPic")
	public ReMsg uploadTmpPic(@RequestParam MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		// if (super.getUid() < 1) {
		// return new ReMsg(ReCode.FAIL);
		// }
		return storageService.upload(file, true, request, response);
	}

	@ResponseBody
	@RequestMapping("/uResPic")
	public ReMsg uploadResPic(@RequestParam MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		return storageService.upload(file, false, request, response);

	}

	@ResponseBody
	@RequestMapping("/check")
	public String check() {
		return "ok";
	}

}
