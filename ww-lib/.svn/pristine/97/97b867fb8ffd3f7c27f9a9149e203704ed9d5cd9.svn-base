package com.zb.service.room.robit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.room.Room;
import com.zb.models.room.activity.SchoolWar;
import com.zb.models.room.activity.SchoolWarActor;
import com.zb.service.room.cp.SchoolWarService;

@Service
public class SchoolWarRobitService extends ActivityRobitService {
	static final Logger log = LoggerFactory.getLogger(SchoolWarRobitService.class);

	@Autowired
	SchoolWarService schoolWarService;

	@Override
	public ReMsg handle(Room r, long uid) {
		SchoolWar uc = super.getActivity(r.get_id(), SchoolWar.class);
		if (SchoolWar.STATUS_READY == uc.getStatus() || SchoolWar.STATUS_WAIT == uc.getStatus()) {
			if (null == super.getRoomActor(r.get_id(), uid, SchoolWarActor.class)) {// 机器人不在房间
				schoolWarService.inRoom(uid, r, Const.ACTIVITY_MATCH, true);
			}
			userRobitService.addJob(r.get_id(), uid, 1000);
		} else if (SchoolWar.STATUS_START == uc.getStatus()) {
			if (uc.getTurnBack() != 0) {// 有老师回头
				if (uc.getActors().get(uid) == SchoolWarActor.A_STATUS_LAUGH) {// 嘲笑
					schoolWarService.laughing(uid, r.get_id());
					userRobitService.addJob(r.get_id(), uid, robitMinTime());
				} else if (uc.getActors().get(uid) == SchoolWarActor.A_STATUS_FIGHT) {// 出手
					if (RandomUtil.nextInt(100) < 75) {// 被抓概率
						schoolWarService.fighting(uid, r.get_id(), getWay());
					}
					userRobitService.addJob(r.get_id(), uid, 1000);
				} else {// 站起
					userRobitService.addJob(r.get_id(), uid, robitTime());
				}
			} else if (uc.getTurnBack() == 0) {// 打架
				schoolWarService.fighting(uid, r.get_id(), getWay());
				userRobitService.addJob(r.get_id(), uid, robitMinTime());
			} else {
				if (null != uc.getActors().get(uid)) {
					userRobitService.addJob(r.get_id(), uid, robitTime());
				}
			}
		}
		return null;
	}

	public int robitTime() {
		return RandomUtil.nextInt(100) + 400;
	}

	private int robitMinTime() {
		return RandomUtil.nextInt(20) + 20;
	}

	private int getWay() {
		return RandomUtil.nextInt(2) + 1;
	}
}
