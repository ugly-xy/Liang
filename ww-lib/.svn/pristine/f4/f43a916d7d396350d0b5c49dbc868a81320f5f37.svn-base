package com.zb.service.room.cp;

import java.util.ArrayList;
import java.util.HashSet;
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
import com.zb.models.room.activity.NeuroCat;
import com.zb.models.room.activity.NeuroCatActor;
import com.zb.models.room.activity.NeuroCatBox;
import com.zb.service.GameCPScoreService;
import com.zb.service.room.vo.NeuroCatRoomInfo;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class NeuroCatService extends RoomCPService {
	static final Logger log = LoggerFactory.getLogger(NeuroCatService.class);

	@Autowired
	GameCPScoreService gameCPScoreService;

	@Override
	public ReMsg handle(Room room, long fr) {
		if (room.getType() != ActivityType.NEURO_CAT.getType()) {
			log.error(room.get_id() + " " + room.getType());
			return new ReMsg(ReCode.FAIL);
		}
		try {
			NeuroCat uc = super.getActivity(room.get_id(), NeuroCat.class);
			if (NeuroCat.STATUS_WAIT == uc.getStatus()) {
				start(room, uc);
			} else if (NeuroCat.STATUS_PLAY == uc.getStatus()) {
				nextPlay(room, uc);// 新的回合
			} else if (NeuroCat.STATUS_END == uc.getStatus()) {
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
	public static final int LEVER_COUNT = 10;// 初始杠杆数

	// 地图
	public static final int GRID_X = 9; // 棋盘横向长度
	public static final int GRID_Y = 9;// 棋盘纵向长度

	public static final int POINT_WIN = 30;
	public static final int POINT_DEFAULT = 10;

	// 杆方向
	public static final int LEVER_VERTICLA = 1;// 竖
	public static final int LEVER_SIDEWAYS = 2;// 横

	// 方向
	public static final int AROUND_CENETR = 0;// 中间的猫
	public static final int AROUND_TOP = 1;// 上方
	public static final int AROUND_BELOW = 2;// 下方
	public static final int AROUND_LEFT = 3;// 左方
	public static final int AROUND_RIGHT = 4;// 右方

	// 颜色
	public static final int COLOR_RED = 2;// 红色 下方 4，0
	public static final int COLOR_BLUE = 3;// 蓝色 上方 4，8

	public static final int COIN_DEFAULT = 10;
	// 结束类型
	public static final int END_NORMAL = 0; // 正常结束
	public static final int END_OUTROOM = 1; // 退出房间结束
	public static final int END_GIVEUP = 2; // 认输结束
	public static final int END_FAIL = 4; // fail结束

	public ReMsg inRoom(final long uid, Room r, int model, boolean robit) {
		NeuroCatRoomInfo ri = new NeuroCatRoomInfo(ActivityType.NEURO_CAT.getType());
		NeuroCat uc = super.getActivity(r.get_id(), NeuroCat.class);
		if (uc.getStatus() == NeuroCat.STATUS_READY || uc.getStatus() == NeuroCat.STATUS_WAIT) {// 第一个人进来
			uc.setModel(model == Const.ACTIVITY_INVITE ? model : Const.ACTIVITY_MATCH);
			uc.setStatus(NeuroCat.STATUS_WAIT);
			if (uc.getActors().size() == 0) {
				uc.setColorRed(uid);
				addJob(r.get_id(), TIME_WAIT);
			} else if (uc.getActors().size() == 1) {
				addJob(r.get_id(), TIME_READY);
				uc.setColorBlue(uid);
			} else {
				return new ReMsg(ReCode.FAIL, ri);
			}
			uc.putActor(uid);
			uc.putUserLever(uid, LEVER_COUNT);
			super.saveActivity(r.get_id(), uc);
		} else {
			if (uid != uc.getColorRed() && uid != uc.getColorBlue()) {// 非进入状态下，除了当前用户，不能进入房间
				return new ReMsg(ReCode.FAIL, ri);
			}
		}

		if (r == null || r.getType() != ActivityType.NEURO_CAT.getType()) {
			log.error(r.get_id() + " " + r.getType());
			ri.setCode(1);
			return new ReMsg(ReCode.FAIL, ri);
		}
		NeuroCatActor ua = super.getRoomActor(r.get_id(), uid, NeuroCatActor.class);
		if (ua != null) {
			ua.setStatus(Actor.STATUS_ONLINE);
		} else {
			DBObject user = userService.findById(uid);
			if (user == null) {
				ri.setCode(1);
				return new ReMsg(ReCode.FAIL, ri);
			}
			ua = new NeuroCatActor();
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
			ua.setGame("神经猫");
			if (uc.getColorRed() == uid) {
				ua.setColor(COLOR_RED);
			} else {
				ua.setColor(COLOR_BLUE);
			}
			super.saveActivity(r.get_id(), uc);
			super.saveRoomActor(r.get_id(), ua);
		}
		Set<NeuroCatActor> actors = super.getRoomActors(r.get_id(), NeuroCatActor.class);
		changeRoomCount(r, actors, T_START);
		// 获取棋盘信息
		ri.setColorBlue(uc.getColorBlue());
		ri.setColorRed(uc.getColorRed());
		ri.setPlayer(uc.getPlayer());
		ri.setMap(uc.getMap());
		ri.setStatus(Room.ACTIVITY);
		ri.setLevers(uc.getLevers());// 杠杆数目
		ri.setPub(r.isPub());
		ri.setHasOwner(r.getUid() == 0 ? false : true);
		ri.setActors(actors);
		if (r.getStatus() == Room.ACTIVITY && uc.getActors().size() == START_COUNT
				&& uc.getStatus() == NeuroCat.STATUS_READY) {
			addJob(r.get_id(), TIME_TEMPLATE);
		}
		MapBody mb = new MapBody(SocketHandler.IN_ROOM, SocketHandler.FN_USER_ID, ua.getUid())
				.append("nickname", ua.getNickname()).append("sex", ua.getSex()).append("avatar", ua.getAvatar())
				.append("actors", actors).append("province", ua.getProvince()).append("city", ua.getCity())
				.append("role", ua.getRole()).append(SocketHandler.FN_ROOM_ID, r.get_id());
		long dt = System.currentTimeMillis();
		for (NeuroCatActor a : actors) {
			if (uid != a.getUid() && a.getStatus() == Actor.STATUS_ONLINE) {
				super.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.ROOM, mb, dt);
				break;
			}
		}
		return new ReMsg(ReCode.OK, ri);
	}

	/** 开始游戏 */
	private boolean start(Room r, NeuroCat uc) {
		Set<NeuroCatActor> actors = super.getRoomActors(r.get_id(), NeuroCatActor.class);
		if (r.getRobitCount() == r.getCount()) {
			super.closeRoom(r.get_id());
		} else if (actors.size() == START_COUNT) {
			uc.setMap(initMap.clone());
			// 双猫初始坐标
			uc.setBlueCatLbs(new int[] { 4, 8 });
			uc.setRedCatLbs(new int[] { 4, 0 });
			uc.setStatus(NeuroCat.STATUS_PLAY);
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

	/** 新回合开始 先进行检测 */
	private void nextPlay(Room r, NeuroCat uc) {
		Set<NeuroCatActor> actors = super.getRoomActors(r.get_id(), NeuroCatActor.class);
		if (uc.isPlay()) {// 上把走了 进行判断
			NeuroCatBox[][] map = uc.getMap();
			if (uc.getPlayer() == uc.getColorRed()) {// 红方走的 检测蓝方大本营有没有红猫
				for (int i = 0; i < GRID_Y; i++) { // y=8为蓝方大本营
					if (map[i][8].getCat() == COLOR_RED) {
						end(r.get_id(), uc, uc.getPlayer(), END_NORMAL);
						return;
					}
				}
			} else {// 蓝方走的 检测红方大本营有没有蓝猫
				for (int i = 0; i < GRID_Y; i++) { // y=0为红方大本营
					if (map[i][0].getCat() == COLOR_BLUE) {
						end(r.get_id(), uc, uc.getPlayer(), END_NORMAL);
						return;
					}
				}
			}
			if (uc.getPlayer() == uc.getColorRed()) {
				uc.setPlayer(uc.getColorBlue());
			} else {
				uc.setPlayer(uc.getColorRed());
			}
			play(r.get_id(), uc, actors);
		} else {
			end(r.get_id(), uc, uc.getPlayer(), END_OUTROOM);
		}
	}

	/** 推送新回合开始 */
	private void play(long roomId, NeuroCat uc, Set<NeuroCatActor> actors) {
		uc.setPlay(false);
		uc.setStatus(NeuroCat.STATUS_PLAY);
		super.saveActivity(roomId, uc);
		addJob(roomId, TIME_PLAY);
		MapBody mb = new MapBody(SocketHandler.NEUROCAT_PLAY, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_PLAY).append("map", uc.getMap()).append("player", uc.getPlayer())
				.append("actors", actors).append("levers", uc.getLevers());
		pubRoomMsg(roomId, MsgType.NEURO_CAT, mb);
	}

	/** 放置杠杆 XY 和旗杆的类型 */
	public ReMsg putLever(long uid, long roomId, int[] XY1, int leverType) {
		int[] XY2;
		int around;
		if (leverType == LEVER_VERTICLA) {// 竖杆
			XY2 = new int[] { XY1[0], XY1[1] - 1 };
			around = AROUND_LEFT;
		} else {// 横杆
			XY2 = new int[] { XY1[0] + 1, XY1[1] };
			around = AROUND_BELOW;
		}
		NeuroCat uc = super.getActivity(roomId, NeuroCat.class);
		if (uc.getStatus() != NeuroCat.STATUS_PLAY) {
			return new ReMsg(ReCode.FAIL);
		}
		if (uc.getPlayer() != uid || uc.isPlay()) {
			return new ReMsg(ReCode.NOT_YOUR_PLAYTIME);
		}
		if (uc.getUserLever(uid) == 0) {// 杆数不够了
			return new ReMsg(ReCode.LEVERCNT_ERROR);
		}
		// 初步判断 杠杆是否越界
		if (!validLbsPutLevel(XY1[0], XY1[1], around) || !validLbsPutLevel(XY2[0], XY2[1], around)) {
			MapBody mb = new MapBody(SocketHandler.NEUROCAT_PUTLEVER_ERROR, SocketHandler.FN_ROOM_ID, roomId)
					.append("code", ReCode.CANNOT_PUTLEVER.reCode());
			super.pubRoomUserMsg(roomId, uid, MsgType.NEURO_CAT, mb, System.currentTimeMillis());
			return new ReMsg(ReCode.CANNOT_PUTLEVER);
		}
		// 判断该处是否已经有杠杆
		NeuroCatBox[][] map = uc.getMap();
		NeuroCatBox xy1 = map[XY1[0]][XY1[1]];
		NeuroCatBox xy2 = map[XY2[0]][XY2[1]];
		if (Math.abs(xy1.getDataByAround(around)) == NeuroCatBox.EXIST_YES
				|| Math.abs(xy2.getDataByAround(around)) == NeuroCatBox.EXIST_YES) {
			// 已经存在数据了 即已放置杠杆
			MapBody mb = new MapBody(SocketHandler.NEUROCAT_PUTLEVER_ERROR, SocketHandler.FN_ROOM_ID, roomId)
					.append("code", ReCode.CANNOT_PUTLEVER.reCode());
			super.pubRoomUserMsg(roomId, uid, MsgType.NEURO_CAT, mb, System.currentTimeMillis());
			return new ReMsg(ReCode.PALCE_HAVELEVER);
		}
		if (around == AROUND_LEFT) {// 往左放的话 xy1下边的杆必须是首杆或者是没有杆 不能是普通杆
			if (xy1.getDataByAround(AROUND_BELOW) == NeuroCatBox.EXIST_YES) {// 普通杆不行
				MapBody mb = new MapBody(SocketHandler.NEUROCAT_PUTLEVER_ERROR, SocketHandler.FN_ROOM_ID, roomId)
						.append("code", ReCode.CANNOT_PUTLEVER.reCode());
				super.pubRoomUserMsg(roomId, uid, MsgType.NEURO_CAT, mb, System.currentTimeMillis());
				return new ReMsg(ReCode.PALCE_HAVELEVER);
			}
		} else if (around == AROUND_BELOW) {// 往下放的话 xy1右边的杆不能是首杆
			if (xy1.getDataByAround(AROUND_RIGHT) == NeuroCatBox.EXIST_STARTING_LEVER) {// 是首杆
				MapBody mb = new MapBody(SocketHandler.NEUROCAT_PUTLEVER_ERROR, SocketHandler.FN_ROOM_ID, roomId)
						.append("code", ReCode.CANNOT_PUTLEVER.reCode());
				super.pubRoomUserMsg(roomId, uid, MsgType.NEURO_CAT, mb, System.currentTimeMillis());
				return new ReMsg(ReCode.PALCE_HAVELEVER);
			}
		}
		// 给这两处 以及相邻的两处放置杠杆
		map[XY1[0]][XY1[1]].setDataByAround(around, NeuroCatBox.EXIST_STARTING_LEVER);// 首杆
		map[XY2[0]][XY2[1]].setDataByAround(around, NeuroCatBox.EXIST_YES);
		int transAround = transAround(around);
		// 目标1相邻处的盒子
		int[] lbs = getAroundLbs(XY1[0], XY1[1], around);
		if (lbs != null && validLbsPutLevel(lbs[0], lbs[1], transAround)) {
			// 不为空而且可以放置杠杆 首杆
			map[lbs[0]][lbs[1]].setDataByAround(transAround, NeuroCatBox.EXIST_STARTING_LEVER);
		}
		// 目标2相邻处的盒子
		lbs = getAroundLbs(XY2[0], XY2[1], around);
		if (lbs != null && validLbsPutLevel(lbs[0], lbs[1], transAround)) {// 不为空而且可以放置杠杆
			map[lbs[0]][lbs[1]].setDataByAround(transAround, NeuroCatBox.EXIST_YES);
		}
		// 查看是否堵死
		for (int i = 0; i < 2; i++) {
			boolean canMoveHome = false;
			int[] catLbs = null;// 猫的坐标
			int homeY = 0;// 目标大本营y轴
			if (i == 0) {// 走蓝猫 目标大本营y=0
				catLbs = uc.getBlueCatLbs();
				homeY = 0;
			} else {// 走红猫 目标大本营y=8
				catLbs = uc.getRedCatLbs();
				homeY = 8;
			}
			// 要行走的集合
			List<int[]> playLbs = new ArrayList<int[]>();
			// 缓存集合
			List<int[]> tempLbs = new ArrayList<int[]>();
			// 已经走过的坐标
			Set<String> goByLbs = new HashSet<String>();
			goByLbs.add(catLbs[0] + "," + catLbs[1]);
			playLbs.addAll(getArount4Lbs(catLbs[0], catLbs[1], map[catLbs[0]][catLbs[1]]));
			while (playLbs.size() > 0) {
				Iterator<int[]> iterator = playLbs.iterator();
				while (iterator.hasNext()) {
					int[] next = iterator.next();
					if (!goByLbs.contains(next[0] + "," + next[1])) {
						// 没有走过这个节点 把这个节点能走的路添加进去
						goByLbs.add(next[0] + "," + next[1]);
						tempLbs.addAll(getArount4Lbs(next[0], next[1], map[next[0]][next[1]]));
					}
					iterator.remove();
				}
				for (int[] nextLbs : tempLbs) {
					if (nextLbs[1] == homeY) {// 走到了对方的大本营
						canMoveHome = true;
						break;
					}
				}
				// 目前走不到大本营 继续走 加入到新的搜索队列中
				playLbs.addAll(tempLbs);
				// 清除缓存队列
				tempLbs.clear();
			}
			if (!canMoveHome) {
				MapBody mb = new MapBody(SocketHandler.NEUROCAT_PUTLEVER_ERROR, SocketHandler.FN_ROOM_ID, roomId)
						.append("code", ReCode.CANNOT_BLOCKED_ROAD.reCode());
				super.pubRoomUserMsg(roomId, uid, MsgType.NEURO_CAT, mb, System.currentTimeMillis());
				return new ReMsg(ReCode.CANNOT_BLOCKED_ROAD);
			}
		}
		uc.setPlay(true);
		uc.setMap(map);
		uc.putUserLever(uid, uc.getUserLever(uid) - 1);// 杆数目-1
		super.saveActivity(roomId, uc);
		addJob(roomId, TIME_SHOW);
		MapBody mb = new MapBody(SocketHandler.NEUROCAT_PUTLEVER, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_SHOW).append(SocketHandler.FN_USER_ID, uc.getPlayer())
				.append("XY", XY1[0] + "," + XY1[1]).append("leverType", leverType);
		pubRoomMsg(roomId, MsgType.NEURO_CAT, mb);
		return new ReMsg(ReCode.OK);
	}

	/** 移动猫 */
	public ReMsg moveCat(long uid, long roomId, int[] oldXY, int[] XY) {
		NeuroCat uc = super.getActivity(roomId, NeuroCat.class);
		if (null == uc.getMap()) {
			return new ReMsg(ReCode.FAIL);
		}
		if (uc.getPlayer() != uid || uc.isPlay()) {
			return new ReMsg(ReCode.NOT_YOUR_PLAYTIME);
		}
		// 两个坐标点
		NeuroCatBox[][] map = uc.getMap();
		NeuroCatBox oldNode = map[oldXY[0]][oldXY[1]];
		NeuroCatBox newNode = map[XY[0]][XY[1]];
		if (NeuroCatBox.EXIST_NO == oldNode.getCat()) {
			return new ReMsg(ReCode.NODE_NO_CHESS);
		}
		if (newNode.getCat() > NeuroCatBox.EXIST_NO) {// 目标节点有猫 不能走
			return new ReMsg(ReCode.COORDINATE_ERROR);
		}
		if (oldNode.getCat() == COLOR_RED) {// 要移动的是红色的猫
			if (uc.getColorRed() != uid) {
				return new ReMsg(ReCode.NOT_YOUR_CHESS);
			}
		} else {
			if (uc.getColorBlue() != uid) {
				return new ReMsg(ReCode.NOT_YOUR_CHESS);
			}
		}
		boolean canMove = false;
		// 如果是斜着走的
		if (Math.abs(XY[0] - oldXY[0]) == 1 && Math.abs(XY[1] - oldXY[1]) == 1) {
			int around1 = 0;
			int around2 = 0;
			if (XY[0] - oldXY[0] == 1) {// 新的节点在右边
				around1 = AROUND_RIGHT;
			} else {
				around1 = AROUND_LEFT;
			}
			if (XY[1] - oldXY[1] == 1) {// 新的节点在上边
				around2 = AROUND_TOP;
			} else {
				around2 = AROUND_BELOW;
			}
			int[] lbs1 = getAroundLbs(oldXY[0], oldXY[1], around1);
			int[] lbs2 = getAroundLbs(oldXY[0], oldXY[1], around2);
			NeuroCatBox box1 = null;
			NeuroCatBox box2 = null;
			if (lbs1 != null) {
				box1 = map[lbs1[0]][lbs1[1]];
			}
			if (lbs2 != null) {
				box2 = map[lbs2[0]][lbs2[1]];
			}
			if ((box1 != null && box1.getCat() > NeuroCatBox.EXIST_NO
					&& Math.abs(box1.getDataByAround(around1)) == NeuroCatBox.EXIST_YES
					&& box1.getDataByAround(around2) == NeuroCatBox.EXIST_NO)
					|| (box2 != null && box2.getCat() > NeuroCatBox.EXIST_NO
							&& Math.abs(box2.getDataByAround(around2)) == NeuroCatBox.EXIST_YES
							&& box2.getDataByAround(around1) == NeuroCatBox.EXIST_NO)) {
				canMove = true;
			} else if ((box1 != null && box1.getCat() > NeuroCatBox.EXIST_NO && (lbs1[0] == 0 || lbs1[0] == 8)
					&& box1.getDataByAround(around2) == NeuroCatBox.EXIST_NO)
					|| (box2 != null && box2.getCat() > NeuroCatBox.EXIST_NO && (lbs2[1] == 0 || lbs2[1] == 8)
							&& box2.getDataByAround(around1) == NeuroCatBox.EXIST_NO)) {
				// 猫挡在边界的特殊情况
				canMove = true;
			}
		} else if ((Math.abs(oldXY[0] - XY[0]) == 1 && Math.abs(oldXY[1] - XY[1]) == 0)
				|| (Math.abs(oldXY[0] - XY[0]) == 0 && Math.abs(oldXY[1] - XY[1]) == 1)) {
			// 如果是常规四个方向
			int around = 0;
			if (XY[0] - oldXY[0] == 1 && XY[1] - oldXY[1] == 0) {// 往右走
				around = AROUND_RIGHT;
			} else if (XY[0] - oldXY[0] == -1 && XY[1] - oldXY[1] == 0) {// 往左走
				around = AROUND_LEFT;
			} else if (XY[0] - oldXY[0] == 0 && XY[1] - oldXY[1] == 1) {// 往上走
				around = AROUND_TOP;
			} else if (XY[0] - oldXY[0] == 0 && XY[1] - oldXY[1] == -1) {// 往下走
				around = AROUND_BELOW;
			}
			if (oldNode.getDataByAround(around) == NeuroCatBox.EXIST_NO) {// 节点目标方向没有杆
				canMove = true;
			}
		} else if ((Math.abs(oldXY[0] - XY[0]) == 2 && Math.abs(oldXY[1] - XY[1]) == 0)
				|| (Math.abs(oldXY[0] - XY[0]) == 0 && Math.abs(oldXY[1] - XY[1]) == 2)) {
			// 如果是跨过猫走两格
			int around = 0;
			if (XY[0] - oldXY[0] == 2 && XY[1] - oldXY[1] == 0) {// 往右走
				around = AROUND_RIGHT;
			} else if (XY[0] - oldXY[0] == -2 && XY[1] - oldXY[1] == 0) {// 往左走
				around = AROUND_LEFT;
			} else if (XY[0] - oldXY[0] == 0 && XY[1] - oldXY[1] == 2) {// 往上走
				around = AROUND_TOP;
			} else if (XY[0] - oldXY[0] == 0 && XY[1] - oldXY[1] == -2) {// 往下走
				around = AROUND_BELOW;
			}
			if (oldNode.getDataByAround(around) == NeuroCatBox.EXIST_NO) {// 老节点目标方向没有杆
				// 旧坐标的对应方向 即和新坐标之间是只猫
				int[] lbs = getAroundLbs(oldXY[0], oldXY[1], around);
				if (lbs != null) {
					NeuroCatBox centerNode = map[lbs[0]][lbs[1]];// 中间节点
					if (centerNode.getCat() > NeuroCatBox.EXIST_NO
							&& centerNode.getDataByAround(around) == NeuroCatBox.EXIST_NO) {
						// 中间节点有猫 且对应的方向没有杆
						canMove = true;
					}
				}
			}
		}
		if (!canMove) {
			return new ReMsg(ReCode.COORDINATE_ERROR);
		}
		if (oldNode.getCat() == COLOR_RED) {// 红猫
			uc.setRedCatLbs(XY);
		} else {// 蓝猫
			uc.setBlueCatLbs(XY);
		}
		// 设置猫
		newNode.setCat(oldNode.getCat());
		oldNode.setCat(NeuroCatBox.EXIST_NO);
		uc.getMap()[oldXY[0]][oldXY[1]] = oldNode;
		uc.getMap()[XY[0]][XY[1]] = newNode;
		uc.setPlay(true);
		super.saveActivity(roomId, uc);
		addJob(roomId, TIME_SHOW);
		MapBody mb = new MapBody(SocketHandler.NEUROCAT_MOVE, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_SHOW).append("oldXY", oldXY[0] + "," + oldXY[1])
				.append("XY", XY[0] + "," + XY[1]).append("cat", newNode.getCat());
		pubRoomMsg(roomId, MsgType.NEURO_CAT, mb);
		return new ReMsg(ReCode.OK);
	}

	/** 认输 */
	public ReMsg giveupGame(long uid, long roomId) {
		NeuroCat uc = super.getActivity(roomId, NeuroCat.class);
		if (uc.getActors().contains(uid)) {// 是游戏玩家
			end(roomId, uc, uid, END_GIVEUP);
		}
		return new ReMsg(ReCode.OK);
	}

	/** 结束 */
	private void end(final long roomId, NeuroCat uc, Long uid, int endType) {
		MapBody mb = null;
		if (endType == END_FAIL) {
			mb = new MapBody(SocketHandler.NEUROCAT_END, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_LIMIT, TIME_END).append("endType", endType);
		} else {
			if (uc == null)
				uc = super.getActivity(roomId, NeuroCat.class);
			if (uc.getStatus() != NeuroCat.STATUS_PLAY) {
				return;
			}
			uc.setStatus(NeuroCat.STATUS_END);
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
				uc.setWinner(uid);
				if (uc.getColorRed() == uid) {
					uc.setLoser(uc.getColorBlue());
				} else {
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
				}
			} else {// 匹配
				if (endType == END_OUTROOM) {// 退出房间
					winReward = 0;
				}
				if (winReward > 0 && uc.getWinner() > 0) {
					gameCPScoreService.changeScore(uc.getWinner(), ActivityType.NEURO_CAT.getType(), winReward);
				}
				if (lostReward > 0 && uc.getLoser() > 0) {
					gameCPScoreService.changeScore(uc.getLoser(), ActivityType.NEURO_CAT.getType(), 0 - lostReward);
				}
			}
			gameCPScoreService.saveGameCPHistory(DboUtil.beanToDBO(uc), ActivityType.NEURO_CAT.getType());
			super.saveActivity(roomId, uc);
			super.addJob(roomId, TIME_END);// 5秒展示 结束

			mb = new MapBody(SocketHandler.NEUROCAT_END, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_LIMIT, TIME_END).append("endType", endType).append("model", uc.getModel())
					.append("winner", uc.getWinner()).append("loser", uc.getLoser());
			if (uc.getWinner() != uc.getLoser()) {
				userService.changePoint(uc.getWinner(), Point.NEURO_CAT.getType(), POINT_WIN, 0);
				userService.changePoint(uc.getLoser(), Point.NEURO_CAT.getType(), POINT_DEFAULT, 0);
				mb.append("winPoint", POINT_WIN).append("lostPoint", POINT_DEFAULT);
			}
			mb.append("winCoin", winReward).append("lostCoin", lostReward);
			if (winReward > 0) {
				coinService.addCoin(uc.getWinner(), CoinLog.NEURO_CAT, uc.get_id(), winReward, 0, "游戏胜利加币");
			}
			if (lostReward > 0) {
				coinService.forceReduce(uc.getLoser(), CoinLog.NEURO_CAT, uc.get_id(), lostReward, 0, "游戏失败扣币");
			}
			if (uc.getModel() == Const.ACTIVITY_MATCH) {// 匹配
				mb.append("winScore", winReward).append("lostScore", lostReward);
			}
		}
		super.pubRoomMsg(roomId, MsgType.NEURO_CAT, mb);
	}

	@Override
	public ReMsg outRoom(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.OUT_ROOM);
	}

	/** 退出房间 有人退出即游戏结束 */
	public ReMsg outRoom(long uid, Room r, int type) {
		if (r.getType() != ActivityType.NEURO_CAT.getType()) {
			log.error(r.get_id() + " " + r.getType());
			return new ReMsg(ReCode.FAIL);
		}
		NeuroCatActor actor = super.getRoomActor(r.get_id(), uid, NeuroCatActor.class);
		if (null == actor) {
			return new ReMsg(ReCode.ACTOR_ERROR);
		} else {
			NeuroCat uc = super.getActivity(r.get_id(), NeuroCat.class);
			if (uc.getStatus() == NeuroCat.STATUS_PLAY) {
				end(r.get_id(), uc, uid, END_OUTROOM);
			} else if (uc.getStatus() == NeuroCat.STATUS_READY || uc.getStatus() == NeuroCat.STATUS_WAIT) {
				end(r.get_id(), uc, uid, END_FAIL);
			}
			this.closeRoom(r.get_id());
		}
		return new ReMsg(ReCode.OK);
	}

	public void closeRoom(final long roomId, NeuroCat uc) {
		if (uc.getStatus() == NeuroCat.STATUS_END && uc.getActors().size() == 2) {
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

	/** 根据坐标获取到周围某一方向的坐标 */
	public int[] getAroundLbs(int x, int y, int around) {
		if (around == AROUND_TOP) {// 上
			return validLbs(x, y + 1);
		} else if (around == AROUND_BELOW) {// 下
			return validLbs(x, y - 1);
		} else if (around == AROUND_LEFT) {// 左
			return validLbs(x - 1, y);
		} else if (around == AROUND_RIGHT) {// 右
			return validLbs(x + 1, y);
		}
		return null;
	}

	/** 判断此处正常情况下能否放置杠杆 */
	private boolean validLbsPutLevel(int x, int y, int around) {
		if (x < 0 || x > 8 || y < 0 || y > 8) {// 坐标出界
			return false;
		}
		if ((x == 0) && around == AROUND_LEFT) {// x=0的左边不能放
			return false;
		} else if ((x == 8) && around == AROUND_RIGHT) {// x=8的右边不能放
			return false;
		} else if ((y == 0) && around == AROUND_BELOW) {// y=0的下边不能放
			return false;
		} else if ((y == 8) && around == AROUND_TOP) {// y=8的上边不能放
			return false;
		}
		return true;
	}

	/** 获取方向的对立面 */
	public int transAround(int around) {
		if (around == AROUND_BELOW) { // 下 变为上
			return AROUND_TOP;
		} else if (around == AROUND_TOP) {// 上变为下
			return AROUND_BELOW;
		} else if (around == AROUND_LEFT) {// 左变为右
			return AROUND_RIGHT;
		} else if (around == AROUND_RIGHT) {// 右变为左
			return AROUND_LEFT;
		}
		return around;
	}

	/** 获取周围4个方向未超出地图的 有效的 无杆的可以走的坐标 */
	public List<int[]> getArount4Lbs(int x, int y, NeuroCatBox catBox) {
		List<int[]> temp = new ArrayList<int[]>();
		int[] lbs = validLbs(x, y + 1);// 上
		if (lbs != null && catBox.getDataByAround(AROUND_TOP) == NeuroCatBox.EXIST_NO) {
			temp.add(lbs);
		}
		lbs = validLbs(x, y - 1);// 下
		if (lbs != null && catBox.getDataByAround(AROUND_BELOW) == NeuroCatBox.EXIST_NO) {
			temp.add(lbs);
		}
		lbs = validLbs(x - 1, y);// 左
		if (lbs != null && catBox.getDataByAround(AROUND_LEFT) == NeuroCatBox.EXIST_NO) {
			temp.add(lbs);
		}
		lbs = validLbs(x + 1, y);// 右
		if (lbs != null && catBox.getDataByAround(AROUND_RIGHT) == NeuroCatBox.EXIST_NO) {
			temp.add(lbs);
		}
		return temp;
	}

	/** 检验坐标是否有效 */
	private int[] validLbs(int x, int y) {
		if (0 <= x && x < GRID_X && 0 <= y && y < GRID_Y) {
			return new int[] { x, y };
		}
		return null;
	}

	static NeuroCatBox[][] initMap = new NeuroCatBox[9][9];
	static {
		for (int i = 0; i < GRID_X; i++) {
			for (int j = 0; j < GRID_Y; j++) {
				initMap[i][j] = new NeuroCatBox();
			}
		}
		initMap[4][0] = new NeuroCatBox(COLOR_RED);// 地图下方虹猫
		initMap[4][8] = new NeuroCatBox(COLOR_BLUE);// 地图上方蓝猫
	}

	public static void main(String[] args) {
		NeuroCatBox[][] ss = new NeuroCatBox[9][9];
		for (int i = 0; i < GRID_X; i++) {
			for (int j = 0; j < GRID_Y; j++) {
				ss[i][j] = new NeuroCatBox();
			}
		}
		ss[4][0] = new NeuroCatBox(COLOR_RED);// 虹猫
		ss[4][8] = new NeuroCatBox(COLOR_BLUE);// 蓝猫

		for (int j = GRID_Y - 1; j >= 0; j--) {
			for (int i = 0; i < GRID_X; i++) {
				System.out.print("  " + ss[i][j].getCat());
			}
			System.out.println("");
		}

		for (int j = GRID_Y - 1; j >= 0; j--) {
			for (int i = 0; i < GRID_X; i++) {
				System.out.print("  " + i + "," + j);
			}
			System.out.println("");
		}
	}
}
