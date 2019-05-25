package com.zb.dubbo;

import com.zb.core.web.ReMsg;

public interface RpcWorldChatService {

	public ReMsg worldChat(Long uid, Long atId, Long typeId, String fmt, String txt);
}
