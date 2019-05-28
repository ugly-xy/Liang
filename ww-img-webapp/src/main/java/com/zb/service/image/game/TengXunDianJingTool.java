package com.zb.service.image.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class TengXunDianJingTool extends BaseTool implements TwoDraw{
	public TengXunDianJingTool(StorageService storageService) {
		super(storageService);
	}

	public TengXunDianJingTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/584a61590cf2487520a0097d";
		StorageService storageService = new StorageService();
		TengXunDianJingTool tyt = new TengXunDianJingTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼装点逼装点逼","QQ图片20161115153631.png", tmpPath0,null));
	}
	
	
	static ZbFont zbfont = ZbFont.汉仪菱心体简;
	static int fontSize = 20;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.TYPE1_FONT;
	
	static String ditu[] = {"http://imgzb.zhuangdianbi.com/584a61590cf2487520a0097d",
							"http://imgzb.zhuangdianbi.com/584a61700cf2487520a0097f",
							"http://imgzb.zhuangdianbi.com/584a617f0cf2487520a00980"};
	
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		Random ran  = new Random();
		int a = ran.nextInt(ditu.length);
		
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(263-one.length()*fontSize/2).buildVerticalOffset(382);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				500, 300, 0, true);
		
		//玩家照片
		BufferedImage p = super.compressImage(two, 160, 160);
		p = ChangeImage.resizeCrop(p, 160);
		p = ChangeImage.makeRoundedCorner(p, 160);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(115).buildVerticalOffset(198);
		
		tmpBackPic = ditu[a];


		SimplePositions[] sp = {pSP,nameSP };

		BufferedImage[] bis = {p,nameBI };

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
