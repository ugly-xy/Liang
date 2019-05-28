package com.zb.service.image.genius;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.RotateFilter;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.OneDraw;

public class ZhaoPianYinBeiJingTool extends BaseTool implements OneDraw{
	public ZhaoPianYinBeiJingTool(StorageService storageService) {
		super(storageService);
	}

	public ZhaoPianYinBeiJingTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5779de4c0cf26acda3d8e845";
		StorageService storageService = new StorageService();
		ZhaoPianYinBeiJingTool tyt = new ZhaoPianYinBeiJingTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("5913302.png", tmpPath0,null));
	}
	
	static final String shou ="http://imgzb.zhuangdianbi.com/5779de670cf26acda3d8e85c";
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		
		//玩家照片
		BufferedImage p = super.compressImage(one, 591, 330);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		//手照片
		BufferedImage p3 = super.compressImage(shou, 591, 330);
		SimplePositions pSP3 = new SimplePositions();
		pSP3.buildHorizontalOffset(0).buildVerticalOffset(0);
		//处理成黑白倾斜
		BufferedImage bi = super.getImg(one);
		int w = bi.getWidth();
		int h = bi.getHeight();
		w=191;
		h=150;
		bi =super.compressImage(one, w, h);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(329).buildVerticalOffset(72);
		
		GrayscaleFilter dog =new GrayscaleFilter();
		bi = dog.filter(bi, null);
		
		RotateFilter rf = new RotateFilter();
		rf.setAngle(0.035f);
		bi = rf.filter(bi, null);
		
		SimplePositions[] sp = { pSP,pSP3 ,pSP2};

		BufferedImage[] bis = { p,p3,bi};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
