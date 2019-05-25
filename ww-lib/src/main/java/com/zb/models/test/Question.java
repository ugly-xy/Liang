package com.zb.models.test;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private long id;
	private String title;
	private String content;
	private int type;//0 没有content，1图片 ，2文本
	
	private List<Item> items = new ArrayList<Item>();
	
	

}
