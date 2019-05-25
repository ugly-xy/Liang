package com.zb.models.test;

import java.util.ArrayList;
import java.util.List;

import com.zb.models.AbstractDocument;

public class Paper extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 434361807307900409L;
	private int type;// 1 分数型 2跳转型
	private String title;
	private String content;

	private List<Question> questions = new ArrayList<Question>();

	private List<Result> result = new ArrayList<Result>();

}
