package com.zb.service.image.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.OneDraw;

public class JingLingBaoKeMengTool extends BaseTool implements OneDraw{
	public JingLingBaoKeMengTool(StorageService storageService) {
		super(storageService);
	}

	public JingLingBaoKeMengTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5784b49e0cf2acc3f4e33142";
		StorageService storageService = new StorageService();
		JingLingBaoKeMengTool tyt = new JingLingBaoKeMengTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("模拟玩家照片.png", tmpPath0,null));
	}
	
	static String qiu = "http://imgzb.zhuangdianbi.com/5784b57a0cf2acc3f4e33203";
	static String[] jingling = {"http://imgzb.zhuangdianbi.com/5784b4e70cf2acc3f4e33182",
								"http://imgzb.zhuangdianbi.com/5784b4f20cf2acc3f4e3318e",
								"http://imgzb.zhuangdianbi.com/5784b4fd0cf2acc3f4e3319d",
								"http://imgzb.zhuangdianbi.com/5784b50a0cf2acc3f4e331ab",
								"http://imgzb.zhuangdianbi.com/5784b5140cf2acc3f4e331b6",
								"http://imgzb.zhuangdianbi.com/5784b5210cf2acc3f4e331c3"};
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.compressImage(one, 640, 1103);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		BufferedImage p2 = super.getImg(qiu);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		int len = jingling.length;
		Random ran = new Random();
		
		int num = ran.nextInt(len);
		
		BufferedImage p3 = super.getImg(jingling[num]);
		SimplePositions pSP3 = new SimplePositions();
		pSP3.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = {pSP,pSP2,pSP3};

		BufferedImage[] bis = { p,p2,p3};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
