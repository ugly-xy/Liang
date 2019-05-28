package com.zb.service;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.RegexUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.MobileContacts;
import com.zb.models.People;
import com.zb.models.User;
import com.zb.models.user.Relationship;
import com.zb.view.MobileVO;
import com.zb.view.MobileVO.Mobile;

@Service
public class MobileContactsService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(MobileContactsService.class);
	@Autowired
	UserService userService;
	@Autowired
	RelationshipService relationshipService;

	/*
	 * 上传联系人列表 (前台调用 上传通讯录) list=[一号=123456789,
	 * 礼物=144777,去=22332222131313133131]
	 */
	public ReMsg upLoadMobileContacts(HttpServletRequest req, String string) {
		Long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		// 解析数据
		String[] strings = getStrings(string);
		if (strings.length == 0) {
			return new ReMsg(ReCode.OK);
		}
		DBObject dbo = findOne(uid);
		if (null == dbo) {
			return upload(strings, uid);
		}
		return coverUpload(strings, dbo);
	}

	/*
	 * 根据用户id查询到 该用户通讯录中的玩家 (前台 推送调用)
	 */
	public ReMsg pushMobileContacts(HttpServletRequest req, Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		MobileContacts mc = query(uid);
		if (null == mc) {
			return new ReMsg(ReCode.OK);
		}
		size = super.getSize(size);
		page = super.getPage(page);
		MobileVO mv = getMobileVO(mc);
		return new ReMsg(ReCode.OK, size, page, getMobileVO(mv, page, size));
	}

	// // 忽略用户 以后不推送好友推荐(前台推荐好友时忽略按钮)
	// public ReMsg ignoreMobileContacts(String phone) {
	// long uid = super.getUid();
	// if (uid < 1) {
	// return new ReMsg(ReCode.NOT_AUTHORIZED);
	// }
	// if (phone.length() != 11) {
	// return new ReMsg(ReCode.PARAMETER_ERR);
	// }
	// DBObject uo = new BasicDBObject("$set", new
	// BasicDBObject("peoples.$.isIgnore", MobileContacts.ignore_yes));
	// getC(DocName.MOBILE_CONTACTS).update(new BasicDBObject("_id",
	// uid).append("peoples.rPhone", phone), uo, false,
	// true);
	// return new ReMsg(ReCode.OK);
	// }

	// // 当用户绑定手机号或者是更新手机号或者注册时 需要同步更新到通讯录库(后台用户手机号发生变化时)
	// public void changeMobileContacts(long rid, String phone) {
	// DBObject uo = new BasicDBObject("$set", new
	// BasicDBObject("peoples.$.rPhone", phone));
	// getC(DocName.MOBILE_CONTACTS).update(new BasicDBObject("peoples.rid",
	// rid), uo, false, true);
	// }
	//
	// // 当好友关系发生改变时 通讯录库也要发生改变(后台调用) 保证数据同步
	// public void changeRelationShip(long uid, long rid, int relationShip) {
	// DBObject uo = new BasicDBObject("$set", new BasicDBObject("peoples.$.r",
	// relationShip));
	// getC(DocName.MOBILE_CONTACTS).findAndModify(new BasicDBObject("_id",
	// uid).append("peoples.rid", rid), uo);
	//
	// }

	// 解析
	private String[] getStrings(String string) {
		char fir = string.charAt(0);
		// 去除左右括号
		if ("[".equals("" + fir + "")) {
			String split = string.substring(1);
			string = split.substring(0, split.length() - 1).trim();
		}
		return string.split(",");
	}

	// 上传通讯录
	private ReMsg upload(String[] strings, long uid) {
		MobileContacts mc = new MobileContacts(uid);
		List<People> peoples = mc.getPeoples();
		List<String> has = new ArrayList<String>();
		String[] ss = null;
		String name = null;
		String phone = null;
		for (int i = 0; i < strings.length; i++) {
			// 取到姓名和电话
			ss = strings[i].split("=");
			if (ss.length != 2) {
				continue;
			}
			try {
				name = ss[0].trim();
				phone = ss[1].trim();
			} catch (Exception e) {
				continue;
			}
			if (StringUtils.isBlank(phone) || StringUtils.isBlank(name)) {
				continue;
			}
			phone = phone.replace("-", "");
			phone = phone.replace("(", "");
			phone = phone.replace(")", "");
			phone = phone.replace("*", "");
			phone = phone.replace("&", "");
			phone = phone.replace("/", "");
			phone = phone.replace(" ", "");
			if (phone.length() > 11) {
				phone = phone.substring(phone.length() - 11);
			}
			if (!RegexUtil.isPhone(phone)) {
				continue;
			}
			if (has.contains(phone)) {
				continue;
			}
			has.add(phone);
			peoples.add(new People(name, phone));
		}
		if (peoples.size() == 0) {
			return new ReMsg(ReCode.OK);
		}
		mc.setCount(peoples.size());
		mc.setPeoples(peoples);
		save(mc);
		return new ReMsg(ReCode.OK);
	}

	private ReMsg coverUpload(String[] strings, DBObject dbo) {
		String[] ss = null;
		String name = null;
		String phone = null;
		long uid = DboUtil.getLong(dbo, "_id");
		ArrayList<DBObject> peoples = DboUtil.get(dbo, "peoples", ArrayList.class);
		int count=peoples.size();
		for (int i = 0; i < strings.length; i++) {
			// 取到姓名和电话
			ss = strings[i].split("=");
			if (ss.length != 2) {
				continue;
			}
			try {
				name = ss[0].trim();
				phone = ss[1].trim();
			} catch (Exception e) {
				continue;
			}
			if (StringUtils.isBlank(phone) || StringUtils.isBlank(name)) {
				continue;
			}
			phone = phone.replace("-", "");
			phone = phone.replace("(", "");
			phone = phone.replace(")", "");
			phone = phone.replace("*", "");
			phone = phone.replace("&", "");
			phone = phone.replace("/", "");
			phone = phone.replace(" ", "");
			if (phone.length() > 11) {
				phone = phone.substring(phone.length() - 11);
			}
			if (!RegexUtil.isPhone(phone)) {
				continue;
			}
			// 查询这个uid对应的通讯录中有没有他
			boolean exist = false;
			for (DBObject people : peoples) {
				if (phone.equals(DboUtil.getString(people, "rPhone"))) {
					exist = true;
					break;
				}
			}
			if (exist) {
				continue;
			}
			peoples.add(DboUtil.beanToDBO(new People(name, phone)));
			// if (null == queryByPhone(uid, phone)) {
			// pushMobile(uid, new People(name, phone));
			// // } else {
			// // people = new People(name, phone);
			// // changeMobileName(uid, people);
			// }
		}
		if (peoples.size() == 0) {
			getC(DocName.MOBILE_CONTACTS).remove(new BasicDBObject("_id", uid));
			return new ReMsg(ReCode.OK);
		}
		if(count==peoples.size()){
			return new ReMsg(ReCode.OK);
		}
		DBObject uo = new BasicDBObject("$set", new BasicDBObject("updateTime", System.currentTimeMillis())
				.append("count", peoples.size()).append("peoples", peoples));
		getC(DocName.MOBILE_CONTACTS).update(new BasicDBObject("_id", uid), uo);
		return new ReMsg(ReCode.OK);
		// if(count==0){
		// getC(DocName.MOBILE_CONTACTS).remove(new BasicDBObject("_id", uid));
		//
		// }
		// if (count != DboUtil.get(dbo, "peoples", ArrayList.class).size()) {
		// DBObject uo = new BasicDBObject("$set",
		// new BasicDBObject("updateTime",
		// System.currentTimeMillis()).append("count", count));
		// getC(DocName.MOBILE_CONTACTS).update(new BasicDBObject("_id", uid),
		// uo);
		// }
	}

	private DBObject findOne(long uid) {
		return getC(DocName.MOBILE_CONTACTS).findOne(new BasicDBObject("_id", uid));
	}

	// 检查该用户对应的这个手机号 是否存在
	private DBObject queryByPhone(long uid, String phone) {
		return getC(DocName.MOBILE_CONTACTS).findOne(new BasicDBObject("_id", uid).append("peoples.rPhone", phone));
	}

	// 添加联系人
	private void pushMobile(long uid, People p) {
		DBObject uo = new BasicDBObject("$push",
				new BasicDBObject("peoples", new BasicDBObject("rName", p.getrName()).append("rPhone", p.getrPhone())));
		getC(DocName.MOBILE_CONTACTS).update(new BasicDBObject("_id", uid), uo);
	}

	// 修改联系人name
	// private void changeMobileName(long uid, People p) {
	// DBObject uo = new BasicDBObject("$set", new
	// BasicDBObject("peoples.$.rName", p.getrName()));
	// getC(DocName.MOBILE_CONTACTS).update(new BasicDBObject("_id",
	// uid).append("peoples.rPhone", p.getrPhone()), uo,
	// false, true);
	// }

	private void save(MobileContacts mc) {
		mc.setUpdateTime(System.currentTimeMillis());
		getMongoTemplate().save(mc);
	}

	// private MobileContacts query(long uid, Integer page, Integer size) {
	// DBObject mobile = getC(DocName.MOBILE_CONTACTS).findOne(new
	// BasicDBObject("_id", uid));
	// if (null == mobile) {
	// return null;
	// }
	// MobileContacts mc = DboUtil.db2Bean(mobile, new MobileContacts());
	// mc.setCount(mc.getPeoples().size());
	// mc.setPeoples(getList(mc.getPeoples(), page, size));
	// return mc;
	// }
	private MobileContacts query(long uid) {
		DBObject mobile = getC(DocName.MOBILE_CONTACTS).findOne(new BasicDBObject("_id", uid));
		if (null == mobile) {
			return null;
		}
		MobileContacts mc = DboUtil.toBean(mobile, MobileContacts.class);
		mc.setCount(mc.getPeoples().size());
		// mc.setPeoples(getList(mc.getPeoples()));
		return mc;
	}

	// MobileContains转MobileVO
	private MobileVO getMobileVO(MobileContacts mc) {
		// 传递过来的是整个通讯录对象
		MobileVO mv = new MobileVO();
		Long uid = mc.get_id();
		for (People p : mc.getPeoples()) {
			DBObject object = userService.findByPhone(p.getrPhone());
			if (null == object) {
				mv.getNoRegs().add(new MobileVO().new Mobile(p.getrName(), p.getrPhone()));
				continue;
			}
			User user = DboUtil.toBean(userService.findById(DboUtil.getLong(object, "_id")), User.class);
			// 如果该手机号对应的是自己的bibi号 不添加
			if (uid - user.get_id() == 0) {
				continue;
			}
			int relation = relationshipService.getRelation(uid, user.get_id());
			if (relation != Relationship.FRIENDS && relation != Relationship.BLACK) {
				mv.getIsRegs()
						.add(new MobileVO().new Mobile(p.getrName(), user.getNickname(), user.get_id(),
								user.getAvatar(), relation, p.getrPhone(),
								user.getFriendWorth() == 0 ? 1 : user.getFriendWorth()));
			}
		}
		// 对通讯录联系人按照name进行排序
		ComparatorPeople comparator = new ComparatorPeople();
		Collections.sort(mv.getIsRegs(), comparator);
		Collections.sort(mv.getNoRegs(), comparator);

		mv.setIsReg(mv.getIsRegs().size());
		mv.setNoReg(mv.getNoRegs().size());
		return mv;
	}

	// MobileVO转MobileVO 分页
	private MobileVO getMobileVO(MobileVO mv, Integer page, Integer size) {
		int totalPage = getTotalPage(mv, size);
		int num = 0;
		List<Mobile> Null = new ArrayList<Mobile>();
		if (page > totalPage) {
			return null;
		}
		// if (page * size >= mv.getIsRegs().size() + mv.getNoRegs().size()) {
		// // 设置为已加载完毕
		// mv.setIsReg(-1);
		// }
		int start = super.getStart(page, size);
		int end = start + size;
		// 如果直接需要未注册数据
		if (mv.getIsRegs().size() <= start) {
			// 开始位置
			num = start - mv.getIsRegs().size();
			mv.setIsRegs(Null);
			// 如果未注册数据不够 则返回从num到结束
			if (mv.getNoRegs().size() <= num + size) {
				mv.setNoRegs(mv.getNoRegs().subList(num, mv.getNoRegs().size()));
				// 设置为已加载完毕
				return mv;
			}
			mv.setNoRegs(mv.getNoRegs().subList(num, num + size));
			return mv;
		}
		// 如果已注册数据不够
		if (mv.getIsRegs().size() <= end) {
			mv.setIsRegs(mv.getIsRegs().subList(start, mv.getIsRegs().size()));
			// 数据差值
			num = end - mv.getIsRegs().size();
			if (mv.getNoRegs().size() > num) {
				// 未注册太多 截取
				mv.setNoRegs(mv.getNoRegs().subList(0, num));
			}
			return mv;
		} else {
			// 已注册数据足够返回
			mv.setNoRegs(Null);
			mv.setIsRegs(mv.getIsRegs().subList(start, end));
			return mv;
		}
		// if (mv.getIsRegs().size() <= end) {
		// num = end - mv.getIsRegs().size();
		// if (mv.getNoRegs().size() <= num) {
		// return mv;
		// } else {
		// mv.setNoRegs(mv.getNoRegs().subList(0, num));
		// return mv;
		// }
		// } else {
		// mv.setNoRegs(Null);
		// mv.setIsRegs(mv.getIsRegs().subList(0, end));
		// return mv;
		// }
	}

	private int getTotalPage(MobileVO mv, Integer size) {
		int count = mv.getIsRegs().size() + mv.getNoRegs().size();
		int totalPage = count % size == 0 ? count / size : count / size + 1;
		return totalPage;
	}

	class ComparatorPeople implements Comparator {

		@Override
		public int compare(Object object1, Object object2) {
			Mobile info1 = (Mobile) object1;
			Mobile info2 = (Mobile) object2;
			// 按bean的名字排序
			return Collator.getInstance(Locale.CHINESE).compare(info1.getName(), info2.getName());
		}
	}

	public Page<DBObject> adminContacts(Long uid, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		DBObject q = new BasicDBObject();
		if (null != uid && uid > 0) {
			q.put("_id", uid);
		}
		List<DBObject> list = getC(DocName.MOBILE_CONTACTS).find(q).sort(new BasicDBObject("updateTime", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
		for (DBObject dbo : list) {
			DBObject user = userService.findById(DboUtil.getLong(dbo, "_id"));
			dbo.put("nickname", DboUtil.getString(user, "nickname"));
			dbo.put("avatar", DboUtil.getString(user, "avatar"));
			dbo.put("count", DboUtil.get(dbo, "peoples", ArrayList.class).size());
		}
		int count = getC(DocName.MOBILE_CONTACTS).find(q).count();
		return new Page<>(count, size, page, list);
	}

	public Page<DBObject> adminContactsDetail(Long uid, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		DBObject q = new BasicDBObject();
		if (null != uid && uid > 0) {
			q.put("_id", uid);
		}
		DBObject dbo = getC(DocName.MOBILE_CONTACTS).findOne(q);
		ArrayList<DBObject> peoples = DboUtil.get(dbo, "peoples", ArrayList.class);
		if (null == peoples || 0 == peoples.size()) {
			return new Page<>(0, 0, 0, peoples);
		}
		ArrayList<DBObject> isReg = new ArrayList<DBObject>();
		ArrayList<DBObject> noReg = new ArrayList<DBObject>();
		for (DBObject people : peoples) {
			DBObject r = userService.findByPhone(DboUtil.getString(people, "rPhone"));
			if (null == r) {
				noReg.add(people);
				continue;
			}
			if (DboUtil.getLong(r, "_id") - uid == 0) {
				continue;
			}
			Long rId = DboUtil.getLong(r, "_id");
			people.put("r", relationshipService.getRelation(uid, rId));
			people.put("rId", rId);
			people.put("rNickname", DboUtil.getString(r, "nickname"));
			if (null == DboUtil.getString(r, "avatar")) {
				people.put("rAvatar", "http://zim.zhuangdianbi.com/60b2361241d69c2ed676ce6ea37ece35.png");
			} else {
				people.put("rAvatar", DboUtil.getString(r, "avatar"));
			}
			isReg.add(people);
		}
		isReg.addAll(noReg);
		return new Page<>(peoples.size(), size, page, isReg);
	}
}
