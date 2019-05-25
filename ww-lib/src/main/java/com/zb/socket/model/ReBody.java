package com.zb.socket.model;

import com.zb.common.Constant.ReCode;

public class ReBody extends MapBody {
	
	

	public ReBody(Long id, int st, ReCode recode) {
		super.data.put("id", id);
		super.data.put("st", st);
		super.data.put("code", recode.reCode());
		super.data.put("msg", recode.getMsg());
	}

}
