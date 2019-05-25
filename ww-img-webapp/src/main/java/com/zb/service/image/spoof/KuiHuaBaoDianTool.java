package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class KuiHuaBaoDianTool extends BaseTool implements OneDraw {

	public KuiHuaBaoDianTool(StorageService storageService) {
		super(storageService);
	}

	public KuiHuaBaoDianTool() {
	}

	public static void main(String[] args) throws IOException {
		String p0 = "163.png";
		StorageService storageService = new StorageService();
		KuiHuaBaoDianTool tyt = new KuiHuaBaoDianTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.draw("好宝宝", p0,null));
	}

	@Override
	public String draw(String one, String tmpPath,String count) throws IOException {

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(283).buildVerticalOffset(558);
		BufferedImage nameBI = drawText(one, ZbFont.楷体, 16, new Color(0, 0,
				0), 36, 100, -0.4, true, 10, 10);

		SimplePositions[] sp = { nameSP };
		BufferedImage[] bis = { nameBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

}
