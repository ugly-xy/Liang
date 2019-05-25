package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class GuiDuiWeiZuGuoTool extends BaseTool implements OneDraw{
	public GuiDuiWeiZuGuoTool(StorageService storageService) {
		super(storageService);
	}

	public GuiDuiWeiZuGuoTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5784ee160cf206a1e4db1f7b";
		StorageService storageService = new StorageService();
		GuiDuiWeiZuGuoTool tyt = new GuiDuiWeiZuGuoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,"44545"));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize =29;
	static Color color = new Color(164, 0, 3);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//我是
		String name = "尊敬的";
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(87).buildVerticalOffset(95);
		BufferedImage nameBI3 = drawText(name, zbfont, fontSize, new Color(0,0,0),
				240, 100, 0, true);
		
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(174).buildVerticalOffset(95);
		BufferedImage nameBI = drawText(one+":", zbfont, fontSize, color,
				240, 100, 0, true);
		DecimalFormat df=new DecimalFormat("00000000000");
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(185).buildVerticalOffset(275);
		BufferedImage nameBI2 = drawText(df.format(Integer.parseInt(count)), zbfont, fontSize, color,
				240, 100, 0, true);
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
