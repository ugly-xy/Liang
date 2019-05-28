package com.zb.service.image.genius;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.LensBlurFilter;
import com.jhlabs.image.PerspectiveFilter;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.TwoDraw;

public class FuDaiBiaoQianKaTool extends BaseTool implements TwoDraw{
	public FuDaiBiaoQianKaTool(StorageService storageService) {
		super(storageService);
	}

	public FuDaiBiaoQianKaTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57aaa4470cf2ce6b642d774c";
		StorageService storageService = new StorageService();
		FuDaiBiaoQianKaTool tyt = new FuDaiBiaoQianKaTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("测试.png","御姐型", tmpPath0,
				null));
	}
	
	static String fengmian = "http://imgzb.zhuangdianbi.com/57aaa2d70cf2ce6b642d74fd";
	static String biaoqian[] = {"http://imgzb.zhuangdianbi.com/57aaa3620cf2ce6b642d75ae",
								"http://imgzb.zhuangdianbi.com/57aaa3e80cf2ce6b642d76d2",
								"http://imgzb.zhuangdianbi.com/57aaa4000cf2ce6b642d76e6",
								"http://imgzb.zhuangdianbi.com/57aaa4100cf2ce6b642d76ff",
								"http://imgzb.zhuangdianbi.com/57aaa41c0cf2ce6b642d770e",
								"http://imgzb.zhuangdianbi.com/57aaa4270cf2ce6b642d7714"};
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//模糊玩家照片
		BufferedImage p = super.compressImage(one, 720, 1058);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		LensBlurFilter  bl = new LensBlurFilter();
		p = bl.filter(p, null);
		
		//背景照片
		BufferedImage p2 = super.compressImage(fengmian, 720, 1058);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		//玩家照片
		BufferedImage p3 = super.compressImage(one, 490, 626);
		SimplePositions pSP3 = new SimplePositions();
		pSP3.buildHorizontalOffset(112).buildVerticalOffset(140);
		
		PerspectiveFilter pf = new PerspectiveFilter();
		pf.setCorners(21, -19, p3.getWidth() +22, -7, p3.getWidth(),
				p3.getHeight()+1 , -2, p3.getHeight() - 23);
		p3 = pf.filter(p3, null);
		
		BufferedImage p4 = null;
		SimplePositions pSP4 = null;
		
		if(two.equals("富豪型")){
			p4 = super.compressImage(biaoqian[0], 720, 1058);
			pSP4 = new SimplePositions();
			pSP4.buildHorizontalOffset(0).buildVerticalOffset(0);
			
		}else if(two.equals("鬼祟型")){
			p4 = super.compressImage(biaoqian[1], 720, 1058);
			pSP4 = new SimplePositions();
			pSP4.buildHorizontalOffset(0).buildVerticalOffset(0);
		}else if(two.equals("萝莉型")){
			p4 = super.compressImage(biaoqian[2], 720, 1058);
			pSP4 = new SimplePositions();
			pSP4.buildHorizontalOffset(0).buildVerticalOffset(0);
		}else if(two.equals("上流型")){
			p4 = super.compressImage(biaoqian[3], 720, 1058);
			pSP4 = new SimplePositions();
			pSP4.buildHorizontalOffset(0).buildVerticalOffset(0);
		}else if(two.equals("御姐型")){
			p4 = super.compressImage(biaoqian[4], 720, 1058);
			pSP4 = new SimplePositions();
			pSP4.buildHorizontalOffset(0).buildVerticalOffset(0);
		}else{
			p4 = super.compressImage(biaoqian[5], 720, 1058);
			pSP4 = new SimplePositions();
			pSP4.buildHorizontalOffset(0).buildVerticalOffset(0);
		}
		
		
		SimplePositions[] sp = {pSP,pSP3,pSP2,pSP4};

		BufferedImage[] bis = { p,p3,p2,p4};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
