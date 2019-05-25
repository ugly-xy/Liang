package com.zb.service.room.robit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.common.Constant.Const;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.room.Room;
import com.zb.models.room.activity.Gomoku;
import com.zb.models.room.activity.GomokuActor;
import com.zb.service.room.cp.GomokuService;

@Service
public class GomokuRobitService extends ActivityRobitService {
	static final Logger log = LoggerFactory.getLogger(GomokuRobitService.class);

	@Autowired
	GomokuService gomokuService;

	static final int DIFFICULTY = 2;
	static final int GRID = GomokuService.GRID;
	private static final int ROBIT_TIME = 3000;

	@Override
	public ReMsg handle(Room r, long uid) {
		long roomId = r.get_id();
		Gomoku go = super.getActivity(roomId, Gomoku.class);
		if (Gomoku.STATUS_READY == go.getStatus() || Gomoku.STATUS_WAIT == go.getStatus()) {
			if (null == super.getRoomActor(roomId, uid, GomokuActor.class)) {// 机器人不在房间
				gomokuService.inRoom(uid, r, Const.ACTIVITY_MATCH, true);
			}
			userRobitService.addJob(roomId, uid, ROBIT_TIME);
		} else if (Gomoku.STATUS_START == go.getStatus()) {
			if (go.getCurUid() == uid) {
				attackOrDefense(roomId, go, uid);
			}
			if (go.getBlackUser() == uid || go.getWhiteUser() == uid) {
				userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
			}
		}
		return null;
	}

