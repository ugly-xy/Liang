package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class ZhouDongQiangRenTool extends BaseTool implements TwoDraw{
	public ZhouDongQiangRenTool(StorageService storageService) {
		super(storageService);
	}

	public ZhouDongQiangRenTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/579b02800cf24516f7c91112";
		StorageService storageService = new StorageService();
		ZhouDongQiangRenTool tyt = new ZhouDongQiangRenTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","素材4.png" ,tmpPath0,null));
	}
	//14字
	static ZbFont zbfont = ZbFont.微软雅黑;
	static int fontSize = 12;
	static Color color = new Color(215, 213, 218);
	static int fontType = Font.BOLD;
	
	static String  ditu= "http://imgzb.zhuangdianbi.com/579b02690cf24516f7c910ff";
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		// 姓名
		String one0 = "周杰伦拼了！为抢学员模仿天王"+one+",相似度99%";
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(97).buildVerticalOffset(212);
		BufferedImage nameBI = drawText(one0, zbfont, fontSize, color,
				600, 0, 0, true);
		
		//玩家照片
		BufferedImage p = super.getImg(two);
		p = ChangeImage.resizeCrop(p, 87);
		p = ChangeImage.makeRoundedCorner(p, 87);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(138).buildVerticalOffset(56);
		
		BufferedImage p2 = super.getImg(ditu);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		
		
		SimplePositions[] sp = {  pSP,pSP2,nameSP, };

		BufferedImage[] bis = {  p, p2,nameBI,};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
