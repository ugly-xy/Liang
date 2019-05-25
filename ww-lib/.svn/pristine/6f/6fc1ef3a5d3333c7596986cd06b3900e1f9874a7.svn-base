package com.zb.service.room.robit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.room.Room;
import com.zb.models.room.activity.NeuroCat;
import com.zb.models.room.activity.NeuroCatActor;
import com.zb.models.room.activity.NeuroCatBox;
import com.zb.service.room.cp.NeuroCatService;

@Service
public class NeuroCatRobitService extends ActivityRobitService {
	static final Logger log = LoggerFactory.getLogger(NeuroCatRobitService.class);

	@Autowired
	NeuroCatService neuroCatService;

	private static final int ROBIT_TIME = 3000;

	public static final int AROUND_CENETR = NeuroCatService.AROUND_CENETR;// 中间的猫
	public static final int AROUND_TOP = NeuroCatService.AROUND_TOP;// 上方
	public static final int AROUND_BELOW = NeuroCatService.AROUND_BELOW;// 下方
	public static final int AROUND_LEFT = NeuroCatService.AROUND_LEFT;// 左方
	public static final int AROUND_RIGHT = NeuroCatService.AROUND_RIGHT;// 右方

	public static final int LEVER_VERTICLA = NeuroCatService.LEVER_VERTICLA;// 竖
	public static final int LEVER_SIDEWAYS = NeuroCatService.LEVER_SIDEWAYS;// 横

	public static final int EXIST_YES = NeuroCatBox.EXIST_YES;// 存在
	public static final int EXIST_STARTING_LEVER = NeuroCatBox.EXIST_STARTING_LEVER;// 首杆
	public static final int EXIST_NO = NeuroCatBox.EXIST_NO;// 默认不存在

	@Override
	public ReMsg handle(Room r, long uid) {
		NeuroCat uc = super.getActivity(r.get_id(), NeuroCat.class);
		if (NeuroCat.STATUS_READY == uc.getStatus() || NeuroCat.STATUS_WAIT == uc.getStatus()) {
			if (null == super.getRoomActor(r.get_id(), uid, NeuroCatActor.class)) {// 机器人不在房间
				neuroCatService.inRoom(uid, r, Const.ACTIVITY_MATCH, true);
			}
			userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
		} else if (uc.getStatus() == NeuroCat.STATUS_PLAY) {
			if (uc.getPlayer() != uid || uc.isPlay()) {
				userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
				return null;
			}
			// TODO 判断 自己的距离比对面的距离近 走 对面的距离近 堵
			NeuroCatBox[][] map = uc.getMap();
			int[] myCatLbs;
			int myY = 0;// 终点
			int[] rivalCatLbs;
			int rivalY = 0;// 对方终点
			if (uc.getColorRed() == uid) {// 我是红色
				myY = 8;
				myCatLbs = uc.getRedCatLbs();
				rivalCatLbs = uc.getBlueCatLbs();
			} else {// 我是蓝色
				rivalY = 8;
				myCatLbs = uc.getBlueCatLbs();
				rivalCatLbs = uc.getRedCatLbs();
			}
			if (Math.abs(myCatLbs[1] - myY) == 1 && rivalCatLbs[1] - myY == 0 && myCatLbs[0] == rivalCatLbs[0]) {
				// 我在终点前面 有猫挡着我了
				// 我在终点上边 而且我下边没有有杆 或者 我在终点下边 而且我上边没有杆
				if ((myCatLbs[1] - myY == 1 && map[myCatLbs[0]][myCatLbs[1]].getDataByAround(AROUND_BELOW) == EXIST_NO)
						|| (myCatLbs[1] - myY == -1
								&& map[myCatLbs[0]][myCatLbs[1]].getDataByAround(AROUND_TOP) == EXIST_NO)) {
					// 走对面猫的两边
					int[] leftLbs = neuroCatService.getAroundLbs(rivalCatLbs[0], rivalCatLbs[1], AROUND_LEFT);
					// 左边有位置 而且右边没杆
					if (leftLbs != null && map[leftLbs[0]][leftLbs[1]].getDataByAround(AROUND_RIGHT) == EXIST_NO) {
						neuroCatService.moveCat(uid, r.get_id(), myCatLbs, leftLbs);
						userRobitService.addJob(r.get_id(), uid, robitTime());
						return null;
					}
					int[] rightLbs = neuroCatService.getAroundLbs(rivalCatLbs[0], rivalCatLbs[1], AROUND_RIGHT);
					// 右有位置 而且左边没杆
					if (rightLbs != null && map[rightLbs[0]][rightLbs[1]].getDataByAround(AROUND_LEFT) == EXIST_NO) {
						neuroCatService.moveCat(uid, r.get_id(), myCatLbs, rightLbs);
						userRobitService.addJob(r.get_id(), uid, robitTime());
						return null;
					}
				}
			}
			int[] myRes = calculateRes(myCatLbs, map, myY);
			int[] rivalRes = calculateRes(rivalCatLbs, map, rivalY);
			// 进行比较
			if (myRes[2] > rivalRes[2] + 3 || (rivalRes[2] <= 3 && myCatLbs[2] > rivalRes[2])) {
				if (uc.getUserLever(uid) > 0) {
					// 对方差三步到终点 或者我落后三步以上 而且我还有杆
					// int[] lever = putLeverRobit(rivalCatLbs, map, rivalY);
					int[] lever = putLeverRobit2(rivalCatLbs, rivalRes, map);
					if (lever != null) {
						ReMsg rm = neuroCatService.putLever(uid, r.get_id(), lever, lever[2]);
						if (rm.getCode() == ReCode.OK.reCode()) {// 堵成功了
							userRobitService.addJob(r.get_id(), uid, robitTime());
							return null;
						}
					}
				}
			}
			// 正常走
			neuroCatService.moveCat(uid, r.get_id(), myCatLbs, myRes);
			userRobitService.addJob(r.get_id(), uid, robitTime());
		}
		return null;
	}

