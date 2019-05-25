package com.zb.dubbo;

import com.zb.core.web.ReMsg;

public interface RpcRoomService {

	public ReMsg inRoom(long uid, long roomId, int model);

	public ReMsg outRoom(long uid, long roomId);

	public void disconnectRoom(long uid, long roomId);

	public void chat(long uid, long roomId, String fmt, String txt);

	public ReMsg roomUserStatus(long uid, long roomId);

	public void speaking(long uid, long roomId);

	public void cancelSpeak(long uid, long roomId);

	public void pauseSpeaking(long uid, long roomId);

}
