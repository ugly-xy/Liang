package com.zb.service.image.spoof;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.OneDraw;

public class DanShenGouLiangOneTool extends BaseTool implements OneDraw	{
	public DanShenGouLiangOneTool(StorageService storageService) {
		super(storageService);
	}

	public DanShenGouLiangOneTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57b15de30cf2464bcace68ca";
		StorageService storageService = new StorageService();
		DanShenGouLiangOneTool tyt = new DanShenGouLiangOneTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("单身狗1.png",tmpPath0,null));//243 185
	}
	
	static String ditu = "http://imgzb.zhuangdianbi.com/57b15dfa0cf2464bcace68e5";
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.compressImage(one, 243, 185);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(132).buildVerticalOffset(378);
		
		//玩家照片
		BufferedImage p2 = super.getImg(ditu);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = { pSP,pSP2};

		BufferedImage[] bis = { p,p2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
