package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class HaiTianShengYanTool extends BaseTool implements OneDraw {

	public HaiTianShengYanTool(StorageService storageService) {
		super(storageService);
	}

	public HaiTianShengYanTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/575101b10cf2f32e895061d2";
		StorageService storageService = new StorageService();
		HaiTianShengYanTool tyt = new HaiTianShengYanTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,null));
	}

	@Override
	public String draw(String name, String tmpPath,String count) throws IOException {
		int fontSize = 32;
		Color color = new Color(40, 40, 40);
		String fontStyle = ZbFont.微软雅黑加粗.font();
		int fontType = Font.PLAIN;

		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(397).buildVerticalOffset(445);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 240, 0, -0.16, true);

		SimplePositions[] sp = { nameSP };

		BufferedImage[] bis = { nameBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

	// @Override
	// public void setStorageService(StorageService storageService) {
	// super.storageService = storageService;
	//
	// }

}
