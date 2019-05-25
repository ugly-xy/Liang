package com.zb.service.image.love;

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

public class JiSuanBiaoBaiTool extends BaseTool implements OneDraw{
	public JiSuanBiaoBaiTool(StorageService storageService) {
		super(storageService);
	}

	public JiSuanBiaoBaiTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57aecb280cf2ebdb1bdd6ba5";
		StorageService storageService = new StorageService();
		JiSuanBiaoBaiTool tyt = new JiSuanBiaoBaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点比", tmpPath0,  
				null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 32;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//姓名2
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(542-one.length()*28/2).buildVerticalOffset(450);
 		BufferedImage nameBi = drawText(one, zbfont, fontSize,
 				color, 240, 100, 0, true);
 	
 		
 		
	 	// date
		String date = DateUtil.dateFormat(new Date(), "yyyy        M          d");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(15).buildVerticalOffset(-35);
		BufferedImage dateBi = drawText(date, zbfont, 30,
				color, 240, 0, 0, true);
 		
 		SimplePositions[] sp = {dateSP,nameSP};

		BufferedImage[] bis = {dateBi,nameBi};

		return super.getSaveFile(sp, bis, 0.5f, tmpBackPic);
	}

}
