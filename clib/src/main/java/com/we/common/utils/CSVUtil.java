package com.we.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

public class CSVUtil {

	static final Logger log = LoggerFactory.getLogger(CSVUtil.class);

	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			long line = 1;
			// 一次读入一行，直到读入null为文件结束

			List<DBObject> list = new ArrayList<DBObject>();
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				String[] as = tempString.split(",");
				DBObject dbo = new BasicDBObject("_id", line).append("city", as[0]).append("code3", as[1])
						.append("code4", as[2]).append("name", as[3]).append("enCity", as[4]);
				list.add(dbo);
				line++;
			}
			MongoClient mongoClient = new MongoClient("192.168.1.112", 27017);
			DB db = mongoClient.getDB("zb_db");
			DBCollection collection = db.getCollection("airport");
			collection.insert(list, WriteConcern.ACKNOWLEDGED).getN();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public static void main(String[] args) {
		CSVUtil.readFileByLines("/Users/lava/Downloads/airport.csv");
	}
}
