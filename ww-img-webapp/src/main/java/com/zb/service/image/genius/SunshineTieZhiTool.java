package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class SunshineTieZhiTool extends BaseTool implements TwoDraw{
	public SunshineTieZhiTool(StorageService storageService) {
		super(storageService);
	}

	public SunshineTieZhiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/579717ee0cf22a4dda592890";
		StorageService storageService = new StorageService();
		SunshineTieZhiTool tyt = new SunshineTieZhiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材.png","时光是台滚筒洗衣机，却洗不掉你的孩子气。", tmpPath0,null));
	}
	//我要回去的那个地方，有一辈子不分开的人。     时光是台滚筒洗衣机，却洗不掉你的孩子气。      曾经那么想念那个人，而如今却已各安天涯。   一直想做个安静的人，守护好那最初的萌动。
	
	static ZbFont zbfont = ZbFont.德彪钢笔行书字库;
	static int fontSize = 35;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		//玩家照片圆
		BufferedImage p = super.getImg(one);
		p = ChangeImage.resizeCrop(p, 164);
		p = ChangeImage.makeRoundedCorner(p, 164);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(451).buildVerticalOffset(50);//514 534
		
		//玩家照片方
		BufferedImage p2 = super.compressImage(one, 514, 534);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(28).buildVerticalOffset(538);
		
		//一句话
		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(205).buildVerticalOffset(437);
 		BufferedImage nameBi2 = drawText(two, zbfont, fontSize,
 				color, 380, 0, 0, true);
		
		SimplePositions[] sp = {pSP,pSP2,nameSP2 };

		BufferedImage[] bis = { p,p2,nameBi2 };

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
