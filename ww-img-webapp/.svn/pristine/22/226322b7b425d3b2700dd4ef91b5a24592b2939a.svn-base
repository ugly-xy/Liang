package com.zb.service.image.nc;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.genius.GifTool;
import com.zb.service.image.i.OneDraw;
import com.zb.util.Part;

public class TestMP42Tool extends BaseTool implements OneDraw{
	public TestMP42Tool(StorageService storageService) {
		super(storageService);
	}

	public TestMP42Tool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "";
		StorageService storageService = new StorageService();
		TestMP42Tool tyt = new TestMP42Tool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼",tmpPath0,  //
				null));
	}
	
	static Properties props=System.getProperties(); //获得系统属性集    
	static String osName = props.getProperty("os.name"); //操作系统名称    
	static String osArch = props.getProperty("os.arch"); //操作系统构架    
	static String osVersion = props.getProperty("os.version"); //操作系统版本 
	
	static Part part = new Part();
	static String mp3 = "http://imgzb.zhuangdianbi.com/583d3b580cf24a945fce3c08";  //音频
	static String video1 ="http://imgzb.zhuangdianbi.com/583d3b430cf24a945fce3c04";  //视频段1
	//http://imgzb.zhuangdianbi.com/583d47590cf24a945fce3cd7  //视频段2
	static String video2 ="http://imgzb.zhuangdianbi.com/583d4b720cf24a945fce3d58";  //视频段3
	
	static String WINDOWSPATH = "D:\\ffmpeg-20161110-872b358-win64-static\\bin\\ffmpeg.exe";
	static String LINUXPATH = "";
	static String WINDOWS = "Windows";
	static String LINUX = "Linux";
	static String RANDOMNAME = String.valueOf(Calendar.getInstance().getTimeInMillis())+ Math.round(Math.random() * 100000);
	static String ABSOULTPATH  =props.getProperty("java.io.tmpdir");
	
	static String FOLDER = ABSOULTPATH+RANDOMNAME+"\\";
	static String picName = RANDOMNAME;
	static String pic[]  ={"http://imgzb.zhuangdianbi.com/583d49930cf24a945fce3d13", //图1  
						"http://imgzb.zhuangdianbi.com/583d4a600cf24a945fce3d32",    //图2  
						"http://imgzb.zhuangdianbi.com/583d4a780cf24a945fce3d35",    //图3  
						"http://imgzb.zhuangdianbi.com/583d4a8f0cf24a945fce3d37",    //图4  
						"http://imgzb.zhuangdianbi.com/583d4a9e0cf24a945fce3d3b",    //图5  
						"http://imgzb.zhuangdianbi.com/583d64790cf24a945fce40b3",    //图6  
						"http://imgzb.zhuangdianbi.com/583d648d0cf24a945fce40ba",    //图7  
						"http://imgzb.zhuangdianbi.com/583d64c90cf24a945fce40c6",    //图8  
						"http://imgzb.zhuangdianbi.com/583d70ad0cf24a945fce42ff",    //9   
						"http://imgzb.zhuangdianbi.com/583d70cb0cf24a945fce4309",    //10 
						"http://imgzb.zhuangdianbi.com/583d70db0cf24a945fce4315",    //11 
						"http://imgzb.zhuangdianbi.com/583d70e80cf24a945fce431a",    //12 
						"http://imgzb.zhuangdianbi.com/583d729f0cf24a945fce4361",    //13 
						"http://imgzb.zhuangdianbi.com/583d73920cf24a945fce4387",    //14 
						"http://imgzb.zhuangdianbi.com/583d73b90cf24a945fce438d",    //15 
						"http://imgzb.zhuangdianbi.com/583d73fa0cf24a945fce4391",    //16 
						"http://imgzb.zhuangdianbi.com/583d74370cf24a945fce4399",    //17 
						"http://imgzb.zhuangdianbi.com/583d74410cf24a945fce439d",    //18 
						"http://imgzb.zhuangdianbi.com/583d743b0cf24a945fce439a",    //19 
						"http://imgzb.zhuangdianbi.com/583d74ad0cf24a945fce43b2",    //20 
						"http://imgzb.zhuangdianbi.com/583d74c80cf24a945fce43bc",    //21 
						"http://imgzb.zhuangdianbi.com/583e3da30cf24a945fce4e44",    //22  
						"http://imgzb.zhuangdianbi.com/583e3e100cf24a945fce4e4f",    //23 
						"http://imgzb.zhuangdianbi.com/583e3dab0cf24a945fce4e46",    //24 
						"http://imgzb.zhuangdianbi.com/583e3e3a0cf24a945fce4e51",    //25 
						"http://imgzb.zhuangdianbi.com/583e3e3e0cf24a945fce4e52",    //26 
						"http://imgzb.zhuangdianbi.com/583e3ed30cf24a945fce4e5c",    //27 
						"http://imgzb.zhuangdianbi.com/583e3eda0cf24a945fce4e5f",    //28 
						"http://imgzb.zhuangdianbi.com/583e3edd0cf24a945fce4e60",    //29 
						"http://imgzb.zhuangdianbi.com/583e3f060cf24a945fce4e61",    //30 
						"http://imgzb.zhuangdianbi.com/583e3f0b0cf24a945fce4e62",    //31 
						"http://imgzb.zhuangdianbi.com/583e3f100cf24a945fce4e63",    //32 
						"http://imgzb.zhuangdianbi.com/583e3f310cf24a945fce4e67",    //33 
						"http://imgzb.zhuangdianbi.com/583e3f3b0cf24a945fce4e68",    //34 
						"http://imgzb.zhuangdianbi.com/583e3f3e0cf24a945fce4e69",    //35 
						"http://imgzb.zhuangdianbi.com/583e3f5e0cf24a945fce4e6c",    //36 
						"http://imgzb.zhuangdianbi.com/583e3f620cf24a945fce4e6d",    //37 
						"http://imgzb.zhuangdianbi.com/583e3f680cf24a945fce4e6f",    //38 
						"http://imgzb.zhuangdianbi.com/583e3f8c0cf24a945fce4e71",    //39 
						"http://imgzb.zhuangdianbi.com/583e3f900cf24a945fce4e72",    //40 
						"http://imgzb.zhuangdianbi.com/583e3f960cf24a945fce4e73",    //41 
						"http://imgzb.zhuangdianbi.com/583e3fb70cf24a945fce4e78",    //42 
						"http://imgzb.zhuangdianbi.com/583e3fbc0cf24a945fce4e79",    //43 
						"http://imgzb.zhuangdianbi.com/583e3fc20cf24a945fce4e7a",    //44 
						"http://imgzb.zhuangdianbi.com/583e3fe30cf24a945fce4e7e"};   //45 
	
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 30;
	static Color color = new Color(82, 175, 170);
	static int fontType = Font.BOLD;
	
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		
		tmpBackPic ="";
		
		part.createDir(FOLDER);
		String osNames = "";
		
		if(osName.startsWith(WINDOWS)){
			osNames = WINDOWSPATH;
		}else{
			osNames = LINUXPATH;
		}
		StorageService storageService = new StorageService();
		TestMP42Tool tyt = new TestMP42Tool(storageService);
		for(int i =1;i<=pic.length;i++){
					File file = new File(FOLDER+(i)+".png");
					try {
						part.inputstreamtofile(super.getFile(pic[i-1]), file);
						tyt.d(one, FOLDER+(i)+".png", null,FOLDER+(i)+".png");
						//System.out.println(ul1);
						//part.inputstreamtofile(super.getFile(ul1), file);
					} catch (Exception e) {
						e.printStackTrace();
					}
		}
		
		File sound = new File(FOLDER+picName+".mp3");
		File videoone = new File(FOLDER+picName+"1.mp4");
		
		part.ComPicMP4(osNames, FOLDER+"%d.png",FOLDER+picName+"2.mp4");
		
		
		File videothree = new File(FOLDER+picName+"3.mp4");
		try {
			part.inputstreamtofile(super.getFile(mp3), sound);
			part.inputstreamtofile(super.getFile(video1), videoone);
			part.inputstreamtofile(super.getFile(video2), videothree);
		} catch (Exception e) {
			e.printStackTrace();
		}
		part.changeTs(osNames, FOLDER+picName+"1.mp4", FOLDER+picName+"1.ts");
		
		part.changeTs(osNames, FOLDER+picName+"2.mp4", FOLDER+picName+"2.ts");
		part.changeTs(osNames, FOLDER+picName+"3.mp4", FOLDER+picName+"3.ts");
		
		
		String tss[] ={FOLDER+picName+"1.ts",FOLDER+picName+"2.ts",FOLDER+picName+"3.ts"};
		part.tsToMP4(osNames, tss, FOLDER+picName+"out.mp4");
		part.ComMP3MP4(osNames, FOLDER+picName+".mp3", FOLDER+picName+"out.mp4", FOLDER+picName+"last.mp4");
		part.cutDown(osNames, FOLDER+picName+"last.mp4", FOLDER+picName+"lasts.mp4");
		
		ByteArrayOutputStream bos = part.outStream(FOLDER+picName+"lasts.mp4");
		bos.flush();
		bos.close();
		String url = super.saveVideoFile(bos, "mp4");
		
		part.isExist(FOLDER, FOLDER+picName+"lasts.mp4");
		
		 System.out.println("test");
		//part.deleteFolder(FOLDER);
		
		return url;
	}
	
	public String d(String one,String tmpBackPic,
			String count,String outPath) throws Exception{
		
		
		//姓名
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(100).buildVerticalOffset(100);
 		BufferedImage nameBi = drawText("嘉宾:"+one, zbfont, fontSize,
 				color, 240, 0, 0, true);
		
		
		
		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = { nameBi};

		return super.getSaveFile(sp, bis, 1, tmpBackPic,outPath);
	}
}
