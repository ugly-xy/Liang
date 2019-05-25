package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class JiaZhuangShiPinTool extends BaseTool implements TwoDraw{
	public JiaZhuangShiPinTool(StorageService storageService) {
		super(storageService);
	}

	public JiaZhuangShiPinTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5784b8d40cf2acc3f4e33522";
		StorageService storageService = new StorageService();
		JiaZhuangShiPinTool tyt = new JiaZhuangShiPinTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","1.png", tmpPath0,null));
		
		
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 30;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	
	static String tubiao = "http://imgzb.zhuangdianbi.com/5784b9000cf2acc3f4e33546";
	@Override
	public String draw(String one, String two,String tmpBackPic, String count) throws IOException {
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(52).buildVerticalOffset(12);
		BufferedImage nameBI = drawText(one, zbfont.font(),fontType, fontSize, color,
				240, 100, 0, true);
		int fontSize2 = 18;
		String juzi = "很想和你拥有一个很长很长的未来，"+"\t"+"很想和你得到所有人的幸福，"+"\t"+"很想和你走完你的一生"+"\t"+"彼此温暖，互不辜负。";
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(16).buildVerticalOffset(121);
		BufferedImage nameBI2 = drawText(juzi, zbfont.font(),fontType, fontSize2, color,
				400, 100, 0, true);
		
		//玩家照片
		BufferedImage p = super.compressImage(two, 630, 330);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(5).buildVerticalOffset(225);
		
		//水印
		BufferedImage p2 = super.getImg(tubiao);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		
		SimplePositions[] sp = { nameSP,nameSP2,pSP,pSP2};

		BufferedImage[] bis = { nameBI,nameBI2,p,p2};

		return super.getSaveFile(sp, bis, 0.90f, tmpBackPic);
	}
}
