package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.FiveDraw;

public class WeiXinWeiShangTool extends BaseTool implements FiveDraw{
	public WeiXinWeiShangTool(StorageService storageService) {
		super(storageService);
	}

	public WeiXinWeiShangTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57e511cf0cf2e4b4a580e937";
		StorageService storageService = new StorageService();
		WeiXinWeiShangTool tyt = new WeiXinWeiShangTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点比","zhuangdianbi", "qq素材2.png","装逼恶搞玩具装逼恶搞玩具装逼恶搞玩具装逼恶搞玩具装逼恶搞玩具","素材0.png",tmpPath0,null));// 720 560
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 60;
	static Color color = new Color(90, 47, 126);
	static int fontType = Font.BOLD;
	
	static String ditu = "http://imgzb.zhuangdianbi.com/57e511bd0cf2e4b4a580e930";
	
	@Override
	public String draw(String one, String two, String three, String four, String five, String tmpBackPic, String count)
			throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(26).buildVerticalOffset(430);
		BufferedImage nameBI = drawText(one, zbfont.font(),fontType, fontSize, color,
				300, 200, 0, true);
		
		//底图
		BufferedImage p = super.getImg(ditu);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		//底图
		BufferedImage p2 = super.compressImage(three, 720, 560);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		//微信号
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(26).buildVerticalOffset(500);
		BufferedImage nameBI2 = drawText("微信:"+two, ZbFont.新蒂小丸子小学版, 44, color,
				700, 200, 0, true);
		
		//主要产品
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(26).buildVerticalOffset(630);
		BufferedImage nameBI3 = drawText("主要产品:", zbfont.font(),fontType, 40, color,
				300, 200, 0, true);
		
		//产品名称
		SimplePositions nameSP4 = new SimplePositions();
		nameSP4.buildHorizontalOffset(215).buildVerticalOffset(631);
		BufferedImage nameBI4 = drawText(four, zbfont, 38, color,
				480, 200, 0, true);
		
		//底图
		BufferedImage p3 = super.compressImage(five, 340, 340);
		SimplePositions pSP3 = new SimplePositions();
		pSP3.buildHorizontalOffset(19).buildVerticalOffset(816);

		SimplePositions[] sp = {pSP2,pSP,nameSP,nameSP2,nameSP3,nameSP4,pSP3};

		BufferedImage[] bis = {p2, p,nameBI,nameBI2,nameBI3,nameBI4,p3 };

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
