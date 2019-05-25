package com.zb.service.image.genius;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.OneDraw;

public class GeiLiYouFangQiNiTool extends BaseTool implements OneDraw{
	public GeiLiYouFangQiNiTool(StorageService storageService) {
		super(storageService);
	}

	public GeiLiYouFangQiNiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/578a1ac90cf2c6a36b8209af";
		StorageService storageService = new StorageService();
		GeiLiYouFangQiNiTool tyt = new GeiLiYouFangQiNiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("测试.png", tmpPath0,null));
	}
	
	static String feng = "http://imgzb.zhuangdianbi.com/578a1ab70cf2c6a36b820995";
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		
		BufferedImage fftBI = super.compressImage(one, 246, 313);
		SimplePositions fftSP = new SimplePositions();
		fftSP.buildHorizontalOffset(171).buildVerticalOffset(24);
		
		BufferedImage fftBI2 = super.getImg(feng);
		SimplePositions fftSP2 = new SimplePositions();
		fftSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = { fftSP,fftSP2};

		BufferedImage[] bis = { fftBI,fftBI2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
