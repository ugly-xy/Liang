package com.zb.service.image.love;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class ShouXieQingShuTool extends BaseTool implements OneDraw {

	public ShouXieQingShuTool(StorageService storageService) {
		super(storageService);
	}

	public ShouXieQingShuTool() {
	}

	public static void main(String[] args) throws IOException {
		String p0 = "162.png";
		StorageService storageService = new StorageService();
		ShouXieQingShuTool tyt = new ShouXieQingShuTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.draw("宝宝", p0,null));
	}

	@Override
	public String draw(String one, String tmpPath,String count) throws IOException {

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(345).buildVerticalOffset(518);
		BufferedImage nameBI = drawText(one, ZbFont.新蒂小丸子小学版, 36, new Color(34,
				87, 160), 300, 100, 0, false);

		SimplePositions[] sp = { nameSP };
		BufferedImage[] bis = { nameBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

}
