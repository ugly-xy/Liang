package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class ShaoXiangXuYuanTool extends BaseTool implements OneDraw{
	public ShaoXiangXuYuanTool(StorageService storageService) {
		super(storageService);
	}

	public ShaoXiangXuYuanTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57e507150cf2e4b4a580e418";
		StorageService storageService = new StorageService();
		ShaoXiangXuYuanTool tyt = new ShaoXiangXuYuanTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼变帅", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 12;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(106-one.length()*fontSize/2).buildVerticalOffset(33);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				180, 100, 0, true);
		
		SimplePositions[] sp = {nameSP};

		BufferedImage[] bis = { nameBI};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
