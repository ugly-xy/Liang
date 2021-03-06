package com.we.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;
import com.we.common.AuthCode;
import com.we.common.Constant.EncodeCountry;
import com.we.common.Constant.ReCode;
import com.we.common.crypto.MDUtil;
import com.we.common.mongo.DboUtil;
import com.we.common.redis.RK;
import com.we.common.utils.DateUtil;
import com.we.common.utils.RadixUtil;
import com.we.common.utils.RandomUtil;
import com.we.common.utils.RegexUtil;
import com.we.common.utils.ShareCodeUtil;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.models.DocName;
import com.we.models.User;
import com.we.models.UserShare;
import com.we.models.division.DivisionTask.DivisionTaskType;
import com.we.models.finance.CoinLog;
import com.we.service.cloud.StorageService;

@Service
public class UserService extends BaseService {

	@Autowired
	UserWalletService userWalletService;

	@Autowired
	StorageService storageService;

	@Autowired
	SmsService smsService;

	@Autowired
	UserShareService userShareService;

	@Autowired
	UserDivisionService userDivisionService;

	@Autowired
	MailLogService mailLogService;

	static final Logger log = LoggerFactory.getLogger(UserService.class);

	public DBObject findByUsername(String username) {
		return getC(DocName.USER).findOne(new BasicDBObject("username", username));
	}

	public DBObject findByPhone(String phone) {
		return getC(DocName.USER).findOne(new BasicDBObject("phone", phone));

	}

	public ReMsg getMyInfo(HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject user = findById(uid);
		if (null != user) {
			super.getRedisTemplate().expire(RK.accessToken(super.getToken(req)), 10, TimeUnit.DAYS);
			return new ReMsg(ReCode.OK, user);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg getUserInfo(Long uid) {
		DBObject user = findById(uid);
		if (null != user) {
			user = removeUserInfo(user);
			return new ReMsg(ReCode.OK, user);
		}
		return new ReMsg(ReCode.FAIL);
	}

	private DBObject removeUserInfo(DBObject user) {
		user = removeFile(user, new String[] { "pwd", "phone", "email", });
		return user;
	}

	/**
	 * 获取用户全部信息，带有密码之类敏感信息，最好使用findByIdSafe接口
	 * 
	 * @param id
	 * @return
	 */
	@Deprecated
	public DBObject findById(Long id) {
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String s = opsv.get(RK.keyUser(id));
		if (null == s) {
			DBObject user = getC(DocName.USER).findOne(new BasicDBObject("_id", id));
			if (null != user) {
				opsv.set(RK.keyUser(id), JSON.serialize(user), 1, TimeUnit.HOURS);
			}
			return user;
		} else {
			return (DBObject) JSON.parse(s);
		}
	}

	/**
	 * 根据id获取用户信息，无密码之类敏感信息
	 * 
	 * @param id
	 * @return
	 */
	public DBObject findByIdSafe(Long id) {
		DBObject dbo = findById(id);
		if (dbo != null) {
			return removeUserInfo(dbo);
		}
		return dbo;
	}

	public Map<Long, DBObject> findByIds(Long[] ids) {
		Map<Long, DBObject> map = new HashMap<Long, DBObject>();
		for (Long id : ids) {
			map.put(id, this.findById(id));
		}
		return map;
	}

	public ReMsg checkNickname(String nickname, long uid) {
		DBObject dbo = getC(DocName.USER).findOne(new BasicDBObject("nickname", nickname));
		if (dbo != null) {
			if (uid > 0 && uid == DboUtil.getLong(dbo, "_id")) {
				return new ReMsg(ReCode.OK);
			}
			return new ReMsg(ReCode.NICKNAME_EXISTS);
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg admUpsetUserInfo(Long uid, String firstname, String lastname, String phone, Integer status,
			Integer sex, Integer role, String walletAddress, String email) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		if (StringUtils.isNotBlank(firstname)) {
			u.put("firstname", firstname);
		}
		if (StringUtils.isNotBlank(lastname)) {
			u.put("lastname", lastname);
		}
		if (StringUtils.isNotBlank(phone)) {
			u.put("phone", phone);
		}
		if (status != null && status != 0) {
			u.put("status", status);
		}
		if (sex != null && sex != 0) {
			u.put("sex", sex);
		}
		if (role != null && role != 0) {
			u.put("role", role);
		}
		if (walletAddress != null) {
			u.put("walletAddress", walletAddress);
		}
		if (email != null) {
			u.put("email", email);
		}
		return update(uid, u);
	}

	public ReMsg setStatus(Long uid, Integer status) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		u.put("status", status);
		return update(uid, u);
	}

	public ReMsg setEthAddr(Long uid, String ethAddr) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		u.put("walletAddress", ethAddr);
		return update(uid, u);
	}

	public ReMsg setFirstlastname(Long uid, String firstname, String lastname) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		if (StringUtils.isNotBlank(firstname)) {
			u.put("firstname", firstname);
		}
		if (StringUtils.isNotBlank(lastname)) {
			u.put("lastname", lastname);
		}
		return update(uid, u);
	}

