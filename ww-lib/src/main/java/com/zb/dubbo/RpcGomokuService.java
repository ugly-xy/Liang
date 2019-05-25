package com.zb.dubbo;

import com.zb.core.web.ReMsg;

public interface RpcGomokuService {

	public ReMsg putDown(long roomId, long uid, int x, int y);

	public void drawApply(long roomId, long uid);

	public void drawAgree(long roomId, long uid);

	public void drawRefuse(long roomId, long uid);

	public void regretApply(long roomId, long uid);

	public void regretAgree(long roomId, long uid);

	public void regretRefuse(long roomId, long uid);

	public void sayUncle(long roomId, long uid);

}
