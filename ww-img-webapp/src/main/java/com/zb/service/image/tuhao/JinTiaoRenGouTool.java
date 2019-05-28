package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class JinTiaoRenGouTool extends BaseTool implements OneDraw{
	public JinTiaoRenGouTool(StorageService storageService) {
		super(storageService);
	}

	public JinTiaoRenGouTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/577a12c70cf26acda3d9210d";
		StorageService storageService = new StorageService();
		JinTiaoRenGouTool tyt = new JinTiaoRenGouTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 16;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.TYPE1_FONT;
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		// 姓名
		int len = one.length();
		
		SimplePositions nameSP =null;
		BufferedImage nameBI = null;
		if(len==1){
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(160).buildVerticalOffset(305);
			nameBI = drawText(one, zbfont, fontSize, color,
					240, 50, 0.1, true,20,20);
		}else if(len==2){
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(150).buildVerticalOffset(305);
			nameBI = drawText(one, zbfont, fontSize, color,
					240, 50, 0.1, true,20,20);
		}else{
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(143).buildVerticalOffset(305);
			nameBI = drawText(one, zbfont, fontSize, color,
					240, 50, 0.1, true,20,20);
		}
		

		
		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy年M月d日");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(300).buildVerticalOffset(490);
		BufferedImage dateBi = drawText(date, zbfont.font(), fontType, fontSize,
				color, 240, 100,0.1, true,20,20);
		
		
		SimplePositions[] sp = { nameSP, dateSP };

		BufferedImage[] bis = { nameBI, dateBi };

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
