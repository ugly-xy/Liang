package com.zb.service.room.robit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.common.Constant.Const;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.room.Room;
import com.zb.models.room.activity.AnimalChess;
import com.zb.models.room.activity.AnimalChessActor;
import com.zb.service.room.cp.AnimalChessService;

@Service
public class AnimalChessRobitService extends ActivityRobitService {
	static final Logger log = LoggerFactory.getLogger(AnimalChessRobitService.class);

	@Autowired
	AnimalChessService animalChessService;

	private static final int ROBIT_TIME = 3000;

	@Override
	public ReMsg handle(Room r, long uid) {
		AnimalChess uc = super.getActivity(r.get_id(), AnimalChess.class);
		if (AnimalChess.STATUS_READY == uc.getStatus() || AnimalChess.STATUS_WAIT == uc.getStatus()) {
			if (null == super.getRoomActor(r.get_id(), uid, AnimalChessActor.class)) {// 机器人不在房间
				animalChessService.inRoom(uid, r, Const.ACTIVITY_MATCH, true);
			}
			userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
		} else if (uc.getStatus() == AnimalChess.STATUS_PLAY) {
			if (uc.getPlayer() != uid || uc.isPlay()) {
				userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
				return null;
			}
			if (uc.getStepCnt() > 50) {
				animalChessService.giveupGame(uid, r.get_id());
				return null;
			}
			int color = uc.getColorRed() == uid ? 1 : 2;
			int[][][] chessboard = uc.getChessboard();
			// 0 x 1 y 2chess
			List<int[]> openChess = new ArrayList<int[]>();
			List<int[]> otherOpenChess = new ArrayList<int[]>();
			List<int[]> allCloesChess = new ArrayList<int[]>();
			// 棋子分类
			for (int i = 0; i < chessboard.length; i++) {
				for (int j = 0; j < chessboard[i].length; j++) {
					int[] chess = chessboard[i][j];
					if (chess[1] == AnimalChessService.CHESS_STATUS_CLOSE) {
						allCloesChess.add(new int[] { i, j, chess[0] });
					} else if (chess[1] == AnimalChessService.CHESS_STATUS_OPEN) {
						if (chess[0] % 10 == color) {
							openChess.add(new int[] { i, j, chess[0] });
						} else {
							otherOpenChess.add(new int[] { i, j, chess[0] });
						}
					}
				}
			}
			// 寻找可走棋子
			List<int[][]> playChess = new ArrayList<int[][]>();
			List<int[][]> hitDie = new ArrayList<int[][]>();
			if (openChess.size() != 0 || allCloesChess.size() != 0) {
				for (int i = 0; i < openChess.size(); i++) {
					int[] myChess = openChess.get(i);
					int x = myChess[0];
					int y = myChess[1];
					List<int[]> otherChess = getAroundChess(x, y, color, chessboard, false);
					for (int[] chess : otherChess) {// 对方棋子
						if (myChess[2] / 10 == 8 && chess[2] / 10 == 1) {// 大象被吃
							playChess.add(new int[][] { myChess, chess });
						} else if (myChess[2] / 10 == 1 && chess[2] / 10 == 8) {// 老鼠吃大象
							playChess.add(new int[][] { chess, myChess });
						} else if (myChess[2] / 10 > chess[2] / 10) {// 我吃他
							playChess.add(new int[][] { chess, myChess });
						} else if (myChess[2] / 10 < chess[2] / 10) {// 他吃我
							playChess.add(new int[][] { myChess, chess });
						} else if (myChess[2] / 10 == chess[2] / 10) {// 相等
							hitDie.add(new int[][] { myChess, chess });
						}
					}
				}
				// 逃棋或吃棋
				playChess.stream().sorted((e1, e2) -> e2[0][2] - e1[0][2]).collect(Collectors.toList());
				if (playChess.size() != 0) {
					if (playChess.get(0)[0][2] / 10 != 8 && playChess.get(playChess.size() - 1)[0][2] / 10 == 1) {
						int[][] thisChess = playChess.get(playChess.size() - 1);
						if (thisChess[0][2] % 10 == color) {// 我的老鼠
							List<int[]> aroundPlace = aroundPlace(thisChess[0][0], thisChess[0][1]);
							for (int[] is : aroundPlace) {
								if (chessboard[is[0]][is[1]][0] == 0) {
									if (isDanger(is[0], is[1], thisChess[0][2], chessboard, true)) {
										animalChessService.moveNode(uid, r.get_id(), thisChess[0], is);
										userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
										return null;
									}
								}
							}
						} else {// 对面老鼠
							animalChessService.moveNode(uid, r.get_id(), thisChess[1], thisChess[0]);
							userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							return null;
						}
					}
					for (int[][] thisChess : playChess) {
						int[] oldXY = null;
						int[] XY = null;
						if (thisChess[0][2] % 10 == color) {// 我的棋子
							if (isDanger(thisChess[0][0], thisChess[0][1], thisChess[0][2], chessboard, false)) {
								continue;
							}
							List<int[]> aroundPlace = aroundPlace(thisChess[0][0], thisChess[0][1]);
							for (int[] is : aroundPlace) {
								int[] a = chessboard[is[0]][is[1]];
								if (a[1] != AnimalChessService.CHESS_STATUS_CLOSE && a[0] % 10 != color
										&& a[0] / 10 < thisChess[0][2] / 10
										&& (thisChess[0][2] / 10 != 8 || a[0] / 10 != 1)) {
									if (isDanger(is[0], is[1], thisChess[0][2], chessboard, false)) {
										oldXY = thisChess[0];
										XY = is;
										break;
									}
								}
							}
						} else {// 对面棋子
							if (isDanger(thisChess[0][0], thisChess[0][1], thisChess[1][2], chessboard, false)) {
								oldXY = thisChess[1];
								XY = thisChess[0];
							}
						}
						if (null != oldXY && null != XY) {
							animalChessService.moveNode(uid, r.get_id(), oldXY, XY);
							userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							return null;
						}
					}
				}
				// 撞棋
				hitDie.stream().sorted((e1, e2) -> e2[0][2] - e1[0][2]).collect(Collectors.toList());
				if (hitDie.size() != 0) {
					int[][] is = hitDie.get(0);
					animalChessService.moveNode(uid, r.get_id(), is[0], is[1]);
					userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
					return null;
				}
				// 模拟走一步找对面棋子吃
				if (otherOpenChess.size() > 1) {
					otherOpenChess.stream().sorted((e1, e2) -> e2[2] - e1[2]).collect(Collectors.toList());
					for (int[] is : otherOpenChess) {
						List<int[]> aroundPlace = aroundPlace(is[0], is[1]);
						for (int[] place : aroundPlace) {
							if (chessboard[place[0]][place[1]][0] / 10 == 0) {
								boolean danger = isDanger(place[0], place[1], is[2], chessboard,
										is[2] / 10 == 1 ? true : false);
								if (!danger) {
									aroundPlace = aroundPlace(place[0], place[1]);
									for (int[] i : aroundPlace) {
										int[] chess = chessboard[i[0]][i[1]];
										if (chess[1] != AnimalChessService.CHESS_STATUS_CLOSE && chess[0] % 10 == color
												&& chess[0] / 10 > is[2] / 10 && (chess[0] / 10 != 8 || is[2] != 1)) {
											if (isDanger(place[0], place[1], chess[0], chessboard, false)) {
												animalChessService.moveNode(uid, r.get_id(), i, place);
												userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
												return null;
											}

										}
									}
								}
							}
						}
					}
				}
				// 翻个牌子
				if (allCloesChess.size() != 0) {
					int i = RandomUtil.nextInt(allCloesChess.size());
					animalChessService.clickNode(uid, r.get_id(), allCloesChess.get(i));
					userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
					return null;
				}
				// 没有牌子 往能吃的棋子靠近
				openChess.stream().sorted((e1, e2) -> e2[2] - e1[2]).collect(Collectors.toList());
				if (openChess.size() != 0) {
					int[] XY = null;
					for (int[] is : openChess) {// 我的棋子
						for (int[] other : otherOpenChess) {
							if (is[2] / 10 >= other[2] / 10) {
								if (is[0] - other[0] < 0) {// 往下走
									if (chessboard[is[0] + 1][is[1]][1] % 10 != color) {
										if (isDanger(is[0] + 1, is[1], is[2], chessboard, false)) {
											XY = new int[] { is[0] + 1, is[1] };
											break;
										}
									}
								} else if (is[0] - other[0] > 0) {// 往上走
									if (chessboard[is[0] - 1][is[1]][1] % 10 != color) {
										if (isDanger(is[0] - 1, is[1], is[2], chessboard, false)) {
											XY = new int[] { is[0] - 1, is[1] };
											break;
										}
									}
								}
								if (is[1] - other[1] < 0) {// 往右走
									if (chessboard[is[0]][is[1] + 1][1] % 10 != color) {
										if (isDanger(is[0], is[1] + 1, is[2], chessboard, false)) {
											XY = new int[] { is[0], is[1] + 1 };
											break;
										}
									}
								} else if (is[1] - other[1] > 0) {// 往左走
									if (chessboard[is[0]][is[1] - 1][1] % 10 != color) {
										if (isDanger(is[0], is[1] - 1, is[2], chessboard, false)) {
											XY = new int[] { is[0], is[1] - 1 };
											break;
										}
									}
								}
							}
						}
						if (null != XY) {
							animalChessService.moveNode(uid, r.get_id(), is, XY);
							userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
							return null;
						}
					}
					// 随机走一个
					int[] js = openChess.get(RandomUtil.nextInt(openChess.size()));
					List<int[]> aroundPlace = aroundPlace(js[0], js[1]);
					for (int[] is : aroundPlace) {
						if (chessboard[is[0]][is[1]][1] != color && chessboard[is[0]][is[1]][0] / 10 < js[2] / 10
								&& (js[2] / 10 != 8 || chessboard[is[0]][is[1]][0] / 10 != 1)) {
							if (isDanger(is[0], is[1], js[2], chessboard, false)) {
								animalChessService.moveNode(uid, r.get_id(), js, is);
								userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
								return null;
							}
						}
					}
					animalChessService.giveupGame(uid, r.get_id());
					return null;
				}
			}
		}
		return null;
	}

