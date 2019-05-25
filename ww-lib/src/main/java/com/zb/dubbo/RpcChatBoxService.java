package com.zb.dubbo;

import com.zb.core.web.ReMsg;

public interface RpcChatBoxService {

	/** 下麦 */
	public ReMsg getDownChat(Long operatorUid, long uid, long roomId);

	/** 上麦 */
	public ReMsg climbChat(Long operatorUid, long uid, long roomId, int seatNo);

	/** 锁定座位 */
	public ReMsg lockSeat(long operatorUid, long roomId, int seatNo);

	/** 解锁座位 */
	public ReMsg unlockSeat(long operatorUid, long roomId, int seatNo);

	/** 锁用户麦 */
	public ReMsg lockUser(long operatorUid, long roomId, long uid);

	/** 打开用户麦 */
	public ReMsg unlockUser(long operatorUid, long roomId, long uid);

	/** 全体禁言套餐 */
	public ReMsg shutupChat(long operatorUid, long roomId, Boolean voice, Boolean wordHome);
}
