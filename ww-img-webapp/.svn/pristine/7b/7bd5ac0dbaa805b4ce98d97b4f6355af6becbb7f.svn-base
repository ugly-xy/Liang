package com.zb.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.zb.service.image.BaseTool;

public class Test extends BaseTool {

	public static void main(String[] args) {
		try {
			URL url = new URL("http://zbvideo.zhuangdianbi.com/584019887284494fb85aac55.mp4"); //声明url对象      
	        URLConnection connection;
			File  file = new File("F:\\test.mp4");
	        connection = url.openConnection();
			 connection.setDoOutput(true); 
			 new Part().inputstreamtofile(connection.getInputStream(), file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //打开连接  
       
		}

}
