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

public class DanShenJiuShiCanJiTool extends BaseTool implements OneDraw{
	public DanShenJiuShiCanJiTool(StorageService storageService) {
		super(storageService);
	}

	public DanShenJiuShiCanJiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/582467c20cf2596074c5d906";
		StorageService storageService = new StorageService();
		DanShenJiuShiCanJiTool tyt = new DanShenJiuShiCanJiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static ZbFont zbfont2 = ZbFont.宋体;
	static int fontSize = 16;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(48).buildVerticalOffset(176);
		BufferedImage nameBI = drawText(one+":", zbfont2.font(),fontType,fontSize, color,
				400, 200, 0, true);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(50).buildVerticalOffset(207);
		BufferedImage nameBI2 = drawText("    根据世界卫生组织WHO最新残疾人标准，因"+one+"到达找对象的年龄，却找不到对象而不能正常发育、严重"
				+ "违反了世界人类繁衍的自然规律，而且很多名亲朋好友作证确有此事的前提下，现被归纳为身体残疾。", zbfont2.font(),fontType,fontSize, color,
				400, 200, 0, true);
		
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(50).buildVerticalOffset(324);
		BufferedImage nameBI3 = drawText("    现颁发残疾人证明书，凭此书可以享受国家残疾人补贴和福利。", zbfont2.font(),fontType,fontSize, color,
				400, 200, 0, true);
		
		SimplePositions nameSP4 = new SimplePositions();
		nameSP4.buildHorizontalOffset(50).buildVerticalOffset(400);
		BufferedImage nameBI4 = drawText("    领取此证后不得在与异性交往，否则将被执行宫刑，此后也不再享受国家补贴和福利！切记...", zbfont2.font(),fontType,fontSize, color,
				400, 200, 0, true);
		
		
		
		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy年MM月dd日");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(340).buildVerticalOffset(600);
		BufferedImage dateBi = drawText(date, zbfont, 18,
				color, 300, 0, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,dateSP};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,dateBi};

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
