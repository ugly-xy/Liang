package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class IPoneTongJiTool extends BaseTool implements TwoDraw{
	public IPoneTongJiTool(StorageService storageService) {
		super(storageService);
	}

	public IPoneTongJiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57cea84b0cf2511a29eddf0d";
		StorageService storageService = new StorageService();
		IPoneTongJiTool tyt = new IPoneTongJiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","a.png", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 30;
	static Color color = new Color(84, 88, 103);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名1
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(125).buildVerticalOffset(204);
 		BufferedImage nameBi = drawText(one+" 的苹果账单", zbfont, fontSize,
 				new Color(0,0,0), 700, 0, 0, true);
 		//姓名2
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(360-one.length()*fontSize/2).buildVerticalOffset(440);
 		BufferedImage nameBi2 = drawText(one, zbfont, fontSize,
 				new Color(0,0,0), 700, 0, 0, true);
 		//消费总额
 		SimplePositions nameSP3 = new SimplePositions();
 		nameSP3.buildHorizontalOffset(360-12*fontSize/2).buildVerticalOffset(500);
 		BufferedImage nameBi3 = drawText("他的苹果账单消费总额接近", zbfont, fontSize,
 				new Color(0,0,0), 700, 0, 0, true);
 		//金钱
 		Random ran = new Random();
 		int a = ran.nextInt(100000);
 		if(a<50000){
 			a = a+50000;
 		}
 		String b = a+"";
 		SimplePositions nameSP4 = new SimplePositions();
 		nameSP4.buildHorizontalOffset(415-(b.length())*40/2).buildVerticalOffset(564);
 		BufferedImage nameBi4 = drawText(b, zbfont.font(),fontType, 40,
 				new Color(255,255,255), 700, 0, 0, true);
 		
 		//截至今天
 		SimplePositions nameSP5 = new SimplePositions();
 		nameSP5.buildHorizontalOffset(355-(16+one.length())*fontSize/2).buildVerticalOffset(763);
 		BufferedImage nameBi5 = drawText("截至今天，"+one+" 一共用过了15台苹果设备", zbfont, fontSize,
 				new Color(0,0,0), 800, 0, 0, true);
 		
 		// date
		String date = DateUtil.dateFormat(new Date(), "HH:mm");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(340).buildVerticalOffset(0);
		BufferedImage dateBi = drawText(date, zbfont, 22,
				new Color(255,255,255), 240, 0, 0, true);
		
		//玩家照片
		BufferedImage p = super.compressImage(two, 95, 95);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(318).buildVerticalOffset(306);
		
		
 		SimplePositions[] sp = {nameSP,nameSP2,nameSP3,nameSP4,nameSP5,dateSP,pSP};

		BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4,nameBi5,dateBi,p};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
