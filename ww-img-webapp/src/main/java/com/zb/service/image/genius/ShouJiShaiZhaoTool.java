package com.zb.service.image.genius;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.LensBlurFilter;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.OneDraw;

public class ShouJiShaiZhaoTool extends BaseTool implements OneDraw{
	public ShouJiShaiZhaoTool(StorageService storageService) {
		super(storageService);
	}

	public ShouJiShaiZhaoTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/579c4d5b0cf2ef699f619d43";
		StorageService storageService = new StorageService();
		ShouJiShaiZhaoTool tyt = new ShouJiShaiZhaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材2.png", tmpPath0,null));
	}
	
	static String ditu = "http://imgzb.zhuangdianbi.com/579c4d0f0cf2ef699f619d00";
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.compressImage(one, 540, 374);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		LensBlurFilter  bl = new LensBlurFilter();
		p = bl.filter(p, null);
		
		BufferedImage p2 = super.compressImage(one, 115, 83);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(218).buildVerticalOffset(153);
		
		BufferedImage p3 = super.getImg(ditu);
		SimplePositions pSP3 = new SimplePositions();
		pSP3.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = { pSP,pSP2,pSP3};

		BufferedImage[] bis = {p, p2,p3};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
