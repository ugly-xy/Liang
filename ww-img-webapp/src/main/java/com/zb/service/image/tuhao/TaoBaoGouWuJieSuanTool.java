package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import com.zb.common.utils.DateUtil;
import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class TaoBaoGouWuJieSuanTool extends BaseTool implements TwoDraw{
	public TaoBaoGouWuJieSuanTool(StorageService storageService) {
		super(storageService);
	}

	public TaoBaoGouWuJieSuanTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "淘宝购物信息.png";
		StorageService storageService = new StorageService();
		TaoBaoGouWuJieSuanTool tyt = new TaoBaoGouWuJieSuanTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("测试2.png", "撞壁科技",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 24;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	
	static String[] num = {"99","87","77","79","65","64","49","55","37","29"};
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//玩家传的照片
		BufferedImage p = super.compressImage(one, 95, 95);
		p = ChangeImage.resizeCrop(p, 95);
		p = ChangeImage.makeRoundedCorner(p, 95);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(17).buildVerticalOffset(93);
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(127).buildVerticalOffset(120);
		BufferedImage nameBI = drawText(two,zbfont, fontSize, color,
				700, 0, 0, true);
		int len = num.length;
		Random ran = new Random();
		int n1 = ran.nextInt(len);
		int n2 = ran.nextInt(len);
		int n3 = ran.nextInt(len);
		
		int fontSize3 = 22;
		//1
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(172).buildVerticalOffset(304);
		BufferedImage nameBI2 = drawText(num[n1],zbfont, fontSize3, new Color(253,82,2),
				700, 0, 0, true);
		//2
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(280).buildVerticalOffset(304);
		BufferedImage nameBI3 = drawText(num[n2],zbfont, fontSize3, new Color(253,82,2),
				700, 0, 0, true);
		//3
		SimplePositions nameSP4 = new SimplePositions();
		nameSP4.buildHorizontalOffset(388).buildVerticalOffset(304);
		BufferedImage nameBI4 = drawText(num[n3],zbfont, fontSize3, new Color(253,82,2),
				700, 0, 0, true);
		
		// date
		int fontSize2 = 22;
		String date = DateUtil.dateFormat(new Date(), "HH:mm");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(245).buildVerticalOffset(-1);
		BufferedImage dateBi = drawText(date, zbfont.font(),fontType, fontSize2,
				color, 240, 0, 0, true);
		
		SimplePositions[] sp = { pSP,nameSP,dateSP,nameSP2,nameSP3,nameSP4};

		BufferedImage[] bis = { p,nameBI,dateBi,nameBI2,nameBI3,nameBI4};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
