package com.zb.service.image.i;

import java.io.IOException;

import com.zb.service.cloud.StorageService;

public interface OneDraw {

	String draw(String one, String tmpBackPic,String count) throws IOException;

	void setStorageService(StorageService storageService);

}
