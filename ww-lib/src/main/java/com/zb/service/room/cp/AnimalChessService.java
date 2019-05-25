package com.zb.service.room.cp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.activity.AnimalChess;
import com.zb.models.room.activity.AnimalChessActor;
import com.zb.service.GameCPScoreService;
import com.zb.service.room.vo.AnimalChessRoomInfo;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class AnimalChessService extends RoomCPService {
	static final Logger log = LoggerFactory.getLogger(AnimalChessService.class);

	@Autowired
	GameCPScoreService gameCPScoreService;

	@Override
	public ReMsg handle(Room room, long fr) {
		if (room.getType() != ActivityType.ANIMAL_CHESS.getType()) {
			log.error(room.get_id() + " " + room.getType());
			return new ReMsg(ReCode.FAIL);
		}
		try {
			AnimalChess uc = super.getActivity(room.get_id(), AnimalChess.class);
			// if (AnimalChess.STATUS_READY == uc.getStatus()) {
			if (AnimalChess.STATUS_WAIT == uc.getStatus()) {
				start(room, uc);
			} else if (AnimalChess.STATUS_PLAY == uc.getStatus()) {
				nextPlay(room, uc);// 新的回合
			} else if (AnimalChess.STATUS_END == uc.getStatus()) {
				this.closeRoom(room.get_id(), uc);
			}
		} catch (Exception e) {
			log.error("调用任务roomId=" + room.get_id() + "\n", e);
			this.addJob(room.get_id(), TIME_START);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public static final int TIME_WAIT = 3;
	public static final int TIME_READY = 1;
	public static final int TIME_TEMPLATE = 3;// 开始倒计时
	public static final int TIME_START = 5;
	public static final int TIME_PLAY = 60;// 下棋时间
	public static final int TIME_SHOW = 1;// 展示时间
	public static final int TIME_END = 5;// 结束时间

	public static final int START_COUNT = 2;// 开始人数
	public static final int STEP_GIVEUP = 10;// 认输步数
	// 棋子分类
	public static final int CHESS_ELEPHANT = 80;// 大象
	public static final int CHESS_LION = 70;// 狮子
	public static final int CHESS_TIGER = 60;// 老虎
	public static final int CHESS_LEOPARD = 50;// 豹子
	public static final int CHESS_WOLF = 40;// 狼
	public static final int CHESS_DOG = 30;// 狗
	public static final int CHESS_CAT = 20;// 猫
	public static final int CHESS_MOUSE = 10;// 老鼠
	// 棋子颜色
	public static final int CHESS_COLOR_RED = 1;// 红色
	public static final int CHESS_COLOR_BLUE = 2;// 蓝色
	// 节点棋子状态
	public static final int CHESS_STATUS_CLOSE = 1;// 未翻开
	public static final int CHESS_STATUS_OPEN = 2;// 已翻开
	public static final int CHESS_STATUS_NO = 0;// 不存在

	public static final int POINT_WIN = 30;
	public static final int POINT_DEFAULT = 10;

	public static final int COIN_DEFAULT = 10;
	// 结束类型
	public static final int END_NORMAL = 0; // 正常结束
	public static final int END_OUTROOM = 1; // 退出房间结束
	public static final int END_GIVEUP = 2; // 认输结束
	public static final int END_FAIL = 4; // fail结束

	public ReMsg inRoom(final long uid, Room r, int model, boolean robit) {
		AnimalChessRoomInfo ri = new AnimalChessRoomInfo(ActivityType.ANIMAL_CHESS.getType());
		AnimalChess uc = super.getActivity(r.get_id(), AnimalChess.class);
		if (uc.getStatus() == AnimalChess.STATUS_READY || uc.getStatus() == AnimalChess.STATUS_WAIT) {// 第一个人进来
			uc.setModel(model == Const.ACTIVITY_INVITE ? model : Const.ACTIVITY_MATCH);
			uc.setStatus(AnimalChess.STATUS_WAIT);
			if (uc.getActors().size() == 0) {
				uc.setColorRed(uid);
				addJob(r.get_id(), TIME_WAIT);
			} else if (uc.getActors().size() == 1) {
				addJob(r.get_id(), TIME_READY);
				uc.setColorBlue(uid);
			} else {
				return new ReMsg(ReCode.FAIL, ri);
			}
			uc.putActors(uid);
			super.saveActivity(r.get_id(), uc);
		} else {
			if (uid != uc.getColorRed() && uid != uc.getColorBlue()) {// 非进入状态下，除了当前用户，不能进入房间
				return new ReMsg(ReCode.FAIL, ri);
			}
		}

		if (r == null || r.getType() != ActivityType.ANIMAL_CHESS.getType()) {
			log.error(r.get_id() + " " + r.getType());
			ri.setCode(1);
			return new ReMsg(ReCode.FAIL, ri);
		}
		AnimalChessActor ua = super.getRoomActor(r.get_id(), uid, AnimalChessActor.class);
		if (ua != null) {
			ua.setStatus(Actor.STATUS_ONLINE);
			// super.saveActivity(r.get_id(), uc);
		} else {
			DBObject user = userService.findById(uid);
			if (user == null) {
				ri.setCode(1);
				return new ReMsg(ReCode.FAIL, ri);
			}
			ua = new AnimalChessActor();
			ua.setVip(DboUtil.getInt(user, "vip") == null ? 0 : DboUtil.getInt(user, "vip"));
			ua.setRobit(robit);
			ua.setUid(DboUtil.getLong(user, "_id"));
			ua.setAvatar(DboUtil.getString(user, "avatar"));
			ua.setPoint(DboUtil.getInt(user, "point"));
			ua.setSex(DboUtil.getInt(user, "sex"));
			ua.setNickname(DboUtil.getString(user, "nickname"));
			ua.setProvince(DboUtil.getString(user, "province") == null ? "火星" : DboUtil.getString(user, "province"));
			if (ua.getProvince().contains("市")) {
				ua.setCity("");
			} else {
				ua.setCity(DboUtil.getString(user, "city") == null ? "" : DboUtil.getString(user, "city"));
			}
			ua.setGame("斗兽棋争霸");
			// uc.putActors(ua.getUid());
			if (uc.getColorRed() == uid) {
				ua.setColor(CHESS_COLOR_RED);
			} else {
				ua.setColor(CHESS_COLOR_BLUE);
			}
			super.saveActivity(r.get_id(), uc);
			super.saveRoomActor(r.get_id(), ua);
		}
		Set<AnimalChessActor> actors = super.getRoomActors(r.get_id(), AnimalChessActor.class);
		changeRoomCount(r, actors, T_START);
		// 获取棋盘信息
		ri.setColorBlue(uc.getColorBlue());
		ri.setColorRed(uc.getColorRed());
		ri.setPlayer(uc.getPlayer());
		ri.setChessboard(uc.getChessboard());
		ri.setStatus(Room.ACTIVITY);
		ri.setPub(r.isPub());
		ri.setHasOwner(r.getUid() == 0 ? false : true);
		ri.setActors(actors);
		if (r.getStatus() == Room.ACTIVITY && uc.getActors().size() == START_COUNT
				&& uc.getStatus() == AnimalChess.STATUS_READY) {
			addJob(r.get_id(), TIME_TEMPLATE);
		}
		MapBody mb = new MapBody(SocketHandler.IN_ROOM, SocketHandler.FN_USER_ID, ua.getUid())
				.append("nickname", ua.getNickname()).append("sex", ua.getSex()).append("avatar", ua.getAvatar())
				.append("actors", actors).append("province", ua.getProvince()).append("city", ua.getCity())
				.append("role", ua.getRole()).append(SocketHandler.FN_ROOM_ID, r.get_id());
		long dt = System.currentTimeMillis();
		for (AnimalChessActor a : actors) {
			if (uid != a.getUid() && a.getStatus() == Actor.STATUS_ONLINE) {
				super.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.ROOM, mb, dt);
				break;
			}
		}
		return new ReMsg(ReCode.OK, ri);
	}

	/** 开始游戏 */
	private boolean start(Room r, AnimalChess uc) {
		Set<AnimalChessActor> actors = super.getRoomActors(r.get_id(), AnimalChessActor.class);
		if (r.getRobitCount() == r.getCount()) {
			super.closeRoom(r.get_id());
		} else if (actors.size() == START_COUNT) {
			uc.setChessboard(initChessboard());
			uc.setStatus(AnimalChess.STATUS_PLAY);
			if (RandomUtil.nextInt(2) < 1) {
				uc.setPlayer(uc.getColorRed());
			} else {
				uc.setPlayer(uc.getColorBlue());
			}
			play(r.get_id(), uc, actors);
		} else {
			checkRoom(r, actors);
		}
		return true;
	}


	private void play(long roomId, AnimalChess uc, Set<AnimalChessActor> actors) {
		uc.setPlay(false);
		uc.setStepCnt(uc.getStepCnt() + 1);
		uc.setStatus(AnimalChess.STATUS_PLAY);
		super.saveActivity(roomId, uc);
		addJob(roomId, TIME_PLAY);
		MapBody mb = new MapBody(SocketHandler.CHESS_PLAY, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_PLAY).append("board", uc.getChessboard())
				.append("player", uc.getPlayer()).append("actors", actors).append("stepCnt", uc.getStepCnt())
				.append("giveupCnt", STEP_GIVEUP).append("giveup", canGiveup(uc.getStepCnt()));
		pubRoomMsg(roomId, MsgType.ANIMAL_CHESS, mb);
	}

	private void nextPlay(Room r, AnimalChess uc) {
		Set<AnimalChessActor> actors = super.getRoomActors(r.get_id(), AnimalChessActor.class);
		if (uc.isPlay()) {
			Integer[] point = calculatePoint(uc, false);
			if (point != null) {
				end(r.get_id(), uc, null, point, END_NORMAL);
				return;
			}
			if (uc.getPlayer() == uc.getColorRed()) {
				uc.setPlayer(uc.getColorBlue());
			} else {
				uc.setPlayer(uc.getColorRed());
			}
			play(r.get_id(), uc, actors);
		} else {
			end(r.get_id(), uc, uc.getPlayer(), null, END_OUTROOM);
		}
	}

	/** 点击棋子 */
	public ReMsg clickNode(long uid, long roomId, int[] XY) {
		AnimalChess uc = super.getActivity(roomId, AnimalChess.class);
		if (uc.getStatus() != AnimalChess.STATUS_PLAY) {
			return new ReMsg(ReCode.FAIL);
		}
		int[] node = uc.getChessboard()[XY[0]][XY[1]];
		if (uc.getPlayer() != uid || uc.isPlay()) {
			return new ReMsg(ReCode.NOT_YOUR_PLAYTIME);
		}
		if (0 == node[0]) {
			return new ReMsg(ReCode.NODE_NO_CHESS);
		} else {
			if (CHESS_STATUS_CLOSE == node[1]) {
				uc.getChessboard()[XY[0]][XY[1]][1] = CHESS_STATUS_OPEN;
				uc.setPlay(true);
				super.saveActivity(roomId, uc);
				addJob(roomId, TIME_SHOW);
				MapBody mb = new MapBody(SocketHandler.NODE_CLICK, SocketHandler.FN_ROOM_ID, roomId)
						.append(SocketHandler.FN_LIMIT, TIME_SHOW).append("XY", XY[0] + "," + XY[1])
						.append("node", node[0]);
				pubRoomMsg(roomId, MsgType.ANIMAL_CHESS, mb);
				return new ReMsg(ReCode.OK);
			} else if (CHESS_STATUS_OPEN == node[1]) {
				int color = node[0] % 10;
				if ((color == CHESS_COLOR_RED && uc.getColorRed() == uid)
						|| (color == CHESS_COLOR_BLUE && uc.getColorBlue() == uid)) {
					return new ReMsg(ReCode.NODE_CHESS_WALK);
				}
				return new ReMsg(ReCode.NOT_YOUR_CHESS);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	/** 移动棋子 */
	public ReMsg moveNode(long uid, long roomId, int[] oldXY, int[] XY) {
		if (!(Math.abs(oldXY[0] - XY[0]) == 1 && Math.abs(oldXY[1] - XY[1]) == 0)
				&& !(Math.abs(oldXY[0] - XY[0]) == 0 && Math.abs(oldXY[1] - XY[1]) == 1)) {
			return new ReMsg(ReCode.FAIL);
		}
		AnimalChess uc = super.getActivity(roomId, AnimalChess.class);
		if (null == uc.getChessboard()) {
			return new ReMsg(ReCode.FAIL);
		}
		if (uc.getPlayer() != uid || uc.isPlay()) {
			return new ReMsg(ReCode.NOT_YOUR_PLAYTIME);
		}
		// 两个坐标点
		int[] oldNode = uc.getChessboard()[oldXY[0]][oldXY[1]];
		int[] newNode = uc.getChessboard()[XY[0]][XY[1]];
		if (0 == oldNode[0]) {
			return new ReMsg(ReCode.NODE_NO_CHESS);
		}
		if (oldNode[0] % 10 == CHESS_COLOR_RED) {// 棋子红色
			if (uc.getColorRed() != uid) {
				return new ReMsg(ReCode.NOT_YOUR_CHESS);
			}
		} else {
			if (uc.getColorBlue() != uid) {
				return new ReMsg(ReCode.NOT_YOUR_CHESS);
			}
		}
		if (CHESS_STATUS_OPEN == oldNode[1]) {
			if (0 == newNode[0] || CHESS_STATUS_OPEN == newNode[1]) {
				if ((oldNode[0] % 10) == (newNode[0] % 10)) {
					return new ReMsg(ReCode.COLOR_SAME);
				}
				int oldChess = oldNode[0] / 10;
				int newChess = newNode[0] / 10;
				if ((CHESS_ELEPHANT / 10 == oldChess && CHESS_MOUSE / 10 == newChess)) {
				} else if ((CHESS_MOUSE / 10 == oldChess && CHESS_ELEPHANT / 10 == newChess) || (oldChess > newChess)) {
					uc.getChessboard()[XY[0]][XY[1]] = oldNode;
				} else if (oldChess == newChess) {
					uc.getChessboard()[XY[0]][XY[1]] = new int[] { CHESS_STATUS_NO, CHESS_STATUS_NO };
				}
				uc.getChessboard()[oldXY[0]][oldXY[1]] = new int[] { CHESS_STATUS_NO, CHESS_STATUS_NO };
				uc.setPlay(true);
				super.saveActivity(roomId, uc);
				addJob(roomId, TIME_SHOW);
				MapBody mb = new MapBody(SocketHandler.NODE_MOVE, SocketHandler.FN_ROOM_ID, roomId)
						.append(SocketHandler.FN_LIMIT, TIME_SHOW).append("oldXY", oldXY[0] + "," + oldXY[1])
						.append("XY", XY[0] + "," + XY[1]).append("oldNode", 0)
						.append("newNode", uc.getChessboard()[XY[0]][XY[1]][0]);
				pubRoomMsg(roomId, MsgType.ANIMAL_CHESS, mb);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	/** 认输 */
	public ReMsg giveupGame(long uid, long roomId) {
		AnimalChess uc = super.getActivity(roomId, AnimalChess.class);
		if (canGiveup(uc.getStepCnt())) {
			end(roomId, uc, uid, calculatePoint(uc, true), END_GIVEUP);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.NOT_INNING_GIVEUP);
	}

	/** 结束 */
	private void end(final long roomId, AnimalChess uc, Long uid, Integer[] points, int endType) {
		MapBody mb = null;
		if (endType == END_FAIL) {
			mb = new MapBody(SocketHandler.CHESS_END, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_LIMIT, TIME_END).append("endType", endType);
		} else {
			if (uc == null)
				uc = super.getActivity(roomId, AnimalChess.class);
			if (uc.getStatus() != AnimalChess.STATUS_PLAY) {
				return;
			}
			uc.setStatus(AnimalChess.STATUS_END);
			super.saveActivity(roomId, uc);

			int winReward = COIN_DEFAULT;
			int lostReward = COIN_DEFAULT;
			if (endType != END_NORMAL) {
				uc.setLoser(uid);
				if (uc.getColorRed() == uid) {
					uc.setWinner(uc.getColorBlue());
				} else {
					uc.setWinner(uc.getColorRed());
				}
			} else {
				if (points[0] > points[1]) {
					uc.setWinner(uc.getColorRed());
					uc.setLoser(uc.getColorBlue());
				} else if (points[0] < points[1]) {
					uc.setWinner(uc.getColorBlue());
					uc.setLoser(uc.getColorRed());
				}
			}
			if (gameCPScoreService.isFriend(uc.getColorBlue(), uc.getColorRed())) {
				uc.setModel(Const.ACTIVITY_INVITE);
			} else {
				uc.setModel(Const.ACTIVITY_MATCH);
			}
			if (uc.getModel() == Const.ACTIVITY_INVITE) {// 好友
				if (endType == END_OUTROOM) {// 退出房间
					winReward = 0;
				} else if (uc.getWinner() == uc.getLoser()) {// 平局
					winReward = 0;
					lostReward = 0;
				}
			} else {// 匹配
				if (END_GIVEUP == endType) {// 认输
					winReward = Math.abs(points[0] - points[1]) > 10 ? Math.abs(points[0] - points[1]) : 10;
					lostReward = winReward;
				} else if (END_OUTROOM == endType) {
					winReward = 10;
					lostReward = 30;
				} else if (uc.getWinner() != uc.getLoser()) {
					winReward = Math.abs(points[0] - points[1]);
					lostReward = winReward;
				} else {
					winReward = 0;
					lostReward = 0;
				}
				if (winReward > 0 && uc.getWinner() > 0) {
					gameCPScoreService.changeScore(uc.getWinner(), ActivityType.ANIMAL_CHESS.getType(), winReward);
				}
				if (lostReward > 0 && uc.getLoser() > 0) {
					gameCPScoreService.changeScore(uc.getLoser(), ActivityType.ANIMAL_CHESS.getType(), 0 - lostReward);
				}
			}
			gameCPScoreService.saveGameCPHistory(DboUtil.beanToDBO(uc), ActivityType.ANIMAL_CHESS.getType());
			super.saveActivity(roomId, uc);
			super.addJob(roomId, TIME_END);// 5秒展示 结束

			mb = new MapBody(SocketHandler.CHESS_END, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_LIMIT, TIME_END).append("endType", endType).append("model", uc.getModel())
					.append("winner", uc.getWinner()).append("loser", uc.getLoser());
			if (uc.getWinner() != uc.getLoser()) {
				userService.changePoint(uc.getWinner(), Point.ANIMAL_CHESS.getType(), POINT_WIN, 0);
				userService.changePoint(uc.getLoser(), Point.ANIMAL_CHESS.getType(), POINT_DEFAULT, 0);
				mb.append("winPoint", POINT_WIN).append("lostPoint", POINT_DEFAULT);
			} else {// 平局
				for (Long aid : uc.getActors()) {
					userService.changePoint(aid, Point.ANIMAL_CHESS.getType(), POINT_DEFAULT, 0);
				}
				mb.append("winPoint", POINT_DEFAULT).append("lostPoint", POINT_DEFAULT);
			}
			mb.append("winCoin", winReward).append("lostCoin", lostReward);
			if (winReward > 0) {
				coinService.addCoin(uc.getWinner(), CoinLog.ANIMAL_CHESS, uc.get_id(), winReward, 0, "游戏胜利加币");
			}
			if (lostReward > 0) {
				coinService.forceReduce(uc.getLoser(), CoinLog.ANIMAL_CHESS, uc.get_id(), lostReward, 0, "游戏失败扣币");
			}
			if (uc.getModel() == Const.ACTIVITY_MATCH) {// 匹配
				mb.append("winScore", winReward).append("lostScore", lostReward);
			}
		}
		super.pubRoomMsg(roomId, MsgType.ANIMAL_CHESS, mb);
	}

	@Override
	public ReMsg outRoom(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.OUT_ROOM);
	}

	/** 退出房间 有人退出即游戏结束 */
	public ReMsg outRoom(long uid, Room r, int type) {
		if (r.getType() != ActivityType.ANIMAL_CHESS.getType()) {
			log.error(r.get_id() + " " + r.getType());
			return new ReMsg(ReCode.FAIL);
		}
		AnimalChessActor actor = super.getRoomActor(r.get_id(), uid, AnimalChessActor.class);
		if (null == actor) {
			return new ReMsg(ReCode.ACTOR_ERROR);
		} else {
			AnimalChess uc = super.getActivity(r.get_id(), AnimalChess.class);
			if (uc.getStatus() == AnimalChess.STATUS_PLAY) {
				end(r.get_id(), uc, uid, null, END_OUTROOM);
			} else if (uc.getStatus() == AnimalChess.STATUS_READY || uc.getStatus() == AnimalChess.STATUS_WAIT) {
				end(r.get_id(), uc, uid, null, END_FAIL);
			}
			this.closeRoom(r.get_id());
		}
		return new ReMsg(ReCode.OK);
	}

	public void closeRoom(final long roomId, AnimalChess uc) {
		if (uc.getStatus() == AnimalChess.STATUS_END && uc.getActors().size() == 2) {
			uc.setUpdateTime(System.currentTimeMillis());
			super.getMongoTemplate().save(uc);
		}
		super.closeRoom(roomId);
	}

	@Override
	public ReMsg disconnectRoom(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.DISCONNECT);
	}

	@Override
	public ReMsg kick(long uid, Room r) {
		return null;
	}

	@Override
	public ReMsg getActorStatus(long uid, Room r) {
		return null;
	}

	@Override
	public ReMsg canInRoom(int type, long actId, long roomId) {
		return new ReMsg(ReCode.OK, 2);
	}

	// 初始化棋盘
	private int[][][] initChessboard() {
		List<int[]> place = getPlace();
		int[][][] places = new int[4][4][1];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				places[i][j] = place.get(i * 4 + j);
			}
		}
		return places;
	}

	static ArrayList<int[]> place = new ArrayList<int[]>();
	static {
		place.addAll(Arrays.asList(
				new int[][] { { 11, 1 }, { 21, 1 }, { 31, 1 }, { 41, 1 }, { 51, 1 }, { 61, 1 }, { 71, 1 }, { 81, 1 },
						{ 12, 1 }, { 22, 1 }, { 32, 1 }, { 42, 1 }, { 52, 1 }, { 62, 1 }, { 72, 1 }, { 82, 1 } }));
	}

	private List<int[]> getPlace() {
		ArrayList<int[]> cur = place;
		Collections.shuffle(cur);
		return cur;
	}

	/** 计算积分 */
	private Integer[] calculatePoint(AnimalChess uc, boolean mustPoint) {
		int[][][] chessboard = uc.getChessboard();
		List<Integer> red = new ArrayList<Integer>();
		List<Integer> blue = new ArrayList<Integer>();
		for (int i = 0; i < chessboard.length; i++) {
			for (int j = 0; j < chessboard[i].length; j++) {
				if (!mustPoint && chessboard[i][j][1] == AnimalChessService.CHESS_STATUS_CLOSE) {
					return null;
				}
				if (chessboard[i][j][0] % 10 == CHESS_COLOR_RED) {
					red.add(chessboard[i][j][0]);
				} else if (chessboard[i][j][0] % 10 == CHESS_COLOR_BLUE) {
					blue.add(chessboard[i][j][0]);
				}
			}
		}
		if (mustPoint) {// 必须计算积分
			return new Integer[] { countPoint(red), countPoint(blue) };
		}
		if (red.size() == 0 || blue.size() == 0) {
			return new Integer[] { countPoint(red), countPoint(blue) };
		}
		if (red.size() + blue.size() > 3) {
			return null;
		}
		// 强制结束
		if (uc.getEndCnt() < 10) {
			uc.setEndCnt(uc.getEndCnt() + 1);
		} else {
			if (red.size() == blue.size()) {
				int redPoint = getPoint(red.get(0));
				int bluePoint = getPoint(blue.get(0));
				return new Integer[] { redPoint, bluePoint };
			} else {
				return new Integer[] { countPoint(red), countPoint(blue) };
			}
		}
		return null;
	}

	private int countPoint(List<Integer> chess) {
		if (null == chess || chess.size() == 0) {
			return 0;
		}
		int point = 0;
		for (Integer animal : chess) {
			point += getPoint(animal);
		}
		return point;
	}

	// 棋子换算积分
	private int getPoint(int chess) {
		chess = chess / 10;
		if (chess == 8) {
			return 14;
		} else if (chess == 7) {
			return 12;
		} else if (chess == 6) {
			return 10;
		} else if (chess == 5) {
			return 8;
		} else if (chess == 4) {
			return 6;
		} else if (chess == 3) {
			return 4;
		} else if (chess == 2) {
			return 2;
		} else if (chess == 1) {
			return 16;
		}
		return 0;
	}

	private boolean canGiveup(int cnt) {
		if (cnt >= STEP_GIVEUP) {
			return true;
		}
		return false;
	}
}
