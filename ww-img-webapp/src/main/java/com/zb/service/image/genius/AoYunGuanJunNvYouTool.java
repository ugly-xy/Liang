package com.zb.service.image.genius;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.TwoDraw;

public class AoYunGuanJunNvYouTool extends BaseTool implements TwoDraw{
	public AoYunGuanJunNvYouTool(StorageService storageService) {
		super(storageService);
	}

	public AoYunGuanJunNvYouTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57a963a90cf2e807b302e75b";
		StorageService storageService = new StorageService();
		AoYunGuanJunNvYouTool tyt = new AoYunGuanJunNvYouTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材3.png","女", tmpPath0,  
				null));
	}
	
	static String tu[]  = {"http://imgzb.zhuangdianbi.com/57a9638c0cf2e807b302e717",
							"http://imgzb.zhuangdianbi.com/57a9639a0cf2e807b302e744"};
	@Override
	public String draw(String one, String two,String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.compressImage(one, 720, 1080);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		BufferedImage p2 = null;
		SimplePositions pSP2 =null;
		
		//底图照片
		if(two.equals("男")){
			p2 = super.compressImage(tu[0], 720, 1080);
			pSP2 = new SimplePositions();
			pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		}else{
			p2 = super.compressImage(tu[1], 720, 1080);
			pSP2 = new SimplePositions();
			pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		}
		
		
		SimplePositions[] sp = { pSP,pSP2};

		BufferedImage[] bis = { p,p2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
