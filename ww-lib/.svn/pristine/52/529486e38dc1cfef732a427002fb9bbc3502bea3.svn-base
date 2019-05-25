package com.zb.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.DateUtil;

@Service
public class ToolRedPacketService extends BaseService {

	static final Logger log = LoggerFactory
			.getLogger(ToolRedPacketService.class);

	@Autowired
	ContactHeadService contactHeadService;

	public class Pack {

		String name;// 用户的昵称 (发给 谁 或者 来自 谁)
		String amount;// 红包金额
		String date;// 发红包的时间
		String count;// 红包的数量
		String pic;// 用户头像 谁
		String type;// 红包类型 个人红包，群红包
		String typePic;// 红包类型图片
		String status = "已领取";// 红包状态
		String ft; // 发给或者来自

		public Pack() {

		}

		public Pack(String name, String amount, String date, String count) {
			this.name = name;
			this.amount = amount;
			this.date = date;
			this.count = count;
		}

		public Pack(String name, String amount, String date, String count,
				String pic, String type) {
			this.name = name;
			this.amount = amount;
			this.date = date;
			this.count = count;
			this.pic = pic;
			this.type = type;
		}

		public Pack(String name, String amount, String date, String count,
				String pic, String type, String status, String ft) {
			this.name = name;
			this.amount = amount;
			this.date = date;
			this.count = count;
			this.pic = pic;
			this.type = type;
			this.status = status;
			this.ft = ft;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public String getPic() {
			return pic;
		}

		public void setPic(String pic) {
			this.pic = pic;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getTypePic() {
			return typePic;
		}

		public void setTypePic(String typePic) {
			this.typePic = typePic;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getFt() {
			return ft;
		}

		public void setFt(String ft) {
			this.ft = ft;
		}

	}

	static Random r = new Random();

	static String[] packType = { "拼手气红包", "普通红包" };

	DecimalFormat df1 = new DecimalFormat("##0.00元");

	static Map<Integer, Integer> luckNums = new HashMap<Integer, Integer>();
	static {
		luckNums.put(1300, 14);
		luckNums.put(13100, 40);
		luckNums.put(500, 20);
		luckNums.put(5200, 00);
		luckNums.put(1800, 88);
		luckNums.put(18800, 88);
		luckNums.put(1700, 17);
		luckNums.put(5100, 51);
		luckNums.put(100, 11);
		luckNums.put(1100, 11);
		luckNums.put(11100, 11);
		luckNums.put(200, 22);
		luckNums.put(2200, 22);
		luckNums.put(300, 33);
		luckNums.put(3300, 33);
		luckNums.put(400, 44);
		luckNums.put(4400, 44);
		luckNums.put(500, 55);
		luckNums.put(5500, 55);
		luckNums.put(600, 66);
		luckNums.put(6600, 66);
		luckNums.put(700, 77);
		luckNums.put(7700, 77);
		luckNums.put(800, 88);
		luckNums.put(8800, 88);
		luckNums.put(900, 99);
		luckNums.put(9900, 99);

	}

	// points = {0,14,38,66,55,11,22,33,44,66,77,88,10,20,30,40,50,60,70,80,90};

	public static final String WX_PING = "http://imgzb.zhuangdianbi.com/5705dd7a0cf2e0e533bf4a13";

	public List<Pack> getWxPack(Double amount, Integer num, Integer year) {
		if (amount == null || amount <= 0) {
			amount = 1d;
		}
		if (num == null || num < 0) {
			num = 1;
		} else if (num > (int) (100 * amount)) {
			num = (int) (100 * amount);
		}

		Calendar c = getCalendar(year);
		int len = getLen(num);
		int[] arr = getArrs(amount, num, len);
		List<Pack> packs = new ArrayList<Pack>();
		List<DBObject> chs = getContactsHead();
		for (int i = 0; i < len; i++) {
			if (arr[i] > 20000) {
				arr[i] = 20000;
			}
			Pack p = getPack(c, arr[i], df1);
			DBObject dbo = chs.get(r.nextInt(chs.size()));
			p.setName(DboUtil.getString(dbo, "name"));
			int t = r.nextInt(20);
			if (t < 15) {
				p.setTypePic(WX_PING);
				p.setType(packType[0]);
			} else {
				p.setType(packType[1]);
			}
			packs.add(p);
		}

		return packs;
	}

	public List<DBObject> getContactsHead() {
		List<DBObject> chs = null;
		if (super.getUid() > 0) {
			chs = contactHeadService.findContactsHead(Const.STATUS_UP, null,
					super.getUid(), null, 0, 200);
		}
		if (chs == null) {
			chs = contactHeadService
					.findContactsHead(
							Const.STATUS_UP,
							null,
							null,
							ContactHeadService.CATE_NAME[ContactHeadService.CATE_NAME.length - 1],
							0, 200);
		} else if (chs.size() < 6) {
			chs.addAll(contactHeadService
					.findContactsHead(
							Const.STATUS_UP,
							null,
							null,
							ContactHeadService.CATE_NAME[ContactHeadService.CATE_NAME.length - 1],
							0, 200));
		}
		return chs;
	}

	private int[] getArrs(Double amount, Integer num, Integer len) {

		int[] arr = new int[len];

		if (len == 1) {
			arr[0] = (int) (amount * 100);
		} else {
			int argPack = (int) (amount * 100 / num);
			for (int i = 0; i < len; i++) {
				int cur = r.nextInt(argPack) + 1;
				arr[i] = arr[i] + cur;
				int idx = r.nextInt(len);
				arr[idx] = arr[idx] + (argPack - cur);
			}
		}
		return arr;
	}

	public List<Pack> putWxPack(Double amount, Integer num, Integer year) {
		if (amount == null || amount <= 0) {
			amount = 1d;
		}
		if (num == null || num < 0) {
			num = 1;
		} else if (num > (int) (100 * amount)) {
			num = (int) (100 * amount);
		}
		Calendar c = getCalendar(year);
		int len = getLen(num);
		int[] arr = getArrs(amount, num, len);
		List<Pack> packs = new ArrayList<Pack>();

		String name = null;
		for (int i = 0; i < len; i++) {
			int count = 0;
			if (arr[i] > 20000) {// 大于200红包
				if (arr[i] > 500000) {// 总金额大于5000
					arr[i] = 500000;
				} else {
					arr[i] = arr[i] / 100 * 100;
				}
			} else if (arr[i] > 100) {
				int key = arr[i] / 100 * 100;
				if (luckNums.containsKey(key)) {
					int t = r.nextInt(4);
					if (t == 0) {
						arr[i] = key;
					} else {
						arr[i] = key + luckNums.get(key);
						count = 1;
						name = packType[1];
					}
				} else {
					if (arr[i] > 10000) {
						arr[i] = key;
					} else {
						int t = r.nextInt(9);
						if (t > 2) {
							arr[i] = key;
						}
					}
				}
			}
			if (count == 0) {
				if (arr[i] > 20000) {
					int t = r.nextInt(3);
					if (t == 0) {
						count = r.nextInt(10) + 1;
					} else if (t == 1) {
						count = r.nextInt(50) + 1;
					} else {
						count = r.nextInt(100) + 1;
					}
				} else if (arr[i] > 10000) {
					int t = r.nextInt(12);
					if (t < 4) {
						count = 1;
					} else if (t < 7) {
						count = r.nextInt(20) + 1;
					} else {
						count = r.nextInt(100) + 1;
					}
				} else {
					int t = r.nextInt(16);
					if (t < 6) {
						count = 1;
					} else if (t < 8) {
						count = r.nextInt(10) + 1;
					} else if (t < 10) {
						count = r.nextInt(20) + 1;
					} else if (t < 12) {
						count = r.nextInt(40) + 1;
					} else {
						count = r.nextInt(100) + 1;
					}
				}
			}
			int cCount = arr[i] % 20000 == 0 ? arr[i] / 20000
					: arr[i] / 20000 + 1;

			// System.out.println(count);

			if (count < cCount) {
				count = cCount;
			}
			if (count > arr[i]) {
				count = arr[i];
			}
			if (count > 1) {
				name = packType[0];
			}
			if (name == null) {
				name = packType[r.nextInt(2)];
			}
			int ocount = count;
			if (count > 10) {
				if (r.nextInt(1000) < 5) {
					ocount = ocount - r.nextInt(count / 5);
				}
			}

			Pack p = getPack(c, arr[i], df1);
			p.setName(name);
			p.setType(name);
			p.setCount(ocount + "/" + count);
			packs.add(p);
			// System.out.print(p + " ");
		}

		return packs;
	}

	static final String[] FROM_OR_TO = { "来自", "发给", "包" };

	public List<Pack> getAliPack(Double amount, Integer num, Integer year) {
		if (amount == null || amount <= 0) {
			amount = 1d;
		}
		if (num == null || num < 0) {
			num = 1;
		} else if (num > (int) (100 * amount)) {
			num = (int) (100 * amount);
		}

		Calendar c = getCalendar(year);
		int len = getLen(num);
		int[] arr = getArrs(amount, num, len);
		List<Pack> packs = new ArrayList<Pack>();
		List<DBObject> chs = getContactsHead();
		for (int i = 0; i < len; i++) {
			if (arr[i] > 10000000) {
				arr[i] = 10000000;
			}
			Pack p = getPack(c, arr[i], df1, true, chs);
			p.setFt(FROM_OR_TO[0]);
			packs.add(p);
		}
		return packs;
	}

	public List<Pack> putAliPack(Double amount, Integer num, Integer year) {
		if (amount == null || amount <= 0) {
			amount = 1d;
		}
		if (num == null || num < 0) {
			num = 1;
		} else if (num > (int) (100 * amount)) {
			num = (int) (100 * amount);
		}
		Calendar c = getCalendar(year);
		int len = getLen(num);
		int[] arr = getArrs(amount, num, len);
		List<Pack> packs = new ArrayList<Pack>();
		List<DBObject> chs = getContactsHead();
		for (int i = 0; i < len; i++) {
			if (arr[i] > 10000000) {
				arr[i] = 10000000;
			}
			Pack p = getPack(c, arr[i], df1, false, chs);
			p.setCount(1 + "个红包");
			if ("群红包".equals(p.getType())) {
				int t = r.nextInt(20);
				if (t < 2) {
					p.setCount(1 + "个红包");
				} else if (t < 6) {
					p.setCount(r.nextInt(10) + 1 + "个红包");
				} else if (t < 10) {
					p.setCount(r.nextInt(20) + 1 + "个红包");
				} else if (t < 12) {
					p.setCount(r.nextInt(50) + 1 + "个红包");
				} else {
					p.setCount(r.nextInt(200) + 1 + "个红包");
				}
				p.setFt(FROM_OR_TO[2]);
			} else {
				p.setCount(1 + "个红包");
				p.setFt(FROM_OR_TO[1]);
			}

			packs.add(p);
			// System.out.print(p + " ");
		}

		return packs;
	}

	private static final String PERSONAL_RP = "http://imgzb.zhuangdianbi.com/56f20cc50cf2f2627dec897c";
	private static final String GROUP_RP = "http://imgzb.zhuangdianbi.com/56f20e470cf2f2627dec89a8";
	private static final String HAPPY_NEW_YEAR_RP = "http://imgzb.zhuangdianbi.com/56f20e470cf2f2627dec89a8";

	private Pack getPack(Calendar c, int amount, DecimalFormat df1,
			boolean isGet, List<DBObject> chs) {
		Pack p = new Pack();
		p.setAmount(df1.format(((double) amount) / 100));
		c.add(Calendar.HOUR_OF_DAY, r.nextInt(24) * -1);
		String date;
		if (c.getTimeInMillis() > DateUtil.getTodayZeroTime()) {
			date = "今天 " + DateUtil.dateFormat(c.getTime(), "HH:mm");
		} else {
			date = DateUtil.dateFormat(c.getTime(), "yyyy-MM-dd");
		}
		p.setDate(date);
		if (amount > 1000000) {
			p.setType("个人红包");
			p.setTypePic(PERSONAL_RP);
		} else if (amount < 300) {
			int i = r.nextInt(20);
			if (i < 13) {
				p.setType("个人红包");
				p.setTypePic(PERSONAL_RP);
			} else if (i < 6) {
				p.setType("拜年红包");
				p.setTypePic(HAPPY_NEW_YEAR_RP);
			} else {
				p.setType("群红包");
				p.setTypePic(GROUP_RP);
			}
		} else {
			int i = r.nextInt(10);
			if (i < 7) {
				p.setType("个人红包");
				p.setTypePic(PERSONAL_RP);
			} else {
				p.setType("群红包");
				p.setTypePic(GROUP_RP);
			}
		}

		DBObject dbo = chs.get(r.nextInt(chs.size()));
		p.setName(DboUtil.getString(dbo, "name"));
		p.setPic(DboUtil.getString(dbo, "headPic"));
		return p;
	}

	public List<Pack> getQqPack(Double amount, Integer num, Integer year) {
		if (amount == null || amount <= 0) {
			amount = 1d;
		}
		if (num == null || num < 0) {
			num = 1;
		} else if (num > (int) (100 * amount)) {
			num = (int) (100 * amount);
		}

		Calendar c = getCalendar(year);
		int len = getLen(num);
		int[] arr = getArrs(amount, num, len);
		List<Pack> packs = new ArrayList<Pack>();
		List<DBObject> chs = getContactsHead();
		for (int i = 0; i < len; i++) {
			if (arr[i] > 2000000) {
				arr[i] = 2000000;
			}
			Pack p = getQqPack(c, arr[i], df1, true, chs);
			packs.add(p);
		}
		return packs;
	}

	public List<Pack> putQqPack(Double amount, Integer num, Integer year) {
		if (amount == null || amount <= 0) {
			amount = 1d;
		}
		if (num == null || num < 0) {
			num = 1;
		} else if (num > (int) (100 * amount)) {
			num = (int) (100 * amount);
		}
		Calendar c = getCalendar(year);
		int len = getLen(num);
		int[] arr = getArrs(amount, num, len);
		List<Pack> packs = new ArrayList<Pack>();
		List<DBObject> chs = getContactsHead();
		for (int i = 0; i < len; i++) {
			if (arr[i] > 2000000) {
				arr[i] = 2000000;
			}
			Pack p = getQqPack(c, arr[i], df1, false, chs);
			packs.add(p);
		}

		return packs;
	}

	private static final String QQ_LING = "http://imgzb.zhuangdianbi.com/56f673350cf24e15d581a423";
	private static final String QQ_PIN = "http://imgzb.zhuangdianbi.com/56f6734b0cf24e15d581a431";

	private Pack getQqPack(Calendar c, int amount, DecimalFormat df1,
			boolean isGet, List<DBObject> chs) {
		Pack p = new Pack();
		p.setAmount(df1.format(((double) amount) / 100));
		c.add(Calendar.HOUR_OF_DAY, r.nextInt(12) * -1);
		String date = DateUtil.dateFormat(c.getTime(), "yyyy-MM-dd HH:mm:ss");
		p.setDate(date);
		int i = r.nextInt(10);
		if (i < 6) {
			p.setType("口令红包");
			p.setPic(QQ_LING);
		} else if (i < 8) {
			p.setType("普通红包");
		} else {
			p.setType("拼手气红包");
			p.setPic(QQ_PIN);
		}
		DBObject dbo = chs.get(r.nextInt(chs.size()));
		if (isGet) {
			p.setName(DboUtil.getString(dbo, "name"));
		} else {
			p.setName(p.getType());
			p.setStatus("已领完");
		}
		return p;
	}

	private Pack getPack(Calendar c, int amount, DecimalFormat df1) {
		Pack p = new Pack();
		p.setAmount(df1.format(((double) amount) / 100));
		c.add(Calendar.HOUR_OF_DAY, r.nextInt(12) * -1);
		p.setDate(DateUtil.dateFormat(c.getTime(), "MM-dd"));
		return p;
	}

	private int getLen(int num) {
		int len;
		if (num == 1) {
			len = 1;
		} else {
			len = num > 6 ? 6 : num;
		}
		return len;
	}

	private Calendar getCalendar(Integer year) {
		Calendar c = Calendar.getInstance();
		if (year == null || year < 2015) {
			year = c.get(Calendar.YEAR);
		}
		if (c.get(Calendar.YEAR) > year) {
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DAY_OF_MONTH, 31);
		}
		return c;
	}

	public static void main(String[] args) {
		ToolRedPacketService trp = new ToolRedPacketService();
		trp.getWxPack(2000.0, 10, 2016);
	}

}
