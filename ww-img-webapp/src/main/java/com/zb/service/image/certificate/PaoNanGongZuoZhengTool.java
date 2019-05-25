package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;
import com.zb.service.image.i.TwoDraw;

public class PaoNanGongZuoZhengTool extends BaseTool implements TwoDraw {

	public PaoNanGongZuoZhengTool(StorageService storageService) {
		super(storageService);
	}

	public PaoNanGongZuoZhengTool() {
	}

	public static void main(String[] args) throws IOException {
		String p0 = "164.png";
		StorageService storageService = new StorageService();
		PaoNanGongZuoZhengTool tyt = new PaoNanGongZuoZhengTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.draw("宝宝", "anglebaby", p0,null));
	}

	static Color c = new Color(0, 0, 0);

	@Override
	public String draw(String one, String two, String tmpPath,String count)
			throws IOException {

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(288).buildVerticalOffset(706);
		BufferedImage nameBI = drawText(one, ZbFont.苹方, 28, c, 300, 100, 0.1,
				true);

		two = two + "助理";
		SimplePositions yuanSP = new SimplePositions();
		yuanSP.buildHorizontalOffset(270).buildVerticalOffset(772);
		BufferedImage yuanBI = drawText(two, ZbFont.苹方, 28, c, 300, 100, 0.1,
				true);

		SimplePositions[] sp = { nameSP, yuanSP };
		BufferedImage[] bis = { nameBI, yuanBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

}
