package com.zb.dubbo.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zb.core.web.ReMsg;
import com.zb.dubbo.RpcWerewolfKillService;
import com.zb.service.room.WerewolfKillService;

public class RpcWerewolfKillServiceImpl implements RpcWerewolfKillService {

	@Autowired
	WerewolfKillService werewolfKillService;

	@Override
	public ReMsg ready(long uid, long roomId) {
		return werewolfKillService.ready(uid, roomId);
	}

	@Override
	public ReMsg cancelReady(long uid, long roomId) {
		return werewolfKillService.cancelReady(uid, roomId);
	}

	@Override
	public ReMsg defend(long uid, long did, long roomId) {
		return werewolfKillService.defend(uid, did, roomId);
	}

	@Override
	public ReMsg cancelSpeak(long uid, long roomId) {
		return werewolfKillService.cancelSpeak(uid, roomId);
	}

	@Override
	public ReMsg climbTheTree(long uid, long roomId) {
		return werewolfKillService.climbTheTree(uid, roomId);
	}

	@Override
	public ReMsg getDownTheTree(long uid, long roomId, int seatNo) {
		return werewolfKillService.getDownTheTree(uid, roomId, seatNo);
	}

	@Override
	public ReMsg start(long uid, long roomId) {
		return werewolfKillService.start(uid, roomId);
	}

	@Override
	public ReMsg kill(long uid, long kid, long roomId) {
		return werewolfKillService.kill(uid, kid, roomId);
	}

	@Override
	public ReMsg cancelKill(long uid, long roomId) {
		return werewolfKillService.cancelKill(uid, roomId);
	}

	@Override
	public ReMsg wolfBoom(long uid, long roomId) {
		return werewolfKillService.wolfBoom(uid, roomId);
	}

	@Override
	public ReMsg check(long uid, long cid, long roomId) {
		return werewolfKillService.check(uid, cid, roomId);
	}

	@Override
	public ReMsg save(long uid, long sid, long roomId) {
		return werewolfKillService.save(uid, sid, roomId);
	}

	@Override
	public ReMsg poison(long uid, long pid, long roomId) {
		return werewolfKillService.poison(uid, pid, roomId);
	}

	@Override
	public ReMsg shot(long uid, long kid, long roomId) {
		return werewolfKillService.shot(uid, kid, roomId);
	}

	@Override
	public ReMsg cancelShot(long uid, long roomId) {
		return werewolfKillService.cancelShot(uid, roomId);
	}

	@Override
	public ReMsg speak(long uid, long roomId) {
		return werewolfKillService.speak(uid, roomId);
	}

	@Override
	public ReMsg pauseSpeak(long uid, long roomId) {
		return werewolfKillService.pauseSpeak(uid, roomId);
	}

	@Override
	public ReMsg vote(long uid, long vid, long roomId) {
		return werewolfKillService.vote(uid, vid, roomId);
	}

	@Override
	public ReMsg groupChat(Long uid, String fmt, String txt, Long roomId) {
		return werewolfKillService.groupChat(uid, fmt, txt, roomId);
	}

	@Override
	public ReMsg buy(long uid, long roomId, int iden) {
		return werewolfKillService.buy(uid, roomId, iden);
	}

}
