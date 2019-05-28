package com.zb.service.image.spoof;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.PerspectiveFilter;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.OneDraw;

public class YuZuiPaShuiTool extends BaseTool implements OneDraw{
	public YuZuiPaShuiTool(StorageService storageService) {
		super(storageService);
	}

	public YuZuiPaShuiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57ac4a7f0cf20378729475d3";
		StorageService storageService = new StorageService();
		YuZuiPaShuiTool tyt = new YuZuiPaShuiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("某白.png",tmpPath0,null));
	}
	
	static String chatu = "http://imgzb.zhuangdianbi.com/57ac4aa50cf20378729475fc";
	
	//67 106
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.compressImage(one, 67, 106);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(327).buildVerticalOffset(234);
		
		PerspectiveFilter pf = new PerspectiveFilter();
		pf.setCorners(21, -19, p.getWidth() +14, 15, p.getWidth()-42,
				p.getHeight()-7 , -38, p.getHeight() - 37);
		p = pf.filter(p, null);
		
		BufferedImage p2 = super.getImg(chatu);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = {pSP,pSP2};

		BufferedImage[] bis = {p,p2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
