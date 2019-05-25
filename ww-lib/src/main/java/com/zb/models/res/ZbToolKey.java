package com.zb.models.res;

import java.util.HashMap;
import java.util.Map;

import com.zb.models.AbstractDocument;

public class ZbToolKey extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3703398879028922117L;

	public ZbToolKey() {

	}

	public ZbToolKey(Long userId) {
		this.updateTime = System.currentTimeMillis();
		this.userId = userId;
	}

	private Map<String, Long> toolKeys = new HashMap<String, Long>();

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Map<String, Long> getToolKeys() {
		return toolKeys;
	}

	public void setToolKeys(Map<String, Long> toolKeys) {
		this.toolKeys = toolKeys;
	}

	public void addKey(String mark, Long times) {
		Long exp = toolKeys.get(mark);
		if (null == exp || exp < System.currentTimeMillis()) {
			exp = System.currentTimeMillis() + times;
		} else {
			exp = exp + times;
		}
		toolKeys.put(mark, exp);
	}

	public void removeKey(String mark) {
		toolKeys.remove(mark);
	}

	public boolean hasKey(String mark) {
		Long exp = toolKeys.get(mark);
		if (null != exp && exp > System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

}
