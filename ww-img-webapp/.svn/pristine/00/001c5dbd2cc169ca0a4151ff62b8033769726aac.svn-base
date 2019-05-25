package com.zb.service.image.spoof;

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

public class HuaKaZhiFuTool extends BaseTool implements OneDraw{
	public HuaKaZhiFuTool(StorageService storageService) {
		super(storageService);
	}

	public HuaKaZhiFuTool() {
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57808ff30cf2c2119f846f54";
		StorageService storageService = new StorageService();
		HuaKaZhiFuTool tyt = new HuaKaZhiFuTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("撞壁",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 14;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		// 姓名
		int fontSize2 =22;
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(258).buildVerticalOffset(400);
		BufferedImage nameBI = drawText(one, ZbFont.书体坊赵九江钢笔行书, fontSize2, color,
				240, 0, -0.1, true);
		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy/M/d");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(170).buildVerticalOffset(270);
		BufferedImage dateBi = drawText(date, zbfont, fontSize,
				color, 240, 0,-0.1, true);
		// date2
		String date2 = DateUtil.dateFormat(new Date(), "HH:mm:ss");
		SimplePositions dateSP2 = new SimplePositions();
		dateSP2.buildHorizontalOffset(311).buildVerticalOffset(258);
		BufferedImage dateBi2 = drawText(date2, zbfont, fontSize,
				color, 240, 0,-0.1, true);
		
		
		SimplePositions[] sp = { nameSP,dateSP,dateSP2};

		BufferedImage[] bis = { nameBI,dateBi,dateBi2};

		return super.getSaveFile(sp, bis, 0.75f, tmpBackPic);
	}

}
