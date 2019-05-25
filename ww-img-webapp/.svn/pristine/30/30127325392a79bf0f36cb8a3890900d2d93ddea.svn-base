package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.LensBlurFilter;
import com.jhlabs.image.WaterFilter;
import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class WaterFilterPicTool extends BaseTool implements TwoDraw{
	public WaterFilterPicTool(StorageService storageService) {
		super(storageService);
	}

	public WaterFilterPicTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "1.jpg";
		StorageService storageService = new StorageService();
		WaterFilterPicTool tyt = new WaterFilterPicTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("1.jpg","时光静好，我亦不老倾我一生一世，换取岁月静好。如若岁月静好，我亦微笑，亦不老。", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.德彪钢笔行书字库;
	static int fontSize = 32;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one,String two, String tmpBackPic, String count) throws IOException {
		//手照片
		BufferedImage p = super.compressImage(one, 658, 962);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		
		WaterFilter wf = new WaterFilter();
		//横向位置
		wf.setCentreX((float) 0.51);
		//纵向位置
		wf.setCentreY((float) 0.45);
		//中心大小
		wf.setWavelength(30);
		//波纹多少
		wf.setAmplitude(8);
		//波纹大小
		wf.setRadius(150);
		p = wf.filter(p, null);
		
		LensBlurFilter  bl = new LensBlurFilter();
		p = bl.filter(p, null);
		

		BufferedImage p2 = super.compressImage(one, 627, 911);
		p2 = ChangeImage.resizeCrop(p2, 184);
		p2 = ChangeImage.makeRoundedCorner(p2, 184);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(70).buildVerticalOffset(570);
		
		
		BufferedImage p3 = super.compressImage(one, 627, 911);
		p3 = ChangeImage.resizeCrop(p3, 280);
		p3 = ChangeImage.makeRoundedCorner(p3, 280);
		SimplePositions pSP3 = new SimplePositions();
		pSP3.buildHorizontalOffset(450).buildVerticalOffset(100);
		
	
		//代言
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(320).buildVerticalOffset(630);
		BufferedImage nameBI3 = drawText(two, zbfont, fontSize, new Color(122,54,144),
				260, 600, 0, true);
		
		
		SimplePositions[] sp = { pSP,pSP2,pSP3,nameSP3};

		BufferedImage[] bis = { p,p2,p3,nameBI3};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	
}
