package com.we.common.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class SimpleDBObject  {
	
	private DBObject dbo;
	public SimpleDBObject(DBObject dbo){
		this.dbo = dbo;
	}
	
	public static SimpleDBObject build(DBObject dbo){
		return new SimpleDBObject(dbo);
	}


	public String getString(String field) {
		return DboUtil.getString(dbo, field);
	}
	
	public  <T> T get( String field,Class<T> c) {
		return DboUtil.get(dbo, field,c);
	}

	public Boolean getBool(String field)  {
		return DboUtil.getBool(dbo, field);
	}
	
	public Integer getInt(String field)  {
		return DboUtil.getInt(dbo, field);
	}
	
	public Long getLong(String field)  {
		return DboUtil.getLong(dbo, field);
	}
	
	public Byte getByte(String field)  {
		return DboUtil.getByte(dbo, field);
	}
	
	public Short getShort(String field)  {
		return DboUtil.getShort(dbo, field);
	}
	
	public Float getFloat(String field)  {
		return DboUtil.getFloat(dbo, field);
	}
	
	public double getDouble(String field)  {
		return DboUtil.getDouble(dbo, field);
	}
	
	public static void main(String [] args){
		DBObject dbo = new BasicDBObject("aaa",1);
		SimpleDBObject sdbo = SimpleDBObject.build(dbo);
		System.out.println(sdbo.getInt("aaa"));
		System.out.println(sdbo.getInt("bbb"));
		System.out.println(sdbo.getString("abc"));
		System.out.println(sdbo.getShort("abc"));
	}

}
