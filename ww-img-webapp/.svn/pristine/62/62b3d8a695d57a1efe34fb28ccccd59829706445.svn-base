package com.zb.service.image.spoof;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.OneDraw;

public class EGaoJiuGongGeTool extends BaseTool implements OneDraw{
	public EGaoJiuGongGeTool(StorageService storageService) {
		super(storageService);
	}

	public EGaoJiuGongGeTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57a987250cf2e807b3034482";
		StorageService storageService = new StorageService();
		EGaoJiuGongGeTool tyt = new EGaoJiuGongGeTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材.png", tmpPath0,
				null));
	}
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		
		//玩家照片
		BufferedImage p = super.compressImage(one, 232, 232);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(244).buildVerticalOffset(0);
		
		
		SimplePositions[] sp = {pSP};

		BufferedImage[] bis = { p};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
