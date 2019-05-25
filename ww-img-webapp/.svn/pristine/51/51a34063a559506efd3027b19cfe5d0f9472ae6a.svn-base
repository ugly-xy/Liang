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

public class LingLeiZhengHunTool  extends BaseTool implements ThreeDraw{
	public LingLeiZhengHunTool(StorageService storageService) {
		super(storageService);
	}

	public LingLeiZhengHunTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/579c70e70cf2ef699f61c137";
		StorageService storageService = new StorageService();
		LingLeiZhengHunTool tyt = new LingLeiZhengHunTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材2.png","装点逼","与其每天孤独，不如放开求真爱。", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;//601 732
	static int fontSize = 28;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String three, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.compressImage(one, 601, 732);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(20).buildVerticalOffset(20);
		
		//姓名
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(43).buildVerticalOffset(788);
 		BufferedImage nameBi = drawText("征婚人:"+two, zbfont, fontSize,
 				color, 240, 0, 0, true);
 		//一句话
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(43).buildVerticalOffset(871);
 		BufferedImage nameBi2 = drawText(three, zbfont, fontSize,
 				color, 450, 0, 0, true);
 		
 		SimplePositions[] sp = { pSP,nameSP,nameSP2};

		BufferedImage[] bis = { p,nameBi,nameBi2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