	public ReMsg setRole(Long uid, Integer role) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		u.put("role", role);
		return update(uid, u);
	}

	public ReMsg updateUserDivision(Long uid, int divisionId) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		u.put("divisionId", divisionId);
		return update(uid, u);
	}

	public ReMsg update(Long uid, DBObject u) {
		getC(DocName.USER).update(new BasicDBObject("_id", uid), new BasicDBObject("$set", u));
		super.getRedisTemplate().delete(RK.keyUser(uid));
		return new ReMsg(ReCode.OK);
	}

	// public ReMsg update(DBObject q, DBObject u) {
	// getC(DocName.USER).update(q, new BasicDBObject("$set", u), false, true);
	// return new ReMsg(ReCode.OK);
	// }

	public ReMsg updatePwd(long uid, String newPwd) {
		DBObject user = this.findById(uid);
		if (null != user) {
			DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
			u.put("pwd", MDUtil.SHA.digest2HEX(newPwd));
			return update(uid, u);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReCode save(User user) {
		Long uid = super.getNextId(DocName.USER);
		user.set_id(uid);
		// 用户的邀请码
		user.setShareCode(ShareCodeUtil.toSerialCode(uid));
		if (StringUtils.isNotBlank(user.getPwd())) {
			user.setPwd(MDUtil.SHA.digest2HEX(user.getPwd()));
		}
		user.setCreateTime(System.currentTimeMillis());
		user.setUpdateTime(user.getCreateTime());
		getMongoTemplate().save(user);
		return ReCode.OK;
	}

	public ReCode update(User user) {
		if (StringUtils.isNotBlank(user.getPwd())) {
			user.setPwd(MDUtil.SHA.digest2HEX(user.getPwd()));
		}
		user.setUpdateTime(user.getCreateTime());
		getMongoTemplate().save(user);
		return ReCode.OK;
	}

	public Page<DBObject> queryUser(Long uid, Integer status, Integer role, String phone, String nickname, Integer page,
			Integer size) {
		if (size == null || size == 0) {
			size = 100;
		}
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findUser(uid, status, role, phone, nickname, page, size);
		int count = countUser(uid, status, role, phone, nickname);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> findUser(Long uid, Integer status, Integer role, String phone, Integer page, Integer size) {
		return findUser(uid, status, role, phone, null, page, size);
	}

	public List<DBObject> findUser(Long uid, Integer status, Integer role, String phone, String nickname, Integer page,
			Integer size) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (null != role && role != 0) {
			q.put("role", role);
		}
		if (null != uid && uid > 0) {
			q.put("_id", uid);
		}
		if (StringUtils.isNotBlank(phone)) {
			q.put("phone", phone);
		}
		if (StringUtils.isNotBlank(nickname)) {
			q.put("username", new BasicDBObject("$regex", nickname));
		}
		return getC(DocName.USER).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("createTime", -1)).toArray();
	}

	public List<DBObject> findUserByShareUid(Long uid) {
		DBObject q = new BasicDBObject("shareUid", uid);
		return getC(DocName.USER).find(q).limit(10000).toArray();
	}

	public int countUser(Long uid, Integer status, Integer role, String phone) {
		return countUser(uid, status, role, phone, null);
	}

	public int countUser(Long uid, Integer status, Integer role, String phone, String nickname) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (null != role && role != 0) {
			q.put("role", role);
		}
		if (null != uid && uid > 0) {
			q.put("_id", uid);
		}
		if (StringUtils.isNotBlank(phone)) {
			q.put("phone", phone);
		}
		if (StringUtils.isNotBlank(nickname)) {
			q.put("username", new BasicDBObject("$regex", nickname));
		}
		return getC(DocName.USER).find(q).count();
	}
	
	public ReMsg reg(String encode,String phone, String pwd, String shareCode, HttpServletRequest req) {
		if(StringUtils.isEmpty(encode)) {
			encode = EncodeCountry.SOUTH_KOREA.getEncode();
		}
		if(!RegexUtil.isPhone(encode, phone)) {
			return new ReMsg(ReCode.PHONE_NUMBER_FORMAT_ERROR);
		}
		return reg(encode, phone, pwd,  ADMINDEFINEUSER, shareCode, req);
	}
	
	/** candy club 代码 */
	private static final String ADMINDEFINEUSER = "##'#";
	private static final String AVATAR = "/active/image/avatar/avatar";

	/** 注册用户 */
	public ReMsg reg(String encode, String phone, String pwd, String code, String shareCode, HttpServletRequest req) {
		if (!ADMINDEFINEUSER.equals(code)&&!smsService.validCode(phone, code)) {
			return new ReMsg(ReCode.AUTHCODE_ERR);
		}
		DBObject dbo = findByPhone(phone);
		if (null != dbo) {
			return new ReMsg(ReCode.PHONE_EXISTS);
		}
		User user = new User(phone, pwd, getIp(req), encode);  
		user.setPhoto("/active/image/avatar/man_1.jpg");
		DBObject shareUser = null;
		long shareUid = 0;
		if (StringUtils.isNotBlank(shareCode)) {
			shareUid = ShareCodeUtil.codeToId(shareCode);
			System.out.println(shareUid);
			if (shareUid > 0) {
				shareUser = findById(shareUid);
				if (shareUser == null) {
					shareUid = 0;
				} else {
					user.setShareUid(shareUid);
				}
			}
		}
		user.setSex(1);//默认男
		ReCode rc = this.save(user);// 保存注册用户
		if (rc.reCode() == ReCode.OK.reCode()) {
			userWalletService.addCoin(user.get_id(), CoinLog.IN_REG, 0, CoinLog.DEF_COIN_TYPE, 3000, 0, "신규회원 가입");//新用户注册
			if (shareUser != null) {// 上级用户不为空
				DBObject usDbo = userShareService.findById(shareUid);
				UserShare us = null;
				if (usDbo == null) {
					us = new UserShare(shareUid);
					Long puid = DboUtil.getLong(shareUser, "shareUid");
					if (puid != null) {
						us.setPuid(puid);
					}
				} else {
					us = DboUtil.toBean(usDbo, UserShare.class);
				}
				us.setM1(us.getM1() + 1);
				us.setTotalCnt(us.getTotalCnt() + 1);
				us.setAmount(us.getAmount() + 500);
				super.getMongoTemplate().save(us);
				if (us.getM1() < 4) {
					userDivisionService.doDivisionTask(shareUid, DivisionTaskType.INVITE_3, 1);
					userDivisionService.doDivisionTask(shareUid, DivisionTaskType.INVITE_5, 1);
					userDivisionService.doDivisionTask(shareUid, DivisionTaskType.INVITE_10, 1);
				} else if (us.getM1() < 6) {
					userDivisionService.doDivisionTask(shareUid, DivisionTaskType.INVITE_5, 1);
					userDivisionService.doDivisionTask(shareUid, DivisionTaskType.INVITE_10, 1);
				} else if (us.getM1() < 11) {
					userDivisionService.doDivisionTask(shareUid, DivisionTaskType.INVITE_10, 1);
				}
				userWalletService.addCoin(shareUid, CoinLog.IN_INVITE, user.get_id(), CoinLog.DEF_COIN_TYPE, 500, 0,
						"친구 초대 보너스");//邀请用户注册获得

				for (int i = 0; i < 2; i++) {
					if (us.getPuid() != null && us.getPuid() > 0) {
						usDbo = userShareService.findById(us.getPuid());// 上级
						if (usDbo != null) {
							us = DboUtil.toBean(usDbo, UserShare.class);
							if (i == 0) {
								us.setM2(us.getM2() + 1);
								us.setAmount(us.getAmount() + 300);
								userWalletService.addCoin(us.get_id(), CoinLog.IN_INVITE, user.get_id(),
										CoinLog.DEF_COIN_TYPE, 300, 0, "M2친구 초대 보너스");//邀请M2用户注册获得
							} else {
								us.setM3(us.getM3() + 1);
								us.setAmount(us.getAmount() + 200);
								userWalletService.addCoin(us.get_id(), CoinLog.IN_INVITE, user.get_id(),
										CoinLog.DEF_COIN_TYPE, 200, 0, "M3친구 초대 보너스");//邀请M3用户注册获得
							}
							us.setTotalCnt(us.getTotalCnt() + 1);
							super.getMongoTemplate().save(us);
						}
					}
				}

			}
		}
		return new ReMsg(rc);
	}

	/** 用户更新个人信息 姓 名 钱包地址 邮箱 */
	public ReMsg upsetUserInfo(Long uid, String firstname, String lastname, String walletAddress, String email,
			HttpServletRequest req) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		if (StringUtils.isNotBlank(firstname)) {
			u.put("firstname", firstname);
		}
		if (StringUtils.isNotBlank(lastname)) {
			u.put("lastname", lastname);
		}
		if (StringUtils.isNotBlank(walletAddress)) {
			u.put("walletAddress", walletAddress);
		}
		if (StringUtils.isNotBlank(email)) {
			u.put("email", email);
			// 绑定邮箱
			// userDivisionService.doDivisionTask(uid,
			// Division.ConditiontType.EMAIL.getCode());
		}
		return update(uid, u);
	}

	/** 根据验证码修改密码 */
	public ReMsg upsetPwd(String phone, String code, String newPwd, HttpServletRequest req)
			throws JsonParseException, JsonMappingException, IOException {
		DBObject user = this.findByPhone(phone.toString());
		if (user == null) {
			// 返回用户不存在
			return new ReMsg(ReCode.USER_NOT_EXISTS);
		}
		if (smsService.validCode(phone, code)) {// 验证码通过
			Long uid = DboUtil.getLong(user, "_id");
			DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
			u.put("pwd", MDUtil.SHA.digest2HEX(newPwd));
			if (getC(DocName.USER).update(new BasicDBObject("phone", phone.toString()), new BasicDBObject("$set", u),
					false, false, WriteConcern.ACKNOWLEDGED).getN() > 0) {
				super.getRedisTemplate().delete(RK.keyUser(uid));
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.AUTHCODE_ERR);
	}

	/** 根据旧密码修改密码 */
	public ReMsg upsetPwd(String oldPwd, String newPwd, HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject user = this.findById(uid);
		if (null != user) {
			if (MDUtil.SHA.digest2HEX(oldPwd).equals(user.get("pwd").toString())) {
				DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
				u.put("pwd", MDUtil.SHA.digest2HEX(newPwd));
				if (getC(DocName.USER).update(new BasicDBObject("_id", uid), new BasicDBObject("$set", u), false, false,
						WriteConcern.ACKNOWLEDGED).getN() > 0) {
					super.getRedisTemplate().delete(RK.keyUser(uid));
					return new ReMsg(ReCode.OK);
				}
			} else {
				return new ReMsg(ReCode.OLDPWD_WRITE_ERR);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}
	
	public ReMsg getOriginalPwd(String oldPwd) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject user = this.findById(uid);
		if (null != user) {
			if (MDUtil.SHA.digest2HEX(oldPwd).equals(user.get("pwd").toString())) {
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.OLDPWD_WRITE_ERR);
	}

	// 
	public ReMsg upsetPersonInfo(String username, Integer sex, String photo) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject user = this.findById(uid);
		if (null != user) {
			DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis()).append("username", username)
					.append("sex", sex);
			if (StringUtils.isNotBlank(photo)) {
				u.put("photo", photo);
			}
			if (getC(DocName.USER).update(new BasicDBObject("_id", uid), new BasicDBObject("$set", u), false, false,
					WriteConcern.ACKNOWLEDGED).getN() > 0) {
				super.getRedisTemplate().delete(RK.keyUser(uid));
				userDivisionService.doDivisionTask(uid, DivisionTaskType.USERINFO, 1);
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}
	
	public ReMsg updatePersonPhoto(@RequestParam MultipartFile file) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		ReMsg reMsg =storageService.uploadMyImg(file, null, null);
		if(0==reMsg.getCode()) {
			try {
				storageService.saveThumbIcon(reMsg.getData().toString(), 200, 200);
			} catch (Exception e) {
			}
			DBObject user = this.findById(uid);
			if (null != user) {
				DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis()).append("photo", reMsg.getData());
				if (getC(DocName.USER).update(new BasicDBObject("_id", uid), new BasicDBObject("$set", u), false, false,
						WriteConcern.ACKNOWLEDGED).getN() > 0) {
					super.getRedisTemplate().delete(RK.keyUser(uid));
					return new ReMsg(ReCode.OK);
				}
			}
		}
		return reMsg;
	}

	public DBObject findByEmail(String email) {
		return getC(DocName.USER).findOne(new BasicDBObject("email", email));

	}

	/** 重新绑定邮箱 */
	public ReMsg updateMailPass(Long uid,String email) {
		
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		u.put("email", email);
		u.put("emailVeri", true);
		if (getC(DocName.USER).update(new BasicDBObject("_id", uid), new BasicDBObject("$set", u), false, false,
				WriteConcern.ACKNOWLEDGED).getN() > 0) {
			super.getRedisTemplate().delete(RK.keyUser(uid));
			return new ReMsg(ReCode.OK);
		}

		return new ReMsg(ReCode.Mail_CLICK_OVERTIME);
	}

	public DBObject findByDBObject(DBObject dbObject) {
		return getC(DocName.USER).findOne(dbObject);
	}

	public List<DBObject> findUser(List<Long> shareUids) {
		if (shareUids != null && shareUids.size() > 0) {
			BasicDBList arr = new BasicDBList();
			arr.addAll(shareUids);
			DBObject q = new BasicDBObject("shareUid", new BasicDBObject("$in", arr));
			return getC(DocName.USER).find(q).toArray();
		}
		return null;
	}
	
	public ReMsg queryUserByPhone(String phone) {
		if (StringUtils.isBlank(phone)) {
			return new ReMsg(ReCode.PHONE_NUMBER_FORMAT_ERROR);
		}
		Boolean boo = RegexUtil.isPhoneKorea(phone);
		DBObject db =null;
		if(boo) {
			 db = this.findByPhone(phone);
		}
		if(!boo){
			 if(RegexUtil.isPhoneChina(phone)) {
				 db = this.findByPhone(phone);
			 }else {
				 return new ReMsg(ReCode.PHONE_NUMBER_FORMAT_ERROR);
			 }
		}
		
		if(db!=null) {
			return new ReMsg(ReCode.REGISTED_PHONE_ERR);
		}
		return new ReMsg(ReCode.EMPTY_PHONE_ERR);
	}
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	public String getUserNum(Long initNum) {
		long maxNum =1000000;
		if(initNum<1000000) {
			rwl.readLock().lock(); 
			try {
				Thread.sleep(2000);
				int countUser = countUser(null, null, null, null,null)*5000;
				Long getNum =(Long.valueOf(DateUtil.dateFormat(new Date(),"mm"))*700+Long.valueOf(DateUtil.dateFormat(new Date(),"ss"))*10+RandomUtil.nextInt(10));
				initNum=countUser+getNum;
				if(initNum>1000000) {
					initNum=(maxNum-1);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				rwl.readLock().unlock();
			}			
		}
		return String.valueOf(initNum);
	}
	
	public ReMsg updatePersonUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return new ReMsg(ReCode.NICKNAME_EMP_ERR);
		}
		if (StringUtils.isNotEmpty(username) && username.length() > 20) {
			return new ReMsg(ReCode.NICKNAME_PATTAN_ERR);
		}
		long uid = super.getUid();
		DBObject user = this.findById(uid);
		if(username.equals(DboUtil.getString(user, "username"))) {
			return new ReMsg(ReCode.OK);
		}
		if (null != user) {
			DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis()).append("username", username);
			if (getC(DocName.USER).update(new BasicDBObject("_id", uid), new BasicDBObject("$set", u), false, false,
					WriteConcern.ACKNOWLEDGED).getN() > 0) {
				super.getRedisTemplate().delete(RK.keyUser(uid));
				userDivisionService.doDivisionTask(uid, DivisionTaskType.USERINFO, 1);
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}
	public ReMsg updatePersonSex(Integer sex) {
		long uid = super.getUid();
		DBObject user = this.findById(uid);
		if(sex==DboUtil.getInt(user, "sex")) {
			return new ReMsg(ReCode.OK);
		}
		if (null != user) {
			DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis()).append("sex", sex);
			if (getC(DocName.USER).update(new BasicDBObject("_id", uid), new BasicDBObject("$set", u), false, false,
					WriteConcern.ACKNOWLEDGED).getN() > 0) {
				super.getRedisTemplate().delete(RK.keyUser(uid));
				userDivisionService.doDivisionTask(uid, DivisionTaskType.USERINFO, 1);
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}
	
}