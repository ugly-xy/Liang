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

public class QiXiChuZuTool extends BaseTool implements ThreeDraw{
	public QiXiChuZuTool(StorageService storageService) {
		super(storageService);
	}

	public QiXiChuZuTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/579ee2810cf20c9032ae97f6";
		StorageService storageService = new StorageService();
		QiXiChuZuTool tyt = new QiXiChuZuTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("BIBI","女","1234567890", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.华康少女;
	static int fontSize = 29;
	static Color color = new Color(61, 19, 38);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String three, String tmpBackPic, String count) throws IOException {
		
		SimplePositions nameSP = null;
		SimplePositions nameSP2 = null;
		SimplePositions nameSP3 = null;
		SimplePositions nameSP4 = null;
		
		BufferedImage nameBi = null;
		BufferedImage nameBi2 = null;
		BufferedImage nameBi3 = null;
		BufferedImage nameBi4 = null;
		if(two.equals("男")){
			//男
			nameSP = new SimplePositions();
	 		nameSP.buildHorizontalOffset(189).buildVerticalOffset(42);
	 		nameBi = drawText("男", zbfont.font(),fontType, 48,
	 				color, 240, 0, 0, true);
	 		//女
	 		nameSP2 = new SimplePositions();
	 		nameSP2.buildHorizontalOffset(310).buildVerticalOffset(123);
	 		nameBi2 = drawText("女", zbfont, fontSize,
	 				color, 240, 0, 0, true);
	 		
		}else{
			//女
			nameSP = new SimplePositions();
	 		nameSP.buildHorizontalOffset(189).buildVerticalOffset(42);
	 		nameBi = drawText("女", zbfont.font(),fontType, 48,
	 				color, 240, 0, 0, true);
	 		//男
	 		nameSP2 = new SimplePositions();
	 		nameSP2.buildHorizontalOffset(310).buildVerticalOffset(123);
	 		nameBi2 = drawText("男", zbfont, fontSize,
	 				color, 240, 0, 0, true);
		}
		//联系人
 		nameSP3 = new SimplePositions();
 		nameSP3.buildHorizontalOffset(35).buildVerticalOffset(690);
 		nameBi3 = drawText("联系人: "+one, zbfont, fontSize,
 				color, 300, 0, 0, true);
 		//联系方式
 		nameSP4 = new SimplePositions();
 		nameSP4.buildHorizontalOffset(35).buildVerticalOffset(733);
 		nameBi4 = drawText("联系QQ:"+three, zbfont, fontSize,
 				color, 600, 0, 0, true);
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

		BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
