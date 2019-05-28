package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class AiWoBuPaTool extends BaseTool implements OneDraw{
	public AiWoBuPaTool(StorageService storageService) {
		super(storageService);
	}

	public AiWoBuPaTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57bd3c600cf28d894a8577e6";
		StorageService storageService = new StorageService();
		AiWoBuPaTool tyt = new AiWoBuPaTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点比", tmpPath0,  
				null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 18;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		
		String hua = "近日，中国游泳名将傅园慧隔空问到："+one+"，爱我，你怕了吗？";
		
		//一句话
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(43).buildVerticalOffset(365);
		BufferedImage nameBI = drawText(hua, zbfont, fontSize, color,
				720, 150, 0, true);
		SimplePositions[] sp = {nameSP};

		BufferedImage[] bis = { nameBI};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
