package com.zb.service.image.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class CFShouYouZhanJiTool extends BaseTool implements ThreeDraw{
	public CFShouYouZhanJiTool(StorageService storageService) {
		super(storageService);
	}

	public CFShouYouZhanJiTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57f8d5150cf21bf18cd754ef";
		StorageService storageService = new StorageService();
		CFShouYouZhanJiTool tyt = new CFShouYouZhanJiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("娱乐社区","素材0.png","模板一",tmpPath0,  
				null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 10;
	static Color color = new Color(155,28, 45);
	static int fontType = Font.BOLD;
	
	static String ditu[] = {"http://imgzb.zhuangdianbi.com/57f8d4fb0cf21bf18cd754e4",
							"http://imgzb.zhuangdianbi.com/57f8d50b0cf21bf18cd754ec"};
	
	@Override
	public String draw(String one, String two, String three,String tmpBackPic, String count) throws IOException {
		
		SimplePositions nameSP =null;
		BufferedImage nameBi =null;
		BufferedImage p = null;
		SimplePositions pSP = null;
		BufferedImage p2 = null;
		SimplePositions pSP2 = null;
		
		
		if(three.equals("模板一")){
			//姓名
	 		nameSP = new SimplePositions();
	 		nameSP.buildHorizontalOffset(58).buildVerticalOffset(283);
	 		nameBi = drawText(one, zbfont, fontSize,
	 				color, 600, 100, 0, true);
			
			//玩家照片方
			p = super.compressImage(two, 40, 40);
			pSP = new SimplePositions();
			pSP.buildHorizontalOffset(18).buildVerticalOffset(285);
			
			//底图
			p2 = super.getImg(ditu[0]);
			pSP2 = new SimplePositions();
			pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		}else {
			//姓名
	 		nameSP = new SimplePositions();
	 		nameSP.buildHorizontalOffset(92).buildVerticalOffset(74);
	 		nameBi = drawText(one, zbfont.font(),fontType, fontSize,
	 				color, 600, 100, 0, true);
			
			//玩家照片方
			p = super.compressImage(two, 40, 40);
			pSP = new SimplePositions();
			pSP.buildHorizontalOffset(56).buildVerticalOffset(79);

			//底图
			p2 = super.getImg(ditu[1]);
			pSP2 = new SimplePositions();
			pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		}
		
		
		SimplePositions[] sp = {pSP,pSP2,nameSP };

		BufferedImage[] bis = { p,p2,nameBi };

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
