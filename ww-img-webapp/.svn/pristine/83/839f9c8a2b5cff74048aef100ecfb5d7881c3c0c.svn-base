package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class FuHaoMaiDaoHaoZhaiTool extends BaseTool implements TwoDraw{
	public FuHaoMaiDaoHaoZhaiTool(StorageService storageService) {
		super(storageService);
	}

	public FuHaoMaiDaoHaoZhaiTool() {

	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57722ccc0cf2ebe0ca87e635";
		StorageService storageService = new StorageService();
		FuHaoMaiDaoHaoZhaiTool tyt = new FuHaoMaiDaoHaoZhaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", "哥伦比亚",tmpPath0,null));
	}

	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 12;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		// 姓名+地域
		int fontSize2 = 28;
		String one0 = "中国富豪"+one+"花1.5亿元购"+two+"豪宅";
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(35).buildVerticalOffset(82);
		BufferedImage nameBI = drawText(one0, zbfont.font(),fontType, fontSize2, color, 650, 100,
				0, true);
		
		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy-M-d");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(180).buildVerticalOffset(137);
		BufferedImage dateBi = drawText(date, zbfont, fontSize,
				color, 240, 0, 0, true);
		SimplePositions[] sp = { nameSP, dateSP };

		BufferedImage[] bis = { nameBI, dateBi};

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
