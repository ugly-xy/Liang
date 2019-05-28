package com.zb.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Part {
	
	public boolean partMP3(String osName,String oldfilepath,String mp3outpath){
		
		/*if (!checkfile(oldfilepath)) {
			System.out.println(oldfilepath + " is not file");
			return false;
		}*/
		
		List<String> commend = new ArrayList<String>();
		commend.add(osName);
		commend.add("-i");//分离mp3
		commend.add(oldfilepath);
		commend.add("-vn");
		commend.add("-ar");
		commend.add("44100");
		commend.add("-ac");
		commend.add("2");
		commend.add("-ab");
		commend.add("192");
		commend.add("-f");
		commend.add("mp3");
		
		commend.add(mp3outpath);
		
		try {
			ProcessBuilder builder = new ProcessBuilder(commend);
			builder.command(commend);
			Process p = builder.start();
			doWaitFor(p);
			p.destroy();
			//new File(oldfilepath).delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean partMP4(String osName,String oldfilepath,String mp4outpath){
		
		/*if (!checkfile(oldfilepath)) {
			System.out.println(oldfilepath + " is not file");
			return false;
		}*/
		
		List<String> commend = new ArrayList<String>();
		commend.add(osName);
		commend.add("-i");//获取无声视频
		commend.add(oldfilepath);
		commend.add("-vcodec");
		commend.add("copy");
		commend.add("-an");
		
		commend.add(mp4outpath);
		
		try {
			ProcessBuilder builder = new ProcessBuilder(commend);
			builder.command(commend);
			Process p = builder.start();
			doWaitFor(p);
			p.destroy();
			//new File(oldfilepath).delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean changeTs(String osName,String oldfilepath,String tsoutpath){
		
		/*if (!checkfile(oldfilepath)) {
			System.out.println(oldfilepath + " is not file");
			return false;
		}*/
		
		List<String> commend = new ArrayList<String>();
		commend.add(osName);
		commend.add("-i");//MP4更换为ts
		commend.add(oldfilepath);
		commend.add("-vcodec");
		commend.add("copy");
		commend.add("-vbsf");
		commend.add("h264_mp4toannexb");
		
		commend.add(tsoutpath);
		
		try {
			ProcessBuilder builder = new ProcessBuilder(commend);
			builder.command(commend);
			Process p = builder.start();
			doWaitFor(p);
			p.destroy();
			//new File(oldfilepath).delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean tsToMP4(String osName,String[] oldfilepath,String tsoutpath){
		
		/*if (!checkfile(oldfilepath)) {
			System.out.println(oldfilepath + " is not file");
			return false;
		}*/
		String list = "\"concat:"+oldfilepath[0];
		if(oldfilepath.length>2){
			for(int i = 1;i<oldfilepath.length;i++){
				list  =list+"|"+oldfilepath[i];
			}
		}
		list = list+"\"";
		List<String> commend = new ArrayList<String>();
		commend.add(osName);
		commend.add("-i");//ts合成mp4
		commend.add(list);
		commend.add("-vcodec");
		commend.add("copy");
		commend.add("-vbsf");
		commend.add("h264_mp4toannexb");
		
		commend.add(tsoutpath);
		
		try {
			ProcessBuilder builder = new ProcessBuilder(commend);
			builder.command(commend);
			Process p = builder.start();
			doWaitFor(p);
			p.destroy();
			//new File(oldfilepath).delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean partOneMP4(String osName,String oldfilepath,String starTime,String endTime,String mp4outpath){
		
		/*if (!checkfile(oldfilepath)) {
			System.out.println(oldfilepath + " is not file");
			return false;
		}*/
		
		List<String> commend = new ArrayList<String>();
		commend.add(osName);
		commend.add("-i");//截取视频段
		commend.add(oldfilepath);
		commend.add("-ss");
		commend.add(starTime);//start
		commend.add("-t");
		commend.add(endTime);//end
		commend.add("-acodec");
		commend.add("copy");
		commend.add("-vcodec");
		commend.add("copy");
		commend.add(mp4outpath);
		
		try {
			ProcessBuilder builder = new ProcessBuilder(commend);
			builder.command(commend);
			Process p = builder.start();
			doWaitFor(p);
			p.destroy();
			//new File(oldfilepath).delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean ComMP3MP4(String osName,String mp3path,String mp4path,String mp4outpath){
		
		/*if (!checkfile(mp4path)) {
			System.out.println(mp4path + " is not file");
			return false;
		}
		
		if (!checkfile(mp3path)) {
			System.out.println(mp3path + " is not file");
			return false;
		}
		*/
		List<String> commend = new ArrayList<String>();
		commend.add(osName);
		commend.add("-i");//合成音视频
		commend.add(mp3path);
		commend.add("-i");
		commend.add(mp4path);
		commend.add("-f");
		commend.add("mp4");
		commend.add("-y");
		commend.add(mp4outpath);
		
		
		try {
			ProcessBuilder builder = new ProcessBuilder(commend);
			builder.command(commend);
			Process p = builder.start();
			doWaitFor(p);
			p.destroy();
			//new File(oldfilepath).delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean cutDown(String osName,String mp4path,String mp4outpath){
		
		List<String> commend = new ArrayList<String>();
		commend.add(osName);
		commend.add("-i");//压缩视频
		commend.add(mp4path);
		commend.add("-r");
		commend.add("10");
		commend.add("-b:a");
		commend.add("32k");
		commend.add(mp4outpath);
		
		
		try {
			ProcessBuilder builder = new ProcessBuilder(commend);
			builder.command(commend);
			Process p = builder.start();
			doWaitFor(p);
			p.destroy();
			//new File(oldfilepath).delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean partPic(String osName,String oldfilepath,String picpath){//,String starTime,String endTime
		
		/*if (!checkfile(oldfilepath)) {
			System.out.println(oldfilepath + " is not file");
			return false;
		}*/
		//D:\\ffmpeg-20161110-872b358-win64-static\\bin\\ffmpeg.exe -i f:\\pics\\test2.mp4 f:\\pics\\%d.jpg -vcodec mjpeg -ss 0:00:00 -t 00:00:05
		List<String> commend = new ArrayList<String>();
		commend.add(osName);
		commend.add("-i");//分帧
		commend.add(oldfilepath);
		commend.add(picpath);
		commend.add("-vcodec");
		commend.add("mjpeg");
		/*commend.add("-ss");
		commend.add(starTime);
		commend.add("-t");
		commend.add(endTime);*/
		
		try {
			ProcessBuilder builder = new ProcessBuilder(commend);
			builder.command(commend);
			Process p = builder.start();
			doWaitFor(p);
			p.destroy();
			//new File(oldfilepath).delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean 	ComPicMP4(String osName,String oldpicpath,String mp4outpath){
		
		/*if (!checkfile(oldpicpath)) {
			System.out.println(oldpicpath + " is not file");
			return false;
		}*/
		
		List<String> commend = new ArrayList<String>();
		commend.add(osName);
		commend.add("-y");//图片合成
		commend.add("-r");
		commend.add("10");
		commend.add("-i");
		commend.add(oldpicpath);
		commend.add("-absf");
		commend.add("aac_adtstoasc");
		commend.add(mp4outpath);
		
		try {
			ProcessBuilder builder = new ProcessBuilder(commend);
			builder.command(commend);
			Process p = builder.start();
			doWaitFor(p);
			p.destroy();
			//new File(oldfilepath).delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static int doWaitFor(Process p) {
		   InputStream in = null;
		   InputStream err = null;
		   int exitValue = -1; // returned to caller when p is finished
		   try {
		    //System.out.println("comeing");
		    in = p.getInputStream();
		    err = p.getErrorStream();
		    boolean finished = false; // Set to true when p is finished
		  
		    while (!finished) {
		     try {
		      while (in.available() > 0) {
		       Character c = new Character((char) in.read());
		       //System.out.print(c);
		      }
		      while (err.available() > 0) {
		       Character c = new Character((char) err.read());
		       //System.out.print(c);
		      }
		  
		      exitValue = p.exitValue();
		      finished = true;
		  
		     } catch (IllegalThreadStateException e) {
		      Thread.currentThread().sleep(500);
		     }
		    }
		   } catch (Exception e) {
		    //System.err.println("doWaitFor();: unexpected exception - "
		     // + e.getMessage());
		   } finally {
		    try {
		     if (in != null) {
		      in.close();
		     }
		  
		    } catch (IOException e) {
		     //System.out.println(e.getMessage());
		    }
		    if (err != null) {
		     try {
		      err.close();
		     } catch (IOException e) {
		      //System.out.println(e.getMessage());
		     }
		    }
		   }
		   return exitValue;
		  }
	//流转文件
	public void inputstreamtofile(InputStream ins,File file) throws Exception{
		   OutputStream os = new FileOutputStream(file);
		   int bytesRead = 0;
		   byte[] buffer = new byte[8192];
		   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
		      os.write(buffer, 0, bytesRead);
		   }
		   os.close();
		   ins.close();
		}
	//创建文件夹
	public boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (dir.exists()) {  
            //System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");  
            return false;  
        }  
        if (!destDirName.endsWith(File.separator)) {  
            destDirName = destDirName + File.separator;  
        }  
        //创建目录  
        if (dir.mkdirs()) {  
            //System.out.println("创建目录" + destDirName + "成功！");  
            return true;  
        } else {  
            //System.out.println("创建目录" + destDirName + "失败！");  
            return false;  
        }  
    } 
	
	//转换成输出流
	public ByteArrayOutputStream outStream(String path){
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        try {
            InputStream is = new FileInputStream(new File(path));
            
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
			bytestream.flush();
			bytestream.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        
		return bytestream;
	}
	//备用删除文件夹
	public boolean deleteFolder(String url){  
	    File file=new File(url);  
	    if(!file.exists()){  
	        return false;  
	    }  
	    if(file.isFile()){  
	        file.delete();  
	        return true;  
	    }else{  
	        File[] files=file.listFiles();  
	        for(int i=0;i<files.length;i++){  
	            String root=files[i].getAbsolutePath();//得到子文件或文件夹的绝对路径  
	            //System.out.println(root);  
	            deleteFolder(root);  
	        }  
	        file.delete();  
	        return true;  
	    }
	}
	
	//删除文件和目录
	public void clearFiles(String workspaceRootPath){
	     File file = new File(workspaceRootPath);
	     if(file.exists()){
	         deleteFile(file);
	    }
	}
	private void deleteFile(File file){
	     if(file.isDirectory()){
	          File[] files = file.listFiles();
	          for(int i=0; i<files.length; i++){
	               deleteFile(files[i]);
	          }
	     }
	     file.delete();
	 }
	//删除最后一个文件
	public void isExist(String folderpath,String mp4path){
		while(true){
			this.clearFiles(folderpath);
			if(!new File(mp4path).exists()){
				break;
			}
		}
	}
	private static boolean checkfile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		}
		return true;
	}
}
