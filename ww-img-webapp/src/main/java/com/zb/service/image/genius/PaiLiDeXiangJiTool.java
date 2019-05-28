package com.zb.service.image.genius;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.PerspectiveFilter;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.OneDraw;

public class PaiLiDeXiangJiTool extends BaseTool implements OneDraw{
	public PaiLiDeXiangJiTool(StorageService storageService) {
		super(storageService);
	}

	public PaiLiDeXiangJiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/577b6d230cf267a801c8a058";
		StorageService storageService = new StorageService();
		PaiLiDeXiangJiTool tyt = new PaiLiDeXiangJiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("测试2.png", tmpPath0,null));
	}
	
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.compressImage(one, 260, 340);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(286).buildVerticalOffset(68);
		//处理照片
		PerspectiveFilter pf = new PerspectiveFilter();
		pf.setCorners(0, 0, p.getWidth()-2 , 0, p.getWidth()-32,
				p.getHeight() , 1, p.getHeight() -3);
		
		p = pf.filter(p, null);
		
		
		SimplePositions[] sp = {pSP};

		BufferedImage[] bis = { p };

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
