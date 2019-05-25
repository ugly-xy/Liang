package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class XiaoXiongTieZhiTool extends BaseTool implements TwoDraw{
	public XiaoXiongTieZhiTool(StorageService storageService) {
		super(storageService);
	}

	public XiaoXiongTieZhiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57970d6c0cf22a4dda591eff";
		StorageService storageService = new StorageService();
		XiaoXiongTieZhiTool tyt = new XiaoXiongTieZhiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材2.png","一直想做个安静的人，守护好那最初的萌动。", tmpPath0,null));
	}
	//我要回去的那个地方，有一辈子不分开的人。     时光是台滚筒洗衣机，却洗不掉你的孩子气。      曾经那么想念那个人，而如今却已各安天涯。   一直想做个安静的人，守护好那最初的萌动。
	static String fengmian = "http://imgzb.zhuangdianbi.com/57970d6c0cf22a4dda591eff"; 
	
	static ZbFont zbfont = ZbFont.华康少女;
	static int fontSize = 26;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one,String two, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.compressImage(one, 415, 580);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(11).buildVerticalOffset(200);
		//封面
		BufferedImage p2 = super.getImg(fengmian);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		//文字
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(100).buildVerticalOffset(575);
 		BufferedImage nameBi = drawText(two, zbfont, fontSize,
 				color, 280, 0, 0, true);
		
		
		SimplePositions[] sp = {pSP,pSP2,nameSP };

		BufferedImage[] bis = { p,p2,nameBi};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