	public int robitTime() {
		return RandomUtil.nextInt(5000) + 3000;
	}

	/** 放置杆 oldXY猫的坐标 XY下一步最优路线坐标 */
	private int[] putLeverRobit2(int[] oldXY, int[] XY, NeuroCatBox[][] map) {
		if (Math.abs(XY[0] - oldXY[0]) == 1 && Math.abs(XY[1] - oldXY[1]) == 1) {
			// 没法堵
			return null;
		} else if ((Math.abs(oldXY[0] - XY[0]) >= 1 && Math.abs(oldXY[1] - XY[1]) == 0)
				|| (Math.abs(oldXY[0] - XY[0]) == 0 && Math.abs(oldXY[1] - XY[1]) >= 1)) {
			// 如果是常规四个方向
			if (oldXY[0] - XY[0] >= 1 && oldXY[1] - XY[1] == 0) {
				// 往左走 老坐标左边放杆
				// 下方坐标 oldXY坐标为起点
				int[] bellowLbs = neuroCatService.getAroundLbs(oldXY[0], oldXY[1], AROUND_BELOW);
				if (null != bellowLbs && map[bellowLbs[0]][bellowLbs[1]].getDataByAround(AROUND_LEFT) == EXIST_NO) {
					return new int[] { oldXY[0], oldXY[1], LEVER_VERTICLA };
				}
				// 上方坐标 该坐标为起点
				int[] topLbs = neuroCatService.getAroundLbs(oldXY[0], oldXY[1], AROUND_TOP);
				if (null != topLbs && map[topLbs[0]][topLbs[1]].getDataByAround(AROUND_LEFT) == EXIST_NO) {
					return new int[] { topLbs[0], topLbs[1], LEVER_VERTICLA };
				}
			} else if (oldXY[0] - XY[0] <= -1 && oldXY[1] - XY[1] == 0) {
				// 往右走 新坐标左边放杆
				// 下方坐标 XY坐标为起点
				int[] bellowLbs = neuroCatService.getAroundLbs(XY[0], XY[1], AROUND_BELOW);
				if (null != bellowLbs && map[bellowLbs[0]][bellowLbs[1]].getDataByAround(AROUND_LEFT) == EXIST_NO) {
					return new int[] { XY[0], XY[1], LEVER_VERTICLA };
				}
				// 上方坐标 该坐标为起点
				int[] topLbs = neuroCatService.getAroundLbs(XY[0], XY[1], AROUND_TOP);
				if (null != topLbs && map[topLbs[0]][topLbs[1]].getDataByAround(AROUND_LEFT) == EXIST_NO) {
					return new int[] { topLbs[0], topLbs[1], LEVER_VERTICLA };
				}
			} else if (oldXY[0] - XY[0] == 0 && oldXY[1] - XY[1] >= 1) {
				// 往下走 老坐标下边放杆
				// 左边坐标 该坐标为起点
				int[] leftLbs = neuroCatService.getAroundLbs(oldXY[0], oldXY[1], AROUND_LEFT);
				if (null != leftLbs && map[leftLbs[0]][leftLbs[1]].getDataByAround(AROUND_BELOW) == EXIST_NO) {
					return new int[] { leftLbs[0], leftLbs[1], LEVER_SIDEWAYS };
				}
				// 右方坐标 oldXY坐标为起点
				int[] rightLbs = neuroCatService.getAroundLbs(oldXY[0], oldXY[1], AROUND_RIGHT);
				if (null != rightLbs && map[rightLbs[0]][rightLbs[1]].getDataByAround(AROUND_BELOW) == EXIST_NO) {
					return new int[] { oldXY[0], oldXY[1], LEVER_SIDEWAYS };
				}
			} else if (oldXY[0] - XY[0] == 0 && oldXY[1] - XY[1] <= -1) {
				// 往上走 新坐标下边放杆
				// 左边坐标 该坐标为起点
				int[] leftLbs = neuroCatService.getAroundLbs(XY[0], XY[1], AROUND_LEFT);
				if (null != leftLbs && map[leftLbs[0]][leftLbs[1]].getDataByAround(AROUND_BELOW) == EXIST_NO) {
					return new int[] { leftLbs[0], leftLbs[1], LEVER_SIDEWAYS };
				}
				// 右方坐标 XY坐标为起点
				int[] rightLbs = neuroCatService.getAroundLbs(XY[0], XY[1], AROUND_RIGHT);
				if (null != rightLbs && map[rightLbs[0]][rightLbs[1]].getDataByAround(AROUND_BELOW) == EXIST_NO) {
					return new int[] { XY[0], XY[1], LEVER_SIDEWAYS };
				}
			}
		} else if ((Math.abs(oldXY[0] - XY[0]) == 2 && Math.abs(oldXY[1] - XY[1]) == 0)
				|| (Math.abs(oldXY[0] - XY[0]) == 0 && Math.abs(oldXY[1] - XY[1]) == 2)) {
			// 与上方走一格合并
		}
		return null;
	}

