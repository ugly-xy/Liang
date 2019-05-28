package com.zb.models.easemob;

import java.util.List;
import java.util.Map;


public class EmCmdMsg extends EmMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 945653271368437452L;
	
	public EmCmdMsg(TargetType tType,List<String> targets, String from,Map<String,Object> ext){
		this.setTarget_type(tType.name());
		this.setTarget(targets);
		this.setFrom(from);
		this.appendMsg("type", EmMsgType.cmd.name());
		this.setExt(ext);
	}
	
	public void action(String action){
		this.appendMsg("action", action);
	}
	
}
