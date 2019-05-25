package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.PerspectiveFilter;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.FourDraw;

public class LaoGanMaQingLvTool extends BaseTool implements FourDraw{
	public LaoGanMaQingLvTool(StorageService storageService) {
		super(storageService);
	}

	public LaoGanMaQingLvTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57b6b75b0cf2e843c1265e02";
		StorageService storageService = new StorageService();
		LaoGanMaQingLvTool tyt = new LaoGanMaQingLvTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","1.png","装","2.png", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.书体坊赵九江钢笔行书;
	static int fontSize = 22;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String ditu = "http://imgzb.zhuangdianbi.com/57b6b7470cf2e843c1265df8";
	
	@Override
	public String draw(String one, String two, String three, String four, String tmpBackPic, String count)
			throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(285-one.length()*fontSize/2).buildVerticalOffset(490);
		BufferedImage nameBI = drawText(one, zbfont.font(),fontType, fontSize, color,
				180, 100, -0.2, true,10,10);
		//姓名
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(574-three.length()*25/2).buildVerticalOffset(228);
		BufferedImage nameBI2 = drawText(three, zbfont.font(),fontType, 25, new Color(255,255,255),
				180, 100, -0.44, true,10,10);
		
		//玩家照片1
		BufferedImage p = super.compressImage(two, 90, 110);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(215).buildVerticalOffset(380);
		
		PerspectiveFilter pf = new PerspectiveFilter();
		pf.setCorners(0, 0, p.getWidth() , -20, p.getWidth()+20,
				p.getHeight()-20 , 20, p.getHeight());
		p = pf.filter(p, null);
		
		//玩家照片2
		BufferedImage p2 = super.compressImage(four, 140, 192);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(427).buildVerticalOffset(40);
		
		PerspectiveFilter pf2 = new PerspectiveFilter();
		pf2.setCorners(0, 0, p2.getWidth() , -70, p2.getWidth()+70,
				p2.getHeight()-70 , 70, p2.getHeight());
		p2 = pf2.filter(p2, null);
		
		
		//底图
		BufferedImage p3 = super.getImg(ditu);
		SimplePositions pSP3 = new SimplePositions();
		pSP3.buildHorizontalOffset(0).buildVerticalOffset(0);
		SimplePositions[] sp = {pSP,pSP2,pSP3,nameSP,nameSP2};

		BufferedImage[] bis = {p,p2,p3,nameBI,nameBI2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
