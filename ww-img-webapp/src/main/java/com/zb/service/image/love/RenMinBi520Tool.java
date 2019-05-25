package com.zb.service.image.love;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class RenMinBi520Tool extends BaseTool implements OneDraw {

	public RenMinBi520Tool(StorageService storageService) {
		super(storageService);
	}
	
	public RenMinBi520Tool() {
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/575101d40cf2f32e89506247";
		StorageService storageService = new StorageService();
		RenMinBi520Tool tyt = new RenMinBi520Tool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.draw("宝宝", p0,null));
	}


	@Override
	public String draw(String one, String tmpPath,String count) throws IOException {
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(50).buildVerticalOffset(175);
		BufferedImage nameBI = drawText(one, ZbFont.新蒂小丸子小学版, 40, new Color(
				23, 11, 73), 300, 100, 0.2, true);

		SimplePositions[] sp = { nameSP };
		BufferedImage[] bis = { nameBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}


}
