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
import com.zb.service.image.i.TwoDraw;

public class XinYongKaShenQingTool extends BaseTool implements TwoDraw{
	public XinYongKaShenQingTool(StorageService storageService) {
		super(storageService);
	}

	public XinYongKaShenQingTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57a063ce0cf2d285d1b6588f";
		StorageService storageService = new StorageService();
		XinYongKaShenQingTool tyt = new XinYongKaShenQingTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("BIBI","200000", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 25;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		// date
		String date0 = DateUtil.dateFormat(new Date(), "yyyy-MM-dd");
		
		//姓名
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(42).buildVerticalOffset(190);
 		BufferedImage nameBi = drawText("尊敬的"+one+":您申请的青年黑VISA普卡于"+date0+"已经通过审核,信用额度为"+two+"元"
 				+ "请留意我行寄给您的卡函,您可在两天后登录我行网站查询邮寄编号。【交通银行卡中心】", zbfont, fontSize,
 				color, 340, 0, 0, true);
 		
	 	// date
		String date = DateUtil.dateFormat(new Date(), "HH:mm");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(230).buildVerticalOffset(0);
		BufferedImage dateBi = drawText(date, zbfont, 18,
				color, 240, 0, 0, true);
		
		SimplePositions dateSP2 = new SimplePositions();
		dateSP2.buildHorizontalOffset(247).buildVerticalOffset(137);
		BufferedImage dateBi2 = drawText(date, zbfont.font(),fontType, 17,
				new Color(145,145,150), 240, 0, 0, true);
		
		SimplePositions[] sp = {nameSP,dateSP,dateSP2};
	
		BufferedImage[] bis = { nameBi,dateBi,dateBi2};
	
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
}
