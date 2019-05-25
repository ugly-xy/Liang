package com.zb.service.room.robit;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.common.Constant.Const;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.room.Room;
import com.zb.models.room.activity.Winmine;
import com.zb.models.room.activity.WinmineActor;
import com.zb.service.room.cp.WinmineService;

@Service
public class WinmineRobitService extends ActivityRobitService {
	static final Logger log = LoggerFactory.getLogger(WinmineRobitService.class);

	@Autowired
	WinmineService winmineService;

	private static final int ROBIT_TIME = 3000;

	@Override
	public ReMsg handle(Room r, long uid) {
		Winmine uc = super.getActivity(r.get_id(), Winmine.class);
		if (Winmine.STATUS_READY == uc.getStatus() || Winmine.STATUS_WAIT == uc.getStatus()) {
			if (null == super.getRoomActor(r.get_id(), uid, WinmineActor.class)) {// 机器人不在房间
				winmineService.inRoom(uid, r, Const.ACTIVITY_MATCH, true);
			}
			userRobitService.addJob(r.get_id(), uid, ROBIT_TIME);
		} else if (uc.getStatus() == Winmine.STATUS_START) {
			if (uc.getUserTemp(uid) == null) {// 还没有走
				int[][][] map = uc.getMap();
				List<int[]> chessClose = new ArrayList<int[]>();
				List<int[]> chessOpen = new ArrayList<int[]>();
				for (int j = WinmineService.GRID_Y - 1; j >= 0; j--) {
					for (int i = 0; i < WinmineService.GRID_X; i++) {
						if (map[i][j][1] == WinmineService.CHESS_STATUS_OPEN) {// 已经翻开棋子
							chessOpen.add(new int[] { i, j });
						} else {
							chessClose.add(new int[] { i, j });
						}
					}
				}
				if (RandomUtil.nextInt(5) > 0) {// 在已翻开的附近走
					while (chessOpen.size() > 0) {
						int[] play = chessOpen.remove(RandomUtil.nextInt(chessOpen.size()));
						List<int[]> aroundLbs = winmineService.getArount8Lbs(play[0], play[1]);
						for (int[] lbs : aroundLbs) {// 附近的八个有效坐标
							int[] chess = map[lbs[0]][lbs[1]];
							if (chess[1] == WinmineService.CHESS_STATUS_CLOSE) {// 未翻开
								int type = WinmineService.CLICK_PICK;// 默认开采
								int random = RandomUtil.nextInt(4);
								if ((chess[0] >= 0 && random < 1) || (chess[0] < 0 && random > 0)) {
									// 概率 不是雷 1/4 是雷3/4 改为标记动作
									type = WinmineService.CLICK_MINE;
								}
								winmineService.clickNode(uid, r.get_id(), lbs, type);
								userRobitService.addJob(r.get_id(), uid, robitTime());
								return null;
							}
						}
					}
				}
				winmineService.clickNode(uid, r.get_id(), chessClose.get(RandomUtil.nextInt(chessClose.size())),
						WinmineService.CLICK_PICK);
				userRobitService.addJob(r.get_id(), uid, robitTime());
			} else {
				userRobitService.addJob(r.get_id(), uid, robitTime());
			}
		}
		return null;
	}

	public int robitTime() {
		return RandomUtil.nextInt(5000) + 2000;
	}

}
