package com.zb.service.image.i;

import java.io.IOException;

import com.zb.service.cloud.StorageService;

public interface FiveStyleDraw {

	String draw(String one, String two, String three, String four, String five,
			String style, String tmpBackPic,String count) throws IOException;

	void setStorageService(StorageService storageService);
}
