package com.zb.service.image.spoof;

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
import com.zb.service.image.i.FourDraw;

public class TianMaoShuangShiYiTool extends BaseTool implements FourDraw{

	public TianMaoShuangShiYiTool(StorageService storageService) {
		super(storageService);
	}

	public TianMaoShuangShiYiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "376-1天猫.png";
		StorageService storageService = new StorageService();
		TianMaoShuangShiYiTool tyt = new TianMaoShuangShiYiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装","1465465464","1","QQ图片20161011200731.jpg", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 45;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String three, String four, String tmpBackPic, String count)
			throws IOException {
		Random ran = new Random();
		int a = ran.nextInt(30);	
		//姓名
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(380-one.length()*fontSize/2).buildVerticalOffset(815);
 		BufferedImage nameBi = drawText(one, zbfont, fontSize,
 				new Color(255,255,255), 380, 0, 0, true);
		
 		//姓名
 		SimplePositions nameSP4 = new SimplePositions();
 		nameSP4.buildHorizontalOffset(118).buildVerticalOffset(448);
 		BufferedImage nameBi4 = drawText("有"+a+"件宝贝,让你心动,你为它们支付了", zbfont, 30,
 				new Color(255,255,255), 600, 0, 0, true);
 		
 		//金钱
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(380-two.length()*25/2).buildVerticalOffset(505);
 		BufferedImage nameBi2 = drawText(two+"元", zbfont.font(),fontType, 50,
 				color, 500, 0, 0, true);
 		
 		//排名
 		SimplePositions nameSP3 = new SimplePositions();
 		nameSP3.buildHorizontalOffset(350-three.length()*25/2).buildVerticalOffset(680);
 		BufferedImage nameBi3 = drawText(three+"万名", zbfont.font(),fontType, 50,
 				color, 500, 0, 0, true);
 		
		//玩家照片圆
		BufferedImage p = super.getImg(four);
		p = ChangeImage.resizeCrop(p, 112);
		p = ChangeImage.makeRoundedCorner(p, 112);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(145).buildVerticalOffset(794);
		SimplePositions[] sp = {pSP ,nameSP,nameSP2,nameSP3,nameSP4};

		BufferedImage[] bis = { p,nameBi,nameBi2,nameBi3,nameBi4};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
