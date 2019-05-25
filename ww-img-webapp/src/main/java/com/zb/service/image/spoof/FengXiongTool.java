package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class FengXiongTool extends BaseTool implements ThreeDraw{
	public FengXiongTool(StorageService storageService) {
		super(storageService);
	}

	public FengXiongTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "296-生成配图.png";
		StorageService storageService = new StorageService();
		FengXiongTool tyt = new FengXiongTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材.jpg","装点逼","我在等你！", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 22;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String three, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.getImg(one);
		p = ChangeImage.resizeCrop(p, 134);
		p = ChangeImage.makeRoundedCorner(p, 134);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(55).buildVerticalOffset(567);
		
		//姓名
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(155).buildVerticalOffset(731);
 		BufferedImage nameBi = drawText(two, zbfont, fontSize,
 				color, 240, 0, 0, true);
 		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy年M月d日");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(155).buildVerticalOffset(820);
		BufferedImage dateBi = drawText(date, zbfont, fontSize,
				color, 240, 0, 0, true);
 		//一句话
		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(155).buildVerticalOffset(906);
 		BufferedImage nameBi2 = drawText(three, zbfont, fontSize,
 				color, 300, 0, 0, true);
 		
 		SimplePositions[] sp = {pSP,nameSP,nameSP2,dateSP };

		BufferedImage[] bis = { p,nameBi,nameBi2,dateBi };

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
