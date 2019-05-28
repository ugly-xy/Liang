package com.zb.web.view.admin;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.crypto.MDUtil;
import com.zb.common.http.HttpClientUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.core.web.ReMsg;
import com.zb.service.UserService;

@Controller
@RequestMapping("/admin")
public class ChangeRobotCtl {

	static final Logger LOG = LoggerFactory.getLogger(ChangeRobotCtl.class);

	@Autowired
	UserService userService;

	public List<DBObject> query4downloadRobit(long index, int type) {
		List<DBObject> list = userService.query4downloadRobit(index, type);
		return list.stream().sorted((e1, e2) -> DboUtil.getInt(e1, "_id") - DboUtil.getInt(e2, "_id"))
				.collect(Collectors.toList());
	}

	@ResponseBody
	@RequestMapping("/changeRobot/run/{type}")
	public ReMsg savePic(@PathVariable int type) {
		long rid = DboUtil.getLong(userService.queryFirstRobot(type), "_id");
		changeByIndex(rid, type);
		LOG.error("：：：：：：：跑完了");
		return new ReMsg(ReCode.OK);
	}

	void changeByIndex(long index, int type) {
		List<DBObject> list = query4downloadRobit(index, type);
		if (null == list || list.isEmpty())
			return;
		for (DBObject t : list) {
			LOG.error("：：：：：：：跑一个");
			boolean isEmpty;
			try {
				isEmpty = userService.checkEmpty(DboUtil.getString(t, "avatar"));
			} catch (IOException e) {
				isEmpty = true;
				e.printStackTrace();
			}
			try {
				if (isEmpty) {
					LOG.error("：：：：：：：换一个");
					userService.changeRobitAvatar(DboUtil.getLong(t, "_id"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		long nextIndex = DboUtil.getLong(list.get(list.size() - 1), "_id");
		if (nextIndex != index) {
			changeByIndex(nextIndex, type);
		}
	}

	@RequestMapping("/changeRobot/index")
	public ModelAndView changeRobot() {
		return new ModelAndView("/admin/robot/changeRobot");
	}

	public static void main(String[] args) throws IOException {
		for (int i = 0; i < 10; i++) {
			String ret = HttpClientUtil.get("http://imgzb.oss-cn-beijing.aliyuncs.com/logo/b_logo_b_180.png", null,
					HttpClientUtil.UTF8);
			System.out.println(MDUtil.MD5.digest2HEX(ret));
			// d41d8cd98f00b204e9800998ecf8427e
			// 5b7f5f81049ae612462fd65b229d572d
			// 21777db24581f7382c51fe8a64d1b8ce
		}
	}
}
