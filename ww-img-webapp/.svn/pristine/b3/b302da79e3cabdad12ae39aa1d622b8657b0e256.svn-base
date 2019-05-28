package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class TouMingShouJiTool extends BaseTool implements ThreeDraw{
	public TouMingShouJiTool(StorageService storageService) {
		super(storageService);
	}

	public TouMingShouJiTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57e388a40cf2cce64d805011";
		StorageService storageService = new StorageService();
		TouMingShouJiTool tyt = new TouMingShouJiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点比","未标题-2.png","我在等待永远在等待", tmpPath0,  //13
				null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 12;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String ditu = "http://imgzb.zhuangdianbi.com/57e3be400cf279e977b7e05e";
	@Override
	public String draw(String one, String two, String three, String tmpBackPic, String count) throws IOException {
		//昵称
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(124).buildVerticalOffset(178);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				720, 0, -0.2, true);
		//玩家照片
		BufferedImage p = super.compressImage(two, 37, 37);
		p = this.rotateImage(p, -15);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(85).buildVerticalOffset(185);
		
		//玩家照片
		BufferedImage p2 = super.getImg(ditu);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		/*PerspectiveFilter pf = new PerspectiveFilter();
		pf.setCorners(0, 0, p.getWidth(), -10, p.getWidth(),
				p.getHeight()-10, 0, p.getHeight());
		p = pf.filter(p, null);*/
		
		//签名
		int a = 200;
		if(three.length()<=6){
			a = 215;
		}else{
			a= 205;
		}
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(110).buildVerticalOffset(a);
		BufferedImage nameBI2 = drawText(three, zbfont, 10, new Color(156,157,161),
				160, 160, -0.2, true,20,10);
		
		SimplePositions[] sp = {pSP,pSP2,nameSP,nameSP2 };//

		BufferedImage[] bis = { p,p2,nameBI,nameBI2 };//

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	
	 public static BufferedImage rotateImage(final BufferedImage bufferedimage,
	            final int degree) {
	        int w = bufferedimage.getWidth();
	        int h = bufferedimage.getHeight();
	        int type = bufferedimage.getColorModel().getTransparency();
	        BufferedImage img;
	        Graphics2D graphics2d;
	        (graphics2d = (img = new BufferedImage(w, h, type))
	                .createGraphics()).setRenderingHint(
	                RenderingHints.KEY_INTERPOLATION,
	                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
	        graphics2d.drawImage(bufferedimage, 0, 0, null);
	        graphics2d.dispose();
	        return img;
	    }
}
