package com.zb.service.room.robit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.common.redis.RK;
import com.zb.common.utils.RandomUtil;
import com.zb.service.BaseService;
import com.zb.service.UserService;

@Service
public class UserRobitService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(UserRobitService.class);
	@Autowired
	UserService userService;


	long initTime = 0;

	public Long getRobitId() {
		return userService.getRobitCId();
	}

//	public void addRobitId(Long uid) {
//		super.getRedisTemplate().opsForList().rightPush(RK.userRobitList(), "" + uid);
//	}

	public void addJob(Long roomId, Long uid, long time) {
		super.getRedisTemplate().opsForZSet().add(RK.roomUserRobitJob(), roomId + "-" + uid,
				System.currentTimeMillis() + time);
	}

	public long getTime() {
		return RandomUtil.nextInt(5000) + 800;
	}
	
	public long getMatchTime() {
		return RandomUtil.nextInt(1000) + 100;
	}
}
