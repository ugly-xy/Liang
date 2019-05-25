package com.zb.core.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BdSelector {

//	static final Logger log = LoggerFactory.getLogger(BdSelector.class);
//	private Op op;
//	private List<String> tags;
//
//	public static enum Op {
//		OR("OR"), AND("AND"), DIFF("DIFF");
//		String op;
//
//		private Op(String op) {
//			this.op = op;
//		}
//
//		public String getOp() {
//			return op;
//		}
//	}
//
//	public BdSelector() {
//
//	}
//
//	public BdSelector(Op op, List<String> tags) {
//		this.op = op;
//		this.tags = tags;
//	}
//
//	public BdSelector(Op op) {
//		this.op = op;
//	}
//
//	public BdSelector setOp(Op op) {
//		this.op = op;
//		return this;
//	}
//
//	public BdSelector setTags(List<String> tags) {
//		this.tags = tags;
//		return this;
//	}
//
//	public BdSelector addTag(String tag) {
//		if (tags == null) {
//			tags = new ArrayList<String>();
//		}
//		tags.add(tag);
//		return this;
//	}
//
//	public String toString() {
//		ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, List<String>> tag = new HashMap<String, List<String>>();
//		tag.put("tag", tags);
//		Map<String, Map<String, List<String>>> root = new HashMap<String, Map<String, List<String>>>();
//		root.put(op.getOp(), tag);
//		try {
//			return objectMapper.writeValueAsString(root);
//		} catch (JsonProcessingException e) {
//			log.error("JsonErr:", e);
//		}
//		return null;
//	}
//
//	public static void main(String[] args) {
//		System.out.println(new BdSelector(Op.AND).addTag("aaa").addTag("bbb"));
//	}
}
