package com.zb.util;

import java.io.*;
import java.util.*;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;

public class ConvertVideo extends BaseTool{
	
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
	static String MP3OUTPATH =props.getProperty("java.io.tmpdir")+PATH.substring(PATH.length()-24, PATH.length())+".mp3";
	static String MP4OUTPATH =props.getProperty("java.io.tmpdir")+PATH.substring(PATH.length()-24, PATH.length())+".mp4";
	static String MP4COMPATH =props.getProperty("java.io.tmpdir")+PATH.substring(PATH.length()-24, PATH.length())+"s.mp4";
	static String PICOUTPATH =props.getProperty("java.io.tmpdir")+PATH.substring(PATH.length()-24, PATH.length())+"%d.jpg";
	static String PICTUREPATH = props.getProperty("java.io.tmpdir")+RANDOMNAME+".png";
	
	
	
	public ConvertVideo(StorageService storageService) {
		super(storageService);
	}

	public ConvertVideo() {
	}
	
	
	
	
	public static void main(String[] args) throws Exception{
		StorageService storageService = new StorageService();
		ConvertVideo tyt = new ConvertVideo(storageService);
		
		/*System.out.println(osName);
		System.out.println(osArch);
		System.out.println(osVersion);*/
		System.out.println(MP3OUTPATH);
		
		InputStream in = tyt.getFile(PATH);//根据流获取文件 也可以直接用本地文件 就忽略上面继承（此处是调用方法将流生成文件）
		
		File file=File.createTempFile("amp", ".mp4");
		
		inputstreamtofile(in, file);
		
		String osNames = "";
		
		if(osName.startsWith(WINDOWS)){
			osNames = WINDOWSPATH;
		}else{
			osNames = LINUXPATH;
		}
		
		if(new File(MP3OUTPATH).exists()){
			new File(MP3OUTPATH).delete();
		}
		new Part().partMP3(osNames, "F:\\pics\\IMG_0490.mp4", "F:\\IMG_0490.mp3");//分离MP3
		new Part().partMP4(osNames, "F:\\pics\\IMG_0490.mp4", "F:\\IMG_0490.mp4");//分离MP4
		new Part().partOneMP4(osNames, "F:\\IMG_0490.mp4", "00:00:00", "00:00:03", "F:\\one.mp4");//分割第一段
		new Part().partOneMP4(osNames, "F:\\IMG_0490.mp4", "00:00:03", "00:00:04", "F:\\two.mp4");//分割第二段
		new Part().partOneMP4(osNames, "F:\\IMG_0490.mp4", "00:00:04", "00:00:26", "F:\\three.mp4");//分割第三段
		new Part().partPic(osNames, "F:\\two.mp4", "F:\\%d.jpg");//生成要改变的MP4帧
		if(new File("F:\\two.mp4").exists()){
			new File("F:\\two.mp4").delete();
		}
		new Part().ComPicMP4(osNames, "F:\\%d.jpg", "F:\\two.mp4");//经过处理后的图片合成新的第二段视频
		
		new Part().changeTs(osNames, "F:\\one.mp4", "F:\\one.ts");//分别转码 来进行拼接
		new Part().changeTs(osNames, "F:\\two.mp4", "F:\\two.ts");
		new Part().changeTs(osNames, "F:\\three.mp4", "F:\\three.ts");
		
		String shipin[] = {"F:\\one.ts","F:\\two.ts","F:\\three.ts"};
		
		new Part().tsToMP4(osNames, shipin, "F:\\out.mp4");//拼接成新的MP4
		new Part().ComMP3MP4(osNames, "F:\\IMG_0490.mp3", "F:\\out.mp4", "F:\\last.mp4");//声音和新视频进行合成
		
		System.out.println("ok");
		
		/*if (!checkfile(file.getAbsolutePath())) {
			System.out.println(file.getAbsolutePath() + " is not file");
			return;
		}
		if (process(file.getAbsolutePath())) {
			System.out.println("ok");
			
		}*/
		//file.deleteOnExit();
	}

	private static boolean process(String file) {
		int type = 0;
		boolean status = false;
		if (type == 0) {
			status = processFLV(file);// 直接将文件转为flv文件
		} 
		return status;
	}


	private static boolean checkfile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		}
		return true;
	}

	

	// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	private static boolean processFLV(String oldfilepath) {

		if (!checkfile(oldfilepath)) {
			System.out.println(oldfilepath + " is not file");
			return false;
		}
		
		
		
		return true;
	}
	
	
	public static void inputstreamtofile(InputStream ins,File file) throws Exception{
		   OutputStream os = new FileOutputStream(file);
		   int bytesRead = 0;
		   byte[] buffer = new byte[8192];
		   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
		      os.write(buffer, 0, bytesRead);
		   }
		   os.close();
		   ins.close();
		}

}

