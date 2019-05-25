package com.zb.service.room;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.common.Constant.ActivityType;
import com.zb.core.web.ReMsg;
import com.zb.models.room.Room;
import com.zb.service.BaseService;
import com.zb.service.room.cp.AnimalChessService;
import com.zb.service.room.cp.GomokuService;
import com.zb.service.room.cp.NeuroCatService;
import com.zb.service.room.cp.SchoolWarService;
import com.zb.service.room.cp.WinmineService;

@Service
public class RoomHandlerDispatcher extends BaseService {
	static final Logger log = LoggerFactory.getLogger(RoomHandlerDispatcher.class);

	@Autowired
	RoomDefaultService roomDefaultService;

	@Autowired
	UndercoverService undercoverService;

	@Autowired
	DrawSomethingService drawSomethingService;

	@Autowired
	DiceService diceService;

	@Autowired
	SlotMachinesService slotMachinesService;

	@Autowired
	WorldChatService worldChatService;

	@Autowired
	WerewolfKillService werewolfKillService;

	@Autowired
	SouthPenguinService southPenguinService;

	@Autowired
	SchoolWarService schoolWarService;

	@Autowired
	AnimalChessService animalChessService;

	@Autowired
	GomokuService gomokuService;

	@Autowired
	SakuranService sakuranService;

	@Autowired
	ChatBoxService chatBoxService;

	@Autowired
	WinmineService winmineService;

	@Autowired
	NeuroCatService neuroCatService;

	@Autowired
	CowService cowService;

	@Autowired
	TexasService texasService;

	private final Lock lock = new ReentrantLock();

	public ReMsg handle(long roomId, int fr) {
		try {
			lock.lock();
			Room r = roomDefaultService.getRoom(roomId);
			return handle(r, fr);
		} finally {
			lock.unlock();
		}
	}

	public ReMsg handle(Room room, final long fr) {
		RoomService rs = getService(room);
		return rs.handle(room, fr);
	}

	public RoomService getService(final long roomId) {
		return getService(roomDefaultService.getRoom(roomId));
	}

	private final Lock lockS = new ReentrantLock();

	public RoomService getService(final Room room) {
		try {
			lockS.lock();
			if (room != null) {
				return getServiceByType(room.getType());
			}
		} finally {
			lockS.unlock();
		}
		return roomDefaultService;
	}

	public RoomService getServiceByType(final int type) {
		if (ActivityType.UNDERCOVER.getType() == type) {
			return undercoverService;
		} else if (ActivityType.DRAW_SOMETHING.getType() == type) {
			return drawSomethingService;
		} else if (ActivityType.DICE.getType() == type) {
			return diceService;
		} else if (ActivityType.SLOT_MACHINES.getType() == type) {
			return slotMachinesService;
		} else if (ActivityType.WEREWOLF.getType() == type) {
			return werewolfKillService;
		} else if (ActivityType.WEREWOLF6.getType() == type) {
			return werewolfKillService;
		} else if (ActivityType.WEREWOLF9.getType() == type) {
			return werewolfKillService;
		} else if (ActivityType.SOUTHPENGUIN.getType() == type) {
			return southPenguinService;
		} else if (ActivityType.SCHOOL_WAR.getType() == type) {
			return schoolWarService;
		} else if (ActivityType.ANIMAL_CHESS.getType() == type) {
			return animalChessService;
		} else if (ActivityType.GOMOKU.getType() == type) {
			return gomokuService;
		} else if (ActivityType.SAKURAN.getType() == type) {
			return sakuranService;
		} else if (ActivityType.CHATBOX.getType() == type) {
			return chatBoxService;
		} else if (ActivityType.WINMINE.getType() == type) {
			return winmineService;
		} else if (ActivityType.NEURO_CAT.getType() == type) {
			return neuroCatService;
		} else if (ActivityType.COW_LOW.getType() == type || ActivityType.COW_HIGH.getType() == type) {
			return cowService;
		} else if (ActivityType.TEXAS_LOW.getType() == type || ActivityType.TEXAS_MID.getType() == type
				|| ActivityType.TEXAS_HIGH.getType() == type) {
			return texasService;
		}
		return roomDefaultService;
	}

	public RoomService getDefService() {
		return roomDefaultService;
	}
}
