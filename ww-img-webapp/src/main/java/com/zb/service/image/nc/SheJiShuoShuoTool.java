package com.zb.service.image.nc;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.FiveDraw;

public class SheJiShuoShuoTool extends BaseTool implements FiveDraw{
	public SheJiShuoShuoTool(StorageService storageService) {
		super(storageService);
	}

	public SheJiShuoShuoTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57873de20cf220090c53a586";
		StorageService storageService = new StorageService();
		SheJiShuoShuoTool tyt = new SheJiShuoShuoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("58测试头像.png","装点逼包邮到家包邮到家包邮到家","包邮到家包邮到家包邮到家包邮到家包邮到家包邮22","测试.png","温婉可爱-二妹的座驾", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 24;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String three, String four, String five, String tmpBackPic, String count)
			throws IOException {
		//qq头像
		BufferedImage p = super.getImg(one);
		p = ChangeImage.resizeCrop(p, 58);
		p = ChangeImage.makeRoundedCorner(p, 58);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(14).buildVerticalOffset(142);
		
		//qq昵称
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(83).buildVerticalOffset(135);
		BufferedImage nameBI = drawText(two, zbfont, fontSize, color,
				400, 0, 0, true);
		//说说内容
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(16).buildVerticalOffset(220);
		BufferedImage nameBI2 = drawText(three, zbfont, fontSize, color,
				800, 0, 0, true);
		//玩家照片
		BufferedImage p2 = super.compressImage(four, 285, 377);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(15).buildVerticalOffset(270);
		
		//手机型号
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(35).buildVerticalOffset(690);
		BufferedImage nameBI3 = drawText(five, zbfont, 17, new Color(114,114,114),
				800, 0, 0, true);
		
		// date发布时间
		String date = DateUtil.dateFormat(new Date(), "HH:ss");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(83).buildVerticalOffset(170);
		BufferedImage dateBi = drawText("今天"+date, zbfont, 17,
				new Color(114,114,114), 240, 0, 0, true);
		// date现在时间
		String date2 = DateUtil.dateFormat(new Date(), "HH:ss");
		SimplePositions dateSP2 = new SimplePositions();
		dateSP2.buildHorizontalOffset(10).buildVerticalOffset(3);
		BufferedImage dateBi2 = drawText(date2, zbfont, 21,
				new Color(255,255,255), 240, 0, 0, true);
		
		SimplePositions[] sp = {nameSP,nameSP2,nameSP3,dateSP,dateSP2,pSP,pSP2 };

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3,dateBi,dateBi2, p,p2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
