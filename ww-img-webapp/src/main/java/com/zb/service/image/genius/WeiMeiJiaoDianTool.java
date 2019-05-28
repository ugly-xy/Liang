package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.LensBlurFilter;
import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class WeiMeiJiaoDianTool extends BaseTool implements TwoDraw{
	public WeiMeiJiaoDianTool(StorageService storageService) {
		super(storageService);
	}

	public WeiMeiJiaoDianTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/578c78600cf2961bcd0dc336";
		StorageService storageService = new StorageService();
		WeiMeiJiaoDianTool tyt = new WeiMeiJiaoDianTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("测试.png","可是我有时候宁愿选择留恋不放手", tmpPath0,null));
		
		
	}
	
	static ZbFont zbfont = ZbFont.楷体常规;
	static int fontSize = 24;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String di = "http://imgzb.zhuangdianbi.com/578c784f0cf2961bcd0dc320";
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.compressImage(one, 520, 800);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		BufferedImage p3 = super.getImg(di);
		SimplePositions pSP3 = new SimplePositions();
		pSP3.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		LensBlurFilter  bl = new LensBlurFilter();
		p = bl.filter(p, null);
		
		BufferedImage p2 = super.compressImage(one, 520, 800);
		p2 = ChangeImage.resizeCrop(p2, 184);
		p2 = ChangeImage.makeRoundedCorner(p2, 184);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(168).buildVerticalOffset(180);
		
		SimplePositions nameSP = null;
		SimplePositions nameSP2 = null;
		BufferedImage nameBI =null;
		BufferedImage nameBI2 =null;
		
		if(two.length()==15){
			//前6个字
			nameSP = new SimplePositions();
	 		nameSP.buildHorizontalOffset(260-((6*fontSize))/2).buildVerticalOffset(497);
	 		nameBI = drawText(two.substring(0, 3)+" "+two.substring(3, 6), zbfont, fontSize,
	 				color, 400, 0, 0, true);
	 		
	 		
	 		//后几个字
	 		nameSP2 = new SimplePositions();
	 		nameSP2.buildHorizontalOffset(260-(((two.length()-6)*fontSize))/2).buildVerticalOffset(530);
	 		nameBI2 = drawText(two.substring(6, two.length()), zbfont, fontSize,
	 				color, 400, 0, 0, true);
		}else if(two.length()<15){
			if(two.length()<=6){
				//前6个字
				nameSP = new SimplePositions();
		 		nameSP.buildHorizontalOffset(260-((two.length()*fontSize))/2).buildVerticalOffset(497);
		 		nameBI = drawText(two, zbfont, fontSize,
		 				color, 400, 0, 0, true);
		 		nameSP2 = new SimplePositions();
		 		nameSP2.buildHorizontalOffset(155).buildVerticalOffset(530);
		 		nameBI2 = drawText("", zbfont, fontSize,
		 				color, 400, 0, 0, true);
			}else{
				//前6个字
				nameSP = new SimplePositions();
		 		nameSP.buildHorizontalOffset(260-((6*fontSize))/2).buildVerticalOffset(497);
		 		nameBI = drawText(two.substring(0, 3)+" "+two.substring(3, 6), zbfont, fontSize,
		 				color, 400, 0, 0, true);
		 		nameSP2 = new SimplePositions();
		 		nameSP2.buildHorizontalOffset(260-(((two.length()-6)*fontSize))/2).buildVerticalOffset(530);
		 		nameBI2 = drawText(two.substring(6, two.length()), zbfont, fontSize,
		 				color, 400, 0, 0, true);
			}
		}
		
		
	 	// date
 		
 		
 		
		/*String date = DateUtil.dateFormat(new Date(), "MM");
		String date3 = DateUtil.dateFormat(new Date(), "M月");
		String str = date;
	    SimpleDateFormat sdf = new SimpleDateFormat("MM");
	    Date date2 = null;
		try {
			date2 = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    sdf = new SimpleDateFormat("MMMMM",Locale.US);
		String daxie = PinYinUtil.toPinyinUp(sdf.format(date2).toString());
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(155).buildVerticalOffset(520);
		BufferedImage dateBi = drawText(daxie +"  "+ date3, zbfont, fontSize,
				color, 240, 0, 0, true);*/
		
		SimplePositions[] sp = { pSP,pSP3,pSP2,nameSP,nameSP2};

		BufferedImage[] bis = { p, p3,p2,nameBI,nameBI2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
