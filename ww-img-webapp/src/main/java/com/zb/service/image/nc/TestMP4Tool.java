package com.zb.service.image.nc;

import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.util.Calendar;
import java.util.Properties;

import com.gif4j.GifEncoder;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;
import com.zb.util.Part;

public class TestMP4Tool extends BaseTool implements OneDraw{
	public TestMP4Tool(StorageService storageService) {
		super(storageService);
	}

	public TestMP4Tool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "";
		StorageService storageService = new StorageService();
		TestMP4Tool tyt = new TestMP4Tool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼",tmpPath0,  //
				null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 12;
	static Color color = new Color(82, 175, 170);
	static int fontType = Font.BOLD;
	
	static Properties props=System.getProperties(); //获得系统属性集    
	static String osName = props.getProperty("os.name"); //操作系统名称    
	static String osArch = props.getProperty("os.arch"); //操作系统构架    
	static String osVersion = props.getProperty("os.version"); //操作系统版本 
	
	private final static String PATH = //"http://imgzb.zhuangdianbi.com/582d99fd0cf2807db7bc75bd";//tupian
			"http://imgzb.zhuangdianbi.com/582ab9840cf2be8e8e56d7a5";//自己录音
			//"http://imgzb.zhuangdianbi.com/583267e40cf24a945fcd0f1c";//歌曲
			//"F:\\pics\\test.flv";
	static String WINDOWSPATH = "D:\\ffmpeg-20161110-872b358-win64-static\\bin\\ffmpeg.exe";
	static String LINUXPATH = "";
	static String WINDOWS = "Windows";
	static String LINUX = "Linux";
	static String RANDOMNAME = String.valueOf(Calendar.getInstance().getTimeInMillis())+ Math.round(Math.random() * 100000);
	static String ABSOULTPATH  =props.getProperty("java.io.tmpdir");
	static String FOLDER = ABSOULTPATH+PATH.substring(PATH.length()-24, PATH.length())+"\\";
	static String MP3OUTPATH =FOLDER+PATH.substring(PATH.length()-24, PATH.length())+".mp3";
	static String MP4OUTPATH =FOLDER+PATH.substring(PATH.length()-24, PATH.length())+".mp4";
	static String MP4OUTPATH1 =FOLDER+PATH.substring(PATH.length()-24, PATH.length())+"1.mp4";
	static String MP4OUTPATH2 =FOLDER+PATH.substring(PATH.length()-24, PATH.length())+"2.mp4";
	static String MP4OUTPATH3 =FOLDER+PATH.substring(PATH.length()-24, PATH.length())+"3.mp4";
	static String MP4OUTPATH4 =FOLDER+PATH.substring(PATH.length()-24, PATH.length())+"1.ts";
	static String MP4OUTPATH5 =FOLDER+PATH.substring(PATH.length()-24, PATH.length())+"2.ts";
	static String MP4OUTPATH6 =FOLDER+PATH.substring(PATH.length()-24, PATH.length())+"3.ts";
	static String MP4OUTPATH7 =FOLDER+PATH.substring(PATH.length()-24, PATH.length())+"out.mp4";
	static String MP4COMPATH =FOLDER+PATH.substring(PATH.length()-24, PATH.length())+"s.mp4";
	static String PICOUTPATH =FOLDER+PATH.substring(PATH.length()-24, PATH.length())+"%d.jpg";
	static String PICTUREPATH =FOLDER+RANDOMNAME+".png";
	static Part part = new Part();
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		this.createDir(FOLDER);
		String path = "F:\\pics\\IMG_0490.mp4";
		
		File file  = new File(path);
		
		String osNames = "";
		
		if(osName.startsWith(WINDOWS)){
			osNames = WINDOWSPATH;
		}else{
			osNames = LINUXPATH;
		}
		
		if(new File(MP3OUTPATH).exists()){
			new File(MP3OUTPATH).delete();
		}
		new Part().partMP3(osNames, path, MP3OUTPATH);//分离MP3
		new Part().partMP4(osNames, path, MP4OUTPATH);//分离MP4
		new Part().partOneMP4(osNames, MP4OUTPATH, "00:00:00", "00:00:03", MP4OUTPATH1);//分割第一段
		new Part().partOneMP4(osNames, MP4OUTPATH, "00:00:03", "00:00:04", MP4OUTPATH2);//分割第二段
		new Part().partOneMP4(osNames, MP4OUTPATH, "00:00:05", "00:00:26", MP4OUTPATH3);//分割第三段
		new Part().partPic(osNames, MP4OUTPATH2, PICOUTPATH);//生成要改变的MP4帧
		if(new File(MP4OUTPATH2).exists()){
			new File(MP4OUTPATH2).delete();
		}
		new Part().ComPicMP4(osNames, PICOUTPATH, MP4OUTPATH2);//经过处理后的图片合成新的第二段视频
		
		new Part().changeTs(osNames, MP4OUTPATH1, MP4OUTPATH4);//分别转码 来进行拼接
		new Part().changeTs(osNames, MP4OUTPATH2, MP4OUTPATH5);
		new Part().changeTs(osNames, MP4OUTPATH3, MP4OUTPATH6);
		
		String shipin[] = {MP4OUTPATH4,MP4OUTPATH5,MP4OUTPATH6};
		
		new Part().tsToMP4(osNames, shipin, MP4OUTPATH7);//拼接成新的MP4
		new Part().ComMP3MP4(osNames, MP3OUTPATH, MP4OUTPATH7, MP4COMPATH);//声音和新视频进行合成
		
		System.out.println("ok");
		
		
		byte[] by = new byte[(int) file.length()];
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        try {
            InputStream is = new FileInputStream(new File(MP4COMPATH));
            
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
       
		return super.saveFile(bytestream, "mp4");
	}
	
	public boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (dir.exists()) {  
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");  
            return false;  
        }  
        if (!destDirName.endsWith(File.separator)) {  
            destDirName = destDirName + File.separator;  
        }  
        //创建目录  
        if (dir.mkdirs()) {  
            System.out.println("创建目录" + destDirName + "成功！");  
            return true;  
        } else {  
            System.out.println("创建目录" + destDirName + "失败！");  
            return false;  
        }  
    }  
}
