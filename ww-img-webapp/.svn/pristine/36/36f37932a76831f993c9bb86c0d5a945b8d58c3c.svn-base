package com.zb.service.image.genius;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.*;
import com.jhlabs.image.PerspectiveFilter;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.OneDraw;

public class ShouNaZhaoPianTool extends BaseTool implements OneDraw{
	public ShouNaZhaoPianTool(StorageService storageService) {
		super(storageService);
	}

	public ShouNaZhaoPianTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/577f4c7c0cf2d6c18fb60076";
		StorageService storageService = new StorageService();
		ShouNaZhaoPianTool tyt = new ShouNaZhaoPianTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("测试.png", tmpPath0,null));
	}
	
	static final String shou ="http://imgzb.zhuangdianbi.com/577f4c7c0cf2d6c18fb60076";
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		
		
		
		//玩家照片
		BufferedImage p = super.compressImage(one, 500, 500);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		LensBlurFilter  bl = new LensBlurFilter();
		//bl.setEdgeAction(10);
		//bl.setKernel(kernel);
		
		p = bl.filter(p, null);
		
		//手照片
		BufferedImage p3 = super.compressImage(shou, 500, 500);
		SimplePositions pSP3 = new SimplePositions();
		pSP3.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		BufferedImage p2 = super.compressImage(one, 500, 500);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(98).buildVerticalOffset(125);
		
		//处理照片
		PerspectiveFilter pf = new PerspectiveFilter();
		pf.setCorners(0, 30, p2.getWidth()-257 , 24, p2.getWidth()-247,
				p2.getHeight()-221 , 7, p2.getHeight() -213);
		
		p2 = pf.filter(p2, null);
		
		SimplePositions[] sp = { pSP,pSP3,pSP2};

		BufferedImage[] bis = { p,p3,p2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
