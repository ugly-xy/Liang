package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.common.utils.PinYinUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class DiBaiQiTaoTool extends BaseTool implements TwoDraw{
	public DiBaiQiTaoTool(StorageService storageService) {
		super(storageService);
	}

	public DiBaiQiTaoTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576cb8540cf24fe2e4ecf100";
		StorageService storageService = new StorageService();
		DiBaiQiTaoTool tyt = new DiBaiQiTaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装装装","哈尔宾", tmpPath0,null));
	}

	static ZbFont zbfont = ZbFont.黑体加粗;
	static int fontSize = 18;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpPath,String count) throws IOException {
		//姓名
		int fontSize2 = 16;
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(300).buildVerticalOffset(45);
		BufferedImage nameBI = drawText(one,zbfont,fontSize2, color,
				160, 100, 0.1, true,20,20);
		
		//出发地
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(78).buildVerticalOffset(75);
		BufferedImage nameBI2 = drawText(two, zbfont.font(),fontType, fontSize, color,
				240, 200, 0.1, true,20,20);
		//出发拼音
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(55).buildVerticalOffset(102);
		BufferedImage nameBI3 = drawText(PinYinUtil.toPinyinAllFirstUp(two), zbfont.font(),fontType, fontSize, color,
				240, 200, 0.1, true,20,20);
		
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3};

		return super.getSaveFile(sp, bis, 0.95f, tmpPath);
	}

}
