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

public class NanNvPengYouBiaoQingTool extends BaseTool implements TwoDraw{
	public NanNvPengYouBiaoQingTool(StorageService storageService) {
		super(storageService);
	}

	public NanNvPengYouBiaoQingTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57b6dade0cf2e843c1267cc9";
		StorageService storageService = new StorageService();
		NanNvPengYouBiaoQingTool tyt = new NanNvPengYouBiaoQingTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材.png","女",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 30;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//第一句话
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(55).buildVerticalOffset(280);
		BufferedImage nameBI = drawText("你吓到我了 赔钱", zbfont.font(),fontType, fontSize, color,
				300, 100, 0, true);
		//第二句话
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(474).buildVerticalOffset(260);
		BufferedImage nameBI2 = drawText("除非···", zbfont.font(),fontType, 40, color,
				300, 100, 0, true);
		//第三句话
		SimplePositions nameSP3 =null;
		BufferedImage nameBI3 =null;
		
		if(two.equals("男")){
			nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(25).buildVerticalOffset(640);
			nameBI3 = drawText("你给我介绍男朋友  要这样", zbfont.font(),fontType, 25, color,
					400, 100, 0, true);
		}else{
			nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(25).buildVerticalOffset(640);
			nameBI3 = drawText("你给我介绍女朋友  要这样", zbfont.font(),fontType, 25, color,
					400, 100, 0, true);
		}
		
		//玩家照片1
		BufferedImage p = super.compressImage(one, 362, 361);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(359).buildVerticalOffset(361);
		
		SimplePositions[] sp = {pSP,nameSP,nameSP2,nameSP3};

		BufferedImage[] bis = {p,nameBI,nameBI2,nameBI3};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
