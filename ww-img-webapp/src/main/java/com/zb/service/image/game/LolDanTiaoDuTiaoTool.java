package com.zb.service.image.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.FourDraw;

public class LolDanTiaoDuTiaoTool extends BaseTool implements FourDraw{
	public LolDanTiaoDuTiaoTool(StorageService storageService) {
		super(storageService);
	}

	public LolDanTiaoDuTiaoTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57720f6a0cf2ebe0ca87cacf";
		StorageService storageService = new StorageService();
		LolDanTiaoDuTiaoTool tyt = new LolDanTiaoDuTiaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("测试照片.png","装逼装","测试照片.png","装逼装" ,tmpPath0,null));
	}
	
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 10;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	static String zheZhao = "http://imgzb.zhuangdianbi.com/57721b9d0cf2ebe0ca87d71e";
	static String wangzhekuang = "http://imgzb.zhuangdianbi.com/57721bac0cf2ebe0ca87d72c";
	
	@Override
	public String draw(String one, String two, String three, String four, String tmpBackPic, String count)
			throws IOException {
		
		//上部照片
		BufferedImage p = super.compressImage(one, 120, 200);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(147).buildVerticalOffset(20);
		//上部照片
		BufferedImage kuangBI = super.getImg(wangzhekuang);
		SimplePositions kuangSP = new SimplePositions();
		kuangSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		//上部遮罩
		BufferedImage zZhao = super.getImg(zheZhao);
		SimplePositions zSP = new SimplePositions();
		zSP.buildHorizontalOffset(140).buildVerticalOffset(202);
		
		//上部名字1
		int fontSize2 = 14;
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(187).buildVerticalOffset(200);
		BufferedImage nameBI = drawText(two, zbfont.font(),fontType, fontSize2, new Color(80,181,228),
				240, 0, 0, true);
		
		//上部名字2
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(192).buildVerticalOffset(217);
		BufferedImage nameBI2 = drawText(two, zbfont.font(),fontType, fontSize, color,
				240, 0, 0, true);
		
		
		//下部照片
		BufferedImage p2 = super.compressImage(three, 120, 200);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(147).buildVerticalOffset(311);
		
		//下部遮罩
		BufferedImage zZhao2 = super.getImg(zheZhao);
		SimplePositions zSP2 = new SimplePositions();
		zSP2.buildHorizontalOffset(140).buildVerticalOffset(493);
		
		//下部名字1
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(187).buildVerticalOffset(490);
		BufferedImage nameBI3 = drawText(four, zbfont.font(),fontType, fontSize2, new Color(80,181,228),
				240, 0, 0, true);
		
		//下部名字2
		SimplePositions nameSP4 = new SimplePositions();
		nameSP4.buildHorizontalOffset(192).buildVerticalOffset(509);
		BufferedImage nameBI4 = drawText(four, zbfont.font(),fontType, fontSize, color,
				240, 0, 0, true);
		
		
		SimplePositions[] sp = {pSP,kuangSP,zSP, nameSP , nameSP2,pSP2,zSP2, nameSP3, nameSP4};

		BufferedImage[] bis = {p,kuangBI,zZhao, nameBI , nameBI2,p2,zZhao2, nameBI3, nameBI4};

		return super.getSaveFile(sp, bis, 0.95f, tmpBackPic);
	}

}
