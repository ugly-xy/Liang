package com.zb.service.image.spoof;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.OneDraw;

public class LanShouXiangGuTool extends BaseTool implements OneDraw{
	public LanShouXiangGuTool(StorageService storageService) {
		super(storageService);
	}

	public LanShouXiangGuTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57fe25530cf2cf417cd55ee2";
		StorageService storageService = new StorageService();
		LanShouXiangGuTool tyt = new LanShouXiangGuTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材2.png",tmpPath0,null));//243 185
	}
	
	static String ditu = "http://imgzb.zhuangdianbi.com/57fe25450cf2cf417cd55edd";
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.compressImage(one, 180, 220);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(220).buildVerticalOffset(109);
		
		//玩家照片
		BufferedImage p2 = super.getImg(ditu);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = { pSP,pSP2};

		BufferedImage[] bis = { p,p2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
