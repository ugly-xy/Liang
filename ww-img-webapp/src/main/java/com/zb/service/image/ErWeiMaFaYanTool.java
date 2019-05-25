package com.zb.service.image;

import java.awt.image.BufferedImage;

import com.zb.service.barcode.QRCodeUtil;
import com.zb.service.cloud.StorageService;

public class ErWeiMaFaYanTool extends BaseTool {

	public ErWeiMaFaYanTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		String tmpPath = "http://imgzb.zhuangdianbi.com/57132dc20cf2df2e739ea731";
		StorageService storageService = new StorageService();
		ErWeiMaFaYanTool tyt = new ErWeiMaFaYanTool(storageService);
		tyt.debug = true;
		tyt.drawImg("http://m.zhuangdianbi.com/downloads/app.xhtml", tmpPath);
	}

	/***
	 * @param ruguo
	 *            如果
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws Exception 
	 */
	public String drawImg(String name, String tmpPath) throws Exception {
		int left = 70;
		int height = 250;
		
		BufferedImage bi = QRCodeUtil.encodeBI(name);

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(height);
		
		SimplePositions[] sp = { nameSP };
		BufferedImage[] bis = { bi };
		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}

}