	/** 放置杆 对方的猫坐标 地图 目标y坐标 */
	private int[] putLeverRobit(int[] catLbs, NeuroCatBox[][] map, int y) {
		NeuroCatBox box = map[catLbs[0]][catLbs[1]];
		if (y == 8) {// 往猫的上边放杆
			int aroundLever = box.getDataByAround(AROUND_TOP);
			// 上方没杆
			if (aroundLever == EXIST_NO) {
				// 查看右方坐标的上边有没有杆 没有的话以当前坐标上方为起点放
				int[] rightLbs = neuroCatService.getAroundLbs(catLbs[0], catLbs[1], AROUND_RIGHT);
				if (null != rightLbs && map[rightLbs[0]][rightLbs[1]].getDataByAround(AROUND_TOP) == EXIST_NO) {
					int[] aroundLbs = neuroCatService.getAroundLbs(catLbs[0], catLbs[1], AROUND_TOP);
					if (null != aroundLbs) {
						return new int[] { aroundLbs[0], aroundLbs[1], LEVER_SIDEWAYS };
					}
				}
				// 如果右方坐标上方有杆 不能放 查看左边坐标上边有没有杆 没有的话以左边坐标的上方为起点放
				int[] leftLbs = neuroCatService.getAroundLbs(catLbs[0], catLbs[1], AROUND_LEFT);
				if (null != leftLbs && map[leftLbs[0]][leftLbs[1]].getDataByAround(AROUND_TOP) == EXIST_NO) {
					int[] aroundLbs = neuroCatService.getAroundLbs(leftLbs[0], leftLbs[1], AROUND_TOP);
					if (null != aroundLbs) {
						return new int[] { aroundLbs[0], aroundLbs[1], LEVER_SIDEWAYS };
					}
				}
			} else if (aroundLever == EXIST_YES) {
				// 末位杆 查看该坐标的右边有没有杆
				if (box.getDataByAround(AROUND_RIGHT) == EXIST_NO) {
					// 查看该坐标下边的坐标 右边有没有杆 没有的话就可以以当前坐标的右边为起点往左放竖杆
					int[] bellowLbs = neuroCatService.getAroundLbs(catLbs[0], catLbs[1], AROUND_BELOW);
					if (null != bellowLbs
							&& map[bellowLbs[0]][bellowLbs[1]].getDataByAround(AROUND_RIGHT) == EXIST_NO) {
						int[] aroundLbs = neuroCatService.getAroundLbs(catLbs[0], catLbs[1], AROUND_RIGHT);
						if (null != aroundLbs) {
							return new int[] { aroundLbs[0], aroundLbs[1], LEVER_VERTICLA };
						}
					}
					// 如果下边的坐标右边有杆的话 查看上边的坐标的右边有没有杆 没有的话以当前坐标的上边坐标的右边坐标为起点往左放竖杆
					int[] topLbs = neuroCatService.getAroundLbs(catLbs[0], catLbs[1], AROUND_TOP);
					if (null != topLbs && map[topLbs[0]][topLbs[1]].getDataByAround(AROUND_RIGHT) == EXIST_NO) {
						int[] aroundLbs = neuroCatService.getAroundLbs(topLbs[0], topLbs[1], AROUND_RIGHT);
						if (null != aroundLbs) {
							return new int[] { aroundLbs[0], aroundLbs[1], LEVER_VERTICLA };
						}
					}
				}
			} else if (aroundLever == EXIST_STARTING_LEVER) {
				// 首位杆 查看该坐标的左边有没有杆
				if (box.getDataByAround(AROUND_LEFT) == EXIST_NO) {
					// 左边没有的话往 查看下边坐标的左边有没有杆
					int[] bellowLbs = neuroCatService.getAroundLbs(catLbs[0], catLbs[1], AROUND_BELOW);
					if (null != bellowLbs && map[bellowLbs[0]][bellowLbs[1]].getDataByAround(AROUND_LEFT) == EXIST_NO) {
						return new int[] { catLbs[0], catLbs[1], LEVER_VERTICLA };
					}
					// 如果下边的坐标左边有杆的话 查看上边的坐标的左边有没有杆 没有的话以当前坐标的上边坐标为起点往左放竖杆
					int[] topLbs = neuroCatService.getAroundLbs(catLbs[0], catLbs[1], AROUND_TOP);
					if (null != topLbs && map[topLbs[0]][topLbs[1]].getDataByAround(AROUND_LEFT) == EXIST_NO) {
						return new int[] { topLbs[0], topLbs[1], LEVER_VERTICLA };
					}
				}
			}
		} else {// 往猫的下方放杆
			int aroundLever = box.getDataByAround(AROUND_BELOW);
			if (aroundLever == EXIST_NO) {
				// 查看右方坐标的下边有没有杆 没有的话以当前坐标为起点放
				int[] rightLbs = neuroCatService.getAroundLbs(catLbs[0], catLbs[1], AROUND_RIGHT);
				if (null != rightLbs && map[rightLbs[0]][rightLbs[1]].getDataByAround(AROUND_BELOW) == EXIST_NO) {
					return new int[] { catLbs[0], catLbs[1], LEVER_SIDEWAYS };
				}
				// 如果右方坐标下方有杆 不能放 查看左边坐标下方有没有杆 没有的话以左边坐标为起点放
				int[] leftLbs = neuroCatService.getAroundLbs(catLbs[0], catLbs[1], AROUND_LEFT);
				if (null != leftLbs && map[leftLbs[0]][leftLbs[1]].getDataByAround(AROUND_BELOW) == EXIST_NO) {
					return new int[] { leftLbs[0], leftLbs[1], LEVER_SIDEWAYS };
				}
			} else if (aroundLever == EXIST_YES) {
				// 末位杆 查看该坐标的右边有没有杆
				if (box.getDataByAround(AROUND_RIGHT) == EXIST_NO) {
					// 查看该坐标上边的坐标 右边有没有杆 没有的话就可以 以当前坐标的上边的右边为起点往左放竖杆
					int[] topLbs = neuroCatService.getAroundLbs(catLbs[0], catLbs[1], AROUND_TOP);
					if (null != topLbs && map[topLbs[0]][topLbs[1]].getDataByAround(AROUND_RIGHT) == EXIST_NO) {
						int[] aroundLbs = neuroCatService.getAroundLbs(topLbs[0], topLbs[1], AROUND_RIGHT);
						if (null != aroundLbs) {
							return new int[] { aroundLbs[0], aroundLbs[1], LEVER_VERTICLA };
						}
					}
					// 如果上方的坐标右边有杆的话 查看下边的坐标的右边有没有杆 没有的话以当前坐标的下边坐标的右边坐标为起点往左放竖杆
					int[] bellowLbs = neuroCatService.getAroundLbs(catLbs[0], catLbs[1], AROUND_BELOW);
					if (null != bellowLbs
							&& map[bellowLbs[0]][bellowLbs[1]].getDataByAround(AROUND_RIGHT) == EXIST_NO) {
						int[] aroundLbs = neuroCatService.getAroundLbs(bellowLbs[0], bellowLbs[1], AROUND_RIGHT);
						if (null != aroundLbs) {
							return new int[] { aroundLbs[0], aroundLbs[1], LEVER_VERTICLA };
						}
					}
				}
			} else if (aroundLever == EXIST_STARTING_LEVER) {
				// 首位杆 查看该坐标的左边有没有杆
				if (box.getDataByAround(AROUND_LEFT) == EXIST_NO) {
					// 左边没有的话往 查看上边坐标的左边有没有杆 没有的话以上方坐标为起点放杆
					int[] topLbs = neuroCatService.getAroundLbs(catLbs[0], catLbs[1], AROUND_TOP);
					if (null != topLbs && map[topLbs[0]][topLbs[1]].getDataByAround(AROUND_LEFT) == EXIST_NO) {
						return new int[] { topLbs[0], topLbs[1], LEVER_VERTICLA };
					}
					// 如果上边的坐标左边有杆的话 查看下边的坐标的左边有没有杆 没有的话以当前坐标为起点往左放竖杆
					int[] bellowLbs = neuroCatService.getAroundLbs(catLbs[0], catLbs[1], AROUND_BELOW);
					if (null != bellowLbs && map[bellowLbs[0]][bellowLbs[1]].getDataByAround(AROUND_LEFT) == EXIST_NO) {
						return new int[] { catLbs[0], catLbs[1], LEVER_VERTICLA };
					}
				}
			}
		}
		return null;
	}