	/** 寻找自己四周的棋子 */
	List<int[]> getAroundChess(int x, int y, int color, int[][][] chessboard, boolean myChess) {
		List<int[]> aroundChess = new ArrayList<int[]>();
		if (x == 0 || x == 3) {
			if (x == 0) {
				aroundChess.add(new int[] { x + 1, y, chessboard[x + 1][y][0], chessboard[x + 1][y][1] });
			} else if (x == 3) {
				aroundChess.add(new int[] { x - 1, y, chessboard[x - 1][y][0], chessboard[x - 1][y][1] });
			}
			if (y == 0) {
				aroundChess.add(new int[] { x, y + 1, chessboard[x][y + 1][0], chessboard[x][y + 1][1] });
			} else if (y == 3) {
				aroundChess.add(new int[] { x, y - 1, chessboard[x][y - 1][0], chessboard[x][y - 1][1] });
			} else {
				aroundChess.add(new int[] { x, y + 1, chessboard[x][y + 1][0], chessboard[x][y + 1][1] });
				aroundChess.add(new int[] { x, y - 1, chessboard[x][y - 1][0], chessboard[x][y - 1][1] });
			}
		} else {
			aroundChess.add(new int[] { x - 1, y, chessboard[x - 1][y][0], chessboard[x - 1][y][1] });
			aroundChess.add(new int[] { x + 1, y, chessboard[x + 1][y][0], chessboard[x + 1][y][1] });
			if (y == 0) {
				aroundChess.add(new int[] { x, y + 1, chessboard[x][y + 1][0], chessboard[x][y + 1][1] });
			} else if (y == 3) {
				aroundChess.add(new int[] { x, y - 1, chessboard[x][y - 1][0], chessboard[x][y - 1][1] });
			} else {
				aroundChess.add(new int[] { x, y + 1, chessboard[x][y + 1][0], chessboard[x][y + 1][1] });
				aroundChess.add(new int[] { x, y - 1, chessboard[x][y - 1][0], chessboard[x][y - 1][1] });
			}
		}
		Iterator<int[]> it = aroundChess.iterator();
		while (it.hasNext()) {
			int[] next = it.next();
			if (next[3] != AnimalChessService.CHESS_STATUS_OPEN) {
				it.remove();
				continue;
			}
			if (myChess) {
				if (next[2] % 10 == color) {
					it.remove();
				}
			} else {
				if (next[2] % 10 == color) {
					it.remove();
				}
			}
		}
		// 按照大小的顺序排序
		return aroundChess.stream().sorted((e1, e2) -> e2[2] - e1[2]).collect(Collectors.toList());
	}

