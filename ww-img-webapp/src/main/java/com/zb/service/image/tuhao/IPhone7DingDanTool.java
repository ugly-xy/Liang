package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class IPhone7DingDanTool extends BaseTool implements OneDraw{
	public IPhone7DingDanTool(StorageService storageService) {
		super(storageService);
	}

	public IPhone7DingDanTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57a1bff30cf240d5e8eb2c7f";
		StorageService storageService = new StorageService();
		IPhone7DingDanTool tyt = new IPhone7DingDanTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,"123"));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 29;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		
		int count0 = Integer.parseInt(count);
		
		int count1 = count0+15123;
		//姓名
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(360-one.length()*fontSize/2).buildVerticalOffset(340);
 		BufferedImage nameBi = drawText(one, zbfont, fontSize,
 				color, 240, 0, 0, true);
 		
 		// date
		String date = DateUtil.dateFormat(new Date(), "HH:mm");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(340).buildVerticalOffset(0);
		BufferedImage dateBi = drawText(date, zbfont, 22,
				color, 240, 0, 0, true);
		
		//姓名
		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(180).buildVerticalOffset(380);
 		BufferedImage nameBi2 = drawText("您是第"+count1+"名预订iphone7的用户", zbfont, 24,
 				color, 600, 0, 0, true);
		
		SimplePositions[] sp = {nameSP,nameSP2,dateSP };

		BufferedImage[] bis = { nameBi,nameBi2,dateBi };

		return super.getSaveFile(sp, bis, 0.9f, tmpBackPic);
	}

}