	/** 自己的猫坐标 地图 目标y坐标 */
	public int[] calculateRes(int[] catLbs, NeuroCatBox[][] map, int y) {
		// 找出自己的最优线路
		int step = 1;
		int[] centerLbs = null;
		List<int[]> playLbs = new ArrayList<int[]>();
		// 已经走过的坐标
		Set<String> goByLbs = new HashSet<String>();
		goByLbs.add(catLbs[0] + "," + catLbs[1]);
		List<List<int[]>> tempMap = new ArrayList<List<int[]>>();
		playLbs.addAll(neuroCatService.getArount4Lbs(catLbs[0], catLbs[1], map[catLbs[0]][catLbs[1]]));
		tempMap.add(neuroCatService.getArount4Lbs(catLbs[0], catLbs[1], map[catLbs[0]][catLbs[1]]));
		while (playLbs.size() > 0) {
			List<int[]> tempLbs = new ArrayList<int[]>();
			Iterator<int[]> iterator = playLbs.iterator();
			while (iterator.hasNext()) {
				int[] next = iterator.next();
				if (!goByLbs.contains(next[0] + "," + next[1])) {
					// 没有走过这个节点 把这个节点能走的路添加进去
					goByLbs.add(next[0] + "," + next[1]);
					tempLbs.addAll(neuroCatService.getArount4Lbs(next[0], next[1], map[next[0]][next[1]]));
				}
				iterator.remove();
			}
			// 添加下一回合的要走的缓存坐标点
			step++;
			tempMap.add(tempLbs);
			for (int[] nextLbs : tempLbs) {
				if (nextLbs[1] == y) {// 走到了对方的大本营 这是最优路径
					centerLbs = nextLbs;// 过程中的途经坐标点
					for (int i = step - 1; i >= 0; i--) {
						List<int[]> list = tempMap.get(i);
						for (int[] js : list) {
							// 上一步中挨边的棋子 即距离为1
							boolean move = false;
							if (centerLbs[0] - js[0] == 1 && centerLbs[1] - js[1] == 0) {
								// centerLbs往左走 即centerLbs左边不能有杆
								if (map[centerLbs[0]][centerLbs[1]].getDataByAround(AROUND_LEFT) == EXIST_NO) {
									move = true;
								}

							} else if (centerLbs[0] - js[0] == -1 && centerLbs[1] - js[1] == 0) {
								// centerLbs往右走 即js左边不能有杆
								if (map[js[0]][js[1]].getDataByAround(AROUND_LEFT) == EXIST_NO) {
									move = true;
								}
							} else if (centerLbs[0] - js[0] == 0 && centerLbs[1] - js[1] == 1) {
								// centerLbs往下走 即centerLbs下边不能有杆
								if (map[centerLbs[0]][centerLbs[1]].getDataByAround(AROUND_BELOW) == EXIST_NO) {
									move = true;
								}
							} else if (centerLbs[0] - js[0] == 0 && centerLbs[1] - js[1] == -1) {
								// centerLbs往上走 即js下边不能有杆
								if (map[js[0]][js[1]].getDataByAround(AROUND_BELOW) == EXIST_NO) {
									move = true;
								}
							}
							if (move) {
								if (!(i == 0 && map[js[0]][js[1]].getCat() != EXIST_NO)) {
									// 到达第一步 如果第一步是猫 则忽略第一步 不赋值
									// 当作直接走第二步处理
									centerLbs = js;
								}
								break;
							}
						}
					}
					break;
				}
			}
			if (centerLbs == null) { // 没有找到最优点 即还没有到达对面的大本营
				// 目前走不到大本营 继续走 加入到新的搜索队列中
				playLbs.addAll(tempLbs);
			}
		}
		// 代表我找到了最优点 以及找到了我需要几步走到终点
		return new int[] { centerLbs[0], centerLbs[1], step };
	}
}
