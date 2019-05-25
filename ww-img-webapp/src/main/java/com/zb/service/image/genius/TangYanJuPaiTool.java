package com.zb.service.image.genius;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.PerspectiveFilter;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.OneDraw;

public class TangYanJuPaiTool extends BaseTool implements OneDraw{
	public TangYanJuPaiTool(StorageService storageService) {
		super(storageService);
	}

	public TangYanJuPaiTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/584a4fd80cf2487520a00819";
		StorageService storageService = new StorageService();
		TangYanJuPaiTool tyt = new TangYanJuPaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材3.png", tmpPath0,  //215 320 
				null));
	}
	
	static String ditu = "http://imgzb.zhuangdianbi.com/584a4fc10cf2487520a00813";
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.compressImage(one, 215, 320);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(110).buildVerticalOffset(360);
		
		BufferedImage p2 = super.getImg(ditu);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		
		
		PerspectiveFilter pf = new PerspectiveFilter();
		pf.setCorners(-30, 0, 
				p.getWidth() - 52, -70, 
				p.getWidth()+60,p.getHeight() - 95, 
				72, p.getHeight() - 16);
		p = pf.filter(p, null);
		
		SimplePositions[] sp = { pSP,pSP2};

		BufferedImage[] bis = { p,p2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
