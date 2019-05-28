package com.zb.service.image.genius;

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
import com.zb.service.image.i.FiveDraw;

public class QQGeRenXinXiTool extends BaseTool implements FiveDraw{
	public QQGeRenXinXiTool(StorageService storageService) {
		super(storageService);
	}

	public QQGeRenXinXiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57bc65150cf27297e4146970";
		StorageService storageService = new StorageService();
		QQGeRenXinXiTool tyt = new QQGeRenXinXiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("一个人的武林","1234567890123","我知道你没有我牛逼，因为我爱装逼。","qq素材.png","照片图标.png", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 12;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;//720 534  195 194 
	
	static String ditu = "http://imgzb.zhuangdianbi.com/57bc652c0cf27297e414697a";
	
	@Override
	public String draw(String one, String two, String three, String four, String five, String tmpBackPic, String count)
			throws IOException {
		
		Random ran = new Random();
		
		int a = ran.nextInt(100000);
		
		if(a<20000){
			a = a+20000;
		}
		
		//昵称
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(360-one.length()*42/2).buildVerticalOffset(644);
		BufferedImage nameBI = drawText(one, zbfont, 42, color,
				600, 150, 0, true);
		//签名
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(360-three.length()*26/2).buildVerticalOffset(710);
		BufferedImage nameBI2 = drawText(three, zbfont, 26, color,
				600, 150, 0, true);
		//qq
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(75).buildVerticalOffset(840);
		BufferedImage nameBI3 = drawText(two, zbfont, 29, color,
				600, 150, 0, true);
		
		//zan
		SimplePositions nameSP4 = new SimplePositions();
		nameSP4.buildHorizontalOffset(612).buildVerticalOffset(467);
		BufferedImage nameBI4 = drawText(a+"", zbfont, 30, new Color(255,255,255),
				600, 150, 0, true);
		
		BufferedImage p = super.compressImage(four, 720, 534);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		BufferedImage p2 = super.compressImage(ditu, 720, 1200);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		
		
		//玩家照片
		BufferedImage p3 = super.getImg(five);
		p3 = ChangeImage.resizeCrop(p3, 194);
		p3 = ChangeImage.makeRoundedCorner(p3, 194);
		SimplePositions pSP3 = new SimplePositions();
		pSP3.buildHorizontalOffset(265).buildVerticalOffset(437);
		
		
		
		SimplePositions[] sp = {pSP,pSP2,nameSP,nameSP2,nameSP3,nameSP4,pSP3};

		BufferedImage[] bis = {p,p2,nameBI,nameBI2,nameBI3,nameBI4,p3};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
