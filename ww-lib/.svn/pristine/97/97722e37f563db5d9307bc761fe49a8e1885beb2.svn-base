package com.zb.service.room.cp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
import com.zb.models.room.activity.Winmine;
import com.zb.models.room.activity.WinmineActor;
import com.zb.service.GameCPScoreService;
import com.zb.service.room.vo.WinmineRoomInfo;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class WinmineService extends RoomCPService {
	static final Logger log = LoggerFactory.getLogger(WinmineService.class);

	@Autowired
	GameCPScoreService gameCPScoreService;

	@Override
	public ReMsg handle(Room room, long fr) {
		if (room.getType() != ActivityType.WINMINE.getType()) {
			log.error(room.get_id() + " " + room.getType());
			return new ReMsg(ReCode.FAIL);
		}
		try {
			Winmine uc = super.getActivity(room.get_id(), Winmine.class);
			if (Winmine.STATUS_WAIT == uc.getStatus()) {
				start(room, uc);
			} else if (Winmine.STATUS_START == uc.getStatus()) {
				nextPlay(room, uc);// 新的回合
			} else if (Winmine.STATUS_END == uc.getStatus()) {
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
	public static final int TIME_PLAY = 20;// 下棋时间
	public static final int TIME_SHOW = 2;// 展示时间
	public static final int TIME_END = 5;// 结束时间

	public static final int GRID_X = 8; // 棋盘横向长度
	public static final int GRID_Y = 10;// 棋盘纵向长度

	public static final int START_COUNT = 2;// 开始人数
	public static final int MINE_COUNT_MIN = 20;
	public static final int MINE_COUNT_MAX = 30;

	// 牌状态
	public static final int CHESS_STATUS_CLOSE = 0;// 未翻开
	public static final int CHESS_STATUS_OPEN = 1;// 已翻开

	// 牌数字含义 >0 附近八处雷数
	public static final int CHESS_BLANK = 0;// 空白
	public static final int CHESS_MINE = -1;// 雷
	public static final int CHESS_MARKMINE = -2;// 成功标记的雷

	// 分数
	public static final int SCORE_PICK_OK = 3;// 开采成功
	public static final int SCORE_PICK_FAIL = -6;// 开采失败
	public static final int SCORE_MINE_OK = 6;// 标记成功
	public static final int SCORE_MINE_FAIL = -3;// 标记失败

	// 开采1 标记雷2
	public static final int CLICK_PICK = 1;
	public static final int CLICK_MINE = 2;

	public static final int POINT_WIN = 30;
	public static final int POINT_DEFAULT = 10;

	public static final int COIN_DEFAULT = 10;
	// 结束类型
	public static final int END_NORMAL = 0; // 正常结束
	public static final int END_OUTROOM = 1; // 退出房间结束
	public static final int END_FAIL = 4; // fail结束

	public ReMsg inRoom(final long uid, Room r, int model, boolean robit) {
		WinmineRoomInfo ri = new WinmineRoomInfo(ActivityType.WINMINE.getType());
		Winmine uc = super.getActivity(r.get_id(), Winmine.class);
		if (uc.getStatus() == Winmine.STATUS_READY || uc.getStatus() == Winmine.STATUS_WAIT) {// 第一个人进来
			uc.setModel(model == Const.ACTIVITY_INVITE ? model : Const.ACTIVITY_MATCH);
			uc.setStatus(Winmine.STATUS_WAIT);
			if (uc.getActors().size() == 0) {
				addJob(r.get_id(), TIME_WAIT);
			} else if (uc.getActors().size() == 1) {
				addJob(r.get_id(), TIME_READY);
			} else {
				return new ReMsg(ReCode.FAIL, ri);
			}
			uc.putActor(uid);
			uc.putUserScore(uid, 0);
			super.saveActivity(r.get_id(), uc);
		}

		if (r == null || r.getType() != ActivityType.WINMINE.getType()) {
			log.error(r.get_id() + " " + r.getType());
			ri.setCode(1);
			return new ReMsg(ReCode.FAIL, ri);
		}
		WinmineActor ua = super.getRoomActor(r.get_id(), uid, WinmineActor.class);
		if (ua != null) {
			ua.setStatus(Actor.STATUS_ONLINE);
			// super.saveActivity(r.get_id(), uc);
		} else {
			DBObject user = userService.findById(uid);
			if (user == null) {
				ri.setCode(1);
				return new ReMsg(ReCode.FAIL, ri);
			}
			ua = new WinmineActor();
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
			ua.setGame("扫雷大作战");
			super.saveActivity(r.get_id(), uc);
			super.saveRoomActor(r.get_id(), ua);
		}
		Set<WinmineActor> actors = super.getRoomActors(r.get_id(), WinmineActor.class);
		changeRoomCount(r, actors, T_START);
		ri.setMap(uc.getMap());
		ri.setScores(uc.getScores());
		ri.setStatus(Room.ACTIVITY);
		ri.setPub(r.isPub());
		ri.setHasOwner(r.getUid() == 0 ? false : true);
		ri.setActors(actors);
		if (r.getStatus() == Room.ACTIVITY && uc.getActors().size() == START_COUNT
				&& uc.getStatus() == Winmine.STATUS_READY) {
			addJob(r.get_id(), TIME_TEMPLATE);
		}
		MapBody mb = new MapBody(SocketHandler.IN_ROOM, SocketHandler.FN_USER_ID, ua.getUid())
				.append("nickname", ua.getNickname()).append("sex", ua.getSex()).append("avatar", ua.getAvatar())
				.append("actors", actors).append("province", ua.getProvince()).append("city", ua.getCity())
				.append("role", ua.getRole()).append(SocketHandler.FN_ROOM_ID, r.get_id());
		long dt = System.currentTimeMillis();
		for (WinmineActor a : actors) {
			if (uid != a.getUid() && a.getStatus() == Actor.STATUS_ONLINE) {
				super.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.ROOM, mb, dt);
				break;
			}
		}
		return new ReMsg(ReCode.OK, ri);
	}

	/** 开始游戏 */
	private boolean start(Room r, Winmine uc) {
		Set<WinmineActor> actors = super.getRoomActors(r.get_id(), WinmineActor.class);
		if (r.getRobitCount() == r.getCount()) {
			super.closeRoom(r.get_id());
		} else if (actors.size() == START_COUNT) {
			uc.setMineCnt(randomMineCnt());
			uc.setMap(initMap(uc.getMineCnt()));
			uc.setStatus(Winmine.STATUS_START);
			play(r.get_id(), uc, actors);
		} else {
			checkRoom(r, actors);
		}
		return true;
	}

	/** 下一回合 */
	private void nextPlay(Room r, Winmine uc) {
		Set<WinmineActor> actors = super.getRoomActors(r.get_id(), WinmineActor.class);
		if (uc.getMineCnt() == 0 || uc.getTemp().size() == 0) {
			end(r.get_id(), uc, null, END_NORMAL);
		} else {
			if (uc.getTemp().size() == 2) {
				play(r.get_id(), uc, actors);
			} else {
				for (Long uid : uc.getActors()) {
					if (uc.getUserTemp(uid) == null) {
						end(r.get_id(), uc, uid, END_OUTROOM);
						return;
					}
				}
			}
		}
	}

	private void play(long roomId, Winmine uc, Set<WinmineActor> actors) {
		uc.setInit(uc.getInit() + 1);
		if (uc.getTemp().size() != 0) {
			for (int[] temp : uc.getTemp().values()) {
				if (uc.getMap()[temp[0]][temp[1]][0] == CHESS_BLANK) {
					scopeSweep(uc.getMap(), temp[0], temp[1]);
				}
			}
			uc.clearTemp();
		}
		uc.setStatus(Winmine.STATUS_START);
		super.saveActivity(roomId, uc);
		addJob(roomId, TIME_PLAY);
		MapBody mb = new MapBody(SocketHandler.WINMINE_PLAY, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_PLAY).append("map", uc.getMap()).append("actors", actors)
				.append("mineCnt", uc.getMineCnt()).append("scores", uc.getScores());
		pubRoomMsg(roomId, MsgType.WINMINE, mb);
	}

	/** 点击空白 一炸一大片 */
	private void scopeSweep(int[][][] map, int x, int y) {
		if (map[x][y][0] == CHESS_BLANK) {
			List<int[]> lbs = new ArrayList<int[]>();
			List<int[]> temp = new ArrayList<int[]>();
			lbs.addAll(getArount8Lbs(x, y));
			while (lbs.size() > 0) {
				Iterator<int[]> iterator = lbs.iterator();
				while (iterator.hasNext()) {
					int[] next = iterator.next();
					if (map[next[0]][next[1]][1] == CHESS_STATUS_CLOSE) {
						if (map[next[0]][next[1]][0] > CHESS_BLANK) {
							map[next[0]][next[1]][1] = CHESS_STATUS_OPEN;
						} else if (map[next[0]][next[1]][0] == CHESS_BLANK) {
							map[next[0]][next[1]][1] = CHESS_STATUS_OPEN;
							temp.addAll(getArount8Lbs(next[0], next[1]));
						}
					}
					iterator.remove();
				}
				lbs.addAll(temp);
				temp.clear();
			}
		}
	}

	/** 计算本回合结果 */
	public void calculateRes(long roomId, Winmine uc) {
		Set<Long> keySet = uc.getTemp().keySet();
		for (Long uid : keySet) {
			int[] temp = uc.getUserTemp(uid);
			boolean chessClose = uc.getMap()[temp[0]][temp[1]][1] == CHESS_STATUS_CLOSE;// 棋子初始状态是不是未翻开
			uc.getMap()[temp[0]][temp[1]][1] = CHESS_STATUS_OPEN;
			int chess = uc.getMap()[temp[0]][temp[1]][0];
			int score = 0;
			if (temp[2] == CLICK_PICK) {
				if (chess == CHESS_MINE || chess == CHESS_MARKMINE) {
					score = SCORE_PICK_FAIL;
					if (chessClose) {
						uc.setMineCnt(uc.getMineCnt() - 1);
					}
				} else {
					score = SCORE_PICK_OK;
				}
			} else if (temp[2] == CLICK_MINE) {
				if (chess == CHESS_MINE || chess == CHESS_MARKMINE) {
					uc.getMap()[temp[0]][temp[1]][0] = CHESS_MARKMINE;
					chess = CHESS_MARKMINE;
					score = SCORE_MINE_OK;
					if (chessClose) {
						uc.setMineCnt(uc.getMineCnt() - 1);
					}
				} else {
					score = SCORE_MINE_FAIL;
				}
			}
			uc.putUserTemp(uid, new int[] { temp[0], temp[1], temp[2], chess, score });
			uc.putUserScore(uid, uc.getUserScore(uid) + score);
		}
		super.saveActivity(roomId, uc);
		MapBody mb = new MapBody(SocketHandler.MINENODE_CLICK, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_SHOW).append("scores", uc.getScores()).append("temp", uc.getTemp())
				.append("mineCnt", uc.getMineCnt());

		pubRoomMsg(roomId, MsgType.WINMINE, mb);
		if (uc.getMineCnt() == 0) {
			addJob(roomId, TIME_SHOW + 1);
		} else {
			addJob(roomId, TIME_SHOW);
		}
	}

	/** 点击棋子 type 类型 开采1 标记雷2 */
	public ReMsg clickNode(long uid, long roomId, int[] XY, int type) {
		Winmine uc = super.getActivity(roomId, Winmine.class);
		if (uc.getStatus() != Winmine.STATUS_START) {
			return new ReMsg(ReCode.FAIL);
		}
		int[] node = uc.getMap()[XY[0]][XY[1]];
		if (uc.getTemp().get(uid) != null) {
			return new ReMsg(ReCode.WAIT_NEXT_ROUND);
		}
		if (CHESS_STATUS_OPEN == node[1]) {
			return new ReMsg(ReCode.ALRAEDY_SWEEP);
		} else {
			uc.putUserTemp(uid, new int[] { XY[0], XY[1], type });
			super.saveActivity(roomId, uc);
			if (uc.getTemp().size() == 2) {
				calculateRes(roomId, uc);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	/** 结束 */
	private void end(final long roomId, Winmine uc, Long uid, int endType) {
		MapBody mb = null;
		if (endType == END_FAIL) {
			mb = new MapBody(SocketHandler.WINMINE_END, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_LIMIT, TIME_END).append("endType", endType);
		} else {
			if (uc == null)
				uc = super.getActivity(roomId, Winmine.class);
			if (uc.getStatus() != Winmine.STATUS_START) {
				return;
			}
			uc.setStatus(Winmine.STATUS_END);
			super.saveActivity(roomId, uc);

			int winReward = COIN_DEFAULT;
			int lostReward = COIN_DEFAULT;
			if (endType != END_NORMAL) {
				uc.setLoser(uid);
				Set<Long> actors = uc.getActors();
				for (long actorId : actors) {
					if (actorId != uid) {
						uc.setWinner(actorId);
						break;
					}
				}
			} else {// 比较积分计算输赢
				int score = 0;
				Set<Long> actors = uc.getActors();
				for (long actorId : actors) {
					if (uc.getWinner() == 0 && uc.getLoser() == 0) {
						score = uc.getUserScore(actorId);
						uc.setWinner(actorId);
						uc.setLoser(actorId);
					} else {
						if (uc.getUserScore(actorId) > score) {
							uc.setWinner(actorId);
						} else if (uc.getUserScore(actorId) < score) {
							uc.setLoser(actorId);
						} else {
							uc.setLoser(0);
							uc.setWinner(0);
						}
					}
				}
			}
			if (gameCPScoreService.isFriend(uc.getWinner(), uc.getLoser())) {
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
				if (END_OUTROOM == endType) {
					winReward = 10;
					lostReward = 30;
				} else if (uc.getWinner() != uc.getLoser()) {
					winReward = Math.abs(uc.getUserScore(uc.getWinner()) - uc.getUserScore(uc.getLoser()));
					lostReward = winReward;
				} else {
					winReward = 0;
					lostReward = 0;
				}
				if (winReward > 0 && uc.getWinner() > 0) {
					gameCPScoreService.changeScore(uc.getWinner(), ActivityType.WINMINE.getType(), winReward);
				}
				if (lostReward > 0 && uc.getLoser() > 0) {
					gameCPScoreService.changeScore(uc.getLoser(), ActivityType.WINMINE.getType(), 0 - lostReward);
				}
			}
			gameCPScoreService.saveGameCPHistory(DboUtil.beanToDBO(uc), ActivityType.WINMINE.getType());
			super.saveActivity(roomId, uc);
			super.addJob(roomId, TIME_END);// 5秒展示 结束

			mb = new MapBody(SocketHandler.WINMINE_END, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_LIMIT, TIME_END).append("endType", endType).append("model", uc.getModel())
					.append("winner", uc.getWinner()).append("loser", uc.getLoser());
			if (uc.getWinner() != uc.getLoser()) {
				userService.changePoint(uc.getWinner(), Point.WINMINE.getType(), POINT_WIN, 0);
				userService.changePoint(uc.getLoser(), Point.WINMINE.getType(), POINT_DEFAULT, 0);
				mb.append("winPoint", POINT_WIN).append("lostPoint", POINT_DEFAULT);
			} else {// 平局
				for (Long aid : uc.getActors()) {
					userService.changePoint(aid, Point.WINMINE.getType(), POINT_DEFAULT, 0);
				}
				mb.append("winPoint", POINT_DEFAULT).append("lostPoint", POINT_DEFAULT);
			}
			mb.append("winCoin", winReward).append("lostCoin", lostReward);
			if (winReward > 0) {
				coinService.addCoin(uc.getWinner(), CoinLog.WINMINE, uc.get_id(), winReward, 0, "游戏胜利加币");
			}
			if (lostReward > 0) {
				coinService.forceReduce(uc.getLoser(), CoinLog.WINMINE, uc.get_id(), lostReward, 0, "游戏失败扣币");
			}
			if (uc.getModel() == Const.ACTIVITY_MATCH) {// 匹配
				mb.append("winScore", winReward).append("lostScore", lostReward);
			}
		}
		super.pubRoomMsg(roomId, MsgType.WINMINE, mb);
	}

	@Override
	public ReMsg outRoom(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.OUT_ROOM);
	}

	/** 退出房间 有人退出即游戏结束 */
	public ReMsg outRoom(long uid, Room r, int type) {
		if (r.getType() != ActivityType.WINMINE.getType()) {
			log.error(r.get_id() + " " + r.getType());
			return new ReMsg(ReCode.FAIL);
		}
		WinmineActor actor = super.getRoomActor(r.get_id(), uid, WinmineActor.class);
		if (null == actor) {
			return new ReMsg(ReCode.ACTOR_ERROR);
		} else {
			Winmine uc = super.getActivity(r.get_id(), Winmine.class);
			if (uc.getStatus() == Winmine.STATUS_START) {
				end(r.get_id(), uc, uid, END_OUTROOM);
			} else if (uc.getStatus() == Winmine.STATUS_READY || uc.getStatus() == Winmine.STATUS_WAIT) {
				end(r.get_id(), uc, uid, END_FAIL);
			}
			this.closeRoom(r.get_id());
		}
		return new ReMsg(ReCode.OK);
	}

	public void closeRoom(final long roomId, Winmine uc) {
		if (uc.getStatus() == Winmine.STATUS_END && uc.getActors().size() == 2) {
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

	/** 初始化map */
	private int[][][] initMap(int mineCnt) {
		ArrayList<int[]> place = new ArrayList<int[]>();
		for (int i = 0; i < GRID_X * GRID_Y - mineCnt; i++) {
			place.add(new int[2]);
		}
		for (int i = 0; i < mineCnt; i++) {
			place.add(new int[] { CHESS_MINE, 0 });
		}
		Collections.shuffle(place);
		int[][][] map = new int[GRID_X][GRID_Y][2];
		int index = 0;
		for (int i = 0; i < GRID_X; i++) {
			for (int j = 0; j < GRID_Y; j++) {
				map[i][j] = place.get(index);
				index++;
			}
		}
		// 根据已布置雷的地图 计算出地图上的数字
		for (int i = 0; i < GRID_X; i++) {
			for (int j = 0; j < GRID_Y; j++) {
				if (map[i][j][0] == CHESS_MINE) {
					calculateNearPalceMine(map, i, j);
				}
			}
		}
		return map;
	}

	/** 给雷附近的八个坐标加数字 */
	private void calculateNearPalceMine(int[][][] map, int x, int y) {
		List<int[]> list = getArount8Lbs(x, y);
		int newX = 0;
		int newY = 0;
		for (int[] lbs : list) {
			newX = lbs[0];
			newY = lbs[1];
			if (map[newX][newY][0] >= 0) {
				map[newX][newY][0] = map[newX][newY][0] + 1;
			}
		}
	}

	/** 获取周围八个方向未超出地图的 有效的坐标 */
	public List<int[]> getArount8Lbs(int x, int y) {
		List<int[]> temp = new ArrayList<int[]>();
		if (validLbs(x, y + 1)) {
			temp.add(new int[] { x, y + 1 });
		}
		// 下方 x y-1
		if (validLbs(x, y - 1)) {
			temp.add(new int[] { x, y - 1 });
		}
		// 左方 x-1 y
		if (validLbs(x - 1, y)) {
			temp.add(new int[] { x - 1, y });
		}
		// 右方 x+1 y
		if (validLbs(x + 1, y)) {
			temp.add(new int[] { x + 1, y });
		}
		// 左上 x-1 y+1
		if (validLbs(x - 1, y + 1)) {
			temp.add(new int[] { x - 1, y + 1 });
		}
		// 左下 x-1 y-1
		if (validLbs(x - 1, y - 1)) {
			temp.add(new int[] { x - 1, y - 1 });
		}
		// 右上 x+1 y+1
		if (validLbs(x + 1, y + 1)) {
			temp.add(new int[] { x + 1, y + 1 });
		}
		// 右下 x+1 y-1
		if (validLbs(x + 1, y - 1)) {
			temp.add(new int[] { x + 1, y - 1 });
		}
		return temp;
	}

	/** 校验坐标是否有效 即是否在地图上 */
	private boolean validLbs(int x, int y) {
		if (0 <= x && x < GRID_X && 0 <= y && y < GRID_Y) {
			return true;
		}
		return false;
	}

	/** 随机雷数 */
	private int randomMineCnt() {
		return RandomUtil.nextInt(MINE_COUNT_MAX + 1 - MINE_COUNT_MIN) + MINE_COUNT_MIN;
	}

	public static void main(String[] args) {
		// int[][][] initMap = initMap(30);
		//
		// for (int j = GRID_Y - 1; j >= 0; j--) {
		// for (int i = 0; i < GRID_X; i++) {
		// if (initMap[i][j][0] < 0) {
		// System.out.print(initMap[i][j][0] + " ");
		// } else {
		// System.out.print(" " + initMap[i][j][0] + " ");
		// }
		//
		// }
		// System.out.println("");
		// }
	}
}
