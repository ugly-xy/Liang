package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class QiZongZuiTool extends BaseTool implements OneDraw{
	public QiZongZuiTool(StorageService storageService) {
		super(storageService);
	}

	public QiZongZuiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/581331e80cf21822ca5bff91";
		StorageService storageService = new StorageService();
		QiZongZuiTool tyt = new QiZongZuiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 40;
	static Color color = new Color(117, 117, 117);
	static int fontType = Font.BOLD;
	
	static String ditu[]  = {"http://imgzb.zhuangdianbi.com/581330e50cf21822ca5bff38",
							"http://imgzb.zhuangdianbi.com/581330f80cf21822ca5bff3c",
							"http://imgzb.zhuangdianbi.com/581331130cf21822ca5bff46",
							"http://imgzb.zhuangdianbi.com/581331200cf21822ca5bff4f",
							"http://imgzb.zhuangdianbi.com/5813312f0cf21822ca5bff55",
							"http://imgzb.zhuangdianbi.com/5813313a0cf21822ca5bff58",
							"http://imgzb.zhuangdianbi.com/581331470cf21822ca5bff5e",
							"http://imgzb.zhuangdianbi.com/581331670cf21822ca5bff67",
							"http://imgzb.zhuangdianbi.com/581331700cf21822ca5bff68",
							"http://imgzb.zhuangdianbi.com/5813317a0cf21822ca5bff6c",
							"http://imgzb.zhuangdianbi.com/581331850cf21822ca5bff70",
							"http://imgzb.zhuangdianbi.com/581331910cf21822ca5bff72",
							"http://imgzb.zhuangdianbi.com/5813319d0cf21822ca5bff76",
							"http://imgzb.zhuangdianbi.com/581331a80cf21822ca5bff7a",
							"http://imgzb.zhuangdianbi.com/581331b30cf21822ca5bff7f",
							"http://imgzb.zhuangdianbi.com/581331bd0cf21822ca5bff82",
							"http://imgzb.zhuangdianbi.com/581331ca0cf21822ca5bff85",
							"http://imgzb.zhuangdianbi.com/581331d40cf21822ca5bff88",
							"http://imgzb.zhuangdianbi.com/581331de0cf21822ca5bff8c",
							"http://imgzb.zhuangdianbi.com/581331e80cf21822ca5bff91"};
	
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(324-(one.length()+4)*fontSize/2).buildVerticalOffset(50);
		BufferedImage nameBI = drawText(one+"的七宗罪",zbfont,fontSize, color,
				400, 100, 0, true);
		Random ran = new Random();
		int a = ran.nextInt(ditu.length);
		tmpBackPic = ditu[a];
		SimplePositions[] sp = { nameSP};
		BufferedImage[] bis = { nameBI};
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