	boolean isDanger(int x, int y, int chess, int[][][] chessboard, boolean isMouse) {
		List<int[]> myChess = getAroundChess(x, y, chess % 10, chessboard, true);
		List<int[]> otherChess = getAroundChess(x, y, chess % 10, chessboard, false);
		if (otherChess.size() < 1) {
			return true;
		}
		if (isMouse) {
			return false;
		}
		if (chess >= otherChess.get(0)[2] && (chess / 10 != 8 || otherChess.get(otherChess.size() - 1)[2] / 10 != 1)) {
			return true;
		}
		if (myChess.size() > 0 && otherChess.get(0)[2] < myChess.get(0)[2]) {
			return true;
		}
		return false;
	}

	List<int[]> aroundPlace(int x, int y) {
		List<int[]> aroundPlace = new ArrayList<int[]>();
		if (x == 0 || x == 3) {
			if (x == 0) {
				aroundPlace.add(new int[] { x + 1, y });
			} else if (x == 3) {
				aroundPlace.add(new int[] { x - 1, y });
			}
			if (y == 0) {
				aroundPlace.add(new int[] { x, y + 1 });
			} else if (y == 3) {
				aroundPlace.add(new int[] { x, y - 1 });
			} else {
				aroundPlace.add(new int[] { x, y + 1 });
				aroundPlace.add(new int[] { x, y - 1 });
			}
		} else {
			aroundPlace.add(new int[] { x - 1, y });
			aroundPlace.add(new int[] { x + 1, y });
			if (y == 0) {
				aroundPlace.add(new int[] { x, y + 1 });
			} else if (y == 3) {
				aroundPlace.add(new int[] { x, y - 1 });
			} else {
				aroundPlace.add(new int[] { x, y + 1 });
				aroundPlace.add(new int[] { x, y - 1 });
			}
		}
		return aroundPlace;
	}
}
