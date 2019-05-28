package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class WeiXinPaiXuTool extends BaseTool implements ThreeDraw{
	public WeiXinPaiXuTool(StorageService storageService) {
		super(storageService);
	}

	public WeiXinPaiXuTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57a475750cf2c80ac7a3ebe3";
		StorageService storageService = new StorageService();
		WeiXinPaiXuTool tyt = new WeiXinPaiXuTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材.png","我爱你","150013203465", tmpPath0,  //
				"8888888"));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 32;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String three, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.getImg(one);
		p = ChangeImage.resizeCrop(p, 147);
		p = ChangeImage.makeRoundedCorner(p, 147);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(287).buildVerticalOffset(246);
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(360-two.length()*fontSize/2).buildVerticalOffset(420);
		BufferedImage nameBI = drawText(two, zbfont, fontSize, new Color(19,194,5),
				600, 50, 0, true,0,0);
		
		long time = Long.parseLong(three);
	    Date date = new Date(time);
	    String date2 = DateUtil.dateFormat(date, "yyyy.M.d");
 		SimplePositions dateSP = new SimplePositions();
 		dateSP.buildHorizontalOffset(164).buildVerticalOffset(560);
 		BufferedImage dateBi = drawText(date2, zbfont, 38,
 				color, 240, 0, 0, true);
 		
		DecimalFormat df=new DecimalFormat("00000000");
		SimplePositions count0 = new SimplePositions();
		count0.buildHorizontalOffset(162).buildVerticalOffset(638);
		BufferedImage countBI0 = drawText(df.format(Integer.parseInt(count)), zbfont, 70, color,
				300, 0, 0, true);
		SimplePositions[] sp = { pSP,nameSP,dateSP,count0};

		BufferedImage[] bis = { p,nameBI,dateBi,countBI0};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
