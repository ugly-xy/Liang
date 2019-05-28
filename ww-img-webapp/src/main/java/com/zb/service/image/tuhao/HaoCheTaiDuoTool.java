package com.zb.service.image.tuhao;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.OneDraw;

public class HaoCheTaiDuoTool extends BaseTool implements OneDraw{
	public HaoCheTaiDuoTool(StorageService storageService) {
		super(storageService);
	}

	public HaoCheTaiDuoTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/582d4b450cf2be8e8e572663";
		StorageService storageService = new StorageService();
		HaoCheTaiDuoTool tyt = new HaoCheTaiDuoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("1.png", tmpPath0,null));
	}
	
	static String ditu = "http://imgzb.zhuangdianbi.com/582d4b330cf2be8e8e572662";
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		
		BufferedImage p = super.compressImage(one, 180, 300);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(255).buildVerticalOffset(323);
		
		BufferedImage p2 = super.getImg(ditu);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = { pSP,pSP2};

		BufferedImage[] bis = { p,p2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