	private void attackOrDefense(long roomId, Gomoku go, long uid) {
		int[][] goMap = go.getGoMap();
		int step = go.getStep();
		int xn = 0;
		int yn = 0;
		if (step == 0) {
			int c = RandomUtil.nextInt(6);
			if (c == 0) {
				xn = 6;
				yn = 7;
			} else if (c == 1) {
				xn = 8;
				yn = 7;
			} else if (c == 2) {
				xn = 7;
				yn = 6;
			} else if (c == 3) {
				xn = 7;
				yn = 8;
			} else {
				xn = 7;
				yn = 7;
			}
		} else if (step == 1) {
			int x = 0;
			int y = 0;
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					if (goMap[i][j] == 1) {
						x = i;
						y = j;
					}
				}
			}
			if (x == 7 && y == 7) {
				int c = RandomUtil.nextInt(4);
				if (c == 0) {
					xn = 6;
					yn = 7;
				} else if (c == 1) {
					xn = 8;
					yn = 7;
				} else if (c == 2) {
					xn = 7;
					yn = 6;
				} else {
					xn = 7;
					yn = 8;
				}
			} else if (x == 7 && y > 7) {
				xn = 7;
				yn = y - 1;
			} else if (x == 7 && y < 7) {
				xn = 7;
				yn = y + 1;
			} else if (y == 7 && x > 7) {
				yn = 7;
				xn = x - 1;
			} else if (y == 7 && x < 7) {
				yn = 7;
				xn = x + 1;
			} else {
				if (x < 7) {
					xn = x + 1;
				} else {
					xn = x - 1;
				}
				if (y < 7) {
					yn = y + 1;
				} else {
					yn = y - 1;
				}
			}
		} else {
			int myColour = go.getColour(uid);
			GomokuRobitService g = new GomokuRobitService();
			Point p = g.new Point();
			int h2[] = new int[3], h3[] = new int[3], h4[] = new int[3], s3[] = new int[3], s4[] = new int[3],
					s2[] = new int[3], hs5[] = new int[3];
			change(myColour, goMap);
			abGo(goMap, -1, -9999999, 9999999, 2, s2, h2, h3, h4, s3, s4, hs5, p);
			xn = p.x;
			yn = p.y;
		}
		if (go.getCurUid() == uid) {
			gomokuService.putDown(roomId, uid, xn, yn);
		}
	}

	private static void change(int myColour, int[][] goMap) {
		boolean first = GomokuActor.BLACK == myColour;
		for (int i = 0; i < GRID; i++) {
			for (int j = 0; j < GRID; j++) {
				if (goMap[i][j] % 2 == 1) {
					goMap[i][j] = first ? -1 : 1;
				} else if (goMap[i][j] != 0) {
					goMap[i][j] = first ? 1 : -1;
				}
			}
		}
	}

	public class Point {
		int x;
		int y;
		int m = -999999;
	}

	private static int max(int a, int b) {
		if (a > b)
			return a;
		return b;
	}

	private static int min(int a, int b) {
		if (a < b)
			return a;
		return b;
	}

	private static int abGo(int goMap[][], int player, int a, int b, int depth, int[] s2, int[] h2, int[] h3, int[] h4,
			int[] s3, int[] s4, int[] hs5, Point p) {
		if (depth == 0)
			return calScore(goMap, s2, h2, h3, h4, s3, s4, hs5);
		f: for (int x = 0; x < GRID; x++) {
			for (int y = 0; y < GRID; y++) {
				if (goMap[x][y] != 0)
					continue;
				if (player == -1 && GomokuService.fastCheckCount(goMap, x, y)) {
					p.x = x;
					p.y = y;
					break f;
				}
				goMap[x][y] = player;
				int v = abGo(goMap, player * -1, a, b, depth - 1, s2, h2, h3, h4, s3, s4, hs5, p);
				goMap[x][y] = 0;
				if (player < 0)
					a = max(a, v);
				else
					b = min(b, v);
				if (depth == 2) {
					if (v > p.m) {
						p.m = v;
						p.x = x;
						p.y = y;
					}
				}
				if (a >= b)
					break;
			}
		}
		return player < 0 ? a : b;
	}

	private static int calScore(int[][] goMap, int[] s2, int[] h2, int[] h3, int[] h4, int[] s3, int[] s4, int[] hs5) {
		for (int i = 0; i < GRID; i++) {
			int sum = 0, now = 0;
			for (int j = 0; j < GRID; j++) {
				if (goMap[i][j] != now) {
					now = goMap[i][j];
					sum = 1;
				} else if (now != 0) {
					sum++;
				}
				if (sum == 2) {
					if (j - sum >= 0 && j + 1 < GRID && goMap[i][j - sum] == 0 && goMap[i][j + 1] == 0) {
						h2[now + 1]++;
					} else if (j - sum >= 0 && j + 1 < GRID && (goMap[i][j - sum] == 0 || goMap[i][j + 1] == 0)) {
						s2[now + 1]++;
					}
				} else if (sum == 3) {
					if (j - sum >= 0 && j + 1 < GRID && goMap[i][j - sum] == 0 && goMap[i][j + 1] == 0) {
						h3[now + 1]++;
					} else if (j - sum >= 0 && j + 1 < GRID && (goMap[i][j - sum] == 0 || goMap[i][j + 1] == 0)) {
						s3[now + 1]++;
					}
				} else if (sum == 4) {
					if (j - sum >= 0 && j + 1 < GRID && goMap[i][j - sum] == 0 && goMap[i][j + 1] == 0) {
						h4[now + 1]++;
					} else if (j - sum >= 0 && j + 1 < GRID && (goMap[i][j - sum] == 0 || goMap[i][j + 1] == 0)) {
						s4[now + 1]++;
					}
				} else if (sum == 5) {
					hs5[now + 1]++;
				}
			}

			sum = now = 0;
			for (int j = 0; j < GRID; j++) {
				if (goMap[j][i] != now) {
					now = goMap[j][i];
					sum = 1;
				} else if (now != 0) {
					sum++;
				}
				if (sum == 2) {
					if (j - sum >= 0 && j + 1 < GRID && goMap[j - sum][i] == 0 && goMap[j + 1][i] == 0) {
						h2[now + 1]++;
					} else if (j - sum >= 0 && j + 1 < GRID && (goMap[j - sum][i] == 0 || goMap[j + 1][i] == 0)) {
						s2[now + 1]++;
					}
				} else if (sum == 3) {
					if (j - sum >= 0 && j + 1 < GRID && goMap[j - sum][i] == 0 && goMap[j + 1][i] == 0) {
						h3[now + 1]++;
					} else if (j - sum >= 0 && j + 1 < GRID && (goMap[j - sum][i] == 0 || goMap[j + 1][i] == 0)) {
						s3[now + 1]++;
					}
				} else if (sum == 4) {
					if (j - sum >= 0 && j + 1 < GRID && goMap[j - sum][i] == 0 && goMap[j + 1][i] == 0) {
						h4[now + 1]++;
					} else if (j - sum >= 0 && j + 1 < GRID && (goMap[j - sum][i] == 0 || goMap[j + 1][i] == 0)) {
						s4[now + 1]++;
					}
				} else if (sum == 5) {
					hs5[now + 1]++;
				}
			}
			sum = now = 0;
			for (int j = 0; i + j < GRID; j++) {
				if (goMap[i + j][j] != now) {
					now = goMap[i + j][j];
					sum = 1;
				} else if (now != 0) {
					sum++;
				}
				if (sum == 2) {
					if (j - sum >= 0 && i + j + 1 < GRID && goMap[i + j - sum][j - sum] == 0
							&& goMap[i + j + 1][j + 1] == 0) {
						h2[now + 1]++;
					} else if (j - sum >= 0 && i + j + 1 < GRID
							&& (goMap[i + j - sum][j - sum] == 0 || goMap[i + j + 1][j + 1] == 0)) {
						s2[now + 1]++;
					}
				} else if (sum == 3) {
					if (j - sum >= 0 && i + j + 1 < GRID && goMap[i + j - sum][j - sum] == 0
							&& goMap[i + j + 1][j + 1] == 0) {
						h3[now + 1]++;
					} else if (j - sum >= 0 && i + j + 1 < GRID
							&& (goMap[i + j - sum][j - sum] == 0 || goMap[i + j + 1][j + 1] == 0)) {
						s3[now + 1]++;
					}
				} else if (sum == 4) {
					if (j - sum >= 0 && i + j + 1 < GRID && goMap[i + j - sum][j - sum] == 0
							&& goMap[i + j + 1][j + 1] == 0) {
						h4[now + 1]++;
					} else if (j - sum >= 0 && i + j + 1 < GRID
							&& (goMap[i + j - sum][j - sum] == 0 || goMap[i + j + 1][j + 1] == 0)) {
						s4[now + 1]++;
					}
				} else if (sum == 5) {
					hs5[now + 1]++;
				}
			}
			sum = now = 0;
			for (int j = 0; i + j < GRID; j++) {
				if (i == 0) {
					break;
				}
				if (goMap[j][i + j] != now) {
					now = goMap[j][i + j];
					sum = 1;
				} else if (now != 0) {
					sum++;
				}
				if (sum == 2) {
					if (j - sum >= 0 && i + j + 1 < GRID && goMap[j - sum][i + j - sum] == 0
							&& goMap[j + 1][i + j + 1] == 0) {
						h2[now + 1]++;
					} else if (j - sum >= 0 && i + j + 1 < GRID
							&& (goMap[j - sum][i + j - sum] == 0 || goMap[j + 1][i + j + 1] == 0)) {
						s2[now + 1]++;
					}
				} else if (sum == 3) {
					if (j - sum >= 0 && i + j + 1 < GRID && goMap[j - sum][i + j - sum] == 0
							&& goMap[j + 1][i + j + 1] == 0) {
						h3[now + 1]++;
					} else if (j - sum >= 0 && i + j + 1 < GRID
							&& (goMap[j - sum][i + j - sum] == 0 || goMap[j + 1][i + j + 1] == 0)) {
						s3[now + 1]++;
					}
				} else if (sum == 4) {
					if (j - sum >= 0 && i + j + 1 < GRID && goMap[j - sum][i + j - sum] == 0
							&& goMap[j + 1][i + j + 1] == 0) {
						h4[now + 1]++;
					} else if (j - sum >= 0 && i + j + 1 < GRID
							&& (goMap[j - sum][i + j - sum] == 0 || goMap[j + 1][i + j + 1] == 0)) {
						s4[now + 1]++;
					}
				} else if (sum == 5) {
					hs5[now + 1]++;
				}
			}
			sum = now = 0;
			for (int j = 0; i + j < GRID; j++) {
				if (goMap[i + j][GRID - 1 - j] != now) {
					now = goMap[i + j][GRID - 1 - j];
					sum = 1;
				} else if (now != 0) {
					sum++;
				}
				if (sum == 2) {
					if (sum <= j && i + j + 1 < GRID && goMap[i + j - sum][GRID - 1 - j + sum] == 0
							&& goMap[i + j + 1][GRID - 2 - j] == 0) {
						h2[now + 1]++;
					} else if (sum <= j && i + j + 1 < GRID
							&& (goMap[i + j - sum][GRID - 1 - j + sum] == 0 || goMap[i + j + 1][GRID - 2 - j] == 0)) {
						s2[now + 1]++;
					}
				} else if (sum == 3) {
					if (sum <= j && i + j + 1 < GRID && goMap[i + j - sum][GRID - 1 - j + sum] == 0
							&& goMap[i + j + 1][GRID - 2 - j] == 0) {
						h3[now + 1]++;
					} else if (sum <= j && i + j + 1 < GRID
							&& (goMap[i + j - sum][GRID - 1 - j + sum] == 0 || goMap[i + j + 1][GRID - 2 - j] == 0)) {
						s3[now + 1]++;
					}
				} else if (sum == 4) {
					if (sum <= j && i + j + 1 < GRID && goMap[i + j - sum][GRID - 1 - j + sum] == 0
							&& goMap[i + j + 1][GRID - 2 - j] == 0) {
						h4[now + 1]++;
					} else if (sum <= j && i + j + 1 < GRID
							&& (goMap[i + j - sum][GRID - 1 - j + sum] == 0 || goMap[i + j + 1][GRID - 2 - j] == 0)) {
						s4[now + 1]++;
					}
				} else if (sum == 5) {
					hs5[now + 1]++;
				}
			}
			sum = now = 0;
			for (int j = 0; i + j < GRID; j++) {
				if (i == 0) {
					break;
				}
				if (goMap[j][GRID - 1 - i - j] != now) {
					now = goMap[j][GRID - 1 - i - j];
					sum = 1;
				} else if (now != 0) {
					sum++;
				}
				if (sum == 2) {
					if (j - sum >= 0 && i + j + 1 < GRID && goMap[j - sum][GRID - 1 - i - j + sum] == 0
							&& goMap[j + 1][GRID - 2 - i - j] == 0) {
						h2[now + 1]++;
					} else if (j - sum >= 0 && i + j + 1 < GRID
							&& (goMap[j - sum][GRID - 1 - i - j + sum] == 0 || goMap[j + 1][GRID - 2 - i - j] == 0)) {
						s2[now + 1]++;
					}
				} else if (sum == 3) {
					if (j - sum >= 0 && i + j + 1 < GRID && goMap[j - sum][GRID - 1 - i - j + sum] == 0
							&& goMap[j + 1][GRID - 2 - i - j] == 0) {
						h3[now + 1]++;
					} else if (j - sum >= 0 && i + j + 1 < GRID
							&& (goMap[j - sum][GRID - 1 - i - j + sum] == 0 || goMap[j + 1][GRID - 2 - i - j] == 0)) {
						s3[now + 1]++;
					}
				} else if (sum == 4) {
					if (j - sum >= 0 && i + j + 1 < GRID && goMap[j - sum][GRID - 1 - i - j + sum] == 0
							&& goMap[j + 1][GRID - 2 - i - j] == 0) {
						h4[now + 1]++;
					} else if (j - sum >= 0 && i + j + 1 < GRID
							&& (goMap[j - sum][GRID - 1 - i - j + sum] == 0 || goMap[j + 1][GRID - 2 - i - j] == 0)) {
						s4[now + 1]++;
					}
				} else if (sum == 5) {
					hs5[now + 1]++;
				}
			}
		}
		int own = (s2[0] * 5 + h2[0] * 70 + h3[0] * 300 + s3[0] * 50 + h4[0] * 4000 + s4[0] * 250 + hs5[0] * 5000);
		int oth = (s2[2] * 4 + h2[2] * 65 + h3[2] * 300 + s3[2] * 40 + h4[2] * 4000 + s4[2] * 250 + hs5[2] * 5000);
		for (int i = 0; i < 3; i++) {
			s2[i] = h2[i] = h3[i] = h4[i] = s3[i] = s4[i] = hs5[i] = 0;
		}
		return own - oth;
	}

}
