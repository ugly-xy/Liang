package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.*;

public class ZhaoPianTieTool extends BaseTool implements TwoDraw {

	public ZhaoPianTieTool(StorageService storageService) {
		super(storageService);
	}

	public ZhaoPianTieTool() {
	}

	public static void main(String[] args) throws IOException {
		StorageService storageService = new StorageService();
		ZhaoPianTieTool tyt = new ZhaoPianTieTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("a9.png", "爸爸我爱你", "fbg.png",null));
	}

	static String ftt = "http://imgzb.zhuangdianbi.com/576121ee0cf244b2006aaef1";

	@Override
	public String draw(String one, String two, String tmpPath,String count)
			throws IOException {

		BufferedImage oneBI = Thumbnails.of(super.getFile(one)).size(450, 344)
				.asBufferedImage();
		SimplePositions oneSP = new SimplePositions();
		oneSP.buildHorizontalOffset(135).buildVerticalOffset(130);

		BufferedImage fftBI = super.getImg(ftt);
		SimplePositions fftSP = new SimplePositions();
		fftSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		int size = 48;
		int left = 365 - size * two.length() / 2;
		BufferedImage twoBI = super.drawText(two, ZbFont.陈继世硬笔行书, size,
				Color.black, 446);
		SimplePositions twoSP = new SimplePositions();
		twoSP.buildHorizontalOffset(left).buildVerticalOffset(480);

		SimplePositions[] sp = { oneSP, fftSP, twoSP };
		BufferedImage[] bis = { oneBI, fftBI, twoBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

}
