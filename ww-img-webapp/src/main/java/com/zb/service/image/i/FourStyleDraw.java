package com.zb.service.image.i;

import java.io.IOException;

import com.zb.service.cloud.StorageService;

public interface FourStyleDraw {

	String draw(String one, String two, String three, String four,
			String style, String tmpBackPic,String count) throws IOException;

	void setStorageService(StorageService storageService);
}
