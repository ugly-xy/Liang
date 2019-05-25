package com.zb.service.image.love;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.*;

public class ZhiNanBuHuiWanTool extends BaseTool implements TwoDraw {

	public ZhiNanBuHuiWanTool(StorageService storageService) {
		super(storageService);
	}

	public ZhiNanBuHuiWanTool() {
	}

	public static void main(String[] args) throws IOException {
		String p0 = "179.png";
		StorageService storageService = new StorageService();
		ZhiNanBuHuiWanTool tyt = new ZhiNanBuHuiWanTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.draw("宝宝", "爱你一生一世！", p0,null));
	}

	static Color color = Color.white;

	@Override
	public String draw(String one, String two, String tmpPath,String count)
			throws IOException {

		one = one + "," + two;
		int left = 475-(one.length()*27);
		SimplePositions oneSP = new SimplePositions();
		oneSP.buildHorizontalOffset(left).buildVerticalOffset(275);
		BufferedImage oneBI = drawText(one, ZbFont.黑体加粗, 50, color, 650,
				100, -0.055, true);

		SimplePositions[] sp = { oneSP };
		BufferedImage[] bis = { oneBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

}
