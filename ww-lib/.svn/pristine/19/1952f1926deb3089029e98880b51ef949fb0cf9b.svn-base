package com.zb.models.easemob;

import java.util.List;
import java.util.Map;

public class EmTxtMsg extends EmMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 945653271368437452L;

	public EmTxtMsg(TargetType tType, List<String> targets, String from, Map<String, Object> ext) {
		this.setTarget_type(tType.name());
		this.setTarget(targets);
		this.setFrom(from);
		this.appendMsg("type", EmMsgType.txt.name());
		this.setExt(ext);
	}

	public void msg(String msg) {
		this.appendMsg("msg", msg);
	}

}
