package com.zb.service.room;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.Activity;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.activity.DrawSomethingActor;
import com.zb.models.room.activity.Penguin;
import com.zb.models.room.activity.SouthPenguin;
import com.zb.models.room.activity.SouthPenguinActor;
import com.zb.service.room.vo.SouthPenguinRoomInfo;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class SouthPenguinService extends RoomService {
	private static final int CALCULATE_TIME = 1;// 计算时间
	private static final int SHOW_TIME = 8;// 展示时间(可能包含倒计时时间)
	private static final int TIME_END = 4; // 倒计时
	private static final int DEF_BORDER = 24;// 默认冰块大小(最大x或者y坐标)
	private static final int PENGUIN_SIZE = 1; // 企鹅半径
	private static final int START_MIN_COUNT = 2;// 最小准备人数
	private static final int START_MAX_COUNT = 4;// 最大游戏人数
	private static final int TIME_START = 20;// 10; 倒计时20s开始游戏
	private static final int TIME_TEMPLATE = 3; // 3s
	private static final int PENGUIN_NUM = 4;// 初始企鹅数量
	private static final int LAUNCH_TIME = 20; // 发射企鹅的时间
	private static final int COIN_LOST = 50; // 默认输赢金币
	private static final int OUT_LINE_COIN = 50; // 掉线扣币
	private static final int OUT_ROOM_COIN = 100; // 退出房间扣币
	private static final int POINT_WIN = 50; // 获胜经验
	private static final int POINT_LOST = 10; // 输经验
	private static final int IN_COIN = 50; // 准入金币

	// handle
	@Override
	public ReMsg handle(Room room, long fr) {
		SouthPenguin uc = super.getActivity(room.get_id(), SouthPenguin.class);
		if (uc.getStatus() == SouthPenguin.STATUS_READY) {
			start(room, uc);
		} else if (uc.getStatus() == SouthPenguin.STATUS_LAUNCH) {// 初始发射阶段/新回合开始计算完冰块
			if (uc.getWinner() > 0) {
				settlement(room.get_id(), uc);
			} else {
				sendToAPP(0, room.get_id());
			}
		} else if (uc.getStatus() == SouthPenguin.STATUS_CALCULATE) {// 计算状态但是没有收到app回复
			sendToAPP(0, room.get_id());
		} else if (uc.getStatus() == SouthPenguin.STATUS_SHOW) {// 展示阶段
			show(room.get_id());
		} else if (uc.getStatus() == SouthPenguin.STATUS_INTONEXT) {// 展示结束
																	// 是开始新回合还是结算
			if (uc.getWinner() > 0) {
				settlement(room.get_id(), uc);
			} else {
				// 每局开始 缩小冰块
				intoNext(room.get_id());
			}
		} else if (uc.getStatus() == SouthPenguin.STATUS_END) {
			restart(room, fr);
		}
		return new ReMsg(ReCode.FAIL);
	}


	@Override
	public ReMsg inRoom(long uid, Room r,int model, boolean robit) {
		SouthPenguinRoomInfo ri = new SouthPenguinRoomInfo(ActivityType.SOUTHPENGUIN.getType());
		if (r == null) {
			ri.setCode(1);
			return new ReMsg(ReCode.FAIL, ri);
		}
		SouthPenguinActor ua = super.getRoomActor(r.get_id(), uid, SouthPenguinActor.class);
		if (ua != null) {// 恢复重连
			ua.setStatus(Actor.STATUS_ONLINE);
		} else {// 进入房间
			DBObject user = userService.findById(uid);
			if (user == null) {
				ri.setCode(1);
				return new ReMsg(ReCode.FAIL, ri);
			}
			ua = new SouthPenguinActor();
			ua.setRobit(robit);
			ua.setUid(DboUtil.getLong(user, "_id"));
			ua.setAvatar(DboUtil.getString(user, "avatar"));
			ua.setPoint(DboUtil.getInt(user, "point"));
			ua.setSex(DboUtil.getInt(user, "sex"));
			ua.setNickname(DboUtil.getString(user, "nickname"));
			ua.setProvince(StringUtils.isBlank(DboUtil.getString(user, "province")) ? "火星"
					: DboUtil.getString(user, "province"));
			if (ua.getProvince().contains("市")) {
				ua.setCity("");
			} else {
				ua.setCity(StringUtils.isBlank(DboUtil.getString(user, "city")) ? "" : DboUtil.getString(user, "city"));
			}
			if (Room.ACTIVITY == r.getStatus()) {
				ua.setRole(Actor.ROLE_VIEWER);
			}
			ua.setGame("推推乐");
		}
		super.saveRoomActor(r.get_id(), ua);
		// super.changeRoomCount(r);
		Set<SouthPenguinActor> as = super.getRoomActors(r.get_id(), SouthPenguinActor.class);
		super.changeRoomCount(r, as,T_INROOM);
		Set<SouthPenguinActor> tas = new TreeSet<>();
		for (SouthPenguinActor a : as) {
			tas.add(a);
		}
		SouthPenguin uc = super.getActivity(r.get_id(), SouthPenguin.class);
		if (r.getUid() != 0 && (null == uc.getOwner() || uc.getOwner() == 0)) {
			for (SouthPenguinActor a : tas) {
				// 设置房主
				uc.setOwner(a.getUid());
				a.setOwner(true);
				super.saveRoomActor(r.get_id(), a);
				super.saveActivity(r.get_id(), uc);
				break;
			}
		}
		ri.setPub(r.isPub());
		ri.setHasOwner(r.getUid() == 0 ? false : true);
		ri.setActors(tas);
		// 设置企鹅
		ri.setPenguins(uc.getPenguins());
		ri.setInning(uc.getInning());
		ri.setIceSize(uc.getIceSize());
		if (r.getStatus() == Room.OPEN) {
			ri.setStatus(Room.OPEN);
		} else {
			ri.setStatus(Room.ACTIVITY);
		}
		MapBody mb = new MapBody(SocketHandler.IN_ROOM, SocketHandler.FN_USER_ID, ua.getUid())
				.append("nickname", ua.getNickname()).append("sex", ua.getSex()).append("avatar", ua.getAvatar())
				.append("actors", tas).append("province", ua.getProvince()).append("city", ua.getCity())
				.append("role", ua.getRole());
		long dt = System.currentTimeMillis();
		for (Actor a : as) {
			if (uid != a.getUid()) {
				super.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.ROOM, mb, dt);
			}
		}
		return new ReMsg(ReCode.OK, ri);
	}

	// 准备
	public ReMsg ready(long uid, long roomId) {
		SouthPenguin uc = super.getActivity(roomId, SouthPenguin.class);
		if (SouthPenguin.STATUS_READY == uc.getStatus()) {// 房间在准备状态下，才能准备
			SouthPenguinActor a = super.getRoomActor(roomId, uid, SouthPenguinActor.class);
			if (a != null && Actor.A_STATUS_IN_ROOM == a.getUcStatus()) {
				if (!a.isRobit()) {
					DBObject user = super.userService.findById(uid);
					if (user == null || DboUtil.tryGetInt(user, "coin") < IN_COIN) {
						return new ReMsg(ReCode.COIN_BALANCE_LOW);
					}
				}
				a.setUcStatus(Actor.A_STATUS_READY);
				super.saveRoomActor(roomId, a);
				uc.putActor(uid);
				super.saveActivity(roomId, uc);

				Set<SouthPenguinActor> actors = getRoomActors(roomId, SouthPenguinActor.class);
				MapBody mb = new MapBody(SocketHandler.ACTIVITY_READY, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", uc.get_id());
				boolean start = false;
				int readyCount = uc.getActors().size();
				long total = actors.size();

				if (readyCount >= START_MAX_COUNT) {// 准备人数超过最大游戏数，立即开始
					start = true;
				} else if (readyCount >= START_MIN_COUNT) {// 准备人数超过最小人数
					if (readyCount == total) {// 全体准备
						start = true;
					} else {// 还有未准备用户
						addJob(roomId, TIME_START);// 增加系统调用倒计时
						mb.append(SocketHandler.FN_LIMIT, TIME_START);
					}
				}
				super.pubRoomMsg(roomId, MsgType.SOUTHPENGUIN, mb);// 发送用户准备通知
				if (start) {
					this.addJob(roomId, TIME_TEMPLATE);
				}
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	// 开始
	private boolean start(Room r, SouthPenguin uc) {
		Set<SouthPenguinActor> actors = super.getRoomActors(r.get_id(), SouthPenguinActor.class);
		int readyCount = uc.getActors().size();
		boolean start = false;
		readyCount = uc.getActors().size();
		if (readyCount >= START_MIN_COUNT) {// 准备人数超过最小人数
			start = true;
		} else {
			super.addJob(r.get_id(), TIME_START);
		}
		if (start) {
			r.setStatus(Room.ACTIVITY);
			r.setActivityDate(System.currentTimeMillis());
			this.updateRoom(r,T_START);
			Set<Long> kickIds = new TreeSet<Long>();
			uc.clearActors();
			int kickCount = readyCount - START_MAX_COUNT;
			List<Long> readyActors = null;
			if (kickCount > 0) {
				readyActors = new ArrayList<>();
			}
			for (SouthPenguinActor actor : actors) {
				if (Actor.A_STATUS_READY == actor.getUcStatus()) {
					actor.setUcStatus(SouthPenguinActor.A_STATUS_START);
					saveRoomActor(r.get_id(), actor);
					// 放置游戏参与者
					uc.putActor(actor.getUid());
					if (kickCount > 0) {
						if (actor.isRobit()) {
							kickIds.add(actor.getUid());
							kickCount--;
						}
					}
					if (kickCount > 0 && !actor.isRobit()) {
						readyActors.add(actor.getUid());
					}
				} else {
					kickIds.add(actor.getUid());
				}
			}
			if (kickCount > 0) {
				kickIds.addAll(readyActors.subList(START_MAX_COUNT, START_MAX_COUNT + kickCount));
			}
			for (Long id : kickIds) {
				super.delRoomActor(r.get_id(), id);
				uc.removeActor(id);
			}
			// 计算所有企鹅位置 并进行推送
			pushPenguin(r, uc, actors, kickIds);
		}
		return start;
	}

	// 坐标  以中心为原点(0,0) 正方形边长为  DEF_BORDER*2    有正有负　　　　　 假设边长48最大坐标24企鹅半径1 则初始坐标－23到23
	private Integer[] getPlace(Set<Integer[]> places) {
		//FIXME 初始化坐标
		int x = RandomUtil.nextInt((DEF_BORDER - PENGUIN_SIZE)*2)-(DEF_BORDER - PENGUIN_SIZE);
		int y = RandomUtil.nextInt((DEF_BORDER - PENGUIN_SIZE)*2)-(DEF_BORDER - PENGUIN_SIZE);
		Integer[] place = new Integer[] { x, y };
		if (places.contains(place)) {
			return getPlace(places);
		}
		Iterator<Integer[]> it = places.iterator();
		while (it.hasNext()) {
			Integer[] inPlace = it.next();
			// 如果两圆相交
			if (((place[0] - inPlace[0]) * (place[0] - inPlace[0])
					+ (place[1] - inPlace[1]) * (place[1] - inPlace[1])) < (4 * PENGUIN_SIZE * PENGUIN_SIZE)) {
				return getPlace(places);
			}
		}
		return place;
	}

	// 颜色
	private Integer getColor(Set<Integer> colors) {
		// 随机十种颜色
		int color = RandomUtil.nextInt(10) + 1;
		if (colors.contains(color)) {
			return getColor(colors);
		}
		return color;
	}

	// 推送企鹅位置
	public void pushPenguin(Room r, SouthPenguin uc, Set<SouthPenguinActor> actors, Set<Long> kickIds) {
		if (actors == null) {
			actors = super.getRoomActors(r.get_id(), SouthPenguinActor.class);
		}
		// 清空企鹅
		uc.clearPenguins();
		// 已进入游戏用户
		Set<SouthPenguinActor> acs = new TreeSet<>();
		for (SouthPenguinActor a : actors) {
			if (uc.getActors().contains(a.getUid())) {
				acs.add(a);
			}
		}
		int No = 1;
		List<Penguin> penguins = new ArrayList<Penguin>();
		for (SouthPenguinActor actor : acs) {
			// 设置企鹅颜色
			Integer color = getColor(uc.getColors());
			uc.getColors().add(color);
			actor.setColor(color);
			for (int i = 0; i < PENGUIN_NUM; i++) {
				Integer[] place = getPlace(uc.getPlaces());// 获得初始坐标
				uc.getPlaces().add(place);// 存储初始坐标
				penguins.add(new Penguin(No, actor.getUid(), color, place[0], place[1]));
			}
			// 设置为待发射状态
			actor.setUcStatus(SouthPenguinActor.A_LAUNCH_INIT);
			uc.putActor(actor.getUid());
			super.saveRoomActor(r.get_id(), actor);// 存储房间内用户信息
			No++;
		}
		uc.setPenguins(penguins);
		uc.setStatus(SouthPenguin.STATUS_LAUNCH);// 发射阶段
		uc.setInning(uc.getInning() + 1);
		long curTime = System.currentTimeMillis();
		MapBody mb2 = new MapBody(SocketHandler.PENGUIN_INIT, SocketHandler.FN_ROOM_ID, r.get_id())
				.append(SocketHandler.FN_LIMIT, LAUNCH_TIME).append("inning", uc.getInning()).append("actors", acs)
				.append("kicks", kickIds).append("penguins", uc.getPenguins()).append("iceSize", uc.getIceSize());
		for (SouthPenguinActor a : acs) {// 发送房间内
			if (a.getStatus() == DrawSomethingActor.STATUS_ONLINE) {
				this.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.SOUTHPENGUIN, mb2, curTime);
			}
		}
		addJob(r.get_id(), LAUNCH_TIME);// 发射时间
		super.saveActivity(r.get_id(), uc);
	}

	// 用户确定发送企鹅 替换企鹅信息　　　　	不确定前端提交数据格式
	public ReMsg launch(long uid, long roomId, List<Penguin> penguins) {
		//FIXME 前端提交发射信息
		SouthPenguin uc = super.getActivity(roomId, SouthPenguin.class);
		if (uc.getStatus() != SouthPenguin.STATUS_LAUNCH) {// 只有发射阶段才能上传坐标
			return new ReMsg(ReCode.FAIL);
		}
		SouthPenguinActor actor = super.getRoomActor(roomId, uid, SouthPenguinActor.class);
		// 如果用户处于待发射状态
		if (actor.getUcStatus() == SouthPenguinActor.A_LAUNCH_INIT) {
			// 替换企鹅信息
			for (Penguin penguin1 : uc.getPenguins()) {
				for (Penguin penguin2 : penguins) {
					if (penguin2.getUid() - actor.getUid() == 0 && penguin1.getNo() == penguin2.getNo()) {
						//FIXME 前端提交的信息
						penguin1 = penguin2;
					}
				}
			}
			actor.setStatus(SouthPenguinActor.A_LAUNCH_END);
			// 发送提醒 xxx已经确定了企鹅的方向
			MapBody mb = new MapBody(SocketHandler.LAUNCH_END, SocketHandler.FN_USER_ID, uid)
					.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", uc.get_id());
			// 设置已发射企鹅数目
			uc.addConfirmNum(penguins.size());
			super.saveRoomActor(roomId, actor);
			super.saveActivity(roomId, uc);
			super.pubRoomMsg(roomId, MsgType.SOUTHPENGUIN, mb);// 发送用户已发射企鹅通知
			if (uc.getConfirmNum() == uc.getPenguins().size()) {
				// 存活企鹅发射完毕 进入计算
				mb = new MapBody(SocketHandler.LAUNCH_END, SocketHandler.FN_USER_ID, 0)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", uc.get_id())
						.append(SocketHandler.FN_LIMIT, TIME_TEMPLATE);
				super.pubRoomMsg(roomId, MsgType.SOUTHPENGUIN, mb);
				// 发送全部用户已发射企鹅通知 并增加倒计时3s
				sendToAPP(uid, roomId);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	/**
	 * 20s过后 或者是 全部用户都进入了发射完毕 即房间进入发射完毕状态 请求app计算
	 * 
	 * @param uid
	 * @param roomId
	 */
	// 挑选一个用户 发送坐标 请求计算碰撞
	public void sendToAPP(long uid, long roomId) {
		SouthPenguin uc = super.getActivity(roomId, SouthPenguin.class);
		if (uid == 0) {
			List<Long> actors = new ArrayList<Long>();
			Set<SouthPenguinActor> uas = super.getRoomActors(roomId, SouthPenguinActor.class);
			for (SouthPenguinActor actor : uas) {
				if (actor.getStatus() == SouthPenguinActor.STATUS_ONLINE) {
					actors.add(actor.getUid());
				}
			}
			if (actors.size() == 0) {// 没有一个在线的
				closeRoom(roomId);
				return;
			}
			for (;;) {
				int index = RandomUtil.nextInt(actors.size());
				if (actors.get(index) - uc.getCalculator() != 0) {
					uid = actors.get(index);
					break;
				}
			}

		}
		// 计算
		uc.setStatus(SouthPenguin.STATUS_CALCULATE);
		uc.setCalculator(uid);
		saveActivity(roomId, uc);
		MapBody mb = new MapBody(SocketHandler.CALCULATEPALCE, SocketHandler.FN_ROOM_ID, roomId)
				.append("actId", uc.get_id()).append("penguins", uc.getPenguins());
		Long curTime = System.currentTimeMillis();
		this.pubRoomUserMsg(roomId, uid, MsgType.SOUTHPENGUIN, mb, curTime);
		addJob(roomId, CALCULATE_TIME);// 计算时间1s
	}

	// app上传结果　　　	不确定前端提交数据格式
	public ReMsg calculatePalce(long uid, long roomId, List<Penguin> penguins) {
		//FIXME  前端提交计算 
		SouthPenguin uc = super.getActivity(roomId, SouthPenguin.class);
		if (uid == 0 || uc.getCalculator() - uid != 0) {
			return new ReMsg(ReCode.FAIL);
		}
		uc.removeCalculator();
		uc.setPenguins(penguins);
		uc.setStatus(SouthPenguin.STATUS_SHOW);
		super.saveActivity(roomId, uc);
		return new ReMsg(ReCode.OK);
	}

	// 进行计算并展示
	public ReMsg show(long roomId) {
		SouthPenguin uc = super.getActivity(roomId, SouthPenguin.class);
		List<Penguin> penguins = uc.getPenguins();
		uc.setStatus(SouthPenguin.STATUS_INTONEXT);
		long winner = 0;
		MapBody mb = new MapBody(SocketHandler.PENGUIN_SHOW, SocketHandler.FN_ROOM_ID, roomId)
				.append("penguins", penguins).append("iceSize", uc.getIceSize());
		// 查看结果 并判断哪些企鹅出界(直接出界)
		int border =uc.getIceSize();
		// 如果碰撞后坐标的x或者y >=0 或者>=iceSize 出界
		Iterator<Penguin> iterator = penguins.iterator();
		List<Penguin> outPenguins = new ArrayList<Penguin>();
		while (iterator.hasNext()) {
			Penguin penguin = iterator.next();
			if (Math.abs(penguin.getEx()) >= border || Math.abs(penguin.getEy()) >= border) {
				penguin.setStatus(Penguin.DEATH);
				outPenguins.add(penguin);
				iterator.remove();
			} 
			penguin.setX(penguin.getEx());
			penguin.setY(penguin.getEy());
			penguin.setMx(null);
			penguin.setMy(null);
			penguin.setEx(null);
			penguin.setEy(null);
		}
		if (penguins.size() == 0) {
			winner = outPenguins.get(outPenguins.size() - 1).getUid();
		}
		mb.append("out", outPenguins);
		// 推送消息
		super.pubRoomMsg(roomId, MsgType.SOUTHPENGUIN, mb);
		if (winner > 0) {
			uc.setWinner(winner);
		}
		addJob(roomId, SHOW_TIME);// 展示时间
		super.saveActivity(roomId, uc);
		return new ReMsg(ReCode.OK);
	}

	/**
	 * 碰撞出局结束之后 下一局结束之前 进行缩小冰块 出局企鹅的判断
	 * 
	 * @param room
	 * @param uc
	 */
	public ReMsg intoNext(long roomId) {
		long winner = 0;
		SouthPenguin uc = super.getActivity(roomId, SouthPenguin.class);
		List<Penguin> penguins = uc.getPenguins();
		MapBody mb = new MapBody(SocketHandler.PENGUIN_INIT, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, LAUNCH_TIME).append("penguins", penguins)
				.append("iceSize", uc.getIceSize());
		uc.setIceSize(uc.getIceSize() - PENGUIN_SIZE);
		uc.setInning(uc.getInning() + 1);
		// 初始化已确定企鹅数
		uc.setConfirmNum(0);
		uc.setStatus(SouthPenguin.STATUS_LAUNCH);// 等待发射
		// 边界
		int border =uc.getIceSize();
		// 如果碰撞后坐标的x或者y >=0 或者>=iceSize 出界
		Iterator<Penguin> iterator = penguins.iterator();
		List<Penguin> outPenguins = new ArrayList<Penguin>();
		while (iterator.hasNext()) {
			Penguin penguin = iterator.next();
			if (Math.abs(penguin.getEx()) >= border || Math.abs(penguin.getEy()) >= border) {
				penguin.setStatus(Penguin.DEATH);
				outPenguins.add(penguin);
				iterator.remove();
			}
		}
		if (penguins.size() == 0) {
			winner = outPenguins.get(outPenguins.size() - 1).getUid();
		}
		mb.append("out", outPenguins);
		// 推送消息
		super.pubRoomMsg(roomId, MsgType.SOUTHPENGUIN, mb);
		if (winner > 0) {
			uc.setWinner(winner);
			addJob(roomId, TIME_END);// 几秒后进行结算
		} else {
			addJob(roomId, LAUNCH_TIME+TIME_TEMPLATE);//  缩小冰块的展示时间+发射时间
		}
		super.saveActivity(roomId, uc);
		return new ReMsg(ReCode.OK);
	}

	// 结束 进行结算
	public void settlement(long roomId, SouthPenguin uc) {
		if (uc == null) {
			uc = super.getActivity(roomId, SouthPenguin.class);
		}
		long winner = uc.getWinner();
		if (winner < 1) {
			return;
		}
		uc.setStatus(SouthPenguin.STATUS_END);
		Set<SouthPenguinActor> uas = super.getRoomActors(roomId, SouthPenguinActor.class);
		Set<SouthPenguinActor> actors = new HashSet<SouthPenguinActor>();// 游戏参与者
		for (SouthPenguinActor dsa : uas) {
			if (dsa.getRole() == SouthPenguinActor.ROLE_EXECUTOR) {
				actors.add(dsa);
			}
		}
		super.saveActivity(roomId, uc);
		int winCoin = (uc.getActors().size() - 1) * COIN_LOST;
		MapBody mb = new MapBody(SocketHandler.PENGUIN_END, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_END).append("actors", actors).append("winner", winner)
				.append("winCoin", winCoin).append("losCoin", COIN_LOST).append("iceSize", uc.getIceSize());
		;
		long dt = System.currentTimeMillis();
		for (SouthPenguinActor dsa : uas) {
			if (dsa.getRole() == SouthPenguinActor.ROLE_EXECUTOR) {
				if (dsa.getUid() - winner == 0) {
					coinService.addCoin(dsa.getUid(), CoinLog.SOUTHPENGUIN, uc.get_id(), winCoin, 0, "游戏胜利加币");
					userService.changePoint(dsa.getUid(), Point.SOUTHPENGUIN.getType(), POINT_WIN, 0);
				} else {
					coinService.forceReduce(dsa.getUid(), CoinLog.SOUTHPENGUIN, uc.get_id(), COIN_LOST, 0, "游戏失败扣币");
					userService.changePoint(dsa.getUid(), Point.SOUTHPENGUIN.getType(), POINT_LOST, 0);
				}
				super.pubRoomUserMsg(roomId, dsa.getUid(), MsgType.SOUTHPENGUIN, mb, dt);
				// 如果不在线
				if (dsa.getStatus() != DrawSomethingActor.STATUS_ONLINE) {
					if (dsa.getStatus() == DrawSomethingActor.STATUS_OFFLINE) {// 掉线扣50
						if (!dsa.isReduceCoin()) {
							coinService.forceReduce(dsa.getUid(), CoinLog.SOUTHPENGUIN, uc.get_id(), OUT_LINE_COIN, 0,
									"未完成游戏掉线惩罚扣币");
							dsa.setReduceCoin(true);
						}
						super.saveRoomActor(roomId, dsa);
					} else if (dsa.getStatus() == DrawSomethingActor.STATUS_OUT_ROOM) {// 退出房间扣100
						if (!dsa.isReduceCoin()) {
							coinService.forceReduce(dsa.getUid(), CoinLog.SOUTHPENGUIN, uc.get_id(), OUT_ROOM_COIN, 0,
									"未完成游戏退出房间惩罚扣币");
							dsa.setReduceCoin(true);
						}
						super.saveRoomActor(roomId, dsa);
					}
				}
			}
		}
		uc.setUpdateTime(System.currentTimeMillis());
		super.getMongoTemplate().save(uc);
		super.addJob(roomId, TIME_END);//4s之后倒计时重启
	}

	// 退出
	@Override
	public ReMsg outRoom(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.OUT_ROOM);
	}

	// 断开连接
	@Override
	public ReMsg disconnectRoom(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.DISCONNECT);
	}

	// 踢出
	@Override
	public ReMsg kick(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.KICK);
	}

//	@Deprecated
//	@Override
//	public ReMsg userFastCard(long uid, Room r) {
//		// SouthPenguin uc = super.getActivity(r.get_id(), SouthPenguin.class);
//		// if (uc.getStatus() == SouthPenguin.STATUS_PUNISH) {
//		// ReCode rc = fastCard(uid, uc.get_id());
//		// if (rc.reCode() == ReCode.OK.reCode()) {
//		// this.restart(r, uid);
//		// }
//		// return new ReMsg(rc);
//		// }
//		// return new ReMsg(ReCode.CAN_NOT_USE_FAST_CARD);
//		return new ReMsg(ReCode.FAIL);
//	}

	// 退出房间
	public ReMsg outRoom(long uid, Room r, int type) {
		if (Room.ACTIVITY == r.getStatus() && SocketHandler.KICK == type) {
			return new ReMsg(ReCode.KICK_ERROR);
		}
		SouthPenguinActor actor = super.getRoomActor(r.get_id(), uid, SouthPenguinActor.class);
		SouthPenguin uc = super.getActivity(r.get_id(), SouthPenguin.class);
		if (null == actor) {
			return new ReMsg(ReCode.ACTOR_ERROR);
		} else {
			if (Room.OPEN == r.getStatus()) {
				if (SouthPenguinActor.A_STATUS_READY == actor.getUcStatus()
						|| SouthPenguinActor.A_STATUS_IN_ROOM == actor.getUcStatus()) {
					if (SocketHandler.KICK == type) {
						try {
							MapBody mb = new MapBody(type)
									.append("nickname",
											super.getRoomActor(r.get_id(),
													getActivity(r.get_id(), SouthPenguin.class).getOwner())
															.getNickname());
							super.pubUserMsg(uid, MsgType.PROMPT, mb);
						} catch (Exception e) {
							log.error("发送踢人信息", e);
						}
					}
				}
				if (SouthPenguinActor.A_STATUS_READY == actor.getUcStatus()) {
					// Dice uc = super.getActivity(r.get_id(), Dice.class);
					uc.removeActor(uid);
					super.saveActivity(r.get_id(), uc);
				}
				this.delRoomActor(r.get_id(), uid);
				Set<SouthPenguinActor> as = super.getRoomActors(r.get_id(), SouthPenguinActor.class);
				super.changeRoomCount(r, as,T_OUTROOM);
			} else if (Actor.ROLE_VIEWER == actor.getRole()) {
				this.delRoomActor(r.get_id(), actor.getUid());
				Set<SouthPenguinActor> as = super.getRoomActors(r.get_id(), SouthPenguinActor.class);
				super.changeRoomCount(r, as,T_OUTROOM);
			} else {
				if (SocketHandler.DISCONNECT != type) {
					actor.setStatus(Actor.STATUS_OUT_ROOM);
				} else {
					actor.setStatus(Actor.STATUS_OFFLINE);
				}
				this.saveRoomActor(r.get_id(), actor);
			}
			MapBody mb = new MapBody(type, SocketHandler.FN_USER_ID, actor.getUid())
					.append("nickname", actor.getNickname()).append(SocketHandler.FN_ROOM_ID, r.get_id());
			this.pubRoomMsg(r.get_id(), MsgType.ROOM, mb);
			if (r.getUid() != 0 && SocketHandler.DISCONNECT != type) {
				Long owner = uc.getOwner();
				if (uid == owner) {
					pubOwnerChange(uid, r);
				}
			}
		}
		int outCount = 0;
		Set<SouthPenguinActor> actors = super.getRoomActors(r.get_id(), SouthPenguinActor.class);
		for (SouthPenguinActor ua : actors) {
			if (ua.getStatus() != SouthPenguinActor.STATUS_ONLINE) {
				outCount++;
			}
		}
		if (outCount == actors.size()) {
			closeRoom(r.get_id());
		}
		return new ReMsg(ReCode.OK);
	}

	// 获取房间内用户状态
	@Override
	public ReMsg getActorStatus(long uid, Room r) {
		SouthPenguinActor uca = super.getRoomActor(r.get_id(), uid, SouthPenguinActor.class);
		if (uca == null) {
			return new ReMsg(ReCode.OK, 1);
		} else {
			return new ReMsg(ReCode.OK, 2);
		}
	}

	// 能否进入房间
	@Override
	public ReMsg canInRoom(int type, long actId, long roomId) {
		SouthPenguin uc = super.getActivity(roomId, SouthPenguin.class);
		if (actId == uc.get_id()) {
			if (uc.getStatus() > Activity.STATUS_READY && uc.getStatus() < SouthPenguin.STATUS_END)
				return new ReMsg(ReCode.OK, 1);
		}
		return new ReMsg(ReCode.OK, 2);
	}

	// 重启
	public boolean restart(Room r, long fr) {
		Set<SouthPenguinActor> actors = getRoomActors(r.get_id(), SouthPenguinActor.class);
		long owner = 0L;
		if (r.getUid() != 0) {
			SouthPenguin uc = super.getActivity(r.get_id(), SouthPenguin.class);
			owner = uc.getOwner();
		}
		super.delActivity(r.get_id());
		Set<SouthPenguinActor> initActors = new TreeSet<SouthPenguinActor>();
		int robitCount = 0;
		for (SouthPenguinActor actor : actors) {
			if (Actor.STATUS_ONLINE != actor.getStatus()) {
				super.delRoomActor(r.get_id(), actor.getUid());
				if (r.getUid() != 0 && actor.getUid() == owner) {
					pubOwnerChange(owner, r);
				}
			} else {
				actor.setRole(SouthPenguinActor.ROLE_EXECUTOR);
				actor.setStatus(SouthPenguinActor.STATUS_ONLINE);
				actor.setUcStatus(SouthPenguinActor.A_STATUS_IN_ROOM);
				initActors.add(actor);
				super.saveRoomActor(r.get_id(), actor);
				if (actor.isRobit()) {
					robitCount++;
				}
			}
		}
		r.setStatus(Room.OPEN);
		r.setCount(initActors.size());
		r.setRobitCount(robitCount);
		super.updateRoom(r,T_RESTART);

		if (r.getUid() != 0) {
			SouthPenguin uc = super.getActivity(r.get_id(), SouthPenguin.class);
			uc.setOwner(owner);
			super.saveActivity(r.get_id(), uc);
		}
		MapBody mb = new MapBody(SocketHandler.ROOM_RESTART, SocketHandler.FN_ROOM_ID, r.get_id()).append("actors",
				initActors);
		long dt = System.currentTimeMillis();
		for (Actor actor : initActors) {
			super.pubRoomUserMsg(r.get_id(), actor.getUid(), MsgType.ROOM, mb, dt);
		}
		return true;
	}

	// 更换房主
	private void pubOwnerChange(long uid, Room r) {
		Set<SouthPenguinActor> as = super.getRoomActors(r.get_id(), SouthPenguinActor.class);
		Set<SouthPenguinActor> tas = new TreeSet<>();
		for (SouthPenguinActor a : as) {
			tas.add(a);
		}
		SouthPenguin uc = super.getActivity(r.get_id(), SouthPenguin.class);
		for (SouthPenguinActor a : tas) {

			uc.setOwner(a.getUid());
			a.setOwner(true);
			super.saveRoomActor(r.get_id(), a);
			super.saveActivity(r.get_id(), uc);
			break;
		}
		MapBody mb = new MapBody(SocketHandler.CHANGE_OWNER).append("lastOwner", uid).append("curOwner", uc.getOwner());
		super.pubRoomMsg(r.get_id(), MsgType.ROOM, mb);
	}

	// 关闭房间
	public void closeRoom(long roomId) {
		// log.error("===================begin closeRoom service");
		log.error("CLOSE room:" + roomId);
		Room room = super.getRoom(roomId);
		super.delJob(roomId);
		super.delActivity(roomId);
		super.delRoomAllActors(roomId);
		room.setUid(0L);
		room.setPub(true);
		room.setCount(0);
		room.setStatus(Room.OPEN);
		room.setUpdateTime(System.currentTimeMillis());
		super.updateRoom(room,T_CLOSE);
		MapBody mb = new MapBody(SocketHandler.ROOM_CLOSE, SocketHandler.FN_ROOM_ID, room.get_id());
		super.pubRoomMsg(room.get_id(), MsgType.ROOM, mb);
		// log.error("===================end closeRoom service");
	}

}
