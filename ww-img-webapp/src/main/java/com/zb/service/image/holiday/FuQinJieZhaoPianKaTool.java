package com.zb.service.image.holiday;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.*;

public class FuQinJieZhaoPianKaTool extends BaseTool implements OneDraw {

	public FuQinJieZhaoPianKaTool(StorageService storageService) {
		super(storageService);
	}

	public FuQinJieZhaoPianKaTool() {
	}

	public static void main(String[] args) throws IOException {
		StorageService storageService = new StorageService();
		FuQinJieZhaoPianKaTool tyt = new FuQinJieZhaoPianKaTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("111.png", "203-5.png",null));
	}

	@Override
	public String draw(String one, String tmpPath,String count) throws IOException {

		BufferedImage oneBI = Thumbnails.of(super.getFile(one)).size(258, 346)
				.rotate(-13.3).asBufferedImage();
		SimplePositions oneSP = new SimplePositions();
		oneSP.buildHorizontalOffset(20).buildVerticalOffset(51);
		
		SimplePositions[] sp = { oneSP };
		BufferedImage[] bis = { oneBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

}
