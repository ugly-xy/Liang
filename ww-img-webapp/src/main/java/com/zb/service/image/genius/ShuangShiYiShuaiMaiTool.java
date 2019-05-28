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

public class ShuangShiYiShuaiMaiTool extends BaseTool implements TwoDraw{
	public ShuangShiYiShuaiMaiTool(StorageService storageService) {
		super(storageService);
	}

	public ShuangShiYiShuaiMaiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/582456cc0cf20a752e9848fe";
		StorageService storageService = new StorageService();
		ShuangShiYiShuaiMaiTool tyt = new ShuangShiYiShuaiMaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("啊", "QQ图片20161011200731.jpg",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static ZbFont zbfont2 = ZbFont.方正兰亭特黑简体;
	static int fontSize = 80;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	
	static String ditu = "http://imgzb.zhuangdianbi.com/5824568e0cf20a752e9848e8";
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名
		int left = 360-5*70/2-one.length()*55/2;
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(103);
		BufferedImage nameBI = drawText(one, ZbFont.黑体.font(),fontType, 55, color,
				800, 200, 0, true);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(left+(one.length())*57).buildVerticalOffset(90);
		BufferedImage nameBI2 = drawText("双十一甩卖", ZbFont.方正兰亭特黑简体, 70, color,
				800, 200, 0, true);
		
		BufferedImage fftBI = super.compressImage(two, 300, 300);
		SimplePositions fftSP = new SimplePositions();
		fftSP.buildHorizontalOffset(72).buildVerticalOffset(318);
		
		
		BufferedImage fftBI2 = super.getImg(ditu);
		SimplePositions fftSP2 = new SimplePositions();
		fftSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		
		SimplePositions[] sp = { fftSP,fftSP2,nameSP,nameSP2};

		BufferedImage[] bis = { fftBI,fftBI2,nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
