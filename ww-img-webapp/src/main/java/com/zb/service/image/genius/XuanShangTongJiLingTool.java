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

public class XuanShangTongJiLingTool extends BaseTool implements TwoDraw{
	public XuanShangTongJiLingTool(StorageService storageService) {
		super(storageService);
	}

	public XuanShangTongJiLingTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/578754dc0cf220090c53bb3c";
		StorageService storageService = new StorageService();
		XuanShangTongJiLingTool tyt = new XuanShangTongJiLingTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("279-空间2.png","装点逼", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.华文行楷;
	static int fontSize = 70;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String beijing = "http://imgzb.zhuangdianbi.com/578754cf0cf220090c53bb28";
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.compressImage(one, 380, 490);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(190).buildVerticalOffset(38);
		
		BufferedImage p2 = super.getImg(beijing);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		//名字
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(555).buildVerticalOffset(630);
		BufferedImage nameBI = drawText(two, zbfont, fontSize, color,
				80, 0, 0, true);
		
		SimplePositions[] sp = {pSP,pSP2,nameSP };

		BufferedImage[] bis = { p,p2 ,nameBI};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
