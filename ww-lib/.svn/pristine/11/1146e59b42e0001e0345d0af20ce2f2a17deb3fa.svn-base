package com.zb.dubbo;

import com.zb.core.web.ReMsg;

public interface RpcWerewolfKillService {

	/** 玩家准备 */
	public ReMsg ready(long uid, long roomId);

	/** 玩家取消准备 */
	public ReMsg cancelReady(long uid, long roomId);

	/** 上树 */
	public ReMsg climbTheTree(long uid, long roomId);

	/** 下树 */
	public ReMsg getDownTheTree(long uid, long roomId, int seatNo);

	/** 游戏开始 */
	public ReMsg start(long uid, long roomId);

	/** 取消发言 */
	public ReMsg cancelSpeak(long uid, long roomId);

	/** 守卫兽人 */
	public ReMsg defend(long uid, long did, long roomId);

	/** 狼人杀人 */
	public ReMsg kill(long uid, long kid, long roomId);

	/** 狼人取消杀人 */
	public ReMsg cancelKill(long uid, long roomId);

	/** 狼人自爆 */
	public ReMsg wolfBoom(long uid, long roomId);

	/** 预言家验人 */
	public ReMsg check(long uid, long cid, long roomId);

	/** 女巫救人 */
	public ReMsg save(long uid, long sid, long roomId);

	/** 女巫杀人 */
	public ReMsg poison(long uid, long pid, long roomId);

	/** 猎人杀人 */
	public ReMsg shot(long uid, long kid, long roomId);

	/** 猎人取消杀人 */
	public ReMsg cancelShot(long uid, long roomId);

	/** 发言 */
	public ReMsg speak(long uid, long roomId);

	/** 暂停发言 */
	public ReMsg pauseSpeak(long uid, long roomId);

	/** 投票 */
	public ReMsg vote(long uid, long vid, long roomId);

	/** 聊天 */
	public ReMsg groupChat(Long uid, String fmt, String txt, Long roomId);

	/** 买身份 */
	public ReMsg buy(long uid, long roomId, int iden);

}
