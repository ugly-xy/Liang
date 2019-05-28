package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class BuXiangShuoHuaTool extends BaseTool implements ThreeDraw{
	public BuXiangShuoHuaTool(StorageService storageService) {
		super(storageService);
	}

	public BuXiangShuoHuaTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57fb464b0cf24bc79b620ecf";
		StorageService storageService = new StorageService();
		BuXiangShuoHuaTool tyt = new BuXiangShuoHuaTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("娱乐社区","素材1.png","大便",tmpPath0,  
				null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 18;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String three, String tmpBackPic, String count) throws IOException {
		//你的姓名
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(127-(one.length()+6)*fontSize/2).buildVerticalOffset(195);
 		BufferedImage nameBi = drawText(one+"不想跟你说话", zbfont, fontSize,
 				color, 600, 100, 0, true);
 		
 		//照片内容
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(127-(three.length()+6)*fontSize/2).buildVerticalOffset(222);
 		BufferedImage nameBi2 = drawText("并向你扔来了"+three, zbfont, fontSize,
 				color, 600, 100, 0, true);
 		//玩家照片
		BufferedImage p = super.compressImage(two, 135, 98);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(59).buildVerticalOffset(8);
 				
 		SimplePositions[] sp = {nameSP,nameSP2,pSP};

		BufferedImage[] bis = {nameBi,nameBi2,p};
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
