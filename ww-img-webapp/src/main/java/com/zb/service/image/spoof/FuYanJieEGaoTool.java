package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.genius.TouMingShouJiTool;
import com.zb.service.image.i.TwoDraw;

public class FuYanJieEGaoTool extends BaseTool implements TwoDraw{
	public FuYanJieEGaoTool(StorageService storageService) {
		super(storageService);
	}

	public FuYanJieEGaoTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57e8f5d90cf20cafe64d612c";
		StorageService storageService = new StorageService();
		FuYanJieEGaoTool tyt = new FuYanJieEGaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼装逼","素材0.png",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 40;
	static Color color = new Color(255, 78, 0);
	static int fontType = Font.BOLD;
	
	static String ditu = "http://imgzb.zhuangdianbi.com/57e8fd810cf20cafe64d63d2";
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//玩家照片小
		BufferedImage p = super.compressImage(two, 60, 60);
		p = TouMingShouJiTool.rotateImage(p, -25);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(140).buildVerticalOffset(456);
		
		//玩家照片大
		BufferedImage p2 = super.compressImage(two, 305, 305);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(302).buildVerticalOffset(507);
		
		int left = 466-(one.length()+4)*fontSize/2;
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(347);
		BufferedImage nameBI = drawText(one,zbfont.font(),fontType,fontSize, color,
				600, 0, 0, true);
		
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(left+6+one.length()*fontSize).buildVerticalOffset(347);
		BufferedImage nameBI3 = drawText("实力代言",zbfont.font(),fontType,fontSize, new Color(74,78,188),
				600, 0, 0, true);	
		
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(360).buildVerticalOffset(410);
		BufferedImage nameBI2 = drawText("今天你喝了吗?",zbfont,30, new Color(74,78,188),
				600, 0, 0, true);	
		
		
		//玩家照片大
		BufferedImage p3 = super.getImg(ditu);
		SimplePositions pSP3 = new SimplePositions();
		pSP3.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = {pSP,pSP2 ,pSP3, nameSP,nameSP2,nameSP3};

		BufferedImage[] bis = {p,p2,p3, nameBI,nameBI2,nameBI3};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
