package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.PerspectiveFilter;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class DanShenGouLiangTwoTool extends BaseTool implements TwoDraw{
	public DanShenGouLiangTwoTool(StorageService storageService) {
		super(storageService);
	}

	public DanShenGouLiangTwoTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57b166b70cf2464bcace7681";
		StorageService storageService = new StorageService();
		DanShenGouLiangTwoTool tyt = new DanShenGouLiangTwoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼","单身狗2.png",tmpPath0,null));//155 204
	}
	
	static ZbFont zbfont = ZbFont.宋体;
	static int fontSize = 27;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String ditu = "http://imgzb.zhuangdianbi.com/57b166a40cf2464bcace7666";
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(327-(one.length()+3)*fontSize/2).buildVerticalOffset(142);
 		BufferedImage nameBi = drawText(one+"牌狗粮", zbfont, fontSize,
 				color, 295, 100, 0, true);
 		
 		BufferedImage p2 = super.compressImage(two, 155, 204);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(250).buildVerticalOffset(193);
		
		//处理照片
		PerspectiveFilter pf = new PerspectiveFilter();
		pf.setCorners(0, 0, p2.getWidth() , 0, p2.getWidth(),
				p2.getHeight()-14 , 0, p2.getHeight());
		
		p2 = pf.filter(p2, null);
		
		BufferedImage p = super.getImg(ditu);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = { pSP2,pSP,nameSP};

		BufferedImage[] bis = { p2,p,nameBi};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
