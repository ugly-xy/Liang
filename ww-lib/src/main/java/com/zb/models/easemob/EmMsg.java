package com.zb.models.easemob;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zb.common.utils.JSONUtil;

public abstract class EmMsg  implements Serializable {
	
	
	public enum TargetType{
		users,chatgroups,chatrooms;
	}
	
	public enum EmMsgType{
		txt,img,audio,video,cmd;
	}
	
	
	
	private String target_type;
	private List<String> target;
	private String from;
	private Map<String,Object> msg = new HashMap<String,Object>();
	private Map<String,Object> ext = null;
	
	public String getTarget_type() {
		return target_type;
	}
	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}
	public List<String> getTarget() {
		return target;
	}
	public void setTarget(List<String> target) {
		this.target = target;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public Map<String, Object> getMsg() {
		return msg;
	}
	public void setMsg(Map<String, Object> msg) {
		this.msg = msg;
	}
	public Map<String, Object> getExt() {
		return ext;
	}
	public void setExt(Map<String, Object> ext) {
		this.ext = ext;
	}
	
	 Map<String,Object> appendMsg(String key,Object value){
		msg.put(key, value);
		return msg;
	}
	
	public String json(){
		return JSONUtil.beanToJson(this);
	}
}
